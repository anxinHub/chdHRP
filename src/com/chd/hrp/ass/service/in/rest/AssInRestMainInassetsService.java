/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.in.rest;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050701 资产其他入库主表(其他无形资产)
 * @Table:
 * ASS_IN_REST_MAIN_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssInRestMainInassetsService extends SqlService {
	public String initAssInCardInassets(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public String updateReAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public String updateConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * 其他无形资产 其他入库  入库单状态查询 （打印校验数据专用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryAssInRestInassetsState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 其他无形资产 其它入库  批量打印  新版打印  调用的方法
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssInRestInassetsByPrintTemlatePrint(Map<String, Object> entityMap) throws DataAccessException;

	public String initAssInRestBatchCardInassets(Map<String, Object> mapVo) throws DataAccessException;

	public String queryDetails(Map<String, Object> page) throws DataAccessException;
}
