package com.chd.hrp.pac.serviceImpl.basicset.elsesub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.pac.dao.basicset.elsesub.PactElseSubDictMapper;
import com.chd.hrp.pac.dao.basicset.elsesub.PactElseSubMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactFileTypeEntity;
import com.chd.hrp.pac.entity.basicset.elsesub.PactElseSubDictEntity;
import com.chd.hrp.pac.entity.basicset.elsesub.PactElseSubEntity;
import com.chd.hrp.pac.service.basicset.elsesub.PactElseSubService;
import com.github.pagehelper.PageInfo;

@Service("pactElseSubService")
public class PactElseSubServiceImpl implements PactElseSubService {

	private static Logger logger = Logger.getLogger(PactElseSubServiceImpl.class);

	@Resource(name = "pactElseSubMapper")
	private PactElseSubMapper pactElseSubMapper;

	@Resource(name = "pactElseSubDictMapper")
	private PactElseSubDictMapper pactElseSubDictMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactElseSub(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactElseSubDictEntity> list = (List<PactElseSubDictEntity>) pactElseSubDictMapper.query(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactFileTypeEntity> list = (List<PactFileTypeEntity>) pactElseSubDictMapper.query(entityMap, rowBounds);
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
	public String addPactElseSub(Map<String, Object> mapVo) {
		try {
			PactElseSubEntity entity = queryPactElseSubByCode(mapVo);
			if (entity != null) {
				return "{\"error\":\"添加失败，编码已存在.\",\"state\":\"true\"}";
			}
			entity = pactElseSubMapper.queryPactElseSubByName(mapVo);
			if (entity != null) {
					return "{\"error\":\"添加失败，名称已存在.\",\"state\":\"true\"}";
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("sub_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("sub_name").toString()));
			pactElseSubMapper.add(mapVo);
			mapVo.put("is_new", 0);
			pactElseSubDictMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactElseSub(List<PactElseSubDictEntity> list) {
		try {
			if (!list.isEmpty()) {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("list", list);
				Integer queryUsing = pactElseSubMapper.queryUsing(map);
				if (queryUsing > 0) {
					return "{\"error\":\"删除失败，数据已占用.\",\"state\":\"true\"}";
				}
				pactElseSubMapper.deleteAllBatch(map);
				pactElseSubDictMapper.deleteAllBatch(map);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactElseSubDictEntity queryPactElseSubByCode(Map<String, Object> mapVo) {
		try {
			return pactElseSubDictMapper.queryPactElseSubByCode(mapVo);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactElseSub(Map<String, Object> mapVo) {
		try {
			PactElseSubEntity entity = pactElseSubMapper.queryPactElseSubByName(mapVo);
			if (entity != null) {
				if (!entity.getSub_code().equals(mapVo.get("sub_code"))) {
					return "{\"error\":\"修改失败，名称已存在.\",\"state\":\"true\"}";
				}
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("sub_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("sub_name").toString()));
			pactElseSubMapper.update(mapVo);
			mapVo.put("is_new", 1);
			mapVo.put("is_stop", 1);
			pactElseSubDictMapper.update(mapVo);
			mapVo.put("is_stop", 0);
			mapVo.put("is_new", 0);
			pactElseSubDictMapper.addNew(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
