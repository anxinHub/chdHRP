package com.chd.hrp.ass.service.resource;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.resource.AssApplyDeptResource;
import com.chd.hrp.ass.entity.resource.AssPlanDeptResource;

public interface AssPlanResourceService {

	public String addAssPlanSource(Map<String, Object> entityMap) throws DataAccessException;

//	public String queryAssPlanResource(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryAssPlanResourceList(Map<String, Object> entityMap) throws DataAccessException;

	public String saveResourceItem(List<Map<String, Object>> entityMap) throws DataAccessException;

	public String deleteAssPlanResource(List<Map<String, Object>> entityMap) throws DataAccessException;

	public AssPlanDeptResource queryAssPlanDeptResource(Map<String, Object> entityMap)throws DataAccessException;

	public List<AssPlanDeptResource> queryResource(Map<String, Object> entityMap)throws DataAccessException;

	public String updateDeleteAssPlanResource(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<AssPlanDeptResource> queryResourceAssPlanDeptResource(Map<String, Object> mapVo)throws DataAccessException;
   //引入购置申请添加资金来源
	public String addAssPlanSourceImport(Map<String, Object> allMapVo)throws DataAccessException;
  //引入医院查询资金来源
	public List<AssPlanDeptResource> queryAssPlanResourceListImport(Map<String, Object> mapVo)throws DataAccessException;

	public List<AssPlanDeptResource> queryResourceDelete(Map<String, Object> mapVo)throws DataAccessException;



}
