/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationbasic.zeyfincome;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 总额预付历史收入数据
 * @Table:
 * BUDG_ZEYF_INCOME_HIS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgZeyfIncomeHisService extends SqlService {
	
	/**
	 * 根据 科室编码 查询科室ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object>  queryBudgDeptId(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 医保类型编码 是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryInsuranceCodeExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据主键  查询总额预付历史收入数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 医保类型下拉框查询   添加页面用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgYBT(Map<String, Object> mapVo) throws DataAccessException;

	public String budgZeyfIncomeHisImport(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 保存 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo)throws DataAccessException;


}
