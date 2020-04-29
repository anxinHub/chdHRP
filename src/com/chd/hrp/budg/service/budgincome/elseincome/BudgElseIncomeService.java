/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.elseincome;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 其他收入预算
 * @Table:
 * BUDG_ELSE_INCOME
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgElseIncomeService extends SqlService {
	/**
	 * 判断收入预算科目是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int querySubjCodeExist(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 收入预算科目下拉框（添加时用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgIncomeSubj(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 保存数据（包含 添加、修改 ）
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String saveBudgElseIncome(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 根据填写数据 查询上年收入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String setLastIncome(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 生成 （根据上年执行数据生成）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String addBudgElseIncome(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 导入功能 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String importExcel(Map<String, Object> mapVo) throws DataAccessException;

}
