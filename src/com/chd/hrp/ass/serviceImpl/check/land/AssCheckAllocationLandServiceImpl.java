package com.chd.hrp.ass.serviceImpl.check.land;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.check.land.AssCheckAllocationLandMapper;
import com.chd.hrp.ass.service.check.land.AssCheckAllocationLandService;
import com.github.pagehelper.PageInfo;

@Service("assCheckAllocationLandService")

public class AssCheckAllocationLandServiceImpl implements AssCheckAllocationLandService{

	private static Logger logger = Logger.getLogger(AssCheckAllocationLandServiceImpl.class);

	@Resource(name = "assCheckAllocationLandMapper")
	private final AssCheckAllocationLandMapper assCheckAllocationLandMapper = null;

	@Override
	public String queryAssCheckAllocationLand(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		List<Map<String, Object>> list = null;

		if (sysPage.getTotal() == -1) {
			 if("detail".equals(entityMap.get("searchType").toString())){//明细查询
				list = assCheckAllocationLandMapper.queryAssCheckAllocationLand(entityMap);
			}
			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			 if("detail".equals(entityMap.get("searchType").toString())){//明细查询
				list = assCheckAllocationLandMapper.queryAssCheckAllocationLand(entityMap, rowBounds);
			}
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
}
