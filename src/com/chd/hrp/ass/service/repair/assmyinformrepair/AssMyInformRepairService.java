package com.chd.hrp.ass.service.repair.assmyinformrepair;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.chd.base.SqlService;

public interface AssMyInformRepairService extends SqlService{

	String queryAssMyRepairCenter(Map<String, Object> mapVo);

	String queryMaxNo(Map<String, Object> mapVo);

	String addAssMyRepaor(Map<String, Object> mapVo, HttpServletRequest request, HttpServletResponse response);

	String queryTimeLineRender(Map<String, Object> mapVo);

	String updateAssMyRepair(Map<String, Object> mapVo, HttpServletRequest request, HttpServletResponse response);

	String deleteAssMyRepairBatch(List<String> list);

	Map<String, Object> queryCardDataByCode(Map<String, Object> mapVo);

	String addassRepairAtt(Map<String, Object> mapVo);


	String UploadPic(Map<String, Object> entityMap, MultipartFile uploadFile, HttpServletRequest request,
			HttpServletResponse response, String filePath, String fileName) throws Exception;

	String queryImgUrlByRepCode(Map<String, Object> mapVo);

	String deleteassRepairAtt(Map<String, Object> mapVo);


	Map<String, Object> queryAssRepairByCode(Map<String, Object> mapVo);

	Map<String, Object> saveAssMyRepair(Map<String, Object> paramMap);

	Map<String, Object> cuiAssRepirByRepCode(Map<String, Object> mapVo);

	Map<String, Object> submitRepScore(Map<String, Object> mapVo);

}
