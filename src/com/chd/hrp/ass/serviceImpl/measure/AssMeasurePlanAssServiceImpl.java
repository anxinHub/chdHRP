package com.chd.hrp.ass.serviceImpl.measure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.measure.AssMeasurePlanAssMapper;
import com.chd.hrp.ass.dao.measure.AssMeasurePlanMapper;
import com.chd.hrp.ass.entity.apply.AssApplyDept;
import com.chd.hrp.ass.entity.apply.AssApplyDeptDetail;
import com.chd.hrp.ass.entity.measure.AssMeasurePlan;
import com.chd.hrp.ass.entity.measure.AssMeasurePlanAss;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.measure.AssMeasurePlanAssService;
import com.chd.hrp.ass.serviceImpl.apply.AssApplyDeptDetailServiceImpl;
import com.github.pagehelper.PageInfo;

@Service("assMeasurePlanAssService")
public class AssMeasurePlanAssServiceImpl implements AssMeasurePlanAssService {
	private static Logger logger = Logger.getLogger(AssMeasurePlanAssServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assMeasurePlanMapper")
	private final AssMeasurePlanMapper assMeasurePlanMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	 
	@Resource(name = "assMeasurePlanAssMapper")
	private final AssMeasurePlanAssMapper assMeasurePlanAssMapper = null;

	@Override
	public String addAssMeasurePlanAss(Map<String, Object> entityMap) throws DataAccessException {
		
	int state=	 assMeasurePlanAssMapper.addAssMeasurePlanAss(entityMap);
	return "{\"msg\":\"添加成功.\",\"state\":\"true\"}"; 
	}

	@Override
	public String addBatchAssMeasurePlanAss(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {
			
			assMeasurePlanAssMapper.addBatchAssMeasurePlanAss(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}

	@Override
	public String updateAssMeasurePlanAss(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
		  int state = assMeasurePlanAssMapper.updateAssMeasurePlanAss(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}

	@Override
	public String updateBatchAssMeasurePlanAss(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {
			
			assMeasurePlanAssMapper.updateBatchAssMeasurePlanAss(entityMap);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}

	@Override
	public String deleteAssMeasurePlanAss(List<Map<String,Object>> entityList) throws DataAccessException {
		
		try {
			/**
			 * 删除计量计划表时 1、 删除计量计划资产明细
			 */
			
			assMeasurePlanAssMapper.deleteBatchAssMeasurePlanAss(entityList);
			
//			assMeasurePlanAssMapper.deleteBatchAssMeasurePlanAss(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	@Override
	public String deleteAssMeasurePlanAss(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assMeasurePlanAssMapper.deleteAssMeasurePlanAss(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
	@Override
	public String deleteBatchAssMeasurePlanAss(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		int state=assMeasurePlanAssMapper.deleteBatchAssMeasurePlanAss(entityMap);
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}

	@Override
	public String addOrUpdateAssMeasurePlanAss(Map<String, Object> entityMap) throws DataAccessException {
//		// 获取对象显表
//		AssApplyDeptDetail assApplyDeptDetail = queryAssApplyDeptDetailByCode(entityMap);
		
		/*Map<String, Object> mapVo=new HashMap<String, Object>();
		Map<String, Object> inMapVo =new HashMap<String, Object>();
		Map<String, Object> validateMapVo =new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("plan_id", entityMap.get("plan_id"));
    	
    	List<AssMeasurePlanAss> list=assMeasurePlanAssMapper.queryAssMeasurePlanAss(mapVo);
    	try {
		if (list.size()>0) {
			for (AssMeasurePlanAss assMeasurePlanAss : list) {
				String ass_id_no=assMeasurePlanAss.getAss_card_no().toString();
         	   String assid_no=entityMap.get("ass_card_no").toString();
				Long ass_id=Long.parseLong(entityMap.get("ass_id").toString());
				if (!ass_id_no.equals(assid_no)) {
					assMeasurePlanAssMapper.deleteAssMeasurePlanAss(mapVo);
					assMeasurePlanAssMapper.addAssMeasurePlanAss(entityMap);
				}else if(ass_id_no==assid_no){
					
					assMeasurePlanAssMapper.updateAssMeasurePlanAss(entityMap);
					
				}else{
					
					
				}
			}

			int state = assMeasurePlanAssMapper.updateAssMeasurePlanAss(entityMap);
			

			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}else{

			
			int state = assMeasurePlanAssMapper.addAssMeasurePlanAss(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}*/
		
		Map<String, Object> mapVo=new HashMap<String, Object>();
		Map<String, Object> inMapVo =new HashMap<String, Object>();
		Map<String, Object> validateMapVo =new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("detail_id", entityMap.get("detail_id"));
    	mapVo.put("plan_id", entityMap.get("plan_id"));
    	inMapVo.put("group_id",entityMap.get("group_id")); 
    	inMapVo.put("hos_id",entityMap.get("hos_id"));
    	inMapVo.put("copy_code", entityMap.get("copy_code"));
    	inMapVo.put("plan_id", entityMap.get("plan_id"));
    	List<AssMeasurePlanAss> list=assMeasurePlanAssMapper.queryAssMeasurePlanAss(mapVo);
		
    	try {
    		
    		if(list.size() > 0){
    			int state = assMeasurePlanAssMapper.updateAssMeasurePlanAss(entityMap);
    			
    			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";	
    			
    		}else{
    			int state = assMeasurePlanAssMapper.addAssMeasurePlanAss(entityMap);
    		
    			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
    		}
    		
    	}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
		
		
		
		
		
	}

	@Override
	public String queryAssMeasurePlanAss(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssMeasurePlanAss> list = assMeasurePlanAssMapper.queryAssMeasurePlanAss(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssMeasurePlanAss> list = null;

					if (entityMap.get("ass_nature").equals("02")){
						
						list = assMeasurePlanAssMapper.queryAssMeasuerAssSpecial(entityMap, rowBounds);
					
						}
					if (entityMap.get("ass_nature").equals("03")){
						 
						list = assMeasurePlanAssMapper.queryAssMeasuerAssGeneral(entityMap, rowBounds);
					
						}
					if (entityMap.get("ass_nature").equals("01")){
						
						list = assMeasurePlanAssMapper.queryAssMeasuerAssHouse(entityMap, rowBounds);
					
						}
					if (entityMap.get("ass_nature").equals("04")){
						
						list = assMeasurePlanAssMapper.queryAssMeasuerAssOther(entityMap, rowBounds);
					
						}
					 
				
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}

	@Override
	public AssMeasurePlanAss queryAssMeasurePlanAssByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assMeasurePlanAssMapper.queryAssMeasurePlanAssByCode(entityMap);

	}

	@Override
	public AssMeasurePlanAss queryAssMeasurePlanAssByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		return assMeasurePlanAssMapper.queryAssMeasurePlanAssByUniqueness(entityMap);

	}

	@Override
	public List<AssMeasurePlanAss> queryAssMeasurePlanAssExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return assMeasurePlanAssMapper.queryAssMeasurePlanAssExists(entityMap);
	}

	
	RowBounds rowBoundsALL = new RowBounds(0, 20); 
	@Override
	public String queryMeasurePlanRec(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = null;
		list = assMeasurePlanAssMapper.queryMeasurePlanRec(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());
	}

	

}
