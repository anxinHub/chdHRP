/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.wage;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.AccWage;
import com.chd.hrp.acc.entity.AccWageEmp;
import com.chd.hrp.acc.service.wage.AccWageEmpKindService;
import com.chd.hrp.acc.serviceImpl.wage.AccWageEmpServiceImpl;
import com.chd.hrp.acc.serviceImpl.wage.AccWageServiceImpl;

/**
 * @Title. @Description. 工资套职工关系
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class AccWageEmpController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AccWageEmpController.class);
	
	@Resource(name = "accWageEmpService")
	private final AccWageEmpServiceImpl accWageEmpService = null;
	
	@Resource(name = "accWageEmpKindService")
	private final AccWageEmpKindService accWageEmpKindService = null;
	
	@Resource(name = "accWageService")
	private final AccWageServiceImpl accWageService = null;
    
	// 【工资管理-基础信息-工资套】：主页面
	@RequestMapping(value = "/hrp/acc/accwageemp/accWageEmpMainPage", method = RequestMethod.GET)
	public String accWageEmpMainPage(Model mode) throws Exception {
		return "hrp/acc/accwageemp/accWageEmpMain";
	}

	// 【工资管理-基础信息-工资套】：主页面-工资套添加页面
	@RequestMapping(value = "/hrp/acc/accwageemp/accWageEmpAddPage", method = RequestMethod.GET)
	public String accWageEmpAddPage(Model mode) throws Exception {
		return "hrp/acc/accwageemp/accWageEmpAdd";
	}
	
	// 【工资管理-基础信息-工资套】：主页面-添加工资套与职工分类关系
	@RequestMapping(value = "/hrp/acc/accwageemp/openEmpKindDataPage", method = RequestMethod.GET)
	public String openEmpKindDataPage(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		String json = accWageEmpKindService.queryAccWageEmpKind(getPage(paramMap));
		Map<String, Object> map = JSONObject.parseObject(json);
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("Rows");
		List<String> kindList = new ArrayList<String>();
		for(Map<String, Object> item : list){
			kindList.add("'" + item.get("kind_code").toString() + "'");
		}
		mode.addAttribute("kind_codes", kindList);
		return "hrp/acc/accwageemp/empKindDataPage";
	}
	
	/**
	 * 【工资管理-基础信息-工资套】：主页面-添加工资套与职工关系
	 */
	@RequestMapping(value = "/hrp/acc/accwageemp/openEmpDataPage", method = RequestMethod.GET)
	public String openEmpDataPage(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception {
		mode.addAttribute("wageCode", paramMap.get("wageCode").toString());
		return "hrp/acc/accwageemp/empDataPage";
	}
	
	/**
	 * 工资套职工关系<BR>
	 * 选择职工页面跳转
	 * @deprecated
	 */
	// 没有使用
//	@RequestMapping(value = "/hrp/acc/accwageemp/accWageEmpCollectPage", method = RequestMethod.GET)
	public String accWageEmpCollectPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("wage_code", mapVo.get("wage_code"));
		return "hrp/acc/accwageemp/accWageEmpCollect";
	}
	
	/**
	 * 工资套职工关系<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/acc/accwageemp/addAccWageEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccWageEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		try{
			String json = accWageEmpService.addAccWageEmp(mapVo);
			return JSONObject.parseObject(json);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"保存失败.\", \"state\":\"false\"}");
		}
	}
	
	@RequestMapping(value = "/hrp/acc/accwageemp/addBatchAccWageEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAccWageEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		String paramVo= mapVo.get("paramVo").toString();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> map=new HashMap<String, Object>();
			
			map.put("wage_code", res[0]);
			
			map.put("emp_id", res[1]);
			
			map.put("emp_no", res[2]);
			
			map.put("group_id", res[3]);
            
			map.put("hos_id", res[4]);
            
			map.put("copy_code", SessionManager.getCopyCode());
            
            if(map.get("note") == null){
            	
            	map.put("note", "");
            	
            }
           
            listVo.add(map);
            
            /*if(count ==0){
            	
            	accWageEmpService.deleteAccWageEmp(mapVo);
            	
            	count=1;
            }*/
        }
		
		String AccWageEmpJson = accWageEmpService.addBatchAccWageEmp(listVo);

		return JSONObject.parseObject(AccWageEmpJson);
	}
	
	/**
	 * 工资套职工关系<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accwageemp/queryAccWageEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWageEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String AccWageEmp = accWageEmpService.queryAccWageEmp(getPage(mapVo));
		return JSONObject.parseObject(AccWageEmp);
	}
	
	/**
	 * 查询工资套没有与职工关联
	 */
	@RequestMapping(value = "/hrp/acc/accwageemp/queryAccWageEmpNotBind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWageEmpNotBind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = accWageEmpService.queryAccWageEmpNotBind(getPage(mapVo));
		return JSONObject.parseObject(json);
	}
	
	/**
	*工资套职工关系<BR>
	*查询
	*@deprecated
	*/
