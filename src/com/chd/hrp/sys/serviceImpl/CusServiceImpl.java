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
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.sys.dao.CusDictMapper;
import com.chd.hrp.sys.dao.CusMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Cus;
import com.chd.hrp.sys.service.CusService;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("cusService")
public class CusServiceImpl implements CusService {

	private static Logger logger = Logger.getLogger(CusServiceImpl.class);

	@Resource(name = "cusMapper")
	private final CusMapper cusMapper = null;

	@Resource(name = "cusDictMapper")
	private final CusDictMapper cusDictMapper = null;

	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;

	/**
	 * @Description 添加Cus
	 * @param Cus
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addCus(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());

		List<Cus> list = cusMapper.queryCusById(entityMap);

		if (list.size() > 0) {
			for (Cus cus : list) {

				if (cus.getCus_code().equals(entityMap.get("cus_code"))) {
					return "{\"error\":\"编码：" + cus.getCus_code().toString() + "已存在.\"}";
				}

				if (cus.getCus_name().equals(entityMap.get("cus_name"))) {
					return "{\"error\":\"编码：" + cus.getCus_name().toString() + "已存在.\"}";
				}
			}
		}
		
		entityMap.put("is_mat", entityMap.get("is_mat"));
		entityMap.put("is_ass", entityMap.get("is_ass"));
		entityMap.put("is_med", entityMap.get("is_med"));
		entityMap.put("is_sup", entityMap.get("is_sup"));

		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("cus_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("cus_name").toString()));

		String proj_code = entityMap.get("cus_code").toString();
		
		// 添加编码规则判断
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("mod_code", "00");
		map.put("proj_code", "HOS_CUS");
		Map<Object, Object> rules = sysBaseService.getHosRules(map);
		
		if (null != entityMap.get("cus_code")) {
			Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
			Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
			int max_level = Integer.parseInt(rules.get("max_level").toString());
			if (max_level > 0) {
				String rules_v = rules.get("rules_view").toString();
				String s_view = Strings.removeFirst(rules_v);
				Object level = proj_code.length();
				if (rules_level_length != null) {
					// 当第一级为0时 不验证规则
					if (!rules_level_length.get(1).toString().equals("0")) {
						if (level != rules_level_length.get(1)) {
							return "{\"error\":\"编码不符合要求,请重新添加.编码规则长度：" + s_view + "\"}";
						}
					}
				}
			}
		}

		Map<String, Object> utilMap = new HashMap<String, Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", "");
		utilMap.put("field_table", "HOS_CUS");
		utilMap.put("field_key1", "");
		utilMap.put("field_value1", "");
		utilMap.put("field_key2", "");
		utilMap.put("field_value2", "");

		if (entityMap.get("sort_code").equals("系统生成")) {
			utilMap.remove("field_key2");
			utilMap.put("field_sort", "sort_code");
			int count = sysFunUtilMapper.querySysMaxSort(utilMap);
			entityMap.put("sort_code", count);
		}
		try {

			int result = cusMapper.addCus(entityMap);
			if (result > 0) {
				if(entityMap.get("is_stop").equals("0")){
					entityMap.put("is_disable",0);
				}else if(entityMap.get("is_stop").equals("1")){
					entityMap.put("is_disable",1);
				} 
				
				entityMap.put("cus_id", cusMapper.queryCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "添加");
				entityMap.put("is_stop", 0);
				entityMap.put("is_disable", entityMap.get("is_stop"));
				cusDictMapper.addCusDict(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCus\"}";

		}

	}

	/**
	 * @Description 批量添加Cus
	 * @param Cus
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchCus(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			cusMapper.addBatchCus(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCus\"}";

		}
	}

	/**
	 * @Description 查询Cus分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryCus(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Cus> list = cusMapper.queryCus(entityMap);
			
			return ChdJson.toJson(list);
			
		
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Cus> list = cusMapper.queryCus(entityMap, rowBounds);
			
	        PageInfo page = new PageInfo(list); 
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	/**
	 * @Description 查询CusByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public Cus queryCusByCode(Map<String, Object> entityMap) throws DataAccessException {

		return cusMapper.queryCusByCode(entityMap);

	}

	/**
	 * @Description 批量删除Cus
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchCus(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			String storeIdStr="";//删除科目，判断业务使用
			String reStr="";
			String superCode="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dict_code", "HOS_CUS");
			map.put("group_id", entityList.get(0).get("group_id"));
			map.put("hos_id", entityList.get(0).get("hos_id"));
			map.put("copy_code", "");
			map.put("acc_year", "");
			map.put("p_flag", "");//包括子科目
			if(entityList!=null && entityList.size()>0){
				for(Map<String, Object> mapVo : entityList){
					storeIdStr += mapVo.get("cus_id");
					
						map.put("dict_id_str", storeIdStr);
						//删除科目时，判断业务表是否使用
						//if(LoadSystemInfo.getHrpMap().get("hrpDatatype").toString().equalsIgnoreCase("oracle")){
						
						sysFunUtilMapper.querySysDictDelCheck(map);
						
						storeIdStr="";
						//}
						if(map.get("reNote")!=null)reStr+=map.get("reNote");
					
				}
			}
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的客户被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			
			cusDictMapper.deleteBatchCusDict(entityList);
			cusMapper.deleteBatchCus(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除Cus
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteCus(Map<String, Object> entityMap) throws DataAccessException {

		try {
			cusDictMapper.deleteCusDict(entityMap);
			cusMapper.deleteCus(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCus\"}";

		}
	}

	/**
	 * @Description 更新Cus
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateCus(Map<String, Object> entityMap) throws DataAccessException {

		if (entityMap.get("is_auto").equals("true")) {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("cus_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("cus_name").toString()));
		}

		// 添加编码规则判断
		entityMap.put("proj_code", "HOS_CUS");
		entityMap.put("mod_code", "00");

		String proj_code = entityMap.get("cus_code").toString();

		Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
		if (null != entityMap.get("cus_code")) {
			Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
			Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");

			String rules_v = rules.get("rules_view").toString();
			String s_view = Strings.removeFirst(rules_v);
			Object level = proj_code.length();
			if(rules_level_length!=null){
				//当第一级为0时 不验证规则
				if(!rules_level_length.get(1).toString().equals("0")){
					
					if (level != rules_level_length.get(1)) {
						return "{\"error\":\"编码不符合要求,请重新添加.编码规则长度：" + s_view + "\"}";
					}
					
				}
			}
		}
		List<Cus> list = cusMapper.queryCusById(entityMap);

		if (list.size() > 0) {
			for (Cus cus : list) {

				if (cus.getCus_code().equals(entityMap.get("cus_code"))) {
					return "{\"error\":\"编码：" + cus.getCus_code().toString() + "已存在.\"}";
				}

				if (cus.getCus_name().equals(entityMap.get("cus_name"))) {
					return "{\"error\":\"编码：" + cus.getCus_name().toString() + "已存在.\"}";
				}
			}
		}
		
		Map<String, Object> utilMap = new HashMap<String, Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", "");
		utilMap.put("field_table", "HOS_CUS");
		utilMap.put("field_key1", "");
		utilMap.put("field_value1", "");
		utilMap.put("field_key2", "");
		utilMap.put("field_value2", "");

		if (entityMap.get("sort_code").equals("系统生成")) {
			utilMap.remove("field_key2");
			utilMap.put("field_sort", "sort_code");
			int count = sysFunUtilMapper.querySysMaxSort(utilMap);
			entityMap.put("sort_code", count);
		}

		try {
			cusMapper.updateCus(entityMap);

			if (entityMap.get("history").equals("true")) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("cus_id", entityMap.get("cus_id"));
				map.put("cus_code", entityMap.get("cus_code"));
				map.put("is_stop", 1);

				cusDictMapper.updateCusDictState(map);

				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("is_disable", entityMap.get("is_disable"));
				entityMap.put("dlog", "变更");

				cusDictMapper.addCusDict(entityMap);
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			} else {
				
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "变更");

				cusDictMapper.updateCusDict_Cus(entityMap);

				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCus\"}";

		}
	}

	/**
	 * @Description 批量更新Cus
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchCus(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			cusMapper.updateBatchCus(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCus\"}";

		}

	}

	/**
	 * @Description 导入Cus
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importCus(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
