/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.project.balanceadjust;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgProj;
import com.chd.hrp.budg.entity.BudgProjRemainAdj;
import com.chd.hrp.budg.service.project.balanceadjust.BudgProjRemainAdjService;
/**
 * 
 * @Description:
 * 项目预算
 * @Table:
 * BUDG_PROJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgProjRemainAdjController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgProjRemainAdjController.class);
	
	//引入Service服务
	@Resource(name = "budgProjRemainAdjService")
	private final BudgProjRemainAdjService budgProjRemainAdjService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/balanceadjust/budgProjectBalanceAdjust", method = RequestMethod.GET)
	public String budgProjRemainAdjMainPage(Model mode) throws Exception {

		return "hrp/budg/project/balanceadjust/budgProjBalanceAdjMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/balanceadjust/budgProjRemainAdjAddPage", method = RequestMethod.GET)
	public String budgProjRemainAdjAddPage(Model mode) throws Exception {
		
		
		return "hrp/budg/project/balanceadjust/budgProjBalanceAdjAdd";

	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/balanceadjust/budgProjRemainAdjLoadInPage", method = RequestMethod.GET)
	public String budgProjRemainAdjLoadInPage(Model mode) throws Exception {
		
		
		return "hrp/budg/project/balanceadjust/budgProjBalanceAdjLoadIn";
		
	}

	/**
	 * @Description 
	 * 添加数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/balanceadjust/addBudgProjRemainAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgProjRemainAdj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgProjRemainAdj = "";
		
		try {
			//组装 生成 预算调整单号 参数Map
			Map<String,Object> mapForAdjCode  = new HashMap<String,Object>();
		
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			//获取当前年份
			int Year=Calendar.getInstance().get(Calendar.YEAR);
			mapVo.put("budg_year", Year);
			
			// 生成 预算调整单号
			if("系统生成".equals(mapVo.get("adj_code"))){
				mapForAdjCode.put("group_id", mapVo.get("group_id")) ;
				
				mapForAdjCode.put("hos_id", mapVo.get("hos_id")) ;
				
				mapForAdjCode.put("copy_code", mapVo.get("copy_code")) ;
				
				mapForAdjCode.put("year", mapVo.get("budg_year")) ;
				
				mapVo.put("adj_code",budgProjRemainAdjService.setBudgApplyCode(mapForAdjCode));
			}
			
			//对不是从前台传过来的表中字段进行处理
	        
			mapVo.put("maker", SessionManager.getUserId());//制单人为当前用户
			
			Date date = DateUtil.getCurrenDate("yyyy-MM-dd");;//制单日期为当前时间
			mapVo.put("make_date", date);
			
			mapVo.put("checker", "");//新添加数据  审核人 为空
			mapVo.put("check_date", "");//新添加数据   审核日期为空
			
			mapVo.put("state", "01");//添加时  单据状态为新建
			
			//事务控制  接收service层信息
			budgProjRemainAdj = budgProjRemainAdjService.add(mapVo);
    		
		} catch (Exception e) {
			budgProjRemainAdj = e.getMessage();
		}
    	return JSONObject.parseObject(budgProjRemainAdj);
		
	}
	
	
	/**
	 * @Description 
	 * 更新跳转页面 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/balanceadjust/budgProjRemainAdjUpdatePage", method = RequestMethod.GET)
	public String budgProjRemainAdjUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		BudgProjRemainAdj budgProjRemainAdj = new BudgProjRemainAdj();
    
		budgProjRemainAdj = budgProjRemainAdjService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgProjRemainAdj.getGroup_id());
		mode.addAttribute("hos_id", budgProjRemainAdj.getHos_id());
		mode.addAttribute("copy_code", budgProjRemainAdj.getCopy_code());
		mode.addAttribute("adj_code", budgProjRemainAdj.getAdj_code());
		mode.addAttribute("remark", budgProjRemainAdj.getRemark());
		mode.addAttribute("remain_adj_sum", budgProjRemainAdj.getRemain_adj_sum());
		mode.addAttribute("maker", budgProjRemainAdj.getMaker());
		mode.addAttribute("maker_name", budgProjRemainAdj.getMaker_name());
		mode.addAttribute("make_date", budgProjRemainAdj.getMake_date());
		mode.addAttribute("checker", budgProjRemainAdj.getChecker());
		mode.addAttribute("checker_name", budgProjRemainAdj.getChecker_name());
		mode.addAttribute("check_date", budgProjRemainAdj.getCheck_date());
		mode.addAttribute("state", budgProjRemainAdj.getState());
		
		return "hrp/budg/project/balanceadjust/budgProjBalanceAdjUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/balanceadjust/updateBudgProjRemainAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgProjRemainAdj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgProjRemainAdj = "";
		
		try {
		
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			
			//事务控制  接收service层信息
			budgProjRemainAdj = budgProjRemainAdjService.update(mapVo);
    		
		} catch (Exception e) {
			budgProjRemainAdj = e.getMessage();
		}
    	return JSONObject.parseObject(budgProjRemainAdj);
	}
	
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/balanceadjust/addOrUpdateBudgProjRemainAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgProjRemainAdj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgProjRemainAdj = "";
		
		try {
		
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			
			//事务控制  接收service层信息
			budgProjRemainAdj = budgProjRemainAdjService.update(mapVo);
    		
		} catch (Exception e) {
			budgProjRemainAdj = e.getMessage();
		}
    	return JSONObject.parseObject(budgProjRemainAdj);
	}
	/**
	 * @Description 
	 * 删除数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/balanceadjust/deleteBudgProjRemainAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgProjRemainAdj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("adj_code", ids[3])   ;
			
			listVo.add(mapVo);
      
    }
    
		String budgProjRemainAdjJson = budgProjRemainAdjService.deleteBatch(listVo);
		
		return JSONObject.parseObject(budgProjRemainAdjJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/balanceadjust/queryBudgProjRemainAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjRemainAdj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		  if(mapVo.get("group_id") == null){
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
				}
				
				if(mapVo.get("hos_id") == null){
					
				mapVo.put("hos_id", SessionManager.getHosId());
				
				}
				
				if(mapVo.get("copy_code") == null){
					
				mapVo.put("copy_code", SessionManager.getCopyCode());
		        
				}
				
				String budgProjRemainAdj = budgProjRemainAdjService.query(getPage(mapVo));

				return JSONObject.parseObject(budgProjRemainAdj);
		
	}
	
	/**
	 * 
	 * 审核
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/balanceadjust/reviewbudgProjRemainAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reviewbudgProjRemainAdj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("adj_code", ids[3])   ;
			
			listVo.add(mapVo);
		}
		
		String budgProjRemainAdjJson = budgProjRemainAdjService.updateAdjSate(listVo);
		
		return JSONObject.parseObject(budgProjRemainAdjJson);
		
	}
	
	/**
	 * 
	 * 销审
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/balanceadjust/cancelbudgProjRemainAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelbudgProjRemainAdj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("adj_code", ids[3])   ;
			
			listVo.add(mapVo);
		}
		
		String budgProjRemainAdjJson = budgProjRemainAdjService.updateCancelBatch(listVo);
		
		return JSONObject.parseObject(budgProjRemainAdjJson);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/balanceadjust/queryRemainMoney", method = RequestMethod.POST)
	@ResponseBody
	public String queryRemainMoney(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		  if(mapVo.get("group_id") == null){
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
				}
				
				if(mapVo.get("hos_id") == null){
					
				mapVo.put("hos_id", SessionManager.getHosId());
				
				}
				
				if(mapVo.get("copy_code") == null){
					
				mapVo.put("copy_code", SessionManager.getCopyCode());
		        
				}
				
				String usableAmount = budgProjRemainAdjService.queryUsableAmount(getPage(mapVo));
				
				
				
				return  usableAmount;
		
	}
	
	/**
	 * @Description 
	 * 根据项目ID 资金来源ID  查询数据明细
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/balanceadjust/queryProjMessage", method = RequestMethod.POST)
	@ResponseBody
	public String queryProjMessage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//默认查询当前年度数据明细
		//获取当前年度
		int Year=Calendar.getInstance().get(Calendar.YEAR);
		mapVo.put("budg_year", Year);
			
		String projDetail = budgProjRemainAdjService.queryProjMessage(mapVo);
		
		return  projDetail;
		
	}
	
	/**
	 * @Description 
	 * 根据条件组合   查询数据明细
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/balanceadjust/queryProjDetailByCondition", method = RequestMethod.POST)
	@ResponseBody
	public String queryProjDetailByCondition(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//默认查询当前年度数据明细
		//获取当前年度
		int Year=Calendar.getInstance().get(Calendar.YEAR);
		mapVo.put("budg_year", Year);
		
		String projDetail = budgProjRemainAdjService.queryProjDetailByCondition(getPage(mapVo));
		
		return projDetail;
		
	}
	
	/**
	 * 根据项目 查询资金来源 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/balanceadjust/queryBudgSourceByProj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSourceByProj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		//获取当前年度
		int Year=Calendar.getInstance().get(Calendar.YEAR);
		mapVo.put("budg_year", Year);
		String source = budgProjRemainAdjService.queryBudgSourceByProj(mapVo);
		
		return source;

	}
	
	
	/**
	 * @Description 
	 *修改查询明细数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/balanceadjust/querBudgProjBalanceDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querBudgProjBalanceDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		  if(mapVo.get("group_id") == null){
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
				}
				
				if(mapVo.get("hos_id") == null){
					
				mapVo.put("hos_id", SessionManager.getHosId());
				
				}
				
				if(mapVo.get("copy_code") == null){
					
				mapVo.put("copy_code", SessionManager.getCopyCode());
		        
				}
				
				String budgProjRemainAdj = budgProjRemainAdjService.querBudgProjBalanceDetail(getPage(mapVo));

				return JSONObject.parseObject(budgProjRemainAdj);
		
	}
}

