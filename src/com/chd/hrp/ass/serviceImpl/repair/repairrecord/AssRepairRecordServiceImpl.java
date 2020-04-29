package com.chd.hrp.ass.serviceImpl.repair.repairrecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.ass.dao.repair.assmyinformrepair.AssMyInformRepairMapper;
import com.chd.hrp.ass.dao.repair.repaircentre.AssRepairCentreMapper;
import com.chd.hrp.ass.dao.repair.repairrecord.AssRepairRecordMapper;
import com.chd.hrp.ass.entity.in.AssInMainGeneral;
import com.chd.hrp.ass.service.repair.repairrecord.AssRepairRecordService;
import com.github.pagehelper.PageInfo;

@Service("assRepairRecordService")
public class AssRepairRecordServiceImpl implements AssRepairRecordService {


	@Resource(name = "assRepairRecordMapper")
	private final AssRepairRecordMapper assRepairRecordMapper = null;
	// 默认显示30条数据
	RowBounds rowBoundsALL = new RowBounds(0, 30);

	@Override
	public String queryAssRepairRecord(Map<String, Object> entityMap) {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = assRepairRecordMapper
					.queryAssRepairRecord(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());
			List<Map<String, Object>> list = assRepairRecordMapper.queryAssRepairRecord(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}

	}

	@Override
	public Map<String, Object> queryAssRecordByCode(Map<String, Object> mapVo) {

		return assRepairRecordMapper.queryAssRecordByCode(mapVo);
	}

	@Override
	public String deleteAssRepairRecord(List<String> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());

