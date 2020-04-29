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
import com.chd.hrp.htcg.dao.info.HtcgRecipeTypeMapper;
import com.chd.hrp.htcg.entity.info.HtcgRecipeType;
import com.chd.hrp.htcg.service.info.HtcgRecipeTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("htcgRecipeTypeService")
public class HtcgRecipeTypeServiceImpl implements HtcgRecipeTypeService {

	private static Logger logger = Logger.getLogger(HtcgRecipeTypeServiceImpl.class);

	@Resource(name = "htcgRecipeTypeMapper")
	private final HtcgRecipeTypeMapper htcgRecipeTypeMapper = null;

	@Override
	public String addHtcgRecipeType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			HtcgRecipeType htcgRecipeType = htcgRecipeTypeMapper.queryHtcgRecipeTypeByCode(entityMap);
			if(htcgRecipeType !=null){
				 return "{\"error\":\"编码重复请重新添加.\",\"state\":\"false\"}";
			 }
			htcgRecipeTypeMapper.addHtcgRecipeType(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatchHtcgRecipeType(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcgRecipeTypeMapper.addBatchHtcgRecipeType(list);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcgRecipeType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HtcgRecipeType> list = htcgRecipeTypeMapper.queryHtcgRecipeType(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			List<HtcgRecipeType> list = htcgRecipeTypeMapper.queryHtcgRecipeType(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HtcgRecipeType queryHtcgRecipeTypeByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgRecipeTypeMapper.queryHtcgRecipeTypeByCode(entityMap);
	}

	@Override
	public String deleteHtcgRecipeType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcgRecipeTypeMapper.deleteHtcgRecipeType(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgRecipeType(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
	try {
			
			htcgRecipeTypeMapper.deleteBatchHtcgRecipeType(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String updateHtcgRecipeType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcgRecipeTypeMapper.updateHtcgRecipeType(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

	@Override
	public String importHtcgRecipeType(Map<String, Object> entityMap)
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
 				 
 				 mapVo.put("recipe_type_code", map.get("recipe_type_code").get(1));
 				 
 				 mapVo.put("recipe_type_name", map.get("recipe_type_name").get(1));
 				 
 				 mapVo.put("is_stop", 0);
 				 
 				 mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("recipe_type_name").toString()));
 				
 				 mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("recipe_type_name").toString()));
 				 
 				 HtcgRecipeType htcgRecipeType = htcgRecipeTypeMapper.queryHtcgRecipeTypeByCode(mapVo);
 				
 				 if(htcgRecipeType !=null){
 					 
 					return "{\"error\":\""+ map.get("recipe_type_code").get(0)+" 医嘱分类编码:"+map.get("recipe_type_code").get(1)+" 重复.\",\"state\":\"false\"}";
 				 }
 				 
 				 resultSet.add(mapVo);

        	   }


        	   if(resultSet.size() > 0 ){
        		  htcgRecipeTypeMapper.addBatchHtcgRecipeType(resultSet);  
        	   }
        	  return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
        	  
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

}
