package com.chd.hrp.acc.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.service.AccTargetService;
import com.chd.hrp.sys.dao.UnitMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Unit;

@Controller
public class AccTargetController extends BaseController {

	
	private static Logger logger = Logger.getLogger(AccTargetController.class);

	@Resource(name = "accTargetService")
	private final AccTargetService accTargetService = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	@Resource(name = "unitMapper")
	private final UnitMapper unitMapper = null;

	/**
	 * 基本指标<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/acctarget/accTargetMainPage", method = RequestMethod.GET)
	public String accTargetMainPage(Model mode) throws Exception {

		return "hrp/acc/acctarget/accTargetMain";

	}

	/**
	 * 基本数字<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/acctargetdata/accTargetDataMainPage", method = RequestMethod.GET)
	public String accTargetDataMainPage(Model mode) throws Exception {
		
		mode.addAttribute("yearMonth", MyConfig.getCurAccYearMonth());
		
		return "hrp/acc/acctarget/accTargetDataMain";

	}

	/**
	 * 基本指标<BR>
	 *
	 */
	@RequestMapping(value = "/hrp/acc/acctarget/addAccTargetPage", method = RequestMethod.GET)
	public String addAccTargetPage(Model mode) throws Exception {

		return "hrp/acc/acctarget/accTargetAdd";

	}

	/**
	 * <BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/acc/acctarget/addAccTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccTarget(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get(
				"target_name").toString()));

		mapVo.put("wbx_code",
				StringTool.toWuBi(mapVo.get("target_name").toString()));

		String addAccTargetJson = accTargetService.addAccTarget(mapVo);

		return JSONObject.parseObject(addAccTargetJson);

	}
	/**
	 * 基本指标<BR>
	 *
	 */
	@RequestMapping(value = "/hrp/acc/acctarget/updateAccTargetPage", method = RequestMethod.GET)
	public String updateAccTargetPage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		Map<String, Object> targetMap = accTargetService
				.queryAccTargetByCode(mapVo);

		mode.addAttribute("group_id", targetMap.get("group_id"));
		mode.addAttribute("hos_id", targetMap.get("hos_id"));
		mode.addAttribute("copy_code", targetMap.get("copy_code"));
		mode.addAttribute("target_code", targetMap.get("target_code"));
		mode.addAttribute("target_name", targetMap.get("target_name"));
		mode.addAttribute("unit_code", targetMap.get("unit_code"));
		mode.addAttribute("unit_name", targetMap.get("unit_name"));
		mode.addAttribute("spell_code", targetMap.get("spell_code"));
		mode.addAttribute("wbx_code", targetMap.get("wbx_code"));
		mode.addAttribute("sort_code", targetMap.get("sort_code"));
		mode.addAttribute("is_stop", targetMap.get("is_stop"));
		mode.addAttribute("note", targetMap.get("note"));

