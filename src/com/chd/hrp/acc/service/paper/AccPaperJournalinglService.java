/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.paper;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;





public interface AccPaperJournalinglService {

    //票据库存汇总表明细数据
	public String queryAccPaperStockCollectDetail(Map<String,Object> entityMap) throws DataAccessException;
	
    //票据库存汇总表
	public String queryAccPaperStockCollect(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccPaperStockCollectPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	//票据库存明细表
    public String queryAccPaperStockDetail(Map<String,Object> entityMap) throws DataAccessException;
    public List<Map<String, Object>> queryAccPaperStockDetailPrint(Map<String,Object> entityMap) throws DataAccessException;
	

    //单张管理票据统计表
	public String queryAccPaperSolaCount(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccPaperSolaCountPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	

	  //票据库存汇总表
	public String queryAccPaperCollectCount(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccPaperCollectCountPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	  //票据出入库汇总表
	public String queryAccPaperInOutCollect(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccPaperInOutCollectPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//往来款收据核销明细账
	public String queryAccPaperIntercourseFundsDetail(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccPaperIntercourseFundsDetailPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
}
