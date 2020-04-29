package com.chd.hrp.mat.dao.dura.check;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatDuraDeptMainMapper extends SqlMapper{
	/** 
	 * 查询序列
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatDuraDeptMainSeq() throws DataAccessException;
	/**
	 * 查看单据状态
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int existsMainStateBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 批量修改单据的
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void auditOrUnAuditMainBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 主表数据加载
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String,Object> queryMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 修改盘盈 盘亏单的状态
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void confirmMainBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	

}
