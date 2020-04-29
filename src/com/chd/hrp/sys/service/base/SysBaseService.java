package com.chd.hrp.sys.service.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccYearMonth;


public interface SysBaseService {
	/**
	 * @Description 获取编码规则<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public Map<Object, Object> getHosRules(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 获取编码规则<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */ 
	public Map<String, Map<String, Object>> getHosRulesList(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 获取会计期间信息<BR> 
	 * @param  entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	*/
	public List<AccYearMonth> queryAccYearMonth(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取会计期间信息<BR> 
	 * @param  entityMap
	 *     key yyyyMM ：年月   value : 对应的会计期间信息
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAccYearMonthMap()throws DataAccessException;
	/**
	 * @Description 
	 * 获取会计期间信息<BR> 
	 * @param  entityMap
	 *     key yyyyMM ：年月   value : 对应的会计期间信息
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAccYearMonthMap(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取各系统启用时间<BR> 
	 * @param  entityMap
	 *     key yyyyMM ：年月   value : 对应的会计期间信息
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryModStartMap(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 判断字典中数据是否被引用<BR>
	 *              引用则不允许删除
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String isExistsDataByTable(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 获取当前会计期间
	 * 
	 * @param flag
	 *            各系统结账标识 列名 大小写均可
	 * @return
	 * @throws DataAccessException
	 */
	public  String getSysYearMonth(String flag) throws DataAccessException;
	/**
	 * 获取上一个会计期间
	 * 
	 * @param flag
	 *            各系统结账标识 列名 大小写均可
	 * @return
	 * @throws DataAccessException
	 */
	public  String getLastSysYearMonth(String flag) throws DataAccessException;
	/**
	 * 判断传递的年月是否已经结账
	 * 
	 * @param yearmonth
	 *            年月 yyyyMM
	 * @param flag
	 *            各系统结账标识 列名 大小写均可
	 * @return
	 * @throws DataAccessException
	 */
	public  boolean getAccYearMonthIsCheckOut(String yearmonth, String flag) throws DataAccessException;
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
	public String getSysPara(String mod_code,String para_code) throws DataAccessException;
	/**
	 * 获取系统参数集合
	 * 
	 * @param para_code
	 *            参数编码
	 * @return 参数值
	 */
	public Map<String, ?>  getSysParaMaps() throws DataAccessException;
	
	public Map<String, Map<String, Object>> queryGroupParaList(Map<String, Object> entityMap) throws DataAccessException;
	 
	public Map<String, Map<String, Object>> queryGroupSFList(Map<String, Object> entityMap) throws DataAccessException;
	

}
