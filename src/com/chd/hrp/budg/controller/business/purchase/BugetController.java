package com.chd.hrp.budg.controller.business.purchase;

import java.util.ArrayList;
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
import com.chd.hrp.budg.service.business.purchase.BugetService;

/**
 * 资产采购预算
 * @author Administrator
 *
 */
@Controller
public class BugetController extends BaseController {

	private static Logger logger = Logger.getLogger(BugetController.class);
	
	//引入Serviece服务
	@Resource(name="bugetService")
	private final BugetService bugetService = null;
	
	/**
	 * 主页面跳转
	 */
	@RequestMapping(value = "/hrp/budg/business/purchase/assbuget/budgMainPage", method = RequestMethod.GET)
	public String budgMainPage(Model mode) throws Exception {

		return "hrp/budg/business/purchase/buget/bugetMain";

	}
	/**
	 * @Description 
	 * 查询数据 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/purchase//assbuget/queryBugetMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBugetMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgAssCard = bugetService.query(getPage(mapVo));

		return JSONObject.parseObject(budgAssCard);
	}
	
	/**
	 * 生成
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/purchase/assbuget/generateBuget", method = RequestMethod.POST)
	@ResponseBody
	public String generateBuget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		return bugetService.copyBuget(mapVo);
	}
	
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/purchase/assbuget/deleteBudgWorkCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgWorkCheck(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3]);
				mapVo.put("source_id", ids[4]);
				mapVo.put("ass_type_id", ids[5]);
				
				listVo.add(mapVo);
	      
	    }
	    
		String budgAssCardJson = bugetService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgAssCardJson);
			
	}
	
	
}
