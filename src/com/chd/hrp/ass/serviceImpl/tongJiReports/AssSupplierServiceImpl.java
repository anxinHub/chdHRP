package com.chd.hrp.ass.serviceImpl.tongJiReports;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.tongJiReports.AssSupplierMapper;
import com.chd.hrp.ass.service.tongJiReports.AssSupplierService;
import com.github.pagehelper.PageInfo;


@Service("assSupplierService")
public class AssSupplierServiceImpl  implements AssSupplierService {    
	
	@Resource(name="assSupplierMapper")
	private final AssSupplierMapper assSupplierMapper = null;

	@Override
	public String queryAssSupplier(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = null;

		list = assSupplierMapper.queryAssSupplier(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());
	}

	//打印
	@Override
	public List<Map<String, Object>> queryAssSupplierPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assSupplierMapper.queryAssSupplierPrint(entityMap);
	
		return list;
	}
}
