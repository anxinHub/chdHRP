package com.chd.hrp.acc.dao.tell;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccTellVeri;
import com.chd.hrp.acc.entity.AccVouchCheck;

public interface AccTellVeriMapper extends SqlMapper{
	/**
	 * @Description 
	 * 坏账提取表<BR> 添加AccBankVeri
	 * @param AccBankVeri entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccTellVeri(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addAccTellVeriLog(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 批量添加AccBankVeri
	 * @param  AccBankVeri entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccTellVeri(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 查询AccBankVeri分页
	 * @param  entityMap RowBounds
	 * @return List<AccBankVeri>
	 * @throws DataAccessException
	*/
	public List<AccTellVeri> queryAccTellVeri(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<AccVouchCheck> queryAccVouchCheck(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 查询AccBankVeri所有数据
	 * @param  entityMap
	 * @return List<AccBankVeri>
	 * @throws DataAccessException
	*/
	public List<AccTellVeri> queryAccTellVeri(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AccVouchCheck> queryAccVouchCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 查询AccBankVeriByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccTellVeri queryAccTellVeriByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 删除AccBankVeri
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccTellVeri(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 删除deleteAccBankVeriBySubjAndDate
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccTellVeriBySubjAndDate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 批量删除AccBankVeri
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccTellVeri(List<Map<String, Object>> entityMap)throws DataAccessException;


	public List<Map<String, Object>> queryAccTellData(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccVouchCheckData(Map<String, Object> entityMap) throws DataAccessException;


	public void updateAccTellCheckState(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 出纳对账 出纳数据
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryAccTellDatas(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccTellVeriDataBySubjCode(Map<String, Object> entityMap) throws DataAccessException;

	public void updateAccTellCheck(Map<String, Object> entityMap) throws DataAccessException;
}
