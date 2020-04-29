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
 * @Table: BUDG_Med_PUR
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */

public interface BudgMedPurService extends SqlService {

	public String queryBudgMedTypeSubj(Map<String, Object> mapVo)throws DataAccessException;

	public String collectBudgMed(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 *保存或修改数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String addOrUpdateBudgMedPur(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 保存  药品采购预算编制 数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String saveBudgMedPur(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 根据 预算年度 、 月份 、药品分类  查询其支出预算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCostBudg(Map<String, Object> mapVo) throws DataAccessException;

	
}
