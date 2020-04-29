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
import com.chd.hrp.pac.dao.basicset.type.PactTypeSKXYMapper;
import com.chd.hrp.pac.dao.basicset.warning.PactWarnSetSKXYMapper;
import com.chd.hrp.pac.entity.basicset.type.PactTypeSKXYEntity;
import com.chd.hrp.pac.entity.basicset.warning.BaseWarnEntity;
import com.chd.hrp.pac.service.basicset.type.PactTypeSKXYService;
import com.github.pagehelper.PageInfo;

@Service("pactTypeSKXYService")
public class PactTypeSKXYServiceImpl implements PactTypeSKXYService {

	private static Logger logger = Logger.getLogger(PactTypeSKXYServiceImpl.class);

	@Resource(name = "pactTypeSKXYMapper")
	private PactTypeSKXYMapper pactTypeSKXYMapper;

	@Resource(name = "pactWarnSetSKXYMapper")
	private PactWarnSetSKXYMapper pactWarnSetSKXYMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactTypeSKXY(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactTypeSKXYEntity> list = (List<PactTypeSKXYEntity>) pactTypeSKXYMapper.query(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactTypeSKXYEntity> list = (List<PactTypeSKXYEntity>) pactTypeSKXYMapper.query(entityMap,
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
	public String deletePactTypeSKXY(List<PactTypeSKXYEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactTypeSKXYMapper.deleteAllBatch(listVo);

				Set<String> set = new HashSet<String>();
				for (PactTypeSKXYEntity entity : listVo) {
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
				pactWarnSetSKXYMapper.deleteAllBatch(entityMap);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addPactTypeSKXY(Map<String, Object> mapVo) {
		try {
			PactTypeSKXYEntity entity = pactTypeSKXYMapper.queryPactTypeSKXYByCode(mapVo);
			if (entity != null) {
				return "{\"error\":\"添加失败，编码已存在.\",\"state\":\"true\"}";
			}
			entity = pactTypeSKXYMapper.queryPactTypeSKXYByName(mapVo);
			if (entity != null) {
				return "{\"error\":\"添加失败，名称已存在.\",\"state\":\"true\"}";
			}
			
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			pactTypeSKXYMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactTypeSKXYEntity queryPactTypeSKXYByCode(Map<String, Object> mapVo) {
		try {
			return pactTypeSKXYMapper.queryPactTypeSKXYByCode(mapVo);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactTypeSKXY(Map<String, Object> mapVo) {
		try {
			PactTypeSKXYEntity entity = pactTypeSKXYMapper.queryPactTypeSKXYByName(mapVo);
			if (entity != null) {
				if (!entity.getType_code().equals(mapVo.get("type_code"))) {
					return "{\"error\":\"修改失败，名称已存在.\",\"state\":\"true\"}";
				}
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			pactTypeSKXYMapper.update(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public int qeuryPactTypeCode(Map<String, Object> mapVo) {
		try {
			return pactTypeSKXYMapper.qeuryPactTypeCode(mapVo);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
