/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.back;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.back.AssBackDetailOther;
/**
 * 
 * @Description:
 * 050701 资产退货单明细(其他固定资产)
 * @Table:
 * ASS_BACK_DETAIL_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackDetailOtherMapper extends SqlMapper{

	public List<AssBackDetailOther> queryByAssBackNo(Map<String, Object> entityMap);

	public List<AssBackDetailOther> queryByBackDetailOther(
			Map<String, Object> inMapVo);

	public void addAssBackOther(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryByInit(Map<String, Object> mapVo);
	
}
