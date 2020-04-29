package com.chd.hrp.hr.controller.training;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.hr.dao.base.HrAccParaMapper;
import com.chd.hrp.hr.entity.base.HrAccPara;
import com.chd.hrp.hr.entity.training.exam.HrBukao;
import com.chd.hrp.hr.entity.training.exam.HrExam;
import com.chd.hrp.hr.entity.training.exam.HrExamResult;
import com.chd.hrp.hr.entity.training.exam.HrTrainCert;
import com.chd.hrp.hr.entity.training.exam.HrTrainEmpCert;
import com.chd.hrp.hr.entity.training.plant.HrPlant;
import com.chd.hrp.hr.service.training.HrTrainExamineService;
import com.chd.hrp.hr.service.training.plant.HrPlantService;

/**
 * 【培训管理-培训考核】
 * @author yang
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/training/examine")
public class HrTrainExamineController extends BaseController {

	private static Logger logger = Logger.getLogger(HrTrainExamineController.class);
	
	@Resource(name = "hrTrainExamineService")
	private final HrTrainExamineService hrTrainExamineService = null;// 培训考核service
	
	@Resource(name = "hrPlantService")
	private final HrPlantService hrPlantService = null;// 培训计划service
	
	@Resource(name = "hrAccParaMapper")
	private final HrAccParaMapper hrAccParaMapper = null;// 系统参数dao
	
	/**
	 * 培训考核主页面
	 */
	@RequestMapping(value = "/examineMainPage", method = RequestMethod.GET)
	public String examineMainPage(Model mode) throws Exception{
		// 查职工培训电子证书打印系统参数
		mode.addAttribute("p06007", MyConfig.getSysPara("06007"));
		
		return "hrp/hr/training/examine/hrTrainExamineMain";
	}
	
	/**
	 * 取培训计划树json
	 */
	@RequestMapping(value = "/getTrainPlanTreeJson", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getTrainPlanTreeJson(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		String reJson = hrTrainExamineService.queryTrainPlanTreeJson(putBasePara(paraMap));
		return reJson;
	}
	
	/**
	 * 取培训计划关联的考试安排、补考安排、培训证书
	 */
	@RequestMapping(value = "/queryTrainPlanRelationExam", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTrainExamUnique(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		return hrTrainExamineService.queryTrainPlanRelationExam(putBasePara(paraMap));
	}
	
	// 考试安排start********************************************************************************
	/**
	 * 保存考试安排
	 */
	@RequestMapping(value = "/saveTrainExam", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveTrainExam(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			String reJson = hrTrainExamineService.saveTrainExam(putBasePara(paraMap));
			return JSONObject.parseObject(reJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"保存失败.\",\"state\":\"false\"}");
		}
	}
	// 考试安排end********************************************************************************
	
	// 考试成绩start********************************************************************************
	/**
	 * 查询培训考试成绩
	 */
	@RequestMapping(value = "/queryExamResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryExamResult(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		String reJson = hrTrainExamineService.queryExamResult(getPage(putBasePara(paraMap)));
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * 查询培训对象职工数据
	 */
	@RequestMapping(value = "/queryTrainObjEmpData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTrainObjEmpData(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		String reJson = hrTrainExamineService.queryTrainObjEmpData(getPage(putBasePara(paraMap)));
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * 保存培训考试成绩
	 */
	@RequestMapping(value = "/saveExamResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveExamResult(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			paraMap = putBasePara(paraMap);
			HrExam exam = hrTrainExamineService.getExam(paraMap);
			if(exam == null){
				return JSONObject.parseObject("{\"error\":\"请先维护考试安排。\",\"state\":\"false\"}");
			}
			
			List<HrExamResult> list = JSONArray.parseArray(paraMap.get("paramVo").toString(), HrExamResult.class);
			if(!list.isEmpty()){
				for(HrExamResult examRes : list){
					if(examRes.getGroup_id() == null){
						examRes.setGroup_id(Long.valueOf(paraMap.get("group_id").toString()));
					}
					if(examRes.getHos_id() == null){
						examRes.setHos_id(Long.valueOf(paraMap.get("hos_id").toString()));
					}
					if(examRes.getPlan_id() == null){
						examRes.setPlan_id(Long.valueOf(paraMap.get("plan_id").toString()));
					}
				}
			}
			String reJson = hrTrainExamineService.saveExamResultBatch(list);
			return JSONObject.parseObject(reJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"保存考试成绩失败\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 批量生成考试记录
	 */
	@RequestMapping(value = "/generateExamResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generateExamResult(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			paraMap = putBasePara(paraMap);
			
			HrExam exam = hrTrainExamineService.getExam(paraMap);
			if(exam == null){
				return JSONObject.parseObject("{\"error\":\"请先维护考试安排.\",\"state\":\"true\"}");
			}
			
			String reJson = hrTrainExamineService.generateExamResult(paraMap);
			return JSONObject.parseObject(reJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"生成失败\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 删除培训考试成绩
	 */
	@RequestMapping(value = "/deleteExamResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteExamResult(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			paraMap = putBasePara(paraMap);
			List<HrExamResult> list = JSONArray.parseArray(paraMap.get("paramVo").toString(), HrExamResult.class);
			List<HrExamResult> listVo = new ArrayList<HrExamResult>();
			if(list.isEmpty()){
				return JSONObject.parseObject("{\"warn\":\"请选择行.\",\"state\":\"true\"}");
			}else{
				for(HrExamResult examRes : list){
					// 过滤掉没有在数据库里的记录
					if(examRes.getGroup_id() != null){
						listVo.add(examRes);
					}
				}
			}
			String reJson = hrTrainExamineService.deleteExamResultBatch(listVo);
			return JSONObject.parseObject(reJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"删除考试成绩失败\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 批量更新考试成绩
	 */
	@RequestMapping(value = "/updateExamScoreBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateExamScoreBatch(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			paraMap = putBasePara(paraMap);
			List<HrExamResult> list = JSONArray.parseArray(paraMap.get("paramVo").toString(), HrExamResult.class);
			
			// 要批量更新的值
			Double score = null;
			if(paraMap.containsKey("score")){
				score = Double.valueOf(paraMap.get("score").toString());
			}
			Integer isPass = null;
			if(paraMap.containsKey("is_pass")){
				isPass = Integer.valueOf(paraMap.get("is_pass").toString());
			}
			
			for(HrExamResult item : list){
				// 让新添加，没有执行过保存的也直接保存下来
				if(item.getGroup_id() == null){
					item.setGroup_id(Long.valueOf(paraMap.get("group_id").toString()));
				}
				if(item.getHos_id() == null){
					item.setHos_id(Long.valueOf(paraMap.get("hos_id").toString()));
				}
				if(item.getPlan_id() == null){
					item.setPlan_id(Long.valueOf(paraMap.get("plan_id").toString()));
				}
				
				// 赋 要批量更新的值
				if(score != null){
					item.setScore(score);
				}
				if(isPass != null){
					item.setIs_pass(isPass);
				}
			}
			String reJson = hrTrainExamineService.saveExamResultBatch(list);
			return JSONObject.parseObject(reJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"更新数据失败\",\"state\":\"false\"}");
		}
	}
	
	
	/**
	 * 导入培训考试成绩
	 */
	@RequestMapping(value = "/importExamResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importExamResult(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			String reJson = hrTrainExamineService.importExamResult(paraMap);
			return JSONObject.parseObject(reJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"导入考试成绩失败\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 上传考试原始卷
	 */
	@RequestMapping(value = "/uploadExamPaper", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadExamPaper(@RequestParam Map<String, Object> paraMap, 
			@RequestParam(required = false, value = "PAPER_FILE") MultipartFile file, HttpServletRequest request, Model mode)
					throws Exception {
		try{
			if(file == null){
				return JSONObject.parseObject("{\"error\":\"请选择文件.\",\"state\":\"true\"}");
			}
			
			paraMap = putBasePara(paraMap);
			String[] sarr = file.getOriginalFilename().split("\\.");
			String fileName = paraMap.get("emp_code").toString() + "." + sarr[sarr.length - 1];
			String basePath = "upLoad/" +SessionManager.getGroupId() +"/" +SessionManager.getHosId() 
							+ "/hr/train/exam/" + paraMap.get("plan_id").toString() + "/";// 文件保存目录路径
			File targetFile = uploadFirstStep(basePath, fileName, paraMap, file, request);
			String saveUrl = request.getContextPath() + "/" + basePath + fileName;
			
			String realPath = request.getSession().getServletContext().getRealPath("/");
			
			// 删除旧文件
			String oldPaperPath = paraMap.get("paper_path").toString().replaceFirst(request.getContextPath(), "");
			oldPaperPath = realPath + oldPaperPath;
			File tempFile = new File(oldPaperPath);
			boolean tempBoolean = tempFile.exists() ? tempFile.delete() : false;
			
			// 写入新文件
			file.transferTo(targetFile);
			paraMap.put("paper_path", saveUrl);// 记录路径
			
			if(hrTrainExamineService.updateExamResult(paraMap)){
				return JSONObject.parseObject("{\"msg\":\"上传成功.\",\"state\":\"true\"}");
			}
			return JSONObject.parseObject("{\"warn\":\"考试记录更新失败.\",\"state\":\"false\"}");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"上传原始卷失败.\",\"state\":\"false\"}");
		}
	}

	/**
	 * 批量上传考试原始卷
	 */
	@RequestMapping(value = "/uploadExamPaperBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadExamPaperBatch(@RequestParam Map<String, Object> paraMap, 
			HttpServletRequest request, Model mode) throws Exception {
		try{
			String basePath = "upLoad/" +SessionManager.getGroupId() +"/" +SessionManager.getHosId() 
							+ "/hr/train/exam/" + paraMap.get("plan_id").toString() + "/";// 文件保存目录路径
			int fileNum = Integer.parseInt(paraMap.get("fileNum").toString());
			Map<String, Object> map = batchUploadFirstStep(fileNum, basePath, request);
			
			paraMap = putBasePara(paraMap);
			
			boolean flag = false;
			List<HrExamResult> examResultlist = hrTrainExamineService.queryExamResultList(putBasePara(paraMap));
			for(HrExamResult exam : examResultlist){
				if(map.containsKey(exam.getEmp_code())){
					flag = true;
					exam.setPaper_path(map.get(exam.getEmp_code()).toString());
					MultipartFile f = (MultipartFile) map.get("srcfile" + exam.getEmp_code());
					f.transferTo((File) map.get("file" + exam.getEmp_code()));
				}
			}
			
			if(flag){
				// 更新考试记录
				if(hrTrainExamineService.updateExamResultBatch(JsonListMapUtil.beanToListMap(examResultlist))){
					return JSONObject.parseObject("{\"msg\":\"上传成功.\",\"state\":\"true\"}");
				}
				return JSONObject.parseObject("{\"error\":\"考试记录更新失败.\",\"state\":\"false\"}");
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有一个文件名跟职工工号对应，批量上传请确保文件名与职工工号对应.\",\"state\":\"true\"}");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"上传原始卷失败.\",\"state\":\"false\"}");
		}
	}
	// 考试成绩end********************************************************************************
	
	// 查询补考成绩start********************************************************************************
	/**
	 * 保存考试安排
	 */
	@RequestMapping(value = "/saveTrainBukao", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveTrainBukao(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			paraMap = putBasePara(paraMap);
			String reJson = hrTrainExamineService.saveTrainBukao(paraMap);
			
			// 更新补考参加人数
			List<HrExamResult> bukaoEmpList = hrTrainExamineService.queryBukaoEmpList(paraMap);
			paraMap.put("emp_num", "" + bukaoEmpList.size());
			hrTrainExamineService.updateTrainBukao(paraMap);
			
			Map<String, Object> reMap = JSONObject.parseObject(reJson);
			reMap.put("empNum", "" + bukaoEmpList.size());
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"保存失败.\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 查询补考成绩
	 */
	@RequestMapping(value = "/queryBukaoEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBukaoEmp(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		String reJson = hrTrainExamineService.queryBukaoEmp(getPage(putBasePara(paraMap)));
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * 查询考试结果、与缺考职工数据
	 */
	@RequestMapping(value = "/queryExamResultEmpData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryExamResultEmpData(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		String reJson = hrTrainExamineService.queryExamResultEmpData(getPage(putBasePara(paraMap)));
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * 查询考试、补考结果职工数据
	 */
	@RequestMapping(value = "/queryExamBukaoResultEmpData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryExamBukaoResultEmpData(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		String reJson = hrTrainExamineService.queryExamBukaoResultEmpData(getPage(putBasePara(paraMap)));
		return JSONObject.parseObject(reJson);
	}

	/**
	 * 删除补考成绩
	 */
	@RequestMapping(value = "/deleteBukaoEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBukaoEmp(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			paraMap = putBasePara(paraMap);
			List<HrExamResult> list = JSONArray.parseArray(paraMap.get("paramVo").toString(), HrExamResult.class);
			List<HrExamResult> listVo = new ArrayList<HrExamResult>();
			if(list.isEmpty()){
				return JSONObject.parseObject("{\"warn\":\"请选择行.\",\"state\":\"true\"}");
			}else{
				for(HrExamResult examRes : list){
					// 过滤掉没有执行过保存的记录
					if(examRes.getGroup_id() != null){
						listVo.add(examRes);
					}
				}
			}
			
			String reJson = hrTrainExamineService.deleteBukaoEmpBatch(listVo);
			
			// 更新补考参加人数
			List<HrExamResult> bukaoEmpList = hrTrainExamineService.queryBukaoEmpList(paraMap);
			paraMap.put("emp_num", "" + bukaoEmpList.size());
			hrTrainExamineService.updateTrainBukao(paraMap);
			
			Map<String, Object> reMap = JSONObject.parseObject(reJson);
			reMap.put("empNum", "" + bukaoEmpList.size());
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"删除补考成绩失败\",\"state\":\"false\"}");
		}
	}

	/**
	 * 保存补考成绩
	 */
	@RequestMapping(value = "/saveBukaoEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBukaoEmp(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			paraMap = putBasePara(paraMap);
			HrBukao bukao = hrTrainExamineService.getBukao(paraMap);
			if(bukao == null){
				return JSONObject.parseObject("{\"error\":\"请先维护补考安排\",\"state\":\"false\"}");
			}
			
			List<HrExamResult> list = JSONArray.parseArray(paraMap.get("paramVo").toString(), HrExamResult.class);
			if(!list.isEmpty()){
				for(HrExamResult bukaoEmp : list){
					if(bukaoEmp.getGroup_id() == null){
						bukaoEmp.setGroup_id(Long.valueOf(paraMap.get("group_id").toString()));
					}
					if(bukaoEmp.getHos_id() == null){
						bukaoEmp.setHos_id(Long.valueOf(paraMap.get("hos_id").toString()));
					}
					if(bukaoEmp.getPlan_id() == null){
						bukaoEmp.setPlan_id(Long.valueOf(paraMap.get("plan_id").toString()));
					}
				}
			}
			
			String reJson = hrTrainExamineService.saveBukaoEmpBatch(list);
			
			// 更新补考参加人数
			List<HrExamResult> bukaoEmpList = hrTrainExamineService.queryBukaoEmpList(paraMap);
			paraMap.put("emp_num", bukaoEmpList.size());
			hrTrainExamineService.updateTrainBukao(paraMap);
			
			Map<String, Object> reMap = JSONObject.parseObject(reJson);
			reMap.put("empNum", bukaoEmpList.size());
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"保存补考成绩失败\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 批量更新补考考试成绩
	 */
	@RequestMapping(value = "/updateBukaoScoreBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBukaoScoreBatch(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			paraMap = putBasePara(paraMap);
			List<HrExamResult> list = JSONArray.parseArray(paraMap.get("paramVo").toString(), HrExamResult.class);
			
			// 要批量更新的值
			Double score = null;
			if(paraMap.containsKey("score")){
				score = Double.valueOf(paraMap.get("score").toString());
			}
			Integer isPass = null;
			if(paraMap.containsKey("is_pass")){
				isPass = Integer.valueOf(paraMap.get("is_pass").toString());
			}
			
			for(HrExamResult item : list){
				// 让新添加，没有执行过保存的也直接保存下来
				if(item.getGroup_id() == null){
					item.setGroup_id(Long.valueOf(paraMap.get("group_id").toString()));
				}
				if(item.getHos_id() == null){
					item.setHos_id(Long.valueOf(paraMap.get("hos_id").toString()));
				}
				if(item.getPlan_id() == null){
					item.setPlan_id(Long.valueOf(paraMap.get("plan_id").toString()));
				}
				
				// 赋 批量更新的值
				if(score != null){
					item.setScore(score);
				}
				if(isPass != null){
					item.setIs_pass(isPass);
				}
			}
			
			String reJson = hrTrainExamineService.saveBukaoEmpBatch(list);
			return JSONObject.parseObject(reJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"更新数据失败\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 导入补考成绩
	 */
	@RequestMapping(value = "/importBukaoEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importBukaoEmp(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			String reJson = hrTrainExamineService.importBukaoEmp(paraMap);
			return JSONObject.parseObject(reJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"导入补考成绩失败\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 批量生成补考记录
	 */
	@RequestMapping(value = "/generateBukaoEmpBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generateBukaoEmpBatch(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			paraMap = putBasePara(paraMap);
			
			HrBukao bukao = hrTrainExamineService.getBukao(paraMap);
			if(bukao == null){
				return JSONObject.parseObject("{\"error\":\"请先维护补考安排.\",\"state\":\"true\"}");
			}
			
			// 只查第一次考试成绩不合格的人的考试记录
			List<HrExamResult> examResList = hrTrainExamineService.queryNoPassAndNoExamEmpData(paraMap);
			if(examResList.isEmpty()){
				return JSONObject.parseObject("{\"msg\":\"没有缺考和不合格的人员.\",\"state\":\"true\"}");
			}
			
			String reJson = hrTrainExamineService.generateBukaoEmpBatch(paraMap);
			
			// 更新补考参加人数
			List<HrExamResult> list = hrTrainExamineService.queryBukaoEmpList(paraMap);
			paraMap.put("emp_num", "" + list.size());
			hrTrainExamineService.updateBukaoEmp(paraMap);
			
			Map<String, Object> resMap = JSONObject.parseObject(reJson);
			resMap.put("empNum", "" + list.size());
			return resMap;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"生成失败\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 上传补考原始卷
	 */
	@RequestMapping(value = "/uploadBukaoPaper", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadBukaoPaper(@RequestParam Map<String, Object> paraMap, 
			@RequestParam(required = false, value = "PAPER_FILE") MultipartFile file, HttpServletRequest request, Model mode)
					throws Exception {
		try{
			paraMap = putBasePara(paraMap);
			String[] sarr = file.getOriginalFilename().split("\\.");
			String fileName = paraMap.get("emp_code").toString() + "." + sarr[sarr.length - 1];
			String basePath = "upLoad/" +SessionManager.getGroupId() +"/" +SessionManager.getHosId() 
							+ "/hr/train/bukao/" + paraMap.get("plan_id").toString() + "/";// 文件保存目录路径
			File targetFile = uploadFirstStep(basePath, fileName, paraMap, file, request);
			String saveUrl = request.getContextPath() + "/" + basePath + fileName;
			
			String realPath = request.getSession().getServletContext().getRealPath("/");
			
			// 删除旧文件
			String oldPaperPath = paraMap.get("paper_path").toString().replaceFirst(request.getContextPath(), "");
			oldPaperPath = realPath + oldPaperPath;
			File tempFile = new File(oldPaperPath);
			boolean tempBoolean = tempFile.exists() ? tempFile.delete() : false;
			
			file.transferTo(targetFile);
			paraMap.put("paper_path", saveUrl);
			
			if(hrTrainExamineService.updateBukaoEmp(paraMap)){
				return JSONObject.parseObject("{\"msg\":\"上传成功.\",\"state\":\"true\"}");
			}
			return JSONObject.parseObject("{\"warn\":\"补考记录更新失败.\",\"state\":\"false\"}");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"上传原始卷失败.\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 批量上传补考原始卷
	 */
	@RequestMapping(value = "/uploadBukaoPaperBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadBukaoPaperBatch(@RequestParam Map<String, Object> paraMap, 
			HttpServletRequest request, Model mode) throws Exception {
		try{
			int fileNum = Integer.parseInt(paraMap.get("fileNum").toString());
			String basePath = "upLoad/" +SessionManager.getGroupId() +"/" +SessionManager.getHosId() 
							+ "/hr/train/bukao/" + paraMap.get("plan_id").toString() + "/";// 文件保存目录路径
			
			Map<String, Object> paperMap = batchUploadFirstStep(fileNum, basePath, request);
			
			paraMap = putBasePara(paraMap);
			boolean flag = false;
			List<HrExamResult> buKaolist = hrTrainExamineService.queryBukaoEmpList(paraMap);
			for(HrExamResult bukao : buKaolist){
				if(paperMap.containsKey(bukao.getEmp_code())){
					flag = true;
					bukao.setPaper_path(paperMap.get(bukao.getEmp_code()).toString());
					MultipartFile f = (MultipartFile) paperMap.get("srcfile" + bukao.getEmp_code());
					f.transferTo((File) paperMap.get("file" + bukao.getEmp_code()));
				}
			}
			
			if(flag){
				if(hrTrainExamineService.updateBukaoEmpBatch(JsonListMapUtil.beanToListMap(buKaolist))){
					return JSONObject.parseObject("{\"msg\":\"上传成功.\",\"state\":\"true\"}");
				}
				return JSONObject.parseObject("{\"error\":\"补考记录更新失败.\",\"state\":\"false\"}");
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有一个文件名跟职工工号对应，批量上传请确保文件名与职工工号对应.\",\"state\":\"true\"}");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"上传原始卷失败.\",\"state\":\"false\"}");
		}
	}
	// 查询补考成绩end********************************************************************************
	
	// 培训证书start********************************************************************************
	/**
	 * 保存培训证书
	 */
	@RequestMapping(value = "/saveTrainCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveTrainCert(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			paraMap = putBasePara(paraMap);
//			HrPlant plan = hrPlantService.queryByCode(paraMap);
//			if(plan.getIs_cert() == 1){
				String reJson = hrTrainExamineService.saveTrainCert(paraMap);
				return JSONObject.parseObject(reJson);
//			}else{
//				return JSONObject.parseObject("{\"warn\":\"培训计划【" + plan.getTrain_title() + "】不发证.\",\"state\":\"false\"}");
//			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"保存培训证书失败.\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 查询职工培训证书
	 */
	@RequestMapping(value = "/queryTrainEmpCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTrainEmpCert(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		String reJson = hrTrainExamineService.queryTrainEmpCert(getPage(putBasePara(paraMap)));
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * 保存职工培训证书
	 */
	@RequestMapping(value = "/saveTrainEmpCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveTrainEmpCert(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			paraMap = putBasePara(paraMap);
			HrTrainCert cert = hrTrainExamineService.getTrainCert(paraMap);
			if(cert == null){
				return JSONObject.parseObject("{\"error\":\"请先维护培训证书.\",\"state\":\"false\"}");
			}
			HrPlant plan = hrPlantService.queryByCode(paraMap);
			String maxCertCode = hrTrainExamineService.getMaxCertCode(paraMap);
			int count = maxCertCode == null ? 0 : Integer.valueOf(maxCertCode.substring(maxCertCode.length() - 5));
			String certCode_ = plan.getTrain_type_code() + plan.getYear();
			
			List<HrTrainEmpCert> list = JSONArray.parseArray(paraMap.get("paramVo").toString(), HrTrainEmpCert.class);
			if(!list.isEmpty()){
				// 按职工工号升序排序
				Collections.sort(list, new Comparator<HrTrainEmpCert>(){
					@Override
					public int compare(HrTrainEmpCert empCert1, HrTrainEmpCert empCert2){
						int d = Integer.valueOf(empCert1.getEmp_code()) - Integer.valueOf(empCert2.getEmp_code());
						if(d > 0){
							return 1;
						}else if(d < 0){
							return -1;
						}
						return 0;
					}
				});
				
				for(HrTrainEmpCert empCert : list){
					if(empCert.getGroup_id() == null){
						empCert.setGroup_id(Long.valueOf(paraMap.get("group_id").toString()));
					}
					if(empCert.getHos_id() == null){
						empCert.setHos_id(Long.valueOf(paraMap.get("hos_id").toString()));
					}
					if(empCert.getPlan_id() == null){
						empCert.setPlan_id(Long.valueOf(paraMap.get("plan_id").toString()));
					}
					if(empCert.getIs_give() == null){
						empCert.setIs_give(0);
					}
					
					// 培训计划是发证的，就生成证书编号
					if(empCert.getCert_code() == null && plan.getIs_cert() == 1){
						empCert.setCert_code(certCode_ + String.format("%05d", ++count));
					}
				}
			}
			
			String reJson = hrTrainExamineService.saveTrainEmpCertBatch(list);
			return JSONObject.parseObject(reJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"保存职工培训证书失败\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 批量更新职工培训证书
	 */
	@RequestMapping(value = "/updateTrainEmpCertBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateTrainEmpCertBatch(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			paraMap = putBasePara(paraMap);
			List<HrTrainEmpCert> list = JSONArray.parseArray(paraMap.get("paramVo").toString(), HrTrainEmpCert.class);
			
			// 要批量更新的值
			Integer isGive = null;
			if(paraMap.containsKey("is_give")){
				isGive = Integer.valueOf(paraMap.get("is_give").toString());
			}
			
			for(HrTrainEmpCert item : list){
				// 把没有执行过保存的记录也保存起来
				if(item.getGroup_id() == null){
					item.setGroup_id(Long.valueOf(paraMap.get("group_id").toString()));
				}
				if(item.getHos_id() == null){
					item.setHos_id(Long.valueOf(paraMap.get("hos_id").toString()));
				}
				if(item.getPlan_id() == null){
					item.setPlan_id(Long.valueOf(paraMap.get("plan_id").toString()));
				}
				
				// 赋批量更新的值
				if(isGive != null){
					item.setIs_give(isGive);
				}
			}
			String reJson = hrTrainExamineService.saveTrainEmpCertBatch(list);
			return JSONObject.parseObject(reJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"更新数据失败\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 删除职工培训证书
	 */
	@RequestMapping(value = "/deleteTrainEmpCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteTrainEmpCert(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			paraMap = putBasePara(paraMap);
			List<HrTrainEmpCert> list = JSONArray.parseArray(paraMap.get("paramVo").toString(), HrTrainEmpCert.class);
			List<HrTrainEmpCert> listVo = new ArrayList<HrTrainEmpCert>();
			if(list.isEmpty()){
				return JSONObject.parseObject("{\"warn\":\"请选择行.\",\"state\":\"true\"}");
			}else{
				for(HrTrainEmpCert empCert : list){
					// 过滤掉没有执行过保存的记录
					if(empCert.getGroup_id() != null){
						listVo.add(empCert);
					}
				}
			}
			
			String reJson = hrTrainExamineService.deleteTrainEmpCertBatch(listVo);
			return JSONObject.parseObject(reJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"删除职工培训证书失败\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 生成职工培训证书记录
	 */
	@RequestMapping(value = "/generateTrainExamCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generateTrainExamCert(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			String reJson = hrTrainExamineService.generateTrainExamCert(putBasePara(paraMap));
			return JSONObject.parseObject(reJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"生成职工培训证书失败\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 上传职工培训证书
	 */
	@RequestMapping(value = "/uploadTrainEmpCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadTrainEmpCert(@RequestParam Map<String, Object> paraMap, 
			@RequestParam(required = false, value = "CERT_FILE") MultipartFile file, HttpServletRequest request, Model mode)
					throws Exception {
		try{
			paraMap = putBasePara(paraMap);
			String[] sarr = file.getOriginalFilename().split("\\.");
			String fileName = paraMap.get("emp_code").toString() + "." + sarr[sarr.length - 1];
			String basePath = "upLoad/" +SessionManager.getGroupId() +"/" +SessionManager.getHosId() 
							+ "/hr/train/cert/" + paraMap.get("plan_id").toString() + "/";// 文件保存目录路径
			File targetFile = uploadFirstStep(basePath, fileName, paraMap, file, request);
			String saveUrl = request.getContextPath() + "/" + basePath + fileName;
			
			String realPath = request.getSession().getServletContext().getRealPath("/");
			
			// 删除旧文件
			String oldPaperPath = paraMap.get("cert_path").toString().replaceFirst(request.getContextPath(), "");
			oldPaperPath = realPath + oldPaperPath;
			File tempFile = new File(oldPaperPath);
			boolean tempBoolean = tempFile.exists() ? tempFile.delete() : false;
			
			file.transferTo(targetFile);
			paraMap.put("cert_path", saveUrl);
			
			if(hrTrainExamineService.updateTrainEmpCert(paraMap)){
				return JSONObject.parseObject("{\"msg\":\"上传成功.\",\"state\":\"true\"}");
			}
			return JSONObject.parseObject("{\"warn\":\"职工培训证书更新失败.\",\"state\":\"false\"}");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"上传证书失败.\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 批量上传职工培训证书
	 */
	@RequestMapping(value = "/uploadEmpCertBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadEmpCertBatch(@RequestParam Map<String, Object> paraMap, 
			HttpServletRequest request, Model mode) throws Exception {
		try{
			int fileNum = Integer.parseInt(paraMap.get("fileNum").toString());
			String basePath = "upLoad/" +SessionManager.getGroupId() +"/" +SessionManager.getHosId() 
							+ "/hr/train/cert/" + paraMap.get("plan_id").toString() + "/";// 文件保存目录路径
			
			Map<String, Object> paperMap = batchUploadFirstStep(fileNum, basePath, request);
			
			paraMap = putBasePara(paraMap);
			boolean flag = false;
			List<HrTrainEmpCert> certlist = hrTrainExamineService.queryTrainEmpCertList(putBasePara(paraMap));
			for(HrTrainEmpCert cert : certlist){
				if(paperMap.containsKey(cert.getEmp_code())){
					flag = true;
					cert.setCert_path(paperMap.get(cert.getEmp_code()).toString());
					MultipartFile f = (MultipartFile) paperMap.get("srcfile" + cert.getEmp_code());
					f.transferTo((File) paperMap.get("file" + cert.getEmp_code()));
				}
			}
			
			if(flag){
				// 更新考试记录
				if(hrTrainExamineService.updateTrainEmpCertBatch(JsonListMapUtil.beanToListMap(certlist))){
					return JSONObject.parseObject("{\"msg\":\"上传成功.\",\"state\":\"true\"}");
				}
				return JSONObject.parseObject("{\"error\":\"职工证书记录更新失败.\",\"state\":\"false\"}");
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有一个文件名跟职工工号对应，批量上传请确保文件名与职工工号对应.\",\"state\":\"true\"}");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"上传证书失败.\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 批量下载职工培训证书前检查证书文件是否存在，
	 * 查看考试、补考原始卷前检查
	 */
	@RequestMapping(value = "/checkFileIsExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkFileIsExists(@RequestParam Map<String, Object> paraMap, 
			HttpServletRequest request, Model mode) throws Exception {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String ctxPath = request.getContextPath();
		
		if(paraMap.containsKey("file_path") && !"".equals(paraMap.get("file_path").toString())){
			// 检查单个文件
			String fp = realPath + paraMap.get("file_path").toString().replaceFirst(ctxPath, "");
			File f = new File(fp);
			if(f.exists()){
				return JSONObject.parseObject("{\"exists\":\"true\",\"state\":\"true\"}"); 
			}
			return JSONObject.parseObject("{\"warn\":\"文件不在此服务器或源文件被删除.\",\"exists\":\"false\",\"state\":\"false\"}"); 
		}else{
			// 检查所有职工证书
			List<HrTrainEmpCert> empCertList = hrTrainExamineService.queryTrainEmpCertExistsCertPath(putBasePara(paraMap));
			if(empCertList.isEmpty()){
				return JSONObject.parseObject("{\"error\":\"没有可下载证书.\",\"state\":\"false\"}");
			}else{
				StringBuilder sb = new StringBuilder("以下职工的证书不在此服务器：");
				int i = 0;
				boolean flag = false;
				boolean flag2 = false;// 有没有拼接省略号
				for(HrTrainEmpCert empCert : empCertList){
					String filePath = realPath + empCert.getCert_path().replaceFirst(ctxPath, "");
					File file = new File(filePath);
					if(!file.exists()){
						if(i <= 8){
							sb.append("<br/>").append(empCert.getEmp_name());
							if(i >= 8){// 大于8个拼个省略号
								if(!flag2){
									sb.append("<br/>").append("……");
									flag2 = true;
								}
							}
						}
						i++;
					}else{
						flag = true;
					}
				}
				
				// i > 0  表示：有些职工上传的证书不在程序所在服务器上
				if(i > 0 && flag){// 服务上存在其他人的证书就下载，状态为true
					return JSONObject.parseObject("{\"warn\":\"" + sb.toString() + "\",\"state\":\"true\"}");
				}else if(i > 0){
					return JSONObject.parseObject("{\"error\":\"" + sb.toString() + ".\",\"state\":\"false\"}");
				}
			}
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}
	}
	
	/**
	 * 批量下载职工培训证书
	 */
	@RequestMapping(value = "/downloadCertBatch", method = RequestMethod.GET)
	public String downloadCertBatch(@RequestParam Map<String, Object> paraMap, 
			HttpServletRequest request, HttpServletResponse response, Model mode) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("UTF-8");
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		FileOutputStream fileOutputStream = null;
		ZipOutputStream zipOutputStream = null;
		File tempFile = null;
		List<HrTrainEmpCert> empCertList = hrTrainExamineService.queryTrainEmpCertExistsCertPath(putBasePara(paraMap));
		List<File> fileList = new ArrayList<File>();
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String fileName = paraMap.get("plan_id").toString() + ".zip";// 文件保存目录路径
		String tempFilePath = realPath + "/upLoad/" +SessionManager.getGroupId() +"/" +SessionManager.getHosId() 
							+ "/hr/train/cert/" + paraMap.get("plan_id").toString() + System.currentTimeMillis() + ".zip";
		String ctxPath = request.getContextPath();
		try {
			for(HrTrainEmpCert empCert : empCertList){
				String filePath = realPath + empCert.getCert_path().replaceFirst(ctxPath, "");
				File file = new File(filePath);
				if(file.exists()){
					fileList.add(file);
				}
			}
			tempFile = new File(tempFilePath);
			fileOutputStream = new FileOutputStream(tempFile);
			zipOutputStream = new ZipOutputStream(fileOutputStream);
			zipFileList(fileList, zipOutputStream);
			zipOutputStream.close();
			
			long fileLength = tempFile.length();
			response.setContentType("application/octet-stream;");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			
			bis = new BufferedInputStream(new FileInputStream(tempFile.getPath()));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(zipOutputStream != null){
				zipOutputStream.close();
			}
			if(fileOutputStream != null){
				fileOutputStream.close();
			}
			if (bis != null){
				bis.close();
			}
			if (bos != null){
				bos.close();
			}
			tempFile.delete();
		}
		return null;
	}
	// 培训证书end********************************************************************************
	
	/**
	 * 文件集合压缩成zip
	 */
	private static void zipFileList(List<File> fileList, ZipOutputStream zipOutputStream) throws Exception {
		for (File file : fileList) {
			try {
				if (!file.exists()) {
					continue;
				} else {
					if (file.isFile()) {
						zipFile(file, zipOutputStream);
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				continue;
			}
		}
	}
	
	/**
	 * 文件压缩成zip
	 */
	private static void zipFile(File file, ZipOutputStream zipOutputStream) throws Exception {
		FileInputStream fileInputStream = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			if(!file.exists()){
				throw new SysException("文件" + file.getName() + "不存在.");
			}else{
				if (file.isFile()) {
					fileInputStream = new FileInputStream(file);
					bufferedInputStream = new BufferedInputStream(fileInputStream);
					ZipEntry zipEntry = new ZipEntry(file.getName());
					zipOutputStream.putNextEntry(zipEntry);
					
					final int MAX_BYTE = 2048;
					long streamTotal = 0; // 接受流的容量
					int streamNum = 0; // 流需要分开的数量
					int leaveByte = 0; // 文件剩下的字符数
					byte[] inOutbyte; // byte数组接受文件的数据
					
					streamTotal = bufferedInputStream.available(); // 通过available方法取得流的最大字符数
					streamNum = (int) Math.floor(streamTotal / MAX_BYTE);// 取得流文件需要分开的数量
					leaveByte = (int) streamTotal % MAX_BYTE; // 分开文件之后,剩余的数量
					
					if (streamNum > 0) {
						for (int j = 0; j < streamNum; ++j) {
							inOutbyte = new byte[MAX_BYTE];
							// 读入流,保存在byte数组
							bufferedInputStream.read(inOutbyte, 0, MAX_BYTE);
							zipOutputStream.write(inOutbyte, 0, MAX_BYTE); // 写出流
						}
					}
					// 写出剩下的流数据
					inOutbyte = new byte[leaveByte];
					
					bufferedInputStream.read(inOutbyte, 0, leaveByte);
					zipOutputStream.write(inOutbyte);
				}
			}
			
			// 关闭
			zipOutputStream.closeEntry();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}finally {
			if (bufferedInputStream != null){
				bufferedInputStream.close();
			}
			if (fileInputStream != null){
				fileInputStream.close();
			}
		}
	}
	
	/**
	 * 放入集团、医院、账套基本参数
	 */
	private Map<String, Object> putBasePara(Map<String, Object> map){
		if(map.get("group_id") == null){
			map.put("group_id", SessionManager.getGroupId());
		}
		if(map.get("hos_id") == null){
			map.put("hos_id", SessionManager.getHosId());
		}
		return map;
	}
	
	/**
	 * 上传第一步，确保文件存储路径存在，删除旧文件，
	 */
	private File uploadFirstStep(String basePath, String fileName, Map<String, Object> map, MultipartFile file, HttpServletRequest request){
		String realPath = request.getSession().getServletContext().getRealPath("/") + "/" + basePath;
		String fileUrl = realPath + fileName;
		
		// 确保文件存储路径存在
		File realPathFile = new File(realPath);
		if(!realPathFile.exists()){
			realPathFile.mkdirs();
		}
		// 删除旧文件（扩展名相同）
		File paperFile = new File(fileUrl);
		if(paperFile.exists()){
			paperFile.delete();
		}
		return paperFile;
	}
	
	/**
	 * 批量上传第一步，确保文件存储路径存在，删除旧文件,返回map存放的是文件保存路径、源文件对象、目标文件对象
	 */
	private Map<String, Object> batchUploadFirstStep(int num, String basePath, HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> srcFileList = new ArrayList<MultipartFile>();
		for(int i = 0; i < num; i++){
			srcFileList.add(multipartRequest.getFile("file" + i));
		}
		
		for(MultipartFile file : srcFileList){
			String realPath = request.getSession().getServletContext().getRealPath("/") + "/" + basePath;
			String fileName = file.getOriginalFilename();
			String saveUrl = request.getContextPath() + "/" + basePath + fileName;
			String fileUrl = realPath + fileName;
			
			File realPathFile = new File(realPath);
			if(!realPathFile.exists()){
				realPathFile.mkdirs();
			}
			File targetFile = new File(fileUrl);
			if(targetFile.exists()){
				targetFile.delete();// 删除旧的
			}
			
			String empCode = fileName.split("\\.")[0];
			map.put(empCode, saveUrl);// 保存文件路径（以项目开始）
			map.put("srcfile" + empCode, file);// 源文件
			map.put("file" + empCode, targetFile);//保存路径的文件对象
		}
		return map;
	}
}
