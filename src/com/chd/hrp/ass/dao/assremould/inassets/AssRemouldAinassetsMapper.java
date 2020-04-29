/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assremould.inassets;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.assremould.inassets.AssRemouldAinassets;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REMOULD_A_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldAinassetsMapper extends SqlMapper{

	Map<String,Object> collectAssBackInassets(Map<String, Object> entityMap);

	void updateAssRemouldAinassetsConfirmState(List<Map<String, Object>> listVo);

	List<AssRemouldAinassets> queryAssApplyDeptByPlanDept(Map<String, Object> entityMap);

	List<AssRemouldAinassets> queryAssApplyDeptByPlanDept(Map<String, Object> entityMap, RowBounds rowBounds);

	
}
