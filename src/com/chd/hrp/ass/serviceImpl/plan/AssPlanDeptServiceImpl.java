/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.plan;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.apply.AssApplyDeptMapper;
import com.chd.hrp.ass.dao.plan.AssPlanDeptDetailMapper;
import com.chd.hrp.ass.dao.plan.AssPlanDeptMapper;
import com.chd.hrp.ass.entity.plan.AssPlanDept;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.plan.AssPlanDeptDetailService;
import com.chd.hrp.ass.service.plan.AssPlanDeptService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 050301 购置计划单
 * @Table: ASS_PLAN_DEPT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Service("assPlanDeptService")
public class AssPlanDeptServiceImpl implements AssPlanDeptService {

	private static Logger logger = Logger.getLogger(AssPlanDeptServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assPlanDeptMapper")
	private final AssPlanDeptMapper assPlanDeptMapper = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	@Resource(name = "assPlanDeptDetailMapper")
	private final AssPlanDeptDetailMapper assPlanDeptDetailMapper = null;
	
	@Resource(name= "assPlanDeptDetailService")
	private final AssPlanDeptDetailService assPlanDeptDetailService = null;

	/**
	 * @Description 添加050301 购置计划单<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAssPlanDept(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("bill_table", "ASS_PLAN_DEPT");
		// 获取对象050301 购置计划单
		AssPlanDept assPlanDept = queryAssPlanDeptByCode(entityMap);

		if (assPlanDept != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			String plan_no = assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("plan_no", plan_no);
			int state = assPlanDeptMapper.addAssPlanDept(entityMap);
			Long plan_id = queryAssPlanDeptSequence();
			if (state > 0) {
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}

			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"plan_id\":\"" + plan_id + "\",\"plan_no\":\"" + plan_no
					+ "\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	///
	/**
	 * @Description 添加050301 购置计划单<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAssPlanDeptImport(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050301 购置计划单
		AssPlanDept assPlanDept = queryAssPlanDeptByCode(entityMap);

		if (assPlanDept != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assPlanDeptMapper.addAssPlanDeptImport(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量添加050301 购置计划单<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAssPlanDept(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assPlanDeptMapper.addBatchAssPlanDept(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新050301 购置计划单<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAssPlanDept(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assPlanDeptMapper.updateAssPlanDept(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量更新050301 购置计划单<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAssPlanDept(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assPlanDeptMapper.updateBatchAssPlanDept(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除050301 购置计划单<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAssPlanDept(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assPlanDeptMapper.deleteAssPlanDept(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量删除050301 购置计划单<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAssPlanDept(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assPlanDeptDetailMapper.deleteBatchAssPlanDeptDetail(entityList);
			assPlanDeptMapper.deleteBatchAssPlanDept(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 添加050301 购置计划单<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdateAssPlanDept(Map<String, Object> entityMap) throws DataAccessException {

		String plan_id = entityMap.get("plan_id").toString();
		String plan_no = entityMap.get("plan_no").toString();
		
		// //获取对象050301 购置计划单
		// AssPlanDept assPlanDept = queryAssPlanDeptByCode(entityMap);
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("plan_id", entityMap.get("plan_id"));
		if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
			entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
			entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
		}
		entityMap.put("bill_table", "ASS_PLAN_DEPT");
		List<AssPlanDept> list = assPlanDeptMapper.queryAssPlanDeptExists(mapVo);

		try {
			if (list.size() > 0) {
				int state = assPlanDeptMapper.updateAssPlanDept(entityMap);
			}else{
				plan_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("plan_no", plan_no);
				int state = assPlanDeptMapper.addAssPlanDept(entityMap);
				plan_id = String.valueOf(queryAssPlanDeptSequence());
				if (state > 0) {
					assBaseService.updateAssBillNoMaxNo(entityMap);
				}
			}
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {
				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				
				detailVo.put("price",detailVo.get("sum_price"));
				detailVo.put("plan_id", plan_id);
				detailVo.put("plan_no", plan_no);
				detailVo.put("source_id", entityMap.get("source_id"));
				
				if (detailVo.get("plan_detail_id") == null) {
					detailVo.put("plan_detail_id", "0");
				}

				if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
					continue;
				}

				if (StringUtils.isNotEmpty((String) detailVo.get("need_date"))) {
					detailVo.put("need_date",
							DateUtil.stringToDate(detailVo.get("need_date").toString(), "yyyy-MM-dd"));
				}
				
				String id = detailVo.get("ass_id").toString();

			
				// double sum_price= (Double) detailVo.get("sum_price");
				detailVo.put("ass_id", id.split("@")[0]);

				detailVo.put("ass_no", id.split("@")[1]);
				if (detailVo.get("fac_id") != null) {
					String fid = detailVo.get("fac_id").toString();
				
				if(fid != null && fid.split("@").length == 2){
					detailVo.put("fac_id", fid.split("@")[0]);
					detailVo.put("fac_no", fid.split("@")[1]);
				}else{
					detailVo.put("fac_id", null);
					detailVo.put("fac_no", null);
				}}
				// detailVo.put("sum_price", sum_price);

				assPlanDeptDetailService.addOrUpdateAssPlanDeptDetail(detailVo);
			}
			//return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"plan_id\":\"" + entityMap.get("plan_id")
			//+ "\",\"plan_no\":\"" + entityMap.get("plan_no") + "\"}";
			return "{\"msg\":\"操作成功.\",\"state\":\"true\",\"plan_id\":\"" + plan_id + "\",\"plan_no\":\"" + plan_no
					+ "\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集050301 购置计划单<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssPlanDept(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssPlanDept> list = assPlanDeptMapper.queryAssPlanDept(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssPlanDept> list = assPlanDeptMapper.queryAssPlanDept(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	///
	@Override
	public String queryAssPlanDepts(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssPlanDept> list = assPlanDeptMapper.queryAssPlanDepts(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssPlanDept> list = assPlanDeptMapper.queryAssPlanDepts(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050301 购置计划单<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AssPlanDept queryAssPlanDeptByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assPlanDeptMapper.queryAssPlanDeptByCode(entityMap);
	}

	/**
	 * @Description 获取050301 购置计划单<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssPlanDept
	 * @throws DataAccessException
	 */
	@Override
	public AssPlanDept queryAssPlanDeptByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assPlanDeptMapper.queryAssPlanDeptByUniqueness(entityMap);
	}

