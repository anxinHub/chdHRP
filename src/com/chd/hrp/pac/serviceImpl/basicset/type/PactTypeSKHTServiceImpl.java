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
import com.chd.hrp.pac.dao.basicset.type.PactTypeSKHTMapper;
import com.chd.hrp.pac.dao.basicset.warning.PactWarnSetSKHTMapper;
import com.chd.hrp.pac.entity.basicset.type.PactTypeSKHTEntity;
import com.chd.hrp.pac.entity.basicset.warning.BaseWarnEntity;
import com.chd.hrp.pac.service.basicset.type.PactTypeSKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactTypeSKHTService")
public class PactTypeSKHTServiceImpl implements PactTypeSKHTService {

	private static Logger logger = Logger.getLogger(PactTypeSKHTServiceImpl.class);

	@Resource(name = "pactTypeSKHTMapper")
	private PactTypeSKHTMapper pactTypeSKHTMapper;

	@Resource(name = "pactWarnSetSKHTMapper")
	private PactWarnSetSKHTMapper pactWarnSetSKHTMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactTypeSKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactTypeSKHTEntity> list = (List<PactTypeSKHTEntity>) pactTypeSKHTMapper.query(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactTypeSKHTEntity> list = (List<PactTypeSKHTEntity>) pactTypeSKHTMapper.query(entityMap, rowBounds);
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
	public String deletePactTypeSKHT(List<PactTypeSKHTEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactTypeSKHTMapper.deleteAllBatch(listVo);

				Set<String> set = new HashSet<String>();
				for (PactTypeSKHTEntity entity : listVo) {
					String type_code = entity.getType_code();
					set.add(type_code);
				}
				Integer group_id = listVo.get(0).getGroup_id();
				Integer hos_id = listVo.get(0).getHos_id();
				String copy_code = listVo.get(0).getCopy_code();
				List<BaseWarnEntity> entityMap = new ArrayList<BaseWarnEntity>();
				for (String pact_type_code : set) {
					BaseWarnEntity entity = new BaseWarnEntity();
					entity.setPact_type_code(pact_type_code);
					entity.setGroup_id(group_id);
					entity.setHos_id(hos_id);
					entity.setCopy_code(copy_code);
					entityMap.add(entity);
				}
				pactWarnSetSKHTMapper.deleteAllBatch(entityMap);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addPactTypeSKHT(Map<String, Object> mapVo) {
		try {
			PactTypeSKHTEntity entity = pactTypeSKHTMapper.queryPactTypesSKHTByCode(mapVo);
			if (entity != null) {
				return "{\"error\":\"添加失败，编码已存在.\",\"state\":\"true\"}";
			}
			entity = pactTypeSKHTMapper.queryPactTypesSKHTByName(mapVo);
			if (entity != null) {
				return "{\"error\":\"添加失败，名称已存在.\",\"state\":\"true\"}";
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			pactTypeSKHTMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactTypeSKHTEntity queryPactTypesSKHTByCode(Map<String, Object> mapVo) {
		try {
			return pactTypeSKHTMapper.queryPactTypesSKHTByCode(mapVo);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactTypeSKHT(Map<String, Object> mapVo) {
		try {
			PactTypeSKHTEntity entity = pactTypeSKHTMapper.queryPactTypesSKHTByName(mapVo);
			if (entity != null) {
				if(!entity.getType_code().equals(mapVo.get("type_code"))) {
					return "{\"error\":\"修改失败，名称已存在.\",\"state\":\"true\"}";
				}
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			pactTypeSKHTMapper.update(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public int qeuryPactTypeCode(Map<String, Object> mapVo) {
		try {
			return pactTypeSKHTMapper.qeuryPactTypeCode(mapVo);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
