/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.project.information;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * HOS_PROJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HosProjService extends SqlService {

	String endHosProj(List<Map<String, Object>> listVo);

	String escEndProj(List<Map<String, Object>> listVo);

	String suspendHosProj(List<Map<String, Object>> listVo);

	String escSuspendHosProj(List<Map<String, Object>> listVo);

}
