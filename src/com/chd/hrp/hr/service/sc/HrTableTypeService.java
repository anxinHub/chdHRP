package com.chd.hrp.hr.service.sc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.sc.HrTableType;

/**
 * 
 * @ClassName: HrTableTypeService 
 * @Description: 数据表分类
 * @author zn 
 * @date 2017年10月17日 下午4:14:31 
 * 
 *
 */
public interface HrTableTypeService{
	/**
	 * 添加
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String addHrTableType(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 更新
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String updateHrTableType(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteHrTableType(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteBatchHrTableType(List<HrTableType> entityList) throws DataAccessException;
	
	/**
	 * 查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrTableType(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<List<String>> queryHrTableTypeList(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据code查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrTableType queryHrTableTypeByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 批量添加
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	String addBatchHrTableType(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 导入excel
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	String importExcel(Map<String, Object> entityMap) throws DataAccessException;

}
