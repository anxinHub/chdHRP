package com.chd.hrp.htcg.serviceImpl.info;

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
import com.chd.hrp.htcg.dao.info.HtcgAnestTypeDictMapper;
import com.chd.hrp.htcg.entity.info.HtcgAnestTypeDict;
import com.chd.hrp.htcg.entity.info.HtcgRecipeType;
import com.chd.hrp.htcg.service.info.HtcgAnestTypeDictService;
import com.github.pagehelper.PageInfo;
@Service("htcgAnestTypeDictService")
public class HtcgAnestTypeDictServiceImpl implements HtcgAnestTypeDictService {

	private static Logger logger = Logger.getLogger(HtcgAnestTypeDictServiceImpl.class);
	
	@Resource(name="htcgAnestTypeDictMapper")
	private final HtcgAnestTypeDictMapper htcgAnestTypeDictMapper = null;

	@Override
	public String addHtcgAnestTypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcgAnestTypeDict htcgAnestTypeDict = htcgAnestTypeDictMapper.queryHtcgAnestTypeDictByCode(entityMap);
			
			if(null !=htcgAnestTypeDict){
				
				return "{\"error\":\"编码已存在!.\",\"state\":\"false\"}";
				
			}
			
			htcgAnestTypeDictMapper.addHtcgAnestTypeDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatchHtcgAnestTypeDict(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
            htcgAnestTypeDictMapper.addBatchHtcgAnestTypeDict(list);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcgAnestTypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HtcgAnestTypeDict> list = htcgAnestTypeDictMapper.queryHtcgAnestTypeDict(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			List<HtcgAnestTypeDict> list = htcgAnestTypeDictMapper.queryHtcgAnestTypeDict(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HtcgAnestTypeDict queryHtcgAnestTypeDictByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgAnestTypeDictMapper.queryHtcgAnestTypeDictByCode(entityMap);
	}

	@Override
	public String deleteHtcgAnestTypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcgAnestTypeDictMapper.deleteHtcgAnestTypeDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgAnestTypeDict(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
         try {
			
			htcgAnestTypeDictMapper.deleteBatchHtcgAnestTypeDict(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String updateHtcgAnestTypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
				htcgAnestTypeDictMapper.updateHtcgAnestTypeDict(entityMap);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"修改失败\"}");
			}
	}

	@Override
	public String importHtcgAnestTypeDict(Map<String, Object> entityMap)
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
  				 
  				 mapVo.put("anest_type_code", map.get("anest_type_code").get(1));
  				 
  				 mapVo.put("anest_type_name", map.get("anest_type_name").get(1));
  				 
  				 mapVo.put("is_stop", 0);
  				 
  				 mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("anest_type_name").toString()));
  				
  				 mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("anest_type_name").toString()));
        		 
  				HtcgAnestTypeDict htcgAnestTypeDict = htcgAnestTypeDictMapper.queryHtcgAnestTypeDictByCode(mapVo);
  				
  				if(null !=htcgAnestTypeDict){
  					
  					return "{\"error\":\""+ map.get("anest_type_code").get(0)+" 麻醉种类编码:"+map.get("anest_type_code").get(1)+" 重复.\",\"state\":\"false\"}";
  					
  				}
  				
  				resultSet.add(mapVo);
        		 
        	 }

        	  if(resultSet.size() > 0 ){
        		  htcgAnestTypeDictMapper.addBatchHtcgAnestTypeDict(resultSet);
        	  }
			  return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			  
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

	

}
