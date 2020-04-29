
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
import com.chd.hrp.htc.dao.task.basic.HtcPeopleDictMapper;
import com.chd.hrp.htc.dao.task.basic.HtcPeopleTitleDictMapper;
import com.chd.hrp.htc.entity.task.basic.HtcPeopleDict;
import com.chd.hrp.htc.entity.task.basic.HtcPeopleTitleDict;
import com.chd.hrp.htc.service.task.basic.HtcPeopleTitleDictService;
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


@Service("htcPeopleTitleDictService")
public class HtcPeopleTitleDictServiceImpl implements HtcPeopleTitleDictService {

	private static Logger logger = Logger.getLogger(HtcPeopleTitleDictServiceImpl.class);
	
	@Resource(name = "htcPeopleTitleDictMapper")
	private final HtcPeopleTitleDictMapper htcPeopleTitleDictMapper = null;
    
	@Resource(name = "htcPeopleDictMapper")
	private final HtcPeopleDictMapper htcPeopleDictMapper = null;
	
	@Override
	public String addHtcPeopleTitleDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			HtcPeopleTitleDict htcPeopleTitleDict = htcPeopleTitleDictMapper.queryHtcPeopleTitleDictByCode(entityMap);
			
			if(null != htcPeopleTitleDict){
				
				return "{\"error\":\"人员职称编码重复.\",\"state\":\"false\"}";
				
			}
			
			htcPeopleTitleDictMapper.addHtcPeopleTitleDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");	
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addHtcPeopleTitleDict\"}";
		}
	
	}
	
	
	@Override
	public String queryHtcPeopleTitleDict(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			
			List<HtcPeopleTitleDict> list = htcPeopleTitleDictMapper.queryHtcPeopleTitleDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcPeopleTitleDict> list = htcPeopleTitleDictMapper.queryHtcPeopleTitleDict(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public HtcPeopleTitleDict queryHtcPeopleTitleDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcPeopleTitleDictMapper.queryHtcPeopleTitleDictByCode(entityMap);
	}
	
	@Override
	public String deleteHtcPeopleTitleDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String deleteBatchHtcPeopleTitleDict(
			List<Map<String, Object>> entityList) throws DataAccessException{
		try {
			
			for(int i = 0; i < entityList.size(); i++){
				List<HtcPeopleDict> newList = htcPeopleDictMapper.queryHtcPeopleDict(entityList.get(i));
				
				if(newList.size() != 0){
					return "{\"error\":\"["+entityList.get(i).get("title_code")+"]编码已与人员字典表存在关系\"}";
				}
			}
			
			htcPeopleTitleDictMapper.deleteBatchHtcPeopleTitleDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");	
		}
	}
	
	@Override
	public String updateHtcPeopleTitleDict(Map<String,Object> entityMap)throws DataAccessException{
		try {
			htcPeopleTitleDictMapper.updateHtcPeopleTitleDict(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");	
		}
	}
	

	@Override
	public String synchroHtcPeopleTitleDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcPeopleTitleDictMapper.synchroHtcPeopleTitleDict(entityMap);
			
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 错误编码 updateHtcPeopleTitleDict\"}";
		}
	}


	@Override
	public String impHtcPeopleTitleDict(Map<String, Object> entityMap)
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
 				
                 mapVo.put("title_code", map.get("title_code").get(1));
				
				 mapVo.put("title_name", map.get("title_name").get(1));
				
				 mapVo.put("title_desc", map.get("title_desc").get(1));
				
				 mapVo.put("is_stop", 0);
				
				 mapVo.put("spell_code",StringTool.toPinyinShouZiMu(map.get("title_name").get(1).toString()));
				
				 mapVo.put("wbx_code", StringTool.toWuBi(map.get("title_name").get(1).toString()));
				 
				 HtcPeopleTitleDict htcPeopleTitleDict = htcPeopleTitleDictMapper.queryHtcPeopleTitleDictByCode(mapVo);
				 
				 if(null != htcPeopleTitleDict){
						
					return "{\"error\":\""+ map.get("title_code").get(0)+" 人员职称:"+map.get("title_code").get(1)+" 重复.\",\"state\":\"false\"}";
				 }
				 
				 resultSet.add(mapVo);
				 
			   }

	        	  if(resultSet.size() > 0){
	        		  System.out.println(resultSet);
	        		  htcPeopleTitleDictMapper.addBatchHtcPeopleTitleDict(resultSet);
	        		  
	        	  }
			  return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}
}
