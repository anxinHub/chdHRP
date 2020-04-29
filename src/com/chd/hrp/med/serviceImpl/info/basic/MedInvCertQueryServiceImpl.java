package com.chd.hrp.med.serviceImpl.info.basic;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.info.basic.MedInvCertQueryMapper;
import com.chd.hrp.med.service.info.basic.MedInvCertQueryService;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * 证件材料查询
 * @Table:
 * MED_INV_CERT_RELA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("medInvCertQueryService")
public class MedInvCertQueryServiceImpl implements MedInvCertQueryService {
	
	private static Logger logger = Logger.getLogger(MedInvCertQueryServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "medInvCertQueryMapper")
	private final MedInvCertQueryMapper medInvCertQueryMapper = null;

	@Override
	public String queryMedInvCertQuery(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medInvCertQueryMapper.queryMedInvCertQuery(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medInvCertQueryMapper.queryMedInvCertQuery(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

}
