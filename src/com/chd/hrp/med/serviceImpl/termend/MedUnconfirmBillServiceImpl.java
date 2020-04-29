package com.chd.hrp.med.serviceImpl.termend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.service.termend.MedUnconfirmBillService;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.dao.termend.MedUnconfirmBillMapper;
import com.github.pagehelper.PageInfo;

@Service("medUnconfirmBillService")
public class MedUnconfirmBillServiceImpl implements MedUnconfirmBillService {
	
	private static Logger logger = Logger.getLogger(MedUnconfirmBillServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medUnconfirmBillMapper")
	private final MedUnconfirmBillMapper medUnconfirmBillMapper = null;
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;

	@Override
	public String query(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = medUnconfirmBillMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medUnconfirmBillMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

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
	public String updateUnconfirmBill(Map<String, Object> entityMap) {
		try {
			Integer month=Integer.parseInt(entityMap.get("month").toString());
			Integer year=Integer.parseInt(entityMap.get("year").toString());

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
			
			int flag = medCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，下月期间不存在请配置！\",\"state\":\"false\"}";
			}
			
			entityMap.put("next_month", (next_month.toString().length()==1)?('0'+next_month.toString()):next_month.toString());
			entityMap.put("next_year", next_year.toString());
			
			String billFlag = entityMap.get("billFlag").toString();
			
			if(billFlag.length()==0) 
				return "{\"error\":\"结转失败 不存在结转数据! 方法 checkOutUnconfirmBill\"}";
			
			if(billFlag.contains("medin")){
				medUnconfirmBillMapper.updateUnconfirmBillByMedIn(entityMap);
			}
			if(billFlag.contains("medout")){
				medUnconfirmBillMapper.updateUnconfirmBillByMedOut(entityMap);
			}
			if(billFlag.contains("medtran")){
				medUnconfirmBillMapper.updateUnconfirmBillByMedTran(entityMap);
			}
			if(billFlag.contains("affiin")){
				medUnconfirmBillMapper.updateUnconfirmBillByAffiIn(entityMap);
			}
			if(billFlag.contains("affiout")){
				medUnconfirmBillMapper.updateUnconfirmBillByAffiOut(entityMap);
			}
			if(billFlag.contains("affitran")){
				medUnconfirmBillMapper.updateUnconfirmBillByAffiTran(entityMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"结转失败 数据库异常 请联系管理员!\"}");
		}
		
		return "{\"msg\":\"结转成功.\",\"state\":\"true\"}";
	}
}
