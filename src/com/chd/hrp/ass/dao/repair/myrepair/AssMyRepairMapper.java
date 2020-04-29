package com.chd.hrp.ass.dao.repair.myrepair;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssMyRepairMapper extends SqlMapper {

	List<Map<String, Object>> queryAssMyRepairCenter(Map<String, Object> mapVo) throws DataAccessException ;

	int submitRepScore(Map<String, Object> mapVo);
	
	List<Map<String, Object>> queryTimeLineRender(Map<String, Object> mapVo) throws DataAccessException;

	Map<String, Object> queryCardDataByCode(Map<String, Object> mapVo);

	List<Map<String, Object>> queryImgUrlByRepCode(Map<String, Object> mapVo);

	Map<String,Object> queryAssRepairByCode(Map<String, Object> mapVo);
	 
	//更新单据发送状态
	public void updateAssRepairSend(Map<String, Object> map);
	
	List<Map<String, Object>> queryRepTeamUser(Map<String, Object> mapVo);

	int addAssRepTask(Map<String, Object> tsdkMap);
	
	int deleteAssRepTask(Map<String, Object> tsdkMap);

	List<String> queryAssRepairStateByRepcode(Map<String, Object> mapVo);

	int updateAssRepairEndState(Map<String, Object> mapVo);

	int updateAssRepairState(Map<String, Object> mapVo);
	
	int updateAssRepairStateBack(Map<String, Object> mapVo);

	int addAssRepTaskByMyUserId(Map<String, Object> mapVo);

	List<Map<String, Object>> existsAssRepairTaskState(Map<String, Object> mapVo);

	List<Map<String, Object>> queryMatInvDict(Map<String, Object> mapVo);

	int updateEndRepairState(Map<String, Object> mapVo);

	int addRepairInv(Map<String, Object> invMap);

}
