package com.chd.base.quartz;

import javax.annotation.Resource;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.mvc.Mvcs;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.dao.DataAccessException;
import org.apache.log4j.Logger;

import com.chd.task.ass.AssMobileInventory;

public class HrJob implements Job {

	private static Logger logger = Logger.getLogger(HrJob.class);
	
	@Resource(name = "assMobileInventory")
	private AssMobileInventory assMobileInventory;
	/**
	 * 
	 */
	public void execute(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		String jobName = jobExecutionContext.getJobDetail().getKey().getName();
	//	System.out.println(new Date() + ":测试--->" + jobName);
		assMobileInventory.autoCheckData();
	//	doJob(jobName);
	}

	public void doJob(String jobName) throws DataAccessException {
		Dao nutDao = Mvcs.ctx().getDefaultIoc().get(Dao.class, "dao");
		Sql sql = Sqls.create("CALL "+jobName);
		sql.setEntity(nutDao.getEntity(String.class));
		//sql.params().set("copy_code", "3"); // 设置入参
		//sql.params().set("dept_code", "22");
		//sql.params().set("OUTHR_APPCODE", OracleTypes.VARCHAR);
		//sql.params().set("OUTHR_ERRTXT", OracleTypes.VARCHAR);
		sql = nutDao.execute(sql);
		Record re = sql.getOutParams();
	}
}