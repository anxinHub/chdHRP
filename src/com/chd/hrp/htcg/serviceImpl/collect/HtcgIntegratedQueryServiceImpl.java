package com.chd.hrp.htcg.serviceImpl.collect;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htcg.dao.collect.HtcgIntegratedQueryMapper;
import com.chd.hrp.htcg.entity.collect.HtcgIntegratedQuery;
import com.chd.hrp.htcg.service.collect.HtcgIntegratedQueryService;
import com.github.pagehelper.PageInfo;

@Service("htcgIntegratedQueryService")
public class HtcgIntegratedQueryServiceImpl implements HtcgIntegratedQueryService {
	private static Logger logger = Logger.getLogger(HtcgIntegratedQueryServiceImpl.class);
	@Resource(name = "htcgIntegratedQueryMapper")
	private final HtcgIntegratedQueryMapper htcgIntegratedQueryMapper = null;

	@Override
	public String queryHtcgIntegratedQuery(Map<String, Object> mapVo) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<HtcgIntegratedQuery> list = htcgIntegratedQueryMapper.queryHtcgIntegratedQuery(mapVo) ;
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgIntegratedQuery> list = htcgIntegratedQueryMapper.queryHtcgIntegratedQuery(mapVo, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

}
