
package com.chd.hrp.htc.serviceImpl.task.basic;

import java.util.ArrayList;
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
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.htc.dao.info.basic.HtcDeptDictMapper;
import com.chd.hrp.htc.dao.task.basic.HtcPeopleDictMapper;
import com.chd.hrp.htc.dao.task.basic.HtcPeopleTitleDictMapper;
import com.chd.hrp.htc.dao.task.basic.HtcPeopleTypeDictMapper;
import com.chd.hrp.htc.entity.task.basic.HtcPeopleDict;
import com.chd.hrp.htc.entity.task.basic.HtcPeopleTitleDict;
import com.chd.hrp.htc.entity.task.basic.HtcPeopleTypeDict;
import com.chd.hrp.htc.service.task.basic.HtcPeopleDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("htcPeopleDictService")
public class HtcPeopleDictServiceImpl implements HtcPeopleDictService {

	private static Logger logger = Logger.getLogger(HtcPeopleDictServiceImpl.class);
	
	@Resource(name = "htcPeopleDictMapper")
	private final HtcPeopleDictMapper htcPeopleDictMapper = null;
	
	@Resource(name = "htcPeopleTitleDictMapper")
	private final HtcPeopleTitleDictMapper htcPeopleTitleDictMapper= null;
	
	@Resource(name = "htcPeopleTypeDictMapper")
	private final HtcPeopleTypeDictMapper htcPeopleTypeDictMapper = null;
	
	@Resource(name = "htcDeptDictMapper")
	private final HtcDeptDictMapper htcDeptDictMapper = null;
    
	@Override
	public String addHtcPeopleDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			HtcPeopleDict htcPeopleDict = htcPeopleDictMapper.queryHtcPeopleDictByCode(entityMap);
			
			if(null != htcPeopleDict){
				return "{\"error\":\"人员编码重复.\",\"state\":\"false\"}";
			}

