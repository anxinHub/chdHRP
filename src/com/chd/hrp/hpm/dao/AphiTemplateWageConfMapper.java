package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


/*
 * 工资与奖金项目配置
 * */
public interface AphiTemplateWageConfMapper extends SqlMapper {
	
	
	/**
	 * @Description 
	 * 查询 所有配置明细数据 <BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAphiTemplateWageConfForGrant(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 查询 所有配置明细数据 <BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAphiTemplateWageConfDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 查询 所有配置明细数据  分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAphiTemplateWageConfDetail(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 批量删除  配置明细数据<BR> 
	 * @param  List
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAphiTemplateWageConfDetailAll(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询 工资项目<BR> 
	 * @param  List
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryWageItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询 工资项目<BR> 
	 * @param  List
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryWage(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询 工资项目 分页<BR> 
	 * @param  List
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryWageItem(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加 明细表数据<BR> 
	 * @param  List
	 * @return int
	 * @throws DataAccessException
	 */
	public int addBatchAphiTemplateWageConfDetail(List<Map<String,Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改 主表数据<BR> 
	 * @param  List
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateAphiTemplateWageConf(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加 主表数据<BR> 
	 * @param  List
	 * @return int
	 * @throws DataAccessException
	 */
	public int addAphiTemplateWageConf(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询主表数据<BR> 
	 * @param  List
	 * @return int
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAphiTemplateWageConf(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteAphiTemplateWageConfDetailMain(
			List<Map<String, Object>> listVo) throws DataAccessException;
	public int queryWageDetailList(
			List<Map<String, Object>> listVo) throws DataAccessException;
	public int deleteAphiTemplateWageMain(List<Map<String, Object>> listVo) throws DataAccessException;
	public List<Map<String, Object>> queryWageEmp(
			Map<String, Object> wageMapString) throws DataAccessException;
	
}