	@Override
	public Long queryAssPlanDeptSequence() throws DataAccessException {
		// TODO Auto-generated method stub
		return assPlanDeptMapper.queryAssPlanDeptSequence();
	}

	@Override
	public String updateToExamine(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO 审核
		try {

			int state = assPlanDeptMapper.updateToExamine(entityMap);

			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateNotToExamine(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO 销审
		try {

			int state = assPlanDeptMapper.updateNotToExamine(entityMap);

			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String examineAndApprove(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO 审批意见
		try {

			int state = assPlanDeptMapper.examineAndApprove(entityMap);

			return "{\"msg\":\"审批成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<AssPlanDept> queryAssPlanDeptExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return assPlanDeptMapper.queryAssPlanDeptExists(entityMap);
	}

	/**
	 * @Description 引入购置计划时 往中间表 添加数据 《ASS_BID_PLAN_MAP》
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String addAssPlanDeptImportBid(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			assPlanDeptMapper.addAssPlanDeptImportBid(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String queryAssPlanDeptByGroup(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssPlanDept> list = assPlanDeptMapper.queryAssPlanDeptByGroup(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssPlanDept> list = assPlanDeptMapper.queryAssPlanDeptByGroup(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	/**
	 * @Description 购置计划打印05009
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public Map<String,Object> queryAssPlanDeptDY(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssPlanDeptMapper assPlanDeptMapper = (AssPlanDeptMapper)context.getBean("assPlanDeptMapper");
			if("1".equals(String.valueOf(map.get("p_num")))){
				//置购计划打印模板主表
				List<Map<String,Object>> mainList=assPlanDeptMapper.queryAssPlanDeptBatch(map);
						
				//置购计划打印模板从表
				List<Map<String,Object>> detailList=assPlanDeptMapper.queryAssPlanDept_detail(map);
				
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}else{
				Map<String,Object> mainList=assPlanDeptMapper.queryAssPlanDeptByPrint(map);
				
				//置购打印模板从表
				List<Map<String,Object>> detailList=assPlanDeptMapper.queryAssPlanDept_detail(map);
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}
			
		
			
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	
	
	
	@Override
	public String queryAssPlanDeptsByHosGroup(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssPlanDept> list = assPlanDeptMapper.queryAssPlanDeptsByHosGroup(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssPlanDept> list = assPlanDeptMapper.queryAssPlanDeptsByHosGroup(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	/**
	 * 打印状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<String> queryPlanDeptState(Map<String, Object> mapVo)
			throws DataAccessException {
	
		return assPlanDeptMapper.queryPlanDeptState(mapVo);
	}

	


}
