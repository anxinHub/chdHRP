package com.chd.hrp.flw.serviceImpl.instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.hrp.flw.dao.flow.FlowDesignerMapper;
import com.chd.hrp.flw.entity.flow.ActReProcdef;
import com.chd.hrp.flw.service.instance.FlowSendService;

@Service("flowSendService")
public class FlowSendServiceImpl implements FlowSendService{
	
	private static Logger logger = Logger.getLogger(FlowSendServiceImpl.class); 
	@Resource(name = "flowDesignerMapper")
	private final FlowDesignerMapper flowDesignerMapper = null;
	
	//新建事项页面发送工作流
	@Override
	public String sendFlow(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	//查询激活状态的流程类别、部署ID
	@Override
	public String queryFlowCategoryDeploymentId(Map<String, Object> mapVo)throws DataAccessException{
		mapVo.put("group_id", SessionManager.getGroupId());//集团ID
		mapVo.put("hos_id", SessionManager.getHosId());//医院ID
		
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<ActReProcdef> flowList=new ArrayList<ActReProcdef>(); 
		flowList=flowDesignerMapper.queryFlowCategoryDeploymentId(mapVo);
		if(flowList!=null && flowList.size()>0){
			int row = 0;
			int pId=0;
			for(ActReProcdef a:flowList){
				
				//类别
				if(a.getCategory_()!=null && !a.getCategory_().equals("") && jsonResult.toString().indexOf(a.getCategory_())==-1){
					if (row == 0) {
						jsonResult.append("{");
					} else {
						jsonResult.append(",{");
					}
					row++;
					pId=row;
					jsonResult.append("id:" + row + ",");
					jsonResult.append("name:'" + a.getCategory_()+ "',");
					jsonResult.append("pId:" + 0 + ",");
					jsonResult.append("deployment_id:\"\"" );
					jsonResult.append("}");
				}
				
				//流程
				if (row == 0) {
					jsonResult.append("{");
					jsonResult.append("pId:" + 0 + ",");
				} else {
					jsonResult.append(",{");
					jsonResult.append("pId:" + pId + ",");
				}
				row++;
				jsonResult.append("id:" + row + ",");
				jsonResult.append("name:'" + a.getName_()+ "',");
				jsonResult.append("pId:" + pId + ",");
				jsonResult.append("deployment_id:'" + a.getDeployment_id_()+"'" );
				jsonResult.append("}");
			}
		}
		
		jsonResult.append("]}");
		return jsonResult.toString();
	}
}
