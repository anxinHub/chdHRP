package com.chd.hrp.mat.service.cert;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MatCertTypeService {
	//证件类型查询
	public String queryMatCertTypeList(Map<String, Object> map) throws DataAccessException;
	//证件类型保存
	public Map<String, Object> saveMatCertType(Map<String, Object> map) throws DataAccessException;
	//证件类型删除
	public Map<String, Object> deleteMatCertType(Map<String, Object> map) throws DataAccessException;
	//证件类型启用
	public Map<String, Object> updateMatCertTypeToStart(Map<String, Object> map) throws DataAccessException;
	//证件类型停用
	public Map<String, Object> updateMatCertTypeToStop(Map<String, Object> map) throws DataAccessException;
	//证件类型导入
	public Map<String, Object> addMatCertTypeByImp(Map<String, Object> map) throws DataAccessException;
}
