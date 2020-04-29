/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.base.budgcostfasset;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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
 

public interface BudgAssetTypyCostShipMapper extends SqlMapper{

	/**
	 * 编码规则 资产分类过滤用
	 * @param mapVo
	 * @return
	 */
	public Map<String,Object> getHosRules(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 资产类别下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String,Object>> queryAssTypes(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 资产类别下拉框 添加页面用
	 * 若某资产分类维护了对应关系，则以其编码为前缀的其他资产分类均不可被选中，其各层上级也不可以被选中
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String,Object>> queryAssTypesFilter(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 支出预算科目下拉框 添加页面用
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String,Object>> queryBudgSubj(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询支出预算科目编码是否存在 修改用
	 * @param mapVo
	 * @return
	 */
	public int queryBudgSubjExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 资金性质下拉框  添加页面用
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> querySourceNature(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询资金性质编码是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNatureExist(Map<String, Object> mapVo) throws DataAccessException ;
	
	/**
	 * 获取上一年度数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryPrevYearData(Map<String, Object> mapVo) throws DataAccessException;
	
}
