package com.chd.hrp.pac.serviceImpl.skht.pactinfo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.skht.pactinfo.PactPlanSKHTMapper;
import com.chd.hrp.pac.entity.skht.pactinfo.PactPlanSKHTEntity;
import com.chd.hrp.pac.service.skht.pactinfo.PactPlanSKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactPlanSKHTService")
public class PactPlanSKHTServiceImpl implements PactPlanSKHTService {

	private static Logger logger = Logger.getLogger(PactPlanSKHTServiceImpl.class);

	@Resource(name = "pactPlanSKHTMapper")
	private PactPlanSKHTMapper pactPlanSKHTMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactPlanSKHT(Map<String, Object> mapVo) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) mapVo.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactPlanSKHTEntity> query = (List<PactPlanSKHTEntity>) pactPlanSKHTMapper.query(mapVo);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactPlanSKHTEntity> query = (List<PactPlanSKHTEntity>) pactPlanSKHTMapper.query(mapVo, rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page = new PageInfo(query);
				return ChdJson.toJson(query, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactPlanSKHT(List<PactPlanSKHTEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactPlanSKHTMapper.deleteAllBatch(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactPlanSKHTForPay(Map<String, Object> mapVo) {
		try {
			List<PactPlanSKHTEntity> query = pactPlanSKHTMapper.queryForPay(mapVo);
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	@Override
	public String queryPactPlanSKHTForEdit(Map<String, Object> mapVo) {
		try {
			List<PactPlanSKHTEntity> query = pactPlanSKHTMapper.queryForEdit(mapVo);
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
