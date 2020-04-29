package com.chd.hrp.flw.service.instance;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface FlowSendService {

	//发送流程
	public String sendFlow(Map<String, Object> mapVo)throws DataAccessException;
	
	//查询激活状态的流程类别、部署ID
	public String queryFlowCategoryDeploymentId(Map<String, Object> mapVo)throws DataAccessException;
}
