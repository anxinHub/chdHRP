package com.chd.hrp.pac.dao.template.pacttemplateset;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface PactTemplateButtonMapper extends SqlMapper{
	/**
	 * 根据 方案id,模块id 查询 按钮信息   页面渲染按钮用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactTemplateButtonInfo(Map<String, Object> mapVo) throws DataAccessException;
	

}
