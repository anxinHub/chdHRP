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
import com.chd.hrp.hr.dao.scientificresearch.HrAcademicPaperMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrAcademicPaper;
import com.chd.hrp.hr.service.scientificresearch.HrAcademicPaperService;
import com.github.pagehelper.PageInfo;

/**
 * 学术论文发表
 * 
 * @author Administrator
 *
 */
@Service("hrAcademicPaperService")
public class HrAcademicPaperServiceImpl implements HrAcademicPaperService {
	private static Logger logger = Logger.getLogger(HrAcademicPaperServiceImpl.class);

	@Resource(name = "hrAcademicPaperMapper")
	private final HrAcademicPaperMapper hrAcademicPaperMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 学术论文发表增加
	 */
	@Override
	public String addAcademicPaper(Map<String, Object> entityMap) throws DataAccessException {
		try {

			List<HrAcademicPaper> listVo=JSONArray.parseArray(String.valueOf(entityMap.get("paraVo")), HrAcademicPaper.class);
			if (listVo!=null && listVo.size()>0) {
				for (HrAcademicPaper hrAcademicPaper : listVo) {
					
				}
			}
			
			
			
			
			
			
			int state = hrAcademicPaperMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 学术论文发表修改
	 */
	@Override
	public String updateAcademicPaper(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrAcademicPaperMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 学术论文发表删除
	 */
	@Override
	public String deleteAcademicPaper(List<HrAcademicPaper> entityList) throws DataAccessException {

		try {

			hrAcademicPaperMapper.deleteAcademicPaper(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 学术论文发表查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryAcademicPaper(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrAcademicPaper> list = (List<HrAcademicPaper>) hrAcademicPaperMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrAcademicPaper> list = (List<HrAcademicPaper>) hrAcademicPaperMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrAcademicPaper queryByCodeAcademicPaper(Map<String, Object> entityMap) throws DataAccessException {
		return hrAcademicPaperMapper.queryByCode(entityMap);
	}

	@Override
	public String queryAcademicPaperTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrAcademicPaper> storeTypeList = (List<HrAcademicPaper>) hrAcademicPaperMapper.query(entityMap);
		for (HrAcademicPaper storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
