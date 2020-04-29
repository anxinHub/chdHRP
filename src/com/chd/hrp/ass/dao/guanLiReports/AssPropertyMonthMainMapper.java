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
import com.chd.hrp.ass.entity.guanLiReports.AssPropertyMonthMain;
import com.chd.hrp.ass.entity.tongJiReports.AssDepreExpire;
/**
 * 
 * @Description:
 * 051101 资产月报表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */ 
 

public interface AssPropertyMonthMainMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  财务制度>) (专用设备查询)  带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/                            
	public List<AssPropertyMonthMain> queryAssPropertyMonthMainSpecial(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  财务制度>) (专用设备查询)  
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/                            
	public List<AssPropertyMonthMain> queryAssPropertyMonthMainSpecial(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  财务制度>)  (一般设备查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyMonthMainGeneral(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  财务制度>)  (一般设备查询) 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyMonthMainGeneral(Map<String,Object> entityMap ) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  财务制度>)  (房屋及建筑查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyMonthMainHouse(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  财务制度>)  (房屋及建筑查询) 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyMonthMainHouse(Map<String,Object> entityMap ) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  财务制度>) (其他固定资产查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyMonthMainOther(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  财务制度>) (其他固定资产查询) 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyMonthMainOther(Map<String,Object> entityMap ) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  内部管理>) (专用设备查询)  带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/                            
	public List<AssPropertyMonthMain> queryAssPropertyMonthMainManageSpecial(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  内部管理>) (专用设备查询)   
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/                            
	public List<AssPropertyMonthMain> queryAssPropertyMonthMainManageSpecial(Map<String,Object> entityMap ) throws DataAccessException;
	 
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  内部管理>)  (一般设备查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyMonthMainManageGeneral(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  内部管理>)  (一般设备查询) 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyMonthMainManageGeneral(Map<String,Object> entityMap ) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  内部管理>)  (房屋及建筑查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyMonthMainManageHouse(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  内部管理>)  (房屋及建筑查询) 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyMonthMainManageHouse(Map<String,Object> entityMap ) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  内部管理>) (其他固定资产查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyMonthMainManageOther(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  内部管理>) (其他固定资产查询) 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyMonthMainManageOther(Map<String,Object> entityMap ) throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产汇总   全院     财务制度>) (专用设备查询)  带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/                            
	public List<AssPropertyMonthMain> queryAssPropertyHosMainSpecial(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产汇总   全院     财务制度>) (专用设备查询)  
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/                            
	public List<AssPropertyMonthMain> queryAssPropertyHosMainSpecial(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产汇总  全院    财务制度>)  (一般设备查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyHosMainGeneral(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产汇总  全院    财务制度>)  (一般设备查询) 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyHosMainGeneral(Map<String,Object> entityMap ) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产汇总    全院     财务制度>)  (房屋及建筑查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyHosMainHouse(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产汇总    全院     财务制度>)  (房屋及建筑查询) 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyHosMainHouse(Map<String,Object> entityMap ) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产汇总   全院     财务制度>) (其他固定资产查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyHosMainOther(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产汇总   全院     财务制度>) (其他固定资产查询) 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyHosMainOther(Map<String,Object> entityMap ) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产汇总     全院     内部管理>) (专用设备查询)  带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/                            
	public List<AssPropertyMonthMain> queryAssPropertyHosMainManageSpecial(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产汇总     全院     内部管理>) (专用设备查询)   
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/                            
	public List<AssPropertyMonthMain> queryAssPropertyHosMainManageSpecial(Map<String,Object> entityMap ) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产汇总     全院          内部管理>)  (一般设备查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyHosMainManageGeneral(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产汇总     全院          内部管理>)  (一般设备查询) 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyHosMainManageGeneral(Map<String,Object> entityMap ) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产汇总        全院        内部管理>)  (房屋及建筑查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyHosMainManageHouse(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产汇总        全院        内部管理>)  (房屋及建筑查询) 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyHosMainManageHouse(Map<String,Object> entityMap ) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产汇总     全院        内部管理>) (其他固定资产查询)带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyHosMainManageOther(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产汇总     全院        内部管理>) (其他固定资产查询) 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPropertyMonthMain> queryAssPropertyHosMainManageOther(Map<String,Object> entityMap ) throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  财务制度>) (整合查询)  不带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/                            
	public List<AssPropertyMonthMain> queryAssPropertyMonthMain(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  财务制度>) (整合查询)  带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/   
	public List<AssPropertyMonthMain> queryAssPropertyMonthMain(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  财务制度>) 不带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/      
	public List<AssPropertyMonthMain> queryAssPropertyHosMain(Map<String, Object> entityMap);

	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  财务制度>) 带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/      
	public List<AssPropertyMonthMain> queryAssPropertyHosMain(Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryAssPropertyMonthMainPrint(
			Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAssPropertyHosMainPrint(
			Map<String, Object> entityMap);
	
	public List<Map<String, Object>> queryAssPropertyBusTypeMonthMain(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public List<Map<String, Object>> queryAssPropertyBusTypeMonthMain(Map<String, Object> entityMap)throws DataAccessException;
}
