package com.chd.hrp.hr.service.attendancemanagement.accrest;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrAccRestInitService {
     /**
      * 添加积休
      * @param mapVo
      * @return
      * @throws DataAccessException
      */
	String addAccRestInit(Map<String, Object> mapVo) throws DataAccessException;
     /**
      * 查询
      * @param page
      * @return
      * @throws DataAccessException
      */
	String queryAccRestInit(Map<String, Object> page)throws DataAccessException;
	
	String setAccJxMax(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询积休最大值
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	String queryAttendAcctop(Map<String, Object> map) throws DataAccessException;;

	String importRestInit(Map<String, Object> entityMap);
}