		return "hrp/acc/acctarget/accTargetUpdate";

	}

	/**
	 * <BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/acc/acctarget/updateAccTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccTarget(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		mapVo.put("spell_code",StringTool.toPinyinShouZiMu(mapVo.get("target_name").toString()));

		mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("target_name").toString()));

		String AccTargetJson = accTargetService.updateAccTarget(mapVo);

		return JSONObject.parseObject(AccTargetJson);
	}
	
	
	/**
	 * <BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/acc/acctargetdata/saveBatchAccTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBatchAccTargetData(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String saveBatchAccTargetDataJson = accTargetService.saveBatchAccTargetData(mapVo);
			
		return JSONObject.parseObject(saveBatchAccTargetDataJson);
		
				
	}
	
	/**
	 * <BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/acc/acctargetdata/saveAccTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccTargetData(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());

		try{
			
			String addOrUpdateAccTargetDataJson = accTargetService.saveAccTargetData(mapVo);
			
			return JSONObject.parseObject(addOrUpdateAccTargetDataJson);
			
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
				

		
	}
	
	/**
	*<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/acctarget/deleteBatchAccTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBatchAccTarget(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("target_code", ids[3]);
           
            Map<String, Object> targetDataMap = accTargetService.queryAccTargetDataByCode(mapVo);
            
            if(targetDataMap!=null){
            	
            	flag = false;
				break;
            }
            
            listVo.add(mapVo);
        }
		
		if(flag == false){
			
			return JSONObject.parseObject("{\"error\":\"删除失败 该数据已被引用! \"}");
			
		}
		
		String accTargetJson = accTargetService.deleteBatchAccTarget(listVo);
	   
		return JSONObject.parseObject(accTargetJson);
			
	}
	
	/**
	*<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/acctargetdata/deleteBatchAccTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBatchAccTargetData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("acc_year", ids[3]);
            
            mapVo.put("acc_month", ids[4]);
            
            mapVo.put("target_code", ids[5]);
           
            listVo.add(mapVo);
        }
		
		String accTargetDataJson = accTargetService.deleteBatchAccTargetData(listVo);
	   
		return JSONObject.parseObject(accTargetDataJson);
			
	}
	
	/**
	 * <BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/acctarget/queryAccTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccTarget(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String accTarget = accTargetService.queryAccTarget(getPage(mapVo));

		return JSONObject.parseObject(accTarget);

	}

	/**
	 * <BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/acctargetdata/queryAccTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccTargetData(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String accTargetData = accTargetService.queryAccTargetData(getPage(mapVo));

		return JSONObject.parseObject(accTargetData);

	}
	
	//下载导入模版
		@RequestMapping(value="/hrp/acc/acctarget/downTemplate", method = RequestMethod.GET)  
		public String downTemplate(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,
					   HttpServletResponse response,Model mode) throws IOException { 
						
			List<List> list = new ArrayList();
						
			List<String> listdata = new ArrayList<String>();
						
			listdata.add("基本指标编码");
						
			listdata.add("基本指标名称");
						
			listdata.add("单位编码");
						
			listdata.add("单位名称");
						
			listdata.add("是否停用");
						
			listdata.add("备注");
						
			list.add(listdata);
						
			String ctxPath = request.getSession().getServletContext().getRealPath("/")
					+ "\\" + "excelTemplate\\"+"\\acc\\downTemplate\\";
						
			String downLoadPath = ctxPath + "基本指标.xls";
						
			ExcelWriter.exportExcel(new File(downLoadPath), list);
						
			printTemplate(request, response, "acc\\downTemplate", "基本指标.xls");

			return null; 
		}
		
		//导入页面跳转	
		@RequestMapping(value = "/hrp/acc/acctarget/accTargetImportPage", method = RequestMethod.GET)
		public String accTargetImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			return "hrp/acc/acctarget/accTargetImport";
		}
		
		/**
		*银行未达账<BR>
		*导入
		*/
		@RequestMapping(value="/hrp/acc/acctarget/readAccTargetFiles",method = RequestMethod.POST)  
	    public String readAccBankAccountFiles(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException {
			
			List<String[]> Utillist = UploadUtil.readFile(plupload, request, response);
			
			Map<String, Object> state = new HashMap<String, Object>();
			
	        state.put("0", "0");
			
	        state.put("1", "1");
			
	        state.put("否", "0");
			
	        state.put("是", "1");
	        
	        List<Map<String, Object>> list_err = new ArrayList<Map<String,Object>>();
	        
	        List<Map<String, Object>> add_list = new ArrayList<Map<String,Object>>();
			
	        try {
				
	               for(int i = 1;i<Utillist.size();i++){
	    			
	    			String temp[] = Utillist.get(i);// 行
	    			
	    			Map<String, Object> mapVo = new HashMap<String, Object>();
	    			
	    	        StringBuffer err_sb = new StringBuffer();
	    			
	    			Map<String, Object> errorMap = new HashMap<String, Object>();
	    			
	    			mapVo.put("group_id", SessionManager.getGroupId());
	    			
	    			mapVo.put("hos_id", SessionManager.getHosId());
	    			
	    			mapVo.put("copy_code", SessionManager.getCopyCode());
	    			
	    			if(ExcelReader.validExceLColunm(temp,0)){
	    				
	    				mapVo.put("target_code", temp[0]);
	    				
	    				errorMap.put("target_code",temp[0]);
	    				
	    			}else {
	    				
	    				err_sb.append("基本指标编码不存在");
	    			}
	    			
	                if(ExcelReader.validExceLColunm(temp,1)){
	    				
	    				mapVo.put("target_name", temp[1]);
	    				
	    				errorMap.put("target_name",temp[1]);
	    				
	    			}else {
	    				
	    				err_sb.append("基本指标名称不存在");
	    			}
	                
	                if(ExcelReader.validExceLColunm(temp,2)){
	    				
	                	Map<String, Object> unitMap = new HashMap<String, Object>();
	                	
	                	unitMap.put("group_id", mapVo.get("group_id"));
	                	
	                	unitMap.put("hos_id", mapVo.get("hos_id"));
	                	
	                	unitMap.put("unit_code", temp[2]);
	                	
	                	Unit unit =  unitMapper.queryUnitByCode(unitMap);
	                	
	                	if(unit == null){
	                		
	                		err_sb.append("单位编码不存在");
	                		
	                	}else {
	                		
	        				mapVo.put("unit_code", temp[2]);
	                	}
	                	
	                	errorMap.put("unit_code",temp[2]);
	    				
	    			}else {
	    				
	    				err_sb.append("单位编码不能为空");
	    			}
	                
	                if(ExcelReader.validExceLColunm(temp,3)){
	    				
	    				mapVo.put("unit_name", temp[3]);
	    				
	    				errorMap.put("unit_name",temp[3]);
	    				
	    			}else {
	    				
	    				err_sb.append("单位名称不能为空");
	    			}
	                
	                if(ExcelReader.validExceLColunm(temp,4)){
	                	
	    				mapVo.put("is_stop", state.get(temp[4]).toString());
	    				
	    				errorMap.put("is_stop",temp[4]);
	    				
	    			}else {
	    				
	    				err_sb.append("是否停用");
	    			}
	                
	                if(ExcelReader.validExceLColunm(temp,5)){
	    				
	    				mapVo.put("note", temp[5]);
	    				
	    				errorMap.put("note",temp[5]);
	    				
	    			}else {
	    				
	    				mapVo.put("note", "");
	    			}
	                
	                
	                Map<String, Object> ex_map = new HashMap<String, Object>();
	                
	                ex_map.put("group_id", mapVo.get("group_id"));
                	
	                ex_map.put("hos_id", mapVo.get("hos_id"));
                	
	                ex_map.put("copy_code", mapVo.get("copy_code"));
	                
	                ex_map.put("target_code", mapVo.get("target_code"));
	                
	                Map<String, Object > exe_map = accTargetService.queryAccTargetByCode(ex_map);
	                
	                if(exe_map!=null){
	                	
	                	err_sb.append("基本指标编码已经存在");
	                }
	                
	            	if (err_sb.toString().length() > 0) {

	            		errorMap.put("error_type", err_sb.toString());
	    				
	            		list_err.add(errorMap);
	            		
	            		break;

	    			} else {

	    				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("target_name").toString()));

	    				mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("target_name").toString()));
	    				
	    				Map<String, Object> utilMap = new HashMap<String, Object>();
	    				
	    				utilMap.put("group_id", mapVo.get("group_id"));
	    				
	    				utilMap.put("hos_id", mapVo.get("hos_id"));
	    				
	    				utilMap.put("copy_code",mapVo.get("copy_code"));
	    				
	    				utilMap.put("field_table","acc_target");
	    				
	    				utilMap.put("field_sort", "sort_code");
	    				
	    				int count = sysFunUtilMapper.querySysMaxSort(utilMap);
	    				
	    				mapVo.put("sort_code", count);
	    				
	    				add_list.add(mapVo);
	    			}
	    			
	    		}
	               
	           if(add_list.size() > 0 ){
	        	
	        	   accTargetService.addBatchAccTarget(add_list);
	           }
	        	
			} catch (Exception e) {
				// TODO: handle exception
				Map<String, Object> errorMap = new HashMap<String, Object>();
				
				errorMap.put("error_type", "导入系统出错");
				
				list_err.add(errorMap);
				
			}
			
	        response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
			
			return null;
		}
		
		

		/**
		 * <BR>
		 * 继承上月数据
		 */

		@RequestMapping(value = "/hrp/acc/acctargetdata/inheritAccTargetData", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> inheritAccTargetData(
				@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String saveBatchAccTargetDataJson = accTargetService.inheritAccTargetData(mapVo);
				
			return JSONObject.parseObject(saveBatchAccTargetDataJson);
			
					
		}
		
		
		//导入页面跳转	
		@RequestMapping(value = "/hrp/acc/acctargetdata/accTargetDataImportPage", method = RequestMethod.GET)
		public String accTargetDataImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/acctarget/accTargetDataImport";
		}
		
		
		
		/**
		*银行未达账<BR>
		*导入
		*/
		@RequestMapping(value="/hrp/acc/acctargetdata/readAccTargetDataFiles",method = RequestMethod.POST)  
	    public String readAccTargetDataFiles(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException {
			
			List<String[]> Utillist = UploadUtil.readFile(plupload, request, response);
			
			Map<String, Object> state = new HashMap<String, Object>();
			
	        state.put("0", "0");
			
	        state.put("1", "1");
			
	        state.put("否", "0");
			
	        state.put("是", "1");
	        
	        List<Map<String, Object>> list_err = new ArrayList<Map<String,Object>>();
	        
	        List<Map<String, Object>> add_list = new ArrayList<Map<String,Object>>();
			
	        try {
				
	               for(int i = 1;i<Utillist.size();i++){
	    			
	    			String temp[] = Utillist.get(i);// 行
	    			
	    			Map<String, Object> mapVo = new HashMap<String, Object>();
	    			
	    	        StringBuffer err_sb = new StringBuffer();
	    			
	    			Map<String, Object> errorMap = new HashMap<String, Object>();
	    			
	    			mapVo.put("group_id", SessionManager.getGroupId());
	    			
	    			mapVo.put("hos_id", SessionManager.getHosId());
	    			
	    			mapVo.put("copy_code", SessionManager.getCopyCode());
	    			
	    			if(ExcelReader.validExceLColunm(temp,0)){
	    				
	    				mapVo.put("year_month", temp[0]);
	    				
	    				errorMap.put("year_month",temp[0]);
	    				
	    			}else {
	    				
	    				err_sb.append("年月不存在");
	    			}
	    			
	                if(ExcelReader.validExceLColunm(temp,1)){
	    				
	    				mapVo.put("target_code", temp[1]);
	    				
	    				errorMap.put("target_code",temp[1]);
	    				
	    			}else {
	    				
	    				err_sb.append("基本指标编码不存在");
	    			}
	                
	                if(ExcelReader.validExceLColunm(temp,4)){
	    				
	    				mapVo.put("target_num", temp[4]);
	    				
	    				errorMap.put("target_num",temp[4]);
	    				
	    			}else {
	    				
	    				err_sb.append("数字不能为空");
	    			}
           
	                
	            	if (err_sb.toString().length() > 0) {

	            		errorMap.put("error_type", err_sb.toString());
	    				
	            		list_err.add(errorMap);
	            		
	            		break;

	    			} else {

	    				
	    				add_list.add(mapVo);
	    			}
	    			
	    		}
	               
	           if(add_list.size() > 0 ){
	        	
	        	   accTargetService.addImportAccTargetData(add_list);
	           }
	        	
			} catch (Exception e) {
				// TODO: handle exception
				Map<String, Object> errorMap = new HashMap<String, Object>();
				
				errorMap.put("error_type", "导入系统出错");
				
				list_err.add(errorMap);
				
			}
			
	        response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
			
			return null;
		}
		
		
		//下载导入模版
				@RequestMapping(value="/hrp/acc/acctargetdata/downTemplateTargetDate", method = RequestMethod.GET)  
				public String downTemplateTargetDate(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,
							   HttpServletResponse response,Model mode) throws IOException { 
								
					List<List> list = new ArrayList();
								
					List<String> listdata = new ArrayList<String>();
								
					listdata.add("年月");
								
					listdata.add("基本指标编码");
								
					listdata.add("基本指标名称");
								
					listdata.add("单位");
								
					listdata.add("数字");
								
					list.add(listdata);
								
					String ctxPath = request.getSession().getServletContext().getRealPath("/")
							+ "\\" + "excelTemplate\\"+"\\acc\\downTemplate\\";
								
					String downLoadPath = ctxPath + "基本数字.xls";
								
					ExcelWriter.exportExcel(new File(downLoadPath), list);
								
					printTemplate(request, response, "acc\\downTemplate", "基本数字.xls");

					return null; 
				}

}