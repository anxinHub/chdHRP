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
import com.chd.hrp.ass.dao.dict.AssGBcodeMapper;
import com.chd.hrp.ass.entity.dict.AssNoDict;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
import com.chd.hrp.ass.entity.dict.AssGBcode;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssGBcodeService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 050101 国标码字典
 * @Table: MAT_FINA_TYPE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("assGBcodeService")
public class AssGBcodeServiceImpl implements AssGBcodeService {

	private static final Reader reader = null;

	private static final JSONArray gb_name = null;

	private static final JSONArray gb_code = null;

	private static Logger logger = Logger.getLogger(AssGBcodeServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "assGBcodeMapper")
	private final AssGBcodeMapper assGBcodeMapper = null;

	// 引入DAO操作
	@Resource(name = "assNoDictMapper")
	private final AssNoDictMapper assNoDictMapper = null;

	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;


	/**
	 * @Description 添加050101 国标码字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAssGBcode(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> map_code = new HashMap<String, Object>();
		map_code.put("group_id", entityMap.get("group_id"));
		map_code.put("hos_id", entityMap.get("hos_id"));
		map_code.put("copy_code", entityMap.get("copy_code"));
		map_code.put("gb_code", entityMap.get("gb_code"));
		// 判断编码是否重复
		AssGBcode data_exc_extis_code = assGBcodeMapper.queryAssGBcodeByUniqueness(map_code);

		if (data_exc_extis_code != null) {

			return "{\"error\":\"编码重复,请重新添加.\"}";

		}
		Map<String, Object> map_name = new HashMap<String, Object>();
		map_name.put("group_id", entityMap.get("group_id"));
		map_name.put("hos_id", entityMap.get("hos_id"));
		map_name.put("copy_code", entityMap.get("copy_code"));
		map_name.put("gb_name", entityMap.get("gb_name"));

		AssGBcode data_exc_extis_name = assGBcodeMapper.queryAssGBcodeByUniquenessname(map_name);

		if (data_exc_extis_name != null) {

			return "{\"error\":\"名称重复,请重新添加.\"}";

		}

		try {

			int state = assGBcodeMapper.addAssGBcode(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量添加050101 国标码字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAssGBcode(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assGBcodeMapper.addBatchAssGBcode(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新050101 国标码字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAssGBcode(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int state = assGBcodeMapper.updateAssGBcode(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量更新050101 国标码字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAssGBcode(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assGBcodeMapper.updateBatchAssGBcode(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除050101 国标码字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAssGBcode(Map<String, Object> entityMap) throws DataAccessException {

		try {
			// 如果是非末级则先删除下级在删除自己
			AssGBcode at= assGBcodeMapper.queryAssGBcodeByCode(entityMap);
			Map<String, Object> supper_code_map =new HashMap<String, Object>();
			
			supper_code_map.put("supper_code", at.getGb_code());
			assGBcodeMapper.deleteAssGBcodeBySuperCode(supper_code_map);
			//删除自身
			int state = assGBcodeMapper.deleteAssGBcode(entityMap);
			
			//如果有上级
			if(Strings.isNotBlank(at.getSupper_code())) {
				//如果上级无其它下级 则更改上级为末级  
				Map<String, Object> supper_map =new HashMap<String, Object>(entityMap);
				supper_map.put("supper_code", at.getSupper_code());
				List<AssGBcode> list = assGBcodeMapper.queryAssGBcodeChild(supper_map);
				if(list == null || list.size() == 0){
					supper_map.put("is_last", 1);
					assGBcodeMapper.updateAssGBcode(supper_map);
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
	 * @Description 批量删除050101 国标码字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAssGBcode(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assGBcodeMapper.deleteBatchAssGBcode(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 查询结果集050101 国标码字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssGBcode(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssGBcode> list = assGBcodeMapper.queryAssGBcode(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssGBcode> list = assGBcodeMapper.queryAssGBcode(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050101 国标码字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public synchronized AssGBcode queryAssGBcodeByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assGBcodeMapper.queryAssGBcodeByCode(entityMap);
	}

	/**
	 * @Description 获取050101 国标码字典<BR>
	 * @param entityMap
	 *            需要检索的唯一性字段
	 * @return AssGBcode
	 * @throws DataAccessException
	 */
	@Override
	public  synchronized  AssGBcode   queryAssGBcodeByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assGBcodeMapper.queryAssGBcodeByUniqueness(entityMap);
	}

	@Override
	public List<?> queryAssGBcodeByTree(Map<String, Object> entityMap) throws DataAccessException {
		List<?> l_map = assGBcodeMapper.queryAssGBcodeByTree(entityMap);
		return l_map;
	}

	@Override
    public String deleteAssGBcodeBySuperCode(Map<String, Object> entityMap) throws DataAccessException {
		try {

			assGBcodeMapper.deleteAssGBcodeBySuperCode(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
    }

	@Override
	public List<AssGBcode> queryAssGBcodeList(Map<String, Object> entityMap)
			throws DataAccessException {
		return assGBcodeMapper.queryAssGBcode(entityMap);
	}
//导入
	@Override
	public String readAssGBcodeFiles(Map<String, Object> map) throws DataAccessException {

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

				if (map2.get("gb_code").get(1) == null || map2.get("gb_code").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("gb_code").get(0)
							+ "，国标编码为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("gb_code").get(0)
							+ "\"}";
				} else if (map2.get("gb_name").get(1) == null || map2.get("gb_name").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("gb_name").get(0)
							+ "，国标名称名称为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("gb_name").get(0)
							+ "\"}";
				}  else if (map2.get("is_last").get(1) == null || map2.get("is_last").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("is_last").get(0)
							+ "，是否末级分类为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("is_last").get(0) + "\"}";
				}else if (map2.get("supper_code").get(1) == null || map2.get("supper_code").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("supper_code").get(0)
							+ "，上级编码为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("supper_code").get(0) + "\"}";
				} else if (map2.get("is_stop").get(1) == null || map2.get("is_stop").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("is_stop").get(0) + "，是否停用为空！\",\"state\":\"false\",\"row_cell\":\""
							+ map2.get("is_stop").get(0) + "\"}";
				} 

				Map<String, Object> mapVo1 = new HashMap<String, Object>();

				
				mapVo1.put("gb_code", map2.get("gb_code").get(1));
				mapVo1.put("gb_name", map2.get("gb_name").get(1));
				mapVo1.put("supper_code", map2.get("supper_code").get(1));
				mapVo1.put("is_last", is_last_map.get(map2.get("is_last").get(1)));
				mapVo1.put("is_stop", is_last_map.get(map2.get("is_stop").get(1)));

				// 判断唯一性 编码

				Map<String, Object> map_code = new HashMap<String, Object>();
				map_code.put("group_id", mapVo1.get("group_id"));
				map_code.put("hos_id", mapVo1.get("hos_id"));
				map_code.put("copy_code", mapVo1.get("copy_code"));
				map_code.put("gb_code", mapVo1.get("gb_code"));

				AssGBcode data_exc_extis_code = assGBcodeMapper.queryAssGBcodeByUniqueness(map_code);

				if (data_exc_extis_code != null) {
					continue;
				}
			
				Map<String, Object> map_name = new HashMap<String, Object>();
				map_name.put("group_id", mapVo1.get("group_id"));
				map_name.put("hos_id", mapVo1.get("hos_id"));
				map_name.put("copy_code", mapVo1.get("copy_code"));
				map_name.put("gb_name", mapVo1.get("gb_name"));

				AssGBcode data_exc_extis_name = assGBcodeMapper.queryAssGBcodeByUniquenessname(map_name);

				if (data_exc_extis_name != null) {
					continue;
					/*
					 * return "{\"msg\":\""+map2.get("gb_name").get(0)+
					 * "，财务类别名称已存在！\",\"state\":\"false\",\"row_cell\":\""+map2.
					 * get("gb_name").get(0)+"\"}";
					 */
				}

				mapVo1.put("spell_code", StringTool.toPinyinShouZiMu(mapVo1.get("gb_name").toString()));

				mapVo1.put("wbx_code", StringTool.toWuBi(mapVo1.get("gb_name").toString()));

				assGBcodeMapper.addAssGBcode(mapVo1);

			}

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());

		}
		
	}

	@Override
	public List<AssGBcode> queryAssGBcodeChild(Map<String, Object> mapVo) throws DataAccessException {
		return assGBcodeMapper.queryAssGBcodeChild(mapVo);
	}
	

}
