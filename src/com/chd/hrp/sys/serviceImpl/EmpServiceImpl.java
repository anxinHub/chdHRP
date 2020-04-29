/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.sys.serviceImpl;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.AccEmpAttrMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchCheckMapper;
import com.chd.hrp.acc.dao.wage.AccWageEmpMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageCarryOverMapper;
import com.chd.hrp.acc.dao.wagedata.AccWagePayMapper;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.dao.EmpDictMapper;
import com.chd.hrp.sys.dao.EmpKindMapper;
import com.chd.hrp.sys.dao.EmpMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.entity.Emp;
import com.chd.hrp.sys.entity.EmpDict;
import com.chd.hrp.sys.entity.EmpKind;
import com.chd.hrp.sys.service.EmpService;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.github.pagehelper.PageInfo;
/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0 
 */
@Service("empService")
public class EmpServiceImpl implements EmpService {

	private static Logger logger = Logger.getLogger(EmpServiceImpl.class);

	@Resource(name = "empMapper")
	private final EmpMapper empMapper = null;

	@Resource(name = "empDictMapper")
	private final EmpDictMapper empDictMapper = null;

	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	@Resource(name = "accEmpAttrMapper")
	private final AccEmpAttrMapper accEmpAttrMapper = null;
	
	@Resource(name = "accVouchCheckMapper")
	private final AccVouchCheckMapper accVouchCheckMapper = null;
	
	@Resource(name = "accWageEmpMapper")
	private final AccWageEmpMapper accWageEmpMapper = null;
	
	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;
	
	@Resource(name = "empKindMapper")
	private final EmpKindMapper empKindMapper = null;
	
	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
	
	@Resource(name = "accWagePayMapper")
	private final AccWagePayMapper accWagePayMapper = null;
	
	@Resource(name = "accWageCarryOverMapper")
	private final AccWageCarryOverMapper accWageCarryOverMapper = null;

