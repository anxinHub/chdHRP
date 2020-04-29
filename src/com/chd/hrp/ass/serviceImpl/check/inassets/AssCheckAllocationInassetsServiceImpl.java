package com.chd.hrp.ass.serviceImpl.check.inassets;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.check.inassets.AssCheckAllocationInassetsMapper;
import com.chd.hrp.ass.service.check.inassets.AssCheckAllocationInassetsService;
import com.github.pagehelper.PageInfo;

@Service("assCheckAllocationInassetsService")

public class AssCheckAllocationInassetsServiceImpl implements AssCheckAllocationInassetsService{

	private static Logger logger = Logger.getLogger(AssCheckAllocationInassetsServiceImpl.class);

	@Resource(name = "assCheckAllocationInassetsMapper")
	private final AssCheckAllocationInassetsMapper assCheckAllocationInassetsMapper = null;

	@Override
	public String queryAssCheckAllocationInassets(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		List<Map<String, Object>> list = null;

		if (sysPage.getTotal() == -1) {
			 if("detail".equals(entityMap.get("searchType").toString())){//明细查询
				list = assCheckAllocationInassetsMapper.queryAssCheckAllocationInassets(entityMap);
			}
			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			 if("detail".equals(entityMap.get("searchType").toString())){//明细查询
				list = assCheckAllocationInassetsMapper.queryAssCheckAllocationInassets(entityMap, rowBounds);
			}
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
}
