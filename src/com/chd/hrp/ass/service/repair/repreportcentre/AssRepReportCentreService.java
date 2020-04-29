package com.chd.hrp.ass.service.repair.repreportcentre;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.chd.base.SqlService;

public interface AssRepReportCentreService extends SqlService{

	String queryAssRepReportCentreCenter(Map<String, Object> mapVo);

	String queryMaxNo(Map<String, Object> mapVo);

	String addAssRepReportCentre(Map<String, Object> mapVo, HttpServletRequest request, HttpServletResponse response);

	String queryTimeLineRender(Map<String, Object> mapVo);

	String updateAssRepReportCentre(Map<String, Object> mapVo, HttpServletRequest request, HttpServletResponse response);

	String deleteAssRepReportCentreBatch(List<String> list);

	Map<String, Object> queryCardDataByCode(Map<String, Object> mapVo);

	String addassRepairAtt(Map<String, Object> mapVo);


	String UploadPic(Map<String, Object> entityMap, MultipartFile uploadFile, HttpServletRequest request,
			HttpServletResponse response, String filePath, String fileName) throws Exception;

	String queryImgUrlByRepCode(Map<String, Object> mapVo);

	String deleteassRepairAtt(Map<String, Object> mapVo);


	Map<String, Object> queryAssRepairByCode(Map<String, Object> mapVo);

	Map<String, Object> saveAssRepReportCentre(Map<String, Object> paramMap);

	Map<String, Object> submitRepScore(Map<String, Object> mapVo);

	Map<String, Object> cuiAssRepirByRepCode(Map<String, Object> mapVo);

	String getSendUser(Map<String, Object> paramMap, List<String> userRal);
	

}
