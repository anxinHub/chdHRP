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
import com.chd.base.util.UUIDLong;
import com.chd.hrp.mat.dao.cert.MatCertFacCertMapper;
import com.chd.hrp.mat.service.cert.MatCertFacCertService;
import com.chd.hrp.mat.service.cert.MatCertFileService;
import com.github.pagehelper.PageInfo;

@Service("matCertFacCertService")
public class MatCertFacCertServiceImpl implements MatCertFacCertService {

	private static Logger logger = Logger.getLogger(MatCertFacCertServiceImpl.class);
	
	@Resource(name = "matCertFacCertMapper")
	private final MatCertFacCertMapper matCertFacCertMapper = null;
	
	@Resource(name = "matCertFileService")
	private final MatCertFileService matCertFileService = null;
	
	@Override
	public String queryMatFacCert(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> resultList = matCertFacCertMapper.queryMatFacCert(mapVo);
			return ChdJson.toJson(resultList);	
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> resultList = matCertFacCertMapper.queryMatFacCert(mapVo, rowBounds);
			PageInfo page = new PageInfo(resultList);			
			return ChdJson.toJsonLower(resultList, page.getTotal());
		}
	}

	@Override
	public Map<String, Object> saveMatFacCert(Map<String, Object> mapVo,
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
			
			Map<String, Object> facCertMap = matCertFacCertMapper.queryMatFacCertByCode(mapVo);
			
			if(facCertMap != null){
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
			matCertFacCertMapper.saveMatFacCert(mapVo);
			
			//文件信息
			String cert_files = matCertFileService.saveMatCertFile("MAT_FAC_CERT_FILE", "cert_id", cert_id, "", new_cert_files, certFileList);

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
	public Map<String, Object> deleteMatFacCert(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			
			matCertFacCertMapper.deleteMatCertFacFile(mapVo);
			matCertFacCertMapper.deleteMatCertFac(mapVo);
			
			retMap.put("state", "true");
			retMap.put("msg", "删除成功！");
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}

	@Override
	public String queryMatFacCertInfo(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = matCertFacCertMapper.queryMatFacCertInfo(mapVo);
		return ChdJson.toJsonLower(resultList);	
	}

	@Override
	public String queryMatFacCertInv(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> resultList = matCertFacCertMapper.queryMatFacCertInv(mapVo);
			return ChdJson.toJsonLower(resultList);	
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> resultList = matCertFacCertMapper.queryMatFacCertInv(mapVo, rowBounds);
			PageInfo page = new PageInfo(resultList);			
			return ChdJson.toJsonLower(resultList, page.getTotal());
		}
	}

	@Override
	public Map<String, Object> updateMatCertFacState(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			
			mapVo.put("oper_name", SessionManager.getUserName());
        	mapVo.put("oper_date", new Date());
			
			matCertFacCertMapper.updateMatCertFacState(mapVo);
			
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
		return matCertFacCertMapper.queryCertType(mapVo);
	}

	@Override
	public Map<String, Object> queryMatFacCertById(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return matCertFacCertMapper.queryMatFacCertById(mapVo);
	}

	@Override
	public Map<String, Object> updateMatFacCert(Map<String, Object> mapVo,
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
			
			Map<String, Object> facCertMap = matCertFacCertMapper.queryMatFacCertByCode(mapVo);
			
			if(facCertMap != null && !facCertMap.get("CERT_CODE").equals(mapVo.get("cert_code"))){
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
			matCertFacCertMapper.updateMatFacCert(mapVo);
			
			//文件信息
			String cert_files = matCertFileService.saveMatCertFile("MAT_FAC_CERT_FILE", "cert_id", mapVo.get("cert_id").toString(), old_cert_files, new_cert_files, certFileList);

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
