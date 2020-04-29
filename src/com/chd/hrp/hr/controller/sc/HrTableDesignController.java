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
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.hrp.hr.entity.sc.HrTableDesign;
import com.chd.hrp.hr.service.sc.HrTableDesignService;
import com.chd.hrp.hr.service.sc.HrTableStrucService;
import com.chd.hrp.hr.util.FileUtil;
import com.chd.hrp.hr.util.StringUtils;

/**
 * 
 * @ClassName: HrTableDesignController
 * @Description: 查询设计器
 * @author zn
 * @date 
 * 
 *
 */

@Controller
@RequestMapping(value = "/hrp/hr/sc/hrtabledesign")
public class HrTableDesignController extends BaseController {

	//private static Logger logger = Logger.getLogger(HrTableDesignController.class);

	// 引入Service服务
	@Resource(name = "hrTableDesignService")
	private final HrTableDesignService hrTableDesignService = null;
	
	@Resource(name = "hrTableStrucService")
	private final HrTableStrucService hrTableStrucService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTableDesignMainPage", method = RequestMethod.GET)
	public String hrTableDesignMainPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrtabledesign/hrTableDesignMain";
	}
	
	/**
	 * @Description 添加查询设计器页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTableDesignAddPage", method = RequestMethod.GET)
	public String hrTableDesignAddPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrtabledesign/hrTableDesignAdd";
	}

	/**
	 * @Description 更新查询设计器页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTableDesignUpdatePage", method = RequestMethod.GET)
	public String hrTableDesignUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		HrTableDesign hrTableDesign = hrTableDesignService.queryHrTableDesignByCode(mapVo);
		
		if(hrTableDesign != null){
			mode.addAttribute("design", hrTableDesign);
		}
		
		return "hrp/hr/sc/hrtabledesign/hrTableDesignUpdate";
	}
	
	/**
	 * @Description 添加数据表页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrDataTableAddPage", method = RequestMethod.GET)
	public String hrDataTableAddPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrtabledesign/hrDataTableAdd";
	}
	
	/**
	 * @Description 添加数据项页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrDataItemAddPage", method = RequestMethod.GET)
	public String hrDataItemAddPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrtabledesign/hrDataItemAdd";
	}
	
	/**
	 * @Description 选择数据表页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrChooseTablePage", method = RequestMethod.GET)
	public String hrChooseTablePage(Model mode) throws Exception {
		return "hrp/hr/sc/hrtabledesign/hrChooseTable";
	}
	
	/**
	 * @Description 设置页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrDesignQuerySetPage", method = RequestMethod.GET)
	public String hrDesignQuerySetPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrtabledesign/hrDesignQuerySet";
	}
	
	/**
	 * @Description 导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPage", method = RequestMethod.GET)
	public String importPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrtabledesign/import";
	}
	
	/**
	 * @Description 查询数据 数据表字段
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrTableCol", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrTableCol(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrTableStrucService.queryTabColsByCode(mapVo);
	}
	
	/**
	 * @Description 查询数据 数据表字段(用于表格)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrTableColByCodes", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrTableColByCodes(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrTableStrucService.queryHrTableColByCodes(mapVo);
	}
	
	/**
	 * @Description 查询数据 数据表字段(用于列表ligerListBox)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrTableColByCodes2", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrTableColByCodes2(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrTableStrucService.queryHrTableColByCodes2(mapVo);
	}
	
	/**
	 * @Description 添加数据 查询设计器
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrTableDesign", method = RequestMethod.POST)
	@ResponseBody
	public String addHrTableDesign(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			String design_code = mapVo.get("design_code").toString();
			if(design_code.length() > 30){
				return "{\"error\":\"数据表编码长度最大值为30个字符\"}";
			}
			return hrTableDesignService.addHrTableDesign(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}

	/**
	 * @Description 更新数据  查询设计器
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrTableDesign", method = RequestMethod.POST)
	@ResponseBody
	public String updateHrTableDesign(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrTableDesignService.updateHrTableDesign(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * @Description 删除数据 查询设计器
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrTableDesign", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrTableDesign(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			//判断数据表是否被引用
			//String str = sysBaseService.isExistsDataByTable("cost_charge_item_arrt", mapVo.get("tab_code"));
			//if(Strings.isNotBlank(str)){
			//	return "{\"error\":\"删除失败，选择的收费项目被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
			//}
			return hrTableDesignService.deleteHrTableDesign(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * @Description 查询数据(树型菜单) 设计器
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrTableDesignTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrTableDesignTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrTableDesignService.queryHrTableDesignTree(mapVo);
	}
	
	/**
	 * @Description 生成数据表SQL
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/generateSqlStatement", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> generateSqlStatement(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrTableDesignService.generateSqlStatement(mapVo);
		} catch (Exception e) {
			Map<String, String> error = new HashMap<String, String>();
			error.put("error", e.getMessage());
			return error;
		}
		
	}
	
	/**
	 * @Description 生成页面显示设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/genDesignQueryPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> genDesignQueryPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrTableDesignService.genDesignQueryPage(mapVo);
		} catch (Exception e) {
			Map<String, Object> error = new HashMap<String, Object>();
			error.put("error", e.getMessage());
			return error;
		}
		
	}
	
	/**
	 * @Description 保存数据 查询设计器
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveDesignQueryCol", method = RequestMethod.POST)
	@ResponseBody
	public String saveDesignQueryCol(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrTableDesignService.updateDesignQueryColByCode(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}
	
	/**
	 * @Description 保存数据 生成的SQL
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveDesignQuerySql", method = RequestMethod.POST)
	@ResponseBody
	public String saveDesignQuerySql(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrTableDesignService.updateDesignQuerySqlByCode(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}
	
	/**
	 * @Description 保存数据 生成的SQL
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveDesignQueryPage", method = RequestMethod.POST)
	@ResponseBody
	public String saveDesignQueryPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrTableDesignService.updateDesignQueryPageByCode(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}
	
	/**
	 * @Description 查询数据 查询设计器
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDesignQueryCol", method = RequestMethod.POST)
	@ResponseBody
	public String queryDesignQueryCol(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrTableDesignService.queryDesignQueryColByCode(mapVo);
	}
	
	/**
	 * @Description 查询数据 查询设计器列设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDesignQueryPage", method = RequestMethod.POST)
	@ResponseBody
	public String queryDesignQueryPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrTableDesignService.queryDesignQueryPageByCode(mapVo);
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
		
		List<HrTableDesign> list_err = new ArrayList<HrTableDesign>();
		int succesCount = 0;
		int errorCount = 0;
		
		try {
			plupload.setRequest(request);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) plupload.getRequest();   
	        MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();  
	        if(map != null) {
	        	Iterator<String> iter = map.keySet().iterator();  
	            while(iter.hasNext()) {  
	                String str = (String) iter.next();  
	                List<MultipartFile> fileList =  map.get(str); 
	                for(MultipartFile multipartFile : fileList) {  
	                	StringBuffer strBuf = new StringBuffer();
	                	String charset = FileUtil.codeString(multipartFile.getInputStream());
	                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), charset));
	                    int tempchar;
	                    while ((tempchar = bufferedReader.read()) != -1) {
	                        strBuf.append((char) tempchar);
	                    }
	                    bufferedReader.close();
	                    String json = strBuf.toString();
                		
                		Object object = JSON.parse(json);
                		if (!(object instanceof JSONArray)) {
                			response.getWriter().print("{\"error\":\"数据格试错误,要求JSONArray格式\"}");
                			return null;
                		}
                		
                		List<HrTableDesign> list = JSONArray.parseArray(json, HrTableDesign.class);
                		
                		succesCount += list.size();
                		
                		/*Set<String> isExist = new HashSet<String>();
                		for (HrTableDesign hrTableDesign : list) {
                			isExist.add(hrTableDesign.getDesign_code());
						}*/
                		
                		for (HrTableDesign hrTableDesign : list) {
                			
                			StringBuffer err_sb = new StringBuffer();
                			
                			if(hrTableDesign.getDesign_code() == null || StringUtils.isEmpty(hrTableDesign.getDesign_code())){
                				err_sb.append("编码不能为空！");
                			}
                			
                			if(hrTableDesign.getDesign_name() == null || StringUtils.isEmpty(hrTableDesign.getDesign_name())){
                				err_sb.append("名称不能为空！");
                			}
                			
                			entityMap.put("design_code", hrTableDesign.getDesign_code());
                			HrTableDesign design = hrTableDesignService.queryHrTableDesignByCode(entityMap);
                			if(design != null){
                				err_sb.append("编码" + hrTableDesign.getDesign_code() + "已存在！");
                			}
                			
                			/*if(hrTableDesign.getSuper_code() != null && !"0".equals(hrTableDesign.getSuper_code())){
                				entityMap.put("design_code", hrTableDesign.getSuper_code());
                    			HrTableDesign superDesign = hrTableDesignService.queryHrTableDesignByCode(entityMap);
                    			if(superDesign == null && !isExist.contains(hrTableDesign.getSuper_code())){
                    				err_sb.append("上级编码" + hrTableDesign.getSuper_code() + "不存在！");
                    			}
                			}*/
                			
                			if(err_sb.length() > 0){
                				hrTableDesign.setError_type(err_sb.toString());
                				list_err.add(hrTableDesign);
                				succesCount--;
                				errorCount++;
                			}
                			
                			hrTableDesign.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
                			hrTableDesign.setHos_id(Integer.parseInt(SessionManager.getHosId()));
	                    }
                		
                		//if(list_err.size() == 0){
                			hrTableDesignService.addBatchTableDesign(list);
                		//}
	                }
	            }
	        }
			
		} catch (Exception e) {

		}
		
		response.getWriter().print("{succesCount: "+succesCount+", errorCount: "+errorCount+", errorRows: "+ChdJson.toJson(list_err, list_err.size())+"}");

		return null;
	}
	
	/**
	 * 导入功能
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addImportData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addImportData(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
		entityMap.put("super_code", request.getParameter("design_code"));
		
		String designName = request.getParameter("design_name");
		String fileName = designName != null && StringUtils.isNotEmpty(designName) ? "查询设计器_" + designName : "查询设计器";
		
		String json = hrTableDesignService.queryHrTableDesignExport(entityMap);
		
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
		return hrTableDesignService.queryDBTableTree(mapVo);
	}
	
	/**
	 * 简单统计设置页面
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrSimpleStatisticsSetForm", method = RequestMethod.GET)
	public String hrSimpleStatisticsSetForm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		HrTableDesign design = hrTableDesignService.queryHrTableDesignByCode(mapVo);
		if(design == null){
			design = new HrTableDesign();
			design.setDesign_query_col("{\"tableData\":[],\"conditionData\":[],\"groupData\":[],\"sortData\":[]}");
		}
		mode.addAttribute("design", design);
		return "hrp/hr/pf/pfs/hrSimpleStatisticsSetForm";
	}
	
	/**
	 * @Description 查询数据(树型菜单) 数据表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrStatisticTableTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrStatisticTableTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrTableDesignService.queryHrStatisticTableTree(mapVo);
	}
	
	/**
	 * @Description 保存数据 简单统计设计器
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveHrStatisticDesign", method = RequestMethod.POST)
	@ResponseBody
	public String saveHrStatisticDesign(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("super_code", "pfs");
		mapVo.put("is_stop", "0");
		
		try {
			return hrTableDesignService.saveHrStatisticDesign(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * @Description 删除数据 简单统计设计器
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrStatisticDesign", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrStatisticDesign(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrTableDesignService.deleteHrTableDesign(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	

}
