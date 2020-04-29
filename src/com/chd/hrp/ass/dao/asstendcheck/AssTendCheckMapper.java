
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.dao.asstendcheck;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatInvCertSup;
/**
 * 
 * @Description: 定标审核
 * 
 */
 

public interface AssTendCheckMapper extends SqlMapper{
	
	/**
	 * 查询 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssTendCheck(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssTendCheck(Map<String, Object> entityMap,	RowBounds rowBounds) throws DataAccessException;
	/**
	 * 提交/取消提交/审核/消审 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateAssTendCheckState(List<Map<String, Object>> entityList)throws DataAccessException;
	/**
	 * 更新
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateAssTendCheck(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量更新
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBatchAssTendCheck(List<Map<String, Object>> entityList)throws DataAccessException;
	/**
	 * 删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteAssTendCheck(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量删除
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteBatchAssTendCheck(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 保存/删除时 修改定标信息
	 * @param listVo
	 * @throws DataAccessException
	 */
	public void updateAssTendCheckInfo(List<Map<String, Object>> listVo) throws DataAccessException;
	

	
}
