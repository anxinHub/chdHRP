package com.chd.hrp.pac.dao.fkht.change;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface PactMoneyCFKHTMapper extends SqlMapper{
	
	/**
	 * 签订后合同变动 修改页面 明细数据查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactChangeFKHTAfterDetUpdate(Map<String, Object> page) throws DataAccessException;
	


}
