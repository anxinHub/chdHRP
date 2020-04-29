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
import com.chd.hrp.ass.dao.prepay.AssPrePayDetailMapper;
import com.chd.hrp.ass.dao.prepay.AssPrePayMainMapper;
import com.chd.hrp.ass.dao.prepay.AssPrePayMapper;
import com.chd.hrp.ass.dao.prepay.AssPrePayStageMapper;
import com.chd.hrp.ass.dao.repair.AssRepairRecMapper;
import com.chd.hrp.ass.entity.prepay.AssPrePay;
import com.chd.hrp.ass.entity.prepay.AssPrePayDetail;
import com.chd.hrp.ass.entity.prepay.AssPrePayMain;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.prepay.AssPrePayMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 预付款主表
 * @Table: ASS_PRE_PAY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */ 
@Service("assPrePayMainService")
public class AssPrePayMainServiceImpl implements AssPrePayMainService {
	private static Logger logger = Logger.getLogger(AssPrePayMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assPrePayMainMapper")
	private final AssPrePayMainMapper assPrePayMainMapper = null;
	
	@Resource(name = "assPrePayDetailMapper")
	private final AssPrePayDetailMapper assPrePayDetailMapper = null;
	
	@Resource(name = "assPrePayStageMapper")
	private final AssPrePayStageMapper assPrePayStageMapper = null;
	
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
			
			assPrePayDetailMapper.delete(entityMap);
			assPrePayMainMapper.delete(entityMap);
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
			assPrePayStageMapper.deleteBatch(entityMap);
			assPrePayDetailMapper.deleteBatch(entityMap);
			assPrePayMainMapper.deleteBatch(entityMap);
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
		entityMap.put("bill_table", "ASS_PRE_PAY_MAIN");
		List<AssPrePayMain> list = (List<AssPrePayMain>)assPrePayMainMapper.queryExists(mapVo);
		
		try {
			if (list.size()>0) {
				assPrePayMainMapper.update(entityMap);
			}else{
				String pay_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("pay_no", pay_no);
				assPrePayMainMapper.add(entityMap);
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
				
				AssPrePayDetail assPrePayDetail = assPrePayDetailMapper.queryByCode(detail);
				if(assPrePayDetail == null){
					listVo.add(detail);
				}
				
			}
			
			//assPrePayStageMapper.delete(entityMap);
			//assPrePayDetailMapper.delete(entityMap);
			if(listVo != null && listVo.size() > 0 ){
				assPrePayDetailMapper.addBatch(listVo);
			}
			//entityMap.put("pay_money", pay_money);
			//assPrePayMainMapper.updatePayMoney(entityMap);//放到添加付款方式时更新付款金额
			
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
			
			List<AssPrePayMain> list = (List<AssPrePayMain>)assPrePayMainMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssPrePayMain> list = (List<AssPrePayMain>)assPrePayMainMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assPrePayMainMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assPrePayMainMapper.queryByUniqueness(entityMap);
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assPrePayMainMapper.queryExists(entityMap);
	}

	@Override
	public String queryAssPreBill(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String,Object>> list = assPrePayMainMapper.queryAssPreBill(entityMap);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	@Override
	public String queryAssPreBillDetail(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			if(entityMap.get("bill_no") !=null && !"".equals(entityMap.get("bill_no").toString())){
				list = assPrePayMainMapper.queryAssPreBillDetail(entityMap);
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
			List<Map<String,Object>> listMap = assPrePayMainMapper.queryAccPayType(entityMap);
			return JSON.toJSONString(listMap);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 预付款模板打印
	 */
	@Override
	public Map<String,Object> queryAssPrePayMainDY(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssPrePayMainMapper assPrePayMainMapper = (AssPrePayMainMapper)context.getBean("assPrePayMainMapper");
			if("1".equals(String.valueOf(map.get("p_num")))){
				// 资产维修打印模板主表
				List<Map<String,Object>> mainList=assPrePayMainMapper.queryAssPrePayMainBatch(map);
						
				//资产维修打印模板从表
				List<Map<String,Object>> detailList=assPrePayMainMapper.queryAssPrePayMain_detail(map);
				
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}else{
				Map<String,Object> mainList=assPrePayMainMapper.querAssPrePayMainByPrint(map);
				
				//资产维修打印模板从表
				List<Map<String,Object>> detailList=assPrePayMainMapper.queryAssPrePayMain_detail(map);
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
				List<Map<String,Object>> assPrePaymapList = assPrePayMainMapper.collectAssPrePayData(map);
				for (Map<String, Object> assPrePaymap : assPrePaymapList) {
					if(assPrePaymap.get("pay_code") == null || "".equals(assPrePaymap.get("pay_code").toString())){
						return "{\"warn\":\""+map.get("pay_no")+"未维护支付方式.\",\"state\":\"false\"}";
					}
					if(assPrePaymap.get("source_id") == null || "".equals(assPrePaymap.get("source_id").toString())){
						return "{\"warn\":\"未维护支付方式与资金来源对应关系.\",\"state\":\"false\"}";
					}
					AssPrePay assPrePay = assPrePayMapper.queryByCode(assPrePaymap);
					if(assPrePay == null){
						assPrePayMapper.add(assPrePaymap);
					}else{
						assPrePaymap.put("cur_money", assPrePay.getCur_money()+Double.parseDouble(assPrePaymap.get("cur_money").toString()));
						assPrePayMapper.updateCurMoney(assPrePaymap);
					}
				}
				
			}
			//主表确认更新
			assPrePayMainMapper.updateBatchConfirm(entityMap);
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateCancelConfirmPrePay(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			for (Map<String, Object> map : entityMap) {
				//核定表添加记录
				/*List<Map<String,Object>> assPrePaymapList = assPrePayMainMapper.collectAssPrePayData(map);
				for (Map<String, Object> assPrePaymap : assPrePaymapList) {
					AssPrePay assPrePay = assPrePayMapper.queryByCode(assPrePaymap);
					if(assPrePay == null){
						assPrePayMapper.add(assPrePaymap);
					}else{
						assPrePaymap.put("cur_money", assPrePay.getCur_money()+Double.parseDouble(assPrePaymap.get("cur_money").toString()));
						assPrePayMapper.updateCurMoney(assPrePaymap);
					}
				}*/
				
			}
			//主表确认更新
			assPrePayMainMapper.updateBatchCancelConfirm(entityMap);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<String> queryAssPrepayState(Map<String, Object> mapVo)
			throws DataAccessException {
		
		return assPrePayMainMapper.queryAssPrepayState(mapVo);
	}

}
