package com.chd.hrp.hr.serviceImpl.nursingtraining;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.hrp.hr.dao.nursingtraining.HrEvalSetMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrEvalSet;
import com.chd.hrp.hr.service.nursingtraining.HrEvalSetService;

@Service("hrEvalSetService")
public class HrEvalSetServiceImpl implements HrEvalSetService {

	private static Logger logger = Logger.getLogger(HrEvalSetServiceImpl.class);

	@Resource(name = "hrEvalSetMapper")
	private final HrEvalSetMapper hrEvalSetMapper = null;

	@Override
	public int saveBatchHrEvalSet(List<HrEvalSet> dataList) throws DataAccessException {
		try {
			return hrEvalSetMapper.saveBatchHrEvalSet(dataList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public int deleteBatchHrEvalSet(List<HrEvalSet> dataList) throws DataAccessException {
		try {
			return hrEvalSetMapper.deleteBatchHrEvalSet(dataList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public int deleteHrEvalSet(Map<String, Object> entityMap) throws DataAccessException {
		try {
			return hrEvalSetMapper.deleteHrEvalSet(entityMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<HrEvalSet> queryHrEvalSet(Map<String, Object> entityMap) throws DataAccessException {
		return hrEvalSetMapper.queryHrEvalSet(entityMap);
	}

}
