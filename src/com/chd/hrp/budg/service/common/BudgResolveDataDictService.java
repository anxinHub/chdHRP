/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.common;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.budg.entity.BudgFunPara;
/**
 * 
 * @Description:
 * 函数参数表
 * @Table:
 * FUN_PARA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface BudgResolveDataDictService extends SqlService {
	
	
	/**
	 * 查询数据 自定义分解系数明细数据( 医院月、科室年、科室月)
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgResolveData(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 删除数据 自定义分解系数 明细数据(医院月、科室年、科室月)
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteBudgResolveData(List<Map<String, Object>> listVo) throws DataAccessException;
	
	
}
