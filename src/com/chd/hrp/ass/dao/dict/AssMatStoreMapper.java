/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.dict;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatStore;
/**
 * 
 * @Description:
 * 04107 库房附属表
 * @Table:
 * MAT_STORE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssMatStoreMapper extends SqlMapper{
	/**
	 * 查看变更记录
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<?> queryMatStoreDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查看是否存在附属信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIsExistsByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 hos_store 表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryHosStoreByCode(Map<String, Object> entityMap) throws DataAccessException;

	
}
