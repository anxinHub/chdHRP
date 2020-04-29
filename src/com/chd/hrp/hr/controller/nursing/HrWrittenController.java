package com.chd.hrp.hr.controller.nursing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
import com.chd.hrp.hr.entity.nursing.HrWritten;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.nursing.HrWrittenService;

/**
 * 笔试成绩
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursing")
public class HrWrittenController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrWrittenController.class);

	// 引入Service服务
	@Resource(name = "hrWrittenService")
	private final HrWrittenService hrWrittenService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrWrittenMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursing/written/writtenMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addWrittenPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/nursing/written/writtenAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addWritten", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addWritten(@RequestParam Map<String, Object> paramVo, Model mode) throws Exception {
		
		String hrJson;
		try {
			hrJson = hrWrittenService.addWritten(paramVo);
		} catch (Exception e) {
			hrJson = e.getMessage();
		}
		return JSONObject.parseObject(hrJson);

	}



	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteWritten", method = RequestMethod.POST)
	@ResponseBody

	public String deleteWritten(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
		 boolean falg = true;
		 List<HrWritten> listVo = JSONArray.parseArray(paramVo, HrWritten.class);
		 
		 List<HrWritten> entityList = new ArrayList<HrWritten>();//重新组装List,用于组装正确的删除数据,避免误删除操作
		 try {
			for (int x = 0 ; x < listVo.size(); x++) {
				
				HrWritten hrWritten = listVo.get(x);
				
				hrWritten.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrWritten.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				
		/*		if (StringUtils.isBlank(hrCardiopulmonary.getYear()) && hrCardiopulmonary.getEmp_id() == null || StringUtils.isBlank(hrCardiopulmonary.getEmp_id().toString())) {
					listVo.remove(hrWritten);
				}*/
				
				
				//判断状态
				if (hrWritten.getState() !=null && hrWritten.getState()!=0) {
					falg = false;
					continue;
				}
				
				//判断主键是否为空,避免误删数据
				if(hrWritten.getYear() == null || hrWritten.getEmp_id() ==null){
					continue;
				}
				if("".equals(hrWritten.getYear())|| "".equals(String.valueOf(hrWritten.getEmp_id()))){
					continue;
				}
				
				entityList.add(hrWritten);
			}
			
			if (!falg) {
				return ("{\"error\":\"删除失败,请选择新建状态申请删除\",\"state\":\"false\"}");
			}
			
			if(entityList.size() == 0){
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
			
			return hrWrittenService.deleteWritten(entityList);

		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
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
	@RequestMapping(value = "/queryWritten", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWritten(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("dept_code") !=null && StringUtils.isNotBlank(mapVo.get("dept_code").toString())) {
			String dept_id_no = mapVo.get("dept_code").toString();

			mapVo.put("dept_no", dept_id_no.split("@")[0]);
		}
		String stationTransef = hrWrittenService.queryWritten(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmWritten", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmWritten(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			List<HrWritten> listVo = JSONArray.parseArray(paramVo, HrWritten.class);
			String msg = hrWrittenService.confirmWritten(listVo);

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * @Description 取消提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reConfirmWritten", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmWritten(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			List<HrWritten> listVo = JSONArray.parseArray(paramVo, HrWritten.class);
			String msg = hrWrittenService.reConfirmWritten(listVo);

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	/*
	* 导入数据
	*/
	@RequestMapping(value = "/importWrite", method = RequestMethod.POST)
	@ResponseBody
	public String importWrite(@RequestParam Map<String, Object> mapVo, Model mode,
	HttpServletRequest request) throws Exception {
	String reJson = hrWrittenService.importWrite(mapVo);
	return reJson;
	}
}
