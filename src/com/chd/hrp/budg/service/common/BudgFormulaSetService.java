/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.common;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 计算公式
 * @Table:
 * BUDG_FORMULA_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgFormulaSetService extends SqlService {
	/**
	 * 计算元素 树加载
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public String queryElementTree(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 查询 计算公式元素栈 数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryFormulaStack(Map<String, Object> mapVo) throws DataAccessException;

}
