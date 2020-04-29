package com.chd.hrp.hr.serviceImpl.scientificresearch;

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
import com.chd.hrp.hr.dao.scientificresearch.HrPersonalAcademicCreditMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrAcadeHonorDetail;
import com.chd.hrp.hr.entity.scientificresearch.HrPersonalAcademicCredit;
import com.chd.hrp.hr.service.scientificresearch.HrPersonalAcademicCreditService;
import com.github.pagehelper.PageInfo;

/**
 * 个人学术荣誉申请
 * 
 * @author Administrator
 *
 */
@Service("hrPersonalAcademicCreditService")
public class HrPersonalAcademicCreditServiceImpl implements
		HrPersonalAcademicCreditService {
	private static Logger logger = Logger
			.getLogger(HrPersonalAcademicCreditServiceImpl.class);

	@Resource(name = "hrPersonalAcademicCreditMapper")
	private final HrPersonalAcademicCreditMapper hrPersonalAcademicCreditMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 个人学术荣誉申请增加
	 */
	@Override
	public String addPersonalAcademicCredit(Map<String, Object> entityMap)
			throws DataAccessException {
		/*
		 * // 获取对象 List<HrPersonalAcademicCredit> list =
		 * hrPersonalAcademicCreditMapper
		 * .queryPersonalAcademicCreditById(entityMap);
		 */
		/*
		 * if (list.size() > 0) {
		 * 
		 * for (HrPersonalAcademicCredit hrPersonalAcademicCredit : list) {
		 * 
		 * if
		 * (hrPersonalAcademicCredit.getKind_code().equals(entityMap.get("kind_code"
		 * ))) { return "{\"error\":\"编码：" +
		 * hrDutyLevel.getKind_code().toString() + "已存在.\"}"; } if
		 * (hrPersonalAcademicCredit
		 * .getKind_name().equals(entityMap.get("kind_name"))) { return
		 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() +
		 * "已存在.\"}"; }
		 * 
		 * } }
		 */
		try {

			@SuppressWarnings("unused")
			List<HrPersonalAcademicCredit> alllistVo = JSONArray.parseArray(
					String.valueOf(entityMap.get("gridData")),
					HrPersonalAcademicCredit.class);

			if (alllistVo != null && alllistVo.size() > 0) {
				for (HrPersonalAcademicCredit hrPersonalAcademicCredit : alllistVo) {
					hrPersonalAcademicCredit.setHos_id(Integer
							.parseInt(SessionManager.getHosId()));
					hrPersonalAcademicCredit.setGroup_id(Integer
							.parseInt(SessionManager.getGroupId()));
					hrPersonalAcademicCredit.setYear(entityMap.get("year")
							.toString());
					hrPersonalAcademicCredit.setHonor_code(entityMap.get(
							"honor_code").toString());
					hrPersonalAcademicCredit.setScore(Double
							.parseDouble(entityMap.get("score").toString()));
					hrPersonalAcademicCredit.setNote(entityMap.get("note")
							.toString());
				}

			}
			int state = hrPersonalAcademicCreditMapper.addBatch(alllistVo);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 个人学术荣誉申请修改
	 */
	@Override
	public String updatePersonalAcademicCredit(Map<String, Object> entityMap)
			throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrPersonalAcademicCreditMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 个人学术荣誉申请删除
	 */
	@Override
	public String deletePersonalAcademicCredit(
			List<HrPersonalAcademicCredit> entityList)
			throws DataAccessException {

		try {

			hrPersonalAcademicCreditMapper
					.deletePersonalAcademicCredit(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 个人学术荣誉申请查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryPersonalAcademicCredit(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrPersonalAcademicCredit> list = (List<HrPersonalAcademicCredit>) hrPersonalAcademicCreditMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrPersonalAcademicCredit> list = (List<HrPersonalAcademicCredit>) hrPersonalAcademicCreditMapper
					.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrPersonalAcademicCredit queryByCodePersonalAcademicCredit(
			Map<String, Object> entityMap) throws DataAccessException {
		return hrPersonalAcademicCreditMapper.queryByCode(entityMap);
	}

	@Override
	public String queryPersonalAcademicCreditTree(Map<String, Object> entityMap)
			throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrPersonalAcademicCredit> storeTypeList = (List<HrPersonalAcademicCredit>) hrPersonalAcademicCreditMapper
				.query(entityMap);
		for (HrPersonalAcademicCredit storeType : storeTypeList) {
			/*
			 * treeJson.append( "{'id':'" + storeType.getKind_code() +
			 * "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
			 */
		}
		treeJson.append("]");
		return treeJson.toString();
	}

	/**
	 * 查询学术荣誉
	 */
	@Override
	public String queryHonorsName(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrPersonalAcademicCreditMapper.queryHonorsName(entityMap);
		return JSONArray.toJSONString(list);
	}
	/**
	 * 保存个人学术荣誉积分
	 */
	@Override
	public String savePersonalAcademicCredit(Map<String, Object> mapVo) throws DataAccessException {
		try {
			List<HrPersonalAcademicCredit> alllistVo = JSONArray.parseArray(String.valueOf(mapVo.get("paramVo")), HrPersonalAcademicCredit.class);

			if (alllistVo != null && alllistVo.size() > 0) {
				for (HrPersonalAcademicCredit hrPersonalAcademicCredit : alllistVo) {
					hrPersonalAcademicCredit.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					hrPersonalAcademicCredit.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrPersonalAcademicCredit.setYear(mapVo.get("year").toString());
					hrPersonalAcademicCredit.setNote("");

				}

			}
			if (alllistVo.size() > 0) {
				hrPersonalAcademicCreditMapper.deletePersonalAcademicCredit(alllistVo);
				hrPersonalAcademicCreditMapper.addBatch(alllistVo);
				return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			} else {
				return "{\"msg\":\"没有要保存的数据.\",\"state\":\"false\"}";
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	/**
	 * 继承
	 */
	@Override
	public String copyAcademicCredit(Map<String, Object> mapVo) throws DataAccessException {
		try {
			// 继承前先查上一年有没有数据
			int count = hrPersonalAcademicCreditMapper.queryHonorByLastYear(mapVo);
			if(count <= 0){
				return "{\"msg\":\"继承失败,上一年没有数据.\",\"state\":\"false\"}";
			}
			
			if (mapVo!=null) {
				mapVo.put("year", SessionManager.getAcctYear());
			}
			
			// 先删除
			hrPersonalAcademicCreditMapper.deleteHonorCode(mapVo);
			int state  =  hrPersonalAcademicCreditMapper.inheritAcademicCredit(mapVo);
			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public Map<String, Object> queryAcadeHonor(Map<String, Object> entityMap) {
		
		return hrPersonalAcademicCreditMapper.queryAcadeHonor(entityMap);
	}

	@Override
	public String queryAcadeHonorValue(Map<String, Object> entityMap)
			throws DataAccessException {
		Map<String,Object> list=hrPersonalAcademicCreditMapper.queryAcadeHonor(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String savePersonalAcadeHonor(Map<String, Object> entityMap) throws DataAccessException {
		try{
			if (entityMap!=null) {
				Map<String, Object> list=(Map<String, Object>) hrPersonalAcademicCreditMapper.queryAcadeHonor(entityMap);
				if (list!=null && list.size()>0) {
					hrPersonalAcademicCreditMapper.updateAcadeHonor(entityMap);
				}else {
					hrPersonalAcademicCreditMapper.addAcadeHonor(entityMap);
				}
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
	
		}
		
	}
	

}
