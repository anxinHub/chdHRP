/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.toptodown.hosmonthinbudg;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 医院月份医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_HOS_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface MedInHMBudgService extends SqlService {
	
	/**
	 * 查询 科室月份医疗收入预算 汇总数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectMedInHMBudgUp(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 导入时 查询数据是否已存在  （专用  勿动）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;

	public String save(List<Map<String, Object>> listVo);
	
	/**
	 * 查询数据 医院月份医疗收入预算  预算分解维护
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryResolve(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 分解维护页面 计算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String countMedInHMBudg(Map<String, Object> mapVo)	throws DataAccessException;
	
	/**
	 * 批量更新 预算值
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateBatchBudgValue(List<Map<String, Object>> listVo)throws DataAccessException ;
		
	/**
	 * 增量生成 查询数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询上年收入
	 * @param paraMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryLastYearIncome(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询导出数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<List<String>> queryExportData(Map<String, Object> mapVo) throws DataAccessException;
	
	
	public List<Map<String,Object>> getPrintData(Map<String,Object> mapVo)throws DataAccessException;
	public List<Map<String,Object>> queryHosMontBudgUpData(Map<String,Object> mapVo)throws DataAccessException;
}
