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
import com.chd.hrp.acc.entity.autovouch.HisAccMedPayType;
import com.chd.hrp.acc.entity.autovouch.HisAccMedStore;
import com.chd.hrp.acc.entity.autovouch.HisAccMedVen;
import com.chd.hrp.acc.entity.autovouch.HisAccMedVenRef;
import com.chd.hrp.acc.service.autovouch.his.HisAccMedStoreService;
import com.chd.hrp.acc.service.autovouch.his.HisAccMedVenRefService;
import com.chd.hrp.acc.service.autovouch.his.HisAccMedVenService;
import com.chd.hrp.sys.entity.Store;
import com.chd.hrp.sys.entity.Sup;
import com.chd.hrp.sys.serviceImpl.StoreServiceImpl;
import com.chd.hrp.sys.serviceImpl.SupServiceImpl;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HisAccMedVenRefController extends BaseController {
	private static Logger logger = Logger.getLogger(HisAccMedVenRefController.class);

	@Resource(name = "hisAccMedVenRefService")
	private final HisAccMedVenRefService hisAccMedVenRefService = null;
	
	@Resource(name = "hisAccMedVenService")
	private final HisAccMedVenService hisAccMedVenService = null;
	
	@Resource(name = "supService")
	private final SupServiceImpl supService = null;

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedvenref/hisAccMedVenRefMainPage", method = RequestMethod.GET)
	public String hisAccMedVenRefMainPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/his/hisaccmedvenref/hisAccMedVenRefMain";
	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedvenref/hisAccMedVenRefAddPage", method = RequestMethod.GET)
	public String hisAccMedVenRefAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("ven_code" , mapVo.get("ven_code"));
		
		return "hrp/acc/autovouch/his/hisaccmedvenref/hisAccMedVenRefAdd";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedvenref/updateHisAccMedVenRefPage", method = RequestMethod.GET)
	public String updateHisAccMedVenRefPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		HisAccMedVenRef apt = hisAccMedVenRefService.queryHisAccMedVenRefByCode(mapVo);

		mode.addAttribute("ven_code" , apt.getVen_code());

		mode.addAttribute("ven_name", apt.getVen_name());

		mode.addAttribute("group_id",apt.getGroup_id());
		
		mode.addAttribute("hos_id",apt.getHos_id());
		
		mode.addAttribute("copy_code",apt.getCopy_code());

		return "hrp/acc/autovouch/his/hisaccmedvenref/hisAccMedVenRefUpdate";

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedvenref/queryHisAccMedVenRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisAccMedVenRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String str = hisAccMedVenRefService.queryHisAccMedVenRef(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedvenref/addHisAccMedVenRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHisAccMedVenRef(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("group_id", SessionManager.getGroupId());
            
            mapVo.put("hos_id", SessionManager.getHosId());
            
            mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("hrp_ven_id", res[0]);
			
            mapVo.put("his_ven_code", res[1]);
           
            listVo.add(mapVo);
            
        }
		
		String AccWageEmpJson = hisAccMedVenRefService.addHisAccMedVenRef(listVo);

		return JSONObject.parseObject(AccWageEmpJson);

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedvenref/updateHisAccMedVenRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHisAccMedVenRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String str = hisAccMedVenRefService.updateHisAccMedVenRef(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedvenref/deleteHisAccMedVenRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHisAccMedVenRef(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("group_id", res[0]);//实际实体类变量
            
            mapVo.put("hos_id", res[1]);
            
            mapVo.put("copy_code", res[4]);
            
            mapVo.put("his_ven_code", res[2]);
            
            mapVo.put("hrp_ven_id", res[3]);
            
            listVo.add(mapVo);
        }
		String apt = hisAccMedVenRefService.delHisAccMedVenRef(listVo);


		return JSONObject.parseObject(apt);

	}
	
	
	//下载导入模版
	@RequestMapping(value="/hrp/acc/autovouch/his/hisaccmedvenref/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
		
		List<List> list = new ArrayList<List>();
		
		List<String> listdata = new ArrayList<String>();
		
		listdata.add("HIS供应商编码");
		
		listdata.add("HIS供应商名称");
		
		//listdata.add("HIS药库药房标识(1药库2药房)");
		
		listdata.add("HRP供应商编码");
		
		listdata.add("HRP供应商名称");
		
		list.add(listdata);
		
		String ctxPath = request.getSession().getServletContext().getRealPath("/")
				+ "\\" + "excelTemplate\\"+"\\acc\\downTemplate\\";
		
		String downLoadPath = ctxPath + "供应商对应关系.xls";
		
		ExcelWriter.exportExcel(new File(downLoadPath), list);
		
		printTemplate(request, response, "acc\\downTemplate", "供应商对应关系.xls");

		return null; 
	 }
	
	
	/**
	 * 导入数据页面
	 * */
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedvenref/accHisAccMedVenRefImportPage", method = RequestMethod.GET)
	public String accHisAccMedVenRefImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/acc/autovouch/his/hisaccmedvenref/hisAccMedVenRefImport";
	}
	
	@RequestMapping(value="/hrp/acc/autovouch/his/hisaccmedvenref/readAccMedVenRef",method = RequestMethod.POST)  
    public String readAccMedVenRef(@RequestParam Map<String, Object> entityMap,Plupload plupload,HttpServletRequest request,
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
						
						entityMap.put("his_ven_code", temp[0]);
						
						mapVo.put("his_ven_code", temp[0]);
						
					} else {
						
						err_sb.append("HIS供应商编码为空  ");
						
					}
					
					//mapVo.put("his_store_flag", temp[2]);
					
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("group_id", mapVo.get("group_id"));
					
					map.put("hos_id",mapVo.get("hos_id"));
					
					map.put("copy_code",mapVo.get("copy_code"));
					
					map.put("ven_code", mapVo.get("his_ven_code"));
					
					//map.put("his_store_flag", mapVo.get("his_store_flag"));
					
					HisAccMedVen amv = hisAccMedVenService.queryHisAccMedVenByCode(map);//.queryHisAccMedStoreByCode(map);
					 
					if(amv!= null){
						
						entityMap.put("his_ven_name", amv.getVen_name());
						
						mapVo.put("his_ven_name", amv.getVen_name());
						
					}else {
						
						mapVo.put("his_ven_code", temp[0]);
						
						if(ExcelReader.validExceLColunm(temp,1)){
							
							mapVo.put("his_ven_name", temp[1]);
							
						}
						
						err_sb.append("HIS供应商编码不存在  ");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						entityMap.put("sup_code", temp[2]);
						
						mapVo.put("sup_code", temp[2]);
						
					} else {
						
						err_sb.append("HRP供应商编码为空  ");
						
					}
					  
					Map<String, Object> mapSup = new HashMap<String, Object>();
					
					mapSup.put("group_id", mapVo.get("group_id"));
					
					mapSup.put("hos_id",mapVo.get("hos_id"));
					
					mapSup.put("copy_code",mapVo.get("copy_code"));
					
					mapSup.put("sup_code", mapVo.get("sup_code")); 
					
					Sup sup= supService.querySupByCode(mapSup);//.queryStoreByCode(mapStore);
					
					if(sup!= null){
						
						entityMap.put("sup_code", sup.getSup_code());
						
						mapVo.put("sup_code", sup.getSup_code());
						
						mapVo.put("hrp_ven_id", sup.getSup_id());
						
					}else {
						
						mapVo.put("sup_code", temp[2]);
						
						if(ExcelReader.validExceLColunm(temp,3)){
							
							mapVo.put("sup_name", temp[3]);
							
						}
						
						err_sb.append("HRP供应商编码不存在  ");
						
					}
					
				/*if(err_sb.toString().length() == 0){
						
					AccWagePay data_exc_extis = accWagePayService.queryAccWagePayByCode(mapVo);
					
					if (data_exc_extis != null) {
						
						err_sb.append("此条数据已经存在！ ");
					
					}
				
				}*/
				
				mapList.add(mapVo);
				
				if(err_sb.toString().length() > 0){
					
					mapVo.put("error_type",err_sb.toString());
					
					list_err.add(mapVo);
					
				}
				
			}
			
			if(list_err.size()<=0){
				
				hisAccMedVenRefService.addHisAccMedVenRef(mapList);//.addHisAccMedStoreRef(mapList);
				
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
