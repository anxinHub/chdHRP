/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.accept;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.accept.AssAcceptDetailMapper;
import com.chd.hrp.ass.dao.accept.AssAcceptMainMapper;
import com.chd.hrp.ass.dao.dict.AssAcceptItemMapper;
import com.chd.hrp.ass.entity.accept.AssAcceptDetail;
import com.chd.hrp.ass.entity.accept.AssAcceptMain;
import com.chd.hrp.ass.entity.apply.AssApplyDept;
import com.chd.hrp.ass.entity.apply.AssApplyDeptDetail;
import com.chd.hrp.ass.entity.dict.AssAcceptItem;
import com.chd.hrp.ass.entity.maintain.AssMaintainRecItem;
import com.chd.hrp.ass.service.accept.AssAcceptDetailService;
import com.chd.hrp.ass.service.accept.AssAcceptMainService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 050601 资产验收明细
 * @Table: ASS_ACCEPT_DETAIL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("assAcceptDetailService")
public class AssAcceptDetailServiceImpl implements AssAcceptDetailService {

	private static Logger logger = Logger.getLogger(AssAcceptDetailServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assAcceptDetailMapper")
	private final AssAcceptDetailMapper assAcceptDetailMapper = null;
	@Resource(name = "assAcceptMainMapper")
	private final AssAcceptMainMapper assAcceptMainMapper = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	// 引入Service服务
	@Resource(name = "assAcceptMainService")
	private final AssAcceptMainService assAcceptMainService = null;
	@Resource(name = "assAcceptItemMapper")
	private final AssAcceptItemMapper assAcceptItemMapper = null;

	/**
	 * @Description 添加050601 资产验收明细<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAssAcceptDetail(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050601 资产验收明细
		AssAcceptDetail assAcceptDetail = queryAssAcceptDetailByCode(entityMap);

		if (assAcceptDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assAcceptDetailMapper.addAssAcceptDetail(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量添加050601 资产验收明细<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAssAcceptDetail(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assAcceptDetailMapper.addBatchAssAcceptDetail(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新050601 资产验收明细<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAssAcceptDetail(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assAcceptDetailMapper.updateAssAcceptDetail(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量更新050601 资产验收明细<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAssAcceptDetail(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assAcceptDetailMapper.updateBatchAssAcceptDetail(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除050601 资产验收明细<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAssAcceptDetail(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assAcceptDetailMapper.deleteAssAcceptDetail(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量删除050601 资产验收明细<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAssAcceptDetail(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			assAcceptItemMapper.deleteBatchAssAcceptItem(entityList);
			assAcceptDetailMapper.deleteBatchAssAcceptDetail(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 添加050601 资产验收明细<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdateAssAcceptDetail(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050601 资产验收明细
		Map<String, Object> mapVo = new HashMap<String, Object>();
		Map<String, Object> inMapVo = new HashMap<String, Object>();
		Map<String, Object> validateMapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("accept_detail_id", entityMap.get("accept_detail_id"));
		inMapVo.put("group_id", entityMap.get("group_id"));
		inMapVo.put("hos_id", entityMap.get("hos_id"));
		inMapVo.put("copy_code", entityMap.get("copy_code"));
		inMapVo.put("accept_id", entityMap.get("accept_id"));
		validateMapVo.put("group_id", entityMap.get("group_id"));
		validateMapVo.put("hos_id", entityMap.get("hos_id"));
		validateMapVo.put("copy_code", entityMap.get("copy_code"));
		validateMapVo.put("ass_id", entityMap.get("ass_id"));
		validateMapVo.put("ass_no", entityMap.get("ass_no"));
		validateMapVo.put("ass_model", entityMap.get("ass_model"));
		validateMapVo.put("ass_spec", entityMap.get("ass_spec"));
		validateMapVo.put("ass_brand", entityMap.get("ass_brand"));
		validateMapVo.put("fac_id", entityMap.get("fac_id"));
		validateMapVo.put("accept_id", entityMap.get("accept_id"));
		

		String warranty_date = String.valueOf(entityMap.get("warranty_date"));
		if (StringUtils.isNotEmpty(warranty_date) && !"null".equals(warranty_date)) {
			entityMap.put("warranty_date", DateUtil.stringToDate(warranty_date, "yyyy-MM-dd"));
		}
		
		String depre_begin_date = String.valueOf(entityMap.get("depre_begin_date"));
		if (StringUtils.isNotEmpty(depre_begin_date) && !"null".equals(depre_begin_date)) {
			entityMap.put("depre_begin_date", DateUtil.stringToDate(depre_begin_date, "yyyy-MM-dd"));
		}

		
		// AssAcceptDetail assAcceptDetail =
		// queryAssAcceptDetailByCode(entityMap);
		List<AssAcceptDetail> list = assAcceptDetailMapper.queryAssAcceptDetailExists(mapVo);
//	    List<AssAcceptItem> listAss=assAcceptMainService.queryByAcceptId(entityMap);
	    
		try {
			if (list.size() > 0) {

				for (AssAcceptDetail assAcceptDetail : list) {
					int ass_id_no = Integer.parseInt(assAcceptDetail.getAss_id().toString());
					int assid_no = Integer.parseInt(entityMap.get("ass_id").toString());
					if (ass_id_no != assid_no) {
						assAcceptItemMapper.deleteAssAcceptItemByAssAcceptDetail(assAcceptDetail);

					} else {
						int state = assAcceptDetailMapper.updateAssAcceptDetail(entityMap);
					}
				}
				// assAcceptDetailMapper.updateAssAcceptDetail(entityMap);

				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			} else {
				List<AssAcceptDetail> validateList = (List<AssAcceptDetail>) assAcceptDetailMapper
						.queryByAssAcceptId(validateMapVo);
				if (validateList.size() > 0) {
					return "{\"error\":\"资产信息重复.\"}";
				}
				int state = assAcceptDetailMapper.addAssAcceptDetail(entityMap);

				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

			}
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集050601 资产验收明细<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssAcceptDetail(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssAcceptDetail> list = assAcceptDetailMapper.queryAssAcceptDetail(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssAcceptDetail> list = assAcceptDetailMapper.queryAssAcceptDetail(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050601 资产验收明细<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AssAcceptDetail queryAssAcceptDetailByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assAcceptDetailMapper.queryAssAcceptDetailByCode(entityMap);
	}

	/**
	 * @Description 获取050601 资产验收明细<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssAcceptDetail
	 * @throws DataAccessException
	 */
	@Override
	public AssAcceptDetail queryAssAcceptDetailByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assAcceptDetailMapper.queryAssAcceptDetailByUniqueness(entityMap);
	}

