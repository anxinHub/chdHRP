package com.chd.hrp.hpm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiCompanyBonusDataMapper;
import com.chd.hrp.hpm.entity.AphiCompanyBonusData;
import com.chd.hrp.hpm.service.AphiCompanyBonusDataService;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiCompanyBonusDataService")
public class AphiCompanyBonusDataServiceImpl implements AphiCompanyBonusDataService {

	private static Logger logger = Logger.getLogger(AphiCompanyBonusDataServiceImpl.class);

	@Resource(name = "aphiCompanyBonusDataMapper")
	private final AphiCompanyBonusDataMapper aphiCompanyBonusDataMapper = null;

	/**
	 * 
	 */
	@Override
	public String addCompanyBonusData(Map<String, Object> entityMap) throws DataAccessException {
		int state = aphiCompanyBonusDataMapper.addCompanyBonusData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";
		}
	}

	/**
	 * 
	 */
	@Override
	public String queryCompanyBonusData(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		return JsonListBeanUtil.listToJson(aphiCompanyBonusDataMapper.queryCompanyBonusData(entityMap, rowBounds), sysPage.getTotal());
	}

	/**
	 * 
	 */
	@Override
	public AphiCompanyBonusData queryCompanyBonusDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		return aphiCompanyBonusDataMapper.queryCompanyBonusDataByCode(entityMap);
	}

	/**
	 * 
	 */

	public String deleteCompanyBonusData(List<Map<String, Object>> entityList) throws DataAccessException {
		String request = "";
		for (Map<String, Object> entityMap : entityList) {
			int state = aphiCompanyBonusDataMapper.deleteCompanyBonusData(entityMap);
			if (state > 0) {
				request = "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} else {
				request = "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
			}
		}

		return request;
	}

	@Override
	public String deleteCompanyBonusData(Map<String, Object> entityMap) throws DataAccessException {
		String request = "";
		int state = aphiCompanyBonusDataMapper.deleteCompanyBonusData(entityMap);
		if (state > 0) {
			request = "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} else {
			request = "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
		}
		return request;
	}

	/**
	 * 
	 */
	@Override
	public String deleteCompanyBonusDataById(String[] ids) throws DataAccessException {
		String request = "";
		if (ids != null && ids.length > 0) {
			for (String s : ids) {
				aphiCompanyBonusDataMapper.deleteCompanyBonusDataById(s);
			}
			request = "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} else {
			request = "{\"error\":\"没有要删除的数据.\",\"state\":\"false\"}";
		}
		return request;

	}

	/**
	 * 
	 */
	@Override
	public String updateCompanyBonusData(Map<String, Object> entityMap) throws DataAccessException {
		int state = aphiCompanyBonusDataMapper.updateCompanyBonusData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";
		}
	}

	@Override
	public String initCompanyBonusData(Map<String, Object> entityMap) throws DataAccessException {
		int state = aphiCompanyBonusDataMapper.initCompanyBonusData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"生成失败.\",\"state\":\"false\"}";
		}
	}
}
