/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.med.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedStoreMod;
import com.chd.hrp.sys.entity.Mod;

/**
 * @Title. @Description.
 *  
 * @Version: 1.0
 */

public interface MedStoreModMapper extends SqlMapper {

	/**
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addStoreModStart(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addBatchStoreModStart(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @param entityMap
	 *            RowBounds
	 * @return List<Mod>
	 * @throws DataAccessException
	 */
	public List<MedStoreMod> queryStoreMod(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<MedStoreMod> queryStoreModList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @param entityMap
	 * @return List<Mod>
	 * @throws DataAccessException
	 */
	public List<MedStoreMod> queryStoreMod(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public MedStoreMod queryStoreModByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteStoreMod(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteBatchStoreMod(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateStoreMod(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateBatchStoreMod(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	//用于判断期初入库的入库日期不能大于仓库启用日期
	public  Map<String, Object>  existsStoreMod(Map<String, Object> entityMap) throws DataAccessException;
	 
		
}
