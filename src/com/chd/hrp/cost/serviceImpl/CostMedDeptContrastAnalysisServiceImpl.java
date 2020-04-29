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
import com.chd.hrp.cost.dao.CostMedDeptContrastAnalysisMapper;
import com.chd.hrp.cost.entity.CostMedDeptContrast;
import com.chd.hrp.cost.service.CostMedDeptContrastAnalysisService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 医技科室收入对比分析
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costMedDeptContrastAnalysisService")
public class CostMedDeptContrastAnalysisServiceImpl implements CostMedDeptContrastAnalysisService {

	private static Logger logger = Logger.getLogger(CostMedDeptContrastAnalysisServiceImpl.class);
	
	@Resource(name = "costMedDeptContrastAnalysisMapper")
	private final CostMedDeptContrastAnalysisMapper costMedDeptContrastAnalysisMapper = null;

	
	/**
	 * @Description 
	 * 医技科室收入对比分析
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostMedDeptContrast(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<CostMedDeptContrast> list = costMedDeptContrastAnalysisMapper.queryCostMedDeptContrast(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<CostMedDeptContrast> list = costMedDeptContrastAnalysisMapper.queryCostMedDeptContrast(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
}
