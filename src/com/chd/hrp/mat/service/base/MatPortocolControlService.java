/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.base;

import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlService;

public interface MatPortocolControlService {
	
	/**
	 * 材料入库付款协议单价控制返回消息查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryControlExecMatInPriceProcess(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 材料入库付款协议总额控制返回消息查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryControlExecMatInMoneyProcess(Map<String, Object> mapVo) throws DataAccessException;
}
