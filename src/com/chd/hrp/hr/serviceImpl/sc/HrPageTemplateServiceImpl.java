package com.chd.hrp.hr.serviceImpl.sc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.sc.HrPageTemplateMapper;
import com.chd.hrp.hr.entity.sc.HrPageTemplate;
import com.chd.hrp.hr.service.sc.HrPageTemplateService;


@Service("hrPageTemplateService")
public class HrPageTemplateServiceImpl  implements HrPageTemplateService{

	private static Logger logger = Logger.getLogger(hrPageDesignServiceImpl.class);
	
	// 引入DAO操作
	@Resource(name = "hrPageTemplateMapper")
	private final HrPageTemplateMapper hrPageTemplateMapper = null;

	@Override
	public String queryHrPageTemplate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<HrPageTemplate> list = hrPageTemplateMapper.queryHrPageTemplate(entityMap);
		
		return ChdJson.toJson(list);
		
		
	}
}
