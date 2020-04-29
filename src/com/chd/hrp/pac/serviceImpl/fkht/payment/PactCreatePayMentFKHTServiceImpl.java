package com.chd.hrp.pac.serviceImpl.fkht.payment;

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
import com.chd.hrp.pac.dao.fkht.payment.PactCreatePayMentFKHTMapper;
import com.chd.hrp.pac.dao.fkht.payment.PactPayDetFKHTMapper;
import com.chd.hrp.pac.dao.fkht.payment.PactPayMainFKHTMapper;
import com.chd.hrp.pac.dao.fkht.payment.PactPayPlanFKHTMapper;
import com.chd.hrp.pac.service.fkht.payment.PactCreatePayMentFKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactCreatePayMentFKHTService")
public class PactCreatePayMentFKHTServiceImpl implements PactCreatePayMentFKHTService {

	private static Logger logger = Logger.getLogger(PactCreatePayMentFKHTServiceImpl.class);

	@Resource(name = "pactCreatePayMentFKHTMapper")
	private PactCreatePayMentFKHTMapper pactCreatePayMentFKHTMapper;

	@Resource(name = "pactPayMainFKHTMapper")
	private PactPayMainFKHTMapper pactPayMainFKHTMapper;

	@Resource(name = "pactPayPlanFKHTMapper")
	private PactPayPlanFKHTMapper pactPayPlanFKHTMapper;

	@Resource(name = "pactPayDetFKHTMapper")
	private PactPayDetFKHTMapper pactPayDetFKHTMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryCreatPactPayMainFKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String, Object>> query = (List<Map<String, Object>>) pactCreatePayMentFKHTMapper
						.query(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = (List<Map<String, Object>>) pactCreatePayMentFKHTMapper
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
	public String addCreatPactPayMainFKHT(List<Map<String, Object>> list) {
		try {
			if (!list.isEmpty()) {
				pactPayMainFKHTMapper.addBatch(list);
				pactPayPlanFKHTMapper.addBatch(list);
				pactPayDetFKHTMapper.addBatch(list);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryCreatPactPayMainFKHTPrint(Map<String, Object> mapVo) {
		try {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> query = (List<Map<String, Object>>) pactCreatePayMentFKHTMapper.query(mapVo);
			return query;
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
