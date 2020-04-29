/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.assremould;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050805 资产改造记录(专用设备)
 * @Table:
 * ASS_REMOULD_R_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldRspecialService extends SqlService {

	String queryAssRemouldSourceSpecial(Map<String, Object> mapVo);

	String queryAssRemouldRdetailspecial(Map<String, Object> mapVo);

	String saveAssRemouldRSourcespecial(Map<String, Object> mapVo);

	String updateAssRemouldAspecialConfirmState(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo);

	String deleteAssSourceSpecial(List<Map<String, Object>> listVo);

	String initAssCheckSpecial(Map<String, Object> mapVo);

	String deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo);

}
