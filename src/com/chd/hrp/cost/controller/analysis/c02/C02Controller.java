/**
 * @Copyright: Copyright (c) 2116-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller.analysis.c02;

import java.util.Date;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.cost.controller.CostIncomeDetailController;
import com.chd.hrp.cost.service.analysis.c02.C02Service;

/**
 * @Title. @Description. 构成分析请求类
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class C02Controller extends BaseController {

	private static Logger logger = Logger.getLogger(C02Controller.class);
	@Resource(name = "c02Service")
	private final C02Service c02Service = null;
	
	
	/**
	 * 全院成本构成比例
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0200MainPage",method = RequestMethod.GET)
	public String c0200MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0200Main";
	};
	
	/**
	 * 全院成本构成比例查询
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0200" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0200(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			
		    mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			
	        System.out.println(mapVo);
			
			String analysisC0201 = c02Service.queryAnalysisC0200(getPage(mapVo));

			return JSONObject.parseObject(analysisC0201);
	}
	
	
	/**
	 * 医疗构成分析总表c0201页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0201MainPage",method = RequestMethod.GET)
	public String c0201MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0201Main";
	};
	
	/**
	 * 医疗构成分析总表c0201查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0201" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0201(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			
//			//时间查询的处理
//			String month = (String) mapVo.get("acc_month1");
//			int  month1 = Integer.parseInt(month);
//			mapVo.put("acc_month1", (month1+1)+"");
			
			String analysisC0201 = c02Service.queryAnalysisC0201(getPage(mapVo));

			return JSONObject.parseObject(analysisC0201);
	}
	
	
	
	// 添加页面
				@RequestMapping(value = "/hrp/cost/analysis/c02/costAnalysisC0201AddPage", method = RequestMethod.GET)
				public String costAnalysisC0201AddPage(Model mode) throws Exception {

					return "hrp/cost/analysis/c02/c0201Add";

				}
				
				/**
				*科室收入数据明细表<BR>
				*保存
				*/
				@RequestMapping(value = "/hrp/cost/analysis/c02/addCostAnalysisC0201", method = RequestMethod.POST)
				@ResponseBody
				
				public Map<String, Object> addCostAnalysisC0201(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
					if(mapVo.get("group_id") == null){
					mapVo.put("group_id", SessionManager.getGroupId());
					}
					if(mapVo.get("hos_id") == null){
					mapVo.put("hos_id", SessionManager.getHosId());
					}
					if(mapVo.get("copy_code") == null){
			        mapVo.put("copy_code", SessionManager.getCopyCode());
					}
					mapVo.put("create_user", SessionManager.getUserId());
					mapVo.put("create_date", new Date());
					String costAnalysisC0201Json = c02Service.addCostAnalysisC0201(mapVo);

					return JSONObject.parseObject(costAnalysisC0201Json);
					
				}
	
	
	/**
	 * 医疗构成分析总表c0202页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0202MainPage",method = RequestMethod.GET)
	public String c0202MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0202Main";
	};
	
	/**
	 * 医疗构成分析总表c0201查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0202" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0202(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0202 = c02Service.queryAnalysisC0202(getPage(mapVo));

			return JSONObject.parseObject(analysisC0202);
	}
	
	/**
	 * 医疗构成分析总表c0203页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0203MainPage",method = RequestMethod.GET)
	public String c0203MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0203Main";
	};
	
	/**
	 * 医疗构成分析总表c0203查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0203" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0203(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			
//			//时间查询的处理
//			String month = (String) mapVo.get("acc_month1");
//			int  month1 = Integer.parseInt(month);
//			mapVo.put("acc_month1", (month1+1)+"");
			
			String analysisC0203 = c02Service.queryAnalysisC0203(getPage(mapVo));

			return JSONObject.parseObject(analysisC0203);
	}
	
	/**
	 * 医疗构成分析总表c0204页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0204MainPage",method = RequestMethod.GET)
	public String c0204MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0204Main";
	};
	
	/**
	 * 医疗构成分析总表c0204查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0204" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0204(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			
//			//时间查询的处理
//			String month = (String) mapVo.get("acc_month1");
//			int  month1 = Integer.parseInt(month);
//			mapVo.put("acc_month1", (month1+1)+"");
			
			String analysisC0204 = c02Service.queryAnalysisC0204(getPage(mapVo));

			return JSONObject.parseObject(analysisC0204);
	}
	
	/**
	 * 医疗构成分析总表c0205页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0205MainPage",method = RequestMethod.GET)
	public String c0205MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0205Main";
	};
	
	/**
	 * 医疗构成分析总表c0204查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0205" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0205(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			
//			//时间查询的处理
//			String month = (String) mapVo.get("acc_month1");
//			int  month1 = Integer.parseInt(month);
//			mapVo.put("acc_month1", (month1+1)+"");
			
			String analysisC0205 = c02Service.queryAnalysisC0205(getPage(mapVo));

			return JSONObject.parseObject(analysisC0205);
	}
	
	/**
	 * 医疗构成分析总表c0206页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0206MainPage",method = RequestMethod.GET)
	public String c0206MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0206Main";
	};
	
	/**
	 * 医疗构成分析总表c0206查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0206" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0206(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0206 = c02Service.queryAnalysisC0206(getPage(mapVo));

			return JSONObject.parseObject(analysisC0206);
	}
	
	/**
	 * 医疗构成分析总表c0207页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0207MainPage",method = RequestMethod.GET)
	public String c0207MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0207Main";
	};
	
	/**
	 * 医疗构成分析总表c0207查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0207" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0207(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			
//			//时间查询的处理
//			String month = (String) mapVo.get("acc_month1");
//			int  month1 = Integer.parseInt(month);
//			mapVo.put("acc_month1", (month1+1)+"");
			
			String analysisC0207 = c02Service.queryAnalysisC0207(getPage(mapVo));

			return JSONObject.parseObject(analysisC0207);
	}
	
	/**
	 * 医疗构成分析总表c0208页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0208MainPage",method = RequestMethod.GET)
	public String c0208MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0208Main";
	};
	
	/**
	 * 医疗构成分析总表c0204查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0208" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0208(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			
//			//时间查询的处理
//			String month = (String) mapVo.get("acc_month1");
//			int  month1 = Integer.parseInt(month);
//			mapVo.put("acc_month1", (month1+1)+"");
			
			String analysisC0208 = c02Service.queryAnalysisC0208(getPage(mapVo));

			return JSONObject.parseObject(analysisC0208);
	}
	
	/**
	 * 医疗构成分析总表c0209页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0209MainPage",method = RequestMethod.GET)
	public String c0209MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0209Main";
	};
	
	/**
	 * 医疗构成分析总表c0204查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0209" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0209(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			
//			//时间查询的处理
//			String month = (String) mapVo.get("acc_month1");
//			int  month1 = Integer.parseInt(month);
//			mapVo.put("acc_month1", (month1+1)+"");
			
			String analysisC0209 = c02Service.queryAnalysisC0209(getPage(mapVo));

			return JSONObject.parseObject(analysisC0209);
	}
	
	
	/**
	 * 医疗构成分析总表c0210页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0210MainPage",method = RequestMethod.GET)
	public String c0210MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0210Main";
	};
	
	/**
	 * 医疗构成分析总表c0204查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0210" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0210(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			
//			//时间查询的处理
//			String month = (String) mapVo.get("acc_month1");
//			int  month1 = Integer.parseInt(month);
//			mapVo.put("acc_month1", (month1+1)+"");
			
			String analysisC0210 = c02Service.queryAnalysisC0210(getPage(mapVo));

			return JSONObject.parseObject(analysisC0210);
	}
	
	/**
	 * 医疗构成分析总表c0211页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0211MainPage",method = RequestMethod.GET)
	public String c0211MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0211Main";
	};
	
	/**
	 * 医疗构成分析总表c0211查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0211" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0211(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0211 = c02Service.queryAnalysisC0211(getPage(mapVo));

			return JSONObject.parseObject(analysisC0211);
	}
	
	
	/**
	 * 医疗构成分析总表c0212页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0212MainPage",method = RequestMethod.GET)
	public String c0212MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0212Main";
	};
	
	/**
	 * 医疗构成分析总表c0211查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0212" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0212(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0212 = c02Service.queryAnalysisC0212(getPage(mapVo));

			return JSONObject.parseObject(analysisC0212);
	}
	
	/**
	 * 医疗构成分析总表c0213页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0213MainPage",method = RequestMethod.GET)
	public String c0213MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0213Main";
	};
	
	/**
	 * 医疗构成分析总表c0211查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0213" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0213(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0213 = c02Service.queryAnalysisC0213(getPage(mapVo));

			return JSONObject.parseObject(analysisC0213);
	}
	
	/**
	 * 医疗构成分析总表c0214页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0214MainPage",method = RequestMethod.GET)
	public String c0214MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0214Main";
	};
	
	/**
	 * 医疗构成分析总表c0211查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0214" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0214(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0214 = c02Service.queryAnalysisC0214(getPage(mapVo));

			return JSONObject.parseObject(analysisC0214);
	}
	
	/**
	 * 医疗构成分析总表c0215页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0215MainPage",method = RequestMethod.GET)
	public String c0215MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0215Main";
	};
	
	/**
	 * 医疗构成分析总表c0215查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0215" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0215(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0215 = c02Service.queryAnalysisC0215(getPage(mapVo));

			return JSONObject.parseObject(analysisC0215);
	}
	
	/**
	 * 医疗构成分析总表c0216页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0216MainPage",method = RequestMethod.GET)
	public String c0216MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0216Main";
	};
	
	/**
	 * 医疗构成分析总表c0216查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0216" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0216(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0216 = c02Service.queryAnalysisC0216(getPage(mapVo));

			return JSONObject.parseObject(analysisC0216);
	}
	
	/**
	 * 医疗构成分析总表c0217页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0217MainPage",method = RequestMethod.GET)
	public String c0217MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0217Main";
	};
	
	/**
	 * 医疗构成分析总表c0217查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0217" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0217(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0217 = c02Service.queryAnalysisC0217(getPage(mapVo));

			return JSONObject.parseObject(analysisC0217);
	}
	
	/**
	 * 医疗构成分析总表c0218页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0218MainPage",method = RequestMethod.GET)
	public String c0218MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0218Main";
	};
	
	/**
	 * 医疗构成分析总表c0211查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0218" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0218(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0218 = c02Service.queryAnalysisC0218(getPage(mapVo));

			return JSONObject.parseObject(analysisC0218);
	}
	
	/**
	 * 医疗构成分析总表c0219页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0219MainPage",method = RequestMethod.GET)
	public String c0219MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0219Main";
	};
	
	/**
	 * 医疗构成分析总表c0219查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0219" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0219(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0219 = c02Service.queryAnalysisC0219(getPage(mapVo));

			return JSONObject.parseObject(analysisC0219);
	}
	
	/**
	 * 医疗构成分析总表c0220页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0220MainPage",method = RequestMethod.GET)
	public String c0220MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0220Main";
	};
	
	/**
	 * 医疗构成分析总表c0220查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0220" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0220(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0220 = c02Service.queryAnalysisC0220(getPage(mapVo));

			return JSONObject.parseObject(analysisC0220);
	}
	
	/**
	 * 医疗构成分析总表c0221页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0221MainPage",method = RequestMethod.GET)
	public String c0221MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0221Main";
	};
	
	/**
	 * 医疗构成分析总表c0221查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0221" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0221(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0221 = c02Service.queryAnalysisC0221(getPage(mapVo));

			return JSONObject.parseObject(analysisC0221);
	}
	
	/**
	 * 医疗构成分析总表c0222页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0222MainPage",method = RequestMethod.GET)
	public String c0222MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0222Main";
	};
	
	/**
	 * 医疗构成分析总表c0221查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0222" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0222(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0222 = c02Service.queryAnalysisC0222(getPage(mapVo));

			return JSONObject.parseObject(analysisC0222);
	}
	
	/**
	 * 医疗构成分析总表c0223页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0223MainPage",method = RequestMethod.GET)
	public String c0223MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0223Main";
	};
	
	/**
	 * 医疗构成分析总表c0223查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0223" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0223(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0223 = c02Service.queryAnalysisC0223(getPage(mapVo));

			return JSONObject.parseObject(analysisC0223);
	}
	
	/**
	 * 医疗构成分析总表c0224页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0224MainPage",method = RequestMethod.GET)
	public String c0224MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0224Main";
	};
	
	/**
	 * 医疗构成分析总表c0224查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0224" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0224(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0224 = c02Service.queryAnalysisC0224(getPage(mapVo));

			return JSONObject.parseObject(analysisC0224);
	}
	
	/**
	 * 医疗构成分析总表c0225页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0225MainPage",method = RequestMethod.GET)
	public String c0225MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0225Main";
	};
	
	/**
	 * 医疗构成分析总表c0225查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0225" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0225(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0225 = c02Service.queryAnalysisC0225(getPage(mapVo));

			return JSONObject.parseObject(analysisC0225);
	}
	
	/**
	 * 医疗构成分析总表c0226页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0226MainPage",method = RequestMethod.GET)
	public String c0226MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0226Main";
	};
	
	/**
	 * 医疗构成分析总表c0226查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0226" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0226(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0226 = c02Service.queryAnalysisC0226(getPage(mapVo));

			return JSONObject.parseObject(analysisC0226);
	}
	
	/**
	 * 医疗构成分析总表c0227页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0227MainPage",method = RequestMethod.GET)
	public String c0227MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0227Main";
	};
	
	/**
	 * 医疗构成分析总表c0227查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0227" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0227(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0227 = c02Service.queryAnalysisC0227(getPage(mapVo));

			return JSONObject.parseObject(analysisC0227);
	}
	
	/**
	 * 医疗构成分析总表c0228页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0228MainPage",method = RequestMethod.GET)
	public String c0228MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0228Main";
	};
	
	/**
	 * 医疗构成分析总表c0228查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0228" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0228(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0228 = c02Service.queryAnalysisC0228(getPage(mapVo));

			return JSONObject.parseObject(analysisC0228);
	}
	
	/**
	 * 医疗构成分析总表c0229页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/c0229MainPage",method = RequestMethod.GET)
	public String c0229MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c02/c0229Main";
	};
	
	/**
	 * 医疗构成分析总表c0225查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/c02/queryAnalysisC0229" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0229(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String analysisC0229 = c02Service.queryAnalysisC0229(getPage(mapVo));

			return JSONObject.parseObject(analysisC0229);
	}
}
