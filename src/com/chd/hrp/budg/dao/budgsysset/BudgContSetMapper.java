package com.chd.hrp.budg.dao.budgsysset;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;
import com.chd.hrp.sys.entity.ModStart;

public interface BudgContSetMapper extends SqlMapper {
	/**
	 * 保存预算节点设置
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int saveBudgContSet(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 根据主键查询 单条预算节点设置 是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int existsBudgContSetByCode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询 反向节点 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String,Object> queryReNodeByCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询 预算节点设置方案 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgContSet(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询 预算节点设置方案 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryBudgContSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 修改 预算节点设置方案 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgContSet(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 删除 预算节点设置方案 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public void deleteBudgContSet(Map<String, Object> entityMap)  throws DataAccessException;
	
	/**
	 * 
	 * @param mapVo
	 * @return
	 */
	public List<Map<String,Object>> queryBudgContNote(Map<String, Object> mapVo) throws DataAccessException;	
	
}
