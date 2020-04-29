/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.storage.special;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedSpecialAffiOutRela;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MED_OUT_RESOURCE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedSpecialAffiOutRelaMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 查询专购品与代销对应关系
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	*/
	public MedSpecialAffiOutRela queryMedSpecialAffiOutRela(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询专购品与代销对应关系
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	*/
	public List<MedSpecialAffiOutRela> queryMedSpecialAffiOutRelaList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询专购品与代销对应关系
	 * @param  entityList
	 * @return Date
	 * @throws DataAccessException
	*/
	public List<MedSpecialAffiOutRela> queryMedSpecialAffiOutRelaList(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 插入专购品与代销对应关系
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedSpecialAffiOutRelaBatch(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 删除专购品与代销对应关系
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedSpecialAffiOutRela(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 批量删除专购品与代销对应关系
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedSpecialAffiOutRelaBatch(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 批量判断单据是否可删除、修改
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMedSpecialAffiOutRelaStateBatch(List<Map<String,Object>> entityList) throws DataAccessException;
}
