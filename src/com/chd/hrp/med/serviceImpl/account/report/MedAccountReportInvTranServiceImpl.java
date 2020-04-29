/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.account.report;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.account.report.MedAccountReportInvTranMapper;
import com.chd.hrp.med.service.account.report.MedAccountReportInvTranService;

/**
 * 
 * @Description:
 * 药品调拨汇总表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medAccountReportInvTranService")
public class MedAccountReportInvTranServiceImpl implements MedAccountReportInvTranService {

	private static Logger logger = Logger.getLogger(MedAccountReportInvTranServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medAccountReportInvTranMapper")
	private final MedAccountReportInvTranMapper medAccountReportInvTranMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedAccountReportInvTran(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = medAccountReportInvTranMapper.queryMedAccountReportInvTran(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
}
