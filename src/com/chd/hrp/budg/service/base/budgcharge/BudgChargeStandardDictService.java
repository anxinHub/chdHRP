/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.base.budgcharge;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 费用标准    是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR> 
 * @Table:
 * BUDG_CHARGE_STANDARD_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgChargeStandardDictService extends SqlService {
	/**
	 * 判断费用标准名称是否被占用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNameExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 判断费用标准性质编码是否存在
	 * @param mapVo
	 * @return
	 */
	public int queryStanNatureExist(Map<String, Object> mapVo);
	
	/**
	 * 查询预算科室
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryChargeStanDeptSet(Map<String, Object> page) throws DataAccessException;

}
