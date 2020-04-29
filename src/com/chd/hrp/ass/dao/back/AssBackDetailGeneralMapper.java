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
import com.chd.hrp.ass.entity.back.AssBackDetailGeneral;
/**
 * 
 * @Description:
 * 050701 资产退货单明细(一般设备)
 * @Table:
 * ASS_BACK_DETAIL_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackDetailGeneralMapper extends SqlMapper{
	public List<AssBackDetailGeneral> queryByAssBackNo(Map<String,Object> entityMap)throws DataAccessException;

	public List<AssBackDetailGeneral> queryByBackDetailGeneral(
			Map<String, Object> inMapVo);

	public void addAssBackGeneral(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryByInit(Map<String, Object> mapVo);
}
