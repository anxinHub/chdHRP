/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.sell.out;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050901 资产有偿调拨出库单主表（一般设备）
 * @Table:
 * ASS_SELL_OUT_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssSellOutGeneralService extends SqlService {
	public String updateSellOutConfirm(List<Map<String, Object>> entityMap,List<Map<String, Object>> cardEntityMap)throws DataAccessException;

	public String queryBySellInImport(Map<String, Object> entityMap)throws DataAccessException;
	
	public String queryBySellInImportView(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 一般设备  资产调拨出库 出库单状态查询(打印校验数据用)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryAssSellOutGeneralState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 一般设备 资产调拨出库 新版打印  调用的方法
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssSellOutGeneralByPrintTemlatePrint(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDetails(Map<String, Object> page);
}
