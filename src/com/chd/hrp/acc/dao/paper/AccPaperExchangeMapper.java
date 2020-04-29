package com.chd.hrp.acc.dao.paper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;

public interface AccPaperExchangeMapper extends SqlMapper {

	/**
	 * 汇率查询方法
	 * @param entityMap
	 * @return
	 */
	List<Map<String,Object>> queryAccExchange(Map<String, Object> entityMap);

	/**
	 * 汇率新增方法
	 * @param mapVo
	 * @return
	 */
	String queryAccExchangeByCode(Map<String, Object> mapVo);

	/**
	 * 新增汇率
	 * @param mapVo
	 * @return
	 */
	int addPaperExchange(Map<String, Object> mapVo);

	/**
	 * 修改汇率
	 * @param mapVo
	 * @return
	 */
	int updatePaperExchange(Map<String, Object> mapVo);

	int deletePaperExchange(@Param("list")String[] strid,@Param("vo")Map<String, Object> mapVo);

}
