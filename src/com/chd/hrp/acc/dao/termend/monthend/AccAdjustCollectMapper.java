/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.termend.monthend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccCurRate;
import com.chd.hrp.acc.entity.AccCusAttr;
import com.chd.hrp.acc.entity.AccVouch;

/**
* @Title. @Description.
* 期末调汇<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccAdjustCollectMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 期末调汇<BR> 添加AccVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String addAccAdjustCollectVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末调汇<BR> 查询币种
	 * @param  entityMap 
	 * @return AccVouch
	 * @throws DataAccessException
	*/
	public AccVouch queryAccAdjustCollectByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末调汇<BR> 选择币种带出汇率信息
	 * @param  entityMap 
	 * @return AccCurRate
	 * @throws DataAccessException
	*/
	public AccCurRate queryAccAdjustCollectCurRateByCode(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 期末调汇<BR> 查询币种汇率是否存在
	 * @param  entityMap 
	 * @return AccCurRate
	 * @throws DataAccessException
	*/
	public int existsAccAdjustCollectCurRateByCode(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末调汇<BR> 添加币种汇率
	 * @param  entityMap 
	 * @return AccCurRate
	 * @throws DataAccessException
	*/
	public int addAccAdjustCollectCurRate(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末调汇<BR> 修改币种汇率
	 * @param  entityMap 
	 * @return AccCurRate
	 * @throws DataAccessException
	*/
	public int updateAccAdjustCollectCurRate(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末调汇<BR> 获得需要调汇的科目账+调汇金额集合
	 * @param  entityMap 
	 * @return AccCurRate
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCurSubjMoneyList(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末调汇<BR> 获得需要调汇的科目辅助账+调汇金额集合
	 * @param  entityMap 
	 * @return AccCurRate
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCurSubjCheckMoneyListByCode(Map<String, Object> entityMap)throws DataAccessException;
}
