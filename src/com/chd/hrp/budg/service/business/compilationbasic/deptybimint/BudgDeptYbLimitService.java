/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationbasic.deptybimint;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 科室医保额度控制
 * @Table:
 * BUDG_DEPT_YB_LIMIT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgDeptYbLimitService extends SqlService {

	/**
	 * 医保类型下拉框 添加页面
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public  String queryBudgYBTY(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDeptDict(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 *查询 医保类型编码是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryInsuranceCodeExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据 科室编码 查询 科室ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDeptID(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据主键 查询科室医保额度控制数据 是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo)  throws DataAccessException;
	
	/**
	 * 增量生成时 查询 预算科室中DEPT_TYPE=02的直接医疗科室
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDeptData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 增量生成
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int addGenerate(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据年度、科室、医保类型信息 查询 全院医保额度、上年医保收入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String qureyLastData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 历史数据比例分解
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateResolveRate(Map<String, Object> mapVo) throws DataAccessException;

	public String budgDeptYbLimitImportNewPage(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 保存
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo)  throws DataAccessException;

	

}
