/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.invdisburse;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * BUDG_CHARGE_MAT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgChargeMatService extends SqlService {
	/**
	 * @Description 
	 * 生成  根据年度月份物资分类生成本年度支出预算数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String generateBudgChargeMat(Map<String, Object> mapVo) throws DataAccessException;
	
	public String budgChargeMatUpdateAdjRate(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 查询数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;

}
