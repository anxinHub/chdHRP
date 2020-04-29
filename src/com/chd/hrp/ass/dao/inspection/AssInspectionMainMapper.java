package com.chd.hrp.ass.dao.inspection;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.ins.AssInsMain;
import com.chd.hrp.ass.entity.inspection.AssInspectionMain;
 

public interface AssInspectionMainMapper extends SqlMapper{

	public int addAssInspectionMain(Map<String, Object> entityMap) throws DataAccessException;
	
	public int updateAssInspectionMain(Map<String,Object> entityMap)throws DataAccessException;

	public AssInspectionMain queryAssInspectionMainByCode(Map<String, Object> entityMap)throws DataAccessException;

	public List<AssInspectionMain> queryAssInspectionMainExists( Map<String, Object> entityMap)throws DataAccessException;

	public int deleteBatchAssInspectionMain(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<AssInspectionMain> queryAssInspectionMain(Map<String, Object> entityMap)throws DataAccessException;

	public List<AssInspectionMain> queryAssInspectionMain( Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;


	/**
	 * @Description 
	 * 批量更新051202 巡检记录<BR> (审核)
	 * @param  entityMap
	 * @return AssMaintainPlan
	 * @throws DataAccessException
	*/
	public int updateBatchAssInspectionMain(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量更新051202 巡检记录<BR> (消审)
	 * @param  entityMap
	 * @return AssMaintainPlan
	 * @throws DataAccessException
	*/
	public int updateBatchAssInspectionMainBack(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量更新051202 巡检记录<BR> (终止)
	 * @param  entityMap
	 * @return AssMaintainPlan
	 * @throws DataAccessException
	*/
	public int updateBatchAssInspectionMainStop(List<Map<String, Object>> entityMap)throws DataAccessException;

	public Long queryCurrentSequence()throws DataAccessException;
    /**
     * 巡检记录全部
     * @param entityMap
     * @return
     */
	public List<AssInspectionMain> queryAssInspectionMainByCard(Map<String, Object> entityMap);
    /**
     * 巡检记录带分页
     * @param entityMap
     * @param rowBounds
     * @return
     */
	public List<AssInspectionMain> queryAssInspectionMainByCard(Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryAssCardLand(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryAssCardOther(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryAssCardInassets(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryAssCardHouse(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryAssCardGeneral(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryAssCardSpecial(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryInspectionMainByDetail(
			Map<String, Object> entityMap);

	public Map<String, Object> queryInspectionMainByMain(
			Map<String, Object> entityMap);

	public List<Map<String, Object>> queryInspectionMainByMainBatch(
			Map<String, Object> entityMap);

	public List<String> queryInSpectionMainStatee(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);

	
}
