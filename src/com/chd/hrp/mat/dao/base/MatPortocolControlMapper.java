/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.base;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
 

public interface MatPortocolControlMapper extends SqlMapper{
	
	/**
	 * 物流协议控制数据查询 --材料入库 单价控制
	 * @param mapVo 协议编码，控制方式，明细数据 材料ID ，材料单价，材料金额
	 * @return map 材料编码，材料名称, 
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInPriceControl(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 物流协议控制数据查询 --材料入库 总额控制
	 * @param mapVo 协议编码，控制方式，明细数据 材料ID ，材料单价，材料金额
	 * @return map 返回金额预警信息
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInMoneyControl(Map<String, Object> mapVo) throws DataAccessException;
		
}
