package com.chd.hrp.acc.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.hrp.acc.entity.AccFinaContent;

/**
* @Title. @Description.
* 财政补助内容<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
@Service("accFinaContentService")
public interface AccFinaContentService {
	
	/**
	 * @Description 
	 * 财政补助内容表<BR> 查询AccFinaContent
	 * @param AccFinaContent entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccFinaContent(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccFinaContentPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 财政补助内容表<BR> 查询AccFinaContent
	 * @param AccFinaContent entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccFinaContent queryAccFinaContentByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 财政补助内容表<BR> 添加AccFinaContent
	 * @param AccFinaContent entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccFinaContent(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 财政补助内容表<BR> 批量添加AccFinaContent
	 * @param AccFinaContent entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccFinaContent(List<Map<String, Object>> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 财政补助内容表<BR> 删除AccFinaContent
	 * @param AccFinaContent entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccFinaContent(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 财政补助内容表<BR> 批量删除AccFinaContent
	 * @param AccFinaContent entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccFinaContent(List<Map<String, Object>> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 财政补助内容表<BR> 修改AccFinaContent
	 * @param AccFinaContent entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccFinaContent(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 财政补助内容表<BR> 批量修改AccFinaContent
	 * @param AccFinaContent entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccFinaContent(List<Map<String, Object>> entityMap) throws DataAccessException;
	
}
