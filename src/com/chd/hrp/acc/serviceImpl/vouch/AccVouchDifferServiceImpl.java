package com.chd.hrp.acc.serviceImpl.vouch;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.vouch.AccVouchDifferMapper;
import com.chd.hrp.acc.service.vouch.AccVouchDifferService;

@Service("accVouchDifferService")
public class AccVouchDifferServiceImpl implements AccVouchDifferService {

	private static Logger logger = Logger.getLogger(AccVouchDifferServiceImpl.class);

	@Resource(name = "accVouchDifferMapper")
	private AccVouchDifferMapper accVouchDifferMapper;

	@Override
	public String queryAccDifferQuery(Map<String, Object> entityMap) {
		try {
			int pay_type_code = Integer.parseInt(entityMap.get("pay_type_code").toString());
			if (pay_type_code == 1) {
				List<Map<String, Object>> list = accVouchDifferMapper.queryDataByVouch(entityMap);
				return ChdJson.toJson(list);
			} else {
				List<Map<String, Object>> list = accVouchDifferMapper.queryDataByDiffer(entityMap);
				return ChdJson.toJson(list);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public List<Map<String, Object>> queryAccDifferQueryPrint(Map<String, Object> entityMap) {
		try {
			int pay_type_code = Integer.parseInt(entityMap.get("pay_type_code").toString());
			if (pay_type_code == 1) {
				List<Map<String, Object>> list = accVouchDifferMapper.queryDataByVouch(entityMap);
				return list;
			} else {
				List<Map<String, Object>> list = accVouchDifferMapper.queryDataByDiffer(entityMap);
				return list;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e.getCause());
		}
	}

}
