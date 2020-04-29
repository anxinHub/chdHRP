package com.chd.hrp.pac.serviceImpl.basicset.warning;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.basicset.warning.PactWarnSetSKHTMapper;
import com.chd.hrp.pac.entity.basicset.warning.PactWarnSetFKHTEntity;
import com.chd.hrp.pac.service.basicset.warning.PactWarnSetSKHTService;
import com.github.pagehelper.PageInfo;

@Service(value = "pactWarnSetSKHTService")
public class PactWarnSetSKHTServiceImpl implements PactWarnSetSKHTService {

	private static Logger logger = Logger.getLogger(PactWarnSetSKHTServiceImpl.class);

	@Resource(name = "pactWarnSetSKHTMapper")
	private PactWarnSetSKHTMapper pactWarnSetSKHTMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactWarnSetSKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactWarnSetFKHTEntity> list = (List<PactWarnSetFKHTEntity>) pactWarnSetSKHTMapper.query(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactWarnSetFKHTEntity> list = (List<PactWarnSetFKHTEntity>) pactWarnSetSKHTMapper.query(entityMap,
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
	public String updatePactWarnSetSKHT(Map<String, Object> mapVo) {
		try {
			Integer queryByCode = pactWarnSetSKHTMapper.queryByCode(mapVo);
			if (queryByCode == 1) {
				pactWarnSetSKHTMapper.update(mapVo);
			} else {
				pactWarnSetSKHTMapper.add(mapVo);
			}
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
