package com.chd.hrp.sys.serviceImpl.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.hrp.sys.dao.portal.PortalMapper;
import com.chd.hrp.sys.service.portal.PortalService;

@Service("portalService")
public class PortalServicerImpl implements PortalService {

	private static Logger logger = Logger.getLogger(PortalServicerImpl.class);
	
	@Resource(name = "portalMapper")
	private final PortalMapper portalMapper = null ;
	
	/**
	 * 物流模块  栏目信息查询 
	 * @param entityMap
	 * @return
	 */
	@Override
	public String querySysPortalInfo(Map<String,Object> entityMap) throws DataAccessException{
		Long count = portalMapper.querySysPortalTitleUserCount(entityMap);
		List<Map<String,Object>> portalTitleInfoList = null;
		if(count > 0){
			portalTitleInfoList = portalMapper.querySysPortalTitleUser(entityMap);
		}else{
			portalTitleInfoList = portalMapper.querySysPortalTitleSet(entityMap);
		}
		return JSONArray.toJSONString(portalTitleInfoList);
	}
	/**
	 * 物流模块  查询门户栏目 
	 * @param entityMap
	 * @return
	 */
	@Override
	public String querySysPortalTitle(Map<String, Object> mapVo)	throws DataAccessException {
		
		List<Map<String, Object>> list = portalMapper.querySysPortalTitle(mapVo);
		
		return JSONArray.toJSONString(list);
	}
	@Override
	public String querySysShowPortalInfo(Map<String, Object> entityMap) throws DataAccessException {
		Long count = portalMapper.querySysPortalTitleUserCount(entityMap);
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> portalTitleInfoList = null;
		if(count > 0){
			portalTitleInfoList = portalMapper.querySysPortalTitleUser(entityMap);
		}else{
			portalTitleInfoList = portalMapper.querySysPortalTitleSet(entityMap);
		}
		return JSONArray.toJSONString(portalTitleInfoList);
	}
	@Override
	public String querySysShowPortalData(Map<String, Object> temp) throws DataAccessException { 
		Map<String, Object> entityMap = new HashMap<String, Object>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
			if(temp.containsKey("view_code")){
				String view_code = temp.get("view_code").toString();
				String view_column = temp.get("view_column").toString();
				String view_where = temp.get("view_where").toString();
				String show_rows = temp.get("show_rows").toString();
				String title_code = temp.get("title_code").toString();
				
				String sql = "select "+view_column+" from "+view_code+" v where "+view_where;
				entityMap.put("show_rows", show_rows);
				entityMap.put("sql", sql);
				entityMap.put("group_id", SessionManager.getGroupId());
				entityMap.put("hos_id", SessionManager.getHosId());
				entityMap.put("copy_code", SessionManager.getCopyCode());
				entityMap.put("mod_code", SessionManager.getModCode());

				List<Map<String,Object>> warningList = portalMapper.executeViewSql(entityMap);
				returnMap.put(title_code, warningList);
			}
		return JSON.toJSONString(returnMap);
	}
	
