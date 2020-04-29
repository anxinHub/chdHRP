﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.assremould.other;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050805 资产改造竣工(其他固定资产)
 * @Table:
 * ASS_REMOULD_F_Other
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldFOtherService extends SqlService {

	String queryAssRemouldFSourceOther(Map<String, Object> entityMap);

	String queryAssRemouldFDetailOther(Map<String, Object> mapVo);

	String saveAssRemouldFSourceOther(Map<String, Object> mapVo);

	String deleteAssRemouldFDetailOther(List<Map<String, Object>> listVo);

	String deleteAssRemouldFSourceOther(List<Map<String, Object>> listVo);

	String updateConfirmAssRemouldFOther(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo);

}