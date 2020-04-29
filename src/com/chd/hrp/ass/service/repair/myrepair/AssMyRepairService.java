package com.chd.hrp.ass.service.repair.myrepair;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.chd.base.SqlService;

public interface AssMyRepairService extends SqlService{

	String queryAssMyRepairCenter(Map<String, Object> mapVo);
	
	Map<String, Object> submitRepScore(Map<String, Object> mapVo);

	String queryTimeLineRender(Map<String, Object> mapVo);

	Map<String, Object> queryCardDataByCode(Map<String, Object> mapVo);

	String queryImgUrlByRepCode(Map<String, Object> mapVo);

	Map<String, Object> queryAssRepairByCode(Map<String, Object> mapVo);

	String queryRepTeamUser(Map<String, Object> mapVo);

	String repairReceiving(Map<String, Object> mapVo);
	
	String repairBack(Map<String, Object> mapVo);

	String updateRepUser(Map<String, Object> mapVo);

	String updateEndRepUser(Map<String, Object> mapVo);

	String queryMatInvDict(Map<String, Object> mapVo);

	String updateEndRepairState(Map<String, Object> mapVo);

}