	/**
	 * @Description 添加Emp
	 * @param Emp entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addEmp(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("group_id", SessionManager.getGroupId());

		entityMap.put("hos_id", SessionManager.getHosId());
		
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("emp_name").toString()));
		
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("emp_name").toString()));
		// 添加编码规则判断
		entityMap.put("proj_code", "HOS_EMP");
		
		entityMap.put("mod_code", "00");

		String proj_code = entityMap.get("emp_code").toString();

		Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
		
		if (null != entityMap.get("emp_code")) {
			
			Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
			
			Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
			
			int max_level = Integer.parseInt(rules.get("max_level").toString());
			
			if(max_level>0){
				
				String rules_v = rules.get("rules_view").toString();
				
				String s_view = Strings.removeFirst(rules_v);
				
				Object level = proj_code.length();
				int code=proj_code.length();
				if(rules_level_length!=null){
					//当第一级为0时 不验证规则
					int sLevel=Integer.parseInt(rules_level_length.get(1).toString());
					if(!rules_level_length.get(1).toString().equals("0")){
						
						if(code > sLevel){
							return "{\"error\":\"编码不符合要求,请重新添加.编码规则长度：" + s_view + "\"}";
						}
						String eCode="";
						for (int i = 0; i < sLevel-code; i++) {
							eCode+="0";
						}
						String emp_code=eCode+proj_code;
						entityMap.put("emp_code", emp_code);
						/*if (level != rules_level_length.get(1)) {
							return "{\"error\":\"编码不符合要求,请重新添加.编码规则长度：" + s_view + "\"}";
						}*/
						
					}
				}
			}
			
		}
		

		Emp emp = empMapper.queryEmpById(entityMap);

		if (emp != null) {

			return "{\"error\":\"编码：" + emp.getEmp_code().toString() + "已存在.\"}";

		}

		Map<String, Object> utilMap = new HashMap<String, Object>();
		
		utilMap.put("group_id", entityMap.get("group_id"));
		
		utilMap.put("hos_id", entityMap.get("hos_id"));
		
		utilMap.put("copy_code", "");
		
		utilMap.put("field_table", "HOS_EMP");
		
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
			
			int result = empMapper.addEmp(entityMap);
			
			if (result > 0) {
				
				entityMap.put("emp_id", empMapper.queryCurrentSequence());
				
				entityMap.put("user_code", SessionManager.getUserCode());
				
				entityMap.put("create_date", DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
				
				entityMap.put("dlog", "添加");
				
				entityMap.put("remark", "[系统]职工新增");
				
				entityMap.put("is_stop", 0);
				
				entityMap.put("is_disable", entityMap.get("is_stop"));
				
				int empNo=empDictMapper.queryEmpDictNextval(entityMap);
				
				entityMap.put("emp_no", empNo);
				
				empDictMapper.addEmpDict(entityMap);
				
				empMapper.addEmpChange(entityMap);
				//增加职工默认发放工资、性别、发放方式
				entityMap.put("sex", 0);
				
				entityMap.put("is_pay", 1);
				
				entityMap.put("pay_type_code", "01");
				
				accEmpAttrMapper.addAccEmpAttr(entityMap); 
				
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addEmp\"}";

		}

	}

	public String addImportEmp(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int result = empMapper.addEmp(entityMap);
			if (result > 0) {
				entityMap.put("emp_id", empMapper.queryCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				//entityMap.put("create_date", new Date());
				entityMap.put("create_date", DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
				entityMap.put("dlog", "添加");
				entityMap.put("is_stop", 0);
				int empNo=empDictMapper.queryEmpDictNextval(entityMap);
				
				entityMap.put("emp_no", empNo);
				empDictMapper.addEmpDict(entityMap);
				entityMap.put("sex", 0);
				entityMap.put("is_pay", 1);
				entityMap.put("pay_type_code", "01");
				accEmpAttrMapper.addAccEmpAttr(entityMap); 
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addEmp\"}";

		}

	}

	/**
	 * @Description 批量添加Emp
	 * @param Emp entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchEmp(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			empMapper.addBatchEmp(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchEmp\"}";

		}
	}

	/**
	 * @Description 查询Emp分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryEmp(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<Emp> list = empMapper.queryEmp(entityMap);
			
			return ChdJson.toJson(list);
			
		
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Emp> list = empMapper.queryEmp(entityMap, rowBounds);
			
	        PageInfo page = new PageInfo(list); 
			
			return ChdJson.toJson(list, page.getTotal());
			
		}

	}

	/**
	 * @Description 查询EmpByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public Emp queryEmpByCode(Map<String, Object> entityMap) throws DataAccessException {

		return empMapper.queryEmpByCode(entityMap);

	}

	/**
	 * @Description 批量删除Emp
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchEmp(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			String storeIdStr="";//删除科目，判断业务使用
			String reStr="";
			String superCode="";
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			map.put("dict_code", "HOS_EMP");
			map.put("group_id", entityList.get(0).get("group_id"));
			map.put("hos_id", entityList.get(0).get("hos_id"));
			map.put("copy_code", "");
			map.put("acc_year", "");
			map.put("p_flag", "");//包括子科目
			if(entityList!=null && entityList.size()>0){
				for(Map<String, Object> mapVo : entityList){
					storeIdStr+=mapVo.get("emp_id")+",";
					
						map.put("dict_id_str", storeIdStr.substring(0, storeIdStr.length()-1));
						//删除科目时，判断业务表是否使用
						//if(LoadSystemInfo.getHrpMap().get("hrpDatatype").toString().equalsIgnoreCase("oracle")){
						
						sysFunUtilMapper.querySysDictDelCheck(map);
						
						storeIdStr="";
						//}
						if(map.get("reNote")!=null)reStr+=map.get("reNote");
					
				}
			}
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的项目被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			empDictMapper.deleteBatchEmpDict(entityList);
			empMapper.deleteBatchEmp(entityList);
			accEmpAttrMapper.deleteBatchAccEmpAttr(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchEmp\"}";

		}

	}

	/**
	 * @Description 删除Emp
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteEmp(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			 int countVouch = accVouchCheckMapper.queryAccVouchCheckCountByEmpId(entityMap) ;
			
			if (countVouch >0){
				 
				return "{\"error\":\"该职工已在辅助核算中引用，不能删除\"}";
			}
			
			int countWage = accWageEmpMapper.queryAccWageEmpCountByEmpId(entityMap) ;
			
			if (countWage >0){
				 
				return "{\"error\":\"该职工已在工资中引用，不能删除\"}";
			} 
	
			
			empDictMapper.deleteEmpDict(entityMap);
			empMapper.deleteEmp(entityMap);
			accEmpAttrMapper.deleteAccEmpAttr(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteEmp\"}";

		}
	}

	/**
	 * @Description 更新Emp
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateEmp(Map<String, Object> entityMap) throws DataAccessException {

		if (entityMap.get("is_auto").equals("true")) {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("emp_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("emp_name").toString()));
		}

		// 添加编码规则判断  --编码不能修改所以修改功能不需要判断编码
		/*entityMap.put("proj_code", "HOS_EMP");
		entityMap.put("mod_code", "00");
		String proj_code = entityMap.get("emp_code").toString();

		Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
		if (null != entityMap.get("emp_code")) {
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
		Emp emp = empMapper.queryEmpById(entityMap);

		if (emp != null) {

			return "{\"error\":\"编码：" + emp.getEmp_code().toString() + "已存在.\"}";

		}*/

		/*
		 * Emp empSort = empMapper.queryEmp_sort(entityMap); if(empSort !=
		 * null){ return "{\"error\":\"排序号：" + empSort.getEmp_sort().toString()
		 * + "已存在.不能使用，请重新输入\"}"; }
		 */
		
		String changeMsg="";
		
		Emp emp1 = empMapper.queryEmpByCode(entityMap);

		if (emp1 != null) {

			if(!entityMap.get("emp_name").equals(emp1.getEmp_name())){
				
				changeMsg+=",[系统]名称变更";
				
			}
			
			if(Integer.parseInt(entityMap.get("is_disable").toString())!=emp1.getIs_disable()){
				
				changeMsg+=",[系统]职工停用";
				
			}
			if(Integer.parseInt(entityMap.get("dept_id").toString())!=emp1.getDept_id()){
				
				changeMsg+=",[系统]职工部门变更";
				
			}
			if(!entityMap.get("kind_code").equals(emp1.getKind_code())&&emp1.getKind_code()!=null&&!"".equals(entityMap.get("kind_code"))){
				
				changeMsg+=",[系统]职工分类变更";
				
			}
		}else{
			
			changeMsg+=",[系统]职工新增";
		}
		 
		if(changeMsg.length()>0){
			
			entityMap.put("remark", changeMsg.substring(1));
			
		}
		
		EmpDict empDict=null;
		
		try {

			empMapper.updateEmp(entityMap);

			if (entityMap.get("history").equals("true")) {

				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("group_id", entityMap.get("group_id"));
				
				map.put("hos_id", entityMap.get("hos_id"));
				
				map.put("emp_id", entityMap.get("emp_id"));
				
				map.put("emp_code", entityMap.get("emp_code"));
				
				map.put("is_stop", entityMap.get("is_stop"));
				
				empDictMapper.updateEmpDictState(map);

				entityMap.put("user_code", SessionManager.getUserCode());
				
				entityMap.put("create_date", DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
				
				entityMap.put("dlog", "变更");
				
				entityMap.put("is_disable", entityMap.get("is_stop"));
				
		     	entityMap.put("is_stop", entityMap.get("is_stop"));
		     	
		     	int empNo=empDictMapper.queryEmpDictNextval(entityMap);
				
				entityMap.put("emp_no", empNo);
				
				empDictMapper.addEmpDict(entityMap);
				
				if (changeMsg.length() > 0)
		        {
		          empMapper.addEmpChange(entityMap);
		        }
				
			} else {
				
				entityMap.put("user_code", SessionManager.getUserCode());
				
				entityMap.put("create_date", DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
				
				entityMap.put("dlog", "变更");
			/*	entityMap.put("is_disable", entityMap.get("is_stop"));*/
		     	
				empDictMapper.updateEmpDict(entityMap);
				
				 if (changeMsg.length() > 0)
			        {
			          this.empMapper.addEmpChange(entityMap);
			        }
				 
			}
			
			//修改职工的信息同步更新职工在当月工资表中的信息
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
			entityMap.put("wage_flag", "1");
			
 			String account_day = accWageCarryOverMapper.queryAccYearMonthNow(entityMap);
 			
 			entityMap.put("acc_year", account_day.substring(0,4));
			
 			entityMap.put("acc_month", account_day.substring(5,7));
 			
			accWagePayMapper.updateAccWagePayByEmpDict(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateEmp\"}";

		}
	}

	/**
	 * @Description 批量更新Emp
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchEmp(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			empMapper.updateBatchEmp(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateEmp\"}";

		}

	}

	/**
	 * @Description 导入Emp
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importEmp(Map<String, Object> map) throws DataAccessException {

		try {

			String columns = map.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);
			if (jsonColumns == null || jsonColumns.size() == 0) {
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}

			String content = map.get("content").toString();
			List<Map<String, List<String>>> allDataList = SpreadTableJSUtil.toListMap(content, 1);
			if (allDataList == null || allDataList.size() == 0) {
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}

			Map<String, Object> entityMap = new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			//entityMap.put("copy_code", SessionManager.getCopyCode());
			
			// 添加编码规则判断
			entityMap.put("proj_code", "HOS_EMP");
			entityMap.put("mod_code", "00");
			
			Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
			Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
			Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
			
			
			Map<String, Object> queryMaxSortCodeMap = new HashMap<String, Object>();
        	
			queryMaxSortCodeMap.put("group_id", entityMap.get("group_id"));
			queryMaxSortCodeMap.put("hos_id", entityMap.get("hos_id"));
			queryMaxSortCodeMap.put("field_table", "HOS_EMP");
			queryMaxSortCodeMap.put("field_sort", "sort_code");
    		
    		int sort_code = sysFunUtilMapper.querySysMaxSort(queryMaxSortCodeMap);
			
			
						

			// 查询 职工字典 List
			List<Emp> empList = empMapper.queryEmp(entityMap);
			
			entityMap.put("is_stop", "0");
			// 查询科室字典 List
			List<DeptDict> deptList = deptDictMapper.queryDeptDict(entityMap);
			// 查询职工分类字典 List
			List<EmpKind> empKindList = empKindMapper.queryEmpKind(entityMap);
			

			// 用于存储查询empList中的Emp对象,以键值对的形式存储,用于判断职工是否存在
			Map<String, Emp> empMap = new HashMap<String, Emp>();
			// 用于存储查询deptList中的DeptDict对象,以键值对的形式存储,用于判断科室是否存在
			Map<String,DeptDict> deptMap = new HashMap<String,DeptDict>();
			// 用于存储查询empKindList中的EmpKind对象,以键值对的形式存储,用于判断职工分类是否存在
			Map<String,EmpKind> empKindMap = new HashMap<String,EmpKind>();
			
			

			// 将指标List存入Map 键:emp_code 值:Emp
			for (Emp emp : empList) {
				empMap.put(emp.getEmp_code(), emp);
			}

			// 将科室List存入Map 键:dept_name 值:DeptDict
			for (DeptDict dept : deptList) {
				deptMap.put(dept.getDept_code(), dept);
				deptMap.put(dept.getDept_name(), dept);
			}
			
			// 将科室List存入Map 键:dept_name 值:DeptDict
			for (EmpKind empKind : empKindList) {
				empKindMap.put(empKind.getKind_code(), empKind);
				empKindMap.put(empKind.getKind_name(), empKind);
			}
			
			
			
			// 用于存储传的数据值,判断数据是否重复
			Map<String, String> exitMap = new HashMap<String, String>();
			// 用于记录重复数据
			StringBuffer err_sb = new StringBuffer();
			
			// 存储保存数据List
			List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
			// 存储保存数据List
			List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
			// 遍历导入数据
			for (Map<String, List<String>> item : allDataList) {

				List<String> emp_code = item.get("职工编码");
				List<String> emp_name = item.get("职工名称");
				List<String> dept_name = item.get("科室编码或科室名称");
				List<String> emp_kind = item.get("职工分类编码或职工分类名称");
				List<String> note = item.get("备注");
				
				if (null != emp_code.get(1)) {
				
				int max_level = Integer.parseInt(rules.get("max_level").toString());
				String proj_code = emp_code.get(1).toString();
				String empCode="";
				if(max_level>0){
					
					String rules_v = rules.get("rules_view").toString();
					
					String s_view = Strings.removeFirst(rules_v);
					
					Object level = proj_code.length();
					int code=proj_code.length();
					if(rules_level_length!=null){
						//当第一级为0时 不验证规则
						int sLevel=Integer.parseInt(rules_level_length.get(1).toString());
						if(!rules_level_length.get(1).toString().equals("0")){
							
							if(code > sLevel){
								return "{\"error\":\"编码"+proj_code+"不符合要求,请重新添加.编码规则长度：" + s_view + "\"}";
							}
							String eCode="";
							for (int i = 0; i < sLevel-code; i++) {
								eCode+="0";
							}
							empCode=eCode+proj_code;
							entityMap.put("emp_code", empCode);
							emp_code.set(1, empCode);
						}
					}
				}
				
			}

				if (emp_code.get(1) == null && emp_name == null) {
					break;
				}

				if (emp_code.get(1) == null) {
					return "{\"warn\":\"职工编码为空！\",\"state\":\"false\",\"row_cell\":\"" + emp_code.get(0) + "\"}";
				}else{
					if(emp_code.get(1).length() != Integer.parseInt(String.valueOf(rules_level_length.get(1)))){
						return "{\"warn\":\"编码不符合要求,请重新添加\",\"state\":\"false\",\"row_cell\":\"" + emp_code.get(0) + "\"}";
					}
				} 

				if (emp_name.get(1) == null) {
					return "{\"warn\":\"职工名称为空！\",\"state\":\"false\",\"row_cell\":\"" + emp_name.get(0) + "\"}";
				}

				if (dept_name.get(1) == null) {
					return "{\"warn\":\"科室编码或科室名称为空！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
				} else {
					if (deptMap.get(dept_name.get(1)) == null) {
						return "{\"warn\":\"" + dept_name.get(1) + ",科室编码或科室名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
					}
				}
				
				
				if (emp_kind.get(1) != null) {
					
					if (empKindMap.get(emp_kind.get(1)) == null) {
						return "{\"warn\":\"" + emp_kind.get(1) + ",职工分类编码或职工分类名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + emp_kind.get(0) + "\"}";
					}
				}
				
				
				// 以职工编码为键值,判断导入数据是否重复
				String key = emp_code.get(1);
				if (exitMap.get(key) != null) {
					err_sb.append(emp_code.get(1) + "职工编码" );
				} else {
					exitMap.put(key, key);
				}
				
				// 添加数据Map
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("group_id", SessionManager.getGroupId());
				dataMap.put("hos_id", SessionManager.getHosId());
				dataMap.put("emp_code", emp_code.get(1));
				dataMap.put("emp_name", emp_name.get(1));
				dataMap.put("dept_id", deptMap.get(dept_name.get(1)).getDept_id());
				dataMap.put("dept_no", deptMap.get(dept_name.get(1)).getDept_no());
				dataMap.put("kind_code",emp_kind.get(1) == null ? "" :empKindMap.get(emp_kind.get(1)).getKind_code());
				
				
				
				dataMap.put("note", note.get(1) == null ? "" :note.get(1).toString());
				dataMap.put("spell_code", StringTool.toPinyinShouZiMu(emp_name.get(1).toString()));
				dataMap.put("wbx_code", StringTool.toWuBi(emp_name.get(1).toString()));
				dataMap.put("user_code", SessionManager.getUserCode());
				dataMap.put("create_date",DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
				
				dataMap.put("is_stop", 0);
				dataMap.put("is_disable", 0);
				
				if (empMap.get(emp_code.get(1)) == null) {
					dataMap.put("dlog", "添加");
					dataMap.put("sort_code", sort_code);
					addList.add(dataMap);
				}else{
					dataMap.put("dlog", "更新");
					dataMap.put("sort_code", empMap.get(emp_code.get(1)).getSort_code());
					dataMap.put("emp_id", empMap.get(emp_code.get(1)).getEmp_id());
					dataMap.put("emp_no", empMap.get(emp_code.get(1)).getEmp_no());
					updateList.add(dataMap);
				}
				
				sort_code += 10;
			}

			if (err_sb.length() > 0) {// 重复数据是否存在
				return "{\"warn\":\"以下数据【" + err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}
			
			if(updateList.size() > 0 ){
				for(Map<String,Object> updateMap : updateList){
					// 修改职工 非变更表
					empMapper.updateEmp(updateMap);
					// 修改职工 变更表停用状态
					empDictMapper.updateEmpDictState(updateMap);
					updateMap.put("emp_no", empDictMapper.queryEmpDictSeqNextVal());
					// 新增职工 变更表记录
					empDictMapper.addEmpDict(updateMap);
				}
			}
			
			if(addList.size() > 0 ){
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> addEmpMap : addList) {
					int emp_id = empMapper.queryEmpSeqNextVal();
					addEmpMap.put("emp_id", emp_id);
					addEmpMap.put("create_date", new Date());
					list.add(addEmpMap);
				}
				empMapper.addBatchHosEmp(list);
				empDictMapper.addBatchEmpDict(list);
			}
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}

	@Override
	public String updateEmpName(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("emp_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("emp_name").toString()));
		try {

			empMapper.updateEmpName(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateEmp\"}";

		}
	}

	@Override
	public String updateEmpCode(Map<String, Object> entityMap) throws DataAccessException {
		try {

			empMapper.updateEmpCode(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateEmp\"}";

		}
	}
	
	public String queryEmpChangeRemark(Map<String, Object> entityMap)
		    throws DataAccessException
	  {
	    List list = empMapper.queryEmpChangeRemark(entityMap);
	
	    return ChdJson.toJson(list);
	  }
}
