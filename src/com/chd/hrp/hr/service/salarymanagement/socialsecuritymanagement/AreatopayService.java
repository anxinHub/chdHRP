package com.chd.hrp.hr.service.salarymanagement.socialsecuritymanagement;

import java.util.List;
import java.util.Map;

public interface AreatopayService {

	//社保险种下拉加载
	List<Map<String, Object>> queryAreatopayInsurtypeSelect(Map<String, Object> mapVo);

	//缴费金额添加
	String addAreapay(Map<String, Object> mapVo);

	//缴费基数添加
	String queryAreatopay(Map<String, Object> mapVo);

	//条件查询下拉加载
	List<Map<String, Object>> queryAreatopayInsurtypeSelects(Map<String, Object> mapVo);

	//缴费基数数据导入
	String importAreatopay(Map<String, Object> mapVo);
}
