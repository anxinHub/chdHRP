package com.chd.hrp.hr.serviceImpl.organize;

import java.util.ArrayList;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.AccDeptAttrMapper;
import com.chd.hrp.acc.entity.AccDeptAttr;
import com.chd.hrp.acc.serviceImpl.AccDeptAttrServiceImpl;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.dao.base.HrSysFunUtilMapper;
import com.chd.hrp.hr.dao.organize.HrDeptDictMapper;
import com.chd.hrp.hr.dao.organize.HrDeptMapper;
import com.chd.hrp.hr.entity.base.HrAccDeptAttr;
import com.chd.hrp.hr.entity.orangnize.HrDept;
import com.chd.hrp.hr.entity.orangnize.HrDeptDict;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.organize.HrDeptService;
import com.chd.hrp.hr.serviceImpl.base.HrAccDeptAttrServiceImpl;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.github.pagehelper.PageInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 部门架构
 * 
 * @author Administrator
 *
 */
@Service("hrDeptService")
public class HrDeptServiceImpl implements HrDeptService {

	private static Logger logger = Logger.getLogger(HrDeptServiceImpl.class);

	@Resource(name = "hrDeptMapper")
	private final HrDeptMapper hrDeptMapper = null;

	@Resource(name = "hrDeptDictMapper")
	private final HrDeptDictMapper hrDeptDictMapper = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	@Resource(name = "hrAccDeptAttrService")
	private final HrAccDeptAttrServiceImpl hrAccDeptAttrService = null;

	@Resource(name = "hrSysFunUtilMapper")
	private final HrSysFunUtilMapper hrSysFunUtilMapper = null;
	
	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	
	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	@Resource(name = "accDeptAttrService")
	private final AccDeptAttrServiceImpl accDeptAttrService = null;
	
	@Resource(name = "accDeptAttrMapper")
	private final AccDeptAttrMapper accDeptAttrMapper = null;

	
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * @Description 添加Dept
	 * @param Dept
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String addDept(Map<String, Object> entityMap) throws DataAccessException {
		try {
		String storeIdStr="";//删除科目，判断业务使用
		String reStr="";
		Map<String, Object> mapCheck = new HashMap<String, Object>();
		mapCheck.put("dict_code", "HR_DEPT");
		mapCheck.put("group_id",  SessionManager.getGroupId());
		mapCheck.put("hos_id",  SessionManager.getHosId());
		mapCheck.put("copy_code", "");
		mapCheck.put("acc_year", "");
		mapCheck.put("p_flag", "");//包括子科目
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());

		List<HrDept> list = hrDeptMapper.queryDeptById(entityMap);

		if (list.size() > 0) {

			for (HrDept dept : list) {
				if (dept.getDept_code().equals(entityMap.get("dept_code"))) {
					return "{\"error\":\"编码：" + dept.getDept_code().toString() + "已存在.\"}";
				}
				if (dept.getDept_name().equals(entityMap.get("dept_name"))) {
					return "{\"error\":\"名称：" + dept.getDept_name().toString() + "已存在.\"}";
				}
			}
		}

		// String rules = (String) entityMap.get("rules");// 编码规则4-2-2....

		// 添加编码规则判断
		entityMap.put("proj_code", "HR_DEPT");
		entityMap.put("mod_code", "06");

		Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);

		String rules_v = rules.get("rules_view").toString();
		String s_view = Strings.removeFirst(rules_v);

		String dept_code = (String) entityMap.get("dept_code");// 科目编码

		String[] ruless = s_view.split("-");

		Map<Integer, Integer> maxNumMap = new HashMap<Integer, Integer>();

		Map<Integer, Integer> position = new HashMap<Integer, Integer>();

		int super_num = 0;

		for (int i = 0; i < ruless.length; i++) {
			int num = Integer.parseInt(ruless[i].trim());
			super_num += num;
			maxNumMap.put(super_num, i + 1);
			position.put(i + 1, super_num);
		}

