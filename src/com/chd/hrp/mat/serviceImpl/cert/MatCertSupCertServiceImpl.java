package com.chd.hrp.mat.serviceImpl.cert;

import java.util.Date;
import java.util.HashMap;
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

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.mat.dao.cert.MatCertSupCertMapper;
import com.chd.hrp.mat.service.cert.MatCertFileService;
import com.chd.hrp.mat.service.cert.MatCertSupCertService;
import com.github.pagehelper.PageInfo;

@Service("matCertSupCertService")
public class MatCertSupCertServiceImpl implements MatCertSupCertService {

	private static Logger logger = Logger.getLogger(MatCertSupCertServiceImpl.class);
	
	@Resource(name = "matCertSupCertMapper")
	private final MatCertSupCertMapper matCertSupCertMapper = null;
	
	@Resource(name = "matCertFileService")
	private final MatCertFileService matCertFileService = null;
	
	@Override
	public String queryMatSupCert(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> resultList = matCertSupCertMapper.queryMatSupCert(mapVo);
			return ChdJson.toJson(resultList);	
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> resultList = matCertSupCertMapper.queryMatSupCert(mapVo, rowBounds);
			PageInfo page = new PageInfo(resultList);			
			return ChdJson.toJsonLower(resultList, page.getTotal());
		}
	}

	@Override
	public Map<String, Object> saveMatSupCert(Map<String, Object> mapVo,
			HttpServletRequest request, HttpServletResponse response)
			throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			if(mapVo.get("cert_type_code") == null || "".equals(mapVo.get("cert_type_code").toString())){
				retMap.put("state", "false");
				retMap.put("error", "证件类型不能为空！");
				return retMap;
			}
			if(mapVo.get("cert_code") == null || "".equals(mapVo.get("cert_code").toString())){
				retMap.put("state", "false");
				retMap.put("error", "证件编码不能为空！");
				return retMap;
			}
			
			Map<String, Object> supCertMap = matCertSupCertMapper.queryMatSupCertByCode(mapVo);
			
			if(supCertMap != null){
				retMap.put("state", "false");
				retMap.put("warn", "证件编码已存在！");
				return retMap;
			}
			
        	//处理证件日期
        	if(mapVo.get("start_date") == null || "".equals(mapVo.get("start_date"))){
				retMap.put("state", "false");
				retMap.put("error", "证件开始日期不能为空！");
				return retMap;
        	}
        	String cert_date = mapVo.get("start_date").toString()+"至";
        	mapVo.put("start_date", DateUtil.stringToDate(mapVo.get("start_date").toString(), "yyyy-MM-dd"));
        	if(mapVo.get("is_long") != null && "1".equals(mapVo.get("is_long").toString())){
        		cert_date += "永久有效";
        		mapVo.put("end_date", null);
        	}else{
        		if(mapVo.get("end_date") == null || "".equals(mapVo.get("end_date"))){
    				retMap.put("state", "false");
    				retMap.put("error", "证件结束日期不能为空！");
    				return retMap;
            	}
        		cert_date += mapVo.get("end_date").toString();
        		mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date").toString(), "yyyy-MM-dd"));
        	}
        	mapVo.put("cert_date", cert_date);

        	mapVo.put("oper_name", SessionManager.getUserName());
        	mapVo.put("oper_date", new Date());

			//文件
        	MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
        	List<MultipartFile> certFileList = multiRequest.getFiles("cert_files");
        	
        	//获取页面删除的文件
        	String new_cert_files = mapVo.get("new_cert_files") == null ? "" : mapVo.get("new_cert_files").toString();

			//生成主键
        	String cert_id = UUIDLong.absStringUUID();
			mapVo.put("cert_id", cert_id);
			mapVo.put("check_state", 1);  //默认未审核
			mapVo.put("authent_state", 1);  //默认未认证
			mapVo.put("com_from", 1);
			mapVo.put("is_stop", 0);
			
			//新增
			matCertSupCertMapper.saveMatSupCert(mapVo);
			
			//文件信息
			String cert_files = matCertFileService.saveMatCertFile("MAT_SUP_CERT_FILE", "cert_id", cert_id, "", new_cert_files, certFileList);

			retMap.put("cert_files", cert_files);
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}

	@Override
	public Map<String, Object> deleteMatSupCert(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			
			matCertSupCertMapper.deleteMatCertSupFile(mapVo);
			matCertSupCertMapper.deleteMatCertSup(mapVo);
			
			retMap.put("state", "true");
			retMap.put("msg", "删除成功！");
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}

	@Override
	public String queryMatSupCertInfo(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = matCertSupCertMapper.queryMatSupCertInfo(mapVo);
		return ChdJson.toJsonLower(resultList);	
	}

	@Override
	public String queryMatSupCertInv(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> resultList = matCertSupCertMapper.queryMatSupCertInv(mapVo);
			return ChdJson.toJsonLower(resultList);	
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> resultList = matCertSupCertMapper.queryMatSupCertInv(mapVo, rowBounds);
			PageInfo page = new PageInfo(resultList);			
			return ChdJson.toJsonLower(resultList, page.getTotal());
		}
	}

	@Override
	public Map<String, Object> updateMatCertSupState(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			
			mapVo.put("oper_name", SessionManager.getUserName());
        	mapVo.put("oper_date", new Date());
			
			matCertSupCertMapper.updateMatCertSupState(mapVo);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}

	@Override
	public Map<String, Object> queryCertType(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return matCertSupCertMapper.queryCertType(mapVo);
	}

	@Override
	public Map<String, Object> queryMatSupCertById(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return matCertSupCertMapper.queryMatSupCertById(mapVo);
	}

	@Override
	public Map<String, Object> updateMatSupCert(Map<String, Object> mapVo,
			HttpServletRequest request, HttpServletResponse response)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			if(mapVo.get("cert_type_code") == null || "".equals(mapVo.get("cert_type_code").toString())){
				retMap.put("state", "false");
				retMap.put("error", "证件类型不能为空！");
				return retMap;
			}
			if(mapVo.get("cert_code") == null || "".equals(mapVo.get("cert_code").toString())){
				retMap.put("state", "false");
				retMap.put("error", "证件编码不能为空！");
				return retMap;
			}
			
			Map<String, Object> supCertMap = matCertSupCertMapper.queryMatSupCertByCode(mapVo);
			
			if(supCertMap != null && !supCertMap.get("CERT_CODE").equals(mapVo.get("cert_code"))){
				retMap.put("state", "false");
				retMap.put("warn", "证件编码已存在！");
				return retMap;
			}
			
        	//处理证件日期
        	if(mapVo.get("start_date") == null || "".equals(mapVo.get("start_date"))){
				retMap.put("state", "false");
				retMap.put("error", "证件开始日期不能为空！");
				return retMap;
        	}
        	String cert_date = mapVo.get("start_date").toString()+"至";
        	mapVo.put("start_date", DateUtil.stringToDate(mapVo.get("start_date").toString(), "yyyy-MM-dd"));
        	if(mapVo.get("is_long") != null && "1".equals(mapVo.get("is_long").toString())){
        		cert_date += "永久有效";
        		mapVo.put("end_date", null);
        	}else{
        		if(mapVo.get("end_date") == null || "".equals(mapVo.get("end_date"))){
    				retMap.put("state", "false");
    				retMap.put("error", "证件结束日期不能为空！");
    				return retMap;
            	}
        		cert_date += mapVo.get("end_date").toString();
        		mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date").toString(), "yyyy-MM-dd"));
        	}
        	mapVo.put("cert_date", cert_date);

        	mapVo.put("oper_name", SessionManager.getUserName());
        	mapVo.put("oper_date", new Date());

			//文件
        	MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
        	List<MultipartFile> certFileList = multiRequest.getFiles("cert_files");
        	
        	//获取页面删除的文件
        	String new_cert_files = mapVo.get("new_cert_files") == null ? "" : mapVo.get("new_cert_files").toString();
			String old_cert_files = mapVo.get("old_cert_files") == null ? "" : mapVo.get("old_cert_files").toString();
        	
			//新增
			matCertSupCertMapper.updateMatSupCert(mapVo);
			
			//文件信息
			String cert_files = matCertFileService.saveMatCertFile("MAT_SUP_CERT_FILE", "cert_id", mapVo.get("cert_id").toString(), old_cert_files, new_cert_files, certFileList);

			retMap.put("cert_files", cert_files);
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
	
}
