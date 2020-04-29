package com.chd.hrp.flw.controller.flow;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.util.Plupload;
import com.chd.hrp.flw.entity.flow.ActReProcdef;
import com.chd.hrp.flw.service.flow.FlowDesignerService;

@Controller
@RequestMapping(value="/hrp/flw/flow")
public class FlowDesignerController extends BaseController{

	private static Logger logger = Logger.getLogger(FlowDesignerController.class);
	@Resource(name = "flowDesignerService")
	private final FlowDesignerService flowDesignerService = null;
	
	
	// 流程主页面跳转
	@RequestMapping(value = "/flowDesignerMainPage", method = RequestMethod.GET)
	public String flowDesignerMainPage(Model mode) throws Exception {

		return "hrp/flw/flow/designer/main";
	}
		
	// 流程设计页面跳转
	@RequestMapping(value = "/flowDesignerPage", method = RequestMethod.GET)
	public String flowDesignerPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(null!=mapVo && mapVo.size()>1){
	        ActReProcdef actReProcdef = new ActReProcdef();
	        actReProcdef =flowDesignerService.queryFlowDesignerByCode(mapVo) ;
			mode.addAttribute("deployment_id_", actReProcdef.getDeployment_id_());
			
			//String bpmnStr=flowDesignerService.queryFlowByBlob(mapVo);
			//mode.addAttribute("bpmnStr", bpmnStr);
			
		}
		return "hrp/flw/flow/designer/designer";
	}
	
	// 导入流程页面跳转
	@RequestMapping(value = "/flowImportPage", method = RequestMethod.GET)
	public String flowImportPage(Model mode) throws Exception {

		return "hrp/flw/flow/designer/import";
	}	
	
	
	// 流程设计页面加載bpmn
	@RequestMapping(value = "/flowDesignerBpmn", method = RequestMethod.POST)
	@ResponseBody
	public String flowDesignerBpmn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		String bpmnStr=flowDesignerService.queryFlowByBlob(mapVo);
			
		return bpmnStr;
	}
	
	// 查询最新版本的流程
	@RequestMapping(value = "/queryFlowActReProcdef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryFlowActReProcdef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String jsonStr=flowDesignerService.queryFlowActReProcdef(mapVo);
		
		return JSONObject.parseObject(jsonStr);
	}

	// 历史版本页面跳转
	@RequestMapping(value = "/queryFlowActReProcdefVersionPage", method = RequestMethod.GET)
	public String queryFlowActReProcdefVersionPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("key_", mapVo.get("key_"));
		mode.addAttribute("version_", mapVo.get("version_"));
		return "hrp/flw/flow/designer/version";
	}	
	
	//查询历史版本的流程类别
	@RequestMapping(value = "/queryFlowActReProcdefVersion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryFlowActReProcdefVersion(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		String jsonStr=flowDesignerService.queryFlowActReProcdefVersion(mapVo);
		return JSONObject.parseObject(jsonStr);
	}		
	
	//流程保存发布
	@RequestMapping(value = "/releaseFlowDesigner", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> releaseFlowDesigner(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		String jsonStr=flowDesignerService.releaseFlowDesigner(mapVo);
		return JSONObject.parseObject(jsonStr);
	}
	
	//流程保存停用
	@RequestMapping(value = "/cancelFlowDesigner", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> cancelFlowDesigner(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		String jsonStr=flowDesignerService.cancelFlowDesigner(mapVo);
		return JSONObject.parseObject(jsonStr);
	}
	
	//根据导入zip发布流程
	@RequestMapping(value = "/releaseFlowDesignerByZip", method = RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String releaseFlowDesignerByZip(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		String jsonStr=null;
		plupload.setRequest(request);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) plupload.getRequest();   
        MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
        String fileNameTemp = plupload.getName();//获取临时文件名
        String extension=fileNameTemp.substring(fileNameTemp.lastIndexOf(".")+1,fileNameTemp.length());//扩展名
        if(extension.equalsIgnoreCase("zip") || extension.equalsIgnoreCase("bpmn")){
            mapVo.put("extension", extension);
    		if(map != null && map.size()>0) {  
    			jsonStr=flowDesignerService.releaseFlowDesignerByZip(map.get("file").get(0),mapVo);
//    	            Iterator<String> iter = map.keySet().iterator();  
//    	            while(iter.hasNext()) {  
//    	                String str = (String) iter.next(); 
//    	                List<MultipartFile> fileList =  map.get(str); 
//    	                for(MultipartFile multipartFile : fileList) {  
//    	                	jsonStr=flowDesignerService.releaseFlowDesignerByZip(multipartFile,mapVo);
//    	                }
//    	            }
    	     }
        }else{
        	jsonStr="{\"showType\":\"error\",\"msg\":\"不是指定格式的文件\",\"fileId\":\""+mapVo.get("fileId")+"\"}";
        }

		return jsonStr;
	}
	
	//删除流程
	@RequestMapping(value = "/flowDeploymentDelete", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> flowDeploymentDelete(@RequestParam(value="paramVo") String paramVo,Model mode) throws Exception {
		
		String jsonStr=flowDesignerService.flowDeploymentDelete(paramVo);
		return JSONObject.parseObject(jsonStr);
	}
	
	
	//模拟运行
	@RequestMapping(value = "/runFlowDesignerTest", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> runFlowDesignerTest(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		String jsonStr=flowDesignerService.runFlowDesignerTest(mapVo);
		return JSONObject.parseObject(jsonStr);
	}

	//流程图页面跳转
	@RequestMapping(value="/flowViewImagePage",method=RequestMethod.GET)
	public String flowViewImagePage(@RequestParam(value="deploymentId") String deploymentId,Model mode) throws Exception{
		mode.addAttribute("deploymentId", deploymentId);
		//mode.addAttribute("dgrmResourceName", dgrmResourceName);
		return "hrp/flw/flow/designer/flowViewImage";
	}	
	
	//查看流程图
	@RequestMapping(value = "/flowViewImage", method = RequestMethod.GET)
	public ResponseEntity<byte[]> flowViewImage(@RequestParam(value="deploymentId") String deploymentId,Model mode,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
//		byte paramName[] = dgrmResourceName.getBytes("ISO-8859-1"); //以"ISO-8859-1"方式解析name字符串
//		dgrmResourceName= new String(paramName, "UTF-8"); //再用"utf-8"格式表示name
		InputStream in=flowDesignerService.flowViewImage(deploymentId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        byte[] buff = new byte[1024*8];  
        int rc = 0;  
        while ((rc = in.read(buff, 0, 100)) > 0) {  
            swapStream.write(buff, 0, rc);  
        }  
        byte[] returnBuff = swapStream.toByteArray();  
		
		return new ResponseEntity<byte[]>(returnBuff,headers, HttpStatus.CREATED);
	}
	
	//查询最新版本的流程类别
	@RequestMapping(value = "/queryFlowCategory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryFlowCategory(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		String jsonStr=flowDesignerService.queryFlowCategory(mapVo);
		return JSONObject.parseObject(jsonStr);
	}	
	
}
