/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.info.basic;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.HosPackage;
/**
 * 
 * @Description:
 * 04117 包装单位表
 * @Table:
 * HOS_PACKAGE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface HosPackageMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加04117 包装单位表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addHosPackage(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04117 包装单位表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchHosPackage(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04117 包装单位表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateHosPackage(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04117 包装单位表<BR> 
	 * @param  entityMap
	 * @return HosPackage
	 * @throws DataAccessException
	*/
	public int updateBatchHosPackage(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除04117 包装单位表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteHosPackage(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除04117 包装单位表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchHosPackage(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04117 包装单位表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<HosPackage> queryHosPackage(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04117 包装单位表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<HosPackage> queryHosPackage(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取04117 包装单位表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return HosPackage
	 * @throws DataAccessException
	*/
	public HosPackage queryHosPackageByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04117 包装单位表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return HosPackage
	 * @throws DataAccessException
	*/
	public HosPackage queryHosPackageByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 根据 包装单位名称 查询 包装单位记录 （判断包装单位名称是否已使用）
	 * @param entityMap
	 * @return
	 */
	public List<HosPackage> queryHosPackageByName(Map<String, Object> entityMap);
	
	public List<HosPackage> queryHosPackageByNameCode(Map<String, Object> entityMap);
	
	
	
}
