package com.chd.hrp.hr.controller.medicalmanagement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.chd.base.util.DateUtil;
import com.chd.hrp.hr.entity.medicalmanagement.HrCertificate;
import com.chd.hrp.hr.service.medicalmanagement.HrCertificateService;

/**
 * 证书管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/medicalmanagement/certificate")
public class HrCertificateController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrCertificateController.class);

	// 引入Service服务
	@Resource(name = "hrCertificateService")
	private final HrCertificateService hrCertificateService = null;


	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrCertificateMainPage", method = RequestMethod.GET)
	public String hrCertificateMainPage(Model mode) throws Exception {
		return "hrp/hr/medicalmanagement/certificate/hrCertificateMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCertificate", method = RequestMethod.GET)
	public String addCertificate(Model mode) throws Exception {	
	

		return "hrp/hr/medicalmanagement/certificate/hrCertificateAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCertificate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCertificate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}


		String hrJson;
		try {
			hrJson =hrCertificateService.addCertificate(mapVo);
		} catch (Exception e) {
			hrJson = e.getMessage();
		}
		return JSONObject.parseObject(hrJson);
        
	}
	

	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCertificatePage", method = RequestMethod.GET)
	public String updateCertificatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		HrCertificate hrCertificate = new HrCertificate();

		hrCertificate = hrCertificateService.queryByCodeCertificate(mapVo);
		SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd");

//		mode.addAttribute("state",hrCertificate.getState());
		return "hrp/hr/medicalmanagement/certificate/hrCertificateUpdate";

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCertificate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCertificate(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			String hosEmpKindJson = hrCertificateService.updateCertificate(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCertificate", method = RequestMethod.POST)
	@ResponseBody

	public String deleteCertificate(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
		List<HrCertificate> listVo = JSONArray.parseArray(paramVo, HrCertificate.class);
		try {
		
			return	 hrCertificateService.deleteCertificate(listVo);

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
	@RequestMapping(value = "/queryCertificate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCertificate(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		mapVo.put("mDate", sdf.format(date));

		String stationTransef = hrCertificateService.queryCertificate(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importCertificate", method = RequestMethod.POST)
	@ResponseBody
	public String importCertificate(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrCertificateService.importCertificate(mapVo);
		return reJson;
	}

}
