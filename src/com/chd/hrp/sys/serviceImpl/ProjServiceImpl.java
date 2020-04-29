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
import com.chd.hrp.acc.service.AccProjAttrService;
import com.chd.hrp.sys.dao.ProjDictMapper;
import com.chd.hrp.sys.dao.ProjMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Proj;
import com.chd.hrp.sys.service.ProjService;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Service("projService")
public class ProjServiceImpl implements ProjService {

	private static Logger logger = Logger.getLogger(ProjServiceImpl.class);

	@Resource(name = "projMapper")
	private final ProjMapper projMapper = null;

	@Resource(name = "projDictMapper")
	private final ProjDictMapper projDictMapper = null;

	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;

	@Resource(name = "accProjAttrService") 
	private final AccProjAttrService accProjAttrService = null;
	

	/**
	 * @Description 添加Proj
	 * @param Proj
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addProj(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		List<Proj> list = projMapper.queryProjById(entityMap);

		if (list.size() > 0) {
			for (Proj proj : list) {
				if (proj.getProj_code().equals(entityMap.get("proj_code"))) {
					return "{\"error\":\"编码：" + proj.getProj_code().toString() + "已存在.\"}";
				}
				if (proj.getProj_name().equals(entityMap.get("proj_name"))) {
					return "{\"error\":\"编码：" + proj.getProj_name().toString() + "已存在.\"}";
				}
			}
		}

		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("proj_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("proj_name").toString()));
		String p_code = entityMap.get("proj_code").toString();
		
		// 添加编码规则判断
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("mod_code", "00");
		map.put("proj_code", "HOS_PROJ");
		Map<Object, Object> rules = sysBaseService.getHosRules(map);
		
		if (null != entityMap.get("proj_code")) {
			Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
			Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
			
			int max_level = Integer.parseInt(rules.get("max_level").toString());
			if (max_level > 0) {
				String rules_v = rules.get("rules_view").toString();
				String s_view = Strings.removeFirst(rules_v);
				Object level = p_code.length();
				if(rules_level_length!=null){
					//当第一级为0时 不验证规则
					if(!rules_level_length.get(1).toString().equals("0")){
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
		utilMap.put("field_table", "HOS_PROJ");
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

			int result = projMapper.addProj(entityMap);
			if (result > 0) {
				
				entityMap.put("proj_id", projMapper.queryProjCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "添加");
				entityMap.put("is_stop", 0);
				entityMap.put("is_disable", entityMap.get("is_stop"));
				projDictMapper.addProjDict(entityMap);
				
				accProjAttrService.updateAccProjAttr(entityMap);
				
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"proj_id\":\""+entityMap.get("proj_id").toString()+"\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addEmp\"}";
		}
	}

	/**
	 * @Description 批量添加Proj
	 * @param Proj
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchProj(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			projMapper.addBatchProj(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchProj\"}";

		}
	}

	/**
	 * @Description 查询Proj分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryProj(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());

		if(entityMap.get("proj_name") !=null){
			
			entityMap.put("proj_name",entityMap.get("proj_name").toString().toUpperCase());
			
		}
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = projMapper.queryAccProj(entityMap);
			
			return ChdJson.toJson(list);
			
		
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = projMapper.queryAccProj(entityMap, rowBounds);
			
	        PageInfo page = new PageInfo(list); 
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	/**
	 * @Description 查询ProjByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public Proj queryProjByCode(Map<String, Object> entityMap) throws DataAccessException {

		return projMapper.queryProjByCode(entityMap);

	}

	/**
	 * @Description 批量删除Proj
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchProj(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			StringBuffer dict_id_str = new StringBuffer();//删除科目，判断业务使用
			//判断是否被使用
			String reStr="";
			
			if(entityList!=null && entityList.size()>0){
				for(Map<String, Object> mapVo : entityList){
					dict_id_str.append(",").append(mapVo.get("proj_id").toString());
				}
				//删除科目时，判断业务表是否使用
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("dict_code", "HOS_PROJ");
				map.put("group_id", entityList.get(0).get("group_id"));
				map.put("hos_id", entityList.get(0).get("hos_id"));
				map.put("copy_code", "");
				map.put("dict_id_str", dict_id_str.substring(1));
				map.put("acc_year", "");
				map.put("p_flag", "1");
				sysFunUtilMapper.querySysDictDelCheck(map);
				
				if(map.get("reNote")!=null) {
					reStr+=map.get("reNote");
					
				}
			}
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的项目被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			projDictMapper.deleteBatchProjDict(entityList);

			projMapper.deleteBatchProj(entityList);
			
			accProjAttrService.deleteBatchAccProjAttr(entityList);
			 
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除Proj
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteProj(Map<String, Object> entityMap) throws DataAccessException {

		try {
			projMapper.deleteProj(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteProj\"}";
		}
	}

	/**
	 * @Description 更新Proj
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateProj(Map<String, Object> entityMap) throws DataAccessException {
		
		if("1".equals(entityMap.get("is_disable"))){
			entityMap.put("acc_year", SessionManager.getAcctYear());
			 List<Map<String,Object>> count = projMapper.queryProjByStop(entityMap);
			if(count.size() > 0){
				return "{\"error\":\"该项目有余额不能进行停用！\"}";
			}
		}
		

		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("proj_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("proj_name").toString()));
		

		String p_code = entityMap.get("proj_code").toString();
		// 添加编码规则判断
		entityMap.put("proj_code", "HOS_PROJ");
		entityMap.put("mod_code", "00");

		Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
		entityMap.put("proj_code", p_code);
		if (null != entityMap.get("proj_code")) {
			Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
			Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");

			String rules_v = rules.get("rules_view").toString();
			String s_view = Strings.removeFirst(rules_v);
			Object level = p_code.length();
			if(rules_level_length!=null){
				//当第一级为0时 不验证规则
				if(!rules_level_length.get(1).toString().equals("0")){
					
					if (level != rules_level_length.get(1)) {
						return "{\"error\":\"编码不符合要求,请重新添加.编码规则长度：" + s_view + "\"}";
					}
					
				}
			}
		}

		List<Proj> list = projMapper.queryProjById(entityMap);

		if (list.size() > 0) {
			for (Proj proj : list) {
				if (proj.getProj_code().equals(entityMap.get("proj_code"))) {
					return "{\"error\":\"编码：" + proj.getProj_code().toString() + "已存在.\"}";
				}
				if (proj.getProj_name().equals(entityMap.get("proj_name"))) {
					return "{\"error\":\"编码：" + proj.getProj_name().toString() + "已存在.\"}";
				}
			}
		}

		try {
			System.out.println("+++++++++++++++++"+entityMap);
			projMapper.updateProj(entityMap);

			if (entityMap.get("history").equals("true")) {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("proj_id", entityMap.get("proj_id"));
				map.put("proj_code", entityMap.get("proj_code"));
				map.put("is_stop", entityMap.get("is_stop"));
				projDictMapper.updateProjDictState(map);

				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "变更");
				entityMap.put("is_disable", entityMap.get("is_disable"));
		     	entityMap.put("is_stop", entityMap.get("is_stop"));

				projDictMapper.addProjDict(entityMap);
				
				accProjAttrService.updateAccProjAttr(entityMap);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

			} else {
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "变更");

				projDictMapper.updateProjDict_Proj(entityMap);
				
				accProjAttrService.updateAccProjAttr(entityMap);

				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateProj\"}";

		}
	}

	/**
	 * @Description 批量更新Proj
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchProj(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			projMapper.updateBatchProj(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateProj\"}";

		}

	}

	/**
	 * @Description 导入Proj
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importProj(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String updateProjName(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("spel_code", StringTool.toPinyinShouZiMu(entityMap.get("proj_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("proj_name").toString()));
		try {

			projMapper.updateProjName(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateProj\"}";

		}
	}

	@Override
	public String updateProjCode(Map<String, Object> entityMap) throws DataAccessException {
		try {

			projMapper.updateProjCode(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateProj\"}";

		}
	}
}
