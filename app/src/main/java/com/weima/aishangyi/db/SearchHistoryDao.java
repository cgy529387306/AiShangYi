package com.weima.aishangyi.db;

import android.database.Cursor;

import com.mb.android.utils.Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * function: user_history表
 *
 * @ author:cgy
 */
public class SearchHistoryDao extends BaseDao {
	
	
	public static String TABLE_NAME = "search_history";

	/**
	 * 
	 * function: 创建表 DataBaseOpenHelper->onCreate 调用
	 *
	 *
	 * @ author:cgy
	 */
	public void createTable() {
		execSQL("CREATE TABLE "+ TABLE_NAME +
				" ( " +
				" keyword VARCHAR(255)" +
				" );"); 
	}
	
	/**
	 * 判断搜索内容是否存在
	 * @param keyword
	 * @return
	 */
	public boolean exist(String keyword) {
		boolean isExist = false;
		int count = 0;
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) from ").append(TABLE_NAME).append(" where keyword=? ");
		Cursor cursor = rawQuery(sql.toString(), new String[] { keyword });
		if (cursor.moveToNext()) {
			count = cursor.getInt(0);
		}
		DataBaseOpenHelper.closeCursor(cursor);
		if (count > 0) {
			isExist = true;
		}
		return isExist;
	}
	

	/**
	 */
	public void add(String keyword){
		if (!exist(keyword)) {
			String sql = " insert into " + TABLE_NAME + " values(?)";
			execSQL(sql, new String[] {keyword});
		}
	}
	
	/**
	 * 添加景点搜索历史
	 */
	public void delete(String keyword){
		if (exist(keyword)) {
			String sql = " delete from " + TABLE_NAME + " where keyword = "+ keyword;
			execSQL(sql);
		}
	}
	
	/**
	 * 清除景点搜索历史
	 */
	public void clear() {
		String sql = " delete from " + TABLE_NAME;
		execSQL(sql);
	}
	
	/**
	 * 获取账号登录历史
	 * @return
	 */
	public List<String> getList() {
		List<String> list = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from ").append(TABLE_NAME);
		Cursor cursor = rawQuery(sql.toString(), new String[] {});
		while(cursor.moveToNext()) {
			String keyword = cursor.getString(cursor.getColumnIndex("keyword"));
			if (Helper.isNotEmpty(keyword)){
				list.add(keyword);
			}
		}
		DataBaseOpenHelper.closeCursor(cursor);
		return list;
	}
	
}
