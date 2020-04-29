package com.chd.hrp.ass.dao.tendnotify;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.tend.AssTendMain;

/**
 * 中标通知书
 * 
 * @author Administrator
 *
 */
public interface AssTendNotifyMapper extends SqlMapper {
	/**
	 * 查询主方法
	 * 
	 * @param mapVo
	 * @param bounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<AssTendMain> queryAssTendNotifyMain(Map<String, Object> mapVo, RowBounds bounds) throws DataAccessException;
	
	/**
	 * 根据ID查询
	 * 
	 * @param mapVo
	 * @param bounds
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssTendFileById(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 添加文件管理表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addAssTendFile(Map<String,Object> mapVo)throws DataAccessException;
	
	/**
	 * 更新招标主表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateAssTendMain(Map<String,Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询招标文件表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<AssTendMain> queryAssTendFile(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询  文件Id
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryAssTendFileSequence() throws DataAccessException;
	
	/**
	 * 刪除招标文件管理表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteTendFile(Map<String, Object> mapVo)throws DataAccessException;
}
