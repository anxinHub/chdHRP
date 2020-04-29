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
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
import com.chd.hrp.hr.service.sc.HrTableStrucService;
import com.chd.hrp.hr.util.FileUtil;
import com.chd.hrp.sys.service.base.SysBaseService;

/**
 * 
 * @ClassName: HrTableStrucController
 * @Description: 数据表构建
 * @author zn
 * @date 2020年02月10日
 * 
 *
 */

@Controller
@RequestMapping(value = "/hrp/hr/sc/hrtablestruc")
public class HrTableStrucController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrTableStrucController.class);

	// 引入Service服务
	@Resource(name = "hrTableStrucService")
	private final HrTableStrucService hrTableStrucService = null;
	
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTableStrucMainPage", method = RequestMethod.GET)
	public String hrTableStrucMainPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrtablestruc/hrTableStrucMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTableStrucAddPage", method = RequestMethod.GET)
	public String hrTableStrucAddPage(Model mode) throws Exception {
		String tab_code = hrTableStrucService.queryHrTabCodeSeq();
		mode.addAttribute("tab_code",tab_code);
		return "hrp/hr/sc/hrtablestruc/hrTableStrucAdd";
	}

	/**
	 * @Description 更新页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTableStrucUpdatePage", method = RequestMethod.GET)
	public String hrTableStrucUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		HrTableStruc hrTableStruc = hrTableStrucService.queryHrTableStrucByCode(mapVo);
		
		if(hrTableStruc != null){
			mode.addAttribute("group_id", hrTableStruc.getGroup_id());
			mode.addAttribute("hos_id", hrTableStruc.getHos_id());
			//mode.addAttribute("copy_code", hrTableStruc.getCopy_code());
			mode.addAttribute("tab_code", hrTableStruc.getTab_code());
			mode.addAttribute("tab_name", hrTableStruc.getTab_name());
			mode.addAttribute("type_tab_code", hrTableStruc.getType_tab_code());
			mode.addAttribute("is_innr", hrTableStruc.getIs_innr());
			mode.addAttribute("note", hrTableStruc.getNote());
		}
		
		return "hrp/hr/sc/hrtablestruc/hrTableStrucUpdate";
	}
	
	/**
	 * @Description 查询数据 系统构建表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrTableStruc", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrTableStrucs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrTableStrucService.queryHrTableStruc(mapVo);
	}
	
	/**
	 * @Description 添加数据 系统构建表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrTableStruc", method = RequestMethod.POST)
	@ResponseBody
	public String addHrTableStruc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			String tab_code = mapVo.get("tab_code").toString();
			if(tab_code.length() > 30){
				return "{\"error\":\"数据表编码长度最大值为30个字符\"}";
			}
			return hrTableStrucService.addHrTableStruc(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}

	/**
	 * @Description 更新数据  系统构建表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrTableStruc", method = RequestMethod.POST)
	@ResponseBody
	public String updateHrTableStruc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrTableStrucService.updateHrTableStruc(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * @Description 更新数据  系统构建表 是否停用
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrTableStrucIsStop", method = RequestMethod.POST)
	@ResponseBody
	public String updateHrTableStrucIsStop(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrTableStrucService.updateHrTableStrucIsStop(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}

	/**
	 * @Description 删除数据 系统构建表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrTableStruc", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrTableStruc(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			//判断数据表是否被引用
			//String str = sysBaseService.isExistsDataByTable("cost_charge_item_arrt", mapVo.get("tab_code"));
			//if(Strings.isNotBlank(str)){
			//	return "{\"error\":\"删除失败，选择的收费项目被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
			//}
			return hrTableStrucService.deleteHrTableStruc(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * @Description 查询数据(左侧树型菜单) 系统构建表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrTableStrucTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrTableStrucsTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrTableStrucService.queryHrTableStrucTree(mapVo);
	}
	
	/**
	 * @Description 查询数据 数据表字段
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrColStruc", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrColStruc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrTableStrucService.queryTabColsByCode(mapVo);
	}
	
	/**
	 * @Description 添加数据 数据表字段
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveHrColStruc", method = RequestMethod.POST)
	@ResponseBody
	public String saveHrColStruc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrTableStrucService.updateTabColByTabCode(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}
	
	/**
	 * @Description 创建表结构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/createTableStruc", method = RequestMethod.POST)
	@ResponseBody
	public String createTableStruc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrTableStrucService.createTableStruc(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}
	
	/**
	 * @Description 修改表结构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/alterTableStruc", method = RequestMethod.POST)
	@ResponseBody
	public String alterTableStruc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrTableStrucService.alterTableStruc(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}
	
	/**
	 * @Description 删除数据 数据表字段
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrColStruc", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrColStruc(@RequestParam Map<String,Object> mapVo, Model mode)throws Exception {
		
		if(mapVo == null || mapVo.get("tab_code") == null || StringUtils.isBlank(mapVo.get("tab_code").toString())){
			return "{\"warn\":\"参数不完整\"}";
		}
		
		//表信息
		//String tab_code = mapVo.get("tab_code").toString();
		
		//列信息
		//List<HrColStruc> listVo = JSONArray.parseArray(mapVo.get("paramVo").toString(), HrColStruc.class);
		
		try {
			//return hrColStrucService.deleteBatchHrColStruc(tab_code,listVo);
			return null;
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
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
	public String generateSqlStatement(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrTableStrucService.generateSqlStatement(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}
	
	/**
	 * @Description 保存数据 数据表SQL
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSqlStatement", method = RequestMethod.POST)
	@ResponseBody
	public String saveSqlStatement(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrTableStrucService.saveSqlStatement(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}
	
	/**
	 * @Description 删除数据 数据表SQL
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSqlStatement", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSqlStatement(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrTableStrucService.deleteSqlStatement(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}
	
	/**
	 * @Description 查询数据 数据表SQL
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySqlStatement", method = RequestMethod.POST)
	@ResponseBody
	public String querySqlStatement(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrTableStrucService.queryTabSqlsByCode(mapVo);
	}
	
	/**
	 * @Description 查询数据 用于sql输入提示{表名:[列名,...]}
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrTableWords", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrTableWords(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrTableStrucService.queryHrTableWords(mapVo);
	}
	
	/**
	 * @Description 导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPage", method = RequestMethod.GET)
	public String importPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrtablestruc/import";
	}
	
	//导入
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	@ResponseBody
	public String importExcel(@RequestParam Map<String,Object> mapVo, Model mode)throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//String reJson = hrColStrucService.importExcel(mapVo);
		//return reJson;
		return null;
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
		
		List<HrTableStruc> list_err = new ArrayList<HrTableStruc>();
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
                		//String json = IoUtil.read(multipartFile.getInputStream());
	                    String json = strBuf.toString();
                		
                		Object object = JSON.parse(json);
                		if (!(object instanceof JSONArray)) {
                			response.getWriter().print("{\"error\":\"数据格试错误,要求JSONArray格式\"}");
                			return null;
                		}
                			
                		List<HrTableStruc> list = JSONArray.parseArray(json, HrTableStruc.class);
                		
                		succesCount += list.size();
                		
                		for (HrTableStruc hrTableStruc : list) {
                			
                			StringBuffer err_sb = new StringBuffer();
                			
                			entityMap.put("tab_code", hrTableStruc.getTab_code());
                			HrTableStruc struc = hrTableStrucService.queryHrTableStrucByCode(entityMap);
                			if(struc != null){
                				err_sb.append("编码" + hrTableStruc.getTab_code() + "已存在！");
                			}
                			
                			if(err_sb.length() > 0){
                				hrTableStruc.setError_type(err_sb.toString());
                				list_err.add(hrTableStruc);
                				succesCount--;
                				errorCount++;
                			}
                			
                			hrTableStruc.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
                    		hrTableStruc.setHos_id(Integer.parseInt(SessionManager.getHosId()));
	                    }
                		
                		//if(list_err.size() == 0){
                			hrTableStrucService.addBatchTableStruc(list);
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
	public String export(HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("tab_code", request.getParameter("tab_code"));
		
		String tabName = request.getParameter("tab_name");
		String fileName = tabName != null && StringUtils.isNotEmpty(tabName) ? "数据表构建_" + tabName : "数据表构建";
		
		String json = hrTableStrucService.queryHrTableStrucExport(entityMap);
		
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
	 * @Description 导入旧版本 系统构建表
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
			return hrTableStrucService.copyHrTableStrucByOld(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}
	
	/**
	 * @Description 查询数据 代码表下拉表格
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrFiledTableStruc", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrFiledTableStruc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrTableStrucService.queryHrFiledTableStruc(mapVo);
	}
	
}
