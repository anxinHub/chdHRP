package com.chd.hrp.flw;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ActFlowTest {

	
	public static void main(String args[]) {
//		 //初始化spring上下文		
//		//ApplicationContext ctx = new ClassPathXmlApplicationContext(LoadSystemInfo.getProjectUrl() + "/WEB-INF/classes/activiti-conf.xml");   
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("F:/eclipse_workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/CHD-HRP/WEB-INF/classes/activiti-conf.xml");
//		//获得Activiti主接口
//		ProcessEngine processEngine = (ProcessEngine) ctx.getBean("processEngine"); 		
//		System.out.println("processEngine name :" + processEngine.getName());
//		
//		//获得资源操作接口
//		RepositoryService repositoryService = processEngine.getRepositoryService(); 	
//		//获得运行时操作接口（大多操作流程实例）
//		RuntimeService runtimeService = (RuntimeService) processEngine.getRuntimeService();	
//		//获得任务操作接口
//		TaskService taskService = processEngine.getTaskService();			
//		//获得身份操作接口（用户和用户组）
//		IdentityService identityService = processEngine.getIdentityService();		
//		//获得历史查询接口
//		HistoryService historyService = processEngine.getHistoryService();		
//		//获得表单数据绑定接口
//		FormService formService = processEngine.getFormService();			
//		//获得管理接口
//		ManagementService managementService = processEngine.getManagementService();	
//		
//		//部署流程定义
//		Deployment deployment = repositoryService.createDeployment().addClasspathResource("demo.bpmn").deploy();
//		System.out.print("发布流程定义成功！" + "  Id=" + deployment.getId() + ", Name=" + deployment.getName());
		
		
//		
//		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//			  // Get Activiti services
//			RepositoryService repositoryService = processEngine.getRepositoryService();
//			RuntimeService runtimeService = processEngine.getRuntimeService();
//			
//			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process1453806453457");
		
		
	}
}
