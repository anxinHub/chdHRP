package com.chd.hrp.hpm.serviceImpl.report;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiPostBonusRatioMapper;
import com.chd.hrp.hpm.service.report.AphiPostBonusRatioService;

@Service("aphiPostBonusRatioService")
public class AphiPostBonusRatioServiceImpl implements AphiPostBonusRatioService {
	
	private static Logger logger = Logger.getLogger(AphiPostBonusRatioServiceImpl.class);
	
	@Resource(name="aphiPostBonusRatioMapper")
	private AphiPostBonusRatioMapper  aphiPostBonusRatioMapper= null;
	
	
	@Override
	public String queryPostBonusRatioByCode(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage  sysPage = new SysPage();
		sysPage= (SysPage) mapVo.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		return JsonListBeanUtil.listToJson(aphiPostBonusRatioMapper.queryPostBonusRatioByCode(mapVo, rowBounds), sysPage.getTotal());
	}

}
