package com.chd.hrp.pac.serviceImpl.basicset.doc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.pac.dao.basicset.doc.PactDocTypeMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactDocTypeEntity;
import com.chd.hrp.pac.service.basicset.doc.PactDocTypeService;
import com.github.pagehelper.PageInfo;

@Service("pactDocTypeService")
public class PactDocTypeServiceImpl implements PactDocTypeService {

	private static Logger logger = Logger.getLogger(PactDocTypeServiceImpl.class);

	@Resource(name = "pactDocTypeMapper")
	private PactDocTypeMapper pactDocTypeMapper;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String queryPactDocType(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactDocTypeEntity> list = (List<PactDocTypeEntity>) pactDocTypeMapper.query(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactDocTypeEntity> list = (List<PactDocTypeEntity>) pactDocTypeMapper.query(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addPactDocType(Map<String, Object> mapVo) {
		try {
			PactDocTypeEntity entity = queryPactDocTypeByCode(mapVo);
			if (entity != null) {
				return "{\"error\":\"添加失败，编码已存在.\",\"state\":\"true\"}";
			}
			entity = pactDocTypeMapper.queryPactDocTypeByName(mapVo);
			if (entity != null) {
				return "{\"error\":\"添加失败，名称已存在.\",\"state\":\"true\"}";
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			pactDocTypeMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactDocType(List<PactDocTypeEntity> list) {
		try {
			if (!list.isEmpty()) {
				pactDocTypeMapper.deleteAllBatch(list);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactDocTypeEntity queryPactDocTypeByCode(Map<String, Object> mapVo) {
		try {
			return pactDocTypeMapper.queryPactDocTypeByCode(mapVo);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactDocType(Map<String, Object> mapVo) {
		try {
			PactDocTypeEntity entity = pactDocTypeMapper.queryPactDocTypeByName(mapVo);
			if (entity != null) {
				if (!entity.getType_code().equals(mapVo.get("type_code"))) {
					return "{\"error\":\"修改失败，名称已存在.\",\"state\":\"true\"}";
				}
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			pactDocTypeMapper.update(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactDocTypeSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactDocTypeMapper.queryPactDocTypeSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
