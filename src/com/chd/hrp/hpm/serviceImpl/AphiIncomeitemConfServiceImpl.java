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
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.dao.AphiDeptHipMapper;
import com.chd.hrp.hpm.dao.AphiDeptMapper;
import com.chd.hrp.hpm.dao.AphiIncomeItemMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemConfMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemSeqMapper;
import com.chd.hrp.hpm.entity.AphiDept;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.entity.AphiDeptHip;
import com.chd.hrp.hpm.entity.AphiIncomeItem;
import com.chd.hrp.hpm.entity.AphiIncomeitemConf;
import com.chd.hrp.hpm.entity.AphiIncomeitemSeq;
import com.chd.hrp.hpm.entity.AphiWorkitemData;
import com.chd.hrp.hpm.service.AphiIncomeitemConfService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiIncomeitemConfService")
public class AphiIncomeitemConfServiceImpl implements AphiIncomeitemConfService {

	private static Logger logger = Logger.getLogger(AphiIncomeitemConfServiceImpl.class);

	@Resource(name = "aphiIncomeitemConfMapper")
	private final AphiIncomeitemConfMapper aphiIncomeitemConfMapper = null;

	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;

	@Resource(name = "aphiIncomeItemMapper")
	private final AphiIncomeItemMapper aphiIncomeItemMapper = null;
	
	@Resource(name = "aphiIncomeitemSeqMapper")
	private final AphiIncomeitemSeqMapper aphiIncomeitemSeqMapper = null;
	
	@Resource(name = "aphiDeptMapper")
	private final AphiDeptMapper aphiDeptMapper = null;
	
	@Resource(name = "aphiDeptHipMapper")
	private final AphiDeptHipMapper aphiDeptHipMapper = null;

	/**
	 * 
	 */
	@Override
	public String addIncomeitemConf(Map<String, Object> entityMap) throws DataAccessException {

		try {

			AphiIncomeitemConf incomeitemConf = aphiIncomeitemConfMapper.queryIncomeitemConfByCode(entityMap);

			if (incomeitemConf != null) {

				return "{\"msg\":\"数据编码已经存在.\",\"state\":\"false\"}";
			}

			aphiIncomeitemConfMapper.addIncomeitemConf(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败 数据库异常\",\"state\":\"false\"}");
			//return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";

		}

	}

