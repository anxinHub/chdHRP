package com.chd.hrp.pac.serviceImpl.basicset.doc;

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
import com.chd.hrp.pac.dao.basicset.doc.PactFileTypeMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactFileTypeEntity;
import com.chd.hrp.pac.service.basicset.doc.PactFileTypeService;
import com.github.pagehelper.PageInfo;

@Service("pactFileTypeService")
public class PactFileTypeServiceImpl implements PactFileTypeService {

	private static Logger logger = Logger.getLogger(PactFileTypeServiceImpl.class);

	@Resource(name = "pactFileTypeMapper")
	private PactFileTypeMapper pactFileTypeMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactFileType(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactFileTypeEntity> list = (List<PactFileTypeEntity>) pactFileTypeMapper.query(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactFileTypeEntity> list = (List<PactFileTypeEntity>) pactFileTypeMapper.query(entityMap, rowBounds);
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
	public String addPactFileType(Map<String, Object> mapVo) {
		try {
			PactFileTypeEntity entity = queryPactFileTypeByCode(mapVo);
			if (entity != null) {
				return "{\"error\":\"添加失败，编码已存在.\",\"state\":\"true\"}";
			}
			entity = pactFileTypeMapper.queryPactFileTypeByName(mapVo);
			if (entity != null) {
				return "{\"error\":\"添加失败，名称已存在.\",\"state\":\"true\"}";
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			pactFileTypeMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactFileType(List<PactFileTypeEntity> list) {
		try {
			if (!list.isEmpty()) {
				pactFileTypeMapper.deleteAllBatch(list);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactFileTypeEntity queryPactFileTypeByCode(Map<String, Object> mapVo) {
		try {
			return pactFileTypeMapper.queryPactFileTypeByCode(mapVo);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactFileType(Map<String, Object> mapVo) {
		try {
			PactFileTypeEntity entity = pactFileTypeMapper.queryPactFileTypeByName(mapVo);
			if (entity != null) {
				if (!entity.getType_code().equals(mapVo.get("type_code"))) {
					return "{\"error\":\"修改失败，名称已存在.\",\"state\":\"true\"}";
				}
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			pactFileTypeMapper.update(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactFileTypeSimple(Map<String, Object> page) {
		try {
			List<PactFileTypeEntity> list = pactFileTypeMapper.queryPactFileTypeForExec(page);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
