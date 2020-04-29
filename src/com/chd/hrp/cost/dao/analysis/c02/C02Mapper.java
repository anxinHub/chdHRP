package com.chd.hrp.cost.dao.analysis.c02;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface C02Mapper extends SqlMapper{
	
	
	
	/**
	 * @Description 
	 * 全院成本构成比例查询<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0200(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<?> queryAnalysisC0200(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0201(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	 public int deleteCostAnalysisC0201(Map<String,Object> entityMap)throws DataAccessException;
		
		
		public int addCostAnalysisC0201(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0201(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0202(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0202(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0203(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0203(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0204(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0204(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0205(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0205(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0206(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0206(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0207(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0207(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0208(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0208(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0209(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0209(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0210(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0210(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0211(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0211(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0212(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0212(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0213(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0213(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0214(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0214(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0215(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0215(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0216(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0216(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0217(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0217(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0218(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0218(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0219(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0219(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0220(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0220(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0221(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0221(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0222(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0222(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0223(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0223(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0224(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0224(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0225(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0225(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0226(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0226(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0227(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0227(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0228(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0228(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0229(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0229(Map<String,Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0201Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0202Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0203Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0204Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0205Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0206Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0207Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0208Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0209Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0210Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0211Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0212Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0213Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0214Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0215Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0220Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0221Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0222Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0223Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0224Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0225Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0226Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0227Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0228Print(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0229Print(Map<String, Object> entityMap) throws DataAccessException;

}

