/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.controller.autovouch.his;

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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.autovouch.HisAccMedStore;
import com.chd.hrp.acc.entity.autovouch.HisAccMedStoreRef;
import com.chd.hrp.acc.service.autovouch.his.HisAccMedStoreRefService;
import com.chd.hrp.acc.service.autovouch.his.HisAccMedStoreService;
import com.chd.hrp.sys.entity.Store;
import com.chd.hrp.sys.serviceImpl.StoreServiceImpl;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HisAccMedStoreRefController extends BaseController {
	private static Logger logger = Logger.getLogger(HisAccMedStoreRefController.class);

	@Resource(name = "hisAccMedStoreRefService")
	private final HisAccMedStoreRefService hisAccMedStoreRefService = null;
	
	@Resource(name = "hisAccMedStoreService")
	private final HisAccMedStoreService hisAccMedStoreService = null;
	
	@Resource(name = "storeService")
	private final StoreServiceImpl storeService = null;

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedstoreref/hisAccMedStoreRefMainPage", method = RequestMethod.GET)
	public String hisAccMedStoreRefMainPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/his/hisaccmedstoreref/hisAccMedStoreRefMain";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedstoreref/hisAccMedStoreRefAddPage", method = RequestMethod.GET)
	public String hisAccMedStoreRefAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("his_store_code" , mapVo.get("his_store_code"));
		
		mode.addAttribute("store_flag" , mapVo.get("store_flag"));
		
		return "hrp/acc/autovouch/his/hisaccmedstoreref/hisAccMedStoreRefAdd";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedstoreref/updateHisAccMedStoreRefPage", method = RequestMethod.GET)
	public String updateHisAccMedStoreRefPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		HisAccMedStoreRef apt = hisAccMedStoreRefService.queryHisAccMedStoreRefByCode(mapVo);

		mode.addAttribute("his_store_code" , apt.getHis_store_code());

		mode.addAttribute("hrp_store_id", apt.getHrp_store_id());
		
		mode.addAttribute("his_store_flag", apt.getHis_store_flag());

		mode.addAttribute("group_id",apt.getGroup_id());
		
		mode.addAttribute("hos_id",apt.getHos_id());
		
		mode.addAttribute("copy_code",apt.getCopy_code());

		return "hrp/acc/autovouch/his/hisaccmedstoreref/hisAccMedStoreRefUpdate";

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedstoreref/queryHisAccMedStoreRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisAccMedStoreRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String str = hisAccMedStoreRefService.queryHisAccMedStoreRef(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedstoreref/addHisAccMedStoreRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHisAccMedStoreRef(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("group_id", SessionManager.getGroupId());
            
            mapVo.put("hos_id", SessionManager.getHosId());
            
            mapVo.put("copy_code", SessionManager.getCopyCode());
            
			mapVo.put("hrp_store_id", res[0]);
			
			mapVo.put("his_store_code", res[1]);
			
			mapVo.put("his_store_flag", res[2]);
           
            listVo.add(mapVo);
            
        }

		String str = hisAccMedStoreRefService.addHisAccMedStoreRef(listVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedstoreref/updateHisAccMedStoreRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHisAccMedStoreRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String str = hisAccMedStoreRefService.updateHisAccMedStoreRef(mapVo);

		return JSONObject.parseObject(str);

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedstoreref/deleteHisAccMedStoreRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHisAccMedStoreRef(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("group_id", res[0]);//实际实体类变量
            
            mapVo.put("hos_id", res[1]);
            
            mapVo.put("copy_code", res[4]);
            
            mapVo.put("his_store_code", res[2]);
            
            mapVo.put("hrp_store_id", res[3]);
            
            listVo.add(mapVo);
        }
		
		String apt = hisAccMedStoreRefService.delHisAccMedStoreRef(listVo);

		return JSONObject.parseObject(apt);

	}
	
	//下载导入模版
		@RequestMapping(value="/hrp/acc/autovouch/his/hisaccmedstoreref/downTemplate", method = RequestMethod.GET)  
		 public String downTemplate(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,
		    		HttpServletResponse response,Model mode) throws IOException { 
			
			List<List> list = new ArrayList<List>();
			
			List<String> listdata = new ArrayList<String>();
			
			listdata.add("HIS库房编码");
			
			listdata.add("HIS库房名称");
			
			listdata.add("HIS药库药房标识(1药库2药房)");
			
			listdata.add("HRP库房编码");
			
			listdata.add("HRP库房名称");
			
			list.add(listdata);
			
			String ctxPath = request.getSession().getServletContext().getRealPath("/")
					+ "\\" + "excelTemplate\\"+"\\acc\\downTemplate\\";
			
			String downLoadPath = ctxPath + "库房对应关系.xls";
			
			ExcelWriter.exportExcel(new File(downLoadPath), list);
			
			printTemplate(request, response, "acc\\downTemplate", "库房对应关系.xls");

			return null; 
		 }
		
		/**
		 * 导入数据页面
		 * */
		@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedstoreref/accHisAccMedStoreRefImportPage", method = RequestMethod.GET)
		public String accHisAccMedStoreRefImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			return "hrp/acc/autovouch/his/hisaccmedstoreref/hisAccMedStoreRefImport";
		}
		
		@RequestMapping(value="/hrp/acc/autovouch/his/hisaccmedstoreref/readAccMedStoreRef",method = RequestMethod.POST)  
	    public String readAccMedStoreRef(@RequestParam Map<String, Object> entityMap,Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,String rules,Model mode) throws IOException { 
				
			List<Map<String, Object>> list_err = new ArrayList<Map<String, Object>>();
			
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			
			List<String[]> list = UploadUtil.readFile(plupload, request, response);
			
			Map<String, Object> sqlMap = new HashMap<String, Object>();
			
			try {
				
				for (int i = 1; i < list.size(); i++) {
					
					StringBuffer err_sb = new StringBuffer();
					
					String temp[] = list.get(i);// 行
					
					Map<String, Object> mapVo = new HashMap<String, Object>();
					
						if (entityMap.get("group_id") == null) {
							
							mapVo.put("group_id", SessionManager.getGroupId());
							
						}else{
							
							mapVo.put("group_id", entityMap.get("group_id"));
							
						}
						
						if (entityMap.get("hos_id") == null) {
							
							mapVo.put("hos_id", SessionManager.getHosId());
							
						}else{
							
							mapVo.put("hos_id", entityMap.get("hos_id"));
							
						}
						
						if (entityMap.get("copy_code") == null) {
							
							mapVo.put("copy_code", SessionManager.getCopyCode());
						
						}else{
							
							mapVo.put("copy_code", entityMap.get("copy_code"));
							
						}
						
						if (ExcelReader.validExceLColunm(temp,0)) {
							
							entityMap.put("his_store_code", temp[0]);
							
							mapVo.put("his_store_code", temp[0]);
							
						} else {
							
							err_sb.append("HIS库房编码为空  ");
							
						}
						
						mapVo.put("his_store_flag", temp[2]);
						
						Map<String, Object> map = new HashMap<String, Object>();
						
						map.put("group_id", mapVo.get("group_id"));
						
						map.put("hos_id",mapVo.get("hos_id"));
						
						map.put("copy_code",mapVo.get("copy_code"));
						
						map.put("store_code", mapVo.get("his_store_code"));
						
						map.put("his_store_flag", mapVo.get("his_store_flag"));
						
						HisAccMedStore apt = hisAccMedStoreService.queryHisAccMedStoreByCode(map);
						 
						if(apt!= null){
							
							entityMap.put("his_store_name", apt.getStore_name());
							
							mapVo.put("his_store_name", apt.getStore_name());
							
						}else {
							
							err_sb.append("HIS库房编码不存在  ");
							
						}
						
						if (ExcelReader.validExceLColunm(temp,3)) {
							
							entityMap.put("hrp_store_code", temp[3]);
							
							mapVo.put("hrp_store_code", temp[3]);
							
						} else {
							
							err_sb.append("HRP库房编码为空  ");
							
						}
						  
						Map<String, Object> mapStore = new HashMap<String, Object>();
						
						mapStore.put("group_id", mapVo.get("group_id"));
						
						mapStore.put("hos_id",mapVo.get("hos_id"));
						
						mapStore.put("copy_code",mapVo.get("copy_code"));
						
						mapStore.put("store_code", mapVo.get("hrp_store_code")); 
						
						Store store= storeService.queryStoreByCode(mapStore);
						
						if(store!= null){
							
							entityMap.put("hrp_store_name", store.getStore_name());
							
							mapVo.put("hrp_store_name", store.getStore_name());
							
							mapVo.put("hrp_store_id", store.getStore_id());
							
						}else {
							
							err_sb.append("HRP库房编码不存在  ");
							
						}
						
					if(err_sb.toString().length() == 0){
							
						/*AccWagePay data_exc_extis = accWagePayService.queryAccWagePayByCode(mapVo);
						
						if (data_exc_extis != null) {
							
							err_sb.append("此条数据已经存在！ ");
						
						}*/
					
					}
					
					mapList.add(mapVo);
					
					if(err_sb.toString().length() > 0){
						
						mapVo.put("error_type",err_sb.toString());
						
						list_err.add(mapVo);
						
					}
					
				}
				
				if(list_err.size()<=0){
					
					hisAccMedStoreRefService.addHisAccMedStoreRef(mapList);
					
				}
				
			} catch (DataAccessException e) {
				
				logger.error(e.getMessage(), e);
				
				Map<String,Object> data_exc = new HashMap<String, Object>();
				
				data_exc.put("error_type","导入系统出错  ");
				
				list_err.add(data_exc);
			}
			
			response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
			
			return null;
	    } 

}
