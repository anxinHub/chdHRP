package com.chd.hrp.ass.serviceImpl.assremould.inassets;

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
import com.chd.hrp.ass.dao.assremould.inassets.AssRemouldRdetailInassetsMapper;
import com.chd.hrp.ass.dao.assremould.inassets.AssRemouldRsourceInassetsMapper;
import com.chd.hrp.ass.dao.card.AssCardInassetsMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInassetsMapper;
import com.chd.hrp.ass.entity.assremould.inassets.AssRemouldRdetailInassets;
import com.chd.hrp.ass.entity.change.AssChangeInassets;
import com.chd.hrp.ass.service.assremould.inassets.AssRemouldRDetailInassetsService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.serviceImpl.assremould.AssRemouldRDetailSpecialServiceImpl;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050805 资产改造记录(一般设备)
 * @Table:
 * ASS_REMOULD_R_Inassets
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 @Service("assRemouldRDetailInassetsService")
public class AssRemouldRDetailInassetsServiceImpl  implements AssRemouldRDetailInassetsService{
	 private static Logger logger = Logger.getLogger(AssRemouldRDetailInassetsServiceImpl.class);
	 //引入DAO操作
			@Resource(name = "assRemouldRdetailInassetsMapper")
			private final AssRemouldRdetailInassetsMapper assRemouldRdetailInassetsMapper = null;
			@Resource(name = "assRemouldRsourceInassetsMapper")
			private final AssRemouldRsourceInassetsMapper assRemouldRsourceInassetsMapper = null;
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
			assRemouldRsourceInassetsMapper.deleteBatch(entityList);
			assRemouldRdetailInassetsMapper.deleteBatch(entityList);
			
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
	public List<AssRemouldRdetailInassets> queryByDisANo(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return assRemouldRdetailInassetsMapper.queryByDisANo(mapVo);
	}}
