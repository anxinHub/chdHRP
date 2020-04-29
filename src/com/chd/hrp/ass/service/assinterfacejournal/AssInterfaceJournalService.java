package com.chd.hrp.ass.service.assinterfacejournal;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AssInterfaceJournalService {
	/**
	 * 查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssInterfaceJournal(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 保存
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int insertAssInterfaceJournal(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 补录
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String addMakeUpInterface(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryPItype(Map<String, Object> mapVo) throws DataAccessException;

}
