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
import com.chd.hrp.mat.dao.cert.MatWarnTypeMapper;
import com.chd.hrp.mat.service.cert.MatCertFileService;
import com.chd.hrp.mat.service.cert.MatProdCertService;
import com.chd.hrp.mat.service.cert.MatWarnTypeService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

@Service("matWarnTypeService")
public class MatWarnTypeServiceImpl implements MatWarnTypeService{

	private static Logger logger = Logger.getLogger(MatWarnTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matWarnTypeMapper")
	private final MatWarnTypeMapper matWarnTypeMapper = null;
	
	@Override
	public String queryMatWarnType(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> resultList = matWarnTypeMapper.queryMatWarnType(mapVo);
			return ChdJson.toJson(resultList);	
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> resultList = matWarnTypeMapper.queryMatWarnType(mapVo, rowBounds);
			PageInfo page = new PageInfo(resultList);			
			return ChdJson.toJsonLower(resultList, page.getTotal());
		}
	}
	@Override
	public Map<String, Object> queryMatWarnTypeByCode(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return matWarnTypeMapper.queryMatWarnTypeByCode(mapVo);
	}
	@Override
	public Map<String, Object> saveMatWarnType(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("is_sys", 0);
			mapVo.put("note", "");
			
			Map<String, Object> codeMap = new HashMap<String, Object>();
			codeMap.put("group_id", SessionManager.getGroupId());
			codeMap.put("hos_id", SessionManager.getHosId());
			codeMap.put("copy_code", SessionManager.getCopyCode());
			codeMap.put("warn_type_code", mapVo.get("warn_type_code"));
			
			Map<String, Object> matWarnTypeCodeMap = matWarnTypeMapper.queryMatWarnTypeByCode(codeMap);
			
			if(matWarnTypeCodeMap != null){
				retMap.put("state", "false");
				retMap.put("warn", "预警类型编码已存在！");
				return retMap;
			}
			
			Map<String, Object> nameMap = new HashMap<String, Object>();
			nameMap.put("group_id", SessionManager.getGroupId());
			nameMap.put("hos_id", SessionManager.getHosId());
			nameMap.put("copy_code", SessionManager.getCopyCode());
			nameMap.put("warn_type_code", mapVo.get("warn_type_code"));
			
			Map<String, Object> matWarnTypeNameMap = matWarnTypeMapper.queryMatWarnTypeByCode(nameMap);
			
			if(matWarnTypeNameMap != null){
				retMap.put("state", "false");
				retMap.put("warn", "预警类型名称已存在！");
				return retMap;
			}
			
        	mapVo.put("oper_name", SessionManager.getUserName());
        	mapVo.put("oper_date", new Date());
        	
        	matWarnTypeMapper.saveMatWarnType(mapVo);
		
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	@Override
	public Map<String, Object> deleteMatWarnType(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			matWarnTypeMapper.deleteMatWarnType(mapVo);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	@Override
	public Map<String, Object> updateMatWarnTypeState(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			matWarnTypeMapper.updateMatWarnTypeState(mapVo);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	@Override
	public Map<String, Object> updateMatWarnType(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			if(!mapVo.get("old_code").equals(mapVo.get("warn_type_code"))){
				Map<String, Object> codeMap = new HashMap<String, Object>();
				codeMap.put("group_id", SessionManager.getGroupId());
				codeMap.put("hos_id", SessionManager.getHosId());
				codeMap.put("copy_code", SessionManager.getCopyCode());
				codeMap.put("warn_type_code", mapVo.get("warn_type_code"));
				
				Map<String, Object> matWarnTypeCodeMap = matWarnTypeMapper.queryMatWarnTypeByCode(codeMap);
				
				if(matWarnTypeCodeMap != null){
					retMap.put("state", "false");
					retMap.put("warn", "预警类型编码已存在！");
					return retMap;
				}
				
				Map<String, Object> nameMap = new HashMap<String, Object>();
				nameMap.put("group_id", SessionManager.getGroupId());
				nameMap.put("hos_id", SessionManager.getHosId());
				nameMap.put("copy_code", SessionManager.getCopyCode());
				nameMap.put("warn_type_code", mapVo.get("warn_type_code"));
				
				Map<String, Object> matWarnTypeNameMap = matWarnTypeMapper.queryMatWarnTypeByCode(nameMap);
				
				if(matWarnTypeNameMap != null){
					retMap.put("state", "false");
					retMap.put("warn", "预警类型名称已存在！");
					return retMap;
				}
			}
        	
        	matWarnTypeMapper.updateMatWarnType(mapVo);
		
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
}
