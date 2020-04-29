package com.chd.hrp.hr.service.sysstruc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.sysstruc.HrTabType;

/**
 * 
 * @ClassName: HrTabTypeService 
 * @Description: 数据表分类
 * @author zn 
 * @date 2017年10月17日 下午4:14:31 
 * 
 *
 */
public interface HrTabTypeService{
	/**
	 * 添加
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String addHrTabType(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 更新
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String updateHrTabType(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteHrTabType(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteBatchHrTabType(List<HrTabType> entityList) throws DataAccessException;
	
	/**
	 * 查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrTabType(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<List<String>> queryHrTabTypeList(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据code查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrTabType queryHrTabTypeByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 批量添加
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	String addBatchHrTabType(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 导入excel
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	String importExcel(Map<String, Object> entityMap) throws DataAccessException;

}
