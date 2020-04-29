/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.check.land;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 051101 盘盈登记明细_土地
 * @Table:
 * ASS_CHECK_P_DETAIL_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCheckPdetailLandService extends SqlService {

	Map<String, Object> queryAssInSpecialByPrintTemlatePrints(Map<String, Object> entityMap) throws DataAccessException;

}
