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
 * 科室材料支出预算
 * @Table:
 * BUDG_MAT
 * @Author: slient
 * @email:  slient@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMatService extends SqlService {

	public String queryHosDeptDict(Map<String, Object> mapVo)throws DataAccessException;

	public String queryBudgMatTypeSubj(Map<String, Object> mapVo)throws DataAccessException;

	public String collectBudgMat(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 校验数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;

	
}
