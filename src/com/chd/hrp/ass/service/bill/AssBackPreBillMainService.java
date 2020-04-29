/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.bill;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface AssBackPreBillMainService extends SqlService {

	String queryAssBackPreBilldetail(Map<String, Object> mapVo);

	String checkAssBackPreBillMain(List<Map<String, Object>> listVo);
	//预退发票打印
	Map<String, Object> queryAssBackPreBillDY(Map<String, Object> map)
			throws DataAccessException;

	String updateNotToExamineAssPreBillMain(List<Map<String, Object>> listVo);

	List<String> queryAssBackPreBillState(Map<String, Object> mapVo)throws DataAccessException;

}
