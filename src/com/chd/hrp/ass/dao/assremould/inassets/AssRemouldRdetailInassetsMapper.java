/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assremould.inassets;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.assremould.inassets.AssRemouldRdetailInassets;
/**
 * 
 * @Description:
 * 050805 资产改造记录明细表(无形资产)
 * @Table:
 * ASS_REMOULD_R_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldRdetailInassetsMapper extends SqlMapper{

	List<Map<String, Object>> queryByRecordNoMap(Map<String, Object> mapVo);

	int updatePriceBySource(Map<String, Object> entityMap);

	List<AssRemouldRdetailInassets> queryByDisANo(Map<String, Object> mapVo);
	
}
