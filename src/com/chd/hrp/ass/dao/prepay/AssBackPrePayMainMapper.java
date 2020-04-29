/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.ass.dao.prepay;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * 
 * @Description:
 * 预退款主表
 * @Table:
 * ASS_BACK_PRE_PAY_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
public interface AssBackPrePayMainMapper extends SqlMapper{

	/**
	 * 预退发票信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryAssBackPreBill(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 预付发票明细信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryAssBackPreBillDetail(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 支付方式下拉框
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryAccPayType(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 更新 预退款主表付款金额
	 * @param entityMap
	 * @throws DataAccessException
	 */
	int updatePayMoney(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 批量确认操作
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	int updateBatchConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * 组装预付核定表数据
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> collectAssPrePayData(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 预退款打印主表
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryAssBackPrePayMainBatch(
			Map<String, Object> map);
	/**
	 * 预退款打印从表
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryAssBackPrePayMain_detail(
			Map<String, Object> map);

	Map<String, Object> querAssBackPrePayMainByPrint(Map<String, Object> map);

	List<String> queryAssBackPrepayState(Map<String, Object> mapVo)throws DataAccessException;

}
