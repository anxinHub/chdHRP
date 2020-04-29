package com.chd.hrp.budg.dao.business.purchase;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 资产采购预算
 * @author Administrator
 *
 */
public interface BugetMapper extends SqlMapper{
	/**
	 * 生成
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> copyBuget(Map<String, Object> entityMap) throws DataAccessException;

}
