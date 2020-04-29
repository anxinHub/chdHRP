package com.chd.hrp.hpm.serviceImpl;

import java.util.ArrayList;
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
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.dao.AphiDeptHipMapper;
import com.chd.hrp.hpm.dao.AphiDeptMapper;
import com.chd.hrp.hpm.dao.AphiWorkitemConfMapper;
import com.chd.hrp.hpm.dao.AphiWorkitemMapper;
import com.chd.hrp.hpm.dao.AphiWorkitemSeqMapper;
import com.chd.hrp.hpm.entity.AphiDept;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.entity.AphiDeptHip;
import com.chd.hrp.hpm.entity.AphiWorkitem;
import com.chd.hrp.hpm.entity.AphiWorkitemConf;
import com.chd.hrp.hpm.service.AphiWorkitemConfService;
import com.github.pagehelper.PageInfo;

/**
 * alfred
 */

@Service("aphiWorkitemConfService")
public class AphiWorkitemConfServiceImpl implements AphiWorkitemConfService {

	private static Logger logger = Logger.getLogger(AphiWorkitemConfServiceImpl.class);

	@Resource(name = "aphiWorkitemConfMapper")
	private final AphiWorkitemConfMapper aphiWorkitemConfMapper = null;

	@Resource(name = "aphiDeptMapper")
	private final AphiDeptMapper aphiDeptMapper = null;

	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;

	@Resource(name = "aphiWorkitemMapper")
	private final AphiWorkitemMapper aphiWorkitemMapper = null;

	@Resource(name = "aphiWorkitemSeqMapper")
	private final AphiWorkitemSeqMapper aphiWorkitemSeqMapper = null;
	
	@Resource(name = "aphiDeptHipMapper")
	private final AphiDeptHipMapper aphiDeptHipMapper = null;

