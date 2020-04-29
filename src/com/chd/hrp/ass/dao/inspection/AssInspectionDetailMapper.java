package com.chd.hrp.ass.dao.inspection;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.inspection.AssInspectionDetail;
import com.chd.hrp.ass.entity.measure.AssMeasureRecDetail;

public interface AssInspectionDetailMapper extends SqlMapper {

	public int addAssInspectionDetail(Map<String, Object> entityMap) throws DataAccessException;

	public AssInspectionDetail queryAssInspectionDetailByCode( Map<String, Object> entityMap) throws DataAccessException;

	public int updateAssInspectionDetail(Map<String, Object> entityMap)throws DataAccessException;

	public int deleteBatchAssInspectionDetail(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryAssInspectionDetailByUpdate( Map<String, Object> entityMap)throws DataAccessException;

	public AssInspectionDetail queryAssInspectionDetailByUniqueness( Map<String, Object> entityMap)throws DataAccessException;

	public List<AssInspectionDetail> queryAssInspectionDetailExists( Map<String, Object> entityMap) throws DataAccessException;

	public List<AssInspectionDetail> queryAssInspectionDetail( Map<String, Object> entityMap)throws DataAccessException;

	public List<AssInspectionDetail> queryAssInspectionDetail( Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<AssInspectionDetail> queryByAssInspectionDetailId(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 资产巡检明细<BR>带分页  (专用设备查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssInspectionDetail> queryAssInspectionDetailSpecial(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 资产巡检明细<BR>带分页  (一般设备查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssInspectionDetail> queryAssInspectionDetailGeneral(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202 资产巡检明细<BR>带分页  (房屋及建筑查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssInspectionDetail> queryAssInspectionDetailHouse(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202 资产巡检明细<BR>带分页  (其他固定资产查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssInspectionDetail> queryAssInspectionDetailOther(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	

}
