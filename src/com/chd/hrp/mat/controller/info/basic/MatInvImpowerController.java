/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.info.basic;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.mat.entity.MatInvCertType;
import com.chd.hrp.mat.entity.MatInvImpower;
import com.chd.hrp.mat.entity.MatVenCertDetail;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.info.basic.MatInvCertTypeService;
import com.chd.hrp.mat.serviceImpl.info.basic.MatInvCertTypeServiceImpl;
import com.chd.hrp.mat.serviceImpl.info.basic.MatInvImpowerServiceImpl;
/**
 * 
 * @Description:
 * 04601 材料证件类型字典
 * @Table:
 * MAT_INV_CERT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatInvImpowerController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatInvImpowerController.class);
	
	//引入Service服务
	@Resource(name = "matInvImpowerService")
	private final MatInvImpowerServiceImpl matInvImpowerService = null;
	
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/impower/matInvImpowerMainPage", method = RequestMethod.GET)
	public String matInvImpowerMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/impower/matInvImpowerMain"; 

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/impower/matInvImpowerAddPage", method = RequestMethod.GET)
	public String matInvImpowerAddPage(Model mode) throws Exception {
		Map<String,Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("copu_code", SessionManager.getCopyCode());
		mapVo.put("hos_id", SessionManager.getHosId());
		Long impower_id = matInvImpowerService.queryMatInvImpowerNextId(mapVo);
		mode.addAttribute("impower_id",impower_id);
		return "hrp/mat/info/basic/impower/matInvImpowerAdd";

	}

	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/impower/addMatInvImpower", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInvImpower(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
	      mapVo.put("copy_code", SessionManager.getCopyCode());
	        
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		mapVo.put("impower_start_date", DateUtil.stringToDate(format.format(new Date()), "yyyy-MM-dd"));
		mapVo.put("impower_end_date", DateUtil.stringToDate(format.format(new Date()), "yyyy-MM-dd"));
		String matInvImpowerJson = matInvImpowerService.addMatInvImpower(mapVo);

		return JSONObject.parseObject(matInvImpowerJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/impower/matInvImpowerUpdatePage", method = RequestMethod.GET)
	public String matInvImpowerUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	  if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
           mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
    MatInvImpower matInvImpower = new MatInvImpower();
    
		matInvImpower = matInvImpowerService.queryMatInvImpowerByCode(mapVo);
		
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
		String sDate = a.format(matInvImpower.getImpower_start_date());
		String eDate = a.format(matInvImpower.getImpower_end_date());
		
		mode.addAttribute("group_id", matInvImpower.getGroup_id());
		mode.addAttribute("hos_id", matInvImpower.getHos_id());
		mode.addAttribute("copy_code", matInvImpower.getCopy_code());
		mode.addAttribute("impower_id", matInvImpower.getImpower_id());
		mode.addAttribute("impower_code", matInvImpower.getImpower_code());
		mode.addAttribute("fac_id", matInvImpower.getFac_id());
		mode.addAttribute("fac_code", matInvImpower.getFac_code());
		mode.addAttribute("fac_name", matInvImpower.getFac_name());
		mode.addAttribute("sup_id", matInvImpower.getSup_id());
		mode.addAttribute("sup_code", matInvImpower.getSup_code());
		mode.addAttribute("sup_name", matInvImpower.getSup_name());
		mode.addAttribute("sup_id_b", matInvImpower.getSup_id_b());
		mode.addAttribute("impower_start_date", sDate);
		mode.addAttribute("impower_end_date", eDate);
		mode.addAttribute("impower_person", matInvImpower.getImpower_person());
		mode.addAttribute("impower_tel", matInvImpower.getImpower_tel());
		mode.addAttribute("impower_mobile", matInvImpower.getImpower_mobile());
		mode.addAttribute("file_address", matInvImpower.getFile_address());
		mode.addAttribute("file_path", matInvImpower.getFile_path());
		mode.addAttribute("is_stop", matInvImpower.getIs_stop());

		
		return "hrp/mat/info/basic/impower/matInvImpowerUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/impower/updateMatInvImpower", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatInvImpower(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
				
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
	    mapVo.put("copy_code", SessionManager.getCopyCode());
	        
	    }
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		mapVo.put("impower_start_date", DateUtil.stringToDate(format.format(new Date()), "yyyy-MM-dd"));
		mapVo.put("impower_end_date", DateUtil.stringToDate(format.format(new Date()), "yyyy-MM-dd"));
		
		String matInvImpowerJson = matInvImpowerService.updateMatInvImpower(mapVo);
		
		return JSONObject.parseObject(matInvImpowerJson);
	}
	
	//上传页面跳转
	@RequestMapping(value = "/hrp/mat/info/basic/impower/upLodePage", method = RequestMethod.GET)
	public String upLodePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
			mode.addAttribute("impower_id", mapVo.get("impower_id"));
		
		return "hrp/mat/info/basic/impower/upLode";
	}
	
	//上传文件
	@RequestMapping(value="/hrp/mat/info/basic/impower/upLodeFile", method = RequestMethod.POST)  
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
			List<File> fileList = UploadUtil.upload(plupload, "matFile\\venFile",request, response);
			result = "{\"file_path\":\""+fileList.get(0).getPath().replaceAll("\\\\", "/")+"\",\"state\":\"true\"}";	
		response.getWriter().print(result);
	    return null; 
	 }
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/impower/deleteMatInvImpower", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatInvImpower(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				//表的主键
				mapVo.put("group_id", id.split("@")[0])   ;
				mapVo.put("hos_id", id.split("@")[1])   ;
				mapVo.put("impower_id", id.split("@")[2]);
				mapVo.put("impower_code", id.split("@")[3]);
				
	            listVo.add(mapVo);
	      
	    }
			
		
		String matInvImpowerJson = matInvImpowerService.deleteBatchMatInvImpower(listVo);
		
		return JSONObject.parseObject(matInvImpowerJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/impower/queryMatInvImpower", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvImpower(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
	      mapVo.put("copy_code", SessionManager.getCopyCode());
	        
	    }
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		mapVo.put("impower_start_date", DateUtil.stringToDate(format.format(new Date()), "yyyy-MM-dd"));
//		mapVo.put("impower_end_date", DateUtil.stringToDate(format.format(new Date()), "yyyy-MM-dd"));
		if(mapVo.get("impower_start_date") != null && !"".equals(mapVo.get("impower_start_date"))){
			mapVo.put("impower_start_date", DateUtil.stringToDate(mapVo.get("impower_start_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("impower_end_date") != null && !"".equals(mapVo.get("impower_end_date"))){
			mapVo.put("impower_end_date", DateUtil.stringToDate(mapVo.get("impower_end_date").toString(), "yyyy-MM-dd"));
		}
		String matInvImpower = matInvImpowerService.queryMatInvImpower(getPage(mapVo));

		return JSONObject.parseObject(matInvImpower);
		
	}
	
	
	// 获取内置数据
		@RequestMapping(value = "/hrp/mat/info/basic/impower/queryMatImpowerInvList", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatImpowerInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
				
			String hrpMatSelect = matInvImpowerService.queryMatImpowerInvList(getPage(mapVo));
			return hrpMatSelect;
		}
		
		/**
		 * 获取对应关系明细数据
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/mat/info/basic/impower/queryMatInvImpowerDetail", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatInvImpowerDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String hrpMatSelect = matInvImpowerService.queryMatInvImpowerDetail(getPage(mapVo));
			
			return hrpMatSelect;
		}
		
		
		/**
		 * @Description 
		 * 选择材料页面跳转 
		 * @param  mode
		 * @return String
		 * @throws Exception
		*/
		@RequestMapping(value = "/hrp/mat/info/basic/impower/matInvImpowerChoiceInvPage", method = RequestMethod.GET)
		public String matInvImpowerChoiceInvPage(Model mode) throws Exception {

			return "hrp/mat/info/basic/impower/matInvImpowerChoiceInv";

		}
		
		// 材料选择页面材料查询
		@RequestMapping(value = "/hrp/mat/info/basic/impower/queryMatImpowerInvListObject", method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> queryMatImpowerInvListObject(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
					
			String matImpowerInvList = matInvImpowerService.queryMatImpowerInvList(getPage(mapVo));
			
			return JSONObject.parseObject(matImpowerInvList);
		}
		
		// 查询 选择的材料
		@RequestMapping(value = "/hrp/mat/info/basic/impower/queryMatImpowerInvChoiceInvList", method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> queryMatImpowerInvChoiceInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
					
			String invDataList = matInvImpowerService.queryMatImpowerInvChoiceInvList(mapVo);
			
			return JSONObject.parseObject(invDataList);
		}
    
}

