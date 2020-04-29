/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.termend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.acc.dao.termend.AccTermendTemplateDeptMapper;
import com.chd.hrp.acc.dao.termend.AccTermendTemplateDetailMapper;
import com.chd.hrp.acc.dao.termend.AccTermendTemplateMapper;
import com.chd.hrp.acc.entity.AccTermendTemplate;
import com.chd.hrp.acc.entity.AccTermendTemplateDetail;
import com.chd.hrp.acc.service.termend.AccTermendTemplateService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 
 * 期末处理模板<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accTermendTemplateService")
public class AccTermendTemplateServiceImpl implements AccTermendTemplateService {

	private static Logger logger = Logger.getLogger(AccTermendTemplateServiceImpl.class);

	@Resource(name = "accTermendTemplateMapper")
	private final AccTermendTemplateMapper accTermendTemplateMapper = null;
	
	@Resource(name = "accTermendTemplateDetailMapper")
	private final AccTermendTemplateDetailMapper accTermendTemplateDetailMapper = null;
	
	@Resource(name = "accTermendTemplateDeptMapper")
	private final AccTermendTemplateDeptMapper accTermendTemplateDeptMapper = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	/**
	 * @Description 期末处理模板<BR>
	 *              查询期末处理模板相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccTermendTemplate>
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccTermendTemplate(Map<String, Object> entityMap) throws DataAccessException {
		List<AccTermendTemplate> list = accTermendTemplateMapper.queryAccTermendTemplate(entityMap);
		return ChdJson.toJson(list);
	}
	
	/**
	 * @Description 期末处理模板<BR>
	 *              查询期末处理模板明细相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccTermendTemplate>
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccTermendTemplateDetail(Map<String, Object> entityMap) throws DataAccessException {
		List<AccTermendTemplateDetail> list = accTermendTemplateDetailMapper.queryAccTermendTemplateDetail(entityMap);
		return ChdJson.toJson(list);
	}

	/**
	 * @Description 期末处理模板<BR>
	 *              保存期末处理模板
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAccTermendTemplate(Map<String, Object> entityMap) throws DataAccessException {
		try {
			//校验模板名称
			int count = checkName(entityMap, true);
			if (count >0) {
				return "{\"error\":\"模板名称：[" + entityMap.get("template_name").toString() + "]重复.\"}";
			}
			//去掉前后空格
			entityMap.put("template_name", entityMap.get("template_name").toString().trim());
			entityMap.put("summary", entityMap.get("summary").toString().trim());
			//获取主键
			entityMap.put("template_id", accTermendTemplateMapper.queryAccTermendTemplateSeq());
			//判断是否存在明细科目
			if(entityMap.get("detailData") == null){
				//保存模板主表
				accTermendTemplateMapper.addAccTermendTemplate(entityMap);
			}else{
				if(entityMap.get("detailData") != null && !"[]".equals(entityMap.get("detailData").toString())){
					//保存明细数据
					List<Map<String,Object>> list_template_detail_batch = new ArrayList<Map<String,Object>>();
					JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
					Iterator it = json.iterator();
					while (it.hasNext()) {
						Map<String,Object> mapDetailVo=new HashMap<String,Object>();
						mapDetailVo.put("group_id", entityMap.get("group_id"));
						mapDetailVo.put("hos_id", entityMap.get("hos_id"));
						mapDetailVo.put("copy_code", entityMap.get("copy_code"));
						mapDetailVo.put("template_id", entityMap.get("template_id"));
						mapDetailVo.put("acc_year", entityMap.get("acc_year"));
						JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
						mapDetailVo.put("subj_code",  jsonObj.get("subj_code"));
						mapDetailVo.put("detail_type",  jsonObj.get("detail_type"));
						list_template_detail_batch.add(mapDetailVo);
					}
					if(entityMap.get("secondData") != null && !"[]".equals(entityMap.get("secondData").toString())){
						//保存明细数据
						JSONArray secondJson = JSONArray.parseArray((String)entityMap.get("secondData"));
						Iterator secondIt = secondJson.iterator();
						while (secondIt.hasNext()) {
							Map<String,Object> mapDetailVo=new HashMap<String,Object>();
							mapDetailVo.put("group_id", entityMap.get("group_id"));
							mapDetailVo.put("hos_id", entityMap.get("hos_id"));
							mapDetailVo.put("copy_code", entityMap.get("copy_code"));
							mapDetailVo.put("acc_year", entityMap.get("acc_year"));
							mapDetailVo.put("template_id", entityMap.get("template_id"));
							JSONObject jsonObj = JSONObject.parseObject(secondIt.next().toString());
							mapDetailVo.put("subj_code",  jsonObj.get("subj_code"));
							mapDetailVo.put("detail_type",  jsonObj.get("detail_type"));
							list_template_detail_batch.add(mapDetailVo);
						}
					}
					//保存模板主表
					accTermendTemplateMapper.addAccTermendTemplate(entityMap);
					//删除模板明细数据
					accTermendTemplateDetailMapper.deleteAccTermendTemplateDetail(entityMap);
					//保存模板明细表
					accTermendTemplateDetailMapper.addAccTermendTemplateDetail(list_template_detail_batch);
				}else{
					return "{\"error\"\"科目明细为空，请修改\"}";
				}
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败\"}");
		}
		return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"template_id\":\""+
		entityMap.get("template_id").toString()+"\"}";
	}
	
	/**
	 * @Description 期末处理模板<BR>
	 *              修改期末处理模板
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAccTermendTemplate(Map<String, Object> entityMap) throws DataAccessException {
		try {
			//校验模板名称
			int count = checkName(entityMap, false);
			if (count >0) {
				return "{\"error\":\"模板名称：[" + entityMap.get("template_name").toString() + "]重复.\"}";
			}
			//去掉前后空格
			entityMap.put("template_name", entityMap.get("template_name").toString().trim());
			entityMap.put("summary", entityMap.get("summary").toString().trim());
			//判断是否存在明细科目
			if(entityMap.get("detailData") == null){
				//保存模板主表
				accTermendTemplateMapper.updateAccTermendTemplate(entityMap);
			}else{
				if(entityMap.get("detailData") != null && !"[]".equals(entityMap.get("detailData").toString())){
					//保存明细数据
					List<Map<String,Object>> list_template_detail_batch = new ArrayList<Map<String,Object>>();
					JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
					Iterator it = json.iterator();
					while (it.hasNext()) {
						Map<String,Object> mapDetailVo=new HashMap<String,Object>();
						mapDetailVo.put("group_id", entityMap.get("group_id"));
						mapDetailVo.put("hos_id", entityMap.get("hos_id"));
						mapDetailVo.put("copy_code", entityMap.get("copy_code"));
						mapDetailVo.put("acc_year", entityMap.get("acc_year"));
						mapDetailVo.put("template_id", entityMap.get("template_id"));
						JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
						mapDetailVo.put("subj_code",  jsonObj.get("subj_code"));
						mapDetailVo.put("detail_type",  jsonObj.get("detail_type"));
						list_template_detail_batch.add(mapDetailVo);
					}
					if(entityMap.get("secondData") != null && !"[]".equals(entityMap.get("secondData").toString())){
						//保存明细数据
						JSONArray secondJson = JSONArray.parseArray((String)entityMap.get("secondData"));
						Iterator secondIt = secondJson.iterator();
						while (secondIt.hasNext()) {
							Map<String,Object> mapDetailVo=new HashMap<String,Object>();
							mapDetailVo.put("group_id", entityMap.get("group_id"));
							mapDetailVo.put("hos_id", entityMap.get("hos_id"));
							mapDetailVo.put("copy_code", entityMap.get("copy_code"));
							mapDetailVo.put("acc_year", entityMap.get("acc_year"));
							mapDetailVo.put("template_id", entityMap.get("template_id"));
							JSONObject jsonObj = JSONObject.parseObject(secondIt.next().toString());
							mapDetailVo.put("subj_code",  jsonObj.get("subj_code"));
							mapDetailVo.put("detail_type",  jsonObj.get("detail_type"));
							list_template_detail_batch.add(mapDetailVo);
						}
					}
					//保存模板主表  
					accTermendTemplateMapper.updateAccTermendTemplate(entityMap);
					//删除模板明细数据
					accTermendTemplateDetailMapper.deleteAccTermendTemplateDetail(entityMap);
					//保存模板明细表
					if(list_template_detail_batch.size() > 0){
						accTermendTemplateDetailMapper.addAccTermendTemplateDetail(list_template_detail_batch);
					}
				}else{
					return "{\"error\":\"科目明细为空，请修改\"}";
				}
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败\"}");
		}
		return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"template_id\":\""+entityMap.get("template_id").toString()+"\"}";
	}
	
	/**
	 * @Description 期末处理模板<BR>
	 *              批量删除期末处理模板
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAccTermendTemplate(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			//删除科室比例信息
			accTermendTemplateDeptMapper.deleteBatchAccTermendTemplateDept(entityMap);
			//删除模板明细信息
			accTermendTemplateDetailMapper.deleteBatchAccTermendTemplateDetail(entityMap);
			//删除模板信息
			accTermendTemplateMapper.deleteBatchAccTermendTemplate(entityMap);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败\"}");
		}
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 期末处理模板<BR>
	 *              查询期末处理科目相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccTermendTemplate>
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccTermendTemplateSubj(Map<String, Object> entityMap) throws DataAccessException {
		List<AccTermendTemplate> list = accTermendTemplateMapper.queryAccTermendTemplateSubj(entityMap);
		return ChdJson.toJson(list);
	}

	/**
	 * @Description 期末处理模板<BR>
	 *              查询期末处理凭证查询相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccTermendTemplate>
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccTermendTemplateVouch(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		List<Map<String, Object>> list = accTermendTemplateMapper.queryAccTermendTemplateVouch(entityMap);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	@Override
	public List<Map<String, Object>> queryAccTermendTemplateVouchPrint(Map<String, Object> map) throws DataAccessException{
		List<Map<String, Object>> list = accTermendTemplateMapper.queryAccTermendTemplateVouchPrint(map);
		return JsonListMapUtil.beanToListMap(list);
	}
	
	/**
	 * @Description 期末处理模板<BR>
	 *              校验模板名称是否重复
	 * @param entityMap
	 * @param isAdd  添加:true,修改:false
	 * @return
	 * @throws DataAccessException
	 */ 
	public int checkName(Map<String, Object> entityMap, boolean isAdd) throws DataAccessException{
		int count;
		Map<String,Object> utilMap=new HashMap<String,Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", entityMap.get("copy_code"));
		utilMap.put("acc_year", entityMap.get("acc_year"));
		utilMap.put("field_table", "acc_termend_template");
		utilMap.put("field_key1", "template_name");
		utilMap.put("field_value1", entityMap.get("template_name"));
		utilMap.put("field_key2", "template_type_code");
		utilMap.put("field_value2", entityMap.get("template_type_code"));
		if(isAdd){
			count = sysFunUtilMapper.existsSysCodeNameByAdd(utilMap);
		}else{
			utilMap.put("field_id", "template_id");
			utilMap.put("field_id_value", entityMap.get("template_id"));
			count = sysFunUtilMapper.existsSysCodeNameByUpdate(utilMap);
		}
		return count;
	}
	
