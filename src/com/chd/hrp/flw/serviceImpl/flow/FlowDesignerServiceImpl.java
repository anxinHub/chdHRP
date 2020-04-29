package com.chd.hrp.flw.serviceImpl.flow;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;
import javax.annotation.Resource;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.flw.dao.flow.ActGeBytearrayMapper;
import com.chd.hrp.flw.dao.flow.FlowDesignerMapper;
import com.chd.hrp.flw.entity.flow.ActGeBytearray;
import com.chd.hrp.flw.entity.flow.ActReProcdef;
import com.chd.hrp.flw.service.flow.FlowDesignerService;

@Service("flowDesignerService")
public class FlowDesignerServiceImpl implements FlowDesignerService{

	private static Logger logger = Logger.getLogger(FlowDesignerServiceImpl.class);
	@Resource(name = "flowDesignerMapper")
	private final FlowDesignerMapper flowDesignerMapper = null;
	
	//查询最新版本的流程
	@Override
	public String queryFlowActReProcdef(Map<String, Object> entityMap)
			throws DataAccessException {
		String jsonStr=null;
		entityMap.put("group_id", SessionManager.getGroupId());//集团ID
		entityMap.put("hos_id", SessionManager.getHosId());//医院ID
		jsonStr=ChdJson.toJson(flowDesignerMapper.queryFlowActReProcdef(entityMap));
		
		return jsonStr;
	}
	
	//查询历史版本的流程
	@Override
	public String queryFlowActReProcdefVersion(Map<String, Object> entityMap)
			throws DataAccessException {
		String jsonStr=null;
		entityMap.put("group_id", SessionManager.getGroupId());//集团ID
		entityMap.put("hos_id", SessionManager.getHosId());//医院ID
		jsonStr=ChdJson.toJson(flowDesignerMapper.queryFlowActReProcdefVersion(entityMap));
		
		return jsonStr;
	}
	
