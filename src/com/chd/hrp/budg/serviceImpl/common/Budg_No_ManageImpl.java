package com.chd.hrp.budg.serviceImpl.common;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.hrp.budg.dao.common.BudgNoManageMapper;
import com.chd.hrp.budg.service.common.Budg_No_Manage;
@Service("budg_No_Manage")
public class Budg_No_ManageImpl implements Budg_No_Manage{
	private static Logger logger = Logger.getLogger(BudgComTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgNoManageMapper")
	private final BudgNoManageMapper budgNoManageMapper = null;
	/**
	 * 生成table_code表的下一个单号（使用于出入库业务）
	 * @param entityMap
	 * @return
	 */	
	public String getMatNextNo(Map<String, Object> entityMap) throws DataAccessException {
		if(entityMap.get("group_id") == null){
			entityMap.put("group_id", SessionManager.getGroupId());
		}
		if(entityMap.get("hos_id") == null){
			entityMap.put("hos_id", SessionManager.getHosId());
		}
		if(entityMap.get("copy_code") == null){
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		map.put("check_code", entityMap.get("check_code"));
		map.put("adjust_code", entityMap.get("adjust_code"));
		map.put("table_code", entityMap.get("table_code").toString().toUpperCase());
		map.put("year", entityMap.get("year"));
		map.put("month", entityMap.get("month"));
		//生成日期(只需要取到日即可,来源于系统日期)
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String day =df.format(new Date()).substring(6,2);
		map.put("prefixe", entityMap.get("prefixe"));
		//获取check_code
		String check_code = budgNoManageMapper.queryCheckCode(map);
        map.put("check_code", check_code);
		//判断是否存在该业务流水码
		int flag = budgNoManageMapper.queryIsExists(map);
		String max_no = "";
		if(flag == 0){
			//如不存在则流水码为1，并插入流水码表中
			max_no = "1";
			map.put("max_no", 1);
			budgNoManageMapper.add(map) ;
		}else{
			//更新该业务流水码+1
			budgNoManageMapper.updateMaxNo(map);
			//取出该业务更新后的流水码
			max_no = budgNoManageMapper.queryMaxCode(map);
		}
		//补流水码前缀0
		for (int i = max_no.length() ; i < 4; i++) {
			max_no = "0" + max_no;
		}
		//组装流水码
		String next_no = check_code + "-" + entityMap.get("year").toString().substring(2, 4) +day + max_no;	
		return next_no;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
