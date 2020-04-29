/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.assremould.land;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050805 资产改造记录(土地)
 * @Table:
 * ASS_REMOULD_R_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldRlandService extends SqlService {

	String queryAssRemouldSourceLand(Map<String, Object> mapVo);

	String queryAssRemouldRdetailland(Map<String, Object> mapVo);

	String saveAssRemouldRSourceland(Map<String, Object> mapVo);



	String updateAssRemouldALandConfirmState(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo);

	String deleteAssSourceLand(List<Map<String, Object>> listVo);

	String initAssCheckland(Map<String, Object> mapVo);

	String deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo);

}
