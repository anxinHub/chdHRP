package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiTemplateData;


public interface AphiTemplateDataMapper  extends SqlMapper{
	
	/**
	 * @Description 
	 * 添加主表数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAphiTemplateData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询 所有主表数据 <BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<AphiTemplateData> queryAphiTemplateData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改 主表数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAphiTemplateData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加 明细表数据<BR> 
	 * @param  List
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAphiTemplateDetailData(List<?> entityMap)throws DataAccessException;
  
	
	/**
	 * @Description 
	 * 批量删除 明细表数据<BR> 
	 * @param  List
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAphiTemplateDetailData(List<Map<String,Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 按编码查询 主表数据<BR> 
	 * @param  List
	 * @return int
	 * @throws DataAccessException
	*/
	public AphiTemplateData queryAphiTemplateDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 按编码查询 明细数据<BR> 
	 * @param  List
	 * @return int
	 * @throws DataAccessException
	*/
	public List<AphiTemplateData> queryAphiTemplateDetailDataByTemplateCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除 主表数据<BR> 
	 * @param  List
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAphiTemplateData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除 明细数据<BR> 
	 * @param  List
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAphiTemplateDetailDataByTemplateCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除 明细数据<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAphiTemplateDataForParseSql(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义修改<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAphiTemplateDataForParseSql(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义删除<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteAphiTemplateDataForParseSql(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义生成<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int initAphiTemplateDataForParseSql(Map<String,Object> entityMap)throws DataAccessException;
}
