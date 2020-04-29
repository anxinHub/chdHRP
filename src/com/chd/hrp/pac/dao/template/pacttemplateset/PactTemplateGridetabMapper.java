package com.chd.hrp.pac.dao.template.pacttemplateset;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface PactTemplateGridetabMapper extends SqlMapper{
	
	/**
	 *  查询 表格模块标签页配置  id
	 * @return
	 */
	public int queryNextSeq();
	
   public List<Map<String, Object>> queryCsCode(Map<String, Object> mapVo);
}
