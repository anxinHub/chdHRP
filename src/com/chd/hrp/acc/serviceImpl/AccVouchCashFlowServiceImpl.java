/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.AccCashFlowMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.entity.AccCashFlow;
import com.chd.hrp.acc.entity.AccVouch;
import com.chd.hrp.acc.service.AccVouchCashFlowService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 凭证主表<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accVouchCashFlowService")
public class AccVouchCashFlowServiceImpl implements AccVouchCashFlowService {

	private static Logger logger = Logger.getLogger(AccVouchCashFlowServiceImpl.class);

	@Resource(name = "accVouchMapper")
	private final AccVouchMapper accVouchMapper = null;
	
	@Resource(name = "accCashFlowMapper")
	private final AccCashFlowMapper accCashFlowMapper = null;
	
	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch现金流量标注 统计数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccVouchCashFlow(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String, Object>> list = accVouchMapper.queryAccVouchCashFlow(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	//批量标注、单条标注通用方法
	@Override
	public String saveAccVouchCashFlow(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		//分录金额与标注金额不相等！
		Map<String,Object> checkMap =new HashMap<String,Object>();
		checkMap.put("group_id", SessionManager.getGroupId());
		checkMap.put("hos_id", SessionManager.getHosId());
		checkMap.put("copy_code", SessionManager.getCopyCode());
		checkMap.put("vouch_id", entityMap.get(0).get("vouch_id"));
		checkMap.put("vouch_detail_id", entityMap.get(0).get("vouch_detail_id"));
		
		double check_money = 0.0;
		
		for (int i = 0; i < entityMap.size(); i++) {
			
			  check_money= check_money + Double.parseDouble(entityMap.get(i).get("cash_money").toString());
		}
	
		checkMap.put("check_money",check_money);
		 
		int checkcount = accCashFlowMapper.queryCheckByCashFlowExistsMoney(checkMap);
		
		if(checkcount == 1){
			
			return "{\"error\":\"分录金额与标注金额不相等！.\",\"state\":\"true\"}";
		}
		
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("group_id", SessionManager.getGroupId());
		m.put("hos_id", SessionManager.getHosId());
		m.put("copy_code", SessionManager.getCopyCode());
		m.put("vouch_id", entityMap.get(0).get("vouch_id"));
		String[] vouchNo=accCashFlowMapper.queryVouchStateByDetailId(m);
		if(vouchNo!=null && vouchNo.length>0){
			return "{\"error\":\""+ Arrays.toString(vouchNo)+"记账凭证不能取消标注.\",\"state\":\"true\"}";
		}
		
		//004:现金流量与辅助核算一起保存，判断标注的条数与辅助核算的条数是否一致，不一致的话会影响凭证辅助核算页面加载数据
		//该参数启用后，只能单条标注，不能批量标注。
		
		if(MyConfig.getSysPara("004").equals("1")){
			Map<String, Object> paraMap=new HashMap<String, Object>();
			paraMap.put("group_id", SessionManager.getGroupId());
			paraMap.put("hos_id", SessionManager.getHosId());
			paraMap.put("copy_code", SessionManager.getCopyCode());
			paraMap.put("vouch_id", entityMap.get(0).get("vouch_id"));
			paraMap.put("vouch_detail_id", entityMap.get(0).get("vouch_detail_id"));
			int count=accCashFlowMapper.queryVouchCheckCountByDetailId(paraMap);
			if(count>0 && count!=entityMap.size()){
				return "{\"error\":\"该分录的辅助核算条数："+count+"，与标注的条数必须保持一致.\",\"state\":\"false\"}";
			}
		}
		
		int state=accCashFlowMapper.deleteBatchAccCashFlow(entityMap);
		if(state>=0){
			accCashFlowMapper.addBatchAccCashFlow(entityMap);
			return "{\"msg\":\"标注成功.\",\"state\":\"true\"}";
		}else{
			return "{\"error\":\"标注失败\"}";
		}
		
	}

	//批量标注
	@Override
	public Map<String, Object> saveAccVouchCashFlowBatch(Map<String, Object> map) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("create_user", SessionManager.getUserId());
			map.put("create_date", new Date());
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			JSONArray json = JSONArray.parseArray((String)map.get("checkData"));
			Map<String,Object> tmpMap = null;
			JSONObject jsonObj = null;
			Iterator<Object> it = json.iterator();
			while (it.hasNext()) {
				jsonObj = JSONObject.parseObject(it.next().toString());
				tmpMap = new HashMap<String, Object>();
				tmpMap.put("vouch_id", jsonObj.get("vouch_id"));
				tmpMap.put("vouch_detail_id", jsonObj.get("vouch_detail_id"));
				tmpMap.put("cash_id",UUIDLong.absStringUUID());
				
				list.add(tmpMap);
			}
			
			//判断凭证是否已记账
			int num = accCashFlowMapper.existsHaveAccount(map, list);
			if(num > 0){
				retMap.put("state", "false");
				retMap.put("error", "已记账凭证不能标注现金流量！");
				return retMap;
			}
			
			//先删除 后添加
			accCashFlowMapper.deleteAccCashFlowByVouch(map, list);
			accCashFlowMapper.addAccCashFlowByVouch(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "标注成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}
	
	//批量取消标注、单条取消标注通用方法
	@Override
	public String deleteAccVouchCashFlow(List<Map<String, Object>> entityMap) throws DataAccessException {
			
			
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("group_id", SessionManager.getGroupId());
			m.put("hos_id", SessionManager.getHosId());
			m.put("copy_code", SessionManager.getCopyCode());
			m.put("vouch_id",entityMap.get(0).get("vouch_id"));
			String[] vouchNo=accCashFlowMapper.queryVouchStateByDetailId(m);
			if(vouchNo!=null && vouchNo.length>0){
				return "{\"error\":\""+ Arrays.toString(vouchNo)+"记账凭证不能取消标注.\",\"state\":\"true\"}";
			}
			
				
			int state=accCashFlowMapper.deleteBatchAccCashFlow(entityMap);
			if(state>=0){
				return "{\"msg\":\"取消成功.\",\"state\":\"true\"}";
			}else{
				return "{\"error\":\"取消标注失败\"}";
			}
	
	}

	@Override
	public String queryCashFlowSubj(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<AccCashFlow> list = accCashFlowMapper.queryCashFlowSubj(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}

	@Override
	public String updateBatchAccVouchCashFlow(List<Map<String, Object>> entityMap) throws DataAccessException {

		try {

			accVouchMapper.updateBatchAccVouchCashFlow(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchAccVouchCashFlow\"}";

		}
		
	}

	@Override
	public String queryAccCashFlow(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<AccCashFlow> list = new ArrayList<AccCashFlow>();
		
		List<AccCashFlow> qList = accCashFlowMapper.queryAccCashFlowByVouchId(entityMap);
		
		if(qList.size()>0){
			
			list=accCashFlowMapper.queryAccCashFlow(entityMap);
			
		}else{
			
			list=accCashFlowMapper.queryAccCashFlowListByVouchId(entityMap);
		}
		
		return ChdJson.toJson(list);
		
	}
	
	@Override
	public List<AccCashFlow> queryAccCashFlowListByVouchId(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<AccCashFlow> list = accCashFlowMapper.queryAccCashFlowListByVouchId(entityMap);
		
		return list;
	}
	

	@Override
	public List<Map<String, Object>> queryAccVouchCashFlowPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> list = accVouchMapper.queryAccVouchCashFlow(entityMap);
		return list;
	}
}
