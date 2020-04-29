/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.common;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.budg.dao.common.BudgControlPlanExecMapper;
import com.chd.hrp.budg.service.common.BudgControlPlanExecService;
import com.github.pagehelper.PageInfo;


@Service("budgControlPlanExecService")
public class BudgControlPlanExecServiceImpl implements BudgControlPlanExecService {

	private static Logger logger = Logger.getLogger(BudgControlPlanExecServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "budgControlPlanExecMapper")
	private final BudgControlPlanExecMapper budgControlPlanExecMapper = null;
		
	@Resource(name = "budgControlPlanExecService") //方案执行过程 service
	private final BudgControlPlanExecService budgControlPlanExecService =null;
	/**
     * 预算控制执行过程查看 各计算项数据查询
     */
	@Override
	public Map<String, Object> queryOperDateBeginEndDate(Map<String, Object> mapVo) throws DataAccessException {
		Map<String, Object>  operOperDateBeginEndDate=  budgControlPlanExecMapper.queryOperDateBeginEndDate(mapVo);
		return operOperDateBeginEndDate ;
	}
    /**
     * 预算控制执行过程查看 各计算项数据查询 --科室出库
     */
	@Override
	public Map<String, Object> queryControlExecMatOutProcess(Map<String, Object> mapVo) throws DataAccessException {
		
		String returnMap = "";
		StringBuffer err_sb = new StringBuffer();
		int count = budgControlPlanExecMapper.queryLinkCodeIsExists(mapVo);
		//没有方案或状态值没在控制范围内
		if (count == 0){
			return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");
		}else{
			
			List<Map<String,Object>> listVo =  budgControlPlanExecMapper.queryLinkCodeMap(mapVo);
			
			if (listVo.size() ==0)
				return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");
			
			int work_flag_4 = 0;
			int work_flag_5 = 0;
			StringBuffer control_str = new StringBuffer();
			for(Map<String, Object> linkCodeMap : listVo) {
				////PLAN_CODE ='01' 医疗支出预算 ,'0103' 材料支出预算
				if("0103".equals(linkCodeMap.get("PLAN_CODE").toString())){
					// CONT_M: 01非明细控制、02明细控制、03明细总额控制
					Map<String, Object> matOutMapVo = new HashMap<String, Object>();
					matOutMapVo.put("group_id", linkCodeMap.get("GROUP_ID"));
					matOutMapVo.put("hos_id",   linkCodeMap.get("HOS_ID"));
					matOutMapVo.put("copy_code", linkCodeMap.get("COPY_CODE"));
			 		matOutMapVo.put("plan_code", linkCodeMap.get("PLAN_CODE"));
			 		matOutMapVo.put("plan_name", linkCodeMap.get("PLAN_NAME"));
			 		matOutMapVo.put("mod_code", linkCodeMap.get("MOD_CODE"));
			 		matOutMapVo.put("work_link_code", linkCodeMap.get("LINK_CODE"));
			 		matOutMapVo.put("work_link_name", linkCodeMap.get("LINK_NAME"));
			 		matOutMapVo.put("tab_code", linkCodeMap.get("TAB_CODE"));
			 		matOutMapVo.put("work_cont_code", linkCodeMap.get("CONT_NOTE"));
			 		matOutMapVo.put("use_state", linkCodeMap.get("USE_STATE"));
			 		matOutMapVo.put("re_link", linkCodeMap.get("RE_LINK"));	
			 		matOutMapVo.put("month_begin_date", mapVo.get("month_begin_date"));
			 		matOutMapVo.put("month_end_date", mapVo.get("month_end_date"));
			 		matOutMapVo.put("year_begin_date", mapVo.get("year_begin_date"));
			 		matOutMapVo.put("year_end_date", mapVo.get("year_end_date"));
			 		matOutMapVo.put("budg_year", mapVo.get("budg_year"));
			 		matOutMapVo.put("budg_month", mapVo.get("budg_month"));
			 		matOutMapVo.put("work_select_id", mapVo.get("work_select_id"));
			 		
					if( "02".equals(linkCodeMap.get("CONT_M").toString()) ){
				 		
				 		List<Map<String, Object>> resultList= budgControlPlanExecMapper.queryWorkBudgList(matOutMapVo);
				 		
				 		
						for (Map<String, Object> resultMap : resultList) {
							
							control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
							
							if ("01".equals(resultMap.get("CONT_L").toString())) //全院
							    control_str.append("全院,");
							else
							    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
							
							control_str.append(""+resultMap.get("MAT_TYPE_NAME")+","); //预算项
							control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
							control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
							control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
							if ("02".equals(resultMap.get("CONT_W"))) //'02' 提示控制 ，'03' 严格控制
							    control_str.append("提示控制.");
							else
							    control_str.append("严格控制."); //科室或职能科室
							
							if ("02".equals(resultMap.get("CONT_W"))) { 
								work_flag_4 = 1;
							}
							if ("03".equals(resultMap.get("CONT_W"))) { 
								work_flag_5 = 1;
							}									
						}						
					}
					else if( "01".equals(linkCodeMap.get("CONT_M").toString()) ){
						
				 		List<Map<String, Object>> resultList2= budgControlPlanExecMapper.queryWorkBudgListOne(matOutMapVo);
				 		
						for (Map<String, Object> resultMap : resultList2) {
							control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
							
							if ("01".equals(resultMap.get("CONT_L").toString())) //全院
							    control_str.append("全院,");
							else
							    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
							
							//control_str.append(""+resultMap.get("MAT_TYPE_NAME")+","); //预算项
							control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
							control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
							control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
							if ("02".equals(resultMap.get("CONT_W"))) //'02' 提示控制 ，'03' 严格控制
							    control_str.append("提示控制.");
							else
							    control_str.append("严格控制."); //科室或职能科室
							
							if ("02".equals(resultMap.get("CONT_W"))) { 
								work_flag_4 = 1;
							}
							if ("03".equals(resultMap.get("CONT_W"))) { 
								work_flag_5 = 1;
							}		
						}	
					}
					else if( "03".equals(linkCodeMap.get("CONT_M").toString()) ){
				 		List<Map<String, Object>> resultList3= budgControlPlanExecMapper.queryWorkBudgListThree(matOutMapVo);
				 		
						for (Map<String, Object> resultMap : resultList3) {
							control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
							
							if ("01".equals(resultMap.get("CONT_L").toString())) //全院
							    control_str.append("全院,");
							else
							    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
							
							//control_str.append(""+resultMap.get("MAT_TYPE_NAME")+","); //预算项
							control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
							control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
							control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
							if ("02".equals(resultMap.get("CONT_W"))) //'02' 提示控制 ，'03' 严格控制
							    control_str.append("提示控制.");
							else
							    control_str.append("严格控制."); //科室或职能科室
							
							if ("02".equals(resultMap.get("CONT_W"))) { 
								work_flag_4 = 1;
							}
							if ("03".equals(resultMap.get("CONT_W"))) { 
								work_flag_5 = 1;
							}			
							
						}
						
					}
				}
				//PLAN_CODE ='01' 医疗支出预算
				if("01".equals(linkCodeMap.get("PLAN_CODE").toString())){
					// CONT_M: 01非明细控制、02明细控制、03明细总额控制
					Map<String, Object> matOutMapVo = new HashMap<String, Object>();
					matOutMapVo.put("group_id", linkCodeMap.get("GROUP_ID"));
					matOutMapVo.put("hos_id",   linkCodeMap.get("HOS_ID"));
					matOutMapVo.put("copy_code", linkCodeMap.get("COPY_CODE"));
			 		matOutMapVo.put("plan_code", linkCodeMap.get("PLAN_CODE"));
			 		matOutMapVo.put("plan_name", linkCodeMap.get("PLAN_NAME"));
			 		matOutMapVo.put("mod_code", linkCodeMap.get("MOD_CODE"));
			 		matOutMapVo.put("work_link_code", linkCodeMap.get("LINK_CODE"));
			 		matOutMapVo.put("work_link_name", linkCodeMap.get("LINK_NAME"));
			 		matOutMapVo.put("tab_code", linkCodeMap.get("TAB_CODE"));
			 		matOutMapVo.put("work_cont_code", linkCodeMap.get("CONT_NOTE"));
			 		matOutMapVo.put("use_state", linkCodeMap.get("USE_STATE"));
			 		matOutMapVo.put("re_link", linkCodeMap.get("RE_LINK"));	
			 		matOutMapVo.put("month_begin_date", mapVo.get("month_begin_date"));
			 		matOutMapVo.put("month_end_date", mapVo.get("month_end_date"));
			 		matOutMapVo.put("year_begin_date", mapVo.get("year_begin_date"));
			 		matOutMapVo.put("year_end_date", mapVo.get("year_end_date"));
			 		matOutMapVo.put("budg_year", mapVo.get("budg_year"));
			 		matOutMapVo.put("budg_month", mapVo.get("budg_month"));
			 		matOutMapVo.put("work_select_id", mapVo.get("work_select_id"));
			 		
					if( "02".equals(linkCodeMap.get("CONT_M").toString()) ){
				 		
				 		List<Map<String, Object>> resultList= budgControlPlanExecMapper.queryBudgMatOutMedCostTwo(matOutMapVo);
				 		
						for (Map<String, Object> resultMap : resultList) {
							
							control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
							
							if ("01".equals(resultMap.get("CONT_L").toString())) //全院
							    control_str.append("全院,");
							else
							    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
							
							control_str.append(""+resultMap.get("SUBJ_NAME")+","); //预算项
							control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
							control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
							control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
							if ("02".equals(resultMap.get("CONT_W"))) //'02' 提示控制 ，'03' 严格控制
							    control_str.append("提示控制.");
							else
							    control_str.append("严格控制."); //科室或职能科室
							
							if ("02".equals(resultMap.get("CONT_W"))) { 
								work_flag_4 = 1;
							}
							if ("03".equals(resultMap.get("CONT_W"))) { 
								work_flag_5 = 1;
							}									
						}						
					}
					else if( "01".equals(linkCodeMap.get("CONT_M").toString()) ){
						
				 		List<Map<String, Object>> resultList1= budgControlPlanExecMapper.queryBudgMatOutMedCostOne(matOutMapVo);
				 		
						for (Map<String, Object> resultMap : resultList1) {
							control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
							
							if ("01".equals(resultMap.get("CONT_L").toString())) //全院
							    control_str.append("全院,");
							else
							    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
							
							//control_str.append(""+resultMap.get("MAT_TYPE_NAME")+","); //预算项
							control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
							control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
							control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
							if ("02".equals(resultMap.get("CONT_W"))){  //'02' 提示控制 ，'03' 严格控制
							    control_str.append("提示控制.");
							    work_flag_4 = 1;
							} else if ("03".equals(resultMap.get("CONT_W"))){ 
							    control_str.append("严格控制."); 
							    work_flag_5 = 1;
							}
						}	
					}
					else if( "03".equals(linkCodeMap.get("CONT_M").toString()) ){
				 		List<Map<String, Object>> resultList3= budgControlPlanExecMapper.queryBudgMatOutMedCostThree(matOutMapVo);
				 		
						for (Map<String, Object> resultMap : resultList3) {
							control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
							
							if ("01".equals(resultMap.get("CONT_L").toString())) //全院
							    control_str.append("全院,");
							else
							    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
							
							//control_str.append(""+resultMap.get("MAT_TYPE_NAME")+","); //预算项
							control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
							control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
							control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
							if ("02".equals(resultMap.get("CONT_W"))){  //'02' 提示控制 ，'03' 严格控制
							    control_str.append("提示控制.");
							    work_flag_4 = 1;
							} else if ("03".equals(resultMap.get("CONT_W"))){ 
							    control_str.append("严格控制."); 
							    work_flag_5 = 1;
							}
							
						}
						
					}
				}
			}
			if (work_flag_5==1){
				return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"5\"}");
			}else if (work_flag_4==1){
				return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"4\"}");
			}
			return JSONObject.parseObject("{\"work_msg\":\"无消息\",\"work_flag\":\"1\"}");
			
		}
	}
		/**
	     * 预算控制执行过程查看 各计算项数据查询 --采购入库
	     */
		@Override
		public Map<String, Object> queryControlExecMatInProcess(Map<String, Object> mapVo) throws DataAccessException {
			
			String returnMap = "";
			StringBuffer err_sb = new StringBuffer();
			int count = budgControlPlanExecMapper.queryLinkCodeIsExists(mapVo);
			//没有方案或状态值没在控制范围内
			if (count == 0){
				return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");
			}else{
				
				List<Map<String,Object>> listVo =  budgControlPlanExecMapper.queryLinkCodeMap(mapVo);
				
				if (listVo.size() ==0)
					return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");
				
				int work_flag_4 = 0;
				int work_flag_5 = 0;
				StringBuffer control_str = new StringBuffer();
				for(Map<String, Object> linkCodeMap : listVo) {
					////PLAN_CODE ='01' 医疗支出预算 ,'0103' 材料支出预算
					if("05".equals(linkCodeMap.get("PLAN_CODE").toString())){
						// CONT_M: 01非明细控制、02明细控制、03明细总额控制
						Map<String, Object> matOutMapVo = new HashMap<String, Object>();
						matOutMapVo.put("group_id", linkCodeMap.get("GROUP_ID"));
						matOutMapVo.put("hos_id",   linkCodeMap.get("HOS_ID"));
						matOutMapVo.put("copy_code", linkCodeMap.get("COPY_CODE"));
				 		matOutMapVo.put("plan_code", linkCodeMap.get("PLAN_CODE"));
				 		matOutMapVo.put("plan_name", linkCodeMap.get("PLAN_NAME"));
				 		matOutMapVo.put("mod_code", linkCodeMap.get("MOD_CODE"));
				 		matOutMapVo.put("work_link_code", linkCodeMap.get("LINK_CODE"));
				 		matOutMapVo.put("work_link_name", linkCodeMap.get("LINK_NAME"));
				 		matOutMapVo.put("tab_code", linkCodeMap.get("TAB_CODE"));
				 		matOutMapVo.put("work_cont_code", linkCodeMap.get("CONT_NOTE"));
				 		matOutMapVo.put("use_state", linkCodeMap.get("USE_STATE"));
				 		matOutMapVo.put("re_link", linkCodeMap.get("RE_LINK"));	
				 		matOutMapVo.put("month_begin_date", mapVo.get("month_begin_date"));
				 		matOutMapVo.put("month_end_date", mapVo.get("month_end_date"));
				 		matOutMapVo.put("year_begin_date", mapVo.get("year_begin_date"));
				 		matOutMapVo.put("year_end_date", mapVo.get("year_end_date"));
				 		matOutMapVo.put("budg_year", mapVo.get("budg_year"));
				 		matOutMapVo.put("budg_month", mapVo.get("budg_month"));
				 		matOutMapVo.put("work_select_id", mapVo.get("work_select_id"));
				 		
						if( "02".equals(linkCodeMap.get("CONT_M").toString()) ){
					 		List<Map<String, Object>> resultList3= budgControlPlanExecMapper.queryBudgMatInMatPurTwo(matOutMapVo);
					 		
							for (Map<String, Object> resultMap : resultList3) {
								control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
								
								if ("01".equals(resultMap.get("CONT_L").toString())) //全院
								    control_str.append("全院,");
								else
								    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
								
								control_str.append(""+resultMap.get("MAT_TYPE_NAME")+","); //预算项
								control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
								control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
								control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
								if ("02".equals(resultMap.get("CONT_W"))){  //'02' 提示控制 ，'03' 严格控制
								    control_str.append("提示控制.");
								    work_flag_4 = 1;
								} else if ("03".equals(resultMap.get("CONT_W"))){ 
								    control_str.append("严格控制."); 
								    work_flag_5 = 1;
								}			
							}				
						}
					}
					//PLAN_CODE ='01' 医疗支出预算
				}
				if (work_flag_5==1){
					return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"5\"}");
				}else if (work_flag_4==1){
					return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"4\"}");
				}
				return JSONObject.parseObject("{\"work_msg\":\"无消息\",\"work_flag\":\"1\"}");			
			}
		//return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"workflag\":\"1\"}");
		//return returnMap ;
	}

	@Override
	public Map<String, Object> queryControlExecAssInProcess(Map<String, Object> mapVo) throws DataAccessException {
		
		String returnMap = "";
		StringBuffer err_sb = new StringBuffer();
		int count = budgControlPlanExecMapper.queryLinkCodeIsExists(mapVo);
		//没有方案或状态值没在控制范围内
		if (count == 0){
			return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");
		}else{
			
			List<Map<String,Object>> listVo =  budgControlPlanExecMapper.queryLinkCodeMap(mapVo);
			
			if (listVo.size() ==0){
				return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");
			}
			int work_flag_4 = 0;
			int work_flag_5 = 0;
			StringBuffer control_str = new StringBuffer();
			for(Map<String, Object> linkCodeMap : listVo) {
				////PLAN_CODE ='04' 资产采购预算 
				if("04".equals(linkCodeMap.get("PLAN_CODE").toString())){
					// CONT_M: 01非明细控制、02明细控制、03明细总额控制
					Map<String, Object> matOutMapVo = new HashMap<String, Object>();
					matOutMapVo.put("group_id", linkCodeMap.get("GROUP_ID"));
					matOutMapVo.put("hos_id",   linkCodeMap.get("HOS_ID"));
					matOutMapVo.put("copy_code", linkCodeMap.get("COPY_CODE"));
			 		matOutMapVo.put("plan_code", linkCodeMap.get("PLAN_CODE"));
			 		matOutMapVo.put("plan_name", linkCodeMap.get("PLAN_NAME"));
			 		matOutMapVo.put("mod_code", linkCodeMap.get("MOD_CODE"));
			 		matOutMapVo.put("work_link_code", linkCodeMap.get("LINK_CODE"));
			 		matOutMapVo.put("work_link_name", linkCodeMap.get("LINK_NAME"));
			 		matOutMapVo.put("tab_code", linkCodeMap.get("TAB_CODE"));
			 		matOutMapVo.put("work_cont_code", linkCodeMap.get("CONT_NOTE"));
			 		matOutMapVo.put("use_state", linkCodeMap.get("USE_STATE"));
			 		matOutMapVo.put("re_link", linkCodeMap.get("RE_LINK"));	
			 		matOutMapVo.put("month_begin_date", mapVo.get("month_begin_date"));
			 		matOutMapVo.put("month_end_date", mapVo.get("month_end_date"));
			 		matOutMapVo.put("year_begin_date", mapVo.get("year_begin_date"));
			 		matOutMapVo.put("year_end_date", mapVo.get("year_end_date"));
			 		matOutMapVo.put("budg_year", mapVo.get("budg_year"));
			 		matOutMapVo.put("budg_month", mapVo.get("budg_month"));
			 		matOutMapVo.put("work_select_id", mapVo.get("work_select_id"));
			 		
			 		if(mapVo.get("naturs_code").toString().equals("01")){
			 			matOutMapVo.put("ass_in_main", "ass_in_main_house");
				 		matOutMapVo.put("ass_in_detail", "ass_in_detail_house");
				 		matOutMapVo.put("prm_ass_back_main", "ass_back_house");
				 		matOutMapVo.put("prm_ass_back_detail", "ass_back_detail_house");
				 		matOutMapVo.put("naturs_code", mapVo.get("naturs_code"));
				 		
			 		}else if(mapVo.get("naturs_code").toString().equals("02")){
			 			matOutMapVo.put("ass_in_main", "ass_in_main_special");
				 		matOutMapVo.put("ass_in_detail", "ass_in_detail_special");
				 		matOutMapVo.put("prm_ass_back_main", "ass_back_special");
				 		matOutMapVo.put("prm_ass_back_detail", "ass_back_detail_special");
				 		matOutMapVo.put("prm_ass_back_car", "ass_card_special");
				 		matOutMapVo.put("naturs_code", mapVo.get("naturs_code"));
			 		}else if(mapVo.get("naturs_code").toString().equals("03")){
			 			matOutMapVo.put("ass_in_main", "ass_in_main_General");
				 		matOutMapVo.put("ass_in_detail", "ass_in_detail_General");
				 		matOutMapVo.put("prm_ass_back_main", "ass_back_General");
				 		matOutMapVo.put("prm_ass_back_detail", "ass_back_detail_General");
				 		matOutMapVo.put("prm_ass_back_car", "ass_card_General");
				 		matOutMapVo.put("naturs_code", mapVo.get("naturs_code"));
			 		}
			 		else if(mapVo.get("naturs_code").toString().equals("04")){
			 			matOutMapVo.put("ass_in_main", "ass_in_main_other");
				 		matOutMapVo.put("ass_in_detail", "ass_in_detail_other");
				 		matOutMapVo.put("prm_ass_back_main", "ass_back_other");
				 		matOutMapVo.put("prm_ass_back_detail", "ass_back_detail_other");
				 		matOutMapVo.put("prm_ass_back_car", "ass_card_other");
				 		matOutMapVo.put("naturs_code", mapVo.get("naturs_code"));
			 		}
			 		else if(mapVo.get("naturs_code").toString().equals("05")){
			 			matOutMapVo.put("ass_in_main", "ass_in_main_Inassets");
				 		matOutMapVo.put("ass_in_detail", "ass_in_detail_Inassets");
				 		matOutMapVo.put("prm_ass_back_main", "ass_back_Inassets");
				 		matOutMapVo.put("prm_ass_back_detail", "ass_back_detail_Inassets");
				 		matOutMapVo.put("prm_ass_back_car", "ass_card_Inassets");
				 		matOutMapVo.put("naturs_code", mapVo.get("naturs_code"));
			 		}else{
			 			matOutMapVo.put("ass_in_main", "ass_in_main_land");
				 		matOutMapVo.put("ass_in_detail", "ass_in_detail_land");
				 		matOutMapVo.put("prm_ass_back_main", "ass_back_land");
				 		matOutMapVo.put("prm_ass_back_detail", "ass_back_detail_land");
				 		matOutMapVo.put("prm_ass_back_car", "ass_card_land");
				 		matOutMapVo.put("naturs_code", mapVo.get("naturs_code"));
			 		}
			 		
			 		
					if( "02".equals(linkCodeMap.get("CONT_M").toString()) ){
				 		
				 		List<Map<String, Object>> resultList= budgControlPlanExecMapper.queryWorkBudgAssInList(matOutMapVo);
				 		
				 		
						for (Map<String, Object> resultMap : resultList) {
							
							control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
							
							if ("01".equals(resultMap.get("CONT_L").toString())) //全院
							    control_str.append("全院,");
							else
							   
							
							control_str.append("预算项："+resultMap.get("SUBJ_NAME")+","); //预算项
							control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
							control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
							control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
							if ("02".equals(resultMap.get("CONT_W"))) //'02' 提示控制 ，'03' 严格控制
							    control_str.append("提示控制.");
							else
							    control_str.append("严格控制."); //科室或职能科室
							
							if ("02".equals(resultMap.get("CONT_W"))) { 
								work_flag_4 = 1;
							}
							if ("03".equals(resultMap.get("CONT_W"))) { 
								work_flag_5 = 1;
							}									
						}						
					}
					else if( "01".equals(linkCodeMap.get("CONT_M").toString()) ){
						
				 		List<Map<String, Object>> resultList2= budgControlPlanExecMapper.queryWorkBudgListOne(matOutMapVo);
				 		
						for (Map<String, Object> resultMap : resultList2) {
							control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
							
							if ("01".equals(resultMap.get("CONT_L").toString())) //全院
							    control_str.append("全院,");
							else
							    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
							
							//control_str.append(""+resultMap.get("MAT_TYPE_NAME")+","); //预算项
							control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
							control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
							control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
							if ("02".equals(resultMap.get("CONT_W"))) //'02' 提示控制 ，'03' 严格控制
							    control_str.append("提示控制.");
							else
							    control_str.append("严格控制."); //科室或职能科室
							
							if ("02".equals(resultMap.get("CONT_W"))) { 
								work_flag_4 = 1;
							}
							if ("03".equals(resultMap.get("CONT_W"))) { 
								work_flag_5 = 1;
							}		
						}	
					}
					else if( "03".equals(linkCodeMap.get("CONT_M").toString()) ){
				 		List<Map<String, Object>> resultList3= budgControlPlanExecMapper.queryWorkBudgListThree(matOutMapVo);
				 		
						for (Map<String, Object> resultMap : resultList3) {
							control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
							
							if ("01".equals(resultMap.get("CONT_L").toString())) //全院
							    control_str.append("全院,");
							else
							    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
							
							//control_str.append(""+resultMap.get("MAT_TYPE_NAME")+","); //预算项
							control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
							control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
							control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
							if ("02".equals(resultMap.get("CONT_W"))) //'02' 提示控制 ，'03' 严格控制
							    control_str.append("提示控制.");
							else
							    control_str.append("严格控制."); //科室或职能科室
							
							if ("02".equals(resultMap.get("CONT_W"))) { 
								work_flag_4 = 1;
							}
							if ("03".equals(resultMap.get("CONT_W"))) { 
								work_flag_5 = 1;
							}			
							
						}
						
					}
				}
				
			}
			if (work_flag_5==1){
				return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"5\"}");
			}else if (work_flag_4==1){
				return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"4\"}");
			}
			return JSONObject.parseObject("{\"work_msg\":\"无消息\",\"work_flag\":\"1\"}");
			
		}
		
		//return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"workflag\":\"1\"}");
		//return returnMap ;
	}


		/**
	     * 预算控制执行过程查看 各计算项数据查询 --科室申领
	     */
		@Override
		public Map<String, Object> queryControlExecMatApplyProcess(Map<String, Object> mapVo) throws DataAccessException {
			
			String returnMap = "";
			StringBuffer err_sb = new StringBuffer();
			int count = budgControlPlanExecMapper.queryLinkCodeIsExists(mapVo);
			//没有方案或状态值没在控制范围内
			if (count == 0){
				return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");
			}else{
				
				List<Map<String,Object>> listVo =  budgControlPlanExecMapper.queryLinkCodeMap(mapVo);
				
				if (listVo.size() ==0)
					return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");
				
				int work_flag_4 = 0;
				int work_flag_5 = 0;
				StringBuffer control_str = new StringBuffer();
				for(Map<String, Object> linkCodeMap : listVo) {
					////PLAN_CODE ='01' 医疗支出预算 ,'0103' 材料支出预算
					if("0103".equals(linkCodeMap.get("PLAN_CODE").toString())){
						// CONT_M: 01非明细控制、02明细控制、03明细总额控制
						Map<String, Object> matOutMapVo = new HashMap<String, Object>();
						matOutMapVo.put("group_id", linkCodeMap.get("GROUP_ID"));
						matOutMapVo.put("hos_id",   linkCodeMap.get("HOS_ID"));
						matOutMapVo.put("copy_code", linkCodeMap.get("COPY_CODE"));
				 		matOutMapVo.put("plan_code", linkCodeMap.get("PLAN_CODE"));
				 		matOutMapVo.put("plan_name", linkCodeMap.get("PLAN_NAME"));
				 		matOutMapVo.put("mod_code", linkCodeMap.get("MOD_CODE"));
				 		matOutMapVo.put("work_link_code", linkCodeMap.get("LINK_CODE"));
				 		matOutMapVo.put("work_link_name", linkCodeMap.get("LINK_NAME"));
				 		matOutMapVo.put("tab_code", linkCodeMap.get("TAB_CODE"));
				 		matOutMapVo.put("work_cont_code", linkCodeMap.get("CONT_NOTE"));
				 		matOutMapVo.put("use_state", linkCodeMap.get("USE_STATE"));
				 		matOutMapVo.put("re_link", linkCodeMap.get("RE_LINK"));	
				 		matOutMapVo.put("month_begin_date", mapVo.get("month_begin_date"));
				 		matOutMapVo.put("month_end_date", mapVo.get("month_end_date"));
				 		matOutMapVo.put("year_begin_date", mapVo.get("year_begin_date"));
				 		matOutMapVo.put("year_end_date", mapVo.get("year_end_date"));
				 		matOutMapVo.put("budg_year", mapVo.get("budg_year"));
				 		matOutMapVo.put("budg_month", mapVo.get("budg_month"));
				 		matOutMapVo.put("work_select_id", mapVo.get("work_select_id"));
				 		
						if( "02".equals(linkCodeMap.get("CONT_M").toString()) ){
					 		
					 		List<Map<String, Object>> resultList= budgControlPlanExecMapper.queryBudgMatApplyMatInvTwo(matOutMapVo);
					 		
					 		
							for (Map<String, Object> resultMap : resultList) {
								
								control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
								
								if ("01".equals(resultMap.get("CONT_L").toString())) //全院
								    control_str.append("全院,");
								else
								    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
								
								control_str.append(""+resultMap.get("MAT_TYPE_NAME")+","); //预算项
								control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
								control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
								control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
								if ("02".equals(resultMap.get("CONT_W"))) //'02' 提示控制 ，'03' 严格控制
								    control_str.append("提示控制.");
								else
								    control_str.append("严格控制."); //科室或职能科室
								
								if ("02".equals(resultMap.get("CONT_W"))) { 
									work_flag_4 = 1;
								}
								if ("03".equals(resultMap.get("CONT_W"))) { 
									work_flag_5 = 1;
								}									
							}						
						}
						else if( "01".equals(linkCodeMap.get("CONT_M").toString()) ){
							
					 		List<Map<String, Object>> resultList2= budgControlPlanExecMapper.queryBudgMatApplyMatInvOne(matOutMapVo);
					 		
							for (Map<String, Object> resultMap : resultList2) {
								control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
								
								if ("01".equals(resultMap.get("CONT_L").toString())) //全院
								    control_str.append("全院,");
								else
								    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
								
								//control_str.append(""+resultMap.get("MAT_TYPE_NAME")+","); //预算项
								control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
								control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
								control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
								if ("02".equals(resultMap.get("CONT_W"))) //'02' 提示控制 ，'03' 严格控制
								    control_str.append("提示控制.");
								else
								    control_str.append("严格控制."); //科室或职能科室
								
								if ("02".equals(resultMap.get("CONT_W"))) { 
									work_flag_4 = 1;
								}
								if ("03".equals(resultMap.get("CONT_W"))) { 
									work_flag_5 = 1;
								}		
							}	
						}
						else if( "03".equals(linkCodeMap.get("CONT_M").toString()) ){
					 		List<Map<String, Object>> resultList3= budgControlPlanExecMapper.queryBudgMatApplyMatInvThree(matOutMapVo);
					 		
							for (Map<String, Object> resultMap : resultList3) {
								control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
								
								if ("01".equals(resultMap.get("CONT_L").toString())) //全院
								    control_str.append("全院,");
								else
								    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
								
								//control_str.append(""+resultMap.get("MAT_TYPE_NAME")+","); //预算项
								control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
								control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
								control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
								if ("02".equals(resultMap.get("CONT_W"))) //'02' 提示控制 ，'03' 严格控制
								    control_str.append("提示控制.");
								else
								    control_str.append("严格控制."); //科室或职能科室
								
								if ("02".equals(resultMap.get("CONT_W"))) { 
									work_flag_4 = 1;
								}
								if ("03".equals(resultMap.get("CONT_W"))) { 
									work_flag_5 = 1;
								}			
								
							}
							
						}
					}
					//PLAN_CODE ='01' 医疗支出预算
					if("01".equals(linkCodeMap.get("PLAN_CODE").toString())){
						// CONT_M: 01非明细控制、02明细控制、03明细总额控制
						Map<String, Object> matOutMapVo = new HashMap<String, Object>();
						matOutMapVo.put("group_id", linkCodeMap.get("GROUP_ID"));
						matOutMapVo.put("hos_id",   linkCodeMap.get("HOS_ID"));
						matOutMapVo.put("copy_code", linkCodeMap.get("COPY_CODE"));
				 		matOutMapVo.put("plan_code", linkCodeMap.get("PLAN_CODE"));
				 		matOutMapVo.put("plan_name", linkCodeMap.get("PLAN_NAME"));
				 		matOutMapVo.put("mod_code", linkCodeMap.get("MOD_CODE"));
				 		matOutMapVo.put("work_link_code", linkCodeMap.get("LINK_CODE"));
				 		matOutMapVo.put("work_link_name", linkCodeMap.get("LINK_NAME"));
				 		matOutMapVo.put("tab_code", linkCodeMap.get("TAB_CODE"));
				 		matOutMapVo.put("work_cont_code", linkCodeMap.get("CONT_NOTE"));
				 		matOutMapVo.put("use_state", linkCodeMap.get("USE_STATE"));
				 		matOutMapVo.put("re_link", linkCodeMap.get("RE_LINK"));	
				 		matOutMapVo.put("month_begin_date", mapVo.get("month_begin_date"));
				 		matOutMapVo.put("month_end_date", mapVo.get("month_end_date"));
				 		matOutMapVo.put("year_begin_date", mapVo.get("year_begin_date"));
				 		matOutMapVo.put("year_end_date", mapVo.get("year_end_date"));
				 		matOutMapVo.put("budg_year", mapVo.get("budg_year"));
				 		matOutMapVo.put("budg_month", mapVo.get("budg_month"));
				 		matOutMapVo.put("work_select_id", mapVo.get("work_select_id"));
				 		
						if( "02".equals(linkCodeMap.get("CONT_M").toString()) ){
					 		
					 		List<Map<String, Object>> resultList= budgControlPlanExecMapper.queryBudgMatApplyMedCostTwo(matOutMapVo);
					 		
							for (Map<String, Object> resultMap : resultList) {
								
								control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
								
								if ("01".equals(resultMap.get("CONT_L").toString())) //全院
								    control_str.append("全院,");
								else
								    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
								
								control_str.append(""+resultMap.get("SUBJ_NAME")+","); //预算项
								control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
								control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
								control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
								if ("02".equals(resultMap.get("CONT_W"))) //'02' 提示控制 ，'03' 严格控制
								    control_str.append("提示控制.");
								else
								    control_str.append("严格控制."); //科室或职能科室
								
								if ("02".equals(resultMap.get("CONT_W"))) { 
									work_flag_4 = 1;
								}
								if ("03".equals(resultMap.get("CONT_W"))) { 
									work_flag_5 = 1;
								}									
							}						
						}
						else if( "01".equals(linkCodeMap.get("CONT_M").toString()) ){
							
					 		List<Map<String, Object>> resultList1= budgControlPlanExecMapper.queryBudgMatApplyMedCostOne(matOutMapVo);
					 		
							for (Map<String, Object> resultMap : resultList1) {
								control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
								
								if ("01".equals(resultMap.get("CONT_L").toString())) //全院
								    control_str.append("全院,");
								else
								    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
								
								//control_str.append(""+resultMap.get("MAT_TYPE_NAME")+","); //预算项
								control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
								control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
								control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
								if ("02".equals(resultMap.get("CONT_W"))){  //'02' 提示控制 ，'03' 严格控制
								    control_str.append("提示控制.");
								    work_flag_4 = 1;
								} else if ("03".equals(resultMap.get("CONT_W"))){ 
								    control_str.append("严格控制."); 
								    work_flag_5 = 1;
								}
							}	
						}
						else if( "03".equals(linkCodeMap.get("CONT_M").toString()) ){
					 		List<Map<String, Object>> resultList3= budgControlPlanExecMapper.queryBudgMatApplyMedCostThree(matOutMapVo);
					 		
							for (Map<String, Object> resultMap : resultList3) {
								control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
								
								if ("01".equals(resultMap.get("CONT_L").toString())) //全院
								    control_str.append("全院,");
								else
								    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
								
								//control_str.append(""+resultMap.get("MAT_TYPE_NAME")+","); //预算项
								control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
								control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
								control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
								if ("02".equals(resultMap.get("CONT_W"))){  //'02' 提示控制 ，'03' 严格控制
								    control_str.append("提示控制.");
								    work_flag_4 = 1;
								} else if ("03".equals(resultMap.get("CONT_W"))){ 
								    control_str.append("严格控制."); 
								    work_flag_5 = 1;
								}
								
							}
							
						}
					}
				}
				if (work_flag_5==1){
					return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"5\"}");
				}else if (work_flag_4==1){
					return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"4\"}");
				}
				return JSONObject.parseObject("{\"work_msg\":\"无消息\",\"work_flag\":\"1\"}");
				
			}
		}
	/**
     * 预算控制执行过程查看 各计算项数据查询 --采购计划
     */
	@Override
	public Map<String, Object> queryControlExecMatPurProcess(Map<String, Object> mapVo) throws DataAccessException {
		
		String returnMap = "";
		StringBuffer err_sb = new StringBuffer();
		int count = budgControlPlanExecMapper.queryLinkCodeIsExists(mapVo);
		//没有方案或状态值没在控制范围内或方案未启用
		if (count == 0){
			return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");
		}else{			
			List<Map<String,Object>> listVo =  budgControlPlanExecMapper.queryLinkCodeMap(mapVo);
			if (listVo.size() ==0)
				return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");
			
			int work_flag_4 = 0;
			int work_flag_5 = 0;
			StringBuffer control_str = new StringBuffer();
			for(Map<String, Object> linkCodeMap : listVo) {
				//PLAN_CODE ='05' 05材料采购预算
				if("05".equals(linkCodeMap.get("PLAN_CODE").toString())){
					// CONT_M: 01非明细控制、02明细控制、03明细总额控制
					Map<String, Object> matOutMapVo = new HashMap<String, Object>();
					matOutMapVo.put("group_id", linkCodeMap.get("GROUP_ID"));
					matOutMapVo.put("hos_id",   linkCodeMap.get("HOS_ID"));
					matOutMapVo.put("copy_code", linkCodeMap.get("COPY_CODE"));
			 		matOutMapVo.put("plan_code", linkCodeMap.get("PLAN_CODE"));
			 		matOutMapVo.put("plan_name", linkCodeMap.get("PLAN_NAME"));
			 		matOutMapVo.put("mod_code", linkCodeMap.get("MOD_CODE"));
			 		matOutMapVo.put("work_link_code", linkCodeMap.get("LINK_CODE"));
			 		matOutMapVo.put("work_link_name", linkCodeMap.get("LINK_NAME"));
			 		matOutMapVo.put("tab_code", linkCodeMap.get("TAB_CODE"));
			 		matOutMapVo.put("work_cont_code", linkCodeMap.get("CONT_NOTE"));
			 		matOutMapVo.put("use_state", linkCodeMap.get("USE_STATE"));
			 		matOutMapVo.put("re_link", linkCodeMap.get("RE_LINK"));	
			 		matOutMapVo.put("month_begin_date", mapVo.get("month_begin_date"));
			 		matOutMapVo.put("month_end_date", mapVo.get("month_end_date"));
			 		matOutMapVo.put("year_begin_date", mapVo.get("year_begin_date"));
			 		matOutMapVo.put("year_end_date", mapVo.get("year_end_date"));
			 		matOutMapVo.put("budg_year", mapVo.get("budg_year"));
			 		matOutMapVo.put("budg_month", mapVo.get("budg_month"));
			 		matOutMapVo.put("work_select_id", mapVo.get("work_select_id"));
			 		
					if( "02".equals(linkCodeMap.get("CONT_M").toString()) ){
				 		List<Map<String, Object>> resultList3= budgControlPlanExecMapper.queryBudgMatPurMatPurTwo(matOutMapVo);
				 		
						for (Map<String, Object> resultMap : resultList3) {
							control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
							
							if ("01".equals(resultMap.get("CONT_L").toString())) //全院
							    control_str.append("全院,");
							else
							    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
							
							control_str.append(""+resultMap.get("MAT_TYPE_NAME")+","); //预算项
							control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
							control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
							control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
							if ("02".equals(resultMap.get("CONT_W"))){  //'02' 提示控制 ，'03' 严格控制
							    control_str.append("提示控制.");
							    work_flag_4 = 1;
							} else if ("03".equals(resultMap.get("CONT_W"))){ 
							    control_str.append("严格控制."); 
							    work_flag_5 = 1;
							}			
						}				
					}
				}
				//PLAN_CODE ='01' 医疗支出预算
			}
			if (work_flag_5==1){
				return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"5\"}");
			}else if (work_flag_4==1){
				return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"4\"}");
			}
			return JSONObject.parseObject("{\"work_msg\":\"无消息\",\"work_flag\":\"1\"}");			
		}
		
	}
	/**
     * 预算控制执行过程查看 各计算项数据查询 --物流订单
     */
	@Override
	public Map<String, Object> queryControlExecMatOrderProcess(Map<String, Object> mapVo) throws DataAccessException {
		
		String returnMap = "";
		StringBuffer err_sb = new StringBuffer();
		int count = budgControlPlanExecMapper.queryLinkCodeIsExists(mapVo);
		//没有方案或状态值没在控制范围内或方案未启用
		if (count == 0){
			return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");
		}else{
			List<Map<String,Object>> listVo =  budgControlPlanExecMapper.queryLinkCodeMap(mapVo);
			if (listVo.size() ==0)
				return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");
			
			int work_flag_4 = 0;
			int work_flag_5 = 0;
			StringBuffer control_str = new StringBuffer();
			for(Map<String, Object> linkCodeMap : listVo) {
				//PLAN_CODE ='05' 05材料采购预算
				if("05".equals(linkCodeMap.get("PLAN_CODE").toString())){
					// CONT_M: 01非明细控制、02明细控制、03明细总额控制
					Map<String, Object> matOutMapVo = new HashMap<String, Object>();
					matOutMapVo.put("group_id", linkCodeMap.get("GROUP_ID"));
					matOutMapVo.put("hos_id",   linkCodeMap.get("HOS_ID"));
					matOutMapVo.put("copy_code", linkCodeMap.get("COPY_CODE"));
			 		matOutMapVo.put("plan_code", linkCodeMap.get("PLAN_CODE"));
			 		matOutMapVo.put("plan_name", linkCodeMap.get("PLAN_NAME"));
			 		matOutMapVo.put("mod_code", linkCodeMap.get("MOD_CODE"));
			 		matOutMapVo.put("work_link_code", linkCodeMap.get("LINK_CODE"));
			 		matOutMapVo.put("work_link_name", linkCodeMap.get("LINK_NAME"));
			 		matOutMapVo.put("tab_code", linkCodeMap.get("TAB_CODE"));
			 		matOutMapVo.put("work_cont_code", linkCodeMap.get("CONT_NOTE"));
			 		matOutMapVo.put("use_state", linkCodeMap.get("USE_STATE"));
			 		matOutMapVo.put("re_link", linkCodeMap.get("RE_LINK"));	
			 		matOutMapVo.put("month_begin_date", mapVo.get("month_begin_date"));
			 		matOutMapVo.put("month_end_date", mapVo.get("month_end_date"));
			 		matOutMapVo.put("year_begin_date", mapVo.get("year_begin_date"));
			 		matOutMapVo.put("year_end_date", mapVo.get("year_end_date"));
			 		matOutMapVo.put("budg_year", mapVo.get("budg_year"));
			 		matOutMapVo.put("budg_month", mapVo.get("budg_month"));
			 		matOutMapVo.put("work_select_id", mapVo.get("work_select_id"));
			 		
					if( "02".equals(linkCodeMap.get("CONT_M").toString()) ){
				 		List<Map<String, Object>> resultList= budgControlPlanExecMapper.queryBudgMatOrderMatPurTwo(matOutMapVo);
				 		
						for (Map<String, Object> resultMap : resultList) {
							control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
							
							if ("01".equals(resultMap.get("CONT_L").toString())) //全院
							    control_str.append("全院,");
							else
							    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
							
							control_str.append(""+resultMap.get("MAT_TYPE_NAME")+","); //预算项
							control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
							control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
							control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
							if ("02".equals(resultMap.get("CONT_W"))){  //'02' 提示控制 ，'03' 严格控制
							    control_str.append("提示控制.");
							    work_flag_4 = 1;
							} else if ("03".equals(resultMap.get("CONT_W"))){ 
							    control_str.append("严格控制."); 
							    work_flag_5 = 1;
							}			
						}				
					}
				}
				//PLAN_CODE ='01' 医疗支出预算
			}
			if (work_flag_5==1){
				return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"5\"}");
			}else if (work_flag_4==1){
				return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"4\"}");
			}
			return JSONObject.parseObject("{\"work_msg\":\"无消息\",\"work_flag\":\"1\"}");			
		}
	}
	/**
     * 预算控制执行过程查看 各计算项数据查询 --会计凭证
     */
	@Override
	public Map<String, Object> queryControlExecAccVouchProcess(Map<String, Object> mapVo) throws DataAccessException {
		
		String returnMap = "";
		StringBuffer err_sb = new StringBuffer();
		int count = budgControlPlanExecMapper.queryLinkCodeIsExists(mapVo);
		//没有方案或状态值没在控制范围内或方案未启用
		if (count == 0){
			return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");
		}else{
			List<Map<String,Object>> listVo =  budgControlPlanExecMapper.queryLinkCodeMap(mapVo);
			if (listVo.size() ==0)
				return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");
			
			int work_flag_4 = 0;
			int work_flag_5 = 0;
			StringBuffer control_str = new StringBuffer();
			for(Map<String, Object> linkCodeMap : listVo) {
				//PLAN_CODE ='01' 01医疗支出预算
				if("01".equals(linkCodeMap.get("PLAN_CODE").toString())){
					// CONT_M: 01非明细控制、02明细控制、03明细总额控制
					Map<String, Object> matOutMapVo = new HashMap<String, Object>();
					matOutMapVo.put("group_id", linkCodeMap.get("GROUP_ID"));
					matOutMapVo.put("hos_id",   linkCodeMap.get("HOS_ID"));
					matOutMapVo.put("copy_code", linkCodeMap.get("COPY_CODE"));
			 		matOutMapVo.put("plan_code", linkCodeMap.get("PLAN_CODE"));
			 		matOutMapVo.put("plan_name", linkCodeMap.get("PLAN_NAME"));
			 		matOutMapVo.put("mod_code", linkCodeMap.get("MOD_CODE"));
			 		matOutMapVo.put("work_link_code", linkCodeMap.get("LINK_CODE"));
			 		matOutMapVo.put("work_link_name", linkCodeMap.get("LINK_NAME"));
			 		matOutMapVo.put("tab_code", linkCodeMap.get("TAB_CODE"));
			 		matOutMapVo.put("work_cont_code", linkCodeMap.get("CONT_NOTE"));
			 		matOutMapVo.put("use_state", linkCodeMap.get("USE_STATE"));
			 		matOutMapVo.put("re_link", linkCodeMap.get("RE_LINK"));	
			 		matOutMapVo.put("month_begin_date", mapVo.get("month_begin_date"));
			 		matOutMapVo.put("month_end_date", mapVo.get("month_end_date"));
			 		matOutMapVo.put("year_begin_date", mapVo.get("year_begin_date"));
			 		matOutMapVo.put("year_end_date", mapVo.get("year_end_date"));
			 		matOutMapVo.put("budg_year", mapVo.get("budg_year"));
			 		matOutMapVo.put("budg_month", mapVo.get("budg_month"));
			 		matOutMapVo.put("work_select_id", mapVo.get("work_select_id"));
			 		
					if( "02".equals(linkCodeMap.get("CONT_M").toString()) ){
				 		List<Map<String, Object>> resultList= budgControlPlanExecMapper.queryBudgAccVouchMedCostTwo(matOutMapVo);
				 		
						for (Map<String, Object> resultMap : resultList) {
							control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
							
							if ("01".equals(resultMap.get("CONT_L").toString())) //全院
							    control_str.append("全院,");
							else
							    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
							
							control_str.append(""+resultMap.get("SUBJ_NAME")+","); //预算项
							control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
							control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
							control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
							if ("02".equals(resultMap.get("CONT_W"))){  //'02' 提示控制 ，'03' 严格控制
							    control_str.append("提示控制.");
							    work_flag_4 = 1;
							} else if ("03".equals(resultMap.get("CONT_W"))){ 
							    control_str.append("严格控制."); 
							    work_flag_5 = 1;
							}			
						}				
					}
					else if( "01".equals(linkCodeMap.get("CONT_M").toString()) ){
				 		List<Map<String, Object>> resultList= budgControlPlanExecMapper.queryBudgAccVouchMedCostOne(matOutMapVo);
				 		
						for (Map<String, Object> resultMap : resultList) {
							control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
							
							if ("01".equals(resultMap.get("CONT_L").toString())) //全院
							    control_str.append("全院,");
							else
							    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
							
							//control_str.append(""+resultMap.get("SUBJ_NAME")+","); //预算项
							control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
							control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
							control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
							if ("02".equals(resultMap.get("CONT_W"))){  //'02' 提示控制 ，'03' 严格控制
							    control_str.append("提示控制.");
							    work_flag_4 = 1;
							} else if ("03".equals(resultMap.get("CONT_W"))){ 
							    control_str.append("严格控制."); 
							    work_flag_5 = 1;
							}		
						}				
					}
					else if( "03".equals(linkCodeMap.get("CONT_M").toString()) ){
				 		List<Map<String, Object>> resultList= budgControlPlanExecMapper.queryBudgAccVouchMedCostThree(matOutMapVo);
				 		
						for (Map<String, Object> resultMap : resultList) {
							control_str.append(""+linkCodeMap.get("PLAN_NAME")+","); //方案
							
							if ("01".equals(resultMap.get("CONT_L").toString())) //全院
							    control_str.append("全院,");
							else
							    control_str.append(""+resultMap.get("DEPT_NAME")+","); //科室或职能科室
							
							//control_str.append(""+resultMap.get("SUBJ_NAME")+","); //预算项
							control_str.append("预算值："+resultMap.get("PLAN_VALUE")+",");
							control_str.append("已发生值："+resultMap.get("EXEC_VALUE")+",");
							control_str.append("单据值"+resultMap.get("OCCUR_VALUE")+",");
							if ("02".equals(resultMap.get("CONT_W"))){  //'02' 提示控制 ，'03' 严格控制
							    control_str.append("提示控制.");
							    work_flag_4 = 1;
							} else if ("03".equals(resultMap.get("CONT_W"))){ 
							    control_str.append("严格控制."); 
							    work_flag_5 = 1;
							}		
						}				
					}
				}
				//PLAN_CODE ='01' 医疗支出预算
			}
			if (work_flag_5==1){
				return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"5\"}");
			}else if (work_flag_4==1){
				return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"4\"}");
			}
			return JSONObject.parseObject("{\"work_msg\":\"无消息\",\"work_flag\":\"1\"}");	
		}
	}
}
