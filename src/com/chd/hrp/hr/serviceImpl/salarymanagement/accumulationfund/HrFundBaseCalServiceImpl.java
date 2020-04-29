package com.chd.hrp.hr.serviceImpl.salarymanagement.accumulationfund;

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
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.acc.dao.report.SuperReportDesignMapper;
import com.chd.hrp.acc.entity.report.RepDefineEle;
import com.chd.hrp.hr.dao.salarymanagement.accumulationfund.HrFundBaseCalMapper;
import com.chd.hrp.hr.entity.salarymanagement.accumulationfund.HrFundBaseCal;
import com.chd.hrp.hr.service.salarymanagement.accumulationfund.HrFundBaseCalService;
import com.chd.hrp.hr.service.salarymanagement.wageItemCal.HrWageItemCalService;
import com.github.pagehelper.PageInfo;
/**
 * 【薪资管理-公积金等】:缴费基数设置
 * @author yang
 *
 */
@Service("hrFundBaseCalService")
public class HrFundBaseCalServiceImpl implements HrFundBaseCalService {

	private static Logger logger = Logger.getLogger(HrFundBaseCalServiceImpl.class);
	
	// 引入service
	@Resource(name = "hrWageItemCalService")
	private final HrWageItemCalService hrWageItemCalService = null;
	
	// 引入DAO
	@Resource(name="hrFundBaseCalMapper")
	private final HrFundBaseCalMapper hrFundBaseCalMapper = null;
	
	@Resource(name="superReportDesignMapper")
	private final SuperReportDesignMapper superReportDesignMapper = null;

	// 查询
	@Override
	public String queryHrFundBaseCal(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		/*if("全部".equals(paramMap.get("kind_code").toString())){
			paramMap.put("kind_code", null);
		}*/
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<Map<String, Object>> list = ChdJson
				.toListLower((List<Map<String, Object>>) hrFundBaseCalMapper.query(paramMap));
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = ChdJson
				.toListLower((List<Map<String, Object>>) hrFundBaseCalMapper.query(paramMap, rowBounds));
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	// 删除公式
	@Override
	public String deleteHrFundBaseCal(Map<String, Object> paramMap) throws DataAccessException {
		try{
			List<HrFundBaseCal> list = JSONArray.parseArray(String.valueOf(paramMap.get("paramVo")), HrFundBaseCal.class);
			List<Map<String, Object>> delList = JsonListMapUtil.beanToListMap(list);
			// 根据主键批量删除
			if(delList.size() > 0){
				hrFundBaseCalMapper.deleteBatch(delList);
				return "{\"msg\":\"删除成功\", \"state\":\"true\"}";
			}
			return "{\"msg\":\"没有删除数据\", \"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败\", \"state\":\"false\"}";
		}
	}

	// 添加
	@Override
	public String addHrFundBaseCal(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		try{
			// 查询已存在
			List<HrFundBaseCal> existsList = (List<HrFundBaseCal>) hrFundBaseCalMapper.queryExists(paramMap);
			if(existsList.size() > 0){
				if(paramMap.get("kind_code").toString().equals("0")){
					return "{\"warn\":\"职工类别【 全部】已存在，直接选中编辑\",\"state\":\"false\"}";
					}else{
						return "{\"warn\":\"职工类别【" + paramMap.get("kind_code").toString() + "】已存在，直接选中编辑\",\"state\":\"false\"}";
					}
			}
			
			// 公式转译
			paramMap = hrWageItemCalService.calTranslation(paramMap);
			if(paramMap.get("error") != null){
				return paramMap.get("error").toString();
			}
			
			
			// 添加
			hrFundBaseCalMapper.add(paramMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败.\",\"state\":\"false\"}";
		}
	}
	
	// 保存（更新）
	@Override
	public String updateHrFundBaseCal(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("copy_code", SessionManager.getCopyCode());
		try{
			// 查询已存在
			List<HrFundBaseCal> existsList = (List<HrFundBaseCal>) hrFundBaseCalMapper.queryExists(paramMap);
			if(existsList.size() > 0){
				if(paramMap.get("kind_code").toString().equals("0")){
					return "{\"warn\":\"职工类别【 全部】已存在，直接选中编辑\",\"state\":\"false\"}";
					}else{
						return "{\"warn\":\"职工类别【" + paramMap.get("kind_code").toString() + "】已存在，直接选中编辑\",\"state\":\"false\"}";
					}
			}
			
			// 公式转译
			paramMap = hrWageItemCalService.calTranslation(paramMap);
			if(paramMap.get("error") != null){
				return paramMap.get("error").toString();
			}
			
		
			// 更新
			hrFundBaseCalMapper.update(paramMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败.\",\"state\":\"false\"}";
		}
	}

	// 公式编辑左侧树
	@Override
	public String queryFundBaseSetFunTree(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[")
				  .append("{'id':'1','name':'取值函数'}");
		
		paramMap.put("copy_code", SessionManager.getCopyCode());
		paramMap.put("mod_code", "0601");
		List<RepDefineEle> eleList = superReportDesignMapper.querySuperReportEleByMod(paramMap);
		for (RepDefineEle ele : eleList) {
			jsonResult.append(",{'id':'" + ele.getEle_code() + "','pId':'1','name':'" + ele.getEle_name()+"'}");
		}
		
		jsonResult.append("]}");
		return jsonResult.toString();
	}

}
