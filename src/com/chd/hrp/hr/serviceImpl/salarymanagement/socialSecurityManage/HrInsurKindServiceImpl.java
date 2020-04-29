package com.chd.hrp.hr.serviceImpl.salarymanagement.socialSecurityManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.salarymanagement.socialSecurityManage.HrInsurKindMapper;
import com.chd.hrp.hr.entity.salarymanagement.socialSecurityManage.HrInsurKind;
import com.chd.hrp.hr.service.salarymanagement.socialSecurityManage.HrInsurKindService;
import com.github.pagehelper.PageInfo;
/**
 * 【薪资管理-社保管理】：社保险种
 * @author yang
 *
 */
@Service("hrInsurKindService")
public class HrInsurKindServiceImpl implements HrInsurKindService {
	
	private static Logger logger = Logger.getLogger(HrInsurKindServiceImpl.class);
	
	// 引入DAO
	@Resource(name = "hrInsurKindMapper")
	private final HrInsurKindMapper hrInsurKindMapper = null;

	@Override
	public Map<String, Object> queryInsurKind(Map<String, Object> paramMap) throws DataAccessException {
		if(paramMap.get("group_id") == null){
			paramMap.put("group_id", SessionManager.getGroupId());
		}
		if(paramMap.get("hos_id") == null){
			paramMap.put("hos_id", SessionManager.getHosId());
		}
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<Map<String, Object>> list = ChdJson
				.toListLower((List<Map<String, Object>>) hrInsurKindMapper.query(paramMap));
			return JSONObject.parseObject(ChdJson.toJson(list));
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = ChdJson
				.toListLower((List<Map<String,Object>>) hrInsurKindMapper.query(paramMap, rowBounds));
			PageInfo page = new PageInfo(list);
			return JSONObject.parseObject(ChdJson.toJson(list, page.getTotal()));
		}
	}

	@Override
	public String saveInsureKind(Map<String, Object> paramMap) throws DataAccessException {
		if(paramMap.get("group_id") == null){
			paramMap.put("group_id", SessionManager.getGroupId());
		}
		if(paramMap.get("hos_id") == null){
			paramMap.put("hos_id", SessionManager.getHosId());
		}
		try{
			List<HrInsurKind> listVo = JSONArray.parseArray(String.valueOf(paramMap.get("paramVo")), HrInsurKind.class);
			List<HrInsurKind> addList = new ArrayList<HrInsurKind>();
			List<HrInsurKind> updateList = new ArrayList<HrInsurKind>();
			if(listVo.size() > 0){
				StringBuilder insureCodes = new StringBuilder();
				StringBuilder insureNames = new StringBuilder();
				for(HrInsurKind ssk : listVo){
					if(StringUtils.isEmpty(ssk.getInsure_code())){
						return "{\"warn\":\"险种编码不能是空.\",\"state\":\"false\"}";
					}else if(ssk.getGroup_id() != null){
						insureCodes.append(",'").append(ssk.getInsure_code()).append("'");
						insureNames.append(",'").append(ssk.getInsure_name()).append("'");
						updateList.add(ssk);
					}else{
						ssk.setGroup_id(Integer.valueOf(SessionManager.getGroupId()));
						ssk.setHos_id(Integer.valueOf(SessionManager.getHosId()));
						ssk.setSpell_code(StringTool.toPinyinShouZiMu(ssk.getInsure_name()));
						ssk.setWbx_code(StringTool.toWuBi(ssk.getInsure_name()));
						insureCodes.append(",'").append(ssk.getInsure_code()).append("'");
						insureNames.append(",'").append(ssk.getInsure_name()).append("'");
						addList.add(ssk);
					}
				}
				paramMap.put("insure_codes", insureCodes.substring(1));
				paramMap.put("insure_names", insureNames.substring(1));
				
					if(addList.size() > 0){
						List<HrInsurKind> existsList = (List<HrInsurKind>) hrInsurKindMapper.queryExists(paramMap);
						if(existsList.size() > 0){
							return "{\"warn\":\"险种编码或名称被占用.\",\"state\":\"false\"}";
						}else {
						hrInsurKindMapper.addBatch(addList);
					}
					}
					if(updateList.size() > 0){
						hrInsurKindMapper.updateBatch(updateList);
					}
				
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"保存失败.\",\"state\":\"false\"}";
		}
	}

	@Override
	public String deleteInsureKind(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		try{
			List<HrInsurKind> listVo = JSONArray.parseArray(String.valueOf(paramMap.get("paramVo")), HrInsurKind.class);
			
			hrInsurKindMapper.deleteBatch(JsonListMapUtil.beanToListMap(listVo));
			return "{\"msg\":\"删除成功\", \"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败\", \"state\":\"false\"}";
		}
	}
}
