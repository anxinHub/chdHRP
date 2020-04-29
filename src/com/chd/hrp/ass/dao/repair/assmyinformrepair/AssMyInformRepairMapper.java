package com.chd.hrp.ass.dao.repair.assmyinformrepair;

import java.util.List;  
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssMyInformRepairMapper extends SqlMapper {

	List<Map<String, Object>> queryAssMyRepairCenter(Map<String, Object> mapVo) throws DataAccessException ;

	String queryMaxNo(Map<String, Object> mapVo) throws DataAccessException;

	void addAssMyRepaor(Map<String, Object> mapVo) throws DataAccessException;

	List<Map<String, Object>> queryTimeLineRender(Map<String, Object> mapVo) throws DataAccessException;

	int addassRepairAtt(Map<String, Object> map) throws DataAccessException;


	int updateAssMyRepair(Map<String, Object> mapVo) throws DataAccessException;


	int deleteAssMyRepairBatch(Map<String, Object> mapVo) throws DataAccessException;

	Map<String, Object> queryCardDataByCode(Map<String, Object> mapVo);

	List<Map<String, Object>> queryImgUrlByRepCode(Map<String, Object> mapVo);

	int deleteassRepairAtt(Map<String, Object> map);


	int addAssRepairTask(Map<String, Object> repDataMap);

	int updateUserWorkByUserId(Map<String, Object> map);

	List<String> queryAssRepUserType(Map<String, Object> map);

	Map<String,Object> queryAssRepairByCode(Map<String, Object> mapVo);

	//检查单据发送状态 
	public List<String> existsAssRepairSend(Map<String, Object> map);
	
	//查询卡片对应关系的user_id
	public List<String> queryAssRepairUserCardSend(Map<String, Object> map);
	
	//查询资产分类对应关系的user_id
	public List<String> queryAssRepairUserTypeSend(Map<String, Object> map);
	
	//查询故障分类对应关系的user_id
	public List<String> queryAssRepairUserFaultSend(Map<String, Object> map);
	
	//根据用户对应关系查询是否上班
	public List<Map<String, Object>> queryWorkUserByDateSend(Map<String, Object> map);

	//根据用户对应关系查询工作量最小的用户
	public String queryUserWorkMinSend(Map<String, Object> map);
	
	//更新单据发送状态
	public void updateAssRepairSend(Map<String, Object> map);
	
	public void addUserWorkByUserId(Map<String, Object> map);

	Map<String, Object> queryUserWorkByUserId(Map<String, Object> repDataMap);

	int addUserWork(Map<String, Object> repDataMap);

	List<String> existsAssRepairState(Map<String, Object> mapVo);

	int cuiAssRepirByRepCode(Map<String, Object> mapVo);

	List<String> existsAssRepairUrgState(Map<String, Object> mapVo);

	int submitRepScore(Map<String, Object> mapVo);

	String queryRepairState(Map<String, Object> mapVo);

	String queryRepairExistsScore(Map<String, Object> mapVo);
	
	public List<Map<String, Object>> queryWorkUserSend(Map<String, Object> map);

}
