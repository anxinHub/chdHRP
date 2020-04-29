package com.chd.hrp.htcg.serviceImpl.calculation;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htcg.dao.calculation.HtcgDrugAdminResultMapper;
import com.chd.hrp.htcg.entity.calculation.HtcgDrugAdminResult;
import com.chd.hrp.htcg.service.calculation.HtcgDrugAdminResultService;
import com.github.pagehelper.PageInfo;

@Service("htcgDrugAdminResultService")
public class HtcgDrugAdminResultServiceImpl implements HtcgDrugAdminResultService {
	
	private static Logger logger = Logger.getLogger(HtcgDrugAdminResultServiceImpl.class);
	@Resource(name = "htcgDrugAdminResultMapper")
	private HtcgDrugAdminResultMapper htcgDrugAdminResultMapper = null;

	@Override
	public String queryHtcgDrugAdminResult(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<HtcgDrugAdminResult> list = htcgDrugAdminResultMapper.queryHtcgDrugAdminResult(entityMap) ;
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgDrugAdminResult> list = htcgDrugAdminResultMapper.queryHtcgDrugAdminResult(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

}
