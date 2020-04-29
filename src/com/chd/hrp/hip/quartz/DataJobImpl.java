package com.chd.hrp.hip.quartz;

import java.util.Date;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.chd.base.util.DateUtil;
import com.chd.base.util.HttpClient;
import com.chd.hrp.hip.entity.HipDataJob;

public class DataJobImpl implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String uri=null;
		Map<String,Object> paraMap=context.getJobDetail().getJobDataMap();
		try{			
			uri=paraMap.get("uri").toString()+"/hrp/hip/dataType/syncData.do";
			paraMap.put("isAuto", true);
			paraMap.put("isCheck", false);
			String[] bbd=getJobDate((HipDataJob)paraMap.get("job"));
			paraMap.put("begin_date", bbd[0]);
			paraMap.put("end_date", bbd[1]);
			if(uri!=null){
				HttpClient.doPost(uri, paraMap);
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
		
	}

	private String[] getJobDate(HipDataJob job){
		String[] bbd=new String[2];
		Date curDate=DateUtil.getCurrenDate("yyyy-MM-dd");
		String sdate=DateUtil.dateToString(curDate);
		int cream=0;
		try{
			if(job.getPtype()==5){//按周计算
				int ck=DateUtil.getCurWeek(curDate);
				cream=ck;
				bbd[1]=DateUtil.getAfterDate(sdate, ((job.getPeriod()-1)*7+cream)*-1);
				bbd[0]=DateUtil.getAfterDate(sdate, (job.getPeriod()*7+cream-1)*-1);	
			}else if(job.getPtype()==4){//按月计算
				String dm=DateUtil.dateToString(curDate, "yyyy-MM");
				String pdm=DateUtil.getPerMonth(dm,job.getPeriod()*-1);
				bbd[0]=pdm+"-1";
				bbd[1]=pdm+"-"+DateUtil.getMaxMonthDate(pdm.split("-")[0],pdm.split("-")[1]);			
			}else if(job.getPtype()==3){
				bbd[1]=DateUtil.getAfterDate(sdate, job.getPeriod()*-1);
				bbd[0]=DateUtil.getAfterDate(sdate, job.getPeriod()*-1);	
			}else if(job.getPtype()==2){
				bbd[1]=DateUtil.getAfterDate(sdate, job.getPeriod()*-1);
				bbd[0]=DateUtil.getAfterDate(sdate, job.getPeriod()*-1);	
			}else if(job.getPtype()==1){
				bbd[1]=DateUtil.getAfterDate(sdate, job.getPeriod()*-1);
				bbd[0]=DateUtil.getAfterDate(sdate, job.getPeriod()*-1);	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return bbd;
	}

}
