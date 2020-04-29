
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
import com.chd.hrp.eqc.service.query.AssEqUseConsItemDetailService;

/**
 * Title: AssEqUseConsItemDetailController
 * @author ChengXiaoDong
 * @version 2020年4月17日 下午12:54:30
*/

/**
* 设备使用消耗资源查询-服务项目 ASS_EQUseConsumableItem Controller实现类
*/
@Controller
@RequestMapping(value = "/hrp/eqc/xyquerydetail")
public class AssEqUseConsItemDetailController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqUseConsItemDetailController.class);
	
	//引入Service服务
	@Resource(name = "assEqUseConsItemDetailService")
	private final AssEqUseConsItemDetailService assEqUseConsItemDetailService = null;
   
	/**
	 * @Description 
	   * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/assEqUseConsItemDetailMainPage", method = RequestMethod.GET)
	public String assEqUseConsumableItemMainPage(Model mode) throws Exception {
		return "hrp/eqc/query/assEqUseConsItemDetailMain";
	}
	
	/**
	 * 主页面查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAssEqUseConsItemDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqUseConsItemDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqUseConsItemDetail = assEqUseConsItemDetailService.query(getPage(mapVo));
		return JSONObject.parseObject(assEqUseConsItemDetail);
	}

    
}

