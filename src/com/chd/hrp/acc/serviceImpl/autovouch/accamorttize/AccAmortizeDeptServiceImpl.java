package com.chd.hrp.acc.serviceImpl.autovouch.accamorttize;

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
import com.chd.hrp.acc.dao.autovouch.accamortize.AccAmortizeDeptMapper;
import com.chd.hrp.acc.service.autovouch.accamortize.AccAmortizeDeptService;

@Service("accAmortizeDeptService")
public class AccAmortizeDeptServiceImpl implements AccAmortizeDeptService {

	private static Logger logger = Logger.getLogger(AccAmortizeDeptServiceImpl.class);

	@Resource(name = "accAmortizeDeptMapper")
	private AccAmortizeDeptMapper accAmortizeDeptMapper;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String saveAmortizeDeptList(Map<String, Object> mapVo) {
		try {
			Set<String> soureSet = new HashSet<String>();
			List<Map> list = JSON.parseArray(mapVo.get("saveList").toString(), Map.class);
			List<Map<String, Object>> saveMap = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> map : list) {
				String dept_id = map.get("dept_id").toString();
				if (soureSet.contains(dept_id)) {
					return "{\"error\":\"添加失败，请确保科室 "+map.get("dept_name")+" 的唯一性!\"}";
				}
				soureSet.add(dept_id);
				map.put("dept_id", Integer.parseInt(dept_id));
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				saveMap.add(map);
			}

			accAmortizeDeptMapper.deleteAmortizeDeptList(saveMap);
			accAmortizeDeptMapper.addBatch(saveMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryAmortizeDeptList(Map<String, Object> mapVo) {
		if (mapVo.get("apply_code") == null || "".equals(mapVo.get("apply_code").toString()) || "编码由系统生成".equals(mapVo.get("apply_code").toString())) {
			return ChdJson.toJson(new ArrayList<Map<String, Object>>());
		}
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> query = (List<Map<String, Object>>) accAmortizeDeptMapper.query(mapVo);
		return ChdJson.toJson(query);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String deleteAmortizeDeptList(Map<String, Object> mapVo) {
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

				accAmortizeDeptMapper.deleteAmortizeDeptList(rmlist);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

}
