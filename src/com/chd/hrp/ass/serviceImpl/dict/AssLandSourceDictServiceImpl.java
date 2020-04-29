/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.dict;

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
import com.chd.hrp.ass.dao.dict.AssLandSourceDictMapper;
import com.chd.hrp.ass.entity.dict.AssLandSourceDict;
import com.chd.hrp.ass.service.dict.AssLandSourceDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 土地来源
 * @Table:
 * ASS_LAND_SOURCE_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assLandSourceDictService")
public class AssLandSourceDictServiceImpl implements AssLandSourceDictService {

	private static Logger logger = Logger.getLogger(AssLandSourceDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assLandSourceDictMapper")
	private final AssLandSourceDictMapper assLandSourceDictMapper = null;
    
	/**
	 * @Description 
	 * 添加土地来源<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			int state = assLandSourceDictMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加土地来源<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assLandSourceDictMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新土地来源<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assLandSourceDictMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新土地来源<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assLandSourceDictMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除土地来源<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assLandSourceDictMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除土地来源<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assLandSourceDictMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加土地来源<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象土地来源
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssLandSourceDict> list = (List<AssLandSourceDict>)assLandSourceDictMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assLandSourceDictMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assLandSourceDictMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集土地来源<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssLandSourceDict> list = (List<AssLandSourceDict>)assLandSourceDictMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssLandSourceDict> list = (List<AssLandSourceDict>)assLandSourceDictMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象土地来源<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assLandSourceDictMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取土地来源<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssLandSourceDict
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assLandSourceDictMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取土地来源<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssLandSourceDict>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assLandSourceDictMapper.queryExists(entityMap);
	}
	
	@Override
	public String readAssLandSourceDictFiles(Map<String, Object> entityMap) throws DataAccessException {
		String dataJson = null;
		Map<String, Object> is_stop_map = new HashMap<String, Object>();
		is_stop_map.put("是", "1");
		is_stop_map.put("否", "0");
		is_stop_map.put("1", "1");
		is_stop_map.put("0", "0");
		try {

			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}
			for (Map<String, List<String>> map2 : list) {

				if (map2.get("land_source_code").get(1) == null || map2.get("land_source_code").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("land_source_code").get(0)
							+ "，保养项目编码为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("land_source_code").get(0)
							+ "\"}";
				} else if (map2.get("land_source_name").get(1) == null || map2.get("land_source_name").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("land_source_name").get(0)
							+ "，保养项目名称为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("land_source_name").get(0)
							+ "\"}";
				} else if (map2.get("is_stop").get(1) == null || map2.get("is_stop").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("is_stop").get(0) + "，是否停用为空！\",\"state\":\"false\",\"row_cell\":\""
							+ map2.get("is_stop").get(0) + "\"}";
				} 

				Map<String, Object> mapVo1 = new HashMap<String, Object>();

				mapVo1.put("group_id", SessionManager.getGroupId());
				mapVo1.put("hos_id", SessionManager.getHosId());
				mapVo1.put("copy_code", SessionManager.getCopyCode());
				mapVo1.put("land_source_code", map2.get("land_source_code").get(1));
				mapVo1.put("land_source_name", map2.get("land_source_name").get(1));
				mapVo1.put("is_stop", is_stop_map.get(map2.get("is_stop").get(1)));
				// 判断唯一性 编码

				Map<String, Object> map_code = new HashMap<String, Object>();
				map_code.put("group_id", mapVo1.get("group_id"));
				map_code.put("hos_id", mapVo1.get("hos_id"));
				map_code.put("copy_code", mapVo1.get("copy_code"));
				map_code.put("land_source_code", mapVo1.get("land_source_code"));

				AssLandSourceDict data_exc_extis_code = assLandSourceDictMapper.queryAssLandSourceDictByUniqueness(map_code);

				if (data_exc_extis_code != null) {
					continue;
					/*return "{\"warn\":\"" + map2.get("land_source_code").get(0)
							+ ",编码已经存在！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("land_source_code").get(0)
							+ "\"}";*/

				}
				// 判断唯一性 名字

				Map<String, Object> map_name = new HashMap<String, Object>();
				map_name.put("group_id", mapVo1.get("group_id"));
				map_name.put("hos_id", mapVo1.get("hos_id"));
				map_name.put("copy_code", mapVo1.get("copy_code"));
				map_name.put("land_source_name", mapVo1.get("land_source_name"));

				AssLandSourceDict data_exc_extis_name = assLandSourceDictMapper.queryAssLandSourceDictByUniqueness(map_name);

				if (data_exc_extis_name != null) {
					continue;
					/*return "{\"warn\":\"" + map2.get("land_source_name").get(0)
							+ ",名称已经存在！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("land_source_name").get(0)
							+ "\"}";*/

				}

				mapVo1.put("spell_code", StringTool.toPinyinShouZiMu(mapVo1.get("land_source_name").toString()));

				mapVo1.put("wbx_code", StringTool.toWuBi(mapVo1.get("land_source_name").toString()));
				
				assLandSourceDictMapper.add(mapVo1);
				dataJson = "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}

			return dataJson;

		} catch (Exception e) {

			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());

		}
	}
	@Override
	public AssLandSourceDict queryByName(Map<String, Object> entityMap) throws DataAccessException {
		return assLandSourceDictMapper.queryByName(entityMap);

	}
	
}
