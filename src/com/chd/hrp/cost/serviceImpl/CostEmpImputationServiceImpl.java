/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostEmpImputationMapper;
import com.chd.hrp.cost.entity.CostEmpImputation;
import com.chd.hrp.cost.service.CostEmpImputationService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 科室材料支出明细表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costEmpImputationService")
public class CostEmpImputationServiceImpl implements CostEmpImputationService {

	private static Logger logger = Logger.getLogger(CostEmpImputationServiceImpl.class);

	@Resource(name = "costEmpImputationMapper")
	private final CostEmpImputationMapper costEmpImputationMapper = null;

	/**
	 * @Description 科室材料归集<BR>
	 *              查询queryCostEmpImputation分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryCostEmpImputation(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<CostEmpImputation> list = costEmpImputationMapper.queryCostEmpImputation(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<CostEmpImputation> list = costEmpImputationMapper.queryCostEmpImputation(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

}
