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
import com.chd.hrp.hpm.dao.AphiCostitemConfMapper;
import com.chd.hrp.hpm.dao.AphiCostitemMapper;
import com.chd.hrp.hpm.dao.AphiCostitemSeqMapper;
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.dao.AphiDeptHipMapper;
import com.chd.hrp.hpm.dao.AphiDeptMapper;
import com.chd.hrp.hpm.entity.AphiCostitem;
import com.chd.hrp.hpm.entity.AphiCostitemConf;
import com.chd.hrp.hpm.entity.AphiCostitemSeq;
import com.chd.hrp.hpm.entity.AphiDept;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.entity.AphiDeptHip;
import com.chd.hrp.hpm.service.AphiCostitemConfService;
import com.github.pagehelper.PageInfo;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiCostitemConfService")
public class AphiCostitemConfServiceImpl implements AphiCostitemConfService {

	private static Logger logger = Logger.getLogger(AphiCostitemConfServiceImpl.class);

	@Resource(name = "aphiCostitemConfMapper")
	private final AphiCostitemConfMapper aphiCostitemConfMapper = null;

	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;

	@Resource(name = "aphiCostitemMapper")
	private final AphiCostitemMapper aphiCostitemMapper = null;
	
	@Resource(name = "aphiCostitemSeqMapper")
	private final AphiCostitemSeqMapper aphiCostitemSeqMapper = null;
	
	@Resource(name = "aphiDeptMapper")
	private final AphiDeptMapper aphiDeptMapper = null;
	
	@Resource(name = "aphiDeptHipMapper")
	private final AphiDeptHipMapper aphiDeptHipMapper = null;
	

	/**
	 * 
	 */
	@Override
	public String addCostitemConf(Map<String, Object> entityMap) throws DataAccessException {

		AphiCostitemConf costitemConf = aphiCostitemConfMapper.queryCostitemConfByCode(entityMap);

		if (costitemConf != null) {

			return "{\"msg\":\"重复数据.\",\"state\":\"false\"}";
		}

		int state = aphiCostitemConfMapper.addCostitemConf(entityMap);

		if (state > 0) {

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} else {
			
			return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";

		}
	}

