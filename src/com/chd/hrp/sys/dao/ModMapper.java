/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.sys.entity.Mod;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface ModMapper extends SqlMapper {

	/**
	 * @Description 添加Mod
	 * @param Mod
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addMod(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量添加Mod
	 * @param Mod
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addBatchMod(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 查询Mod分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<Mod>
	 * @throws DataAccessException
	 */
	public List<Mod> queryMod(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Mod> queryModList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Mod所有数据
	 * @param entityMap
	 * @return List<Mod>
	 * @throws DataAccessException
	 */
	public List<Mod> queryMod(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询ModByCode
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public Mod queryModByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 删除Mod
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteMod(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量删除Mod
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteBatchMod(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 更新Mod
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateMod(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量更新Mod
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateBatchMod(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	// 查询有权限的模块信息
	public List<Mod> queryModByPerm(Map<String, Object> entityMap) throws DataAccessException;
	// 根据当前模块编码查询父级系统是否启用
	public Mod queryParentByModCode(Map<String, Object> entityMap) throws DataAccessException;
	
	//医院权限查询系统模块，只查询具有集团权限的系统模块
	public List<Mod> queryModByGroupPerm(Map<String, Object> entityMap) throws DataAccessException;
		
}
