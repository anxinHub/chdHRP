package com.chd.hrp.acc.service.books.check;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccBooksCheckService {
	
	/**
	 * 总账查询
	 * */
	public String collectAccBooksCheckZZ(Map<String,Object> mapVo)throws DataAccessException;
	
	/**
	 * 总账模板打印
	 */
	public Map<String, Object> collectAccBooksCheckPrintZZ(Map<String,Object> mapVo)throws DataAccessException;
	
	/**
	 * 总账表格打印
	 */
	public List<Map<String, Object>> collectAccBooksCheckPrintGridZZ(Map<String,Object> mapVo)throws DataAccessException;
	
	/**
	 * 明细账查询
	 * */
	public String collectAccBooksCheckMXZ(Map<String,Object> mapVo)throws DataAccessException;
	
	/**
	 * 明细账模板打印
	 */
	public Map<String, Object> collectAccBooksCheckPrintMXZ(Map<String,Object> mapVo)throws DataAccessException;
	
	/**
	 * 明细账表格打印
	 */
	public List<Map<String, Object>> collectAccBooksCheckPrintGridMXZ(Map<String,Object> mapVo)throws DataAccessException;

	
	/**
	 * 余额表查询
	 * */
	public String collectAccBooksCheckYEB(Map<String,Object> mapVo)throws DataAccessException;
	
	/**
	 * 余额表模板打印
	 */
	public Map<String, Object> collectAccBooksCheckPrintYEB(Map<String,Object> mapVo)throws DataAccessException;
	
	/**
	 * 余额表表格打印
	 */
	public List<Map<String, Object>> collectAccBooksCheckPrintGridYEB(Map<String,Object> mapVo)throws DataAccessException;

	
	/**
	 * 交叉表查询
	 * */
	public String collectAccBooksCheckJCB(Map<String,Object> mapVo)throws DataAccessException;
	
	/**
	 * 交叉表模板打印
	 */
	public Map<String, Object> collectAccBooksCheckPrintJCB(Map<String,Object> mapVo)throws DataAccessException;
	
	/**
	 * 交叉表表格打印
	 */
	public List<Map<String, Object>> collectAccBooksCheckPrintGridJCB(Map<String,Object> mapVo)throws DataAccessException;

	/**
	* 交叉表查询表头
	*/
	public List<Map<String, Object>> querySubjHeadByJCB(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据核算项名称获取核算项ID
	 */
	public Map<String, Object> queryCheckTypeByCheck(Map<String, Object> mapVo) throws DataAccessException;
}
