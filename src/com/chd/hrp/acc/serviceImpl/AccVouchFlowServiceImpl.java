/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccVouchFlowMapper;
import com.chd.hrp.acc.service.AccVouchFlowService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 凭证制单流程<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accVouchFlowService")
public class AccVouchFlowServiceImpl implements AccVouchFlowService {

	private static Logger logger = Logger.getLogger(AccVouchFlowServiceImpl.class);
	
	@Resource(name = "accVouchFlowMapper")
	private final AccVouchFlowMapper accVouchFlowMapper = null;
	
	//列表查询
	@Override
	public String queryAccVouchFlow(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		/**
		 * 三套组合（node_id: 1=制单, 2=审核, 3=签字, 99=记账）
		 * 1. {1、凭证制单   2、凭证审核    3、凭证记账}
		 * 2. {1、凭证制单(会计人员填制新的凭证)  2、出纳签字(出纳人员对凭证进行签字)   3、凭证审核(由主管会计对凭证进行审核)  4、凭证记账(将凭证写入账表)}
		 * 3. {1、凭证制单  2、凭证审核   3、出纳签字  4、凭证记账}
		 * */
		int[][] nodes = {{1, 3, 4}, {1, 3, 2, 4}, {1, 2, 3, 4}}; //对应initList.get(** - 1)
		//获取制单流程确定选择项
		List<Map<String, Object>> list = accVouchFlowMapper.queryList(entityMap);
		String check_node = "";
		for(Map<String, Object> map : list){
			check_node += map.get("node_id").toString();
		}
		/**
		 * [{"Rows":[
		 *   {"id":"1","name":"凭证制单","note":"会计人员填制新的凭证"},
		 *   {"id":"3","name":"凭证审核","note":"由主管会计对凭证进行审核"},
		 *   {"id":"99","name":"凭证记账","note":"将凭证写入账表"}
		 *  ],
		 *  "is_check":1},{..."
		 */
		
		//查询初始化数据
		List<Map<String, Object>> initList = accVouchFlowMapper.queryInitData(entityMap);
		Map<String, Object> initMap = null;
		String node = "";
		StringBuilder retJson = new StringBuilder();
		retJson.append("[");
		for(int i = 0; i < nodes.length; i++){
			node = "";
			if(i > 0){
				retJson.append(",");
			}
			retJson.append("{\"Row\": [");
			for(int j = 0; j < nodes[i].length; j++){
				initMap = initList.get(nodes[i][j] - 1);
				node += initMap.get("node_id").toString();
				if(j > 0){
					retJson.append(",");
				}
				retJson.append("{\"node_id\":\"").append(initMap.get("node_id").toString()).append("\",");
				retJson.append("\"node_name\":\"").append(initMap.get("node_name").toString()).append("\",");
				retJson.append("\"parent_node_id\":\"").append(initMap.get("parent_node_id") == null ? "" : initMap.get("parent_node_id").toString()).append("\",");
				retJson.append("\"note\":\"").append(initMap.get("note").toString()).append("\"}");
			}
			retJson.append("], \"is_check\":\"").append(node.equals(check_node) ? 1 : 0).append("\"}");
		}
		retJson.append("]");
		
		return retJson.toString();
	}

	//保存
	@Override
	public Map<String, Object> saveAccVouchFlow(Map<String,Object> entityMap)throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
			//校验是否含未记账凭证
			int flag = accVouchFlowMapper.existsAcc(entityMap);
			if(flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "存在未记账凭证不能修改");
				return retMap;
			}
			
			//解析明细数据
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("allData")));
			Iterator iterator = json.iterator();
			JSONObject jsonObj = null;
			Map<String, Object> map = null;
			int parent_node_id = 0, sort_code = 1;
			while(iterator.hasNext()){
				jsonObj = JSONObject.parseObject(iterator.next().toString());
				
				map = new HashMap<String, Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("node_id", jsonObj.getString("node_id"));
				map.put("parent_node_id", parent_node_id);
				map.put("node_name", jsonObj.getString("node_name"));
				map.put("note", jsonObj.getString("note"));
				map.put("sort_code", sort_code);
				
				parent_node_id = jsonObj.getInteger("node_id");
				sort_code ++;
				
				list.add(map);
			}
			
			//删除明细
			accVouchFlowMapper.deleteAccVouchFlow(entityMap);
			//添加
			accVouchFlowMapper.addAccVouchFlow(list);

			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			//throw new SysException("操作失败");
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}
}
