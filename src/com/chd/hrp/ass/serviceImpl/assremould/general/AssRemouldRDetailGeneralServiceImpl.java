package com.chd.hrp.ass.serviceImpl.assremould.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.dao.assremould.AssRemouldRdetailSpecialMapper;
import com.chd.hrp.ass.dao.assremould.general.AssRemouldRGeneralMapper;
import com.chd.hrp.ass.dao.assremould.general.AssRemouldRdetailGeneralMapper;
import com.chd.hrp.ass.dao.assremould.general.AssRemouldRsourceGeneralMapper;
import com.chd.hrp.ass.dao.card.AssCardGeneralMapper;
import com.chd.hrp.ass.dao.resource.AssResourceGeneralMapper;
import com.chd.hrp.ass.entity.assremould.general.AssRemouldRGeneral;
import com.chd.hrp.ass.entity.assremould.general.AssRemouldRdetailGeneral;
import com.chd.hrp.ass.entity.change.AssChangeGeneral;
import com.chd.hrp.ass.service.assremould.general.AssRemouldRDetailGeneralService;
import com.chd.hrp.ass.service.assremould.general.AssRemouldRGeneralService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.serviceImpl.assremould.AssRemouldRDetailSpecialServiceImpl;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050805 资产改造记录(一般设备)
 * @Table:
 * ASS_REMOULD_R_General
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 @Service("assRemouldRDetailGeneralService")
public class AssRemouldRDetailGeneralServiceImpl  implements AssRemouldRDetailGeneralService{
	 private static Logger logger = Logger.getLogger(AssRemouldRDetailGeneralServiceImpl.class);
	 //引入DAO操作
	@Resource(name = "assRemouldRdetailGeneralMapper")
	private final AssRemouldRdetailGeneralMapper assRemouldRdetailGeneralMapper = null;
	@Resource(name = "assRemouldRsourceGeneralMapper")
	private final AssRemouldRsourceGeneralMapper assRemouldRsourceGeneralMapper = null;
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			assRemouldRsourceGeneralMapper.deleteBatch(entityList);
			assRemouldRdetailGeneralMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}	
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AssRemouldRdetailGeneral> queryByDisANo(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return assRemouldRdetailGeneralMapper.queryByDisANo(mapVo);
	}}
