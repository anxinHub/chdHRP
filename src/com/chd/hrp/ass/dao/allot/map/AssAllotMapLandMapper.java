/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.allot.map;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050201 无偿调拨出库与入库关系表(土地)
 * @Table:
 * ASS_ALLOT_MAP_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssAllotMapLandMapper extends SqlMapper{
	List<Map<String, Object>> queryByAllotInNo(Map<String,Object> entityMap) throws DataAccessException;
}
