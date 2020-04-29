package com.chd.hrp.acc.serviceImpl.vouch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.vouch.AccDifferSubjMapper;
import com.chd.hrp.acc.service.vouch.AccDifferSubjService;
import com.github.pagehelper.PageInfo;

@Service("accDifferSubjService")
public class AccDifferSubjServiceImpl implements AccDifferSubjService {

	private static Logger logger = Logger.getLogger(AccDifferSubjServiceImpl.class);

	@Resource(name = "accDifferSubjMapper")
	private AccDifferSubjMapper accDifferSubjMapper;

	@Override
	@SuppressWarnings("unchecked")
	public String queryAccDifferSubj(Map<String, Object> entityMap) {
		try {
			List<Map<String, Object>> query = (List<Map<String, Object>>) accDifferSubjMapper.query(entityMap);
			return ChdJson.toJson(query);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}

	@Override
	public String queryAccDifferSubjForAdd(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();

			sysPage = (SysPage) entityMap.get("sysPage");

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			if ("末级".equals(entityMap.get("subj_level"))) {
				entityMap.put("is_last", 1);
				entityMap.remove("subj_level");
			}
			
			entityMap.put("page_query", 1);
			List<Map<String, Object>> list = accDifferSubjMapper.queryAccDifferSubjForAdd(entityMap, rowBounds);
			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo<>(list);
			return ChdJson.toJson(list, page.getTotal());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}

	@Override
	public String addAccDifferSubj(Map<String, Object> mapVo) {
		try {
			String subjCodes = mapVo.get("subj_code_list").toString();
			List<String> list = JSON.parseArray(subjCodes, String.class);

			Set<String> set = new HashSet<String>(list);
			list = new ArrayList<String>(set);
			mapVo.put("list", list);

			Map<String, Object> mapVo2=new HashMap<String, Object>();
			mapVo2.put("group_id", SessionManager.getGroupId());
			mapVo2.put("hos_id", SessionManager.getHosId());
			mapVo2.put("copy_code",SessionManager.getCopyCode());
			mapVo2.put("diff_type_code", mapVo.get("diff_type_code"));
			mapVo2.put("subj_code", list);
			Integer queryExist = accDifferSubjMapper.queryExistInAccDiffSubjSet(mapVo2);
			if (queryExist > 0) {
				return "{\"error\":\"该差异类别下已存在选中科目.\"}";
			}
			
			accDifferSubjMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}

	@Override
	public String deleteAccDifferSubj(String mapVo) {
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			String[] split = mapVo.split(",");
			for (String string : split) {
				Map<String, Object> map = new HashMap<String, Object>();
				String[] split2 = string.split("@");
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("subj_code", split2[0]);
				map.put("diff_item_code", split2[1]);
				list.add(map);
			}

			accDifferSubjMapper.deleteBatch(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}

}
