/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.hrp.acc.dao.AccLederCheckMapper;
import com.chd.hrp.acc.service.AccLederCheckService;

/**
* @Title. @Description.
* 财务辅助账表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accLederCheckService")
public class AccLederCheckServiceImpl implements AccLederCheckService {

	private static Logger logger = Logger.getLogger(AccLederCheckServiceImpl.class);
	
	@Resource(name = "accLederCheckMapper")
	private final AccLederCheckMapper accLederCheckMapper = null;
	
	
	/**
	 * @Description 
	 * 财务辅助账表<BR> 导入AccLederCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccLederCheck(Map<String,Object> entityMap)throws DataAccessException{
		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";
		}
	}

}