	/**
	 * @Description 期末处理模板<BR>
	 *              保存模板明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public void saveDetail(Map<String, Object> entityMap) throws DataAccessException{
		if(entityMap.get("detailData") == null){
			return;
		}
		//删除模板明细数据
		accTermendTemplateDetailMapper.deleteAccTermendTemplateDetail(entityMap);
		//保存明细数据
		JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
		List<Map<String,Object>> list_template_detail_batch = new ArrayList<Map<String,Object>>();
		Iterator it = json.iterator();
		while (it.hasNext()) {
			Map<String,Object> mapDetailVo=new HashMap<String,Object>();
			mapDetailVo.put("group_id", entityMap.get("group_id"));
			mapDetailVo.put("hos_id", entityMap.get("hos_id"));
			mapDetailVo.put("copy_code", entityMap.get("copy_code"));
			mapDetailVo.put("template_id", entityMap.get("template_id"));
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			mapDetailVo.put("subj_code",  jsonObj.get("subj_code"));
			list_template_detail_batch.add(mapDetailVo);
		}
		//保存模板明细表
		accTermendTemplateDetailMapper.addAccTermendTemplateDetail(list_template_detail_batch);
	}

	@Override
	public String deleteAccTermendTemplateDetail(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			accTermendTemplateDetailMapper.deleteBatchAccTermendTemplateDetail(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 根据list中的辅助核算列来过滤并汇总map中的辅助核算
	 * 即：list中含有check3和check7，map中含有check1、check3、check7，则调用方法返回的map中只有check3和check7并且把金额汇总
	 * @param map
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Map<String, Object>> getMapByCheckCol(Map<String, Map<String, Object>> map, List<Map<String, Object>> list) throws DataAccessException {
		Map<String, Map<String, Object>> retMap = new HashMap<String, Map<String,Object>>();
		
		Map<String, String> colMap = new HashMap<String, String>();
		//解析list得科目辅助核算列Map
		String column_check = "";
		for(Map<String, Object> tmpMap : list){
			column_check = tmpMap.get("column_check").toString().toLowerCase();
			//判断是否含变更信息
			if(tmpMap.get("is_sys") != null && "1".equals(tmpMap.get("is_sys").toString())){
				if(tmpMap.get("is_change") != null && "1".equals(tmpMap.get("is_change").toString())){
					//记录包含的辅助核算项
					if(!colMap.containsKey(column_check + "_no")){
						colMap.put(column_check + "_no", column_check + "_no");
					}
				}
				//记录包含的辅助核算项
				if(!colMap.containsKey(column_check + "_id")){
					colMap.put(column_check + "_id", column_check + "_id");
				}
			}else{
				//记录包含的辅助核算项
				if(!colMap.containsKey(column_check)){
					colMap.put(column_check.toString(), column_check.toString());
				}
			}
		}
		
		//清洗Map剔除无用辅助核算
		String keyCol = "";
		Map<String, Object> tmpCheck = null;
		Map<String, Object> checkMap = null;
		for(Map.Entry<String, Map<String, Object>> tmp : map.entrySet()){
			tmpCheck = tmp.getValue();
			keyCol = "";
			for (Map.Entry<String, String> entry : colMap.entrySet()) {
				keyCol += tmpCheck.get(entry.getKey()) == null ? "" : tmpCheck.get(entry.getKey());
			}
			
			if(retMap.containsKey(keyCol)){
				checkMap = retMap.get(keyCol);
				checkMap.put("money", Double.parseDouble(checkMap.get("money").toString()) + Double.parseDouble(tmpCheck.get("money").toString()));
				checkMap.put("money_w", Double.parseDouble(checkMap.get("money_w").toString()) + Double.parseDouble(tmpCheck.get("money_w").toString()));
			}else{
				checkMap = new HashMap<String, Object>();
				//剔除没有的辅助核算
				for (Map.Entry<String, Object> check : tmpCheck.entrySet()) {
					if(check.getKey().startsWith("check") || check.getKey().startsWith("zcheck")){
						if(colMap.containsKey(check.getKey())){
							checkMap.put(check.getKey(), check.getValue());
						}
					}else{
						checkMap.put(check.getKey(), check.getValue());
					}
				}
				
				retMap.put(keyCol, checkMap);
			}
		}
		
		return retMap;
	}
}
