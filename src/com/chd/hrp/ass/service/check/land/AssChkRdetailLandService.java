/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.check.land;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.check.land.AssChkRdetailLand;
/**
 * 
 * @Description:
 * 050701 资产盘亏记录明细(土地)
 * @Table:
 * ASS_CHK_R_DETAIL_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChkRdetailLandService extends SqlService {
	List<AssChkRdetailLand> queryByAssChkNo(Map<String, Object> mapVo)throws DataAccessException;
}
