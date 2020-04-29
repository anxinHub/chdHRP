/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.dao.storage.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 库存明细查询
 * @Table:
 * 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatWorkDetailMapper extends SqlMapper{


	/**
	 * @Description 
	 * 库存明细查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatStorageQueryWorkDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 库存明细查询
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatStorageQueryWorkDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryMatInOutDetail(Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryMatInOutDetail(Map<String, Object> entityMap);
    /**
     * 仓库材料结余
     * @param entityMap
     * @return
     */
	public List<Map<String, Object>> queryMatStoreInvBalanceDetail(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryMatStoreInvBalanceDetail(Map<String, Object> entityMap, RowBounds rowBounds);
    /**
     * 仓库材料结余-材料出入库明细
     * @param entityMap
     * @return
     */
	public List<Map<String, Object>> queryMatStoreInvInOutDetail(Map<String, Object> entityMap);
	/**
	 * 更具store_id 查询store的基础信息
	 * @param entityMap
	 * @return
	 */
	public Map<String, Object> queryStoreMsg(Map<String, Object> entityMap);
	/**
	 * 根据inv_id 查询inv的基础信息
	 * @param entityMap
	 * @return
	 */
	public Map<String, Object> queryInvMsg(Map<String, Object> entityMap);
	
	
	


}
