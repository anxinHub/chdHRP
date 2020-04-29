/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.base.budginv;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 物资分类与预算支出科目对应关系
 * @Table:
 * BUDG_MAT_TYPE_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMatTypeCostShipService extends SqlService {
	
	/**
	 * 获取物资分类
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatTypes(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 获取物资分类
	 * 查询物资分类 若某物资分类维护了对应关系，则以其编码为前缀的其他物资分类均不可被选中，其各层上级也不可以被选中
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatTypesFilter(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 获取预算科目下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgSubj(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 继承上一年度物资分类与预算科目对应关系
	 * @param mapVo
	 * @return
	 */
	public String extendBudgMatTypeCostShip(Map<String, Object> mapVo) throws DataAccessException ;
	
	/**
	 * 查询 支出预算科目是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryCostSubjByCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryBudgTypeDictByCode(Map<String, Object> mapVo) throws DataAccessException ;

	public String importExcel(Map<String, Object> mapVo)throws DataAccessException ;

}
