package com.chd.hrp.pac.serviceImpl.basicset.paycond;

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
import com.chd.hrp.pac.dao.basicset.paycond.PactPayCondMapper;
import com.chd.hrp.pac.entity.basicset.paycond.PactPayCondEntity;
import com.chd.hrp.pac.service.basicset.paycond.PactPayCondService;
import com.github.pagehelper.PageInfo;

@Service("pactPayCondService")
public class PactPayCondServiceImpl implements PactPayCondService {

	private static Logger logger = Logger.getLogger(PactPayCondServiceImpl.class);

	@Resource(name = "pactPayCondMapper")
	private PactPayCondMapper pactPayCondMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactPayCond(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactPayCondEntity> list = (List<PactPayCondEntity>) pactPayCondMapper.query(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactPayCondEntity> list = (List<PactPayCondEntity>) pactPayCondMapper.query(entityMap, rowBounds);
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
	public String addPactPayCond(Map<String, Object> mapVo) {
		try {
			PactPayCondEntity entity = queryPactPayCondByCode(mapVo);
			if (entity != null) {
				return "{\"error\":\"添加失败，编码已存在.\",\"state\":\"true\"}";
			}
			entity = pactPayCondMapper.queryPactPayCondByName(mapVo);
			if (entity != null) {
					return "{\"error\":\"添加失败，名称已存在.\",\"state\":\"true\"}";
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cond_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cond_name").toString()));
			pactPayCondMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactPayCond(List<PactPayCondEntity> list) {
		try {
			if (!list.isEmpty()) {
				pactPayCondMapper.deleteAllBatch(list);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactPayCondEntity queryPactPayCondByCode(Map<String, Object> mapVo) {
		try {
			return pactPayCondMapper.queryPactPayCondByCode(mapVo);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactPayCond(Map<String, Object> mapVo) {
		try {
			PactPayCondEntity entity = pactPayCondMapper.queryPactPayCondByName(mapVo);
			if (entity != null) {
				if (!entity.getCond_code().equals(mapVo.get("cond_code"))) {
					return "{\"error\":\"修改失败，名称已存在.\",\"state\":\"true\"}";
				}
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cond_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cond_name").toString()));
			pactPayCondMapper.update(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
