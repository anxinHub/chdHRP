
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.mat.controller.info.basic;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.chd.hrp.mat.service.info.basic.MatFacAttrService;
import com.chd.hrp.sys.entity.Rules;
import com.chd.hrp.sys.service.FacService;
import com.chd.hrp.sys.service.RulesService;

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
public class MatFacAttrController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatFacAttrController.class);
	
	//引入Service服务
	@Resource(name = "matFacAttrService")
	private final MatFacAttrService matFacAttrService = null;
	
	@Resource(name = "rulesService")
	private final RulesService rulesService = null;
	
	@Resource(name = "facService")
	private final FacService facService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 * 
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matfacattr/matFacAttrMainPage", method = RequestMethod.GET)
	public String matFacAttrMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/matfacattr/matFacAttrMain";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matfacattr/matFacAttrAddPage", method = RequestMethod.GET)
	public String matFacAttrAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		//获取编码规则
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("mod_code", "00");
		map.put("proj_code", "HOS_FAC");
		Rules rules = rulesService.queryRulesByCode(map);
		mode.addAttribute("is_auto", rules.getIs_auto());
		mode.addAttribute("type_code",mapVo.get("type_code"));
		return "hrp/mat/info/basic/matfacattr/matFacAttrAdd";
	}

	/**
	 * @Description 
	 * 添加数据 050115 生产厂商附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matfacattr/addMatFacAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatFacAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String matFacAttrJson = "";
		try{
			matFacAttrJson = matFacAttrService.addMatFacAttr(mapVo);
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
	@RequestMapping(value = "/hrp/mat/info/basic/matfacattr/matFacAttrUpdatePage", method = RequestMethod.GET)
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
    
		assFacAttr = matFacAttrService.queryMatFacAttrByCode(mapVo);
	    mode.addAttribute("group_id", assFacAttr.getGroup_id());
		mode.addAttribute("hos_id", assFacAttr.getHos_id());
		mode.addAttribute("fac_id", assFacAttr.getFac_id());
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
		mode.addAttribute("is_mat", assFacAttr.getIs_mat());
		mode.addAttribute("is_ass", assFacAttr.getIs_ass());
		mode.addAttribute("is_med", assFacAttr.getIs_med());
		mode.addAttribute("is_sup", assFacAttr.getIs_sup());
		
		return "hrp/mat/info/basic/matfacattr/matFacAttrUpdate";
	}

	/**
	 * @Description 
	 * 查询供应商银行信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matfacattr/queryHosFacBank", method = RequestMethod.POST)
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
		
		String hosFacBank = matFacAttrService.queryHosFacBank(mapVo);

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
	@RequestMapping(value = "/hrp/mat/info/basic/matfacattr/addHosFacBank", method = RequestMethod.POST)
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
       
		String hosFacBankJson = matFacAttrService.addHosFacBank(mapVo);

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
	@RequestMapping(value = "/hrp/mat/info/basic/matfacattr/updateMatFacAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatFacAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		/*if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}*/
		if(mapVo.get("acct_year") == null){
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		String matFacAttrJson ="";
		try{
			matFacAttrJson = matFacAttrService.updateMatFacAttr(mapVo);
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
	@RequestMapping(value = "/hrp/mat/info/basic/matfacattr/init", method = RequestMethod.POST)
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
		String matFacAttrJson = matFacAttrService.init(mapVo);
		
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
	@RequestMapping(value = "/hrp/mat/info/basic/matfacattr/deleteMatFacAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatFacAttr(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
		
			listVo.add(mapVo);
		}
		String matFacAttrJson = "";
		try{
			matFacAttrJson = matFacAttrService.deleteBatchMatFacAttr(listVo);
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
	@RequestMapping(value = "/hrp/mat/info/basic/matfacattr/queryMatFacAttr", method = RequestMethod.POST)
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
		
		//不同系统过滤筛选出相应系统的生产商
		String modCode = SessionManager.getModCode();//获取系统编码
		//物流管理系统modCode=04
		//耐用品管理modCode=0413
		if(modCode!=null && modCode.startsWith("04")) {
			mapVo.put("is_mat", "1");
		}
		//固定资产管理系统modCode=05
		if(modCode!=null && modCode.startsWith("05")) {
			mapVo.put("is_ass", "1");
		}
		//药品管理系统modCode=08
		if(modCode!=null && modCode.startsWith("08")) {
			mapVo.put("is_med", "1");
		}		
		
		String matFacAttr = matFacAttrService.queryMatFacAttr(getPage(mapVo));
		
		return JSONObject.parseObject(matFacAttr);
	}
	
	
	@RequestMapping(value = "/hrp/mat/info/basic/matfacattr/queryHosFacTypeByMenu", method = RequestMethod.POST)
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
		
		String assSupAttr = matFacAttrService.queryHosFacTypeByMenu(getPage(mapVo));

		return JSONObject.parseObject(assSupAttr);
	}
  /**
	 * @Description 
	 * 导入跳转页面 050115 生产厂商附属表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matfacattr/matFacAttrImportPage", method = RequestMethod.GET)
	public String matFacAttrImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/matfacattr/matFacAttrImport";
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
	@RequestMapping(value="hrp/mat/info/basic/matfacattr/downTemplate", method = RequestMethod.GET)  
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
	 @RequestMapping(value="/hrp/mat/info/basic/matfacattr/readMatFacAttrFiles",method = RequestMethod.POST)  
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
					int count = matFacAttrService.queryFacExists(mapVo);
					if(count > 0){
						err_sb.append("生产厂商名称已被占用;");
					}
	    		
				} else {
					
					err_sb.append("生产厂商名称为空 ;");
					
				}

				if (StringTool.isNotBlank(temp[1])) {
					
					assFacAttr.setType_code(temp[1]);
								
		    		mapVo.put("type_code", temp[1]);
		    		
		    		int count = matFacAttrService.queryFacTypeExist(mapVo);
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
		    		
		    		int count = matFacAttrService.queryModExist(mapVo);
		    		if(count == 0){
		    			err_sb.append("所属系统模块编码不存在;");
		    		}
	    		
				} else {
					
					err_sb.append("所属系统模块编码为空;");
					
				}
				if (StringTool.isNotBlank(temp[4])) {
					
					assFacAttr.setIs_mat(Integer.valueOf(String.valueOf(temp[4])));
								
		    		mapVo.put("is_mat", temp[4]);
	    		
				} else {
				
					err_sb.append("所属模块非物流管理;");
					
				}
				if (StringTool.isNotBlank(temp[5])) {
					
					assFacAttr.setIs_ass(Integer.valueOf(String.valueOf(temp[5])));
								
		    		mapVo.put("is_ass", temp[5]);
		    	
	    		
				} else {
					
					err_sb.append("所属模块非药品管理;");
					
				}
				if (StringTool.isNotBlank(temp[6])) {
					
					assFacAttr.setIs_med(Integer.valueOf(String.valueOf(temp[6])));
								
					mapVo.put("is_med", temp[6]);
					
				
				} else {
					
					err_sb.append("所属模块非固定资产;");
					
				}
				if (StringTool.isNotBlank(temp[7])) {
					
					assFacAttr.setIs_sup(Integer.valueOf(String.valueOf(temp[7])));
								
					mapVo.put("is_sup", temp[7]);
				
				} else {
					
					err_sb.append("所属模块非供应商平台;");
					
				}
				
				mapVo.put("fac_code", "自动生成");
				mapVo.put("fac_sort", "系统生成");
				
				/*AssFacAttr data_exc_extis = matFacAttrService.queryMatFacAttrByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}*/
				if (err_sb.toString().length() > 0) {
					
					assFacAttr.setError_type(err_sb.toString());
					
					list_err.add(assFacAttr);
					
				} else {
			  
					String dataJson = matFacAttrService.addMatFacAttr(mapVo);
					
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
	@RequestMapping(value = "/hrp/mat/info/basic/matfacattr/addBatchMatFacAttr", method = RequestMethod.POST)
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
					Long fac_id = matFacAttrService.queryFacIdByCode(mapVo);
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
			
				AssFacAttr data_exc_extis = matFacAttrService.queryMatFacAttrByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assFacAttr.setError_type(err_sb.toString());
					
					list_err.add(assFacAttr);
					
				} else {
			  
					String dataJson = matFacAttrService.addMatFacAttr(mapVo);
					
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

