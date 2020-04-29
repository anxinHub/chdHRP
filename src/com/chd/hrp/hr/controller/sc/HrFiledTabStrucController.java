package com.chd.hrp.hr.controller.sc;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.SqlMapper;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.hrp.hr.dao.sc.HrFiledDataMapper;
import com.chd.hrp.hr.entity.sc.HrFiledData;
import com.chd.hrp.hr.entity.sc.HrFiledTabStruc;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
import com.chd.hrp.hr.service.sc.HrFiledTabStrucService;
import com.chd.hrp.hr.util.FileUtil;

/**
 * 
 * @ClassName: HrFiledTabStrucController
 * @Description: 代码表构建
 * @author zn
 * @date 2017年10月17日 下午2:47:13
 * 
 *
 */

@Controller
@RequestMapping(value = "/hrp/hr/sc/hrfiledtabstruc")
public class HrFiledTabStrucController extends BaseController {

	private static Logger logger = Logger.getLogger(HrFiledTabStrucController.class);

	// 引入Service服务
	@Resource(name = "hrFiledTabStrucService")
	private final HrFiledTabStrucService hrFiledTabStrucService = null;
	
	@Resource(name = "hrFiledDataMapper")
	private final HrFiledDataMapper hrFiledDataMapper = null;
	
/*	@Resource(name = "hrfiledViewService")
	private final HrfiledViewService hrfiledViewService = null;
*/
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiledTabStrucMainPage", method = RequestMethod.GET)
	public String hrFiledTabStrucMainPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrfiledtabstruc/hrFiledTabStrucMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiledTabStrucAddPage", method = RequestMethod.GET)
	public String hrFiledTabStrucAddPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrfiledtabstruc/hrFiledTabStrucAdd";
	}

	/**
	 * @Description 学科分类查询页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiledTabsubjectMainPage", method = RequestMethod.GET)
	public String hrfiledTabsubjectMainPage(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		//HrFiledTabStruc hrFiledTabStruc = hrFiledTabStrucService.queryByCode(entityMap);
	/*	mode.addAttribute("field_tab_code", hrFiledTabStruc.getField_tab_code());
		mode.addAttribute("field_tab_name", hrFiledTabStruc.getField_tab_name());*/
		return "hrp/hr/sc/hrfiledtabstruc/hrfiledTabsubjectMain";
	}

	/**
	 * @Description 更新页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiledTabStrucUpdatePage", method = RequestMethod.GET)
	public String hrFiledTabStrucUpdatePage(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		HrFiledTabStruc hrFiledTabStruc = hrFiledTabStrucService.queryByCode(entityMap);
		mode.addAttribute("field_tab_code", hrFiledTabStruc.getField_tab_code());
		mode.addAttribute("field_tab_name", hrFiledTabStruc.getField_tab_name());
		mode.addAttribute("type_tab_code", hrFiledTabStruc.getType_tab_code());
		mode.addAttribute("is_innr", hrFiledTabStruc.getIs_innr());
		mode.addAttribute("is_stop", hrFiledTabStruc.getIs_stop());
		mode.addAttribute("is_cite", hrFiledTabStruc.getIs_cite());
		mode.addAttribute("note", hrFiledTabStruc.getNote());
		return "hrp/hr/sc/hrfiledtabstruc/hrFiledTabStrucUpdate";
	}

	/**
	 * @Description 导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPage", method = RequestMethod.GET)
	public String importPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrfiledtabstruc/import";
	}
	
	
	
	/**
	 * 导入功能
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/readFiles", method = RequestMethod.POST)
	public String readFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws Exception {
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		hrFiledTabStrucService.readJsonFiles(plupload, request, response, mode, entityMap);
		return null;
	}
	
	
	
	/**
	 * 导出功能
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public String export(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		if(request.getParameter("type_tab_code") != null){
			entityMap.put("type_tab_code", request.getParameter("type_tab_code"));
		}else{
			entityMap.put("field_tab_code", request.getParameter("field_tab_code"));
		}
		
		String tabName[] = request.getParameter("file_name").toString().split("【");
		
		String fileName = tabName != null && StringUtils.isNotEmpty(tabName[0]) ? "代码表构建_" + tabName[0] : "代码表构建";
		
		String json = hrFiledTabStrucService.queryHrFiledTabStrucExport(entityMap);
		
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("UTF-8");
		byte[] buff = new byte[1024];
		BufferedInputStream bis = null;
		OutputStream os = null;

		try {
			long fileLength = json.getBytes().length;
			fileName += ".json";
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename=" 
					+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			
			response.setHeader("Content-Length", String.valueOf(fileLength));
			
			os = response.getOutputStream();
			bis = new BufferedInputStream(new ByteArrayInputStream(json.getBytes()));
			int i = bis.read(buff);
			 
			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (os != null)
				os.close();
		}
		return null;
	}
	
	/**
	 * @Description 根据treeId查询代码项数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrFiledData", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrfiledData(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		String hrfiledData =hrFiledTabStrucService.queryHrFiledData(entityMap);

		return hrfiledData;
	}

	/**
	 * @Description 根据treeId查询数据表数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryColAndTabName", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryColAndTabName(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		String hrfiledData = hrFiledTabStrucService.queryColAndTabName(entityMap);

		return hrfiledData;
	}

	/**
	 * @Description 查询页面tree
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrFiledTabStrucTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrFiledTabStrucTree(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		String hrFiledTabStrucTree = hrFiledTabStrucService.queryHrFiledTabStrucTree(entityMap);

		return hrFiledTabStrucTree;
	}

	/**
	 * @Description 添加数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiledTabStrucAdd", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String hrFiledTabStrucAdd(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());

		return hrFiledTabStrucService.add(entityMap);
	}

	/**
	 * @Description 删除数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/deleteHrFiledData", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteHrfiledData(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		List<Map> listVo = JSONArray.parseArray(entityMap.get("ParamVo").toString(), Map.class);
		for (Map map : listVo) {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
		}

		return hrFiledTabStrucService.deleteHrFiledData(listVo);
	}

	/**
	 * @Description 添加数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveHrFiledData", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String saveHrfiledData(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {

		try {
			return hrFiledTabStrucService.saveHrFiledData(entityMap);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}

	/**
	 * @Description 添加数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiledTabStrucUpdate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String hrFiledTabStrucUpdate(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		return hrFiledTabStrucService.update(entityMap);
	}

	/**
	 * @Description 删除数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiledTabStrucDelete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String hrFiledTabStrucDelete(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		return hrFiledTabStrucService.delete(entityMap);
	}

	/**
	 * 设置外部引用SQL页面
	 * 
	 * @param entityMap
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/hrFiledViewAddPage", method = RequestMethod.GET)
	public String hrfiledViewAddPage(@RequestParam Map<String, Object> entityMap, Model mode) {
		mode.addAllAttributes(entityMap);
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		HrFiledTabStruc hrfiledView = hrFiledTabStrucService.queryHrFiledTabStruc(entityMap);
		if(hrfiledView != null){
			mode.addAttribute("related_sql", hrfiledView.getRelated_sql());
			mode.addAttribute("query_sql", hrfiledView.getQuery_sql());
			mode.addAttribute("cite_json", hrfiledView.getCite_json());
			mode.addAttribute("field_tab_code", hrfiledView.getField_tab_code());
			String related_sql =  hrfiledView.getRelated_sql();
			if(related_sql != "null" && !" ".equals(related_sql) && related_sql !=  null){
				mode.addAttribute("tab_code",related_sql.substring(related_sql.indexOf("from")+4,related_sql.indexOf("where")).replaceAll("\n|\t|\r|\\s{1,}", " ").replaceAll(" ", ""));
			}
			
		}
		return "hrp/hr/sc/hrfiledtabstruc/hrFiledViewAdd";
	}

	/**
	 * @Description 设置外部引用保存
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrFiledTabStruc", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String updateHrFiledTabStruc(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		return hrFiledTabStrucService.updateFiledTabStrucSQL(entityMap);
	}
	
	/**
	 * @Description 查询数据 数据表字段
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryFiledColsByCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryFiledColsByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrFiledTabStrucService.queryFiledColsByCode(mapVo);
	}
	
	/**
	 * 设置外部引用SQL业务
	 * 
	 * @param entityMap
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/saveHrfiledView", method = RequestMethod.POST)
	@ResponseBody
	public String saveHrfiledView(@RequestParam Map<String, Object> entityMap, Model mode) {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		try {
			return hrFiledTabStrucService.updateHrfiledView(entityMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importDateHFTS", method = RequestMethod.POST)
	@ResponseBody
	public String importDateHFTS(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrFiledTabStrucService.importDate(mapVo);
		return reJson;
	}
	/**
	 * 
	 */
	/**
	 * 代码分类HR_FIIED_TAB_TYPE
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryFiledTypeSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryTypeFiledTypeSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrFiledTabStrucService.queryFiledTypeSelect(mapVo);
		return json;

	}
	
	/**
	 * @Description 导入旧版本 代码构建表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/importOldData", method = RequestMethod.POST)
	@ResponseBody
	public String importOldData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrFiledTabStrucService.copyHrTableStrucByOld(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}
	
	/**
	 * @Description 查询数据 数据库所有表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDBTableTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryDBTableTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		return hrFiledTabStrucService.queryDBTableTree(mapVo);
	}
}
