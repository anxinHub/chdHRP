/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.sys.serviceImpl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.dao.info.basic.MatSupAttrMapper;
import com.chd.hrp.sys.dao.SupDictMapper;
import com.chd.hrp.sys.dao.SupMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Rules;
import com.chd.hrp.sys.entity.Sup;
import com.chd.hrp.sys.service.RulesService;
import com.chd.hrp.sys.service.SupService;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("supService")
public class SupServiceImpl implements SupService {

	private static Logger logger = Logger.getLogger(SupServiceImpl.class);

	@Resource(name = "supMapper")
	private final SupMapper supMapper = null;

	@Resource(name = "supDictMapper")
	private final SupDictMapper supDictMapper = null;

	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
	
	@Resource(name = "matSupAttrMapper")
	private final MatSupAttrMapper matSupAttrMapper = null;
	
	@Resource(name = "rulesService")
	private final RulesService rulesService = null;

	/**
	 * @Description 添加Sup
	 * @param Sup
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addSupTz(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		
		entityMap.put("hos_id", SessionManager.getHosId());
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("sup_name").toString()));
		
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("sup_name").toString()));
	
		List<Sup> list = supMapper.querySupById(entityMap);

		if (list.size() > 0) {
			for (Sup sup : list) {

				if (sup.getSup_name().equals(entityMap.get("sup_name"))) {
					return "{\"error\":\"编码：" + sup.getSup_name().toString() + "已存在.\"}";
				}
			}
		}
		Sup Maptypepy = supMapper.querySupMaxTypePy(entityMap);
		
		
		String newsup_code;
		
		String typepy="";
		if(Maptypepy!=null){
			typepy=Maptypepy.getSup_code().toUpperCase();
		}
		entityMap.put("typepy", typepy);
		
		
		Sup Map1 = supMapper.querySupMaxTypeCode(entityMap);
		
		
		if(Map1 ==null)
		{
			newsup_code	=typepy+"0001";
		}
		else
		{
			String maxcode =Map1.getSup_code().toString();
			//String maxcode ="0099";
			
			 DecimalFormat df=new DecimalFormat("0000");
			 
			int code1=Integer.parseInt(maxcode.substring(maxcode.length()-4,maxcode.length()))+1;
			
			newsup_code=typepy+df.format(code1);	
			
		}
		entityMap.put("sup_code", newsup_code);
		
		
		
		// 添加编码规则判断
		entityMap.put("proj_code", "HOS_SUP");
		entityMap.put("mod_code", "00");
		Map<String, Object> utilMap = new HashMap<String, Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", "");
		utilMap.put("field_table", "HOS_SUP");
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

			if(entityMap.get("is_delivery") == null || entityMap.get("is_delivery").equals("")){
				entityMap.put("is_delivery","0");
			}
			
			if(entityMap.get("note") == null || entityMap.get("note").equals("")){
				entityMap.put("note","");
			}
			
			
			int result = supMapper.addSup(entityMap);
			if (result > 0) {
				entityMap.put("sup_id", supMapper.querySupCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "添加");
				entityMap.put("is_stop", 0);
				entityMap.put("is_disable", 0);
				supDictMapper.addSupDict(entityMap);
			}
			matSupAttrMapper.addMatSupAttrUser(entityMap);
			matSupAttrMapper.addMatSupAttrRole(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			//logger.error(e.getMessage(), e);

			throw new SysException("添加失败");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addSup\"}";

		}

	}
	
	// 需要供应商类别作为编码 自动生成流水
	@Override
	public String addSupType(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		entityMap.put("group_id", SessionManager.getGroupId());
		
		entityMap.put("hos_id", SessionManager.getHosId());
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("sup_name").toString()));
		
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("sup_name").toString()));
	
		List<Sup> list = supMapper.querySupById(entityMap);

		if (list.size() > 0) {
			for (Sup sup : list) {

				if (sup.getSup_name().equals(entityMap.get("sup_name"))) {
					return "{\"error\":\"名称：" + sup.getSup_name().toString() + "已存在.\"}";
				}
			}
		}

		String newsup_code;
		//获取编码规则
		Map<String, Map<String, Object>> mv = sysBaseService.getHosRulesList(entityMap);
		// 新的流水号
		String newCode = generatorCode(entityMap, mv);
		
		newsup_code = entityMap.get("type_code").toString() + "-" + newCode;
		entityMap.put("sup_code", newsup_code);
		
		// 添加编码规则判断
		entityMap.put("proj_code", "HOS_SUP");
		entityMap.put("mod_code", "00");
		Map<String, Object> utilMap = new HashMap<String, Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", "");
		utilMap.put("field_table", "HOS_SUP");
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

			if(entityMap.get("is_delivery") == null || entityMap.get("is_delivery").equals("")){
				entityMap.put("is_delivery","0");
			}
			
			if(entityMap.get("note") == null || entityMap.get("note").equals("")){
				entityMap.put("note","");
			}
			
			
			int result = supMapper.addSup(entityMap);
			if (result > 0) {
				entityMap.put("sup_id", supMapper.querySupCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "添加");
				entityMap.put("is_stop", 0);
				entityMap.put("is_disable", 0);
				supDictMapper.addSupDict(entityMap);
			}
			matSupAttrMapper.addMatSupAttrUser(entityMap);
			matSupAttrMapper.addMatSupAttrRole(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			//logger.error(e.getMessage(), e);

			throw new SysException("添加失败");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addSup\"}";

		}
	}
	// 不需要加类别  使用流水号
	@Override
	public String addSupNotType(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		entityMap.put("group_id", SessionManager.getGroupId());
		
		entityMap.put("hos_id", SessionManager.getHosId());
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("sup_name").toString()));
		
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("sup_name").toString()));
	
		List<Sup> list = supMapper.querySupById(entityMap);

		if (list.size() > 0) {
			for (Sup sup : list) {

				if (sup.getSup_name().equals(entityMap.get("sup_name"))) {
					return "{\"error\":\"名称：" + sup.getSup_name().toString() + "已存在.\"}";
				}
			}
		}

		String newsup_code;
		//获取编码规则
		Map<String, Map<String, Object>> mv = sysBaseService.getHosRulesList(entityMap);
		// 新的流水号
		newsup_code = generatorCode(entityMap, mv);
		entityMap.put("sup_code", newsup_code);
		
		// 添加编码规则判断
		entityMap.put("proj_code", "HOS_SUP");
		entityMap.put("mod_code", "00");
		Map<String, Object> utilMap = new HashMap<String, Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", "");
		utilMap.put("field_table", "HOS_SUP");
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

			if(entityMap.get("is_delivery") == null || entityMap.get("is_delivery").equals("")){
				entityMap.put("is_delivery","0");
			}
			
			if(entityMap.get("note") == null || entityMap.get("note").equals("")){
				entityMap.put("note","");
			}
			
			
			int result = supMapper.addSup(entityMap);
			if (result > 0) {
				entityMap.put("sup_id", supMapper.querySupCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "添加");
				entityMap.put("is_stop", 0);
				entityMap.put("is_disable", 0);
				supDictMapper.addSupDict(entityMap);
			}
			matSupAttrMapper.addMatSupAttrUser(entityMap);
			matSupAttrMapper.addMatSupAttrRole(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			//logger.error(e.getMessage(), e);

			throw new SysException("添加失败");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addSup\"}";

		}
	}
	// 不使用流水号 添加编码供应商 (区别出 是否需要添加类别前缀)
	@Override
	public String addSup(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("user_id", SessionManager.getUserId());
		List<Sup> list = supMapper.querySupById(entityMap);
		
		if (list.size() > 0) {
			for (Sup sup : list) {
				if (sup.getSup_code().equals(entityMap.get("sup_code"))) {
					return "{\"error\":\"编码：" + sup.getSup_code().toString() + "已存在.\"}";
				}
				if (sup.getSup_name().equals(entityMap.get("sup_name"))) {
					return "{\"error\":\"名称：" + sup.getSup_name().toString() + "已存在.\"}";
				}
			}
		} 
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("sup_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("sup_name").toString()));
		//是否自动配货 
		if(entityMap.get("is_delivery") == null || entityMap.get("is_delivery").equals("")){
			entityMap.put("is_delivery","0");
		}
		// 添加编码规则判断
		entityMap.put("mod_code", "00");

		String proj_code = entityMap.get("sup_code").toString();
		
		Map<String, Object> ruleMap = new HashMap<String, Object>();
		ruleMap.put("group_id", SessionManager.getGroupId());
		ruleMap.put("hos_id", SessionManager.getHosId());
		ruleMap.put("mod_code", entityMap.get("mod_code"));
		ruleMap.put("proj_code", "HOS_SUP");

		Map<Object, Object> rules = sysBaseService.getHosRules(ruleMap);
		if (null != entityMap.get("sup_code")) {
			Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
			Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");

			if(proj_code.equals("自动生成")){
				//获取编码规则
				Map<String, Map<String, Object>> mv = sysBaseService.getHosRulesList(entityMap);
				proj_code = generatorCode(entityMap, mv);
				
			}
			
			int max_level = Integer.parseInt(rules.get("max_level").toString());
			if(max_level>0){
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
		}

		
		Map<String, Object> utilMap = new HashMap<String, Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", "");
		utilMap.put("field_table", "HOS_SUP");
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
			
			// 判断是否需要添加类别前缀
			//供应商编码自动生成是否使用供应商类别做为前缀
			String p98008 = MyConfig.getSysPara(SessionManager.getGroupId(), "0", "0", "98008");
			if ("1".equals(p98008)) {
				proj_code = entityMap.get("type_code") + "-" + proj_code;
			}
			
			entityMap.put("sup_code", proj_code);
			int result = supMapper.addSup(entityMap);
			if (result > 0) {
				 Long sup_id =supMapper.querySupCurrentSequence();
				entityMap.put("sup_id",sup_id.toString());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "添加");
				entityMap.put("is_stop", 0);
				entityMap.put("is_disable", 0);
				supDictMapper.addSupDict(entityMap);
			}
			matSupAttrMapper.addMatSupAttrUser(entityMap);
			matSupAttrMapper.addMatSupAttrRole(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("添加失败");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addSup\"}";

		}

	}
	
	/**
	 *  生成流水号
	 * @param entityMap 实体
	 * @param mv 编码规则
	 * @return
	 */
	private String generatorCode(Map<String, Object> entityMap, Map<String, Map<String, Object>> mv) {
		// 自动生成流水号
		String newsup_code = "";

		int maxlev = Integer.parseInt(mv.get("HOS_SUP").get("max_level").toString());
		// code应该的长度
		int codeLength = 0;
		for (int i = 1; i <= maxlev; i++) {
			codeLength += Integer.parseInt(mv.get("HOS_SUP").get("level" + i).toString());
		}
		entityMap.put("codeLength", codeLength);

		// 查询数据库code的最大值+1
		String next_code = supMapper.querySupMaxCodeNext(entityMap);

		if (StringUtils.isBlank(next_code)) {
			// 类别 + 字符格式化指定长度补0
			newsup_code = String.format("%0" + codeLength + "d", 1);
		} else {
			newsup_code = String.format("%0" + codeLength + "d", Integer.parseInt(next_code));
		}
		
		return newsup_code;
	}