		try {
			for (String repCode : list) {
				map.put("rep_code", repCode);
				/*Map<String, Object> AssMyRepair = assRepairRecordMapper.queryAssRepairByCode(map);
				if (Integer.parseInt(AssMyRepair.get("STATE").toString()) > 1) {
					return "{\"error\":\"已发送的订单无法删除.\",\"state\":\"false\"}";
				}*/
				assRepairRecordMapper.deleteAssRepairRecord(map);
				List<Map<String, Object>> AssRepairFileList = assRepairRecordMapper.queryImgUrlByRepRecordCode(map);
				for (Map<String, Object> AssRepairFileMap : AssRepairFileList) {
					if (!FtpUtil.deleteFileTwo(AssRepairFileMap.get("ATT_PATH")
							.toString(), AssRepairFileMap.get("ATT_NAME_N")
							.toString())) {
						TransactionAspectSupport.currentTransactionStatus()
								.setRollbackOnly();
						return "error";
					}
				}
			}

			return "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			throw new SysException(e);
		}
	}

	@Override
	public String addAssRepairRecord(Map<String, Object> mapVo,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = null;
		try {
			mapVo.put("app_times", mapVo.get("app_time").toString()+" "+mapVo.get("begin_date")+":00");
			mapVo.put("comp_times", mapVo.get("comp_time").toString()+" "+mapVo.get("end_date")+":00");
			Date begin=stringToDate1(mapVo.get("app_time").toString());
			Date end=stringToDate1(mapVo.get("comp_time").toString());
			long  hour=(end.getTime()-begin.getTime())/1000/60/60;//除以1000是为了转换成秒 

			/*long fen= miao/60  ; // 多少分

			 long  hour=(miao/60)/60;  //  多少小时
*/			mapVo.put("rep_hours",hour);
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("rep_dept",mapVo.get("rep_dept").toString().split("@")[0]);
			mapVo.put("rep_code", queryRecordMaxNo(mapVo));
			/*if ("".equals(mapVo.get("ass_card_no").toString())) {
				mapVo.put("is_card", "0");
			} else {
				mapVo.put("is_card", "1");
			}*/

			assRepairRecordMapper.addAssRepairRecord(mapVo);

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultiValueMap<String, MultipartFile> multiFileMap = multipartRequest
					.getMultiFileMap();
			String basePath = "ass";
			String group_id = SessionManager.getGroupId();
			String hos_id = SessionManager.getHosId();
			String copy_code = SessionManager.getCopyCode();
			String assFilePath = "repair";
			if (multiFileMap != null) {
				for (String key : multiFileMap.keySet()) {
					List<MultipartFile> MultipartFiles = multiFileMap.get(key);
					for (MultipartFile file : MultipartFiles) {

						String locaFileName = new String(
								file.getOriginalFilename());
						// 文件类型
						String fileType = locaFileName.substring(
								locaFileName.lastIndexOf("."),
								locaFileName.length());
						// 使用uuid生成一个新的文件名称
						String serverFileName = UUIDLong.absStringUUID()
								+ fileType;
						String filePath = group_id + "/" + hos_id + "/"
								+ copy_code + "/" + basePath + "/"
								+ assFilePath;
						mapVo.put("att_name_o", locaFileName);
						mapVo.put("att_name_n", serverFileName);
						mapVo.put("att_path", filePath);
						mapVo.put("att_size", file.getSize());
						// 保存文件路径以及文件相关信息
						retJson = addassRepairRecordAtt(mapVo);
						JSONObject json = JSONObject.parseObject(retJson);
						if (!json.get("state").equals("true")) {
							return retJson;
						}
						// 将文件上传到服务器
						retJson = UploadPic(mapVo, file, request, response,
								filePath, serverFileName);
					}
				}
			}
			if (mapVo.get("invdata") != null
					&& !mapVo.get("invdata").toString().equals("")) {
				String[] invData = mapVo.get("invdata").toString().split(",");
				for (String string : invData) {
					Map<String, Object> invMap = new HashMap<String, Object>();
					invMap.put("group_id", SessionManager.getGroupId());
					invMap.put("hos_id", SessionManager.getHosId());
					invMap.put("copy_code", SessionManager.getCopyCode());
					invMap.put("rep_code", mapVo.get("rep_code").toString()
							.trim());
					invMap.put("inv_id", Integer.parseInt(string.split(" ")[0]));
					invMap.put("inv_no", Integer.parseInt(string.split(" ")[1]));
					invMap.put("amount",
							Double.parseDouble(string.split(" ")[2]));
					assRepairRecordMapper.addRecordRepairInv(invMap);
				}
			}
			return "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SysException(e);
		}

	}

	@Override
	public String updateAssRepairRecord(Map<String, Object> mapVo,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = null;
		try {
			mapVo.put("app_times", mapVo.get("app_time").toString()+" "+mapVo.get("begin_date")+":00");
			mapVo.put("comp_times", mapVo.get("comp_time").toString()+" "+mapVo.get("end_date")+":00");
			Date begin=stringToDate1(mapVo.get("app_time").toString());
			Date end=stringToDate1(mapVo.get("comp_time").toString());
			long  hour=(end.getTime()-begin.getTime())/1000/60/60;//除以1000是为了转换成秒 
			mapVo.put("rep_hours",hour);
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("rep_dept", mapVo.get("rep_dept").toString().split("@")[0]);
			
			//更新维修单数据
			assRepairRecordMapper.updateAssRepairRecord(mapVo);
			 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;      
		       MultiValueMap<String, MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();  
		       if(!("").equals(mapVo.get("old_path").toString())){
		    	   String[] old_name= mapVo.get("old_path").toString().split(",");
		    	   for (int i = 0; i < old_name.length; i++) {
		    		   mapVo.put("old_name", old_name[i].substring(old_name[i].lastIndexOf("/")+1, old_name[i].length()));
		    		   //删除已去掉的图片
		    		   retJson = deleteassRepairAtt(mapVo);
		    		   
		    	   }
		       }
			if(multiFileMap!=null){
				
				for (String key : multiFileMap.keySet()) {  
					List<MultipartFile> MultipartFiles = multiFileMap.get(key);  
					if(MultipartFiles != null){
					
						for(MultipartFile file :MultipartFiles){  
							String basePath = "ass";
							String group_id = SessionManager.getGroupId();
							String hos_id = SessionManager.getHosId();
							String copy_code = SessionManager.getCopyCode();
							String assFilePath = "repair";
							//上传文件的原始名称
							String locaFileName = new String(file.getOriginalFilename());  
							//文件类型
							String fileType = locaFileName.substring(locaFileName.lastIndexOf("."), locaFileName.length());  
							//使用uuid生成一个新的文件名称
							String serverFileName = UUIDLong.absStringUUID() + fileType;  
							String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+assFilePath;
							mapVo.put("att_name_o", locaFileName);
							mapVo.put("att_name_n", serverFileName);
							mapVo.put("att_path", filePath);
							mapVo.put("att_size", file.getSize());
							//添加图片到图片数据表中
							retJson = addassRepairRecordAtt(mapVo);			                
							JSONObject json = JSONObject.parseObject(retJson);
							if(!json.get("state").equals("true")){
								return retJson;
							}
							//图片上传
							retJson = UploadPic(mapVo,file,request,response,filePath,serverFileName);
							
							
						}
					}
					if(retJson.equals("error")){
						return "{\"error\":\"上传文件有误 \"}";
					}
				}
				if (mapVo.get("invdata") != null&& !mapVo.get("invdata").toString().equals("")) {
					String[] invData = mapVo.get("invdata").toString().split(",");
					assRepairRecordMapper.deleteBatchRecordRepairInv(mapVo);
					for (String string : invData) {
						Map<String, Object> invMap = new HashMap<String, Object>();
						invMap.put("group_id", SessionManager.getGroupId());
						invMap.put("hos_id", SessionManager.getHosId());
						invMap.put("copy_code", SessionManager.getCopyCode());
						invMap.put("rep_code", mapVo.get("rep_code").toString()
								.trim());
						invMap.put("inv_id", Integer.parseInt(string.split(" ")[0]));
						invMap.put("inv_no", Integer.parseInt(string.split(" ")[1]));
						invMap.put("amount",
								Double.parseDouble(string.split(" ")[2]));
						assRepairRecordMapper.addRecordRepairInv(invMap);
					}
				}
			} 
				return "{\"msg\":\"成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SysException(e);
		}
		
		
	}

	
	@Override
	public String queryMatInvDictSelect(Map<String, Object> entityMap)
			throws DataAccessException {

		List<Map<String, Object>> list = assRepairRecordMapper
				.queryMatInvDictSelect(entityMap);
		return JSONArray.toJSONString(list);

	}

	@Override
	public String queryMatInvDictSelectInfo(Map<String, Object> mapVo)
			throws DataAccessException {
		List<Map<String, Object>> list = assRepairRecordMapper
				.queryMatInvDictSelectInfo(mapVo);
		return ChdJson.toJsonLower(list);
	}

	@Override
	public String queryImgUrlByRecordCode(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = assRepairRecordMapper
				.queryImgUrlByRecordCode(mapVo);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryInvUpdate(Map<String, Object> entityMap) {


		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = assRepairRecordMapper.queryInvUpdate(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			List<Map<String, Object>> list = assRepairRecordMapper.queryInvUpdate(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}

	
	}

	
	
	
	
	
	
	
	
	
	
	
	//添加附件
	public String addassRepairRecordAtt(Map<String, Object> mapVo) {

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("rep_code", mapVo.get("rep_code"));
			map.put("att_name_o", mapVo.get("att_name_o"));
			map.put("att_name_n", mapVo.get("att_name_n"));
			map.put("att_path", mapVo.get("att_path"));
			map.put("att_size", mapVo.get("att_size"));
			map.put("create_user", mapVo.get("rep_user"));
			map.put("create_date", mapVo.get("app_time"));
			map.put("create_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			assRepairRecordMapper.addassRepairRecordAtt(map);

			return "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			throw new SysException(e);
		}

	}

	public String queryRecordMaxNo(Map<String, Object> mapVo) {
		String MaxNo = "";
		mapVo.put("app_times", mapVo.get("app_time").toString()+" "+mapVo.get("begin_date")+":00");
		mapVo.put("comp_times", mapVo.get("comp_time").toString()+" "+mapVo.get("end_date")+":00");
		MaxNo = assRepairRecordMapper.queryRecordMaxNo(mapVo);
		if (MaxNo == null) {
			String sdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).replaceAll("-", "");
			MaxNo = sdate + "001";
		}
		return MaxNo;
	}

	public String UploadPic(Map<String, Object> entityMap,
			MultipartFile uploadFile, HttpServletRequest request,
			HttpServletResponse response, String filePath, String fileName)
			throws Exception {
		try {

			if (entityMap.get("ord_file_url") != null&& !entityMap.get("ord_file_url").equals("")) {
				String ord_file_url = entityMap.get("ord_file_url").toString();
				String file_name = ord_file_url.substring(ord_file_url.lastIndexOf("/") + 1,ord_file_url.length());
				String path = ord_file_url.substring(0,ord_file_url.lastIndexOf("/"));
				if (!FtpUtil.deleteFileTwo(path, file_name)) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}

			if (uploadFile != null) {
				if (!FtpUtil.uploadFile(uploadFile, "", filePath, fileName,request, response)) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
			return "{\"msg\":\"上传成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
//删除附件	
public String deleteassRepairAtt(Map<String, Object> mapVo) {
		
		try {
			Map<String,Object>map = new HashMap<String, Object>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("rep_code", mapVo.get("rep_code"));
				map.put("old_name", mapVo.get("old_name"));
				map.put("create_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				assRepairRecordMapper.deleteassRecordRepairAtt(map);
			String old_path =mapVo.get("old_path").toString().substring(8, mapVo.get("old_path").toString().lastIndexOf("/"));
			if (!FtpUtil.deleteFileTwo(old_path ,mapVo.get("old_name").toString())) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "error";
			}
			
			return "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e);
		}
		
		
	}

@Override
public String queryTimeLineRecordRender(Map<String, Object> mapVo) {
	List<Map<String,Object>>list = assRepairRecordMapper.queryTimeLineRecordRender(mapVo);
	return JSONArray.toJSONString(list);
}

@Override
public Map<String, Object> queryRecordCardDataByCode(Map<String, Object> mapVo) {
	
	Map<String, Object> map =assRepairRecordMapper.queryRecordCardDataByCode(mapVo);
	
	return map;
}
public Map<String, Object> queryAssRepairRecordPrint(Map<String, Object> mapVo) {
	Map<String, Object> result = new HashMap<String, Object>();
	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
	AssRepairRecordMapper assRepairRecordMapper = (AssRepairRecordMapper) context.getBean("assRepairRecordMapper");
	if ("1".equals(String.valueOf(mapVo.get("p_num")))) {
		mapVo.put("is_list", true);
		List<Map<String, Object>> map = assRepairRecordMapper.queryAssRepairRecordPrint(mapVo);
		result.put("main", map);
	} else {
		mapVo.put("is_list", false);
		Map<String, Object> map = assRepairRecordMapper.queryAssRepairRecordPrint(mapVo).get(0);
		result.put("main", map);
	}
	return result;

}
public static Date stringToDate1(String sDate) {
	SimpleDateFormat datePattern = new SimpleDateFormat("yyyy-MM-dd");
	Date d = null;
	try {
		d = datePattern.parse(sDate);
	} catch (ParseException e) {
		e.printStackTrace();
	}
	return d;
}

}
