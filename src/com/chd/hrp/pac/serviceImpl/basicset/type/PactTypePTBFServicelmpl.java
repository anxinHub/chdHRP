package com.chd.hrp.pac.serviceImpl.basicset.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.github.pagehelper.PageInfo;
import com.chd.hrp.pac.service.basicset.type.PactTypePTBFService;
import com.chd.hrp.pac.dao.basicset.type.PactTypePTBFMapper;

@Service("pactTypePTBFService")
public class PactTypePTBFServicelmpl implements PactTypePTBFService {
	
	
	private static Logger logger= Logger.getLogger(PactTypePTBFServicelmpl.class);
	
	@Resource(name="pactTypePTBFMapper")
	
	private final PactTypePTBFMapper pactTypePTBFMapper=null;
	
	
	 public String queryPactPtbf(Map<String ,Object>mapVo) throws DataAccessException{
		 
		 try{
			 
			 SysPage sysPage=new SysPage();
				sysPage=(SysPage) mapVo.get("sysPage");
			 if (sysPage.getTotal() == -1){
				 List<Map<String,Object>> list=pactTypePTBFMapper.queryPactPtbf(mapVo);
					return ChdJson.toJsonLower(list);
				 
			 }else {
				 
				 RowBounds rowBounds=new RowBounds(sysPage.getPage(), sysPage.getPagesize());
					
			      List<Map<String, Object>> list=  pactTypePTBFMapper.queryPactPtbf(mapVo, rowBounds);
				 PageInfo page=new PageInfo(list);
					
					return ChdJson.toJsonLower(list, page.getTotal());
				 
			 }
			 
		 }catch(Exception e){
			 
			 logger.warn(e.getMessage(),e);
			 throw new SysException(e.getMessage(),e);
		 }
		 
		 
	 }
	 
	 /**
	  * 保存方法
	  * 
	  */
	 public String savePacPtbfAction   (Map<String ,Object>mapVo) throws DataAccessException{
		 //新增按钮
		
		 try{
			 Long BB_Rowid=pactTypePTBFMapper.queryBBID(mapVo); //获取按钮函数id
			if(BB_Rowid==null){
				
				int ret= pactTypePTBFMapper.savebbidmethod(mapVo); //增加按钮生成id
				Long bbrowid=pactTypePTBFMapper.queryBBID(mapVo); //获取按钮函数id
				mapVo.put("BB_Rowid", bbrowid);
				
				if (mapVo.get("group_id") == null) {
		    		  mapVo.put("group_id", SessionManager.getGroupId());
		    	    }
		    	    if (mapVo.get("hos_id")== null) {
		    	    	mapVo.put("hos_id", SessionManager.getHosId());
		    	    }
		    	    if (mapVo.get("copy_code") == null) {
		    	    mapVo.put("copy_code", SessionManager.getCopyCode());
		    	    }
		    	int res=pactTypePTBFMapper.savePacPtbfAction(mapVo);
		    	return "{\"msg\":\"添加成功\",\"state\":\"true\"}";
				
			}else{
				
				mapVo.put("BB_Rowid", BB_Rowid);
				
				if (mapVo.get("group_id") == null) {
		    		  mapVo.put("group_id", SessionManager.getGroupId());
		    	    }
		    	    if (mapVo.get("hos_id")== null) {
		    	    	mapVo.put("hos_id", SessionManager.getHosId());
		    	    }
		    	    if (mapVo.get("copy_code") == null) {
		    	    mapVo.put("copy_code", SessionManager.getCopyCode());
		    	    }
		    	int ret=pactTypePTBFMapper.savePacPtbfAction(mapVo);
		    	return "{\"msg\":\"添加成功\",\"state\":\"true\"}";
			}
			
			 
		 }catch(Exception e){
			 
			 logger.warn(e.getMessage(), e);
			 throw new SysException(e.getMessage(),e);
		 }
		 
	 }
	 
	 /**更新方法
	  * 
	  * 
	  * 
	  */
	 public String updatePacPtbfAction (Map<String ,Object>mapVo) throws DataAccessException{
		 
		 try{
			 
			 int res=pactTypePTBFMapper.updatePacPtbfAction(mapVo);
			 
			 return "{\"msg\":\"更新成功\",\"state\":\"true\"}";
		 }catch(Exception e){
			 
			 logger.warn(e.getMessage(),e);
			 throw new SysException(e.getMessage(),e);
			 
		 }
		 
		 
	 }
	 
	 /**
	  * 
	  * 删除方法
	  * 
	  * 
	  */
	 public String deletePacPtbfAction(Map<String ,Object> mapVo) throws DataAccessException{
		 
		 try{
			 
			 int res=pactTypePTBFMapper.deletePacPtbfAction(mapVo);
				
				
				return "{\"msg\":\"删除成功\",\"state\":\"true\"}";
		 }catch(Exception e){
			 
			 logger.warn(e.getMessage(),e);
			 throw new SysException (e.getMessage(),e);
		 }
		 
		 
	 }
	 
	 /**
	  * 根据ID查询
	  */
	 
	 public String queryPactPtbfId(Map<String ,Object>mapVo) throws DataAccessException{
		 
		 try{ 
			 Map<String, Object> map=pactTypePTBFMapper.queryPactPtbfId(mapVo);
		 return JSON.toJSONString(map);
			 
		 }catch(Exception e){
			 
			 
			 logger.warn(e.getMessage(), e);
			 throw new SysException (e.getMessage(),e);
		 }
		
		 
	 }
}
