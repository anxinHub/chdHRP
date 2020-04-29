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
import com.chd.hrp.acc.dao.autovouch.his.HisAccMedTypeMapper;
import com.chd.hrp.acc.entity.autovouch.HisAccMedType;
import com.chd.hrp.acc.service.autovouch.his.HisAccMedTypeService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hisAccMedTypeService")
public class HisAccMedTypeServiceImpl implements HisAccMedTypeService {

	private static Logger logger = Logger.getLogger(HisAccMedTypeServiceImpl.class);

	@Resource(name = "hisAccMedTypeMapper")
	private final HisAccMedTypeMapper hisAccMedTypeMapper = null;

	@Override
	public String queryHisAccMedType(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = hisAccMedTypeMapper.queryHisAccMedType(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = hisAccMedTypeMapper.queryHisAccMedType(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HisAccMedType queryHisAccMedTypeByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hisAccMedTypeMapper.queryHisAccMedTypeByCode(entityMap);
	}

	@Override
	public String updateHisAccMedType(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hisAccMedTypeMapper.updateHisAccMedType(entityMap);

			return "{\"msg\":\"更改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更改失败 数据库异常 请联系管理员! 方法 updateHisAccMedType\"}";

		}
	}

	@Override
	public String addHisAccMedType(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			String saveFlag = (String)entityMap.get("saveFlag");
			
			if(!"1".equals(saveFlag)){
				
				HisAccMedType apt = hisAccMedTypeMapper.queryHisAccMedTypeByCode(entityMap);
				 
				if(apt != null){
					
					return "{\"msg\":\"编码重复 请修改编码.\",\"state\":\"error\"}";
					
				}
				
			}
				
			hisAccMedTypeMapper.addHisAccMedType(entityMap);
				
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHisAccMedType\"}";

		}
	}

	@Override
	public String delHisAccMedType(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			hisAccMedTypeMapper.deleteHisAccMedType(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHisAccMedType\"}";

		}
	}

	@Override
	public List<Map<String, Object>> queryHisAccMedTypePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = hisAccMedTypeMapper.queryHisAccMedType(entityMap);

		return list;

	}

}
