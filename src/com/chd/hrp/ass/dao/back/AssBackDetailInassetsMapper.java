/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.back;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.back.AssBackDetailInassets;
/**
 * 
 * @Description:
 * 050701 资产退货单明细(其他无形资产)
 * @Table:
 * ASS_BACK_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackDetailInassetsMapper extends SqlMapper{
	public List<AssBackDetailInassets> queryByAssBackNo(Map<String,Object> entityMap)throws DataAccessException;

	public List<AssBackDetailInassets> queryByBackDetailInassets(
			Map<String, Object> inMapVo);

	public void addAssBackInassets(Map<String, Object> entityMap);
}
