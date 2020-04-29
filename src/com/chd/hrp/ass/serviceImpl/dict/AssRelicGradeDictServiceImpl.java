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
import com.chd.hrp.ass.dao.dict.AssRelicGradeDictMapper;
import com.chd.hrp.ass.entity.dict.AssRelicGradeDict;
import com.chd.hrp.ass.service.dict.AssRelicGradeDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 土地来源
 * @Table:
 * ASS_RELIC_GRADE_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assRelicGradeDictService")
public class AssRelicGradeDictServiceImpl implements AssRelicGradeDictService {

	private static Logger logger = Logger.getLogger(AssRelicGradeDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assRelicGradeDictMapper")
	private final AssRelicGradeDictMapper assRelicGradeDictMapper = null;
    
	/**
	 * @Description 
	 * 添加土地来源<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象土地来源
		AssRelicGradeDict assRelicGradeDict = queryByCode(entityMap);

		if (assRelicGradeDict != null) {
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}
		
		try {
			
			int state = assRelicGradeDictMapper.add(entityMap);
			
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
			
			assRelicGradeDictMapper.addBatch(entityList);
			
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
			
		  int state = assRelicGradeDictMapper.update(entityMap);
			
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
			
		  assRelicGradeDictMapper.updateBatch(entityList);
			
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
			
			int state = assRelicGradeDictMapper.delete(entityMap);
			
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
			
			assRelicGradeDictMapper.deleteBatch(entityList);
			
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
		
		List<AssRelicGradeDict> list = (List<AssRelicGradeDict>)assRelicGradeDictMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assRelicGradeDictMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assRelicGradeDictMapper.add(entityMap);
			
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
			
			List<AssRelicGradeDict> list = (List<AssRelicGradeDict>)assRelicGradeDictMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssRelicGradeDict> list = (List<AssRelicGradeDict>)assRelicGradeDictMapper.query(entityMap, rowBounds);
			
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
		return assRelicGradeDictMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取土地来源<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssRelicGradeDict
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assRelicGradeDictMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取土地来源<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssRelicGradeDict>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assRelicGradeDictMapper.queryExists(entityMap);
	}
	
	@Override
	public String readAssRelicGradeDictFiles(Map<String, Object> entityMap) throws DataAccessException {
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

				if (map2.get("relic_grade_code").get(1) == null || map2.get("relic_grade_code").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("relic_grade_code").get(0)
							+ "，保养项目编码为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("relic_grade_code").get(0)
							+ "\"}";
				} else if (map2.get("relic_grade_name").get(1) == null || map2.get("relic_grade_name").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("relic_grade_name").get(0)
							+ "，保养项目名称为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("relic_grade_name").get(0)
							+ "\"}";
				} else if (map2.get("is_stop").get(1) == null || map2.get("is_stop").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("is_stop").get(0) + "，是否停用为空！\",\"state\":\"false\",\"row_cell\":\""
							+ map2.get("is_stop").get(0) + "\"}";
				} 

				Map<String, Object> mapVo1 = new HashMap<String, Object>();

				mapVo1.put("group_id", SessionManager.getGroupId());
				mapVo1.put("hos_id", SessionManager.getHosId());
				mapVo1.put("copy_code", SessionManager.getCopyCode());
				mapVo1.put("relic_grade_code", map2.get("relic_grade_code").get(1));
				mapVo1.put("relic_grade_name", map2.get("relic_grade_name").get(1));
				mapVo1.put("is_stop", is_stop_map.get(map2.get("is_stop").get(1)));
				// 判断唯一性 编码

				Map<String, Object> map_code = new HashMap<String, Object>();
				map_code.put("group_id", mapVo1.get("group_id"));
				map_code.put("hos_id", mapVo1.get("hos_id"));
				map_code.put("copy_code", mapVo1.get("copy_code"));
				map_code.put("relic_grade_code", mapVo1.get("relic_grade_code"));

				AssRelicGradeDict data_exc_extis_code = assRelicGradeDictMapper.queryAssRelicGradeDictByUniqueness(map_code);

				if (data_exc_extis_code != null) {
					continue;
					/*return "{\"warn\":\"" + map2.get("relic_grade_code").get(0)
							+ ",编码已经存在！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("relic_grade_code").get(0)
							+ "\"}";*/

				}
				// 判断唯一性 名字

				Map<String, Object> map_name = new HashMap<String, Object>();
				map_name.put("group_id", mapVo1.get("group_id"));
				map_name.put("hos_id", mapVo1.get("hos_id"));
				map_name.put("copy_code", mapVo1.get("copy_code"));
				map_name.put("relic_grade_name", mapVo1.get("relic_grade_name"));

				AssRelicGradeDict data_exc_extis_name = (AssRelicGradeDict) assRelicGradeDictMapper.queryAssRelicGradeDictByUniqueness(map_name);

				if (data_exc_extis_name != null) {
					continue;
					/*return "{\"warn\":\"" + map2.get("relic_grade_name").get(0)
							+ ",名称已经存在！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("relic_grade_name").get(0)
							+ "\"}";*/

				}

				mapVo1.put("spell_code", StringTool.toPinyinShouZiMu(mapVo1.get("relic_grade_name").toString()));

				mapVo1.put("wbx_code", StringTool.toWuBi(mapVo1.get("relic_grade_name").toString()));
				
				assRelicGradeDictMapper.add(mapVo1);
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
	public AssRelicGradeDict queryByName(Map<String, Object> entityMap) throws DataAccessException {
		return assRelicGradeDictMapper.queryByName(entityMap);
	}
	
}
