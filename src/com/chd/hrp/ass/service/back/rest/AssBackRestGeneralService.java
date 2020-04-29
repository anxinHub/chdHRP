/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.back.rest;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050701 资产其他退货单主表(一般设备)
 * @Table:
 * ASS_BACK_REST_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackRestGeneralService extends SqlService {
	public String updateBackConfirm(List<Map<String, Object>> entityMap,List<Map<String, Object>> cardEntityMap)throws DataAccessException;
	
	/**
	 * 一般设备 其他退货 退货单状态查询 (批量打印 校验数据用)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryAssBackRestGeneralState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 一般设备 其他退货  批量打印 数据查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssBackRestGeneralByPrintTemlatePrint(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDetails(Map<String, Object> page) throws DataAccessException;
}
