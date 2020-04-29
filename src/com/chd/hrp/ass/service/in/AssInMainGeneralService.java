/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.in;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 资产入库主表(专用设备)
 * @Table:
 * ASS_IN_MAIN_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssInMainGeneralService extends SqlService {
public String initAssInCardGeneral(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public String updateReAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public String updateConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public String updateAssInMainBillNo(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateIsPrint(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * 一般设备 采购入库  入库单状态查询 （打印时校验数据专用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryAssInGeneralState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 一般设备 采购入库  批量打印  新版打印  调用的方法
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssInGeneralByPrintTemlatePrint(Map<String, Object> entityMap) throws DataAccessException;

	
	public String initAssInBatchCardGeneral(Map<String, Object> mapVo) throws DataAccessException;

	public String queryDetails(Map<String, Object> page)throws DataAccessException;

	public String queryInMap(Map<String, Object> page);

	public String queryAssBackInMainGeneral(Map<String, Object> page);

	public Integer querycountStore(Map<String, Object> vStoreMap);

	public Integer querycountVen(Map<String, Object> vVenMap);
	
	public String queryByInitOut(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryInMainByOutNo(Map<String, Object> entityMap) throws DataAccessException;

	public String offsetInGeneral(List<Map<String, Object>> listVo) throws DataAccessException;
	
	public String assInGenerateBill(List<Map<String, Object>> listVo) throws DataAccessException;
}
