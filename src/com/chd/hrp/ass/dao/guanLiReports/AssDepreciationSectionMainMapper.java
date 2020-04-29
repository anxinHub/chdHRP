/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.guanLiReports;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.guanLiReports.AssDepreciationSectionMain;
import com.chd.hrp.ass.entity.guanLiReports.AssResourceSet;
/**
 * 
 * @Description:
 * 051101 资产月报表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssDepreciationSectionMainMapper extends SqlMapper{
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧到期>) (专用设备查询) 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/                            
	public List<AssDepreciationSectionMain> querySpecial(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧到期>)  (一般设备查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreciationSectionMain> queryGeneral(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧到期>)  (房屋及建筑查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreciationSectionMain> queryHouse(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧到期>) (其他固定资产查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreciationSectionMain> queryOther(Map<String,Object> entityMap) throws DataAccessException;
	
	
	 
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧到期>) (专用设备查询)  带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/                            
	public List<AssDepreciationSectionMain> querySpecial(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧到期>)  (一般设备查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreciationSectionMain> queryGeneral(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧到期>)  (房屋及建筑查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreciationSectionMain> queryHouse(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧到期>) (其他固定资产查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreciationSectionMain> queryOther(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询结果集
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<AssResourceSet> queryAssDepreciationSectionMain(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryAssDepreciationSectionPrint(
			Map<String, Object> entityMap);
	
	
	
}
