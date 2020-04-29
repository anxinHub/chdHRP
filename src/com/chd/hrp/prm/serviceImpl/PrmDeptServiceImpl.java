/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.prm.dao.PrmDeptDictMapper;
import com.chd.hrp.prm.dao.PrmDeptKindMapper;
import com.chd.hrp.prm.dao.PrmDeptMapper;
import com.chd.hrp.prm.dao.PrmDeptNatureMapper;
import com.chd.hrp.prm.entity.PrmDept;
import com.chd.hrp.prm.entity.PrmDeptKind;
import com.chd.hrp.prm.entity.PrmDeptNature;
import com.chd.hrp.prm.service.PrmDeptService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 8801 科室字典表
 * @Table: Prm_DEPT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmDeptService")
public class PrmDeptServiceImpl implements PrmDeptService {

	private static Logger logger = Logger.getLogger(PrmDeptServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmDeptMapper")
	private final PrmDeptMapper prmDeptMapper = null;

	// 引入DAO操作
	@Resource(name = "prmDeptDictMapper")
	private final PrmDeptDictMapper prmDeptDictMapper = null;
	// 引入DAO操作
	@Resource(name = "prmDeptKindMapper")
	private final PrmDeptKindMapper prmDeptKindMapper = null;
	// 引入DAO操作
		@Resource(name = "prmDeptNatureMapper")
		private final PrmDeptNatureMapper prmDeptNatureMapper = null;
	/**
	 * @Description 添加8801 科室字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmDept(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			PrmDept dept = prmDeptMapper.queryPrmDeptByDeptCode(entityMap);
			if(dept != null ){
				return "{\"error\":\"科室编码已存在 \"}";
			}
			
			Integer dept_id = prmDeptMapper.queryPrmDeptNextval();
			entityMap.put("dept_id", dept_id);
			entityMap.put("user_code", SessionManager.getUserCode());
			entityMap.put("create_date", new Date());
			entityMap.put("is_stop", 0);
			entityMap.put("spell_code",StringTool.toPinyinShouZiMu(entityMap.get("dept_name").toString()));
			entityMap.put("wbx_code",StringTool.toWuBi(entityMap.get("dept_name").toString()));
			entityMap.put("note", "新增");
			
			prmDeptMapper.addPrmDept(entityMap);//添加主表数据
			entityMap.put("dept_no", prmDeptDictMapper.queryPrmDeptDictSeqNextval());
			prmDeptDictMapper.addPrmDeptDict(entityMap);//添加变更表数据

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败 \"}");

		}


	}

	/**
	 * @Description 批量添加8801 科室字典表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmDept(List<Map<String, Object>> entityList) throws DataAccessException {
		try {

			prmDeptMapper.addBatchPrmDept(entityList);

			prmDeptDictMapper.addBatchPrmDeptDict(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDept\"}";

		}
	}

	/**
	 * @Description 更新8801 科室字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmDept(Map<String, Object> entityMap) throws DataAccessException {


		try {
			//修改主表
			prmDeptMapper.updatePrmDept(entityMap);
			//停用变更表 现有记录
		/*	prmDeptDictMapper.updatePrmDeptDictIsStopState(entityMap);
			//新增变更表记录
			entityMap.put("dept_no", prmDeptDictMapper.queryPrmDeptDictSeqNextval());
			prmDeptDictMapper.addPrmDeptDict(entityMap);
*/
			
        int save_history = Integer.parseInt(String.valueOf(entityMap.get("save_history")));//是否保留历史记录
			
			if(save_history == 0){//不保留历史记录
				
				int dept_no = prmDeptDictMapper.queryPrmDeptDictMaxNo(entityMap);//查询当前科室最大变更号
				
				entityMap.put("dept_no", dept_no);
				
				prmDeptDictMapper.updatePrmDeptDict(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
				
			}
			
			if(save_history == 1){//保留历史记录
				
				prmDeptDictMapper.updatePrmDeptDictIsStopState(entityMap);
				
				entityMap.put("dept_no", prmDeptDictMapper.queryPrmDeptDictSeqNextval());
				
				prmDeptDictMapper.addPrmDeptDict(entityMap);
			}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException ("{\"error\":\"操作失败 \"}");

		}

	}

