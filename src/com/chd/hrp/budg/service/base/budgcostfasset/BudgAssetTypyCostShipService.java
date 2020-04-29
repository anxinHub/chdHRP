/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.base.budgcostfasset;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 资金性质取自系统平台
 * @Table:
 * BUDG_ASSET_TYPY_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgAssetTypyCostShipService extends SqlService {
	
	/**
	 * 资产类别下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public  String queryAssTypes(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 资产类别下拉框  添加页面用
	 * 若某资产分类维护了对应关系，则以其编码为前缀的其他资产分类均不可被选中，其各层上级也不可以被选中
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public  String queryAssTypesFilter(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 支出预算科目下拉框  添加页面用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgSubj(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 继承上一年度资产分类与预算科目对应关系
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String extendBudgCostFassetShip(Map<String, Object> mapVo);
	
	/**
	 * 查询 资金性质编码是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNatureExist(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 资金性质下拉框  添加页面用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String querySourceNature(Map<String, Object> mapVo) throws DataAccessException;

}
