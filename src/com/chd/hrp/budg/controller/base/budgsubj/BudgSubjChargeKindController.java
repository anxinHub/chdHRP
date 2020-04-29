package com.chd.hrp.budg.controller.base.budgsubj;

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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgAccSubjShip;
import com.chd.hrp.budg.entity.BudgCostSubj;
import com.chd.hrp.budg.service.base.budgsubj.BudgCostSubjService;
import com.chd.hrp.budg.service.base.budgsubj.BudgSubjChargeKindService;

@Controller
public class BudgSubjChargeKindController  extends BaseController {

	private static Logger logger = Logger.getLogger(BudgSubjChargeKindController.class);
	
	//引入Service服务
	@Resource(name = "budgSubjChargeKindService")
	private final BudgSubjChargeKindService budgSubjChargeKindService = null;
	@Resource(name = "budgCostSubjService")
	private final BudgCostSubjService budgCostSubjService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 (收入预算科目与会计科目对应关系)
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/budg/base/budgsubj/budgchargekind/budgSubjChargeKindMainPage", method = RequestMethod.GET)
	public String budgAccSubjShipMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsubj/budgchargekind/budgSubjChargeKindMain";

	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgchargekind/budgSubjChargeKindShipAddPage", method = RequestMethod.GET)
	public String budgAccSubjShipAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsubj/budgchargekind/budgSubjChargeKindShipAdd";

	}

	/**
	 * @Description 
	 * 查询数据 预算科目与会计科目对应关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgchargekind/queryBudgSubjChargeKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgSubjChargeKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgAccSubjShip = budgSubjChargeKindService.queryBudgSubjChargeKind(getPage(mapVo));

		return JSONObject.parseObject(budgAccSubjShip);
		
	}
	/**
	 *支出预算科目类别维护<BR>
	 *保存
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgchargekind/saveBudgSubjChargeKind", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> saveBudgAccSubjIncomeShip(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("budg_year", id.split("@")[0]);
			mapVo.put("budg_subj_code", id.split("@")[1]);
			mapVo.put("budg_subj_name", id.split("@")[2]);
			mapVo.put("charge_kind_code", id.split("@")[3]);
			mapVo.put("charge_kind_name", id.split("@")[4]);
			mapVo.put("budg_subj_code_old", id.split("@")[5]);
			mapVo.put("charge_kind_code_old", id.split("@")[6]);
			mapVo.put("flag", id.split("@")[7]);
			listVo.add(mapVo);
		}
		String typeSetJson =budgSubjChargeKindService.save(listVo);		
		return JSONObject.parseObject(typeSetJson);
		
	}
	
/**
	 * @Description 
	 * 更新跳转页面 预算科目与会计科目对应关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgchargekind/budgSubjChargeKindUpdatePage", method = RequestMethod.GET)
	public String budgAccSubjShipUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		mapVo.put("table1", "BUDG_COST_SUBJ");
		
		mapVo.put("table2", "BUDG_COST_SUBJ_TYPE");
		
		BudgAccSubjShip budgAccSubjShip = null;//budgSubjChargeKindService.queryBudgAccSubjShipByCode(mapVo);
		
		mode.addAttribute("group_id", budgAccSubjShip.getGroup_id());
		mode.addAttribute("hos_id", budgAccSubjShip.getHos_id());
		mode.addAttribute("copy_code", budgAccSubjShip.getCopy_code());
		mode.addAttribute("budg_year", budgAccSubjShip.getBudg_year());
		mode.addAttribute("subj_code", budgAccSubjShip.getSubj_code());
		mode.addAttribute("subj_name", budgAccSubjShip.getSubj_name());
		mode.addAttribute("acc_subj_code2", budgAccSubjShip.getAcc_subj_code2());
		mode.addAttribute("acc_subj_name", budgAccSubjShip.getAcc_subj_name());
		mode.addAttribute("subj_type", budgAccSubjShip.getSubj_type());
		mode.addAttribute("subj_type_name", budgAccSubjShip.getSubj_type_name());
		
		return "hrp/budg/base/budgsubj/budgchargekind/budgSubjChargeKindUpdate";
	}
		
	
	
	/**
	 * @Description 
	 * 删除数据 预算科目与会计科目对应关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgchargekind/deleteBudgSubjChargeKind",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgSubjChargeKind(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3]);
				mapVo.put("budg_subj_code", ids[4]);
				mapVo.put("charge_kind_code", ids[5]);				
	      listVo.add(mapVo);
	      
	    }
	    
			String budgAccSubjShipJson = budgSubjChargeKindService.deleteBatchBudgSubjChargeKind(listVo);
			
	  return JSONObject.parseObject(budgAccSubjShipJson);
			
	}
	@RequestMapping(value="/hrp/budg/base/budgsubj/budgchargekind/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate1(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","预算科目与收费类别对应关系.xls");
	    
	    return null; 
	 }
	/**
	 * @Description 
	 * 导入跳转页面 预算科目与会计科目对应关系表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgchargekind/budgSubjChargeKindImportPage", method = RequestMethod.GET)
	public String budgAccSubjCostShipImportPage(Model mode,String budg_year) throws Exception {
			mode.addAttribute("budg_year", budg_year);
			return "hrp/budg/base/budgsubj/budgchargekind/budgSubjChargeKindImport";
	}
	 /**
	 * @Description 
	 * 导入数据 预算科目与会计科目对应关系表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/budg/base/budgsubj/budgchargekind/readBudgSubjChargeKindFiles",method = RequestMethod.POST)  
	 public String readBudgSubjChargeKindFiles(Plupload plupload,HttpServletRequest request,String budg_year,
   		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<String> list_err = new ArrayList<String>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		Map<String,Object> mapVo=null;
		try {
			for (int i = 1; i < list.size(); i++) {
				StringBuffer err_sb = new StringBuffer();
								
				String temp[] = list.get(i);// 行
				mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					if(temp[0].equals(error[0]) && temp[2].equals(error[2])){
						err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
					}
						
				}
				
					if (StringTool.isNotBlank(temp[0])) {
						mapVo.put("budg_subj_code", temp[0]);
					} else {
						
						err_sb.append("预算科目编码为空;");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
						mapVo.put("budg_subj_name", temp[1]);
					} else {						
						err_sb.append("预算科目名称为空;");
						
					}
					
					if (StringTool.isNotBlank(temp[2])) {					
						mapVo.put("charge_kind_code", temp[2]);
						int count = budgSubjChargeKindService.querySubjCodeExist(mapVo);
						if(count == 0 ){
							err_sb.append("该年度会计科目编码不存在或已停用;");
						}
					} else {
						
						err_sb.append("收费类别编码为空;");
						
					}
					
					if (StringTool.isNotBlank(temp[3])) {
						
						mapVo.put("charge_kind_name", temp[3]);
						
						
					} else {
						
						err_sb.append("收费类别名称为空;");
						
					}
					mapVo.put("budg_year", budg_year);
										
					int data_exc_extis = budgSubjChargeKindService.queryIsExist(mapVo);
				
					if (data_exc_extis >0 ) {
					
						err_sb.append("数据已经存在！ ");
					
					}
					if (err_sb.toString().length() > 0) {
										
					list_err.add(err_sb.toString());
					
				} else {
					
					addList.add(mapVo);
					
					
				}
				
			}
			if(list_err.size() == 0 ){
				String dataJson = budgSubjChargeKindService.save(addList);
			}
			
		} catch (DataAccessException e) {
			
			list_err.add("导入系统出错");
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
   }  
}
