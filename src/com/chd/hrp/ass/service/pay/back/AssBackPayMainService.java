/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.pay.back;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_BACK_PAY_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackPayMainService extends SqlService {
	String updateBackPayConfirm(List<Map<String, Object>> entityMap);
//退款打印
	Map<String, Object> queryAssBackPayDY(Map<String, Object> map)
			throws DataAccessException;
	String initAssBackPaySave(Map<String, Object> mapVo)throws DataAccessException;
	List<String> queryAssBackPayState(Map<String, Object> mapVo)throws DataAccessException;
}
