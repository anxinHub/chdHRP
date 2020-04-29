package com.chd.hrp.mat.serviceImpl.sms;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.dao.Chain;
import org.nutz.dao.Dao;
import org.nutz.mvc.Mvcs;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.PushSMS.PushMessage;
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.dao.sms.MatSupSmsMapper;
import com.chd.hrp.mat.service.sms.MatSupSmsService;

@Service("matSupSmsService")
public class MatSupSmsServiceImpl implements MatSupSmsService {

	private static Logger logger = Logger.getLogger(MatSupSmsServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "matSupSmsMapper")
	private final MatSupSmsMapper matSupSmsMapper = null;

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// 短信通知供应商
		
		String supErrMsgs="";

		Map<String, Object> smsmanage = messagecontent(entityMap);

		matSupSmsMapper.add(smsmanage);

		Object send_para = MyConfig.getSysPara("04060");

		send_para = send_para == null ? "0" : send_para;

		int is_sms = Integer.valueOf(send_para.toString());
		// is_sms 各医院发送标识 新增医院递增累加 0:不发生 1:台州 2:东阳
		switch (is_sms) {
		case 1:
			// 发送短信通知供应商
			Map<String, Object> sendMap = new HashMap<String, Object>();

			sendMap.put("PhoneNum", smsmanage.get("destinationaddress"));

			sendMap.put("Content", smsmanage.get("messagecontent"));

			PushMessage pm = PushMessage.getInstance();

			pm.send(sendMap);
			logger.debug("订单已发送给供应商 编号" + entityMap.get("order_code").toString());

			break;
		case 2:

			supErrMsgs=sedSMSDY(smsmanage);
			
			break;
		}

		return supErrMsgs;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {

		PushMessage pm = PushMessage.getInstance();

		Object send_para = MyConfig.getSysPara("04060");

		send_para = send_para == null ? "0" : send_para;

		int is_sms = Integer.valueOf(send_para.toString());
		
		String supErrMsgs="";//用于记录号码错误的供应商名称(号码必须11位)

		for (Map<String, Object> map : entityMap) {

			Map<String, Object> smsmanage = messagecontent(map);
			if(smsmanage==null){
				continue;
			}
			matSupSmsMapper.add(smsmanage);

			// is_sms 各医院发送标识 新增医院递增累加 0:不发生 1:台州 2:东阳
			switch (is_sms) {
			case 1: 
				// 发送短信通知供应商
				Map<String, Object> sendMap = new HashMap<String, Object>();

				sendMap.put("PhoneNum", smsmanage.get("destinationaddress"));

				sendMap.put("Content", smsmanage.get("messagecontent"));

				pm.send(sendMap);
				logger.debug("订单已发送给供应商 编号" + smsmanage.get("order_code"));

				break;
			case 2:

				String supMsg=sedSMSDY(smsmanage); //正确为"",错误为供应商名称
				if (!"".equals(supMsg)) {
					supErrMsgs+=supMsg+" ";
				}
				break;
			}

		}
		return supErrMsgs;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> smsmanage = messagecontent(entityMap);
		matSupSmsMapper.update(smsmanage);
		logger.debug("订单已发送给供应商 编号" + entityMap.get("order_code").toString());
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		matSupSmsMapper.updateBatch(entityMap);
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		matSupSmsMapper.delete(entityMap);
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		matSupSmsMapper.deleteBatch(entityMap);
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		matSupSmsMapper.query(entityMap);
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		matSupSmsMapper.queryByCode(entityMap);
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		matSupSmsMapper.queryByUniqueness(entityMap);
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = (List<Map<String, Object>>) matSupSmsMapper.queryExists(entityMap);

		return list;
	}

	private Map<String, Object> messagecontent(Map<String, Object> entityMap) {

		//只查询未通知的订单，否则MAT_SUP_SMS表联合主键会报错
		Map<String, Object> order = matSupSmsMapper.queryOrderByCode(entityMap);
		if(order==null){
			return null;
		}
		
		
		// 供应商联系电话
		StringBuilder sb = new StringBuilder();
		// 订单提醒：2014年11月20日
		// 10:32:08,由设备科[李胜婕],生成订单[共1种物资],订单编号:2014112000001,请及时处理！
		Object inv_amount = (order.get("inv_amount") == null ? 0 : order.get("inv_amount"));
		String pur_hos_name = (String) (order.get("pur_hos_name") == null ? "" : order.get("pur_hos_name").toString());
		String dept_name = (String) (order.get("dept_name") == null ? "" : order.get("dept_name").toString());
		String stocker_name = (String) (order.get("stocker_name") == null ? "" : order.get("stocker_name").toString());
		String telephone = (String) (order.get("telephone") == null ? "" : order.get("telephone").toString());

		String sup_id = (String) (order.get("sup_id") == null ? "" : order.get("sup_id").toString());
		String sup_name = (String) (order.get("sup_name") == null ? "" : order.get("sup_name").toString());

		sb.append("订单提醒：");
		sb.append(DateUtil.dateToString(new Date()));
		sb.append(pur_hos_name);
		sb.append("由");
		sb.append(dept_name);
		sb.append("[" + stocker_name + "],");
		sb.append("生成订单");
		sb.append("[共" + inv_amount + "种物资]");
		sb.append("订单编号:" + order.get("order_code"));
		sb.append("请及时处理！");

		entityMap.put("messagecontent", sb.toString());
		entityMap.put("createtime", new Date());
		entityMap.put("status", 0);
		entityMap.put("destinationaddress", telephone);
		entityMap.put("order_code", order.get("order_code"));

		entityMap.put("sup_id", sup_id);
		entityMap.put("sup_name", sup_name);

		return entityMap;
	}

	private String sedSMSDY(Map<String, Object> entityMap) {
		try{
			
			// insert dbo.HosMobileSend( YsID , YsXm , MobileTelNo ,Rq , Msg , SendRq
			// ,SendResult ,SendResultText ,DelFlag )  
			// values ( :'供应商ID',:'供应商名称' ,:'手机号' ,:'now()' ,:'短信内容' ,:'now()' ,0 ,'' ,'0')
			// Chain ch = Chain.make("字段名",字段值); SendResult：1 表示 发送   0
			Dao daoDYSMS = Mvcs.ctx().getDefaultIoc().get(Dao.class, "daoDYSMS");
			
			Chain ch = Chain.make("YsID", entityMap.get("sup_id"));
			ch.add("YsXm", entityMap.get("sup_name"));
			ch.add("MobileTelNo", entityMap.get("destinationaddress"));
			ch.add("Rq", DateUtil.dateToString(new Date()));
			ch.add("Msg", entityMap.get("messagecontent"));
			ch.add("SendRq", DateUtil.dateToString(new Date()));
			ch.add("SendResult", "1");
			ch.add("SendResultText", "");
			ch.add("DelFlag", "0");
			
			if (daoDYSMS != null) {
				daoDYSMS.insert("HosMobileSend", ch);
				logger.debug("订单已发送给供应商 编号" + entityMap.get("order_code").toString());
			} else {
				logger.debug("发送短信的数据源未配置！请配置数据源  路径  dao.js");
			}
			return "";
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return String.valueOf(entityMap.get("sup_name"));
		}

	}
}
