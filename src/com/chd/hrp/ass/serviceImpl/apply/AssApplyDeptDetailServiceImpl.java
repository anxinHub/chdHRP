/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.apply;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.apply.AssApplyDeptDetailMapper;
import com.chd.hrp.ass.dao.apply.AssApplyDeptMapper;
import com.chd.hrp.ass.dao.apply.AssApplyDeptProofMapper;
import com.chd.hrp.ass.dao.resource.AssApplyDeptResourceMapper;
import com.chd.hrp.ass.entity.apply.AssApplyDept;
import com.chd.hrp.ass.entity.apply.AssApplyDeptDetail;
import com.chd.hrp.ass.entity.resource.AssApplyDeptResource;
import com.chd.hrp.ass.service.apply.AssApplyDeptDetailService;
import com.chd.hrp.ass.service.resource.AssApplyDeptResourceService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 050201 资产购置申请明显表
 * @Table: ASS_APPLY_DEPT_DETAIL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("assApplyDeptDetailService")
public class AssApplyDeptDetailServiceImpl implements AssApplyDeptDetailService {

	private static Logger logger = Logger.getLogger(AssApplyDeptDetailServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "assApplyDeptDetailMapper")
	private final AssApplyDeptDetailMapper assApplyDeptDetailMapper = null;
	@Resource(name = "assApplyDeptMapper")
	private final AssApplyDeptMapper assApplyDeptMapper = null;
	@Resource(name = "assApplyDeptResourceMapper")
	private final AssApplyDeptResourceMapper assApplyDeptResourceMapper = null;
	@Resource(name = "assApplyDeptResourceService")
	private final AssApplyDeptResourceService assApplyDeptResourceService = null;
	
