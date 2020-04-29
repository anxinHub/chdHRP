package com.chd.hrp.hr.dao.ss;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


public interface SeqManageMapper extends SqlMapper{

	/**
	 * @Description  查询 Rule 的全部记录
	 * @param： entityMap
	 * @return: List<Result>
	 * @throws: DataAccessException
	 */

       public List<Map<String, Object>> querySeqManage(Map<String, Object> entityMap)throws DataAccessException;

       public void addSeqManage(Map<String, Object> entityMap)throws DataAccessException;

       public void deleteSeqManage(List<Map<String, Object>> listVo)throws DataAccessException;
       
       public void createSeqManage(Map<String, Object> entityMap)throws DataAccessException;
       
       public void dropSeqManage(List<Map<String, Object>> listVo)throws DataAccessException;
}
