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
import com.chd.hrp.cost.dao.CostInhosWorkContrastAnalysisMapper;
import com.chd.hrp.cost.entity.CostInhosWorkContrast;
import com.chd.hrp.cost.service.CostInhosWorkContrastAnalysisService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 住院科室收入对比分析
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costInhosWorkContrastAnalysisService")
public class CostInhosWorkContrastAnalysisServiceImpl implements CostInhosWorkContrastAnalysisService {

	private static Logger logger = Logger.getLogger(CostInhosWorkContrastAnalysisServiceImpl.class);
	
	@Resource(name = "costInhosWorkContrastAnalysisMapper")
	private final CostInhosWorkContrastAnalysisMapper costInhosWorkContrastAnalysisMapper = null;

	
	/**
	 * @Description 
	 * 门诊科室收入对比分析
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostInhosWorkContrast(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<CostInhosWorkContrast> list = costInhosWorkContrastAnalysisMapper.queryCostInhosWorkContrast(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<CostInhosWorkContrast> list = costInhosWorkContrastAnalysisMapper.queryCostInhosWorkContrast(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
}
