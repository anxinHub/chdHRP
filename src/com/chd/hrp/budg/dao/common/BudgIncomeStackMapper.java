/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.common;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgFunPara;
/**
 * 
 * @Description:
 * 收入科目 函数参数栈
 * @Table:
 * BUDG_INCOME_STACK
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface BudgIncomeStackMapper extends SqlMapper{
	
	/**
	 * 根据 指标编码 查询 预算指标函数参数栈数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgIndexStackByCode(Map<String, Object> mapVo) throws DataAccessException;

	
}
