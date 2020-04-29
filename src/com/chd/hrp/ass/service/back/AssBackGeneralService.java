/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.back;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050701 资产退货单主表(一般设备)
 * @Table:
 * ASS_BACK_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackGeneralService extends SqlService {
	public String updateBackConfirm(List<Map<String, Object>> entityMap,List<Map<String, Object>> cardEntityMap)throws DataAccessException;
	
	/**
	 * 一般设备 采购退货   退货单状态查询  （打印时校验数据专用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryAssBackGeneralState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 一般设备 采购退货  新版打印  调用方法
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssBackGeneralByPrintTemlatePrint(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDetails(Map<String, Object> page) throws DataAccessException;

	public String addAssBack(Map<String, Object> nMapVo);

	public String assImportBackIn(Map<String, Object> mapVo);

	public String updateAssBackMainBillNo(Map<String, Object> entityMap)throws DataAccessException;
}
