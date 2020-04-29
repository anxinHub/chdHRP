/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.budg.controller.budgsysset;
import java.util.*;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.acc.serviceImpl.AccParaServiceImpl;
import com.chd.hrp.budg.service.budgsysset.BudgContSetService;

/**
* @Title. @Description.
* 系统参数
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class BudgContSetController extends BaseController{
	private static Logger logger = Logger.getLogger(BudgContSetController.class);
	
	@Resource(name = "budgContSetService")
	private final BudgContSetService budgContSetService = null;
    
	/**
	*节点页面跳转
	*
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgcontset/budgContSetMainPage", method = RequestMethod.GET)
	public String budgParaMainPage(Model mode) throws Exception {

		return "hrp/budg/budgsysset/budgcontset/budgContSetMain";
	}
	
	/**
	*节点控制数据保存
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgcontset/saveBudgContSet", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> saveBudgContSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        
		String accParaJson = budgContSetService.saveBudgContSet(mapVo);

		return JSONObject.parseObject(accParaJson);
	}
	/**
	*节点控制数据
	*查询
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgcontset/queryBudgContSet", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryBudgContSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        
		String accPara = budgContSetService.queryBudgContSet(mapVo);

		return JSONObject.parseObject(accPara);
		
	}
	/**
	*节点控制数据
	*删除
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgcontset/deleteBudgContSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgContSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
	        mapVo.put("hos_id", SessionManager.getHosId());
			
			String budgContSetJson = budgContSetService.deleteBudgContSet(mapVo);
			
			return JSONObject.parseObject(budgContSetJson);
	}
	
	/**
	 * 控制节点下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgsysset/budgcontset/queryBudgContNote", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgContNote(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String resolve = budgContSetService.queryBudgContNote(mapVo);
		
		return resolve;
	}
	
	/**
	 * 根据控制节点 查询反向节点code  name
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgsysset/budgcontset/queryReNodeByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryReNodeByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String resolve = budgContSetService.queryReNodeByCode(mapVo);
		
		return JSONObject.parseObject(resolve);
	}
}

