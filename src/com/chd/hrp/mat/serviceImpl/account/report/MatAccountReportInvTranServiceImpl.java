/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.account.report;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.account.report.MatAccountReportInvTranMapper;
import com.chd.hrp.mat.service.account.report.MatAccountReportInvTranService;

/**
 * 
 * @Description:
 * 材料调拨汇总表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matAccountReportInvTranService")
public class MatAccountReportInvTranServiceImpl implements MatAccountReportInvTranService {

	private static Logger logger = Logger.getLogger(MatAccountReportInvTranServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matAccountReportInvTranMapper")
	private final MatAccountReportInvTranMapper matAccountReportInvTranMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatAccountReportInvTran(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = matAccountReportInvTranMapper.queryMatAccountReportInvTran(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
}
