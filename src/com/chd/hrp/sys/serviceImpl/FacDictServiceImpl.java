/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.sys.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.sys.dao.FacDictMapper;
import com.chd.hrp.sys.dao.FacMapper;
import com.chd.hrp.sys.entity.FacDict;
import com.chd.hrp.sys.service.FacDictService;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("facDictService")
public class FacDictServiceImpl implements FacDictService {

	private static Logger logger = Logger.getLogger(FacDictServiceImpl.class);

	@Resource(name = "facDictMapper")
	private final FacDictMapper facDictMapper = null;

	@Resource(name = "facMapper")
	private final FacMapper facMapper = null;

	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;

	/**
	 * @Description 添加FacDict
	 * @param FacDict
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addFacDict(Map<String, Object> entityMap) throws DataAccessException {

		FacDict facDict = queryFacDictByCode(entityMap);

		if (facDict != null) {

			return "{\"error\":\"编码：" + facDict.getFac_code().toString() + "已存在.\"}";

		}

		// 添加编码规则判断
		entityMap.put("proj_code", "HOS_FAC");
		entityMap.put("mod_code", "00");

		String proj_code = entityMap.get("fac_code").toString();

		Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
		if (null != entityMap.get("fac_code")) {
			Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
			Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");

			String rules_v = rules.get("rules_view").toString();
			String s_view = Strings.removeFirst(rules_v);
			Object level = level_map.get(proj_code.length());
			if (level != rules_level_length.get(1)) {
				return "{\"error\":\"编码不符合要求,请重新添加.编码规则长度：" + s_view + "\"}";
			}
		}

		try {

			if ("0".endsWith(entityMap.get("dict_type").toString())) {

				facMapper.updateFacByCode(entityMap);

			} else if ("1".endsWith(entityMap.get("dict_type").toString())) {

				facMapper.updateFacByName(entityMap);

			}

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("is_stop", 1);

			map.put("group_id", entityMap.get("group_id"));

			map.put("hos_id", entityMap.get("hos_id"));

			map.put("fac_code", entityMap.get("fac_code"));

			map.put("fac_id", entityMap.get("fac_id"));

			facDictMapper.updateFacDictState(map);

			entityMap.put("user_code", SessionManager.getUserCode());

			entityMap.put("create_date", new Date());

			facDictMapper.addFacDict(entityMap);

			return "{\"msg\":\"变更成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addFacDict\"}";

		}

	}

	/**
	 * @Description 批量添加FacDict
	 * @param FacDict
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchFacDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			facDictMapper.addBatchFacDict(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchFacDict\"}";

		}
	}

	/**
	 * @Description 查询FacDict分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryFacDict(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<FacDict> list = facDictMapper.queryFacDict(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());

	}
	
	@Override
	public String queryFacDictList(Map<String, Object> paramMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<FacDict> list = facDictMapper.queryFacDictList(paramMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<FacDict> list = facDictMapper.queryFacDictList(paramMap, rowBounds);
	        PageInfo page = new PageInfo(list); 
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * @Description 查询FacDictByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public FacDict queryFacDictByCode(Map<String, Object> entityMap) throws DataAccessException {

		return facDictMapper.queryFacDictByCode(entityMap);

	}

	/**
	 * @Description 批量删除FacDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchFacDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			int state = facDictMapper.deleteBatchFacDict(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchFacDict\"}";

		}

	}

	/**
	 * @Description 删除FacDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteFacDict(Map<String, Object> entityMap) throws DataAccessException {

		try {
			facDictMapper.deleteFacDict(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteFacDict\"}";

		}
	}

	/**
	 * @Description 更新FacDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateFacDict(Map<String, Object> entityMap) throws DataAccessException {

		try {

			facDictMapper.updateFacDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateFacDict\"}";

		}
	}

	/**
	 * @Description 批量更新FacDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchFacDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			facDictMapper.updateBatchFacDict(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateFacDict\"}";

		}

	}

	/**
	 * @Description 导入FacDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importFacDict(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

}
