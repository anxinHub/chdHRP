package com.chd.hrp.acc.dao.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccItialIncome;

/**
* @Title. @Description.
* 财政基本补助收入支出表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface AccItialIncomeMapper extends SqlMapper {
	
	/**
	 * @Description 
	 * 查询初始收入<BR> 查询AccItialIncome分页
	 * @param  entityMap RowBounds
	 * @return List<AccItialIncome>
	 * @throws DataAccessException
	*/
	public List<AccItialIncome> queryAccItialIncome(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询初始收入<BR> 查询AccItialIncome所有数据
	 * @param  entityMap
	 * @return List<AccItialIncome>
	 * @throws DataAccessException
	*/
	public List<AccItialIncome> queryAccItialIncome(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccItialIncomePrint(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询初始收入<BR> 查询AccItialIncome
	 * @param  entityMap
	 * @return AccItialIncome
	 * @throws DataAccessException
	*/
	public AccItialIncome queryAccItialIncomeByCode(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 添加初始收入<BR> 添加AccItialIncome
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccItialIncome(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加初始收入<BR> 批量添加AccItialIncome
	 * @param  AccItialIncome entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccItialIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除初始收入<BR> 删除AccItialIncome
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccItialIncome(List<Map<String, Object>> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 删除初始收入<BR> 删除AccItialIncomeByCode
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteAccItialIncome(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询导入凭证信息<BR> 查询AccItialIncome所有数据
	 * @param  entityMap
	 * @return List<AccItialIncome>
	 * @throws DataAccessException
	*/
	public List<AccItialIncome> queryAccVouchImport(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询导入凭证信息<BR> 查询AccItialIncome所有数据
	 * @param  entityMap
	 * @return List<AccItialIncome>
	 * @throws DataAccessException
	*/
	public List<AccItialIncome> queryAccVouchImport(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
