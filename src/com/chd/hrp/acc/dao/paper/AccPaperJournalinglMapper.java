/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.paper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccCashFlow;

/**
* @Title. @Description.
* 票据管理主表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccPaperJournalinglMapper extends SqlMapper{
	
	 //票据库存汇总表明细数据
    public List<Map<String, Object>> queryAccPaperStockCollectDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccPaperStockCollectDetail(Map<String,Object> entityMap) throws DataAccessException;
		
	 //票据库存汇总表
	public List<Map<String, Object>> queryAccPaperStockCollect(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccPaperStockCollect(Map<String,Object> entityMap) throws DataAccessException;
	

	 //票据库存明细表
	public List<Map<String, Object>> queryAccPaperStockDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccPaperStockDetail(Map<String,Object> entityMap) throws DataAccessException;

	
	 //单张管理票据统计表
	public List<Map<String, Object>> queryAccPaperSolaCount(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccPaperSolaCount(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
	 //票据汇总统计表
	public List<Map<String, Object>> queryAccPaperCollectCount(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccPaperCollectCount(Map<String,Object> entityMap) throws DataAccessException;
	
	
	  //票据出入库汇总表
		public List<Map<String, Object>> queryAccPaperInOutCollect(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

		public List<Map<String, Object>> queryAccPaperInOutCollect(Map<String,Object> entityMap) throws DataAccessException;
		
		//往来款收据核销明细账
		public List<Map<String, Object>> queryAccPaperIntercourseFundsDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		
		public List<Map<String, Object>> queryAccPaperIntercourseFundsDetail(Map<String,Object> entityMap) throws DataAccessException;
	
}
