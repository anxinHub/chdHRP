
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
import com.chd.hrp.ass.entity.dict.AssFacAttr;
import com.chd.hrp.sys.entity.Fac;
/**
 * 
 * @Description:
 * 050115 生产厂商附属表
 * @Table:
 * ASS_FAC_ATTR
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AssMatFacAttrMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatFacAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int init(Map<String,Object> entityMap)throws DataAccessException;
	public List<Fac> queryFac(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 批量添加050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatFacAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatFacAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return AssFacAttr
	 * @throws DataAccessException
	*/
	public int updateBatchMatFacAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatFacAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatFacAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050115 生产厂商附属表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssFacAttr> queryMatFacAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050115 生产厂商附属表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssFacAttr> queryMatFacAttr(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return AssFacAttr
	 * @throws DataAccessException
	*/
	public AssFacAttr queryMatFacAttrByCode(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * 根据 生产厂商编码 查询 生产厂ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryFacIdByCode(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 添加 生产厂商 与系统模块对应关系
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addHosFacMod(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据生产厂商ID 查询该供应商附属表是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryHosFacModExist(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 修改 生产厂商 与系统模块对应关系
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void updateHosFacMod(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除生产厂商  与模块的对应关系
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void deleteBatchHosFacMod(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 查询 生产厂商名称是否存在 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryFacExists(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询 生产厂商分类 编码是否存在 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryFacTypeExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 所属系统模块编码是否存在
	 * @param mapVo
	 * @return
	 */
	public int queryModExist(Map<String, Object> mapVo) throws DataAccessException;


	public List<AssFacAttr> queryMatFacAttrByCodeExists(Map<String, Object> entityMap);
	
	
	
	
}
