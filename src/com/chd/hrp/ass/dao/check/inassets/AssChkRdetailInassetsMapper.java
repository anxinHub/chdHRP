/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.check.inassets;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.check.inassets.AssChkRdetailInassets;
/**
 * 
 * @Description:
 * 050701 资产盘亏记录明细(无形资产)
 * @Table:
 * ASS_CHK_R_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChkRdetailInassetsMapper extends SqlMapper{
	List<AssChkRdetailInassets> queryByAssChkNo(Map<String, Object> mapVo)throws DataAccessException;

	List<Map<String, Object>> queryStoreStore(List<Map<String, Object>> listVo)throws DataAccessException;

	List<Map<String, Object>> queryDeptDept(List<Map<String, Object>> listVo)throws DataAccessException;
}
