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
import com.chd.hrp.htc.dao.task.basic.HtcWageItemDictMapper;
import com.chd.hrp.htc.entity.task.basic.HtcWageItemDict;
import com.chd.hrp.htc.service.task.basic.HtcWageItemDictService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 2015-3-17 author:alfred
 */

@Service("htcWageItemDictService")
public class HtcWageItemDictServiceImpl implements HtcWageItemDictService {

	private static Logger logger = Logger.getLogger(HtcWageItemDictServiceImpl.class);

	@Resource(name = "htcWageItemDictMapper")
	private final HtcWageItemDictMapper htcWageItemDictMapper = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	@Override
	public String addHtcWageItemDict(Map<String, Object> entityMap) throws DataAccessException {

		try{
			
			Map<String, Object> utilMap = new HashMap<String, Object>();
			utilMap.put("group_id", entityMap.get("group_id"));
			utilMap.put("hos_id", entityMap.get("hos_id"));
			utilMap.put("copy_code", entityMap.get("copy_code"));
			utilMap.put("field_table", "htc_wage_item_dict".toUpperCase());
			utilMap.put("field_sort", "sort_code");
			int count = sysFunUtilMapper.querySysMaxSort(utilMap);
			entityMap.put("sort_code", count);
			HtcWageItemDict htcWageItemDict = queryHtcWageItemDictByCode(entityMap);
			
			if(null != htcWageItemDict){
				
				return "{\"error\":\"工资项重复.\",\"state\":\"false\"}";

			}
			
			htcWageItemDictMapper.addHtcWageItemDict(entityMap);
			
			Map<String, Object> utilMapVo = new HashMap<String, Object>();
			utilMapVo.put("group_id", entityMap.get("group_id"));
			utilMapVo.put("hos_id", entityMap.get("hos_id"));
			utilMapVo.put("copy_code", entityMap.get("copy_code"));

			Map<String, Object> m = htcWageItemDictMapper.queryHtcWageItemMapMaxId(utilMapVo); 
			String clum_id = m.get("CLUM_ID").toString(); 
			Map<String, Object> wageItemMap =new HashMap<String, Object>();
			wageItemMap.put("group_id", entityMap.get("group_id"));
			wageItemMap.put("hos_id", entityMap.get("hos_id"));
			wageItemMap.put("copy_code", entityMap.get("copy_code"));
			wageItemMap.put("wage_item_code", entityMap.get("wage_item_code"));
			wageItemMap.put("clum_code", "WAGE"+clum_id);
			wageItemMap.put("clum_id", clum_id);
			
			htcWageItemDictMapper.addHtcWageItemMap(wageItemMap);
			
			htcWageItemDictMapper.addHtcWageItemMapByAlter(wageItemMap);
				
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcWageItemDict(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			
			List<HtcWageItemDict> list = htcWageItemDictMapper.queryHtcWageItemDict(entityMap);
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcWageItemDict> list = htcWageItemDictMapper.queryHtcWageItemDict(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}

	@Override
	public HtcWageItemDict queryHtcWageItemDictByCode(Map<String, Object> entityMap) throws DataAccessException {

		return htcWageItemDictMapper.queryHtcWageItemDictByCode(entityMap);

	}

	@Override
	public String deleteBatchHtcWageItemDict(List<Map<String, Object>> entityList) throws DataAccessException {
		
		
		try {

			htcWageItemDictMapper.deleteBatchHtcWageItemMap(entityList);
			htcWageItemDictMapper.deleteBatchHtcWageItemDict(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}"); 
		}
	}

	@Override
	public String updateHtcWageItemDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			htcWageItemDictMapper.updateHtcWageItemDict(entityMap);	
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"修改失败\"}"); 
		}	
	}

	@Override
	public String impHtcWageItemDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			
			List<Map<String,Object>> resultSet = new ArrayList<Map<String,Object>>();
			
			for (Map<String, List<String>> map : list) {
				
                Map<String, Object> mapVo = new HashMap<String, Object>();
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
				mapVo.put("copy_code", SessionManager.getCopyCode());    
				
                mapVo.put("wage_item_code", map.get("wage_item_code").get(1));
				
				mapVo.put("wage_item_name", map.get("wage_item_name").get(1));
				
				Map<String, Object> utilMap = new HashMap<String, Object>();
				
				utilMap.put("group_id", mapVo.get("group_id"));
				
				utilMap.put("hos_id", mapVo.get("hos_id"));
				
				utilMap.put("copy_code", mapVo.get("copy_code"));
				
				utilMap.put("field_table", "htc_wage_item_dict".toUpperCase());
				
				utilMap.put("field_sort", "sort_code");
				
				int count = sysFunUtilMapper.querySysMaxSort(utilMap);
				
				mapVo.put("sort_code", count);
				
				mapVo.put("remark", null==map.get("remark").get(1)?"":map.get("remark").get(1));
				
                mapVo.put("is_stop", 0);
				
				mapVo.put("spell_code",StringTool.toPinyinShouZiMu(map.get("wage_item_name").get(1).toString()));
				
				mapVo.put("wbx_code", StringTool.toWuBi(map.get("wage_item_name").get(1).toString()));
				
				HtcWageItemDict htcWageItemDict = queryHtcWageItemDictByCode(mapVo);
				
				if(null != htcWageItemDict){
					
					return "{\"error\":\""+ map.get("wage_item_code").get(0)+"工资项:"+map.get("wage_item_code").get(1)+"已存在.\",\"state\":\"false\"}";

				}
				
				Map<String, Object> utilMapVo = new HashMap<String, Object>();
				
				utilMapVo.put("group_id", SessionManager.getGroupId());
				
				utilMapVo.put("hos_id", SessionManager.getHosId());
				
				utilMapVo.put("copy_code", SessionManager.getCopyCode());

				Map<String, Object> m = htcWageItemDictMapper.queryHtcWageItemMapMaxId(utilMapVo); 
				
				String clum_id = m.get("CLUM_ID").toString(); 
				
				Map<String, Object> wageItemMap =new HashMap<String, Object>();
				
				wageItemMap.put("group_id", mapVo.get("group_id"));
				
				wageItemMap.put("hos_id", mapVo.get("hos_id"));
				
				wageItemMap.put("copy_code", mapVo.get("copy_code"));
				
				wageItemMap.put("wage_item_code", mapVo.get("wage_item_code"));
				
				wageItemMap.put("clum_code", "WAGE"+clum_id);
				
				wageItemMap.put("clum_id", clum_id);
				
				htcWageItemDictMapper.addHtcWageItemDict(mapVo);
				
				htcWageItemDictMapper.addHtcWageItemMap(wageItemMap);
				
		
			}
			
			 return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			 
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			
			throw new SysException(e.getMessage());
		}
	}
}
