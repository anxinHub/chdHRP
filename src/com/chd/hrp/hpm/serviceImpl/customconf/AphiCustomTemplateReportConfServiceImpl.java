package com.chd.hrp.hpm.serviceImpl.customconf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiCustomTemplateReportConfMapper;
import com.chd.hrp.hpm.entity.AphiCustomTemplateReportConf;
import com.chd.hrp.hpm.service.customconf.AphiCustomTemplateReportConfService;

@Service("aphiCustomTemplateReportConfService")
public class AphiCustomTemplateReportConfServiceImpl implements AphiCustomTemplateReportConfService {
	
	private static Logger logger = Logger.getLogger(AphiCustomTemplateReportConfServiceImpl.class);
	
	@Resource(name = "aphiCustomTemplateReportConfMapper")
	private final AphiCustomTemplateReportConfMapper aphiCustomTemplateReportConfMapper = null;

	@Override
	public String queryAphiCustomTemplateReportConf(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		StringBuilder json = new StringBuilder();
		 
		try {
			
			List<AphiCustomTemplateReportConf> list = aphiCustomTemplateReportConfMapper.queryAphiCustomTemplateReportConf(entityMap);
			
			
			//查询所有模板
			if (list.size() ==0 && list.size() ==0) {
				json.append("{Rows:[]}");
				return json.toString();
			}
			
			
			json.append("{Rows:[");
			for(AphiCustomTemplateReportConf actrc:list){
				
				json.append("{");
				
				json.append("\"group_id\":\"" + actrc.getGroup_id() + "\","); 
				json.append("\"hos_id\":\"" + actrc.getHos_id() + "\",");
				json.append("\"copy_code\":\"" + actrc.getCopy_code() + "\",");
				json.append("\"template_code\":\"" + actrc.getTemplate_code() + "\",");
				json.append("\"template_type\":\"" + actrc.getTemplate_type() + "\",");
				json.append("\"template_name\":\"" + actrc.getTemplate_name() + "\",");
				json.append("\"template_kind_code\":\"" + actrc.getTemplate_kind_code() + "\",");
				json.append("\"template_kind_name\":\"" + actrc.getTemplate_kind_name() + "\",");
				
				/*String template_sql = new String(actrc.getTemplate_sql(),"UTF-8");
				json.append("\"template_sql\":\"" + template_sql.replaceAll("\"", "\\\\\"") + "\"");*/
				String template_sql = new String(actrc.getTemplate_sql(),"UTF-8");
				
				json.append("\"template_sql\":\"" + template_sql + "\"");
				//json.append("\"template_sql\":\"" + template_sql.replaceAll("\"", "\\\\\"") + "\"");
				
				json.append("},");
			}
			
			json = json.deleteCharAt(json.length() - 1);
			json.append("]}");
			
			return json.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"操作失败.\",\"state\":\"false\"}";
		}
	}

