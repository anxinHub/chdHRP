package com.chd.hrp.ass.serviceImpl.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.resource.AssPlanResourceMapper;
import com.chd.hrp.ass.entity.plan.AssPlanDeptDetail;
import com.chd.hrp.ass.entity.resource.AssApplyDeptResource;
import com.chd.hrp.ass.entity.resource.AssPlanDeptResource;
import com.chd.hrp.ass.service.resource.AssPlanResourceService;

@Service("assPlanResourceService")
public class AssPlanResourceServiceImpl implements AssPlanResourceService {
	private static Logger logger = Logger.getLogger(AssPlanResourceServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assPlanResourceMapper")
	private final AssPlanResourceMapper assPlanResourceMapper = null;

	@Override
	public String addAssPlanSource(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> mapVo = new HashMap<String, Object>();
		Map<String, Object> inMapVo = new HashMap<String, Object>();
		Map<String, Object> validateMapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("plan_detail_id", entityMap.get("plan_detail_id"));
		mapVo.put("plan_id", entityMap.get("plan_id"));
		mapVo.put("plan_no", entityMap.get("plan_no"));
		mapVo.put("ass_id", entityMap.get("ass_id"));
		mapVo.put("ass_no", entityMap.get("ass_no"));
		mapVo.put("price", entityMap.get("sum_price"));
		/*
		 * if (entityMap.get("source_id") == 1) { mapVo.put("source_id", "1"); }
		 */
		mapVo.put("source_id", entityMap.get("source_id"));
		inMapVo.put("group_id", entityMap.get("group_id"));
		inMapVo.put("hos_id", entityMap.get("hos_id"));
		inMapVo.put("copy_code", entityMap.get("copy_code"));
		inMapVo.put("plan_id", entityMap.get("plan_id"));
		inMapVo.put("plan_no", entityMap.get("plan_no"));
		// 验证是否为重复数据的Map
		validateMapVo.put("group_id", entityMap.get("group_id"));
		validateMapVo.put("hos_id", entityMap.get("hos_id"));
		validateMapVo.put("copy_code", entityMap.get("copy_code"));
		validateMapVo.put("plan_detail_id", entityMap.get("plan_detail_id"));
		validateMapVo.put("plan_id", entityMap.get("plan_id"));
		validateMapVo.put("plan_no", entityMap.get("plan_no"));
		validateMapVo.put("ass_id", entityMap.get("ass_id"));
		validateMapVo.put("ass_no", entityMap.get("ass_no"));
		validateMapVo.put("price", entityMap.get("sum_price"));
		/*
		 * if (entityMap.get("source_id") == 1) { mapVo.put("source_id", "1"); }
		 */
		mapVo.put("source_id", entityMap.get("source_id"));
		List<AssPlanDeptResource> list = assPlanResourceMapper.queryAssPlanDeptDetailExists(mapVo);
		if (list.size() > 0) {

			int state = assPlanResourceMapper.saveResourceItem(entityMap);

			/*
			 * inMapVo.put("plan_money", plan_money+"");
			 * assPlanDeptMapper.updateAssPlanDeptMoney(inMapVo);
			 */

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			List<AssPlanDeptDetail> validateList = (List<AssPlanDeptDetail>) assPlanResourceMapper
					.queryByAssPlanDeptId(validateMapVo);
			if (validateList.size() > 0) {
				return "{\"error\":\"资产来源信息重复.\"}";
			}

			int state = assPlanResourceMapper.addAssPlanSource(mapVo);

			List<AssPlanDeptDetail> details = assPlanResourceMapper.queryByAssPlanDeptDetail(inMapVo);
			/* inMapVo.put("plan_money", plan_money+""); */

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}
/**
 * 查询资金来源
 */
	@Override
	public String queryAssPlanResourceList(Map<String, Object> entityMap) throws DataAccessException {
		List<AssPlanDeptResource> list = assPlanResourceMapper.queryAssPlanResourceList(entityMap);
		return ChdJson.toJson(list);


	}

	@Override
	public String saveResourceItem(List<Map<String, Object>> entityMap)  throws DataAccessException {

		try {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get(0).get("group_id"));
			mapVo.put("hos_id", entityMap.get(0).get("hos_id"));
			mapVo.put("copy_code", entityMap.get(0).get("copy_code"));
			if (entityMap.size()>1) {
				mapVo.put("plan_id", entityMap.get(1).get("plan_id"));
				mapVo.put("plan_no", entityMap.get(1).get("accept_id"));
			}else{
			mapVo.put("plan_id", entityMap.get(0).get("plan_id"));
			mapVo.put("plan_no", entityMap.get(0).get("plan_no"));}
			/*mapVo.put("price", entityMap.get(0).get("resource_price"));
			mapVo.put("source_code", entityMap.get(0).get("source_code"));
			mapVo.put("ass_id", entityMap.get(0).get("ass_id"));
			mapVo.put("ass_no", entityMap.get(0).get("ass_no"));*/
			assPlanResourceMapper.deleteAssAcceptItem(mapVo);

			assPlanResourceMapper.addBatchAssAcceptItem(entityMap);

//			int state = assPlanResourceMapper.saveResourceItem(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public String deleteAssPlanResource(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assPlanResourceMapper.deleteAssPlanResource(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}
	@Override
	public String updateDeleteAssPlanResource(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assPlanResourceMapper.updateDeleteAssPlanResource(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public AssPlanDeptResource queryAssPlanDeptResource(Map<String, Object> entityMap) {
		return assPlanResourceMapper.queryAssPlanResource(entityMap);

	}

	@Override
	public List<AssPlanDeptResource> queryResource(Map<String, Object> entityMap) {
		return assPlanResourceMapper.queryResource(entityMap);
	}
	@Override
	public List<AssPlanDeptResource> queryResourceAssPlanDeptResource(Map<String, Object> mapVo)
			throws DataAccessException {
		return assPlanResourceMapper.queryResourceAssPlanDeptResource(mapVo);
	}
	@Override
	public String addAssPlanSourceImport(Map<String, Object> allMapVo) throws DataAccessException {
		try {
			double price =0.0;
			int amount= (Integer) allMapVo.get("plan_amount");
			double advice_price=(Double) allMapVo.get("advice_price");
			price=amount*advice_price;
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", allMapVo.get("group_id"));
			mapVo.put("hos_id", allMapVo.get("hos_id"));
			mapVo.put("copy_code", allMapVo.get("copy_code"));
			mapVo.put("plan_detail_id", allMapVo.get("plan_detail_id"));
			mapVo.put("plan_id", allMapVo.get("plan_id"));
			mapVo.put("plan_no", allMapVo.get("plan_no"));
			mapVo.put("ass_id", allMapVo.get("ass_id"));
			mapVo.put("ass_no", allMapVo.get("ass_no"));
			mapVo.put("price", allMapVo.get("price"));
			mapVo.put("source_id", allMapVo.get("source_id"));
			assPlanResourceMapper.addAssPlanSourceImport(mapVo);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}
	@Override
	public List<AssPlanDeptResource> queryAssPlanResourceListImport(Map<String, Object> mapVo)
			throws DataAccessException {
		
		return assPlanResourceMapper.queryAssPlanResourceListImport(mapVo);
	}
	@Override
	public List<AssPlanDeptResource> queryResourceDelete(Map<String, Object> mapVo)
			throws DataAccessException {
		
		return assPlanResourceMapper.queryResourceDelete(mapVo);
	}
	}



