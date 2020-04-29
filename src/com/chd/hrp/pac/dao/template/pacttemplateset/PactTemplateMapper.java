package com.chd.hrp.pac.dao.template.pacttemplateset;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface PactTemplateMapper extends SqlMapper{
	
	/**
	 * 根据 方案名首拼 查最大尾号
	 * @param codeMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryMaxCodeNum(Map<String, Object> codeMap) throws DataAccessException;
	
	/**
	 * 选择合同模板后  根据 模板id 查询 合同模板表单模块/表格模块数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryTemplateModular(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 合同配置项查询 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactTemplateItem(	Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 合同配置项查询 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactTemplateItem(	Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 合同配置项配置查询 不分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactTemplateItemSet(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 合同配置项配置查询 不分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactTemplateItemSet(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 合同模板配置方案 查询 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactTemplateSet(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 合同模板配置方案 查询 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactTemplateSet(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	

}
