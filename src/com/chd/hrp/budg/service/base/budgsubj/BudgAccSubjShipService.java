
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.service.base.budgsubj;
import java.util.*;

import org.springframework.dao.DataAccessException;

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
 


public interface BudgAccSubjShipService {

	/**
	 * @Description 
	 * 添加预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBudgAccSubjShip(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchBudgAccSubjShip(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBudgAccSubjShip(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchBudgAccSubjShip(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBudgAccSubjShip(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchBudgAccSubjShip(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryBudgAccSubjShip(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public BudgAccSubjShip queryBudgAccSubjShipByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 由会计科目对照生成预算科目
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String setBudgAccSubjShip(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 添加页面  会计科目 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccSubj(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 添加页面  收入预算科目 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgIncomeTypeSet(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 添加页面  支出预算科目 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgCostTypeSet(Map<String, Object> mapVo) throws DataAccessException;
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
	 * 保存数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 增量生成
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String generate(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 继承
	 * @param mapVo
	 * @return
	 */
	public String extend(Map<String, Object> mapVo) throws DataAccessException;
}
