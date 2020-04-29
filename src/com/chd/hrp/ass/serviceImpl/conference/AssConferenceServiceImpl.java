package com.chd.hrp.ass.serviceImpl.conference;

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
import com.chd.hrp.ass.dao.conference.AssConferenceMapper;
import com.chd.hrp.ass.entity.tend.AssTendFile;
import com.chd.hrp.ass.service.conference.AssConferenceService;
import com.github.pagehelper.PageInfo;

@Service("assConferenceService")
public class AssConferenceServiceImpl implements AssConferenceService {
  
	 private static Logger logger = Logger.getLogger(AssConferenceServiceImpl.class);
	 
	@Resource(name="assConferenceMapper")	
  private final	AssConferenceMapper assConferenceMapper=null;

 public String queryAssConference(Map<String, Object> mapVo)
			throws DataAccessException {
		try{
		SysPage sysPage=new SysPage();
		sysPage=(SysPage) mapVo.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list=assConferenceMapper.queryAssConference(mapVo);
			return ChdJson.toJsonLower(list);
		}else{
		RowBounds rowBounds=new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Map<String, Object>> list=assConferenceMapper.queryAssConference(mapVo,rowBounds);
        PageInfo page=new PageInfo(list);
		
		return ChdJson.toJsonLower(list, page.getTotal());
		}
		}catch(Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	/**
	 * 保存
	 */
	public String saveAssConference(Map<String, Object> mapVo)
			throws DataAccessException {
		try{
			///会议主表添加
			int res=assConferenceMapper.saveAssConference(mapVo);
			Long con_id=assConferenceMapper.queryAssConferenceSequence();
			///参会人员添加
			///获取参会人员ID
		if((mapVo.get("personelidsv")!=null)&&(!"".equals(mapVo.get("personelidsv")))){
			String personelids=mapVo.get("personelidsv").toString();
			
			List<Map<String, Object>> listvo=new ArrayList();
			  String[] arrayOfString;
			   int j = (arrayOfString = personelids.split(",")).length;
		      for(int i=0;i<j;i++){
		    	  Map<String, Object> mapPer = new HashMap();
		    	  mapPer.put("personelid", arrayOfString[i]);
		    	  mapPer.put("con_id", con_id);
		    	  if (mapPer.get("group_id") == null) {
		    		  mapPer.put("group_id", SessionManager.getGroupId());
		    	    }
		    	    if (mapPer.get("hos_id") == null) {
		    	    	mapPer.put("hos_id", SessionManager.getHosId());
		    	    }
		    	    if (mapPer.get("copy_code") == null) {
		    	    	mapPer.put("copy_code", SessionManager.getCopyCode());
		    	    }
		    	    
		    	  listvo.add(mapPer);
		      }
		      ///调用插入方法
		      assConferenceMapper.saveConferencePersonnel(listvo);
			}
			///获取缺席人员ID
		if((mapVo.get("personelidnsv")!=null)&&(!"".equals(mapVo.get("personelidnsv")))){
			String personelidns=mapVo.get("personelidnsv").toString();
			List<Map<String, Object>> listvon=new ArrayList();
			  String[] arrayOfString;
			   int j = (arrayOfString = personelidns.split(",")).length;
		      for(int i=0;i<j;i++){
		    	  Map<String, Object> mapPern = new HashMap();
		    	  mapPern.put("personelidn", arrayOfString[i]);
		    	  mapPern.put("con_id", con_id);
		    	  if (mapPern.get("group_id") == null) {
		    		  mapPern.put("group_id", SessionManager.getGroupId());
		    	    }
		    	    if (mapPern.get("hos_id") == null) {
		    	    	mapPern.put("hos_id", SessionManager.getHosId());
		    	    }
		    	    if (mapPern.get("copy_code") == null) {
		    	    	mapPern.put("copy_code", SessionManager.getCopyCode());
		    	    }
		    	    
		    	  listvon.add(mapPern);
		      }
		      
		      assConferenceMapper.saveConferencePersonneln(listvon);
		}
		
		///其他参会人员conp_otherpersonel
		if((mapVo.get("conp_otherpersonel")!=null)&&(!"".equals(mapVo.get("conp_otherpersonel")))){
			
			  mapVo.put("con_id", con_id);
		      assConferenceMapper.saveOtherPersonel(mapVo);
		}
			return "{\"msg\":\"添加成功\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
		
	}
	/**
	 * 根据ID查询
	 */
	public String queryAssConferenceByID(Map<String, Object> mapVo)
			throws DataAccessException {
		///根据ID查只会有一条值，这里为了使用toJsonLower这个方法，用了list
		Map<String, Object> map=assConferenceMapper.queryAssConferenceByID(mapVo);
		
		///考虑参会和缺席人员
		//参会人员
		List<Map<String, Object>> personelid=assConferenceMapper.querypersonelid(mapVo);
		String personelids="";
		String personelNames="";
		
		if((personelid!=null)&&(personelid.size()>0)){
		for (Map<String, Object> mapdet : personelid) {
			if("".equals(personelids)){
				personelids=mapdet.get("conp_personelid").toString();
				personelNames=mapdet.get("conp_personelname").toString();
			}else{
				personelids=personelids+","+mapdet.get("conp_personelid").toString();
				personelNames=personelNames+","+mapdet.get("conp_personelname").toString();
				
			}
		}
		map.put("personelids", personelids);
		map.put("personelNames", personelNames);
		}
		//缺席人员
		List<Map<String, Object>> personelidn=assConferenceMapper.querypersonelidn(mapVo);
		
		String personelidns="";
		String personelNamens="";
		if((personelidn!=null)&&(personelidn.size()>0)){

			for (Map<String, Object> mapdetn : personelidn) {
				if("".equals(personelidns)){
					personelidns=mapdetn.get("conp_personelid").toString();
					personelNamens=mapdetn.get("conp_personelname").toString();
			}else{
				personelidns=personelidns+","+mapdetn.get("conp_personelid").toString();
				personelNamens=personelNamens+","+mapdetn.get("conp_personelname").toString();
				
				}
			}
			map.put("personelidns", personelidns);
		    map.put("personelNamens", personelNamens);		
		}
		
		//其他参会人员
		Map<String, Object> othermap=assConferenceMapper.queryOtherPersonel(mapVo);
		if((othermap!=null)&&(!othermap.isEmpty())){			
			map.put("otherpersonel", othermap.get("conp_supplier"));
		}
		return JSON.toJSONString(map);
	}
	/**
	 * 更新方法
	 */
	public String updateAssConference(Map<String, Object> mapVo)
			throws DataAccessException {
		try{
			///更新会议主表
			int res=assConferenceMapper.updateAssConference(mapVo);
			
			///下面考虑参会人员（插入新增的参会人员）
			///先删除所有从新添加
			assConferenceMapper.deleteConferencePersonnel(mapVo);
			if((mapVo.get("personelidsv")!=null)&&(!"".equals(mapVo.get("personelidsv")))){
				String personelids=mapVo.get("personelidsv").toString();
				
				List<Map<String, Object>> listvo=new ArrayList();
				  String[] arrayOfString;
				   int j = (arrayOfString = personelids.split(",")).length;
			      for(int i=0;i<j;i++){
			    	  Map<String, Object> mapPer = new HashMap();
			    	  mapPer.put("personelid", arrayOfString[i]);
			    	  mapPer.put("con_id", mapVo.get("con_id"));
			    	  if (mapPer.get("group_id") == null) {
			    		  mapPer.put("group_id", SessionManager.getGroupId());
			    	    }
			    	    if (mapPer.get("hos_id") == null) {
			    	    	mapPer.put("hos_id", SessionManager.getHosId());
			    	    }
			    	    if (mapPer.get("copy_code") == null) {
			    	    	mapPer.put("copy_code", SessionManager.getCopyCode());
			    	    }
			    	    
			    	  listvo.add(mapPer);
			      }
			      ///调用插入方法
			      assConferenceMapper.saveConferencePersonnel(listvo);
				}
				///获取缺席人员ID
			if((mapVo.get("personelidnsv")!=null)&&(!"".equals(mapVo.get("personelidnsv")))){
				String personelidns=mapVo.get("personelidnsv").toString();
				List<Map<String, Object>> listvon=new ArrayList();
				  String[] arrayOfString;
				   int j = (arrayOfString = personelidns.split(",")).length;
			      for(int i=0;i<j;i++){
			    	  Map<String, Object> mapPern = new HashMap();
			    	  mapPern.put("personelidn", arrayOfString[i]);
			    	  mapPern.put("con_id", mapVo.get("con_id"));
			    	  if (mapPern.get("group_id") == null) {
			    		  mapPern.put("group_id", SessionManager.getGroupId());
			    	    }
			    	    if (mapPern.get("hos_id") == null) {
			    	    	mapPern.put("hos_id", SessionManager.getHosId());
			    	    }
			    	    if (mapPern.get("copy_code") == null) {
			    	    	mapPern.put("copy_code", SessionManager.getCopyCode());
			    	    }
			    	    
			    	  listvon.add(mapPern);
			      }
			      
			      assConferenceMapper.saveConferencePersonneln(listvon);
			}
			
			///其他参会人员conp_otherpersonel
			if((mapVo.get("conp_otherpersonel")!=null)&&(!"".equals(mapVo.get("conp_otherpersonel")))){
				
				
			      assConferenceMapper.saveOtherPersonel(mapVo);
			}
			return "{\"msg\":\"更新成功\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	
	}
	@Override
	public String deleteConference(Map<String, Object> mapVo)
			throws DataAccessException {
		try{
			
			///删除会议主表
			int res=assConferenceMapper.deleteAssConference(mapVo);
			
			///下面考虑人员表记录删除
			assConferenceMapper.deleteConferencePersonnel(mapVo);
			return "{\"msg\":\"删除成功\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String deleteConferencePerson(Map<String, Object> mapVo)
			throws DataAccessException {
		try{
			
			int res=assConferenceMapper.deleteConferencePerson(mapVo);
			return "{\"msg\":\"删除成功\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
		
	}

	
	/**
	 * 附件上传会写文件表
	 */
	public String addAssTendFile(Map<String, Object> mapVo)
			throws DataAccessException {
		assConferenceMapper.addAssTendFile(mapVo);
		return null;
	}
	/**
	 * 查询附件（判断是否存在）
	 */
	public List<AssTendFile> queryAssTendFileExit(Map<String, Object> mapVo)
			throws DataAccessException {
		List<AssTendFile> list=assConferenceMapper.queryAssTendFile(mapVo);
		return list;
	}
	
	/**
	 * 
	 */
	public String queryAssTendFile(Map<String, Object> mapvo) {
		SysPage sysPage=new SysPage();
		 sysPage = (SysPage)mapvo.get("sysPage");
		 
		RowBounds rowBounds=new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<AssTendFile> list=assConferenceMapper.queryAssTendFile(mapvo, rowBounds);
		
		PageInfo page=new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
	}
	/**
	 * 删除文件
	 */
	public String deleteTendFile(Map<String, Object> mapVo)
			throws DataAccessException {
		 try
		    {
			 
		    int state=assConferenceMapper.deleteTendFile(mapVo);
		      return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		    }
		    catch (Exception e)
		    {
		      logger.error(e.getMessage(), e);
		    }
		    return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteTend\"}";

	}
}
