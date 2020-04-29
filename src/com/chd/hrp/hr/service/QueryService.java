package com.chd.hrp.hr.service;

import java.util.List;
import java.util.Map;
import java.util.stream.BaseStream;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.orangnize.HosStation;

public interface QueryService {
	/**
	 * 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String query(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询(修改页面跳转)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> queryByCode(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 打印
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryBaseInfoPtint(Map<String, Object> page)throws DataAccessException;
	/**
	 * 树形
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String  queryTree(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 返回普通json数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryJson(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 返回普通jsonDetail数据 一览统计
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryJsonDetail(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 模板打印
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> queryBaseInfoPrintTemp(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 导入动态表头
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrExcelColumn(Map<String, Object> mapVo) throws DataAccessException;
}
