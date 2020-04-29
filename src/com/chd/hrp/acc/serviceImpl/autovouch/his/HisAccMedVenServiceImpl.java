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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.autovouch.his.HisAccMedVenMapper;
import com.chd.hrp.acc.entity.autovouch.HisAccMedVen;
import com.chd.hrp.acc.service.autovouch.his.HisAccMedVenService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hisAccMedVenService")
public class HisAccMedVenServiceImpl implements HisAccMedVenService {

	private static Logger logger = Logger.getLogger(HisAccMedVenServiceImpl.class);

	@Resource(name = "hisAccMedVenMapper")
	private final HisAccMedVenMapper hisAccMedVenMapper = null;

	@Override
	public String queryHisAccMedVen(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HisAccMedVen> list = hisAccMedVenMapper.queryHisAccMedVen(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HisAccMedVen> list = hisAccMedVenMapper.queryHisAccMedVen(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	@Override
	public List<Map<String,Object>> queryHisAccMedVenPrint(Map<String, Object> entityMap) throws DataAccessException {
		
			List<Map<String,Object>> list = hisAccMedVenMapper.queryHisAccMedVenPrint(entityMap);
			
			return list ;
	}

	@Override
	public HisAccMedVen queryHisAccMedVenByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hisAccMedVenMapper.queryHisAccMedVenByCode(entityMap);
	}

	@Override
	public String updateHisAccMedVen(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hisAccMedVenMapper.updateHisAccMedVen(entityMap);

			return "{\"msg\":\"更改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更改失败 数据库异常 请联系管理员! 方法 updateHisAccMedVen\"}";

		}
	}

	@Override
	public String addHisAccMedVen(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			String saveFlag = (String)entityMap.get("saveFlag");
			
			if(!"1".equals(saveFlag)){
				
				HisAccMedVen apt = hisAccMedVenMapper.queryHisAccMedVenByCode(entityMap);
				 
				if(apt != null){
					
					return "{\"msg\":\"编码重复 请修改编码.\",\"state\":\"error\"}";
					
				}
				
			}

			hisAccMedVenMapper.addHisAccMedVen(entityMap);
				
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHisAccMedVen\"}";

		}
	}

	@Override
	public String delHisAccMedVen(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			hisAccMedVenMapper.deleteHisAccMedVen(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHisAccMedVen\"}";

		}
	}

}