	/**
	 * 
	 */
	@Override
	public String addWorkitemConf(Map<String, Object> entityMap) throws DataAccessException {

		AphiWorkitemConf wkc = queryWorkitemConfByCode(entityMap);

		if (wkc != null) {

			if (wkc.getDept_id() != null) {

				return "{\"error\":\"科室编码：" + entityMap.get("dept_code").toString() + "重复.\"}";

			}

			if (wkc.getWork_item_code() != null) {

				return "{\"error\":\"工作量指标编码：" + entityMap.get("work_item_code").toString() + "重复.\"}";

			}

		}

		try {

			aphiWorkitemConfMapper.addWorkitemConf(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addWorkitemConf\"}";

		}
	}

	/**
	 * 
	 */
	@Override
	public String queryWorkitemConf(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiWorkitemConf> list = aphiWorkitemConfMapper.queryWorkitemConf(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiWorkitemConf> list = aphiWorkitemConfMapper.queryWorkitemConf(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 
	 */
	@Override
	public AphiWorkitemConf queryWorkitemConfByCode(Map<String, Object> entityMap) throws DataAccessException {

		return aphiWorkitemConfMapper.queryWorkitemConfByCode(entityMap);

	}

	/**
	 * 
	 */
	@Override
	public String deleteWorkitemConf(Map<String, Object> mapVo, String codes) throws DataAccessException {

		try {

			// String income_item_codes =
			// mapVo.get("income_item_codes").toString();

			if (codes != null && !codes.equals("")) {

				String[] codes_array = codes.split(",");

				for (String code : codes_array) {

					String[] code_array = code.split(";");

					mapVo.put("dept_id", code_array[0]);

					mapVo.put("dept_no", code_array[1]);

					mapVo.put("work_item_code", code_array[2]);

					aphiWorkitemConfMapper.deleteWorkitemConf(mapVo);
				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteWorkitemConf\"}";

		}
	}

	/**
	 * 
	 */
	@Override
	public String updateWorkitemConf(Map<String, Object> entityMap) throws DataAccessException {

		try {

			aphiWorkitemConfMapper.updateWorkitemConf(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateWorkitemConf\"}";

		}
	}

	@Override
	public String createWorkitemConf(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		// 清空aphi_workitem_conf
		aphiWorkitemConfMapper.clearWorkitemConf(entityMap);

		// 生成数据
		List<AphiDeptDict> allDept = aphiDeptDictMapper.queryPrmDeptDict(entityMap);

		List<AphiWorkitem> allWorkitem = aphiWorkitemMapper.queryWorkitem(entityMap);
		try {
			for (int i = 0; i < allDept.size(); i++) {

				AphiDeptDict dept = allDept.get(i);

				for (int j = 0; j < allWorkitem.size(); j++) {

					AphiWorkitem workitem = allWorkitem.get(j);

					entityMap.put("dept_id", dept.getDept_id());

					entityMap.put("dept_no", dept.getDept_no());

					entityMap.put("work_item_code", workitem.getWork_item_code());

					entityMap.put("work_standard", 0);

					entityMap.put("is_acc", "1");

					aphiWorkitemConfMapper.addWorkitemConf(entityMap);

				}

			}
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"msg\":\"生成失败.\",\"state\":\"false\"}");

		}

	}

	@Override
	public String copyWorkitemConf(Map<String, Object> entityMap) throws DataAccessException {

		// 先清空目标科室
		entityMap.put("dept_id", entityMap.get("dept_id_t"));// 目标科室id
		entityMap.put("dept_no", entityMap.get("dept_no_t"));// 目标科室no

		aphiWorkitemConfMapper.clearWorkitemConf(entityMap);

		// 复制源可是到目标科室
		entityMap.put("dept_id", entityMap.get("dept_id_s"));// 源科室id
		entityMap.put("dept_no", entityMap.get("dept_no_s"));// 源科室no

		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		List<AphiWorkitemConf> list = aphiWorkitemConfMapper.queryWorkitemConf(entityMap);
		try {

			for (int i = 0; i < list.size(); i++) {

				AphiWorkitemConf wf = list.get(i);

				entityMap.put("dept_id", entityMap.get("dept_id_t"));
				entityMap.put("dept_no", entityMap.get("dept_no_t"));

				entityMap.put("work_item_code", wf.getWork_item_code());
				entityMap.put("work_standard", 0);

				entityMap.put("is_acc", wf.getIs_acc());

				aphiWorkitemConfMapper.addWorkitemConf(entityMap);
				;

			}

			return "{\"msg\":\"复制完成.\",\"state\":\"true\"}";

		} catch (Exception e) {

			return "{\"msg\":\"复制失败.\",\"state\":\"false\"}";

		}

	}

	@Override
	public String addBatchWorkitemConf(List<Map<String, Object>> entityMap) throws DataAccessException {
		int state = aphiWorkitemMapper.addBatchWorkitem(entityMap);
		if (state > 0) {
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		}
		return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
	}

	@Override
	public String importHpmWorkitemConf(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			String columns=map.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);	
			if(jsonColumns==null || jsonColumns.size()==0){
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}
			
			String content = map.get("content").toString();
			List<Map<String, List<String>>> liData = SpreadTableJSUtil.toListMap(content, 1);
			if (liData == null || liData.size() == 0) {
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}

			// new Map查询导入时对应数据信息
			Map<String, Object> entityMap = new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("is_stop", "0");// 查询未停用
			entityMap.put("dept_kind_code",map.get("dept_kind_code"));

			// 查询工作量字典List
			List<AphiWorkitem> workitemList = aphiWorkitemMapper.queryWorkitem(entityMap);
			// 以键值对的形式存储,用于判断是否存在工作量
			Map<String, AphiWorkitem> workitemMap = new HashMap<String, AphiWorkitem>();
			// 工作量List 放入Map 键 work_item_code 值 AphiWorkitemSeq
			for (AphiWorkitem workitem : workitemList) {
				workitemMap.put(workitem.getWork_item_code(), workitem);
				workitemMap.put(workitem.getWork_item_name(), workitem);
			}
			
			//判断表头中工作量指标是否存在
			StringBuffer sb = new StringBuffer();//提示信息:用于存储表头中不存在的工作量指标
			Map<String,String> itemColumnMap = new HashMap<String,String>();//用于存储表头中的工作量指标,作为遍历数据时取指标值
			
			for(Map<String,List<String>> item : liData ){
				for(Map.Entry<String, List<String>> entry : item.entrySet()){
					String key = entry.getKey();
					
					if("科室名称".equals(key) || "是否参与核算".equals(key)){
						continue;
					}
					
					if(key != null){
						itemColumnMap.put(key, key);
					}
					
					if(workitemMap.get(key) == null){
						sb.append("工作量指标" + key + "不存在,");
					}
				}
				break;//判断指标表头是否存在,只遍历一次
			}
			
			if(itemColumnMap == null || "".equals(itemColumnMap)){
				return "{\"error\":\"表头中未存在工作量指标\",\"state\":\"false\"}";
			}
			
			//表头含有不存在工作量指标 返回提示
			if(sb.length() > 0){
				return "{\"error\":\"" + sb.deleteCharAt(sb.length() -1).toString()+ "\",\"state\":\"false\"}";
			}
			
			//查询所有数据
			List<AphiWorkitemConf> awicList = aphiWorkitemConfMapper.queryWorkitemConfAll(entityMap);
			//awicMap用于判断数据是否存在
			Map<String,AphiWorkitemConf> isExitMap = new HashMap<String,AphiWorkitemConf>();
			for(AphiWorkitemConf awic: awicList){
				String key = awic.getDept_id() + "|" + awic.getDept_no() + "|" + awic.getWork_item_code();
				isExitMap.put(key, awic);
			}
			
			
			//查询所有绩效科室
			List<AphiDeptDict> deptDictList = aphiDeptDictMapper.queryPrmDeptDict(entityMap);
			//用于存储查询deptList中的AphiDept对象,以键值对的形式存储,用于判断科室是否存在
			Map<String,AphiDeptDict> deptDictMap = new HashMap<String,AphiDeptDict>();
			//将科室List存入Map   键:dept_name 值:AphiDeptDict
			for(AphiDeptDict deptDict : deptDictList){
				deptDictMap.put(deptDict.getDept_name(), deptDict);
				deptDictMap.put(deptDict.getDept_code(), deptDict);
			}
			
			//按科室与系统平台对应关系查询科室  List
			List<AphiDept> deptList = aphiDeptMapper.queryAphiDeptRelaByMaping(entityMap);
			//用于存储查询deptList中的AphiDept对象,以键值对的形式存储,用于判断科室是否存在
			Map<String,AphiDept> deptMap = new HashMap<String,AphiDept>();
			//将科室List存入Map   键:dept_name 值:AphiDept
			for(AphiDept dept : deptList){
				deptMap.put(dept.getDept_name(), dept);
				deptMap.put(dept.getDept_code(), dept);
			}
			
			
			//按科室与其它平台对应关系查询科室  List
			List<AphiDeptHip> deptHipList = aphiDeptHipMapper.queryAphiDeptRelaByMapingHip(entityMap);			
			//用于存储查询deptHipList中的AphiDeptHip对象,以键值对的形式存储,用于判断科室是否存在
			Map<String,AphiDeptHip> deptHipMap = new HashMap<String,AphiDeptHip>();
			//将科室List存入Map   键:dept_name 值:AphiDeptHip
			for(AphiDeptHip deptHip : deptHipList){
				deptHipMap.put(deptHip.getDept_name(), deptHip);
				deptHipMap.put(deptHip.getDept_code(), deptHip);
			}
			

			// 用于存储传的数据值,判断数据是否重复
			Map<String, String> exitMap = new HashMap<String, String>();
			// 存储要添加保存的数据List
			List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
			// 用于记录重复数据
			StringBuffer err_sb = new StringBuffer();
			
			// 用于记录已经存在的数据
			StringBuffer err_exitData = new StringBuffer();
			
			// 遍历导入数据-遍历单一导入数据,得到名称-判断是否为空-跳出循环
			for (Map<String, List<String>> item : liData) {
				if(item == null){
					continue;
				}
				
				for(Map.Entry<String, String> entry:itemColumnMap.entrySet()){
					
					List<String> dept_name = item.get("科室名称");
					List<String> is_acc = item.get("是否参与核算");
					List<String> work_item_code = item.get(entry.getKey());
					
					if(dept_name == null || is_acc == null || work_item_code == null){
						continue;
					}
					
					
					if(dept_name.get(1) == null){
						return "{\"warn\":\"科室名称为空！\",\"state\":\"false\",\"row_cell\":\"\"}";
					}else{
						if(deptMap.get(dept_name.get(1)) == null && deptHipMap.get(dept_name.get(1)) == null && deptDictMap.get(dept_name.get(1)) == null){
							return "{\"warn\":\"" + dept_name.get(1) + ",科室名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}
					}
					
					if (work_item_code.get(1) == null) {
						return "{\"warn\":\""+entry.getKey()+"工作量指标值为空！\",\"state\":\"false\",\"row_cell\":\"" + work_item_code.get(0) + "\"}";
					}else{
						try {
							Double.parseDouble(work_item_code.get(1));
						} catch (Exception e) {
							// TODO: handle exception
							return "{\"warn\":\"工作量指标值错误,请重新输入！\",\"state\":\"false\",\"row_cell\":\"" + work_item_code.get(0) + "\"}";
						}
					} 
					
					if (is_acc.get(1) == null) {
						return "{\"warn\":\"是否参与核算为空！\",\"state\":\"false\",\"row_cell\":\"" + is_acc.get(0) + "\"}";
					} else if (Character.isDigit(is_acc.get(1).charAt(0))) {
						is_acc.add(1, (is_acc.get(1).equals("1") ? is_acc.get(1).replace("1", "是") : is_acc.get(1).replace("0", "否")));
					}
					
					// 添加数据 addMap
					Map<String, Object> addMap = new HashMap<String, Object>();
					addMap.put("group_id", SessionManager.getGroupId());
					addMap.put("hos_id", SessionManager.getHosId());
					addMap.put("copy_code", SessionManager.getCopyCode());
					//系统平台科室
					if(deptMap.get(dept_name.get(1)) != null){
						addMap.put("dept_id",deptMap.get(dept_name.get(1)).getDept_id());
						addMap.put("dept_no",deptMap.get(dept_name.get(1)).getDept_no());
					}
					
					//其它平台科室
					if(deptHipMap.get(dept_name.get(1)) != null){
						addMap.put("dept_id",deptHipMap.get(dept_name.get(1)).getDept_id());
						addMap.put("dept_no",deptHipMap.get(dept_name.get(1)).getDept_no());
					}
					
					//绩效科室
					if(deptDictMap.get(dept_name.get(1)) != null){
						addMap.put("dept_id",deptDictMap.get(dept_name.get(1)).getDept_id());
						addMap.put("dept_no",deptDictMap.get(dept_name.get(1)).getDept_no());
					}
					addMap.put("work_item_code", workitemMap.get(entry.getKey()).getWork_item_code());
					addMap.put("is_acc", (is_acc.get(1).equals("是") ? 1 : 0));
					addMap.put("work_standard", work_item_code.get(1));
					
					// 以工作量指标|科室 为键值,判断导入数据是否重复
					String key = addMap.get("dept_id") + "|" + addMap.get("dept_no") + "|" +work_item_code.get(1);
					if (exitMap.get(key) != null) {//数据重复
						err_sb.append(dept_name.get(1) + "科室名称 " + entry.getKey() + "工作量指标" );
					} else {
						exitMap.put(key, key);
					}
					
					if(isExitMap.get(key) != null){//数据已经存在
						err_exitData.append(dept_name.get(1) + "科室名称 " + entry.getKey() + "工作量指标" );
					}
					
					addList.add(addMap);
					
				}
			}
			
			if(err_exitData.toString().length() > 0){
				return "{\"warn\":\"以下数据【" + err_exitData.toString() + "】数据已经存在！\",\"state\":\"false\"}";
			}

			if (err_sb.toString().length() > 0) {
				return "{\"warn\":\"以下数据【" + err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			} else {
				aphiWorkitemConfMapper.addBatchWorkitemConf(addList);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"msg\":\"操作失败.\",\"state\":\"error\"}");
		}
	}
}
