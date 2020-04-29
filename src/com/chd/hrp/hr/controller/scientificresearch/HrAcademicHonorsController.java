package com.chd.hrp.hr.controller.scientificresearch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.chd.hrp.hr.entity.scientificresearch.HrAcadeHonorDetail;
import com.chd.hrp.hr.entity.scientificresearch.HrAcademicHonors;
import com.chd.hrp.hr.service.scientificresearch.HrAcademicHonorsService;

/**
 * 个人学术荣誉申请
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/scientificresearch")
public class HrAcademicHonorsController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAcademicHonorsController.class);

	// 引入Service服务
	@Resource(name = "hrAcademicHonorsService")
	private final HrAcademicHonorsService hrAcademicHonorsService = null;


	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAcademicHonorsMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/scientificresearch/academichonors/academicHonorsMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAcademicHonorsPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {
		
		return "hrp/hr/scientificresearch/academichonors/academicHonorsAdd";

	}

	/**
	 * 添加图片信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPicture", method = RequestMethod.POST)
	@ResponseBody
	public String addPicture(@RequestParam Map<String, Object> mapVo,
			@RequestParam(required = false, value = "file") MultipartFile file, Model mode, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			// 处理头像上传
			if (file != null) {

				String group_id = SessionManager.getGroupId();
				String hos_id = SessionManager.getHosId();
				String copy_code = SessionManager.getCopyCode();
				String url = null;
				String url1 = null;
				String fileExt = null;// 文件类型
				String fileName = file.getOriginalFilename();// 文件原名称
				fileExt = fileName.indexOf(".") != -1
						? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;// 文件类型
				if (fileExt != null) {
					if ("GIF".equals(fileExt.toUpperCase()) || "PNG".equals(fileExt.toUpperCase())
							|| "JPG".equals(fileExt.toUpperCase())) {
						String basePath = "upLoad/hr/scientificresearch/images/" + group_id + "/" + hos_id + "/"
								+ copy_code + "/";
						// 文件保存目录url
						String saveUrl = request.getContextPath() + "/" + basePath;

						url = saveUrl + fileName;
						url1 = fileName;
						FtpUtil.uploadFileExt(file, "", basePath, request, response);
						return "{\"url\":\"" + url + "\"}";
					} else {
						return "{\"error\":\"图片格式错误，请选择以.png .jpg .gif 为扩展名的文件\"}";
					}
				} else {
					return "{\"error\":\"图片格式错误，请选择以.png .jpg .gif 为扩展名的文件\"}";
				}
			}
			return "{\"error\":\"图片格式错误，请选择以.png .jpg .gif 为扩展名的文件\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAcademicHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAcademicHonors(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			mapVo.put("state", 0);

			/**
			 * 添加主表
			 */
			String hosEmpKindJson = hrAcademicHonorsService.addAcademicHonors(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
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
	@RequestMapping(value = "/updateAcademicHonorsPage", method = RequestMethod.GET)
	public String updateHrDeptscientificresearchPage(@RequestParam Map<String, Object> mapVo, Model mode)
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
		HrAcademicHonors hrdeptscientificresearch = new HrAcademicHonors();
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
		hrdeptscientificresearch = hrAcademicHonorsService.queryByCodeAcademicHonors(mapVo);
		mode.addAttribute("apply_no", hrdeptscientificresearch.getApply_no());
		mode.addAttribute("apply_date", a.format(hrdeptscientificresearch.getApply_date()));
		mode.addAttribute("emp_name", hrdeptscientificresearch.getEmp_name());
		mode.addAttribute("emp_id", hrdeptscientificresearch.getEmp_id());
		mode.addAttribute("honor_code", hrdeptscientificresearch.getHonor_code());
		mode.addAttribute("get_date", a.format(hrdeptscientificresearch.getGet_date()));
		mode.addAttribute("state", hrdeptscientificresearch.getState());
		mode.addAttribute("note", hrdeptscientificresearch.getNote());
		return "hrp/hr/scientificresearch/academichonors/academicHonorsUpdate";

	}

	/**
	 * 查询明细表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrAcadeHonorDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrAcadeHonorDetail(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String hrAcadeHonorDetail = hrAcademicHonorsService.queryHrAcadeHonorDetail(getPage(mapVo));

		return JSONObject.parseObject(hrAcadeHonorDetail);

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAcademicHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAcademicHonors(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			mapVo.put("state", 0);
			String hosEmpKindJson = hrAcademicHonorsService.updateAcademicHonors(mapVo);
			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
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
	@RequestMapping(value = "/deleteAcademicHonors", method = RequestMethod.POST)
	@ResponseBody
	public String deleteAcademicHonors(@RequestParam String paramVo, Model mode) throws Exception {
		
		List<HrAcademicHonors> listVo = JSONArray.parseArray(paramVo, HrAcademicHonors.class);
		try {
			return hrAcademicHonorsService.deleteAcademicHonors(listVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
		
	}
	
	/**
	 * @Description 删除明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAcademicHonorsDetail", method = RequestMethod.POST)
	@ResponseBody
	public String deleteAcademicHonorsDetail(@RequestParam String paramVo, Model mode) throws Exception {
		List<HrAcadeHonorDetail> listVo = JSONArray.parseArray(paramVo, HrAcadeHonorDetail.class);
		List<HrAcadeHonorDetail> entityList = new ArrayList<HrAcadeHonorDetail>();
		boolean falg = true;
		
		try {
			for (HrAcadeHonorDetail hrAcadeHonorDetail : listVo) {
				//处理空行
				if(hrAcadeHonorDetail.getEmp_id() ==null){
					continue;
				}
				
				hrAcadeHonorDetail.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hrAcadeHonorDetail.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				
				entityList.add(hrAcadeHonorDetail);
				//移出文件
				String pathall = hrAcadeHonorDetail.getFilepath();
				if(pathall!=null && pathall !=""){
					String path = pathall.substring(pathall.indexOf("/", pathall.indexOf("/") + 1),
							pathall.lastIndexOf("/") + 1);
					String file_name = pathall.substring(pathall.lastIndexOf("/") + 1);
					FtpUtil.deleteFile(path, file_name);
				}
			}
			
			return hrAcademicHonorsService.deleteAcademicHonorsDetail(entityList);
			
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
	@RequestMapping(value = "/queryAcademicHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAcademicHonors(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrAcademicHonorsService.queryAcademicHonors(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}

	/**
	 * @Description 查询学术荣誉
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHonor", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHonor(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrAcademicHonorsService.queryHonor(mapVo);
		return json;

	}
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmAcademicHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmAcademicHonors(@RequestParam String paramVo, Model mode)
			throws Exception {
		String msg = "";
		try {
			List<HrAcademicHonors> listVo = JSONArray.parseArray(paramVo, HrAcademicHonors.class);

			for (HrAcademicHonors hrAcademicHonors : listVo) {
				if (hrAcademicHonors.getState() == 0) {
					hrAcademicHonors.setState(1);
					hrAcademicHonors.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrAcademicHonors.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					msg = hrAcademicHonorsService.confirmAcademicHonors(hrAcademicHonors);
				} else {
					msg = "{\"error\":\"提交失败！请勿重复提交！\",\"state\":\"false\"}";
				}
			}

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
	@RequestMapping(value = "/reConfirmAcademicHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmAcademicHonors(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrAcademicHonors> listVo = JSONArray.parseArray(paramVo, HrAcademicHonors.class);

			for (HrAcademicHonors hrAcademicHonors : listVo) {
				if (hrAcademicHonors.getState() == 1) {
					hrAcademicHonors.setState(0);
					hrAcademicHonors.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrAcademicHonors.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					msg = hrAcademicHonorsService.reConfirmAcademicHonors(hrAcademicHonors);
				} else {
					msg = "{\"error\":\"撤回失败！状态不是提交状态！\",\"state\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditAcademicHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditAcademicHonors(@RequestParam String paramVo, Model mode)
			throws Exception {
		String msg = "";
		try {
			List<HrAcademicHonors> listVo = JSONArray.parseArray(paramVo, HrAcademicHonors.class);

			for (HrAcademicHonors hrAcademicHonors : listVo) {
				if (hrAcademicHonors.getState() == 1) {
					hrAcademicHonors.setState(2);
					hrAcademicHonors.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrAcademicHonors.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					hrAcademicHonors.setAudit_date(new Date());
					hrAcademicHonors.setAudit_id(Long.parseLong(SessionManager.getUserId()));
					msg = hrAcademicHonorsService.auditAcademicHonors(hrAcademicHonors);
				} else {
					msg = "{\"error\":\"审核失败！请选择提交状态的单据！\",\"state\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * @Description 销审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/unAuditAcademicHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditAcademicHonors(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrAcademicHonors> listVo = JSONArray.parseArray(paramVo, HrAcademicHonors.class);

			for (HrAcademicHonors hrAcademicHonors : listVo) {
				if (hrAcademicHonors.getState() == 2) {
					hrAcademicHonors.setState(1);
					hrAcademicHonors.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrAcademicHonors.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					msg = hrAcademicHonorsService.unAuditAcademicHonors(hrAcademicHonors);
				} else {
					msg = "{\"error\":\"销审失败！状态不是审核状态！\",\"state\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
}
