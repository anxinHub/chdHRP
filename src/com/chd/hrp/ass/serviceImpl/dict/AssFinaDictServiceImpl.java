/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.ass.serviceImpl.dict;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.dao.dict.AssNoDictMapper;
import com.chd.hrp.ass.dao.dict.AssFinaDictMapper;
import com.chd.hrp.ass.entity.dict.AssNoDict;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
import com.chd.hrp.ass.entity.dict.AssFinaDict;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssFinaDictService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 050101 资产分类字典
 * @Table: MAT_FINA_TYPE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("assFinaDictService")
public class AssFinaDictServiceImpl implements AssFinaDictService {

	private static final Reader reader = null;

	private static final JSONArray fina_type_name = null;

	private static final JSONArray fina_type_code = null;

	private static Logger logger = Logger.getLogger(AssFinaDictServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "assFinaDictMapper")
	private final AssFinaDictMapper assFinaDictMapper = null;

	// 引入DAO操作
	@Resource(name = "assNoDictMapper")
	private final AssNoDictMapper assNoDictMapper = null;

	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;


	/**
	 * @Description 添加050101 资产分类字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAssFinaDict(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> map_code = new HashMap<String, Object>();
		map_code.put("group_id", entityMap.get("group_id"));
		map_code.put("hos_id", entityMap.get("hos_id"));
		map_code.put("copy_code", entityMap.get("copy_code"));
		map_code.put("fina_type_code", entityMap.get("fina_type_code"));
		// 判断编码是否重复
		AssFinaDict data_exc_extis_code = assFinaDictMapper.queryAssFinaDictByUniqueness(map_code);

		if (data_exc_extis_code != null) {

			return "{\"error\":\"编码重复,请重新添加.\"}";

		}
		Map<String, Object> map_name = new HashMap<String, Object>();
		map_name.put("group_id", entityMap.get("group_id"));
		map_name.put("hos_id", entityMap.get("hos_id"));
		map_name.put("copy_code", entityMap.get("copy_code"));
		map_name.put("fina_type_name", entityMap.get("fina_type_name"));

		AssFinaDict data_exc_extis_name = assFinaDictMapper.queryAssFinaDictByUniqueness(map_name);

		if (data_exc_extis_name != null) {

			return "{\"error\":\"名称重复,请重新添加.\"}";

		}

		// 判断上级编码是否为空 不为空则反查上级编码所属资产性质
		if (StringTool.isNotBlank(entityMap.get("super_code"))) {
			Map<String, Object> map_super = new HashMap<String, Object>();
			map_super.put("group_id", entityMap.get("group_id"));
			map_super.put("hos_id", entityMap.get("hos_id"));
			map_super.put("copy_code", entityMap.get("copy_code"));
			map_super.put("fina_type_code", entityMap.get("super_code"));

			AssFinaDict data_exc_extis_super = assFinaDictMapper.queryAssFinaDictByUniqueness(map_super);

			if (null == data_exc_extis_super) {
				return "{\"error\":\"上级编码不存在,请重新添加.\"}";
			} 
			// 判断上级编码是否为末级
			if (data_exc_extis_super.getIs_last() == 1) {
				Map<String, Object> update_is_last = new HashMap<String, Object>();
				update_is_last.put("group_id", entityMap.get("group_id"));
				update_is_last.put("hos_id", entityMap.get("hos_id"));
				update_is_last.put("copy_code", entityMap.get("copy_code"));
				update_is_last.put("fina_type_code", data_exc_extis_super.getFina_type_code());
				update_is_last.put("is_last", "0");
				assFinaDictMapper.updateAssFinaDict(update_is_last);
			}

		}
		try {

			int state = assFinaDictMapper.addAssFinaDict(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量添加050101 资产分类字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAssFinaDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assFinaDictMapper.addBatchAssFinaDict(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新050101 资产分类字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAssFinaDict(Map<String, Object> entityMap) throws DataAccessException {
		// 判断上级编码是否为空 不为空则反查上级编码所属资产性质
		if (Strings.isNotBlank((CharSequence) entityMap.get("super_code"))) {
			Map<String, Object> map_super = new HashMap<String, Object>();
			map_super.put("group_id", entityMap.get("group_id"));
			map_super.put("hos_id", entityMap.get("hos_id"));
			map_super.put("copy_code", entityMap.get("copy_code"));
			map_super.put("fina_type_code", entityMap.get("super_code"));

			AssFinaDict data_exc_extis_super = assFinaDictMapper.queryAssFinaDictByUniqueness(map_super);

			if (null == data_exc_extis_super) {
				return "{\"error\":\"上级编码不存在,请重新添加.\"}";
			} 

		}
		try {

			int state = assFinaDictMapper.updateAssFinaDict(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量更新050101 资产分类字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAssFinaDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assFinaDictMapper.updateBatchAssFinaDict(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除050101 资产分类字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAssFinaDict(Map<String, Object> entityMap) throws DataAccessException {

		try {
			// 如果是非末级则先删除下级在删除自己
			AssFinaDict at= assFinaDictMapper.queryAssFinaDictByCode(entityMap);
			Map<String, Object> supper_code_map =new HashMap<String, Object>();
			supper_code_map.put("group_id", at.getGroup_id());
			supper_code_map.put("hos_id", at.getHos_id());
			supper_code_map.put("copy_code", at.getCopy_code());
			supper_code_map.put("super_code", at.getFina_type_code());
			assFinaDictMapper.deleteAssFinaDictBySuperCode(supper_code_map);
			//删除自身
			int state = assFinaDictMapper.deleteAssFinaDict(entityMap);
			
			//如果有上级
			if(Strings.isNotBlank(at.getSuper_code())) {
				//如果上级无其它下级 则更改上级为末级  
				Map<String, Object> supper_map =new HashMap<String, Object>(entityMap);
				supper_map.put("fina_type_code", at.getSuper_code());
				List<AssFinaDict> list = assFinaDictMapper.queryAssFinaDictChild(supper_map);
				if(list == null || list.size() == 0){
					supper_map.put("is_last", "1");
					assFinaDictMapper.updateAssFinaDict(supper_map);
				}
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量删除050101 资产分类字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAssFinaDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assFinaDictMapper.deleteBatchAssFinaDict(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 查询结果集050101 资产分类字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssFinaDict(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssFinaDict> list = assFinaDictMapper.queryAssFinaDict(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssFinaDict> list = assFinaDictMapper.queryAssFinaDict(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050101 资产分类字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public synchronized AssFinaDict queryAssFinaDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assFinaDictMapper.queryAssFinaDictByCode(entityMap);
	}

	/**
	 * @Description 获取050101 资产分类字典<BR>
	 * @param entityMap
	 *            需要检索的唯一性字段
	 * @return AssFinaDict
	 * @throws DataAccessException
	 */
	@Override
	public  synchronized  AssFinaDict   queryAssFinaDictByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assFinaDictMapper.queryAssFinaDictByUniqueness(entityMap);
	}

	@Override
	public List<?> queryAssFinaDictByTree(Map<String, Object> entityMap) throws DataAccessException {
		List<?> l_map = assFinaDictMapper.queryAssFinaDictByTree(entityMap);
		return l_map;
	}

	@Override
    public String deleteAssFinaDictBySuperCode(Map<String, Object> entityMap) throws DataAccessException {
		try {

			assFinaDictMapper.deleteAssFinaDictBySuperCode(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
    }

	@Override
	public List<AssFinaDict> queryAssFinaDictList(Map<String, Object> entityMap)
			throws DataAccessException {
		return assFinaDictMapper.queryAssFinaDict(entityMap);
	}
//导入
	@Override
	public String readAssFinaDictFiles(Map<String, Object> map) throws DataAccessException {

		Map<String, Object> is_last_map = new HashMap<String, Object>();
		is_last_map.put("是", "1");
		is_last_map.put("否", "0");
		is_last_map.put("1", "1");
		is_last_map.put("0", "0");

		try {

			List<Map<String, List<String>>> list = ReadFiles.readFiles(map);
			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}
			for (Map<String, List<String>> map2 : list) {

				if (map2.get("fina_type_code").get(1) == null || map2.get("fina_type_code").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("fina_type_code").get(0)
							+ "，财务类别编码为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("fina_type_code").get(0)
							+ "\"}";
				} else if (map2.get("fina_type_name").get(1) == null || map2.get("fina_type_name").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("fina_type_name").get(0)
							+ "，财务类别名称为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("fina_type_name").get(0)
							+ "\"}";
				} else if (map2.get("is_last").get(1) == null || map2.get("is_last").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("is_last").get(0)
							+ "，是否末级分类为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("is_last").get(0) + "\"}";
				} else if (map2.get("is_stop").get(1) == null || map2.get("is_stop").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("is_stop").get(0) + "，是否停用为空！\",\"state\":\"false\",\"row_cell\":\""
							+ map2.get("is_stop").get(0) + "\"}";
				} else if (map2.get("is_budg").get(1) == null || map2.get("is_budg").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("is_budg").get(0) + "，是否预算为空！\",\"state\":\"false\",\"row_cell\":\""
							+ map2.get("is_budg").get(0) + "\"}";
				}

				Map<String, Object> mapVo1 = new HashMap<String, Object>();

				mapVo1.put("group_id", SessionManager.getGroupId());

				mapVo1.put("hos_id", SessionManager.getHosId());

				mapVo1.put("copy_code", SessionManager.getCopyCode());
				mapVo1.put("fina_type_code", map2.get("fina_type_code").get(1));
				mapVo1.put("fina_type_name", map2.get("fina_type_name").get(1));
				mapVo1.put("is_last", is_last_map.get(map2.get("is_last").get(1)));
				mapVo1.put("is_stop", is_last_map.get(map2.get("is_stop").get(1)));
				mapVo1.put("is_budg", is_last_map.get(map2.get("is_budg").get(1)));
				// 添加编码规则判断
				mapVo1.put("proj_code", "ASS_FINA_DICT");
				mapVo1.put("mod_code", "05");

				String fina_type_code1 = mapVo1.get("fina_type_code").toString();

				Map<Object, Object> rules = assBaseService.getHosRules(mapVo1);
				if (null != mapVo1.get("fina_type_code")) {
					Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
					Map<Object, Object> level_length = (Map<Object, Object>) rules.get("rules_level_length");
					Object level = level_map.get(fina_type_code1.length());

					String rules_v = rules.get("rules_view").toString();
					Strings.removeFirst(rules_v);
					if (null != level) {
						int int_level = (Integer) level;
						mapVo1.put("type_level", level);
						mapVo1.put("super_code", "");
						if (int_level == 1) {
							mapVo1.put("type_level", level);
							mapVo1.put("super_code", "");
						} else {
							mapVo1.put("type_level", level);
							int v_level = int_level - 1;
							int end = (Integer) level_length.get(v_level);
							mapVo1.put("super_code", fina_type_code1.substring(0, end));
						}
					} else {
						throw new SysException(map2.get("fina_type_code").get(0) + ",分类编码规则错误！");
					}

				}

				// 判断唯一性 编码

				Map<String, Object> map_code = new HashMap<String, Object>();
				map_code.put("group_id", mapVo1.get("group_id"));
				map_code.put("hos_id", mapVo1.get("hos_id"));
				map_code.put("copy_code", mapVo1.get("copy_code"));
				map_code.put("fina_type_code", mapVo1.get("fina_type_code"));

				AssFinaDict data_exc_extis_code = assFinaDictMapper.queryAssFinaDictByUniqueness(map_code);

				if (data_exc_extis_code != null) {
					continue;
				}

				// 判断上级编码是否为空 不为空则反查上级编码所属资产性质
				if (StringTool.isNotBlank(mapVo1.get("super_code"))) {
					Map<String, Object> map_super = new HashMap<String, Object>();
					map_super.put("group_id", mapVo1.get("group_id"));
					map_super.put("hos_id", mapVo1.get("hos_id"));
					map_super.put("copy_code", mapVo1.get("copy_code"));
					map_super.put("fina_type_code", mapVo1.get("super_code"));

					AssFinaDict data_exc_extis_super = assFinaDictMapper.queryAssFinaDictByCode(map_super);

					if (null != data_exc_extis_super) {

						// 判断上级编码是否为末级
						if (data_exc_extis_super.getIs_last() == 1) {
							Map<String, Object> update_is_last = new HashMap<String, Object>();
							update_is_last.put("group_id", mapVo1.get("group_id"));
							update_is_last.put("hos_id", mapVo1.get("hos_id"));
							update_is_last.put("copy_code", mapVo1.get("copy_code"));
							update_is_last.put("fina_type_code", data_exc_extis_super.getFina_type_code());
							update_is_last.put("is_last", "0");
							update_is_last.put("super_code", data_exc_extis_super.getSuper_code());
							assFinaDictMapper.updateAssFinaDict(update_is_last);
						}
					} else {

						throw new SysException(map2.get("fina_type_code").get(0) + ",编码错误或上级编码不存在！");
					}
				}
				Map<String, Object> map_name = new HashMap<String, Object>();
				map_name.put("group_id", mapVo1.get("group_id"));
				map_name.put("hos_id", mapVo1.get("hos_id"));
				map_name.put("copy_code", mapVo1.get("copy_code"));
				map_name.put("fina_type_name", mapVo1.get("fina_type_name"));

				AssFinaDict data_exc_extis_name = assFinaDictMapper.queryAssFinaDictByUniqueness(map_name);

				if (data_exc_extis_name != null) {
					continue;
					/*
					 * return "{\"msg\":\""+map2.get("fina_type_name").get(0)+
					 * "，财务类别名称已存在！\",\"state\":\"false\",\"row_cell\":\""+map2.
					 * get("fina_type_name").get(0)+"\"}";
					 */
				}

				mapVo1.put("spell_code", StringTool.toPinyinShouZiMu(mapVo1.get("fina_type_name").toString()));

				mapVo1.put("wbx_code", StringTool.toWuBi(mapVo1.get("fina_type_name").toString()));

				assFinaDictMapper.addAssFinaDict(mapVo1);

			}

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());

		}
		
	}

	@Override
	public List<AssFinaDict> queryAssFinaDictChild(Map<String, Object> mapVo) throws DataAccessException {
		return assFinaDictMapper.queryAssFinaDictChild(mapVo);
	}
	

}
