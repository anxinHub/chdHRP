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
import com.chd.hrp.ass.entity.allot.out.AssAllotOutDetailInassets;
/**
 * 
 * @Description:
 * 050901 资产无偿调拨出库单明细(无形资产)
 * @Table:
 * ASS_ALLOT_OUT_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssAllotOutDetailInassetsMapper extends SqlMapper{
public List<AssAllotOutDetailInassets> queryByAllotOutNo(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssAllotOutDetailInassets> queryByImportAllotIn(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryByAllotOutNoMap(Map<String,Object> entityMap)throws DataAccessException;
}
