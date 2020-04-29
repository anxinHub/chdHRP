
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.serviceImpl.dict;

import java.util.*;
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
import com.chd.hrp.ass.dao.dict.AssUsageDictMapper;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
import com.chd.hrp.ass.entity.dict.AssUsageDict;
import com.chd.hrp.ass.service.dict.AssUsageDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050104 资产用途
 * @Table:
 * ASS_USAGE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assUsageDictService")
public class AssUsageDictServiceImpl implements AssUsageDictService {

	private static Logger logger = Logger.getLogger(AssUsageDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assUsageDictMapper")
	private final AssUsageDictMapper assUsageDictMapper = null;
    
	/**
	 * @Description 
	 * 添加050104 资产用途<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssUsageDict(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050104 资产用途
		AssUsageDict assUsageDict = queryAssUsageDictByCode(entityMap);

		if (assUsageDict != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assUsageDictMapper.addAssUsageDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050104 资产用途<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssUsageDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assUsageDictMapper.addBatchAssUsageDict(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050104 资产用途<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssUsageDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assUsageDictMapper.updateAssUsageDict(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050104 资产用途<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssUsageDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assUsageDictMapper.updateBatchAssUsageDict(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050104 资产用途<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssUsageDict(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assUsageDictMapper.deleteAssUsageDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050104 资产用途<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssUsageDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assUsageDictMapper.deleteBatchAssUsageDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集050104 资产用途<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssUsageDict(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssUsageDict> list = assUsageDictMapper.queryAssUsageDict(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssUsageDict> list = assUsageDictMapper.queryAssUsageDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050104 资产用途<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssUsageDict queryAssUsageDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assUsageDictMapper.queryAssUsageDictByCode(entityMap);
	}
	@Override
	public AssUsageDict queryAssUsageDictByCodeOrName(Map<String, Object> mapVo) throws DataAccessException {
		return assUsageDictMapper.queryAssUsageDictByCodeOrName(mapVo);
	}
	/**
	 * 导入数据 050104 资产用途
	 * @throws Exception 
	 */
	@Override
	public String assUsageDictImport(Map<String, Object> mapVo) throws Exception {
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(mapVo);
			if (list==null || list.size()==0) {
				return	"{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}
			for (Map<String, List<String>> map : list) {
				if(map.get("equi_usage_code").get(1)==null || "".equals(map.get("equi_usage_code").get(1))){
					return "{\"msg\":\""+map.get("equi_usage_code").get(0)+"，为空！\",\"state\":\"false\",\"row_cell\":\""+map.get("equi_usage_code").get(0)+"\"}";

				}
				if (map.get("equi_usage_name").get(1)==null || map.get("equi_usage_name").get(1).equals("")){
					return "{\"msg\":\""+map.get("equi_usage_name").get(0)+"，为空！\",\"state\":\"false\",\"row_cell\":\""+map.get("equi_usage_name").get(0)+"\"}";
				}
				if (map.get("is_stop").get(1)==null || map.get("is_stop").get(1).equals("")){
					return "{\"msg\":\""+map.get("is_stop").get(0)+"，为空！\",\"state\":\"false\",\"row_cell\":\""+map.get("is_stop").get(0)+"\"}";
				}
				Map<String, Object> mapVo1 = new HashMap<String, Object>();

				mapVo1.put("group_id", SessionManager.getGroupId());

				mapVo1.put("hos_id", SessionManager.getHosId());

				mapVo1.put("copy_code", SessionManager.getCopyCode());
				mapVo1.put("equi_usage_code", map.get("equi_usage_code").get(1));
				mapVo1.put("equi_usage_name", map.get("equi_usage_name").get(1));
				mapVo1.put("is_stop", map.get("is_stop").get(1));
				
				Map<String, Object> map_code = new HashMap<String, Object>();
				map_code.put("group_id", mapVo1.get("group_id"));
				map_code.put("hos_id", mapVo1.get("hos_id"));
				map_code.put("copy_code", mapVo1.get("copy_code"));
				map_code.put("equi_usage_code", mapVo1.get("equi_usage_code"));
				AssUsageDict data_exc_extis_code = assUsageDictMapper.queryAssUsageDictByCodeOrName(map_code);

				if (data_exc_extis_code != null) {
					return "{\"warn\":\"" + map.get("equi_usage_code").get(0) + ",编码已经存在！\",\"state\":\"false\",\"row_cell\":\""
							+  map.get("equi_usage_code").get(0) + "\"}";

				}
				// 判断唯一性 名字

				Map<String, Object> map_name = new HashMap<String, Object>();
				map_name.put("group_id", mapVo1.get("group_id"));
				map_name.put("hos_id", mapVo1.get("hos_id"));
				map_name.put("copy_code", mapVo1.get("copy_code"));
				map_name.put("equi_usage_name", mapVo1.get("equi_usage_name"));

				AssUsageDict data_exc_extis_name = assUsageDictMapper.queryAssUsageDictByCodeOrName(map_name);

				if (data_exc_extis_name != null) {

					return "{\"warn\":\"" +  map.get("equi_usage_name").get(0) + ",名称已经存在！\",\"state\":\"false\",\"row_cell\":\""
							+ map.get("equi_usage_name").get(0) + "\"}";

				}
				mapVo1.put("spell_code", StringTool.toPinyinShouZiMu(mapVo1.get("equi_usage_name").toString()));

				mapVo1.put("wbx_code", StringTool.toWuBi(mapVo1.get("equi_usage_name").toString()));
				
				assUsageDictMapper.addAssUsageDict(mapVo1);
			}
			return"{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			throw new SysException(e);
		}
	}
	
}
