package com.chd.hrp.pac.serviceImpl.pacsysset;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.entity.ModStart;
import com.chd.hrp.sys.service.ModStartService;

@Service("pactModStartService")
public class PactModStartServiceImpl implements ModStartService {
	
	private static Logger logger = Logger.getLogger(PactModStartServiceImpl.class);

	@Resource(name = "modStartMapper")
	private ModStartMapper modStartMapper;

	@Override
	public String addModStart(Map<String, Object> mapVo) {
		try {
			ModStart modStart = modStartMapper.existsModStartByCode(mapVo);
			if (modStart == null) {
				modStart = queryModStartByCode(mapVo);
				if (modStart != null) {
					modStartMapper.updateModStart(mapVo);
				} else {
					modStartMapper.addModStart(mapVo);
				}
				return "{\"msg\":\"启用成功.\",\"state\":\"true\"}";
			} else {
				return "{\"error\":\"不能重复启用.\",\"state\":\"true\"}";
			}
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public String addBatchModStart(List<Map<String, Object>> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String queryModStart(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public ModStart queryModStartByCode(Map<String, Object> entityMap) throws DataAccessException {
		return modStartMapper.queryModStartByCode(entityMap);
	}

	@Override
	public String deleteModStart(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String deleteBatchModStart(List<Map<String, Object>> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String updateModStart(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String updateBatchModStart(List<Map<String, Object>> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String importModStart(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String querySysModStart(Map<String, Object> entityMap) throws DataAccessException {
		return  null;
	}

	public String queryModStartByModeCode(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return modStartMapper.queryModStartByModeCode(mapVo);
	}

}
