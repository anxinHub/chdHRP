package com.chd.hrp.pac.serviceImpl.fkxy.logistics;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.fkxy.logistics.PactLogisticsProtocolMapper;
import com.chd.hrp.pac.service.fkxy.logistics.PactLogisticsProtocolService;

@Service("pactLogisticsProtocolService")
public class PactLogisticsProtocolServiceImpl implements PactLogisticsProtocolService {

	@Resource(name = "pactLogisticsProtocolMapper")
	private PactLogisticsProtocolMapper pactLogisticsProtocolMapper;

	@Override
	public String queryPactProtocalSummaryFKXY(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactLogisticsProtocolMapper.queryPactProtocalSummaryFKXY(mapVo);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryPactProtocolDetailFKXY(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactLogisticsProtocolMapper.queryPactProtocolDetailFKXY(mapVo);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryPactProtocalSummaryFKXYPrint(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactLogisticsProtocolMapper.queryPactProtocalSummaryFKXY(mapVo);
			return list;
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryPactProtocolDetailFKXYPrint(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactLogisticsProtocolMapper.queryPactProtocolDetailFKXY(mapVo);
			return list;
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

}
