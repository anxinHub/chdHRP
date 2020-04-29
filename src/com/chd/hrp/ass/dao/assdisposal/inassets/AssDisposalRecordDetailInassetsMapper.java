/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assdisposal.inassets;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.assdisposal.inassets.AssDisposalRecordDetailInassets;
/**
 * 
 * @Description:
 * 050701 资产处置记录明细(无形资产)
 * @Table:
 * ASS_DISPOSAL_R_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssDisposalRecordDetailInassetsMapper extends SqlMapper{

	List<AssDisposalRecordDetailInassets> queryByDisRNo(Map<String, Object> mapVo)throws DataAccessException;
	
}
