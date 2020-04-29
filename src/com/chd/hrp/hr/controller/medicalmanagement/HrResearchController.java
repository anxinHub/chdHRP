/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.controller.medicalmanagement;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.ftp.FtpUtil;
import com.chd.hrp.hr.entity.medicalmanagement.HrResearch;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.medicalmanagement.HrResearchService;

/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_RESEARCH 纠纷调查
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/privilegemanagement/medicalsafety")
public class HrResearchController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrResearchController.class);

	// 引入Service服务
	@Resource(name = "hrResearchService")
	private final HrResearchService hrResearchService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrResearchMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/medicalmanagement/research/researchMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addResearchPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {	

		return "hrp/hr/medicalmanagement/research/researchAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addResearch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addResearch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("dept_code") != null) {
			String dept_id_no = mapVo.get("dept_code").toString();

			mapVo.put("dept_no", dept_id_no.split("@")[0]);
			mapVo.put("dept_id", dept_id_no.split("@")[1]);
		}
		mapVo.put("is_commit", 0);
		try {
			String hosEmpKindJson = hrResearchService.addResearch(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	

	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateResearchPage", method = RequestMethod.GET)
	public String updateHrDepttechnologicalmanagementPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrResearch hrResearch = new HrResearch();

		hrResearch = hrResearchService.queryByCodeResearch(mapVo);
		mode.addAttribute("group_id", hrResearch.getGroup_id());
		mode.addAttribute("hos_id", hrResearch.getHos_id());
		mode.addAttribute("occ_date", hrResearch.getOcc_date());
		mode.addAttribute("plaint_date", hrResearch.getPlaint_date());
		mode.addAttribute("in_hos_no", hrResearch.getIn_hos_no());
		mode.addAttribute("prob_nature", hrResearch.getProb_nature());
		mode.addAttribute("prob_type", hrResearch.getProb_type());
		mode.addAttribute("case_discuss", hrResearch.getCase_discuss());
		mode.addAttribute("case_no", hrResearch.getCase_no());
		mode.addAttribute("join_doc", hrResearch.getJoin_doc());
		mode.addAttribute("opinion", hrResearch.getOpinion());
		mode.addAttribute("prob_reason", hrResearch.getProb_reason());
		mode.addAttribute("is_commit", hrResearch.getIs_commit());
		return "hrp/hr/medicalmanagement/research/researchUpdate";

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateResearch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateResearch(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("is_commit", 0);
			String hosEmpKindJson = hrResearchService.updateResearch(mapVo);

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
	@RequestMapping(value = "/deleteResearch", method = RequestMethod.POST)
	@ResponseBody

	public String deleteResearch(@RequestParam String paramVo, Model mode) throws Exception {
		List<HrResearch> listVo = JSONArray.parseArray(paramVo, HrResearch.class);
		try {
			return	 hrResearchService.deleteResearch(listVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
    


	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteResearchDetail", method = RequestMethod.POST)
	@ResponseBody

	public String deleteResearchDetail(@RequestParam String paramVo, Model mode) throws Exception {
		List<HrResearch> listVo = JSONArray.parseArray(paramVo, HrResearch.class);
		try {
			return	 hrResearchService.deleteResearchDetail(listVo);
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
	@RequestMapping(value = "/queryResearch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryResearch(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrResearchService.queryResearch(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryResearchDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryResearchDetail(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String researchDetail = hrResearchService.queryResearchDetail(getPage(mapVo));

		return JSONObject.parseObject(researchDetail);

	}
	
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmHrTechRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmHrTechRec(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			List<HrResearch> listVo = JSONArray.parseArray(paramVo, HrResearch.class);
			String msg = hrResearchService.confirmHrTechRec(listVo);

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
	@RequestMapping(value = "/reConfirmHrHrTechRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmHrHrTechRec(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			List<HrResearch> listVo = JSONArray.parseArray(paramVo, HrResearch.class);
			String msg = hrResearchService.reConfirmHrHrTechRec(listVo);
			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	//审核
	@RequestMapping(value = "/auditHrTechRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditHrTechRec(@RequestParam String paramVo, Model mode)
			throws Exception {
		String msg = "";
		try {
			List<HrResearch> listVo = JSONArray.parseArray(paramVo, HrResearch.class);

			for (HrResearch hrNursingPromotion : listVo) {
				hrNursingPromotion.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hrNursingPromotion.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				HrResearch Research = hrResearchService.queryByCode(hrNursingPromotion);
				if (Research != null) {
					if (Research.getIs_commit() == 1) {
						hrNursingPromotion.setIs_commit(2);
						msg = hrResearchService.auditHrTechRec(hrNursingPromotion);
					} else {
						msg = "{\"error\":\"审批失败！请勿重复审批！\",\"state\":\"false\"}";
					}
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	//销审
	@RequestMapping(value = "/unauditHrHrTechRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unauditHrHrTechRec(@RequestParam String paramVo, Model mode)
			throws Exception {

		String msg = "";
		try {
			List<HrResearch> listVo = JSONArray.parseArray(paramVo, HrResearch.class);

			for (HrResearch hrNursingPromotion : listVo) {
				if (hrNursingPromotion.getIs_commit() == 2) {
					hrNursingPromotion.setIs_commit(1);
					hrNursingPromotion.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					hrNursingPromotion.setHos_id(Double.parseDouble(SessionManager.getHosId()));
					msg = hrResearchService.unauditHrHrTechRec(hrNursingPromotion);
				} else {
					msg = "{\"error\":\"销审失败！\",\"state\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	
	
//@RequestParam Map<String, Object> mapVo, Model mode
	/**
	 * @Description 添加页面提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmHrResearchAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmHrResearchAdd(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String msg = "";
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		try {
			HrResearch research = JSONArray.parseObject(JSONObject.toJSONString(mapVo).toString(), HrResearch.class);
			msg = hrResearchService.confirmHrResearchAdd(research);

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * @Description 添加页面取消提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reConfirmHResearchAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmHResearchAdd(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String msg = "";
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		try {
			HrResearch research = JSONArray.parseObject(JSONObject.toJSONString(mapVo).toString(), HrResearch.class);
			msg = hrResearchService.reConfirmHResearchAdd(research);
			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	//
	/**
	 * 查询住院号
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryinHosNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryinHosNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());

		String json = hrResearchService.queryinHosNo(mapVo);
		return json;

	}
	/**
	 * 查询投诉详细信息
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryinHosNoDetail", method = RequestMethod.POST)
	@ResponseBody
	public String queryinHosNoDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String hrdeptnursing = hrResearchService.queryinHosNoDetail(mapVo);

		return hrdeptnursing;

	}
	/**
	 * 添加图片信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAccessory", method = RequestMethod.POST)
	@ResponseBody
	public String addAccessory(@RequestParam Map<String, Object> mapVo,
			@RequestParam(required = false, value = "file") MultipartFile file, Model mode, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			// 处理头像上传
			if (file != null) {

				String group_id = SessionManager.getGroupId();
				String hos_id = SessionManager.getHosId();
				String copy_code = SessionManager.getCopyCode();
				String url = null;
				String fileExt = null;// 文件类型
				String fileName = file.getOriginalFilename();// 文件原名称
				fileExt = fileName.indexOf(".") != -1
						? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;// 文件类型
				if (fileExt != null) {

					String basePath = "upload/hr/scientificresearch/file/" + group_id + "/" + hos_id + "/" + copy_code
							+ "/";
					// 文件保存目录url
					String saveUrl = request.getContextPath() + "/" + basePath;

					url = saveUrl + fileName;

					FtpUtil.uploadFileExt(file, "", basePath, request, response);
					return "{\"url\":\"" + url + "\"}";
				}
			}
			return "{\"error\":\"上传失败\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

}

