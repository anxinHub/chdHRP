package com.chd.hrp.hr.dao.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.dao.DataAccessException;

import com.chd.base.Parameter;
import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sysstruc.HrColStruc;
import com.chd.hrp.hr.serviceImpl.base.HrCommonServiceImpl;

public interface BaseCRUDMapper extends SqlMapper {

	/**
	 * 保存
	 * 
	 * @param entityMap
	 */
	public void saveInfo(@Param("tableMap") Map<String, Map<String, Object>> tableMap) throws DataAccessException;

	/**
	 * 更新
	 * 
	 * @param entityMap
	 */
	public void updateInfo(@Param("tableMap") Map<String, Map<String, Object>> tableMap) throws DataAccessException;

	/**
	 * 删除
	 * 
	 * @param entityMap
	 */
	public void deleteInfo(@Param("tableMap") Map<String, Map<String, Object>> tableMap) throws DataAccessException;

	/**
	 * 查询
	 * 
	 * @param <T>
	 * @param entityMap
	 */
	public List<Map<String, Object>> queryInfo(@Param("tableMap") Map<String, Map<String, Object>> tableMap)
			throws DataAccessException;

	/**
	 * 自定义SQL查询
	 * 
	 * @param <T>
	 * @param entityMap
	 */
	public List<Map<String, Object>> queryListByCustomSql(@Param("tableMap") Map<String, String> tableMap)
			throws DataAccessException;

	/**
	 * 自定义SQL查询count
	 * 
	 * @param <T>
	 * @param entityMap
	 */
	public int queryCountByCustomSql(String tab_code) throws DataAccessException;

	/**
	 * 自定义SQL查询
	 * 
	 * @param <T>
	 * @param sql
	 */
	public List<Map<String, Object>> queryCustomSql(String sql) throws DataAccessException;
	
	public List<Map<String, Object>> queryDictCustomSql(String sql) throws DataAccessException;
	
	/**
	 * 
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	@SelectProvider(type = HrCommonServiceImpl.class, method = "selectGridData")
	public List<Map<String, Object>> queryGridData(String sql) throws DataAccessException;
	
	/**
	 * 
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	@SelectProvider(type = HrCommonServiceImpl.class, method = "selectStatisticCustom")
	public List<Map<String, Object>> queryStatisticCustom(String sql) throws DataAccessException;
	
	/**
	 * 查询序列
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public int querySeqByTabCode(String tab_code) throws DataAccessException;


	public List<HashMap<String, Object>> queryChildData(Map<String, Object> paramMap);

	public List<Map<String,Object>> queryAllTable(Parameter param);

	public int saveHosEmpDict(@Param(value="USER_CODE")String USER_CODE,@Param(value="map")Map<String, Object> tableMap);

	public int updateHosEmpDict(@Param(value="USER_CODE")String USER_CODE, @Param(value="map") Map<String, Object> tableMap);

	public int  batchEmpUpate(@Param(value="list") List<String> listBuffers);

	public int queryCount(Map<String, Object> map);

	


}
