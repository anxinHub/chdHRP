﻿/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.cost.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * @Description: 成本_分摊参数设置
 * @Table: COST_PARA_SET
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface CostParaSetService extends SqlService {

	/**
	 * @Description 分摊参数设置树形展示
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryByTree(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 分摊参数生成
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public String generate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 继承参数
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public String inheritance(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加科室明细
	 * @return int
	 * @throws DataAccessException
	*/
	public String addParaDeptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 添加成本项目明细
	 * @return int
	 * @throws DataAccessException
	 */
	public String addParaItemDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除科室明细
	 * @return int
	 * @throws DataAccessException
	 */
	public String deleteParaDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除成本项目明细
	 * @return int
	 * @throws DataAccessException
	 */
	public String deleteParaItemDetail(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 查询科室明细
	 * @return int
	 * @throws DataAccessException
	 */
	public List<?> queryParaDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 查询成本项目明细
	 * @return int
	 * @throws DataAccessException
	 */
	public List<?> queryParaItemDetail(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 获取最大值
	 * 
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMaxNo(Map<String, Object> map) throws DataAccessException;
}
