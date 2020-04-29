
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.service.base.budgybinfor;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 付费机制
 * @Table:
 * HEALTH_INSURANCE_PAY_MODE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface BudgPayModeService extends SqlService{
	/**
	 * 生成 付费机制
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String resetBudgePayMode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询医保类型编码是否存在或已停用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryInsCodeExist(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询付费机制编码是否存在或已停用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryPayModCodeExist(Map<String, Object> mapVo) throws DataAccessException;

	
}
