package com.chd.hrp.pac.serviceImpl.basicset.type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.pac.dao.basicset.type.PactTypeFKHTMapper;
import com.chd.hrp.pac.dao.basicset.warning.PactWarnSetFKHTMapper;
import com.chd.hrp.pac.entity.basicset.type.PactTypeFKHTEntity;
import com.chd.hrp.pac.entity.basicset.warning.PactWarnSetFKHTEntity;
import com.chd.hrp.pac.service.basicset.type.PactTypeFKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactTypeFKHTService")
public class PactTypeFKHTServiceImpl implements PactTypeFKHTService {

	private static Logger logger = Logger.getLogger(PactTypeFKHTServiceImpl.class);

	@Resource(name = "pactTypeFKHTMapper")
	private PactTypeFKHTMapper pactTypeFKHTMapper;

	@Resource(name = "pactWarnSetFKHTMapper")
	private PactWarnSetFKHTMapper pactWarnSetFKHTMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactTypeFKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactTypeFKHTEntity> list = (List<PactTypeFKHTEntity>) pactTypeFKHTMapper.query(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactTypeFKHTEntity> list = (List<PactTypeFKHTEntity>) pactTypeFKHTMapper.query(entityMap,
						rowBounds);
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
	public String deletePactTypeFKHT(List<PactTypeFKHTEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactTypeFKHTMapper.deleteAllBatch(listVo);
				Set<String> set = new HashSet<String>();
				for (PactTypeFKHTEntity entity : listVo) {
					String type_code = entity.getType_code();
					set.add(type_code);
				}
				Integer group_id = listVo.get(0).getGroup_id();
				Integer hos_id = listVo.get(0).getHos_id();
				String copy_code = listVo.get(0).getCopy_code();
				List<PactWarnSetFKHTEntity> entityMap = new ArrayList<PactWarnSetFKHTEntity>();
				for (String pact_type_code : set) {
					PactWarnSetFKHTEntity entity = new PactWarnSetFKHTEntity();
					entity.setPact_type_code(pact_type_code);
					entity.setGroup_id(group_id);
					entity.setHos_id(hos_id);
					entity.setCopy_code(copy_code);
					entityMap.add(entity);
				}

				pactWarnSetFKHTMapper.deleteAllBatch(entityMap);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addPactTypeFKHT(Map<String, Object> mapVo) {
		try {
			PactTypeFKHTEntity entity = pactTypeFKHTMapper.queryPactTypeFKHTByCode(mapVo);
			if (entity != null) {
				return "{\"error\":\"添加失败，编码已存在.\",\"state\":\"true\"}";
			}
			entity = pactTypeFKHTMapper.queryPactTypeFKHTByName(mapVo);
			if (entity != null) {
				return "{\"error\":\"添加失败，名称已存在.\",\"state\":\"true\"}";
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			mapVo.put("dept_no", mapVo.get("dept_id"));
			pactTypeFKHTMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactTypeFKHTEntity queryPactTypeFKHTByCode(Map<String, Object> mapVo) {
		try {
			return pactTypeFKHTMapper.queryPactTypeFKHTByCode(mapVo);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactTypeFKHT(Map<String, Object> mapVo) {
		try {
			PactTypeFKHTEntity entity = pactTypeFKHTMapper.queryPactTypeFKHTByName(mapVo);
			if (entity != null) {
				if (!entity.getType_code().equals(mapVo.get("type_code"))) {
					return "{\"error\":\"修改失败，名称已存在.\",\"state\":\"true\"}";
				}
			}

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			pactTypeFKHTMapper.update(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public int qeuryPactTypeCode(Map<String, Object> mapVo) {
		try {
			return pactTypeFKHTMapper.qeuryPactTypeCode(mapVo);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
