package com.chd.hrp.sys.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.startup.LoadChdInfo;
import com.chd.base.util.ChdJson;
import com.chd.base.util.FileUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.sys.service.MyPageOfficeService;
import com.zhuozhengsoft.pageoffice.DocumentOpenType;
import com.zhuozhengsoft.pageoffice.FileMakerCtrl;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OfficeVendorType;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PDFCtrl;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.excelwriter.Sheet;
import com.zhuozhengsoft.pageoffice.excelwriter.Workbook;
import com.zhuozhengsoft.pageoffice.wordwriter.WordDocument;

@Controller
@RequestMapping(value="/PageOffice")
public class MyPageOfficeController{
	
	private static Logger logger = Logger.getLogger(MyPageOfficeController.class);
	@Resource(name = "myPageOfficeService")
	private final MyPageOfficeService myPageOfficeService = null;
	
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	
	@RequestMapping(value = "/openFile",method = RequestMethod.GET)
	public String openFile(@RequestParam Map<String, Object> mapVo,Model mode,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String para="";
		if(mapVo!=null && mapVo.size()>0){
			for(Entry<String, Object> enty:mapVo.entrySet()){
				if(para.equals("")){
					para="?"+enty.getKey()+"="+enty.getValue();
				}
				para=para+"&"+enty.getKey()+"="+enty.getValue();
			}
		}
		
		String openWindow = "javascript:POBrowser.openWindow('PageOffice/printTemplate.do"+para+"', 'fullscreen=yes');";
		//String openWindow = PageOfficeLink.openWindow(request,"printTemplate.do"+para,"width=0;");
		
		mode.addAttribute("openWindow", openWindow);
		//request.setAttribute("openWindow", openWindow);
		return "PageOffice/openFile";
	}
	
