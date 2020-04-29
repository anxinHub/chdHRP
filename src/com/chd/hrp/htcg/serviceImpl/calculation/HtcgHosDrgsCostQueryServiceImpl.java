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
import com.chd.hrp.htcg.dao.calculation.HtcgHosDrgsCostQueryMapper;
import com.chd.hrp.htcg.dao.calculation.HtcgHosPdrgsCostMapper;
import com.chd.hrp.htcg.entity.calculation.HtcgHosPdrgsCost;
import com.chd.hrp.htcg.service.calculation.HtcgHosDrgsCostQueryService;
import com.github.pagehelper.PageInfo;

@Service("htcgHosDrgsCostQueryService")
public class HtcgHosDrgsCostQueryServiceImpl implements HtcgHosDrgsCostQueryService { 

private static Logger logger = Logger.getLogger(HtcgHosPdrgsCostServiceImpl.class);
	
	@Resource(name = "htcgHosDrgsCostQueryMapper")
	private final HtcgHosDrgsCostQueryMapper htcgHosDrgsCostQueryMapper = null;
	
	@Override
	public String queryHtcgHosDrgsCostQuery(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<HtcgHosPdrgsCost> list = htcgHosDrgsCostQueryMapper.queryHtcgHosDrgsCostQuery(entityMap) ;
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgHosPdrgsCost> list = htcgHosDrgsCostQueryMapper.queryHtcgHosDrgsCostQuery(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
}
