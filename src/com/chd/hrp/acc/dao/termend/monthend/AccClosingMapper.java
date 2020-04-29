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
import com.chd.hrp.acc.entity.AccVouch;
import com.chd.hrp.acc.entity.AccYearMonth;

/**
* @Title. @Description.
* 月末结账<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccClosingMapper extends SqlMapper{
	/**
	 * @Description 
	 * 结账<BR> 期间查询
	 * @param  entityMap
	 * @return List<AccYearMonth>
	 * @throws DataAccessException
	*/
	public List<AccYearMonth> queryAccClosing(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 结账<BR> 期间查询
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public Integer queryAccClosingCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结账<BR> 未记账凭证查询
	 * @param  entityMap,rowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	*/
	public List<AccVouch> queryAccClosingCheckVouch(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结账<BR> 核对总账与辅助账查询
	 * @param  entityMap,rowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	*/
	public List<AccVouch> queryAccClosingCheckLederToCheck(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结账<BR> 核对总账与凭证查询
	 * @param  entityMap,rowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	*/
	public List<AccVouch> queryAccClosingCheckLederToVouch(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 结账<BR> 建立下一期间账
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccClosingNextLeder(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结账<BR> 建立下一期间辅助账
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccClosingNextCheckLeder(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 结账<BR> 年度结账复制未核销完成的往来账到下一年度
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int copyAccClosingVeriVouchCkeck(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结账<BR> 判断本期间是否已结账
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public Integer existsAccClosing(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结账<BR> 修改结账标志
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccClosing(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结账<BR> 反结删除对应表
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccClosing(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结账<BR> 获取当前结账涉及到的凭证数量
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAccClosingCountVouch(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 结账<BR> 获取科目的辅助核算名称
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String getAccClosingCheckNamesBySubj(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 结账<BR> 未结账凭证张数
	 * @param  entityMap
	 * @return Integer
	 * @throws DataAccessException
	*/
	public Integer queryAccClosingUnAccountVouch(Map<String,Object> entityMap) throws DataAccessException;
}
