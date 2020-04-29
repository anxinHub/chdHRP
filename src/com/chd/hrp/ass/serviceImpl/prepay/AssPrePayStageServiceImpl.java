package com.chd.hrp.ass.serviceImpl.prepay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.prepay.AssPrePayDetailMapper;
import com.chd.hrp.ass.dao.prepay.AssPrePayMainMapper;
import com.chd.hrp.ass.dao.prepay.AssPrePayStageMapper;
import com.chd.hrp.ass.entity.prepay.AssPrePayDetail;
import com.chd.hrp.ass.service.prepay.AssPrePayStageService;

/**
 * 
 * @Description: 预付款主表
 * @Table: ASS_PRE_PAY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Service("assPrePayStageService")
public class AssPrePayStageServiceImpl implements AssPrePayStageService {
	private static Logger logger = Logger.getLogger(AssPrePayStageServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assPrePayStageMapper")
	private final AssPrePayStageMapper assPrePayStageMapper = null;
	
	@Resource(name = "assPrePayMainMapper")
	private final AssPrePayMainMapper assPrePayMainMapper = null;
	
	@Resource(name = "assPrePayDetailMapper")
	private final AssPrePayDetailMapper assPrePayDetailMapper = null;

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		try {
			//先删除再添加
			assPrePayStageMapper.delete(entityMap);
			Double pay_money = 0.0; //更新主表付款金额
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			if(entityMap.get("data") != null && !"".equals(entityMap.get("data").toString())){
				List<Map> mapList = JSONArray.parseArray(entityMap.get("data").toString(), Map.class);
				for (Map<String,Object> map : mapList) {
					if(map.get("pay_code") != null){
						map.put("group_id", entityMap.get("group_id"));
						map.put("hos_id", entityMap.get("hos_id"));
						map.put("copy_code", entityMap.get("copy_code"));
						map.put("pay_no", entityMap.get("pay_no"));
						map.put("bill_no", entityMap.get("bill_no"));
						if (map.get("note") != null && !map.get("note").equals("")) {
							map.put("note", map.get("note"));
						} else {
							map.put("note", "");
						}
						list.add(new HashMap<String,Object>(map));
					}
				}
			}
			assPrePayStageMapper.addBatch(list);
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("group_id", entityMap.get("group_id"));
			paramMap.put("hos_id", entityMap.get("hos_id"));
			paramMap.put("copy_code", entityMap.get("copy_code"));
			paramMap.put("pay_no", entityMap.get("pay_no"));
			List<AssPrePayDetail> detailList = (List<AssPrePayDetail>) assPrePayDetailMapper.query(paramMap);
			for (AssPrePayDetail assPrePayDetail : detailList) {
				pay_money += assPrePayDetail.getPay_money();
			}
			entityMap.put("pay_money", pay_money);
			assPrePayMainMapper.updatePayMoney(entityMap);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\",\"pay_money\":\""+pay_money+"\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assPrePayStageMapper.addBatch(entityMap);
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
			assPrePayStageMapper.delete(entityMap);
			//更新主表付款金额
			Double pay_money = 0.0;
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("group_id", entityMap.get("group_id"));
			paramMap.put("hos_id", entityMap.get("hos_id"));
			paramMap.put("copy_code", entityMap.get("copy_code"));
			paramMap.put("pay_no", entityMap.get("pay_no"));
			List<AssPrePayDetail> detailList = (List<AssPrePayDetail>) assPrePayDetailMapper.query(paramMap);
			for (AssPrePayDetail assPrePayDetail : detailList) {
				pay_money += assPrePayDetail.getPay_money();
			}
			paramMap.put("pay_money", pay_money);
			assPrePayMainMapper.updatePayMoney(paramMap);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\",\"pay_money\":\""+pay_money+"\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assPrePayStageMapper.deleteBatch(entityMap);
			//更新主表付款金额
			Double pay_money = 0.0;
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("group_id", entityMap.get(0).get("group_id"));
			paramMap.put("hos_id", entityMap.get(0).get("hos_id"));
			paramMap.put("copy_code", entityMap.get(0).get("copy_code"));
			paramMap.put("pay_no", entityMap.get(0).get("pay_no"));
			List<AssPrePayDetail> detailList = (List<AssPrePayDetail>) assPrePayDetailMapper.query(paramMap);
			for (AssPrePayDetail assPrePayDetail : detailList) {
				pay_money += assPrePayDetail.getPay_money();
			}
			paramMap.put("pay_money", pay_money);
			assPrePayMainMapper.updatePayMoney(paramMap);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\",\"pay_money\":\""+pay_money+"\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		return ChdJson.toJson(assPrePayStageMapper.query(entityMap));
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assPrePayStageMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assPrePayStageMapper.queryByUniqueness(entityMap);
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assPrePayStageMapper.queryExists(entityMap);
	}

}
