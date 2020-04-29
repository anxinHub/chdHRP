
package com.chd.hrp.eqc.controller.query;

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
import com.chd.hrp.eqc.service.query.AssEqUseConsItemService;

/**
 * Title: AssEqUseConsItemController
 * @author ChengXiaoDong
 * @version 2020年4月17日 下午12:54:30
*/

/**
* 设备使用消耗资源查询-服务细项 ASS_EQUseConsumableItem Controller实现类
*/
@Controller
@RequestMapping(value = "/hrp/eqc/xyquery")
public class AssEqUseConsItemController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqUseConsItemController.class);
	
	//引入Service服务
	@Resource(name = "assEqUseConsItemService")
	private final AssEqUseConsItemService assEqUseConsItemService = null;
   
	/**
	 * @Description 
	   * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/assEqUseConsItemMainPage", method = RequestMethod.GET)
	public String assEqUseConsumableItemMainPage(Model mode) throws Exception {
		return "hrp/eqc/query/assEqUseConsItemMain";
	}
	
	/**
	 * 主页面查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAssEqUseConsItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqUseConsItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqUseConsItem = assEqUseConsItemService.query(getPage(mapVo));
		return JSONObject.parseObject(assEqUseConsItem);
	}

    
}

