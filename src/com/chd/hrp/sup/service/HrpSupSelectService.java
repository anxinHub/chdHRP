package com.chd.hrp.sup.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.HrpSysSelect;

public interface HrpSupSelectService {
	public String queryDeptDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDeptDictLast(Map<String, Object> entityMap) throws DataAccessException;
	public String querySupDict(Map<String, Object> entityMap) throws DataAccessException;
	
			//采购类型
			public String querySupStockType(Map<String, Object> entityMap) throws DataAccessException;
			//采购发票 付款条件下拉框
			public String querySupPayTerm(Map<String, Object> entityMap) throws DataAccessException;
			//当前用户查看有权限的科室并且是采购科室
			public String querySupPurDept(Map<String, Object> entityMap) throws DataAccessException;
			//采购人
			public String querySupStockEmp(Map<String, Object> entityMap) throws DataAccessException;
			//编制科室
			public String querySupDeptDict(Map<String, Object> entityMap) throws DataAccessException;
			//医院名称
			public String queryHosInfo(Map<String, Object> entityMap) throws DataAccessException;
			

}
