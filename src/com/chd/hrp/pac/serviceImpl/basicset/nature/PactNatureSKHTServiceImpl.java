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
import com.chd.hrp.pac.dao.basicset.nature.PactNatureSKHTMapper;
import com.chd.hrp.pac.entity.basicset.nature.PactNatureEntity;
import com.chd.hrp.pac.service.basicset.nature.PactNatureSKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactNatureSKHTService")
public class PactNatureSKHTServiceImpl implements PactNatureSKHTService {

	private static Logger logger = Logger.getLogger(PactNatureSKHTServiceImpl.class);

	@Resource(name = "pactNatureSKHTMapper")
	private PactNatureSKHTMapper pactNatureSKHTMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String querySKHTNature(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactNatureEntity> list = (List<PactNatureEntity>) pactNatureSKHTMapper.query(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactNatureEntity> list = (List<PactNatureEntity>) pactNatureSKHTMapper.query(entityMap, rowBounds);
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
	public String addSKHTNature(Map<String, Object> mapVo) {
		try {
			PactNatureEntity entity = pactNatureSKHTMapper.querySKHTNatureByCode(mapVo);
			if (entity != null) {
				return "{\"error\":\"添加失败，编码已存在.\",\"state\":\"true\"}";
			}
			entity = pactNatureSKHTMapper.querySKHTNatureByName(mapVo);
			if (entity != null) {
				if (!entity.getNature_code().equals(mapVo.get("nature_code"))) {
					return "{\"error\":\"添加失败，名称已存在.\",\"state\":\"true\"}";
				}
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("nature_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("nature_name").toString()));
			pactNatureSKHTMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deleteSKHTNature(List<PactNatureEntity> list) {
		try {
			if (!list.isEmpty()) {
				pactNatureSKHTMapper.deleteAllBatch(list);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactNatureEntity querySKHTNatureByCode(Map<String, Object> mapVo) {
		try {
			return pactNatureSKHTMapper.querySKHTNatureByCode(mapVo);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updateSKHTNature(Map<String, Object> mapVo) {
		try {
			PactNatureEntity entity = pactNatureSKHTMapper.querySKHTNatureByName(mapVo);
			if (entity != null) {
				if (!entity.getNature_code().equals(mapVo.get("nature_code"))) {
					return "{\"error\":\"更新失败，名称已存在.\",\"state\":\"true\"}";
				}
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("nature_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("nature_name").toString()));
			pactNatureSKHTMapper.update(mapVo);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
