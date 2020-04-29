package com.chd.hrp.hr.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface ImportService {
     /**
      * 导入
      * @param mapVo
      * @return
      * @throws DataAccessException
      */
	String importData(Map<String, Object> mapVo)throws DataAccessException;


}
