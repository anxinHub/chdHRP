package com.chd.hrp.ass.service.resource;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.resource.AssApplyDeptResource;
import com.chd.hrp.ass.entity.resource.AssPlanDeptResource;

public interface AssApplyDeptResourceService {

	public String addAssPlanSource(Map<String, Object> entityMap) throws DataAccessException;

	public String queryAssPlanResource(Map<String, Object> entityMap) throws DataAccessException;

	public String saveResourceItem(List<Map<String, Object>> listVo) throws DataAccessException;

	public String deleteAssPlanResource(List<Map<String, Object>> entityMap) throws DataAccessException;

	public AssApplyDeptResource queryAssPlanDeptResource(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 跳转更新页面查询资金来源
	 * @param mapVo
	 * @return
	 */

	public AssApplyDeptResource queryResource(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 跳转更新页面查询所有资金来源
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */

	public String queryAssPlanResourceList(Map<String, Object> page)throws DataAccessException;
	
    //审核时候查询资金来源
	public List<AssApplyDeptResource> queryResourceAssPlanDeptResource(Map<String, Object> mapVo)throws DataAccessException;

	public List<AssApplyDeptResource> queryAssPlanResourceListImport(Map<String, Object> mapVo)throws DataAccessException;
//主页删除查询数据
	public List<AssApplyDeptResource> queryResourcedelete(Map<String, Object> mapVo)throws DataAccessException;

}
