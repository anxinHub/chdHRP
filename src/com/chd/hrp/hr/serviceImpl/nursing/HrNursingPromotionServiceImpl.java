package com.chd.hrp.hr.serviceImpl.nursing;

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
import com.chd.hrp.hr.dao.nursing.HrNursingPromotionMapper;
import com.chd.hrp.hr.entity.nursing.HrNursingPromotion;
import com.chd.hrp.hr.entity.nursing.HrPromotionLeave;
import com.chd.hrp.hr.service.nursing.HrNursingPromotionService;
import com.github.pagehelper.PageInfo;

/**
 * 护理晋级申请表
 * 
 * @author Administrator
 *
 */
@Service("hrNursingPromotionService")
public class HrNursingPromotionServiceImpl implements HrNursingPromotionService {
	private static Logger logger = Logger.getLogger(HrNursingPromotionServiceImpl.class);

	@Resource(name = "hrNursingPromotionMapper")
	private final HrNursingPromotionMapper hrNursingPromotionMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 护理晋级申请表增加
	 */
	@Override
	public String addNursingPromotion(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrNursingPromotion> list = hrNursingPromotionMapper.queryNursingPromotionById(entityMap);

		if (list.size() > 0) {

			for (HrNursingPromotion hrNursingPromotion : list) {
				
				  if (hrNursingPromotion.getYear().equals(entityMap.get("year"))) {
				 return "{\"error\":\"年度：" + hrNursingPromotion.getYear().toString() +
				  "已存在.\"}"; } if
				 (hrNursingPromotion.getEmp_name().equals(entityMap.get("emp_name"))) { return
				  "{\"error\":\"用户：" + hrNursingPromotion.getEmp_name().toString() + "已存在.\"}"; }
				 
			}
		}
		try {
			List<HrPromotionLeave> listVo = JSONArray.parseArray(entityMap.get("Param").toString(), HrPromotionLeave.class);
			if (listVo!=null) {
				for (HrPromotionLeave hrUserPermData : listVo) {
					
					hrUserPermData.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
					hrUserPermData.setHos_id(Long.parseLong(SessionManager.getHosId()));
					hrUserPermData.setEmp_id(Integer.parseInt(entityMap.get("emp_id").toString()));
					hrUserPermData.setYear(entityMap.get("year").toString());
				}
				addaddNursingPromotion(listVo);
			}
			
			@SuppressWarnings("unused")
			int state = hrNursingPromotionMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 护理晋级申请表修改
	 */
	@Override
	public String updateNursingPromotion(Map<String, Object> entityMap) throws DataAccessException {

		try {

		
			List<HrPromotionLeave> listVo = JSONArray.parseArray(entityMap.get("Param").toString(), HrPromotionLeave.class);
			if (listVo!=null) {
				for (HrPromotionLeave hrUserPermData : listVo) {
					
					hrUserPermData.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
					hrUserPermData.setHos_id(Long.parseLong(SessionManager.getHosId()));
					hrUserPermData.setEmp_id(Integer.parseInt(entityMap.get("emp_id").toString()));
					hrUserPermData.setYear(entityMap.get("year").toString());
				}
				UpdateAttend(listVo);
			}
			
			@SuppressWarnings("unused")      
			int state = hrNursingPromotionMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}



	/**
	 * 护理晋级申请表删除
	 */
	@Override
	public String deleteNursingPromotion(List<HrNursingPromotion> entityList) throws DataAccessException {

		try {

			hrNursingPromotionMapper.deleteNursingPromotion(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 护理晋级申请表查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryNursingPromotion(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrNursingPromotion> list = (List<HrNursingPromotion>) hrNursingPromotionMapper.queryNursingPromotion(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrNursingPromotion> list = (List<HrNursingPromotion>) hrNursingPromotionMapper.queryNursingPromotion(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrNursingPromotion queryByCodeNursingPromotion(Map<String, Object> entityMap) throws DataAccessException {
		return hrNursingPromotionMapper.queryByCode(entityMap);
	}
	 /**
     * 提交护理晋级申请
     */
	@Override
	public String confirmNursingPromotion(HrNursingPromotion hrNursingPromotion) {
		try {
			hrNursingPromotionMapper.addPromotionEvaluate(hrNursingPromotion);
			hrNursingPromotionMapper.confirmNursingPromotion(hrNursingPromotion);
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	@Override
	public String confirmNursingPromotionBatch(List<HrNursingPromotion> list) {
		try {
			if(list == null || list.size() == 0){
				return "{\"msg\":\"提交失败，请选择行！\",\"state\":\"false\"}";
			}
			hrNursingPromotionMapper.addPromotionEvaluateBatch(list);
			hrNursingPromotionMapper.confirmNursingPromotionBatch(list);
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	 /**
     * 取消提交护理晋级申请
     */
	@Override
	public String reConfirmNursingPromotion(HrNursingPromotion hrNursingPromotion) {
		try {
			hrNursingPromotionMapper.reConfirmPromotionEvaluate(hrNursingPromotion);
			hrNursingPromotionMapper.reConfirmNursingPromotion(hrNursingPromotion);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	/**
     * 取消提交护理晋级申请(批量)
     */
	@Override
	public String reConfirmNursingPromotionBatch(List<HrNursingPromotion> list) {
		try {
			if(list == null || list.size() == 0){
				return "{\"msg\":\"撤回失败，请选择行！\",\"state\":\"false\"}";
			}
			hrNursingPromotionMapper.reConfirmPromotionEvaluateBatch(list);
			hrNursingPromotionMapper.confirmNursingPromotionBatch(list);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryHosEmpDetail(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> list = hrNursingPromotionMapper.queryHosEmpDetail(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryLevel(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> list = hrNursingPromotionMapper.queryLevel(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryAttend(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = (List<Map<String, Object>>) hrNursingPromotionMapper.queryAttend(entityMap);
		return ChdJson.toJson(list);
	}
   /**
    * 添加近三年资料
    */
	@Override
	public String addaddNursingPromotion(List<HrPromotionLeave> entityMap) throws DataAccessException {

		try {
			
			int state=hrNursingPromotionMapper.addaddNursingPromotion(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	 /**
	  * 修改近三年资料
	  * @param listVo
	  */
	private String UpdateAttend(List<HrPromotionLeave> entityMap) throws DataAccessException{
	try {
			
			int state=hrNursingPromotionMapper.UpdateAttend(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String deletePromotionLeave(List<HrNursingPromotion> entityList) throws DataAccessException {

		try {

			hrNursingPromotionMapper.deletePromotionLeave(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	/**
	 * 打印
	 */
	@Override
	public List<Map<String,Object>> queryNursingPromotionByPrint(Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrNursingPromotionMapper.queryNursingPromotionByPrint(entityMap));
		 return list;
	}
	

}
