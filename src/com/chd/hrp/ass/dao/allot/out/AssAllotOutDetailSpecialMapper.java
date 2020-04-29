/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.allot.out;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutDetailSpecial;
/**
 * 
 * @Description:
 * 050901 资产无偿调拨出库单明细(专用设备)
 * @Table:
 * ASS_ALLOT_OUT_DETAIL_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssAllotOutDetailSpecialMapper extends SqlMapper{
	public List<AssAllotOutDetailSpecial> queryByAllotOutNo(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssAllotOutDetailSpecial> queryByImportAllotIn(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryByAllotOutNoMap(Map<String,Object> entityMap)throws DataAccessException;
}
