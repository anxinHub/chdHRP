/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.toptodown.hosyearinbudg;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.budg.entity.BudgMedIncomeHosYear;
/**
 * 
 * @Description:
 * 医院年度医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_HOS_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedIncomeHosYearService extends SqlService {
	
	/**
	 * 上年收入查询 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryLastYearIncome(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 保存（包含 添加、修改）
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo)  throws DataAccessException;
	
	/**
	 * 增量生成时 查询要生成的数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException ;
	
	/**
	 * 增量生成
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String addGenerateData(List<Map<String, Object>> listVo)  throws DataAccessException ;
	
	/**
	 * 计算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collect(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 自上而下  医院年  科室各级科目汇总
	 * @param mapVo
	 * @return
	 */
	public String sumDeptBudgValue(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询导出数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<List<String>> queryExportData(Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String,Object>> getPrintData(Map<String,Object> mapVo) throws DataAccessException;
    /**
     *  生成 分解比例维护
     * @param listVo
     * @return
     * @throws DataAccessException
     */
	public String generateResolveRate(List<Map<String, Object>> listVo)throws DataAccessException;

	public List<Map<String,Object>> queryIncome(Map<String, Object> mapVo)throws DataAccessException;


}
