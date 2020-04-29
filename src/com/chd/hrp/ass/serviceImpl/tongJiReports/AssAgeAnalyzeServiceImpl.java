package com.chd.hrp.ass.serviceImpl.tongJiReports;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.tongJiReports.AssAgeAnalyzeMapper;
import com.chd.hrp.ass.entity.card.AssCardSpecial;
import com.chd.hrp.ass.service.tongJiReports.AssAgeAnalyzeService;
import com.github.pagehelper.PageInfo;

@Service("assAgeAnalyzeService")
public class AssAgeAnalyzeServiceImpl implements AssAgeAnalyzeService{
	
	//引入DAO操作
	@Resource(name="assAgeAnalyzeMapper")
	private final AssAgeAnalyzeMapper assAgeAnalyzeMapper = null;
	
	
	private static Logger logger = Logger.getLogger(AssAgeAnalyzeServiceImpl.class);
	@Override
	public String queryAssCardUseYearOrLifeYear(Map<String, Object> mapVo) {
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) mapVo.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String,Object>> listvo =  assAgeAnalyzeMapper.queryAssCardUseYearOrLifeYear(mapVo);

			return ChdJson.toJson(listvo);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String,Object>> listvo =  assAgeAnalyzeMapper.queryAssCardUseYearOrLifeYear(mapVo, rowBounds);

			PageInfo page = new PageInfo(listvo);

			return ChdJson.toJson(listvo, page.getTotal());

		}
	}
	@Override
	public List<Map<String, Object>> queryAssAgeAnalyePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assAgeAnalyzeMapper.queryAssAgeAnalyePrint(entityMap);
	
		return list;
	}

}
