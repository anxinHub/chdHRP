/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.allot.out;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050901 资产无偿调拨出库单主表（一般设备）
 * @Table:
 * ASS_ALLOT_OUT_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssAllotOutGeneralService extends SqlService {
	public String updateAllotOutConfirm(List<Map<String, Object>> entityMap,List<Map<String, Object>> cardEntityMap)throws DataAccessException;

	public String queryByAllotInImport(Map<String, Object> entityMap)throws DataAccessException;

	public String queryByAllotInImportView(Map<String, Object> page)throws DataAccessException;
	
	/**
	 * 一般设备 资产调剂出口库 出库单状态查询（打印校验数据用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryAssAllotOutGeneralState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 一般设备 资产调剂 出库  打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssAllotOutGeneralByPrintTemlatePrint(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDetails(Map<String, Object> page);
}
