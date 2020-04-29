/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.vouch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccVouchCheck;
import com.chd.hrp.acc.serviceImpl.vouch.AccVouchCheckServiceImpl;

/**
* @Title. @Description.
* 凭证辅助核算表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccVouchCheckController extends BaseController{
	private static Logger logger = Logger.getLogger(AccVouchCheckController.class);
	
	
	@Resource(name = "accVouchCheckService")
	private final AccVouchCheckServiceImpl accVouchCheckService = null;
   
	
	/**
	*凭证辅助核算表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accvouchcheck/accVouchCheckAddPage", method = RequestMethod.GET)
	public String accVouchCheckAddPage(Model mode) throws Exception {

		return "hrp/acc/accvouchcheck/accVouchCheckAdd";

	}
	/**
	 * 凭证辅助核算表是否能shanc
	 */
	/**
	*凭证辅助核算表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accvouchcheck/addAccVouchCheck", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccVouchCheck(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		
		//mapVo.put("summary", check_data.substring(0, 4));
		//mapVo.put("summary", check_data.gets)
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String accVouchCheckJson = "";
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("vouch_check_id", "");
            mapVo.put("vouch_id", "");
            mapVo.put("vouch_detail_id", "");
            mapVo.put("group_id", SessionManager.getGroupId());
            mapVo.put("hos_id", SessionManager.getHosId());
            mapVo.put("copy_code", SessionManager.getCopyCode());
            mapVo.put("acc_year", SessionManager.getAcctYear());
            mapVo.put("subj_id", id.split("@")[0]);
            mapVo.put("summary", id.split("@")[1]);
            
            mapVo.put("check2_id", "");
    		mapVo.put("check2_no", "");
        	mapVo.put("check1_id", "");
    		mapVo.put("check1_no", "");
        	mapVo.put("check3_id", "");
    		mapVo.put("check3_no", "");
        	mapVo.put("check4_id", "");
    		mapVo.put("check4_no", "");
        	mapVo.put("check5_id", "");
    		mapVo.put("check5_no", "");
        	mapVo.put("check6_id", "");
    		mapVo.put("check6_no", "");
        	mapVo.put("check7_id", "");
            
            String check1 = id.split("@")[2];
            String check1_id = check1.split("\\.")[0];
            String check1_no = check1.split("\\.")[1];
            String check1_type = check1.split("\\.")[2];
            
            String check2 = id.split("@")[3];
            String check2_id = check2.split("\\.")[0];
            String check2_no = check2.split("\\.")[1];
            String check2_type = check2.split("\\.")[2];
            
            String check3 = id.split("@")[4];
            String check3_id = check3.split("\\.")[0];
            String check3_no = check3.split("\\.")[1];
            String check3_type = check3.split("\\.")[2];
            
            String check4 = id.split("@")[5];
            String check4_id = check4.split("\\.")[0];
            String check4_no = check4.split("\\.")[1];
            String check4_type = check4.split("\\.")[2];
            
            if(check1_type.equals("HOS_EMP_DICT")){
            	mapVo.put("check2_id", check1_id);
        		mapVo.put("check2_no", check1_no);
            }else if(check1_type.equals("HOS_DEPT_DICT")){
            	mapVo.put("check1_id", check1_id);
        		mapVo.put("check1_no", check1_no);
            }else if(check1_type.equals("HOS_PROJ_DICT")){
            	mapVo.put("check3_id", check1_id);
        		mapVo.put("check3_no", check1_no);
            }else if(check1_type.equals("HOS_STORE_DICT")){
            	mapVo.put("check4_id", check1_id);
        		mapVo.put("check4_no", check1_no);
            }else if(check1_type.equals("HOS_CUS_DICT")){
            	mapVo.put("check5_id", check1_id);
        		mapVo.put("check5_no", check1_no);
            }else if(check1_type.equals("HOS_SUP_DICT")){
            	mapVo.put("check6_id", check1_id);
        		mapVo.put("check6_no", check1_no);
            }else if(check1_type.equals("HOS_SOURCE")){
            	mapVo.put("check7_id", check1_id);
            }else if(check1_type.equals("ACC_CHECK_ITEM")){
            	
            }
            
            if(check2_type.equals("HOS_EMP_DICT")){
            	mapVo.put("check2_id", check2_id);
        		mapVo.put("check2_no", check2_no);
            }else if(check2_type.equals("HOS_DEPT_DICT")){
            	mapVo.put("check1_id", check2_id);
        		mapVo.put("check1_no", check2_no);
            }else if(check2_type.equals("HOS_PROJ_DICT")){
            	mapVo.put("check3_id", check2_id);
        		mapVo.put("check3_no", check2_no);
            }else if(check2_type.equals("HOS_STORE_DICT")){
            	mapVo.put("check4_id", check2_id);
        		mapVo.put("check4_no", check2_no);
            }else if(check2_type.equals("HOS_CUS_DICT")){
            	mapVo.put("check5_id", check2_id);
        		mapVo.put("check5_no", check2_no);
            }else if(check2_type.equals("HOS_SUP_DICT")){
            	mapVo.put("check6_id", check2_id);
        		mapVo.put("check6_no", check2_no);
            }else if(check2_type.equals("HOS_SOURCE")){
            	mapVo.put("check7_id", check2_id);
            }else if(check2_type.equals("ACC_CHECK_ITEM")){
            	
            }
            
            if(check3_type.equals("HOS_EMP_DICT")){
            	mapVo.put("check2_id", check3_id);
        		mapVo.put("check2_no", check3_no);
            }else if(check3_type.equals("HOS_DEPT_DICT")){
            	mapVo.put("check1_id", check3_id);
        		mapVo.put("check1_no", check3_no);
            }else if(check3_type.equals("HOS_PROJ_DICT")){
            	mapVo.put("check3_id", check3_id);
        		mapVo.put("check3_no", check3_no);
            }else if(check3_type.equals("HOS_STORE_DICT")){
            	mapVo.put("check4_id", check3_id);
        		mapVo.put("check4_no", check3_no);
            }else if(check3_type.equals("HOS_CUS_DICT")){
            	mapVo.put("check5_id", check3_id);
        		mapVo.put("check5_no", check3_no);
            }else if(check3_type.equals("HOS_SUP_DICT")){
            	mapVo.put("check6_id", check3_id);
        		mapVo.put("check6_no", check3_no);
            }else if(check3_type.equals("HOS_SOURCE")){
            	mapVo.put("check7_id", check3_id);
            }else if(check3_type.equals("ACC_CHECK_ITEM")){
            	
            }
    		
            if(check4_type.equals("HOS_EMP_DICT")){
            	mapVo.put("check2_id", check4_id);
        		mapVo.put("check2_no", check4_no);
            }else if(check4_type.equals("HOS_DEPT_DICT")){
            	mapVo.put("check1_id", check4_id);
        		mapVo.put("check1_no", check4_no);
            }else if(check4_type.equals("HOS_PROJ_DICT")){
            	mapVo.put("check3_id", check4_id);
        		mapVo.put("check3_no", check4_no);
            }else if(check4_type.equals("HOS_STORE_DICT")){
            	mapVo.put("check4_id", check4_id);
        		mapVo.put("check4_no", check4_no);
            }else if(check4_type.equals("HOS_CUS_DICT")){
            	mapVo.put("check5_id", check4_id);
        		mapVo.put("check5_no", check4_no);
            }else if(check4_type.equals("HOS_SUP_DICT")){
            	mapVo.put("check6_id", check4_id);
        		mapVo.put("check6_no", check4_no);
            }else if(check4_type.equals("HOS_SOURCE")){
            	mapVo.put("check7_id", check4_id);
            }else if(check4_type.equals("ACC_CHECK_ITEM")){
            	
            }
    		
            mapVo.put("occur_date", id.split("@")[6]);
            mapVo.put("due_date", id.split("@")[7]);
            mapVo.put("con_no", id.split("@")[8]);
            mapVo.put("check_no", "");
            mapVo.put("business_no", id.split("@")[9]);
    		
            String price = id.split("@")[10].split("\\.")[0];
            String price_type = id.split("@")[10].split("\\.")[1];
    		if(price_type.equals("debit")){
    			mapVo.put("debit", price);
    			mapVo.put("debit_w", price);
    			mapVo.put("credit", 0);
    	    	mapVo.put("credit_w", 0);
    		}else{
    			mapVo.put("credit", price);
    	    	mapVo.put("credit_w", price);
    	    	mapVo.put("debit", 0);
    			mapVo.put("debit_w", 0);
    		}
    		mapVo.put("pay_type_code", "");
    		
    		mapVo.put("is_init", 1);
    		mapVo.put("old_check_id", "");
    		String vouch_check_id = id.split("@")[12];
    		if(!vouch_check_id.equals("") && !vouch_check_id.equals("undefined")){
    			mapVo.put("vouch_check_id", vouch_check_id);
    			accVouchCheckJson = accVouchCheckService.updateAccVouchCheck(mapVo);
    		}else{
    			//System.out.println("1111111111");
    			accVouchCheckJson = accVouchCheckService.addAccVouchCheck(mapVo);
    		}
    	
            listVo.add(mapVo);
        }
		
		return JSONObject.parseObject(accVouchCheckJson);
		
	}
	
	/**
	 * 凭证辅助核算表保存
	 */
	@RequestMapping(value = "/hrp/acc/accvouchcheck/addAccVouchVerCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccVouchVerCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String accVouchCheckJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		JSONArray json = JSONArray.parseArray((String)mapVo.get("check_data"));
		List<Map<String,Object>> list_vouch_check_batch1 = new ArrayList<Map<String,Object>>();//修改存放进去
		List<Map<String,Object>> list_vouch_check_batch2 = new ArrayList<Map<String,Object>>();//新增存放进去
		int update =0;
		int insert=0;
		Iterator it = json.iterator();
		try{
			while (it.hasNext()) {
				
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				Map<String, Object> mapDetailVo = new HashMap<String, Object>();
				
				mapDetailVo.put("group_id", SessionManager.getGroupId());
				mapDetailVo.put("hos_id", SessionManager.getHosId());
				mapDetailVo.put("copy_code", SessionManager.getCopyCode());
				mapDetailVo.put("acc_year", SessionManager.getAcctYear());
				mapDetailVo.put("subj_id", mapVo.get("subj_id"));
				
				/*
				 * 2016/09/23
				 * Author:Joe
				 * 此处给默认值,解决SQL少字段报错的问题,问题来源于往来初始明细账保存操作
				 * */
				//默认为空
				mapDetailVo.put("check1_id", "");
	            mapDetailVo.put("check1_no", "");
	            mapDetailVo.put("check2_id", "");
	            mapDetailVo.put("check2_no", "");
	            mapDetailVo.put("check3_id", "");
	            mapDetailVo.put("check3_no", "");
	            mapDetailVo.put("check4_id", "");
	            mapDetailVo.put("check4_no", "");
	            mapDetailVo.put("check5_id", "");
	            mapDetailVo.put("check5_no", "");
	            mapDetailVo.put("check6_id", "");
	            mapDetailVo.put("check6_no", "");
	            mapDetailVo.put("check7_id", "");
				
				
				if("".equals(jsonObj.get("vouch_check_id"))|| null==jsonObj.get("vouch_check_id")){	
					insert=insert+1;
					mapDetailVo.put("vouch_id", "");  
		            mapDetailVo.put("vouch_detail_id", "");
		            
					mapDetailVo.put("summary",  jsonObj.get("summary"));
					if("0".equals( jsonObj.get("subj_dire"))){
						
						mapDetailVo.put("debit",  jsonObj.get("money"));
						mapDetailVo.put("credit",  0);
					}else{

						mapDetailVo.put("debit",  0);
						mapDetailVo.put("credit",  jsonObj.get("money"));
					}
					mapDetailVo.put("con_no",  jsonObj.get("con_no"));
					mapDetailVo.put("check_no",  "");
					
					/*
					 * 2016/09/23
					 * Author:Joe
					 * 给以下选填字段加判断 如jsonObj.get("xxx") != null && !"".equals(jsonObj.get("xxx").toString())
					 * 解决参数为空程序报空指针异常的问题,问题来源于往来初始明细账保存操作
					 * */
					if(jsonObj.get("business_no") == null || "".equals(jsonObj.get("business_no").toString())){
						mapDetailVo.put("business_no", "");
					}else{
						mapDetailVo.put("business_no",  jsonObj.get("business_no"));
					}
					
					if(jsonObj.get("occur_date") != null && !"".equals(jsonObj.get("occur_date").toString())){
						
						mapDetailVo.put("occur_date",  jsonObj.get("occur_date").toString().substring(0, 10));
					}
					
					if(jsonObj.get("due_date") == null || "".equals(jsonObj.get("due_date").toString())){
						mapDetailVo.put("due_date","");
					}else{
						
						mapDetailVo.put("due_date",  jsonObj.get("due_date").toString().substring(0, 10));
					}
					
					mapDetailVo.put("pay_type_code", "");
					mapDetailVo.put("debit_w", 0);
					mapDetailVo.put("credit_w", 0);
					mapDetailVo.put("is_init",  1);			
		            mapDetailVo.put("old_check_id", ""); 
					
					if(!"".equals(jsonObj.get("check1")) && jsonObj.get("check1") != null){
						String check1_id = jsonObj.get("check1").toString().split("\\.")[0];
			            String check1_no = jsonObj.get("check1").toString().split("\\.")[1];
			            mapDetailVo.put("check1_id",  check1_id);
			            mapDetailVo.put("check1_no",  check1_no);
			            mapDetailVo.put("check2_id", "");
			            mapDetailVo.put("check2_no", "");
			            mapDetailVo.put("check3_id", "");
			            mapDetailVo.put("check3_no", "");
			            mapDetailVo.put("check4_id", "");
			            mapDetailVo.put("check4_no", "");
			            mapDetailVo.put("check5_id", "");
			            mapDetailVo.put("check5_no", "");
			            mapDetailVo.put("check6_id", "");
			            mapDetailVo.put("check6_no", "");
			            mapDetailVo.put("check7_id", "");
			            
					}else if(!"".equals(jsonObj.get("check2")) && jsonObj.get("check2") != null){
						String check2_id = jsonObj.get("check2").toString().split("\\.")[0];
			            String check2_no = jsonObj.get("check2").toString().split("\\.")[1];
			            mapDetailVo.put("check1_id",  "");
			            mapDetailVo.put("check1_no",  "");
			            mapDetailVo.put("check2_id", check2_id);
			            mapDetailVo.put("check2_no", check2_no);
			            mapDetailVo.put("check3_id", "");
			            mapDetailVo.put("check3_no", "");
			            mapDetailVo.put("check4_id", "");
			            mapDetailVo.put("check4_no", "");
			            mapDetailVo.put("check5_id", "");
			            mapDetailVo.put("check5_no", "");
			            mapDetailVo.put("check6_id", "");
			            mapDetailVo.put("check6_no", "");
			            mapDetailVo.put("check7_id", "");
					}else if(!"".equals(jsonObj.get("check3")) && jsonObj.get("check3") != null){
						String check3_id = jsonObj.get("check3").toString().split("\\.")[0];
			            String check3_no = jsonObj.get("check3").toString().split("\\.")[1];
			            mapDetailVo.put("check1_id",  "");
			            mapDetailVo.put("check1_no",  "");
			            mapDetailVo.put("check2_id", "");
			            mapDetailVo.put("check2_no", "");
			            mapDetailVo.put("check3_id", check3_id);
			            mapDetailVo.put("check3_no", check3_no);
			            mapDetailVo.put("check4_id", "");
			            mapDetailVo.put("check4_no", "");
			            mapDetailVo.put("check5_id", "");
			            mapDetailVo.put("check5_no", "");
			            mapDetailVo.put("check6_id", "");
			            mapDetailVo.put("check6_no", "");
			            mapDetailVo.put("check7_id", "");
					}else if(!"".equals(jsonObj.get("check4")) && jsonObj.get("check4") != null){
						String check4_id = jsonObj.get("check4").toString().split("\\.")[0];
			            String check4_no = jsonObj.get("check4").toString().split("\\.")[1];
			            mapDetailVo.put("check1_id",  "");
			            mapDetailVo.put("check1_no",  "");
			            mapDetailVo.put("check2_id", "");
			            mapDetailVo.put("check2_no", "");
			            mapDetailVo.put("check3_id", "");
			            mapDetailVo.put("check3_no", "");
			            mapDetailVo.put("check4_id", check4_id);
			            mapDetailVo.put("check4_no", check4_no);
			            mapDetailVo.put("check5_id", "");
			            mapDetailVo.put("check5_no", "");
			            mapDetailVo.put("check6_id", "");
			            mapDetailVo.put("check6_no", "");
			            mapDetailVo.put("check7_id", "");
					}else if(!"".equals(jsonObj.get("check5")) && jsonObj.get("check5") != null){
						String check5_id = jsonObj.get("check5").toString().split("\\.")[0];
			            String check5_no = jsonObj.get("check5").toString().split("\\.")[1];
			            mapDetailVo.put("check1_id",  "");
			            mapDetailVo.put("check1_no",  "");
			            mapDetailVo.put("check2_id", "");
			            mapDetailVo.put("check2_no", "");
			            mapDetailVo.put("check3_id", "");
			            mapDetailVo.put("check3_no", "");
			            mapDetailVo.put("check4_id", "");
			            mapDetailVo.put("check4_no", "");
			            mapDetailVo.put("check5_id", check5_id);
			            mapDetailVo.put("check5_no", check5_no);
			            mapDetailVo.put("check6_id", "");
			            mapDetailVo.put("check6_no", "");
			            mapDetailVo.put("check7_id", "");
					}else if(!"".equals(jsonObj.get("check6")) && jsonObj.get("check6") != null){
						String check6_id = jsonObj.get("check6").toString().split("\\.")[0];
			            String check6_no = jsonObj.get("check6").toString().split("\\.")[1];
			            mapDetailVo.put("check1_id",  "");
			            mapDetailVo.put("check1_no",  "");
			            mapDetailVo.put("check2_id", "");
			            mapDetailVo.put("check2_no", "");
			            mapDetailVo.put("check3_id", "");
			            mapDetailVo.put("check3_no", "");
			            mapDetailVo.put("check4_id", "");
			            mapDetailVo.put("check4_no", "");
			            mapDetailVo.put("check5_id", "");
			            mapDetailVo.put("check5_no", "");
			            mapDetailVo.put("check6_id", check6_id);
			            mapDetailVo.put("check6_no", check6_no);
			            mapDetailVo.put("check7_id", "");
					}else if(!"".equals(jsonObj.get("check7")) && jsonObj.get("check7") != null){
						String check7_id = jsonObj.get("check7").toString().split("\\.")[0];
			           
			            mapDetailVo.put("check1_id",  "");
			            mapDetailVo.put("check1_no",  "");
			            mapDetailVo.put("check2_id", "");
			            mapDetailVo.put("check2_no", "");
			            mapDetailVo.put("check3_id", "");
			            mapDetailVo.put("check3_no", "");
			            mapDetailVo.put("check4_id", "");
			            mapDetailVo.put("check4_no", "");
			            mapDetailVo.put("check5_id", "");
			            mapDetailVo.put("check5_no", "");
			            mapDetailVo.put("check6_id", "");
			            mapDetailVo.put("check6_no", "");
			            mapDetailVo.put("check7_id", check7_id);
					}
					
					
					list_vouch_check_batch2.add(mapDetailVo);
				}else{
					//更新
					update=update+1;
					mapDetailVo.put("vouch_check_id",  jsonObj.get("vouch_check_id"));
					mapDetailVo.put("vouch_id", jsonObj.get("vouch_id"));  
		            mapDetailVo.put("vouch_detail_id", jsonObj.get("vouch_detail_id"));
		            if(jsonObj.get("summary") != null && !"".equals(jsonObj.get("summary").toString())){
		            	
		            	mapDetailVo.put("summary",  jsonObj.get("summary"));
		            }else{
		            	mapDetailVo.put("summary","");
		            }
					
		            mapDetailVo.put("occur_date",  jsonObj.get("occur_date"));
					
					if(jsonObj.get("due_date") != null && !"".equals(jsonObj.get("due_date").toString())){
		            	
		            	mapDetailVo.put("due_date",  jsonObj.get("due_date"));
		            }else{
		            	mapDetailVo.put("due_date","");
		            }
					
					if(jsonObj.get("con_no") != null && !"".equals(jsonObj.get("con_no").toString())){
		            	
		            	mapDetailVo.put("con_no",  jsonObj.get("con_no"));
		            }else{
		            	mapDetailVo.put("con_no","");
		            }
					
					if(jsonObj.get("check_no") != null && !"".equals(jsonObj.get("check_no").toString())){
		            	
		            	mapDetailVo.put("check_no",  jsonObj.get("check_no"));
		            }else{
		            	mapDetailVo.put("check_no","");
		            }
					
					if(jsonObj.get("business_no") != null && !"".equals(jsonObj.get("business_no").toString())){
		            	
		            	mapDetailVo.put("business_no",  jsonObj.get("business_no"));
		            }else{
		            	mapDetailVo.put("business_no","");
		            }
					
					if("0".equals( jsonObj.get("subj_dire"))){
						
						mapDetailVo.put("debit",  jsonObj.get("money"));
						mapDetailVo.put("credit",  0);
					}else{

						mapDetailVo.put("debit",  0);
						mapDetailVo.put("credit",  jsonObj.get("money"));
					}
					mapDetailVo.put("debit_w", jsonObj.get("debit_w"));
					mapDetailVo.put("credit_w", jsonObj.get("credit_w"));
					
					if(jsonObj.get("pay_type_code") != null && !"".equals(jsonObj.get("pay_type_code").toString())){
						
						mapDetailVo.put("pay_type_code", jsonObj.get("pay_type_code"));
					}else{
						mapDetailVo.put("pay_type_code", "");
					}
					
					mapDetailVo.put("is_init", jsonObj.get("is_init"));
					
					if(jsonObj.get("old_check_id") != null && !"".equals(jsonObj.get("old_check_id").toString())){
						
						mapDetailVo.put("old_check_id", jsonObj.get("old_check_id"));
					}else{
						mapDetailVo.put("old_check_id", "");
					}
		            
					if(jsonObj.get("check1") != null && !"".equals(jsonObj.get("check1").toString()) 
							&& jsonObj.get("check1").toString().length()>1){
						String check1_id = jsonObj.get("check1").toString().split("\\.")[0];
			            String check1_no = jsonObj.get("check1").toString().split("\\.")[1];
			            mapDetailVo.put("check1_id",  check1_id);
			            mapDetailVo.put("check1_no",  check1_no);
			            mapDetailVo.put("check2_id", "");
			            mapDetailVo.put("check2_no", "");
			            mapDetailVo.put("check3_id", "");
			            mapDetailVo.put("check3_no", "");
			            mapDetailVo.put("check4_id", "");
			            mapDetailVo.put("check4_no", "");
			            mapDetailVo.put("check5_id", "");
			            mapDetailVo.put("check5_no", "");
			            mapDetailVo.put("check6_id", "");
			            mapDetailVo.put("check6_no", "");
			            mapDetailVo.put("check7_id", "");
			            
					}else if(jsonObj.get("check2") != null && !"".equals(jsonObj.get("check2").toString()) 
							&& jsonObj.get("check2").toString().length()>1){
						String check2_id = jsonObj.get("check2").toString().split("\\.")[0];
			            String check2_no = jsonObj.get("check2").toString().split("\\.")[1];
			            mapDetailVo.put("check1_id",  "");
			            mapDetailVo.put("check1_no",  "");
			            mapDetailVo.put("check2_id", check2_id);
			            mapDetailVo.put("check2_no", check2_no);
			            mapDetailVo.put("check3_id", "");
			            mapDetailVo.put("check3_no", "");
			            mapDetailVo.put("check4_id", "");
			            mapDetailVo.put("check4_no", "");
			            mapDetailVo.put("check5_id", "");
			            mapDetailVo.put("check5_no", "");
			            mapDetailVo.put("check6_id", "");
			            mapDetailVo.put("check6_no", "");
			            mapDetailVo.put("check7_id", "");
					}else if(jsonObj.get("check3") != null && !"".equals(jsonObj.get("check3").toString()) 
							&& jsonObj.get("check3").toString().length()>1){
						String check3_id = jsonObj.get("check3").toString().split("\\.")[0];
			            String check3_no = jsonObj.get("check3").toString().split("\\.")[1];
			            mapDetailVo.put("check1_id",  "");
			            mapDetailVo.put("check1_no",  "");
			            mapDetailVo.put("check2_id", "");
			            mapDetailVo.put("check2_no", "");
			            mapDetailVo.put("check3_id", check3_id);
			            mapDetailVo.put("check3_no", check3_no);
			            mapDetailVo.put("check4_id", "");
			            mapDetailVo.put("check4_no", "");
			            mapDetailVo.put("check5_id", "");
			            mapDetailVo.put("check5_no", "");
			            mapDetailVo.put("check6_id", "");
			            mapDetailVo.put("check6_no", "");
			            mapDetailVo.put("check7_id", "");
					}else if(jsonObj.get("check4") != null && !"".equals(jsonObj.get("check4").toString()) 
							&& jsonObj.get("check4").toString().length()>1){
						String check4_id = jsonObj.get("check4").toString().split("\\.")[0];
			            String check4_no = jsonObj.get("check4").toString().split("\\.")[1];
			            mapDetailVo.put("check1_id",  "");
			            mapDetailVo.put("check1_no",  "");
			            mapDetailVo.put("check2_id", "");
			            mapDetailVo.put("check2_no", "");
			            mapDetailVo.put("check3_id", "");
			            mapDetailVo.put("check3_no", "");
			            mapDetailVo.put("check4_id", check4_id);
			            mapDetailVo.put("check4_no", check4_no);
			            mapDetailVo.put("check5_id", "");
			            mapDetailVo.put("check5_no", "");
			            mapDetailVo.put("check6_id", "");
			            mapDetailVo.put("check6_no", "");
			            mapDetailVo.put("check7_id", "");
					}else if(jsonObj.get("check5") != null && !"".equals(jsonObj.get("check5").toString()) 
							&& jsonObj.get("check5").toString().length()>1){
						String check5_id = jsonObj.get("check5").toString().split("\\.")[0];
			            String check5_no = jsonObj.get("check5").toString().split("\\.")[1];
			            mapDetailVo.put("check1_id",  "");
			            mapDetailVo.put("check1_no",  "");
			            mapDetailVo.put("check2_id", "");
			            mapDetailVo.put("check2_no", "");
			            mapDetailVo.put("check3_id", "");
			            mapDetailVo.put("check3_no", "");
			            mapDetailVo.put("check4_id", "");
			            mapDetailVo.put("check4_no", "");
			            mapDetailVo.put("check5_id", check5_id);
			            mapDetailVo.put("check5_no", check5_no);
			            mapDetailVo.put("check6_id", "");
			            mapDetailVo.put("check6_no", "");
			            mapDetailVo.put("check7_id", "");
					}else if(jsonObj.get("check6") != null && !"".equals(jsonObj.get("check6").toString()) 
							&& jsonObj.get("check6").toString().length()>1){
						String check6_id = jsonObj.get("check6").toString().split("\\.")[0];
			            String check6_no = jsonObj.get("check6").toString().split("\\.")[1];
			            mapDetailVo.put("check1_id",  "");
			            mapDetailVo.put("check1_no",  "");
			            mapDetailVo.put("check2_id", "");
			            mapDetailVo.put("check2_no", "");
			            mapDetailVo.put("check3_id", "");
			            mapDetailVo.put("check3_no", "");
			            mapDetailVo.put("check4_id", "");
			            mapDetailVo.put("check4_no", "");
			            mapDetailVo.put("check5_id", "");
			            mapDetailVo.put("check5_no", "");
			            mapDetailVo.put("check6_id", check6_id);
			            mapDetailVo.put("check6_no", check6_no);
			            mapDetailVo.put("check7_id", "");
					}else if(jsonObj.get("check7") != null && !"".equals(jsonObj.get("check7").toString()) 
							&& jsonObj.get("check7").toString().length()>1){
						if(jsonObj.get("check7").toString().length()>1){
							String check7_id = jsonObj.get("check7").toString().split("\\.")[0];
					           
				            mapDetailVo.put("check1_id", "");
				            mapDetailVo.put("check1_no", "");
				            mapDetailVo.put("check2_id", "");
				            mapDetailVo.put("check2_no", "");
				            mapDetailVo.put("check3_id", "");
				            mapDetailVo.put("check3_no", "");
				            mapDetailVo.put("check4_id", "");
				            mapDetailVo.put("check4_no", "");
				            mapDetailVo.put("check5_id", "");
				            mapDetailVo.put("check5_no", "");
				            mapDetailVo.put("check6_id", "");
				            mapDetailVo.put("check6_no", "");
				            mapDetailVo.put("check7_id", check7_id);
						}
						
					}
					
					list_vouch_check_batch1.add(mapDetailVo);
					
				}
				/*System.out.println("--------------update:"+update);
				System.out.println("--------------insert:"+insert);
				
				for (Map<String, Object> m : list_vouch_check_batch2){  
			      for (String k : m.keySet()){  
			          System.out.println("--------------:"+k + " : " + m.get(k));  
			      }  
			  
			    } */ 
				
				
				
				if(update>0){
					
					accVouchCheckJson = accVouchCheckService.updateBatchAccVouchCheck(list_vouch_check_batch1);
				
				}
				if(insert>0){
					
					accVouchCheckJson = accVouchCheckService.addBatchAccVouchCheck(list_vouch_check_batch2);
				
					//return JSONObject.parseObject("{\"msg\":\"添加成功.\",\"state\":\"true\"}");
				}
				
			}
		}catch (DataAccessException e) {

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}
		return JSONObject.parseObject(accVouchCheckJson);
	}
	
	/**
	*凭证辅助核算表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accvouchcheck/queryAccVouchCheck", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccVouchCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		
		String accVouchCheck = accVouchCheckService.queryAccVouchCheck(getPage(mapVo));

		return JSONObject.parseObject(accVouchCheck);
		
	}
	/**
	 * 是否结账
	 */
	@RequestMapping(value = "/hrp/acc/accvouchcheck/getIsAccFlag", method = RequestMethod.POST)
	@ResponseBody
	public String getIsAccFlag(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String accVouchCheckJson=accVouchCheckService.getIsAccFlag(mapVo);
        
		return accVouchCheckJson;
	}
	/**
	*凭证辅助核算表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accvouchcheck/deleteAccVouchCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccVouchCheck(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String accVouchCheckJson = accVouchCheckService.deleteBatchAccVouchCheck(listVo);
	   return JSONObject.parseObject(accVouchCheckJson);
			
	}
	/**
	*凭证辅助核算表<BR>
	*批量删除
	*/
	@RequestMapping(value = "/hrp/acc/accvouchcheck/deleteAccVouchVerCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccVouchVerCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		JSONArray json = JSONArray.parseArray((String)mapVo.get("check_data"));
		Iterator it = json.iterator();
		try{
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				Map<String, Object> mapVo1 = new HashMap<String, Object>();
				mapVo1.put("vouch_check_id", jsonObj.get("vouch_check_id"));
				
				mapVo1.put("group_id", SessionManager.getGroupId());
				mapVo1.put("hos_id", SessionManager.getHosId());
				mapVo1.put("copy_code", SessionManager.getCopyCode());
				mapVo1.put("acc_year", SessionManager.getAcctYear());
				listVo.add(mapVo1);
			}
		}catch (DataAccessException e) {
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}
		/*for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }*/
		String accVouchCheckJson = accVouchCheckService.deleteBatchAccVouchCheck(listVo);
	   return JSONObject.parseObject(accVouchCheckJson);
			
	}
	
	/**
	*凭证辅助核算表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accvouchcheck/accVouchCheckUpdatePage", method = RequestMethod.GET)
	
	public String accVouchCheckUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccVouchCheck accVouchCheck = new AccVouchCheck();
		accVouchCheck = accVouchCheckService.queryAccVouchCheckByCode(mapVo);
		mode.addAttribute("vouch_check_id", accVouchCheck.getVouch_check_id());
		mode.addAttribute("vouch_id", accVouchCheck.getVouch_id());
		mode.addAttribute("vouch_detail_id", accVouchCheck.getVouch_detail_id());
		mode.addAttribute("group_id", accVouchCheck.getGroup_id());
		mode.addAttribute("hos_id", accVouchCheck.getHos_id());
		mode.addAttribute("copy_code", accVouchCheck.getCopy_code());
		mode.addAttribute("acc_year", accVouchCheck.getAcc_year());
		mode.addAttribute("subj_code", accVouchCheck.getSubj_code());
		mode.addAttribute("summary", accVouchCheck.getSummary());
		mode.addAttribute("debit", accVouchCheck.getDebit());
		mode.addAttribute("credit", accVouchCheck.getCredit());
		mode.addAttribute("con_no", accVouchCheck.getCon_no());
		mode.addAttribute("check_no", accVouchCheck.getCheck_no());
		mode.addAttribute("business_no", accVouchCheck.getBusiness_no());
		mode.addAttribute("occur_date", accVouchCheck.getOccur_date());
		mode.addAttribute("due_date", accVouchCheck.getDue_date());
		mode.addAttribute("pay_type_code", accVouchCheck.getPay_type_code());
		mode.addAttribute("debit_w", accVouchCheck.getDebit_w());
		mode.addAttribute("credit_w", accVouchCheck.getCredit_w());
		mode.addAttribute("check1_id", accVouchCheck.getCheck1_id());
		mode.addAttribute("check1_no", accVouchCheck.getCheck1_no());
		mode.addAttribute("check2_id", accVouchCheck.getCheck2_id());
		mode.addAttribute("check2_no", accVouchCheck.getCheck2_no());
		mode.addAttribute("check3_id", accVouchCheck.getCheck3_id());
		mode.addAttribute("check3_no", accVouchCheck.getCheck3_no());
		mode.addAttribute("check4_id", accVouchCheck.getCheck4_id());
		mode.addAttribute("check4_no", accVouchCheck.getCheck4_no());
		mode.addAttribute("check5_id", accVouchCheck.getCheck5_id());
		mode.addAttribute("check5_no", accVouchCheck.getCheck5_no());
		mode.addAttribute("check6_id", accVouchCheck.getCheck6_id());
		mode.addAttribute("check6_no", accVouchCheck.getCheck6_no());
		mode.addAttribute("check7_id", accVouchCheck.getCheck7_id());
		mode.addAttribute("is_init", accVouchCheck.getIs_init());
		mode.addAttribute("old_check_id", accVouchCheck.getOld_check_id());
		return "hrp/acc/accvouchcheck/accVouchCheckUpdate";
	}
	/**
	*凭证辅助核算表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accvouchcheck/updateAccVouchCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccVouchCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		String accVouchCheckJson = accVouchCheckService.updateAccVouchCheck(mapVo);
		
		return JSONObject.parseObject(accVouchCheckJson);
	}
	/**
	*凭证辅助核算表<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accvouchcheck/importAccVouchCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccVouchCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accVouchCheckJson = accVouchCheckService.importAccVouchCheck(mapVo);
		
		return JSONObject.parseObject(accVouchCheckJson);
	}

}

