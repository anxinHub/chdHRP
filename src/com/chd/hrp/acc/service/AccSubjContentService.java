package com.chd.hrp.acc.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.hrp.acc.entity.AccSubjContent;

/**
* @Title. @Description.
* 财政补助内容<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
@Service("accSubjContentService")
public interface AccSubjContentService {
	
	/**
	 * @Description 
	 * 财政补助内容表<BR> 查询AccSubjContent
	 * @param AccSubjContent entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccSubjContent(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccSubjContentPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 财政补助内容表<BR> 查询AccSubjContent
	 * @param AccSubjContent entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccSubjContent queryAccSubjContentByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 财政补助内容表<BR> 添加AccSubjContent
	 * @param AccSubjContent entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccSubjContent(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 财政补助内容表<BR> 批量添加AccSubjContent
	 * @param AccSubjContent entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccSubjContent(List<Map<String, Object>> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 财政补助内容表<BR> 删除AccSubjContent
	 * @param AccSubjContent entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccSubjContent(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 财政补助内容表<BR> 批量删除AccSubjContent
	 * @param AccSubjContent entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccSubjContent(List<Map<String, Object>> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 财政补助内容表<BR> 修改AccSubjContent
	 * @param AccSubjContent entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccSubjContent(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 财政补助内容表<BR> 批量修改AccSubjContent
	 * @param AccSubjContent entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccSubjContent(List<Map<String, Object>> entityMap) throws DataAccessException;
	
}
