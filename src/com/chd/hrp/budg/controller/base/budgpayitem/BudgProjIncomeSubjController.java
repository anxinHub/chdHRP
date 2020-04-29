/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgpayitem;
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
import com.chd.hrp.budg.entity.BudgProjIncomeSubj;
import com.chd.hrp.budg.service.base.budgpayitem.BudgProjIncomeSubjService;
/**
 * 
 * @Description:
 * 项目与收入预算科目对应关系
 * @Table:
 * BUDG_PROJ_INCOME_SUBJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgProjIncomeSubjController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgProjIncomeSubjController.class);
	
	//引入Service服务
	@Resource(name = "budgProjIncomeSubjService")
	private final BudgProjIncomeSubjService budgProjIncomeSubjService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/projincomesubj/budgProjIncomeSubjMainPage", method = RequestMethod.GET)
	public String budgProjIncomeSubjMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgpayitem/projincomesubj/budgProjIncomeSubjMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/projincomesubj/budgProjIncomeSubjAddPage", method = RequestMethod.GET)
	public String budgProjIncomeSubjAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgpayitem/projincomesubj/budgProjIncomeSubjAdd";

	}

	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/projincomesubj/addBudgProjIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgProjIncomeSubj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("budg_year", ids[0])   ;
			mapVo.put("fund_nature", ids[1])   ;
			mapVo.put("subj_code", ids[2]);
			mapVo.put("proj_id", ids[3]);
			listVo.add(mapVo);
      
		}
	      
       
		String budgProjIncomeSubjJson = budgProjIncomeSubjService.addBatch(listVo);

		return JSONObject.parseObject(budgProjIncomeSubjJson);
		
	}
	
	/**
	 * @Description 
	 * 删除  旧的对应关系数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/projincomesubj/deleteBudgProjIncomeSubjData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgProjIncomeSubjData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
	      
       
		String budgProjIncomeSubjJson = budgProjIncomeSubjService.delete(mapVo);

		return JSONObject.parseObject(budgProjIncomeSubjJson);
		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/projincomesubj/budgProjIncomeSubjUpdatePage", method = RequestMethod.GET)
	public String budgProjIncomeSubjUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		BudgProjIncomeSubj budgProjIncomeSubj = new BudgProjIncomeSubj();
    
		budgProjIncomeSubj = budgProjIncomeSubjService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgProjIncomeSubj.getGroup_id());
		mode.addAttribute("hos_id", budgProjIncomeSubj.getHos_id());
		mode.addAttribute("copy_code", budgProjIncomeSubj.getCopy_code());
		mode.addAttribute("budg_year", budgProjIncomeSubj.getBudg_year());
		mode.addAttribute("proj_id", budgProjIncomeSubj.getProj_id());
		mode.addAttribute("fund_nature", budgProjIncomeSubj.getFund_nature());
		mode.addAttribute("subj_code", budgProjIncomeSubj.getSubj_code());
		
		return "hrp/budg/base/budgpayitem/projincomesubj/budgProjIncomeSubjUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/projincomesubj/updateBudgProjIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgProjIncomeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgProjIncomeSubjJson = budgProjIncomeSubjService.update(mapVo);
		
		return JSONObject.parseObject(budgProjIncomeSubjJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/projincomesubj/addOrUpdateBudgProjIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgProjIncomeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgProjIncomeSubjJson ="";
		

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
	  
		budgProjIncomeSubjJson = budgProjIncomeSubjService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgProjIncomeSubjJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/projincomesubj/deleteBudgProjIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgProjIncomeSubj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("proj_id", ids[4])   ;
				mapVo.put("fund_nature", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgProjIncomeSubjJson = budgProjIncomeSubjService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgProjIncomeSubjJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/projincomesubj/queryBudgProjIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjIncomeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("budg_year") == null){
			
		mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgProjIncomeSubj = budgProjIncomeSubjService.query(getPage(mapVo));

		return JSONObject.parseObject(budgProjIncomeSubj);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/projincomesubj/budgProjIncomeSubjImportPage", method = RequestMethod.GET)
	public String budgProjIncomeSubjImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgpayitem/projincomesubj/budgProjIncomeSubjImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgpayitem/projincomesubj/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","项目与预算支出科目对应关系模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgpayitem/projincomesubj/readBudgProjIncomeSubjFiles",method = RequestMethod.POST)  
    public String readBudgProjIncomeSubjFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgProjIncomeSubj> list_err = new ArrayList<BudgProjIncomeSubj>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] sourceList  = {"1","2","3","4"};
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgProjIncomeSubj budgProjIncomeSubj = new BudgProjIncomeSubj();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])){
							err_sb.append("第"+i+"行数据与第 "+j+"行预算年度、项目、资金性质相同 ,多个预算支出科目 不允许对应相同的预算年度、项目、资金性质;");
						}
						
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])&& temp[3].equals(error[3])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
					} 
					if (StringTool.isNotBlank(temp[0])) {
						
						budgProjIncomeSubj.setBudg_year(temp[0]);
			    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
						budgProjIncomeSubj.setPayment_item_code(temp[1]);
						
			    		mapVo.put("proj_code", temp[1]);
		    		
			    		Map<String,Object> count = budgProjIncomeSubjService.queryItemCodeExist(mapVo);
			    		
			    		if( count != null ){
			    			
			    			mapVo.put("proj_id", count.get("proj_id"));
			    			
			    		}else{
			    			
			    			err_sb.append("项目编码不存在或已停用;");
			    		}
					
					} else {
						
						err_sb.append("项目编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						if(Arrays.asList(sourceList).contains(temp[2])){
							
							budgProjIncomeSubj.setFund_nature(temp[2]);
				    		mapVo.put("fund_nature", temp[2]);
						}else{
							err_sb.append("资金性质编码不符合规则(1-4)");
						}
					
					} else {
						
						err_sb.append("资金性质编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
						budgProjIncomeSubj.setSubj_code(temp[3]);
			    		mapVo.put("subj_code", temp[3]);
			    		
			    		int count = budgProjIncomeSubjService.queryIncomeSubjByCode(mapVo);
						if(count == 0 ){
							err_sb.append("该年度收入预算科目不是末级科目或不存在;");
						}
					
					} else {
						
						err_sb.append("收入预算科目编码为空;");
						
					}
					 
					
				BudgProjIncomeSubj data_exc_extis = budgProjIncomeSubjService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgProjIncomeSubj.setError_type(err_sb.toString());
					
					list_err.add(budgProjIncomeSubj);
					
				} else {
			  
					addList.add(mapVo) ;
					
				}
				
			}
			
			if(list_err.size() == 0){
				
				String dataJson = budgProjIncomeSubjService.addBatch(addList);
			}
		} catch (DataAccessException e) {
			
			BudgProjIncomeSubj data_exc = new BudgProjIncomeSubj();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
  
	
	/**
	 * 项目信息查询 （添加、修改页面用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/projincomesubj/queryBudgProjDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgProjDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String item = budgProjIncomeSubjService.queryBudgProjDict(getPage(mapVo));
		
		return JSONObject.parseObject(item);

	}
    
	/**
	 * 继承
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/projincomesubj/copyData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> copyData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String item = budgProjIncomeSubjService.copyData(mapVo);
		
		return JSONObject.parseObject(item);

	}
	
}

