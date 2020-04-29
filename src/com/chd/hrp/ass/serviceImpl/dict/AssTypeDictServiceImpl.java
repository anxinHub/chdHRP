/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.ass.serviceImpl.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.dao.dict.AssNatursMapper;
import com.chd.hrp.ass.dao.dict.AssNoDictMapper;
import com.chd.hrp.ass.dao.dict.AssTypeDictMapper;
import com.chd.hrp.ass.entity.dict.AssFinaDict;
import com.chd.hrp.ass.entity.dict.AssNaturs;
import com.chd.hrp.ass.entity.dict.AssNoDict;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssTypeDictService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 050101 资产分类字典
 * @Table: ASS_TYPE_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("assTypeDictService")
public class AssTypeDictServiceImpl implements AssTypeDictService {

	private static Logger logger = Logger.getLogger(AssTypeDictServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "assTypeDictMapper")
	private final AssTypeDictMapper assTypeDictMapper = null;

	// 引入DAO操作
	@Resource(name = "assNoDictMapper")
	private final AssNoDictMapper assNoDictMapper = null;
	
	@Resource(name = "assNatursMapper")
	private final AssNatursMapper assNatursMapper = null;

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
	public String addAssTypeDict(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> map_code = new HashMap<String, Object>();
		map_code.put("group_id", entityMap.get("group_id"));
		map_code.put("hos_id", entityMap.get("hos_id"));
		map_code.put("copy_code", entityMap.get("copy_code"));
		map_code.put("ass_type_code", entityMap.get("ass_type_code"));
		// 判断编码是否重复
		AssTypeDict data_exc_extis_code = assTypeDictMapper.queryAssTypeDictByUniqueness(map_code);

		if (data_exc_extis_code != null) {

			return "{\"error\":\"编码重复,请重新添加.\"}";

		}
		Map<String, Object> map_name = new HashMap<String, Object>();
		map_name.put("group_id", entityMap.get("group_id"));
		map_name.put("hos_id", entityMap.get("hos_id"));
		map_name.put("copy_code", entityMap.get("copy_code"));
		map_name.put("ass_type_name", entityMap.get("ass_type_name"));

		AssTypeDict data_exc_extis_name = assTypeDictMapper.queryAssTypeDictByUniqueness(map_name);

		if (data_exc_extis_name != null) {

			return "{\"error\":\"名称重复,请重新添加.\"}";

		}
		
		// 判断上级编码是否为空 不为空则反查上级编码所属资产性质
		if (StringTool.isNotBlank(entityMap.get("super_code"))) {
			Map<String, Object> map_super = new HashMap<String, Object>();
			map_super.put("group_id", entityMap.get("group_id"));
			map_super.put("hos_id", entityMap.get("hos_id"));
			map_super.put("copy_code", entityMap.get("copy_code"));
			map_super.put("ass_type_code", entityMap.get("super_code"));

			AssTypeDict data_exc_extis_super = assTypeDictMapper.queryAssTypeDictByUniqueness(map_super);

			if (null != data_exc_extis_super) {
				entityMap.put("ass_naturs", data_exc_extis_super.getAss_naturs());
			} else {
				return "{\"error\":\"上级编码不存在,请重新添加.\"}";
			}
			// 判断上级编码是否为末级
			if (data_exc_extis_super.getIs_last() == 1) {
				Map<String, Object> update_is_last = new HashMap<String, Object>();
				update_is_last.put("group_id", entityMap.get("group_id"));
				update_is_last.put("hos_id", entityMap.get("hos_id"));
				update_is_last.put("copy_code", entityMap.get("copy_code"));
				update_is_last.put("manage_depre_amount", entityMap.get("manage_depre_amount"));
				update_is_last.put("ass_type_id", data_exc_extis_super.getAss_type_id());
				update_is_last.put("is_last", "0");
				assTypeDictMapper.updateAssTypeDict(update_is_last);
			}

		}
		try {

			int state = assTypeDictMapper.addAssTypeDict(entityMap);

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
	public String addBatchAssTypeDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assTypeDictMapper.addBatchAssTypeDict(entityList);

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
	public String updateAssTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		// 判断上级编码是否为空 不为空则反查上级编码所属资产性质
		if (Strings.isNotBlank((CharSequence) entityMap.get("super_code"))) {
			Map<String, Object> map_super = new HashMap<String, Object>();
			map_super.put("group_id", entityMap.get("group_id"));
			map_super.put("hos_id", entityMap.get("hos_id"));
			map_super.put("copy_code", entityMap.get("copy_code"));
			map_super.put("ass_type_code", entityMap.get("super_code"));

			AssTypeDict data_exc_extis_super = assTypeDictMapper.queryAssTypeDictByUniqueness(map_super);

			if (null != data_exc_extis_super) {
				entityMap.put("ass_naturs", data_exc_extis_super.getAss_naturs());
			} else {
				return "{\"error\":\"上级编码不存在,请重新添加.\"}";
			}

		}
		try {

			int state = assTypeDictMapper.updateAssTypeDict(entityMap);

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
	public String updateBatchAssTypeDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assTypeDictMapper.updateBatchAssTypeDict(entityList);

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
	public String deleteAssTypeDict(Map<String, Object> entityMap) throws DataAccessException {


		try {
		
			// 如果是非末级则先删除下级在删除自己
			AssTypeDict at= assTypeDictMapper.queryAssTypeDictByCode(entityMap);
			
			Map<String, Object> supper_code_map =new HashMap<String, Object>();
			supper_code_map.put("group_id", at.getGroup_id());
			supper_code_map.put("hos_id", at.getHos_id());
			supper_code_map.put("copy_code", at.getCopy_code());
			supper_code_map.put("super_code", at.getAss_type_code());
			assTypeDictMapper.deleteAssTypeDictBySuperCode(supper_code_map);
			//删除自身
			int state = assTypeDictMapper.deleteAssTypeDict(entityMap);
			
			//如果上级无其它下级 则更改上级为末级  zhaon
			if(at.getSuper_code() != null){
				Map<String, Object> supper_map =new HashMap<String, Object>(entityMap);
				supper_map.put("ass_type_code", at.getSuper_code());
				List<AssTypeDict> list = assTypeDictMapper.queryAssTypeDiceChild(supper_map);
				if(list == null || list.size() == 0){
					supper_map.put("is_last", "1");
					assTypeDictMapper.updateAssTypeDictByCode(supper_map);
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
	public String deleteBatchAssTypeDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assTypeDictMapper.deleteBatchAssTypeDict(entityList);

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
	public String queryAssTypeDict(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssTypeDict> list = assTypeDictMapper.queryAssTypeDict(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssTypeDict> list = assTypeDictMapper.queryAssTypeDict(entityMap, rowBounds);

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
	public synchronized AssTypeDict queryAssTypeDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assTypeDictMapper.queryAssTypeDictByCode(entityMap);
	}

	/**
	 * @Description 获取050101 资产分类字典<BR>
	 * @param entityMap
	 *            需要检索的唯一性字段
	 * @return AssTypeDict
	 * @throws DataAccessException
	 */
	@Override
	public synchronized AssTypeDict queryAssTypeDictByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assTypeDictMapper.queryAssTypeDictByUniqueness(entityMap);
	}

	@Override
	public List<?> queryAssTypeDictByTree(Map<String, Object> entityMap) throws DataAccessException {
		List<?> l_map = assTypeDictMapper.queryAssTypeDictByTree(entityMap);
		return l_map;
	}

	@Override
    public String deleteAssTypeDictBySuperCode(Map<String, Object> entityMap) throws DataAccessException {
		try {

			assTypeDictMapper.deleteAssTypeDictBySuperCode(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
    }

	@Override
	public List<AssTypeDict> queryAssTypeDictList(Map<String, Object> entityMap)
			throws DataAccessException {
		return assTypeDictMapper.queryAssTypeDict(entityMap);
	}
//导入
	@Override
	public String readAssTypeDictFiles(Map<String, Object> entityMap) throws DataAccessException {
		
		StringBuffer errorMsg = new StringBuffer();
		
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");
		//资产性质 可通过编码或名称获取编码
		List<AssNaturs> natursList = assNatursMapper.queryAssNaturs(entityMap);
		Map<String, String> natursMap = new HashMap<String, String>();
		for (AssNaturs assNaturs : natursList) {
			natursMap.put(assNaturs.getNaturs_code(), assNaturs.getNaturs_code());
			natursMap.put(assNaturs.getNaturs_name(), assNaturs.getNaturs_code());
		}

		try {

			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}
			for (Map<String, List<String>> map : list) {
				
				if (map.get("ass_type_code") == null || map.get("ass_type_code").get(1) == null) {
					errorMsg.append(map.get("ass_type_code").get(0)+":分类编码不能为空！<br/>");
				} else if (map.get("ass_type_name") == null || map.get("ass_type_name").get(1) == null) {
					errorMsg.append(map.get("ass_type_name").get(0)+":类别名称不能为空！<br/>");
				} else if (map.get("is_last") == null || map.get("is_last").get(1) == null) {
					errorMsg.append(map.get("is_last").get(0)+":是否末级不能为空！<br/>");
				} else if (map.get("is_stop") == null || map.get("is_stop").get(1) == null) {
					errorMsg.append(map.get("is_last").get(0)+":是否停用不能为空！<br/>");
				} else if (map.get("ass_naturs") == null || map.get("ass_naturs").get(1) == null) {
					errorMsg.append(map.get("ass_naturs").get(0)+":性质编码不能为空！<br/>");
				}
				
				String ass_naturs = null;
				if(map.get("ass_naturs") != null && map.get("ass_naturs").get(1) != null){
					ass_naturs = natursMap.get(map.get("ass_naturs").get(1));
				}
				
				if(ass_naturs == null){
					errorMsg.append(map.get("ass_naturs").get(0)+":性质编码不存在！<br/>");
				}
				
				if(!"".equals(errorMsg.toString())){
					throw new SysException(errorMsg.toString());
				}

				Map<String, Object> dataMap = new HashMap<String, Object>();

				dataMap.put("group_id", SessionManager.getGroupId());
				dataMap.put("hos_id", SessionManager.getHosId());
				dataMap.put("copy_code", SessionManager.getCopyCode());
				dataMap.put("ass_type_code", map.get("ass_type_code").get(1));
				dataMap.put("ass_type_name", map.get("ass_type_name").get(1));
				dataMap.put("ass_naturs", ass_naturs);
				dataMap.put("is_last", whetherMap.get(map.get("is_last").get(1)));
				dataMap.put("is_stop", whetherMap.get(map.get("is_stop").get(1)));
				dataMap.put("manage_depre_amount", map.get("manage_depre_amount") == null ? null :map.get("manage_depre_amount").get(1));

				// 添加编码规则判断
				dataMap.put("proj_code", "ASS_TYPE_DICT");
				dataMap.put("mod_code", "05");

				String ass_type_code1 = dataMap.get("ass_type_code").toString();

				Map<Object, Object> rules = assBaseService.getHosRules(dataMap);
				if (null != dataMap.get("ass_type_code")) {
					Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
					Map<Object, Object> level_length = (Map<Object, Object>) rules.get("rules_level_length");
					Object level = level_map.get(ass_type_code1.length());

					if (null != level) {
						int int_level = (Integer) level;
						dataMap.put("type_level", level);
						dataMap.put("super_code", "");
						if (int_level == 1) {
							dataMap.put("type_level", level);
							dataMap.put("super_code", "");
						} else {
							dataMap.put("type_level", level);
							int v_level = int_level - 1;
							int end = (Integer) level_length.get(v_level);
							dataMap.put("super_code", ass_type_code1.substring(0, end));
						}
					}else{
						errorMsg.append(map.get("ass_type_code").get(0)+":分类编码规则错误！<br/>");
						throw new SysException(errorMsg.toString());
					}

				}

				// 判断唯一性 编码

				Map<String, Object> map_code = new HashMap<String, Object>();
				map_code.put("group_id", dataMap.get("group_id"));
				map_code.put("hos_id", dataMap.get("hos_id"));
				map_code.put("copy_code", dataMap.get("copy_code"));
				map_code.put("ass_type_code", dataMap.get("ass_type_code"));

				AssTypeDict data_exc_extis_code = assTypeDictMapper.queryAssTypeDictByUniqueness(map_code);

				if (data_exc_extis_code != null) {
					continue;
				}
				// 判断唯一性 名字

				Map<String, Object> map_name = new HashMap<String, Object>();
				map_name.put("group_id", dataMap.get("group_id"));
				map_name.put("hos_id", dataMap.get("hos_id"));
				map_name.put("copy_code", dataMap.get("copy_code"));
				map_name.put("ass_type_name", dataMap.get("ass_type_name"));

				AssTypeDict data_exc_extis_name = assTypeDictMapper.queryAssTypeDictByUniqueness(map_name);

				if (data_exc_extis_name != null) {
					continue;
				}

				// 判断上级编码是否为空 不为空则反查上级编码所属资产性质
				if (StringTool.isNotBlank(dataMap.get("super_code"))) {
					Map<String, Object> map_super = new HashMap<String, Object>();
					map_super.put("group_id", dataMap.get("group_id"));
					map_super.put("hos_id", dataMap.get("hos_id"));
					map_super.put("copy_code", dataMap.get("copy_code"));
					map_super.put("ass_type_code", dataMap.get("super_code"));

					AssTypeDict data_exc_extis_super = assTypeDictMapper.queryAssTypeDictByCode(map_super);

					if (null != data_exc_extis_super) {
						dataMap.put("ass_naturs", data_exc_extis_super.getAss_naturs());

						// 判断上级编码是否为末级
						if (data_exc_extis_super.getIs_last() == 1) {
							Map<String, Object> update_is_last = new HashMap<String, Object>();
							update_is_last.put("group_id", dataMap.get("group_id"));
							update_is_last.put("hos_id", dataMap.get("hos_id"));
							update_is_last.put("copy_code", dataMap.get("copy_code"));
							update_is_last.put("ass_type_id", data_exc_extis_super.getAss_type_id());
							update_is_last.put("is_last", "0");
							update_is_last.put("super_code", data_exc_extis_super.getSuper_code());

							assTypeDictMapper.updateAssTypeDict(update_is_last);
						}
					} else {
						throw new SysException(map.get("ass_type_code").get(0)
								+ ",编码错误或上级编码不存在！");
					}
				}

				dataMap.put("spell_code", StringTool.toPinyinShouZiMu(dataMap.get("ass_type_name").toString()));

				dataMap.put("wbx_code", StringTool.toWuBi(dataMap.get("ass_type_name").toString()));

				assTypeDictMapper.addAssTypeDict(dataMap);
			}

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());

		}

	}

	@Override
	public List<AssTypeDict> querycount(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return assTypeDictMapper.querycount(mapVo);
	}

	/**
	 * zhaon
	 */
	@Override
	public List<AssTypeDict> queryAssTypeDiceChild(Map<String, Object> mapVo) throws DataAccessException {
		return assTypeDictMapper.queryAssTypeDiceChild(mapVo);
	}}


