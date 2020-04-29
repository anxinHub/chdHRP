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
import com.chd.hrp.budg.entity.BudgFunParaMethod;
/**
 * 
 * @Description:
 * 函数参数取值表
 * @Table:
 * FUN_PARA_METHOD
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface BudgFunParaMethodService extends SqlService {
	
	/**
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryFunParaByDict(Map<String, Object> mapVo)  throws DataAccessException;

	/**
	 * 查询 参数名称是否已被占用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNameExist(Map<String, Object> mapVo)  throws DataAccessException;

}