	@Resource(name = "assApplyDeptProofMapper")
	private final AssApplyDeptProofMapper assApplyDeptProofMapper = null;
	
	
	/**
	 * @Description 添加050201 资产购置申请明显表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAssApplyDeptDetail(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050201 资产购置申请明显表
		AssApplyDeptDetail assApplyDeptDetail = queryAssApplyDeptDetailByCode(entityMap);

		if (assApplyDeptDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assApplyDeptDetailMapper.addAssApplyDeptDetail(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量添加050201 资产购置申请明显表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAssApplyDeptDetail(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assApplyDeptDetailMapper.addBatchAssApplyDeptDetail(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新050201 资产购置申请明显表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAssApplyDeptDetail(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assApplyDeptDetailMapper.updateAssApplyDeptDetail(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量更新050201 资产购置申请明显表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAssApplyDeptDetail(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assApplyDeptDetailMapper.updateBatchAssApplyDeptDetail(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除050201 资产购置申请明显表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAssApplyDeptDetail(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assApplyDeptDetailMapper.deleteAssApplyDeptDetail(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量删除050201 资产购置申请明显表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAssApplyDeptDetail(List<Map<String, Object>> entityList) throws DataAccessException {

		Map<String, Object> inMapVo =new HashMap<String, Object>();
		inMapVo.put("group_id",entityList.get(0).get("group_id"));
    	inMapVo.put("hos_id",entityList.get(0).get("hos_id"));
    	inMapVo.put("copy_code", entityList.get(0).get("copy_code"));
    	inMapVo.put("apply_id", entityList.get(0).get("apply_id"));
    	inMapVo.put("apply_no", entityList.get(0).get("apply_no"));
		try {

			assApplyDeptDetailMapper.deleteBatchAssApplyDeptDetail(entityList);
			List<AssApplyDeptDetail> details=assApplyDeptDetailMapper.queryByAssApplyDeptDetail(inMapVo);
			double apply_money=0;
			for(AssApplyDeptDetail temp :  details ){
				apply_money += Double.parseDouble(temp.getApply_amount().toString()) * temp.getAdvice_price();
			}
			inMapVo.put("apply_money", apply_money+"");
			assApplyDeptMapper.updateAssApplyDept(inMapVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"apply_money\":\""+apply_money+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
//	///
//	@Override
//	public String deleteBatchAssApplyDeptDetails(List<Map<String, Object>> entityList) throws DataAccessException {
//
//		try {
//
//			assApplyDeptDetailMapper.deleteBatchAssApplyDeptDetail(entityList);
//			
//			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
//
//		}
//		catch (Exception e) {
//
//			logger.error(e.getMessage(), e);
//
//			throw new SysException(e.getMessage());
//
//		}
//	}


	/**
	 * @Description 查询结果集050201 资产购置申请明显表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssApplyDeptDetail(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssApplyDeptDetail> list = assApplyDeptDetailMapper.queryAssApplyDeptDetail(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssApplyDeptDetail> list = assApplyDeptDetailMapper.queryAssApplyDeptDetail(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050201 资产购置申请明显表<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AssApplyDeptDetail queryAssApplyDeptDetailByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assApplyDeptDetailMapper.queryAssApplyDeptDetailByCode(entityMap);
	}

	/**
	 * @Description 获取050201 资产购置申请明显表<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return AssApplyDeptDetail
	 * @throws DataAccessException
	 */
	@Override
	public AssApplyDeptDetail queryAssApplyDeptDetailByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assApplyDeptDetailMapper.queryAssApplyDeptDetailByUniqueness(entityMap);
	}

	@Override
	public String addOrUpdateAssApplyDeptDetail(Map<String, Object> entityMap) throws DataAccessException {
//		// 获取对象050201 资产购置申请明显表
//		AssApplyDeptDetail assApplyDeptDetail = queryAssApplyDeptDetailByCode(entityMap);
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); //格式化当前系统日期
		Map<String, Object> mapVo=new HashMap<String, Object>();
		Map<String, Object> inMapVo =new HashMap<String, Object>();
		Map<String, Object> validateMapVo =new HashMap<String, Object>();
		entityMap.put("price", entityMap.get("sum_price"));
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("detail_id", entityMap.get("detail_id"));
    	mapVo.put("apply_id", entityMap.get("apply_id"));
    	inMapVo.put("group_id",entityMap.get("group_id")); 
    	inMapVo.put("hos_id",entityMap.get("hos_id"));
    	inMapVo.put("copy_code", entityMap.get("copy_code"));
    	inMapVo.put("apply_id", entityMap.get("apply_id"));
    	validateMapVo.put("group_id", entityMap.get("group_id"));
    	validateMapVo.put("hos_id",entityMap.get("hos_id"));
    	validateMapVo.put("copy_code", entityMap.get("copy_code"));
    	validateMapVo.put("ass_code", entityMap.get("ass_no"));
    	validateMapVo.put("ass_model", entityMap.get("ass_model"));
    	validateMapVo.put("ass_spec", entityMap.get("ass_spec"));
    	validateMapVo.put("ass_brand",entityMap.get("ass_brand"));
    	validateMapVo.put("fac_id", entityMap.get("fac_id"));
    	validateMapVo.put("apply_id", entityMap.get("apply_id"));
       	validateMapVo.put("ass_id", entityMap.get("ass_id"));
    	validateMapVo.put("ass_no", entityMap.get("ass_no"));
    	
    	List<AssApplyDeptDetail> list=assApplyDeptDetailMapper.queryAssApplyDeptDetailExists(mapVo);
    	try {
		if (list.size()>0) {
			for (AssApplyDeptDetail assApplyDeptDetail : list) {
				
					assApplyDeptResourceMapper.deleteAssAcceptItemAssApplyDeptDetail(assApplyDeptDetail);
					assApplyDeptResourceMapper.addAssPlanSource(entityMap);
				
			}
			//System.out.println("更新明细数据**************"+entityMap);
			int state = assApplyDeptDetailMapper.updateAssApplyDeptDetail(entityMap);
			
			List<AssApplyDept> details=(List<AssApplyDept>)assApplyDeptDetailMapper.queryByAssApplyId(inMapVo);
			double apply_money=0;
			int detail_id = 0;
			for(AssApplyDept temp :  details ){
				apply_money += Double.parseDouble(temp.getApply_amount().toString()) * temp.getAdvice_price();
				detail_id=Integer.parseInt(String.valueOf(temp.getDetail_id()));
			}
			inMapVo.put("apply_money", apply_money+"");
			inMapVo.put("detail_id", detail_id);

			assApplyDeptMapper.updateAssApplyDept(inMapVo);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"apply_money\":\""+apply_money+"\",\"detail_id\":\"" + detail_id
					+ "\"}";
		}else{

			List<AssApplyDept> validateList = (List<AssApplyDept>) assApplyDeptDetailMapper.queryByAssApplyId(validateMapVo);
			if(validateList.size() > 0){
				return "{\"error\":\"资产信息重复.\"}";
			}
			//System.out.println("添加明细数据*******************"+entityMap);
			String emp_dept_id_no = entityMap.get("emp_dept_id").toString();
			entityMap.put("emp_dept_id", emp_dept_id_no.split("@")[0]);
			entityMap.put("emp_dept_no", emp_dept_id_no.split("@")[1]);
			
			int state = assApplyDeptDetailMapper.addAssApplyDeptDetailNew(entityMap);


			List<AssApplyDeptDetail> details=assApplyDeptDetailMapper.queryByAssApplyDeptDetail(inMapVo);
			double apply_money=0;
			int detail_id = 0;
			for(AssApplyDeptDetail temp :  details ){
				apply_money += Double.parseDouble(temp.getApply_amount().toString()) * temp.getAdvice_price();
				detail_id=Integer.parseInt(String.valueOf(temp.getDetail_id()));
			}
			inMapVo.put("apply_money", apply_money+"");
			entityMap.put("detail_id", detail_id);
			assApplyDeptResourceService.addAssPlanSource(entityMap);

			assApplyDeptMapper.updateAssApplyDept(inMapVo);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"apply_money\":\""+apply_money+"\",\"detail_id\":\"" + detail_id
					+ "\"}";
		}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String queryAssApplyDeptDetailByUpdate(Map<String, Object> entityMap) throws DataAccessException{
		List<Map<String,Object>> list =assApplyDeptDetailMapper.queryAssApplyDeptDetailByUpdate(entityMap);
		
		for(Map<String,Object> item : list){
			List<Map<String,Object>> proofDetaillist = assApplyDeptProofMapper.queryApplyProofDetail(item);
			
			item.put("detailData", JSONObject.parseObject(ChdJson.toJson(proofDetaillist)));
		}
		
		return ChdJson.toJson(list);
	}

	@Override
	public List<AssApplyDeptDetail> queryByAssApplyDeptDetail(
			Map<String, Object> entityMap) throws DataAccessException {
		return assApplyDeptDetailMapper.queryByAssApplyDeptDetail(entityMap);
	}

	@Override
	public List<AssApplyDeptDetail> queryAssApplyDeptDetailExists(
			Map<String, Object> entityMap) throws DataAccessException {
		return assApplyDeptDetailMapper.queryAssApplyDeptDetailExists(entityMap);
	}

	@Override
	public List<AssApplyDeptDetail> queryByAssApplyDeptDetailByPlanDept(Map<String, Object> entityMap)
			throws DataAccessException {
		return assApplyDeptDetailMapper.queryByAssApplyDeptDetailByPlanDept(entityMap);
	}

}
