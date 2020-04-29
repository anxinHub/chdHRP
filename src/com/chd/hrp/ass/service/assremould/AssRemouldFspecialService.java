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
 * 050805 资产改造竣工(专用设备)
 * @Table:
 * ASS_REMOULD_F_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldFspecialService extends SqlService {

	String queryAssRemouldFSourceSpecial(Map<String, Object> entityMap);

	String queryAssRemouldFDetailSpecial(Map<String, Object> mapVo);

	String saveAssRemouldFSourceSpecial(Map<String, Object> mapVo);

	String deleteAssRemouldFDetailspecial(List<Map<String, Object>> listVo);

	String deleteAssRemouldFSourceSpecial(List<Map<String, Object>> listVo);

	String updateConfirmAssRemouldFspecial(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo);

}
