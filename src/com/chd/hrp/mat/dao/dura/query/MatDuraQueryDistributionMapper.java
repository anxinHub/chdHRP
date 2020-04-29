package com.chd.hrp.mat.dao.dura.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccSubjType;
import com.chd.hrp.mat.entity.MatTypeDict;

/**
 * @Description: 耐用品数量分布查询
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatDuraQueryDistributionMapper extends SqlMapper {
	
	/**
	 * @Description 
	 * 查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatDuraQueryDistribution(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分页查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatDuraQueryDistribution(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 耐用品分类查询
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryInvMatType(Map<String, Object> entityMap);
	/**
	 * 耐用品查询
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryMatInvByMenu(Map<String, Object> entityMap);
	
}
