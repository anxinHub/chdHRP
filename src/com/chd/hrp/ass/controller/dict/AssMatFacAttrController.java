
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.controller.dict;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.dict.AssFacAttr;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssMatFacAttrService;
import com.chd.hrp.sys.service.FacService;

/**
 * 
 * @Description:
 * 050115 生产厂商附属表
 * @Table:
 * ASS_FAC_ATTR
 * @Author: linuxxu
 * @email:  linuxxu@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class AssMatFacAttrController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssMatFacAttrController.class);
	
	//引入Service服务
	@Resource(name = "assMatFacAttrService")
	private final AssMatFacAttrService assMatFacAttrService = null;
	
	@Resource(name = "facService")
	private final FacService facService = null;
	
	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 * 
	*/
	@RequestMapping(value = "hrp/ass/assfinadict/assMatFacAttrMainPage", method = RequestMethod.GET)
	public String matFacAttrMainPage(Model mode) throws Exception {

		return "hrp/ass/assmatFacAttrdict/matFacAttrMain";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/ass/assfinadict/assAddMatFacAttr", method = RequestMethod.GET)
	public String matFacAttrAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		Map<String, Map<String, Object>> mv = (Map<String, Map<String, Object>>) SessionManager.queryHosRulesMap();
		mode.addAttribute("is_auto", String.valueOf(mv.get("HOS_FAC").get("is_auto")));
		mode.addAttribute("type_code",mapVo.get("type_code"));
		return "hrp/ass/assmatFacAttrdict/matFacAttrAdd";
	}

	/**
	 * @Description 
	 * 添加数据 050115 生产厂商附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/ass/assfinadict/assAddMatFacAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatFacAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		int is_mat;
		int is_ass;
		int is_med;
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null){
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		String type_code_no = mapVo.get("type_code") == null ? "" : mapVo.get("type_code").toString();
//		mapVo.put("type_code", type_code_no.split("@")[0]);
		mapVo.put("is_mat", 0);
		mapVo.put("is_ass", 1);
		mapVo.put("is_med", 0);
		mapVo.put("is_sup", 0);
		mapVo.put("is_stop", mapVo.get("is_stop"));
		String matFacAttrJson = "";
		try{
			matFacAttrJson = assMatFacAttrService.addMatFacAttr(mapVo);
		}catch(Exception e ){
			matFacAttrJson = e.getMessage();
		}
		

		return JSONObject.parseObject(matFacAttrJson);
	}
	
	/**
	 * @Description 
	 * 更新跳转页面 050115 生产厂商附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/ass/assfinadict/assMatFacAttrUpdatePage", method = RequestMethod.GET)
	public String matFacAttrUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acct_year") == null){
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		AssFacAttr assFacAttr = new AssFacAttr();
    
		assFacAttr = assMatFacAttrService.queryMatFacAttrByCode(mapVo);
	    mode.addAttribute("group_id", assFacAttr.getGroup_id());
		mode.addAttribute("hos_id", assFacAttr.getHos_id());
		mode.addAttribute("fac_id", assFacAttr.getFac_id());
		mode.addAttribute("fac_no",assFacAttr.getFac_no());
		mode.addAttribute("fac_code", assFacAttr.getFac_code());
		mode.addAttribute("fac_name", assFacAttr.getFac_name());
		mode.addAttribute("type_code", assFacAttr.getType_code());
		mode.addAttribute("type_name", assFacAttr.getType_name());
		mode.addAttribute("mod_code", assFacAttr.getMod_code());
		mode.addAttribute("spell_code", assFacAttr.getSpell_code());
		mode.addAttribute("wbx_code", assFacAttr.getWbx_code());
		mode.addAttribute("fac_sort", assFacAttr.getFac_sort());
		mode.addAttribute("legal", assFacAttr.getLegal());
		mode.addAttribute("regis_no", assFacAttr.getRegis_no());
		mode.addAttribute("phone", assFacAttr.getPhone());
		mode.addAttribute("mobile", assFacAttr.getMobile());
		mode.addAttribute("contact", assFacAttr.getContact());
		mode.addAttribute("fax", assFacAttr.getFax());
		mode.addAttribute("email", assFacAttr.getEmail());
		mode.addAttribute("region", assFacAttr.getRegion());
		mode.addAttribute("zip_code", assFacAttr.getZip_code());
		mode.addAttribute("address", assFacAttr.getAddress());
		mode.addAttribute("note", assFacAttr.getNote());
		mode.addAttribute("is_stop", assFacAttr.getIs_stop());
		
		return "hrp/ass/assmatFacAttrdict/matFacAttrUpdate";
	}

	/**
	 * @Description 
	 * 查询供应商银行信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/ass/assfinadict/assQueryHosFacBank", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosFacBank(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		String hosFacBank = assMatFacAttrService.queryHosFacBank(mapVo);

		return JSONObject.parseObject(hosFacBank);
	}
	
	/**
	 * @Description 
	 * 添加生产厂商与银行账户关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/ass/assfinadict/assAddHosFacBank", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHosFacBank(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
       
		String hosFacBankJson = assMatFacAttrService.addHosFacBank(mapVo);

		return JSONObject.parseObject(hosFacBankJson);
	}
		
	/**
	 * @Description 
	 * 更新数据 050115 生产厂商附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/ass/assfinadict/assUpdateMatFacAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatFacAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		int is_mat;
		int is_ass;
		int is_med;
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acct_year") == null){
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		mapVo.put("is_mat", 0);
		mapVo.put("is_ass", 1);
		mapVo.put("is_med", 0);
		mapVo.put("is_sup", 0);
		mapVo.put("is_stop", mapVo.get("is_stop"));
		String matFacAttrJson ="";
		try{
			matFacAttrJson = assMatFacAttrService.updateMatFacAttr(mapVo);
		}catch(Exception e ){
			matFacAttrJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matFacAttrJson);
	}
	
	/**
	 * @Description 
	 * 生成数据 050115 生产厂商附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/ass/assfinadict/assInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> init(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acct_year") == null){
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		String matFacAttrJson = assMatFacAttrService.init(mapVo);
		
		return JSONObject.parseObject(matFacAttrJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 050115 生产厂商附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/ass/assfinadict/assDeleteMatFacAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatFacAttr(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String str = "";
		boolean falg = true;
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("fac_id", ids[2]);
			mapVo.put("fac_code", ids[3]);
			mapVo.put("mod_code", ids[4]);
			
			str = str + assBaseService.isExistsDataByTable("ASS_FAC_ATTR", id.split("@")[2])== null ? ""
					: assBaseService.isExistsDataByTable("ASS_FAC_ATTR", id.split("@")[2]);
			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}
		
			listVo.add(mapVo);
		}
		if (!falg) {
			return JSONObject.parseObject("{\"error\":\"删除失败，选择的生产厂家被以下业务使用：【" + str.substring(0, str.length() - 1)
			+ "】。\",\"state\":\"false\"}");
		}
		String matFacAttrJson = "";
		try{
			matFacAttrJson = assMatFacAttrService.deleteBatchMatFacAttr(listVo);
		}catch(Exception e){
			matFacAttrJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matFacAttrJson);
	}
	
	/**
	 * @Description 
	 * 查询数据 050115 生产厂商附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/ass/assfinadict/assQueryMatFacAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatFacAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acct_year") == null){
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		mapVo.put("is_ass", "1");   
		String matFacAttr = assMatFacAttrService.queryMatFacAttr(getPage(mapVo));
		
		return JSONObject.parseObject(matFacAttr);
	}
	
	
	@RequestMapping(value = "hrp/ass/assfinadict/assQueryHosFacTypeByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosFacTypeByMenu(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		String assSupAttr = assMatFacAttrService.queryHosFacTypeByMenu(getPage(mapVo));

		return JSONObject.parseObject(assSupAttr);
	}
  /**
	 * @Description 
	 * 导入跳转页面 050115 生产厂商附属表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/ass/assfinadict/assMatFacAttrImportPage", method = RequestMethod.GET)
	public String matFacAttrImportPage(Model mode) throws Exception {

		return "hrp/ass/assmatFacAttrdict/matFacAttrImport";
	}
	/**
	 * @Description 
	 * 下载导入模版 050115 生产厂商附属表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/ass/assfinadict/assDownTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","050115 生产厂商模版.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 050115 生产厂商附属表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/ass/assfinadict/assReadMatFacAttrFiles",method = RequestMethod.POST)  
    public String readMatFacAttrFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssFacAttr> list_err = new ArrayList<AssFacAttr>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssFacAttr assFacAttr = new AssFacAttr();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					if(temp[0].equals(error[0]) ){
						err_sb.append("第"+i+"行数据与第 "+j+"行数据生产厂商名称重复;");
					}
						
				}
				if (StringTool.isNotBlank(temp[0])) {
					
					mapVo.put("fac_name", temp[0]);
					assFacAttr.setFac_name(String.valueOf(temp[0]));
					//根据 生产厂商编码 查询 生产厂ID 
					int count = assMatFacAttrService.queryFacExists(mapVo);
					if(count > 0){
						err_sb.append("生产厂商名称已被占用;");
					}
	    		
				} else {
					
					err_sb.append("生产厂商名称为空 ;");
					
				}

				if (StringTool.isNotBlank(temp[1])) {
					
					assFacAttr.setType_code(temp[1]);
								
		    		mapVo.put("type_code", temp[1]);
		    		
		    		int count = assMatFacAttrService.queryFacTypeExist(mapVo);
		    		if(count == 0){
		    			err_sb.append("类型编码不存在;");
		    		}
	    		
				} else {
					
					err_sb.append("类型编码为空;");
					
				}
				if (StringTool.isNotBlank(temp[2])) {
					
					assFacAttr.setIs_stop(Integer.valueOf(String.valueOf(temp[2])));
								
		    		mapVo.put("is_stop", temp[2]);
	    		
				} else {
					
					err_sb.append("是否停用为空;");
					
				}
				if (StringTool.isNotBlank(temp[3])) {
					
					assFacAttr.setMod_code(temp[3]);
								
		    		mapVo.put("mod_code", temp[3]);
		    		
		    		int count = assMatFacAttrService.queryModExist(mapVo);
		    		if(count == 0){
		    			err_sb.append("所属系统模块编码不存在;");
		    		}
	    		
				} else {
					
					err_sb.append("所属系统模块编码为空;");
					
				}
				mapVo.put("fac_code", "自动生成");
				mapVo.put("fac_sort", "系统生成");
				/*AssFacAttr data_exc_extis = assmatFacAttrService.queryMatFacAttrByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}*/
				if (err_sb.toString().length() > 0) {
					
					assFacAttr.setError_type(err_sb.toString());
					
					list_err.add(assFacAttr);
					
				} else {
			  
					String dataJson = assMatFacAttrService.addMatFacAttr(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssFacAttr data_exc = new AssFacAttr();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /** 此方法   暂时不用
	 * @Description 
	 * 批量添加数据 050115 生产厂商附属表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "hrp/ass/assfinadict/assAddBatchMatFacAttr", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatFacAttr(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssFacAttr> list_err = new ArrayList<AssFacAttr>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Iterator it = json.iterator();
		
		try {
			
			while (it.hasNext()) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssFacAttr assFacAttr = new AssFacAttr();
				
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				if (StringTool.isNotBlank(jsonObj.get("fac_code"))) {
				
					mapVo.put("fac_code", String.valueOf(jsonObj.get("fac_code")));
					assFacAttr.setFac_code(String.valueOf(jsonObj.get("fac_code")));
					//根据 生产厂商编码 查询 生产厂ID 
					Long fac_id = assMatFacAttrService.queryFacIdByCode(mapVo);
					if(fac_id > 0){
						assFacAttr.setFac_id(fac_id);
						mapVo.put("fac_id", fac_id);
					}else{
						err_sb.append("生产厂商不存在;");
					}
	    		
				} else {
					
					err_sb.append("生产厂商编码为空 ;");
					
				}
				assFacAttr.setFac_id(Long.valueOf((String)jsonObj.get("fac_code")));
			
				AssFacAttr data_exc_extis = assMatFacAttrService.queryMatFacAttrByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assFacAttr.setError_type(err_sb.toString());
					
					list_err.add(assFacAttr);
					
				} else {
			  
					String dataJson = assMatFacAttrService.addMatFacAttr(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssFacAttr data_exc = new AssFacAttr();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
	
	
    
}