	/**
	 * 
	 */
	@Override
	public String queryCostitemConf(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiCostitemConf> list = aphiCostitemConfMapper.queryCostitemConf(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiCostitemConf> list = aphiCostitemConfMapper.queryCostitemConf(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 
	 */
	@Override
	public AphiCostitemConf queryCostitemConfByCode(Map<String, Object> entityMap) throws DataAccessException {

		return aphiCostitemConfMapper.queryCostitemConfByCode(entityMap);

	}

	/**
	 * 
	 */
	@Override
	public String deleteCostitemConf(Map<String, Object> entityMap, String codes) throws DataAccessException {

		try {

			if (codes != null && !codes.equals("")) {

				String[] code_codeArray = codes.split(",");

				for (String code : code_codeArray) {

					String[] code_array = code.split(";");
					for (int i = 0; i < code_array.length;) {
						entityMap.put("dept_id", code_array[i].toString().split("@")[0]);
						entityMap.put("dept_no", code_array[i].toString().split("@")[1]);
						break;
					}
					entityMap.put("cost_item_code", code_array[1]);

					aphiCostitemConfMapper.deleteCostitemConf(entityMap);
				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败 请联系管理员！ 错误编码deleteIncomeItem\"}");

			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteIncomeItem\"}";

		}
	}

	/**
	 * 
	 */
	@Override
	public String updateCostitemConf(Map<String, Object> entityMap) throws DataAccessException {

		int state = aphiCostitemConfMapper.updateCostitemConf(entityMap);

		if (state > 0) {

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} else {

			return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";

		}

	}

	@Override
	public String createCostitemConf(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		// 清空APHI_COSTITEM_CONF
		aphiCostitemConfMapper.clearCostitemConf(entityMap);

		// 生成数据
		List<AphiDeptDict> allDept = aphiDeptDictMapper.queryPrmDeptDict(entityMap);//queryPrmDept()

		List<AphiCostitem> allCostitem = aphiCostitemMapper.queryCostitem(entityMap);
		try {
			for (int i = 0; i < allDept.size(); i++) {

				AphiDeptDict dept = (AphiDeptDict) allDept.get(i);

				for (int j = 0; j < allCostitem.size(); j++) {

					AphiCostitem costitem = allCostitem.get(j);

					entityMap.put("dept_id", dept.getDept_id());
					entityMap.put("dept_no", dept.getDept_no());
					entityMap.put("cost_item_code", costitem.getCost_item_code());

					entityMap.put("is_acc", "1");

					entityMap.put("is_prim_cost", "1");

					entityMap.put("is_calc_cost", "2");

					aphiCostitemConfMapper.addCostitemConf(entityMap);

				}

			}
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败 数据库异常\",\"state\":\"false\"}");
			//return "{\"msg\":\"生成失败.\",\"state\":\"false\"}";

		}

	}

	@Override
	public String copyCostitemConf(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		// 先清空目标科室
		entityMap.put("dept_id", entityMap.get("dept_id_t"));
		entityMap.put("dept_no", entityMap.get("dept_no_t"));

		aphiCostitemConfMapper.clearCostitemConf(entityMap);

		// 复制源可是到目标科室
		entityMap.put("dept_id", entityMap.get("dept_id_s"));

		List<AphiCostitemConf> list = aphiCostitemConfMapper.queryCostitemConf(entityMap);
		try {

			for (int i = 0; i < list.size(); i++) {

				AphiCostitemConf cc = list.get(i);

				entityMap.put("dept_id", entityMap.get("dept_id_t"));
				entityMap.put("dept_no", entityMap.get("dept_no_t"));
				
				entityMap.put("cost_item_code", cc.getCost_item_code());

				entityMap.put("is_acc", cc.getIs_acc());

				entityMap.put("is_prim_cost", cc.getIs_prim_cost());

				entityMap.put("is_calc_cost", cc.getIs_calc_cost());

				aphiCostitemConfMapper.addCostitemConf(entityMap);

			}

			return "{\"msg\":\"复制完成.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败 数据库异常\",\"state\":\"false\"}");
			//return "{\"msg\":\"复制失败.\",\"state\":\"false\"}";

		}

	}

	@Override
	public String addBatchCostitemConf(List<Map<String, Object>> entityMap) throws DataAccessException {

		int state = aphiCostitemConfMapper.addBatchCostitemConf(entityMap);

		if (state > 0) {

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}

		return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";

	}

	//导入
	@Override
	public String hpmCostitemConfImport(Map<String, Object> map)throws DataAccessException {
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
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("is_stop", "0");// 查询未停用
			entityMap.put("dept_kind_code",map.get("dept_kind_code"));
			
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
			
			//查询支出项目字典List
			List<AphiCostitemSeq> costitemSeqList = aphiCostitemSeqMapper.queryCostitemSeq(entityMap);
			//以键值对的形式存储,用于判断是否存在支出项目
			Map<String,AphiCostitemSeq> costitemSeqMap = new HashMap<String, AphiCostitemSeq>();
			////收入项目List 放入Map 键 cost_item_code 值 AphiCostitemSeq
			for(AphiCostitemSeq costItemSeq:costitemSeqList){
				costitemSeqMap.put(costItemSeq.getCost_item_code(), costItemSeq);
				costitemSeqMap.put(costItemSeq.getCost_iitem_name(), costItemSeq);
			}
			
			//用于存储传的数据值,判断数据是否重复
			Map<String,String> exitMap = new HashMap<String,String>();
			//存储要添加保存的数据List
			List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
			//用于记录重复数据
			StringBuffer err_sb = new StringBuffer();
			
			for(Map<String, List<String>> item:liData){
				for(String st :item.keySet()){
					if(item.get(st).get(1) == null){
						break;
					}
					
					List<String> cost_item_code = item.get("支出项目名称");
					List<String> dept_name = item.get("科室名称");
					List<String> is_acc = item.get("是否参与核算");
					List<String> is_prim_cost = item.get("是否直接成本");
					List<String> is_calc_cost = item.get("是否间接成本");
					
					
					
					if(dept_name.get(1) == null){
					return "{\"warn\":\"科室名称为空！\",\"state\":\"false\",\"row_cell\":\"\"}";
					}else{
						if(deptMap.get(dept_name.get(1)) == null && deptHipMap.get(dept_name.get(1)) == null && deptDictMap.get(dept_name.get(1)) == null){
							return "{\"warn\":\"" + dept_name.get(1) + ",科室名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}
					}
					
					if (cost_item_code.get(1) == null) {
						return "{\"warn\":\"支出项目名称单元格为空！\",\"state\":\"false\",\"row_cell\":\"" + cost_item_code.get(0) + "\"}";
					} else if(Character.isDigit(cost_item_code.get(1).charAt(0))){
						if(costitemSeqMap.get(cost_item_code.get(1)) == null){
							return "{\"warn\":\"支出项目编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + cost_item_code.get(0) + "\"}";
						}
					}else if(costitemSeqMap.get(cost_item_code.get(1)) == null){
						return "{\"warn\":\"支出项目名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + cost_item_code.get(0) + "\"}";
					}
					
					if(is_acc.get(1) == null){
						return "{\"warn\":\"是否参与核算为空！\",\"state\":\"false\",\"row_cell\":\"" + is_acc.get(0) + "\"}";
					}else if(Character.isDigit(is_acc.get(1).charAt(0))){
						is_acc.add(1,(is_acc.get(1).equals("1")?is_acc.get(1).replace("1","是"):is_acc.get(1).replace("0","否")));
					}
					
					if(is_prim_cost.get(1) == null){
						return "{\"warn\":\"是否直接成本为空！\",\"state\":\"false\",\"row_cell\":\"" + is_prim_cost.get(0) + "\"}";
					}else if(Character.isDigit(is_prim_cost.get(1).charAt(0))){
						is_prim_cost.add(1,(is_prim_cost.get(1).equals("1")?is_prim_cost.get(1).replace("1","是"):is_prim_cost.get(1).replace("0","否")));
					}
					
					if(is_calc_cost.get(1) == null){
						return "{\"warn\":\"是否间接成本为空！\",\"state\":\"false\",\"row_cell\":\"" + is_calc_cost.get(0) + "\"}";
					}else if(Character.isDigit(is_calc_cost.get(1).charAt(0))){
						//验证输入的字符串中是否是数字
						is_calc_cost.add(1,(is_calc_cost.get(1).equals("1")?is_calc_cost.get(1).replace("1","是"):is_calc_cost.get(1).replace("0","否")));
					}
					
					
					//以年度+月份+指标编码+科室名称为键值,判断导入数据是否重复
					String key =cost_item_code.get(1) + dept_name.get(1);
					if(exitMap.get(key) != null ){
						err_sb.append(cost_item_code.get(1)+"支出项目," +dept_name.get(1)+"科室名称 ");
					}else{
						exitMap.put(key, key);
					}
					
					//添加数据Map中add到returnList
					Map<String,Object> returnMap = new HashMap<String,Object>();
					returnMap.put("group_id", SessionManager.getGroupId());
					returnMap.put("hos_id", SessionManager.getHosId());
					returnMap.put("copy_code", SessionManager.getCopyCode());
					
					//系统平台科室
					if(deptMap.get(dept_name.get(1)) != null){
						returnMap.put("dept_id",deptMap.get(dept_name.get(1)).getDept_id());
						returnMap.put("dept_no",deptMap.get(dept_name.get(1)).getDept_no());
					}
					
					//其它平台科室
					if(deptHipMap.get(dept_name.get(1)) != null){
						returnMap.put("dept_id",deptHipMap.get(dept_name.get(1)).getDept_id());
						returnMap.put("dept_no",deptHipMap.get(dept_name.get(1)).getDept_no());
					}
					
					//绩效科室
					if(deptDictMap.get(dept_name.get(1)) != null){
						returnMap.put("dept_id",deptDictMap.get(dept_name.get(1)).getDept_id());
						returnMap.put("dept_no",deptDictMap.get(dept_name.get(1)).getDept_no());
					}
					returnMap.put("cost_item_code", cost_item_code.get(1));
					returnMap.put("is_acc", (is_acc.get(1).equals("是")?1:0));
					returnMap.put("is_prim_cost", (is_prim_cost.get(1).equals("是")?1:0));
					returnMap.put("is_calc_cost", (is_calc_cost.get(1).equals("是")?1:0));
					returnList.add(returnMap);
					break;
					
				}
			}
			
			if (err_sb.toString().length() > 0 ) {
				return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}else{
				aphiCostitemConfMapper.addBatchCostitemConf(returnList);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}	
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
}
