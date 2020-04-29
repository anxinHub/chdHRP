/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.tongJiReports;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.tongJiReports.AssDepreExpire;
/**
 * 
 * @Description:
 * 051204 检查计量计划资产明细
 * @Table:
 * ASS_MEASURE_PLAN_ASS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssDepreExpireMapper extends SqlMapper{
	 
	 
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧到期>) (专用设备查询)  带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/                            
	public List<AssDepreExpire> queryAssDepreExpireSpecial(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧到期>)  (一般设备查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreExpire> queryAssDepreExpireGeneral(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧到期>)  (房屋及建筑查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreExpire> queryAssDepreExpireHouse(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧到期>) (其他固定资产查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreExpire> queryAssDepreExpireOther(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧年限>) (专用设备查询)  带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/                            
	public List<AssDepreExpire> queryAssDepreTermSpecial(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧年限>)  (一般设备查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreExpire> queryAssDepreTermGeneral(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧年限>)  (房屋及建筑查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreExpire> queryAssDepreTermHouse(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧年限>) (其他固定资产查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreExpire> queryAssDepreTermOther(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产逾龄役龄查询>) (专用设备查询)  带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/                            
	public List<AssDepreExpire> queryAssAgeAnalyzeSpecial(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产逾龄役龄查询>)  (一般设备查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreExpire> queryAssAgeAnalyzeGeneral(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产逾龄役龄查询>)  (房屋及建筑查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreExpire> queryAssAgeAnalyzeHouse(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产逾龄役龄查询>) (其他固定资产查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreExpire> queryAssAgeAnalyzeOther(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧到期>) (整合查询)  不带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/   
	public List<AssDepreExpire> queryAssDepreExpire(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧到期>) (整合查询)  带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/   
	public List<AssDepreExpire> queryAssDepreExpire(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧年限>) (整合查询)不带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreExpire> queryAssDepreTerm(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产折旧年限>) (整合查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreExpire> queryAssDepreTerm(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public List<Map<String, Object>> queryAssDepreExpirePrint(
			Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAssDepreTermPrint(
			Map<String, Object> entityMap);

	

	
}