//	@RequestMapping(value = "/hrp/acc/accwageemp/queryAccWageAttr", method = RequestMethod.POST)
//	@ResponseBody
	public Map<String, Object> queryAccWageAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String AccWageEmp = accWageEmpService.queryAccWageAttr(getPage(mapVo));

		return JSONObject.parseObject(AccWageEmp);
		
	}
	
	/**
	 * 工资套职工关系<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/acc/accwageemp/deleteAccWageEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWageEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String json = accWageEmpService.deleteAccWageEmp(mapVo);
		return JSONObject.parseObject(json);
	}
	
	/**
	*工资套职工关系<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accwageemp/deleteBatchAccWageEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBatchAccWageEmp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			mapVo.put("wage_code", res[0]);
			
			mapVo.put("emp_id", res[1]);
			
            mapVo.put("group_id", res[2]);
            
            mapVo.put("hos_id", res[3]);
            
            mapVo.put("copy_code", res[4]);
           
            listVo.add(mapVo);
        }
		
		String AccWageEmpJson = accWageEmpService.deleteBatchAccWageEmp(listVo);
	   
		return JSONObject.parseObject(AccWageEmpJson);
			
	}
	
	/**
	*工资套职工关系<BR>
	*修改页面跳转
	*@deprecated
	*/
	@RequestMapping(value = "/hrp/acc/accwageemp/accWageEmpUpdatePage", method = RequestMethod.GET)
	public String accWageEmpUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
		AccWageEmp accWageEmp = new AccWageEmp();
        
		accWageEmp = accWageEmpService.queryAccWageEmpByCode(mapVo);
		
		mode.addAttribute("wage_code", accWageEmp.getWage_code());
		
		mode.addAttribute("wage_name", accWageEmp.getWage_name());
		
		mode.addAttribute("group_id", accWageEmp.getGroup_id());
		
		mode.addAttribute("hos_id", accWageEmp.getHos_id());
		
		mode.addAttribute("copy_code", accWageEmp.getCopy_code());
		
		mode.addAttribute("note", accWageEmp.getNote());
	
		return "hrp/acc/accwageemp/accWageEmpUpdate";
	}
	/**
	*工资套职工关系<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accwageemp/updateAccWageEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWageEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		String AccWageEmpJson = accWageEmpService.updateAccWageEmp(mapVo);
		
		return JSONObject.parseObject(AccWageEmpJson);
	}
	/**
	*工资套职工关系<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accwageemp/importAccWageEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccWageEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String AccWageEmpJson = accWageEmpService.importAccWageEmp(mapVo);
		
		return JSONObject.parseObject(AccWageEmpJson);
	}
	
	/**
	*工资套职工关系<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accwageemp/queryAccWageEmpList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWageEmpList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("emp_history")==null){
			
			mapVo.put("emp_history", MyConfig.getSysPara("021"));
			
		}
		
		if(mapVo.get("emp_code") != null){
			//用来转换大写
			mapVo.put("emp_code",mapVo.get("emp_code").toString().toUpperCase());
		}
		if(mapVo.get("dept_code") != null){
			//用来转换大写
			mapVo.put("dept_code",mapVo.get("dept_code").toString().toUpperCase());
		}
		if(mapVo.get("kind_code") != null){
			//用来转换大写
			mapVo.put("kind_code",mapVo.get("kind_code").toString().toUpperCase());
		}
        String AccWageEmp = accWageEmpService.queryAccWageEmpList(getPage(mapVo));

		return JSONObject.parseObject(AccWageEmp);
		
	}
	
	/**
	 * 跳转继承工资套页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accwageemp/accExtendWageEmpPage", method = RequestMethod.GET)
	public String accExtendWageEmpPage(Model mode) throws Exception {

		return "hrp/acc/accwageemp/accExtendWageEmpPage";

	}
	/**
	 * 工资套继承，A继承B，将B中的数据添加到A中，重复的数据不添加
	 */
	@RequestMapping(value = "/hrp/acc/accwageemp/accExtendOldWageEmpToNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> accExtendOldWageEmpToNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
	        mapVo.put("copy_code", SessionManager.getCopyCode());
		String AccWageEmpJson = accWageEmpService.accExtendOldWageEmpToNew(mapVo);

		return JSONObject.parseObject(AccWageEmpJson);
		
	}
	
	/**
	 * @deprecated
	 */
