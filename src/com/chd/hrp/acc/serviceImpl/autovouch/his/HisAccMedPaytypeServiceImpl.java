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
import com.chd.hrp.acc.dao.autovouch.his.HisAccMedPayTypeMapper;
import com.chd.hrp.acc.entity.autovouch.HisAccMedPayType;
import com.chd.hrp.acc.service.autovouch.his.HisAccMedPayTypeService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hisAccMedPayTypeService")
public class HisAccMedPaytypeServiceImpl implements HisAccMedPayTypeService {

	private static Logger logger = Logger.getLogger(HisAccMedPaytypeServiceImpl.class);

	@Resource(name = "hisAccMedPayTypeMapper")
	private final HisAccMedPayTypeMapper hisAccMedPayTypeMapper = null;

	@Override
	public String queryHisAccMedPayType(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = hisAccMedPayTypeMapper.queryHisAccMedPayType(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = hisAccMedPayTypeMapper.queryHisAccMedPayType(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HisAccMedPayType queryHisAccMedPayTypeByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hisAccMedPayTypeMapper.queryHisAccMedPayTypeByCode(entityMap);
	}

	@Override
	public String updateHisAccMedPayType(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hisAccMedPayTypeMapper.updateHisAccMedPayType(entityMap);

			return "{\"msg\":\"更改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更改失败 数据库异常 请联系管理员! 方法 updateHisAccMedPayType\"}";

		}
	}

	@Override
	public String addHisAccMedPayType(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			String saveFlag = (String)entityMap.get("saveFlag");
			
			if(!"1".equals(saveFlag)){
				
				HisAccMedPayType apt = hisAccMedPayTypeMapper.queryHisAccMedPayTypeByCode(entityMap);
				 
				if(apt != null){
					
					return "{\"msg\":\"编码重复 请修改编码.\",\"state\":\"error\"}";
					
				}
				
			}

			hisAccMedPayTypeMapper.addHisAccMedPayType(entityMap);
				
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHisAccMedPayType\"}";

		}
	}

	@Override
	public String delHisAccMedPayType(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			hisAccMedPayTypeMapper.deleteHisAccMedPayType(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHisAccMedPayType\"}";

		}
	}

	@Override
	public List<Map<String, Object>> queryHisAccMedPayTypePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = hisAccMedPayTypeMapper.queryHisAccMedPayType(entityMap);

		return list;
	}

}
