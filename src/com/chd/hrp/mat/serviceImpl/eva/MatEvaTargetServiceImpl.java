package com.chd.hrp.mat.serviceImpl.eva;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.eva.MatEvaTargetMapper;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.eva.MatEvaTargetService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;

@Service("matEvaTargetService")
public class MatEvaTargetServiceImpl implements MatEvaTargetService{

	private static Logger logger = Logger.getLogger(MatEvaTargetServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matEvaTargetMapper")
	private final MatEvaTargetMapper matEvaTargetMapper = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	//指标类别查询(返回树形结构的json格式)
	@Override
	public String queryMatEvaTargetTypeTree(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> list = matEvaTargetMapper.queryMatEvaTargetTypeList(map);
		
		return JsonListMapUtil.listToString(list);
	}
	//指标分类编码规则查询
	@Override
	public String queryMatEvaTargetTypeRules(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("proj_code", "MAT_EVA_TARGET_TYPE");
		map.put("mod_code", "04");
	    String rules = matCommonService.getMatHosRules(map);//获取编码规则2-2-2....
	    String[] ruless  = rules.split("-");
	    
	    StringBuffer sb = new StringBuffer();
	    
	    for(int i = 0; i <= ruless.length; i++){
	    	
	    	if(ruless[i].equals("0")){
	    		break;
	    	}
	    	if(i > 0){
	    		sb.append("-");
	    	}
	    	sb.append(ruless[i]);
	    }
		return "{\"rules\":\""+sb.toString()+"\"}";
	}
	//指标类别保存
	@Override
	public Map<String, Object> saveMatEvaTargetType(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("target_type_code") == null || "".equals(map.get("target_type_code").toString())){
				retMap.put("state", "false");
				retMap.put("error", "指标分类编码为空！");
				return retMap;
			}
			if(map.get("target_type_name") == null || "".equals(map.get("target_type_name").toString())){
				retMap.put("state", "false");
				retMap.put("error", "指标分类名称为空！");
				return retMap;
			}
			
			map.put("spell_code", StringTool.toPinyinShouZiMu(map.get("target_type_name").toString()));
			map.put("wbx_code", StringTool.toWuBi(map.get("target_type_name").toString()));

			//用于编码校验
			map.put("table_code", "mat_eva_target_type");
			map.put("col_code", "target_type_code");
			map.put("code_val", map.get("target_type_code"));
			map.put("col_name", "target_type_name");
			map.put("name_val", map.get("target_type_name"));
			
			if(map.get("is_update") != null && "1".equals(map.get("is_update").toString())){
				//名称重复校验
				int count = matCommonMapper.existsNameByUpdate(map);
				if(count > 0){
					retMap.put("state", "false");
					retMap.put("error", "名称重复！");
					return retMap;
				}
				//修改
				matEvaTargetMapper.updateMatEvaTargetType(map);
			}else{
				//编码规则校验
				boolean is_update_super = false;
				String target_type_code = map.get("target_type_code").toString();
				String super_code = map.get("super_code").toString();
				String[] rules = map.get("rules").toString().split("-");
				int super_level = Integer.parseInt(map.get("super_level").toString());
				if(!"TOP".equals(super_code)){
					if(!target_type_code.startsWith(super_code)){
						retMap.put("state", "false");
						retMap.put("error", "与父级编码不对应！");
						return retMap;
					}
					//去掉父级编码用于判断编码规则
					target_type_code = target_type_code.substring(super_code.length(), target_type_code.length());
					
					is_update_super = true;
				}
				int num = Integer.parseInt(rules[super_level]);
				if(target_type_code.length() != num){
					retMap.put("state", "false");
					retMap.put("error", "不符合编码规范！");
					return retMap;
				}
				
				//查询父级节点是否已维护了指标
				Integer con = matEvaTargetMapper.queryMatEvaTargetBySuperType(map);
				if(con != null && con > 0){
					retMap.put("state", "false");
					retMap.put("error", "父级节点已维护指标，不能新增下级类别！");
					return retMap;
				}
				
				//编码名称重复校验
				int count = matCommonMapper.existsCodeNameByAdd(map);
				if(count > 0){
					retMap.put("state", "false");
					retMap.put("error", "编码或名称重复！");
					return retMap;
				}
				
				//自动生成排序号
				Integer max_sort = matCommonMapper.getMaxSortCodeByTable(map);
				map.put("sort_code", (max_sort == null ? 0 : max_sort) + 10);
				//新增默认为末级
				map.put("is_last", 1);
				//级次
				map.put("type_level", super_level + 1);
				//新增
				matEvaTargetMapper.addMatEvaTargetType(map);
				if(is_update_super){
					matEvaTargetMapper.updateMatEvaTargetTypeIsLast(map);
				}
				//新增时返回排序号
				retMap.put("sort_code", map.get("sort_code"));
			}
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	//指标类别删除
	@Override
	public Map<String, Object> deleteMatEvaTargetType(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("target_type_code") == null || "".equals(map.get("target_type_code").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择指标分类！");
				return retMap;
			}
			//判断引用
			String reStr="";
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("dict_code", "mat_eva_target_type".toUpperCase());
			checkMap.put("group_id", map.get("group_id"));
			checkMap.put("hos_id", map.get("hos_id"));
			checkMap.put("copy_code", map.get("copy_code"));
			checkMap.put("dict_id_str", map.get("target_type_code"));
			checkMap.put("acc_year", "");
			checkMap.put("p_flag", "1");
			sysFunUtilMapper.querySysDictDelCheck(checkMap);
			
			if(checkMap.get("reNote")!=null) {
				reStr += checkMap.get("reNote");
			}
			
			if(reStr!=null && !reStr.equals("")){
				retMap.put("state", "false");
				retMap.put("error", "删除失败，选择的指标类别被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。");
				return retMap;
			}
			
			//删除
			matEvaTargetMapper.deleteMatEvaTargetType(map);
			
			//判断是否修改为末级
			matEvaTargetMapper.updateMatEvaTargetTypeIsLast(map);

			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	
	//标准标度查询
	@Override
	public String queryMatEvaScaleList(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> list = matEvaTargetMapper.queryMatEvaScaleList(map);
		
		return ChdJson.toJson(list);
	}
	//标准标度保存
	@Override
	public Map<String, Object> saveMatEvaScale(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("scale_code") == null || "".equals(map.get("scale_code").toString())){
				retMap.put("state", "false");
				retMap.put("error", "标度代码为空！");
				return retMap;
			}
			if(map.get("scale_name") == null || "".equals(map.get("scale_name").toString())){
				retMap.put("state", "false");
				retMap.put("error", "标度名称为空！");
				return retMap;
			}
			if(map.get("scale_point") == null || "".equals(map.get("scale_point").toString())){
				retMap.put("state", "false");
				retMap.put("error", "标度比例为空！");
				return retMap;
			}
			
			map.put("spell_code", StringTool.toPinyinShouZiMu(map.get("scale_name").toString()));
			map.put("wbx_code", StringTool.toWuBi(map.get("scale_name").toString()));

			//用于编码校验
			map.put("table_code", "mat_eva_scale");
			map.put("col_code", "scale_code");
			map.put("code_val", map.get("scale_code"));
			map.put("col_name", "scale_name");
			map.put("name_val", map.get("scale_name"));
			
			if(map.get("is_update") != null && "1".equals(map.get("is_update").toString())){
				//名称重复校验
				int count = matCommonMapper.existsNameByUpdate(map);
				if(count > 0){
					retMap.put("state", "false");
					retMap.put("error", "名称重复！");
					return retMap;
				}
				//修改
				matEvaTargetMapper.updateMatEvaScale(map);
			}else{
				//编码名称重复校验
				int count = matCommonMapper.existsCodeNameByAdd(map);
				if(count > 0){
					retMap.put("state", "false");
					retMap.put("error", "代码或名称重复！");
					return retMap;
				}
				
				//自动生成排序号
				Integer max_sort = matCommonMapper.getMaxSortCodeByTable(map);
				map.put("sort_code", (max_sort == null ? 0 : max_sort) + 10);
				//新增
				matEvaTargetMapper.addMatEvaScale(map);
				
				//返回排序号
				retMap.put("sort_code", map.get("sort_code"));
			}
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	//标准标度删除
	@Override
	public Map<String, Object> deleteMatEvaScale(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("codes") == null || "".equals(map.get("codes").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择标度代码！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String code: map.get("codes").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("scale_code", code);
				list.add(tmpMap);
			}
			
			//删除
			matEvaTargetMapper.deleteMatEvaScale(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	
	//指标查询
	@Override
	public String queryMatEvaTargetList(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		//最顶级的TOP转换成“”方便模糊查询
		if(map.get("target_type_code") != null && "TOP".equals(map.get("target_type_code"))){
			map.put("target_type_code", "");
		}
		
		List<Map<String, Object>> list = matEvaTargetMapper.queryMatEvaTargetList(map);
		
		return ChdJson.toJson(list);
	}
	//指标保存
	@Override
	public Map<String, Object> saveMatEvaTarget(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("user_id", SessionManager.getUserId());

			if(map.get("target_type_code") == null || "".equals(map.get("target_type_code").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择指标类别然后再新增！");
				return retMap;
			}
			if(map.get("target_code") == null || "".equals(map.get("target_code").toString())){
				retMap.put("state", "false");
				retMap.put("error", "指标编号为空！");
				return retMap;
			}
			if(map.get("target_name") == null || "".equals(map.get("target_name").toString())){
				retMap.put("state", "false");
				retMap.put("error", "指标名称为空！");
				return retMap;
			}
			if(map.get("eva_method") == null || "".equals(map.get("eva_method").toString())){
				retMap.put("state", "false");
				retMap.put("error", "评价方式为空！");
				return retMap;
			}
			
			map.put("spell_code", StringTool.toPinyinShouZiMu(map.get("target_name").toString()));
			map.put("wbx_code", StringTool.toWuBi(map.get("target_name").toString()));

			//用于编码校验
			map.put("table_code", "mat_eva_target");
			map.put("col_code", "target_code");
			map.put("code_val", map.get("target_code"));
			map.put("col_name", "target_name");
			map.put("name_val", map.get("target_name"));
			
			if(map.get("is_update") != null && "1".equals(map.get("is_update").toString())){
				//名称重复校验
				int count = matCommonMapper.existsNameByUpdate(map);
				if(count > 0){
					retMap.put("state", "false");
					retMap.put("error", "名称重复！");
					return retMap;
				}
				//修改
				matEvaTargetMapper.updateMatEvaTarget(map);
			}else{
				//编码名称重复校验
				int count = matCommonMapper.existsCodeNameByAdd(map);
				if(count > 0){
					retMap.put("state", "false");
					retMap.put("error", "代码或名称重复！");
					return retMap;
				}
				
				//自动生成排序号
				map.put("whereSql", " AND target_type_code = #{target_type_code,jdbcType=VARCHAR} ");
				Integer max_sort = matCommonMapper.getMaxSortCodeByTable(map);
				map.put("sort_code", (max_sort == null ? 0 : max_sort) + 10);
				//新增
				matEvaTargetMapper.addMatEvaTarget(map);
				//导入标准标度
				matEvaTargetMapper.addMatEvaTargetScaleByBZ(map);
				
				//返回排序号
				retMap.put("sort_code", map.get("sort_code"));
			}
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	//指标删除
	@Override
	public Map<String, Object> deleteMatEvaTarget(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("user_id", SessionManager.getUserId());
			
			if(map.get("codes") == null || "".equals(map.get("codes").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择指标！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String code: map.get("codes").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("target_code", code);
				list.add(tmpMap);
			}

			//判断引用
			String reStr="";
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("dict_code", "mat_eva_target".toUpperCase());
			checkMap.put("group_id", map.get("group_id"));
			checkMap.put("hos_id", map.get("hos_id"));
			checkMap.put("copy_code", map.get("copy_code"));
			checkMap.put("dict_id_str", map.get("codes"));
			checkMap.put("acc_year", "");
			checkMap.put("p_flag", "1");
			sysFunUtilMapper.querySysDictDelCheck(checkMap);
			
			if(checkMap.get("reNote")!=null) {
				reStr += checkMap.get("reNote");
			}
			
			if(reStr!=null && !reStr.equals("")){
				retMap.put("state", "false");
				retMap.put("error", "删除失败，选择的指标类别被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。");
				return retMap;
			}

			//删除指标扣分项
			matEvaTargetMapper.deleteMatEvaTargetDeductBatch(map, list);
			//删除指标标度
			matEvaTargetMapper.deleteMatEvaTargetScaleBatch(map, list);
			//修改指标变革表为停用
			matEvaTargetMapper.updateMatEvaTargetScaleDBatchByStop(map, list);
			//删除指标
			matEvaTargetMapper.deleteMatEvaTarget(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	//指标导入
	@Override
	public Map<String, Object> addMatEvaTargetByImp(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			List<Map<String, Object>> targetList = new ArrayList<Map<String,Object>>();
			String group_id = SessionManager.getGroupId();
			String hos_id = SessionManager.getHosId();
			String copy_code = SessionManager.getCopyCode();
			String user_id = SessionManager.getUserId();
			
			Map<String, Object> seachMap = new HashMap<String, Object>();
			seachMap.put("group_id", group_id);
			seachMap.put("hos_id", hos_id);
			seachMap.put("copy_code", copy_code);
			seachMap.put("user_id", user_id);
			
			//获取指标类别
			List<Map<String, Object>> matEvaTargetTypeList = matEvaTargetMapper.queryMatEvaTargetTypeForImport(seachMap);
			Map<String, String> matEvaTargetTypeMap = new HashMap<String, String>();
			for(Map<String, Object> matEvaTargetType : matEvaTargetTypeList){
				matEvaTargetTypeMap.put(matEvaTargetType.get("TARGET_TYPE_CODE").toString(), matEvaTargetType.get("TARGET_TYPE_NAME").toString());
			}
			
			//获取指标
			List<Map<String, Object>> matEvaTargetList = matEvaTargetMapper.queryMatEvaTargetForImport(seachMap);
			Map<String, String> matEvaTargetMap = new HashMap<String, String>();
			for(Map<String, Object> matEvaTarget : matEvaTargetList){
				matEvaTargetMap.put(matEvaTarget.get("TARGET_CODE").toString(), matEvaTarget.get("TARGET_CODE").toString());
				matEvaTargetMap.put(matEvaTarget.get("TARGET_NAME").toString(), matEvaTarget.get("TARGET_NAME").toString());
			}
			
			//指标属性
			Map<String, String> attrMap = new HashMap<String, String>();
			attrMap.put("1", "1");
			attrMap.put("2", "2");
			attrMap.put("定性", "1");
			attrMap.put("定量", "2");
			
			//指标类型
			Map<String, String> kindMap = new HashMap<String, String>();
			kindMap.put("1", "1");
			kindMap.put("2", "2");
			kindMap.put("主观", "1");
			kindMap.put("客观", "2");
			
			//评价方法
			Map<String, String> methodMap = new HashMap<String, String>();
			methodMap.put("1", "1");
			methodMap.put("2", "2");
			methodMap.put("标度", "1");
			methodMap.put("打分", "2");
			
			//是否停用
			Map<String, String> isStopMap = new HashMap<String, String>();
			isStopMap.put("0", "0");
			isStopMap.put("1", "1");
			isStopMap.put("否", "0");
			isStopMap.put("是", "1");
			
			//解析前台数据
			if(map.get("data") == null || "".equals(map.get("data"))){
				retMap.put("state", "false");
				retMap.put("error", "EXCEL的数据太多，请减少条数再重新导入！");
				return retMap;
			}
			String data = map.get("data").toString();
			List<Map<String, List<String>>> dataList = SpreadTableJSUtil.toListMap(data, 1);
			if(dataList==null || dataList.size()==0){
				retMap.put("state", "false");
				retMap.put("error", "EXCEL中没有数据！");
				return retMap;
			}
			List<String> rowList=null;
			Map<String, Object> targetMap = null;
			for(Map<String, List<String>> dataMap : dataList){
				targetMap = new HashMap<String, Object>();
				
				/**校验指标编码************begin*****************/
				rowList = dataMap.get("指标编码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，指标编码为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				if(matEvaTargetMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，指标编码已存在！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				matEvaTargetMap.put(rowList.get(1), rowList.get(1));
				targetMap.put("target_code", rowList.get(1));
				/**校验指标编码************end*******************/
				
				/**校验指标名称************begin*****************/
				rowList = dataMap.get("指标名称");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，指标名称为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				if(matEvaTargetMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，指标名称已存在！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				matEvaTargetMap.put(rowList.get(1), rowList.get(1));
				targetMap.put("target_name", rowList.get(1));
				/**校验指标名称************end*******************/
				
				/**校验指标分类编码************begin*****************/
				rowList = dataMap.get("指标分类编码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，指标分类编码为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				if(!matEvaTargetTypeMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，指标分类编码不存在！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				targetMap.put("target_type_code", rowList.get(1));
				/**校验指标分类编码************end*******************/
				
				/**校验评价方法************begin*****************/
				rowList = dataMap.get("评价方法");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，评价方法为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				if(!methodMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，评价方法不合法！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				targetMap.put("eva_method", methodMap.get(rowList.get(1)));
				/**校验评价方法************end*******************/
				
				/**校验指标类型************begin*****************/
				rowList = dataMap.get("指标类型");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，指标类型为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				if(!kindMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，指标类型不合法！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				targetMap.put("target_kind", kindMap.get(rowList.get(1)));
				/**校验评价方法************end*******************/
				
				/**校验指标属性************begin*****************/
				rowList = dataMap.get("指标属性");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，指标属性为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				if(!attrMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，指标属性不合法！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				targetMap.put("target_attr", attrMap.get(rowList.get(1)));
				/**校验评价方法************end*******************/
				
				/**校验指标解释******begin*****************/
				rowList = dataMap.get("指标解释");
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
					targetMap.put("target_explain", rowList.get(1));
				}else{
					targetMap.put("target_explain", "");
				}
				/**校验指标解释******end*******************/
				/**校验考核内容******begin*****************/
				rowList = dataMap.get("考核内容");
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
					targetMap.put("eva_content", rowList.get(1));
				}else{
					targetMap.put("eva_content", "");
				}
				/**校验考核内容******end*******************/
				/**校验评分原则******begin*****************/
				rowList = dataMap.get("评分原则");
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
					targetMap.put("eva_principle", rowList.get(1));
				}else{
					targetMap.put("eva_principle", "");
				}
				/**校验评分原则******end*******************/
				/**校验排序号******begin*****************/
				rowList = dataMap.get("排序号");
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
					targetMap.put("sort_code", rowList.get(1));
				}else{
					targetMap.put("sort_code", "0");
				}
				/**校验排序号******end*******************/
				/**校验是否停用******begin*****************/
				rowList = dataMap.get("是否停用");
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
					if(!isStopMap.containsKey(rowList.get(1))){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，是否停用不合法！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					targetMap.put("is_stop", isStopMap.get(rowList.get(1)));
				}else{
					targetMap.put("is_stop", "0");
				}
				/**校验是否停用******end*******************/
				
				targetMap.put("spell_code", StringTool.toPinyinShouZiMu(targetMap.get("target_name").toString()));
				targetMap.put("wbx_code", StringTool.toWuBi(targetMap.get("target_name").toString()));
				targetMap.put("note", "");
				
				targetList.add(targetMap);
			}
			
			//导入指标
			matEvaTargetMapper.addMatEvaTargetByImp(seachMap, targetList);
			//查询标准标度
			List<Map<String, Object>> scaleList = matEvaTargetMapper.queryMatEvaScaleList(seachMap);
			if(scaleList != null && scaleList.size() > 0){
				//批量插入指标标度
				matEvaTargetMapper.addMatEvaTargetScaleBatch(seachMap, targetList, scaleList);
			}
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	
	//指标标度查询
	@Override
	public String queryMatEvaTargetScaleList(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> list = matEvaTargetMapper.queryMatEvaTargetScaleList(map);
		
		return ChdJson.toJson(list);
	}
	//指标标度保存
	@Override
	public Map<String, Object> saveMatEvaTargetScale(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("user_id", SessionManager.getUserId());
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析JSON
			JSONArray json = JSONArray.parseArray((String)map.get("allData"));
			Iterator<Object> it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				if(jsonObj.get("scale_code") == null || "".equals(jsonObj.getString("scale_code"))){
					//空行直接跳过
					continue;
				}
				tmpMap = new HashMap<String, Object>();
				tmpMap.put("scale_code", jsonObj.getString("scale_code"));
				tmpMap.put("scale_name", jsonObj.getString("scale_name"));
				tmpMap.put("scale_content", jsonObj.getString("scale_content"));
				tmpMap.put("scale_point", jsonObj.getFloat("scale_point"));
				tmpMap.put("high_point", jsonObj.getFloat("high_point"));
				tmpMap.put("low_point", jsonObj.getFloat("low_point"));
				tmpMap.put("is_stop", jsonObj.getInteger("is_stop"));
				tmpMap.put("sort_code", jsonObj.getString("sort_code"));
				tmpMap.put("note", jsonObj.getString("note"));
				tmpMap.put("spell_code", StringTool.toPinyinShouZiMu(jsonObj.getString("scale_name")));
				tmpMap.put("wbx_code", StringTool.toWuBi(jsonObj.getString("scale_name")));
				
				list.add(tmpMap);
			}

			//删除
			matEvaTargetMapper.deleteMatEvaTargetScale(map);
			//保存
			matEvaTargetMapper.addMatEvaTargetScale(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	//指标标度删除
	@Override
	public Map<String, Object> deleteMatEvaTargetScale(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("codes") == null || "".equals(map.get("codes").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择指标标度！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String code: map.get("codes").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("scale_code", code);
				list.add(tmpMap);
			}
			
			//删除
			//matEvaTargetMapper.deleteMatEvaTargetScale(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	//指标标度引用标准标度
	@Override
	public Map<String, Object> addMatEvaTargetScaleByBZ(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("user_id", SessionManager.getUserId());
			
			if(map.get("target_code") == null || "".equals(map.get("target_code"))){
				retMap.put("state", "false");
				retMap.put("error", "指标不存在！");
				return retMap;
			}
			
			//删除
			matEvaTargetMapper.deleteMatEvaTargetScale(map);
			//导入标准标度
			matEvaTargetMapper.addMatEvaTargetScaleByBZ(map);

			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	//批量设置指标标度保存
	@Override
	public Map<String, Object> saveMatEvaTargetScaleBatch(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("user_id", SessionManager.getUserId());
			
			List<Map<String, Object>> targetList = new ArrayList<Map<String,Object>>();
			Map<String, Object> targetMap = null;
			//解析为List
			for ( String code: map.get("codes").toString().split(",")) {
				targetMap = new HashMap<String, Object>();
				//表的主键
				targetMap.put("target_code", code);
				targetList.add(targetMap);
			}
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析JSON
			JSONArray json = JSONArray.parseArray((String)map.get("allData"));
			Iterator<Object> it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				if(jsonObj.get("scale_code") == null || "".equals(jsonObj.getString("scale_code"))){
					//空行直接跳过
					continue;
				}
				tmpMap = new HashMap<String, Object>();
				tmpMap.put("scale_code", jsonObj.getString("scale_code"));
				tmpMap.put("scale_name", jsonObj.getString("scale_name"));
				tmpMap.put("scale_content", jsonObj.getString("scale_content"));
				tmpMap.put("scale_point", jsonObj.getFloat("scale_point"));
				tmpMap.put("high_point", jsonObj.getFloat("high_point"));
				tmpMap.put("low_point", jsonObj.getFloat("low_point"));
				tmpMap.put("is_stop", jsonObj.getInteger("is_stop"));
				tmpMap.put("sort_code", jsonObj.getString("sort_code"));
				tmpMap.put("note", jsonObj.getString("note"));
				tmpMap.put("spell_code", StringTool.toPinyinShouZiMu(jsonObj.getString("scale_name")));
				tmpMap.put("wbx_code", StringTool.toWuBi(jsonObj.getString("scale_name")));
				
				list.add(tmpMap);
			}

			//批量删除
			matEvaTargetMapper.deleteMatEvaTargetScaleBatch(map, targetList);
			//批量保存
			matEvaTargetMapper.addMatEvaTargetScaleBatch(map, targetList, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	
	//指标扣分项查询
	@Override
	public String queryMatEvaTargetDeductList(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> list = matEvaTargetMapper.queryMatEvaTargetDeductList(map);
		
		return ChdJson.toJson(list);
	}
	//指标扣分项保存
	@Override
	public Map<String, Object> saveMatEvaTargetDeduct(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());

			if(map.get("target_code") == null || "".equals(map.get("target_code").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择指标然后再新增！");
				return retMap;
			}
			if(map.get("deduct_code") == null || "".equals(map.get("deduct_code").toString())){
				retMap.put("state", "false");
				retMap.put("error", "扣分项代码为空！");
				return retMap;
			}
			if(map.get("deduct_name") == null || "".equals(map.get("deduct_name").toString())){
				retMap.put("state", "false");
				retMap.put("error", "扣分项名称为空！");
				return retMap;
			}
			
			map.put("spell_code", StringTool.toPinyinShouZiMu(map.get("deduct_name").toString()));
			map.put("wbx_code", StringTool.toWuBi(map.get("deduct_name").toString()));

			//用于编码校验
			map.put("table_code", "mat_eva_target_deduct");
			map.put("col_code", "deduct_code");
			map.put("code_val", map.get("deduct_code"));
			map.put("col_name", "deduct_name");
			map.put("name_val", map.get("deduct_name"));
			map.put("whereSql", map.get(" AND target_code = #{target_code,jdbcType=VARCHAR} "));
			
			if(map.get("is_update") != null && "1".equals(map.get("is_update").toString())){
				//名称重复校验
				int count = matCommonMapper.existsNameByUpdate(map);
				if(count > 0){
					retMap.put("state", "false");
					retMap.put("error", "名称重复！");
					return retMap;
				}
				//修改
				matEvaTargetMapper.updateMatEvaTargetDeduct(map);
			}else{
				//编码名称重复校验
				int count = matCommonMapper.existsCodeNameByAdd(map);
				if(count > 0){
					retMap.put("state", "false");
					retMap.put("error", "代码或名称重复！");
					return retMap;
				}
				
				//自动生成排序号
				Integer max_sort = matCommonMapper.getMaxSortCodeByTable(map);
				map.put("sort_code", (max_sort == null ? 0 : max_sort) + 10);
				//新增
				matEvaTargetMapper.addMatEvaTargetDeduct(map);
				
				//返回排序号
				retMap.put("sort_code", map.get("sort_code"));
			}
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	//指标扣分项删除
	@Override
	public Map<String, Object> deleteMatEvaTargetDeduct(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("codes") == null || "".equals(map.get("codes").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择指标扣分项！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String code: map.get("codes").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("deduct_code", code);
				list.add(tmpMap);
			}
			
			//判断是否被引用
			Integer count = matEvaTargetMapper.existsMatEvaSupDeductByTargetDeduct(map, list);
			if(count != null && count > 0){
				retMap.put("state", "false");
				retMap.put("error", "所选指标扣分项已被供应商评价引用！");
				return retMap;
			}
			
			//删除
			matEvaTargetMapper.deleteMatEvaTargetDeduct(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
}
