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
import com.chd.hrp.hr.dao.scientificresearch.HrIntegrationProjectsResultsMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrIntegrationProjectsResults;
import com.chd.hrp.hr.service.scientificresearch.HrIntegrationProjectsResultsService;
import com.github.pagehelper.PageInfo;

/**
 * 科研项目与成果积分
 * 
 * @author Administrator
 *
 */
@Service("hrIntegrationProjectsResultsService")
public class HrIntegrationProjectsResultsServiceImpl implements HrIntegrationProjectsResultsService {
	private static Logger logger = Logger.getLogger(HrIntegrationProjectsResultsServiceImpl.class);

	@Resource(name = "hrIntegrationProjectsResultsMapper")
	private final HrIntegrationProjectsResultsMapper hrIntegrationProjectsResultsMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 科研项目与成果积分增加
	 */
	@Override
	public String addIntegrationProjectsResults(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<HrIntegrationProjectsResults> listVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")), HrIntegrationProjectsResults.class);
			if (listVo != null && listVo.size() > 0) {
				for (HrIntegrationProjectsResults hrIntegrationProjectsResults : listVo) {
					hrIntegrationProjectsResults.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrIntegrationProjectsResults.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					hrIntegrationProjectsResults.setYear(entityMap.get("year").toString());
				}
			}
			if(listVo.size() > 0){
				hrIntegrationProjectsResultsMapper.deleteIntegrationProjectsResults(listVo);
				int state = hrIntegrationProjectsResultsMapper.addBatch(listVo);
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}else{
				return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	/**
	 * 保存满分
	 */
	@Override
	public String savePersonalAcadeProj(Map<String, Object> entityMap) throws DataAccessException {
		try {
			//entityMap.put("year", SessionManager.getAcctYear());
			List<Map<String, Object>> list = hrIntegrationProjectsResultsMapper.queryProjectsTot(entityMap);
			if (list != null && list.size() > 0) {
				hrIntegrationProjectsResultsMapper.updateProjectsTot(entityMap);
			} else {
				hrIntegrationProjectsResultsMapper.addProjectsTot(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 科研项目与成果积分删除
	 */
	@Override
	public String deleteIntegrationProjectsResults(List<HrIntegrationProjectsResults> entityList) throws DataAccessException {

		try {

			hrIntegrationProjectsResultsMapper.deleteIntegrationProjectsResults(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 科研项目与成果积分查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryIntegrationProjectsResults(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrIntegrationProjectsResults> list = (List<HrIntegrationProjectsResults>) hrIntegrationProjectsResultsMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrIntegrationProjectsResults> list = (List<HrIntegrationProjectsResults>) hrIntegrationProjectsResultsMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}


	@Override
	public String copyIntegration(Map<String, Object> entityMap) throws DataAccessException {
		try {
			// 继承前先查上一年数据
			int count = hrIntegrationProjectsResultsMapper.queryIntegrationByLastYear(entityMap);
			if(count <= 0){
				return "{\"msg\":\"继承失败,上一年没有数据.\",\"state\":\"false\"}";
			}
			
			if (entityMap!=null) {
	        	entityMap.put("year", SessionManager.getAcctYear());
			}
			// 先删除
			hrIntegrationProjectsResultsMapper.deleteIntegration(entityMap);
			int state  =  hrIntegrationProjectsResultsMapper.copyIntegration(entityMap);
			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 查询满分按年份切换
	 */
	@Override
	public String queryProjByYear(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> list=hrIntegrationProjectsResultsMapper.queryProjByYear(entityMap);
		return JSONArray.toJSONString(list);
	} 
    

}
