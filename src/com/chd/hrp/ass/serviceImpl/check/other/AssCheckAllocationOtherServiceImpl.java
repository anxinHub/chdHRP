package com.chd.hrp.ass.serviceImpl.check.other;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.check.other.AssCheckAllocationOtherMapper;
import com.chd.hrp.ass.service.check.other.AssCheckAllocationOtherService;
import com.github.pagehelper.PageInfo;

@Service("assCheckAllocationOtherService")

public class AssCheckAllocationOtherServiceImpl implements AssCheckAllocationOtherService{

	private static Logger logger = Logger.getLogger(AssCheckAllocationOtherServiceImpl.class);

	@Resource(name = "assCheckAllocationOtherMapper")
	private final AssCheckAllocationOtherMapper assCheckAllocationOtherMapper = null;

	@Override
	public String queryAssCheckAllocationOther(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		List<Map<String, Object>> list = null;

		if (sysPage.getTotal() == -1) {
			 if("detail".equals(entityMap.get("searchType").toString())){//明细查询
				list = assCheckAllocationOtherMapper.queryAssCheckAllocationOther(entityMap);
			}
			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			 if("detail".equals(entityMap.get("searchType").toString())){//明细查询
				list = assCheckAllocationOtherMapper.queryAssCheckAllocationOther(entityMap, rowBounds);
			}
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
}
