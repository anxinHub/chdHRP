package com.chd.hrp.mat.service.cert;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;

public interface MatProdCertService {
	//产品注册证列表查询
	public String queryMatProdCertList(Map<String, Object> map) throws DataAccessException;
	//产品注册证查询
	public Map<String, Object> queryMatProdCertById(Map<String, Object> map) throws DataAccessException;
	//产品注册证保存
	public Map<String, Object> saveMatProdCert(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws DataAccessException;
	//产品注册证删除
	public Map<String, Object> deleteMatProdCert(Map<String, Object> map) throws DataAccessException;
	//产品注册证认证
	public Map<String, Object> updateMatProdCertToAuthent(Map<String, Object> map) throws DataAccessException;
	//产品注册证取消认证
	public Map<String, Object> updateMatProdCertToUnAuthent(Map<String, Object> map) throws DataAccessException;
	//产品注册证审核
	public Map<String, Object> updateMatProdCertToCheck(Map<String, Object> map) throws DataAccessException;
	//产品注册证取消审核
	public Map<String, Object> updateMatProdCertToUnCheck(Map<String, Object> map) throws DataAccessException;
	//产品注册证导入
	public Map<String, Object> addMatProdCertByImp(Map<String, Object> map) throws DataAccessException;
	
	//查询材料列表
	public String queryMatInvList(Map<String, Object> map) throws DataAccessException;
	
	//查询关联材料
	public String queryMatProdCertInvList(Map<String, Object> map) throws DataAccessException;
	//保存关联材料
	public Map<String, Object> saveMatProdCertInv(Map<String, Object> map) throws DataAccessException;
	//选择新证查询
	public String queryMatProdCertChooseList(Map<String, Object> map) throws DataAccessException;
	//选择新证
	public Map<String, Object> updateMatProdCertToNewCert(Map<String, Object> map) throws DataAccessException;
	//取消新证
	public Map<String, Object> updateMatProdCertToUnNewCert(Map<String, Object> map) throws DataAccessException;
}
