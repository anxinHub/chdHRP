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
 * @Table:
 * BUDG_AWARDS_ITEM_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgAwardsItemDictService extends SqlService {
	/**
	 * 查询 奖金项目名称是否已被占用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNameExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 期间属性是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryPeriodNatureExist(Map<String, Object> mapVo) throws DataAccessException;

}
