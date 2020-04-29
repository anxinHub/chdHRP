/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.downtotop.hosyearbudg;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 医院年度医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface MedInHYBudgMapper extends SqlMapper{
	
	/**
	 * 添加页面 科室下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgDept(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询预算科室是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDeptExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 计算方法下:
	 * 查询医院年度医疗预算中同级科室信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCountData(Map<String, Object> mapVo);
	
	/**
	 * 计算方法下:
	 *	查询 所选年度、科目 的 所有科室上年收入 总和 
	 * 
	 */
	public Map<String, Object> queryLastYearIncome(
			Map<String, Object> lastMap);
	/**
	 * 计算方法下:
	 *	查询 所选年度、科目 的 所有科室 总数 
	 * 
	 */
	public Integer querySumDept(Map<String, Object> mapVo);
	/**
	 * 查询数据是否存在
	 * @param item
	 * @return
	 */
	public Map<String, Object> queryDataExits(Map<String, Object> item);
	
}
