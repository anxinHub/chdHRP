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
 * 科室年度业务预算
 * @Table:
 * BUDG_WORK_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkDeptYearService extends SqlService {
	
	/**
	 * 保存数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 查询 所传 科室的 上年业务量
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptYearLastYearWork(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 所传 科室的 上年业务量
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryGetWay(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 计算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectBudgWorkDeptYearUp(Map<String, Object> mapVo)  throws DataAccessException;
	
	/**
	 * 根据 指标编码、 科室编码 查询  预算指标对应科室 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryIndexDeptSet(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 生成 科室年度业务概率预算 运营尺度数据
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryProbBudgRate(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 自下而上 科室年度业务预算查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgWorkDeptYearDown(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 增量生成时  查询要生成的数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据主键  查询数据是否存在
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 预算指标下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgIndex(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据指标 关联查询科室下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgIndexDeptSet(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询数据  预算分解页面
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgWorkDeptYearResolve(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 科室年度业务预算  确定预算 计算（自下而上）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectCertenDYBudgData(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 增长比例获取
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String getGrowRate(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 科室年度业务预算增量预算  更新 增长比例 及相关数据数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateGrowRate(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 下发 撤回(自上而下)
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String issuedOrRetract(List<Map<String, Object>> listVo) throws DataAccessException; 

	/**
	 *确认 (通过,不通过) (自上而下)
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String passOrDisPass(List<Map<String, Object>> listVo) throws DataAccessException; 
	
	/**
	 * 提交  撤回  取消审核(自下而上)
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String reviewOrUnreview(List<Map<String, Object>> listVo) throws DataAccessException; 
	
	/**
	 *审核 (通过,不通过) (自下而上)
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String passOrDisPassDown(List<Map<String, Object>> listVo) throws DataAccessException;

	List<Map<String, Object>> getPrintData(Map<String, Object> mapVo)
			throws DataAccessException; 
	

}
