package com.chd.hrp.budg.controller.project.begin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.budg.dao.project.begin.BudgProjBeginMapper;
import com.chd.hrp.budg.entity.BudgProjBegain;
import com.chd.hrp.budg.entity.BudgProjBegainDetail;
import com.chd.hrp.budg.service.base.BudgSelectService;
import com.chd.hrp.budg.service.project.begin.BudgPaymentItmService;
import com.chd.hrp.budg.service.project.begin.BudgProjBegainDetailService;
import com.chd.hrp.budg.service.project.begin.BudgProjBeginService;
import com.chd.hrp.budg.service.project.begin.BudgProjDetailYearService;
import com.chd.hrp.budg.service.project.begin.BudgProjYearService;
/**
 * 
 * @Description:
 * 期初项目预算表
 * @Table:
 * COM_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class BudgProjBegainController extends BaseController {
private static Logger logger = Logger.getLogger(BudgProjBegainController.class);
	//引入Service服务
	@Resource(name = "budgProjBeginService")
	private final BudgProjBeginService budgProjBeginService = null;
	//引入Service服务
	@Resource(name = "budgProjBegainDetailService")
	private final BudgProjBegainDetailService  budgProjBegainDetailService=null;
	//引入Service服务
	@Resource(name = "budgProjYearService")
	private final BudgProjYearService  budgProjYearService=null;
	//引入Service服务
	@Resource(name = "budgProjDetailYearService")
	private final BudgProjDetailYearService budgProjDetailYearService=null;
	//引入Service服务
	@Resource(name = "budgSelectService")
	private  final  BudgSelectService   budgSelectService=null;
	//引入Service服务
	@Resource(name = "budgPaymentItmService")
	private  final  BudgPaymentItmService   budgPaymentItmService=null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/begin/budgBeginProject", method = RequestMethod.GET)
	public String budgFunTypeMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		 if(mapVo.get("group_id") == null){
				
			 mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		//查询 初项目预算记账 状态
		Map<String, Object> stateMap = budgProjBeginService.queryBegainMark(mapVo);
		
		if(stateMap != null){
			String state = stateMap.get("state").toString();
			
			mode.addAttribute("state", state);
		}else{
			mode.addAttribute("state", "0");
		}
		
		

		return "hrp/budg/project/begin/budgProjBegainMain";

	}
	/**
	 * @Description 
	 * 查询数据   期初项目预算表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/begin/queryBeginProject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryComType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String budgProj = budgProjBeginService.query(getPage(mapVo));

		return JSONObject.parseObject(budgProj);		
	}
	/**
	 * @Description 
	 * 查询数据   期初项目预算明细表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/begin/queryBeginProjectDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBeginProjectDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String budgProj = budgProjBegainDetailService.query(getPage(mapVo));

		return JSONObject.parseObject(budgProj);		
	}
	/**
	 * @Description 
	 * 删除数据   期初预算表---以及期初预算的明细表数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/begin/deleteBeginProject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBeginProject(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");	
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("proj_id", ids[3])   ;
				mapVo.put("source_id", ids[4]);
				mapVo.put("budg_year", ids[5])   ;
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgProjBeginJson = budgProjBeginService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgProjBeginJson);
			
	}
	/**
	 * @Description 
	 * 删除数据   期初预算表---以及期初预算的明细表数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/begin/deleteBeginProjectDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBeginProjectDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");	
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("proj_id", ids[3])   ;
				mapVo.put("source_id", ids[4])   ;
				mapVo.put("payment_item_id", ids[5])   ;
				mapVo.put("budg_year", ids[6])   ;
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgProjBeginDetailJson = budgProjBegainDetailService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgProjBeginDetailJson);
			
	}
	
	/**
	 * @Description 
	 * 添加数据   期初预算添加页面跳转
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/begin/addBeginProject", method = RequestMethod.GET)
	public String addBeginProject( Model mode) throws Exception {
		return "hrp/budg/project/begin/budgProjBegainAdd";	
	}
	/**
	 * @Description 
	 * 添加数据
	 * 
	 * 表四：期初项目预算表
	 * 添加数据的时候必须判断BUDG_PROJ_BEGAIN_MARK:state:0,未记账状态才可以进行添加
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/begin/addBudgProjBegain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgProjBegain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 
		   String budg_proj_begain=null;
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;			 
				mapVo.put("checker",null);
				mapVo.put("check_date",null);
				mapVo.put("maker",null);
				mapVo.put("make_date",null);
				mapVo.put("state","01");
				int count =budgProjBeginService.querybudgProjBegain(mapVo);		
				
				// 根据 年度、 项目、资金来源 查询  该年度之后相同项目、资金来源是否存在数据（若存在则不能录入）
				
				int nextData = budgProjBeginService.queryNextDataExist(mapVo);
				
				if(nextData > 0){
					return JSONObject.parseObject("{\"error\":\"录入数据存在该年度以后数据!\"}");
				}
				
				if(count ==  0 ){
					//查询上一年的数据
					String last_year=String.valueOf(Integer.parseInt(mapVo.get("budg_year").toString())-1);
					Map<String,Object>hashMap=new HashMap<String,Object>();
					hashMap.put("group_id", SessionManager.getGroupId());
					hashMap.put("hos_id", SessionManager.getHosId());
					hashMap.put("copy_code", SessionManager.getCopyCode());
					hashMap.put("proj_id", mapVo.get("proj_id"));
					hashMap.put("source_id",mapVo.get("source_id"));
					hashMap.put("budg_year", last_year);
					Map<String,Object> hashMainMap=budgProjBeginService.queryLastMainData(hashMap);
					
					if(hashMainMap==null){
						mapVo.put("b_remain_amount", "0.00");//本年的期初预算余额=上一年【预算余额】
						mapVo.put("b_usable_amount", "0.00"); //本年的期初可用余额=上一年【可用余额】
						double   remain_amount= Double.parseDouble((String) mapVo.get("budg_amount"))-Double.parseDouble((String) mapVo.get("cost_amount"))+Double.parseDouble( mapVo.get("b_remain_amount").toString());
						double   usable_amount= Double.parseDouble((String) mapVo.get("in_amount"))-Double.parseDouble((String) mapVo.get("cost_amount"))+Double.parseDouble( mapVo.get("b_usable_amount").toString());
						
						mapVo.put("remain_amount", String.valueOf(remain_amount));
						mapVo.put("usable_amount", String.valueOf(usable_amount));  	
					}else{
						mapVo.put("b_remain_amount", hashMainMap.get("remain_amount"));//本年的期初预算余额=上一年【预算余额】
						mapVo.put("b_usable_amount", hashMainMap.get("usable_amount")); //本年的期初可用余额=上一年【可用余额】
						double   remain_amount= Double.parseDouble((String) mapVo.get("budg_amount"))-Double.parseDouble((String) mapVo.get("cost_amount"))+Double.parseDouble( mapVo.get("b_remain_amount").toString());
						double   usable_amount= Double.parseDouble((String) mapVo.get("in_amount"))-Double.parseDouble((String) mapVo.get("cost_amount"))+Double.parseDouble( mapVo.get("b_usable_amount").toString());
						mapVo.put("remain_amount", String.valueOf(remain_amount));
						mapVo.put("usable_amount", String.valueOf(usable_amount));
					}
					 budg_proj_begain = budgProjBeginService.add(mapVo);
				}else{
					
					budg_proj_begain="{\"error\":\"数据已存在!\"}";
				}
			
				return JSONObject.parseObject(budg_proj_begain);
		}
	/**
	 * @Description 
	 * 添加数据   
	 * 表三：期初项目预算明细表
	 * 添加数据的时候必须判断BUDG_PROJ_BEGAIN_MARK:state:0,未记账状态才可以进行添加
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/begin/addBudgProjBegainDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgProjBegainDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {		
		   String budg_proj_begain_detail=null;								
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;				
				//查询上一年的数据
				String last_year=String.valueOf(Integer.parseInt(mapVo.get("budg_year").toString())-1);
				Map<String,Object>hashMap=new HashMap<String,Object>();
				hashMap.put("group_id", SessionManager.getGroupId());
				hashMap.put("hos_id", SessionManager.getHosId());
				hashMap.put("copy_code", SessionManager.getCopyCode());
				hashMap.put("proj_id", mapVo.get("proj_id"));
				hashMap.put("source_id",mapVo.get("source_id"));
				hashMap.put("budg_year", last_year);
				hashMap.put("payment_item_id", mapVo.get("payment_item_id"));
				List<Map<String,Object>> hashMainDetailMap=budgProjBeginService.queryLastMainDetailData(hashMap);
				
				// 根据 年度、 项目、资金来源、支出项目  查询  该年度之后相同项目、资金来源、支出项目是否存在数据（若存在则不能录入）
				
				int nextData = budgProjBeginService.queryNextDataDetailExist(mapVo);
				
				if(nextData > 0){
					return JSONObject.parseObject("{\"error\":\"录入数据存在该年度以后数据!\"}");
				}
				
				if(hashMainDetailMap.size()==0){
					mapVo.put("b_remain_amount", "0.00");//本年的期初预算余额=上一年【预算余额】
					mapVo.put("b_usable_amount", "0.00"); //本年的期初可用余额=上一年【可用余额】
					double   remain_amount= Double.parseDouble((String) mapVo.get("budg_amount"))-Double.parseDouble((String) mapVo.get("cost_amount"))+Double.parseDouble(mapVo.get("b_remain_amount").toString());
					mapVo.put("remain_amount", String.valueOf(remain_amount));
				}else{
				for(Map map: hashMainDetailMap){
				mapVo.put("b_remain_amount", map.get("REMAIN_AMOUNT"));//本年的期初预算余额=上一年【预算余额】
				double   remain_amount= Double.parseDouble((String) mapVo.get("budg_amount"))-Double.parseDouble((String) mapVo.get("cost_amount"))+Double.parseDouble((String)mapVo.get("b_remain_amount").toString());
				mapVo.put("remain_amount", String.valueOf(remain_amount));
				
					}
				}		
				int count =budgProjBeginService.querybudgProjBegain(mapVo);	
				int countdetail=budgProjBeginService.querybudgProjBegainDetail(mapVo);	
				
				if(count>0&&countdetail==0){
				budg_proj_begain_detail = budgProjBegainDetailService.add(mapVo);
				
				} else{
					budg_proj_begain_detail="{\"error\":\"数据已存在! 方法 addBudgProjBegain \"}";
					
				}	    
		    return JSONObject.parseObject(budg_proj_begain_detail);
			}
	
	
	
	
	
	
	/**
	 * @Description 
	 * 期初预算项目,修改页面跳转 sate:01新建  02：审核
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/	
	
	@RequestMapping(value = "/hrp/budg/project/begin/updateBeginProject", method = RequestMethod.GET)
	public String budgUpdateBeginProject(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		      
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acct_year") == null){
        mapVo.put("acct_year", SessionManager.getAcctYear());
		}		
		//期初项目预算表
		BudgProjBegain  budgProjBegain =  new BudgProjBegain();		
		budgProjBegain = budgProjBeginService.queryByCode(mapVo);
		mode.addAttribute("group_id", budgProjBegain.getGroup_id());
		mode.addAttribute("hos_id", budgProjBegain.getHos_id());
		mode.addAttribute("copy_code", budgProjBegain.getCopy_code());
		mode.addAttribute("budg_year", budgProjBegain.getBudg_year());
		mode.addAttribute("proj_id", budgProjBegain.getProj_id());
		mode.addAttribute("source_id", budgProjBegain.getSource_id());
		mode.addAttribute("budg_amount", budgProjBegain.getBudg_amount());
		mode.addAttribute("in_amount", budgProjBegain.getIn_amount());
		mode.addAttribute("cost_amount", budgProjBegain.getCost_amount());
		mode.addAttribute("remain_amount", budgProjBegain.getRemain_amount());
		mode.addAttribute("checker", budgProjBegain.getChecker());
		mode.addAttribute("check_date", budgProjBegain.getCheck_date());
		mode.addAttribute("state", budgProjBegain.getState());
		//期初项目预算明细表
		BudgProjBegainDetail budgProjBegainDetail = new BudgProjBegainDetail();
	    
		budgProjBegainDetail = budgProjBegainDetailService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgProjBegainDetail.getGroup_id());
		mode.addAttribute("hos_id", budgProjBegainDetail.getHos_id());
		mode.addAttribute("copy_code", budgProjBegainDetail.getCopy_code());
		mode.addAttribute("budg_year", budgProjBegainDetail.getBudg_year());
		mode.addAttribute("proj_id", budgProjBegainDetail.getProj_id());
		mode.addAttribute("source_id", budgProjBegainDetail.getSource_id());
		mode.addAttribute("payment_item_id", budgProjBegainDetail.getPayment_item_id());
		mode.addAttribute("payment_item_no", budgProjBegainDetail.getPayment_item_no());
		mode.addAttribute("budg_amount", budgProjBegainDetail.getBudg_amount());
		mode.addAttribute("cost_amount", budgProjBegainDetail.getCost_amount());
		mode.addAttribute("remain_amount", budgProjBegainDetail.getRemain_amount());	
		return "hrp/budg/project/begin/budgProjBegainUpdate";
	}
	
	/**
	 * @Description 
	 * 跟新数据：期初预算项目sate:01新建  02：审核
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/	
	@RequestMapping(value = "/hrp/budg/project/begin/updateBudgProjBegain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgProjBegain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String BudgProjExeJson=null;
       for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("budg_year", ids[0])   ;
			mapVo.put("proj_id", ids[1])   ;
			mapVo.put("source_id", ids[2])   ;
			mapVo.put("b_remain_amount", ids[3]);
			mapVo.put("b_usable_amount", ids[4]);
			mapVo.put("budg_amount", ids[5]);
			mapVo.put("in_amount", ids[6]);
			mapVo.put("cost_amount", ids[7]);
			mapVo.put("checker",null);
			mapVo.put("check_date",null);
			mapVo.put("maker",null);
			mapVo.put("make_date",null);
			mapVo.put("state","01");
			double   remain_amount= Double.parseDouble(ids[5])-Double.parseDouble(ids[7])+Double.parseDouble(ids[3]);
			double   usable_amount= Double.parseDouble(ids[6])-Double.parseDouble(ids[7])+Double.parseDouble(ids[4]);
			mapVo.put("remain_amount", remain_amount);
			mapVo.put("usable_amount", usable_amount);  
		
			listVo.add(mapVo);//添加操作		    	      
	    }
		if(listVo.size()>0){
			BudgProjExeJson=budgProjBeginService.updateBatch(listVo);
		}
		return JSONObject.parseObject(BudgProjExeJson);
	}
	/**
	 * @Description 
	 * 跟新数据：期初预算项目sate:01新建  02：审核
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/	
	@RequestMapping(value = "/hrp/budg/project/begin/updateBudgProjBegainDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgProjBegainDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String BudgProjExeJson=null;
       for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("budg_year", ids[0])   ;
			mapVo.put("proj_id", ids[1])   ;
			mapVo.put("source_id", ids[2])   ;
			mapVo.put("payment_item_id", ids[3]);
			mapVo.put("budg_amount", ids[4]);
			mapVo.put("b_remain_amount", ids[5]);
			mapVo.put("cost_amount", ids[6]);  
			mapVo.put("payment_item_no", ids[7]); 
			mapVo.put("checker",null);
			mapVo.put("check_date",null);
			mapVo.put("maker",null);
			mapVo.put("make_date",null);
			mapVo.put("state","01");
			double   remain_amount= Double.parseDouble(ids[4])-Double.parseDouble(ids[6])+Double.parseDouble(ids[5]);
			//查询期初余额
			Map<String,Object>  budg_proj_begain=budgProjBeginService.queryB_Usable_Amount(mapVo);
			if(budg_proj_begain==null){
				 BudgProjExeJson="{\"error\":\"明细没有主表数据,请补充主表数据 \"}";
				 return JSONObject.parseObject(BudgProjExeJson);
			}
			budg_proj_begain.get("b_usable_amount");
			budg_proj_begain.get("in_amount");		
		     //System.out.println(budg_proj_begain);
			double   usable_amount= Double.parseDouble(budg_proj_begain.get("in_amount").toString())-Double.parseDouble( mapVo.get("cost_amount").toString())+Double.parseDouble((String) budg_proj_begain.get("b_usable_amount").toString());
			mapVo.put("remain_amount", remain_amount);
			mapVo.put("usable_amount", usable_amount);
			int count =budgProjBeginService.querybudgProjBegain(mapVo);	
			if(count>0){
	      listVo.add(mapVo);
	      }else{
	    	  BudgProjExeJson="{\"error\":\"明细没有主表数据,进行跟新操作! 方法 addBudgProjBegain \"}";
	      }
	      
	    }
		if(listVo.size()>0){
			
		 BudgProjExeJson = budgProjBegainDetailService.updateBatch(listVo);
		 //budgProjBeginService.updateBatch(listVo);
		}
	
		
		return JSONObject.parseObject(BudgProjExeJson);
	}
	
	
	
	/**
	 * @Description 
	 * 期初预算项目审核,点击审核按钮的时候,state:01变成02
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/	
	@RequestMapping(value = "/hrp/budg/project/begin/BudgProjBegainAudit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> BudgProjBegainAudit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String str="";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String matOrderMain=null;
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", ids[0]);
			mapVo.put("proj_id", ids[1]);
			mapVo.put("source_id", ids[2]);
			mapVo.put("b_remain_amount", ids[3]);
			mapVo.put("budg_amount", ids[4]);
			mapVo.put("cost_amount", ids[5]);
			mapVo.put("remain_amount", ids[6]);
			if(ids[7].toString().equals("02")){
			mapVo.put("state",ids[7]);
			} 		
			mapVo.put("b_usable_amount",ids[8]);
			mapVo.put("proj_name",ids[9]);
			mapVo.put("source_name",ids[10]);
			mapVo.put("checker",SessionManager.getUserId());			
			mapVo.put("check_date",DateUtil.stringToDate(DateUtil.jsDateToString(new  SimpleDateFormat("yyyy-MM-dd").format(new Date()), "yyyy-MM-dd"),"yyyy-MM-dd"));
			      //查询明细里面的数据之和是否和主表的数据一致，如果一样则可以进行审核
			        Map<String,Object> detailMap = budgProjBegainDetailService.queryDetail(mapVo);
			        
			        if(detailMap != null ){
			        double remain_amount= Double.parseDouble(detailMap.get("REMAIN_AMOUNT").toString());			        
	        		 double  b_remain_amount= Double.parseDouble(detailMap.get("B_REMAIN_AMOUNT").toString());	
	        		 double budg_amount= Double.parseDouble(detailMap.get("BUDG_AMOUNT").toString());
	        		 double cost_amount= Double.parseDouble(detailMap.get("COST_AMOUNT").toString());
			        
			       
			        double remain_amount1= Double.parseDouble((String)mapVo.get("remain_amount"));
			        double b_remain_amount1= Double.parseDouble((String)mapVo.get("b_remain_amount"));
			        double budg_amount1= Double.parseDouble((String)mapVo.get("budg_amount"));
			        double cost_amount1=Double.parseDouble((String)mapVo.get("cost_amount"));

			        		
			      //判断明细表中有没有数据,如果没有数据,只需要审核主表数据即可
			      /*  List<Map<String,Object>> listDetailVo=budgProjBeginService.queryDetail(mapVo);
			       if(listDetailVo.size()==0&&(last_b_remain_amount==last_b_remain_amount1)&&last_b_usable_amount1==last_b_usable_amount){
			    	   matOrderMain= budgProjBeginService.BudgProjBegainAudit(listVo);	
			       }else{
			    	   matOrderMain="{\"error\":\"本年的期初预算金额或本年的期初可用余额! 方法 BudgProjBegainAudit \"}";  
			       }*/
				        if((budg_amount==budg_amount1)&&(cost_amount==cost_amount1)&&(remain_amount==remain_amount1)&&(b_remain_amount==b_remain_amount1)){
								
							listVo.add(mapVo);
							
						}else{
							str  = mapVo.get("proj_name") + "-" +mapVo.get("proj_id") + "-"+  mapVo.get("source_name")+ "-"+  mapVo.get("source_id")+ "-"+  mapVo.get("budg_year") +";";
							
						}
			        }else{
			        	mapVo.put("state","02");
			        	listVo.add(mapVo);
			        	
					}
		}
		
		
		
		if("".equals(str)){
			matOrderMain= budgProjBeginService.BudgProjBegainAudit(listVo);	
			
			return JSONObject.parseObject(matOrderMain);
		}else{
			
			return JSONObject.parseObject("{\"error\":\"以下数据:【"+str.substring(0,str.length()-1)+"】项目预算和项目预算明细合计值不一致! 方法 BudgProjBegainAudit \"}");
		}
		
		
	}
	
	
	
	
	
	
	/**
	 * @Description 
	 * 期初预算项目消核,点击销审按钮的时候,state:02变成01
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/	
	
	@RequestMapping(value = "/hrp/budg/project/begin/UnBudgProjBegainAudit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> UnBudgProjBegainAudit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", ids[0]);
			mapVo.put("proj_id", ids[1]);
			if(ids[2].toString().equals("01")){
			mapVo.put("state",ids[2]);
			}
			mapVo.put("maker",SessionManager.getUserId());
			mapVo.put("make_date",DateUtil.stringToDate(DateUtil.jsDateToString(new  SimpleDateFormat("yyyy-MM-dd").format(new Date()), "yyyy-MM-dd"),"yyyy-MM-dd"));

			listVo.add(mapVo);			

		}
		String matOrderMain = budgProjBeginService.UnBudgProjBegainAudit(listVo);		
		return JSONObject.parseObject(matOrderMain);
	}
	
	
	
	/**
	 * @Description 
	 * 资金来源下拉框  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/	
	@RequestMapping(value = "/hrp/budg/project/begin/queryBudgSourceId", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSourceId(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String source_id = budgProjBeginService.queryBudgSourceId(mapVo);
		return source_id;

	}

	
	/**
	 * @Description 
	 * 资金来源名称下拉框  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/	
	@RequestMapping(value = "/hrp/budg/project/begin/queryBudgSourceName", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSourceName(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String source_name = budgProjBeginService.queryBudgSourceName(mapVo);
		return source_name;

	}
	
	
	
	
	
	/**
	 * @Description 
	 * 支出项目变更号下拉框  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/begin/queryBudgPaymentItemNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgPaymentItemNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		String payment_item_no = budgProjBeginService.queryBudgPaymentItemNo(mapVo);
		return payment_item_no;

	}
	
	/**
	 * @Description 
	 * 支出项目设置页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/begin/outProjectSet", method = RequestMethod.GET)
	public String outProjectSet(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		 if(mapVo.get("group_id") == null){
				
			 mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
	mode.addAttribute("proj_id",mapVo.get("proj_id"));
	return "hrp/budg/project/begin/budgPaymentItemMain";
	}
	
	
	/**
	 * @Description 
	 * 查询数据   期初项目预算表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/begin/queryBudgPaymentItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgPaymentItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String budgProj = budgPaymentItmService.query(getPage(mapVo));
		return JSONObject.parseObject(budgProj);		
	}
	/**
	 * @Description 
	 * 添加数据   预算支付项目budg_payment_item表    添加页面跳转
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/begin/budgPaymentItemDictAddPage", method = RequestMethod.GET)
	public  String budgPaymentItemDictAddPage( Model mode) throws Exception {
		
		return "hrp/budg/project/begin/budgPaymentItemAdd";	
	}
	
	
	/**
	 * @Description 
	 *
	 *添加数据   预算支付项目budg_payment_item表  
	 *
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/begin/addBudgPaymentItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgPaymentItem(@RequestParam Map<String ,Object> mapVo, Model mode) throws Exception {
		        List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		        String budg_payment_item=null;
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;	
				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("spell_code").toString()));					
				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("wbx_code").toString()));
		  		budg_payment_item = budgPaymentItmService.add(mapVo);
		    return JSONObject.parseObject(budg_payment_item);
			}
	/**
	 * @Description 
	 *
	 *重置数据   预算支付项目budg_payment_item表  
	 *
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	
	/**
	 * @Description 
	 * 重置数据---budg_payment_item表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/begin/deleteBudgPaymentItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgPaymentItemDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String budgPaymentItm=null;
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");	
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("payment_item_no", ids[3])   ;
				mapVo.put("payment_item_id", ids[4])   ;
				
	      listVo.add(mapVo);
	      
	    }	    
			budgPaymentItm= budgPaymentItmService.deleteBatch(listVo);			
	  return JSONObject.parseObject(budgPaymentItm);
			
	}
	/**
	 * 预算年度下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/begin/queryBudgYear", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgYear(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String budgYear = budgProjBeginService.queryBudgYear(mapVo);
		return budgYear;

	}
	/**
	 * @Description 
	 * 期初预算的主表导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/begin/budgProjBegainImportPage", method = RequestMethod.GET)
	public String budgProjBegainImportPage(Model mode) throws Exception {

		return "/hrp/budg/project/begin/budgProjBegainImport";

	}
	

	/**
	 * 最新主表导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/begin/importBudgProjBegain", method = RequestMethod.POST)
	@ResponseBody
	public String importBudgProjBegain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgProjBeginService.importBudgProjBegain(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	/**
	 * @Description 
	 * 期初预算的明细表导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/begin/budgProjBegainDetailImportPage", method = RequestMethod.GET)
	public String budgProjBegainDetailImportPage(Model mode) throws Exception {
		return "/hrp/budg/project/begin/budgProjBegainDetailImport";

	}

	/**
	 * 根据选定 项目、资金来源  查询 项目负责人、期初预算余额、期初可用余额 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/begin/qureyInfoData", method = RequestMethod.POST)
	@ResponseBody
	public String qureyLastData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
		String lastData = budgProjBeginService.qureyInfoData(mapVo);
		
		return lastData;

	}

	/**
	 * @Description 
	 * 期初预算的明细表导入
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/budg/project/begin/importBudgProjBegainDetail", method = RequestMethod.POST)
	@ResponseBody
	public String importBudgProjBegainDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgProjBeginService.importBudgProjBegainDetail(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	

	
	/**
	 * 根据 选定 项目、资金来源、支出项目后  查询 期初预算余额 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/begin/qureyInfoDataDetail", method = RequestMethod.POST)
	@ResponseBody
	public String qureyInfoDataDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
		String lastData = budgProjBeginService.qureyInfoDataDetail(mapVo);
		
		return lastData;

	}

}