		if (maxNumMap.containsKey(dept_code.length())) {// 编码匹配
			int dept_level = maxNumMap.get(dept_code.length());
			entityMap.put("dept_level", dept_level);
			if (dept_level == 1) {
				entityMap.put("super_code", "0");
			} else {
				int super_level = dept_level - 1;// 上级级次
				int subPosition = position.get(super_level);// 上级级次位置
				String super_code = dept_code.substring(0, subPosition);// 截取上级编码
				entityMap.put("super_code", super_code);

				List<Map<String, Object>> superCode = hrDeptMapper.qureySurp_code(entityMap);

				if (superCode.size() == 0) {
					return "{\"error\":\"输入部门编码的上级部门编码不存在，不允许添加，请添加上级部门后再操作！\"}";
				}
				if(superCode.size() != 0){
	                storeIdStr+=superCode.get(0).get("DEPT_ID")+",";
					
					mapCheck.put("dict_id_str", storeIdStr.substring(0, storeIdStr.length()-1));
					//删除科目时，判断业务表是否使用
					//if(LoadSystemInfo.getHrpMap().get("hrpDatatype").toString().equalsIgnoreCase("oracle")){
					
					sysFunUtilMapper.querySysDictDelCheck(mapCheck);
					
					storeIdStr="";
					if(mapCheck.get("reNote")!=null)reStr+=mapCheck.get("reNote");
					if(reStr!=null && !reStr.equals("")){
						return "{\"error\":\"添加失败，该部门的上级部门被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。不允许添加下级。\",\"state\":\"false\"}";
					}
					if(superCode.get(0).get("IS_LAST").toString().equals("1")){
						//return "{\"error\":\"上级部门为末级部门，不允许添加，请修改部门是否末级后再操作！\"}";
						Map<String, Object> isLast= new HashMap<String, Object>();
						isLast.put("group_id", SessionManager.getGroupId());
						isLast.put("hos_id", SessionManager.getHosId());
						isLast.put("is_last", 0);
						isLast.put("dept_id", super_code);
						hrDeptMapper.updateIsLast(isLast);
						hrDeptDictMapper.updateIsLast(isLast);
					
					}
				
				}
				if(superCode.size() != 0){
					if(!entityMap.get("kind_code").toString().equals(superCode.get(0).get("KIND_CODE").toString())){
						return "{\"error\":\"输入部门类别编码和上级部门类别编码不相同，不允许添加，请修改类别编码后再操作！\"}";
					}
				}
			}

		} else {

			return "{\"error\":\"添加失败 不符合编码规则 请重新输入！\"}";
		}

		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("dept_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("dept_name").toString()));

		Map<String, Object> utilMap = new HashMap<String, Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", "");
		utilMap.put("field_table", "HR_DEPT");
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

		

			int result = hrDeptMapper.addDept(entityMap);
			
			if (result > 0) {
				
				if(entityMap.get("is_stop").equals("0")){
					entityMap.put("is_disable",0);
				}else if(entityMap.get("is_stop").equals("1")){
					entityMap.put("is_disable",1);
				} 
				entityMap.put("dept_id", hrDeptMapper.queryCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "添加");
				entityMap.put("is_stop", 0);
				hrDeptDictMapper.addDeptDict(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addDept\"}";

		}

	}

	@Transactional(rollbackFor = Exception.class)
	public String addDeptInput(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("dept_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("dept_name").toString()));

		Map<String, Object> utilMap = new HashMap<String, Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", "");
		utilMap.put("field_table", "HR_DEPT");
		utilMap.put("field_key1", "");
		utilMap.put("field_value1", "");
		utilMap.put("field_key2", "");
		utilMap.put("field_value2", "");

		utilMap.remove("field_key2");
		utilMap.put("field_sort", "sort_code");
		int count = sysFunUtilMapper.querySysMaxSort(utilMap);
		entityMap.put("sort_code", count);

		try {

			int result = hrDeptMapper.addDept(entityMap);
			if (result > 0) {
				try{
				entityMap.put("dept_id", hrDeptMapper.queryCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "添加");
				entityMap.put("is_stop", 0);
//				accDeptAttrMapper.addAccDeptAttr(entityMap);
				hrDeptDictMapper.addDeptDict(entityMap);
				}catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage(), e);

					return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addDept\"}";

				}

			}

		/*	AccDeptAttr accDeptAttr = accDeptAttrService.queryDeptByCode(entityMap);
			if (accDeptAttr != null) {
				Map<String, Object> mapA = new HashMap<String, Object>();
				mapA.put("group_id", accDeptAttr.getGroup_id());
				mapA.put("hos_id", accDeptAttr.getHos_id());
				mapA.put("dept_id", accDeptAttr.getDept_id());
				mapA.put("type_code", entityMap.get("type_code"));
				mapA.put("natur_code", entityMap.get("natur_code"));

				accDeptAttrService.updateAccDeptAttr(mapA);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("dept_id", entityMap.get("dept_id"));
				map.put("type_code", entityMap.get("type_code"));
				map.put("natur_code", entityMap.get("natur_code"));

				accDeptAttrService.addAccDeptAttr(map);
			}*/

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addDept\"}";

		}

	}

