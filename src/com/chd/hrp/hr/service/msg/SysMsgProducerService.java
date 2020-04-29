package com.chd.hrp.hr.service.msg;

import java.util.Map;

import com.chd.hrp.hr.entity.msg.SysMsgProducer;

public interface SysMsgProducerService {

	public String getMsgProducers(Map<String,Object> mapVo);
	
	public SysMsgProducer queryProducerByPrimaryKey(String id);
	
	public String addProducer(SysMsgProducer producer);
	
	public String updateProducer(SysMsgProducer producer);
	
}
