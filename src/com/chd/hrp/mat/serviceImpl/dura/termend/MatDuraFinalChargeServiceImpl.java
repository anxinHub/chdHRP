package com.chd.hrp.mat.serviceImpl.dura.termend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.base.MatCommonMapper;

import com.chd.hrp.mat.dao.dura.termend.MatDuraFinalChargeMapper;
import com.chd.hrp.mat.entity.MatFinalCheck;
import com.chd.hrp.mat.service.dura.termend.MatDuraFinalChargeService;
import com.github.pagehelper.PageInfo;

@Service("matDuraFinalChargeService")
public class MatDuraFinalChargeServiceImpl implements MatDuraFinalChargeService {
	private static Logger logger = Logger.getLogger(MatDuraFinalChargeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matDuraFinalChargeMapper")
	private final MatDuraFinalChargeMapper matDuraFinalChargeMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public synchronized String updateMatDuraFinalCharge(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			//获取下个物流耐用品期间
			Map<String,Object> map_start = matCommonMapper.queryMatDuraStartYearMonth(entityMap);
			
			Integer month=Integer.parseInt(entityMap.get("month").toString());
			Integer year=Integer.parseInt(entityMap.get("year").toString());
			Integer before_month=month;
			Integer before_year=year;
			String p_before_month ="";
			String p_before_year ="";
			
			if(entityMap.get("month").toString().equals(map_start.get("acc_month").toString()) && entityMap.get("year").toString().equals(map_start.get("acc_year").toString())){
				p_before_month="00";
				p_before_year="0000";
			}else if(month==1){
				p_before_month="12";
				before_year=year - 1;
				p_before_year = before_year.toString();
				//验证下年期间是否存在
				//判断存不存在此会计期间，如果不存在，提示请配置。
			}else{
				before_month=month-1;
				p_before_month =(before_month.toString().length()==1)?('0'+before_month.toString()):before_month.toString();
				before_year=year;
				p_before_year =before_year.toString();
			}
			
			Integer next_month=month;
			Integer next_year=year;
			
			if(month==12){				
				next_month=1;
				next_year=year+1;
				//验证下年期间是否存在
				//判断存不存在此会计期间，如果不存在，提示请配置。
			}else{
				next_month=month+1;
				next_year=year;
			}
			
			//判断下一期间是否存在
			Map<String, Object> existsMap = new HashMap<String, Object>();
			
			existsMap.put("group_id", entityMap.get("group_id"));
			existsMap.put("hos_id", entityMap.get("hos_id"));
			existsMap.put("copy_code", entityMap.get("copy_code"));
			existsMap.put("year", next_year);
			existsMap.put("month", (next_month.toString().length()==1)?('0'+next_month.toString()):next_month.toString());
			
			int flag = matCommonMapper.existsMatYearMonthCheck(existsMap);
			if(flag == 0){
				return "{\"error\":\"结账失败，下月期间不存在请配置！\",\"state\":\"false\"}";
			}
			
			entityMap.put("before_year", p_before_year);
			entityMap.put("before_month",p_before_month);
			
			//完成结账
			entityMap.put("next_month", (next_month.toString().length()==1)?('0'+next_month.toString()):next_month.toString());
			entityMap.put("next_year", next_year.toString());
			

		    matDuraFinalChargeMapper.updateMatDuraFinalCharge(entityMap);
			
			String msg = entityMap.get("msg").toString();
			return msg;			
			//return "{\"msg\":\"结账成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"耐用品结账失败 数据库异常 请联系管理员! \"}");
		}
	}

	@Override
	public String updateMatDuraFinalInverse(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			Integer last_month=Integer.parseInt(entityMap.get("last_month").toString());
			Integer last_year=Integer.parseInt(entityMap.get("last_year").toString());
			
			Integer before_month=last_month;
			Integer before_year=last_year;
			
			if(last_month==1){
				before_month=12;
				before_year=last_year-1;				
			}else{
				before_month=last_month-1;
				before_year=last_year;
			}
	
			//完成结账
			entityMap.put("before_month", (before_month.toString().length()==1)?('0'+before_month.toString()):before_month.toString());
			entityMap.put("before_year", before_year.toString());
			
			matDuraFinalChargeMapper.updateMatDuraFinalInverse(entityMap);
			
			String msg = entityMap.get("msg").toString();			
			return msg;
			//return "{\"msg\":\"反结账成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"耐用品反结账失败 数据库异常 请联系管理员! \"}");
		}
	}

	@Override
	public String queryExistsMatDuraFinalUnconfirm(Map<String, Object> entityMap) throws DataAccessException {
		
		Integer count=matDuraFinalChargeMapper.existsMatDuraFinalUnconfirm(entityMap);
		
		if(count.intValue()!=0){
			return "{\"state\":\"false\"}";
		}
		return "{\"state\":\"true\"}";
	}	
}
