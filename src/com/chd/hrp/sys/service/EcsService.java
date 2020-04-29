package com.chd.hrp.sys.service;

import java.util.Map;

public interface EcsService {

	public String queryMatSupProdSpec(Map<String, Object> mapVo);
	
	public Map<String,Object> querySupProdSpecStateById(Map<String, Object> mapVo);

	public Map<String, Object> addMatSupProdSpecInv(Map<String, Object> mapVo);
	
	//查询供应商材料
	public String queryMatInvList(Map<String, Object> mapVo);
	
	//材料新增
	public Map<String, Object> addMatInv(Map<String, Object> mapVo);

	//查询送货单列表
	public String queryMatDeliveryList(Map<String, Object> mapVo);
	
	//主表加载
	public Map<String, Object> queryMatDeliveryMainByCode(Map<String, Object> entityMap);

	//根据主表主键加载明细
	public String queryMatDeliveryDetailByCode(Map<String, Object> mapVo);
	
	//送货单签收/作废
	public Map<String, Object> updateMatDeliveryState(Map<String, Object> entityMap);
	
	//入库单新增
	public Map<String, Object> addMatInByDelivery(Map<String, Object> entityMap);
	
}
