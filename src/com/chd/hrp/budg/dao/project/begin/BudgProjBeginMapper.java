package com.chd.hrp.budg.dao.project.begin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgProjBegain;



public interface BudgProjBeginMapper extends SqlMapper {
	/**
	 * @Description 
	 * 资金来源下拉框  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	public List<Map<String,Object>> queryBudgSourceId(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 资金来源名称下拉框  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	public List<Map<String,Object>> queryBudgSourceName(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 支出项目下拉框  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	public List<Map<String,Object>> queryBudgPaymentItemId(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 支出项目变更号下拉框  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	public List<Map<String,Object>> queryBudgPaymentItemNo(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 期初项目预算记账BUDG_PROJ_BEGAIN_MARK中状态初始为0未记账
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	public Map<String,Object> queryBegainMark(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * @Description 
	 * 期初项目预算记账BUDG_PROJ_BEGAIN中proj_id
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	public List<Map<String,Object>> queryProjId(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 期初预算项目审核,点击审核按钮的时候,state:01变成02
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/	
	public int BudgProjBegainAudit(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * @Description 
	 * 期初预算项目消核,点击销审按钮的时候,state:02变成01
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	public int UnBudgProjBegainAudit(List<Map<String, Object>> listVo) throws DataAccessException;
	
	public int queryDataExists(Map<String, Object> mapVo) throws DataAccessException;
	/*
	添加数据的时候,判断数据已经存在	
	*/
	public BudgProjBegain querybudgProjBegin(List<Map<String, Object>> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 项目名称下拉框  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	public List<Map<String,Object>> queryBudgProjName(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 预算年度下拉框  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	public List<Map<String,Object>> queryBudgYear(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;

	public int querybudgProjBegain(Map<String, Object> mapVo) throws DataAccessException;
	public int querybudgProjBegainDetail(Map<String, Object> mapVo) throws DataAccessException;
	public Map<String, Object> queryB_Usable_Amount(Map<String, Object> mapVo) throws DataAccessException;
	public Map<String, Object> queryMainLast(Map<String, Object> mapVo) throws DataAccessException;
	public Map<String, Object> queryMinYear(Map<String, Object> mapVo) throws DataAccessException;
	public Map<String, Object> queryMainLastU(Map<String, Object> mapVo) throws DataAccessException;
	public List<Map<String, Object>> queryDetail(Map<String, Object> mapVo) throws DataAccessException;
	public Map<String, Object> queryLastMainData(Map<String, Object> hashMap) throws DataAccessException;

	
	public Map<String, Object> queryByProjIdOrSourceId(Map<String, Object> mapVo) throws DataAccessException;
	public List<Map<String, Object>> queryLastMainDetailData(Map<String, Object> hashMap) throws DataAccessException;

	public List<Map<String, Object>> queryBudgSourceNam(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryProjNam(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryPaymentItem(Map<String, Object> entityMap) throws DataAccessException;
	public Map<String, Object> queryKey(Map<String, Object> returnMap) throws DataAccessException;

	
	/**
	 * 根据 年度、 项目、资金来源 查询  该年度之后相同项目、资金来源是否存在数据（若存在则不能录入）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNextDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据选定 项目、资金来源  查询 期初预算余额、期初可用余额 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> qureyInfoData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据选定 项目、资金来源  查询 项目负责人、
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> qureyProjEmp(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 明细表 根据 选定 项目、资金来源、支出项目后  查询 期初预算余额 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> qureyInfoDataDetail(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 根据 年度、 项目、资金来源、支出项目  查询  该年度之后相同项目、资金来源、支出项目是否存在数据（若存在则不能录入）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNextDataDetailExist(Map<String, Object> mapVo)throws DataAccessException;


}
