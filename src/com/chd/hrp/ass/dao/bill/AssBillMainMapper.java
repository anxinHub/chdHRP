/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.bill;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.bill.AssBillMain;
/**
 * 
 * @Description:
 * 发票主表
 * @Table:
 * ASS_BILL_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBillMainMapper extends SqlMapper{
	public int updateConfirm(List<Map<String, Object>> entityMap)throws DataAccessException; 

	public int updateAudit(List<Map<String, Object>> entityMap)throws DataAccessException;

	public int updateReAudit(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateBillMoney(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 付款发票主表打印
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryAssBillMainBatch(
			Map<String, Object> map);

	/**
	 * 付款发票从表打印
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryAssBill_detail(Map<String, Object> map);

	public Map<String, Object> querAssBillByPrint(Map<String, Object> map);

	public List<AssBillMain> queryBillMainImport(Map<String, Object> entityMap);

	public List<AssBillMain> queryBillMainImport(Map<String, Object> entityMap,
			RowBounds rowBounds);
	
	public List<Map<String, Object>> queryAssInAndAssChange(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAssInAndAssChange(Map<String, Object> entityMap,
			RowBounds rowBounds);
	

	public List<String> queryAssBillState(Map<String, Object> mapVo);

	public List<AssBillMain> queryByImportPay(Map<String, Object> entityMap);

	public List<AssBillMain> queryBillMainImports(Map<String, Object> entityMap);

	public List<AssBillMain> queryBillMainImports(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryInMainBill(
			Map<String, Object> entityMap);

	public List<Map<String, Object>> queryInMainBill(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryAssBillDetail_Add(
			Map<String, Object> item);

	public Map<String, Object> queryAssInAndAssChanges(
			Map<String, Object> map);



}
