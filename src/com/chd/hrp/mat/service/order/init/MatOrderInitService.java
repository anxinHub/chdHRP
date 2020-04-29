package com.chd.hrp.mat.service.order.init;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface MatOrderInitService extends SqlService{   
	/**
	 * 订单页面--代销使用查询--查询代销出库数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAffiOut(Map<String, Object> entityMap) throws DataAccessException;   
	
	/**
	 * 订单页面--代销使用查询--查询代销出库主表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAffiOutMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 订单页面--代销使用查询--查询代销出库明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAffiOutDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 修改页面 --根据主键查询明细信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 订单编制 --合并订单
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String mergeMatOrderInit(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 中止订单
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String abortMatOrderInit(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 采购计划生成--查询已审核采购订单主表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatOrderGenPur(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 采购计划生成--根据主键查询主表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatPurById(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 采购计划生成--根据主键查询主明细信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatPurDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 采购计划--生成订单
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String genByPurMatOrderInit(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 根据代销使用生成订单
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String genByAffiMatOrderInit(List<Map<String, Object>> entityList) throws DataAccessException;

	public String queryMatOrderInitByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public Map<String,Object> queryMatOrderInitByPrintTemlatePage(Map<String, Object> map)throws DataAccessException;
    
	/**
	 * 采购计划导入主查询
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	public String queryMatPurMainForOrder(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 采购计划导入明细查询
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	public String queryMatPurDetailForOrder(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 关闭采购明细
	 * @param entityList
	 * @return  String
	 * @throws DataAccessException
	 */
	public String updateMatPurDetailByOrderClose(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 根据采购计划生成订单
	 * @param entityList
	 * @return  String
	 * @throws DataAccessException
	 */
	public String addMatOrderByPurImp(Map<String, Object> entityList) throws DataAccessException;
	/**
	 * 查询采购明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatPurDetailGenOrder(Map<String, Object> entityMap) throws DataAccessException;
	
	public String addMatOrderByPurDetail(Map<String, Object> mapVo) throws DataAccessException;

	public void updateMatPurState(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询订单明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryOrderDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 生成订单
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String addMatOrderInitByPur(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 订单编制 查询- 显示明细 
	 * @param page
	 * @return
	 */
	public String queryShowDetail(Map<String, Object> page) throws DataAccessException;

	public String stateMatOrderInit(Map<String, Object> mapVo) throws DataAccessException;


}
