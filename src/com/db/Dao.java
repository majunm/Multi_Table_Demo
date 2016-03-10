package com.db;

import java.io.Serializable;
import java.util.List;

/**
 * 通用的实体操作接口
 * 
 * @author l
 * 
 */
public interface Dao<T> {
	/**
	 * 增加
	 * 
	 * @param m
	 * @return
	 */
	long insert(T m);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	int delete(Serializable id);// int long String

	/**
	 * 修改
	 * 
	 * @param m
	 * @return
	 */
	int update(T m);

	/**
	 * 查询
	 * 
	 * @return
	 */
	List<T> findAll();
}
