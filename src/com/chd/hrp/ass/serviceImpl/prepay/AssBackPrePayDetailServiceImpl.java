package com.chd.hrp.ass.serviceImpl.prepay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.prepay.AssBackPrePayDetailMapper;
import com.chd.hrp.ass.dao.prepay.AssBackPrePayMainMapper;
import com.chd.hrp.ass.dao.prepay.AssBackPrePayStageMapper;
import com.chd.hrp.ass.entity.prepay.AssBackPrePayDetail;
import com.chd.hrp.ass.service.prepay.AssBackPrePayDetailService;

/**
 * 
 * @Description: 预退款主表
 * @Table: ASS_BACK_PRE_PAY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Service("assBackPrePayDetailService")
public class AssBackPrePayDetailServiceImpl implements AssBackPrePayDetailService {
	private static Logger logger = Logger.getLogger(AssBackPrePayDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBackPrePayMainMapper")
	private final AssBackPrePayMainMapper assBackPrePayMainMapper = null;
	
	@Resource(name = "assBackPrePayDetailMapper")
	private final AssBackPrePayDetailMapper assBackPrePayDetailMapper = null;
	
	@Resource(name = "assBackPrePayStageMapper")
	private final AssBackPrePayStageMapper assBackPrePayStageMapper = null;
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assBackPrePayDetailMapper.addBatch(entityMap);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		try {
			assBackPrePayStageMapper.delete(entityMap);
			assBackPrePayDetailMapper.delete(entityMap);
			//更新主表付款金额
			Double pay_money = 0.0;
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("group_id", entityMap.get("group_id"));
			paramMap.put("hos_id", entityMap.get("hos_id"));
			paramMap.put("copy_code", entityMap.get("copy_code"));
			paramMap.put("pay_no", entityMap.get("pay_no"));
			List<AssBackPrePayDetail> detailList = (List<AssBackPrePayDetail>) assBackPrePayDetailMapper.query(paramMap);
			for (AssBackPrePayDetail assBackPrePayDetail : detailList) {
				pay_money += assBackPrePayDetail.getPay_money();
			}
			paramMap.put("pay_money", pay_money);
			assBackPrePayMainMapper.updatePayMoney(paramMap);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\",\"pay_money\":\""+pay_money+"\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assBackPrePayStageMapper.deleteBatch(entityMap);
			assBackPrePayDetailMapper.deleteBatch(entityMap);
			
			//更新主表付款金额
			Double pay_money = 0.0;
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("group_id", entityMap.get(0).get("group_id"));
			paramMap.put("hos_id", entityMap.get(0).get("hos_id"));
			paramMap.put("copy_code", entityMap.get(0).get("copy_code"));
			paramMap.put("pay_no", entityMap.get(0).get("pay_no"));
			List<AssBackPrePayDetail> detailList = (List<AssBackPrePayDetail>) assBackPrePayDetailMapper.query(paramMap);
			for (AssBackPrePayDetail assBackPrePayDetail : detailList) {
				pay_money += assBackPrePayDetail.getPay_money();
			}
			paramMap.put("pay_money", pay_money);
			assBackPrePayMainMapper.updatePayMoney(paramMap);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\",\"pay_money\":\""+pay_money+"\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		return ChdJson.toJson(assBackPrePayDetailMapper.query(entityMap));
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assBackPrePayDetailMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assBackPrePayDetailMapper.queryByUniqueness(entityMap);
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assBackPrePayDetailMapper.queryExists(entityMap);
	}

}
