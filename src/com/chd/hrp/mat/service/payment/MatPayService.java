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
 * 付款单
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatPayService {
	//主查询
	public String queryMatPayList(Map<String, Object> map) throws DataAccessException;
	
	//加载单据
	public Map<String, Object> queryMatPayMainById(Map<String, Object> map) throws DataAccessException;
	public String queryMatPayDetailById(Map<String, Object> map) throws DataAccessException;
	
	//保存发票（新增和修改）
	public Map<String, Object> saveMatPay(Map<String, Object> map) throws DataAccessException;
	
	//删除发票
	public Map<String, Object> deleteMatPay(Map<String, Object> map) throws DataAccessException;
	
	//修改审核状态
	public Map<String, Object> updateMatPayState(Map<String, Object> map) throws DataAccessException;
	
	//批量修改备注
	public Map<String, Object> updateMatPayNote(Map<String, Object> map) throws DataAccessException;
	
	//查询发票
	public String queryMatBillByPay(Map<String, Object> map) throws DataAccessException;
	public String queryMatBillDetailByPay(Map<String, Object> map) throws DataAccessException;
	//根据所选发票生成付款单明细
	public String queryMatPayDetailByBill(Map<String, Object> map) throws DataAccessException;
	
	
	//模板打印（包含主从表）
	public String queryMatPayByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	//查询付款单打印数据
	public Map<String, Object> queryMatPayMainByPrintTemlateNew(Map<String, Object> entityMap) throws DataAccessException;
	//查询付款单明细打印数据
	public Map<String, Object> queryMatPayDetailByPrintTemlateNew(Map<String, Object> entityMap) throws DataAccessException;
	//表格打印
	public List<Map<String, Object>> queryMatPayMainForPrint(Map<String, Object> entityMap) throws DataAccessException;
}
