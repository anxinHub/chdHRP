package com.chd.hrp.hr.controller.sc;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.Plupload;
import com.chd.hrp.hr.entity.sc.HrPageDesign;
import com.chd.hrp.hr.service.sc.HrPageDesignService;
import com.chd.hrp.hr.util.StringUtils;

/**
 * 
 * @ClassName: HrTableDesignController
 * @Description: 页面设计器
 * @author zn
 * @date 
 * 
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/sc/hrpagedesign")
public class HrPageDesignController extends BaseController {

	private static Logger logger = Logger.getLogger(HrPageDesignController.class);
	
	// 引入Service服务
	@Resource(name = "hrPageDesignService")
	private final HrPageDesignService hrPageDesignService = null;
	
	/**
	 * 
	* @Title: hrTableDesignMainPage
	* @Description: main页面跳转
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年3月18日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrPageDesignMainPage", method = RequestMethod.GET)
	public String hrPageDesignMainPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrpagedesign/hrPageDesignMain";
	}
	
	/**
	 * @Description 树形查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrPageDesignTree", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrPageDesignTree(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		 mapVo.put("group_id", SessionManager.getGroupId());
		 mapVo.put("hos_id", SessionManager.getHosId());
		 return  hrPageDesignService.queryHrPageDesignTree(mapVo);
	}
	
	/**
	 * 
	* @Title: hrPageDesignAddPage
	* @Description: 添加页面
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年3月18日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrPageDesignAddPage", method = RequestMethod.GET)
	public String hrPageDesignAddPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrpagedesign/hrPageDesignAdd";
	}
	
	/**
	 * @Description 添加
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrPageDesign", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrPageDesign(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
    
		String hrPageDesignJson;
		
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			hrPageDesignJson = hrPageDesignService.addHrPageDesign(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			hrPageDesignJson = e.getMessage();
		}
		
		return JSONObject.parseObject(hrPageDesignJson);
	}
	
	
	/**
	 * 
	* @Title: hrPageDesignUpdatePage
	* @Description: 修改页面
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年3月18日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrPageDesignUpdatePage", method = RequestMethod.GET)
	public String hrPageDesignUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		if((null != mapVo.get("table_type_code")||"".equals(mapVo.get("table_type_code"))) && (null !=mapVo.get("page_code")||"".equals(mapVo.get("page_code")))){
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			HrPageDesign hrPageDesign = hrPageDesignService.queryHrPageDesignByCode(mapVo);
			mode.addAttribute("hrPageDesign", hrPageDesign);
		}
		return "hrp/hr/sc/hrpagedesign/hrPageDesignUpdate";
	}
	
	
	/**
	 * @Description 修改
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrPageDesign", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHrPageDesign(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
    
		String hrPageDesignJson;
		
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			hrPageDesignJson = hrPageDesignService.updateHrPageDesign(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			hrPageDesignJson = e.getMessage();
		}
		
		return JSONObject.parseObject(hrPageDesignJson);
	}
	
	
	/**
	 * @Description 删除数据 页面设计器
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrPageDesign", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrPageDesign(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String hrPageDesignJson;
		try {
			
			hrPageDesignJson =  hrPageDesignService.deleteHrPageDesign(mapVo);
			
		} catch (Exception e) {
			hrPageDesignJson = e.getMessage();
		}
		return hrPageDesignJson;
	}
	
	/**
	 * @Description 导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrPageDesignImportPage", method = RequestMethod.GET)
	public String hrPageDesignImportPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrpagedesign/hrPageDesignImport";
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
	@RequestMapping(value = "/exportHrPageDesign", method = RequestMethod.GET)
	public String exportHrPageDesign(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("table_type_code", request.getParameter("table_type_code"));
		entityMap.put("page_code", request.getParameter("page_code"));
		
		String page_name = request.getParameter("page_name");
		String fileName = page_name != null && StringUtils.isNotEmpty(page_name) ? "页面设计器_" + page_name : "页面设计器";
		
		String json = hrPageDesignService.queryHrPageDesignExport(entityMap);
		
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
	 * 
	* @Title: hrPageDesignTreeElemMainPage
	* @Description: 树形选择按钮页面跳转
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年3月18日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrPageDesignTreeElemMainPage", method = RequestMethod.GET)
	public String hrPageDesignTreeElemMainPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrpagedesign/hrPageDesignTreeElemMain";
	}
	
	/**
	 * 
	* @Title: hrPageDesignGridElemMainPage
	* @Description: 表格选择按钮页面跳转
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年3月18日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrPageDesignGridElemMainPage", method = RequestMethod.GET)
	public String hrPageDesignGridElemMainPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrpagedesign/hrPageDesignGridElemMain";
	}
	
	/**
	 * 
	* @Title: hrPageDesignEventElemMainPage
	* @Description: 事件选择按钮页面跳转
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年3月18日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrPageDesignEventElemMainPage", method = RequestMethod.GET)
	public String hrPageDesignEventElemMainPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrpagedesign/hrPageDesignEventElemMain";
	}
	
	/**createHrPageDesignJsp
	 * @Description 生成JSP文件
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveHrPageDesign", method = RequestMethod.POST)
	@ResponseBody
	public String saveHrPageDesign(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 mapVo.put("group_id", SessionManager.getGroupId());
		 mapVo.put("hos_id", SessionManager.getHosId());
		 String hrPageDesignJson;
		try {
			 hrPageDesignJson = hrPageDesignService.saveHrPageDesign(mapVo);
			
		} catch (Exception e) {
			hrPageDesignJson = e.getMessage();
		}
		 return hrPageDesignJson;
	}
	
}
