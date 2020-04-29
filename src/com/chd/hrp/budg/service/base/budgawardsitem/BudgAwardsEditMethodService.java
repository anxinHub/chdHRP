/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.base.budgawardsitem;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * BUDG_AWARDS_EDIT_METHOD
 * @Table:
 * BUDG_AWARDS_EDIT_METHOD
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgAwardsEditMethodService extends SqlService {
	/**
	 * 查询  编制方法与取值方法对应关系 是否存在
	 * @return
	 * @throws DataAccessException
	 */
	public int queryEditGetShip(Map<String,Object> entityMap) throws DataAccessException;

}
