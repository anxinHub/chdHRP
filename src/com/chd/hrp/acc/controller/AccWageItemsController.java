/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.dao.HrpAccSelectMapper;
import com.chd.hrp.acc.entity.AccWage;
import com.chd.hrp.acc.entity.AccWageItems;
import com.chd.hrp.acc.entity.HrpAccSelect;
import com.chd.hrp.acc.serviceImpl.AccWageItemsServiceImpl;
import com.chd.hrp.acc.serviceImpl.wage.AccWageServiceImpl;

/**
* @Title. @Description.
* 工资项目表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccWageItemsController extends BaseController{
	private static Logger logger = Logger.getLogger(AccWageItemsController.class);
	
	
	@Resource(name = "accWageItemsService")
	private final AccWageItemsServiceImpl accWageItemsService = null;
	
	@Resource(name = "accWageService")
	private final AccWageServiceImpl accWageService = null;
	
	@Resource(name = "hrpAccSelectMapper")
	private final HrpAccSelectMapper hrpAccSelectMapper = null;
   
	/**
	 * 【工资管理-工资项目-工资项目】：主页面
	 */
	@RequestMapping(value = "/hrp/acc/accwageitems/accWageItemsMainPage", method = RequestMethod.GET)
	public String accWageItemsMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", DateUtil.dateToString(new Date(), "yyyyMM"));
		return "hrp/acc/accwageitems/accWageItemsMain";
	}
	
	/**
	*工资项目表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accwageitems/accWageItemsAddPage", method = RequestMethod.GET)
	public String accWageItemsAddPage(Model mode) throws Exception {

		return "hrp/acc/accwageitems/accWageItemsAdd";

	}
	
	/**
	 * 工资项目表<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/acc/accwageitems/addAccWageItems", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccWageItems(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		if(StringUtils.isBlank(paramVo)){
			return JSONObject.parseObject("{\"warn\":\"没有可保存数据\",\"state\":\"true\"}");
		}
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] res = id.split("@");
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("wage_code", res[0]);
			mapVo.put("acc_year", res[1]);
			mapVo.put("item_code", res[2]);
			mapVo.put("item_name", res[3]);
			
			if (res[4].indexOf("u") > -1) {
				mapVo.put("item_nature", "");
			} else {
				mapVo.put("item_nature", res[4]);
			}
			
			if (res[5].indexOf("u") > -1) {
				mapVo.put("item_type", "");
			} else {
				mapVo.put("item_type", res[5]);
			}
			
			mapVo.put("item_cal", res[6]);
			mapVo.put("is_jc", res[7]);
			mapVo.put("is_sum", res[8]);
			mapVo.put("is_stop", res[9]);
			
			if (res.length > 10) {
				if (!"undefined".equals(res[10]) && !"null".equals(res[10])) {
					mapVo.put("note", res[10]);
				} else {
					mapVo.put("note", "");
				}
			} else {
				mapVo.put("note", "");
			}
			
			mapVo.put("sort_code", res[11]);
			
			if (null != res[12] && !"".equals(res[12])) {
				mapVo.put("data_type", res[12]);
			} else {
				mapVo.put("data_type", "0");
			}
			
			// 根据名称生成拼音码 、五笔码
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("item_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("item_name").toString()));
			
			listVo.add(mapVo);
		}
		
		String accWageItemsJson = accWageItemsService.addBatchAccWageItems(listVo);
		return JSONObject.parseObject(accWageItemsJson);
	}

	/**
	 * 【工资管理-工资数据】：维护方案-导入工资项页面查询
	 */
	@RequestMapping(value = "/hrp/acc/accwageitems/queryAccWageItems", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWageItems(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
        
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if (mapVo.get("acc_year") == null) {
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
		}
       
		if(mapVo.get("scheme_id") == null||"undefined".equals(mapVo.get("scheme_id"))||"".equals(mapVo.get("scheme_id"))){
			
	        mapVo.put("scheme_id", "");
	        
		}
		if(mapVo.get("item_code") != null){
			//用来转换大写
			mapVo.put("item_code",mapVo.get("item_code").toString().toUpperCase());
		}
	
		
		String accWageItems = accWageItemsService.queryAccWageItems(getPage(mapVo));

		return JSONObject.parseObject(accWageItems);
		
	}
	/**
	*工资项目表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accwageitems/deleteAccWageItems", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWageItems(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			if("undefined".equals(id.split("@")[0])|| null==id.split("@")[0]){
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
			}else{
				
				mapVo.put("group_id", id.split("@")[0]);
				
			}
			 
			if("undefined".equals(id.split("@")[1])|| null==id.split("@")[1]){
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
			}else{
				
				mapVo.put("hos_id", id.split("@")[1]);
				
			}
			
			if("undefined".equals(id.split("@")[2])|| null==id.split("@")[2]){
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
			}else{
				
				mapVo.put("copy_code", id.split("@")[2]);
				
			}
			
			mapVo.put("acc_year", id.split("@")[3]);
			
			mapVo.put("wage_code", id.split("@")[4]);
            
			mapVo.put("item_code", id.split("@")[5]);
           
			listVo.add(mapVo);
       
		}
		
		String accWageItemsJson = accWageItemsService.deleteBatchAccWageItems(listVo);
	   
		return JSONObject.parseObject(accWageItemsJson);
			
	}
	
	/**
	*工资项目表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accwageitems/accWageItemsUpdatePage", method = RequestMethod.GET)
	
	public String accWageItemsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
		AccWageItems accWageItems = new AccWageItems();
		
		accWageItems = accWageItemsService.queryAccWageItemsByCode(mapVo);
		
		mode.addAttribute("group_id", accWageItems.getGroup_id());
		
		mode.addAttribute("hos_id", accWageItems.getHos_id());
		
		mode.addAttribute("copy_code", accWageItems.getCopy_code());
		
		mode.addAttribute("acc_year", accWageItems.getAcc_year());
		
		mode.addAttribute("wage_code", accWageItems.getWage_code());
		
		mode.addAttribute("wage_name", accWageItems.getWage_name());
		
		mode.addAttribute("item_code", accWageItems.getItem_code());
		
		mode.addAttribute("item_id", accWageItems.getItem_id());
		
		mode.addAttribute("item_name", accWageItems.getItem_name());
		
		mode.addAttribute("item_type", accWageItems.getItem_type());
		
		mode.addAttribute("item_cal", accWageItems.getItem_cal());
		
		mode.addAttribute("item_nature", accWageItems.getItem_nature());
		
		mode.addAttribute("is_jc", accWageItems.getIs_jc());
		
		mode.addAttribute("is_sum", accWageItems.getIs_sum());
		
		mode.addAttribute("sort_code", accWageItems.getSort_code());
		
		mode.addAttribute("spell_code", accWageItems.getSpell_code());
		
		mode.addAttribute("wbx_code", accWageItems.getWbx_code());
		
		mode.addAttribute("is_stop", accWageItems.getIs_stop());
		
		mode.addAttribute("column_item", accWageItems.getColumn_item());
		
		return "hrp/acc/accwageitems/accWageItemsUpdate";
	}
	/**
	*工资项目表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accwageitems/updateAccWageItems", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWageItems(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("item_name").toString()));
	   
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("item_name").toString()));

		String accWageItemsJson = accWageItemsService.updateAccWageItems(mapVo);
		
		return JSONObject.parseObject(accWageItemsJson);
	}
	
	// 工资项目导入页面
	@RequestMapping(value = "/hrp/acc/accwageitems/importAccWageItemPage", method = RequestMethod.GET)
	public String importAccWageItemPage(Model mode) throws Exception {

		return "hrp/acc/accwageitems/accWageItemImport";

	}
	/**
	*工资项目表<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accwageitems/readAccWageItemsFiles", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> readAccWageItemsFiles(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accWageItemsJson = accWageItemsService.importAccWageItems(mapVo);
		
		return JSONObject.parseObject(accWageItemsJson);
	}
	
	/**
	 * 继承工资项
	 * 
	 * **/
	@RequestMapping(value = "/hrp/acc/accwageitems/extendAccWageItems", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendAccWageItems(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		if (mapVo.get("acc_year") == null) {
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
		}
		
		String accWageItemsJson = accWageItemsService.extendAccWageItems(mapVo);
		
		return JSONObject.parseObject(accWageItemsJson);
	}
	
	@RequestMapping(value="/hrp/acc/accwageitems/readAccWageItemFiles",method = RequestMethod.POST)
	
    public String readAccWageItemFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AccWageItems> list_err = new ArrayList<AccWageItems>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		int sort_code = 0;
		
		try {
			
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AccWageItems accWageItems = new AccWageItems();
				
				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
				if(ExcelReader.validExceLColunm(temp,0)){
						
					mapVo.put("group_id", SessionManager.getGroupId());
					
					mapVo.put("hos_id", SessionManager.getHosId());
						
					mapVo.put("copy_code", SessionManager.getCopyCode());
					
					mapVo.put("acc_year", SessionManager.getAcctYear());
					
					mapVo.put("wage_code", temp[0]);
					
					AccWage accWage=accWageService.queryAccWageByCode(mapVo);
					
					if(accWage==null){
						
						err_sb.append("工资套不存在  ");
					}
					
					accWageItems.setWage_code(temp[0]);
					//根据工资套编码查询出工资套名称
						
				}else{
						
					err_sb.append("工资套编码为空  ");
				}
				
				if(ExcelReader.validExceLColunm(temp,2)){
						
					if(StringTool.isNotBlank(temp[2])){
						
						mapVo.put("item_code", temp[2]);
						
						accWageItems.setItem_code(temp[2]);
						
						AccWageItems accWageItem = accWageItemsService.queryAccWageItemsByCode(mapVo);
						
						if(accWageItem!=null){
							
							err_sb.append("工资项编码重复  ");
						}
						
					}else {
							
						err_sb.append("工资项编码为空  ");
						
					}
						
				} 
				
				if(ExcelReader.validExceLColunm(temp,3)){
					
						mapVo.put("item_name", temp[3]);
						
						accWageItems.setItem_name(temp[3]);
						
					} else {
							
						err_sb.append("工资项名称为空  ");
						
				}

				if(ExcelReader.validExceLColunm(temp,4)){
					
					mapVo.put("key", temp[4]);
					
					List<HrpAccSelect> accWageType = hrpAccSelectMapper.queryWageItemType(mapVo);
					
					if(accWageType.size()>0){
						
						accWageItems.setType_name(temp[4]);
						
						mapVo.put("item_type", accWageType.get(0).getType_code());
						
					}else{
						
						accWageItems.setType_name(temp[4]);
						
						err_sb.append("工资类型名称不存在  ");
					}
					
					//判断工资类型	
				} else {
							
						err_sb.append("工资类型名称为空  ");
						
				}
					
				if(ExcelReader.validExceLColunm(temp,5)){
					
					mapVo.put("key", temp[5]);
					
					List<HrpAccSelect> accWageType = hrpAccSelectMapper.queryWageItemNature(mapVo);
					
					if(accWageType.size()>0){
						
						accWageItems.setNature_name(temp[5]);
						
						mapVo.put("item_nature", accWageType.get(0).getId());
						
					}else{
						
						accWageItems.setNature_name(temp[5]);
						
						err_sb.append("工资性质名称不存在  ");
					}
				} else {
							
					err_sb.append("工资性质编码为空  ");
						
				}
				
				if(ExcelReader.validExceLColunm(temp,6)){
					
					if("输入项".equals(temp[6])){
						
						mapVo.put("item_cal", 1);
						
						accWageItems.setItem_cal(1);
						
					}else if("计算项".equals(temp[6])){
						
						mapVo.put("item_cal", 2);
						
						accWageItems.setItem_cal(2);
						
					}else{
						
						mapVo.put("item_cal", 3);
						
						accWageItems.setItem_cal(3);
					}
					
				} else {
							
					err_sb.append("计算项为空  ");
						
				}
				
				if(ExcelReader.validExceLColunm(temp,7)){
									
					if("否".equals(temp[7])){
						
						mapVo.put("is_jc", 0);
						
						accWageItems.setIs_jc(0);
						
					}else if("是".equals(temp[7])){
						
						mapVo.put("is_jc", 1);
						
						accWageItems.setIs_jc(1);
						
					}
						
				} else {
											
					err_sb.append("是否继承为空  ");
										
				}
			
				if(ExcelReader.validExceLColunm(temp,8)){
					
						if("否".equals(temp[8])){
						
						mapVo.put("is_sum", 0);
						
						accWageItems.setIs_sum(0);
						
					}else if("是".equals(temp[8])){
						
						mapVo.put("is_sum", 1);
						
						accWageItems.setIs_sum(1);
						
					}
						
				} else {
							
					err_sb.append("是否参与合计为空  ");
						
				}
			
				if (err_sb.toString().length() > 0) {
				
					accWageItems.setError_type(err_sb.toString());
					
					list_err.add(accWageItems);
				
				}else{
					
					//根据名称生成拼音码
			        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("item_name").toString()));
					//根据名称生成五笔码
			        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("item_name").toString()));
			        
			        mapVo.put("is_stop", 0);
			        
			        mapVo.put("note", "");
			        
			        mapVo.put("sort_code", sort_code);
					
			        accWageItemsService.addAccWageItems(mapVo);
			        
			        sort_code+=10;
				} 
				
			}
			
		} catch (DataAccessException e) {
			
			logger.error(e.getMessage(), e);
			
			AccWageItems accWageItem = new AccWageItems();
			
			accWageItem.setError_type("导入系统出错");
			
			list_err.add(accWageItem);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    } 
	
	//下载导入模版
		@RequestMapping(value="hrp/acc/accwageitems/downTemplate", method = RequestMethod.GET)  
		 public String downTemplate(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,
		    		HttpServletResponse response,Model mode) throws IOException { 
			
			List<List> list = new ArrayList();
			
			List<String> listdata = new ArrayList<String>();
			
			listdata.add("工资套编码");
			
			listdata.add("工资套名称");
			
			listdata.add("工资项目编码");
			
			listdata.add("工资项目名称");
			
			//listdata.add("工资类型编码");
			
			listdata.add("工资类型名称");
			
			//listdata.add("工资性质编码");
			
			listdata.add("工资性质名称");
			
			listdata.add("计算方式(输入项/计算项/合并计算项)");
			
			listdata.add("是否继承（是/否）");
			
			listdata.add("是否参与合计（是/否）");
			
			list.add(listdata);
			
			String ctxPath = request.getSession().getServletContext().getRealPath("/")
					+ "\\" + "excelTemplate\\"+"\\acc\\downTemplate\\";
			
			String downLoadPath = ctxPath + "工资项目模板.xls";
			
			ExcelWriter.exportExcel(new File(downLoadPath), list);
			
			printTemplate(request, response, "acc\\downTemplate", "工资项目模板.xls");

			return null; 
		 }
		
		
		/**
		*工资项目表<BR>
		*批量更新序列
		*/
		@RequestMapping(value = "/hrp/acc/accwageitems/updateSortcodeByItemcode", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateSortcodeByItemcode(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
			
			List<Map<String,Object>> listVo = new ArrayList<Map<String,Object>>();
			
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				mapVo.put("group_id", SessionManager.getGroupId());
			 
				mapVo.put("hos_id", SessionManager.getHosId());
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				mapVo.put("acc_year", id.split("@")[3]);
					
				mapVo.put("sort_code", id.split("@")[2]);
				
				mapVo.put("wage_code", id.split("@")[1]);
	            
				mapVo.put("item_code", id.split("@")[0]);
	           
				listVo.add(mapVo);
	       
			}
			
			if(listVo==null || listVo.size()==0){
			
				return JSONObject.parseObject("{\"error\":\"数据为空！\"}");
			
			}
			
			String accWageItemsJson = accWageItemsService.updateSortcodeByItemcode(listVo);

			return JSONObject.parseObject(accWageItemsJson);
			
		}
		
		/**
		 *工资项目表<BR>
		 *在grid最后一列，按回车保存
		 *按itecode修改单挑数据
		 */
		@RequestMapping(value = "/hrp/acc/accwageitems/updateoraddByItemcode", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateoraddByItemcode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			 
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			//根据名称生成拼音码
	        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("item_name").toString()));
			//根据名称生成五笔码
	        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("item_name").toString()));

			
			String accWageItemsJson = accWageItemsService.updateoraddByItemcode(mapVo);
			return JSONObject.parseObject(accWageItemsJson);
			
		}

}

