package com.chd.hrp.pac.serviceImpl.basicset.common;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.exception.SysException;
import com.chd.hrp.pac.dao.basicset.common.PactSelectMapper;
import com.chd.hrp.pac.service.basicset.common.PactSelectService;

@Service(value = "pactSelectService")
public class PactSelectServiceImpl implements PactSelectService {

	private static Logger logger = Logger.getLogger(PactSelectServiceImpl.class);

	@Resource(name = "pactSelectMapper")
	private PactSelectMapper pactSelectMapper;
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	@Override
	public String queryHosEmpSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> query = pactSelectMapper.queryHosEmpSelect(mapVo);
			return JSONArray.toJSONString(query);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryHosSupSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> query = pactSelectMapper.queryHosSupSelect(mapVo);
			return JSONArray.toJSONString(query);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryHosSourceDictSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryHosSourceDictSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryAccCurSelect(Map<String, Object> mapVo) {
		try {
			mapVo.put("is_stop", 0);
			Calendar calendar = Calendar.getInstance();
			mapVo.put("acc_year", String.valueOf(calendar.get(Calendar.YEAR)));
			List<Map<String, Object>> list = pactSelectMapper.queryAccCurSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryHosProjDictSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryHosProjDictSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryDeptSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryDeptSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactMainFKHTSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryPactMainFKHTSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactFKHTSelectPerm(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryPactFKHTSelectPerm(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	} 
	@Override
	public String queryPactMainSKHTSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryPactMainSKHTSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactFKXYSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryPactFKXYSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactSKXYSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryPactSKXYSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactMatInvDictSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryPactMatInvDictSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactMedInvDictSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryPactMedInvDictSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactAssNoDictSelect(Map<String, Object> mapVo) {
		try {
			String without_id = (String) mapVo.get("without_id");
			if (without_id != null && (!"".equals(without_id))) {
				String[] ids = without_id.split(",");
				StringBuffer buffer = new StringBuffer();
				for (String string : ids) {
					buffer.append("'").append(string).append("',");
				}
				buffer.deleteCharAt(buffer.length() - 1);
				mapVo.put("without_id", buffer.toString());
			}
			List<Map<String, Object>> list = pactSelectMapper.queryPactAssNoDictSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactElseSubDictSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryPactElseSubDictSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactTypeSelect(Map<String, Object> mapVo, String table_type) {
		try {
			mapVo.put("mod_code", "11");
			mapVo.put("is_stop", 0);
			mapVo.put("is_read", 1);
			mapVo.put("table_code", "PACT_TYPE_" + table_type.toUpperCase());
			List<Map<String, Object>> list = pactSelectMapper.queryPactTypeSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactDocTypeSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryPactDocTypeSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPayTypeDict(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryPayTypeDict(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactBankSelectDict(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryPactBankSelectDict(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryAssTypeSelectDict(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryAssTypeSelectDict(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryPayTypeDictBySource(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryPayTypeDictBySource(mapVo);
			return list;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String querySelcetFKHTNature(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.querySelcetFKHTNature(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactStateSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryPactStateSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactPayCondSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryPactPayCondSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryDictSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryDictSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryTypeSKHTNatureSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryTypeSKHTNatureSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryHosCusDictSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryHosCusDictSelect(mapVo);
			return list;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryAssTypeSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryAssTypeSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryHosFacDict(Map<String, Object> mapVo) throws DataAccessException {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryHosFacDict(mapVo);
			
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryAssTendInfo(Map<String, Object> mapVo) throws DataAccessException {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryAssTendInfo(mapVo);
			
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryDeptSelectDict(Map<String, Object> mapVo) throws DataAccessException {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryDeptSelectDict(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryHosSupSelectDict(Map<String, Object> mapVo) 	throws DataAccessException {
		try {
			List<Map<String, Object>> query = pactSelectMapper.queryHosSupSelectDict(mapVo);
			return JSONArray.toJSONString(query);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryHosProjSelectDict(Map<String, Object> mapVo)	throws DataAccessException {
		try {
			List<Map<String, Object>> query = pactSelectMapper.queryHosProjSelectDict(mapVo);
			return JSONArray.toJSONString(query);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	
	@Override
	public String queryPactFKXYSelectPerm(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryPactFKXYSelectPerm(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	
	@Override
	public String queryPactSKXYSelectPerm(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactSelectMapper.queryPactSKXYSelectPerm(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactTemplate(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(pactSelectMapper.queryPactTemplate(entityMap, rowBounds));
	}

@Override 
public String queryBtenDictSelect(Map<String ,Object> mapVo){
	
	
	try{
		List<Map<String ,Object>> list =pactSelectMapper.queryBtenDictSelect(mapVo);
		return JSONArray.toJSONString(list);
		
	}catch(Exception e){
		
		logger.warn(e.getMessage(), e);
		throw new SysException(e.getMessage(),e);
	}
}

@Override
public String queryMatStoreAll(Map<String, Object> mapVo) 	throws DataAccessException {
	try {
		List<Map<String, Object>> query = pactSelectMapper.queryMatStoreAll(mapVo);
		return JSONArray.toJSONString(query);
	} catch (Exception e) {
		logger.warn(e.getMessage(), e);
		throw new SysException(e.getMessage(), e);
	}
}

@Override
public String queryMatType(Map<String, Object> mapVo) 	throws DataAccessException {
	try {
		List<Map<String, Object>> query = pactSelectMapper.queryMatType(mapVo);
		return JSONArray.toJSONString(query);
	} catch (Exception e) {
		logger.warn(e.getMessage(), e);
		throw new SysException(e.getMessage(), e);
	}
}

@Override
public String queryHosUnit(Map<String, Object> mapVo) throws DataAccessException {
	try {
		List<Map<String, Object>> query = pactSelectMapper.queryHosUnit(mapVo);
		return JSONArray.toJSONString(query);
	} catch (Exception e) {
		logger.warn(e.getMessage(), e);
		throw new SysException(e.getMessage(), e);
	}
}

@Override
public String queryDeptNameAndId(Map<String, Object> mapVo) throws DataAccessException {
	try {
		List<Map<String, Object>> query = pactSelectMapper.queryDeptNameAndId(mapVo);
		return JSONArray.toJSONString(query);
	} catch (Exception e) {
		logger.warn(e.getMessage(), e);
		throw new SysException(e.getMessage(), e);
	}
}

}