	//查询组织结构图
	@Override
	public String queryOrgChart(Map<String, Object> map)throws DataAccessException{
	
		//typeCode：0医院用户，1集团用户，2超级管理员，3集团管理员，4医院管理员
		String typeCode=SessionManager.getTypeCode();
		String userCode=SessionManager.getUserCode();
		String userName=SessionManager.getUserName();
		String groupId=SessionManager.getGroupId();
		String hosId=SessionManager.getHosId();
		
		StringBuilder json=new StringBuilder();
		List<Map<String,Object>> groupList=null;
		List<Map<String,Object>> hosList=null;
		
		try{
			
			String name="系统管理员";
			if(map.get("type").toString().equals("2")){
				name="系统平台";
			}else if(map.get("type").toString().equals("3")){
				name="组织架构";
			}
			
			json.append("{");
			json.append("\"name\":\"admin\",");
			json.append("\"title\":\""+name+"\",");
			json.append("\"children\":[");
			
			if(typeCode.equals("0") || typeCode.equals("4")){
				//0医院用户
				map.put("group_id", groupId);
				map.put("hos_id", hosId);
			}else if(typeCode.equals("1") || typeCode.equals("3")){
				//1集团用户
				map.put("group_id", groupId);
			}
			
			//查询集团
			groupList=portalMapper.queryOrgChartByGroup(map);
			//查询医院
			hosList=portalMapper.queryOrgChartByHos(map);
					
			List<Map<String,Object>> lastList=null;
			if(map.get("type").toString().equals("1")){
				//查询管理员
				lastList=portalMapper.queryOrgChartByUser(map);
			}else if(map.get("type").toString().equals("2")){
				//查询账套
				lastList=portalMapper.queryOrgChartByCopy(map);
			}else{
				//查询科室
				lastList=portalMapper.queryOrgChartByDept(map);
			}
			
			//集团
			if(groupList!=null && groupList.size()>0){
				
				for(Map<String,Object> g:groupList){
					String qGroupId=g.get("group_id").toString();
					json.append("{");
					json.append("\"name\":\""+g.get("name").toString().split("-")[0]+"\",");
					json.append("\"title\":\""+g.get("title").toString()+"\",");
					json.append("\"className\":\"group\",");
					json.append("\"children\":[");
					//医院
					if(hosList!=null && hosList.size()>0){
						for(Map<String,Object> h:hosList){
							String qhosId=h.get("hos_id").toString();
							
							if(!qGroupId.equals(h.get("group_id").toString())){
								continue;
							}
							
							json.append("{");
							json.append("\"name\":\""+h.get("name").toString().split("-")[0]+"\",");
							json.append("\"title\":\""+h.get("title").toString()+"\",");
							json.append("\"className\":\"hos\",");
							json.append("\"children\":[");
							
							if(map.get("type").toString().equals("1")){
								//管理员
								getUserLast(json,qGroupId,qhosId,lastList,h.get("name").toString().split("-")[1]);
							}else{
								//账套、科室
								getCopyDeptLast(json,qGroupId,qhosId,lastList,map);
							}
							
							
							json.append("]},");
						}
					}
					//集团没有医院，直接挂业务
					if(map.get("type").toString().equals("1")){
						//管理员
						getUserLast(json,qGroupId,"0",lastList,g.get("name").toString().split("-")[1]);
					}else{
						//账套、科室
						getCopyDeptLast(json,qGroupId,"0",lastList,map);
					}
					json.append("]}");
				}
			}
			
			json.append("]}");
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
		return replace(json.toString());
	}
	
	//账套、科室
	private void getCopyDeptLast(StringBuilder json,String qGroupId,String qhosId,List<Map<String,Object>> lastList,Map<String,Object> map){
		
		if(lastList!=null && lastList.size()>0){
			
			for(Map<String,Object> c:lastList){
				
				if(!qGroupId.equals(c.get("group_id").toString()) || !qhosId.equals(c.get("hos_id").toString())){
					continue;
				}
				
				if(map.get("type").toString().equals("3") && !c.get("dept_level").toString().equals("1")){
					continue;
				}
				
				json.append("{");
				json.append("\"id\":\""+c.get("name").toString()+"\",");
				json.append("\"name\":\""+c.get("name").toString()+"\",");
				json.append("\"title\":\""+c.get("title").toString()+"\",");
				json.append("\"className\":\"last\"");
				
				if(map.get("type").toString().equals("3")){
					getDeptChild(json,qGroupId,qhosId,lastList,c.get("name").toString());
					
				}
				json.append("},");
					
			}
		}
	}
	
	//递归取下级科室
	private void getDeptChild(StringBuilder json,String qGroupId,String qhosId,List<Map<String,Object>> lastList,String deptCode){
		
		json.append(",\"children\":[");
		for(Map<String,Object> c:lastList){
			if(!qGroupId.equals(c.get("group_id").toString()) || !qhosId.equals(c.get("hos_id").toString())){
				continue;
			}
			
			if(!deptCode.equals(c.get("super_code").toString())){
				continue;
			}
			
			json.append("{");
			json.append("\"name\":\""+c.get("name").toString()+"\",");
			json.append("\"title\":\""+c.get("title").toString()+"\",");
			json.append("\"className\":\"last\"");
			getDeptChild(json,qGroupId,qhosId,lastList,c.get("name").toString());
			json.append("},");
			
		}
		
		json.append("]");
		
	}
	
	
	//管理员用户
	private void getUserLast(StringBuilder json,String qGroupId,String qhosId,List<Map<String,Object>> lastList,String userId){
		
		if(lastList!=null && lastList.size()>0){
			
			for(Map<String,Object> c:lastList){
				
				if(!qGroupId.equals(c.get("group_id").toString()) || !qhosId.equals(c.get("hos_id").toString())){
					continue;
				}
				
				if(c.get("sj_id")==null || userId.equals(c.get("sj_id").toString())){
					json.append("{");
					json.append("\"id\":\""+c.get("name").toString()+"\",");
					json.append("\"name\":\""+c.get("name").toString()+"\",");
					json.append("\"title\":\""+c.get("title").toString()+"\",");
					json.append("\"className\":\"last\"");
					getUserChild(json,qGroupId,qhosId,lastList,c.get("user_id").toString());
						
					json.append("},");
				}
					
			}
		}
	}
	
	//递归取下级用户
	private void getUserChild(StringBuilder json,String qGroupId,String qhosId,List<Map<String,Object>> lastList,String userId){
		
		json.append(",\"children\":[");
		for(Map<String,Object> c:lastList){
			if(!qGroupId.equals(c.get("group_id").toString()) || !qhosId.equals(c.get("hos_id").toString())){
				continue;
			}
			
			if(c.get("sj_id")==null || !userId.equals(c.get("sj_id").toString())){
				continue;
			}
			
			json.append("{");
			json.append("\"name\":\""+c.get("name").toString()+"\",");
			json.append("\"title\":\""+c.get("title").toString()+"\",");
			json.append("\"className\":\"last\"");
			getUserChild(json,qGroupId,qhosId,lastList,c.get("user_id").toString());
			json.append("},");
			
		}
		
		json.append("]");
		
	}
	
	//递归替换children\":[]
	private String replace(String json){
		
		if(json.indexOf(",\"children\":[]")!=-1){
			json=json.replace(",\"children\":[]", "");
			replace(json);
		}
		return json;
	}
}
