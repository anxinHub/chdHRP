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
import com.chd.hrp.ass.dao.measure.AssMeasureRecDetailMapper;
import com.chd.hrp.ass.entity.maintain.AssMaintainRecAss;
import com.chd.hrp.ass.entity.measure.AssMeasurePlanAss;
import com.chd.hrp.ass.entity.measure.AssMeasureRec;
import com.chd.hrp.ass.entity.measure.AssMeasureRecDetail;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.measure.AssMeasureRecDetailService;
import com.github.pagehelper.PageInfo;

@Service("assMeasureRecDetailService")
public class AssMeasureRecDetailServiceImpl implements AssMeasureRecDetailService {
	private static Logger logger = Logger.getLogger(AssMeasureRecDetailServiceImpl.class);
//引入DAO操作
@Resource(name = "assMeasurePlanMapper")
private final AssMeasurePlanMapper assMeasurePlanMapper = null;

@Resource(name = "assBaseService")
private final AssBaseService assBaseService = null;

@Resource(name = "assMeasureRecDetailMapper")
private final AssMeasureRecDetailMapper assMeasureRecDetailMapper = null;

	@Override
	public String addAssMeasureRecDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		//获取对象051204 检测计量记录
		AssMeasureRecDetail assMeasureRec = queryAssMeasureRecDetailByCode(entityMap);

		if (assMeasureRec != null) { 

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assMeasureRecDetailMapper.addAssMeasureRecDetail(entityMap);
			
//			Long sequence = assMeasureRecDetailMapper.queryCurrentSequence();
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}"; 
			
			
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}

	@Override
	public String addBatchAssMeasureRecDetail(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {
			
			assMeasureRecDetailMapper.addBatchAssMeasureRecDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}

	@Override
	public String updateAssMeasureRecDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
		  int state = assMeasureRecDetailMapper.updateAssMeasureRecDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}

	@Override
	public String updateBatchAssMeasureRecDetail(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {
			
			assMeasureRecDetailMapper.updateBatchAssMeasureRecDetail(entityMap);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}

	@Override
	public String deleteAssMeasureRecDetail(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assMeasureRecDetailMapper.deleteAssMeasureRecDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }

	@Override
	public String deleteBatchAssMeasureRecDetail(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {
			
			assMeasureRecDetailMapper.deleteBatchAssMeasureRecDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}

	@Override
	public String addOrUpdateAssMeasureRecDetail(Map<String, Object> entityMap) throws DataAccessException {
//		// 获取对象显表
//		AssApplyDeptDetail assApplyDeptDetail = queryAssApplyDeptDetailByCode(entityMap);
		
		Map<String, Object> mapVo=new HashMap<String, Object>();
		Map<String, Object> inMapVo =new HashMap<String, Object>();
		Map<String, Object> validateMapVo =new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("rec_id", entityMap.get("rec_id"));
    	mapVo.put("detail_id", entityMap.get("detail_id"));
    	
    	validateMapVo.put("group_id",entityMap.get("group_id"));
    	validateMapVo.put("rec_id", entityMap.get("rec_id"));
    	
    	validateMapVo.put("hos_id",entityMap.get("hos_id"));
    	
    	validateMapVo.put("copy_code", entityMap.get("copy_code"));
    	
    	validateMapVo.put("rec_id",entityMap.get("rec_id"));
    	
    	validateMapVo.put("ass_card_no",entityMap.get("ass_card_no"));
    	
    	List<AssMeasureRecDetail> list=assMeasureRecDetailMapper.queryAssMeasureRecDetail(mapVo);
    	try {
		if (list.size()>0) {
			for (AssMeasureRecDetail assMeasurePlanAss : list) {
				String ass_card_no = assMeasurePlanAss.getAss_card_no().toString().substring(4,12);
			 	String asscard_no = entityMap.get("ass_card_no").toString().substring(4, 12);
			 	int ass_card_no1 = Integer.valueOf(ass_card_no);
				int asscard_no1 = Integer.valueOf(asscard_no);
				if (ass_card_no1 ==asscard_no1) {
					//assMeasureRecDetailMapper.deleteAssMeasureRecDetail(entityMap);
					int state = assMeasureRecDetailMapper.updateAssMeasureRecDetail(entityMap);
					
				}
			}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}else{
			List<AssMeasureRecDetail> list1 = (List<AssMeasureRecDetail>) assMeasureRecDetailMapper.queryByAssMeasureRecId(validateMapVo);
			
			if (list1.size()>0){
				 
				 return "{\"error\":\"资产卡片号重复.\",\"state\":\"true\"}";
			 }
			int state = assMeasureRecDetailMapper.addAssMeasureRecDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String queryAssMeasureRecDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssMeasureRecDetail> list = assMeasureRecDetailMapper.queryAssMeasureRecDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssMeasureRecDetail> list =null;
			
				if (entityMap.get("ass_nature").equals("02")){
					
					list = assMeasureRecDetailMapper.queryAssMeasuerRecDetailSpecial(entityMap, rowBounds);
				
					}
				if (entityMap.get("ass_nature").equals("03")){
					 
					list = assMeasureRecDetailMapper.queryAssMeasuerRecDetailGeneral(entityMap, rowBounds);
				
					}
				if (entityMap.get("ass_nature").equals("01")){
					
					list = assMeasureRecDetailMapper.queryAssMeasuerRecDetailHouse(entityMap, rowBounds);
				
					}
				if (entityMap.get("ass_nature").equals("04")){
					
					list = assMeasureRecDetailMapper.queryAssMeasuerRecDetailOther(entityMap, rowBounds);
				
				}
				 
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
			
		}
		
	

	@Override
	public AssMeasureRecDetail queryAssMeasureRecDetailByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		return assMeasureRecDetailMapper.queryAssMeasureRecDetailByCode(entityMap);
		}

	@Override
	public AssMeasureRecDetail queryAssMeasureRecDetailByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return assMeasureRecDetailMapper.queryAssMeasureRecDetailByUniqueness(entityMap);
	}

	@Override
	public List<AssMeasureRecDetail> queryAssMeasureRecDetailExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return assMeasureRecDetailMapper.queryAssMeasureRecDetailExists(entityMap);
	}

}
