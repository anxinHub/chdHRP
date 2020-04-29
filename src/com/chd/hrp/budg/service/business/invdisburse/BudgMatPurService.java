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
 * @Description: 材料采购预算编制
 * @Table: BUDG_MAT_PUR
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */

public interface BudgMatPurService extends SqlService {

	public String queryBudgMatTypeSubj(Map<String, Object> mapVo)throws DataAccessException;

	public String collectBudgMat(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 *保存或修改数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String addOrUpdateBudgMatPur(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 保存  材料采购预算编制 数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String saveBudgMatPur(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 根据 预算年度 、 月份 、物资分类  查询其支出预算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCostBudg(Map<String, Object> mapVo) throws DataAccessException;

	public String save(List<Map<String, Object>> listVo)  throws DataAccessException;

	
}
