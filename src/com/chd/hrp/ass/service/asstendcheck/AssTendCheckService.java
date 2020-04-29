
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.service.asstendcheck;
import java.text.ParseException;
import java.util.*;

import org.springframework.dao.DataAccessException;

/**
 * @Description: 定标审核
 */
 

public interface AssTendCheckService {
	
	/**
	 * 查询 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssTendCheck(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 更新
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 * @throws ParseException
	 */
	public String updateAssTendCheck(Map<String,Object> entityMap)throws DataAccessException, ParseException;
	
	/**
	 * 批量更新
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateBatchAssTendCheck(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteAssTendCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 批量删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteBatchAssTendCheck(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	/**
	 * 提交/取消提交/审核/消审 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateAssTendCheckState(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 保存/删除 是 修改定标信息
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateAssTendCheckInfo(List<Map<String, Object>> listVo) throws DataAccessException;

	
}
