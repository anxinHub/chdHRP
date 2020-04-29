/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationplan.downtoup.budg;
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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgIndexDict;
import com.chd.hrp.budg.entity.BudgWorkDeptYear;
import com.chd.hrp.budg.entity.BudgWorkHosMonth;
import com.chd.hrp.budg.entity.BudgWorkHosYear;
import com.chd.hrp.budg.service.base.BudgSelectService;
import com.chd.hrp.budg.service.base.budgindexdict.BudgIndexDictService;
import com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkHosYearService;
/**
 * 
 * @Description:
 * 医院年度业务预算 
 * @Table:
 * BUDG_WORK_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class WorkHosYearController extends BaseController{
	
	private static Logger logger = Logger.getLogger(WorkHosYearController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkHosYearService")
	private final BudgWorkHosYearService budgWorkHosYearService = null;
	
	@Resource(name = "budgSelectService")
	private final BudgSelectService budgSelectService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosyearbudg/budgWorkHosYearMainPage", method = RequestMethod.GET)
	public String budgWorkHosYearMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/downtoup/hosyearbudg/budgWorkHosYearMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosyearbudg/budgWorkHosYearAddPage", method = RequestMethod.GET)
	public String budgWorkHosYearAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/downtoup/hosyearbudg/budgWorkHosYearAdd";

	}

	/**
	 * @Description 
	 * 添加数据 医院年度业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosyearbudg/saveBudgWorkHosYearDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgWorkHosYearDown(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("index_code", ids[1])   ;
			
			mapVo.put("budg_value", ids[2]);
			mapVo.put("remark", ids[3]);
			
			mapVo.put("rowNo", ids[4]);
			mapVo.put("flag", ids[5]);
			
			//构建 查询上年收入参数Map 
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("group_id", SessionManager.getGroupId())   ;
			map.put("hos_id", SessionManager.getHosId())   ;
			map.put("copy_code", SessionManager.getCopyCode())   ;
			map.put("year", Integer.parseInt(ids[0]) -1 )   ;
			map.put("index_code", ids[1]);
			
			Map<String,Object> incomeMap = JSONObject.parseObject(budgWorkHosYearService.queryLastYearWork(map)) ;
			
			if(incomeMap != null ){
				if(incomeMap.get("last_year_workload") == null){
					mapVo.put("last_year_workload", "");
				}else{
					mapVo.put("last_year_workload", String.valueOf(incomeMap.get("last_year_workload")));
				}
			}else{
				mapVo.put("last_year_workload", "");
			}
			
			mapVo.put("count_value", "");
			mapVo.put("grow_rate", "");
			mapVo.put("grow_value", "");
			
			listVo.add(mapVo);
		}
		
		String budgWorkHosYearJson = null ;
		
		try {
			
			budgWorkHosYearJson = budgWorkHosYearService.save(listVo);
			
		} catch (Exception e) {
			
			budgWorkHosYearJson = e.getMessage() ;
		}

		return JSONObject.parseObject(budgWorkHosYearJson);
			
	}
	/**
	 * @Description 
	 * 更新跳转页面 医院年度业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosyearbudg/budgWorkHosYearUpdatePage", method = RequestMethod.GET)
	public String budgWorkHosYearUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			if(mapVo.get("year") == null){
	        mapVo.put("year", SessionManager.getAcctYear());
			}
			
			BudgWorkHosYear budgWorkHosYear = budgWorkHosYearService.queryByCode(mapVo);
			
			mode.addAttribute("group_id", budgWorkHosYear.getGroup_id());
			mode.addAttribute("hos_id", budgWorkHosYear.getHos_id());
			mode.addAttribute("copy_code", budgWorkHosYear.getCopy_code());
			mode.addAttribute("year", budgWorkHosYear.getYear());
			mode.addAttribute("index_code", budgWorkHosYear.getIndex_code());
			mode.addAttribute("count_value", budgWorkHosYear.getCount_value());
			mode.addAttribute("budg_value", budgWorkHosYear.getBudg_value());
			mode.addAttribute("remark", budgWorkHosYear.getRemark());
			mode.addAttribute("grow_rate", budgWorkHosYear.getGrow_rate());
			mode.addAttribute("grow_value", budgWorkHosYear.getGrow_value());
			mode.addAttribute("last_year_workload", budgWorkHosYear.getLast_year_workload());
			mode.addAttribute("hos_suggest", budgWorkHosYear.getHos_suggest());
			mode.addAttribute("dept_suggest_sum", budgWorkHosYear.getDept_suggest_sum());
		
		return "hrp/budg/business/compilationplan/downtoup/hosyearbudg/budgWorkHosYearUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院年度业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosyearbudg/updateBudgWorkHosYearDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgWorkHosYearDown(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("index_code", ids[1])   ;
			mapVo.put("budg_value", ids[2]);
			mapVo.put("last_year_workload", ids[3]);
			
			mapVo.put("remark", ids[4]);
			if("-1".equals(ids[5])){
				mapVo.put("hos_suggest", "");
			}else{
				mapVo.put("hos_suggest", ids[5]);
			}
			mapVo.put("count_value", "");
			mapVo.put("grow_rate", "");
			mapVo.put("grow_value", "");
			mapVo.put("dept_suggest_sum", "");
			
			listVo.add(mapVo);
		}
	  
		String budgHosIndependentSubjJson = budgWorkHosYearService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgHosIndependentSubjJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医院年度业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosyearbudg/addOrUpdateBudgWorkHosYearDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgWorkHosYearDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgHosIndependentSubjJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		budgHosIndependentSubjJson = budgWorkHosYearService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgHosIndependentSubjJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院年度业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosyearbudg/deleteBudgWorkHosYearDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgWorkHosYearDown(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("index_code", ids[4]);
				
				listVo.add(mapVo);
	      
	    }
	    
		String budgHosIndependentSubjJson = budgWorkHosYearService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgHosIndependentSubjJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院年度业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosyearbudg/queryBudgWorkHosYearDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkHosYearDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgHosIndependentSubj = budgWorkHosYearService.query(getPage(mapVo));

		return JSONObject.parseObject(budgHosIndependentSubj);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医院年度业务预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosyearbudg/budgWorkHosYearImportPage", method = RequestMethod.GET)
	public String budgWorkHosYearImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/downtoup/hosyearbudg/budgWorkHosYearImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院年度业务预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationplan/downtoup/hosyearbudg/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business\\downtotop","医院年度业务预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院年度业务预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationplan/downtoup/hosyearbudg/readBudgWorkHosYearFiles",method = RequestMethod.POST)  
    public String readBudgWorkHosYearFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> errMap = new HashMap<String,Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1]) && temp[2].equals(error[2])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
							
					}
		    		
					if (StringTool.isNotBlank(temp[0])) {
						
						errMap.put("year",temp[0]);
						
			    		mapVo.put("year", temp[0]);
			    		
			    		mapVo.put("budg_year", temp[0]);
		    		
					} else {
						
						err_sb.append("预算年度为空 ;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
							
						errMap.put("index_code",temp[1]);
			    		mapVo.put("index_code", temp[1]);
			    		
			    		mapVo.put("budg_level", "03");
			    		
			    		String index = budgSelectService.qureyBudgIndexFromPlan(mapVo);
			    		
						if(index.length() == 2 ){
							
							err_sb.append("在所填预算年度业务预算编制方案中该指标编码未设置方案;");
						}
					
					} else {
						
						err_sb.append("指标编码为空 ;");
						
					}
					
					
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						errMap.put("last_year_workload",Double.valueOf(temp[2]));
			    		mapVo.put("last_year_workload", temp[2]);
						
					} else {
						
						err_sb.append("上年业务量为空;");
						
					}
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						errMap.put("budg_value",Double.valueOf(temp[3]));
			    		mapVo.put("budg_value", temp[3]);
						
					} else {
						
						err_sb.append("预算值为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						errMap.put("remark",temp[4]);
			    		mapVo.put("remark", temp[4]);
					
					}else{
						mapVo.put("remark", "");
					}
					 
					/*if (ExcelReader.validExceLColunm(temp,5)) {
						
						errMap.put("hos_suggest",Double.valueOf(temp[5]));
			    		mapVo.put("hos_suggest", temp[5]);
					
					}else{
						mapVo.put("hos_suggest", "");
					}*/
					 
					int count = budgWorkHosYearService.queryDataExist(mapVo);
					
					if (count >0 ) {
						
						err_sb.append("数据已经存在！ ");
						
					}
					
				if (err_sb.toString().length() > 0) {
					
					errMap.put("error_type",err_sb.toString());
					
					list_err.add(errMap);
					
				} else {
					
					mapVo.put("count_value", "");
					mapVo.put("grow_rate", "");
					mapVo.put("grow_value", "");
					
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0 ){
				
				String dataJson = budgWorkHosYearService.addBatch(addList);
			}
			
			
		} catch (DataAccessException e) {
			
			Map<String,Object> data_exc = new HashMap<String,Object>();
			
			data_exc.put("error_type","导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
    
	/**
	 * 查询 所传 指标的 上年业务量
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosyearbudg/queryLastYearWork", method = RequestMethod.POST)
	@ResponseBody
	public String queryLastYearWork(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("budg_level", "01") ;
		
		int year = Integer.parseInt(String.valueOf(mapVo.get("budg_year")))  - 1 ;
		
		mapVo.put("year", year) ;
		
		String str  = budgWorkHosYearService.queryLastYearWork(mapVo) ;

		return str;

	}
	
	/**
	 * 汇总
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosyearbudg/collectBudgWorkHosYearDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectBudgWorkHosYearDown(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String collect  = budgWorkHosYearService.collectBudgWorkHosYearDown(mapVo) ;

		return JSONObject.parseObject(collect);

	}
	
	/**
	 * @Description 
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosyearbudg/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			mapVo.put("budg_level", "03");//预算层次（LEVEL） 01医院年度	02医院月份 03科室年度 04科室月份

			// 查询要生成的数据
			List<Map<String,Object>> list = budgWorkHosYearService.queryData(mapVo);
			
			for(Map<String, Object> item : list ){
				
				//根据主键  查询数据是否存在
				int count  = budgWorkHosYearService.queryDataExist(item);
				
				//不存在  组装 生成数据
				if(count == 0){
					
					//构建 查询上年业务量  参数 Map
					Map<String,Object> paraMap =  new HashMap<String,Object>();
					
					paraMap.put("group_id", SessionManager.getGroupId())   ;
					paraMap.put("hos_id", SessionManager.getHosId())   ;
					paraMap.put("copy_code", SessionManager.getCopyCode())   ;
					paraMap.put("budg_year", item.get("year"));
					paraMap.put("index_code", item.get("index_code"));
					paraMap.put("year", Integer.parseInt(String.valueOf(item.get("year")))-1);
					
					mapVo.put("budg_level", "01");//预算层次（LEVEL） 01医院年度	02医院月份 03科室年度 04科室月份

					
					//查询上年业务量
					Map<String,Object> map = JSONObject.parseObject(budgWorkHosYearService.queryLastYearWork(paraMap));
					
					if(map == null){
						
						item.put("last_year_workload","");
						
					}else{
						
						item.put("last_year_workload", map.get("last_year_workload"));
					}
					item.put("count_value", "");
					item.put("budg_value", "");
					item.put("remark", "");
					item.put("grow_rate", "");
					item.put("grow_value", "");
					item.put("hos_suggest", "");
					item.put("dept_suggest_sum", "");
			        listVo.add(item); 
					
				}
			}
			
			String budgWorkDeptYearJson = null ;
			
				
			if(listVo.size() > 0 ){
				
				budgWorkDeptYearJson =budgWorkHosYearService.addBatch(listVo);
		         
			}else{
				budgWorkDeptYearJson = "{\"error\":\"数据已全部生成,无需再次生成.\"}";
			}
				
			return JSONObject.parseObject(budgWorkDeptYearJson);	
	} 	
}

