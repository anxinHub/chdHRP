/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.vouch.AccVouchMapper;
import com.chd.hrp.acc.entity.AccVouch;
import com.chd.hrp.acc.service.AccVouchStatisticsService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 凭证主表<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accVouchStatisticsService")
public class AccVouchStatisticsServiceImpl implements AccVouchStatisticsService {

	private static Logger logger = Logger.getLogger(AccVouchStatisticsServiceImpl.class);

	@Resource(name = "accVouchMapper")
	private final AccVouchMapper accVouchMapper = null;

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch出纳数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccVouchStatistics(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String, Object>> list = accVouchMapper.queryAccVouchStatistics(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJsonLower(list, page.getTotal());
	}

	@Override
	public List<Map<String, Object>> queryAccVouchStatisticsPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> list = accVouchMapper.queryAccVouchStatistics(entityMap);
		return list;
	}
	
}
