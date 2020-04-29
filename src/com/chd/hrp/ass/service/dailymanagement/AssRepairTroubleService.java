package com.chd.hrp.ass.service.dailymanagement;

import java.util.Map;

public interface AssRepairTroubleService {
   //查询故障统计
	public String querytrouble(Map<String, Object> entityMap);
   //查询费用统计
	public String queryAssRepairRec(Map<String, Object> entityMap);
	//查询维修工时
	public	String queryAssRepairHours(Map<String, Object> entityMap);
	//查询维修费用对比
	public	String queryAssRepairMoneyContrast(Map<String, Object> entityMap);

}
