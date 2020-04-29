package com.chd.hrp.hr.serviceImpl.report;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.report.HosPersonnelStructureMapper;
import com.chd.hrp.hr.service.report.HosPersonnelStructureService;
import com.github.pagehelper.PageInfo;

@Service("hosPersonnelStructureService")
public class HosPersonnelStructureServiceImpl implements HosPersonnelStructureService {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HosPersonnelStructureServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "hosPersonnelStructureMapper")
	private final HosPersonnelStructureMapper hosPersonnelStructureMapper = null;


	
	

	/**
	 * @Description 查询结果集<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryHrPersonnelStructure(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) hosPersonnelStructureMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) hosPersonnelStructureMapper.query(entityMap, rowBounds);

		
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}





	@Override
	public List<Map<String, Object>> queryPersonnelStructureByPrint(Map<String, Object> entityMap) throws DataAccessException {
			 List<Map<String,Object>> list = ChdJson.toListLower(hosPersonnelStructureMapper.queryPersonnelStructureByPrint(entityMap));
			 return list;
	}





}
