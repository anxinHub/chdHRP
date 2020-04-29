package com.chd.hrp.hr.serviceImpl.scientificresearch;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.scientificresearch.HrAcademicPaperIntegrationMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrAcademicPaperIntegration;
import com.chd.hrp.hr.service.scientificresearch.HrAcademicPaperIntegrationService;
import com.github.pagehelper.PageInfo;

/**
 * 学术论文积分
 * 
 * @author Administrator
 *
 */
@Service("hrAcademicPaperIntegrationService")
public class HrAcademicPaperIntegrationServiceImpl implements HrAcademicPaperIntegrationService {
	private static Logger logger = Logger.getLogger(HrAcademicPaperIntegrationServiceImpl.class);

	@Resource(name = "hrAcademicPaperIntegrationMapper")
	private final HrAcademicPaperIntegrationMapper hrAcademicPaperIntegrationMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 学术论文积分增加
	 */
	@Override
	public String addAcademicPaperIntegration(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<HrAcademicPaperIntegration> listVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")), HrAcademicPaperIntegration.class);
			if (listVo != null && listVo.size() > 0) {
				for (HrAcademicPaperIntegration hrAcademicPaperIntegration : listVo) {
					hrAcademicPaperIntegration.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrAcademicPaperIntegration.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					hrAcademicPaperIntegration.setYear(entityMap.get("year").toString());
				}
			}
			if(listVo.size() > 0){
				hrAcademicPaperIntegrationMapper.deleteAcademicPaperIntegration(listVo);
				int state = hrAcademicPaperIntegrationMapper.addBatch(listVo);
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}else{
				return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";
			}

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}
	/**
	 * 保存最高分
	 */
	@Override
	public String savePersonalAcadePaper(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String, Object>> list = hrAcademicPaperIntegrationMapper.queryTot(entityMap);
			if (list != null && list.size() > 0) {
				hrAcademicPaperIntegrationMapper.updateTot(entityMap);
			} else {
				hrAcademicPaperIntegrationMapper.addTot(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	
	/**
	 * 学术论文积分删除
	 */
	@Override
	public String deleteAcademicPaperIntegration(List<HrAcademicPaperIntegration> entityList) throws DataAccessException {

		try {

			hrAcademicPaperIntegrationMapper.deleteAcademicPaperIntegration(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 学术论文积分查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryAcademicPaperIntegration(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrAcademicPaperIntegration> list = (List<HrAcademicPaperIntegration>) hrAcademicPaperIntegrationMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrAcademicPaperIntegration> list = (List<HrAcademicPaperIntegration>) hrAcademicPaperIntegrationMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}



	@Override
	public String queryPaperType(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list=hrAcademicPaperIntegrationMapper.queryPaperType(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryAffectPara(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list=hrAcademicPaperIntegrationMapper.queryAffectPara(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String copyAcademicPaper(Map<String, Object> entityMap) throws DataAccessException {
		try {
			// 继承前先查询上一年的数据
			int count = hrAcademicPaperIntegrationMapper.queryAcademicPaperByLastYear(entityMap);
			if(count <= 0){
				return "{\"msg\":\"继承失败,上一年没有数据.\",\"state\":\"false\"}";
			}
			
			if (entityMap!=null) {
	        	entityMap.put("year", SessionManager.getAcctYear());
			}
			// 先删除
			hrAcademicPaperIntegrationMapper.deletePaper(entityMap);
			int state  =  hrAcademicPaperIntegrationMapper.copyAcademicPaper(entityMap);
			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryTotMainByYear(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> list=hrAcademicPaperIntegrationMapper.queryTotMainByYear(entityMap);
		return JSONArray.toJSONString(list);
	}

	
    

}
