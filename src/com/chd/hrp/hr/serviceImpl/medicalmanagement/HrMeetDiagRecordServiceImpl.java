package com.chd.hrp.hr.serviceImpl.medicalmanagement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.medicalmanagement.HrMeetDiagRecordMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrMeetDiagRecord;
import com.chd.hrp.hr.service.medicalmanagement.HrMeetDiagRecordService;
import com.github.pagehelper.PageInfo;

@Service("hrMeetDiagRecordService")
public class HrMeetDiagRecordServiceImpl implements HrMeetDiagRecordService{

	private static Logger logger = Logger.getLogger(HrMeetDiagRecordServiceImpl.class);

	@Resource(name = "hrMeetDiagRecordMapper")
	private final HrMeetDiagRecordMapper hrMeetDiagRecordMapper = null;
	
	
	@Override
	public String addMeetDiagRecord(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<HrMeetDiagRecord> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrMeetDiagRecord.class);
			if (alllistVo != null && alllistVo.size() > 0) {
				for(HrMeetDiagRecord md : alllistVo){
					if(md.getRec_date().getTime() < md.getMd_date().getTime()){
						return "{\"msg\":\"申请日期大于会诊日期.\",\"state\":\"true\"}";
					}
				}
				// 先删除
				hrMeetDiagRecordMapper.deleteMeetDiagRecord(alllistVo, entityMap);
				// 增加
				hrMeetDiagRecordMapper.addBatchMeetDiagRecord(alllistVo, entityMap);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String deleteMeetDiagRecord(List<HrMeetDiagRecord> entityList)
			throws DataAccessException {

		try {
			if (entityList !=null) {
				Map<String,Object> mapVo=new HashMap<String,Object>();
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				
				hrMeetDiagRecordMapper.deleteMeetDiagRecord(entityList,mapVo);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String queryMeetDiagRecord(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HrMeetDiagRecord> list = (List<HrMeetDiagRecord>)hrMeetDiagRecordMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HrMeetDiagRecord> list = (List<HrMeetDiagRecord>)hrMeetDiagRecordMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}

	@Override
	public String queryMeetDiagApp(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)hrMeetDiagRecordMapper.queryMeetDiagApp(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)hrMeetDiagRecordMapper.queryMeetDiagApp(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}

	@Override
	public String confirmMeetDiagRecord(HrMeetDiagRecord hrMeetDiagRecord)
			throws DataAccessException {
		try {
	        
			hrMeetDiagRecordMapper.confirmMeetDiagRecord(hrMeetDiagRecord);
			
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

}

	@Override
	public String reConfirmMeetDiagRecord(HrMeetDiagRecord hrMeetDiagRecord)
			throws DataAccessException {
		try {
	        
			hrMeetDiagRecordMapper.reConfirmMeetDiagRecord(hrMeetDiagRecord);
			
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

}
	
	@Override
	public List<Map<String, Object>> queryRecordByPrint(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> list = ChdJson.toListLower(hrMeetDiagRecordMapper.queryRecordByPrint(entityMap));
		return list;
	}

}
