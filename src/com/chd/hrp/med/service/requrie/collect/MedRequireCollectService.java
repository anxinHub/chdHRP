/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.requrie.collect;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.med.entity.MedRequireMain;
/**
 * 
 * @Description:
 * 科室购置计划汇总
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface MedRequireCollectService extends SqlService{
	
	/**
	 * 科室购置计划汇总--查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCollect(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 科室购置计划汇总--计划单明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCollectDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查看计划单明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCollectDetailPlan(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 保存&提交
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addCollect(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查看明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryConfirmDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 定向汇总查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDirCollect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 保存提交汇总
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addCollectNotDir(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 科室购置计划汇总--根据系统参数查询未审核科室需求
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCollectNotCheck(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 定向汇总查询--根据系统参数查询未审核科室需求
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDirCollectNotCheck(Map<String, Object> entityMap) throws DataAccessException;
}
