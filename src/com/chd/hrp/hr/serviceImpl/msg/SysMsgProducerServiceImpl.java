package com.chd.hrp.hr.serviceImpl.msg;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chd.base.quartz.QuartzManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.msg.SysMsgProducerMapper;
import com.chd.hrp.hr.entity.msg.SysMsgProducer;
import com.chd.hrp.hr.service.msg.SysMsgProducerService;
import com.github.pagehelper.PageInfo;

@Service("msgProducerService")
public class SysMsgProducerServiceImpl implements SysMsgProducerService{

	@Resource(name = "sysMsgProducerMapper")
	private SysMsgProducerMapper sysMsgProducerMapper;
	@Override
	public String getMsgProducers(Map<String, Object> mapVo) {
		List<SysMsgProducer> list=sysMsgProducerMapper.selectByExample(null);
		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());
	}
	@Override
	public SysMsgProducer queryProducerByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		SysMsgProducer producer=sysMsgProducerMapper.selectByPrimaryKey(Integer.parseInt(id));
		return producer;
	}
	@Override
	public String addProducer(SysMsgProducer producer) {
		// TODO Auto-generated method stub
		sysMsgProducerMapper.insert(producer);
		return  "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	}
	@Override
	public String updateProducer(SysMsgProducer producer) {
		
		Object o=null;
		try {
			o=Class.forName(producer.getProducer());
			if(producer.getStatus()==1){
				
				if(QuartzManager.hasJob(producer.getId().toString(), producer.getProducer(), producer.getId().toString(), producer.getProducer())){
					QuartzManager.removeJob(producer.getId().toString(), producer.getProducer(), producer.getId().toString(), producer.getProducer());
					QuartzManager.addJob(producer.getId().toString(), producer.getProducer(), producer.getId().toString(), producer.getProducer(),QuartzManager.jobMap.get(producer.getProducer()), producer.getCron(),null);
				}else{
					QuartzManager.addJob(producer.getId().toString(), producer.getProducer(), producer.getId().toString(), producer.getProducer(),QuartzManager.jobMap.get(producer.getProducer()), producer.getCron(),null);
				}
			}else{
				if(QuartzManager.hasJob(producer.getId().toString(), producer.getProducer(), producer.getId().toString(), producer.getProducer())){
					QuartzManager.removeJob(producer.getId().toString(), producer.getProducer(), producer.getId().toString(), producer.getProducer());
				}
			}
		} catch (ClassNotFoundException e) {
			return "{\"msg\":\"更新失败,无法找到消息生产者.\",\"state\":\"false\"}";
		}
		
		
		sysMsgProducerMapper.updateByPrimaryKeySelective(producer);	
		return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	}

}
