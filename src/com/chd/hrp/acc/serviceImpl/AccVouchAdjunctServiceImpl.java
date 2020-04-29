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
import com.chd.hrp.acc.service.AccVouchAdjunctService;
import com.chd.hrp.acc.service.AccVouchStatisticsService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 凭证附件<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accVouchAdjunctService")
public class AccVouchAdjunctServiceImpl implements AccVouchAdjunctService {

	private static Logger logger = Logger.getLogger(AccVouchAdjunctServiceImpl.class);

	@Resource(name = "accVouchMapper")
	private final AccVouchMapper accVouchMapper = null;

	/**
	 * @Description 凭证附件<BR>
	 * @param entityMap
	 *            RowBounds
	 * @return List<?>
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccVouchAdjunct(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<?> list = accVouchMapper.queryAccVouchAdjunct(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
}
