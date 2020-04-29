package com.chd.hrp.hr.serviceImpl.attendancemanagement.attendresult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.attendancemanagement.attendresult.HrAttendResultExamineMapper;
import com.chd.hrp.hr.dao.attendancemanagement.attendresult.HrAttendResultItemMapper;
import com.chd.hrp.hr.service.attendancemanagement.attendresult.HrAttendResultItemService;
import com.github.pagehelper.PageInfo;

@Service("hrAttendResultItemService")
public class HrAttendResultItemServiceImpl implements HrAttendResultItemService {
private static Logger logger = Logger.getLogger(HrAttendResultItemServiceImpl.class);
	
	@Resource(name = "hrAttendResultItemMapper")
	private final HrAttendResultItemMapper hrAttendResultItemMapper = null;
	@Override
	public String queryAttendResultItem(Map<String, Object> entityMap) {
		
		StringBuffer sql=new StringBuffer();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("user_id", SessionManager.getUserId());
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		if(!entityMap.get("attend_item").toString().equals("")){
			//获取考勤项目
			List<Map<String,Object>> itemList = hrAttendResultItemMapper.queryResultItemHead(entityMap);
			sql.append(" and (");
			if(itemList.size()>0){
				for (Map<String, Object> map : itemList) {
					sql.append("  a."+map.get("ATTEND_ITEM")+"!='0' or ");
				}
				sql.delete(sql.length() - 4 , sql.length());
			}
			sql.append(")");
			entityMap.put("sql", sql);}
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = hrAttendResultItemMapper.queryAttendResultItem(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = hrAttendResultItemMapper.queryAttendResultItem(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	} 
	@Override
	public String queryResultItemHead(Map<String, Object> entityMap)
			throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
	
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			//获取考勤项目
			List<Map<String,Object>> itemList = hrAttendResultItemMapper.queryResultItemHead(entityMap);
		/*	boolean is_first = true;
			StringBuilder jsonHead = new StringBuilder();  //用于表头显示
			jsonHead.append("[{");
			StringBuilder selectSql = new StringBuilder();  //记录查询列

			if(itemList != null && itemList.size() > 0){
				for(Map<String, Object> map : itemList){
					if(!is_first){
						jsonHead.append("},{");	
					}
					jsonHead.append("\"display\": \"").append(map.get("ATTEND_SHORTNAME").toString()).append("\"");
					jsonHead.append(", \"name\": \"").append(map.get("ATTEND_ITEM")).append("\"");
					jsonHead.append(", \"attend_code\": \"").append(map.get("ATTEND_CODE")).append("\"");
					jsonHead.append(", \"attend_types\": \"").append(map.get("ATTEND_TYPES")).append("\"");
	
					selectSql.append("a.").append(map.get("ATTEND_ITEM").toString()).append(" as \"").append(map.get("ATTEND_ITEM").toString()).append("\", ");
					
					is_first = false;
				}
	
				jsonHead.append("}]");
				retMap.put("jsonHead", jsonHead.toString());
				retMap.put("selectSql", selectSql.substring(0, selectSql.length() - 2).toString());
			}*/
			return ChdJson.toJson(itemList);
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		
	} 
	@Override
	public List<Map<String, Object>> queryAttendResultItemPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		
		StringBuffer sql=new StringBuffer();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("user_id", SessionManager.getUserId());
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		if(entityMap.get("kind_code") != null && entityMap.get("kind_code") != ""){
			entityMap.put("kind_code", entityMap.get("kind_code").toString().replaceAll(";", ","));
	        }
		if(entityMap.get("yh_code") != null && entityMap.get("yh_code") != ""){
			entityMap.put("yh_code", entityMap.get("yh_code").toString().replaceAll(";", ","));
	        }
		if(!entityMap.get("attend_item").toString().equals("")){
			entityMap.put("item_code", entityMap.get("attend_item").toString().replaceAll(";", ","));
			//获取考勤项目
			List<Map<String,Object>> itemList = hrAttendResultItemMapper.queryResultItemHead(entityMap);
			sql.append(" and (");
			if(itemList.size()>0){
				for (Map<String, Object> map : itemList) {
					sql.append("  a."+map.get("ATTEND_ITEM")+"!='0' or ");
				}
				sql.delete(sql.length() - 4 , sql.length());
			}
			sql.append(")");
			entityMap.put("sql", sql);}
		
			List<Map<String, Object>> list = hrAttendResultItemMapper.queryAttendResultItem(entityMap);
			return list;
		}
	

}
