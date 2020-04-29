/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.assremould.inassets;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050805 资产改造竣工(无形资产)
 * @Table:
 * ASS_REMOULD_F_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldFinassetsService extends SqlService {

	String queryAssRemouldFSourceInassets(Map<String, Object> entityMap);

	String queryAssRemouldFDetailInassets(Map<String, Object> mapVo);

	String saveAssRemouldFSourceInassets(Map<String, Object> mapVo);

	String deleteAssRemouldFDetailinassets(List<Map<String, Object>> listVo);

	String deleteAssRemouldFSourceInassets(List<Map<String, Object>> listVo);

	String updateConfirmAssRemouldFinassets(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo);

	

}
