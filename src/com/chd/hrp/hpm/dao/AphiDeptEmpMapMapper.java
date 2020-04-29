package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptEmpMap;

public interface AphiDeptEmpMapMapper extends SqlMapper{

	List<AphiDeptEmpMap> queryDeptEmpMap(Map<String, Object> mapVo, RowBounds rowBounds);

	AphiDeptEmpMap queryDeptEmpMapByCode(Map<String, Object> mapVo);

	int addDeptEmpMap(Map<String, Object> mapVo);

	int updateDeptEmpMap(Map<String, Object> mapVo);

	int deleteDeptEmpMap(Map<String, Object> mapVo);

	List<Map<String, Object>> queryHpmItem(Map<String, Object> entityMap);

	List<Map<String, Object>> queryHpmEmpItem(Map<String, Object> entityMap);

	int addBatchDeptEmpMap(List<Map<String,Object>> list);

}
