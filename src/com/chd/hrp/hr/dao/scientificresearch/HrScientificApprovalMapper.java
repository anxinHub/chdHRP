package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrScientificApproval;
/**
 * 科研项目立项
 * @author Administrator
 *
 */
public interface HrScientificApprovalMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrScientificApproval> queryScientificApprovalById(Map<String, Object> entityMap);
    /**
     * 删除科研项目立项
     * @param entityList
     */
	void deleteScientificApproval(List<HrScientificApproval> entityList);
	

}
