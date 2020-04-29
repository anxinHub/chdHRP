package com.chd.hrp.hr.controller.sysstruc;

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

import com.alibaba.fastjson.JSONArray;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.entity.sysstruc.HrCastrans;
import com.chd.hrp.hr.service.sysstruc.HrCastransService;

/**
 * 
 * @ClassName: HrCastransController
 * @Description: 级联事务
 * @author zn
 * @date 2017年10月17日 下午2:47:23
 * 
 *
 */

@Controller
@RequestMapping(value = "/hrp/hr/sysstruc/hrcastrans")
public class HrCastransController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrCastransController.class);

	// 引入Service服务
	@Resource(name = "hrCastransService")
	private final HrCastransService hrCastransService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode 级联事务
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrCastransMainPage", method = RequestMethod.GET)
	public String hrCastransMainPage(Model mode) throws Exception {
		return "hrp/hr/sysstruc/hrcastrans/hrCastransMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrCastransAddPage", method = RequestMethod.GET)
	public String hrCastransAddPage(Model mode) throws Exception {
		return "hrp/hr/sysstruc/hrcastrans/hrCastransAdd";
	}

	/**
	 * @Description 更新页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrCastransUpdatePage", method = RequestMethod.GET)
	public String hrCastransUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		HrCastrans hrCastrans = hrCastransService.queryHrCastransByCode(mapVo);
		
		if(hrCastrans != null){
			mode.addAttribute(hrCastrans);
		}
		
		return "hrp/hr/sysstruc/hrcastrans/hrCastransUpdate";
	}
	
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrCastrans", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrCastrans(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrCastransService.queryHrCastrans(mapVo);
	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrCastrans", method = RequestMethod.POST)
	@ResponseBody
	public String addHrCastrans(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrCastransService.addHrCastrans(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrCastrans", method = RequestMethod.POST)
	@ResponseBody
	public String updateHrCastrans(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrCastransService.updateHrCastrans(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrCastrans", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrCastrans(@RequestParam String paramVo, Model mode)throws Exception {
		
		List<HrCastrans> listVo = JSONArray.parseArray(paramVo, HrCastrans.class);
		
		try {
			return hrCastransService.deleteBatchHrCastrans(listVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
}
