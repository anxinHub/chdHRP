package com.chd.hrp.pac.serviceImpl.fkht.execute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.fkht.execute.PactAssetPurchaseMapper;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactMainFKHTEntity;
import com.chd.hrp.pac.service.fkht.execute.PactAssetPurchaseService;
import com.chd.hrp.pac.serviceImpl.fkht.pactinfo.PactMainFKHTServiceImpl;
import com.github.pagehelper.PageInfo;

import org.apache.log4j.Logger;

@Service("pactAssetPurchaseService")
public class PactAssetPurchaseServiceImpl implements PactAssetPurchaseService {

	private static Logger logger = Logger.getLogger(PactAssetPurchaseServiceImpl.class);
	
	@Resource(name = "pactAssetPurchaseMapper")
	private PactAssetPurchaseMapper pactAssetPurchaseMapper;

	@Override
	public String queryPactAssetPurchaseFKHT(Map<String, Object> mapVo) {
		try {
			List<Map<String,Object>> list = pactAssetPurchaseMapper.queryPactAssetPurchaseFKHT(mapVo);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public List<Map<String,Object>> queryPactAssetPurchaseFKHTPrint(Map<String, Object> mapVo) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode()); 
		mapVo.put("mod_code", SessionManager.getModCode());
		try {
			List<Map<String,Object>> list = pactAssetPurchaseMapper.queryPactAssetPurchaseFKHT(mapVo);
			return list;
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryPactAssetPurchaseDetailFKHT(Map<String, Object> mapVo) {
		try {
			List<Map<String,Object>> list = pactAssetPurchaseMapper.queryPactAssetPurchaseDetailFKHT(mapVo);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public List<Map<String,Object>> queryPactAssetPurchaseDetailFKHTPrint(Map<String, Object> mapVo) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode()); 
		mapVo.put("mod_code", SessionManager.getModCode());
		try {
			List<Map<String,Object>> list = pactAssetPurchaseMapper.queryPactAssetPurchaseDetailFKHT(mapVo);
			return list ;
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}
	
	@Override
	public String queryPactAssPurchaseFKHTDet(Map<String, Object> entityMap) throws DataAccessException {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> query = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssPurchaseFKHTDet(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String,Object>> list = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssPurchaseFKHTDet(entityMap,rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactAssPurchaseFKHTDetIn(Map<String, Object> entityMap)	throws DataAccessException {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> query = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssPurchaseFKHTDetIn(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String,Object>> list = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssPurchaseFKHTDetIn(entityMap,rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactAssPurchaseFKHTDetBack(Map<String, Object> entityMap) throws DataAccessException {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> query = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssPurchaseFKHTDetBack(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String,Object>> list = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssPurchaseFKHTDetBack(entityMap,rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactAssPurchaseFKHTMainIns(Map<String, Object> entityMap)	throws DataAccessException {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> query = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssPurchaseFKHTMainIns(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String,Object>> list = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssPurchaseFKHTMainIns(entityMap,rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactAssPurchaseFKHTMainAccept(Map<String, Object> entityMap)	throws DataAccessException {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> query = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssPurchaseFKHTMainAccept(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String,Object>> list = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssPurchaseFKHTMainAccept(entityMap,rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactAssPurchaseFKHTMainIn(Map<String, Object> entityMap) throws DataAccessException {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> query = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssPurchaseFKHTMainIn(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String,Object>> list = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssPurchaseFKHTMainIn(entityMap,rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactAssPurchaseFKHTMainBack(Map<String, Object> entityMap) throws DataAccessException {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> query = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssPurchaseFKHTMainBack(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String,Object>> list = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssPurchaseFKHTMainBack(entityMap,rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	
	
	
	
	/**
	 * 付款记录  查询
	 */
	@Override
	public String queryPactAssetPurchasePayPay(Map<String, Object> mapVo) throws DataAccessException {
		try {
			//默认 付款
			mapVo.put("pm", "ass_pay_main");
			mapVo.put("pd", "ass_pay_detail");
			mapVo.put("bm", "ass_bill_main");
			if(mapVo.containsKey("pay_type")){
				String payType = (String)mapVo.get("pay_type");
				if ( !(payType.isEmpty() || payType.equals("")) ){
					if(payType.equals("0")) {
						//付款	
						mapVo.put("pm", "ass_pay_main");
						mapVo.put("pd", "ass_pay_detail");
						mapVo.put("bm", "ass_bill_main");
					}else if(payType.equals("1")) {
						//退款
						mapVo.put("pm", "ass_back_pay_main");
						mapVo.put("pd", "ass_back_pay_detail");
						mapVo.put("bm", "ass_back_bill_main");
					}else if(payType.equals("2")) {
						//预付款
						mapVo.put("pm", "ass_pre_pay_main");
						mapVo.put("pd", "ass_pre_pay_detail");
						mapVo.put("bm", "ass_pre_bill_main");
					}else if(payType.equals("3")) {
						//预退款	
						mapVo.put("pm", "ass_back_pre_pay_main");
						mapVo.put("pd", "ass_back_pre_pay_detail");
						mapVo.put("bm", "ass_back_pre_bill_main");
					}	
				}
			}
			List<Map<String,Object>> query = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssetPurchasePayPay(mapVo);
			PageInfo page = new PageInfo(query);
			return ChdJson.toJsonLower(query, page.getTotal());
			
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	/**
	 * 发票记录  查询
	 */
	@Override
	public String queryPactAssetPurchasePayBill(Map<String, Object> mapVo) throws DataAccessException {
		try {
			//默认 付款
			mapVo.put("bm", "ass_bill_main");
			if(mapVo.containsKey("pay_type")){
				String payType = (String)mapVo.get("pay_type");
				if( !(payType.isEmpty() || payType.equals("")) ){
					if(payType.equals("0")) {
					//付款发票	
						mapVo.put("bm", "ass_bill_main");
					}else if(payType.equals("1")) {
					//退款发票
						mapVo.put("bm", "ass_back_bill_main");
					}else if(payType.equals("2")) {
					//预付发票
						mapVo.put("bm", "ass_pre_bill_main");
					}else if(payType.equals("3")) {
					//预退发票
						mapVo.put("bm", "ass_back_pre_bill_main");
					}
				}
			}
			
			List<Map<String,Object>> query = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssetPurchasePayBill(mapVo);
			PageInfo page = new PageInfo(query);
			return ChdJson.toJsonLower(query, page.getTotal());
			
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	/**
	 * 资产采购应付款 查询
	 */
	@Override
	public String queryPactAssetPurchasePay(Map<String, Object> entityMap) throws DataAccessException {
		try {
				List<Map<String,Object>> query = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssetPurchasePay(entityMap);
//				return ChdJson.toJson(query);
				PageInfo page = new PageInfo(query);
				return ChdJson.toJsonLower(query, page.getTotal());
				
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	/**
	 * 资产采购应付款明细 查询
	 */
	@Override
	public String queryPactAssetPurchasePayMD(Map<String, Object> entityMap) throws DataAccessException {
		try {
//			SysPage sysPage = new SysPage();
//			sysPage = (SysPage) entityMap.get("sysPage");
//			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> query = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssetPurchasePayMD(entityMap);
//				return ChdJson.toJson(query);
//			} else {
//				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
//				List<Map<String,Object>> list = (List<Map<String,Object>>) pactAssetPurchaseMapper.queryPactAssetPurchasePayMD(entityMap,rowBounds);
//				@SuppressWarnings("rawtypes")
				PageInfo page = new PageInfo(query);
				return ChdJson.toJsonLower(query, page.getTotal());
//			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
