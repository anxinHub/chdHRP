package com.chd.hrp.pac.service.cmitype;

import java.util.List;
import java.util.Map;


import org.springframework.dao.DataAccessException;


import com.chd.hrp.pac.entity.cmitype.PACTTemplateDataSource;




public interface PACTTemplateDataSourceService {
	/**
	 * 
	 * 增加
	 */
	public String addPACTTemplateDataSource(List<PACTTemplateDataSource> mapVo)throws DataAccessException;
	
	/**
	 * 
	 * 更新
	 */
	public String updatePACTTemplateDataSource(List<PACTTemplateDataSource> mapVo)throws DataAccessException;
	
	/**
	 * 
	 * 删除
	 */
	public String deletePACTTemplateDataSource(List<PACTTemplateDataSource> mapVo) throws DataAccessException;
	
	/**
	 * 
	 * 查询
	 */
	public String queryPACTTemplateDataSource(Map<String,Object> page) throws DataAccessException;


	
	
	
}
