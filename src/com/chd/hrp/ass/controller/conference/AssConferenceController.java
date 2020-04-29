package com.chd.hrp.ass.controller.conference;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.startup.LoadSystemInfo;
import com.chd.base.util.FileUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.tend.AssTendFile;
import com.chd.hrp.ass.service.conference.AssConferenceService;

/**
 * 会议记录页面
 * @author cyw
 *
 */
@Controller
public class AssConferenceController extends BaseController{
	
	@Resource(name="assConferenceService")
	private final AssConferenceService assConferenceService=null;
    /**
     * 主页面跳转
     * @return
     */
	@RequestMapping(value="/hrp/ass/assconference/AssConferenceMainPage",method=RequestMethod.GET)
	public String AssConferenceMainPage(){
		
		return "hrp/ass/assconference/assConferenceMain";
	}
	/**
	 * 主表查询方法
	 * @param mapVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hrp/ass/assconference/queryAssConference",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssConference(@RequestParam Map<String, Object> mapVo,Model model){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String res=assConferenceService.queryAssConference(getPage(mapVo));
		return JSONObject.parseObject(res);
	};
	
	/**
	 * 主表查询方法(根据ID查询)
	 * @param mapVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hrp/ass/assconference/queryAssConferenceByID",method=RequestMethod.POST)
	@ResponseBody
	public String queryAssConferenceByID(@RequestParam Map<String, Object> mapVo,Model model){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String res=assConferenceService.queryAssConferenceByID(mapVo);
		return res;
	};
	/**
	 * 保存方法
	 * @param mapVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hrp/ass/assconference/saveAssConference",method=RequestMethod.POST)
	@ResponseBody
	public String saveAssConference(@RequestParam Map<String, Object> mapVo,Model model){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String res=assConferenceService.saveAssConference(mapVo);
		return res;
	}
	/**
	 * 更新方法
	 * @param mapVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hrp/ass/assconference/updateAssConference",method=RequestMethod.POST)
	@ResponseBody
	public String updateAssConference(@RequestParam Map<String, Object> mapVo,Model model){
		
		  if (mapVo.get("group_id") == null) {
		      mapVo.put("group_id", SessionManager.getGroupId());
		    }
		    if (mapVo.get("hos_id") == null) {
		      mapVo.put("hos_id", SessionManager.getHosId());
		    }
		    if (mapVo.get("copy_code") == null) {
		      mapVo.put("copy_code", SessionManager.getCopyCode());
		    }
		  String res=  assConferenceService.updateAssConference(mapVo);
		return res;
	}

	@RequestMapping(value="/hrp/ass/assconference/deleteConference",method=RequestMethod.POST)
	@ResponseBody
	public String deleteConference(@RequestParam Map<String, Object> mapVo,Model model){
		//System.out.println(mapVo);
		 if (mapVo.get("group_id") == null) {
		      mapVo.put("group_id", SessionManager.getGroupId());
		    }
		    if (mapVo.get("hos_id") == null) {
		      mapVo.put("hos_id", SessionManager.getHosId());
		    }
		    if (mapVo.get("copy_code") == null) {
		      mapVo.put("copy_code", SessionManager.getCopyCode());
		    }
		    
		String res=assConferenceService.deleteConference(mapVo);
		return res;
	}
	/**
	 * 删除出席人员
	 * @param mapVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hrp/ass/assconference/deleteConferencePerson")
	@ResponseBody
	public String deleteConferencePerson(@RequestParam Map<String, Object> mapVo,Model model){
		 if (mapVo.get("group_id") == null) {
		      mapVo.put("group_id", SessionManager.getGroupId());
		    }
		    if (mapVo.get("hos_id") == null) {
		      mapVo.put("hos_id", SessionManager.getHosId());
		    }
		    if (mapVo.get("copy_code") == null) {
		      mapVo.put("copy_code", SessionManager.getCopyCode());
		    }
		    String res=assConferenceService.deleteConferencePerson(mapVo);
		return res;
	}
	
	/**
	 * 文件上传页面跳转
	 * @return
	 */
	@RequestMapping(value="/hrp/ass/assconference/upLodePage",method=RequestMethod.GET)
	public String upLodePage(@RequestParam Map<String, Object> mapVo,Model model){
		model.addAttribute("con_id", mapVo.get("con_id"));
		return "hrp/ass/assconference/upLode";
	}
	
