/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.invdisburse;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 科室非医用材料支出
 * @Table:
 * BUDG_NO_MED_MAT_COST
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgNoMedMatCostService extends SqlService {
	
	/**
	 * 校验数据 是否存在
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> map)throws DataAccessException;
	
	/**
	 * 科室非医用材料支出 数据采集（系统内部数据采集）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectData(Map<String, Object> mapVo) throws DataAccessException;
}