	//流程查询ByCode
	@Override
	public ActReProcdef queryFlowDesignerByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return flowDesignerMapper.queryFlowActReProcdefByCode(entityMap);
	}
	
	//查询流程bpmn文件
	@Override
	public String queryFlowByBlob(Map<String, Object> entityMap)
			throws DataAccessException, UnsupportedEncodingException {
		ActGeBytearray actGeBytearray=new ActGeBytearray();
		entityMap.put("generated_", "0");
		entityMap.put("group_id", SessionManager.getGroupId());//集团ID
		entityMap.put("hos_id", SessionManager.getHosId());//医院ID
		actGeBytearray=flowDesignerMapper.queryFlowByBlob(entityMap);
		byte[] bytes=actGeBytearray.getBytes_();
		String bpmnStr=null;
		if(null!=bytes && bytes.length>0){
			bpmnStr=new String(bytes,"UTF-8");
		}
		
		return bpmnStr;
		
	}
	
	
	//流程保存发布
	@Override
	public String releaseFlowDesigner(Map<String, Object> entityMap)
			throws DataAccessException {
		String jsonStr=null;
	/*	File file=new File(LoadSystemInfo.getProjectUrl() +"WEB-INF\\classes\\bpmn\\demo.bpmn");    
		if(!file.exists()){    
		   try {    
		        file.createNewFile();    
		    } catch (IOException e) {    
		        // TODO Auto-generated catch block    
		        e.printStackTrace();    
		    }    
		} */
		
		
		try {
			
			//获得Activiti主接口
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			// 获取仓库服务
			RepositoryService repositoryService = processEngine.getRepositoryService();
			
			//部署单个流程定义
			Deployment deployment=repositoryService
						.createDeployment()// 创建一个部署对象 
						.name("我的BPMN")//部署名称
						.addClasspathResource("flw/bpmn/demo.bpmn")// 从classpath的资源中加载，一次只能加载一个文件 
						.deploy();// 完成部署
			
			logger.debug("流程发布成功！" + "  Id=" + deployment.getId() + ", Name=" + deployment.getName());	
			
			//验证已部署流程定义
//			ProcessDefinitionQuery processDefinitionQuery=repositoryService.createProcessDefinitionQuery().latestVersion();
//			List<ProcessDefinition> pds = processDefinitionQuery.list();
////			  // 遍历集合，查看内容
//			for (ProcessDefinition pd : pds) {
//				  System.out.print("id:" + pd.getId() +",");
//				  System.out.print("name:" + pd.getName() +",");
//				  System.out.print("key:" + pd.getKey() +",");
//				  System.out.println("version:" + pd.getVersion());
//			}
//			
////			//启动流程并返回流程实例
//			RuntimeService runtimeService=processEngine.getRuntimeService();
//			ProcessInstance processInstance=runtimeService.startProcessInstanceByKey(pds.get(0).getKey());
//			
			//logger.debug("流程发布成功！" + "  Id=" + processInstance.getId() + ", pdid=" + processInstance.getProcessDefinitionId());	
			
			jsonStr="{\"showType\":\"info\",\"msg\":\"流程发布成功！\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		
		if(jsonStr==null){
			jsonStr="{\"showType\":\"error\",\"msg\":\"流程发布失败！\"}";
		}
		
		return jsonStr;
	}

	//流程保存停用
	@Override
	public String cancelFlowDesigner(Map<String, Object> entityMap) {  
	   String jsonStr=null;
		try {
			//flowDesignerMapper.queryFlowActReProcdefByCode(entityMap);
		//	jsonStr=flowDeploymentDelete(entityMap.get("deployment_id_").toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		if(jsonStr==null){
			jsonStr="{\"showType\":\"error\",\"msg\":\"流程保存失败！\"}";
		}
		
	    return jsonStr;
	}  

	
	//根据导入发布流程
	@Override
	public String releaseFlowDesignerByZip(MultipartFile multipartFile,Map<String, Object> mapVo) {  
		String jsonStr=null;
		
		try {
			String fileName=mapVo.get("fileName").toString();
			//获得Activiti主接口
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			// 获取仓库服务
			RepositoryService repositoryService = processEngine.getRepositoryService();
			DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();// 创建一个部署对象  
			
			if(mapVo.get("extension").toString().equalsIgnoreCase("zip")){
				//根据导入zip，批量部署流程定义
				//InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip"); 
				ZipInputStream zipInputStream = new ZipInputStream(multipartFile.getInputStream());
				deploymentBuilder.addZipInputStream(zipInputStream);
			}else{
				//其他类型的文件
				deploymentBuilder.addInputStream(mapVo.get("fileName").toString(), multipartFile.getInputStream());
			}
			deploymentBuilder.name(fileName.substring(0,fileName.lastIndexOf(".")));//部署名称
			//deploymentBuilder.category("部署类别");//部署类别,暂时备用
			deploymentBuilder.tenantId(SessionManager.getUserId());//用户ID
			Deployment deployment=deploymentBuilder.deploy();// 完成部署  
			
			ProcessDefinitionQuery processDefinitionQuery=repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId());
			if(processDefinitionQuery!=null && processDefinitionQuery.list().size()>0){
				jsonStr="{\"showType\":\"info\",\"msg\":\"导入成功！\",\"fileId\":\""+mapVo.get("fileId")+"\"}";
				logger.debug("导入成功！" + "  Id=" + deployment.getId() + ", Name=" + deployment.getName()+", fileId="+mapVo.get("fileId"));	
			}else{
				jsonStr="{\"showType\":\"error\",\"msg\":\"导入失败，流程格式不符合！\",\"fileId\":\""+mapVo.get("fileId")+"\"}";
				logger.debug("导入失败！" + "  Id=" + deployment.getId() + ", Name=" + deployment.getName()+", fileId="+mapVo.get("fileId"));	
				repositoryService.deleteDeployment(deployment.getId(), true);
			}
			
		} catch (Exception e) {
			jsonStr="{\"showType\":\"error\",\"msg\":\""+StringTool.string2Json(e.getMessage())+"\",\"fileId\":\""+mapVo.get("fileId")+"\"}";
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
	  
	    return jsonStr;
	}

	
	//删除流程
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String flowDeploymentDelete(String paramVo){
		String jsonStr=null;
		String deploymentIdCatch=null;
		try {
			//获得Activiti主接口
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			// 获取仓库服务
			RepositoryService repositoryService = processEngine.getRepositoryService();
			
			
			for ( String deploymentId: paramVo.split(",")) {
				deploymentIdCatch=deploymentId;
				repositoryService.deleteDeployment(deploymentId, false);//false,普通删除，如果当前规则下有正在执行的流程，则抛异常。true,级联删除,会删除和当前规则相关的所有信息，包括历史
			}
			jsonStr="{\"state\":\"ok\",\"tipMsg\":\"删除成功！\"}";
		} catch (Exception e) {
			//ORA-02292: 违反完整约束条件 (HBI.ACT_FK_EXE_PROCDEF) - 已找到子记录
			if(e.getMessage().indexOf("ORA-02292")!=-1){
				jsonStr="{\"state\":\"error\",\"error\":\""+deploymentIdCatch+"流程正在执行，删除失败！\"}";
			}else{
				jsonStr="{\"state\":\"error\",\"error\":\""+deploymentIdCatch+"流程删除失败！\"}";
			}
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
	
		return jsonStr;
	}	
	
	
	//查看流程图片
	@Override
	public InputStream flowViewImage(String deploymentId){
		  InputStream in=null;
		  try {
			  //获得Activiti主接口
			  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			  // 创建仓库服务对象
			  RepositoryService repositoryService = processEngine.getRepositoryService();
			  // 从仓库中找需要展示的文件，为了解决中文名称乱码又从数据库查一遍文件名称
			  List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
			  String imageName = null;
			  if(names!=null && names.size()>0){
				  for (String name : names) {
					  if(name.indexOf(".png")>=0){
					    imageName = name;
					  }
				  }
				  if(imageName!=null){
				   // 通过部署ID和文件名称得到文件的输入流
					  in =  repositoryService.getResourceAsStream(deploymentId, imageName);
					//  File f = new File("e:/"+ imageName);
					//  FileUtils.copyInputStreamToFile(in, f);
				  }
			  }
			  
		  } catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
		}
		return in;
	}
	
	//查看流程定义
	public void queryProcessDefinition(String key) throws Exception {
		  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		  // 获取仓库服务对象
		  RepositoryService repositoryService = processEngine.getRepositoryService();
//		  // 获取流程定义查询对象
//		  ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		  DeploymentQuery processDefinitionQuery = repositoryService.createDeploymentQuery();
//		  // 配置查询对象
//	//	  processDefinitionQuery
//		    //添加过滤条件
////			  .processDefinitionName(processDefinitionName)
////			  .processDefinitionId(processDefinitionId)
////			  .processDefinitionKey(key)
//		    //分页条件
//			  //.listPage(1, 100);
//		    //排序条件
//		   // .orderByProcessDefinitionVersion().desc();
//		  /**
//		  * 执行查询
//		  * list : 执行后返回一个集合
//		  * singelResult 执行后，首先检测结果长度是否为1，如果为一则返回第一条数据；如果不唯一，抛出异常
//		  * count： 统计符合条件的结果数量
//		  */
		  List<Deployment> pds = processDefinitionQuery.list();//;.orderByDeploymentId().asc().list();
//		  // 遍历集合，查看内容
		  for (Deployment pd : pds) {
			  System.out.print("id:" + pd.getId() +",");
			  System.out.print("name:" + pd.getName() +",");
//			  System.out.print("key:" + pd.getKey() +",");
//			  System.out.println("version:" + pd.getVersion());
		  }
	}

	//查询最新版本的流程类别
	@Override
	public String queryFlowCategory(Map<String, Object> mapVo)throws DataAccessException{
		mapVo.put("group_id", SessionManager.getGroupId());//集团ID
		mapVo.put("hos_id", SessionManager.getHosId());//医院ID
		
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<String> flowList=new ArrayList<String>(); 
		flowList=flowDesignerMapper.queryFlowCategory(mapVo);
		if(flowList!=null && flowList.size()>0){
			int row = 0;
			for(String a:flowList){
				
				if (row == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				row++;
				jsonResult.append("id:" + row + ",");
				jsonResult.append("name:'" + a+ "',");
				jsonResult.append("pId:" + 0 );
				jsonResult.append("}");
			
			}
		}
		
		jsonResult.append("]}");
		return jsonResult.toString();
	}
	
	//模拟运行
	@Override
	public String runFlowDesignerTest(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	
		  // Create Activiti process engine
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

		  // Get Activiti services
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process1453806453457");
		
		
		TaskService taskService = processEngine.getTaskService();
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();
		
		try {
			//flowDeploymentDelete("55001",false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "{\"msg\":\"运行成功！\"}";
	}

}