//	@RequestMapping(value = "/hrp/acc/accwageemp/importAccWageEmpPage", method = RequestMethod.GET)
	public String importAccWageEmpPage(Model mode) throws Exception {
		return "hrp/acc/accwageemp/accWageEmpImport";
	}
	
	//下载导入模版
	@RequestMapping(value="hrp/acc/accwageemp/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
		
		List<List> list = new ArrayList();
		
		List<String> listdata = new ArrayList<String>();
		
		listdata.add("工资套编码");
		
		listdata.add("工资套名称");
		
		listdata.add("职工编码");
		
		listdata.add("职工名称");

		list.add(listdata);
		
		String ctxPath = request.getSession().getServletContext().getRealPath("/")
				+ "\\" + "excelTemplate\\"+"\\acc\\downTemplate\\";
		
		String downLoadPath = ctxPath + "工资套职工.xls";
		
		ExcelWriter.exportExcel(new File(downLoadPath), list);
		
		printTemplate(request, response, "acc\\downTemplate", "工资套职工.xls");

		return null; 
	 }
	
	/**
	 * @deprecated
	 */
//	@RequestMapping(value = "/hrp/acc/accwageemp/readAccWageEmpFiles", method = RequestMethod.POST)
//	@ResponseBody
	public String readAccWageEmpFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException  {
		
		List<AccWageEmp> list_err = new ArrayList<AccWageEmp>();
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		List<String[]> listAdd = UploadUtil.readFile(plupload, request, response);
		
		try {
			
			for (int i = 1; i < listAdd.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AccWageEmp accWageEmp = new AccWageEmp();
				
				String temp[] = listAdd.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
				if(ExcelReader.validExceLColunm(temp,0)){
						
					mapVo.put("group_id", SessionManager.getGroupId());
					
					mapVo.put("hos_id", SessionManager.getHosId());
						
					mapVo.put("copy_code", SessionManager.getCopyCode());
					
					mapVo.put("wage_code", temp[0]);
					
					AccWage accWage=accWageService.queryAccWageByCode(mapVo);
					
					if(accWage==null){
						
						accWageEmp.setWage_code(temp[0]);

						accWageEmp.setWage_name(temp[1]);
						
						err_sb.append("工资套不存在  ");
					}
					
				}else{
						
					err_sb.append("工资套编码为空  ");
				}
				
				if(ExcelReader.validExceLColunm(temp,2)){
						
					if(StringTool.isNotBlank(temp[2])){
						
						mapVo.put("emp_code", temp[2]);
						
						AccWageEmp accWageEmps = accWageEmpService.queryAccWageEmpCodeByImp(mapVo);
						
						if(accWageEmps!=null ){
							
							mapVo.put("emp_id", accWageEmps.getEmp_id());
							
							mapVo.put("emp_no", accWageEmps.getEmp_no());
							
							mapVo.put("note", "");
							
							list.add(mapVo);
							
						}else{
							
							accWageEmp.setEmp_code(temp[2]);
							
							accWageEmp.setEmp_name(temp[3]);
							
							err_sb.append("职工编码不存在  ");
						}
						
					}else {
							
						err_sb.append("职工编码为空  ");
						
					}
						
				} 
				
				if (err_sb.toString().length() > 0) {
					
					accWageEmp.setError_type(err_sb.toString());
					
					list_err.add(accWageEmp);
				
				}
				
			} 
			
			if(list_err.size()<=0){
				
				 accWageEmpService.deleteBatchAccWageEmp(list);
				
		        accWageEmpService.addBatchAccWageEmp(list);
		        
			} 
			
		} catch (DataAccessException e) {
			
			logger.error(e.getMessage(), e);
			
			AccWageEmp accWageEmp = new AccWageEmp();
			
			accWageEmp.setError_type("导入系统出错");
			
			list_err.add(accWageEmp);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
	}
	
}

