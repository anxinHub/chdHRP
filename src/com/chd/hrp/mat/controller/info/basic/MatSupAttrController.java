package com.chd.hrp.mat.controller.info.basic;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.dict.AssSupAttr;
import com.chd.hrp.mat.service.info.basic.MatSupAttrService;
import com.chd.hrp.sys.entity.Rules;
import com.chd.hrp.sys.entity.Sup;
import com.chd.hrp.sys.service.RulesService;
import com.chd.hrp.sys.serviceImpl.SupServiceImpl;

/**
 * 
 * @Description:
 * 050113 供应商附属表
 * @Table:
 * ASS_Sup_ATTR 
 * @Author: linuxxu
 * @email:  linuxxu@s-chd.com
 * @Version: 1.0
 */
@Controller  
public class MatSupAttrController extends BaseController{

	//引入日志管理
	private static Logger logger = Logger.getLogger(MatSupAttrController.class);
	
	//引入Service服务
	@Resource(name = "matSupAttrService")
	private final MatSupAttrService matSupAttrService = null;
	
	@Resource(name = "rulesService")
	private final RulesService rulesService = null;
	
	@Resource(name = "supService")
	private final SupServiceImpl supService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/matSupAttrMainPage", method = RequestMethod.GET)
	public String matSupAttrMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/matsupattr/matSupAttrMain";
	}
	
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/matSupAttrAddPage", method = RequestMethod.GET)
	public String matSupAttrAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		//获取编码规则
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("mod_code", "00");
		map.put("proj_code", "HOS_SUP");
		Rules rules = rulesService.queryRulesByCode(map);
		mode.addAttribute("is_auto", rules.getIs_auto());
		mode.addAttribute("type_code",mapVo.get("type_code"));
		
		return "hrp/mat/info/basic/matsupattr/matSupAttrAdd";
	}
	
	/**
	 * @Description 
	 * 添加数据 050113 供应商附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/addMatSupAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatSupAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String matSupAttrJson= null;
		try{
			
			matSupAttrJson = matSupAttrService.addMatSupAttr(mapVo);
			
		}catch (Exception e) {
			matSupAttrJson = e.getMessage();
		}
		

		return JSONObject.parseObject(matSupAttrJson);
	}
	
	/**
	 * @Description 
	 * 更新跳转页面 050113 供应商附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/matSupAttrUpdatePage", method = RequestMethod.GET)
	public String matSupAttrUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		AssSupAttr assSupAttr = new AssSupAttr();
    
		assSupAttr = matSupAttrService.queryMatSupAttrByCode(mapVo);
		if(assSupAttr!=null){
			mode.addAttribute("group_id", assSupAttr.getGroup_id());
			mode.addAttribute("hos_id", assSupAttr.getHos_id());
			mode.addAttribute("sup_id", assSupAttr.getSup_id());
			mode.addAttribute("sup_code", assSupAttr.getSup_code());
			mode.addAttribute("sup_name", assSupAttr.getSup_id());
			mode.addAttribute("sort_code", assSupAttr.getSort_code());
			mode.addAttribute("type_code", assSupAttr.getType_code());
			mode.addAttribute("type_name", assSupAttr.getType_name());
			mode.addAttribute("note", assSupAttr.getNote());
			mode.addAttribute("mod_code", assSupAttr.getMod_code());
			mode.addAttribute("sup_name", assSupAttr.getSup_name());
			mode.addAttribute("spell_code", assSupAttr.getSpell_code());
			mode.addAttribute("wbx_code", assSupAttr.getWbx_code());
			mode.addAttribute("legal", assSupAttr.getLegal());
			mode.addAttribute("regis_no", assSupAttr.getRegis_no());
			mode.addAttribute("phone", assSupAttr.getPhone());
			mode.addAttribute("mobile", assSupAttr.getMobile());
			mode.addAttribute("contact", assSupAttr.getContact());
			mode.addAttribute("fax", assSupAttr.getFax());
			mode.addAttribute("email", assSupAttr.getEmail());
			mode.addAttribute("region", assSupAttr.getRegion());
			mode.addAttribute("zip_code", assSupAttr.getZip_code());
			mode.addAttribute("address", assSupAttr.getAddress());
			mode.addAttribute("note", assSupAttr.getNote());
			mode.addAttribute("sup_alias", assSupAttr.getSup_alias());
			mode.addAttribute("ven_trade", assSupAttr.getVen_trade());
			mode.addAttribute("prov", assSupAttr.getProv());
			mode.addAttribute("city", assSupAttr.getCity());
			mode.addAttribute("ven_dis_rate", assSupAttr.getVen_dis_rate());
			mode.addAttribute("ven_pay_con", assSupAttr.getVen_pay_con());
			mode.addAttribute("ven_person", assSupAttr.getVen_person());
			mode.addAttribute("ven_dir_address", assSupAttr.getVen_dir_address());
			mode.addAttribute("ven_cre_grade", assSupAttr.getVen_cre_grade());
			mode.addAttribute("end_date", DateUtil.dateToString(assSupAttr.getEnd_date(), "yyyy-MM-dd"));
			mode.addAttribute("is_count", assSupAttr.getIs_count());
			mode.addAttribute("dept_id", assSupAttr.getDept_id());
			mode.addAttribute("ven_cre_line", assSupAttr.getVen_cre_line());
			mode.addAttribute("bven_tax", assSupAttr.getBven_tax());
			mode.addAttribute("ven_dev_date", DateUtil.dateToString(assSupAttr.getVen_dev_date(), "yyyy-MM-dd"));
			mode.addAttribute("products", assSupAttr.getProducts());
			mode.addAttribute("is_stop", assSupAttr.getIs_stop());
			mode.addAttribute("business_charter", assSupAttr.getBusiness_charter());
			mode.addAttribute("frequency", assSupAttr.getFrequency());
			mode.addAttribute("is_mat", assSupAttr.getIs_mat());
			mode.addAttribute("is_ass", assSupAttr.getIs_ass());
			mode.addAttribute("is_med", assSupAttr.getIs_med());
			mode.addAttribute("is_sup", assSupAttr.getIs_sup());
			mode.addAttribute("is_delivery", assSupAttr.getIs_delivery());
			mode.addAttribute("pay_code", assSupAttr.getPay_code());
			mode.addAttribute("pay_name", assSupAttr.getPay_name());
		}else{
			mode.addAttribute("group_id", mapVo.get("group_id"));
			mode.addAttribute("hos_id", mapVo.get("hos_id"));
			mode.addAttribute("sup_id", mapVo.get("sup_id"));
		}
		mode.addAttribute("sup_code", mapVo.get("sup_code").toString());
		
		mode.addAttribute("sup_name", mapVo.get("sup_name").toString());
		
		return "hrp/mat/info/basic/matsupattr/matSupAttrUpdate";
	}

	/**
	 * @Description 
	 * 查询供应商银行信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/queryHosSupBank", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosSupBank(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String hosSupBank = matSupAttrService.queryHosSupBank(mapVo);

		return JSONObject.parseObject(hosSupBank);
	}
	
	/**
	 * @Description 
	 * 添加供应商与银行账户关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/addHosSupBank", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHosSupBank(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
       
		String hosSupBankJson = matSupAttrService.addHosSupBank(mapVo);

		return JSONObject.parseObject(hosSupBankJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 050113 供应商附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/updateMatSupAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatSupAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("ven_dev_date") != null && mapVo.get("ven_dev_date") != ""){
			mapVo.put("ven_dev_date", DateUtil.stringToDate(mapVo.get("ven_dev_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_date") != null && mapVo.get("end_date") != ""){
			mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date").toString(), "yyyy-MM-dd"));
		}
		String assSupAttrJson = null ;
		try{
			assSupAttrJson = matSupAttrService.updateMatSupAttr(mapVo);
		}catch(Exception e){
			assSupAttrJson = e.getMessage();
		}
		
		return JSONObject.parseObject(assSupAttrJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 050113 供应商附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/deleteMatSupAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatSupAttr(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("sup_id", ids[2]);
			mapVo.put("sup_code", ids[3]);
			mapVo.put("mod_code", ids[4]);
			listVo.add(mapVo);
	    }
		String assSupAttrJson = null;
		try{
			assSupAttrJson= matSupAttrService.deleteBatchMatSupAttr(listVo);
		}catch (Exception e){
			assSupAttrJson = e.getMessage();
		}
		
		return JSONObject.parseObject(assSupAttrJson);
	}
	
	/**
	 * @Description 
	 * 查询数据 050113 供应商附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/queryMatSupAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSupAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String assSupAttr = matSupAttrService.queryMatSupAttr(getPage(mapVo));

		return JSONObject.parseObject(assSupAttr);
	}
	
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/queryHosSupTypeByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosSupTypeByMenu(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String assSupAttr = matSupAttrService.queryHosSupTypeByMenu(getPage(mapVo));

		return JSONObject.parseObject(assSupAttr);
	}
	
	/**
	 * @Description 
	 * 导入跳转页面 050113 供应商附属表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/matSupAttrImportPage", method = RequestMethod.GET)
	public String matSupAttrImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/matsupattr/matSupAttrImport";
	}
	/**
	 * @Description 
	 * 下载导入模版 050113 供应商附属表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/mat/info/basic/matsupattr/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","050113 供应商模版.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 050113 供应商附属表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws Exception 
	*/
	 @RequestMapping(value="/hrp/mat/info/basic/matsupattr/readMatSupAttrFiles",method = RequestMethod.POST)  
    public String readMatSupAttrFiles(Plupload plupload,HttpServletRequest request,	HttpServletResponse response,Model mode) throws Exception { 
			 
		List<AssSupAttr> list_err = new ArrayList<AssSupAttr>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssSupAttr assSupAttr = new AssSupAttr();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					mapVo.put("group_id", SessionManager.getGroupId());
					mapVo.put("hos_id", SessionManager.getHosId());
					mapVo.put("copy_code", SessionManager.getCopyCode());
					
					if (StringTool.isNotBlank(temp[0])) {
						
						mapVo.put("sup_code", temp[0]);
						
						assSupAttr.setSup_code(String.valueOf(temp[0]));
		    		
					}else {
						
						err_sb.append("供应商编码为空 ;");
						
					}
					if (StringTool.isNotBlank(temp[1])) {
						
						mapVo.put("sup_name", temp[1]);
						
						assSupAttr.setSup_name(String.valueOf(temp[1]));
		    		
					}else {
						
						err_sb.append("供应商名称为空 ;");
						
					}
					//查询 供应商编码、供应商名称是否已存在
					List<Sup> listSup = supService.querySupById(mapVo);
					if(listSup.size()>0){
						for(Sup item : listSup){
							if(item.getSup_code().equals(mapVo.get("sup_code"))){
								if(item.getSup_name().equals(mapVo.get("sup_name"))){
									err_sb.append("供应商编码:"+String.valueOf(mapVo.get("sup_code"))+"、供应名称:"+String.valueOf(mapVo.get("sup_name"))+"重复 ;");
								}else{
									err_sb.append("供应商编码:"+String.valueOf(mapVo.get("sup_code"))+"重复 ;");
								}
							}else{
								if(item.getSup_name().equals(mapVo.get("sup_name"))){
									err_sb.append("供应名称:"+String.valueOf(mapVo.get("sup_name"))+"重复 ;");
								}
							}
						}
					}
					
					if (StringTool.isNotBlank(temp[2])) {
						
						assSupAttr.setType_code(temp[2]);
									
			    		mapVo.put("type_code", temp[2]);
			    		
			    		int count = matSupAttrService.querySupTypeExist(mapVo);
			    		if(count == 0){
			    			err_sb.append("类型编码不存在;");
			    		}
		    		
					} else {
						
						err_sb.append("类型编码为空;");
						
					}
					
					if (StringTool.isNotBlank(temp[3])) {
						
						assSupAttr.setMod_code(temp[3]);
									
			    		mapVo.put("mod_code", temp[3]);
			    		
			    		int count = matSupAttrService.queryModExist(mapVo);
			    		if(count == 0){
			    			err_sb.append("所属系统模块编码不存在;");
			    		}
		    		
					} else {
						
						err_sb.append("所属系统模块编码为空;");
						
					}
					if (StringTool.isNotBlank(temp[4])) {
						
						assSupAttr.setIs_count(Integer.valueOf(temp[4]));
									
			    		mapVo.put("is_count", temp[4]);
		    		
					} else {
						
						err_sb.append("是否区县为空;");
						
					}
					
					if (StringTool.isNotBlank(temp[5])) {
						
						mapVo.put("dept_code", temp[5]);
						assSupAttr.setDept_code(String.valueOf(temp[5]));
						Long dept_id = matSupAttrService.queryDeptIdByCode(mapVo);
						
						if(dept_id > 0){
							assSupAttr.setDept_id(dept_id);
							mapVo.put("dept_id", dept_id);
						}else{
							err_sb.append("分管科室不存在;");
						}
					}else{
						mapVo.put("dept_code", "");
						assSupAttr.setDept_code("");
					}
					if (StringTool.isNotBlank(temp[6])) {
						
						assSupAttr.setNote(String.valueOf(temp[6]));
									
			    		mapVo.put("note", temp[6]);
		    		
					} else {
						
						assSupAttr.setNote("");
						
			    		mapVo.put("note", "");
						
					}
					if (StringTool.isNotBlank(temp[7])) {
						
						assSupAttr.setIs_stop(Integer.valueOf(temp[7]));
									
			    		mapVo.put("is_stop", temp[7]);
		    		
					} else {
						
						err_sb.append("是否停用为空 ;");
						
					}
					
					mapVo.put("sort_code", "系统生成");
				/*AssSupAttr data_exc_extis = matSupAttrService.queryMatSupAttrByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在!");
					
				}*/
				if (err_sb.toString().length() > 0) {
					
					assSupAttr.setError_type(err_sb.toString());
					
					list_err.add(assSupAttr);
					
				} else {
					String dataJson = null;
					try{
						
						dataJson = matSupAttrService.addMatSupAttr(mapVo);
						
					}catch (Exception e) {
						dataJson = e.getMessage();
					}
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssSupAttr data_exc = new AssSupAttr();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description    废除  不用   （代码未作修改  不可用   ）
	 * 批量添加数据 050113 供应商附属表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/addBatchMatSupAttr", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatSupAttr(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssSupAttr> list_err = new ArrayList<AssSupAttr>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		
		
		Iterator it = json.iterator();
		
		try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			AssSupAttr assSupAttr = new AssSupAttr();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					mapVo.put("group_id", SessionManager.getGroupId());
					mapVo.put("hos_id", SessionManager.getHosId());
					mapVo.put("copy_code", SessionManager.getCopyCode());
					
					if (StringTool.isNotBlank(jsonObj.get("sup_code"))) {
						
						mapVo.put("sup_code", jsonObj.get("sup_code"));
						//根据 供应商编码 查询 供应商ID
						Long sup_id = matSupAttrService.querySupIdByCode(mapVo);
						if(sup_id > 0){
							assSupAttr.setSup_id(sup_id);
							mapVo.put("sup_id", sup_id);
						}else{
							err_sb.append("供应商不存在");
						}
		    		
					} else {
						
						err_sb.append("供应商编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("is_count"))) {
						
					assSupAttr.setIs_count(Integer.valueOf(String.valueOf(jsonObj.get("bank_name"))));
											
		    		mapVo.put("is_count", jsonObj.get("is_count"));
		    		
					} else {
						
						err_sb.append("是否区县为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("dept_code"))) {
						
					mapVo.put("dept_code", String.valueOf(jsonObj.get("dept_code")));
					
					assSupAttr.setDept_code(String.valueOf(jsonObj.get("dept_code")));
					
					Long dept_id = matSupAttrService.queryDeptIdByCode(mapVo);
					
					if(dept_id > 0){
						assSupAttr.setDept_id(dept_id);
						mapVo.put("dept_id", dept_id);
					}else{
						err_sb.append("分管科室不存在");
					}
		    		
					} else {
						
						err_sb.append("分管科室编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					assSupAttr.setLegal((String)jsonObj.get("is_stop"));
											
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		
					} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
				AssSupAttr data_exc_extis = matSupAttrService.queryMatSupAttrByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assSupAttr.setError_type(err_sb.toString());
					
					list_err.add(assSupAttr);
					
				} else {
			  
					String dataJson = matSupAttrService.addMatSupAttr(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssSupAttr data_exc = new AssSupAttr();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
	
	/**
	 * 供应商经营许可证信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/queryHosSupBusinessById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosSupBusinessById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		//mapVo.put("sup_id", SessionManager.getSupId());

		//mapVo.put("sup_no", SessionManager.getSupNo());
		String hosSupBusiness = matSupAttrService.queryHosSupBusinessById(mapVo);

		return JSONObject.parseObject(hosSupBusiness);
	}
	
	/**
	 * 供应商生产许可证信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/queryHosSupProductById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosSupProductById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		//mapVo.put("sup_id", SessionManager.getSupId());

		//mapVo.put("sup_no", SessionManager.getSupNo());
		String hosSupProduct = matSupAttrService.queryHosSupProductById(mapVo);

		return JSONObject.parseObject(hosSupProduct);
	}
	
	/**
	 * 银行账号保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/saveHosSupBank", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHosSupBank(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//mapVo.put("sup_id", SessionManager.getSupId());

		//mapVo.put("sup_no", SessionManager.getSupNo());
		String matSupAttrJson= null;
		try{
			matSupAttrJson = matSupAttrService.saveHosSupBank(mapVo);
		}catch (Exception e) {
			matSupAttrJson = e.getMessage();
		}
		return JSONObject.parseObject(matSupAttrJson);
	}
	
	/**
	 * 经营许可证保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/saveHosSupBusiness", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHosSupBusiness(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//mapVo.put("sup_id", SessionManager.getSupId());

		//mapVo.put("sup_no", SessionManager.getSupNo());
		String matSupAttrJson= null;
		try{
			matSupAttrJson = matSupAttrService.saveHosSupBusiness(mapVo);
		}catch (Exception e) {
			matSupAttrJson = e.getMessage();
		}
		return JSONObject.parseObject(matSupAttrJson);
	}
	/**
	 * 生产许可证信息保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/saveHosSupProduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHosSupProduct(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//mapVo.put("sup_id", SessionManager.getSupId());

		//mapVo.put("sup_no", SessionManager.getSupNo());
		String matSupAttrJson= null;
		try{
			matSupAttrJson = matSupAttrService.saveHosSupProduct(mapVo);
		}catch (Exception e) {
			matSupAttrJson = e.getMessage();
		}
		return JSONObject.parseObject(matSupAttrJson);
	}
	/**
	 * 删除
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/deleteHosSupBank", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHosSupBank(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("sup_id", ids[2]);
			mapVo.put("bank_no", ids[3]);
			listVo.add(mapVo);
	    }
		String assSupAttrJson = null;
		try{
			assSupAttrJson= matSupAttrService.deleteHosSupBankBatch(listVo);
		}catch (Exception e){
			assSupAttrJson = e.getMessage();
		}
		
		return JSONObject.parseObject(assSupAttrJson);
	}
	
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/deleteHosSupBusiness", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHosSupBusiness(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("sup_id", ids[2]);
			mapVo.put("business_id", ids[3]);
			mapVo.put("business_no", ids[4]);
			listVo.add(mapVo);
	    }
		String assSupAttrJson = null;
		try{
			assSupAttrJson= matSupAttrService.deleteHosSupBusinessBatch(listVo);
		}catch (Exception e){
			assSupAttrJson = e.getMessage();
		}
		
		return JSONObject.parseObject(assSupAttrJson);
	}
	
	@RequestMapping(value = "/hrp/mat/info/basic/matsupattr/deleteHosSupProduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHosSupProduct(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("sup_id", ids[2]);
			mapVo.put("product_id", ids[3]);
			mapVo.put("fac_id", ids[4]);
			listVo.add(mapVo);
	    }
		String assSupAttrJson = null;
		try{
			assSupAttrJson= matSupAttrService.deleteHosSupProductBatch(listVo);
		}catch (Exception e){
			assSupAttrJson = e.getMessage();
		}
		
		return JSONObject.parseObject(assSupAttrJson);
	}
}
