package com.chd.hrp.pac.serviceImpl.skht.payment;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.skht.payment.PactCreateRecMentSKHTMapper;
import com.chd.hrp.pac.dao.skht.payment.PactRecDetSKHTMapper;
import com.chd.hrp.pac.dao.skht.payment.PactRecMainSKHTMapper;
import com.chd.hrp.pac.dao.skht.payment.PactRecPlanSKHTMapper;
import com.chd.hrp.pac.service.skht.payment.PactCreateRecMentSKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactCreateRecMentSKHTService")
public class PactCreateRecMentSKHTServiceImpl implements PactCreateRecMentSKHTService {

	private static Logger logger = Logger.getLogger(PactCreateRecMentSKHTServiceImpl.class);

	@Resource(name = "pactCreateRecMentSKHTMapper")
	private PactCreateRecMentSKHTMapper pactCreateRecMentSKHTMapper;

	@Resource(name = "pactRecMainSKHTMapper")
	private PactRecMainSKHTMapper pactRecMainSKHTMapper;

	@Resource(name = "pactRecPlanSKHTMapper")
	private PactRecPlanSKHTMapper pactRecPlanSKHTMapper;

	@Resource(name = "pactRecDetSKHTMapper")
	private PactRecDetSKHTMapper pactRecDetSKHTMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryCreatPactRecMainSKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String, Object>> query = (List<Map<String, Object>>) pactCreateRecMentSKHTMapper
						.query(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = (List<Map<String, Object>>) pactCreateRecMentSKHTMapper
						.query(entityMap, rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addCreatPactRecMainSKHT(List<Map<String, Object>> list) {
		try {
			pactRecMainSKHTMapper.addBatch(list);
			pactRecPlanSKHTMapper.addBatch(list);
			pactRecDetSKHTMapper.addBatch(list);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryCreatPactRecMainSKHTPrint(Map<String, Object> mapVo) {
		try {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> query = (List<Map<String, Object>>) pactCreateRecMentSKHTMapper.query(mapVo);
			return query;
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
