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
import com.chd.hrp.ass.entity.back.AssBackDetailSpecial;
/**
 * 
 * @Description:
 * 050701 资产退货单明细(专用设备)
 * @Table:
 * ASS_BACK_DETAIL_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackDetailSpecialMapper extends SqlMapper{
	
	
	public List<AssBackDetailSpecial> queryByAssBackNo(Map<String,Object> entityMap)throws DataAccessException;

	public List<AssBackDetailSpecial> queryAssBackSpecialDetail(
			Map<String, Object> mapVo);

	public void addAssBackSpecial(Map<String, Object> entityMap);

	public List<AssBackDetailSpecial> queryByBackDetailSpecial(
			Map<String, Object> inMapVo);

	public List<Map<String, Object>> queryByInit(Map<String, Object> mapVo);

}
