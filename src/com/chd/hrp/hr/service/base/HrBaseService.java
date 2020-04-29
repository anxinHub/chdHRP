/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.service.base;

import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.base.HrAssBillNo;





/**
 * @Description: 050201 关系表
 * @Table: ASS_APPLY_ACCEPT_MAP
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface HrBaseService {

	/**
	 * @Description 获取编码规则<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public Map<Object, Object> getHosRules(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 更新最大单号<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public int updateAssBillNoMaxNo(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 获取对应业务表的单据号生成规则<BR>
	 * @param entityMap
	 * @return AssBillNo
	 * @throws DataAccessException
	 */
	public HrAssBillNo queryAssBillNoByName(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 获取对应业务表的单据号生成规则<BR>
	 * @param entityMap
	 * @return AssBillNo
	 * @throws DataAccessException
	 */
	public String getBillNOSeqNo(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 获取对应业务表的单据号生成规则<BR>
	 * @param entityMap
	 * @return AssBillNo
	 * @throws DataAccessException
	 */
	public String getBillNOSeqNo(String tableName) throws DataAccessException;

	/**
	 * @Description 更新最大单号<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public int updateAssBillNoMaxNo(String tableName) throws DataAccessException;

	/**
	 * @Description 判断字典中数据是否被引用<BR>
	 *              引用则不允许删除
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String isExistsDataByTable(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 判断字典中数据是否被引用<BR>
	 *              引用则不允许删除
	 * @param tableName
	 *            表名
	 * @param id
	 *            表的主键
	 * @return
	 * @throws DataAccessException
	 */
	public String isExistsDataByTable(String tableName, Object id) throws DataAccessException;
	/**
	 * @Description 判断字典中数据是否被引用<BR>
	 *              引用则不允许删除
	 * @param tableName
	 *            表名
	 * @param id
	 *            表的主键
	 * @param p_flag
	 *            表是否带级次 0:不带 1：带  
	 * @return
	 * @throws DataAccessException
	 */
	public String isExistsDataByTable(String tableName, Object id,String p_flag) throws DataAccessException;


	/**
	 * 获取系统参数
	 * 
	 * @param mod_code
	 *            模块编码
	 * @return 各模块的系统参数集合 Map<String, Object>
	 */
	public  Map<String, Object> getSysParaMap(String mod_code) throws DataAccessException;
	
	/**
	 * 获取系统参数
	 * 
	 * @param para_code
	 *            参数编码
	 * @return 参数值
	 */
	public String getAssPara(String para_code) throws DataAccessException;
	/**
	 * 获取当期会计年月
	 * @return yyyy-mm
	 * @throws DataAccessException
	 */
	public String getAssYearMonth() throws DataAccessException ;
	
	
	/**
	 * 获取当期会计年月
	 * @return yyyy-mm
	 * @throws DataAccessException
	 */
	public String getLastAssYearMonth() throws DataAccessException ;
	
	/**
	 * 获取资产当月是否结账
	 * @param year
	 * @param month
	 * @return 
	 * @throws DataAccessException
	 */
	public boolean getAssIsCheckOut(String year,String month) throws DataAccessException ;
	
	/**
	 * @Description 查询科室集合<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public List<?> queryStores(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 查询科室集合<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public List<?> queryDepts(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询单号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String QueryHrBillNo(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 更新单号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateAndQueryHrBillNo(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 新增关联表表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String insertTable(Map<String, Object> entityMap,List<Map<String, Object>> list) throws DataAccessException;
	/**
	 * 更新关联表表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateTable(Map<String, Object> entityMap,List<Map<String, Object>> list) throws DataAccessException;
}
