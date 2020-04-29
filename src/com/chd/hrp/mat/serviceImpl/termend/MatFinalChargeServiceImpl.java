package com.chd.hrp.mat.serviceImpl.termend;

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
import com.chd.hrp.mat.dao.termend.MatFinalChargeMapper;
import com.chd.hrp.mat.entity.MatFinalCheck;
import com.chd.hrp.mat.service.termend.MatFinalChargeService;
import com.github.pagehelper.PageInfo;

@Service("matFinalChargeService")
public class MatFinalChargeServiceImpl implements MatFinalChargeService {

	private static Logger logger = Logger.getLogger(MatUnconfirmBillServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matFinalChargeMapper")
	private final MatFinalChargeMapper matFinalChargeMapper = null;
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
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<MatFinalCheck> list = (List<MatFinalCheck>)matFinalChargeMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<MatFinalCheck> list = (List<MatFinalCheck>)matFinalChargeMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
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
	public synchronized String updateMatFinalCharge(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			/*---20161121---hsy--由于确认出入库时直接修改期间年月所以去掉该限制*/
			//验证是否存在未确认单据
			/*Integer count=matFinalChargeMapper.existsMatFinalUnconfirm(entityMap);
			
			if(count.intValue()!=0){
				return "{\"error\":\"结转失败，本月存在未确认单据！\",\"state\":\"false\"}";
			}*/
			/*---20161121---hsy--由于确认出入库时直接修改期间年月所以去掉该限制*/
			//获取下个物流期间
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
			

			//完成结账
			entityMap.put("next_month", (next_month.toString().length()==1)?('0'+next_month.toString()):next_month.toString());
			entityMap.put("next_year", next_year.toString());
			
			int by_store = Integer.valueOf(MyConfig.getSysPara("04045").toString());
			if(by_store == 0){
				matFinalChargeMapper.updateMatFinalCharge(entityMap);
			}else{
				matFinalChargeMapper.updateMatFinalChargeStore(entityMap);
			}
			//System.out.println(entityMap.get("msg").toString());
			String msg = entityMap.get("msg").toString();
			return msg;
			
			//return "{\"msg\":\"结账成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"结账失败 数据库异常 请联系管理员! \"}");
		}
	}

	@Override
	public String updateMatFinalInverse(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			/*---20161121---hsy--由于确认出入库时直接修改期间年月所以去掉该限制*/
			/*---20170405---hsy--再次去掉该限制*/
			//验证是否存在未确认单据
			/*Integer count=matFinalChargeMapper.existsMatFinalUnconfirm(entityMap);
			
			if(count.intValue()!=0){
				return "{\"error\":\"结转失败，本月存在未确认单据！\",\"state\":\"false\"}";
			}*/
			/*---20161121---hsy--由于确认出入库时直接修改期间年月所以去掉该限制*/
			//获取上个物流期间
			Integer last_month=Integer.parseInt(entityMap.get("last_month").toString());
			Integer last_year=Integer.parseInt(entityMap.get("last_year").toString());
			
			Integer before_month=last_month;
			Integer before_year=last_year;
			
			if(last_month==1){
				before_month=12;
				before_year=last_year-1;
				//验证下年期间是否存在
				//判断存不存在此会计期间，如果不存在，提示请配置。
			}else{
				before_month=last_month-1;
				before_year=last_year;
			}
	
			//完成结账
			entityMap.put("before_month", (before_month.toString().length()==1)?('0'+before_month.toString()):before_month.toString());
			entityMap.put("before_year", before_year.toString());
			
			int by_store = Integer.valueOf(MyConfig.getSysPara("04045"));
			
			if(by_store == 0){
				matFinalChargeMapper.updateMatFinalInverse(entityMap);
			}else{
				matFinalChargeMapper.updateMatFinalInverseStore(entityMap);
			}
			//System.out.println(entityMap.get("msg").toString());
			String msg = entityMap.get("msg").toString();
			
			return msg;
			
			//return "{\"msg\":\"结账成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"反结账失败 数据库异常 请联系管理员! \"}");
		}
	}

	@Override
	public String queryExistsMatFinalUnconfirm(Map<String, Object> entityMap) throws DataAccessException {
		
		Integer count=matFinalChargeMapper.existsMatFinalUnconfirm(entityMap);
		
		if(count.intValue()!=0){
			return "{\"state\":\"false\"}";
		}
		return "{\"state\":\"true\"}";
	}

	@Override
	public String queryYearMonthByStoreSet(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String, Object> map = matFinalChargeMapper.queryYearMonthByStoreSet(entityMap);
		
		String year_month = "";
		String before_year_month = "";
		
		if(map != null){
			year_month = map.get("YEAR_MONTH") == null ? "" : map.get("YEAR_MONTH").toString();
			before_year_month = map.get("BEFORE_YEAR_MONTH") == null ? "" : map.get("BEFORE_YEAR_MONTH").toString();
		}
		
		return "{\"state\":\"true\",\"year_month\":\""+year_month+"\",\"before_year_month\":\""+before_year_month+"\"}";
	}
}
