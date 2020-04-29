package com.chd.hrp.acc.service.autovouch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccMedAutoVouchService {
	
	//查询表头
	public String queryMedAutoVouchHead(Map<String,Object> entityMap)throws DataAccessException;
	
	//查询
	public String queryMedAutoVouch(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedAutoVouchPrint(Map<String, Object> entityMap) throws DataAccessException;

	//根据业务类型查询凭证Json
	public String queryVouchJsonByMed(Map<String, Object> entityMap) throws DataAccessException;
	
	//查询药品入库退货明细数据
	public String queryMedInBackDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/*生成自动凭证*/
	public String saveAutoVouch(Map<String, Object> entityMap) throws DataAccessException;
}
