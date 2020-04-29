package com.chd.hrp.acc.service.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;


public interface AccEducationService {
	public String queryAccEducation(Map<String,Object> entityMap) throws DataAccessException;
	//项目备查簿 东阳专用
	public String queryAccEducationBySplit(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccEducationDataMining(Map<String,Object> entityMap) throws DataAccessException;
	//项目备查簿 逐级撰取凭证  东阳专用
	public String queryAccEducationDataMiningByDy(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目项目备查簿   打印
	public List<Map<String, Object>> queryAccEducationPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccEducationBySplitPrint(Map<String,Object> entityMap) throws DataAccessException;
}
