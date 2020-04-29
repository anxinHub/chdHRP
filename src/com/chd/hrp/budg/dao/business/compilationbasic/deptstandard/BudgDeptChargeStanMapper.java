/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.compilationbasic.deptstandard;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室费用标准维护
 * @Table:
 * BUDG_DEPT_CHARGE_STAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgDeptChargeStanMapper extends SqlMapper{
	
	/**
	 * 查询 科室费用标准维护数据 是否已存在
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDeptChargeStanExist(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 校验 费用标准 是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryChargeStanCodeExist(Map<String, Object> mapVo) throws DataAccessException ;
	
	/**
	 * 生成时 查询 生成数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDeptChargeStanData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据 费用标准编码 科室编码 查询  预算科室ID
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryDeptID(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 批量 查询 添加数据是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExistList(List<Map<String, Object>> addList) throws DataAccessException;
}
