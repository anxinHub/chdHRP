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
import com.chd.hrp.pac.dao.basicset.warning.PactWarnSetFKXYMapper;
import com.chd.hrp.pac.entity.basicset.warning.BaseWarnEntity;
import com.chd.hrp.pac.service.basicset.warning.PactWarnSetFKXYService;
import com.github.pagehelper.PageInfo;

@Service(value="pactWarnSetFKXYService")
public class PactWarnSetFKXYServiceImpl implements PactWarnSetFKXYService {
	
	private static Logger logger = Logger.getLogger(PactWarnSetFKXYServiceImpl.class);

	@Resource(name="pactWarnSetFKXYMapper")
	private PactWarnSetFKXYMapper pactWarnSetFKXYMapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public String queryPactWarnSetFKXY(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<BaseWarnEntity> list = (List<BaseWarnEntity>) pactWarnSetFKXYMapper.query(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<BaseWarnEntity> list = (List<BaseWarnEntity>) pactWarnSetFKXYMapper.query(entityMap, rowBounds);
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
	public String updatePactWarnSetFKXY(Map<String, Object> mapVo) {
		try {
			Integer queryByCode = pactWarnSetFKXYMapper.queryByCode(mapVo);
			if (queryByCode == 1) {
				pactWarnSetFKXYMapper.update(mapVo);
			} else {
				pactWarnSetFKXYMapper.add(mapVo);
			}
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}
}
