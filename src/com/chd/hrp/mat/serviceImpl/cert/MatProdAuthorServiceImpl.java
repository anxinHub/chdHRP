/**
* @Copyright: Copyright (c) 2017-9-13 
* @Company: 杭州亦童科技有限公司
*/
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
import org.nutz.json.Json;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.mat.dao.cert.MatProdAuthorMapper;
import com.chd.hrp.mat.service.cert.MatCertFileService;
import com.chd.hrp.mat.service.cert.MatProdAuthorService;
import com.github.pagehelper.PageInfo;

@Service("matProdAuthorService")
public class MatProdAuthorServiceImpl implements MatProdAuthorService {

	private static Logger logger = Logger.getLogger(MatProdAuthorServiceImpl.class);

	// 引入Service服务
	@Resource(name = "matProdAuthorMapper")
	private final MatProdAuthorMapper matProdAuthorMapper = null;
	@Resource(name = "matCertFileService")
	private final MatCertFileService matCertFileService = null;


	//列表查询
	@Override
	public String queryMatProdAuthorList(Map<String, Object> map)throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());

    	//授权日期转换
    	if(map.get("start_date") != null && !"".equals(map.get("start_date"))){
    		map.put("start_date", DateUtil.stringToDate(map.get("start_date").toString(), "yyyy-MM-dd"));
    	}
    	if(map.get("end_date") != null && !"".equals(map.get("end_date"))){
    		map.put("end_date", DateUtil.stringToDate(map.get("end_date").toString(), "yyyy-MM-dd"));
    	}
    	
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<?> list = matProdAuthorMapper.queryMatProdAuthorList(map, rowBounds);
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
	}

	//根据ID查询授权书
	@Override
	public Map<String, Object> queryMatProdAuthorById(Map<String, Object> map) throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		//查询主表
		Map<String, Object> retMap = matProdAuthorMapper.queryMatProdAuthorById(map);
		if(retMap == null || retMap.get("auth_id") == null || "".equals(retMap.get("auth_id").toString())){
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "授权书已被删除");
			return retMap;
		}
		//查询明细表
		List<Map<String, Object>> detailList = matProdAuthorMapper.queryMatProdAuthorDetailById(map);
		List<Map<String, Object>> list = null;
		String []cert_files = null;
		for(Map<String, Object> detailMap : detailList){
			list = matCertFileService.queryMatCertFileList("mat_prod_author_file", "auth_detail_id", detailMap.get("auth_detail_id").toString());
			cert_files = new String[list.size()];
			for(int i = 0; i < list.size(); i++){
				cert_files[i] = list.get(i).get("file_path").toString() + list.get(i).get("file_name").toString();
			}
			detailMap.put("cert_files", cert_files);
			
	    	//授权日期转换
	    	if(detailMap.get("start_date") != null && !"".equals(detailMap.get("start_date"))){
	    		detailMap.put("start_date", DateUtil.dateToString((Date)detailMap.get("start_date"), "yyyy-MM-dd"));
	    	}
	    	if(detailMap.get("end_date") != null && !"".equals(detailMap.get("end_date"))){
	    		detailMap.put("end_date", DateUtil.dateToString((Date)detailMap.get("end_date"), "yyyy-MM-dd"));
	    	}
		}
		retMap.put("detailJson", Json.toJson(detailList));
		
		return retMap;
	}
	
	//目标企业字典加载
	@Override
	public String queryHosFacSup(Map<String, Object> map) throws Exception{
		RowBounds rowBounds = new RowBounds(0, 20);
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		}
		
		return JSON.toJSONString(matProdAuthorMapper.queryHosFacSup(map, rowBounds));
	}
	
	//保存
	@Override
	public Map<String, Object> saveMatProdAuthor(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		try{
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			//取授权书最大级次
			if(map.get("auth_level") == null || "".equals(map.get("auth_level").toString())){
				retMap.put("error", "请维护授权级次！");
				retMap.put("state", false);
				return retMap;
			}
			int auth_level = Integer.valueOf(map.get("auth_level").toString());
			
			//判断修改还是添加
			boolean is_update = false;
			String auth_id = "";
			//主键ID
			if(map.get("auth_id") != null && !"自动生成".equals(map.get("auth_id").toString()) && !"".equals(map.get("auth_id").toString())){
				auth_id = map.get("auth_id").toString();
				is_update = true;
			}else{
				auth_id = UUIDLong.absStringUUID();
			}
			
			/**组装主表数据**********begin************/
			Map<String, Object> mainMap = new HashMap<String, Object>();
			mainMap.put("group_id", SessionManager.getGroupId());
			mainMap.put("hos_id", SessionManager.getHosId());
			mainMap.put("copy_code", SessionManager.getCopyCode());
			mainMap.put("auth_id", auth_id);  //授权书ID
			mainMap.put("cert_type_code", map.get("cert_type_code"));  //证件类型
			mainMap.put("fac_id", map.get("fac_id"));  //所属厂商
			mainMap.put("prod_name", map.get("prod_name"));  //授权产品名称
			mainMap.put("is_stop", map.get("is_stop"));  //是否停用
			mainMap.put("new_auth_id", map.get("new_auth_id"));  //授权产品名称
			mainMap.put("new_auth_info", map.get("new_auth_info"));  //授权产品名称
			mainMap.put("old_auth_id", map.get("old_auth_id"));  //授权产品名称
			mainMap.put("old_auth_info", map.get("old_auth_info"));  //授权产品名称
			mainMap.put("oper_name", SessionManager.getUserName());  //操作人
			mainMap.put("oper_date", new Date());  //操作时间
			/**组装主表数据**********end**************/
        	
			/**组装明细表数据**********begin************/
			List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();  //明细集合
        	MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  //文件流
			List<List<MultipartFile>> fileList = new ArrayList<List<MultipartFile>>();  //文件集合
			Map<Integer, Map<String, String>> fileMap = new HashMap<Integer, Map<String,String>>();  //文件其他信息
			Map<String, Object> detailMap = null;
			String auth_detail_id = "";
			
			for(int i = 1; i <= auth_level; i++){
				//明细表数据
				detailMap = new HashMap<String, Object>();
				if(map.get("auth_detail_id"+i) == null || "".equals(map.get("auth_detail_id"+i))){
					auth_detail_id = UUIDLong.absStringUUID();
				}else{
					auth_detail_id = map.get("auth_detail_id"+i).toString();
				}
				detailMap.put("auth_detail_id", auth_detail_id);  //授权明细ID
				detailMap.put("auth_level", i);  //授权级次
				detailMap.put("from_type", map.get("from_type"+i));  //发起企业类型
				detailMap.put("fac_id_from", map.get("fac_id_from"+i));  //发起企业
				detailMap.put("to_type", map.get("to_type"+i));  //目标企业类型
				detailMap.put("fac_id_to", map.get("fac_id_to"+i));  //目标企业
				//授权开始日期
		    	if(map.get("start_date"+i) != null && !"".equals(map.get("start_date"+i))){
		    		detailMap.put("start_date", DateUtil.stringToDate(map.get("start_date"+i).toString(), "yyyy-MM-dd"));
		    	}
		    	//授权结束日期
		    	if(map.get("end_date"+i) != null && !"".equals(map.get("end_date"+i))){
		    		detailMap.put("end_date", DateUtil.stringToDate(map.get("end_date"+i).toString(), "yyyy-MM-dd"));
		    	}else{
		    		detailMap.put("end_date", null);
		    	}
				detailMap.put("is_long", map.get("is_long"+i));  //是否长期
				if(detailMap.get("is_long") != null && "1".equals(detailMap.get("is_long").toString())){
					detailMap.put("auth_date",	map.get("start_date"+i).toString() + " 至 长期");
				}else{
					detailMap.put("auth_date",	map.get("start_date"+i).toString() + " 至 " + map.get("end_date"+i).toString());  //有效期限
				}
				detailMap.put("note", "");  //map.get("note"+i));  //备注
				detailMap.put("is_last", 0);  //默认非末级
				
				if(i == auth_level){
					//最后级次的授权日期与目标企业记录到主表中
					mainMap.put("sup_id",	detailMap.get("fac_id_to"));  //目标企业
					mainMap.put("start_date", detailMap.get("start_date"));  //授权开始日期
					mainMap.put("end_date", detailMap.get("end_date"));  //授权结束日期
					mainMap.put("is_long", detailMap.get("is_long"));  //是否长期
					mainMap.put("auth_date", detailMap.get("auth_date"));  //有效日期
					
					//最后级次明细表is_last = 1
					detailMap.put("is_last", 1);
				}
				
				detailList.add(detailMap);
				
				//处理文件
				List<MultipartFile> files = multiRequest.getFiles("fileList"+i);
				fileList.add(files);
				
				//文件其他信息
	        	String old_cert_files = map.get("old_cert_files"+i) == null ? "" : map.get("old_cert_files"+i).toString();
	        	String new_cert_files = map.get("new_cert_files"+i) == null ? "" : map.get("new_cert_files"+i).toString();
	        	Map<String, String> fileStr = new HashMap<String, String>();
	        	fileStr.put("old_cert_files", old_cert_files);
	        	fileStr.put("new_cert_files", new_cert_files);
	        	fileStr.put("auth_detail_id", auth_detail_id);
	        	fileMap.put(i - 1, fileStr);

				retMap.put("auth_detail_id"+i, auth_detail_id);  //记录明细ID用于返回页面
				
			}
			/**组装明细表数据**********end**************/
			
			if(is_update){
				//修改主表
				matProdAuthorMapper.addMatProdAuthor(mainMap);
				//修改关联材料的厂商与供应商
				matProdAuthorMapper.updateMatProdAuthorInvForFacSup(mainMap);
				//删除明细表
				matProdAuthorMapper.deleteMatProdAuthorDetail(mainMap);
			}else{
				mainMap.put("is_new", 1);  //默认新证
				mainMap.put("com_from", 1);  //默认手工录入
				mainMap.put("check_state", 1);  //默认未审核
				mainMap.put("authent_state", 1);  //默认未认证

				//添加主表
				matProdAuthorMapper.addMatProdAuthor(mainMap);
				
				//新证需要复制老证的材料信息并更新老证的信息
				if(mainMap.get("old_auth_id") != null && !"".equals(mainMap.get("old_auth_id").toString())){
					mainMap.put("changer", SessionManager.getUserName());
					mainMap.put("change_date", new Date());
					mainMap.put("change_note", "过期换证，原因：" + map.get("change_note"));
					//更新老证的信息
					matProdAuthorMapper.updateMatProdAuthorToOld(mainMap);
					//复制老证的材料信息
					matProdAuthorMapper.addMatProdAuthorInvByCopyOld(mainMap);
				}
			}
			//添加明细表
			matProdAuthorMapper.addMatProdAuthorDetail(mainMap, detailList);
			
			//保存文件
			for(int i = 0 ; i < fileList.size() ; i++) {
				Map<String, String> tmpMap = fileMap.get(i);
				String ret_files = matCertFileService.saveMatCertFile("mat_prod_author_file", "auth_detail_id", tmpMap.get("auth_detail_id").toString(), tmpMap.get("old_cert_files").toString(), tmpMap.get("new_cert_files").toString(), fileList.get(i));
				
				retMap.put("cert_files"+(i+1), ret_files);  //记录文件路径用于返回页面
			}
				
			retMap.put("msg", "操作成功");
			retMap.put("auth_id", auth_id);
			retMap.put("auth_date", mainMap.get("auth_date"));
		}catch(Exception e){

			logger.error(e.getMessage(), e);
			throw new SysException("操作失败！");
		}
		return retMap;
	}

	//删除
	@Override
	public Map<String, Object> deleteMatProdAuthor(Map<String, Object> map) throws Exception {
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("ids") == null || "".equals(map.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择授权书！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			List<String> id_list = new ArrayList<String>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String id: map.get("ids").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("auth_id", id);
				list.add(tmpMap);
				id_list.add(id);
			}

			//判断是否已认证
			Integer is_flag = matProdAuthorMapper.notExistsMatProdAuthorByState(map, "authent_state", 1, list);
			if(is_flag != null && is_flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "删除失败，存在已认证的授权书！");
				return retMap;
			}
			
			//获取附件路径
			List<String> authList = matProdAuthorMapper.queryMatProdAuthorDetailListByDelete(map, list);
			
			//删除授权书
			matProdAuthorMapper.deleteMatProdAuthor(map, list);
			//删除附件
			if(authList.size() > 0){
				matCertFileService.deleteMatCertFile("mat_prod_author_file", "auth_detail_id", authList);
			}
			retMap.put("msg", "删除成功");
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("操作失败！");
		}
		
		return retMap;
	}
	
	//认证
	@Override
	public Map<String, Object> updateMatProdAuthorToAuthent(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("ids") == null || "".equals(map.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String id: map.get("ids").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("auth_id", id);
				list.add(tmpMap);
			}

			//判断是否已认证
			Integer is_flag = matProdAuthorMapper.notExistsMatProdAuthorByState(map, "authent_state", 1, list);
			if(is_flag != null && is_flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "认证失败，存在已认证的授权书！");
				return retMap;
			}
			
			map.put("authent_state", 2);
			map.put("authenter", SessionManager.getUserName());
			map.put("authent_date", new Date());
			
			//认证
			matProdAuthorMapper.updateMatProdAuthorAuthentState(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	
	//取消认证
	@Override
	public Map<String, Object> updateMatProdAuthorToUnAuthent(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("ids") == null || "".equals(map.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String id: map.get("ids").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("auth_id", id);
				list.add(tmpMap);
			}

			//判断是否已审核
			Integer is_flag = matProdAuthorMapper.notExistsMatProdAuthorByState(map, "check_state", 1, list);
			if(is_flag != null && is_flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "取消认证失败，存在已审核的授权书！");
				return retMap;
			}

			//判断是否已认证
			is_flag = 0;
			is_flag = matProdAuthorMapper.notExistsMatProdAuthorByState(map, "authent_state", 2, list);
			if(is_flag != null && is_flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "取消认认证失败，存在未认证的授权书！");
				return retMap;
			}
			
			map.put("authent_state", 1);
			map.put("authenter", SessionManager.getUserName());
			map.put("authent_date", new Date());
			
			//取消认证
			matProdAuthorMapper.updateMatProdAuthorAuthentState(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	
	//审核
	@Override
	public Map<String, Object> updateMatProdAuthorToCheck(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("ids") == null || "".equals(map.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String id: map.get("ids").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("auth_id", id);
				list.add(tmpMap);
			}

			//判断是否已认证
			Integer is_flag = matProdAuthorMapper.notExistsMatProdAuthorByState(map, "authent_state", 2, list);
			if(is_flag != null && is_flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "审核失败，存在未认证的授权书！");
				return retMap;
			}

			//判断是否已审核
			is_flag = 0;
			is_flag = matProdAuthorMapper.notExistsMatProdAuthorByState(map, "check_state", 1, list);
			if(is_flag != null && is_flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "审核失败，存在已审核的授权书！");
				return retMap;
			}
			
			map.put("check_state", 2);
			map.put("checker", SessionManager.getUserName());
			map.put("check_date", new Date());
			
			//审核
			matProdAuthorMapper.updateMatProdAuthorCheckState(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	
	//消审
	@Override
	public Map<String, Object> updateMatProdAuthorToUnCheck(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("ids") == null || "".equals(map.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择！");
				return retMap;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析为List
			for ( String id: map.get("ids").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("auth_id", id);
				list.add(tmpMap);
			}

			//判断是否已审核
			Integer is_flag = matProdAuthorMapper.notExistsMatProdAuthorByState(map, "check_state", 2, list);
			if(is_flag != null && is_flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "消审失败，存在未审核的授权书！");
				return retMap;
			}
			
			map.put("check_state", 1);
			map.put("checker", SessionManager.getUserName());
			map.put("check_date", new Date());
			
			//消审
			matProdAuthorMapper.updateMatProdAuthorCheckState(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	
	//导入
	@Override
	public Map<String, Object> addMatProdAuthorByImp(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		
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
    	
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String, Object>> list = matProdAuthorMapper.queryMatInvList(map, rowBounds);
		PageInfo page = new PageInfo(list);			
		return ChdJson.toJson(list, page.getTotal());
	}
	
	//查询关联材料
	@Override
	public String queryMatProdAuthorInvList(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());

		List<Map<String, Object>> list = matProdAuthorMapper.queryMatProdAuthorInvList(map);
		return ChdJson.toJson(list);	
	}
	
	//保存关联材料
	@Override
	public Map<String, Object> saveMatProdAuthorInv(Map<String, Object> map) throws DataAccessException{
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
			matProdAuthorMapper.deleteMatProdAuthorInv(map);
			//保存
			if(list != null && list.size() > 0){
				matProdAuthorMapper.addMatProdAuthorInv(map, list);
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
	public String queryMatProdAuthorChooseList(Map<String, Object> map) throws DataAccessException{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage"); 
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String, Object>> list = matProdAuthorMapper.queryMatProdAuthorChooseList(map, rowBounds);
		PageInfo page = new PageInfo(list);			
		return ChdJson.toJson(list, page.getTotal());
	}
	
	//换证
	@Override
	public Map<String, Object> updateMatProdAuthorToNewCert(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("new_auth_id") == null || "".equals(map.get("new_auth_id").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择新证！");
				return retMap;
			}
			
			map.put("changer", SessionManager.getUserName());
			map.put("change_date", new Date());
			map.put("change_note", "选择新证，原因：" + map.get("change_note"));
			
			//换证
			matProdAuthorMapper.updateMatProdAuthorToNewCert(map);
			
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
	public Map<String, Object> updateMatProdAuthorToUnNewCert(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("new_auth_id") == null || "".equals(map.get("new_auth_id").toString())){
				retMap.put("state", "false");
				retMap.put("error", "该证件没有新证！");
				return retMap;
			}

			map.put("changer", SessionManager.getUserName());
			map.put("change_date", new Date());
			map.put("change_note", "取消换证，原因：" + map.get("change_note"));
			
			//取消换证
			matProdAuthorMapper.updateMatProdAuthorToUnNewCert(map);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
}
