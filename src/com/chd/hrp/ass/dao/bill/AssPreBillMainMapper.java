/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.bill;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_PRE_BILL_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssPreBillMainMapper extends SqlMapper{


	int checkAssPreBillMain(List<Map<String, Object>> listVo);
	// 预付款发票打印模板主表
	List<Map<String, Object>> queryAssPreBillMainBatch(Map<String, Object> map);
	//预付款发票打印模板从表
	List<Map<String, Object>> queryAssPreBill_detail(Map<String, Object> map);
	Map<String, Object> querAssPreBillByPrint(Map<String, Object> map);
	int updateNotToExamineAssPreBillMain(List<Map<String, Object>> listVo);
	List<String> queryAssPreBillState(Map<String, Object> mapVo);
}
