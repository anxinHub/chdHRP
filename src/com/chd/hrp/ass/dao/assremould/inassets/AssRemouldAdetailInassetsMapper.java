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
import com.chd.hrp.ass.entity.assremould.inassets.AssRemouldAdetailInassets;
import com.chd.hrp.ass.entity.resource.AssResourceInassets;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REMOULD_A_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldAdetailInassetsMapper extends SqlMapper{

	List<AssResourceInassets> queryByAssCardNo(Map<String, Object> detailVo);

	List<Map<String, Object>> queryAssRemouldADict(Map<String, Object> entityMap);

	List<AssRemouldAdetailInassets> queryByDisANo(Map<String, Object> mapVo);
	
}
