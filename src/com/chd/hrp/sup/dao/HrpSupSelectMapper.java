package com.chd.hrp.sup.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.nutz.dao.SqlManager;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.sys.entity.HrpSysSelect;


public interface HrpSupSelectMapper extends SqlMapper{
	// 部门查询
		public List<HrpSysSelect> queryDeptDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

		// 末级科室
		public List<HrpSysSelect> queryDeptDictLast(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		// 供应商
		public List<HrpSysSelect> querySupDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		
		//采购类型
		public List<HrpSysSelect> querySupStockType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//采购发票 付款条件下拉框
		public List<HrpSysSelect> querySupPayTerm(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//当前用户查看有权限的科室并且是采购科室
		public List<HrpSysSelect> querySupPurDept(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//采购人
		public List<HrpSysSelect> querySupStockEmp(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//编制科室
		public List<HrpSysSelect> querySupDeptDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//医院名称
		public List<HrpSysSelect> queryHosInfo(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		
}
