/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.bill.back;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.bill.AssBillMain;
import com.chd.hrp.ass.entity.bill.back.AssBackBillMain;
/**
 * 
 * @Description:
 * 红冲发票主表
 * @Table:
 * ASS_BACK_BILL_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackBillMainMapper extends SqlMapper{
	public int updateConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;

	public int updateAudit(List<Map<String, Object>> entityMap)throws DataAccessException;

	public int updateReAudit(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateBillMoney(Map<String, Object> entityMap)throws DataAccessException;
	//退款打印主表
	public List<Map<String, Object>> queryAssBackBillBatch(
			Map<String, Object> map);
	//退款打印从表
	public List<Map<String, Object>> queryAssBackBill_detail(
			Map<String, Object> map);

	public Map<String, Object> querAssBackBillByPrint(Map<String, Object> map);

	public List<AssBackBillMain> queryBackBillMainImport(
			Map<String, Object> entityMap);

	public List<AssBillMain> queryBackBillMainImport(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<String> queryAssBackPreBillState(Map<String, Object> mapVo)throws DataAccessException;

	public List<String> queryAssBackBillState(Map<String, Object> mapVo)throws DataAccessException;
	
	
	public List<Map<String, Object>> queryAssInAndAssChangeBack(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<Map<String, Object>> queryAssInAndAssChangeBack(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<AssBackBillMain> queryByImportPay(Map<String, Object> entityMap);

	public List<AssBillMain> queryBackBillMainImports(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<AssBackBillMain> queryBackBillMainImports(
			Map<String, Object> entityMap);
	
	

}
