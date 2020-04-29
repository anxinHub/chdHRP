/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.cost.entity.CostClinicWorkContrast;


/**
* @Title. @Description.
* 门诊科室对比分析<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface CostClinicWorkContrastAnalysisMapper extends SqlMapper{

	
	/**
	 * @Description 
	 * 门诊科室对比分析
	 * @param  entityMap RowBounds
	 * @return List<CostClinicWorkContrast>
	 * @throws DataAccessException
	*/
	public List<CostClinicWorkContrast> queryCostClinicWorkContrast(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 门诊科室对比分析
	 * @param  entityMap RowBounds
	 * @return List<CostClinicWorkContrast>
	 * @throws DataAccessException
	*/
	public List<CostClinicWorkContrast> queryCostClinicWorkContrast(Map<String,Object> entityMap) throws DataAccessException;
}
