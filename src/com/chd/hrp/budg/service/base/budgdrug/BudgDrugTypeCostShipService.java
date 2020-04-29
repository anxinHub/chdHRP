/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.base.budgdrug;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 药品分类与预算支出科目对应关系
 * @Table:
 * BUDG_DRUG_TYPE_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgDrugTypeCostShipService extends SqlService {
	
	/**
	 * 药品类别 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedTypes(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 药品类别 下拉框(添加页面用)
	 * 若某药品分类维护了对应关系，则以其编码为前缀的其他药品分类均不可被选中，其各层上级也不可以被选中
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedTypesFilter(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 获取预算科目下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgSubj(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 继承上一年度药品分类与预算科目对应关系
	 * @param mapVo
	 * @return
	 */
	public String extendBudgDrugTypeCostShip(Map<String, Object> mapVo) throws DataAccessException ;

}
