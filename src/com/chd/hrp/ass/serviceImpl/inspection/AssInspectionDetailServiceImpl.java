package com.chd.hrp.ass.serviceImpl.inspection;
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
import com.chd.hrp.ass.dao.inspection.AssInspectionDetailMapper;
import com.chd.hrp.ass.entity.inspection.AssInspectionDetail;
import com.chd.hrp.ass.entity.measure.AssMeasureRecDetail;
import com.chd.hrp.ass.service.inspection.AssInspectionDetailService;
import com.github.pagehelper.PageInfo;
@Service("assInspectionDetailService")
public class AssInspectionDetailServiceImpl implements AssInspectionDetailService {
	private static Logger logger = Logger.getLogger(AssInspectionDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assInspectionDetailMapper")
	private final AssInspectionDetailMapper assInspectionDetailMapper = null;
	
	
	@Override
	public String addAssInspectionDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		AssInspectionDetail assInspectionDetail = queryAssInspectionDetailByCode(entityMap);

		if (assInspectionDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assInspectionDetailMapper.addAssInspectionDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public AssInspectionDetail queryAssInspectionDetailByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		return assInspectionDetailMapper.queryAssInspectionDetailByCode(entityMap);
	}

	@Override
	public String updateAssInspectionDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			  int state = assInspectionDetailMapper.updateAssInspectionDetail(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}	
	}

	@Override 
	public String addOrUpdateAssInspectionDetail(Map<String, Object> entityMap)
			throws DataAccessException {
       
        Map<String, Object> mapVo=new HashMap<String, Object>();
		
		mapVo.put("group_id",entityMap.get("group_id"));
		
		mapVo.put("hos_id",entityMap.get("hos_id"));
		
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	
    	mapVo.put("ins_id", entityMap.get("ins_id"));
    	
    	mapVo.put("detail_id", entityMap.get("detail_id"));
    	
    	Map<String, Object> validateMapVo =new HashMap<String, Object>();
    	
    	validateMapVo.put("group_id",entityMap.get("group_id"));
    	
    	validateMapVo.put("hos_id",entityMap.get("hos_id"));
    	
    	validateMapVo.put("copy_code", entityMap.get("copy_code"));
    	
    	validateMapVo.put("ass_card_no",entityMap.get("ass_card_no")); 
    	
    	validateMapVo.put("ins_id",entityMap.get("ins_id")); 
    	
    	validateMapVo.put("detail_id",entityMap.get("detail_id"));
    	
		List<AssInspectionDetail> list = assInspectionDetailMapper.queryAssInspectionDetailExists(mapVo);
		
		try {
			
			if (list.size()>0) {
	
				int state = assInspectionDetailMapper.updateAssInspectionDetail(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	
			}else {
			
					List<AssInspectionDetail> validateList = assInspectionDetailMapper.queryByAssInspectionDetailId(validateMapVo);
					
					if(validateList.size() > 0){
						
						return "{\"error\":\"资产卡片号重复.\"}";
					}
					
					
					int state = assInspectionDetailMapper.addAssInspectionDetail(entityMap);
					
					return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
					
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String deleteBatchAssInspectionDetail(
			List<Map<String, Object>> entityList) throws DataAccessException {
try {
			
			assInspectionDetailMapper.deleteBatchAssInspectionDetail(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}

	@Override
	public String queryAssInspectionDetailByUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String,Object>> list =assInspectionDetailMapper.queryAssInspectionDetailByUpdate(entityMap);
		return ChdJson.toJson(list);
	}
	/**
	 * @Description 
	 * 获取050601 资产巡检明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssInsDetail
	 * @throws DataAccessException
	*/
	@Override
	public AssInspectionDetail queryAssInspectionDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assInspectionDetailMapper.queryAssInspectionDetailByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050601 资产巡检明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssInsDetail>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssInspectionDetail> queryAssInspectionDetailExists(Map<String,Object> entityMap)throws DataAccessException{
		return assInspectionDetailMapper.queryAssInspectionDetailExists(entityMap);
	}
	
	/**
	 * @Description 
	 * 查询结果集050601 资产巡检明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssInspectionDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssInspectionDetail> list = assInspectionDetailMapper.queryAssInspectionDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			List<AssInspectionDetail> list =  null ;
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			if (entityMap.get("ass_nature").equals("02")){ 
				
				list = assInspectionDetailMapper.queryAssInspectionDetailSpecial(entityMap, rowBounds);
			
				}
			if (entityMap.get("ass_nature").equals("03")){
				 
				list = assInspectionDetailMapper.queryAssInspectionDetailGeneral(entityMap, rowBounds);
			
				}
			if (entityMap.get("ass_nature").equals("01")){
				
				list = assInspectionDetailMapper.queryAssInspectionDetailHouse(entityMap, rowBounds);
			
				}
			if (entityMap.get("ass_nature").equals("04")){
				
				list = assInspectionDetailMapper.queryAssInspectionDetailOther(entityMap, rowBounds);
			
				}  
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
}
