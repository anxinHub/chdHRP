/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.payment;
import java.util.*;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @Description:
 * 采购发票
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatBillService {
	//主查询
	public String queryMatBillList(Map<String, Object> map) throws DataAccessException;
	
	//加载单据
	public Map<String, Object> queryMatBillMainById(Map<String, Object> map) throws DataAccessException;
	public String queryMatBillDetailById(Map<String, Object> map) throws DataAccessException;
	
	//保存发票（新增和修改）
	public Map<String, Object> saveMatBill(Map<String, Object> map) throws DataAccessException;
	
	//删除发票
	public Map<String, Object> deleteMatBill(Map<String, Object> map) throws DataAccessException;
	
	//修改审核状态
	public Map<String, Object> updateMatBillState(Map<String, Object> map) throws DataAccessException;
	
	//批量修改备注
	public Map<String, Object> updateMatBillNote(Map<String, Object> map) throws DataAccessException;
	
	//查询入库单
	public String queryMatInByBill(Map<String, Object> map) throws DataAccessException;
	public String queryMatInDetailByBill(Map<String, Object> map) throws DataAccessException;
	//根据所选入库单生成采购发票明细
	public String queryMatBillDetailByIn(Map<String, Object> map) throws DataAccessException;
	
	
	//模板打印（包含主从表）
	public String queryMatBillByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	
	//查询 发票是否已付款
	public int queryPayOrNot(Map<String, Object> map) throws DataAccessException;
	
	//模板打印
	public Map<String,Object> queryMatBillMainByPrintPage(Map<String, Object> entityMap)throws DataAccessException;
	//表格打印
	public List<Map<String, Object>> queryMatBillMainForPrint(Map<String, Object> entityMap) throws DataAccessException;
}
