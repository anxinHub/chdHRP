package com.chd.hrp.hr.controller.training;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chd.base.SessionManager;
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.service.training.HrTrainRecordService;

@Controller
@RequestMapping("/hrp/hr/training/record")
public class HrTrainRecordController {

	private static Logger logger = Logger.getLogger(HrTrainRecordController.class);

	@Resource(name = "hrTrainRecordService")
	private HrTrainRecordService hrTrainRecordService;

	@RequestMapping("/recordMainPage")
	public String toRecordMainPage() {
		return "hrp/hr/training/record/recordMainPage";
	}

	/**
	 * 保存培训课程安排
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveTrainRecordCourse")
	public Map<String, Object> saveTrainRecordCourse(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());

			String result = hrTrainRecordService.saveTrainRecordCourse(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping("/queryTrainRecordClass")
	public String queryTrainRecordClass(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());

			String result = hrTrainRecordService.queryTrainRecordClass(mapVo);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询培训对象
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryTrainRecordTarget")
	public Map<String, Object> queryTrainRecordTarget(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			String result = hrTrainRecordService.queryTrainRecordTarget(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询批量添加中的职工信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryTrainRecordTargetForAdd")
	public Map<String, Object> queryTrainRecordTargetForAdd(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			String result = hrTrainRecordService.queryTrainRecordTargetForAdd(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 删除培训对象
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteTrainRecordTarget")
	public Map<String, Object> deleteTrainRecordTarget(@RequestParam String mapVo, Model mode) {
		try {
			List<Map<String, Object>> listVo = JSON.parseObject(mapVo, new TypeReference<List<Map<String, Object>>>() {
			});

			String result = hrTrainRecordService.deleteTrainRecordTarget(listVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 保存培训对象
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveTrainRecordTarget")
	public Map<String, Object> saveTrainRecordTarget(@RequestParam String mapVo, Model mode) {
		try {
			List<Map<String, Object>> listVo = JSON.parseObject(mapVo, new TypeReference<List<Map<String, Object>>>() {
			});

			String result = hrTrainRecordService.saveTrainRecordTarget(listVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询培训通知
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryTrainRecordNotice")
	public Map<String, Object> queryTrainRecordNotice(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			String result = hrTrainRecordService.queryTrainRecordNotice(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 生成培训通知
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryTrainRecordCreateNotice")
	public Map<String, Object> queryTrainRecordCreateNotice(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			hrTrainRecordService.createTrainRecordNotice(mapVo);
			String result = hrTrainRecordService.queryTrainRecordNotice(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 删除培训通知
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteTrainRecordNotice")
	public Map<String, Object> deleteTrainRecordNotice(@RequestParam String mapVo, Model mode) {
		try {
			List<Map<String, Object>> listVo = JSON.parseObject(mapVo, new TypeReference<List<Map<String, Object>>>() {
			});

			String result = hrTrainRecordService.deleteTrainRecordNotice(listVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 保存培训通知
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveTrainRecordNotice")
	public Map<String, Object> saveTrainRecordNotice(@RequestParam String mapVo, Model mode) {
		try {
			List<Map<String, Object>> listVo = JSON.parseObject(mapVo, new TypeReference<List<Map<String, Object>>>() {
			});

			String result = hrTrainRecordService.saveTrainRecordNotice(listVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 生成签到记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryTrainRecordCreateSignIn")
	public Map<String, Object> queryTrainRecordCreateSignIn(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			hrTrainRecordService.createTrainRecordSignIn(mapVo);
			String result = hrTrainRecordService.queryTrainRecordSignIn(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询签到记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryTrainRecordSignIn")
	public Map<String, Object> queryTrainRecordSignIn(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			String result = hrTrainRecordService.queryTrainRecordSignIn(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 删除签到记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteTrainRecordSignIn")
	public Map<String, Object> deleteTrainRecordSignIn(@RequestParam String mapVo, Model mode) {
		try {
			List<Map<String, Object>> listVo = JSON.parseObject(mapVo, new TypeReference<List<Map<String, Object>>>() {
			});

			String result = hrTrainRecordService.deleteTrainRecordSignIn(listVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 保存签到记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveTrainRecordSignIn")
	public Map<String, Object> saveTrainRecordSignIn(@RequestParam String mapVo, Model mode) {
		try {
			List<Map<String, Object>> listVo = JSON.parseObject(mapVo, new TypeReference<List<Map<String, Object>>>() {
			});
			String result = hrTrainRecordService.saveTrainRecordSignIn(listVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询课件
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryTrainRecordCourseware")
	public Map<String, Object> queryTrainRecordCourseware(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			String result = hrTrainRecordService.queryTrainRecordCourseware(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 删除课件
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteTrainRecordCourseware")
	public Map<String, Object> deleteTrainRecordCourseware(@RequestParam String mapVo, Model mode) {
		try {
			List<Map<String, Object>> listVo = JSON.parseObject(mapVo, new TypeReference<List<Map<String, Object>>>() {
			});

			String result = hrTrainRecordService.deleteTrainRecordCourseware(listVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 保存课件
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveTrainRecordCourseware")
	public Map<String, Object> saveTrainRecordCourseware(@RequestParam String mapVo, Model mode) {
		try {
			List<Map<String, Object>> listVo = JSON.parseObject(mapVo, new TypeReference<List<Map<String, Object>>>() {
			});

			String result = hrTrainRecordService.saveTrainRecordCourseware(listVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询补课人员记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryTrainRecordCreateBKEmp")
	public Map<String, Object> queryTrainRecordCreateBKEmp(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			hrTrainRecordService.createTrainRecordBKEmp(mapVo);
			String result = hrTrainRecordService.queryTrainRecordBKEmp(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询补课人员记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveTrainRecordBK")
	public Map<String, Object> saveTrainRecordBK(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			String result = hrTrainRecordService.saveTrainRecordBK(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询补课
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryTrainRecordBK")
	public String queryTrainRecordBK(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			String result = hrTrainRecordService.queryTrainRecordBK(mapVo);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询补课人员记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryTrainRecordBKEmp")
	public Map<String, Object> queryTrainRecordBKEmp(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			String result = hrTrainRecordService.queryTrainRecordBKEmp(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 删除补课人员记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteTrainRecordBK")
	public Map<String, Object> deleteTrainRecordBK(@RequestParam String mapVo, Model mode) {
		try {
			List<Map<String, Object>> listVo = JSON.parseObject(mapVo, new TypeReference<List<Map<String, Object>>>() {
			});

			String result = hrTrainRecordService.deleteTrainRecordBKEmp(listVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 保存补课人员记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveTrainRecordBKEmp")
	public Map<String, Object> saveTrainRecordBKEmp(@RequestParam String mapVo, Model mode) {
		try {
			List<Map<String, Object>> listVo = JSON.parseObject(mapVo, new TypeReference<List<Map<String, Object>>>() {
			});

			String result = hrTrainRecordService.saveTrainRecordBKEmp(listVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询人员信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryEmpDetailInfo")
	public String queryEmpDetailInfo(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			String result = hrTrainRecordService.queryEmpDetailInfo(mapVo);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	@ResponseBody
	@RequestMapping("/queryTrainRecordPhoto")
	public String queryTrainRecordPhoto(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			List<Map<String, Object>> result = hrTrainRecordService.queryTrainRecordPhoto(mapVo);
			return ChdJson.toJson(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 保存图片
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/saveTrainRecordPhoto", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String saveTrainRecordPhoto(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

			Integer file_num = Integer.parseInt(mapVo.get("file_num").toString());

			List<MultipartFile> list = new ArrayList<>();
			for (int i = 1; i <= file_num; i++) {
				List<MultipartFile> files = multipartRequest.getFiles("file" + i);
				for (MultipartFile multipartFile : files) {
					list.add(multipartFile);
				}
			}

			List<Map<String, Object>> saveList = new ArrayList<>();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			Map<String, Object> queryMap = new HashMap<>();
			queryMap.put("group_id", SessionManager.getGroupId());
			queryMap.put("hos_id", SessionManager.getHosId());
			queryMap.put("plan_id", mapVo.get("plan_id"));
			List<Map<String, Object>> result = hrTrainRecordService.queryTrainRecordPhoto(queryMap);

			String paths = mapVo.get("file_path").toString();
			String[] split = paths.split(";");
			for (String string : split) {
				for (Map<String, Object> obj : result) {
					if (string.equals(obj.get("file_path").toString())) {
						saveList.add(obj);
					}
				}
			}

			Integer photo_id = 1;
			for (MultipartFile file : list) {
				String group_id = SessionManager.getGroupId();
				String hos_id = SessionManager.getHosId();
				String url = null;
				String fileExt = null;// 文件类型
				String fileName = file.getOriginalFilename();// 文件原名称
				fileExt = fileName.indexOf(".") != -1
						? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())
						: null;// 文件类型
				if (fileExt != null) {
					String basePath = "upLoad/" + group_id + "/" + hos_id + "/hr/train/record/photo/"
							+ mapVo.get("plan_id").toString() + "/";
					// 文件保存目录url
					String saveUrl = request.getContextPath() + "/" + basePath;

					url = saveUrl + fileName;
					FtpUtil.uploadFileExt(file, "", basePath, request, response);

					Map<String, Object> map = new HashMap<>();
					map.put("group_id", Integer.parseInt(group_id));
					map.put("hos_id", Integer.parseInt(hos_id));
					map.put("file_path", url);
					map.put("plan_id", Long.parseLong(mapVo.get("plan_id").toString()));
					map.put("up_time", dateFormat.format(new Date()));
					map.put("user_id", Integer.parseInt(SessionManager.getUserId()));
					saveList.add(map);
				} else {
					return "{\"error\":\"文件错误\"}";
				}
			}

			for (Map<String, Object> map : saveList) {
				map.put("photo_id", photo_id++);
			}
			hrTrainRecordService.deleteTrainRecordPhoto(queryMap);
			if (!saveList.isEmpty()) {
				hrTrainRecordService.saveTrainRecordPhoto(saveList);
			}

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	@RequestMapping(value = "/addFile", method = RequestMethod.POST)
	@ResponseBody
	public String addFile(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			// 处理头像上传
			MultipartFile file = null;
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			List<MultipartFile> files = multipartRequest.getFiles("file");
			if (files.size() == 1) {
				file = files.get(0);
			} else {
				for (MultipartFile multipartFile : files) {
					file = multipartFile;
				}
			}
			String group_id = SessionManager.getGroupId();
			String hos_id = SessionManager.getHosId();
			String url = null;
			String fileExt = null;// 文件类型
			String fileName = file.getOriginalFilename();// 文件原名称
			fileExt = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())
					: null;// 文件类型
			if (fileExt != null) {
				String basePath = "upLoad/" + group_id + "/" + hos_id + "/hr/train/record/file/"
						+ mapVo.get("plan_id").toString() + "/";
				// 文件保存目录url
				String saveUrl = request.getContextPath() + "/" + basePath;

				url = saveUrl + fileName;
				FtpUtil.uploadFileExt(file, "", basePath, request, response);
				return "{\"url\":\"" + url + "\"}";
			} else {
				return "{\"error\":\"文件错误\"}";
			}
		} catch (Exception e) {
			return "{\"error\":\"文件错误\"}";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/querySignInEmpSelect")
	public String querySignInEmpSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			String result = hrTrainRecordService.querySignInEmpSelect(mapVo);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询培训对象，排除已存在的对象
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryEmpSelectForRecord")
	public String queryEmpSelectForRecord(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			return hrTrainRecordService.queryEmpSelectForRecord(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryNoticeModeSelect")
	public String queryNoticeModeSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			String result = hrTrainRecordService.queryNoticeModeSelect(mapVo);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 导入数据
	 * 
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importDate", method = RequestMethod.POST)
	@ResponseBody
	public String importDate(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request)
			throws Exception {
		String reJson = hrTrainRecordService.importDate(mapVo);
		return reJson;
	}
}
