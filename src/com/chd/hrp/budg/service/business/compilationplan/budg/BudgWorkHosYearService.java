/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationplan.budg;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 医院年度业务预算
 * @Table:
 * BUDG_WORK_HOS_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkHosYearService extends SqlService {
	
	/**
	 * 从 医院业务执行数据(年度) BUDG_WORK_HOS_EXECUTE_YEAR 表中查询 上年业务量
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryLastYearWork(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 生成运营尺度数据
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryProbBudgRate(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 医院年度业务预算查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgWorkHosYear(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 取值方法 查询
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryGetWay(Map<String, Object> mapVo);
	
	/**
	 * 自下而上  医院年度业务预算 汇总
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectBudgWorkHosYearDown(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 增量生成时 查询生成数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据主键 查询数据是否已存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 科室意见汇总
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String sumDeptSuggest(Map<String, Object> mapVo)  throws DataAccessException;
	
	/**
	 * 预算指标 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgIndex(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 医院年度业务预算  确定预算 计算功能
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectCertenHYBudgData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 保存
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 增长比例获取
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String getGrowRate(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 医院年度业务预算增量预算  更新 增长比例 及相关数据数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateGrowRate(List<Map<String, Object>> listVo) throws DataAccessException; 
	
	
	public List<Map<String,Object>> getPrintData(Map<String,Object> mapVo) throws DataAccessException;

}
