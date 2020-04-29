package com.chd.hrp.hr.controller.msg;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.entity.msg.SysMsgProducer;
import com.chd.hrp.hr.entity.record.HosEmpKind;
import com.chd.hrp.hr.service.msg.SysMsgProducerService;
import com.chd.hrp.hr.service.record.HosEmpKindService;
import com.github.pagehelper.StringUtil;

/**
 * 人员分类
 * 
 * @author baiyj
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/msg")
public class MsgProducerController extends BaseController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(MsgProducerController.class);

	// 引入Service服务
	@Resource(name = "msgProducerService")
	private SysMsgProducerService msgProducerService = null;
	@Resource(name = "hosEmpKindService")
	private HosEmpKindService hosEmpKindService=null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */ 
	@RequestMapping(value = "/producerMainPage", method = RequestMethod.GET)
	public String hosEmpKindMainPage(Model mode) throws Exception {
		System.out.println("ddddddddddd");
		return "hrp/hr/msg/producer/main";

	}
	@RequestMapping(value="queryMsgProducer",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMsgProducer(ModelMap map){
		Map<String,Object> mapVo=new HashMap<String,Object>();
		String result=msgProducerService.getMsgProducers(mapVo);
		return JSONObject.parseObject(result);
	}
	

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/addProducerPage", method = RequestMethod.GET)
	public String hosEmpKindAddPage(Model mode) throws Exception {

		return "hrp/hr/msg/producer/add";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addProducer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addProducer(SysMsgProducer producer, Model mode) throws Exception {


		producer.setGroupId(Integer.parseInt(SessionManager.getGroupId()));
		producer.setHosId(Integer.parseInt(SessionManager.getHosId()));
		producer.setCreator(new BigDecimal(SessionManager.getUserId()));
		producer.setCreatime(new Date());
		producer.setCopyCode(SessionManager.getCopyCode());
		String hosEmpKindJson = msgProducerService.addProducer(producer);

		return JSONObject.parseObject(hosEmpKindJson);

	}

	/**
	 * @Description 更新跳转页面
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateProducerPage", method = RequestMethod.GET)
	public String hosEmpKindUpdatePage(String id, Model mode) throws Exception {
		SysMsgProducer producer =null;
		if(!StringUtil.isEmpty(id))
			producer = msgProducerService.queryProducerByPrimaryKey(id);
		if (producer != null) {
			
			mode.addAttribute("id", producer.getId());
			mode.addAttribute("name", producer.getName());
			mode.addAttribute("producer", producer.getProducer());
			mode.addAttribute("cron", producer.getCron());
			mode.addAttribute("reuser", producer.getReuser());
			mode.addAttribute("rerole", producer.getRerole());
			mode.addAttribute("beforday", producer.getBeforday());
			mode.addAttribute("status", producer.getStatus());
			mode.addAttribute("note", producer.getNote());
		}
		return "hrp/hr/msg/producer/update";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateProducer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHosEmpKind(SysMsgProducer producer, Model mode) throws Exception {

		String hosEmpKindJson = msgProducerService.updateProducer(producer);
		
		return JSONObject.parseObject(hosEmpKindJson);
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHosEmpKink", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHosEmpKind(@RequestParam String paramVo, Model mode) throws Exception {

        String str = "";
		boolean falg = true;
		List<HosEmpKind> listVo = JSONArray.parseArray(paramVo, HosEmpKind.class);
		try {
			for (HosEmpKind hosEmpKind : listVo) {
			
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
			}

			if (!falg) {
		return ("{\"error\":\"删除失败，选择的人员分类被以下业务使用：【" + str.substring(0, str.length() - 1)
		+ "】。\",\"state\":\"false\"}");
	}
			return hosEmpKindService.deleteBatchHosEmpKind(listVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHosEmpKink", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosEmpKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String hosEmpKind = hosEmpKindService.query(getPage(mapVo));

		return JSONObject.parseObject(hosEmpKind);

	}
	/**
	 * @Description 查询数据(左侧菜单) 人员分类
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHosEmpKinkTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHosEmpKinkTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hosEmpKindService.queryHosEmpKinkTree(mapVo);

	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importDat", method = RequestMethod.POST)
	@ResponseBody
	public String importDat(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hosEmpKindService.importDate(mapVo);
		return reJson;
	}
}
