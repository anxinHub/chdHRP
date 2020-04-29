
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
import com.chd.hrp.htcg.dao.info.HtcgDrugTypeDictMapper;
import com.chd.hrp.htcg.entity.info.HtcgDrugTypeDict;
import com.chd.hrp.htcg.service.info.HtcgDrugTypeDictService;
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


@Service("htcgDrugTypeDictService")
public class HtcgDrugTypeDictServiceImpl implements HtcgDrugTypeDictService {

	private static Logger logger = Logger.getLogger(HtcgDrugTypeDictServiceImpl.class);
	
	@Resource(name = "htcgDrugTypeDictMapper")
	private final HtcgDrugTypeDictMapper htcgDrugTypeDictMapper = null;

	@Override
	public String addHtcgDrugTypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcgDrugTypeDict htcgDrugTypeDict = htcgDrugTypeDictMapper.queryHtcgDrugTypeDictByCode(entityMap);
			
			if(null !=htcgDrugTypeDict){
				
				return "{\"error\":\"编码已存在.\",\"state\":\"true\"}";
				
			}
			
			htcgDrugTypeDictMapper.addHtcgDrugTypeDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatchHtcgDrugTypeDict(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
       try {
			
			htcgDrugTypeDictMapper.addBatchHtcgDrugTypeDict(list);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcgDrugTypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	      SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<HtcgDrugTypeDict> list = htcgDrugTypeDictMapper.queryHtcgDrugTypeDict(entityMap);
			
			return ChdJson.toJson(list);
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			
			List<HtcgDrugTypeDict> list = htcgDrugTypeDictMapper.queryHtcgDrugTypeDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HtcgDrugTypeDict queryHtcgDrugTypeDictByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgDrugTypeDictMapper.queryHtcgDrugTypeDictByCode(entityMap);
	}

	@Override
	public String deleteHtcgDrugTypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
				htcgDrugTypeDictMapper.deleteHtcgDrugTypeDict(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

	@Override
	public String deleteBatchHtcgDrugTypeDict(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
				htcgDrugTypeDictMapper.deleteBatchHtcgDrugTypeDict(list);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

	@Override
	public String updateHtcgDrugTypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
				htcgDrugTypeDictMapper.updateHtcgDrugTypeDict(entityMap);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

	@Override
	public String importHtcgDrugTypeDict(Map<String, Object> entityMap)
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
  				 
  				 mapVo.put("drug_type_code", map.get("drug_type_code").get(1));
  				 
  				 mapVo.put("drug_type_name", map.get("drug_type_name").get(1));
  				 
  				 mapVo.put("is_stop", 0);
  				 
  				 mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("drug_type_name").toString()));
  				
  				 mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("drug_type_name").toString()));

  				HtcgDrugTypeDict htcgDrugTypeDict = htcgDrugTypeDictMapper.queryHtcgDrugTypeDictByCode(mapVo);
  				
  				if(null !=htcgDrugTypeDict){
  					
  					return "{\"error\":\""+ map.get("drug_type_code").get(0)+" 药品类别编码:"+map.get("drug_type_code").get(1)+" 已存在.\",\"state\":\"false\"}";
  					
  				}
  				
  				 resultSet.add(mapVo);

        	   }
        	 
        	   if(resultSet.size() > 0 ){
        		   htcgDrugTypeDictMapper.addBatchHtcgDrugTypeDict(resultSet);
        	   }
			  return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}
    
	
}
