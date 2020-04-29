package com.chd.hrp.htcg.serviceImpl.making.drgs;
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
import com.chd.hrp.htcg.dao.making.drgs.HtcgDrgsTypeMapper;
import com.chd.hrp.htcg.entity.making.drgs.HtcgDrgsType;
import com.chd.hrp.htcg.service.making.drgs.HtcgDrgsTypeService;
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


@Service("htcgDrgsTypeService")
public class HtcgDrgsTypeServiceImpl implements HtcgDrgsTypeService {

	private static Logger logger = Logger.getLogger(HtcgDrgsTypeServiceImpl.class);
	
	@Resource(name = "htcgDrgsTypeMapper")
	private final HtcgDrgsTypeMapper htcgDrgsTypeMapper = null;

	@Override
	public String addHtcgDrgsType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			HtcgDrgsType htcgDrgsType = htcgDrgsTypeMapper.queryHtcgDrgsTypeByCode(entityMap);
			
			if(htcgDrgsType != null){
				
				return "{\"error\":\"编码重复请重新输入!.\",\"state\":\"false\"}";
			}
			
			htcgDrgsTypeMapper.addHtcgDrgsType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatchHtcgDrgsType(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
            htcgDrgsTypeMapper.addBatchHtcgDrgsType(list);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcgDrgsType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<HtcgDrgsType> list = htcgDrgsTypeMapper.queryHtcgDrgsType(entityMap) ;
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgDrgsType> list = htcgDrgsTypeMapper.queryHtcgDrgsType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcgDrgsType queryHtcgDrgsTypeByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgDrgsTypeMapper.queryHtcgDrgsTypeByCode(entityMap);
	}

	@Override
	public String deleteHtcgDrgsType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	try {
			
            htcgDrgsTypeMapper.deleteHtcgDrgsType(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgDrgsType(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
      try {
			
            htcgDrgsTypeMapper.deleteBatchHtcgDrgsType(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String updateHtcgDrgsType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
	            htcgDrgsTypeMapper.updateHtcgDrgsType(entityMap);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"修改失败\"}");
			}
	}

	@Override
	public String importHtcgDrgsType(Map<String, Object> entityMap)
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
 				 
 				 mapVo.put("drgs_type_code", map.get("drgs_type_code").get(1));
 				 
 				 mapVo.put("drgs_type_name", map.get("drgs_type_name").get(1));
 				 
 				 mapVo.put("drgs_type_note", map.get("drgs_type_note").get(1));
 				 
 				 mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("drgs_type_name").toString()));
 				
 		         mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("drgs_type_name").toString()));
 				 
 				 if(!StringTool.isNotBlank(mapVo.get("drgs_type_note"))){
 					 mapVo.put("drgs_type_note", "");
 				 }
 				 
 				HtcgDrgsType htcgDrgsType = htcgDrgsTypeMapper.queryHtcgDrgsTypeByCode(mapVo);
 				
 				if(htcgDrgsType != null){
 					
 					return "{\"error\":\""+ map.get("drgs_type_code").get(0)+"\t病种分类:"+map.get("drgs_type_code").get(1)+" 重复.\",\"state\":\"false\"}";
 				}
 				
 		         resultSet.add(mapVo);
 		         
        	   }

	            if(resultSet.size() > 0 ){
	        	
	        		  htcgDrgsTypeMapper.addBatchHtcgDrgsType(resultSet);
	        	}
        	 
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"导入失败\"}");
			}
	}
	
}
