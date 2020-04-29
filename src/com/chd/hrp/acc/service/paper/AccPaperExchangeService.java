package com.chd.hrp.acc.service.paper;

import java.util.Map;

public interface AccPaperExchangeService {

	/**
	 * 汇率查询方法
	 * @param mapVo
	 * @return
	 */
	String queryAccExchange(Map<String, Object> mapVo);

	/**
	 * 汇率新增方法
	 * @param mapVo
	 * @return
	 */
	String addPaperExchange(Map<String, Object> mapVo);

	/**
	 * 汇率修改方法
	 * @param mapVo
	 * @return
	 */
	String updatePaperExchange(Map<String, Object> mapVo);

	/**
	 * 删除方法
	 * @param mapVo
	 * @return
	 */
	String deletePaperExchange(Map<String, Object> mapVo);

}
