package com.chd.hrp.ass.serviceImpl.check.house;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.check.house.AssCheckAllocationHouseMapper;
import com.chd.hrp.ass.service.check.house.AssCheckAllocationHouseService;
import com.github.pagehelper.PageInfo;

@Service("assCheckAllocationHouseService")

public class AssCheckAllocationHouseServiceImpl implements AssCheckAllocationHouseService{

	private static Logger logger = Logger.getLogger(AssCheckAllocationHouseServiceImpl.class);

	@Resource(name = "assCheckAllocationHouseMapper")
	private final AssCheckAllocationHouseMapper assCheckAllocationHouseMapper = null;

	@Override
	public String queryAssCheckAllocationHouse(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		List<Map<String, Object>> list = null;

		if (sysPage.getTotal() == -1) {
			 if("detail".equals(entityMap.get("searchType").toString())){//明细查询
				list = assCheckAllocationHouseMapper.queryAssCheckAllocationHouse(entityMap);
			}
			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			 if("detail".equals(entityMap.get("searchType").toString())){//明细查询
				list = assCheckAllocationHouseMapper.queryAssCheckAllocationHouse(entityMap, rowBounds);
			}
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
}
