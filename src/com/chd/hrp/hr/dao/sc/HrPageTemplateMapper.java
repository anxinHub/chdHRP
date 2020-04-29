package com.chd.hrp.hr.dao.sc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sc.HrPageTemplate;

public interface HrPageTemplateMapper  extends SqlMapper{
	
	List<HrPageTemplate> queryHrPageTemplate(Map<String, Object> entityMap) throws DataAccessException;
	
	HrPageTemplate queryHrPageTemplateByCode(Map<String, Object> entityMap) throws DataAccessException;

}
