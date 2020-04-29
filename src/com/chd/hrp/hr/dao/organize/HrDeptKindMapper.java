package com.chd.hrp.hr.dao.organize;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.orangnize.HrDeptKind;
import com.chd.hrp.hr.entity.orangnize.HrDeptSelect;


public interface HrDeptKindMapper  extends SqlMapper{

	List<HrDeptKind> queryDeptKindById(Map<String, Object> entityMap);

	void addDeptKind(Map<String, Object> entityMap);
   
	int deleteDeptKind(List<Map<String, Object>> entityList);

	void deptKindUpate(Map<String, Object> entityMap);

	HrDeptKind queryDeptKindByName(Map<String, Object> entityMap);

	
	public List<HrDeptSelect> queryDeptKindDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<HrDeptKind> queryDeptKindByCodeName(Map<String, Object> entityMap)throws DataAccessException;

	public HrDeptKind queryDeptKindByCode(Map<String,Object> entityMap)throws DataAccessException;


}
