package com.chd.hrp.mat.serviceImpl.cert;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.cert.MatProdCertMapper;
import com.chd.hrp.mat.service.cert.MatCertFileService;
import com.chd.hrp.mat.service.cert.MatProdCertService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

@Service("matProdCertService")
public class MatProdCertServiceImpl implements MatProdCertService{

	private static Logger logger = Logger.getLogger(MatProdCertServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matProdCertMapper")
	private final MatProdCertMapper matProdCertMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	@Resource(name = "matCertFileService")
	private final MatCertFileService matCertFileService = null;
	
	//产品注册证列表查询
	@Override
	public String queryMatProdCertList(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());

    	//处理证件日期
    	if(map.get("start_date") != null && !"".equals(map.get("start_date"))){
    		map.put("start_date", DateUtil.stringToDate(map.get("start_date").toString(), "yyyy-MM-dd"));
    	}
    	if(map.get("end_date") != null && !"".equals(map.get("end_date"))){
    		map.put("end_date", DateUtil.stringToDate(map.get("end_date").toString(), "yyyy-MM-dd"));
    	}
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage"); 
    	
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matProdCertMapper.queryMatProdCertList(map);
			return ChdJson.toJson(list);	
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matProdCertMapper.queryMatProdCertList(map, rowBounds);
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	//产品注册证查询
	@Override
	public Map<String, Object> queryMatProdCertById(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		Map<String, Object> certMap = matProdCertMapper.queryMatProdCertById(map);
		if(certMap == null || certMap.get("cert_id") == null || "".equals(certMap.get("cert_id").toString())){
			certMap = new HashMap<String, Object>();
			certMap.put("state", "false");
			certMap.put("error", "产品注册证已被删除");
			return certMap;
		}
		//处理证件日期
    	if(certMap.get("start_date") != null && !"".equals(certMap.get("start_date"))){
    		certMap.put("start_date", DateUtil.dateToString((Date)certMap.get("start_date"), "yyyy-MM-dd"));
    	}
    	if(certMap.get("end_date") != null && !"".equals(certMap.get("end_date"))){
    		certMap.put("end_date", DateUtil.dateToString((Date)certMap.get("end_date"), "yyyy-MM-dd"));
    	}
		try {
			//获取文件
			List<Map<String, Object>> certList = matCertFileService.queryMatCertFileList("mat_prod_cert_file", "cert_id", map.get("cert_id").toString());
		
			String []cert_files = new String[certList.size()];
			for(int i = 0; i < certList.size(); i++){
				cert_files[i] = certList.get(i).get("file_path").toString() + certList.get(i).get("file_name").toString();
			}
			certMap.put("cert_files", cert_files);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			certMap.put("cert_files", "获取文件失败！");
		}
		
		return certMap;
	}
	
	//产品注册证保存
	@Override
	public Map<String, Object> saveMatProdCert(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());

			if(map.get("cert_type_code") == null || "".equals(map.get("cert_type_code").toString())){
				retMap.put("state", "false");
				retMap.put("error", "证件类型不能为空！");
				return retMap;
			}
			if(map.get("cert_code") == null || "".equals(map.get("cert_code").toString())){
				retMap.put("state", "false");
				retMap.put("error", "产品注册证编码不能为空！");
				return retMap;
			}
			if(map.get("prod_name") == null || "".equals(map.get("prod_name").toString())){
				retMap.put("state", "false");
				retMap.put("error", "产品名称不能为空！");
				return retMap;
			}
        	//处理证件日期
        	if(map.get("start_date") == null || "".equals(map.get("start_date"))){
				retMap.put("state", "false");
				retMap.put("error", "证件开始日期不能为空！");
				return retMap;
        	}
        	String cert_date = map.get("start_date").toString()+"至";
        	map.put("start_date", DateUtil.stringToDate(map.get("start_date").toString(), "yyyy-MM-dd"));
        	if(map.get("is_long") != null && "1".equals(map.get("is_long").toString())){
        		cert_date += "永久有效";
        		map.put("end_date", null);
        	}else{
        		if(map.get("end_date") == null || "".equals(map.get("end_date"))){
    				retMap.put("state", "false");
    				retMap.put("error", "证件结束日期不能为空！");
    				return retMap;
            	}
        		cert_date += map.get("end_date").toString();
        		map.put("end_date", DateUtil.stringToDate(map.get("end_date").toString(), "yyyy-MM-dd"));
        	}
        	map.put("cert_date", cert_date);

        	//判断新老证标识
        	if(map.get("new_cert_id") != null && !"".equals(map.get("new_cert_id").toString())){
        		map.put("is_new", 1);
        	}else{
        		map.put("is_new", 0);
        	}
			map.put("oper_name", SessionManager.getUserName());
			map.put("oper_date", new Date());

			//文件
        	MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
        	List<MultipartFile> certFileList = multiRequest.getFiles("cert_files");
        	
        	//获取页面删除的文件
        	String old_cert_files = map.get("old_cert_files") == null ? "" : map.get("old_cert_files").toString();
        	String new_cert_files = map.get("new_cert_files") == null ? "" : map.get("new_cert_files").toString();
        	
        	String cert_id = "";
			
			if(map.get("cert_id") != null && !"".equals(map.get("cert_id").toString())){
				cert_id = map.get("cert_id").toString();
				//修改
				matProdCertMapper.updateMatProdCert(map);
				//修改对应关系中证件编号
				matProdCertMapper.updateMatProdCertInvForCertCode(map);
			}else{
				//生成主键
				cert_id = UUIDLong.absStringUUID();
				map.put("cert_id", cert_id);
				map.put("check_state", 1);  //默认未审核
				map.put("authent_state", 1);  //默认未认证
				map.put("com_from", 1);
				//新增
				matProdCertMapper.addMatProdCert(map);
				//新证需要复制老证的材料信息并更新老证的信息
				if(map.get("old_cert_id") != null && !"".equals(map.get("old_cert_id").toString())){
					map.put("changer", SessionManager.getUserName());
					map.put("change_date", new Date());
					map.put("change_note", "过期换证，原因：" + map.get("change_note"));
					//更新老证的信息
					matProdCertMapper.updateMatProdCertToOld(map);
					//复制老证的材料信息
					matProdCertMapper.addMatProdCertInvByCopyOld(map);
				}
			}
			
			//文件信息
			String cert_files = matCertFileService.saveMatCertFile("mat_prod_cert_file", "cert_id", cert_id, old_cert_files, new_cert_files, certFileList);

			retMap.put("cert_id", cert_id);
			retMap.put("cert_files", cert_files);
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	
	//产品注册证删除
	@Override
	public Map<String, Object> deleteMatProdCert(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("ids") == null || "".equals(map.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择产品注册证！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			List<String> id_list = new ArrayList<String>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String id: map.get("ids").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("cert_id", id);
				list.add(tmpMap);
				id_list.add(id);
			}
			
			//判断是否已认证
			Integer is_flag = matProdCertMapper.notExistsMatProdCertByState(map, "authent_state", 1, list);
			if(is_flag != null && is_flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "删除失败，存在已认证的授权书！");
				return retMap;
			}

			//判断引用
			String reStr="";
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("dict_code", "mat_cert_type".toUpperCase());
			checkMap.put("group_id", map.get("group_id"));
			checkMap.put("hos_id", map.get("hos_id"));
			checkMap.put("copy_code", map.get("copy_code"));
			checkMap.put("dict_id_str", map.get("ids"));
			checkMap.put("acc_year", "");
			checkMap.put("p_flag", "1");
			sysFunUtilMapper.querySysDictDelCheck(checkMap);
			
			if(checkMap.get("reNote")!=null) {
				reStr += checkMap.get("reNote");
			}
			
			if(reStr!=null && !reStr.equals("")){
				retMap.put("state", "false");
				retMap.put("error", "删除失败，选择的产品注册证被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。");
				return retMap;
			}

			//删除产品注册证
			matProdCertMapper.deleteMatProdCert(map, list);
			//删除文件
			matCertFileService.deleteMatCertFile("mat_prod_cert_file", "cert_id", id_list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	//产品注册证认证
	@Override
	public Map<String, Object> updateMatProdCertToAuthent(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("ids") == null || "".equals(map.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择产品注册证！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String id: map.get("ids").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("cert_id", id);
				list.add(tmpMap);
			}

			//判断是否已认证
			Integer is_flag = matProdCertMapper.notExistsMatProdCertByState(map, "authent_state", 1, list);
			if(is_flag != null && is_flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "认证失败，存在已认证的授权书！");
				return retMap;
			}
			
			map.put("authent_state", 2);
			map.put("authenter", SessionManager.getUserName());
			map.put("authent_date", new Date());
			
			//认证
			matProdCertMapper.updateMatProdCertAuthentState(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	//产品注册证取消认证
	@Override
	public Map<String, Object> updateMatProdCertToUnAuthent(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("ids") == null || "".equals(map.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择产品注册证！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String id: map.get("ids").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("cert_id", id);
				list.add(tmpMap);
			}

			//判断是否已审核
			Integer is_flag = matProdCertMapper.notExistsMatProdCertByState(map, "check_state", 1, list);
			if(is_flag != null && is_flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "取消认证失败，存在已审核的授权书！");
				return retMap;
			}

			//判断是否已认证
			is_flag = 0;
			is_flag = matProdCertMapper.notExistsMatProdCertByState(map, "authent_state", 2, list);
			if(is_flag != null && is_flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "取消认证失败，存在未认证的授权书！");
				return retMap;
			}
			
			map.put("authent_state", 1);
			map.put("authenter", SessionManager.getUserName());
			map.put("authent_date", new Date());
			
			//取消认证
			matProdCertMapper.updateMatProdCertAuthentState(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	//产品注册证审核
	@Override
	public Map<String, Object> updateMatProdCertToCheck(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("ids") == null || "".equals(map.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择产品注册证！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String id: map.get("ids").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("cert_id", id);
				list.add(tmpMap);
			}

			//判断是否已认证
			Integer is_flag = matProdCertMapper.notExistsMatProdCertByState(map, "authent_state", 2, list);
			if(is_flag != null && is_flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "审核失败，存在未认证的授权书！");
				return retMap;
			}

			//判断是否已审核
			is_flag = 0;
			is_flag = matProdCertMapper.notExistsMatProdCertByState(map, "check_state", 1, list);
			if(is_flag != null && is_flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "审核失败，存在已审核的授权书！");
				return retMap;
			}
			
			map.put("check_state", 2);
			map.put("checker", SessionManager.getUserName());
			map.put("check_date", new Date());
			
			//审核
			matProdCertMapper.updateMatProdCertCheckState(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	//产品注册证消审
	@Override
	public Map<String, Object> updateMatProdCertToUnCheck(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("ids") == null || "".equals(map.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择产品注册证！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String id: map.get("ids").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("cert_id", id);
				list.add(tmpMap);
			}

			//判断是否已审核
			Integer is_flag = matProdCertMapper.notExistsMatProdCertByState(map, "check_state", 2, list);
			if(is_flag != null && is_flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "消审失败，存在未审核的授权书！");
				return retMap;
			}
			
			map.put("check_state", 1);
			map.put("checker", SessionManager.getUserName());
			map.put("check_date", new Date());
			
			//消审
			matProdCertMapper.updateMatProdCertCheckState(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	//产品注册证导入
	@Override
	public Map<String, Object> addMatProdCertByImp(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			List<Map<String, Object>> ProdCertList = new ArrayList<Map<String,Object>>();
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
			
			//获取产品注册证
			List<Map<String, Object>> matProdCertList = matProdCertMapper.queryMatProdCertList(seachMap);
			Map<String, String> matProdCertMap = new HashMap<String, String>();
			for(Map<String, Object> matProdCert : matProdCertList){
				matProdCertMap.put(matProdCert.get("cert_type_code").toString(), matProdCert.get("cert_type_code").toString());
				matProdCertMap.put(matProdCert.get("cert_type_name").toString(), matProdCert.get("cert_type_name").toString());
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
			Map<String, Object> ProdCertMap = null;
			for(Map<String, List<String>> dataMap : dataList){
				ProdCertMap = new HashMap<String, Object>();
				
				/**校验产品注册证编码************begin*****************/
				rowList = dataMap.get("产品注册证编码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，产品注册证编码为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				if(matProdCertMap.containsKey(rowList.get(1))){
					retMap.put("state", "false"); 
					retMap.put("warn", rowList.get(0) + "，产品注册证编码已存在！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				matProdCertMap.put(rowList.get(1), rowList.get(1));
				ProdCertMap.put("cert_type_code", rowList.get(1));
				/**校验指标编码************end*******************/
				
				/**校验产品注册证名称************begin*****************/
				rowList = dataMap.get("产品注册证名称");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，产品注册证名称为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				if(matProdCertMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，产品注册证名称已存在！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				matProdCertMap.put(rowList.get(1), rowList.get(1));
				ProdCertMap.put("cert_type_name", rowList.get(1));
				/**校验产品注册证名称************end*******************/
				
				/**校验排序号******begin*****************/
				rowList = dataMap.get("排序号");
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
					ProdCertMap.put("sort_code", rowList.get(1));
				}else{
					ProdCertMap.put("sort_code", "0");
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
					ProdCertMap.put("is_cert_busi", yesNoMap.get(rowList.get(1)));
				}else{
					ProdCertMap.put("is_cert_busi", "0");
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
					ProdCertMap.put("is_cert_name", yesNoMap.get(rowList.get(1)));
				}else{
					ProdCertMap.put("is_cert_name", "0");
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
					ProdCertMap.put("is_stop", yesNoMap.get(rowList.get(1)));
				}else{
					ProdCertMap.put("is_stop", "0");
				}
				/**校验是否停用******end*******************/
				
				ProdCertMap.put("spell_code", StringTool.toPinyinShouZiMu(ProdCertMap.get("target_name").toString()));
				ProdCertMap.put("wbx_code", StringTool.toWuBi(ProdCertMap.get("target_name").toString()));
				ProdCertMap.put("note", "");
				
				ProdCertList.add(ProdCertMap);
			}
			
			//导入指标
			matProdCertMapper.addMatProdCertByImp(seachMap, ProdCertList);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	
	//查询材料列表
	@Override
	public String queryMatInvList(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage"); 
    	
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matProdCertMapper.queryMatInvList(map);
			return ChdJson.toJson(list);	
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matProdCertMapper.queryMatInvList(map, rowBounds);
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	//查询关联材料
	@Override
	public String queryMatProdCertInvList(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());

		List<Map<String, Object>> list = matProdCertMapper.queryMatProdCertInvList(map);
		return ChdJson.toJson(list);	
	}
	
	//保存关联材料
	@Override
	public Map<String, Object> saveMatProdCertInv(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("user_id", SessionManager.getUserId());
			
			List<String> list = new ArrayList<String>();
			Map<String, Object> tmpMap = null;
			//解析JSON
			JSONArray json = JSONArray.parseArray((String)map.get("allData"));
			Iterator<Object> it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				if(jsonObj.get("inv_id") == null || "".equals(jsonObj.getString("inv_id"))){
					//空行直接跳过
					continue;
				}
				
				list.add(jsonObj.getString("inv_id"));
			}

			//删除
			matProdCertMapper.deleteMatProdCertInv(map);
			//保存
			if(list != null && list.size() > 0){
				matProdCertMapper.addMatProdCertInv(map, list);
			}
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}

	//选择新证查询
	@Override
	public String queryMatProdCertChooseList(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage"); 
    	
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matProdCertMapper.queryMatProdCertChooseList(map);
			return ChdJson.toJson(list);	
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matProdCertMapper.queryMatProdCertChooseList(map, rowBounds);
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	//换证
	@Override
	public Map<String, Object> updateMatProdCertToNewCert(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("new_cert_id") == null || "".equals(map.get("new_cert_id").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择新证！");
				return retMap;
			}
			
			map.put("changer", SessionManager.getUserName());
			map.put("change_date", new Date());
			map.put("change_note", "选择新证，原因：" + map.get("change_note"));
			
			//换证
			matProdCertMapper.updateMatProdCertToNewCert(map);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	//取消换证
	@Override
	public Map<String, Object> updateMatProdCertToUnNewCert(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("new_cert_id") == null || "".equals(map.get("new_cert_id").toString())){
				retMap.put("state", "false");
				retMap.put("error", "该证件没有新证！");
				return retMap;
			}

			map.put("changer", SessionManager.getUserName());
			map.put("change_date", new Date());
			map.put("change_note", "取消换证，原因：" + map.get("change_note"));
			
			//取消换证
			matProdCertMapper.updateMatProdCertToUnNewCert(map);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
}
