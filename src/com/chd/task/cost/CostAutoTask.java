package com.chd.task.cost;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.cost.serviceImpl.CostDeptPeopleServiceImpl;
import com.chd.task.ass.AssMobileInventory;

/**
 * @Title:
 * @Description: 成本定时采集服务类
 * @Copyright: Copyright (c) 2017年4月27日 下午5:42:04
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Component
public class CostAutoTask {
	private static Logger logger = Logger.getLogger(CostAutoTask.class);
	@Resource(name = "costDeptPeopleService")
	private final CostDeptPeopleServiceImpl costDeptPeopleService = null;

	private Map<String, Object> aotoYearMonth() {
		Map<String, Object> map = new HashMap<String, Object>();

		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		String time = format.format(c.getTime());
		map.put("year_month", time);
		map.put("acc_year", time.substring(0, 4));
		map.put("acc_month", time.substring(4, 6));
		return map;
	}

	/**
	 * 定时同步科室人员采集
	 * 
	 * <pre>
	 * 注解方式：
	 * "@Scheduled(fixedRate = 1000 * 60 * 5) "
	 * "@Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次 "
	 * cron 说明
	 * "0 0 12 * * ?"    每天中午十二点触发 
	 * "0 15 10 ? * *"    每天早上10：15触发 
	 * "0 15 10 * * ?"    每天早上10：15触发 
	 * "0 15 10 * * ? *"    每天早上10：15触发 
	 * "0 15 10 * * ? 2005"    2005年的每天早上10：15触发 
	 * "0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发 
	 * "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发 
	 * "0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 
	 * "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发 
	 * "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发 
	 * "0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发
	 * </pre>
	 */
	public void autoPeopleCollect() {
		Map<String, Object> mapVo = new HashMap<String, Object>();
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.putAll(aotoYearMonth());
		try {
			costDeptPeopleService.addPeopleCollect(mapVo);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
}
