/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.med;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description: 药品采购预算编制
 * @Table: BUDG_MED_PUR
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */

public interface BudgMedPurExeService extends SqlService {

	public String queryBudgMedTypeSubj(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 *保存或修改数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String addOrUpdateBudgMedPurExe(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 保存  药品采购预算执行 数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String saveBudgMedPurExe(List<Map<String, Object>> listVo) throws DataAccessException;

	
}