	@Override
	public String queryAssAcceptDetailByUpdate(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = assAcceptDetailMapper.queryAssAcceptDetailByUpdate(entityMap);

		return ChdJson.toJson(list);
	}
	
	@Override
	public String queryAssAcceptDetailIn(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = assAcceptDetailMapper.queryAssAcceptDetailIn(entityMap);

		return ChdJson.toJson(list);
	}
	
	@Override
	public List<AssAcceptDetail> queryAssAcceptDetailExists(Map<String, Object> entityMap) throws DataAccessException {
		return assAcceptDetailMapper.queryAssAcceptDetailExists(entityMap);
	}

	/*
	 * 生成合同单据
	 */
	@Override

	public String initAssAcceptDetail(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assAcceptDetailMapper.initAssAcceptDetail(entityMap);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String initInstallAssAcceptDetail(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int state = assAcceptDetailMapper.initInstallAssAcceptDetail(entityMap);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String initAssAcceptDetailBid(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int state = assAcceptDetailMapper.initAssAcceptDetailBid(entityMap);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String initInstallAssAcceptDetailBid(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int state = assAcceptDetailMapper.initInstallAssAcceptDetailBid(entityMap);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String deleteBatchAssAcceptInsMap(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			int state = assAcceptDetailMapper.deleteBatchAssAcceptInsMap(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String deleteBatchAssAcceptContractMap(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			int state = assAcceptDetailMapper.deleteBatchAssAcceptContractMap(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}


}
