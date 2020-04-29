package com.chd.hrp.ass.dao.resource;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.plan.AssPlanDeptDetail;
import com.chd.hrp.ass.entity.resource.AssApplyDeptResource;
import com.chd.hrp.ass.entity.resource.AssPlanDeptResource;

public interface AssPlanResourceMapper extends SqlMapper {
	/**
	 * 添加默认资金来源
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */

	public int addAssPlanSource(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询单个资金来源
	 * @param entityMap
	 * @return
	 */

	public AssPlanDeptResource queryAssPlanResource(Map<String, Object> entityMap);


	/***
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int saveResourceItem(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 删除资金来源
	 * @param entityList
	 * @throws DataAccessException
	 */

	public void deleteAssPlanResource(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<AssPlanDeptResource> queryAssPlanDeptDetailExists(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询资产是否重复
	 * @param validateMapVo
	 * @return
	 * @throws DataAccessException
	 */

	public List<AssPlanDeptDetail> queryByAssPlanDeptId(Map<String, Object> validateMapVo) throws DataAccessException;

	public List<AssPlanDeptDetail> queryByAssPlanDeptDetail(Map<String, Object> inMapVo);
	/**
	 * 查询
	 * @param entityMap
	 * @return
	 */

	public List<AssPlanDeptResource> queryResource(Map<String, Object> entityMap);
	/**
	 * 添加计划实际资金来源
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */

	public int addBatchAssAcceptItem(List<Map<String, Object>> list)throws DataAccessException;
	/**
	 * 删除默认资金来源
	 * @param mapVo
	 * @return
	 */

	public int deleteAssAcceptItem(Map<String, Object> mapVo)throws DataAccessException;
	/***
	 * 查询计划所有资金来源
	 * @param entityMap
	 * @return
	 */

	public List<AssPlanDeptResource> queryAssPlanResourceList(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 修改页面删除
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateDeleteAssPlanResource(List<Map<String, Object>> entityList)throws DataAccessException;
	public void deleteAssAcceptItemAssApplyDeptDetail(AssPlanDeptDetail assPlanDeptDetail)throws DataAccessException;
	//查询是否存在资金来源
	public List<AssPlanDeptResource> queryResourceAssPlanDeptResource(Map<String, Object> mapVo)throws DataAccessException;
	public void addAssPlanSourceImport(Map<String, Object> allMapVo)throws DataAccessException;
	//修改时候保存资金
	public void updateAssAcceptItemAssApplyDeptDetail(Map<String, Object> entityMap)throws DataAccessException;
	  //引入医院查询资金来源
	public List<AssPlanDeptResource> queryAssPlanResourceListImport(Map<String, Object> mapVo)throws DataAccessException;
	public List<AssPlanDeptResource> queryResourceDelete(Map<String, Object> mapVo)throws DataAccessException;

}
