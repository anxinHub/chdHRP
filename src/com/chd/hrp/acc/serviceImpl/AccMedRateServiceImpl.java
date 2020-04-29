package com.chd.hrp.acc.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccMedRateMapper;
import com.chd.hrp.acc.service.AccMedRateService;
import com.github.pagehelper.PageInfo;

@Service("accMedRateService")
public class AccMedRateServiceImpl implements AccMedRateService {

	@Resource(name = "accMedRateMapper")
	private final AccMedRateMapper accMedRateMapper = null;

	@Override
	public String queryMedRate(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = accMedRateMapper.queryMedRate(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = accMedRateMapper.queryMedRate(entityMap, rowBounds);

			@SuppressWarnings("all")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

}
