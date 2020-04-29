package com.chd.hrp.pac.serviceImpl.basicset.nature;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.pac.dao.basicset.nature.PactNatureFKHTMapper;
import com.chd.hrp.pac.entity.basicset.nature.PactNatureEntity;
import com.chd.hrp.pac.service.basicset.nature.PactNatureFKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactNatureFKHTService")
public class PactNatureFKHTServiceImpl implements PactNatureFKHTService {

	private static Logger logger = Logger.getLogger(PactNatureFKHTServiceImpl.class);

	@Resource(name = "pactNatureFKHTMapper")
	private PactNatureFKHTMapper pactNatureFKHTMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryFKHTNature(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactNatureEntity> list = (List<PactNatureEntity>) pactNatureFKHTMapper.query(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactNatureEntity> list = (List<PactNatureEntity>) pactNatureFKHTMapper.query(entityMap, rowBounds);
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
	public String addFKHTNature(Map<String, Object> mapVo) {
		try {
			PactNatureEntity entity = pactNatureFKHTMapper.queryFKHTNatureByCode(mapVo);
			if (entity != null) {
				return "{\"error\":\"添加失败，编码已存在.\",\"state\":\"true\"}";
			}
			entity = pactNatureFKHTMapper.queryFKHTNatureByName(mapVo);
			if (entity != null) {
				if (!entity.getNature_code().equals(mapVo.get("nature_code"))) {
					return "{\"error\":\"添加失败，名称已存在.\",\"state\":\"true\"}";
				}
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("nature_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("nature_name").toString()));
			pactNatureFKHTMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deleteFKHTNature(List<PactNatureEntity> list) {
		try {
			if (!list.isEmpty()) {
				pactNatureFKHTMapper.deleteAllBatch(list);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactNatureEntity queryFKHTNatureByCode(Map<String, Object> mapVo) {
		try {
			return pactNatureFKHTMapper.queryFKHTNatureByCode(mapVo);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updateFKHTNature(Map<String, Object> mapVo) {
		try {
			PactNatureEntity entity = pactNatureFKHTMapper.queryFKHTNatureByName(mapVo);
			if (entity != null) {
				if (!entity.getNature_code().equals(mapVo.get("nature_code"))) {
					return "{\"error\":\"更新失败，名称已存在.\",\"state\":\"true\"}";
				}
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("nature_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("nature_name").toString()));
			pactNatureFKHTMapper.update(mapVo);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
