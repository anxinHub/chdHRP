package com.chd.hrp.app.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.app.dao.DgmMapper;
import com.chd.hrp.app.service.DgmService;
import com.chd.hrp.ass.dao.repair.repreportcentre.AssRepReportCentreMapper;
import com.chd.hrp.ass.service.repair.repreportcentre.AssRepReportCentreService;

/*浙江帝杰曼微信公众号接口*/
@Service("dgmService")
public class DgmServiceImpl implements DgmService{
	
	private static Logger logger = Logger.getLogger(DgmServiceImpl.class);


	@Resource(name = "dgmMapper")
	private final DgmMapper dgmMapper = null;
	@Resource(name = "assRepReportCentreMapper")
	private final AssRepReportCentreMapper assRepReportCentreMapper=null;
	@Resource(name="assRepReportCentreService")
	private final AssRepReportCentreService assRepReportCentreService  = null ;
	
	//报修
	@Override
	public String addRepair(Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		if(map==null){
			resMap.put("rtnmsg", "报修：参数为空");
			logger.error("报修：参数为空");
			return JSON.toJSONString(resMap); 
		}
		
		if(map.get("group_id")==null || map.get("group_id").toString().equals("")){
			resMap.put("rtnmsg", "报修：gourp_id参数为空");
			logger.error("报修：gourp_id参数为空");
			return JSON.toJSONString(resMap); 
		}
		
		if(map.get("hos_id")==null || map.get("hos_id").toString().equals("")){
			resMap.put("rtnmsg", "报修：hos_id参数为空");
			logger.error("报修：hos_id参数为空");
			return JSON.toJSONString(resMap); 
		}
		
		if(map.get("copy_code")==null || map.get("copy_code").toString().equals("")){
			resMap.put("rtnmsg", "报修：copy_code参数为空");
			logger.error("报修：copy_code参数为空");
			return JSON.toJSONString(resMap); 
		}
		
		if(map.get("fau_code")==null || map.get("fau_code").toString().equals("")){
			resMap.put("rtnmsg", "报修：fau_code参数为空");
			logger.error("报修：fau_code参数为空");
			return JSON.toJSONString(resMap); 
		}
		
		if(map.get("loc_code")==null || map.get("loc_code").toString().equals("")){
			resMap.put("rtnmsg", "报修：loc_code参数为空");
			logger.error("报修：loc_code参数为空");
			return JSON.toJSONString(resMap); 
		}
		
		if(map.get("phone")==null || map.get("phone").toString().equals("")){
			resMap.put("rtnmsg", "报修：phone参数为空");
			logger.error("报修：phone参数为空");
			return JSON.toJSONString(resMap); 
		}
		
		if(map.get("fau_note")==null || map.get("fau_note").toString().equals("")){
			resMap.put("rtnmsg", "报修：fau_note参数为空");
			logger.error("报修：fau_note参数为空");
			return JSON.toJSONString(resMap); 
		}
		
		if(map.get("ass_name")==null || map.get("ass_name").toString().equals("")){
			resMap.put("rtnmsg", "报修：ass_name参数为空");
			logger.error("报修：ass_name参数为空");
			return JSON.toJSONString(resMap); 
		}
		
		if(map.get("eme_status")==null || map.get("eme_status").toString().equals("")){
			map.put("eme_status", 3);//紧急程度，默认一般
		}
		
		String group_id = map.get("group_id").toString();
		String hos_id = map.get("hos_id").toString();
		String copy_code = map.get("copy_code").toString();
		String repCode=assRepReportCentreService.queryMaxNo(map);
		map.put("rep_code", repCode);
		
		//是否有卡片
		if(map.get("ass_card_no")!=null && !map.get("ass_card_no").toString().equals("")){
			map.put("is_card", 1);
			
		}else{
			map.put("is_card", 0);
			map.put("ass_card_no", "");
		}
		
		
		map.put("state", 2);//单据状态-待修
		try {
			//保存单据
			int count=dgmMapper.addRepair(map);
			if(count>0){
				
				/***********************按规则派发维修单据的参数********begin***********************************/
				String taskUser=null;
				Map<String,Object> paramMap=new HashMap<String,Object>();
				paramMap.put("group_id", group_id);
				paramMap.put("hos_id", hos_id);
				paramMap.put("copy_code", copy_code);
				paramMap.put("rep_code_cur", repCode);
				paramMap.put("rep_code", repCode);
				List<String> userRal=new ArrayList<String>();
				String  date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				String Year = date.split("-")[0];
				String Month = date.split("-")[1];
				String day = date.split("-")[2];
				paramMap.put("acc_year", Year);
				paramMap.put("acc_month", Month);
				paramMap.put("day", "day"+Integer.parseInt(day));
				//是否有卡片
				if(map.get("ass_card_no")!=null && !map.get("ass_card_no").toString().equals("")){
					
					//查询卡片对应关系的user_id
					userRal=assRepReportCentreMapper.queryAssRepairUserCardSend(paramMap);
					if(userRal!=null && userRal.size()>0){ 
						//按规则取用户
						taskUser=assRepReportCentreService.getSendUser(paramMap,userRal);
					}
					
				}
				
				if(taskUser==null || taskUser.equals("")){
					//查询故障分类对应关系的user_id
					userRal=assRepReportCentreMapper.queryAssRepairUserFaultSend(paramMap);
					if(userRal!=null && userRal.size()>0){
						//按规则取用户
						taskUser=assRepReportCentreService.getSendUser(paramMap,userRal);
						
					}else{
						
						//查询资产分类对应关系的user_id
						userRal=assRepReportCentreMapper.queryAssRepairUserTypeSend(paramMap);
						if(userRal!=null && userRal.size()>0){
							//按规则取用户
							taskUser=assRepReportCentreService.getSendUser(paramMap,userRal);
							
						}
						
					}
				}
				
				
				if(taskUser!=null && !taskUser.equals("")){
					//添加派单数据到任务表
					String taskId=UUIDLong.absStringUUID();
					paramMap.put("task_user",taskUser);
					paramMap.put("task_id", taskId);
					paramMap.put("state", 3);//维修中
					int rc=assRepReportCentreMapper.addAssRepairTask(paramMap);
					if(rc>0){
						//更新工作量表  将工程师工作量+1
						rc=assRepReportCentreMapper.updateUserWorkByUserId(paramMap);
						if(rc==0){
							//工程师没有工作量时添加工程师工作量
							assRepReportCentreMapper.addUserWorkByUserId(paramMap);
						}
						
						//更新单据状态，接单时间
						dgmMapper.updateRepairPaidan(paramMap);
						
						//微信消息推送
						paramMap.put("msg_id", taskId);
						paramMap.put("content", "您有新的维修工单要处理。");
						dgmMapper.addWeChatMsg(paramMap);
					}
				}
				/***********************按规则派发维修单据的参数******end*************************************/
				
				//上传附件
				if(request instanceof MultipartHttpServletRequest){
					
					MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;      
				    MultiValueMap<String, MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();  
			        String basePath = "ass";
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
								map.put("att_name_o", locaFileName);
								map.put("att_name_n", serverFileName);
								map.put("att_path", filePath);
								map.put("att_size", file.getSize());
								//保存文件路径以及文件相关信息
								String retJson = assRepReportCentreService.addassRepairAtt(map);			                
								JSONObject json = JSONObject.parseObject(retJson);
								if(!json.get("state").equals("true")){
									logger.error("报修：图片保存失败");
									resMap.put("rtnmsg", "图片保存失败");
									return JSON.toJSONString(resMap); 
								}
								//将文件上传到服务器
								retJson = assRepReportCentreService.UploadPic(map,file,request,response,filePath,serverFileName);
								if(retJson!=null && retJson.equals("error")){
									logger.error("报修：图片上传失败");
									resMap.put("rtnmsg", "图片上传失败");
									return JSON.toJSONString(resMap); 
								}
							}  
						} 
					}
			   
				}
				
				resMap.put("rtnmsg", "ok");
				logger.debug("报修：添加成功"+count+"条");
			}else{
				resMap.put("rtnmsg", "院端用户没有维护手机号或者职工");
				logger.debug("维修数据添加失败，院端用户没有维护手机号或者职工");
			}
		
			return JSON.toJSONString(resMap); 
		}catch(Exception e){
			throw new SysException(StringTool.string2Json(e.getMessage()));
		}
	}
}
