package com.chd.hrp.acc.serviceImpl.vouch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.vouch.AccDifferMapper;
import com.chd.hrp.acc.dao.vouch.AccDifferSubjMapper;
import com.chd.hrp.acc.service.vouch.AccDifferService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;

@Service("accDifferService")
public class AccDifferServiceImpl implements AccDifferService {

	private static Logger logger = Logger.getLogger(AccDifferServiceImpl.class);

	@Resource(name = "accDifferMapper")
	private AccDifferMapper accDifferMapper;

	@Resource(name = "accDifferSubjMapper")
	private AccDifferSubjMapper accDifferSubjMapper;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	@Override
	public String queryAccDifferItem(Map<String, Object> entityMap) {
		try {
			List<Map<String, Object>> list = accDifferMapper.queryAccDifferItem(entityMap);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}

	@Override
	public Map<String, Object> queryAccDifferItemByCode(Map<String, Object> entityMap) {
		try {
			Map<String, Object> result = accDifferMapper.queryAccDifferItemByCode(entityMap);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}

	@Override
	public String addAccDifferItem(Map<String, Object> entityMap) {
		try {
			Object type_code = entityMap.get("diff_type_code");
			if (type_code == null) {
				return "{\"error\":\"获取类别编码失败.\"}";
			}
			Map<String, Object> result = accDifferMapper.queryAccDifferItemByCode(entityMap);
			if (result != null) {
				return "{\"error\":\"项目编码重复.\"}";
			}
			
			Map<String,Object> checkMap=new HashMap<String, Object>();
			checkMap.put("group_id",entityMap.get("group_id"));
			checkMap.put("hos_id", entityMap.get("hos_id"));
			checkMap.put("copy_code", entityMap.get("copy_code"));
			checkMap.put("field_table","acc_diff_item");
			checkMap.put("field_key1", "diff_type_code");
			checkMap.put("field_value1", entityMap.get("diff_type_code"));
			checkMap.put("field_key2", "is_default");
			checkMap.put("field_value2", 1);
			int count=sysFunUtilMapper.existsSysCodeNameByAdd(checkMap);
			if (count>0 && "1".equals(entityMap.get("is_default").toString())) {
				return "{\"error\":\"一个类别只能设置一个默认项目.\"}";
			}

			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("diff_item_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("diff_item_name").toString()));
			accDifferMapper.addAccDifferItem(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}

	@Override
	public String updateAccDifferItem(Map<String, Object> entityMap) {
		try {
			
			Map<String,Object> checkMap=new HashMap<String, Object>();
			checkMap.put("group_id",entityMap.get("group_id"));
			checkMap.put("hos_id", entityMap.get("hos_id"));
			checkMap.put("copy_code", entityMap.get("copy_code"));
			checkMap.put("field_table","acc_diff_item");
			checkMap.put("field_key1", "diff_type_code");
			checkMap.put("field_value1", entityMap.get("diff_type_code"));
			checkMap.put("field_key2", "is_default");
			checkMap.put("field_value2", 1);
			int count=sysFunUtilMapper.existsSysCodeNameByAdd(checkMap);
			if (count>0 && "1".equals(entityMap.get("is_default").toString())) {
				return "{\"error\":\"一个类别只能设置一个默认项目.\"}";
			}
			
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("diff_item_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("diff_item_name").toString()));
			accDifferMapper.updateAccDifferItem(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}

	@Override
	public String deleteAccDifferItem(String deleteStr) {
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			String[] split = deleteStr.split(",");
			for (String diff_item_code : split) {
				Map<String, Object> entityMap = new HashMap<>();
				entityMap.put("group_id", SessionManager.getGroupId());
				entityMap.put("hos_id", SessionManager.getHosId());
				entityMap.put("copy_code", SessionManager.getCopyCode());
				entityMap.put("diff_item_code", diff_item_code);

				Integer existNum = accDifferSubjMapper.queryExistInAccDiffSubjSet(entityMap);
				if (existNum > 0) {
					return "{\"error\":\"该项目存在关联数据，请清空关联数据后重试.\"}";
				} else {
					list.add(entityMap);
				}
			}

			accDifferMapper.deleteAccDifferItem(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}

	@Override
	public String queryAccDifferType(Map<String, Object> entityMap) {
		try {
			List<Map<String, Object>> list = accDifferMapper.queryAccDifferType(entityMap);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}

	@Override
	public String addAccDifferType(Map<String, Object> entityMap) {
		try {
			Map<String, Object> count = accDifferMapper.queryAccDifferTypeByCode(entityMap);
			if (count != null) {
				return "{\"error\":\"类别编码重复.\"}";
			}

			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("diff_type_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("diff_type_name").toString()));

			accDifferMapper.addAccDifferType(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}

	@Override
	public String deleteAccDifferType(Map<String, Object> entityMap) {
		try {
			List<Map<String, Object>> list = accDifferMapper.queryAccDifferItem(entityMap);
			if (!list.isEmpty()) {
				return "{\"error\":\"该类别存在关联数据，请清空关联数据后重试.\"}";
			}
			accDifferMapper.deleteAccDifferType(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}

	@Override
	public Map<String, Object> queryAccDifferTypeByCode(Map<String, Object> entityMap) {
		try {
			Map<String, Object> result = accDifferMapper.queryAccDifferTypeByCode(entityMap);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}

	@Override
	public String updateAccDifferType(Map<String, Object> entityMap) {
		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("diff_type_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("diff_type_name").toString()));
			accDifferMapper.updateAccDifferType(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}

	@Override
	public String queryDifferItemTree(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = accDifferMapper.queryDifferItemTree(mapVo);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}

}
