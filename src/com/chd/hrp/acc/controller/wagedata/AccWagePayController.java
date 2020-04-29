/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.wagedata;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.chd.base.util.DateUtil;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.AccWagePay;
import com.chd.hrp.acc.entity.AccWagePayDesc;
import com.chd.hrp.acc.service.AccWagePayDescService;
import com.chd.hrp.acc.service.HrpAccSelectService;
import com.chd.hrp.acc.serviceImpl.wagedata.AccWagePayServiceImpl;
/**
 * @Title. @Description. 工资数据 
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */ 
@Controller
public class AccWagePayController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AccWagePayController.class);
	
	@Resource(name = "accWagePayService")
	private final AccWagePayServiceImpl accWagePayService = null;
	
	@Resource(name = "accWagePayDescService")
	private final AccWagePayDescService accWagePayDescService = null;
	
	@Resource(name = "hrpAccSelectService")
	private final HrpAccSelectService hrpAccSelectService = null;
	
	/**
	 * 【工资管理-工资数据-工资录入】：主页面
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/accWagePayMainPage", method = RequestMethod.GET)
	public String accWagePayMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", DateUtil.dateToString(new Date(), "yyyyMM"));
		return "hrp/acc/accwagepay/accWagePayMain";
	}
	
	/**
	*工资数据<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accwagepay/accWagePayBatchAddPage", method = RequestMethod.GET)
	public String accWagePayBatchAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("wage_code",mapVo.get("wage_code"));
		
		mode.addAttribute("acc_year",mapVo.get("acc_year"));
		
		mode.addAttribute("acc_month",mapVo.get("acc_month"));
		
		return "hrp/acc/accwagepay/accWagePayBatchAdd";

	}
	
	/**
	 * 【工资管理-工资数据-工资录入】：添加页面
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/accWagePayAddPage", method = RequestMethod.GET)
	public String accWagePayAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("wage_code",mapVo.get("wage_code"));
		mode.addAttribute("acc_year",mapVo.get("year_month").toString().split("\\.")[0]);
		mode.addAttribute("acc_month",mapVo.get("year_month").toString().split("\\.")[1]);
		mode.addAttribute("wage_name",mapVo.get("wage_name"));
		mode.addAttribute("scheme_name",mapVo.get("scheme_name"));
		mode.addAttribute("scheme_code",mapVo.get("scheme_code"));
		return "hrp/acc/accwagepay/accWagePayAdd";
	}
	
	/**
	 * 【工资管理-工资数据-工资录入】：批量修改页面
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/accWagePayBatchUpdatePage", method = RequestMethod.GET)
	public String accWagePayBatchUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("wage_code",mapVo.get("wage_code"));
		
		mode.addAttribute("acc_year",mapVo.get("year_month").toString().split("\\.")[0]);
		
		mode.addAttribute("acc_month",mapVo.get("year_month").toString().split("\\.")[1]);
		
		mode.addAttribute("wage_name",mapVo.get("wage_name"));
		
		mode.addAttribute("scheme_name",mapVo.get("scheme_name"));
		
		mode.addAttribute("scheme_code",mapVo.get("scheme_code"));
		
		return "hrp/acc/accwagepay/accWagePayBatchUpdate";

	}
	
	/**
	*工资数据<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accwagepay/addAccWagePay", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccWagePay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
			mapVo.put("user_id", SessionManager.getUserId());
			
			mapVo.put("create_date", new Date());
			
		String accWagePayJson = accWagePayService.addAccWagePay(mapVo);

		return JSONObject.parseObject(accWagePayJson);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accwagepay/addBatchAccWagePay", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addBatchAccWagePay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
			mapVo.put("user_id", SessionManager.getUserId());
			
			mapVo.put("create_date", new Date());
			
			String [] res = mapVo.get("item").toString().split(";");
			
			String [] money = mapVo.get("money").toString().split(";");
			
			String sql ="";
			
			String sqls = "";
			
			for (int i = 1; i < res.length; i++) {
				
				sql+=","+res[i];
				
				sqls+=","+money[i];
				
			}
			
			mapVo.put("sql", sql);
			
			mapVo.put("sqls", sqls);
			
		
		String accWagePayJson = accWagePayService.addAccWagePayByList(mapVo);

		return JSONObject.parseObject(accWagePayJson);
		
	}
	
	/**
	 * 【工资管理-工资数据-工资录入】：主页面-右侧查询
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/queryAccWagePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWagePay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("emp_code") != null) {
			// 用来转换大写
			mapVo.put("emp_code", mapVo.get("emp_code").toString().toUpperCase());
		}
		String accWagePay = accWagePayService.queryAccWagePay(getPage(mapVo));
		return JSONObject.parseObject(accWagePay);
	}
	
	/**
	*工资数据<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accwagepay/deleteAccWagePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWagePay(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String [] res = id.split("@");
			
            mapVo.put("pay_id", res[0]);//实际实体类变量
            
            mapVo.put("group_id", res[1]);
            
            mapVo.put("hos_id", res[2]);
            
            mapVo.put("copy_code", res[3]);
            
            mapVo.put("wage_code", res[4]);
            
            mapVo.put("acc_year", res[5]);
            
            mapVo.put("acc_month", res[6]);
            
            mapVo.put("emp_id", res[7]);
           
            listVo.add(mapVo);
        }
		
		try{
			
			String accWagePayJson = accWagePayService.deleteBatchAccWagePay(listVo);
			
			return JSONObject.parseObject(accWagePayJson);
		
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		
		}
			
	}
	
	@RequestMapping(value = "/hrp/acc/accwagepay/deleteAllAccWagePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAllAccWagePay(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		Map<String, Object> mapVo=new HashMap<String, Object>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
            mapVo.put("group_id", res[0]);
            
            mapVo.put("hos_id", res[1]);
            
            mapVo.put("copy_code", res[2]);
            
            mapVo.put("wage_code", res[3]);
            
            mapVo.put("acc_year", res[4]);
            
            mapVo.put("acc_month", res[5]);
           
        }
		
		String AccWagePayJson = accWagePayService.deleteAccWagePay(mapVo);
	   
		return JSONObject.parseObject(AccWagePayJson);
			
	}
	
	/**
	 * 【工资管理-工资数据-工资录入】：职工工资修改页面
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/accWagePayUpdatePage", method = RequestMethod.GET)
	public String accWagePayUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("wage_code", mapVo.get("wage_code").toString());
		mode.addAttribute("acc_year", mapVo.get("acc_year").toString());
		mode.addAttribute("acc_month", mapVo.get("acc_month").toString());
		mode.addAttribute("emp_id", mapVo.get("emp_id").toString());
		mode.addAttribute("emp_name", mapVo.get("emp_name").toString());
		mode.addAttribute("dept_name", mapVo.get("dept_name").toString());
		return "hrp/acc/accwagepay/accWagePayUpdate";
	}
	
	/**
	*工资数据<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accwagepay/updateAccWagePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWagePay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
		
		String accWagePayJson = accWagePayService.updateAccWagePay(mapVo);
		
		return JSONObject.parseObject(accWagePayJson);
	}
	
	/**
	 * 【工资管理-工资数据-工资录入】：计算
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/updateBatchAccWagePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchAccWagePay(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String accWagePayJson = accWagePayService.collectBatchAccWagePay(mapVo);
		return JSONObject.parseObject(accWagePayJson);
	}
	
	@RequestMapping(value = "/hrp/acc/accwagepay/updateAccWagePayOfBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWagePayOfBatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			} 
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
		
		String accWagePayJson = accWagePayService.updateAccWagePayOfBatch(mapVo);
		
		return JSONObject.parseObject(accWagePayJson);
	}
	
//	/**
//	 * 工资数据<BR>
//	 * 导入
//	 */
//	@RequestMapping(value = "/hrp/acc/accwagepay/importAccWagePay", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> importAccWagePay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
//		String accWagePayJson = accWagePayService.importAccWagePay(mapVo);
//		return JSONObject.parseObject(accWagePayJson);
//	}
	
	/**
	 * 【工资管理-工资数据-工资录入】：工资录入列出工资项
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/queryTrendColumn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTrendColumn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accWagePayJson = accWagePayService.queryTrendColumn(mapVo);
		return JSONObject.parseObject(accWagePayJson);
	}
	
	/**
	 * 
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/queryEmpByWageCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEmpByWageCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String accWagePay = accWagePayService.queryEmpByWageCode(mapVo);
		return JSONObject.parseObject(accWagePay);
	}
	
	@RequestMapping(value = "/hrp/acc/accwagepay/queryAccWageItemPay", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccWageItemPay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String accWagePay = accWagePayService.queryAccWageItemPay(getPage(mapVo));

		return JSONObject.parseObject(accWagePay);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accwagepay/queryAccWagePayGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWagePayGrid(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//如果工资套为空默认为0，如果选择方案工资套就为空
		if("".equals(mapVo.get("wage_code"))){
			
			mapVo.put("wage_code", "0");
			
		}
		
		/*if(!"".equals(mapVo.get("scheme_id"))){
			
			mapVo.put("scheme_id", "");
			
		}*/
		
        String accWagePay = accWagePayService.queryAccWagePayGrid(mapVo);

		return JSONObject.parseObject(accWagePay);
		
	}
	
	//下载导入模版
	@RequestMapping(value="hrp/acc/accwagepay/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
		
		List<List> list = new ArrayList();
		
		List<String> listdata = new ArrayList<String>();
		
		//Map<String, Object> entityMap = new HashMap<String, Object>();
		
		//String wage_scheme=mapVo.get("scheme_id").toString();
		
		listdata.add("年度");
		
		listdata.add("月份");
		
		listdata.add("职工编码");
		
		listdata.add("职工名称");
		
		listdata.add("备注");
		
		//if("".equals(wage_scheme) || null == wage_scheme){
			
			/*listdata.add("职工分类");
			
			listdata.add("部门名称");
			
			listdata.add("发放方式");*/
			
		//}
		
		mapVo.put("group_id", SessionManager.getGroupId());
	       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> itemList = accWagePayService.queryAccWageItemList(mapVo);
		
		for (int i = 0; i < itemList.size(); i++) {
			
			Map<String, Object> map = itemList.get(i);
			
			if(!"".equals(map.get("ITEM_NAME"))&& null != map.get("ITEM_NAME")){
				
				listdata.add(map.get("ITEM_NAME").toString());
				
			}
			
		}

		list.add(listdata);
		
		String ctxPath = request.getSession().getServletContext().getRealPath("/")
				+ "\\" + "excelTemplate\\"+"\\acc\\downTemplate\\";
		
		String downLoadPath = ctxPath + "工资录入.xlsx";
		
		ExcelWriter.exportExcel(new File(downLoadPath), list);
		
		printTemplate(request, response, "acc\\downTemplate", "工资录入.xlsx");

		return null; 
	 }
	
	/**
	 * 导入数据页面
	 * */
	@RequestMapping(value = "/hrp/acc/accwagepay/accWagePayImportPage", method = RequestMethod.GET)
	public String accLederImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("scheme_id", mapVo.get("scheme_id"));
		
		mode.addAttribute("wage_code", mapVo.get("wage_code"));
		
		mode.addAttribute("group_id", mapVo.get("group_id"));
		
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		
		return "hrp/acc/accwagepay/accWagePayImport";
	}
	
	@RequestMapping(value="/hrp/acc/accwagepay/readAccWagePayFiles",method = RequestMethod.POST)  
    public String readAccWagePayFiles(@RequestParam Map<String, Object> entityMap,Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,String rules,Model mode) throws IOException { 
			
		List<Map<String, Object>> list_err = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
		
		StringBuffer err_sb= new StringBuffer();
		
		Boolean error = false;
		
		if(list.size()>0){
			
			String [] wage= list.get(1);
			
			if (ExcelReader.validExceLColunm(wage,0)) {
				
				entityMap.put("acc_year", wage[0]);
				
			} else {
				
				err_sb.append("会计年度为空  ");
				
			}

			if (ExcelReader.validExceLColunm(wage,1)) {
				
				entityMap.put("acc_month", wage[1]);
				
			} else {
				
				err_sb.append("会计月份为空  ");
				
			}
			
			String msg =accWagePayService.queryAccWagePerm(entityMap);
			
			if("1".equals(msg)){
				
				err_sb.append("导入工资年度与月份已经结转！！！  ");
				
			}
			
		}
		
		if(err_sb.toString().length() > 0){
			
			entityMap.put("error_type",err_sb.toString());
			
			list_err.add(entityMap);
			
		}else{
		
		try {
			
			for (int i = 1; i < list.size(); i++) {
				
				 err_sb = new StringBuffer();
				
				StringBuffer sql = new StringBuffer("");
				
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
						if(!temp[0].equals(entityMap.get("acc_year"))){
							err_sb.append("导入模板与工资年度不符  ");
						}else{
							entityMap.put("acc_year", temp[0]);
							mapVo.put("acc_year", temp[0]);
						}
						
					} else {
						err_sb.append("会计年度为空  ");
					}

					if (ExcelReader.validExceLColunm(temp,1)) {
						if(!temp[1].equals(entityMap.get("acc_month"))){
							err_sb.append("导入模板与工资年度不符  ");
						}else{
							entityMap.put("acc_month", temp[1]);
							mapVo.put("acc_month", temp[1]);
						}
						
					} else {
						err_sb.append("会计月份为空  ");
					}
					
					if (ExcelReader.validExceLColunm(temp,2)) {
						entityMap.put("emp_code", temp[2].trim());
						mapVo.put("emp_code", temp[2].trim());
					} else {
						err_sb.append("职工编码为空  ");
					}
					
					if (ExcelReader.validExceLColunm(temp,3)) {
						entityMap.put("emp_name", temp[3].trim());
						mapVo.put("emp_name", temp[3].trim());
					} else {
						err_sb.append("职工名称为空  ");
					}
					
					if (ExcelReader.validExceLColunm(temp,4)) {
						entityMap.put("note", temp[4]);
						mapVo.put("note", temp[4]);
					} else {
						entityMap.put("note", "");
						mapVo.put("note","");
					}
					
					int count = accWagePayService.checkEmpNameAndCode(mapVo);
					if (count == 0) {
						err_sb.append("该职工职工号与姓名不一致  ");
					}
					
					
					mapVo.put("state", 1);
					mapVo.put("wage_code", entityMap.get("wage_code"));
					mapVo.put("scheme_id", entityMap.get("scheme_id"));
					mapVo.put("user_id",SessionManager.getUserId());    
					mapVo.put("create_date", new Date());
					
					List<AccWagePay> wagePayList = accWagePayService.queryAccEmpDetail(mapVo);
					
					if(wagePayList.size()>0 && wagePayList != null){
						AccWagePay accWagePay = wagePayList.get(0);
						mapVo.put("emp_id", accWagePay.getEmp_id());
						mapVo.put("emp_no", accWagePay.getEmp_no());
					}else{
						err_sb.append("此工资发放表中不存在该职工  ");
					}
					
					
					
					List<AccWagePay> stateList =  accWagePayService.queryWageState(mapVo);
					
					if(stateList.size() > 0 && stateList != null && stateList.get(0).getPay_state() ==1){
						if(err_sb.toString().length() == 0){
							err_sb.append("本月工资已审核");
						}
					} 
				
					
					if(error==false){
						List<Map<String, Object>> itemList =accWagePayService.queryAccWageItemList(entityMap);
						if((temp.length-5)==itemList.size()){
						
						for (int j = 0; j < itemList.size(); j++) {
							Map<String, Object> map = itemList.get(j);
							if(!ExcelReader.validExceLColunm(temp,j+5)){
								
								entityMap.put(map.get("COLUMN_ITEM").toString(),"0.00");
								
								sql.append(map.get("COLUMN_ITEM").toString()+"="+"0.00"+",");
								
							}else{
								
								mapVo.put(map.get("COLUMN_ITEM").toString(), df.format(Double.parseDouble(temp[j+5])));
								
								entityMap.put(map.get("COLUMN_ITEM").toString(), df.format(Double.parseDouble(temp[j+5])));
								
								if(MyConfig.getSysPara("043").equals("1")){
									String regex = "^(\\+|\\-)";
									
									Pattern pattern = Pattern.compile(regex);
									
									Matcher matcher = pattern.matcher(temp[j+5]);
									
									Boolean exist = matcher.find();
									
									if(exist){
										sql.append(map.get("COLUMN_ITEM").toString() + "= nvl("  + 
												map.get("COLUMN_ITEM").toString()+",0)" +temp[j+5]+",");
									}else{
										/*  东阳需求：无+号也累加工资项 */
										sql.append(map.get("COLUMN_ITEM").toString()+"= nvl("+ 
												map.get("COLUMN_ITEM").toString() + ",0) +" +
												df.format(Double.parseDouble(temp[j+5]))+",");
												
										//sql.append(map.get("COLUMN_ITEM").toString()+"="+df.format(Double.parseDouble(temp[j+5]))+",");
									}
								}else if(MyConfig.getSysPara("043").equals("0")){
									sql.append(map.get("COLUMN_ITEM").toString()+"="+df.format(Double.parseDouble(temp[j+5]))+",");
								}
							}
						}
					}else{
							for (int j = 0; j < list.get(0).length-5; j++) {
								
								entityMap.put("item_name",list.get(0)[j+5]);
								
								Map<String, Object> map = accWagePayService.queryAccWageCulumn(entityMap);
								
								if(null != map&&map.size()>0){
									
									if(!ExcelReader.validExceLColunm(temp,j+5)){
										
											entityMap.put(map.get("COLUMN_ITEM").toString(),"0.00");
											
											sql.append(map.get("COLUMN_ITEM").toString()+"="+"0.00"+",");
											
									}else{
										
										mapVo.put(map.get("COLUMN_ITEM").toString(), temp[j+5]);
										
										entityMap.put(map.get("COLUMN_ITEM").toString(), temp[j+5]);

										if(MyConfig.getSysPara("043").equals("1")){
											String regex = "^(\\+|\\-)";
											
											Pattern pattern = Pattern.compile(regex);
											
											Matcher matcher = pattern.matcher(temp[j+5]);
											
											Boolean exist = matcher.find();
											 
											if(exist){
												sql.append(map.get("COLUMN_ITEM").toString() + "= nvl(" + 
														map.get("COLUMN_ITEM").toString()+",0)" +temp[j+5]+",");
											}else{
												/*  东阳需求：无+号也累加工资项 */
												sql.append(map.get("COLUMN_ITEM").toString()+"= nvl("+ 
														map.get("COLUMN_ITEM").toString() + ",0) +"+
														df.format(Double.parseDouble(temp[j+5]))+",");
														
												//sql.append(map.get("COLUMN_ITEM").toString()+"="+df.format(Double.parseDouble(temp[j+5]))+",");
											}
										}else if(MyConfig.getSysPara("043").equals("0")){
											sql.append(map.get("COLUMN_ITEM").toString()+"="+df.format(Double.parseDouble(temp[j+5]))+",");
										}	
									}
								}else{
									err_sb.append("工资项目【"+list.get(0)[j+5]+"】不存在  "); 
									error=true;
								}
							}
						}
					}
				mapVo.put("sql", sql.length() == 0?sql:sql.substring(0, sql.length()-1));
					
				mapList.add(mapVo);    
				if(err_sb.toString().length() > 0){
					mapVo.put("error_type",err_sb.toString());
					list_err.add(mapVo);
				}
			}
			
			if(list_err.size()<=0){
				accWagePayService.importAccWagePay(mapList);
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			Map<String,Object> data_exc = new HashMap<String, Object>();
			data_exc.put("error_type","导入系统出错  ");
			list_err.add(data_exc);
		}
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		return null;
    } 
	
	/**
	 * 【工资管理-工资数据-工资录入】：生成
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/initAccWagePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAccWagePay(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		Map<String, Object> mapVo = new HashMap<String, Object>();
		String[] res = paramVo.split("@");
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("wage_code", res[0]); 
		mapVo.put("acc_year", res[1]);
		mapVo.put("acc_month", res[2]);
		mapVo.put("user_id", SessionManager.getUserId());
		String jsonResult = "";
		try {
			jsonResult= accWagePayService.addAccWagePayByWageEmp(mapVo);
		} catch (Exception e) {
			jsonResult="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		
		return JSONObject.parseObject(jsonResult);
	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/accwagepay/accWagePayDescAddPage", method = RequestMethod.GET)
	public String accWagePayDescAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		AccWagePayDesc accWagePayDesc=accWagePayDescService.queryAccWagePayDescByCode(mapVo);
		
		if(accWagePayDesc != null){
			
			mode.addAttribute("desc_id", accWagePayDesc.getDesc_id());
			
			mode.addAttribute("group_id", accWagePayDesc.getGroup_id());
			
			mode.addAttribute("hos_id", accWagePayDesc.getHos_id());
			
			mode.addAttribute("copy_code", accWagePayDesc.getCopy_code());
			
			mode.addAttribute("wage_code", accWagePayDesc.getWage_code());
			
			mode.addAttribute("acc_year", accWagePayDesc.getAcc_year());
			
			mode.addAttribute("acc_month", accWagePayDesc.getAcc_month());
			
			mode.addAttribute("kined_code", accWagePayDesc.getKined_code());
			
			mode.addAttribute("kined_name", accWagePayDesc.getKined_name());
			
			mode.addAttribute("note", accWagePayDesc.getNote());
			
		}else{
			
			mode.addAttribute("group_id", mapVo.get("group_id"));
			
			mode.addAttribute("hos_id", mapVo.get("hos_id"));
			
			mode.addAttribute("copy_code",mapVo.get("copy_code"));
			
			mode.addAttribute("wage_code", mapVo.get("wage_code"));
			
			mode.addAttribute("acc_year", mapVo.get("acc_year"));
			
			mode.addAttribute("acc_month",mapVo.get("acc_month"));
			
		}
		
		return "hrp/acc/accwagepay/accWagePayDescAdd";

	}
	
	@RequestMapping(value = "/hrp/acc/accwagepay/addOrUpdateAccWagePayDesc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAccWagePayDesc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accWagePayDescJson="";
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());  
		
		}

		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());   
		
		}

		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		}
		
		if("".equals(mapVo.get("desc_id"))|| mapVo.get("desc_id") == null){
			
			 accWagePayDescJson = accWagePayDescService.addAccWagePayDesc(mapVo);
			
		}else{
			
			accWagePayDescJson = accWagePayDescService.updateAccWagePayDesc(mapVo);
			
		}
		
		return JSONObject.parseObject(accWagePayDescJson);
	}
	
	@RequestMapping(value = "/hrp/acc/accwagepay/combineAccWagePayDesc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> combineAccWagePayDesc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accWagePayDescJson="";
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());  
		
		}

		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());   
		
		}

		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		}
		
		accWagePayDescJson = accWagePayService.addAccWagePayCombine(mapVo);
		
		return JSONObject.parseObject(accWagePayDescJson);
	}
	
	@RequestMapping(value = "/hrp/acc/accwagepay/queryAccSchemeEmpKindList", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccSchemeEmpKindList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());  
		
		}

		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());   
		
		}
		
		return  accWagePayService.queryAccSchemeEmpKindList(mapVo);
	   
	}
	
	@RequestMapping(value = "/hrp/acc/accwagepay/queryAccSchemeWageItemList", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccSchemeWageItemList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());  
		
		}

		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());   
		
		}
		
		return  accWagePayService.queryAccSchemeWageItemList(mapVo);
	   
	}
	
	@RequestMapping(value = "/hrp/acc/accwagepay/chooseEmpMainPage", method = RequestMethod.GET)
	public String chooseEmpMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("wage_code",mapVo.get("wage_code"));
		mode.addAttribute("scheme_id",mapVo.get("scheme_id"));
		mode.addAttribute("scheme_type_code",mapVo.get("scheme_type_code"));
		return "hrp/acc/accwagepay/chooseEmpMain";
	}
	
	/**
	 * 【工资管理-工资数据】：主页面-维护方案-导入职工分类
	 * @param mapVo
	 * @param mode
	 * @return 页面
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/chooseEmpKindMainPage", method = RequestMethod.GET)
	public String chooseEmpKindMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("wage_code",mapVo.get("wage_code"));
		mode.addAttribute("scheme_id",mapVo.get("scheme_id"));
		return "hrp/acc/accwagepay/chooseEmpKindMain";
	}
	
	/**
	 * 【工资管理-工资数据】：维护方案-导入工资项
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/chooseItemMainPage", method = RequestMethod.GET)
	public String chooseItemMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("wage_code", mapVo.get("wage_code"));
		mode.addAttribute("scheme_id", mapVo.get("scheme_id"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("is_gzt", mapVo.get("is_gzt"));
		return "hrp/acc/accwagepay/chooseItemMain";
	}
	
	// 【工资管理-工资数据-工薪录入】：主页面-左侧工资方案树
	@RequestMapping(value = "/hrp/acc/accwagepay/queryAccWagePayTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWagePayTree(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String res = accWagePayService.queryAccWagePayTree(mapVo);
		return JSONObject.parseObject(res);
	}
	
	/**
	 * 维护工资方案：查询没有与页面工资方案关联的职工
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/queryAccEmpTetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccEmpTetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		if (mapVo.get("scheme_id") == null || "undefined".equals(mapVo.get("scheme_id"))
			|| "".equals(mapVo.get("scheme_id"))) {
			mapVo.put("scheme_id", "");
		}

		String accWagePay = accWagePayService.queryAccEmpTetail(getPage(mapVo));
		return JSONObject.parseObject(accWagePay);
	}
	
	@RequestMapping(value = "/hrp/acc/accwagepay/extendAccEmpDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendAccEmpDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
	
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
        String accWagePay = accWagePayService.extendAccEmpDetail(mapVo);

		return JSONObject.parseObject(accWagePay);
		
	}
	
	/**
	 * 【工资管理-工资数据-工资录入】：查询职工资明细
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/queryAccEmpDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccEmpDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accWagePay = accWagePayService.queryAccWageEmpDetail(getPage(mapVo));
		return JSONObject.parseObject(accWagePay);
	}
	
	/**
	 * 【工资管理-工资数据-工资录入】：继承
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/extendAccWagePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendAccWagePay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accWagePay = accWagePayService.extendAccWagePay(mapVo);
		return JSONObject.parseObject(accWagePay);
	}
	
	/**
	 * 【工资管理-工数数据-工资录入】：添加页、职工工资更新页，计算
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/collectAccWagePayByPerson", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectAccWagePayByPerson(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String jsonResult = accWagePayService.collectAccWagePayByPerson(mapVo);
		return JSONObject.parseObject(jsonResult);
	}
	
	/**
	 * 【工资管理-工资数据-工资录入】：审核、取消审核
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/auditAccWagePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditAccWagePay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accWagePayJson = accWagePayService.updateBatchAccWagePay(mapVo);
		return JSONObject.parseObject(accWagePayJson);
	}
	
	/**
	 * 【工资管理-工资数据-工资录入】：审核、取消审核
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/queryEmpDictByWageCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpDictByWageCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		if (mapVo.get("is_stop") == null) {
			mapVo.put("is_stop", 0);
		}
			
		String accWagePayJson = hrpAccSelectService.queryEmpDictByWageCode(mapVo);
		return accWagePayJson;
	}
	
	/**
	 * 【工资管理-工资数据-工资录入】：查询职工资明细
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/queryAccEmpDetailByAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccEmpDetailByAdd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accWagePay = accWagePayService.queryAccWageEmpDetailByAdd(mapVo);
		return JSONObject.parseObject(accWagePay);
	}
	
	@RequestMapping(value = "/hrp/acc/accwagepay/accWageEmpDictMainPage", method = RequestMethod.GET)
	public String accWageEmpDictMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", DateUtil.dateToString(new Date(), "yyyyMM"));
		return "hrp/acc/accwagepay/accWageEmpDictMain";
	}
	
}