			htcPeopleDictMapper.addHtcPeopleDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");	
	
		}
	}
	


	@Override
	public String queryHtcPeopleDict(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcPeopleDict> list = htcPeopleDictMapper.queryHtcPeopleDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcPeopleDict> list = htcPeopleDictMapper.queryHtcPeopleDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
	
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public HtcPeopleDict queryHtcPeopleDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcPeopleDictMapper.queryHtcPeopleDictByCode(entityMap);
	}
	
	@Override
	public String deleteHtcPeopleDict(Map<String,Object> mapVo)throws DataAccessException{
		
		return null;
	}
	
	@Override
    public String deleteBatchHtcPeopleDict(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			
			htcPeopleDictMapper.deleteBatchHtcPeopleDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");	
	
		}
    }
	
	@Override
	public String updateHtcPeopleDict(Map<String,Object> entityMap)throws DataAccessException{
		try {
			
			htcPeopleDictMapper.updateHtcPeopleDict(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"修改失败\"}");

		}
	}



	@Override
	public String synchroHtcPeopleDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
				
				htcPeopleDictMapper.synchroHtcPeopleDict(entityMap);
				
				return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				
				throw new SysException("{\"error\":\"同步失败\"}");
	
			}
	   }



	@Override
	public String impHtcPeopleDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			
			List<Map<String,Object>> resultSet = new ArrayList<Map<String,Object>>();
			
			 if (list.size() == 0 || list == null) {
	 				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
	 			 }
			for (Map<String, List<String>> map : list) {
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
                mapVo.put("group_id", SessionManager.getGroupId());
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
                mapVo.put("people_code", map.get("people_code").get(1));
				
				mapVo.put("people_name", map.get("people_name").get(1));
				
				mapVo.put("title_code", map.get("title_code").get(1));
				
				mapVo.put("people_type_code", map.get("people_type_code").get(1));
				
				mapVo.put("dept_code", map.get("dept_code").get(1));
				
				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(map.get("people_name").get(1).toString()));
				
				mapVo.put("wbx_code", StringTool.toWuBi(map.get("people_name").get(1).toString()));
				
				mapVo.put("is_stop", 0);
				
				mapVo.put("people_note", map.get("people_note").get(1).toString());
				
				//判断职称是否为空
                Map<String, Object> peopleTitleMap = new HashMap<String, Object>();
				
				peopleTitleMap.put("group_id", mapVo.get("group_id"));
				
				peopleTitleMap.put("hos_id", mapVo.get("hos_id"));
				
				peopleTitleMap.put("copy_code", mapVo.get("copy_code"));
				
				peopleTitleMap.put("title_code", mapVo.get("title_code"));
				
				HtcPeopleTitleDict htcPeopleTitleDict = htcPeopleTitleDictMapper.queryHtcPeopleTitleDictByCode(peopleTitleMap);
				
				if(null == htcPeopleTitleDict){
					
					return "{\"error\":\""+ map.get("title_code").get(0)+"人员职称:"+map.get("title_code").get(1)+"不存在.\",\"state\":\"false\"}";
				}
				
				//判断人员类别是否存在
				 Map<String, Object> peopleTypeMap = new HashMap<String, Object>();
				
				 peopleTypeMap.put("group_id", mapVo.get("group_id"));
				
				 peopleTypeMap.put("hos_id", mapVo.get("hos_id"));
				
				 peopleTypeMap.put("copy_code", mapVo.get("copy_code"));
				
				 peopleTypeMap.put("peop_type_code", mapVo.get("people_type_code"));
				 
				 HtcPeopleTypeDict htcPeopleTypeDict = htcPeopleTypeDictMapper.queryHtcPeopleTypeDictByCode(peopleTypeMap);
				
				 if(null == htcPeopleTypeDict){
						
					return "{\"error\":\""+ map.get("people_type_code").get(0)+"人员类别:"+map.get("people_type_code").get(1)+"不存在.\",\"state\":\"false\"}";
				 }
				 
				 
				 //判断科室是否存在如果存在取科室ID和NO
				 Map<String, Object> deptDictMap = new HashMap<String, Object>();
				 
				 deptDictMap.put("group_id", mapVo.get("group_id"));
					
				 deptDictMap.put("hos_id", mapVo.get("hos_id"));
				
				 deptDictMap.put("copy_code", mapVo.get("copy_code"));
				 
				 deptDictMap.put("dept_code", mapVo.get("dept_code"));
				 
				 deptDictMap.put("is_last", "1");
				 
				 deptDictMap.put("is_stop", "0");
				 
				 Map<String, Object> htcDeptDict =  htcDeptDictMapper.queryHtcDeptDictByCode(deptDictMap);
				 
				 if(null == htcDeptDict){
					 
					 return "{\"error\":\""+ map.get("dept_code").get(0)+"科室:"+map.get("dept_code").get(1)+"不存在!.\",\"state\":\"false\"}";
					 
				 }else{
					 
					 mapVo.put("dept_no", htcDeptDict.get("dept_no"));
					 
					 mapVo.put("dept_id", htcDeptDict.get("dept_id"));
				 }
				 
				 //判断人员是否重复
				 Map<String, Object> peopleMap = new HashMap<String, Object>();
				 
				 peopleMap.put("group_id", mapVo.get("group_id"));
					
				 peopleMap.put("hos_id", mapVo.get("hos_id"));
				
				 peopleMap.put("copy_code", mapVo.get("copy_code"));
				
				 peopleMap.put("people_code", mapVo.get("people_code"));
				 
				 HtcPeopleDict htcPeopleDict = htcPeopleDictMapper.queryHtcPeopleDictByCode(peopleMap);
				 
				if(null != htcPeopleDict){
					
					return "{\"error\":\""+ map.get("people_code").get(0)+"人员:"+map.get("people_code").get(1)+"已经存在.\",\"state\":\"false\"}";
				}
				    resultSet.add(mapVo);
			    }
			
				if(resultSet.size() > 0){
					htcPeopleDictMapper.addBatchHtcPeopleDict(resultSet);
				}
			
			     return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

	
}
