/** 
 * @Description:
 * @Copyright: Copyright (c) 2018-4-24 10:50:50 
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.budg.service.base.budgpayitem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description:
 * 支出项目归口设置
 * @Table:
 * BUDG_DUTY_SET 支出项目归口设置表
 * @Author: zzb
 * @email:  jonZhang@e-tonggroup.com
 * @Version: 1.0
 */
public interface ExpenditureItemBelongService {

	public String queryExpenditureData(Map<String, Object> page);

	public String queryDutyDept(Map<String, Object> mapVo);

	public String queryPaymentItem(Map<String, Object> mapVo);

	public String addQueryExpenditureItemBelong(Map<String, Object> mapVo);

	public String addExpenditureItemBelongData(Map<String, Object> mapVo);

	public String deleteBatch(List<Map<String, Object>> listVo);

	public Map<String, Object> queryDictData(Map<String, Object> mapVo);

	public List<HashMap<String, Object>> queryAllDeptData(Map<String, Object> mapVo);

	public List<HashMap<String, Object>> queryAllPayment_itemData(Map<String, Object> mapVo);

	public String readFilesImport(Map<String, Object> mapVo, List<HashMap<String, Object>> listDept,
			List<HashMap<String, Object>> listPayment_item);
	
	
	
}