	/**
	 * @Description 批量更新8801 科室字典表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmDept(List<Map<String, Object>> entityList) throws DataAccessException {

		List<Map<String, Object>> listDeptforUpdate = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> listDeptDictforUpdate = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> listDeptDictforInsert = new ArrayList<Map<String, Object>>();

		try {
			List<Map<String, Object>> list = entityList;

			for (Map<String, Object> map : list) {

				Map<String, Object> mapDeptforUpdate = map;

				mapDeptforUpdate.put("is_stop", '0');

				listDeptforUpdate.add(mapDeptforUpdate);

				Map<String, Object> mapDeptDictforUpdate = new HashMap<String, Object>();

				mapDeptDictforUpdate.put("group_id", map.get("group_id"));

				mapDeptDictforUpdate.put("hos_id", map.get("hos_id"));

				mapDeptDictforUpdate.put("copy_code", map.get("copy_code"));

				mapDeptDictforUpdate.put("dept_id", map.get("dept_id"));

				mapDeptDictforUpdate.put("is_stop", '1');

				listDeptDictforUpdate.add(mapDeptDictforUpdate);

				Map<String, Object> mapDeptDictforInsert = map;

				mapDeptDictforInsert.put("is_stop", '0');

				listDeptDictforInsert.add(mapDeptDictforInsert);

			}

			prmDeptMapper.updateBatchPrmDept(listDeptforUpdate);

			prmDeptDictMapper.updateBatchPrmDeptDict(listDeptDictforUpdate);

			prmDeptDictMapper.addBatchPrmDeptDict(listDeptDictforInsert);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmDept\"}";

		}
	}

	/**
	 * @Description 删除8801 科室字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmDept(Map<String, Object> entityMap) throws DataAccessException {

		try {

			prmDeptMapper.deletePrmDept(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDept\"}";

		}


	}

	/**
	 * @Description 批量删除8801 科室字典表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmDept(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			//删除非变更表记录
			prmDeptMapper.deleteBatchPrmDept(entityList);

			//修改变更表为停用
			for(int x = 0 ; x < entityList.size() ; x++){
				Map<String,Object> deptMap = entityList.get(x);
				prmDeptDictMapper.updatePrmDeptDictIsStopState(deptMap);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 \"}");

		}
	}

	/**
	 * @Description 查询结果集8801 科室字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmDept(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmDept> list = prmDeptMapper.queryPrmDept(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmDept> list = prmDeptMapper.queryPrmDept(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 查询结果集8801 科室字典表<BR>
	 *              全部 该查询结果关联科室分类字典表、科室性质字典表
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmDept_DeptKind_DeptNature(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmDept> list = prmDeptMapper.queryPrmDept_DeptKind_DeptNature(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmDept> list = prmDeptMapper.queryPrmDept_DeptKind_DeptNature(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象8801 科室字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmDept queryPrmDeptByCode(Map<String, Object> entityMap) throws DataAccessException {

		return prmDeptMapper.queryPrmDeptByCode(entityMap);

	}

	/**
	 * @Description 查询结果集8801 科室字典表<BR>
	 *              全部 该查询结果关联科室分类字典表、科室性质字典表
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	@Override
	public PrmDept queryPrmDeptByCode_DeptKind_DeptNature(Map<String, Object> entityMap) throws DataAccessException {

		return prmDeptMapper.queryPrmDeptByCode_DeptKind_DeptNature(entityMap);

	}

	@Override
	public int queryPrmDeptNextval(Map<String, Object> entityMap) throws DataAccessException {

		return prmDeptMapper.queryPrmDeptNextval();

	}

	@Override
	public PrmDept queryPrmDeptByCode_DeptKind_DeptNatureDeptCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		return prmDeptMapper.queryPrmDeptByCode_DeptKind_DeptNatureDeptCode(entityMap);

	}
	//导入
		@Override
		public String importPrmDept(Map<String, Object> map)throws DataAccessException {
			// TODO Auto-generated method stub
			try {
				
				String columns=map.get("columns").toString();
				JSONArray jsonColumns = JSONObject.parseArray(columns);	
				if(jsonColumns==null || jsonColumns.size()==0){
					return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
				}
				
				String content=map.get("content").toString();
				List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
				if(liData==null || liData.size()==0){
					return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
				}
				
				//new Map查询导入时对应数据信息
				Map<String, Object> entityMap=new HashMap<String,Object>();
				entityMap.put("group_id", SessionManager.getGroupId());
				entityMap.put("hos_id", SessionManager.getHosId());
				entityMap.put("copy_code", SessionManager.getCopyCode());
				
				//1.查询	非变更科室 List
				List<PrmDept> deptList = prmDeptMapper.queryPrmDeptAll(entityMap);
				
				//非变更科室 Map 以键值对的形式存储,用于判断科室是否重复  键:dept_code 值:AphiDept
				Map<String,PrmDept> deptCodeMap = new HashMap<String, PrmDept>();
				//非变更科室 Map 以键值对的形式存储,用于判断科室是否重复  键:dept_name 值:AphiDept
				Map<String,PrmDept> deptNameMap = new HashMap<String, PrmDept>();
				
				for(PrmDept prmDept : deptList){
					deptCodeMap.put(prmDept.getDept_code(), prmDept);
					deptNameMap.put(prmDept.getDept_name(), prmDept);
				}
				
				entityMap.put("is_stop", "0");// 查询未停用
				//2.查询	科室分类 List
				List<PrmDeptKind> deptKindList = prmDeptKindMapper.queryPrmDeptKind(entityMap);
				//键:deptKindCode 值:AphiDeptKind
				Map<String,PrmDeptKind> deptKindCodeMap = new HashMap<String,PrmDeptKind>();
				//键:deptKindName 值:AphiDeptKind
				Map<String,PrmDeptKind> deptKindNameMap = new HashMap<String,PrmDeptKind>();
				
				for(PrmDeptKind prmDeptKind : deptKindList){
					deptKindCodeMap.put(prmDeptKind.getDept_kind_code(), prmDeptKind);
					deptKindNameMap.put(prmDeptKind.getDept_kind_name(), prmDeptKind);
				}
				
				
				//3.查询	科室性质 List 
				List<PrmDeptNature> deptNatureList = prmDeptNatureMapper.queryDeptNature(entityMap);
				//键:deptNatureCode 值:AphiDeptNature
				Map<String,PrmDeptNature> deptNatureCodeMap = new HashMap<String,PrmDeptNature>();
				//键:deptNatureName 值:AphiDeptNature
				Map<String,PrmDeptNature> deptNatureNameMap = new HashMap<String,PrmDeptNature>();
				
				for(PrmDeptNature prmDeptNature : deptNatureList){
					deptNatureCodeMap.put(prmDeptNature.getNature_code(), prmDeptNature);
					deptNatureNameMap.put(prmDeptNature.getNature_name(), prmDeptNature);
				}
				
				
				//用于存储传的数据值,判断所导入的数据是否重复
				Map<String,String> exitMap = new HashMap<String,String>();
				
				//科室保存List
				List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
				
				//变更科室保存List
				List<Map<String,Object>> addDictList = new ArrayList<Map<String,Object>>();
				
				//用于记录返回的错误信息
				StringBuffer err_sb = new StringBuffer();
				
				for(Map<String, List<String>> item:liData){
					List<String> dept_code = item.get("科室编码");
					List<String> dept_name = item.get("科室名称");
					List<String> dept_note = item.get("科室描述");
					List<String> dept_kind_code = item.get("科室分类");
					List<String> nature_code = item.get("科室性质");
					List<String> is_avg = item.get("是否参与人均奖金");
						
					if (dept_code.get(1) == null) {
						return "{\"warn\":\"科室编码为空！\",\"state\":\"false\",\"row_cell\":\"" + dept_code.get(0) + "\"}";
					}else{
						if(deptCodeMap.get(dept_code.get(1)) != null){
							return "{\"warn\":\"科室编码已存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_code.get(0) + "\"}";
						}
					}

					if(dept_name.get(1) == null){
						return "{\"warn\":\"科室名称为空！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
					}else{
						if(deptNameMap.get(dept_name.get(1)) != null){
							return "{\"warn\":\"科室名称已存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}
					}
					
					if(dept_kind_code.get(1) == null){
						return "{\"warn\":\"科室分类为空！\",\"state\":\"false\",\"row_cell\":\"" + dept_kind_code.get(0) + "\"}";
					}else{
						if(deptKindCodeMap.get(dept_kind_code.get(1)) == null && deptKindNameMap.get(dept_kind_code.get(1)) == null){
							return "{\"warn\":\"科室分类不存在,请输入正确的科室分类编码或名称\",\"state\":\"false\",\"row_cell\":\"" + dept_kind_code.get(0) + "\"}";
						}
					}
					
					if(nature_code.get(1) == null){
						return "{\"warn\":\"科室性质为空！\",\"state\":\"false\",\"row_cell\":\"" + nature_code.get(0) + "\"}";
					}else{
						if(deptNatureCodeMap.get(nature_code.get(1)) == null && deptNatureNameMap.get(nature_code.get(1)) == null){
							return "{\"warn\":\"科室性质不存在,请输入正确的科室性质编码或名称！\",\"state\":\"false\",\"row_cell\":\"" + nature_code.get(0) + "\"}";
						}
					}
					
					if(is_avg.get(1) != null){
						if(!"是".equals(is_avg.get(1)) && !"否".equals(is_avg.get(1)) && !"0".equals(is_avg.get(1)) && !"1".equals(is_avg.get(1))){
							return "{\"warn\":\"是否参与人均奖金值错误 \",\"state\":\"false\",\"row_cell\":\"" + is_avg.get(0) + "\"}";
						}
					}
					
						
					//判断导入数据是否重复
					String key = dept_code.get(1)+dept_name.get(1);
					
					if(exitMap.get(key) != null ){
						err_sb.append(dept_code.get(1)+"科室编码," + dept_name.get(1)+"科室名称");
					}else{
						exitMap.put(key, key);
					}
						
						
					Integer dept_id = prmDeptMapper.queryPrmDeptNextval();
					//添加数据Map中add到addList
					Map<String,Object> addMap = new HashMap<String,Object>();
					addMap.put("group_id", SessionManager.getGroupId());
					addMap.put("hos_id", SessionManager.getHosId());
					addMap.put("copy_code", SessionManager.getCopyCode());
					addMap.put("dept_id", dept_id);
					addMap.put("dept_code", dept_code.get(1));
					addMap.put("dept_name", dept_name.get(1));
					addMap.put("dept_note", dept_note.get(1) == null ? "" : dept_note.get(1));
					
					if(deptKindCodeMap.get(dept_kind_code.get(1)) != null){
						PrmDeptKind adk = deptKindCodeMap.get(dept_kind_code.get(1));
						addMap.put("dept_kind_code", adk.getDept_kind_code());
					}
					
					if(deptKindNameMap.get(dept_kind_code.get(1)) != null){
						PrmDeptKind adk = deptKindNameMap.get(dept_kind_code.get(1));
						addMap.put("dept_kind_code", adk.getDept_kind_code());
					}
					
					if(deptNatureCodeMap.get(nature_code.get(1)) != null){
						PrmDeptNature adn = deptNatureCodeMap.get(nature_code.get(1));
						addMap.put("nature_code", adn.getNature_code());
					}
					
					if(deptNatureNameMap.get(nature_code.get(1)) != null){
						PrmDeptNature adn = deptNatureNameMap.get(nature_code.get(1));
						addMap.put("nature_code", adn.getNature_code());
					}
					
					if("是".equals(is_avg.get(1)) || "否".equals(is_avg.get(1))){
						addMap.put("is_avg",is_avg.get(1).equals("是")?"1":"0");
					}
					
					if("0".equals(is_avg.get(1)) || "1".equals(is_avg.get(1))){
						addMap.put("is_avg",is_avg.get(1).toString());
					}
					
					addMap.put("is_stop", 0);
					addMap.put("spell_code", StringTool.toPinyinShouZiMu(dept_name.get(1)));
					addMap.put("wbx_code", StringTool.toWuBi(dept_name.get(1)));
					addList.add(addMap);//非变更表List
					
					addMap.put("dept_no", prmDeptDictMapper.queryPrmDeptDictSeqNextval());
					addMap.put("user_code", SessionManager.getUserCode());
					addMap.put("create_date",new Date());
					addMap.put("note", "新增");
					addDictList.add(addMap);
				}
				
				if (err_sb.toString().length() > 0 ) {
					return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
				}
				
				prmDeptMapper.addBatchPrmDept(addList);
				prmDeptDictMapper.addBatchPrmDeptDict(addDictList);
				
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				
				throw new SysException("{\"error\":\"操作失败 \"}");
			}
		}

}
