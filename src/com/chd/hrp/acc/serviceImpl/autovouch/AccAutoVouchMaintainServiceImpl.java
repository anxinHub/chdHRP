package com.chd.hrp.acc.serviceImpl.autovouch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.autovouch.AccAutoVouchMaintainMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.entity.AccVouch;
import com.chd.hrp.acc.service.autovouch.AccAutoVouchMaintainService;
import com.github.pagehelper.PageInfo;
@Service("accAutoVouchMaintainService")
public class AccAutoVouchMaintainServiceImpl implements AccAutoVouchMaintainService {
	private static Logger logger = Logger.getLogger(AccAutoVouchMaintainServiceImpl.class);

	@Resource(name = "accAutoVouchMaintainMapper")
	private final AccAutoVouchMaintainMapper accAutoVouchMaintainMapper = null;
	
	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;

	@Override
	public String queryAccAutoVouch(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = accAutoVouchMaintainMapper.queryAccAutoVouch(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = accAutoVouchMaintainMapper.queryAccAutoVouch(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public String deleteAccAutoVouch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			for(Map<String, Object> map : entityMap){
				//查附件
				List<Map<String,Object>> list=superVouchMapper.queryFileDel(map);
				accAutoVouchMaintainMapper.deleteAccAutoVouch(map);
				if(map.get("reNote")!=null && !map.get("reNote").toString().equalsIgnoreCase("ok")){
					throw new SysException(map.get("reNote").toString());
				}
				
				if(list.size()>0){
					superVouchMapper.deleteBatchFile(list);
					for(Map<String,Object> mmp : list){
						String att_path=mmp.get("att_path").toString();
						String file_name = att_path.substring(att_path.lastIndexOf("/") + 1, att_path.length());
						String path = att_path.substring(0,att_path.lastIndexOf("/"));
						if(!FtpUtil.deleteFile(path, file_name)){
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							return "error";
						}
					}
				}
				
			}
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
	}

	@Override
	public List<Map<String, Object>> queryAccAutoVouchPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accAutoVouchMaintainMapper.queryAccAutoVouch(entityMap);
		
		return list;
	}
	
}
