/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.base.budgpayitem;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 支出项目与收入预算科目对应关系
 * @Table:
 * BUDG_PROJ_INCOME_SUBJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgProjIncomeSubjService extends SqlService {
	
	/**
	 * 根据支出项目编码 查询支出项目ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String,Object> queryItemCodeExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 项目信息查询 （添加、修改页面用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgProjDict(Map<String, Object> mapVo) throws DataAccessException;
		
	/**
	 * 根据 收入预算科目编码 查询该收入预算科目 是否为末级科目、是否存在 （导入校验用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIncomeSubjByCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 继承
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String copyData(Map<String, Object> mapVo) throws DataAccessException;

}
