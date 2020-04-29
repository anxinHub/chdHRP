/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.pay.main;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 付款主表
 * @Table:
 * ASS_PAY_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssPayMainService extends SqlService {
	String updatePayConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;

	Map<String, Object> queryAssPayDY(Map<String, Object> map)
			throws DataAccessException;

	String initAssPaySave(Map<String, Object> mapVo) throws DataAccessException;

	List<String> queryAssPayState(Map<String, Object> mapVo)throws DataAccessException;

	
}
