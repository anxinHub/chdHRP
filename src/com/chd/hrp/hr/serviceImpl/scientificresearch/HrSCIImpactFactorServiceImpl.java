package com.chd.hrp.hr.serviceImpl.scientificresearch;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.scientificresearch.HrSCIImpactFactorMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrSCIImpactFactor;
import com.chd.hrp.hr.service.scientificresearch.HrSCIImpactFactorService;
import com.github.pagehelper.PageInfo;

/**
 * SCI论文影响因子
 * 
 * @author Administrator
 *
 */
@Service("hrSCIImpactFactorService")
public class HrSCIImpactFactorServiceImpl implements HrSCIImpactFactorService {
	private static Logger logger = Logger.getLogger(HrSCIImpactFactorServiceImpl.class);

	@Resource(name = "hrSCIImpactFactorMapper")
	private final HrSCIImpactFactorMapper hrSCIImpactFactorMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * SCI论文影响因子增加
	 */
	@Override
	public String addSCIImpactFactor(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrSCIImpactFactor> list = hrSCIImpactFactorMapper.querySCIImpactFactorById(entityMap);

		if (list.size() > 0) {

			for (HrSCIImpactFactor hrSCIImpactFactor : list) {
				/*
				 * if (hrSCIImpactFactor.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrSCIImpactFactor.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {
           
			List<HrSCIImpactFactor> listVo=JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")), HrSCIImpactFactor.class);
			if (listVo!=null &&listVo.size()>0) {
				for (HrSCIImpactFactor hrSCIImpactFactor : listVo) {
					
				}
			}
			
			
			int state = hrSCIImpactFactorMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * SCI论文影响因子修改
	 */
	@Override
	public String updateSCIImpactFactor(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrSCIImpactFactorMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * SCI论文影响因子删除
	 */
	@Override
	public String deleteSCIImpactFactor(List<HrSCIImpactFactor> entityList) throws DataAccessException {

		try {

			hrSCIImpactFactorMapper.deleteSCIImpactFactor(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * SCI论文影响因子查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String querySCIImpactFactor(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrSCIImpactFactor> list = (List<HrSCIImpactFactor>) hrSCIImpactFactorMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrSCIImpactFactor> list = (List<HrSCIImpactFactor>) hrSCIImpactFactorMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrSCIImpactFactor queryByCodeSCIImpactFactor(Map<String, Object> entityMap) throws DataAccessException {
		return hrSCIImpactFactorMapper.queryByCode(entityMap);
	}

	@Override
	public String querySCIImpactFactorTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrSCIImpactFactor> storeTypeList = (List<HrSCIImpactFactor>) hrSCIImpactFactorMapper.query(entityMap);
		for (HrSCIImpactFactor storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
