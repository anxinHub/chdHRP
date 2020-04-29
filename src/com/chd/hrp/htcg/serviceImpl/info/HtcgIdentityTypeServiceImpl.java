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
import com.chd.hrp.htcg.dao.info.HtcgIdentityTypeMapper;
import com.chd.hrp.htcg.entity.info.HtcgIdentityType;
import com.chd.hrp.htcg.service.info.HtcgIdentityTypeService;
import com.github.pagehelper.PageInfo;
@Service("htcgIdentityTypeService")
public class HtcgIdentityTypeServiceImpl implements HtcgIdentityTypeService {

	private static Logger logger = Logger.getLogger(HtcgIdentityTypeServiceImpl.class);
	
	@Resource(name="htcgIdentityTypeMapper")
	private final HtcgIdentityTypeMapper htcgIdentityTypeMapper = null;

	@Override
	public String addHtcgIdentityType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcgIdentityType htcgIdentityType = htcgIdentityTypeMapper.queryHtcgIdentityTypeByCode(entityMap);
			
			if(null != htcgIdentityType){
				return "{\"error\":\"医保类型编码重复请重新输入!.\",\"state\":\"false\"}";
			}
			
			htcgIdentityTypeMapper.addHtcgIdentityType(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatchHtcgIdentityType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryHtcgIdentityType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HtcgIdentityType> list = htcgIdentityTypeMapper.queryHtcgIdentityType(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			List<HtcgIdentityType> list = htcgIdentityTypeMapper.queryHtcgIdentityType(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HtcgIdentityType queryHtcgIdentityTypeByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgIdentityTypeMapper.queryHtcgIdentityTypeByCode(entityMap);
	}

	@Override
	public String deleteHtcgIdentityType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcgIdentityTypeMapper.deleteHtcgIdentityType(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgIdentityType(
			List<Map<String, Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
       try {
			
    	   htcgIdentityTypeMapper.deleteBatchHtcgIdentityType(entityList);
    	   
    	   return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String updateHtcgIdentityType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcgIdentityTypeMapper.updateHtcgIdentityType(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

	@Override
	public String importHtcgIdentityType(Map<String, Object> entityMap)
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
			 
			 mapVo.put("identity_code", map.get("identity_code").get(1));
				 
			 mapVo.put("identity_name", map.get("identity_name").get(1));
			 
			 mapVo.put("is_stop", 0);
			 
			 mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("identity_name").toString()));
			
			 mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("identity_name").toString()));
			 
			 HtcgIdentityType htcgIdentityType = htcgIdentityTypeMapper.queryHtcgIdentityTypeByCode(mapVo);
			 
			 if(htcgIdentityType !=null){
					 
				return "{\"error\":\""+ map.get("identity_code").get(0)+" 医保类型编码:"+map.get("identity_code").get(1)+" 重复.\",\"state\":\"false\"}";
			  }
			 
			  resultSet.add(mapVo);
       	     }
       	 
	       	 if(resultSet.size() > 0 ){
	       		htcgIdentityTypeMapper.addBatchHtcgIdentityType(resultSet);  
	   	      }
			 return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			throw new SysException("{\"error\":\"导入失败\"}");
		}
	}

	

}
