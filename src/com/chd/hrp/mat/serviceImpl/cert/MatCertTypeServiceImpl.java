package com.chd.hrp.mat.serviceImpl.cert;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.cert.MatCertTypeMapper;
import com.chd.hrp.mat.service.cert.MatCertTypeService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;

@Service("matCertTypeService")
public class MatCertTypeServiceImpl implements MatCertTypeService{

	private static Logger logger = Logger.getLogger(MatCertTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matCertTypeMapper")
	private final MatCertTypeMapper matCertTypeMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	//证件类型查询
	@Override
	public String queryMatCertTypeList(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> list = matCertTypeMapper.queryMatCertTypeList(map);
		
		return ChdJson.toJson(list);
	}
	//证件类型保存
	@Override
	public Map<String, Object> saveMatCertType(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("cert_type_code") == null || "".equals(map.get("cert_type_code").toString())){
				retMap.put("state", "false");
				retMap.put("error", "证件类型编码为空！");
				return retMap;
			}
			if(map.get("cert_type_name") == null || "".equals(map.get("cert_type_name").toString())){
				retMap.put("state", "false");
				retMap.put("error", "证件类型名称为空！");
				return retMap;
			}
			
			map.put("spell_code", StringTool.toPinyinShouZiMu(map.get("cert_type_name").toString()));
			map.put("wbx_code", StringTool.toWuBi(map.get("cert_type_name").toString()));
			map.put("oper_name", SessionManager.getUserName());
			map.put("oper_date", new Date());

			//用于编码校验
			map.put("table_code", "mat_cert_type");
			map.put("col_code", "cert_type_code");
			map.put("code_val", map.get("cert_type_code"));
			map.put("col_name", "cert_type_name");
			map.put("name_val", map.get("cert_type_name"));
			
			if(map.get("is_update") != null && "1".equals(map.get("is_update").toString())){
				//名称重复校验
				int count = matCommonMapper.existsNameByUpdate(map);
				if(count > 0){
					retMap.put("state", "false");
					retMap.put("error", "名称重复！");
					return retMap;
				}
				//修改
				matCertTypeMapper.updateMatCertType(map);
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
				matCertTypeMapper.addMatCertType(map);
				
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
	//证件类型删除
	@Override
	public Map<String, Object> deleteMatCertType(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("codes") == null || "".equals(map.get("codes").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择证件类型！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String code: map.get("codes").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("cert_type_code", code);
				list.add(tmpMap);
			}

			//判断引用
			String reStr="";
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("dict_code", "mat_cert_type".toUpperCase());
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
				retMap.put("error", "删除失败，选择的证件类型被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。");
				return retMap;
			}
			
			//删除
			matCertTypeMapper.deleteMatCertType(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	//证件类型启用
	@Override
	public Map<String, Object> updateMatCertTypeToStart(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("codes") == null || "".equals(map.get("codes").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择证件类型！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String code: map.get("codes").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("cert_type_code", code);
				list.add(tmpMap);
			}
			
			map.put("is_stop", 0);
			map.put("oper_name", SessionManager.getUserName());
			map.put("oper_date", new Date());
			
			//启用
			matCertTypeMapper.updateMatCertTypeIsStop(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	//证件类型停用
	@Override
	public Map<String, Object> updateMatCertTypeToStop(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("codes") == null || "".equals(map.get("codes").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择证件类型！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String code: map.get("codes").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("cert_type_code", code);
				list.add(tmpMap);
			}
			
			map.put("is_stop", 1);
			map.put("oper_name", SessionManager.getUserName());
			map.put("oper_date", new Date());
			
			//停用
			matCertTypeMapper.updateMatCertTypeIsStop(map, list);
			
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
	public Map<String, Object> addMatCertTypeByImp(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			List<Map<String, Object>> certTypeList = new ArrayList<Map<String,Object>>();
			String group_id = SessionManager.getGroupId();
			String hos_id = SessionManager.getHosId();
			String copy_code = SessionManager.getCopyCode();
			String user_id = SessionManager.getUserId();
			
			Map<String, Object> seachMap = new HashMap<String, Object>();
			seachMap.put("group_id", group_id);
			seachMap.put("hos_id", hos_id);
			seachMap.put("copy_code", copy_code);
			seachMap.put("user_id", user_id);
			seachMap.put("oper_name", SessionManager.getUserName());
			seachMap.put("oper_date", new Date());
			
			//获取证件类型
			List<Map<String, Object>> matCertTypeList = matCertTypeMapper.queryMatCertTypeList(seachMap);
			Map<String, String> matCertTypeMap = new HashMap<String, String>();
			for(Map<String, Object> matCertType : matCertTypeList){
				matCertTypeMap.put(matCertType.get("cert_type_code").toString(), matCertType.get("cert_type_code").toString());
				matCertTypeMap.put(matCertType.get("cert_type_name").toString(), matCertType.get("cert_type_name").toString());
			}
			
			//获取证件类别
			List<Map<String, Object>> matCertKindList = matCertTypeMapper.queryMatCertTypeKindListForImport();
			Map<String, String> matCertKindMap = new HashMap<String, String>();
			for(Map<String, Object> matCertKind : matCertKindList){
				matCertKindMap.put(matCertKind.get("CERT_KIND_CODE").toString(), matCertKind.get("CERT_KIND_CODE").toString());
				matCertKindMap.put(matCertKind.get("CERT_KIND_NAME").toString(), matCertKind.get("CERT_KIND_NAME").toString());
			}
			
			//是否
			Map<String, String> yesNoMap = new HashMap<String, String>();
			yesNoMap.put("0", "0");
			yesNoMap.put("1", "1");
			yesNoMap.put("否", "0");
			yesNoMap.put("是", "1");
			
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
			Map<String, Object> certTypeMap = null;
			for(Map<String, List<String>> dataMap : dataList){
				certTypeMap = new HashMap<String, Object>();
				
				/**校验证件类型编码************begin*****************/
				rowList = dataMap.get("证件类型编码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，证件类型编码为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				if(matCertTypeMap.containsKey(rowList.get(1))){
					retMap.put("state", "false"); 
					retMap.put("warn", rowList.get(0) + "，证件类型编码已存在！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				matCertTypeMap.put(rowList.get(1), rowList.get(1));
				certTypeMap.put("cert_type_code", rowList.get(1));
				/**校验指标编码************end*******************/
				
				/**校验证件类型名称************begin*****************/
				rowList = dataMap.get("证件类型名称");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，证件类型名称为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				if(matCertTypeMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，证件类型名称已存在！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				matCertTypeMap.put(rowList.get(1), rowList.get(1));
				certTypeMap.put("cert_type_name", rowList.get(1));
				/**校验证件类型名称************end*******************/
				
				/**校验证件类别************begin*****************/
				rowList = dataMap.get("证件类别");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，证件类别为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				if(!matCertKindMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，证件类别不存在！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				certTypeMap.put("cert_kind_code", matCertKindMap.get(rowList.get(1)));
				/**校验证件类别************end*******************/
				
				/**校验排序号******begin*****************/
				rowList = dataMap.get("排序号");
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
					certTypeMap.put("sort_code", rowList.get(1));
				}else{
					certTypeMap.put("sort_code", "0");
				}
				/**校验排序号******end*******************/
				
				/**校验是否需维护经营范围******begin*****************/
				rowList = dataMap.get("是否需维护经营范围");
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
					if(!yesNoMap.containsKey(rowList.get(1))){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，是否需维护经营范围不合法！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					certTypeMap.put("is_cert_busi", yesNoMap.get(rowList.get(1)));
				}else{
					certTypeMap.put("is_cert_busi", "0");
				}
				/**校验是否需维护经营范围******end*******************/
				
				/**校验是否需维护证件名称******begin*****************/
				rowList = dataMap.get("是否需维护证件名称");
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
					if(!yesNoMap.containsKey(rowList.get(1))){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，是否需维护证件名称不合法！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					certTypeMap.put("is_cert_name", yesNoMap.get(rowList.get(1)));
				}else{
					certTypeMap.put("is_cert_name", "0");
				}
				/**校验是否需维护证件名称******end*******************/
				
				/**校验是否停用******begin*****************/
				rowList = dataMap.get("是否停用");
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
					if(!yesNoMap.containsKey(rowList.get(1))){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，是否停用不合法！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					certTypeMap.put("is_stop", yesNoMap.get(rowList.get(1)));
				}else{
					certTypeMap.put("is_stop", "0");
				}
				/**校验是否停用******end*******************/
				
				certTypeMap.put("spell_code", StringTool.toPinyinShouZiMu(certTypeMap.get("target_name").toString()));
				certTypeMap.put("wbx_code", StringTool.toWuBi(certTypeMap.get("target_name").toString()));
				certTypeMap.put("note", "");
				
				certTypeList.add(certTypeMap);
			}
			
			//导入指标
			matCertTypeMapper.addMatCertTypeByImp(seachMap, certTypeList);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
}
