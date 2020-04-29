package com.chd.hrp.pac.serviceImpl.skxy.pactinfo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.skxy.pactinfo.PactDetSKXYMapper;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactDetSKXYEntity;
import com.chd.hrp.pac.service.skxy.pactinfo.PactDetSKXYService;
import com.github.pagehelper.PageInfo;

@Service("pactDetSKXYService")
public class PactDetSKXYServiceImpl implements PactDetSKXYService {

	private static Logger logger = Logger.getLogger(PactDetSKXYServiceImpl.class);

	@Resource(name = "pactDetSKXYMapper")
	private PactDetSKXYMapper pactDetSKXYMapper;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String queryPactDetSKXY(Map<String, Object> mapVo) {
		try {
			if (mapVo.get("change_code") == null) {
				mapVo.put("table_code", "PACT_DET_SKXY");
			} else {
				mapVo.put("table_code", "PACT_DET_SKXY_C");
			}
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) mapVo.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactDetSKXYEntity> list = (List<PactDetSKXYEntity>) pactDetSKXYMapper.query(mapVo);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactDetSKXYEntity> list = (List<PactDetSKXYEntity>) pactDetSKXYMapper.query(mapVo, rowBounds);
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactDetSKXY(List<PactDetSKXYEntity> list) {
		try {
			pactDetSKXYMapper.updateBatch(list);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactDetSKXY(List<PactDetSKXYEntity> list) {
		try {
			pactDetSKXYMapper.deleteAllBatch(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactDetSKXYByPactCode(List<Map<String,Object>> listVo) {
		try {
			pactDetSKXYMapper.deletePactDetSKXYByPactCode(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
