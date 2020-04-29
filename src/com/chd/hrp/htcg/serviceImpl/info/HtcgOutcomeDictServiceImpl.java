
/*
 *
 */package com.chd.hrp.htcg.serviceImpl.info;

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
import com.chd.hrp.htcg.dao.info.HtcgOutcomeDictMapper;
import com.chd.hrp.htcg.entity.info.HtcgOutcomeDict;
import com.chd.hrp.htcg.service.info.HtcgOutcomeDictService;
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


@Service("htcgOutcomeDictService")
public class HtcgOutcomeDictServiceImpl implements HtcgOutcomeDictService {

	private static Logger logger = Logger.getLogger(HtcgOutcomeDictServiceImpl.class);
	
	@Resource(name = "htcgOutcomeDictMapper")
	private final HtcgOutcomeDictMapper htcgOutcomeDictMapper = null;

	@Override
	public String addHtcgOutcomeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			HtcgOutcomeDict htcgOutcomeDict =htcgOutcomeDictMapper.queryHtcgOutcomeDictByCode(entityMap);
			
			if(null != htcgOutcomeDict){
				
				return "{\"error\":\"编码已存在!.\",\"state\":\"false\"}";
			}
			
			htcgOutcomeDictMapper.addHtcgOutcomeDict(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatchHtcgOutcomeDict(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
       try {
			htcgOutcomeDictMapper.addBatchHtcgOutcomeDict(list);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcgOutcomeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HtcgOutcomeDict> list = htcgOutcomeDictMapper.queryHtcgOutcomeDict(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			List<HtcgOutcomeDict> list = htcgOutcomeDictMapper.queryHtcgOutcomeDict(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HtcgOutcomeDict queryHtcgOutcomeDictByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgOutcomeDictMapper.queryHtcgOutcomeDictByCode(entityMap);
	}

	@Override
	public String deleteHtcgOutcomeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
			try {
				
				htcgOutcomeDictMapper.deleteHtcgOutcomeDict(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

	@Override
	public String deleteBatchHtcgOutcomeDict(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub

		try {
			
			htcgOutcomeDictMapper.deleteBatchHtcgOutcomeDict(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String updateHtcgOutcomeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	  try {
			
			htcgOutcomeDictMapper.updateHtcgOutcomeDict(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

	@Override
	public String importHtcgOutcomeDict(Map<String, Object> entityMap)
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
 				 
 				 mapVo.put("outcome_code", map.get("outcome_code").get(1));
 				 
 				 mapVo.put("outcome_name", map.get("outcome_name").get(1));
 				 
 				 mapVo.put("is_stop", 0);
 				 
 				 mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("outcome_name").toString()));
 				
 				 mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("outcome_name").toString()));
 				 

 				 HtcgOutcomeDict htcgOutcomeDict =htcgOutcomeDictMapper.queryHtcgOutcomeDictByCode(mapVo);
 				
 				 if(null != htcgOutcomeDict){
 					
 					return "{\"error\":\""+ map.get("outcome_code").get(0)+" 转归编码:"+map.get("outcome_code").get(1)+" 已存在.\",\"state\":\"false\"}";
 				 }
 				
 				
 				 resultSet.add(mapVo);
 				 
         	 }
			  
         	  if(resultSet.size() > 0 ){
         		  
         		 htcgOutcomeDictMapper.addBatchHtcgOutcomeDict(resultSet);
         	  }
         	 
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"导入失败\"}");
		}
	}

}
