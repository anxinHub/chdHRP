/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationbasic.hosstandard;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 医院费用标准维护
 * @Table:
 * BUDG_HOS_CHARGE_STAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgHosChargeStanService extends SqlService {
	
	/**
	 * 导入时校验 费用标准编码是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryChargeStanCodeExist(Map<String, Object> mapVo) throws DataAccessException;

	
	/**
	 * 增量生成时查询生成数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDeptChargeStanData(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 判断医院费用标准维护数据 是否存在
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public int queryBudgHosChargeStanExist(Map<String, Object> item) throws DataAccessException;

	/**
	 * 主页面 换行添加  费用标准下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryBudgChargeStan(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 导入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String budgHosChargeStanImport(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 计算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectBudgHosChargeStan(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 保存
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;

}
