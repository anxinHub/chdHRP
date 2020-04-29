package com.chd.hrp.pac.dao.template.pacttemplateset;

import com.chd.base.SqlMapper;

public interface PactTemplateSheetMapper extends SqlMapper{
	/**
	 * 查询 表单元素配置 id
	 * @return
	 */
	public int queryNextSeq();
	

}
