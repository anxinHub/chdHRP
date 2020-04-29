/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.autovouch.his;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.autovouch.his.HisAccMedPayTypeRefMapper;
import com.chd.hrp.acc.entity.autovouch.HisAccMedPayTypeRef;
import com.chd.hrp.acc.service.autovouch.his.HisAccMedPayTypeRefService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hisAccMedPayTypeRefService")
public class HisAccMedPayTypeRefServiceImpl implements HisAccMedPayTypeRefService {

	private static Logger logger = Logger.getLogger(HisAccMedPayTypeRefServiceImpl.class);

	@Resource(name = "hisAccMedPayTypeRefMapper")
	private final HisAccMedPayTypeRefMapper hisAccMedPayTypeRefMapper = null;

	@Override
	public String queryHisAccMedPayTypeRef(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HisAccMedPayTypeRef> list = hisAccMedPayTypeRefMapper.queryHisAccMedPayTypeRef(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HisAccMedPayTypeRef> list = hisAccMedPayTypeRefMapper.queryHisAccMedPayTypeRef(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HisAccMedPayTypeRef queryHisAccMedPayTypeRefByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hisAccMedPayTypeRefMapper.queryHisAccMedPayTypeRefByCode(entityMap);
	}

	@Override
	public String updateHisAccMedPayTypeRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hisAccMedPayTypeRefMapper.updateHisAccMedPayTypeRef(entityMap);

			return "{\"msg\":\"更改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更改失败 数据库异常 请联系管理员! 方法 updateHisAccMedPayTypeRef\"}";

		}
	}

	@Override
	public String addHisAccMedPayTypeRef(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			String saveFlag = (String)entityMap.get("saveFlag");
			
			if(!"1".equals(saveFlag)){
				
				HisAccMedPayTypeRef apt = hisAccMedPayTypeRefMapper.queryHisAccMedPayTypeRefByCode(entityMap);
				 
				if(apt != null){
					
					return "{\"msg\":\"编码重复 请修改编码.\",\"state\":\"error\"}";
					
				}
				
			}

			hisAccMedPayTypeRefMapper.addHisAccMedPayTypeRef(entityMap);
				
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHisAccMedPayTypeRef\"}";

		}
	}

	@Override
	public String delHisAccMedPayTypeRef(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			hisAccMedPayTypeRefMapper.deleteHisAccMedPayTypeRef(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHisAccMedPayTypeRef\"}";

		}
	}

}
