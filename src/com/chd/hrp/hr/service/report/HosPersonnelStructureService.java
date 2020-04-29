package com.chd.hrp.hr.service.report;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 人员结构表
 * @author Administrator
 *
 */
public interface HosPersonnelStructureService {

	String queryHrPersonnelStructure(Map<String, Object> page)throws DataAccessException;
	 /**
		 * 打印
		 * 
		 * @param page
		 * @return
		 * @throws DataAccessException
		 */
	List<Map<String,Object>> queryPersonnelStructureByPrint(Map<String, Object> page)throws DataAccessException;

}
