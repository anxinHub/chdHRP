package com.chd.hrp.hr.serviceImpl.nursingtraining;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.nursingtraining.HrPerformanceIntegralMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrPerformanceIntegral;
import com.chd.hrp.hr.service.nursingtraining.HrPerformanceIntegralService;
import com.github.pagehelper.PageInfo;

/**
 * 员工年终绩效积分考核
 * 
 * @author Administrator
 *
 */
@Service("hrPerformanceIntegralService")
public class HrPerformanceIntegralServiceImpl implements HrPerformanceIntegralService {
	private static Logger logger = Logger.getLogger(HrPerformanceIntegralServiceImpl.class);

	@Resource(name = "hrPerformanceIntegralMapper")
	private final HrPerformanceIntegralMapper hrPerformanceIntegralMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 员工年终绩效积分考核增加
	 */
	@Override
	public String addPerformanceIntegral(Map<String, Object> entityMap) throws DataAccessException {
		try {
	
			List<HrPerformanceIntegral> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrPerformanceIntegral.class);
		
		
				/**
				 * 增加
				 */
				if (alllistVo!=null && alllistVo.size() > 0) {
					for (HrPerformanceIntegral HrPerformanceIntegral : alllistVo) {
						HrPerformanceIntegral.setGroup_id(Integer.parseInt(entityMap.get("group_id").toString()));
						HrPerformanceIntegral.setHos_id(Integer.parseInt(entityMap.get("hos_id").toString()));
					}}
				/**
				 * 先删除
				 */
				hrPerformanceIntegralMapper.deleteAll(alllistVo);
				hrPerformanceIntegralMapper.addBatch(alllistVo);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}


	/**
	 * 员工年终绩效积分考核删除
	 */
	@Override
	public String deletePerformanceIntegral(List<HrPerformanceIntegral> entityList) throws DataAccessException {

		try {

			hrPerformanceIntegralMapper.deletePerformanceIntegral(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 员工年终绩效积分考核查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryPerformanceIntegral(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrPerformanceIntegral> list = (List<HrPerformanceIntegral>) hrPerformanceIntegralMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrPerformanceIntegral> list = (List<HrPerformanceIntegral>) hrPerformanceIntegralMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}


}
