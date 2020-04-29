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
import com.chd.hrp.pac.dao.basicset.warning.PactWarnSetFKHTMapper;
import com.chd.hrp.pac.entity.basicset.warning.PactWarnSetFKHTEntity;
import com.chd.hrp.pac.service.basicset.warning.PactWarnSetFKHTService;
import com.github.pagehelper.PageInfo;

@Service(value = "pactWarnSetFKHTService")
public class PactWarnSetFKHTServiceImpl implements PactWarnSetFKHTService {

	private static Logger logger = Logger.getLogger(PactWarnSetFKHTServiceImpl.class);

	@Resource(name = "pactWarnSetFKHTMapper")
	private PactWarnSetFKHTMapper pactWarnSetFKHTMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactWarnSetFKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactWarnSetFKHTEntity> list = (List<PactWarnSetFKHTEntity>) pactWarnSetFKHTMapper.query(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactWarnSetFKHTEntity> list = (List<PactWarnSetFKHTEntity>) pactWarnSetFKHTMapper.query(entityMap, rowBounds);
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
	public String updatePactWarnSetFKHT(Map<String, Object> mapVo) {
		try {
			Integer queryByCode = pactWarnSetFKHTMapper.queryByCode(mapVo);
			if (queryByCode == 1) {
				pactWarnSetFKHTMapper.update(mapVo);
			} else {
				pactWarnSetFKHTMapper.add(mapVo);
			}
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}
}
