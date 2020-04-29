package com.chd.hrp.hr.dao.report;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

/**
 * 
 * @author Administrator
 *
 */
public interface HosPostDistributionMapper  extends SqlMapper{

	List<Map<String, Object>> queryPostDistributionByPrint(
			Map<String, Object> entityMap);

}
