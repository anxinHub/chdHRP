package com.chd.hrp.pac.serviceImpl.pacsysset;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.basicset.type.PactTypeFKHTMapper;
import com.chd.hrp.pac.dao.pacsysset.PactNoRuleMapper;
import com.chd.hrp.pac.entity.pactsysset.PactNoRuleEntity;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;
import com.github.pagehelper.PageInfo;

@Service(value = "pactNoRuleService")
public class PactNoRuleServiceImpl implements PactNoRuleService {

	private static Logger logger = Logger.getLogger(PactNoRuleServiceImpl.class);

	@Resource(name = "pactNoRuleMapper")
	private PactNoRuleMapper pactNoRuleMapper;

	@Resource(name = "pactTypeFKHTMapper")
	private PactTypeFKHTMapper pactTypeFKHTMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryAllPactNoRule(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactNoRuleEntity> list = (List<PactNoRuleEntity>) pactNoRuleMapper.query(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactNoRuleEntity> list = (List<PactNoRuleEntity>) pactNoRuleMapper.query(entityMap, rowBounds);
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
	public String updatePactNoRule(Map<String, Object> mapVo) {
		try {

			int num = pactNoRuleMapper.queryPreExists(mapVo);
			if (num > 0) {
				return "{\"error\":\"存在重复的单据号前缀.\",\"state\":\"false\"}";
			} else {
				int count = pactNoRuleMapper.queryPactNoRuleUsing(mapVo);
				if (count > 0) {
					return "{\"error\":\"编码规则已被使用，不可修改.\",\"state\":\"false\"}";
				} else {
					pactNoRuleMapper.update(mapVo);
					return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
				}
			}
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String getNo(String tableCode, Map<String, Object> mapVo) {
		try {
			StringBuffer no = new StringBuffer();
			mapVo.put("table_code", tableCode);
			PactNoRuleEntity entity = pactNoRuleMapper.queryByCode(mapVo);
			if (entity == null) {
				throw new SysException("编号自动生成失败");
			}
			no.append(entity.getPrefixe());
			if (entity.getIs_type() == 0) {
				String table_type = tableCode.substring(tableCode.lastIndexOf("_"), tableCode.length());
				Object type_code = mapVo.get("pact_type_code");
				String code;
				if (type_code == null) {
					mapVo.put("main_table_name", "PACT_MAIN" + table_type.toUpperCase());
					code = pactNoRuleMapper.queryTypeCodeByPactCode(mapVo);
				} else {
					code = type_code.toString();
				}

				Map<String, Object> map = new HashMap<>();
				map.put("type_code", code);
				map.put("table_code", "PACT_TYPE" + table_type.toUpperCase());
				map.put("group_id", mapVo.get("group_id"));
				map.put("hos_id", mapVo.get("hos_id"));
				map.put("copy_code", mapVo.get("copy_code"));
				map.put("user_id", SessionManager.getUserId());
				map = pactNoRuleMapper.queryPactTypeByCode(map);
				if (map == null || map.isEmpty()) {
					throw new SysException("编号自动生成失败，无合同类型");
				}
				no.append(map.get("mark"));
			}
			Calendar calendar = Calendar.getInstance();
			if (entity.getIs_year() == 0) {
				no.append(calendar.get(Calendar.YEAR));
			}
			if (entity.getIs_month() == 0) {
				int month = calendar.get(Calendar.MONTH) + 1;
				if (month < 10) {
					no.append("0").append(month);
				} else {
					no.append(month);
				}
			}
			Integer seq_no = entity.getSeq_no();
			mapVo.put("no", no.toString());
			mapVo.put("code_length", seq_no);
			mapVo.put("column_code", entity.getCode_colume());
			String code = pactNoRuleMapper.queryMaxId(mapVo);

			if (code == null || code == "") {
				for (int i = 0; i < seq_no - 1; i++) {
					no.append("0");
				}
				no.append("1");
			} else {
				if (seq_no == 0) {
					seq_no = 4;
				}
				Integer seq = Integer.parseInt(code);
				seq = (seq + 1);
				for (int i = 0; i < seq_no - String.valueOf(seq).length(); i++) {
					no.append("0");
				}
				no.append(seq);
			}
			return no.toString();
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
