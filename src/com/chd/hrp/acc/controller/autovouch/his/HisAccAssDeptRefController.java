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
import com.chd.hrp.acc.entity.autovouch.HisAccAssDept;
import com.chd.hrp.acc.entity.autovouch.HisAccAssDeptRef;
import com.chd.hrp.acc.service.autovouch.his.HisAccAssDeptRefService;
import com.chd.hrp.acc.service.autovouch.his.HisAccAssDeptService;
import com.chd.hrp.sys.entity.Dept;
import com.chd.hrp.sys.serviceImpl.DeptServiceImpl;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HisAccAssDeptRefController extends BaseController {
	private static Logger logger = Logger.getLogger(HisAccAssDeptRefController.class);

	@Resource(name = "hisAccAssDeptRefService")
	private final HisAccAssDeptRefService hisAccAssDeptRefService = null;
	
	@Resource(name = "hisAccAssDeptService")
	private final HisAccAssDeptService hisAccAssDeptService = null;
	
	@Resource(name = "deptService")
	private final DeptServiceImpl deptService = null;

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccassdeptref/hisAccAssDeptRefMainPage", method = RequestMethod.GET)
	public String hisAccAssDeptRefMainPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/his/hisaccassdeptref/main";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccassdeptref/hisAccAssDeptRefAddPage", method = RequestMethod.GET)
	public String hisAccAssDeptRefAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("his_dept_code" , mapVo.get("his_dept_code"));
		
		mode.addAttribute("dept_flag" , mapVo.get("dept_flag"));
		
		return "hrp/acc/autovouch/his/hisaccassdeptref/add";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccassdeptref/updateHisAccAssDeptRefPage", method = RequestMethod.GET)
	public String updateHisAccAssDeptRefPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		HisAccAssDeptRef apt = hisAccAssDeptRefService.queryHisAccAssDeptRefByCode(mapVo);

		mode.addAttribute("his_dept_code" , apt.getHis_dept_code());

		mode.addAttribute("hrp_dept_id", apt.getHrp_dept_id());
		
		mode.addAttribute("group_id",apt.getGroup_id());
		
		mode.addAttribute("hos_id",apt.getHos_id());
		
		mode.addAttribute("copy_code",apt.getCopy_code());

		return "hrp/acc/autovouch/his/hisaccassdeptref/hisAccAssDeptRefUpdate";

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccassdeptref/queryHisAccAssDeptRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisAccAssDeptRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String str = hisAccAssDeptRefService.queryHisAccAssDeptRef(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccassdeptref/addHisAccAssDeptRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHisAccAssDeptRef(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("group_id", SessionManager.getGroupId());
            
            mapVo.put("hos_id", SessionManager.getHosId());
            
            mapVo.put("copy_code", SessionManager.getCopyCode());
            
			mapVo.put("hrp_dept_id", res[0]);
			
			mapVo.put("his_dept_code", res[1]);
			
           
            listVo.add(mapVo);
            
        }

		String str = hisAccAssDeptRefService.addHisAccAssDeptRef(listVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccassdeptref/updateHisAccAssDeptRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHisAccAssDeptRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String str = hisAccAssDeptRefService.updateHisAccAssDeptRef(mapVo);

		return JSONObject.parseObject(str);

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccassdeptref/deleteHisAccAssDeptRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHisAccAssDeptRef(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("group_id", res[0]);//实际实体类变量
            
            mapVo.put("hos_id", res[1]);
            
            mapVo.put("copy_code", res[4]);
            
            mapVo.put("his_dept_code", res[2]);
            
            mapVo.put("hrp_dept_id", res[3]);
            
            listVo.add(mapVo);
        }
		
		String apt = hisAccAssDeptRefService.delHisAccAssDeptRef(listVo);

		return JSONObject.parseObject(apt);

	}
	
	//下载导入模版
		@RequestMapping(value="/hrp/acc/autovouch/his/hisaccassdeptref/downTemplate", method = RequestMethod.GET)  
		 public String downTemplate(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,
		    		HttpServletResponse response,Model mode) throws IOException { 
			
			List<List> list = new ArrayList<List>();
			
			List<String> listdata = new ArrayList<String>();
			
			listdata.add("HIS科室编码");
			
			listdata.add("HIS科室名称");
			
			
			listdata.add("HRP科室编码");
			
			listdata.add("HRP科室名称");
			
			list.add(listdata);
			
			String ctxPath = request.getSession().getServletContext().getRealPath("/")
					+ "\\" + "excelTemplate\\"+"\\acc\\downTemplate\\";
			
			String downLoadPath = ctxPath + "科室对应关系.xls";
			
			ExcelWriter.exportExcel(new File(downLoadPath), list);
			
			printTemplate(request, response, "acc\\downTemplate", "科室对应关系.xls");

			return null; 
		 }
		
		/**
		 * 导入数据页面
		 * */
		@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccassdeptref/accHisAccAssDeptRefImportPage", method = RequestMethod.GET)
		public String accHisAccAssDeptRefImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			return "hrp/acc/autovouch/his/hisaccassdeptref/import";
		}
		
		@RequestMapping(value="/hrp/acc/autovouch/his/hisaccassdeptref/readAccAssDeptRef",method = RequestMethod.POST)  
	    public String readAccAssDeptRef(@RequestParam Map<String, Object> entityMap,Plupload plupload,HttpServletRequest request,
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
							
							entityMap.put("his_dept_code", temp[0]);
							
							mapVo.put("his_dept_code", temp[0]);
							
						} else {
							
							err_sb.append("HIS科室编码为空  ");
							
						}
						
						mapVo.put("his_dept_flag", temp[2]);
						
						Map<String, Object> map = new HashMap<String, Object>();
						
						map.put("group_id", mapVo.get("group_id"));
						
						map.put("hos_id",mapVo.get("hos_id"));
						
						map.put("copy_code",mapVo.get("copy_code"));
						
						map.put("dept_code", mapVo.get("his_dept_code"));
						
						map.put("his_dept_flag", mapVo.get("his_dept_flag"));
						
						HisAccAssDept apt = hisAccAssDeptService.queryHisAccAssDeptByCode(map);
						 
						if(apt!= null){
							
							entityMap.put("his_dept_name", apt.getDept_name());
							
							mapVo.put("his_dept_name", apt.getDept_name());
							
						}else {
							
							err_sb.append("HIS科室编码不存在  ");
							
						}
						
						if (ExcelReader.validExceLColunm(temp,3)) {
							
							entityMap.put("hrp_dept_code", temp[3]);
							
							mapVo.put("hrp_dept_code", temp[3]);
							
						} else {
							
							err_sb.append("HRP科室编码为空  ");
							
						}
						  
						Map<String, Object> mapdept = new HashMap<String, Object>();
						
						mapdept.put("group_id", mapVo.get("group_id"));
						
						mapdept.put("hos_id",mapVo.get("hos_id"));
						
						mapdept.put("copy_code",mapVo.get("copy_code"));
						
						mapdept.put("dept_code", mapVo.get("hrp_dept_code")); 
						
						Dept dept= deptService.queryDeptByCode(mapdept);
						
						if(dept!= null){
							
							entityMap.put("hrp_dept_name", dept.getDept_name());
							
							mapVo.put("hrp_dept_name", dept.getDept_name());
							
							mapVo.put("hrp_dept_id", dept.getDept_id());
							
						}else {
							
							err_sb.append("HRP科室编码不存在  ");
							
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
					
					hisAccAssDeptRefService.addHisAccAssDeptRef(mapList);
					
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
