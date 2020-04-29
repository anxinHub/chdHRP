/** 
 * @Description:
 * @Copyright: Copyright (c) 2018-4-24 10:59:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.dao.base.budgpayitem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;


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
public interface ExpenditureItemBelongMapper extends SqlMapper {

	public List<Map<String, Object>> querydata(Map<String, Object> entityMap);

	public List<Map<String, Object>> querydata(Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String,Object>> queryDutyDept(Map<String, Object> entityMap);

	public List<Map<String,Object>> queryPaymentItem(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAddData(Map<String, Object> entityMap);

	public void addExpenditureItemBelongData(List<Map<String, Object>> list);

	public List<HashMap<String, Object>> queryAllDeptData(Map<String, Object> mapVo);

	public List<HashMap<String, Object>> queryAllPayment_itemData(Map<String, Object> mapVo);

	public int addBatchImport(Map<String, Object> mapVo);

	public void deleteAll(Map<String, Object> mapVo);
	
	
	
}
