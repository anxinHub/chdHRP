package com.chd.hrp.ass.serviceImpl.assremould.house;

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
import com.chd.hrp.ass.dao.assremould.house.AssRemouldAdetailHouseMapper;
import com.chd.hrp.ass.dao.assremould.house.AssRemouldFdetailHouseMapper;
import com.chd.hrp.ass.dao.assremould.house.AssRemouldRHouseMapper;
import com.chd.hrp.ass.dao.assremould.house.AssRemouldRdetailHouseMapper;
import com.chd.hrp.ass.dao.assremould.house.AssRemouldRsourceHouseMapper;
import com.chd.hrp.ass.dao.card.AssCardHouseMapper;
import com.chd.hrp.ass.dao.resource.AssResourceHouseMapper;
import com.chd.hrp.ass.entity.assremould.house.AssRemouldFdetailHouse;
import com.chd.hrp.ass.entity.assremould.house.AssRemouldRHouse;
import com.chd.hrp.ass.entity.change.AssChangeHouse;
import com.chd.hrp.ass.service.assremould.house.AssRemouldFDetailHouseService;
import com.chd.hrp.ass.service.assremould.house.AssRemouldRDetailHouseService;
import com.chd.hrp.ass.service.assremould.house.AssRemouldRHouseService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050805 资产改造记录(一般设备)
 * @Table:
 * ASS_REMOULD_R_House
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 @Service("assRemouldFDetailHouseService")
public class AssRemouldFDetailHouseServiceImpl  implements AssRemouldFDetailHouseService{
	//引入DAO操作
			@Resource(name = "assRemouldFdetailHouseMapper")
			private final AssRemouldFdetailHouseMapper assRemouldFdetailHouseMapper = null;
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
	public List<AssRemouldFdetailHouse> queryByDisANo(Map<String, Object> mapVo) {
		return assRemouldFdetailHouseMapper.queryByDisANo(mapVo);
	}}
