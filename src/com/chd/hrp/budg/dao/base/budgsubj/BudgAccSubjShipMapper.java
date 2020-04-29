
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.dao.base.budgsubj;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.budg.entity.BudgAccSubjShip;
/**
 * 
 * @Description:
 * 预算科目与会计科目对应关系表
 * @Table:
 * BUDG_ACC_SUBJ_SHIP
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface BudgAccSubjShipMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBudgAccSubjShip(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchBudgAccSubjShip(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgAccSubjShip(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return BudgAccSubjShip
	 * @throws DataAccessException
	*/
	public int updateBatchBudgAccSubjShip(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBudgAccSubjShip(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchBudgAccSubjShip(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集预算科目与会计科目对应关系表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<BudgAccSubjShip> queryBudgAccSubjShip(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集预算科目与会计科目对应关系表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<BudgAccSubjShip> queryBudgAccSubjShip(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return BudgAccSubjShip
	 * @throws DataAccessException
	*/
	public BudgAccSubjShip queryBudgAccSubjShipByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 修改对应会计科目时 修改预算科目与会计科目对应关系表
	 * @param entityMap
	 * @return
	 */
	public int updateBudgAccSubjShipById(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 添加页面 收入预算科目  下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgIncomeTypeSet(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 添加页面 支出预算科目  下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgCostTypeSet(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 添加页面 会计科目  下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAccSubj(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询数据是否已存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIsExist(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询会计科目编码是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryAccSubjCodeExist(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据传入subj_type  从会计科目中取出所有科目性质为收入或者支出的会计科目  
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryDataFromAccSubj(Map<String, Object> entityMap);
	/**
	 * 批量插入增量生成数据
	 * @param dataList
	 */
	public void addBatchGenerate(List<Map<String, Object>> dataList);
	/**
	 * 查询所选年度数据是否已有数据  继承功能使用
	 * @param entityMap
	 * @return
	 */
	public int queryDataExist(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询上年度数据
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryLastYearData(Map<String, Object> entityMap) throws DataAccessException;
	
}  
