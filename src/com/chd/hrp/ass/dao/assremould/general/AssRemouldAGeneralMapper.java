package com.chd.hrp.ass.dao.assremould.general;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.assremould.general.AssRemouldAGeneral;

public interface AssRemouldAGeneralMapper extends SqlMapper{

	void collectAssBackGeneral(Map<String, Object> entityMap);

	void updateAssRemouldAGeneralConfirmState(List<Map<String, Object>> listVo);

	List<AssRemouldAGeneral> queryAssApplyDeptByPlanDept(Map<String, Object> entityMap);

	List<AssRemouldAGeneral> queryAssApplyDeptByPlanDept(
			Map<String, Object> entityMap, RowBounds rowBounds);

}