	/**
	 * @Description 批量添加Dept
	 * @param Dept
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchDept(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hrDeptMapper.addBatchDept(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchDept\"}";

		}
	}

	/**
	 * @Description 查询Dept分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryDept(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<HrDept> list = hrDeptMapper.queryDept(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());

	}

	/**
	 * @Description 查询DeptByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public HrDept queryDeptByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrDeptMapper.queryDeptByCode(entityMap);

	}

	/**
	 * @Description 批量删除Dept
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchDept(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			String storeIdStr="";//删除科目，判断业务使用
			String reStr="";
			String superCode="";
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Map<String, Object>> supermap = new HashMap<String, Map<String, Object>>();
			List<HrDept> superCodeMap= new ArrayList<HrDept>(); 
			map.put("dict_code", "HR_DEPT");
			map.put("group_id", entityList.get(0).get("group_id"));
			map.put("hos_id", entityList.get(0).get("hos_id"));
			map.put("copy_code", "");
			map.put("acc_year", "");
			map.put("p_flag", "");//包括子科目
			if(entityList!=null && entityList.size()>0){
				for(Map<String, Object> mapVo : entityList){
					if(!mapVo.get("super_code").toString().equals("0")){
						supermap.put(mapVo.get("super_code").toString(),mapVo);
					}
					
				
					storeIdStr+=mapVo.get("dept_id")+",";
					
						map.put("dict_id_str", storeIdStr.substring(0, storeIdStr.length()-1));
						//删除科目时，判断业务表是否使用
						//if(LoadSystemInfo.getHrpMap().get("hrpDatatype").toString().equalsIgnoreCase("oracle")){
						
						sysFunUtilMapper.querySysDictDelCheck(map);
						
						storeIdStr="";
						//}
						if(map.get("reNote")!=null)reStr+=map.get("reNote");
						map.put("super_code", mapVo.get("dept_code"));
				
						List<Map<String, Object>>	dept=hrDeptMapper.qureySurpcode(map);
						if(dept.size()>0){
							superCode+="已经存在下级部门,请先删除下级部门!";
							break;
						}
				}
			}
			
			if(superCode!=null && !superCode.equals("")){
				return "{\"error\":\"删除失败。"+superCode+"\",\"state\":\"false\"}";
			}
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的部门被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			hrDeptDictMapper.deleteBatchDeptDict(entityList);
			 hrDeptMapper.deleteBatchDept(entityList);
		//遍历修改上级是否末级为是
			 for(String key :supermap.keySet()){
			 
				 Map<String, Object> isLast = new HashMap<String, Object>();
				 Map<String, Object> hse = supermap.get(key);
					isLast.put("group_id", SessionManager.getGroupId());
					isLast.put("hos_id", SessionManager.getHosId());
					map.put("super_code", hse.get("super_code"));
					List<Map<String, Object>> dept1 = hrDeptMapper.qureySurpcode(map);
					if (dept1.size() == 0) {
						isLast.put("group_id", SessionManager.getGroupId());
						isLast.put("hos_id", SessionManager.getHosId());
						isLast.put("is_last", 1);
						isLast.put("dept_id",hse.get("super_code").toString());
						hrDeptMapper.updateIsLast(isLast);
						hrDeptDictMapper.updateIsLast(isLast);
				 
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
	 * @Description 删除Dept
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteDept(Map<String, Object> entityMap) throws DataAccessException {

		try {
			hrDeptDictMapper.deleteDeptDict(entityMap);
			hrDeptMapper.deleteDept(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteDept\"}";

		}
	}

	/**
	 * @Description 更新Dept
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String updateDept(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());

		if (entityMap.get("is_auto").equals("true")) {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("dept_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("dept_name").toString()));
		}

		// 添加编码规则判断
		entityMap.put("proj_code", "HR_DEPT");
		entityMap.put("mod_code", "06");

		Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);

		String rules_v = rules.get("rules_view").toString();
		String s_view = Strings.removeFirst(rules_v);

		String dept_code = (String) entityMap.get("dept_code");// 科目编码

		String[] ruless = s_view.split("-");

		Map<Integer, Integer> maxNumMap = new HashMap<Integer, Integer>();

		Map<Integer, Integer> position = new HashMap<Integer, Integer>();

		int super_num = 0;

		for (int i = 0; i < ruless.length; i++) {
			int num = Integer.parseInt(ruless[i].trim());
			super_num += num;
			maxNumMap.put(super_num, i + 1);
			position.put(i + 1, super_num);
		}

		if (maxNumMap.containsKey(dept_code.length())) {// 编码匹配
			int dept_level = maxNumMap.get(dept_code.length());
			entityMap.put("dept_level", dept_level);
			if (dept_level == 1) {
				entityMap.put("super_code", "0");
			} else {
				int super_level = dept_level - 1;// 上级级次
				int subPosition = position.get(super_level);// 上级级次位置
				String super_code = dept_code.substring(0, subPosition);// 截取上级编码
				entityMap.put("super_code", super_code);

				List<Map<String, Object>> superCode = hrDeptMapper.qureySurp_code(entityMap);

				if (superCode.size() == 0) {
					return "{\"error\":\"输入部门编码的上级部门编码不存在，不允许添加，请添加上级部门后再操作！\"}";
				}
			}

		} else {

			return "{\"error\":\"更改失败 不符合编码规则 请重新输入！\"}";
		}

		try {
			List<HrDept> list = hrDeptMapper.queryDeptByIdName(entityMap);

			if (list.size() > 0) {

				for (HrDept dept : list) {
				/*	if (dept.getDept_code().equals(entityMap.get("dept_code"))) {
						return "{\"error\":\"编码：" + dept.getDept_code().toString() + "已存在.\"}";
					}*/
					if (dept.getDept_name().equals(entityMap.get("dept_name"))) {
						return "{\"error\":\"名称：" + dept.getDept_name().toString() + "已存在.\"}";
					}
				}
			}
            if(entityMap.get("is_last").toString().equals("1")){
            	List<HrDept> listDept=	hrDeptMapper.queryBySuperCode(entityMap);
            	if(listDept.size()>0){
            		return "{\"error\":\"名称：" + entityMap.get("dept_name").toString() + "已存在下级,不允许修改是否末级为是.\"}";
            	}
            }
			hrDeptMapper.updateDept(entityMap);
			
			//判断上级部门类别编码是否一致
			if(entityMap.get("super_code").toString().equals("0")&& !entityMap.get("ol_kind_code").toString().equals(entityMap.get("kind_code").toString()) ){
				//查询下级部门
				List<HrDept> listDept=	hrDeptMapper.queryBySuperCode(entityMap);
				
				if(listDept.size()>0){
					//修改下级部门类别编码
					hrDeptMapper.updateBatchSuperCode(entityMap,listDept);
				}
			}
			//判断上级部门是否停用
			if(entityMap.get("super_code").toString().equals("0")){
            List<HrDept> listDept=	hrDeptMapper.queryBySuperCode(entityMap);
				if(listDept.size()>0){
					//修改是否停用
					hrDeptMapper.updateBatchStop(entityMap,listDept);
				}
			}
			if (entityMap.get("history").equals("true")) {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("dept_id", entityMap.get("dept_id"));
				map.put("is_stop", 1);

				hrDeptDictMapper.updateDeptDictState(map);

				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "变更");
				entityMap.put("is_disable", entityMap.get("is_disable"));
		     	entityMap.put("is_stop", entityMap.get("is_stop"));				

		    	if(entityMap.get("super_code").toString().equals("0")&& !entityMap.get("ol_kind_code").toString().equals(entityMap.get("kind_code").toString()) ){
					List<HrDeptDict> listDept=	hrDeptDictMapper.queryBySuperCode(entityMap);
					
					if(listDept.size()>0){
						
						hrDeptDictMapper.updateBatchSuperCode(entityMap,listDept);
					}
				}
		    	if(entityMap.get("super_code").toString().equals("0")){
		    		List<HrDeptDict> listDept=	hrDeptDictMapper.queryBySuperCode(entityMap);
						
						if(listDept.size()>0){
							//修改是否停用
							hrDeptDictMapper.updateBatchStop(entityMap,listDept);
						}
					}
				hrDeptDictMapper.addDeptDict(entityMap);

				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			} else {

				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "修改");
				if(entityMap.get("super_code").toString().equals("0")&& !entityMap.get("ol_kind_code").toString().equals(entityMap.get("kind_code").toString()) ){
					List<HrDeptDict> listDept=	hrDeptDictMapper.queryBySuperCode(entityMap);
					
					if(listDept.size()>0){
						
						hrDeptDictMapper.updateBatchSuperCode(entityMap,listDept);
					}
				}
				if(entityMap.get("super_code").toString().equals("0")){
					List<HrDeptDict> listDept=	hrDeptDictMapper.queryBySuperCode(entityMap);
						
						if(listDept.size()>0){
							//修改是否停用
							hrDeptDictMapper.updateBatchStop(entityMap,listDept);
						}
					}
				hrDeptDictMapper.updateDeptDict_dept(entityMap);

				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDept\"}";

		}
	}

	@Transactional(rollbackFor = Exception.class)
	public String updateDeptInput(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());

		try {
			hrDeptMapper.updateDept(entityMap);

			entityMap.put("user_code", SessionManager.getUserCode());
			entityMap.put("create_date", new Date());
			entityMap.put("dlog", "变更");

			hrDeptDictMapper.updateDeptDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDept\"}";

		}
	}

	/**
	 * @Description 批量更新Dept
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchDept(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hrDeptMapper.updateBatchDept(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDept\"}";

		}

	}

	/**
	 * @Description 导入Dept
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importDept(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	/**
	 * @Description 查询DeptByMenu
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryDeptByMenu(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<HrDept> deptList = hrDeptMapper.queryDept(entityMap);
		if (deptList.size() > 0) {
			int row = 0;
			for (HrDept dept : deptList) {
				if (row == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				row++;
				jsonResult.append("id:'" + dept.getDept_code() + "',");
				jsonResult.append("dept_id:'" + dept.getDept_id() + "',");
				jsonResult.append("hos_id:" + dept.getHos_id() + ",");
				jsonResult.append("group_id:" + dept.getGroup_id() + ",");
				jsonResult.append("pId:" + dept.getSuper_code() + ",");
				jsonResult.append("name:'" + dept.getDept_name() + "'" + ",open:true");
				jsonResult.append("}");
			}
		}
		jsonResult.append("]}");
		return jsonResult.toString();
	}

	@Override
	public String updateDeptCode(Map<String, Object> entityMap) throws DataAccessException {
		try {
			hrDeptMapper.updateDeptCode(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDept\"}";

		}
	}

	@Override
	public String updateDeptName(Map<String, Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("dept_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("dept_name").toString()));
			hrDeptMapper.updateDeptName(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDept\"}";

		}
	}

	/**
	 * 根据输入的部门编码 自动生成其相应的 部门级次、上级编码
	 * 
	 * @param mapVo
	 * @return
	 */
	public String getSuperCode(@RequestParam Map<String, Object> mapVo) {

		String rules = (String) mapVo.get("rules");// 编码规则4-2-2....

		String dept_code = (String) mapVo.get("dept_code");// 科目编码

		String[] ruless = rules.split("-");

		Map<Integer, Integer> maxNumMap = new HashMap<Integer, Integer>();

		Map<Integer, Integer> position = new HashMap<Integer, Integer>();

		int super_num = 0;

		for (int i = 0; i < ruless.length; i++) {
			int num = Integer.parseInt(ruless[i].replace(" ", ""));
			super_num += num;
			maxNumMap.put(super_num, i + 1);
			position.put(i + 1, super_num);
		}

		if (maxNumMap.containsKey(dept_code.length())) {// 编码匹配
			int dept_level = maxNumMap.get(dept_code.length());
			mapVo.put("dept_level", dept_level);
			if (dept_level == 1) {
				mapVo.put("super_code", "0");
			} else {
				int super_level = dept_level - 1;// 上级级次
				int subPosition = position.get(super_level);// 上级级次位置
				String super_code = dept_code.substring(0, subPosition);// 截取上级编码
				mapVo.put("super_code", super_code);
				List<Map<String, Object>> superCode = hrDeptMapper.qureySurp_code(mapVo);

				if (superCode.size() == 0) {
					return "{\"error\":\"输入部门编码的上级部门编码不存在，不允许修改，请添加上级部门后再操作！\"}";
				}
			}
			return ChdJson.toJson(mapVo);
		} else {

			return "{\"error\":\"添加失败 不符合编码规则 请重新输入！\"}";
		}

	}

	public Map<String, Object> queryDeptByCodeName(Map<String, Object> entityMap) {
		return hrDeptMapper.queryDeptByCodeName(entityMap);
	}

	//查询组织结构图
		@Override
		public String queryDeptOrg(Map<String, Object> map)throws DataAccessException{
		
			//typeCode：0医院用户，1集团用户，2超级管理员，3集团管理员，4医院管理员
			String typeCode=SessionManager.getTypeCode();
			String groupId=SessionManager.getGroupId();
			String hosId=SessionManager.getHosId();
			map.put("group_id", groupId);
			map.put("hos_id", hosId);
			map.put("user_id", SessionManager.getUserId());
			
			StringBuilder json=new StringBuilder();
			
			try{
				
				json.append("{");
				
				if(typeCode.equals("0")){
					json.append("\"name\":\""+SessionManager.getHosCode()+"\",");
					json.append("\"title\":\""+SessionManager.getHosSimple()+"\",");
				}else{
					json.append("\"name\":\""+SessionManager.getGroupCode()+"\",");
					json.append("\"title\":\""+SessionManager.getGroupSimple()+"\",");
				}
				
				json.append("\"children\":[");
				
				//查询科室
				List<Map<String,Object>> lastList=hrDeptMapper.queryDeptOrg(map);
				if(lastList!=null && lastList.size()>0){
					
					for(Map<String,Object> c:lastList){
						
						if(!c.get("dept_level").toString().equals("1")){
							continue;
						}
						
						json.append("{");
						json.append("\"id\":\""+c.get("name").toString()+"\",");
						json.append("\"name\":\""+c.get("name").toString()+"\",");
						json.append("\"title\":\""+c.get("title").toString()+"\",");
						json.append("\"className\":\"last\"");
						getDeptChild(json,lastList,c.get("name").toString());
							
						json.append("},");
							
					}
				}
				
				json.append("]}");
				
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				throw new SysException(e.getMessage());
			}
			
			return replace(json.toString());
		}
		
		
		//递归取下级科室
		private void getDeptChild(StringBuilder json,List<Map<String,Object>> lastList,String deptCode){
			
			json.append(",\"children\":[");
			for(Map<String,Object> c:lastList){
				
				if(!deptCode.equals(c.get("super_code").toString())){
					continue;
				}
				
				json.append("{");
				json.append("\"name\":\""+c.get("name").toString()+"\",");
				json.append("\"title\":\""+c.get("title").toString()+"\",");
				json.append("\"className\":\"last\"");
				getDeptChild(json,lastList,c.get("name").toString());
				json.append("},");
				
			}
			
			json.append("]");
			
		}
		
		//递归替换children\":[]
		private String replace(String json){
			
			if(json.indexOf(",\"children\":[]")!=-1){
				json=json.replace(",\"children\":[]", "");
				replace(json);
			}
			return json;
		}
		@Override
		public List<Map<String, Object>> querySuper(Map<String, Object> mapVo) {
			List<Map<String, Object>> superCode = hrDeptMapper.qureySurp_code(mapVo);
			return superCode;
		}

		public void updateIsLast(Map<String, Object> isLast) {
			hrDeptMapper.updateIsLast(isLast);
			hrDeptDictMapper.updateIsLast(isLast);
			
		}

		public List<Map<String, Object>> qureySurp_code(Map<String, Object> entityMap) {
			return hrDeptMapper.qureySurp_code(entityMap);
		}
}
