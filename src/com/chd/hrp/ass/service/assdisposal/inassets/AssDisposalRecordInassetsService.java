/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.assdisposal.inassets;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 051001资产处置记录(无形资产)
 * @Table:
 * ASS_DISPOSAL_R_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssDisposalRecordInassetsService extends SqlService {

	public String updateConfirmDisposalRecordInassets(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo)throws DataAccessException;
	
	/**
	 * 新版打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> assDisposalRecordInassetsByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询所有未确认数据单号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String>  queryState(Map<String, Object> entityMap) throws DataAccessException;

	public String initAssCheckSpecial(Map<String, Object> mapVo);

	public String deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo);

	public String queryDetails(Map<String, Object> page);
	
	

}
