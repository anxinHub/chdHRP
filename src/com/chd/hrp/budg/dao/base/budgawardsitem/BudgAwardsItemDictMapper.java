/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.base.budgawardsitem;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 期间属性(PERIOD_NATURE):取自系统字典表  01年度 02半年度 03季度 04月度 05其他
 * @Table:
 * BUDG_AWARDS_ITEM_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgAwardsItemDictMapper extends SqlMapper{
	
	/**
	 * 查询 奖金项目名称是否已被占用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNameExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 期间属性是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryPeriodNatureExist(Map<String, Object> mapVo) throws DataAccessException;
	
}