	/************************************************************************************************************************* 
	 * 单据打印模板-处理数据文档（谷歌）
	 */
	@RequestMapping(value="/printFormSetData",method = RequestMethod.POST)
	@ResponseBody
    public Map<String,Object> printFormSetData(@RequestParam Map<String, Object> map,Model mode,HttpServletRequest request, HttpServletResponse response){
		
	
		if(map==null || map.get("template_code")==null || map.get("template_code").equals("")){
			return JSONObject.parseObject("{\"error\":\"没有参数[template_code]\"}");
		}
		
		String template_code=map.get("template_code").toString();
		String group_id=SessionManager.getGroupId();
		String hos_id=SessionManager.getHosId();
		String copy_code=SessionManager.getCopyCode();
		String mod_code=SessionManager.getModCode();
		map.put("group_id", group_id);
		map.put("hos_id", hos_id);
		map.put("copy_code", copy_code);
		map.put("mod_code", mod_code);
		
		String use_id="0";
		if(map.get("use_id")!=null && !map.get("use_id").equals("")){
			use_id=map.get("use_id").toString();
		}		
		map.put("use_id", use_id);
		
		//文件路径：集团/医院/账套/模块编码/模板编码_使用ID.xls
		String docPath="/PageOffice/doc/form/"+group_id+"/"+hos_id+"/"+copy_code+"/"+mod_code;
		ServletContext servletContext= request.getServletContext();
		
		//拷贝文件，目录不存在，创建整个目录 
		String fromFilePath=servletContext.getRealPath("/PageOffice/base/form/"+template_code+".xls");
		String toFilePath=servletContext.getRealPath(docPath+"/"+template_code+"_"+use_id+".xls");
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		try { 
			FileUtil.copyFile(fromFilePath,toFilePath);
			resMap.put("group_id", group_id);
			resMap.put("hos_id", hos_id);
			resMap.put("hos_copy", copy_code);
			resMap.put("mod_code", mod_code);
			resMap.put("template_code", template_code);
			resMap.put("use_id", use_id);
			resMap.put("docPath", docPath);
			
		}catch (Exception e) { 
			resMap.put("error", StringTool.string2Json(e.getMessage()==null?"生成数据异常！":e.getMessage()));
		} 
		
        return resMap;  
    }
	
	
	/** 
	 * 单据打印-模板设置页面（IE）
	 */
	@RequestMapping(value="/printFormSet",method = RequestMethod.GET) 
    public String printFormSet(@RequestParam Map<String, Object> map,Model mode,HttpServletRequest request, HttpServletResponse response){
		
		String group_id=map.get("group_id").toString();
		String hos_id=map.get("hos_id").toString();
		String hos_copy=map.get("hos_copy").toString();
		String template_code=map.get("template_code").toString();
		String mod_code=map.get("mod_code").toString();
		//文件路径：集团/医院/账套/模块编码/模板编码_使用ID.xls
		//"PageOffice/doc/form/"+group_id+"/"+hos_id+"/"+copy_code+"/"+mod_code;
		String docPath=map.get("docPath").toString();
		
		String use_id="0";
		if(map.get("use_id")!=null && !map.get("use_id").equals("")){
			use_id=map.get("use_id").toString();
		}		
		
		ServletContext servletContext= request.getServletContext();
		String toFilePath=servletContext.getRealPath(docPath+"/"+template_code+"_"+use_id+".xls");
		
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		
		//设置服务器页面
		poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
	
		//创建自定义菜单栏
		poCtrl.setTitlebar(false); //标题栏
		poCtrl.setCaption("打印模板设置");
		poCtrl.setMenubar(true);//菜单栏
		poCtrl.setOfficeToolbars(true);//Office工具栏
		poCtrl.setCustomToolbar(true);//自定义工具栏
		poCtrl.getRibbonBar().setSharedVisible("FileSave", false);//隐藏Ribbon快速工具栏中的“保存”按钮
		
		//添加自定义按钮
		poCtrl.addCustomToolButton("保存", "ShowSave()", 1);
		poCtrl.addCustomToolButton("另存为", "ShowAsSave()", 11);
		poCtrl.addCustomToolButton("页面设置", "ShowPrintSet()", 3);
		poCtrl.addCustomToolButton("打印预览", "ShowPrintPre()", 7);
		poCtrl.addCustomToolButton("打印", "ShowPrint()", 6);
		poCtrl.addCustomToolButton("说明", "ShowNote()", 18);
		poCtrl.addCustomToolButton("全屏", "ShowFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "ShowClose()", 21);
		
		//加载完js回调函数
		poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");
		poCtrl.setJsFunction_OnExcelCellClick("cellClick()");
		
		//设置保存页面
		//poCtrl.setSaveFilePage("SaveFile.jsp?docPath="+docPath);
		poCtrl.setSaveFilePage("saveFormFile.do?docPath="+docPath);
		
		//设置并发控制时间
		//poCtrl.setTimeSlice(30);
		
		//根据客户端注册表判断：wps、office
		poCtrl.setOfficeVendor(OfficeVendorType.AutoSelect);
		
		//打开excel文档
		poCtrl.webOpen(toFilePath,OpenModeType.xlsNormalEdit,"");
		poCtrl.setTagId("PageOfficeCtrl1");//此行必需
	
		
		mode.addAttribute("template_code", template_code);
		mode.addAttribute("use_id", use_id);
		mode.addAttribute("group_id", group_id);
		mode.addAttribute("hos_id", hos_id);
		mode.addAttribute("copy_code", hos_copy);
		mode.addAttribute("mod_code", mod_code);
		
        return "PageOffice/printFormSet";  
    }
	
	/** 
	 * 单据打印-模板设置页面-保存模板（IE）
	 */
	@RequestMapping(value="/saveFormFile",method = RequestMethod.POST)
	@ResponseBody
    public void saveFormFile(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){
		
		FileSaver fs=new FileSaver(request,response);
		try{
			
			String docPath=map.get("docPath").toString();
			fs.saveToFile(request.getServletContext().getRealPath("/"+docPath)+"/"+fs.getFileName());
			fs.setCustomSaveResult("ok");
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			fs.setCustomSaveResult("error");
		}finally{
			fs.close();
		}
		
    }
	
	
	/**
	 * 业务调用页面,所有模块打印模板都走此controller，根据系统参数判断，0统一打印(默认)，1按用户打印、2按仓库打印...
	 * 打印模板设置页面，查询打印模板参数
	 */
	@RequestMapping(value = "/queryFormPrintPara", method = RequestMethod.POST)
	@ResponseBody
	public String queryFormPrintPara(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		
		String content="";
		try {
			content=superPrintService.querySuperPrintParaByListJosn(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			content="{}";
		}
		return content;
		
	}
	
	
	/** 
	 * 业务调用页面,所有模块打印模板都走此controller，根据系统参数判断，0统一打印，1按用户打印、2按仓库打印...
	 * 保存打印参数
	 */
	@RequestMapping(value = "/saveFormPrintPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveFormPrintPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String reJson=null;
		try {
			reJson=myPageOfficeService.saveFormPrintPara(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{}";
		}
		return JSONObject.parseObject(reJson);
		
	}
	
	/** 
	 * 单据打印-处理数据文档（谷歌）
	 */
	@RequestMapping(value="/printFormData",method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> printFormData(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){

		if(LoadChdInfo.isPrint()){
			return JSONObject.parseObject("{\"error\":\"\u6253\u5370\u673a\u8fde\u63a5\u5931\u8d25\"}");
		}
		
		if(map==null || map.get("template_code")==null || map.get("template_code").equals("")){
			return JSONObject.parseObject("{\"error\":\"没有参数[template_code]\"}");
		}
		
		String template_code=map.get("template_code").toString();
		String group_id=SessionManager.getGroupId();
		String hos_id=SessionManager.getHosId();
		String copy_code=SessionManager.getCopyCode();
		String mod_code=SessionManager.getModCode();
		map.put("group_id", group_id);
		map.put("hos_id", hos_id);
		map.put("copy_code", copy_code);
		map.put("mod_code", mod_code);
		
		boolean isPreview=false;
		if(map.get("isPreview")!=null && !map.get("isPreview").equals("")){
			isPreview=Boolean.parseBoolean(map.get("isPreview").toString());
		}
		
		String use_id="0";
		if(map.get("use_id")!=null && !map.get("use_id").equals("")){
			use_id=map.get("use_id").toString();
		}		
		map.put("use_id", use_id);
		
		String docPath="/PageOffice/doc/form/"+group_id+"/"+hos_id+"/"+copy_code+"/"+mod_code;
		String openPath=docPath+"/"+template_code+"_"+use_id+".xls";
		
		
		ServletContext servletContext= request.getServletContext();
		//模板文件磁盘路径，带文件名
		String fromFilePath=servletContext.getRealPath(openPath);
		map.put("fromFilePath", fromFilePath);
		//临时文件磁盘路径，不带文件名
		String toFilePath=servletContext.getRealPath(docPath+"/temp");
		map.put("toFilePath", toFilePath);
		
		
		Map<String,Object> resMap=new HashMap<String,Object>();
		
		//取动态生成的文件
		try{
			resMap=myPageOfficeService.printFormBatch(map);
			
			String tempFileName=docPath+"/temp"+"/"+resMap.get("temp_file_name").toString();
			resMap.put("temp_file_name", tempFileName);
			resMap.put("group_id", group_id);
			resMap.put("hos_id", hos_id);
			resMap.put("hos_copy", copy_code);
			resMap.put("template_code", template_code);
			resMap.put("isPreview", isPreview);
			resMap.put("para011", resMap.get("para011"));
			resMap.put("business_no", resMap.get("business_no"));
			
		}catch(Exception e){
			resMap.put("error", StringTool.string2Json(e.getMessage()==null?"生成数据异常！":e.getMessage()));
		}
		
        return resMap;  
    }
	
	/** 
	 * 单据打印-处理数据文档（IE）
	 */
	@RequestMapping(value="/printFormDataBatch",method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> printFormDataBatch(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){

		if(LoadChdInfo.isPrint()){
			return JSONObject.parseObject("{\"error\":\"\u6253\u5370\u673a\u8fde\u63a5\u5931\u8d25\"}");
		}
		
		if(map==null || map.get("template_code")==null || map.get("template_code").equals("")){
			return JSONObject.parseObject("{\"error\":\"没有参数[template_code]\"}");
		}
		
		String template_code=map.get("template_code").toString();
		String group_id=map.get("group_id").toString();
		String hos_id=map.get("hos_id").toString();
		String copy_code=map.get("hos_copy").toString();
		String mod_code=map.get("mod_code").toString();
		map.put("group_id", group_id);
		map.put("hos_id", hos_id);
		map.put("copy_code", copy_code);
		map.put("mod_code", mod_code);
		
		boolean isPreview=false;
		if(map.get("isPreview")!=null && !map.get("isPreview").equals("")){
			isPreview=Boolean.parseBoolean(map.get("isPreview").toString());
		}
		
		String use_id="0";
		if(map.get("use_id")!=null && !map.get("use_id").equals("")){
			use_id=map.get("use_id").toString();
		}		
		map.put("use_id", use_id);
		
		String docPath="/PageOffice/doc/form/"+group_id+"/"+hos_id+"/"+copy_code+"/"+mod_code;
		String openPath=docPath+"/"+template_code+"_"+use_id+".xls";
		
		
		ServletContext servletContext= request.getServletContext();
		//模板文件磁盘路径，带文件名
		String fromFilePath=servletContext.getRealPath(openPath);
		map.put("fromFilePath", fromFilePath);
		//临时文件磁盘路径，不带文件名
		String toFilePath=servletContext.getRealPath(docPath+"/temp");
		map.put("toFilePath", toFilePath);
		
		
		Map<String,Object> resMap=new HashMap<String,Object>();
		
		//取动态生成的文件
		try{
			resMap=myPageOfficeService.printFormBatch(map);
			
			String tempFileName=docPath+"/temp"+"/"+resMap.get("temp_file_name").toString();
			resMap.put("temp_file_name", tempFileName);
			resMap.put("toFilePath", servletContext.getContextPath()+tempFileName);
			resMap.put("group_id", group_id);
			resMap.put("hos_id", hos_id);
			resMap.put("hos_copy", copy_code);
			resMap.put("template_code", template_code);
			resMap.put("isPreview", isPreview);
			resMap.put("para011", resMap.get("para011"));
			resMap.put("business_no", resMap.get("business_no"));
			
		}catch(Exception e){
			resMap.put("error", StringTool.string2Json(e.getMessage()==null?"生成数据异常！":e.getMessage()));
		}
		
        return resMap;  
    }
	
	
	/** 
	 * 单据打印-预览页面（IE）
	 */
	@RequestMapping(value="/printFormPre",method = RequestMethod.GET)
    public String printFormPre(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){
		
		String temp_file_name=map.get("temp_file_name").toString();
		String group_id=map.get("group_id").toString();
		String hos_id=map.get("hos_id").toString();
		String hos_copy=map.get("hos_copy").toString();
		
		String para011="";
		if(map.get("para011")!=null && !map.get("para011").equals("")){
			para011=map.get("para011").toString();
		}
		String template_code="";
		if(map.get("template_code")!=null && !map.get("template_code").equals("")){
			template_code=map.get("template_code").toString();
		}
		String business_no="";
		if(map.get("business_no")!=null && !map.get("business_no").equals("")){
			business_no=map.get("business_no").toString();
		}
		
		String toFilePath=request.getServletContext().getRealPath("/"+temp_file_name);
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		
		//设置服务器页面
		poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
	
		//创建自定义菜单栏
		poCtrl.setTitlebar(false); //标题栏
		//poCtrl.setCaption("打印预览");
		if(para011!=null && !para011.equals("")){
			//有打印次数隐藏菜单栏，主要是为了打印次数的准确性
			poCtrl.setMenubar(false);//菜单栏
		}else{
			poCtrl.setMenubar(true);//菜单栏
		}
		
		poCtrl.setOfficeToolbars(false);//Office工具栏
		poCtrl.setCustomToolbar(true);//自定义工具栏
		
		
		//添加自定义按钮
		//poCtrl.addCustomToolButton("保存", "ShowSave()", 1);
		poCtrl.addCustomToolButton("另存为", "ShowAsSave()", 11);
		poCtrl.addCustomToolButton("页面设置", "ShowPrintSet()", 3);
		if(para011==null || para011.equals("")){
			//有打印次数不显示打印预览，主要是为了打印次数的准确性
			poCtrl.addCustomToolButton("打印预览", "ShowPrintPre()", 7);
		}
		poCtrl.addCustomToolButton("打印", "ShowPrint()", 6);
		poCtrl.addCustomToolButton("全屏", "ShowFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "ShowClose()", 21);
		
		//加载完js回调函数
		poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");
		
		//设置保存页面
		//poCtrl.setSaveFilePage("SaveFile.jsp?docPath="+docPath);
		
		//根据客户端注册表判断：wps、office
		poCtrl.setOfficeVendor(OfficeVendorType.AutoSelect);
		
		Workbook wbWrite=new Workbook();
		Sheet sheetWrite=wbWrite.openSheet("Sheet1");
		//设置当工作表只读时，是否允许用户手动调整行列。  
		sheetWrite.setAllowAdjustRC(true);
		poCtrl.setWriter(wbWrite);//此行必须
		
		//打开excel文档
		poCtrl.webOpen(toFilePath,OpenModeType.xlsReadOnly,"");
		poCtrl.setTagId("PageOfficeCtrl1");//此行必需
		
		mode.addAttribute("temp_file_name", temp_file_name);
		mode.addAttribute("business_no", business_no);
		mode.addAttribute("group_id", group_id);
		mode.addAttribute("hos_id", hos_id);
		mode.addAttribute("hos_copy", hos_copy);
		mode.addAttribute("template_code", template_code);
		
        return "PageOffice/printFormPre";  
    }
	
	
	/** 
	 * 单据批量打印-预览页面（IE）
	 */
	@RequestMapping(value="/printFormBatchPre",method = RequestMethod.GET)
    public String printFormBatchPre(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){
		
		String temp_file_name=map.get("temp_file_name").toString();
		//路径中会多出一个项目文件夹，需要先去掉
		temp_file_name = temp_file_name.replace(request.getServletContext().getContextPath(), "");
		String group_id=map.get("group_id").toString();
		String hos_id=map.get("hos_id").toString();
		String hos_copy=map.get("hos_copy").toString();
		
		String para011="";
		if(map.get("para011")!=null && !map.get("para011").equals("")){
			para011=map.get("para011").toString();
		}
		String template_code="";
		if(map.get("template_code")!=null && !map.get("template_code").equals("")){
			template_code=map.get("template_code").toString();
		}
		String business_no="";
		if(map.get("business_no")!=null && !map.get("business_no").equals("")){
			business_no=map.get("business_no").toString();
		}
		
		String toFilePath=request.getServletContext().getRealPath("/"+temp_file_name);
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		
		//设置服务器页面
		poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
	
		//创建自定义菜单栏
		poCtrl.setTitlebar(false); //标题栏
		//poCtrl.setCaption("打印预览");
		if(para011!=null && !para011.equals("")){
			//有打印次数隐藏菜单栏，主要是为了打印次数的准确性
			poCtrl.setMenubar(false);//菜单栏
		}else{
			poCtrl.setMenubar(true);//菜单栏
		}
		
		poCtrl.setOfficeToolbars(false);//Office工具栏
		poCtrl.setCustomToolbar(true);//自定义工具栏
		
		
		//添加自定义按钮
		//poCtrl.addCustomToolButton("保存", "ShowSave()", 1);
		poCtrl.addCustomToolButton("另存为", "ShowAsSave()", 11);
		poCtrl.addCustomToolButton("页面设置", "ShowPrintSet()", 3);
		if(para011==null || para011.equals("")){
			//有打印次数不显示打印预览，主要是为了打印次数的准确性
			poCtrl.addCustomToolButton("打印预览", "ShowPrintPre()", 7);
		}
		poCtrl.addCustomToolButton("打印", "ShowPrint()", 6);
		poCtrl.addCustomToolButton("全屏", "ShowFullScreen()", 4);
		
		//加载完js回调函数
		poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");
		
		//设置保存页面
		//poCtrl.setSaveFilePage("SaveFile.jsp?docPath="+docPath);
		
		//根据客户端注册表判断：wps、office
		poCtrl.setOfficeVendor(OfficeVendorType.AutoSelect);
		
		Workbook wbWrite=new Workbook();
		Sheet sheetWrite=wbWrite.openSheet("Sheet1");
		//设置当工作表只读时，是否允许用户手动调整行列。  
		sheetWrite.setAllowAdjustRC(true);
		poCtrl.setWriter(wbWrite);//此行必须
		
		//打开excel文档
		poCtrl.webOpen(toFilePath,OpenModeType.xlsReadOnly,"");
		poCtrl.setTagId("PageOfficeCtrl1");//此行必需
		
		mode.addAttribute("temp_file_name", temp_file_name);
		mode.addAttribute("business_no", business_no);
		mode.addAttribute("group_id", group_id);
		mode.addAttribute("hos_id", hos_id);
		mode.addAttribute("hos_copy", hos_copy);
		mode.addAttribute("template_code", template_code);
		
        return "PageOffice/printFormPre";  
    }
	
	
	/** 
	 * 单据打印-直接打印（IE）
	 */
	@RequestMapping(value="/printForm",method = RequestMethod.GET) 
    public String printForm(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){
		
		String temp_file_name=map.get("temp_file_name").toString();
		String group_id=map.get("group_id").toString();
		String hos_id=map.get("hos_id").toString();
		String hos_copy=map.get("hos_copy").toString();
		String template_code="";
		if(map.get("template_code")!=null && !map.get("template_code").equals("")){
			template_code=map.get("template_code").toString();
		}
		String business_no="";
		if(map.get("business_no")!=null && !map.get("business_no").equals("")){
			business_no=map.get("business_no").toString();
		}
		
		String toFilePath=request.getServletContext().getRealPath("/"+temp_file_name);
		FileMakerCtrl fmCtrl = new FileMakerCtrl(request);
		
		//设置服务器页面
		fmCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
		
		//加载完js回调函数
		fmCtrl.setJsFunction_OnProgressComplete("OnProgressComplete(1)");
		
		//根据客户端注册表判断：wps、office
		fmCtrl.setOfficeVendor(OfficeVendorType.AutoSelect);
		
		//打开excel文档
		fmCtrl.fillDocument(toFilePath, DocumentOpenType.Excel);
		fmCtrl.setTagId("PageOfficeCtrl1");//此行必需
		
		mode.addAttribute("temp_file_name", temp_file_name);
		mode.addAttribute("business_no", business_no);
		mode.addAttribute("group_id", group_id);
		mode.addAttribute("hos_id", hos_id);
		mode.addAttribute("hos_copy", hos_copy);
		mode.addAttribute("template_code", template_code);
		
        return "PageOffice/printForm";  
    }
	
	
	/** 
	 * 单据打印-批量打印（谷歌）
	 */
	@RequestMapping(value="/printFormBatch",method = RequestMethod.GET) 
    public String printFormBatch(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){

		//FileMakerCtrl fmCtrl = new FileMakerCtrl(request);
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		
		//设置服务器页面
		//fmCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
		poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
		
		//加载完js回调函数
		//fmCtrl.setJsFunction_OnProgressComplete("OnProgressComplete(1)");
		//poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");
		
		//根据客户端注册表判断：wps、office
		//fmCtrl.setOfficeVendor(OfficeVendorType.AutoSelect);
		poCtrl.setOfficeVendor(OfficeVendorType.AutoSelect);

		//fmCtrl.setTagId("PageOfficeCtrl1");//此行必需
		poCtrl.setTagId("PageOfficeCtrl1");//此行必需
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("hos_copy", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		map.put("mod_code", SessionManager.getModCode());
		
		mode.addAttribute("map", map);
		
        return "PageOffice/printFormBatch";
    }

	
	/** 
	 * 删除打印生成的文件
	 */
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	@ResponseBody
    public Map<String,Object> deleteFile(@RequestParam Map<String, Object> map,HttpServletRequest request){
		if(map.get("temp_file_name")==null || map.get("temp_file_name").equals("")){
			return JSONObject.parseObject("{}");
		}
		
		//临时文件磁盘路径
		String toFilePath=request.getServletContext().getRealPath("/"+map.get("temp_file_name").toString());
		FileUtil.deleteFile(toFilePath);
		return JSONObject.parseObject("{}");
    }

	
	/** 
	 * 批量删除打印生成的文件
	 */
	@RequestMapping(value = "/deleteFileBatch", method = RequestMethod.POST)
	@ResponseBody
    public Map<String,Object> deleteFileBatch(@RequestParam Map<String, Object> map,HttpServletRequest request){
		if(map.get("fileList")==null || map.get("fileList").equals("")){
			return JSONObject.parseObject("{}");
		}
		
		List<Map<String, Object>> fileList = JsonListMapUtil.getListMap(map.get("fileList").toString());
		String toFilePath = "";
		
		for(Map<String, Object> fileMap : fileList){
			if(fileMap.get("temp_file_name")==null || fileMap.get("temp_file_name").equals("")){
				continue;
			}
			//临时文件磁盘路径
			toFilePath = request.getServletContext().getRealPath("/"+fileMap.get("temp_file_name").toString());
			FileUtil.deleteFile(toFilePath);
		}
		
		return JSONObject.parseObject("{}");
    }
	
	/** 
	 * 保存打印次数
	 */
	@RequestMapping(value = "/savePrintCount", method = RequestMethod.POST)
	@ResponseBody
    public Map<String,Object> savePrintCount(@RequestParam Map<String, Object> mapVo,HttpServletRequest request){
		
		if(mapVo.get("business_no")==null || mapVo.get("business_no").equals("")){
			return JSONObject.parseObject("{}");
		}
		
		String businessNo=mapVo.get("business_no").toString();
		mapVo.put("business_no", businessNo.split(","));
		
		try {
			myPageOfficeService.savePrintCountPage(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		
		return JSONObject.parseObject("{}");
    }
	
	/***************************************************************************************************************************
	 * 报表-上传文件处理格式(谷歌)
	 */
	@RequestMapping(value = "/createReportFile",method = RequestMethod.POST)//,produces = {"application/json;charset=UTF-8"}
	@ResponseBody
    public Map<String,Object> createReportFile(@RequestParam(value="file") MultipartFile file,
    		@RequestParam(value="fileName") String fileName,
    		@RequestParam(value="fileType") String fileType,
    		HttpServletRequest request){
		
		if(LoadChdInfo.isPrint()){
			return JSONObject.parseObject("{\"error\":\"\u6253\u5370\u673a\u8fde\u63a5\u5931\u8d25\"}");
		}
		
		//MultipartHttpServletRequest multipartRequest,HttpServletRequest request,HttpServletResponse response
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		try {
			
	    	//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//	    	Map<String,MultipartFile> fileMap = multipartRequest.getFileMap();
//	    	if(fileMap==null || fileMap.size()==0){
//	    		resMap.put("state", false);
//	    		resMap.put("msg", "没有数据！");
//	    		return resMap;
//	    	}
//	    	
//	    	MultipartFile multipartFile=null;
//	    	for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
//	    		multipartFile = fileMap.get(entry.getKey());
//	    		break;
//	    	}
//	    	
	    	String group_id=SessionManager.getGroupId();
			String hos_id=SessionManager.getHosId();
			String hos_copy=SessionManager.getCopyCode();
			String mod_code=SessionManager.getModCode();
			
			String basePath="/PageOffice/doc/report/"+group_id+"/"+hos_id+"/"+hos_copy+"/"+mod_code;
			//String openFile=basePath+"/temp/001_2228483412396950011.xlsx";
			String openFile=basePath+"/temp/"+fileName+"_"+UUIDLong.absLongUUID()+"."+fileType;
			//上传的临时文件磁盘路径
			String toFilePath=request.getServletContext().getRealPath(openFile);
			
			//创建目录
			File mlfile =new File(toFilePath.substring(0,toFilePath.lastIndexOf("\\")));
			if(!mlfile.exists()) {
				mlfile.mkdirs();    
			}
			
			//传目录，删除昨天的临时文件
    		FileUtil.deleteTempPath(mlfile);
			
	    	//将上传文件转存至服务器指定路径
			File toFile=new File(toFilePath);
			file.transferTo(toFile);

			//模板文件磁盘路径
			String templateFilePath=request.getServletContext().getRealPath(basePath+"/"+fileName+"."+fileType);
			//根据报表模板渲染打印信息、行高列宽
			myPageOfficeService.createReportFile(templateFilePath,toFilePath);
			
			resMap.put("state", true);
			resMap.put("temp_file_name", openFile);
			resMap.put("group_id", group_id);
			resMap.put("hos_id", hos_id);
			resMap.put("hos_copy", hos_copy);
			resMap.put("mod_code", mod_code);
			resMap.put("report_code", fileName);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			resMap.put("state", false);
			resMap.put("msg", e.getMessage());
		}

		return resMap;
    }
	
	/** 
	 * 报表打印-预览页面(报表模板、查询页面打印通用)（IE）
	 */
	@RequestMapping(value="/printReportPre",method = RequestMethod.GET)
    public String printReportPre(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){
		
		String temp_file_name=map.get("temp_file_name").toString();
		String fileType=temp_file_name.substring(temp_file_name.lastIndexOf(".")+1,temp_file_name.length());
		String group_id=map.get("group_id").toString();
		String hos_id=map.get("hos_id").toString();
		String hos_copy=map.get("hos_copy").toString();
		String mod_code=map.get("mod_code").toString();
		String report_code=map.get("report_code").toString();
		String sheet_name=map.get("sheet_name").toString();
		String openFlag=map.get("openFlag").toString();
		
		String basePath="PageOffice/doc/report/"+group_id+"/"+hos_id+"/"+hos_copy+"/"+mod_code;
		String fileName=report_code+"."+fileType;
		String toFilePath=request.getServletContext().getRealPath("/"+temp_file_name);
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		String tempFile=basePath+"/"+report_code+"."+fileType;
		
		//设置服务器页面
		poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
	
		//创建自定义菜单栏
		poCtrl.setTitlebar(false); //标题栏
		poCtrl.setMenubar(true);//菜单栏
		poCtrl.setOfficeToolbars(false);//Office工具栏
		poCtrl.setCustomToolbar(true);//自定义工具栏
		
		
		//添加自定义按钮
		poCtrl.addCustomToolButton("保存打印模板", "ShowSave()", 1);//报表模板页面直接保存，数据预览页面调saveReportTempFile
		if(openFlag.equals("template")){
			poCtrl.addCustomToolButton("还原默认", "DelTempFile()", 13);
		}/*else{
			//数据预览页面，先保存临时文件的打印格式，再把打印格式转存到模板上
			tempFile=temp_file_name;
		}*/
		poCtrl.addCustomToolButton("另存为", "ShowAsSave()", 11);
		poCtrl.addCustomToolButton("页面设置", "ShowPrintSet()", 3);
		poCtrl.addCustomToolButton("打印预览", "ShowPrintPre()", 7);
		poCtrl.addCustomToolButton("打印", "ShowPrint()", 6);
		poCtrl.addCustomToolButton("全屏", "ShowFullScreen()", 4);
		if(openFlag.equals("template")){
			poCtrl.addCustomToolButton("说明", "ShowNote()", 18);
		}
		poCtrl.addCustomToolButton("关闭", "ShowClose()", 21);
		
		//加载完js回调函数
		poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");
		
		//设置保存页面
		//poCtrl.setSaveFilePage("SaveReport.jsp?docPath="+basePath+"&fileName="+fileName);
		poCtrl.setSaveFilePage("saveReportFile.do?docPath="+basePath+"/"+fileName);
		
		//根据客户端注册表判断：wps、office
		poCtrl.setOfficeVendor(OfficeVendorType.AutoSelect);

		Workbook wb=new Workbook();
		Sheet sheet=wb.openSheet(sheet_name);
		//设置当工作表只读时，是否允许用户手动调整行列。  
		sheet.setAllowAdjustRC(true);
		poCtrl.setWriter(wb);
		
		
		//打开excel文档
		poCtrl.webOpen(toFilePath,OpenModeType.xlsNormalEdit,"");
		poCtrl.setTagId("PageOfficeCtrl1");//此行必需
		
		mode.addAttribute("temp_file_name", temp_file_name);
		mode.addAttribute("tempFile", tempFile);
		mode.addAttribute("group_id", group_id);
		mode.addAttribute("hos_id", hos_id);
		mode.addAttribute("hos_copy", hos_copy);
		mode.addAttribute("open_flag", openFlag);
		
        return "PageOffice/printReportPre";
    }
	
	/** 
	 * 报表打印-预览页面-保存(报表模板、查询页面打印通用)（IE）
	 */
	@RequestMapping(value="/saveReportFile",method = RequestMethod.POST)
	@ResponseBody
    public void saveReportFile(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){
		
		FileSaver fs=new FileSaver(request,response);
		try{
			
			//保存文件
			String docPath=map.get("docPath").toString();
			fs.saveToFile(request.getServletContext().getRealPath("/"+docPath));
			fs.setCustomSaveResult("ok");
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			fs.setCustomSaveResult("error");
		}finally{
			fs.close();
		}
    }
	
	
	/** 
	 * 保存打印模板-数据预览页面(带数据一起保存，暂时停用)
	 */
	@RequestMapping(value = "/saveReportTempFile",method = RequestMethod.POST)//,produces = {"application/json;charset=UTF-8"}
	@ResponseBody
    public Map<String,Object> saveReportTempFile(@RequestParam Map<String,Object> map,HttpServletRequest request){
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		try {
			String temp_file_name=map.get("temp_file_name").toString();
			String tempFile=map.get("tempFile").toString();
			//数据文件磁盘路径
			ServletContext servletContext=request.getServletContext();
			String fromFilePath=servletContext.getRealPath("/"+temp_file_name);
			
			//模板文件磁盘路径
			String toFilePath=servletContext.getRealPath("/"+tempFile);
			
			myPageOfficeService.saveReportTemplateFile(fromFilePath,toFilePath);
			resMap.put("state", true);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			resMap.put("state", false);
			resMap.put("msg", e.getMessage());
		}

		return resMap;
    }
	
	/** 
	 * 财务报表模板-上传文件处理格式（谷歌）
	 */
	@RequestMapping(value = "/createReportTempFile",method = RequestMethod.POST)//,produces = {"application/json;charset=UTF-8"}
	@ResponseBody
    public Map<String,Object> createReportTempFile(@RequestParam(value="file") MultipartFile file,
    		@RequestParam(value="fileName") String fileName,
    		@RequestParam(value="fileType") String fileType,
    		HttpServletRequest request){
		
		if(LoadChdInfo.isPrint()){
			return JSONObject.parseObject("{\"error\":\"\u6253\u5370\u673a\u8fde\u63a5\u5931\u8d25\"}");
		}
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		try {
			
	    	String group_id=SessionManager.getGroupId();
			String hos_id=SessionManager.getHosId();
			String hos_copy=SessionManager.getCopyCode();
			String mod_code=SessionManager.getModCode();
			
			String openFile="/PageOffice/doc/report/"+group_id+"/"+hos_id+"/"+hos_copy+"/"+mod_code+"/"+fileName+"."+fileType;
			String toFilePath=request.getServletContext().getRealPath(openFile);
			
			//创建目录
			File mlfile =new File(toFilePath.substring(0,toFilePath.lastIndexOf("\\")));
			if(!mlfile.exists()) {
				mlfile.mkdirs();    
			}
			
			File toFile=new File(toFilePath);
			if(!toFile.exists()){
				//将上传文件转存至服务器指定路径
				file.transferTo(toFile);
				//处理单元格公式
				myPageOfficeService.setReportFileFormula(toFilePath);
			}
			
			resMap.put("state", true);
			resMap.put("temp_file_name", openFile);
			resMap.put("group_id", group_id);
			resMap.put("hos_id", hos_id);
			resMap.put("hos_copy", hos_copy);
			resMap.put("mod_code", mod_code);
			resMap.put("report_code", fileName);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			resMap.put("state", false);
			resMap.put("error", StringTool.string2Json(e.getMessage()==null?"生成数据异常！":e.getMessage()));
		}

		return resMap;
    }
	
	
	/** 
	 * 表格模板打印-处理数据文档（谷歌）
	 */
	@RequestMapping(value="/printTableData",method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> printTableData(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){
		
		
		if(LoadChdInfo.isPrint()){
			return JSONObject.parseObject("{\"error\":\"\u6253\u5370\u673a\u8fde\u63a5\u5931\u8d25\"}");
		}
		
		if(map==null || map.get("template_code")==null || map.get("template_code").equals("")){
			return JSONObject.parseObject("{\"error\":\"没有参数[template_code]\"}");
		}
		
		String template_code=map.get("template_code").toString();
		String group_id=SessionManager.getGroupId();
		String hos_id=SessionManager.getHosId();
		String copy_code=SessionManager.getCopyCode();
		String mod_code=SessionManager.getModCode();
		map.put("group_id", group_id);
		map.put("hos_id", hos_id);
		map.put("copy_code", copy_code);
		map.put("mod_code", mod_code);
				
		String use_id="0";
		if(map.get("use_id")!=null && !map.get("use_id").equals("")){
			use_id=map.get("use_id").toString();
		}		
		map.put("use_id", use_id);
		
		String docPath="/PageOffice/doc/form/"+group_id+"/"+hos_id+"/"+copy_code+"/"+mod_code;
		String openPath=docPath+"/"+template_code+"_"+use_id+".xls";
		
		
		ServletContext servletContext= request.getServletContext();
		//模板文件磁盘路径，带文件名
		String fromFilePath=servletContext.getRealPath(openPath);
		map.put("fromFilePath", fromFilePath);
		//临时文件磁盘路径，不带文件名
		String toFilePath=servletContext.getRealPath(docPath+"/temp");
		map.put("toFilePath", toFilePath);
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		try{
			//取动态生成的文件
			String tempFileName=myPageOfficeService.printTableAcc(map);
			
			tempFileName=docPath+"/temp"+"/"+tempFileName;
			resMap.put("temp_file_name", tempFileName);
			resMap.put("group_id", group_id);
			resMap.put("hos_id", hos_id);
			resMap.put("hos_copy", copy_code);
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			resMap.put("state", false);
			resMap.put("error", StringTool.string2Json(e.getMessage()==null?"生成数据异常！":e.getMessage()));
		}
		
        return resMap;  
    }
	
	
	/** 
	 * 表格模板打印-处理数据文档（IE）
	 */
	@RequestMapping(value="/printTableDataBatch",method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> printTableDataBatch(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){
		
		
		if(LoadChdInfo.isPrint()){
			return JSONObject.parseObject("{\"error\":\"\u6253\u5370\u673a\u8fde\u63a5\u5931\u8d25\"}");
		}
		
		if(map==null || map.get("template_code")==null || map.get("template_code").equals("")){
			return JSONObject.parseObject("{\"error\":\"没有参数[template_code]\"}");
		}
		
		String template_code=map.get("template_code").toString();
		String group_id=map.get("group_id").toString();
		String hos_id=map.get("hos_id").toString();
		String copy_code=map.get("hos_copy").toString();
		String mod_code=map.get("mod_code").toString();
		map.put("group_id", group_id);
		map.put("hos_id", hos_id);
		map.put("copy_code", copy_code);
		map.put("mod_code", mod_code);
				
		String use_id="0";
		if(map.get("use_id")!=null && !map.get("use_id").equals("")){
			use_id=map.get("use_id").toString();
		}		
		map.put("use_id", use_id);
		
		String docPath="/PageOffice/doc/form/"+group_id+"/"+hos_id+"/"+copy_code+"/"+mod_code;
		String openPath=docPath+"/"+template_code+"_"+use_id+".xls";
		
		
		ServletContext servletContext= request.getServletContext();
		//模板文件磁盘路径，带文件名
		String fromFilePath=servletContext.getRealPath(openPath);
		map.put("fromFilePath", fromFilePath);
		//临时文件磁盘路径，不带文件名
		String toFilePath=servletContext.getRealPath(docPath+"/temp");
		map.put("toFilePath", toFilePath);
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		try{
			//取动态生成的文件
			String tempFileName=myPageOfficeService.printTableAcc(map);
			
			tempFileName=docPath+"/temp"+"/"+tempFileName;
			resMap.put("temp_file_name", tempFileName);
			resMap.put("toFilePath", servletContext.getContextPath()+tempFileName);
			resMap.put("group_id", group_id);
			resMap.put("hos_id", hos_id);
			resMap.put("hos_copy", copy_code);
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			resMap.put("state", false);
			resMap.put("error", StringTool.string2Json(e.getMessage()==null?"生成数据异常！":e.getMessage()));
		}
		
        return resMap;  
    }
	
	/** 
	 * 表格模板打印-预览页面（IE）
	 */
	@RequestMapping(value="/printTablePre",method = RequestMethod.GET)
    public String printTablePre(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){
		
		String temp_file_name=map.get("temp_file_name").toString();
		String group_id=map.get("group_id").toString();
		String hos_id=map.get("hos_id").toString();
		String hos_copy=map.get("hos_copy").toString();
				
		String toFilePath=request.getServletContext().getRealPath("/"+temp_file_name);
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		
		//设置服务器页面
		poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
		
		//创建自定义菜单栏
		poCtrl.setTitlebar(false); //标题栏
		
		poCtrl.setMenubar(true);//菜单栏
		poCtrl.setOfficeToolbars(false);//Office工具栏
		poCtrl.setCustomToolbar(true);//自定义工具栏
		
		
		//添加自定义按钮
		//poCtrl.addCustomToolButton("保存", "ShowSave()", 1);
		poCtrl.addCustomToolButton("另存为", "ShowAsSave()", 11);
		poCtrl.addCustomToolButton("页面设置", "ShowPrintSet()", 3);
		poCtrl.addCustomToolButton("打印预览", "ShowPrintPre()", 7);
		poCtrl.addCustomToolButton("打印", "ShowPrint()", 6);
		poCtrl.addCustomToolButton("全屏", "ShowFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "ShowClose()", 21);
		
		//加载完js回调函数
		poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");
		
		//设置保存页面
		//poCtrl.setSaveFilePage("SaveFile.jsp?docPath="+docPath);
		
		//根据客户端注册表判断：wps、office
		poCtrl.setOfficeVendor(OfficeVendorType.AutoSelect);
		
		Workbook wbWrite=new Workbook();
		//Sheet sheetWrite=wbWrite.openSheet("Sheet1");
		//设置当工作表只读时，是否允许用户手动调整行列，企业版专享。    
		//sheetWrite.setAllowAdjustRC(true);
		poCtrl.setWriter(wbWrite);//此行必须
		
		//打开excel文档
		poCtrl.webOpen(toFilePath,OpenModeType.xlsReadOnly,"");
		poCtrl.setTagId("PageOfficeCtrl1");//此行必需
		
		mode.addAttribute("temp_file_name", temp_file_name);
		mode.addAttribute("group_id", group_id);
		mode.addAttribute("hos_id", hos_id);
		mode.addAttribute("hos_copy", hos_copy);
		
        return "PageOffice/printTablePre";  
    }
	
	/** 
	 * 表格模板打印-直接打印（IE）
	 */
	@RequestMapping(value="/printTable",method = RequestMethod.GET)
    public String printTable(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){
		
		String temp_file_name=map.get("temp_file_name").toString();
		String group_id=map.get("group_id").toString();
		String hos_id=map.get("hos_id").toString();
		String hos_copy=map.get("hos_copy").toString();
		String isFinish = map.get("isFinish") == null ? "" : map.get("isFinish").toString();
		String printStatePara = map.get("printStatePara") == null ? "" : map.get("printStatePara").toString();
		
		String toFilePath=request.getServletContext().getRealPath("/"+temp_file_name);
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		
		//设置服务器页面
		poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
	
		//加载完js回调函数
		poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");
		
		//根据客户端注册表判断：wps、office
		poCtrl.setOfficeVendor(OfficeVendorType.AutoSelect);
		
		//打开excel文档
		poCtrl.webOpen(toFilePath,OpenModeType.xlsReadOnly,"");
		poCtrl.setTagId("PageOfficeCtrl1");//此行必需
		
		mode.addAttribute("temp_file_name", temp_file_name);
		mode.addAttribute("group_id", group_id);
		mode.addAttribute("hos_id", hos_id);
		mode.addAttribute("hos_copy", hos_copy);
		mode.addAttribute("isFinish", isFinish);
		mode.addAttribute("printStatePara", printStatePara);
		
        return "PageOffice/printTable";
    }
	
	/** 
	 * 表格模板打印-批量打印（谷歌）
	 */
	@RequestMapping(value="/printTableBatch",method = RequestMethod.GET)
    public String printTableBatch(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){
		//pageOffice页面的一些设置
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		
		//设置服务器页面
		poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
		
		//加载完js回调函数
		//poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");
		
		//根据客户端注册表判断：wps、office
		poCtrl.setOfficeVendor(OfficeVendorType.AutoSelect);
		
		poCtrl.setTagId("PageOfficeCtrl1");//此行必需
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("hos_copy", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		map.put("mod_code", SessionManager.getModCode());

		mode.addAttribute("map", map);
		
        return "PageOffice/printTableBatch";
    }
	
	/** 
	 * 表格批量模板打印-预览页面（PageOffice）
	 */
	@RequestMapping(value="/printTableBatchPre",method = RequestMethod.GET)
    public String printTableBatchPre(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){
		
		String temp_file_name=map.get("temp_file_name").toString();
		//路径中会多出一个项目文件夹，需要先去掉
		temp_file_name = temp_file_name.replace(request.getServletContext().getContextPath(), "");
		String group_id=map.get("group_id").toString();
		String hos_id=map.get("hos_id").toString();
		String hos_copy=map.get("hos_copy").toString();
		
		String toFilePath=request.getServletContext().getRealPath("/"+temp_file_name);
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		
		//设置服务器页面
		poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
		
		//创建自定义菜单栏
		poCtrl.setTitlebar(false); //标题栏
		
		poCtrl.setMenubar(true);//菜单栏
		poCtrl.setOfficeToolbars(false);//Office工具栏
		poCtrl.setCustomToolbar(true);//自定义工具栏
		
		
		//添加自定义按钮
		//poCtrl.addCustomToolButton("保存", "ShowSave()", 1);
		poCtrl.addCustomToolButton("另存为", "ShowAsSave()", 11);
		poCtrl.addCustomToolButton("页面设置", "ShowPrintSet()", 3);
		poCtrl.addCustomToolButton("打印预览", "ShowPrintPre()", 7);
		poCtrl.addCustomToolButton("打印", "ShowPrint()", 6);
		poCtrl.addCustomToolButton("全屏", "ShowFullScreen()", 4);
		
		//加载完js回调函数
		//poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");
		
		//设置保存页面
		//poCtrl.setSaveFilePage("SaveFile.jsp?docPath="+docPath);
		
		//根据客户端注册表判断：wps、office
		poCtrl.setOfficeVendor(OfficeVendorType.AutoSelect);
		
		Workbook wbWrite=new Workbook();
		//Sheet sheetWrite=wbWrite.openSheet("Sheet1");
		//设置当工作表只读时，是否允许用户手动调整行列，企业版专享。    
		//sheetWrite.setAllowAdjustRC(true);
		poCtrl.setWriter(wbWrite);//此行必须
		
		//打开excel文档
		poCtrl.webOpen(toFilePath,OpenModeType.xlsReadOnly,"");
		poCtrl.setTagId("PageOfficeCtrl1");//此行必需
		
		mode.addAttribute("temp_file_name", temp_file_name);
		mode.addAttribute("group_id", group_id);
		mode.addAttribute("hos_id", hos_id);
		mode.addAttribute("hos_copy", hos_copy);
		
        return "PageOffice/printTablePre";  
    }

	
	/** 
	 * 查询列表打印-处理数据文档（谷歌）
	 */
	@RequestMapping(value="/printGridData",method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> printGridData(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){
		
		if(LoadChdInfo.isPrint()){
			return JSONObject.parseObject("{\"error\":\"\u6253\u5370\u673a\u8fde\u63a5\u5931\u8d25\"}");
		}
		
		if(map==null || map.get("page_url")==null || map.get("page_url").equals("")){
			return JSONObject.parseObject("{\"error\":\"没有参数[page_url]\"}");
		}
		
		String page_url=map.get("page_url").toString();
		String group_id=null,hos_id=null,copy_code=null,mod_code=null,user_id=null;
		if(map.get("group_id")==null){
			group_id=SessionManager.getGroupId();
			map.put("group_id", group_id);
		}
		if(map.get("hos_id")==null){
			hos_id=SessionManager.getHosId();
			map.put("hos_id", hos_id);
		}
		if(map.get("copy_code")==null){
			copy_code=SessionManager.getCopyCode();
			map.put("copy_code", copy_code);
		}
		if(map.get("mod_code")==null){
			mod_code=SessionManager.getModCode();
			map.put("mod_code", mod_code);
		}
		if(map.get("user_id")==null){
			user_id=SessionManager.getUserId();
			map.put("user_id", user_id);
		}
		
				
		String use_id="0";
		if(map.get("use_id")!=null && !map.get("use_id").equals("")){
			use_id=map.get("use_id").toString();
		}		
		map.put("use_id", use_id);
		
		String docPath="/PageOffice/doc/grid/temp";
		
		ServletContext servletContext= request.getServletContext();
		//临时文件磁盘路径，不带文件名
		String toFilePath=servletContext.getRealPath(docPath);
		map.put("toFilePath", toFilePath);
		
		//模板文件磁盘路径，带文件名
		String fromFilePath=servletContext.getRealPath("/PageOffice/base/form/test.xlsx");
		map.put("fromFilePath", fromFilePath);
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		try{
			//取动态生成的文件
			String tempFileName=myPageOfficeService.printGrid(map);
			
			tempFileName=docPath+"/"+tempFileName;
			resMap.put("temp_file_name", tempFileName);
			resMap.put("group_id", group_id);
			resMap.put("hos_id", hos_id);
			resMap.put("hos_copy", copy_code);
			resMap.put("page_url", page_url);
			resMap.put("mod_code", mod_code);
			resMap.put("user_id", user_id);
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			resMap.put("state", false);
			resMap.put("error", StringTool.string2Json(e.getMessage()==null?"生成数据异常！":e.getMessage()));
		}
		
        return resMap;  
    }
	
	
	/** 
	 * 查询列表打印-预览页面（IE）
	 */
	@RequestMapping(value="/printGridPre",method = RequestMethod.GET)
    public String printGridPre(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){
		
		String temp_file_name=map.get("temp_file_name").toString();
		String group_id=map.get("group_id").toString();
		String hos_id=map.get("hos_id").toString();
		String hos_copy=map.get("hos_copy").toString();
		String page_url=map.get("page_url").toString();
		String mod_code=map.get("mod_code").toString();
		String user_id=map.get("user_id").toString();
				
		String toFilePath=request.getServletContext().getRealPath("/"+temp_file_name);
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		
		//设置服务器页面
		poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
	
		//创建自定义菜单栏
		poCtrl.setTitlebar(false); //标题栏
		
		poCtrl.setMenubar(true);//菜单栏
		poCtrl.setOfficeToolbars(false);//Office工具栏
		poCtrl.setCustomToolbar(true);//自定义工具栏
		
		
		//添加自定义按钮
		poCtrl.addCustomToolButton("保存", "ShowSave()", 1);
		poCtrl.addCustomToolButton("另存为", "ShowAsSave()", 11);
		poCtrl.addCustomToolButton("页面设置", "ShowPrintSet()", 3);
		poCtrl.addCustomToolButton("打印预览", "ShowPrintPre()", 7);
		poCtrl.addCustomToolButton("打印", "ShowPrint()", 6);
		poCtrl.addCustomToolButton("全屏", "ShowFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "ShowClose()", 21);
		
		//加载完js回调函数
		poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");
		
		//设置保存页面
		String docPath=temp_file_name.substring(0,temp_file_name.lastIndexOf("/"));
		//poCtrl.setSaveFilePage("SaveFile.jsp?docPath="+docPath);
		poCtrl.setSaveFilePage("savePrintGridPre.do?docPath="+docPath+"&page_url="+page_url+"&mod_code="+mod_code+"&user_id="+user_id);

		//根据客户端注册表判断：wps、office
		poCtrl.setOfficeVendor(OfficeVendorType.AutoSelect);
		
		Workbook wbWrite=new Workbook();
		//Sheet sheetWrite=wbWrite.openSheet("Sheet1");
		//设置当工作表只读时，是否允许用户手动调整行列，企业版专享。  
		//sheetWrite.setAllowAdjustRC(true);
		poCtrl.setWriter(wbWrite);//此行必须
		
		//打开excel文档
		poCtrl.webOpen(toFilePath,OpenModeType.xlsNormalEdit,"");
		poCtrl.setTagId("PageOfficeCtrl1");//此行必需
		
		mode.addAttribute("temp_file_name", temp_file_name);
		mode.addAttribute("group_id", group_id);
		mode.addAttribute("hos_id", hos_id);
		mode.addAttribute("hos_copy", hos_copy);
		mode.addAttribute("page_url", page_url);
		mode.addAttribute("mod_code", mod_code);
		mode.addAttribute("user_id", user_id);
		
        return "PageOffice/printGridPre";  
    }
	
	/** 
	 * 查询列表打印-预览页面-保存打印格式（IE）
	 */
	@RequestMapping(value="/savePrintGridPre",method = RequestMethod.POST)
	@ResponseBody
    public void savePrintGridPre(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){
		
		/*if(map.get("page_url")==null ||map.get("page_url").equals("")){
			return JSONObject.parseObject("{\"error\":\"page_url页面路径为空！\"}");
		}
		
		if(map.get("mod_code")==null ||map.get("mod_code").equals("")){
			return JSONObject.parseObject("{\"error\":\"mod_code为空！\"}");
		}
		
		if(map.get("user_id")==null ||map.get("user_id").equals("")){
			return JSONObject.parseObject("{\"error\":\"user_id为空！\"}");
		}
		
		String temp_file_name=map.get("temp_file_name").toString();
		String toFilePath=request.getSession().getServletContext().getRealPath(temp_file_name);
		map.put("toFilePath", toFilePath);
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		try{
			resMap=myPageOfficeService.savePrintGridPre(map);
		}catch(Exception e){
			resMap.put("error", StringTool.string2Json(e.getMessage()==null?"生成数据异常！":e.getMessage()));
		}
		
        return resMap; 
		*/
		
//		Map params=request.getParameterMap();
//		Iterator<Object> it = params.keySet().iterator();
//		while(it.hasNext()){
//			String paramName = (String) it.next();
//			String paramValue = request.getParameter(paramName);
//			//处理你得到的参数名与值
//			System.out.println(paramName+"="+paramValue);
//		}
		
		FileSaver fs=new FileSaver(request,response);
		try{
			
			//保存文件
			String docPath=map.get("docPath").toString();
			ServletContext servletContext= request.getServletContext();
			fs.saveToFile(servletContext.getRealPath(docPath)+"/"+fs.getFileName());
			
			//保存数据
			String toFilePath=servletContext.getRealPath(docPath)+"/"+fs.getFileName();
			map.put("toFilePath", toFilePath);
			myPageOfficeService.savePrintGridPre(map);
			
			fs.setCustomSaveResult("ok");
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			fs.setCustomSaveResult("error");
		}finally{
			fs.close();
		}
		
    }
	/**
	 * 在线打开word文档
	 * */
	@RequestMapping(value="/showWord",method = RequestMethod.GET)
    public String showWord(HttpServletRequest request, Map<String,Object> map){
		
		String fileName= request.getParameter("paraName");

        PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
        
        WordDocument doc=new WordDocument();
        
        request.setAttribute("poCtrl", poCtrl);
        
        poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
        
        poCtrl.setWriter(doc);//将替换的标签注入文档中
        
        poCtrl.webOpen(request.getContextPath()+"/doc/"+fileName+".docx",OpenModeType.docAdmin,"");
        
        map.put("pageoffice",poCtrl.getHtmlCode("PageOfficeCtrl1"));
       
        return "PageOffice/word";
    }
	/**
	 * 在线预览
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="/showFile",method = RequestMethod.GET)
	public String showFile(HttpServletRequest request, Map<String,Object> map) throws UnsupportedEncodingException{
		
		String file_path = request.getParameter("file_path");
		///CHD-HRP/upLoad/10/24/pac/fkht/001/测试文件.doc
		String realPath = request.getSession().getServletContext().getRealPath("/");
		
		file_path = file_path.substring("/CHD_HRP".length(),file_path.length());
		
		String file_type  = file_path.substring(file_path.indexOf(".")+1, file_path.length());
		request.setAttribute("file_type", file_type);
		if ("pdf".equals(file_type) ) {
			PDFCtrl pdfCtrl = new PDFCtrl(request);
			request.setAttribute("poCtrl", pdfCtrl);
			
			pdfCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
			pdfCtrl.webOpen(realPath + file_path);
			map.put("pageoffice",pdfCtrl.getHtmlCode("PDFCtrl1"));
			
		}else {
			PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
			
			request.setAttribute("poCtrl", poCtrl);
			poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
			
			if ("doc".equals(file_type) || "docx".equals(file_type)) {
				poCtrl.webOpen(realPath + file_path ,OpenModeType.docAdmin,"");
			}else if("xls".equals(file_type) || "xlsx".equals(file_type)) {
				poCtrl.webOpen(realPath + file_path ,OpenModeType.xlsReadOnly,"");
			}
			map.put("pageoffice",poCtrl.getHtmlCode("PageOfficeCtrl1"));
		}
		
		return "PageOffice/word";
	}
	
	
	/** 
	 * 图片打印（IE）
	 */
	@RequestMapping(value="/printImg",method = RequestMethod.GET)
    public String printImg(@RequestParam Map<String, Object> map, Model mode,HttpServletRequest request, HttpServletResponse response){
		
		mode.addAttribute("imgs", map.get("imgs"));
		
        return "PageOffice/printImg";
    }
}