	@Override
	public String addAphiCustomTemplateReportConf(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			if(entityMap.get("allData") == null){
				
				return "{\"error\":\"保存失败,参数为空\",\"state\":\"false\"}";
			}
			
			
			//获取JSON数组参数
			JSONArray allData = JSON.parseArray(String.valueOf(entityMap.get("allData")));
			Iterator iterator = allData.iterator();
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			while(iterator.hasNext()){
				JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
				
				
				if("08".equals(jsonObj.get("template_type"))){//导入功能SQL限制
					String import_sql = String.valueOf(jsonObj.get("template_sql"));
					import_sql = import_sql.trim().replaceAll(" ", "").toLowerCase();
					
					if(!import_sql.startsWith("delete")){
						return "{\"warn\":\"导入SQL必须以delete语句开头\",\"state\":\"false\"}";
					}
					
					if(import_sql.contains("insertinto") == false){
						return "{\"warn\":\"导入SQL必须包含insert语句\",\"state\":\"false\"}";
					}
					
					int sepIndex = import_sql.indexOf(";");
					int insertIndex = import_sql.indexOf("insertinto");
					int endIndex = import_sql.lastIndexOf(");");
					
					if(sepIndex >= insertIndex){
						return "{\"warn\":\"导入SQL删除语句必须用;结束\",\"state\":\"false\"}";
					}
					
					if(endIndex == -1){
						return "{\"warn\":\"导入SQL添加语句必须用;结束\",\"state\":\"false\"}";
					}
				}
				
				
				
				
				
				Map<String,Object> mapVo = new HashMap<String,Object>();
				
				mapVo.put("group_id", entityMap.get("group_id"));
				mapVo.put("hos_id", entityMap.get("hos_id"));
				mapVo.put("copy_code", entityMap.get("copy_code"));
				mapVo.put("template_code", String.valueOf(entityMap.get("template_code")));
				mapVo.put("template_name", entityMap.get("template_name"));
				mapVo.put("template_type",jsonObj.get("template_type"));
				mapVo.put("template_kind_code", entityMap.get("template_kind_code"));
				mapVo.put("template_sql",jsonObj.get("template_sql").toString().getBytes("UTF-8"));
				//mapVo.put("template_sql",jsonObj.get("template_sql").toString());
				
				addList.add(mapVo);
			}
			
			if("false".equals(String.valueOf(entityMap.get("saveFlag")))){//更改
				aphiCustomTemplateReportConfMapper.deleteAphiCustomTemplateReportConf(entityMap);
			}else{//新增
				
				Map<String,Object> queryMap = new HashMap<String,Object>();
				queryMap.put("group_id", entityMap.get("group_id"));
				queryMap.put("hos_id", entityMap.get("hos_id"));
				queryMap.put("copy_code", entityMap.get("copy_code"));
				queryMap.put("template_code", String.valueOf(entityMap.get("template_code")));
				
				//查询编码是否存在
				List<AphiCustomTemplateReportConf> byCodelist = aphiCustomTemplateReportConfMapper.queryAphiCustomTemplateReportConf(queryMap);
				if(byCodelist.size() > 0){
					return "{\"warn\":\"模板编码已存在.\",\"state\":\"false\"}";
				}
				
				queryMap.remove("template_code");
				queryMap.put("template_name",String.valueOf(entityMap.get("template_name")));
				
				//查询名称是否存在
				List<AphiCustomTemplateReportConf> byNamelist = aphiCustomTemplateReportConfMapper.queryAphiCustomTemplateReportConf(queryMap);
				if(byNamelist.size() > 0){
					return "{\"warn\":\"模板名称已存在.\",\"state\":\"false\"}";
				}
			}
			
			aphiCustomTemplateReportConfMapper.addBatchAphiCustomTemplateReportConf(addList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");
		}
	}

	@Override
	public String updateAphiCustomTemplateReportConf(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			aphiCustomTemplateReportConfMapper.updateAphiCustomTemplateReportConf(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");
		}
	}

	@Override
	public String deleteAphiCustomTemplateReportConf(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			aphiCustomTemplateReportConfMapper.deleteAphiCustomTemplateReportConf(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");
		}
	}

	@Override
	public String queryAphiCustomTemplateReportTree(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		StringBuilder json = new StringBuilder();
		
		
		try {
			
			//查询所有模板分类
			List<Map<String,Object>> tempKindlist = aphiCustomTemplateReportConfMapper.queryAphiTemplateKindList(entityMap);
			//查询所有模板
			List<Map<String, Object>> list = ChdJson.toListLower(aphiCustomTemplateReportConfMapper.queryAphiCustomTemplateReportTree(entityMap));
			
			
			if (list.size() ==0 && list.size() ==0) {
				json.append("{Rows:[]}");
				return json.toString();
			}

			
			json.append("{Rows:[");
			
			for(Map<String,Object> tempKind : tempKindlist){
				json.append("{");
				json.append("\"pid\":\"-1\"," + "\"id\":\"" + tempKind.get("template_kind_code") + "\"," + "\"text\":" + "\"" + tempKind.get("template_kind_name") + "\"");
				json.append("},");
			}
			
			for (Map<String,Object> reportConf : list) {
				json.append("{");
				json.append("\"pid\":\""+ reportConf.get("template_kind_code") +"\"," + "\"id\":\"" + reportConf.get("template_code") + "\"," + "\"text\":" + "\"" + reportConf.get("template_name") + "\"");
				json.append("},");
			}
			json.setCharAt(json.length() - 1, ']');
			json.append("}");
			
		} catch (Exception e) {
			
			json.append("{Rows:[]}");
			return json.toString();
		}
		
		return json.toString();
		
	}
	
	
	@Override
	public List<AphiCustomTemplateReportConf> queryAphiCustomTemplateReportConfListByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return aphiCustomTemplateReportConfMapper.queryAphiCustomTemplateReportConf(entityMap);
	}

	@Override
	public AphiCustomTemplateReportConf queryAphiCustomTemplateReportConfByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return aphiCustomTemplateReportConfMapper.queryAphiCustomTemplateReportConfByCode(entityMap);
	}

}
