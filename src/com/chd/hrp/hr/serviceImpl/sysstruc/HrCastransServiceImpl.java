/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.serviceImpl.sysstruc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.sysstruc.HrCastransMapper;
import com.chd.hrp.hr.entity.sysstruc.HrCastrans;
import com.chd.hrp.hr.service.sysstruc.HrCastransService;

/**
 * 
 * @Description: 级联事务
 * @Colle: HR_CASTRANS
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("hrCastransService")
public class HrCastransServiceImpl implements HrCastransService {

	private static Logger logger = Logger.getLogger(HrCastransServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "hrCastransMapper")
	private final HrCastransMapper hrCastransMapper = null;
	
	@Override
	public String addHrCastrans(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		HrCastrans hrCastrans = hrCastransMapper.queryByCode(entityMap);

		if (hrCastrans != null) {
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}

		try {
			String content = entityMap.get("cas_sql").toString();
			entityMap.put("cas_sql", content.getBytes());
			hrCastransMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String updateHrCastrans(Map<String, Object> entityMap) throws DataAccessException {

		try {
			String content = entityMap.get("cas_sql").toString();
			entityMap.put("cas_sql", content.getBytes());
			hrCastransMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String deleteHrCastrans(Map<String, Object> entityMap) throws DataAccessException {

		try {
			hrCastransMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String deleteBatchHrCastrans(List<HrCastrans> entityList) throws DataAccessException {

		try {
			hrCastransMapper.deleteBatchHrCastrans(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryHrCastrans(Map<String, Object> entityMap) throws DataAccessException {
		List<HrCastrans> list = (List<HrCastrans>) hrCastransMapper.query(entityMap);
		return ChdJson.toJson(list);
	}

	@Override
	public HrCastrans queryHrCastransByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrCastransMapper.queryByCode(entityMap);
	}

}
