package com.chd.hrp.ass.service.inspection;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.accept.AssAcceptDetail;
import com.chd.hrp.ass.entity.dict.AssInspectionItem;
import com.chd.hrp.ass.entity.inspection.AssInspectionMain;

public interface AssInspectionMainService {

	public String addAssInspectionMain(Map<String, Object> entityMap) throws DataAccessException;

	public AssInspectionMain queryAssInspectionMainByCode(Map<String, Object> entityMap) throws DataAccessException;

	public String updateAssInspectionMain(Map<String, Object> entityMap) throws DataAccessException;

	public String addOrUpdateAssInspectionMain(Map<String, Object> entityMap) throws DataAccessException;

	public String deleteBatchAssInspectionMain(List<Map<String, Object>> entityMap) throws DataAccessException;

	public String queryAssInspectionMain(Map<String, Object> entityMap) throws DataAccessException;

	public AssInspectionMain queryAssInspectionMainByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException;

	/**
	 * @Description 批量更新051202 巡检记录<BR>
	 *              (审核)
	 * @param entityMap
	 * @return AssMaintainPlan
	 * @throws DataAccessException
	 */
	public String updateBatchAssInspectionMain(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 批量更新051202 巡检记录<BR>
	 *              (消审)
	 * @param entityMap
	 * @return AssMaintainPlan
	 * @throws DataAccessException
	 */
	public String updateBatchAssInspectionMainBack(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 批量更新051202 巡检记录<BR>
	 *              (终止)
	 * @param entityMap
	 * @return AssMaintainPlan
	 * @throws DataAccessException
	 */
	public String updateBatchAssInspectionMainStop(List<Map<String, Object>> entityMap) throws DataAccessException;

	public String queryAssInspectionMainByCard(Map<String, Object> entityMap) throws DataAccessException;

	public String queryAssCardSpecial(Map<String, Object> mapVo);

	public String queryAssCardGeneral(Map<String, Object> mapVo);

	public String queryAssCardLand(Map<String, Object> mapVo);

	public String queryAssCardOther(Map<String, Object> mapVo);

	public String queryAssCardInassets(Map<String, Object> mapVo);

	public String queryAssCardHouse(Map<String, Object> mapVo);

	// 生成巡检项目
	public String buildAssInsItem(Map<String, Object> entityMap) throws DataAccessException;


	// 查询巡检项目
	public String queryAssInsItem(Map<String, Object> entityMap) throws DataAccessException;

	Map<String, Object> queryAssInSpectionMainPrint(
			Map<String, Object> entityMap) throws DataAccessException;


	// 保存巡检项目
	public String saveAssInsItem(List<Map<String, Object>> entityMap) throws DataAccessException;


	// 删除巡检项目
	public String deleteAssInsItem(List<Map<String, Object>> entityMap) throws DataAccessException;

	public List<String> queryInSpectionMainState(Map<String, Object> mapVo);

	public List<AssInspectionItem> queryByInsId(Map<String, Object> entityMap) throws DataAccessException;

	// 删除巡检项目如果修改过之前资产不存在
	public String deleteAssInsItemByAssInsDetail(AssAcceptDetail entityMap) throws DataAccessException;

	public String queryDetails(Map<String, Object> page);


}
