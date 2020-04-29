package com.chd.hrp.acc.serviceImpl.autovouch.accamorttize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.autovouch.accamortize.AccAmortizeInfoMapper;
import com.chd.hrp.acc.dao.autovouch.accamortize.AccAmortizeSourceMapper;
import com.chd.hrp.acc.service.autovouch.accamortize.AccAmortizeSourceService;

@Service("accAmortizeSourceService")
public class AccAmortizeSourceServiceImpl implements AccAmortizeSourceService {

	private static Logger logger = Logger.getLogger(AccAmortizeSourceServiceImpl.class);

	@Resource(name = "accAmortizeSourceMapper")
	private AccAmortizeSourceMapper accAmortizeSourceMapper;

	@Resource(name = "accAmortizeInfoMapper")
	private AccAmortizeInfoMapper accAmortizeInfoMapper;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String saveAmortizeSourceList(Map<String, Object> mapVo) {
		try {
			BigDecimal decimal = new BigDecimal(0);
			Set<String> soureSet = new HashSet<String>();
			List<Map> list = JSON.parseArray(mapVo.get("saveList").toString(), Map.class);
			List<Map<String, Object>> saveMap = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : list) {
				String source_id = map.get("source_id").toString();
				soureSet.add(source_id);
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());

				saveMap.add(map);
				decimal = decimal.add(new BigDecimal(map.get("money").toString()));
			}
			if (soureSet.size() != list.size()) {
				return "{\"error\":\"添加失败，请确保资金来源唯一性!\"}";
			}

			mapVo.put("apply_code", saveMap.get(0).get("apply_code"));
			Map<String, Object> map = accAmortizeInfoMapper.queryByCode(mapVo);
			BigDecimal decimal2 = new BigDecimal(map.get("origin_value").toString());
			if (decimal.compareTo(decimal2) != 0) {
				return "{\"error\":\"添加失败，请确保资金金额与原值一致!\"}";
			}

			accAmortizeSourceMapper.deleteAmortizeSourceList(saveMap);
			accAmortizeSourceMapper.addBatch(saveMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryAmortizeSourceList(Map<String, Object> mapVo) {
		if (mapVo.get("apply_code") == null || "".equals(mapVo.get("apply_code").toString())
				|| "编码由系统生成".equals(mapVo.get("apply_code").toString())) {
			return ChdJson.toJson(new ArrayList<Map<String, Object>>());
		}
		List<Map<String, Object>> query = (List<Map<String, Object>>) accAmortizeSourceMapper.query(mapVo);
		return ChdJson.toJson(query);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String deleteAmortizeSourceList(Map<String, Object> mapVo) {
		try {
			List<Map> list = JSON.parseArray(mapVo.get("deleteList").toString(), Map.class);
			if (!list.isEmpty()) {
				List<Map<String, Object>> rmlist = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> map : list) {
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					rmlist.add(map);
				}

				accAmortizeSourceMapper.deleteAmortizeSourceList(rmlist);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

}
