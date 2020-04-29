package com.chd.hrp.ass.serviceImpl.repair.assmyinformrepair;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.PushSMS.PushMessage;
import com.chd.base.exception.SysException;
import com.chd.base.ftp.*;
import com.chd.base.util.ChdJson;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.ass.dao.repair.assmyinformrepair.AssMyInformRepairMapper;
import com.chd.hrp.ass.service.repair.assmyinformrepair.AssMyInformRepairService;
 
@Service("assMyInformRepairService")
public class AssMyInformRepairServiceImpl implements AssMyInformRepairService{

	@Resource(name = "assMyInformRepairMapper")
	private final AssMyInformRepairMapper assMyInformRepairMapper=null;
	
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
	public String queryAssMyRepairCenter(Map<String, Object> mapVo) {
		
		List<Map<String,Object>> list=assMyInformRepairMapper.queryAssMyRepairCenter(mapVo);
		
		return ChdJson.toJsonLower(list);
	}

	@Override
	public String queryMaxNo(Map<String, Object> mapVo) {
		String MaxNo="";
		mapVo.put("app_time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		 MaxNo=assMyInformRepairMapper.queryMaxNo(mapVo);
		if(MaxNo == null){
			String sdate=new SimpleDateFormat("yyyy-MM-dd").format(new Date()).replaceAll("-","");
			MaxNo = sdate+"001";
		} 
		return MaxNo;
	}

	@Override
	public String addAssMyRepaor(Map<String, Object> mapVo, HttpServletRequest request, HttpServletResponse response) {
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
			
			assMyInformRepairMapper.addAssMyRepaor(mapVo);
			
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
			assMyInformRepairMapper.addassRepairAtt(map);
			
			return "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			throw new SysException(e);
		}
		
		
	}
	@Override
	public String queryTimeLineRender(Map<String, Object> mapVo) {
		List<Map<String,Object>>list = assMyInformRepairMapper.queryTimeLineRender(mapVo);
		return JSONArray.toJSONString(list);
	}

 