	/**
	 * 文件查看页面跳转
	 * @return
	 */
	@RequestMapping(value="/hrp/ass/assconference/assConferenceFilePage",method=RequestMethod.GET)
	public String assConferenceFile(@RequestParam Map<String, Object> mapVo,Model model){
		model.addAttribute("con_id", mapVo.get("con_id"));
		return "hrp/ass/assconference/assConferenceFile";
	}
	  
 	//上传文件
 	@RequestMapping(value="/hrp/ass/assconference/upLodeFile", method = RequestMethod.POST)  
 	 public String upLodeFile(Plupload plupload,HttpServletRequest request,
 	    		HttpServletResponse response,@RequestParam Map<String, Object> mapVo) throws IOException, DataAccessException, ParseException { 
 		String result = "";
 		if(mapVo.get("group_id") == null){
 			mapVo.put("group_id", SessionManager.getGroupId());
 		}
 		if(mapVo.get("hos_id") == null){
 			mapVo.put("hos_id", SessionManager.getHosId());
 		}
 		if(mapVo.get("copy_code") == null){
 			mapVo.put("copy_code", SessionManager.getCopyCode());
 		}
 		
 		
 		String basePath = "assFile/conferenceFile/"+mapVo.get("con_id") +"/";
 		
 		String fileName = plupload.getName();// 文件原名称
 		
 		// 文件保存目录url
 		String saveUrl = request.getContextPath() + "/" + basePath;

 		String url = saveUrl + fileName;

 			List<File> fileList = UploadUtil.upload(plupload, basePath,request, response);
 			
 			result = "{\"file_path\":\""+url.replaceAll("\\\\","\\\\\\\\")+"\",\"state\":\"true\"}";

 			//System.out.println(url);
 			///更新文件管理表
 			mapVo.put("file_path", url);	
 			mapVo.put("file_name", fileName);	
 			//查看文件是否已经存在
 			List<AssTendFile> list=assConferenceService.queryAssTendFileExit(mapVo);
 		if(list.size()==0){
 			assConferenceService.addAssTendFile(mapVo);
 			}
 		response.getWriter().print(result);
 	    return null; 
 	 }
	/**
	 * 文件查询列表
	 * @param mapVo
	 * @param model
	 * @return
	 */
 	@RequestMapping(value="/hrp/ass/assconference/queryAssTendFile",method=RequestMethod.POST)
 	@ResponseBody
 	public Map<String, Object> queryAssTendFile(@RequestParam Map<String, Object> mapVo,Model model){
 		
 		if(mapVo.get("group_id") == null){
 			mapVo.put("group_id", SessionManager.getGroupId());
 		}
 		if(mapVo.get("hos_id") == null){
 			mapVo.put("hos_id", SessionManager.getHosId());
 		}
 		if(mapVo.get("copy_code") == null){
 			mapVo.put("copy_code", SessionManager.getCopyCode());
 		}
 		
 		String file=assConferenceService.queryAssTendFile(getPage(mapVo));
 		return JSONObject.parseObject(file);
 	}
 	/**
 	 * 删除文件
 	 * @param mapVo
 	 * @param model
 	 * @return
 	 */
 	@RequestMapping(value="/hrp/ass/assconference/deleteTendFile",method=RequestMethod.POST)
 	@ResponseBody
 	public String deleteTendFile(@RequestParam Map<String, Object> mapVo,Model model){
 		
 		if(mapVo.get("group_id") == null){
 			mapVo.put("group_id", SessionManager.getGroupId());
 		}
 		if(mapVo.get("hos_id") == null){
 			mapVo.put("hos_id", SessionManager.getHosId());
 		}
 		if(mapVo.get("copy_code") == null){
 			mapVo.put("copy_code", SessionManager.getCopyCode());
 		}
 		String basePath =  "assFile/conferenceFile/"+mapVo.get("con_id") +"/";
 		String file_name=mapVo.get("file_name").toString();
 		FileUtil.deleteFile( LoadSystemInfo.getProjectUrl()+basePath+file_name);
 		String res=assConferenceService.deleteTendFile(mapVo);
 		return res;
 	}
}
