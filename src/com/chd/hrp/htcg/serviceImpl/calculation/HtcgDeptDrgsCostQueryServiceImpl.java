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
import com.chd.hrp.htcg.dao.calculation.HtcgDeptDrgsCostQueryMapper;
import com.chd.hrp.htcg.entity.calculation.HtcgDeptPdrgsCost;
import com.chd.hrp.htcg.service.calculation.HtcgDeptDrgsCostQueryService;
import com.github.pagehelper.PageInfo;

@Service("htcgDeptDrgsCostQueryService")
public class HtcgDeptDrgsCostQueryServiceImpl implements HtcgDeptDrgsCostQueryService{

	
private static Logger logger = Logger.getLogger(HtcgDeptDrgsCostQueryServiceImpl.class); 
	
	@Resource(name = "htcgDeptDrgsCostQueryMapper")
	private final HtcgDeptDrgsCostQueryMapper htcgDeptDrgsCostQueryMapper = null;
	
	@Override
	public String queryHtcgDeptDrgsCostQuery(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<HtcgDeptPdrgsCost> list = htcgDeptDrgsCostQueryMapper.queryHtcgDeptDrgsCostQuery(entityMap) ;
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgDeptPdrgsCost> list = htcgDeptDrgsCostQueryMapper.queryHtcgDeptDrgsCostQuery(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
}
