package com.chd.hrp.ass.service.asspaysyncpact;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @author ChengXiaoDong
 * Date: 2020年3月24日 上午10:16:09
 *
 */
public interface AssPaySyncPactService extends SqlService{
	/**
	 * 资产付款数据同步至合同付款 mapVo 包括pay_no（付款/预付款单号） pre_pay是否预付款（0否 1是） mark（add delete update
	 * check uncheck） pi_ismakeup是否补录（0否 1是）
	 * @author Administrator
	 *
	 */
	int assPaySyncPact(Map<String, Object> mapVo);
	
}
