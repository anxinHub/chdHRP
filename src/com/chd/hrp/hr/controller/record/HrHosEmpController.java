package com.chd.hrp.hr.controller.record;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.chd.base.MyConfig;
import com.chd.base.Parameter;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.hrp.hr.entity.sysstruc.HrTabStruc;
import com.chd.hrp.hr.service.base.HrCommonService;
import com.chd.hrp.hr.service.base.HrSelectService;
import com.chd.hrp.hr.service.record.HrStatisticConditionService;
import com.chd.hrp.hr.service.record.HrStatisticCustomService;
import com.chd.hrp.hr.service.record.HrStatisticTabStrucService;
import com.chd.hrp.hr.service.sysstruc.HrTabStrucService;

/**
 * 
 * @ClassName: HrEmpController
 * @Description: 人事档案
 * @author zn
 * @date 2017年10月16日 下午3:51:45
 * 
 *
 */

@Controller
@RequestMapping(value = "/hrp/hr/record")
public class HrHosEmpController extends BaseController {


	private static Logger logger = Logger.getLogger(HrHosEmpController.class);

	private static final String HR_EMP_TABLE_NAME = "HOS_EMP";

	// 引入Service服务 
	@Resource(name = "hrSelectService")
	private final HrSelectService hrSelectService = null;

	@Resource(name = "hrCommonService")
	private final HrCommonService hrCommonService = null;

	@Resource(name = "hrTabStrucService")
	private final HrTabStrucService HrTabStrucService = null;

	// 简单统计
	@Resource(name = "hrStatisticCustomService")
	private final HrStatisticCustomService hrStatisticCustomService = null;

	@Resource(name = "hrStatisticTabStrucService")
	private final HrStatisticTabStrucService hrStatisticTabStrucService = null;

	@Resource(name = "hrStatisticConditionService")
	private final HrStatisticConditionService hrStatisticConditionService = null;