	/**
	 * 
	 */
	@Override
	public String queryIncomeitemConf(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiIncomeitemConf> list = aphiIncomeitemConfMapper.queryIncomeitemConf(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiIncomeitemConf> list = aphiIncomeitemConfMapper.queryIncomeitemConf(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	/**
	 * 
	 */
	@Override
	public AphiIncomeitemConf queryIncomeitemConfByCode(Map<String, Object> entityMap) throws DataAccessException {

		return aphiIncomeitemConfMapper.queryIncomeitemConfByCode(entityMap);

	}

	/**
	 * 
	 */
	@Override
	public String deleteIncomeitemConf(Map<String, Object> entityMap, String codes) throws DataAccessException {

		try {

			if (codes != null && !codes.equals("")) {

				String[] code_codeArray = codes.split(",");

				for (String code : code_codeArray) {

					entityMap.put("dept_id", code.toString().split("@")[0]);
					entityMap.put("dept_no", code.toString().split("@")[1]);

					aphiIncomeitemConfMapper.deleteIncomeitemConf(entityMap);
				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteDeptBalancePercConf\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteDeptBalancePercConf\"}";

		}

	}

	/**
	 * 
	 */
	@Override
	public String updateIncomeitemConf(Map<String, Object> entityMap) throws DataAccessException {

		int state = aphiIncomeitemConfMapper.updateIncomeitemConf(entityMap);

		if (state > 0) {

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} else {

			return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";

		}
	}

	@Override
	public String createIncomeitemConf(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		// 清空APHI_INCOMEITEM_CONF
		aphiIncomeitemConfMapper.clearIncomeitemConf(entityMap);

		entityMap.put("is_stop", "0");
		// 生成数据
		List<AphiDeptDict> allDept = aphiDeptDictMapper.queryPrmDeptDict(entityMap);

		List<AphiIncomeItem> allIncomeItem = aphiIncomeItemMapper.queryIncomeItem(entityMap);
		try {
			for (int i = 0; i < allDept.size(); i++) {

				AphiDeptDict dept = (AphiDeptDict) allDept.get(i);

				for (int j = 0; j < allIncomeItem.size(); j++) {

					AphiIncomeItem incomeItem = allIncomeItem.get(j);

					entityMap.put("dept_id", dept.getDept_id());
					
					entityMap.put("dept_no", dept.getDept_no());

					entityMap.put("income_item_code", incomeItem.getIncome_item_code());

					entityMap.put("is_acc", "1");
					
					entityMap.put("order_perc", "1");
					
					entityMap.put("perform_perc", "1");

					aphiIncomeitemConfMapper.addIncomeitemConf(entityMap);

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
	public String copyIncomeitemConf(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		// 先清空目标科室
		entityMap.put("dept_id", entityMap.get("dept_id_t"));

		aphiIncomeitemConfMapper.clearIncomeitemConf(entityMap);

		// 复制源可是到目标科室
		entityMap.put("dept_id", entityMap.get("dept_id_s"));

		List<AphiIncomeitemConf> list = aphiIncomeitemConfMapper.queryIncomeitemConf(entityMap);
		try {

			for (int i = 0; i < list.size(); i++) {

				AphiIncomeitemConf iic = list.get(i);

				entityMap.put("dept_id", entityMap.get("dept_id_t"));
				entityMap.put("dept_no", entityMap.get("dept_no_t"));
				entityMap.put("income_item_code", iic.getIncome_item_code());

				entityMap.put("is_acc", iic.getIs_acc());

				entityMap.put("order_perc", iic.getOrder_perc());

				entityMap.put("perform_perc", iic.getPerform_perc());

				aphiIncomeitemConfMapper.addIncomeitemConf(entityMap);

			}

			return "{\"msg\":\"复制完成.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败 数据库异常\",\"state\":\"false\"}");
			//return "{\"msg\":\"复制失败.\",\"state\":\"false\"}";

		}

	}

	@Override
	public String addBatchIncomeitemConf(List<Map<String, Object>> entityMap) throws DataAccessException {
		int state = aphiIncomeitemConfMapper.addBatchIncomeitemConf(entityMap);
		if (state > 0) {
			return "{\"msg\":\"导入完成.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
		}
	}

	@Override
	public String hpmIncomeitemConfImport(Map<String, Object> map)throws DataAccessException {
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
			
			//查询收入项目字典 List
			List<AphiIncomeitemSeq> incomeItemSeqList = aphiIncomeitemSeqMapper.queryIncomeitemSeq(entityMap);
			//以键值对的形式存储,用于判断是否存在收入项目
			Map<String,AphiIncomeitemSeq> incomeItemSeqMap = new HashMap<String, AphiIncomeitemSeq>();
			//收入项目List 放入Map 键 income_item_code 值 AphiIncomeItemSeq
			for(AphiIncomeitemSeq incomeItemSeq :incomeItemSeqList){
				incomeItemSeqMap.put(incomeItemSeq.getIncome_item_code(), incomeItemSeq);
				incomeItemSeqMap.put(incomeItemSeq.getIncome_item_name(), incomeItemSeq);
			}
			
			//查询科室字典 List
			List<AphiDeptDict> deptDictList = aphiDeptDictMapper.queryPrmDeptDict(entityMap);
			//以键值对的形式存储,用于判断科室是否存在
			Map<String,AphiDeptDict> deptDictMap = new HashMap<String, AphiDeptDict>();
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
					
					List<String> income_item_code = item.get("收入项目编码");
					List<String> dept_name = item.get("科室名称");
					List<String> is_acc = item.get("是否参与核算");
					List<String> order_perc = item.get("开单权重");
					List<String> perform_perc = item.get("执行权重");
					
					if(dept_name.get(1) == null){
						return "{\"warn\":\"科室名称单元格为空！\",\"state\":\"false\",\"row_cell\":\"\"}";
					}else{
						if(deptDictMap.get(dept_name.get(1)) == null && deptMap.get(dept_name.get(1)) == null && deptHipMap.get(dept_name.get(1)) == null){
							return "{\"warn\":\"" + dept_name.get(1) + ",科室编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}
					}
					if (income_item_code.get(1) == null) {
						return "{\"warn\":\"收入项目编码单元格为空！\",\"state\":\"false\",\"row_cell\":\"" + income_item_code.get(0) + "\"}";
					} else if(Character.isDigit(income_item_code.get(1).charAt(0))){
						if(incomeItemSeqMap.get(income_item_code.get(1)) == null){
							return "{\"warn\":\"收入项目编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + income_item_code.get(0) + "\"}";
						}
					}else if(incomeItemSeqMap.get(income_item_code.get(1)) == null){
						return "{\"warn\":\"收入项目名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + income_item_code.get(0) + "\"}";
					}
					
					if(order_perc.get(1) == null){
						return "{\"warn\":\"开单权重为空！\",\"state\":\"false\",\"row_cell\":\" " + order_perc.get(0) + "\"}";
					}else{
						try{
							 Double.parseDouble((order_perc.get(1)));
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + order_perc.get(1) + ",开单权重输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + order_perc.get(0) + "\"}";
						  }
					}
					
					if(perform_perc.get(1) == null){
						return "{\"warn\":\"执行权重为空！\",\"state\":\"false\",\"row_cell\":\" " + perform_perc.get(0) + "\"}";
					}else{
						try{
							 Double.parseDouble((perform_perc.get(1)));
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + perform_perc.get(1) + ",执行权重输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + perform_perc.get(0) + "\"}";
						  }
					}
					
					if(is_acc.get(1) == null){
						return "{\"warn\":\"是否参与核算为空！\",\"state\":\"false\",\"row_cell\":\"" + is_acc.get(0) + "\"}";
					}else if(Character.isDigit(is_acc.get(1).charAt(0))){
						is_acc.add(1,(is_acc.get(1).equals("1")?is_acc.get(1).replace("1","是"):is_acc.get(1).replace("0","否")));
					}
					
					
					//以收入项目编码+科室名称为键值,判断导入数据是否重复
					String key = income_item_code.get(1) + dept_name.get(1);
					if(exitMap.get(key) != null ){
						err_sb.append(income_item_code.get(1)+"收入项目," +dept_name.get(1)+"科室名称 ");
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
					
					returnMap.put("income_item_code", income_item_code.get(1));
					returnMap.put("is_acc", (is_acc.get(1).equals("是")?1:0));
					returnMap.put("order_perc", order_perc.get(1));
					returnMap.put("perform_perc", perform_perc.get(1));
					
					returnList.add(returnMap);
					break;
				}
			}
			
			if (err_sb.toString().length() > 0 ) {
				return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}else{
				aphiIncomeitemConfMapper.addBatchIncomeitemConf(returnList);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
}
