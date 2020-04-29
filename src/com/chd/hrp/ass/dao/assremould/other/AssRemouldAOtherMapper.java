package com.chd.hrp.ass.dao.assremould.other;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.assremould.AssRemouldAspecial;
import com.chd.hrp.ass.entity.assremould.other.AssRemouldAOther;

public interface AssRemouldAOtherMapper extends SqlMapper{

	void collectAssBackOther(Map<String, Object> entityMap);

	void updateAssRemouldAOtherConfirmState(List<Map<String, Object>> listVo);

	List<AssRemouldAOther> queryAssApplyDeptByPlanDept(Map<String, Object> entityMap, RowBounds rowBounds);

	List<AssRemouldAOther> queryAssApplyDeptByPlanDept(Map<String, Object> entityMap);

}
