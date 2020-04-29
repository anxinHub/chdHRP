/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.fixedassets;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 

 * @Table:
 * BUDG_ASS_CARD
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgAssCardMapper extends SqlMapper{


	public int addBudgAssCardSource(List<Map<String, Object>> detailAddList);

	public String querynatursCode(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryBudgAssCardSourceBySourceId(Map<String, Object> mapVo)throws DataAccessException;

	public int updateBudgAssCardSource(List<Map<String, Object>> detailAddList)throws DataAccessException;

	public Map<String, Object> queruBudgAssCardSource(Map<String, Object> detailMap)throws DataAccessException;

	public int deleteBudgAssCardSource(Map<String, Object> detailMap)throws DataAccessException;
	
	/**
	 * 校验数据是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询 资产基本信息(根据code 匹配ID用)
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssDictData(Map<String, Object> map)throws DataAccessException;
	
	/**
	 * 查询 科室基本信息(根据code 匹配ID用)
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptData(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 查询 仓库基本信息(根据code 匹配ID用)
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryStoreData(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 查询 资产折旧方法基本信息(校验资产折旧方法用)
	 * @param paraMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryDeprMethod(Map<String, Object> paraMap)throws DataAccessException;
	
	/**
	 * 查询 资金来源基本信息(根据code 匹配ID用)
	 * @param paraMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> querySourceData(Map<String, Object> paraMap)throws DataAccessException;

	/**
	 * 校验资产现状_资金来源 数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExistSource(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 查询资产卡片数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int insertCollectData(Map<String, Object> entityMap) throws DataAccessException;

	
}