	/**
	 * 初始化参数
	 * 
	 * @param mapVo
	 */
	private void initParam(Map<String, Object> mapVo) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
	}

	/**
	 * 验证参数完整性
	 * 
	 * @param mapVo
	 *            参数
	 * @param group
	 *            需要验证的参数组
	 * @return
	 */
	private boolean paramValidator(Map<String, Object> mapVo, String... group) {
		for (String str : group) {
			if (!mapVo.containsKey(str) || mapVo.get(str) == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 返回异常信息
	 * 
	 * @param obj
	 * @return
	 */
	private String getErrorJson(Object obj) {
		Map<String, Object> reJson = new HashMap<String, Object>();
		reJson.put("error", obj);
		return JSONObject.toJSONString(reJson);
	}

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrEmpMainPage", method = RequestMethod.GET)
	public String hrEmpMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		initParam(mapVo);

		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("table_code", HR_EMP_TABLE_NAME);
		// 权限控制只读
		List<Map<String, Object>> userPerms = hrCommonService.queryHrUserPermByUserId(mapVo);
		if (userPerms != null && userPerms.size() > 0) {
			Map<String, Object> userPerm = userPerms.get(0);
			mode.addAttribute("is_perm", MyConfig.getSysPara("06005")/*MyConfig.getSysPara("06005")*/);
			mode.addAllAttributes(userPerm);
		}
         mode.addAttribute("main_select", MyConfig.getSysPara("06006"));
		return "hrp/hr/record/emp/hrEmpMain";
	}

	/**
	 * @Description 单独子集页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrEmpChildPage", method = RequestMethod.GET)
	public String hrEmpChildPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		initParam(mapVo);
		if(mapVo.get("tab_name")!=null && !mapVo.get("tab_name").toString().equals("")){
			mode.addAttribute("tab_name", mapVo.get("tab_name"));
		}else{
			HrTabStruc tab = HrTabStrucService.queryHrTabStrucByCode(mapVo);
			if (tab != null) {
				mode.addAttribute("tab_name", tab.getTab_name());
			}
		}
		mode.addAttribute("tab_code", mapVo.get("tab_code"));
		
		return "hrp/hr/record/emp/hrEmpChild";
	}
	
	/**
	 * @Description 获取主集表头
	 * @param mapVo
	 *            {档案库分类(store_type_code),数据表(tab_code)}
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEmpMainHead", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpMainHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("store_type_code") == null || mapVo.get("store_type_code") == "") {return "{\"error\":\"请选择档案库 ！\"}";}
		
		if (mapVo.get("main_code") == null || mapVo.get("main_code") == "") {return "{\"error\":\"请选择主集\"}";}
		
		initParam(mapVo);// 初始化参数
		
		try {
			if(MyConfig.getSysPara("06005").toString().equals("1")) {
				mapVo.put("is_read",1);
			}else {
				mapVo.put("is_read","");
			}
			String str = hrCommonService.queryEmpHead(mapVo);
			
			return str;
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}

	/**
	 * @Description 获取主集数据
	 * @param mapVo {档案库分类(store_type_code),数据表(tab_code)}
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEmpMainGrid", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpMainGrid(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		if (mapVo.get("tmpSQL") == null || mapVo.get("tmpSQL") == "") {return "{\"error\":\"请选择主集\"}";}
		
		initParam(mapVo);// 初始化参数
		
		try {
			if(MyConfig.getSysPara("06005").toString().equals("1")) {
				mapVo.put("is_read",1);
			}else {
				mapVo.put("is_read","");
			}
			return hrCommonService.queryEmpGrid(getPage(mapVo));
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}
	
	/**
	 * @Description 获取主集表头
	 * @param mapVo
	 *            {档案库分类(store_type_code),数据表(tab_code)}
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEmpChildHead", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpChildHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("store_type_code") == null || mapVo.get("store_type_code") == "") {return "{\"error\":\"数据查询错误 ！\"}";}
		
		if (mapVo.get("tab_code") == null || mapVo.get("tab_code") == "") {return "{\"error\":\"请选择主集\"}";}
		
		initParam(mapVo);// 初始化参数
		
		try {
			if(MyConfig.getSysPara("06005").toString().equals("1")) {
				mapVo.put("is_read",1);
			}else {
				mapVo.put("is_read","");
			}
			String str = hrCommonService.queryEmpChildHead(mapVo);
			
			return str;
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}

	/**
	 * @Description 获取主集数据
	 * @param mapVo
	 *            {档案库分类(store_type_code),数据表(tab_code)}
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEmpChildGrid", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpChildGrid(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		if (mapVo.get("tmpSQL") == null || mapVo.get("tmpSQL") == "") {return "{\"error\":\"数据查询错误\"}";}
		
		initParam(mapVo);// 初始化参数
		
		try {
			
			String str = hrCommonService.queryEmpChildGrid(mapVo);
			
			return str;
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}

	/**
	 * 获取员工子表格信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/queryEmpChildGrid", method = RequestMethod.POST)
//	@ResponseBody
//	public String queryEmpChildGrid(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
//
//		if (!paramValidator(mapVo, "store_type_code", "tab_code")) {
//			return "{\"error\":\"获取信息失败，请求参数不完整 ！\"}";
//		}
//
//		initParam(mapVo);
//
//		boolean hasData = false;
//		if (mapVo.get("EMP_ID") != null && StringUtils.isNotBlank(mapVo.get("EMP_ID").toString())) {
//			hasData = true;
//		}
//
//		try {
//			// 表格
//			Map<String, Object> gridMap = new HashMap<String, Object>();
//
//			// 表格头信息
//			List<Map<String, Object>> fieldItems = hrCommonService.queryEditGridHeader(mapVo);
//			gridMap.put("columns", fieldItems);
//
//			// 表格数据
//			Map<String, Object> datas = null;
//			if (hasData) {
//				datas = hrCommonService.queryGridData(mapVo);
//			}
//
//			gridMap.put("data", datas);
//
//			return JSONArray.toJSONString(gridMap);
//		} catch (Exception e) {
//			return getErrorJson(e.getMessage());
//		}
//
//	}

	//添加页面相关功能区域----------------------------------------------------------------------------------------
	
	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrEmpAddPage", method = RequestMethod.GET)
	public String hrEmpAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("tab_code", HR_EMP_TABLE_NAME);

		mode.addAllAttributes(mapVo);

		return "hrp/hr/record/emp/hrEmpAdd";
	}

	/**
	 * 获取form表单
	 * 
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrEmpForm", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrEmpForm(@RequestParam Map<String, Object> mapVo) throws Exception {

		if (!paramValidator(mapVo, "store_type_code")) {
			return "{\"error\":\"获取信息失败，请求参数不完整 ！\"}";
		}

		initParam(mapVo);
		mapVo.put("tab_code", HR_EMP_TABLE_NAME);

		// 是否为修改页面，是则先查询数据


		int colNum = Integer.parseInt(MyConfig.getSysPara("06001"));

		String formEle = hrCommonService.queryEditForm(mapVo, colNum);

		return formEle;
	}
	

	/**
	 * 添加员工基本信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addEmpForm", method = RequestMethod.POST)
	@ResponseBody
	public String addEmpForm(@RequestParam Map<String, Object> mapVo,@RequestParam(required = false, value = "PHOTO") MultipartFile file, Model mode, HttpServletRequest request)throws Exception {
		try {
			if (!paramValidator(mapVo, "store_type_code", "tab_code")) {return "{\"error\":\"获取信息失败，请求参数不完整 ！\"}";}
	
			initParam(mapVo);
			
			// 处理头像上传
			if (file != null) {
				
				String path = null,url = null,fileExt = null;// 文件路径、文件类型
	
				String fileName = file.getOriginalFilename();// 文件原名称
				
				fileExt = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()): null;// 文件类型
				
				if (fileExt == null) {return "{\"error\":\"头像图片格式错误，请选择以.png .jpg .gif 为扩展名的文件\"}";}
					
				if (!Pattern.matches("GIF|PNG|JPG",fileExt.toUpperCase())) {return "{\"error\":\"头像图片格式错误，请选择以.png .jpg .gif 为扩展名的文件\"}";}
	
				String basePath = "upLoad/"+SessionManager.getGroupId()+"/"+SessionManager.getHosId()+"/hr/images/";// 文件保存目录路径
				
				String realPath = request.getSession().getServletContext().getRealPath("/") + "/" + basePath;
				
				String saveUrl = request.getContextPath() + "/" + basePath;// 文件保存目录url
				
				String trueFileName = String.valueOf(System.currentTimeMillis()) + "." + fileExt;// 自定义的文件名称
				
				path = realPath + trueFileName;// 设置存放图片文件的路径
				
				url = saveUrl + trueFileName;
	
				File saveDirFile = new File(realPath);
				
				if (!saveDirFile.exists()) {saveDirFile.mkdirs();}
				
				file.transferTo(new File(path));// 转存文件到指定的路径
	       
				mapVo.put("PHOTO", url);   

			}
		
			return hrCommonService.saveFrom(mapVo);
			
		} catch (SysException e) {
			
			return getErrorJson(e.getMessage());
			
		}

	}

	/**
	 * 添加员工附属信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addEmpSubItem", method = RequestMethod.POST)
	@ResponseBody
	public String addEmpSubItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (!paramValidator(mapVo, "store_type_code", "tab_code", "EMP_ID")) {
			return "{\"error\":\"获取信息失败，请求参数不完整 ！\"}";
		}

		String tab_code = mapVo.get("tab_code") == null ? null : mapVo.get("tab_code").toString();
		String EMP_ID = mapVo.get("EMP_ID") == null ? null : mapVo.get("EMP_ID").toString();

		// 添加数据集
		List<Parameter> addlistVo = JSONArray.parseArray(String.valueOf(mapVo.get("addData")), Parameter.class);
		// 更新数据集
		List<Parameter> modlistVo = JSONArray.parseArray(String.valueOf(mapVo.get("modData")), Parameter.class);

		if (addlistVo != null && addlistVo.size() > 0) {
			for (Parameter parameter : addlistVo) {
				parameter.put("EMP_ID", EMP_ID);
				parameter.put("tab_code", tab_code);
				parameter.put("store_type_code", mapVo.get("store_type_code"));
			}
		}

		if (modlistVo != null && modlistVo.size() > 0) {
			for (Parameter parameter : modlistVo) {
				parameter.put("EMP_ID", EMP_ID);
				parameter.put("tab_code", tab_code);
				parameter.put("store_type_code", mapVo.get("store_type_code"));
			}
		}

		try {
			return hrCommonService.saveGrid(tab_code, addlistVo, modlistVo);
		} catch (Exception e) {
			return getErrorJson(e.getMessage());
		}

	}
	
	
	/**
	 * 修改员工附属信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchEmpUpate", method = RequestMethod.POST)
	@ResponseBody
	public String batchEmpUpate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
        
		

		try {
			initParam(mapVo);// 初始化参数
			return hrCommonService.batchEmpUpate(mapVo);
		} catch (Exception e) {
			return getErrorJson(e.getMessage());
		}

	}
	//添加页面相关功能区域----------------------------------------------------------------------------------------
	
	/**
	 * 一览统计页面
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/totalDetail", method = RequestMethod.GET)
	public String totalDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("store_type_code") == null || mapVo.get("store_type_code") == "") {return "{\"error\":\"请选择档案库 ！\"}";}
		
		if (mapVo.get("emp_id") == null || mapVo.get("emp_id") == "") {return "{\"error\":\"请选择职工 ！\"}";}
		
		initParam(mapVo);

		List<Map<String,Object>> datas = hrCommonService.totalDetail(mapVo);

		mode.addAttribute("data", JSONArray.toJSONString(datas));

		return "hrp/hr/record/emp/totalDetail";
	}
	
	
	/**
	 * 删除员工信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteEmpGridItem", method = RequestMethod.POST)
	@ResponseBody
	public String deleteEmpGridItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (!paramValidator(mapVo, "tab_code")) {
			return "{\"error\":\"操作失败，请求参数不完整 ！\"}";
		}

		String tab_code = mapVo.get("tab_code").toString();

		List<Parameter> paramList = JSONArray.parseArray(String.valueOf(mapVo.get("paramVo")), Parameter.class);

		try {
			return hrCommonService.deleteBatchItem(tab_code, paramList);
		} catch (Exception e) {
			return getErrorJson(e.getMessage());
		}

	}

	/**
	 * @Description 自定义查询信息列表
	 * @param mapVo
	 *            {参数列表(param),数据表(TAB_CODE)}
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/customSearchEmp", method = RequestMethod.POST)
	@ResponseBody
	public String customSearchEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (!paramValidator(mapVo, "tab_code", "store_type_code")) {
			return "{\"error\":\"操作失败，请求参数不完整 ！\"}";
		}

		initParam(mapVo);

		return JSONObject.toJSONString(hrCommonService.queryGridDataByCustom(mapVo));

	}

	/**
	 * 搜索页面
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchPage", method = RequestMethod.GET)
	public String searchPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		initParam(mapVo);

		if (mapVo.get("tab_code") == null || StringUtils.isBlank(mapVo.get("tab_code").toString())) {
			mapVo.put("tab_code", HR_EMP_TABLE_NAME);
		}

		int colNum = Integer.parseInt(MyConfig.getSysPara("06002"));
       
		String fromEle = hrCommonService.queryFormSearchPage(mapVo, colNum);
		mode.addAttribute("formEle", fromEle);
		/*  if(mapVo.get("child_tab_code")!=null || StringUtils.isBlank(mapVo.get("child_tab_code").toString())) {
	        	 mapVo.put("tab_code", mapVo.get("child_tab_code"));
	         }
		  String child_fromEle = hrCommonService.queryQueForm(mapVo, colNum);
		  mode.addAttribute("child_fromEle", child_fromEle);*/
		logger.debug(fromEle);
		return "hrp/hr/record/emp/search";
	}

	
	/**
	 * 批量修改页面
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrEmpUpatePage", method = RequestMethod.GET)
	public String hrEmpUpatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		initParam(mapVo);

		if (mapVo.get("tab_code") == null || StringUtils.isBlank(mapVo.get("tab_code").toString())) {
			mapVo.put("tab_code", HR_EMP_TABLE_NAME);
		}

		int colNum = Integer.parseInt(MyConfig.getSysPara("06002"));
       
		String fromEle = hrCommonService.queryFormSearchPage(mapVo, colNum);
		mode.addAttribute("formEle", fromEle);
//		  if(mapVo.get("child_tab_code")!=null || StringUtils.isBlank(mapVo.get("child_tab_code").toString())) {
//	        	 mapVo.put("tab_code", mapVo.get("child_tab_code"));
//	         }
//		 
//		  String child_fromEle = hrCommonService.queryQueForm(mapVo, colNum);
//		  mode.addAttribute("child_fromEle", child_fromEle);
		
		return "hrp/hr/record/emp/batchUpdate";
	}
	
	
	
	
	/**
	 * 批量修改页面左侧表单
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrEmpUpateLeft", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String hrEmpUpateLeft(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> formMap = new HashMap<String, Object>();
		List<Map<String, Object>> fieldItems = new ArrayList<Map<String, Object>>();
		initParam(mapVo);

		if (mapVo.get("tab_code") == null || StringUtils.isBlank(mapVo.get("tab_code").toString())) {
			mapVo.put("tab_code", HR_EMP_TABLE_NAME);
		}

		int colNum = Integer.parseInt(MyConfig.getSysPara("06002"));

		String fromEle = hrCommonService.queryQueFormLeft(mapVo, colNum);
	
	
		 
		
		return fromEle;
	}
	/**
	 * 批量修改页面左侧表单
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrEmpUpateLeftChild", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String hrEmpUpateLeftChild(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		initParam(mapVo);

	

		int colNum = Integer.parseInt(MyConfig.getSysPara("06002"));

	
		  if(mapVo.get("child_code")!=null || StringUtils.isBlank(mapVo.get("child_code").toString())) {
	        	 mapVo.put("tab_code", mapVo.get("child_code"));
	         }
		 
		 String fromEle= hrCommonService.queryQueFormLeft(mapVo, colNum);
		return fromEle;
	}
	/**
	 * 自定义搜索页面
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/customSearchPage", method = RequestMethod.GET)
	public String customSearchPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAllAttributes(mapVo);
		return "hrp/hr/record/emp/customSearch";
	}


	/**
	 * 查询简单统计设置
	 * 
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrStatisticCustomSet", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrStatisticCustomSet(@RequestParam Map<String, Object> mapVo) throws Exception {
		initParam(mapVo);
		return hrStatisticCustomService.queryHrStatisticCustomSetMenu(mapVo);
	}

	/**
	 * 简单统计设置添加页面
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrStatisticCustomSetForm", method = RequestMethod.GET)
	public String hrStatisticCustomSetForm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAllAttributes(mapVo);

		// 如果statistic_code != null 是更新操作 回显数据
		if (mapVo.get("statistic_code") != null) {
			initParam(mapVo);
			Map<String, Object> datas = hrStatisticCustomService.queryHrStatisticCustomSetByCode(mapVo);
			mode.addAllAttributes(datas);
		}

		return "hrp/hr/record/emp/hrStatisticCustomSetForm";
	}

	/**
	 * 简单统计设置添加业务
	 * 
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveHrStatisticCustomSet", method = RequestMethod.POST)
	@ResponseBody
	public String saveHrStatisticCustomSet(@RequestParam Map<String, Object> mapVo) throws Exception {
		if(mapVo == null || mapVo.get("statistic_code") == null || mapVo.get("statistic_name") == null || "".equals(mapVo.get("statistic_name")) ){
			return "{\"warn\":\"统计表名称不允许为空!\"}";
		}
		try {
			return hrStatisticCustomService.saveHrStatisticCustomSet(mapVo);
		} catch (Exception e) {
			return getErrorJson(e.getMessage());
		}
	}

	/**
	 * 简单统计设置表列查询
	 * 
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrStatisticSetTab", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrStatisticSetTab(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			if (mapVo.get("statistic_code") != null && StringUtils.isNotBlank(mapVo.get("statistic_code").toString())) {
				initParam(mapVo);
				return hrStatisticTabStrucService.queryHrStatisticSetTab(mapVo);
			}
			return null;
		} catch (Exception e) {
			return getErrorJson(e.getMessage());
		}
	}

	/**
	 * 简单统计设置条件查询
	 * 
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrStatisticSetCondition", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrStatisticSetCondition(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			if (mapVo.get("statistic_code") != null && StringUtils.isNotBlank(mapVo.get("statistic_code").toString())) {
				initParam(mapVo);
				return hrStatisticConditionService.queryHrStatisticSetCondition(mapVo);
			}
			return null;
		} catch (Exception e) {
			return getErrorJson(e.getMessage());
		}
	}

	/**
	 * 简单统计设置删除业务
	 * 
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrStatisticCustomSet", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrStatisticCustomSet(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			initParam(mapVo);
			String reJson = hrStatisticCustomService.deleteHrStatisticCustomSet(mapVo);
			return reJson;
		} catch (Exception e) {
			return getErrorJson(e.getMessage());
		}
	}

	

	/**
	 * 简单统计查询页面form
	 * 
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrStatisticCustomQueForm", method = RequestMethod.GET)
	public String queryHrStatisticCustomQueForm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		initParam(mapVo);

		if (mapVo.get("tab_code") == null || StringUtils.isBlank(mapVo.get("tab_code").toString())) {
			mapVo.put("tab_code", HR_EMP_TABLE_NAME);
		}

		int colNum = Integer.parseInt(MyConfig.getSysPara("06002"));

		String fromEle = hrCommonService.queryHrStatisticCustomQueForm(mapVo, colNum);
		mode.addAttribute("formEle", fromEle);

		return "hrp/hr/record/emp/hrStatisticCustomSearch";

	}

	// 附件上传功能
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fileUpload(MultipartFile file, Model mode, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> reMap = new HashMap<String, Object>();

		String path = null;// 文件路径
		String url = null;
		String fileExt = null;// 文件类型
		String fileName = null;

		if (file != null) {
			fileName = file.getOriginalFilename();// 文件原名称
			fileExt = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()): null;// 文件类型
			if (fileExt != null) {
				String basePath = "upLoad/"+SessionManager.getGroupId()+"/"+SessionManager.getHosId()+"/hr/file/";
				// 文件保存目录路径
				String realPath = request.getSession().getServletContext().getRealPath("/") + "/" + basePath;
				// 文件保存目录url
				String saveUrl = request.getContextPath() + "/" + basePath;
				// 自定义的文件名称
				String trueFileName = String.valueOf(System.currentTimeMillis()) + "." + fileExt;
				// 设置存放图片文件的路径
				path = realPath + trueFileName;
				url = saveUrl + trueFileName;

				File saveDirFile = new File(realPath);
				if (!saveDirFile.exists()) {
					saveDirFile.mkdirs();
				}
				// 转存文件到指定的路径
				file.transferTo(new File(path));
			}
		}

		reMap.put("fileName", fileName);
		reMap.put("url", url);

		return reMap;

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
	@RequestMapping(value = "/importFromDate", method = RequestMethod.POST)
	@ResponseBody
	public String importFromDate(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		String reJson;
		try {
			initParam(mapVo);
			reJson = hrCommonService.importFromDate(mapVo);
			return reJson;
		} catch (Exception e) {
			return getErrorJson(e.getMessage());
		}
	}
	
	
	/**
	 * 导入功能动态表头
	 * 
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryExcelColumn", method = RequestMethod.POST)
	@ResponseBody
	public String queryExcelColumn(@RequestParam Map<String, Object> mapVo) throws Exception {
		initParam(mapVo);
		String formEle = hrCommonService.queryExcelColumn(mapVo);
		
		return formEle;
	}
	
	
	/**
	 * @Description 跳转简单统计表页面
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrStatisticCustomMainPage", method = RequestMethod.GET)
	public String hrStatisticCustomMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("store_type_code") == null || mapVo.get("store_type_code") == "") {return "{\"error\":\"请选择档案库 ！\"}";}
		
		mode.addAttribute("store_type_code", mapVo.get("store_type_code"));
		
		return "hrp/hr/record/emp/hrStatisticCustomMain";
	}
	
	/**
	 * @Description 获取简单统计表头
	 * @param mapVo {档案库分类(store_type_code),(statistic_code)}
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrStatisticCustomHead", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrStatisticCustomHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("store_type_code") == null || mapVo.get("store_type_code") == "") {return "{\"error\":\"请选择档案库 ！\"}";}
		
		if (mapVo.get("statistic_code") == null || mapVo.get("statistic_code") == "") {return "{\"error\":\"请选择 左侧树！\"}";}
		
		if("-1".equals(mapVo.get("statistic_code"))){
			
			return "";
			
		}
		
		initParam(mapVo);// 初始化参数
		
		try {
			if(MyConfig.getSysPara("06005").toString().equals("1")) {
				mapVo.put("is_read",1);
			}else {
				mapVo.put("is_read","");
			}
			return hrCommonService.queryHrStatisticCustomHead(mapVo);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}
	
	/**
	 * 根据设置查询简单统计
	 * 
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrStatisticCustomGrid", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrStatisticCustomGrid(@RequestParam Map<String, Object> mapVo) throws Exception {
		
		if (mapVo.get("tmpSQL") == null || mapVo.get("tmpSQL") == "") {return "{\"error\":\"数据查询错误\"}";}
		
		initParam(mapVo);// 初始化参数
		
		try {
			
			return hrCommonService.queryHrStatisticCustom(getPage(mapVo));
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\""+e.getMessage()+"\"}";
		}

	}
	/**
	 * 下拉框外部引用
	 * 
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrHosSelectCite", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrHosSelectCite(@RequestParam Map<String, Object> mapVo) throws Exception {
		
	
		
		initParam(mapVo);// 初始化参数
		
		try {
			
			return hrCommonService.queryHrHosSelectCite(getPage(mapVo));
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\""+e.getMessage()+"\"}";
		}

	}
	/**
	 * 下拉框内置查询
	 * 
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrHosSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrHosSelect(@RequestParam Map<String, Object> mapVo) throws Exception {
		
	
		
		initParam(mapVo);// 初始化参数
		
		try {
			
			return hrCommonService.queryHrHosSelect(getPage(mapVo));
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\""+e.getMessage()+"\"}";
		}

	}
	/**
	 * 查询人员按部门下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHosEmpSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());

		String json = hrSelectService.queryEmpSelect(mapVo);
		return json;

	}
}
