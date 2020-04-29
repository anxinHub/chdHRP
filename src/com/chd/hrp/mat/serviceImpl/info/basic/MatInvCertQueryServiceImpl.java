package com.chd.hrp.mat.serviceImpl.info.basic;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.info.basic.MatInvCertQueryMapper;
import com.chd.hrp.mat.service.info.basic.MatInvCertQueryService;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * 证件材料查询
 * @Table:
 * MAT_INV_CERT_RELA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matInvCertQueryService")
public class MatInvCertQueryServiceImpl implements MatInvCertQueryService {
	
	private static Logger logger = Logger.getLogger(MatInvCertQueryServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "matInvCertQueryMapper")
	private final MatInvCertQueryMapper matInvCertQueryMapper = null;

	@Override
	public String queryMatInvCertQuery(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matInvCertQueryMapper.queryMatInvCertQuery(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matInvCertQueryMapper.queryMatInvCertQuery(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

}
