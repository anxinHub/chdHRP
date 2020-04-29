package com.chd.hrp.ass.serviceImpl.repair.repreportcentre;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.exception.SysException;
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.ass.dao.repair.repreportcentre.AssRepReportCentreMapper;
import com.chd.hrp.ass.service.repair.repreportcentre.AssRepReportCentreService;

@Service("assRepReportCentreService")
public class AssRepReportCentreServiceImpl implements AssRepReportCentreService{

	@Resource(name = "assRepReportCentreMapper")
	private final AssRepReportCentreMapper assRepReportCentreMapper=null;
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryAssRepReportCentreCenter(Map<String, Object> mapVo) {
		
		List<Map<String,Object>> list=assRepReportCentreMapper.queryAssRepReportCentreCenter(mapVo);
		
		return ChdJson.toJsonLower(list);
	}

	@Override
	public String queryMaxNo(Map<String, Object> mapVo) {
		String MaxNo="";
		mapVo.put("app_time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		 MaxNo=assRepReportCentreMapper.queryMaxNo(mapVo);
		if(MaxNo == null){
			String sdate=new SimpleDateFormat("yyyy-MM-dd").format(new Date()).replaceAll("-","");
			MaxNo = sdate+"001";
		} 
		return MaxNo;
	}

	/*
	 * 此方法的业务逻辑与src\com\chd\hrp\app\serviceImpl\DgmServiceImpl.java（addRepair）业务逻辑共用
	 * 如果此方法有bug，那边也需要修改
	 */
	@Override
	public String addAssRepReportCentre(Map<String, Object> mapVo, HttpServletRequest request, HttpServletResponse response) {
		String retJson= null;
		try {
			mapVo.put("app_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("rep_dept", mapVo.get("rep_dept").toString().split("@")[0]);
			mapVo.put("rep_code", queryMaxNo(mapVo));
			if("".equals(mapVo.get("ass_card_no").toString())){
				mapVo.put("is_card", "0");
			}else{
				mapVo.put("is_card", "1");
			}
			
			assRepReportCentreMapper.addAssRepReportCentre(mapVo);
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;      
		       MultiValueMap<String, MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();  
		       String basePath = "ass";
				String group_id = SessionManager.getGroupId();
				String hos_id = SessionManager.getHosId();
				String copy_code = SessionManager.getCopyCode();
				String assFilePath = "repair";
				if(multiFileMap!=null){
					for (String key : multiFileMap.keySet()) {  
						List<MultipartFile> MultipartFiles = multiFileMap.get(key);  
						for(MultipartFile file :MultipartFiles){  
							
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
							//保存文件路径以及文件相关信息
							retJson = addassRepairAtt(mapVo);			                
							JSONObject json = JSONObject.parseObject(retJson);
							if(!json.get("state").equals("true")){
								return retJson;  
							}
							//将文件上传到服务器
							retJson = UploadPic(mapVo,file,request,response,filePath,serverFileName);
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
	public String addassRepairAtt(Map<String, Object> mapVo){
		
		try {
			Map<String,Object>map = new HashMap<String, Object>();
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
			assRepReportCentreMapper.addassRepairAtt(map);
			
			return "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			throw new SysException(e);
		}
		
		
	}
	@Override
	public String queryTimeLineRender(Map<String, Object> mapVo) {
		List<Map<String,Object>>list = assRepReportCentreMapper.queryTimeLineRender(mapVo);
		return JSONArray.toJSONString(list);
	}

 

	@Override
	public String updateAssRepReportCentre(Map<String, Object> mapVo, HttpServletRequest request, HttpServletResponse response) {
		String retJson = null;
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			//确认单据状态，只能修改未发送数据
			Map<String,Object> AssRepReportCentre = assRepReportCentreMapper.queryAssRepairByCode(mapVo);
			if(Integer.parseInt(AssRepReportCentre.get("STATE").toString())>1){
				return "{\"error\":\"已发送的订单无法修改.\",\"state\":\"false\"}";
			}
			mapVo.put("rep_dept", mapVo.get("rep_dept").toString().split("@")[0]);
			if("".equals(mapVo.get("ass_card_no").toString())){
				mapVo.put("is_card", "0");
			}else{
				mapVo.put("is_card", "1");
			}
			//更新维修单数据
			assRepReportCentreMapper.updateAssRepReportCentre(mapVo);
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
			if(multiFileMap!=null && multiFileMap.size()>0){
				for (String key : multiFileMap.keySet()) {  
					List<MultipartFile> MultipartFiles = multiFileMap.get(key);  
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
						retJson = addassRepairAtt(mapVo);			                
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
			 
			return "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SysException(e);
		}
		
		
	}
	@Override
	public String deleteassRepairAtt(Map<String, Object> mapVo) {
		
		try {
			Map<String,Object>map = new HashMap<String, Object>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("rep_code", mapVo.get("rep_code"));
				map.put("old_name", mapVo.get("old_name"));
				map.put("create_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				assRepReportCentreMapper.deleteassRepairAtt(map);
			String old_path =mapVo.get("old_path").toString().substring(8, mapVo.get("old_path").toString().lastIndexOf("/"));
			if (!FtpUtil.deleteFileTwo(old_path ,mapVo.get("old_name").toString())) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "error";
			}
			
			return "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SysException(e);
		}
		
		
	}

	@Override
	public String deleteAssRepReportCentreBatch(List<String> list) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("group_id",SessionManager.getGroupId());
		map.put("hos_id",SessionManager.getHosId());
		map.put("copy_code",SessionManager.getCopyCode());
		
		try {
			for (String repCode : list) {
				map.put("rep_code",repCode);
				Map<String,Object> AssRepReportCentre = assRepReportCentreMapper.queryAssRepairByCode(map);
				if(Integer.parseInt(AssRepReportCentre.get("STATE").toString())>1){
					return "{\"error\":\"已发送的订单无法删除.\",\"state\":\"false\"}";
				}
				assRepReportCentreMapper.deleteAssRepReportCentreBatch(map);
				List<Map<String,Object>> AssRepairFileList = assRepReportCentreMapper.queryImgUrlByRepCode(map);
				for (Map<String, Object> AssRepairFileMap : AssRepairFileList) {
					if (!FtpUtil.deleteFileTwo(AssRepairFileMap.get("ATT_PATH").toString(), AssRepairFileMap.get("ATT_NAME_N").toString())) {
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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
	public Map<String, Object> queryCardDataByCode(Map<String, Object> mapVo) {
		
		Map<String, Object> map =assRepReportCentreMapper.queryCardDataByCode(mapVo);
		
		return map;
	}
	@Override
	public String UploadPic(Map<String, Object> entityMap, MultipartFile uploadFile, HttpServletRequest request,
			HttpServletResponse response, String filePath ,String fileName) throws Exception {
		try {
			
			if (entityMap.get("ord_file_url") != null && !entityMap.get("ord_file_url").equals("")) {
				String ord_file_url = entityMap.get("ord_file_url").toString();
				String file_name = ord_file_url.substring(ord_file_url.lastIndexOf("/") + 1, ord_file_url.length());
				String path = ord_file_url.substring(0, ord_file_url.lastIndexOf("/"));
				if (!FtpUtil.deleteFileTwo(path, file_name)) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
			
			if (uploadFile != null) {
				if (!FtpUtil.uploadFile(uploadFile, "", filePath,fileName, request, response)) {
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

	@Override
	public String queryImgUrlByRepCode(Map<String, Object> mapVo) {
		List<Map<String,Object>> list =assRepReportCentreMapper.queryImgUrlByRepCode(mapVo);
		return JSONArray.toJSONString(list);
	}

	@Override
	public Map<String,Object> saveAssRepReportCentre(Map<String,Object> paramMap) {
		
		Map<String,Object> resMap = new HashMap<String, Object>();
		try {
			
			String repStr=paramMap.get("rep_code").toString();
			repStr=repStr.replaceAll(",", "','");//in('1','2')
			repStr="'"+repStr+"'";
			String[] repNo=repStr.split(",");
			paramMap.put("rep_code", repStr);
			
			//检查单据发送状态
			List<String> reCount=assRepReportCentreMapper.existsAssRepairSend(paramMap);
			if(reCount!=null && reCount.size()>0){
				
				resMap.put("state", false);
				resMap.put("error", reCount.toString()+"，该单据不是未发送状态！");
				return resMap;
			}
			
			
			String  date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String Year = date.split("-")[0];
			String Month = date.split("-")[1];
			String day = date.split("-")[2];
			paramMap.put("acc_year", Year);
			paramMap.put("acc_month", Month);
			paramMap.put("day", "day"+Integer.parseInt(day));
			String resLen="";
			for(String rep:repNo){
				
				paramMap.put("rep_code_cur", rep);
				String taskUser=null;
				//查询卡片对应关系的user_id
				List<String> userRal=new ArrayList<String>();
				userRal=assRepReportCentreMapper.queryAssRepairUserCardSend(paramMap);
				if(userRal!=null && userRal.size()>0){ 
					//按规则取用户
					taskUser=getSendUser(paramMap,userRal);
					
				}else{
					 
					//查询故障分类对应关系的user_id
					userRal=assRepReportCentreMapper.queryAssRepairUserFaultSend(paramMap);
					if(userRal!=null && userRal.size()>0){
						//按规则取用户
						taskUser=getSendUser(paramMap,userRal);
						
					}
					//暂时不考虑资产分类对应关系
					else{
						
						//查询资产分类对应关系的user_id
						userRal=assRepReportCentreMapper.queryAssRepairUserTypeSend(paramMap);
						if(userRal!=null && userRal.size()>0){
							//按规则取用户
							taskUser=getSendUser(paramMap,userRal);
							
						}
						
					}
					
				}
				resLen=taskUser;
				if(taskUser!=null && !taskUser.equals("")){
					//添加派单数据到工作表
					Map<String,Object> repDataMap = new HashMap<String, Object>();
					repDataMap.putAll(paramMap);
					repDataMap.put("rep_code",rep);
					repDataMap.put("task_user",taskUser);
					repDataMap.put("task_id", UUIDLong.absStringUUID());
					repDataMap.put("state",2); 
					int rc=assRepReportCentreMapper.addAssRepairTask(repDataMap);
					if(rc>0){
						//更新工作量表  将工程师工作量+1
						rc=assRepReportCentreMapper.updateUserWorkByUserId(repDataMap);
						if(rc==0){
							//工程师没有工作量时添加工程师工作量
							assRepReportCentreMapper.addUserWorkByUserId(repDataMap);
						}
					}
					
				}
			}
			
			
			//更新维修单为已发送状态
			assRepReportCentreMapper.updateAssRepairSend(paramMap);
			if(resLen!=null && !resLen.equals("")){
				resMap.put("msg", "发送成功！");
				resMap.put("state", true);
			}else{
				resMap.put("msg", "卡片或故障分类没有维护对应的维修工程师，需要去维修派单进行人工派单！");
				resMap.put("state", true);
			}
			
		} catch (Exception e) {
			throw new SysException(e);
		}
		return resMap;
	}

	@Override
	public Map<String, Object> queryAssRepairByCode(Map<String, Object> mapVo) {
		
		return assRepReportCentreMapper.queryAssRepairByCode(mapVo);
	}
	
	@Override
	public String getSendUser(Map<String, Object> paramMap,List<String> userRal){
		String resUser=null;
		StringBuilder sb=new StringBuilder();
		String userTemp=null;
		for(String user:userRal){
			sb.append(user+",");
		}
		sb.setCharAt(sb.length()-1, ' ');
		userTemp=userRal.get(0);//先取第一个user_id
		paramMap.put("user_id_ral", sb.toString());//对应关系的user_id
		
		//根据用户对应关系查询是否上班
		List<String> sbUser=assRepReportCentreMapper.queryWorkUserByDateSend(paramMap);
		if(sbUser!=null && sbUser.size()>0){
			sb=new StringBuilder();
			for(String user:sbUser){
				sb.append(user+",");
			}
			sb.setCharAt(sb.length()-1, ' ');
			userTemp=sbUser.get(0);//先取第一个user_id
			paramMap.put("user_id_ral", sb.toString());//根据对应关系上班的user_id
		}
		
		//根据用户对应关系查询工作量最小的用户
		resUser=assRepReportCentreMapper.queryUserWorkMinSend(paramMap);
		if(resUser==null || resUser.equals("")){
			resUser=userTemp;
		}
		
		return resUser;
	}
	
	
	@Override
	public Map<String, Object> cuiAssRepirByRepCode(Map<String, Object> mapVo) {
		List<String>  reCount= assRepReportCentreMapper.existsAssRepairState(mapVo);
		List<String>  reUrgCount= assRepReportCentreMapper.existsAssRepairUrgState(mapVo);
		 Map<String,Object> resMap = new HashMap<String, Object>();
		 
		 	//检查单据发送状态
			if(reCount!=null && reCount.size()>0){
				
				resMap.put("state", false);
				resMap.put("error", reCount.toString()+"，该单据不是待修或维修中状态！");
				return resMap;
			}
			//检查单据发送状态
			if(reUrgCount!=null && reUrgCount.size()>0){
				
				resMap.put("state", false);
				resMap.put("error", reUrgCount.toString()+"，已催的单据不能重复催单！");
				return resMap;
			}
			assRepReportCentreMapper.cuiAssRepirByRepCode(mapVo);
			resMap.put("state", true);
			resMap.put("msg", "成功");
			return resMap;
	}

	@Override
	public Map<String, Object> submitRepScore(Map<String, Object> mapVo) {
		try {
			String rep_code= "";
			 Map<String,Object> resMap = new HashMap<String, Object>();
			 rep_code = assRepReportCentreMapper.queryRepairState(mapVo);
			 if(rep_code!=null ){
				 resMap.put("state", false);
				 resMap.put("error",rep_code+"状态不是已完成，不能进行评价！");
				 return resMap;
			 }
			 rep_code = assRepReportCentreMapper.queryRepairExistsScore(mapVo);
			 if(rep_code!=null ){
				 resMap.put("state", false);
				 resMap.put("error", rep_code+"已评价 ，不能重复评价！"); 
				 return resMap; 
			 }
			 assRepReportCentreMapper.submitRepScore(mapVo);
			resMap.put("state", true);
			resMap.put("msg", "成功");
			return resMap;
		} catch (Exception e) {
			throw new SysException(e);
		}
	}
	
	
	public Map<String, Object> queryAssRepairCenterPrint(Map<String, Object> mapVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		AssRepReportCentreMapper assRepairCentreMapper = (AssRepReportCentreMapper) context.getBean("assRepReportCentreMapper");
		if ("1".equals(String.valueOf(mapVo.get("p_num")))) {
			mapVo.put("is_list", true);
			List<Map<String, Object>> map = assRepairCentreMapper.queryAssRepairCenterPrint(mapVo);
			result.put("main", map);
		} else {
			mapVo.put("is_list", false);
			Map<String, Object> map = assRepairCentreMapper.queryAssRepairCenterPrint(mapVo).get(0);
			result.put("main", map);
		}
		return result;
	
	}

}
