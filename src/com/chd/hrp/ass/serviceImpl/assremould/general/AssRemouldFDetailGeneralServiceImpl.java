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
import com.chd.hrp.ass.dao.assremould.general.AssRemouldAdetailGeneralMapper;
import com.chd.hrp.ass.dao.assremould.general.AssRemouldFdetailGeneralMapper;
import com.chd.hrp.ass.dao.assremould.general.AssRemouldRGeneralMapper;
import com.chd.hrp.ass.dao.assremould.general.AssRemouldRdetailGeneralMapper;
import com.chd.hrp.ass.dao.assremould.general.AssRemouldRsourceGeneralMapper;
import com.chd.hrp.ass.dao.card.AssCardGeneralMapper;
import com.chd.hrp.ass.dao.resource.AssResourceGeneralMapper;
import com.chd.hrp.ass.entity.assremould.general.AssRemouldFdetailGeneral;
import com.chd.hrp.ass.entity.assremould.general.AssRemouldRGeneral;
import com.chd.hrp.ass.entity.change.AssChangeGeneral;
import com.chd.hrp.ass.service.assremould.general.AssRemouldFDetailGeneralService;
import com.chd.hrp.ass.service.assremould.general.AssRemouldRDetailGeneralService;
import com.chd.hrp.ass.service.assremould.general.AssRemouldRGeneralService;
import com.chd.hrp.ass.service.base.AssBaseService;
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
 @Service("assRemouldFDetailGeneralService")
public class AssRemouldFDetailGeneralServiceImpl  implements AssRemouldFDetailGeneralService{
	//引入DAO操作
			@Resource(name = "assRemouldFdetailGeneralMapper")
			private final AssRemouldFdetailGeneralMapper assRemouldFdetailGeneralMapper = null;
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
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
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
	public List<AssRemouldFdetailGeneral> queryByDisANo(Map<String, Object> mapVo) {
		return assRemouldFdetailGeneralMapper.queryByDisANo(mapVo);
	}}
