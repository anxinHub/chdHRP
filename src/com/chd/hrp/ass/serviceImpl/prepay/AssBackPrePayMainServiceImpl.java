package com.chd.hrp.ass.serviceImpl.prepay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.prepay.AssBackPrePayDetailMapper;
import com.chd.hrp.ass.dao.prepay.AssBackPrePayMainMapper;
import com.chd.hrp.ass.dao.prepay.AssPrePayMainMapper;
import com.chd.hrp.ass.dao.prepay.AssPrePayMapper;
import com.chd.hrp.ass.dao.prepay.AssBackPrePayStageMapper;
import com.chd.hrp.ass.entity.prepay.AssPrePay;
import com.chd.hrp.ass.entity.prepay.AssBackPrePayDetail;
import com.chd.hrp.ass.entity.prepay.AssBackPrePayMain;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.prepay.AssBackPrePayMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 预退款主表
 * @Table: ASS_BACK_PRE_PAY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */ 
@Service("assBackPrePayMainService")
public class AssBackPrePayMainServiceImpl implements AssBackPrePayMainService {
	private static Logger logger = Logger.getLogger(AssBackPrePayMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBackPrePayMainMapper")
	private final AssBackPrePayMainMapper assBackPrePayMainMapper = null;
	
	@Resource(name = "assBackPrePayDetailMapper")
	private final AssBackPrePayDetailMapper assBackPrePayDetailMapper = null;
	
	@Resource(name = "assBackPrePayStageMapper")
	private final AssBackPrePayStageMapper assBackPrePayStageMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "assPrePayMapper")
	private final AssPrePayMapper assPrePayMapper = null;

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		return null;
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
			
			assBackPrePayDetailMapper.delete(entityMap);
			assBackPrePayMainMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}	
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assBackPrePayStageMapper.deleteBatch(entityMap);
			assBackPrePayDetailMapper.deleteBatch(entityMap);
			assBackPrePayMainMapper.deleteBatch(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		//要添加的明细列表
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		Double pay_money = 0.0;
		
		//判断是否存在对象预付款主表
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("pay_no", entityMap.get("pay_no"));
		if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
			entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
			entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
		}
		entityMap.put("bill_table", "ASS_BACK_PRE_PAY_MAIN");
		List<AssBackPrePayMain> list = (List<AssBackPrePayMain>)assBackPrePayMainMapper.queryExists(mapVo);
		
		try {
			if (list.size()>0) {
				assBackPrePayMainMapper.update(entityMap);
			}else{
				String pay_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("pay_no", pay_no);
				assBackPrePayMainMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}

			//解析明细列表
			List<Map> detailList = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			
			for (Map<String, Object> detail : detailList) {
				if (detail.get("bill_no") == null || "".equals(detail.get("bill_no"))) {
					continue;
				}
				detail.put("group_id", entityMap.get("group_id"));
				detail.put("hos_id", entityMap.get("hos_id"));
				detail.put("copy_code", entityMap.get("copy_code"));
				detail.put("pay_no", entityMap.get("pay_no"));
				
				Double bill_money = Double.parseDouble(detail.get("bill_money").toString());
				detail.put("bill_money", bill_money);
				
				//pay_money = pay_money + Double.parseDouble(detail.get("pay_money").toString());
				
				if (detail.get("note") != null && !detail.get("note").equals("")) {
					detail.put("note", detail.get("note"));
				} else {
					detail.put("note", "");
				}
				
				AssBackPrePayDetail assBackPrePayDetail = assBackPrePayDetailMapper.queryByCode(detail);
				if(assBackPrePayDetail == null){
					listVo.add(detail);
				}
				
			}
			
			//assBackPrePayStageMapper.delete(entityMap);
			//assBackPrePayDetailMapper.delete(entityMap);
			if(listVo != null && listVo.size() > 0 ){
				assBackPrePayDetailMapper.addBatch(listVo);
			}
			//entityMap.put("pay_money", pay_money);
			//assBackPrePayMainMapper.updatePayMoney(entityMap);//放到添加付款方式时更新付款金额
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"pay_no\":\"" + entityMap.get("pay_no").toString()
					+ "\",\"pay_money\":\"" + pay_money + "\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssBackPrePayMain> list = (List<AssBackPrePayMain>)assBackPrePayMainMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssBackPrePayMain> list = (List<AssBackPrePayMain>)assBackPrePayMainMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assBackPrePayMainMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assBackPrePayMainMapper.queryByUniqueness(entityMap);
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assBackPrePayMainMapper.queryExists(entityMap);
	}

	@Override
	public String queryAssBackPreBill(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String,Object>> list = assBackPrePayMainMapper.queryAssBackPreBill(entityMap);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	@Override
	public String queryAssBackPreBillDetail(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			if(entityMap.get("bill_no") !=null && !"".equals(entityMap.get("bill_no").toString())){
				list = assBackPrePayMainMapper.queryAssBackPreBillDetail(entityMap);
			}
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryAccPayType(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String,Object>> listMap = assBackPrePayMainMapper.queryAccPayType(entityMap);
			return JSON.toJSONString(listMap);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 预退款模板打印
	 */
	@Override
	public Map<String,Object> queryAssBackPrePayMainDY(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssBackPrePayMainMapper assBackPrePayMainMapper = (AssBackPrePayMainMapper)context.getBean("assBackPrePayMainMapper");
			if("1".equals(String.valueOf(map.get("p_num")))){
				// 资产维修打印模板主表
				List<Map<String,Object>> mainList=assBackPrePayMainMapper.queryAssBackPrePayMainBatch(map);
						
				//资产维修打印模板从表
				List<Map<String,Object>> detailList=assBackPrePayMainMapper.queryAssBackPrePayMain_detail(map);
				
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}else{
				Map<String,Object> mainList=assBackPrePayMainMapper.querAssBackPrePayMainByPrint(map);
				
				//资产维修打印模板从表
				List<Map<String,Object>> detailList=assBackPrePayMainMapper.queryAssBackPrePayMain_detail(map);
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}
			
		
			
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	@Override
	public String updateConfirmPrePay(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {
			for (Map<String, Object> map : entityMap) {
				//核定表添加记录
				List<Map<String,Object>> assBackPrePaymapList = assBackPrePayMainMapper.collectAssPrePayData(map);
				for (Map<String, Object> assBackPrePaymap : assBackPrePaymapList) {
					if(assBackPrePaymap.get("pay_code") == null || "".equals(assBackPrePaymap.get("pay_code").toString())){
						return "{\"warn\":\""+map.get("pay_no")+"未维护支付方式.\",\"state\":\"false\"}";
					}
					if(assBackPrePaymap.get("source_id") == null || "".equals(assBackPrePaymap.get("source_id").toString())){
						return "{\"warn\":\"未维护支付方式与资金来源对应关系.\",\"state\":\"false\"}";
					}
					AssPrePay assBackPrePay = assPrePayMapper.queryByCode(assBackPrePaymap);
					if(assBackPrePay == null){
						assPrePayMapper.add(assBackPrePaymap);
					}else{
						assBackPrePaymap.put("cur_money", assBackPrePay.getCur_money()+Double.parseDouble(assBackPrePaymap.get("cur_money").toString()));
						assPrePayMapper.updateCurMoney(assBackPrePaymap);
					}
				}
				
			}
			//主表确认更新
			assBackPrePayMainMapper.updateBatchConfirm(entityMap);
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<String> queryAssBackPrepayState(Map<String, Object> mapVo)
			throws DataAccessException {
	
		return assBackPrePayMainMapper.queryAssBackPrepayState(mapVo);
	}

}