	@Override
	public String updateAssMyRepair(Map<String, Object> mapVo, HttpServletRequest request, HttpServletResponse response) {
		String retJson = null;
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			//确认单据状态，只能修改未发送数据
			Map<String,Object> AssMyRepair = assMyInformRepairMapper.queryAssRepairByCode(mapVo);
			if(Integer.parseInt(AssMyRepair.get("STATE").toString())>1){
				return "{\"error\":\"已发送的订单无法修改.\",\"state\":\"false\"}";
			}
			mapVo.put("rep_dept", mapVo.get("rep_dept").toString().split("@")[0]);
			if("".equals(mapVo.get("ass_card_no").toString())){
				mapVo.put("is_card", "0");
			}else{
				mapVo.put("is_card", "1");
			}
			//更新维修单数据
			assMyInformRepairMapper.updateAssMyRepair(mapVo);
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
				assMyInformRepairMapper.deleteassRepairAtt(map);
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
	public String deleteAssMyRepairBatch(List<String> list) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("group_id",SessionManager.getGroupId());
		map.put("hos_id",SessionManager.getHosId());
		map.put("copy_code",SessionManager.getCopyCode());
		
		try {
			for (String repCode : list) {
				map.put("rep_code",repCode);
				Map<String,Object> AssMyRepair = assMyInformRepairMapper.queryAssRepairByCode(map);
				if(Integer.parseInt(AssMyRepair.get("STATE").toString())>1){
					return "{\"error\":\"已发送的订单无法删除.\",\"state\":\"false\"}";
				}
				assMyInformRepairMapper.deleteAssMyRepairBatch(map);
				List<Map<String,Object>> AssRepairFileList = assMyInformRepairMapper.queryImgUrlByRepCode(map);
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
		
		Map<String, Object> map =assMyInformRepairMapper.queryCardDataByCode(mapVo);
		
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
		List<Map<String,Object>> list =assMyInformRepairMapper.queryImgUrlByRepCode(mapVo);
		return JSONArray.toJSONString(list);
	}

	@Override
	public Map<String,Object> saveAssMyRepair(Map<String,Object> paramMap) {
		
		Map<String,Object> resMap = new HashMap<String, Object>();
		
		PushMessage pm = PushMessage.getInstance();
		
		try {
			
			String repStr=paramMap.get("rep_code").toString();
			repStr=repStr.replaceAll(",", "','");//in('1','2')
			repStr="'"+repStr+"'";
			String[] repNo=repStr.split(",");
			paramMap.put("rep_code", repStr);
			
			//检查单据发送状态
			List<String> reCount=assMyInformRepairMapper.existsAssRepairSend(paramMap);
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
			
			for(String rep:repNo){
				
				paramMap.put("rep_code_cur", rep);
				String taskUser=null;
				//查询卡片对应关系的user_id
				List<String> userRal=new ArrayList<String>();
				userRal=assMyInformRepairMapper.queryAssRepairUserCardSend(paramMap);
				if(userRal!=null && userRal.size()>0){ 
					//按规则取用户
					taskUser=getSendUser(paramMap,userRal);
					
				}else{
					 
					//查询故障分类对应关系的user_id
					userRal=assMyInformRepairMapper.queryAssRepairUserFaultSend(paramMap);
					if(userRal!=null && userRal.size()>0){
						//按规则取用户
						taskUser=getSendUser(paramMap,userRal);
						
					}
					//暂时不考虑资产分类对应管系
					else{
						
						//查询资产分类对应关系的user_id
						userRal=assMyInformRepairMapper.queryAssRepairUserTypeSend(paramMap);
						if(userRal!=null && userRal.size()>0){
							//按规则取用户
							taskUser=getSendUser(paramMap,userRal);
							
						}else{
							
							List<Map<String, Object>> list=assMyInformRepairMapper.queryWorkUserSend(paramMap);
							
							for (Map<String, Object> map : list) {
								
								Map<String, Object> sendMap = new HashMap<String, Object>();

								sendMap.put("PhoneNum", map.get("PHONE2"));

								sendMap.put("Content", map.get("NOTE"));

								pm.send(sendMap); 
							}
						}
						
					}
					
				}
				
				if(taskUser!=null && !taskUser.equals("")){
					//添加派单数据到工作表
					Map<String,Object> repDataMap = new HashMap<String, Object>();
					repDataMap.putAll(paramMap);
					repDataMap.put("rep_code",rep);
					repDataMap.put("task_user",taskUser);
					repDataMap.put("task_id", UUIDLong.absStringUUID());
					repDataMap.put("state",2); 
					int rc=assMyInformRepairMapper.addAssRepairTask(repDataMap);
					if(rc>0){
						Map<String,Object> uesrMap = assMyInformRepairMapper.queryUserWorkByUserId(repDataMap);
						if(uesrMap!=null){
							//更新工作量表  将工程师工作量+1
							assMyInformRepairMapper.updateUserWorkByUserId(repDataMap);
						}else{
							//工程师没有工作量时添加工程师工作量
							assMyInformRepairMapper.addUserWork(repDataMap);
						}
						
					}
					
				}
			}
			
			
			//更新维修单为已发送状态
			assMyInformRepairMapper.updateAssRepairSend(paramMap);
			resMap.put("msg", "发送成功！");
			resMap.put("state", true);
		} catch (Exception e) {
			throw new SysException(e);
		}
		return resMap;
	}

	@Override
	public Map<String, Object> queryAssRepairByCode(Map<String, Object> mapVo) {
		
		return assMyInformRepairMapper.queryAssRepairByCode(mapVo);
	}
	
	
	private String getSendUser(Map<String, Object> paramMap,List<String> userRal){
		String resUser=null;
		StringBuilder sb=new StringBuilder();
		String userTemp=null;
		for(String user:userRal){
			sb.append(user+",");
		}
		sb.setCharAt(sb.length()-1, ' ');
		userTemp=userRal.get(0);//先取第一个user_id
		paramMap.put("user_id_ral", sb.toString());//对应关系的user_id
		
		//根据用户对应关系查询排班表是否有数据，没有数据不走排班功能
		List<Map<String,Object>> sbUser=assMyInformRepairMapper.queryWorkUserByDateSend(paramMap);
		if(userRal!=null && userRal.size()>0){
			sb=new StringBuilder();
			boolean isWorkDay=true;
			for (Map<String, Object> map : sbUser) {
				//上班才派发
				if("1".equals(map.get("DAY").toString())){
					sb.append(map.get("REP_USER")+",");
					if(isWorkDay){
						//先取出第一个上班的
						userTemp=map.get("REP_USER").toString();
						isWorkDay=false;
					}
				}
			}
			if(sb!=null && sb.toString().length()>0){
				sb.setCharAt(sb.length()-1, ' ');
				paramMap.put("user_id_ral", sb.toString());//根据对应关系上班的user_id
			}else{
				//没有上班人员返回null不派发
				return null;
			}
		}
		
		//根据用户对应关系查询工作量最小的用户
		resUser=assMyInformRepairMapper.queryUserWorkMinSend(paramMap);
		if(resUser==null || resUser.equals("")){
			resUser=userTemp;
		}
		
		return resUser;
	}

	@Override
	public Map<String, Object> cuiAssRepirByRepCode(Map<String, Object> mapVo) {
		List<String>  reCount= assMyInformRepairMapper.existsAssRepairState(mapVo);
		List<String>  reUrgCount= assMyInformRepairMapper.existsAssRepairUrgState(mapVo);
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
			assMyInformRepairMapper.cuiAssRepirByRepCode(mapVo);
			resMap.put("state", true);
			resMap.put("msg", "成功");
			return resMap;
	}

	@Override
	public Map<String, Object> submitRepScore(Map<String, Object> mapVo) {
		try {
			String rep_code= "";
			 Map<String,Object> resMap = new HashMap<String, Object>();
			 rep_code = assMyInformRepairMapper.queryRepairState(mapVo);
			 if(rep_code!=null ){
				 resMap.put("state", false);
				 resMap.put("error",rep_code+"状态不是已完成，不能进行评价！");
				 return resMap;
			 }
			 rep_code = assMyInformRepairMapper.queryRepairExistsScore(mapVo);
			 if(rep_code!=null ){
				 resMap.put("state", false);
				 resMap.put("error", rep_code+"已评价 ，不能重复评价！"); 
				 return resMap;
			 }
			assMyInformRepairMapper.submitRepScore(mapVo);
			resMap.put("state", true);
			resMap.put("msg", "成功");
			return resMap;
		} catch (Exception e) {
			throw new SysException(e);
		}
	}

 

}
