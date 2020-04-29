package com.chd.hrp.pac.serviceImpl.basicset.state;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.basicset.state.PactStateMapper;
import com.chd.hrp.pac.entity.basicset.state.PactStateEntity;
import com.chd.hrp.pac.service.basicset.state.PactStateService;
import com.github.pagehelper.PageInfo;

@Service("pactStateService")
public class PactStateServiceImpl implements PactStateService {

	private static Logger logger = Logger.getLogger(PactStateServiceImpl.class);

	@Resource(name = "pactStateMapper")
	private PactStateMapper pactStateMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactState(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactStateEntity> list = (List<PactStateEntity>) pactStateMapper.query(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactStateEntity> list = (List<PactStateEntity>) pactStateMapper.query(entityMap, rowBounds);
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
	public String addPactState(Map<String, Object> page) {
		try {
			PactStateEntity entity = pactStateMapper.queryPactStateByCode(page);
			if (entity != null) {
				return "{\"error\":\"添加失败，编码已存在.\",\"state\":\"true\"}";
			}
			entity = pactStateMapper.queryPactStatByName(page);
			if (entity != null) {
				return "{\"error\":\"添加失败，名称已存在.\",\"state\":\"true\"}";
			}
			pactStateMapper.add(page);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactState(List<PactStateEntity> list) {
		try {
			if (!list.isEmpty()) {
				pactStateMapper.deleteAllBatch(list);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactStateEntity queryPactStateByCode(Map<String, Object> mapVo) {
		try {
			return pactStateMapper.queryPactStateByCode(mapVo);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactState(Map<String, Object> mapVo) {
		try {
			PactStateEntity entity = pactStateMapper.queryPactStatByName(mapVo);
			if (entity != null) {
				if (!entity.getState_code().equals(mapVo.get("state_code"))) {
					return "{\"error\":\"修改失败，名称已存在.\",\"state\":\"true\"}";
				}
			}
			pactStateMapper.update(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
