package com.chd.hrp.flw.service.flow;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartFile;

import com.chd.hrp.flw.entity.flow.ActGeBytearray;
import com.chd.hrp.flw.entity.flow.ActReProcdef;

public interface FlowDesignerService {
	
	//查询最新版本的流程
	public String queryFlowActReProcdef(Map<String,Object> entityMap) throws DataAccessException;

	//查询历史版本的流程
	public String queryFlowActReProcdefVersion(Map<String,Object> entityMap) throws DataAccessException;
	
	//流程查询ByCode
	public ActReProcdef queryFlowDesignerByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	//查询流程bpmn文件
	public String queryFlowByBlob(Map<String,Object> entityMap) throws DataAccessException, UnsupportedEncodingException;
	
	//流程保存发布
	public String releaseFlowDesigner(Map<String,Object> entityMap)throws DataAccessException;
	
	//流程保存停用
	public String cancelFlowDesigner(Map<String,Object> entityMap)throws DataAccessException;
	
	//根据导入zip发布流程
	public String releaseFlowDesignerByZip(MultipartFile multipartFile,Map<String, Object> mapVo)throws DataAccessException;

	//删除流程
	public String flowDeploymentDelete(String paramVo)throws DataAccessException;
	
	//模拟运行
	public String runFlowDesignerTest(Map<String,Object> entityMap)throws DataAccessException;
	
	//查看流程图
	public InputStream flowViewImage(String deploymentId)throws DataAccessException;
	
	//查询最新版本的流程类别
	public String queryFlowCategory(Map<String, Object> mapVo)throws DataAccessException;
}
