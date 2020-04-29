package com.chd.hrp.med.serviceImpl.sms;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.DateUtil;
import com.chd.hrp.med.dao.sms.MedSupSmsMapper;
import com.chd.hrp.med.service.sms.MedSupSmsService;

@Service("medSupSmsService")
public class MedSupSmsServiceImpl implements MedSupSmsService {

	private static Logger logger = Logger.getLogger(MedSupSmsServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "medSupSmsMapper")
	private final MedSupSmsMapper medSupSmsMapper = null;

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// 短信通知供应商

		Map<String, Object> smsmanage = messagecontent(entityMap);

		medSupSmsMapper.add(smsmanage);

		logger.debug("订单已发送给供应商 编号" + entityMap.get("order_code").toString());
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		for (Map<String, Object> map : entityMap) {
			add(map);
		}
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> smsmanage = messagecontent(entityMap);
		medSupSmsMapper.update(smsmanage);
		logger.debug("订单已发送给供应商 编号" + entityMap.get("order_code").toString());
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		medSupSmsMapper.updateBatch(entityMap);
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		medSupSmsMapper.delete(entityMap);
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		medSupSmsMapper.deleteBatch(entityMap);
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		medSupSmsMapper.query(entityMap);
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		medSupSmsMapper.queryByCode(entityMap);
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		medSupSmsMapper.queryByUniqueness(entityMap);
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = (List<Map<String, Object>>) medSupSmsMapper.queryExists(entityMap);

		return list;
	}

	private Map<String, Object> messagecontent(Map<String, Object> entityMap) {

		Map<String, Object> order = medSupSmsMapper.queryOrderByCode(entityMap);
		// 供应商联系电话
		StringBuilder sb = new StringBuilder();
		// 订单提醒：2014年11月20日
		// 10:32:08,由设备科[李胜婕],生成订单[共1种物资],订单编号:2014112000001,请及时处理！
		Object inv_amount = (order.get("inv_amount") == null ? 0 : order.get("inv_amount"));
		String pur_hos_name = (String) (order.get("pur_hos_name") == null ? "" : order.get("pur_hos_name").toString());
		String dept_name = (String) (order.get("dept_name") == null ? "" : order.get("dept_name").toString());
		String stocker_name = (String) (order.get("stocker_name") == null ? "" : order.get("stocker_name").toString());
		String telephone = (String) (order.get("telephone") == null ? "" : order.get("telephone").toString());
		
		sb.append("订单提醒：");
		sb.append(DateUtil.dateToString(new Date()));
		sb.append(pur_hos_name);
		sb.append("由");
		sb.append(dept_name);
		sb.append("[" + stocker_name+ "],");
		sb.append("生成订单");
		sb.append("[共" + inv_amount + "种物资]");
		sb.append("订单编号:" + order.get("order_code"));
		sb.append("请及时处理！");
 
		entityMap.put("messagecontent", sb.toString());
		entityMap.put("createtime", new Date());
		entityMap.put("status", 0);
		entityMap.put("destinationaddress", telephone);
		entityMap.put("order_code", order.get("order_code"));

		return entityMap;
	}
}
