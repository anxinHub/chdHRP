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
import com.chd.hrp.ass.dao.resource.AssApplyDeptResourceMapper;
import com.chd.hrp.ass.entity.apply.AssApplyDeptDetail;
import com.chd.hrp.ass.entity.resource.AssApplyDeptResource;
import com.chd.hrp.ass.entity.resource.AssPlanDeptResource;
import com.chd.hrp.ass.service.resource.AssApplyDeptResourceService;
import com.chd.hrp.ass.service.resource.AssPlanResourceService;

@Service("assApplyDeptResourceService")
public class AssApplyDeptResourceServiceImpl implements AssApplyDeptResourceService {
	private static Logger logger = Logger.getLogger(AssApplyDeptResourceServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assApplyDeptResourceMapper")
	private final AssApplyDeptResourceMapper assApplyDeptResourceMapper = null;

	@Override
	public String addAssPlanSource(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> mapVo = new HashMap<String, Object>();
		Map<String, Object> inMapVo = new HashMap<String, Object>();
		Map<String, Object> validateMapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("detail_id", entityMap.get("detail_id"));
		mapVo.put("apply_id", entityMap.get("apply_id"));
		mapVo.put("apply_no", entityMap.get("apply_no"));
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
		inMapVo.put("apply_id", entityMap.get("apply_id"));
		inMapVo.put("apply_no", entityMap.get("apply_no"));
		// 验证是否为重复数据的Map
		validateMapVo.put("group_id", entityMap.get("group_id"));
		validateMapVo.put("hos_id", entityMap.get("hos_id"));
		validateMapVo.put("copy_code", entityMap.get("copy_code"));
		validateMapVo.put("detail_id", entityMap.get("detail_id"));
		validateMapVo.put("apply_id", entityMap.get("apply_id"));
		validateMapVo.put("apply_no", entityMap.get("apply_no"));
		validateMapVo.put("ass_id", entityMap.get("ass_id"));
		validateMapVo.put("ass_no", entityMap.get("ass_no"));
		validateMapVo.put("price", entityMap.get("sum_price"));
		/*
		 * if (entityMap.get("source_id") == 1) { mapVo.put("source_id", "1"); }
		 */
		mapVo.put("source_id", entityMap.get("source_id"));
		List<AssApplyDeptResource> list = assApplyDeptResourceMapper.queryAssPlanDeptDetailExists(mapVo);
		if (list.size() > 0) {

			int state = assApplyDeptResourceMapper.updateResourceItem(mapVo);

			/*
			 * inMapVo.put("plan_money", plan_money+"");
			 * assPlanDeptMapper.updateAssPlanDeptMoney(inMapVo);
			 */

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			List<AssApplyDeptResource> validateList = (List<AssApplyDeptResource>) assApplyDeptResourceMapper
					.queryByAssPlanDeptId(validateMapVo);
			if (validateList.size() > 0) {
				return "{\"error\":\"资产来源信息重复.\"}";
			}

			int state = assApplyDeptResourceMapper.addAssPlanSource(mapVo);

			List<AssApplyDeptResource> details = assApplyDeptResourceMapper.queryByAssPlanDeptDetail(inMapVo);
			/* inMapVo.put("plan_money", plan_money+""); */

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public String queryAssPlanResource(Map<String, Object> entityMap) throws DataAccessException {
		AssApplyDeptResource assPlanDeptResource = assApplyDeptResourceMapper.queryAssPlanResource(entityMap);

		return ChdJson.toJson(assPlanDeptResource);

	}
/**
 * 批量添加资金来源
 */
	@Override
	public String saveResourceItem(List<Map<String, Object>> entityMap)  throws DataAccessException {


		try {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get(0).get("group_id"));
			mapVo.put("hos_id", entityMap.get(0).get("hos_id"));
			mapVo.put("copy_code", entityMap.get(0).get("copy_code"));
			if (entityMap.size()>1) {
				mapVo.put("apply_id", entityMap.get(1).get("apply_id"));
				mapVo.put("apply_no", entityMap.get(1).get("apply_no"));
			}else{
			mapVo.put("apply_id", entityMap.get(0).get("apply_id"));
			mapVo.put("apply_no", entityMap.get(0).get("apply_no"));}
			/*mapVo.put("price", entityMap.get(0).get("resource_price"));
			mapVo.put("source_code", entityMap.get(0).get("source_code"));
			mapVo.put("ass_id", entityMap.get(0).get("ass_id"));
			mapVo.put("ass_no", entityMap.get(0).get("ass_no"));*/
			assApplyDeptResourceMapper.deleteAssAcceptItem(mapVo);
			
			
			
			assApplyDeptResourceMapper.addBatchAssAcceptItem(entityMap);

//			int state = assPlanResourceMapper.saveResourceItem(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public String deleteAssPlanResource(List<Map<String, Object>> entityList) throws DataAccessException {
		Map<String, Object> inMapVo =new HashMap<String, Object>();
		inMapVo.put("group_id",entityList.get(0).get("group_id"));
    	inMapVo.put("hos_id",entityList.get(0).get("hos_id"));
    	inMapVo.put("copy_code", entityList.get(0).get("copy_code"));
    	inMapVo.put("apply_id", entityList.get(0).get("apply_id"));
    	inMapVo.put("apply_no", entityList.get(0).get("apply_no"));
	
		try {

			assApplyDeptResourceMapper.deleteAssPlanResource(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public AssApplyDeptResource queryAssPlanDeptResource(Map<String, Object> entityMap) {
		return assApplyDeptResourceMapper.queryAssPlanResource(entityMap);

	}
  /**
   * 跳转时候查询资金来源
   */
	@Override
	public AssApplyDeptResource queryResource(Map<String, Object> entityMap) throws DataAccessException {
		return assApplyDeptResourceMapper.queryResource(entityMap);
	}

@Override
public String queryAssPlanResourceList(Map<String, Object> entityMap) throws DataAccessException {
	List<AssApplyDeptResource> list = assApplyDeptResourceMapper.queryAssPlanResourceList(entityMap);
	return ChdJson.toJson(list);
}

@Override
public List<AssApplyDeptResource> queryResourceAssPlanDeptResource(Map<String, Object> mapVo)
		throws DataAccessException {
	return assApplyDeptResourceMapper.queryResourceAssPlanDeptResource(mapVo);
	
}

@Override
public List<AssApplyDeptResource> queryAssPlanResourceListImport(Map<String, Object> mapVo) throws DataAccessException {
	// TODO Auto-generated method stub
	return assApplyDeptResourceMapper.queryAssPlanResourceListImport(mapVo);
}

@Override
public List<AssApplyDeptResource> queryResourcedelete(Map<String, Object> mapVo) throws DataAccessException {
	return assApplyDeptResourceMapper.queryResourcedelete(mapVo);
}

}
