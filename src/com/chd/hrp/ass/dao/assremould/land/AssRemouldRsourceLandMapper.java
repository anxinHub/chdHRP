/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assremould.land;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050805 资产改造记录资金来源(土地)
 * @Table:
 * ASS_REMOULD_R_SOURCE_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldRsourceLandMapper extends SqlMapper{

	List<Map<String, Object>> queryChangeSourceByAssCardNo(Map<String, Object> detailVo);

	List<Map<String, Object>> queryAssRemouldSourceLand(Map<String, Object> mapVo);
	
}
