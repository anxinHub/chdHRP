package com.chd.hrp.ass.dao.resource;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.apply.AssApplyDeptDetail;
import com.chd.hrp.ass.entity.resource.AssApplyDeptResource;
import com.chd.hrp.ass.entity.resource.AssPlanDeptResource;

public interface AssApplyDeptResourceMapper extends SqlMapper {

	public int addAssPlanSource(Map<String, Object> mapVo) throws DataAccessException;

	public AssApplyDeptResource queryAssPlanResource(Map<String, Object> entityMap);

	public List<Map<String, Object>> querySource(Map<String, Object> entityMap) throws DataAccessException;

	public int saveResourceItem(Map<String, Object> entityMap) throws DataAccessException;

	public void deleteAssPlanResource(List<Map<String, Object>> entityList) throws DataAccessException;

	public List<AssApplyDeptResource> queryAssPlanDeptDetailExists(Map<String, Object> mapVo) throws DataAccessException;

	public int updateAssPlanDeptDetail(Map<String, Object> entityMap) throws DataAccessException;

	public List<AssApplyDeptResource> queryByAssPlanDeptId(Map<String, Object> validateMapVo) throws DataAccessException;

	public List<AssApplyDeptResource> queryByAssPlanDeptDetail(Map<String, Object> inMapVo) throws DataAccessException;

	public int updateResourceItem(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 跳转时候查询资金来源
	 * @param entityMap
	 * @return
	 */

	public AssApplyDeptResource queryResource(Map<String, Object> entityMap);
	/**
	 * 添加资金来源时候删除原有资金来源
	 * @param mapVo
	 * @throws DataAccessException
	 */

	public void deleteAssAcceptItem(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 保存资金来源
	 * @param entityMap
	 * @throws DataAccessException
	 */

	public void addBatchAssAcceptItem(List<Map<String, Object>> entityMap)throws DataAccessException;
/**
 * 查询所有资金来源
 * @param entityMap
 * @return
 * @throws DataAccessException
 */
	public List<AssApplyDeptResource> queryAssPlanResourceList(Map<String, Object> entityMap)throws DataAccessException;

public void deleteAssAcceptItemAssApplyDeptDetail(AssApplyDeptDetail assApplyDeptDetail)throws DataAccessException;
//审核时候查询资金来源
public List<AssApplyDeptResource> queryResourceAssPlanDeptResource(Map<String, Object> mapVo)throws DataAccessException;

public List<AssApplyDeptResource> queryAssPlanResourceListImport(Map<String, Object> mapVo)throws DataAccessException;

public List<AssApplyDeptResource> queryResourcedelete(Map<String, Object> mapVo)throws DataAccessException;
//修改时保存资金来源
public void updateAssAcceptItemAssApplyDeptDetail(Map<String, Object> entityMap)throws DataAccessException;


  

}
