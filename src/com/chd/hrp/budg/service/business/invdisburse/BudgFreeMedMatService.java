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
 * BUDG_FREE_MED_MAT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgFreeMedMatService extends SqlService {
	/**
	 * @Description 
	 * 生成  从科室非收费医用材料支出表中取上年（预算年度-1）数据生成本年预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	String generatebudgFreeMedMat(Map<String, Object> mapVo) throws Exception;

	String budgFreeMedMatUpdateAdjRate(List<Map<String, Object>> listVo) throws Exception;
	
	/**
	 * 查询数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;

}
