/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.repair;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REPAIR_TEAM_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRepairTeamDictService extends SqlService {

	String queryAssRepTeamDeptMapByTeamCode(Map<String, Object> mapVo);

	String queryRepUser(Map<String, Object> mapVo);

	String querySysUserNotExists(Map<String, Object> page);

	String addRepairUser(List<Map<String, Object>> listVo);

	String deleteAssRepairUser(List<String> list);

	Map<String, Object> queryUserById(Map<String, Object> mapVo);

	String updateAssRepairUser(Map<String, Object> mapVo);

}
