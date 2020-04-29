/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationbasic.deptstandard;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface BudgDeptChargeStanService extends SqlService {
	
	/**
	 * 校验 费用标准 是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryChargeStanCodeExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 生成时 查询 生成数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgDeptChargeStanData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 科室费用标准维护数据 是否已存在
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDeptChargeStanExist(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 根据 费用标准编码 科室编码 查询  预算科室ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDeptID(Map<String, Object> mapVo) throws DataAccessException;

	public String budgDeptChargeStanImport(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 计算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectBudgDeptChargeStan(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 保存
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;

}
