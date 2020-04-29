package com.chd.hrp.hr.serviceImpl.organize;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;






import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;


import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.organize.HrDeptViewMapper;



import com.chd.hrp.hr.entity.base.HrAccDeptAttr;
import com.chd.hrp.hr.service.organize.HrDeptViewService;
import com.chd.hrp.sys.entity.Dept;
import com.github.pagehelper.PageInfo;



/**
 * 部门架构
 * 
 * @author Administrator
 *
 */
@Service("hrDeptViewService")
public class HrDeptViewServiceImpl implements HrDeptViewService {

	private static Logger logger = Logger.getLogger(HrDeptViewServiceImpl.class);

	@Resource(name = "hrDeptViewMapper")
	private final HrDeptViewMapper hrDeptViewMapper= null;

	
	
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);



	@Override
	public String queryDeptViewOrgSet(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("user_id", SessionManager.getUserId());
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Dept> list = (List<Dept>) hrDeptViewMapper.query(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());
	}


	@Override
	public String queryDeptViewOrg(Map<String, Object> map)
			throws DataAccessException {
		
		//typeCode：0医院用户，1集团用户，2超级管理员，3集团管理员，4医院管理员
		String typeCode=SessionManager.getTypeCode();
		String groupId=SessionManager.getGroupId();
		String hosId=SessionManager.getHosId();
		map.put("group_id", groupId);
		map.put("hos_id", hosId);
		map.put("user_id", SessionManager.getUserId());
		
		StringBuilder json=new StringBuilder();
		
		try{
			
		json.append("{");
			
			if(typeCode.equals("0")){
				json.append("\"id\":\""+SessionManager.getHosCode()+"\",");
				json.append("\"title\":\""+SessionManager.getHosSimple()+"\",");
				json.append("\"topic\":\""+SessionManager.getHosSimple()+"\",");
				json.append("\"isroot\":\""+"true\",");
				
			
			}else{
				json.append("\"id\":\""+SessionManager.getHosCode()+"\",");
				json.append("\"title\":\""+SessionManager.getHosSimple()+"\",");
				json.append("\"topic\":\""+SessionManager.getHosSimple()+"\",");
				json.append("\"isroot\":\""+"true\",");
			
			}
			
			json.append("\"children\":[");
			//查询科室
			List<Map<String,Object>> lastList=hrDeptViewMapper.queryDeptViewOrgSet(map);
			if(lastList!=null && lastList.size()>0){
				
			/*	json.append("\"id\":\""+c.get("name").toString()+"\",");
				json.append("\"topic\":\""+c.get("title").toString()+"\",");
				json.append("\"title\":\""+c.get("title").toString()+"\",");
				json.append("\"parentid\":\""+c.get("super_code").toString()+"\",");
				getDeptChild(json,lastList,c.get("name").toString());*/
for(Map<String,Object> c:lastList){
					
					if(!c.get("dept_level").toString().equals("1")){
						continue;
					}
					
					json.append("{");
					json.append("\"id\":\""+c.get("name").toString()+"\",");
					json.append("\"topic\":\""+c.get("title").toString()+"\",");
					json.append("\"title\":\""+c.get("title").toString()+"\",");
					json.append("\"parentid\":\""+c.get("super_code").toString()+"\"");
					getDeptChild(json,lastList,c.get("name").toString());
						
					json.append("},");
					
				
				}


			}
			json.deleteCharAt(json.length()-1);
			json.append("]}");
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
		return replace(json.toString());
				}
		//递归取下级科室
		private void getDeptChild(StringBuilder json,List<Map<String,Object>> lastList,String deptCode){
			int a=0;
			int b=0;
			json.append(",\"children\":[");
			for(Map<String,Object> c:lastList){
			
				if(!deptCode.equals(c.get("super_code").toString())){
					
					continue;
				}
				
				json.append("{");
				json.append("\"id\":\""+c.get("name").toString()+"\",");
				json.append("\"topic\":\""+c.get("title").toString()+"\",");
				json.append("\"title\":\""+c.get("title").toString()+"\",");
				json.append("\"parentid\":\""+c.get("super_code").toString()+"\",");
				json.append("\"className\":\"last\"");
				getDeptChild(json,lastList,c.get("name").toString());
			
				if(c.get("is_last").toString().equals("1")){
					a=1;
				}
				if(c.get("is_last").toString().equals("0")&&!c.get("dept_level").toString().equals("1")){
					b=1;
				}
			json.append("},");
				
		
			}
			if(a==1){
				json.deleteCharAt(json.length()-1);

				json.append("]");
			}else if (b==1){
				json.deleteCharAt(json.length()-1);

			json.append("]");
			}else{
				json.append("]");
			}
		}
		
		//递归替换children\":[]
		private String replace(String json){
			
			if(json.indexOf(",\"children\":[]")!=-1){
				json=json.replace(",\"children\":[]", "");
				replace(json);
			}
			return json;
		
	}


		@Override
		public String addDeptOrgSet(Map<String, Object> entityMap)
				throws DataAccessException {
			try{
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());

		
			
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				throw new SysException(e.getMessage());
			}
		}


		@Override
		public String queryDeptViewOrgMenu(Map<String, Object> entityMap)
				throws DataAccessException {
			// TODO Auto-generated method stub
			return   ChdJson.toJson(hrDeptViewMapper.queryDeptViewOrgMenu(entityMap));
		}


		@Override
		public String queryDeptViewOrgImg(Map<String, Object> entityMap)
				throws DataAccessException {
			return hrDeptViewMapper.queryDeptViewOrgImg(entityMap);
		}




	
}
