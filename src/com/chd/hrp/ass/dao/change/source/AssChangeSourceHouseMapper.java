/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.change.source;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050805 资产原值变动资金来源(房屋及建筑物)
 * @Table:
 * ASS_CHARGE_SOURCE_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChangeSourceHouseMapper extends SqlMapper{
	List<Map<String, Object>> queryChangeSourceByAssCardNo(Map<String,Object> entityMap)throws DataAccessException;
}
