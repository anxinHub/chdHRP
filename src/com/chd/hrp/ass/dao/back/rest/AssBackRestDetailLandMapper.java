/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.back.rest;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.back.rest.AssBackRestDetailLand;
/**
 * 
 * @Description:
 * 050701 资产其他退账单明细(土地)
 * @Table:
 * ASS_BACK_REST_DETAIL_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackRestDetailLandMapper extends SqlMapper{
	public List<AssBackRestDetailLand> queryByAssBackNo(Map<String,Object> entityMap)throws DataAccessException;
}
