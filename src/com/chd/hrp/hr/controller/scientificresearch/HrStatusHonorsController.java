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
import com.chd.hrp.hr.entity.scientificresearch.HrEmpAcadeStatusDetail;
import com.chd.hrp.hr.entity.scientificresearch.HrStatusHonors;
import com.chd.hrp.hr.service.scientificresearch.HrStatusHonorsService;

/**
 * 个人学术地位申请
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/scientificresearch")
public class HrStatusHonorsController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrStatusHonorsController.class);

	// 引入Service服务
	@Resource(name = "hrStatusHonorsService")
	private final HrStatusHonorsService hrStatusHonorsService = null;


	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrStatusHonorsMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/scientificresearch/statushonors/statusHonorsMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addStatusHonorsPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {
		
		return "hrp/hr/scientificresearch/statushonors/statusHonorsAdd";

	}

	/**
	 * 添加图片信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addStatusPicture", method = RequestMethod.POST)
	@ResponseBody
	public String addStatusPicture(@RequestParam Map<String, Object> mapVo,
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
					if ("GIF".equals(fileExt.toUpperCase()) || "PNG".equals(fileExt.toUpperCase())
							|| "JPG".equals(fileExt.toUpperCase())) {
						String basePath = "upload/hr/scientificresearch/images/" + group_id + "/" + hos_id + "/"
								+ copy_code + "/";
						// 文件保存目录url
						String saveUrl = request.getContextPath() + "/" + basePath;

						url = saveUrl + fileName;

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
	@RequestMapping(value = "/addStatusHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addStatusHonors(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hosEmpKindJson = hrStatusHonorsService.addStatusHonors(mapVo);

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
	@RequestMapping(value = "/updateStatusHonorsPage", method = RequestMethod.GET)
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
		HrStatusHonors hrdeptscientificresearch = new HrStatusHonors();
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
		hrdeptscientificresearch = hrStatusHonorsService.queryByCodeStatusHonors(mapVo);
		mode.addAttribute("apply_no", hrdeptscientificresearch.getApply_no());
		mode.addAttribute("apply_date", a.format(hrdeptscientificresearch.getApply_date()));
		mode.addAttribute("emp_name", hrdeptscientificresearch.getEmp_name());
		mode.addAttribute("emp_id", hrdeptscientificresearch.getEmp_id());
		mode.addAttribute("status_code", hrdeptscientificresearch.getStatus_code());
		mode.addAttribute("beg_date", a.format(hrdeptscientificresearch.getBeg_date()));
		mode.addAttribute("end_date", a.format(hrdeptscientificresearch.getEnd_date()));
		mode.addAttribute("state", hrdeptscientificresearch.getState());
		mode.addAttribute("note", hrdeptscientificresearch.getNote());
		return "hrp/hr/scientificresearch/statushonors/statusHonorsUpdate";

	}

	/**
	 * 查询明细表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrEmpAcadeStatusDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrEmpAcadeStatusDetail(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String hrEmpAcadeStatusDetail = hrStatusHonorsService.queryHrEmpAcadeStatusDetail(getPage(mapVo));

		return JSONObject.parseObject(hrEmpAcadeStatusDetail);

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStatusHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateStatusHonors(@RequestParam Map<String, Object> mapVo, Model mode)
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
			String hosEmpKindJson = hrStatusHonorsService.updateStatusHonors(mapVo);
			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * 删除明细
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteStatusHonorsDetail", method = RequestMethod.POST)
	@ResponseBody
	public String deleteStatusHonorsDetail(@RequestParam String paramVo, Model mode) throws Exception {
		List<HrEmpAcadeStatusDetail> listVo = JSONArray.parseArray(paramVo, HrEmpAcadeStatusDetail.class);
		List<HrEmpAcadeStatusDetail> entityList = new ArrayList<HrEmpAcadeStatusDetail>();
		boolean falg = true;
		
		try {
			for (HrEmpAcadeStatusDetail hrEmpAcadeStatusDetail : listVo) {
				//处理空行
				if(hrEmpAcadeStatusDetail.getSeq_no() ==null){
					continue;
				}
				
				hrEmpAcadeStatusDetail.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hrEmpAcadeStatusDetail.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				
				entityList.add(hrEmpAcadeStatusDetail);
				
				//移出文件
				String pathall = hrEmpAcadeStatusDetail.getFilepath();
				if(pathall!=null && pathall !=""){
					String path = pathall.substring(pathall.indexOf("/", pathall.indexOf("/") + 1),
							pathall.lastIndexOf("/") + 1);
					String file_name = pathall.substring(pathall.lastIndexOf("/") + 1);
					FtpUtil.deleteFile(path, file_name);
				}
			}
			
			return hrStatusHonorsService.deleteAcademicHonorsDetail(entityList);
			
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
	@RequestMapping(value = "/deleteStatusHonors", method = RequestMethod.POST)
	@ResponseBody

	public String deleteStatusHonors(@RequestParam String paramVo, Model mode) throws Exception {
		
		List<HrStatusHonors> listVo = JSONArray.parseArray(paramVo, HrStatusHonors.class);
		try {
			return hrStatusHonorsService.deleteStatusHonors(listVo);
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
	@RequestMapping(value = "/queryStatusHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStatusHonors(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrStatusHonorsService.queryStatusHonors(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}

	/**
	 * @Description 查询学术荣誉
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryStatus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryStatus(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrStatusHonorsService.queryStatus(mapVo);
		return json;

	}
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmStatusHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmStatusHonors(@RequestParam String paramVo, Model mode)
			throws Exception {
		String msg = "";
		try {
			List<HrStatusHonors> listVo = JSONArray.parseArray(paramVo, HrStatusHonors.class);

			for (HrStatusHonors hrStatusHonors : listVo) {
				if (hrStatusHonors.getState() == 0) {
					hrStatusHonors.setState(1);
					hrStatusHonors.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrStatusHonors.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					msg = hrStatusHonorsService.confirmStatusHonors(hrStatusHonors);
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
	@RequestMapping(value = "/reConfirmStatusHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmStatusHonors(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrStatusHonors> listVo = JSONArray.parseArray(paramVo, HrStatusHonors.class);

			for (HrStatusHonors hrStatusHonors : listVo) {
				if (hrStatusHonors.getState() == 1) {
					hrStatusHonors.setState(0);
					hrStatusHonors.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrStatusHonors.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					msg = hrStatusHonorsService.reConfirmStatusHonors(hrStatusHonors);
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
	@RequestMapping(value = "/auditStatusHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditStatusHonors(@RequestParam String paramVo, Model mode)
			throws Exception {
		String msg = "";
		try {
			List<HrStatusHonors> listVo = JSONArray.parseArray(paramVo, HrStatusHonors.class);

			for (HrStatusHonors hrStatusHonors : listVo) {
				if (hrStatusHonors.getState() == 1) {
					hrStatusHonors.setState(2);
					hrStatusHonors.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrStatusHonors.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					hrStatusHonors.setAudit_date(new Date());
					hrStatusHonors.setAudit_id(Integer.parseInt(SessionManager.getUserId()));
					msg = hrStatusHonorsService.auditStatusHonors(hrStatusHonors);
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
	@RequestMapping(value = "/unAuditStatusHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditStatusHonors(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrStatusHonors> listVo = JSONArray.parseArray(paramVo, HrStatusHonors.class);

			for (HrStatusHonors hrStatusHonors : listVo) {
				if (hrStatusHonors.getState() == 2) {
					hrStatusHonors.setState(1);
					hrStatusHonors.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrStatusHonors.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					msg = hrStatusHonorsService.unAuditStatusHonors(hrStatusHonors);
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
