/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.in.rest;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.in.rest.AssInRestDetailSpecial;
/**
 * 
 * @Description:
 * 050701 资产其他入库明细(专用设备)
 * @Table:
 * ASS_IN_REST_DETAIL_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssInRestDetailSpecialMapper extends SqlMapper{
	
	public List<Map<String,Object>> queryByInit(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public List<AssInRestDetailSpecial> queryByAssInNo(Map<String,Object> entityMap)throws DataAccessException;
}
