package com.chd.task.msg;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.chd.base.quartz.ApplicationContextHelper;
import com.chd.base.util.DateUtil;
import com.chd.hrp.hr.entity.msg.SysMsg;
import com.chd.hrp.hr.entity.msg.SysMsgProducer;
import com.chd.hrp.hr.service.msg.SysMsgProducerService;
import com.chd.hrp.hr.service.msg.SysMsgService;
@Component
public class BirthMsgProducer implements Job{
	
	@Resource(name = "sysMsgService")
	private  SysMsgService sysMsgService = null;
	@Resource(name = "msgProducerService")
	private  SysMsgProducerService msgProducerService = null;
	
	public void execute(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		String jobName = jobExecutionContext.getJobDetail().getKey().getName();
		doJob(jobName);
	}

	public void doJob(String jobName) throws DataAccessException {
		//获取发送信息
		msgProducerService=(SysMsgProducerService) ApplicationContextHelper.getBean("msgProducerService");
		SysMsgProducer producer=msgProducerService.queryProducerByPrimaryKey(jobName);
		Map<String,Object> mapVo=new HashMap<String,Object>();
		String bd=DateUtil.getAfterDate(DateUtil.getSysDate()+" 00:00:00", producer.getBeforday());
		mapVo.put("curDay",bd);
		sysMsgService=(SysMsgService) ApplicationContextHelper.getBean("sysMsgService");
		List<Map<String,Object>> list=sysMsgService.getUserBirthMsg(mapVo);
		if(list!=null){
			SysMsg msg=null;
			for(Map<String,Object> map:list){
				msg=new SysMsg();
				msg.setBody(map.get("EMP_NAME")+"您好，"+bd.substring(0,12)+"日您的生日，祝您生日快乐！");
				msg.setCreatime(new Date());
				msg.setMsgtype((short)1);
				msg.setTitle("生日提醒");
				msg.setGroupId(new BigDecimal(producer.getGroupId()));
				msg.setHosId(new BigDecimal(producer.getHosId()));
				msg.setRoleids(producer.getRerole());
				msg.setUserids(producer.getReuser());
				sysMsgService.addMsg(msg);
			}
		}
	}
}
