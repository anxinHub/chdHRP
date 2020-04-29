/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.repair;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REP_TEAM_DEPT_MAP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRepTeamDeptMapMapper extends SqlMapper{

	List<Map<String, Object>> queryAssRepTeamDeptMapByTeamCode(Map<String, Object> mapVo);
	
}
