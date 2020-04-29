package com.chd.hrp.cost.serviceImpl.analysis.c10;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.analysis.c10.C10Mapper;
import com.chd.hrp.cost.service.analysis.c10.C10Service;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 指标分析服务实现类<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("c10Service")
public class C10ServiceImpl implements C10Service{

	private static Logger logger = Logger.getLogger(C10ServiceImpl.class);

	@Resource(name = "c10Mapper")
	private final C10Mapper analysisMapper = null;

	@Override
	public String queryAnalysisC1001(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC1001(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC1001(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String addAnalysisC1001(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		analysisMapper.deleteAnalysisC1001(entityMap);
		try {
			
			analysisMapper.addAnalysisC1001(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC1001\"}";

		}
		
	}
	@Override
	public List<Map<String, Object>> queryC1001Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC1001Print(entityMap);
		
		return list;

	}

}
