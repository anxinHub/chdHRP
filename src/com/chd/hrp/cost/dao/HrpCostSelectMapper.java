package com.chd.hrp.cost.dao;


  
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.HrpAccSelect;
import com.chd.hrp.cost.entity.HrpCostSelect;
 
public interface HrpCostSelectMapper extends SqlMapper{
	//收入类型
	public List<HrpAccSelect> queryIncomeType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
			
	//收入项目
	public  List<HrpAccSelect> queryIncomeItemArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//收费类别
	public  List<HrpAccSelect> queryChargeKindArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//收费项目
	public  List<HrpAccSelect> queryChargeItemArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//材料分类字典 
	public  List<HrpAccSelect> queryMateTypeArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//职工职称字典
	public  List<HrpAccSelect> queryEmpTitleArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//固定资产分类字典 
	public  List<HrpAccSelect> queryFassetTypeArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//无形资产分类字典
	public  List<HrpAccSelect> queryIassetTypeArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//职工分类字典
    public  List<HrpAccSelect> queryEmpTypeArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
  //职工变更字典
    public  List<HrpAccSelect> queryCostEmpDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    //职工字典
    public  List<HrpAccSelect> queryEmpArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    //工资项字典
    public  List<HrpAccSelect> queryWageItemArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//成本习性
	public  List<HrpAccSelect> queryDeptNature(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//成本项目来源
	public  List<HrpAccSelect> queryDataSource(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//成本项目
	public  List<HrpAccSelect> queryItemDict(Map<String,Object> entityMap) throws DataAccessException;
	//成本项目
	public  List<HrpAccSelect> queryItemDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//成本项目
	public  List<HrpAccSelect> queryItemDictLast(Map<String,Object> entityMap) throws DataAccessException;
	//成本项目
	public  List<HrpAccSelect> queryItemDictLast(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//成本分类变更
	public  List<HrpAccSelect> queryDeptTypeDictNo(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//成本项目级次
    public  List<HrpAccSelect> queryItemDictNoItemGrade(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    public  List<HrpAccSelect> queryItemDictNoItemGrade(Map<String,Object> entityMap) throws DataAccessException;
    
	//成本项目变更表
	public  List<HrpAccSelect> queryItemDictNo(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//成本项目变更表
	public  List<HrpAccSelect> queryItemDictNo(Map<String,Object> entityMap) throws DataAccessException;
	//成本项目变更表
	public  List<HrpAccSelect> queryItemDictNoLast(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//成本项目变更表
	public  List<HrpAccSelect> queryItemDictNoLast(Map<String,Object> entityMap) throws DataAccessException;
	
	//成本项目变更表
	public  List<HrpAccSelect> queryItemDictCodeLast(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		
	//成本项目变更表
	public  List<HrpAccSelect> queryItemDictCodeLast(Map<String,Object> entityMap) throws DataAccessException;
		
	//奖金项属性
    public  List<HrpAccSelect> queryBonusItemArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    //职工工资方案
    public  List<HrpAccSelect> queryWangSchemeSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    //职工奖金方案
    public  List<HrpAccSelect> queryBonusSchemeSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    
    public  List<HrpCostSelect> queryDeptLevel(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    /**
     * 科室变更字典
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public  List<HrpAccSelect> queryDeptDictNo(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    /**
     * 科室变更字典code
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public  List<HrpAccSelect> queryDeptDictCode(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

    /**
     * 科室变更字典查询全部
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public  List<HrpAccSelect> queryDeptDictNo(Map<String,Object> entityMap) throws DataAccessException;
    /**
     * 病人类别字典
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, Object>> queryCostPatientTypeDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    /**
     * 分摊参数
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, Object>> queryDeptParaDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    /**
     * 材料信息字典
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, Object>> queryMateArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    /**
     * 无形资产
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, Object>> queryIassetArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    /**
     * 无形资产信息查分类
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, Object>> queryIassetArrtType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    /**
     * 固定资产
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, Object>> queryFassetArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    
    /**
     * 固定资产字典查分类
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, Object>> queryFassetArrtType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    /**
     * 资金来源
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, Object>> querySourceArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    /**
     * 服务项目
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, Object>> queryServerItemDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    /**
     * 科室类型
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, Object>> queryCostDeptKindDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    /**
     * 科室性质
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, Object>> queryCostDeptNature(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

    
    /**
     * 收入项目
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, Object>> queryCostIncomeItemArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;



    
    /**
     * 2016/10/25 lxj 
     * 材料分类 上级编码
     * @param entityMap
     * @param rowBounds
     * @return List
     * @throws DataAccessException
     */
    public List<Map<String,Object>> queryMateTypeSupperCode(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    /**
     * 2016/10/25 lxj 
     * 固定资产分类 上级编码
     * @param entityMap
     * @param rowBounds
     * @return List
     * @throws DataAccessException
     */
    public List<Map<String,Object>> queryFassetTypeSupperCode(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    /**
     * 2016/10/25 lxj 
     * 无形资产分类 上级编码
     * @param entityMap
     * @param rowBounds
     * @return List
     * @throws DataAccessException
     */
    public List<Map<String,Object>> queryIassetTypeSupperCode(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    public List<Map<String,Object>> queryParaType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    public List<Map<String,Object>> queryParaNatur(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    /**
     * 末级科室下拉
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public List<Map<String,Object>> queryDeptDictNoLast(Map<String,Object> entityMap) throws DataAccessException;
    /**
     * 末级科室下拉
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public List<Map<String,Object>> queryDeptDictNoLast(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public  List<Map<String,Object>> queryHosDeptLevel(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
    
	public List<Map<String,Object>> queryCostSubjItemMapByItem(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String,Object>> queryCostSubjItemMapBySubj(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String,Object>> queryCostUserDefinedParaTarget(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	 /**
	   * 科室变更字典code
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
    public  List<HrpAccSelect> queryDeptDictCodeLast(Map<String,Object> entityMap) throws DataAccessException;
    
    /**
	   * 科室变更字典code
   * @param entityMap
   * @param rowBounds
   * @return
   * @throws DataAccessException
   */
  public  List<HrpAccSelect> queryDeptDictCodeLast(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
