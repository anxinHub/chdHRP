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
import com.chd.hrp.hr.dao.nursingtraining.HrAssessmentStatisticalMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrAssessmentStatistical;
import com.chd.hrp.hr.service.nursingtraining.HrAssessmentStatisticalService;
import com.github.pagehelper.PageInfo;

/**
 * 考核统计
 * 
 * @author Administrator
 *
 */
@Service("hrAssessmentStatisticalService")
public class HrAssessmentStatisticalServiceImpl implements HrAssessmentStatisticalService {
	private static Logger logger = Logger.getLogger(HrAssessmentStatisticalServiceImpl.class);

	@Resource(name = "hrAssessmentStatisticalMapper")
	private final HrAssessmentStatisticalMapper hrAssessmentStatisticalMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);


	/**
	 * 考核统计查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryAssessmentStatistical(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) hrAssessmentStatisticalMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) hrAssessmentStatisticalMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

   
	@Override
	public String queryEvalCode(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> list = hrAssessmentStatisticalMapper.queryEvalCode(entityMap);
		return JSONArray.toJSONString(list);
	}


	@Override
	public List<Map<String, Object>> queryAssessmentByPrint(
			Map<String, Object> entityMap) throws DataAccessException {

		 List<Map<String,Object>> list = ChdJson.toListLower(hrAssessmentStatisticalMapper.queryAssessmentByPrint(entityMap));
		 return list;
	}


}
