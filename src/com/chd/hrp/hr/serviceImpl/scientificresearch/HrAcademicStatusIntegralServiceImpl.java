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
import com.chd.hrp.hr.dao.scientificresearch.HrAcademicStatusIntegralMapper;
import com.chd.hrp.hr.entity.nursing.HrAcademicAbility;
import com.chd.hrp.hr.entity.scientificresearch.HrAcademicStatusIntegral;
import com.chd.hrp.hr.service.scientificresearch.HrAcademicStatusIntegralService;
import com.github.pagehelper.PageInfo;

/**
 * 个人学术地位积分
 * 
 * @author Administrator
 *
 */
@Service("hrAcademicStatusIntegralService")
public class HrAcademicStatusIntegralServiceImpl implements HrAcademicStatusIntegralService {
	private static Logger logger = Logger.getLogger(HrAcademicStatusIntegralServiceImpl.class);

	@Resource(name = "hrAcademicStatusIntegralMapper")
	private final HrAcademicStatusIntegralMapper hrAcademicStatusIntegralMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 个人学术地位积分增加
	 */
	@Override
	public String addAcademicStatusIntegral(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			List<HrAcademicStatusIntegral> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")), HrAcademicStatusIntegral.class);
			/**
			 * 增加
			 */
			if (alllistVo != null && alllistVo.size() > 0) {
				for (HrAcademicStatusIntegral hrAcademicStatusIntegral : alllistVo) {
					hrAcademicStatusIntegral.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrAcademicStatusIntegral.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					hrAcademicStatusIntegral.setYear(entityMap.get("year").toString());
				}
			}
			if(alllistVo.size() > 0){
				hrAcademicStatusIntegralMapper.deleteAcademicStatusIntegral(alllistVo);
				int state = hrAcademicStatusIntegralMapper.addBatch(alllistVo);
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}else{
				return "{\"msg\":\"没有要保存的数据！\",\"state\":\"false\"}";
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}
	
	@Override
	public String savePersonalAcadeStatus(Map<String, Object> entityMap) throws DataAccessException {
		try {
			if (entityMap != null) {
				List<Map<String, Object>> list = hrAcademicStatusIntegralMapper.queryStatusTot(entityMap);
				if (list != null && list.size() > 0) {
					hrAcademicStatusIntegralMapper.updateStatusTot(entityMap);
				} else {
					hrAcademicStatusIntegralMapper.addStatusTot(entityMap);
				}
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	

	/**
	 * 个人学术地位积分删除
	 */
	@Override
	public String deleteAcademicStatusIntegral(List<HrAcademicStatusIntegral> entityList) throws DataAccessException {

		try {

			hrAcademicStatusIntegralMapper.deleteAcademicStatusIntegral(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 个人学术地位积分查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryAcademicStatusIntegral(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrAcademicStatusIntegral> list = (List<HrAcademicStatusIntegral>) hrAcademicStatusIntegralMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrAcademicStatusIntegral> list = (List<HrAcademicStatusIntegral>) hrAcademicStatusIntegralMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

   /**
    * 查询下拉框
    * @param entityMap
    * @return
    * @throws DataAccessException
    */
	@Override
	public String queryStatus(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String,Object>> list = hrAcademicStatusIntegralMapper.queryStatus(entityMap);
		return JSONArray.toJSONString(list);
	
	}


	@Override
	public String copyStatus(Map<String, Object> entityMap) throws DataAccessException {
		try {
			// 继承前先查有无去年数据
			int count = hrAcademicStatusIntegralMapper.queryAcademicStatusByLastYear(entityMap);
			if(count <= 0){
				return "{\"msg\":\"继承失败,上一年没有数据.\",\"state\":\"false\"}";
			}
			
			if (entityMap!=null) {
	        	entityMap.put("year", SessionManager.getAcctYear());
			}
			// 先删除
			hrAcademicStatusIntegralMapper.deleteAcademicStatus(entityMap);
			int state  =  hrAcademicStatusIntegralMapper.copyStatus(entityMap);
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
public String queryAcadeStatus(Map<String, Object> entityMap) throws DataAccessException {
	Map<String, Object> list=hrAcademicStatusIntegralMapper.queryAcadeStatus(entityMap);
	return JSONArray.toJSONString(list);
}


 

}
