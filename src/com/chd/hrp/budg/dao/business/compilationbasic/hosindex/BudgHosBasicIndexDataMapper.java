/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.compilationbasic.hosindex;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 医院基本指标数据维护
 * @Table:
 * BUDG_HOS_BASIC_INDEX_DATA
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgHosBasicIndexDataMapper extends SqlMapper{
	
	/**
	 * 查询 基本指标字典 budg_basic_index_dic  指标数据  增量生成用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgBasicIndexData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据 主键查询  数据是否存在
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 根据指标编码 查询 基本指标是否存在 导入用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryBasicIndexExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 指标名称下拉框 主页面用
	 */
	public List<Map<String, Object>> queryIndex(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询基本指标字典信息  导入时用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgBasicIndexDict(Map<String, Object> entityMap) throws DataAccessException;

	public Map<String, Object> queryIndexGetWay(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 批量 查询 添加数据是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExistList(List<Map<String, Object>> addList) throws DataAccessException;

	
}