	/**
	 * @Description 批量添加Sup
	 * @param Sup
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchSup(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			supMapper.addBatchSup(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("添加失败");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchSup\"}";

		}
	}

	/**
	 * @Description 查询Sup分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String querySup(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Sup> list = supMapper.querySup(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Sup> list = supMapper.querySup(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}

	}

	/**
	 * @Description 查询SupByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public Sup querySupByCode(Map<String, Object> entityMap) throws DataAccessException {

		return supMapper.querySupByCode(entityMap);

	}

	/**
	 * @Description 批量删除Sup
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchSup(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			//改为controller层判断是否被业务引用 zhaon
			/*String storeIdStr="";//删除科目，判断业务使用
			String reStr="";
			String superCode="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dict_code", "HOS_SUP");
			map.put("group_id", entityList.get(0).get("group_id"));
			map.put("hos_id", entityList.get(0).get("hos_id"));
			map.put("copy_code", "");
			map.put("acc_year", "");
			map.put("p_flag", "");//包括子科目
			if(entityList!=null && entityList.size()>0){
				for(Map<String, Object> mapVo : entityList){
					storeIdStr+=mapVo.get("sup_id")+",";
					
						map.put("dict_id_str", storeIdStr.substring(0, storeIdStr.length()-1));
						//删除供应商时，判断业务表是否使用
						//if(LoadSystemInfo.getHrpMap().get("hrpDatatype").toString().equalsIgnoreCase("oracle")){
						System.out.println("aaa"+map);
						sysFunUtilMapper.querySysDictDelCheck(map);
						storeIdStr="";
						//}
						if(map.get("reNote")!=null)reStr+=map.get("reNote");
					
				}
			}
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的供应商被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}*/
			 supDictMapper.deleteBatchSupDict(entityList);
			 supMapper.deleteBatchSup(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}

	}
	/**
	 * @Description 删除Sup
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteSup(Map<String, Object> entityMap) throws DataAccessException {

		try {
			supMapper.deleteSup(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("添加失败");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteSup\"}";

		}
	}

	/**
	 * @Description 更新Sup
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateSup(Map<String, Object> entityMap) throws DataAccessException {

		// 添加编码规则判断
		entityMap.put("proj_code", "HOS_SUP");
		entityMap.put("mod_code", "00");
/*
		String proj_code = entityMap.get("sup_code").toString();

		Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
		if (null != entityMap.get("sup_code")) {
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
		}*/

		List<Sup> list = supMapper.querySupById(entityMap);

		if (list.size() > 0) {
			for (Sup sup : list) {
				if (sup.getSup_code().equals(entityMap.get("sup_code"))) {
					return "{\"error\":\"编码：" + sup.getSup_code().toString() + "已存在.\"}";
				}

				if (sup.getSup_name().equals(entityMap.get("sup_name"))) {
					return "{\"error\":\"编码：" + sup.getSup_name().toString() + "已存在.\"}";
				}
			}
		}
		if (entityMap.get("is_auto").equals("true")) {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("sup_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("sup_name").toString()));
		}
		
		Map<String, Object> utilMap = new HashMap<String, Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", "");
		utilMap.put("field_table", "HOS_SUP");
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
			supMapper.updateSup(entityMap);
			//修改变更过的供应商管理员表
			//supMapper.updateSysUserSup(entityMap);

			if (entityMap.get("history").equals("true")) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("sup_id", entityMap.get("sup_id"));
				map.put("sup_code", entityMap.get("sup_code"));
				map.put("is_stop", 1);

				supDictMapper.updateSupDictState(map);

				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "变更");
				entityMap.put("is_disable", entityMap.get("is_disable"));
				entityMap.put("is_stop", 0);
				supDictMapper.addSupDict(entityMap);
				
				int sup_no=  supMapper.querySupDict(entityMap);
				entityMap.put("sup_no", sup_no);
				//修改变更过的供应商管理员表
				supMapper.updateSysUserSup(entityMap);
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			} else {
				 
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "变更");

				supDictMapper.updateSupDict_Sup(entityMap);
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("添加失败");
			//return "{\"error\":\"修改失败 请联系管理员! 错误编码  updateSup\"}";

		}
	}
	
	
	
	@Override
	public String updateSupTz(Map<String, Object> entityMap) throws DataAccessException {
		
		// 添加编码规则判断
				entityMap.put("proj_code", "HOS_SUP");
				entityMap.put("mod_code", "00");

				/*String proj_code = entityMap.get("sup_code").toString();

				Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
				if (null != entityMap.get("sup_code")) {
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
				}*/

				List<Sup> list = supMapper.querySupById(entityMap);

				if (list.size() > 0) {
					for (Sup sup : list) {
						if (sup.getSup_code().equals(entityMap.get("sup_code"))) {
							return "{\"error\":\"编码：" + sup.getSup_code().toString() + "已存在.\"}";
						}

						if (sup.getSup_name().equals(entityMap.get("sup_name"))) {
							return "{\"error\":\"编码：" + sup.getSup_name().toString() + "已存在.\"}";
						}
					}
				}
				if (entityMap.get("is_auto").equals("true")) {
					entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("sup_name").toString()));
					entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("sup_name").toString()));
				}

				if(entityMap.get("is_delivery") == null || entityMap.get("is_delivery").equals("")){
					entityMap.put("is_delivery","0");
				}
				
				if(entityMap.get("note") == null || entityMap.get("note").equals("")){
					entityMap.put("note","");
				}
				
				try {
					supMapper.updateSup(entityMap);
					//修改变更过的供应商管理员表
					//supMapper.updateSysUserSup(entityMap);

					if (entityMap.get("history").equals("true")) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("group_id", entityMap.get("group_id"));
						map.put("hos_id", entityMap.get("hos_id"));
						map.put("sup_id", entityMap.get("sup_id"));
						//map.put("sup_code", entityMap.get("sup_code"));
						map.put("is_stop", 1);

						supDictMapper.updateSupDictState(map);

						entityMap.put("user_code", SessionManager.getUserCode());
						entityMap.put("create_date", new Date());
						entityMap.put("dlog", "变更");
						entityMap.put("is_disable", entityMap.get("is_disable"));
						entityMap.put("is_stop", 0);

						supDictMapper.addSupDict(entityMap);
						
						int sup_no=  supMapper.querySupDict(entityMap);
						entityMap.put("sup_no", sup_no);
						//修改变更过的供应商管理员表
						supMapper.updateSysUserSup(entityMap);
						return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
					} else {
						 
						entityMap.put("user_code", SessionManager.getUserCode());
						entityMap.put("create_date", new Date());
						entityMap.put("dlog", "变更");

						supDictMapper.updateSupDict_Sup(entityMap);
						return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
					}

				}
				catch (Exception e) {

					logger.error(e.getMessage(), e);

					throw new SysException("添加失败");
					//return "{\"error\":\"修改失败 请联系管理员! 错误编码  updateSup\"}";

				}
	}

	/**
	 * @Description 批量更新Sup
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchSup(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			supMapper.updateBatchSup(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("添加失败");
			//return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateSup\"}";

		}

	}

	/**
	 * @Description 导入Sup
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importSup(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("添加失败");
			//return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}



	@Override
	public List<Sup> querySupById(Map<String, Object> mapVo) throws DataAccessException{
		return  supMapper.querySupById(mapVo);
	}




	@Override
	public String addGroupSup(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		int hos=supMapper.existsHosIdByCode(entityMap);
		if(hos==0){
			return "{\"error\":\"请先维护供应商类别.\",\"state\":\"false\"}";
		}
		int count = supMapper.existsGroupSupByAdd(entityMap);
		if(count>0){
			return "{\"error\":\"已经有数据了，无法继承.\",\"state\":\"false\"}";
		}
		int state=0;
		state=supMapper.addBatchGroupSupByCode(entityMap);
		if(state>0){
			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
		}else{
			return "{\"error\":\"没有数据，无法继承.\",\"state\":\"false\"}";
		}
	}


	
}
