package com.chd.hrp.med.dao.affi.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 代销-出库明细查询
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedAffiOutDetailMapper extends SqlMapper {
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 
	 * 出库明细 查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedAffiOutDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 
	 * 出库明细 分页查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedAffiOutDetail(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 
	 * 出库明细-供应商信息 查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedAffiOutSupMessage(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 
	 * 出库明细-供应商信息 分页查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedAffiOutSupMessage(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 植入介入材料查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedAffiOutImplant(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedAffiOutImplant(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
}
