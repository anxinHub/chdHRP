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
 * ASS_BACK_PRE_BILL_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackPreBillMainMapper extends SqlMapper{

	int checkAssBackPreBillMain(List<Map<String, Object>> listVo);
	//预退款发票打印主表
	List<Map<String, Object>> queryAssBackPreBillBatch(Map<String, Object> map);
	//预退款发票打印从表
	List<Map<String, Object>> queryAssBackPreBill_detail(Map<String, Object> map);
	Map<String, Object> querAssBackPreBillByPrint(Map<String, Object> map);
	void updateNotToExamineAssPreBillMain(List<Map<String, Object>> listVo);
	List<String> queryAssBackPreBillState(Map<String, Object> mapVo);
	
}
