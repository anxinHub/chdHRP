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
 * 科室非医用材料支出预算编制
 * @Table:
 * BUDG_NO_MED_MAT
 * @Author: slient
 * @email:  slient@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgNoMedMatService extends SqlService {

	public String queryHosDeptDict(Map<String, Object> mapVo)throws DataAccessException;

	public String queryBudgMatTypeSubj(Map<String, Object> mapVo)throws DataAccessException;
	
	public String copyBudgNoMedMat(Map<String, Object> mapVo)throws DataAccessException;

	public String collectBudgNoMedMat(List<Map<String, Object>> listVo)throws DataAccessException;

	/**
	 * 查询数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
}
