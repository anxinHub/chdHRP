/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.acc.dao.AccLederCheckMapper;
import com.chd.hrp.acc.dao.AccLederMapper;
import com.chd.hrp.acc.dao.HrpAccSelectMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjMapper;
import com.chd.hrp.acc.entity.AccLeder;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.service.AccLederService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
import com.chd.hrp.hr.util.StringUtils;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 财务账表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accLederService")
public class AccLederServiceImpl implements AccLederService {

	private static Logger logger = Logger.getLogger(AccLederServiceImpl.class);
	
	@Resource(name = "accLederMapper")
	private final AccLederMapper accLederMapper = null;
	
	@Resource(name = "accLederCheckMapper")
	private final AccLederCheckMapper accLederCheckMapper = null;
	
	@Resource(name = "modStartMapper")
	private final ModStartMapper modStartMapper = null;
    
	@Resource(name = "accSubjMapper")
	private final AccSubjMapper accSubjMapper = null;
	
	@Resource(name = "hrpAccSelectMapper")
	private final HrpAccSelectMapper hrpAccSelectMapper = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	@Override
	public String addAccLeder(Map<String, Object> entityMap) throws DataAccessException {
		
		StringBuffer sql= new StringBuffer();
		
		StringBuffer sqlValue= new StringBuffer();
		
		StringBuffer sqls= new StringBuffer();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Map<String,Object>> list_leder_add = new ArrayList<Map<String,Object>>();
		
		try{
			
			if("1".equals(entityMap.get("is_check").toString()) ){
				
				String [] data = entityMap.get("check_data").toString().split(",");
				
				String [] column = entityMap.get("para").toString().split(";");
				
				if(String.valueOf(entityMap.get("check_data")) != ""){
					
					for(int i=0;i<data.length;i++){

					    sqlValue.setLength(0);
					
					    sql.setLength(0);
					
						String [] res = data[i].split("@");
						
						Map<String, Object> mapDetailVo = new HashMap<String, Object>();
						
						mapDetailVo.put("group_id",entityMap.get("group_id").toString());
						
						mapDetailVo.put("hos_id",entityMap.get("hos_id").toString());
						
						mapDetailVo.put("copy_code",entityMap.get("copy_code").toString());
						
						mapDetailVo.put("acc_year",entityMap.get("acc_year").toString());
						
						mapDetailVo.put("subj_code",entityMap.get("subj_code").toString());
						
						for (int j = 0; j < column.length; j++) {
							
							if("check7".equals(column[j])||"check8".equals(column[j])){
								
								if(!"".equals(res[j])){
									
									sql.append(column[j]+"_id"+",");
									
									if("check7".equals(column[j])){
										
										sqlValue.append(res[j].toString()/*.split("\\.")*/+",");
									
									}else{
										
										sqlValue.append(res[j].toString()+",");
									
									}
								
								}
								
							}else if(column[j].indexOf("zcheck")!=-1){
					            
									if(!"".equals(res[j].toString())){
									
										sql.append(column[j]+",");
										
										sqlValue.append(res[j].toString()+",");
									
									}
								
							}else{
								
								if(!"".equals(res[j].toString())){
									
									sqlValue.append(res[j].toString().split("\\.")[0]+",");
									
									sqlValue.append(res[j].toString().split("\\.")[1]+",");
									
									sql.append(column[j]+"_id"+",");
									
									sql.append(column[j]+"_no"+",");
									
								}
								
							}
							
						}
						
						sqlValue.append(res[4].toString()+",");
							
						sql.append("bal_os,");
							
						
						if("1".equals(entityMap.get("show_sum"))){
							
							sqlValue.append("0,0,");
							
							sql.append("sum_od,sum_oc,");
								
						}else{
							
							if(!"".equals(res[5].toString())){
							
								sqlValue.append(res[5].toString()+",");
								
							}else{
								
								sqlValue.append("0,");
								
							}
							
							sql.append("sum_od,");
							
							if(!"".equals(res[6].toString())){
								
								sqlValue.append(res[6].toString()+",");
								
							}else{
								
								sqlValue.append("0,");
								
							}
							
							sql.append("sum_oc,");
							
						}
						
						sqlValue.append(res[7].toString()+",");
						
						sql.append("end_os,");
						
						mapDetailVo.put("sqlValue",sqlValue.toString().substring(0, sqlValue.length()));
						
						mapDetailVo.put("sql",sql.toString().substring(0, sql.length()));
						
						list_leder_add.add(mapDetailVo);
						
					}
					
					map.put("itemList", list_leder_add);
					
					map.put("sql", sql.substring(0, sql.length()));
					
					
				}
				
				if(!"".equals(entityMap.get("sum_od").toString())&&!"".equals(entityMap.get("sum_oc").toString())&&entityMap.get("sum_od").toString()!=null&&entityMap.get("sum_oc").toString()!=null){
					
					sqls.append("sum_oc,");
					
					sqls.append("sum_od,");
					
				}
				
				//判断凭证是否记账
				Integer count = accLederMapper.querySubjLederCheckCount(entityMap);
				if (count != 0 && count != null) {
					return "{\"error\":\"当前凭证为记账,无法修改!.\",\"state\":\"true\"}";
				}
				
				entityMap.put("sqls", sqls);
				
				accLederMapper.deleteAccLeder(entityMap);
				accLederCheckMapper.deleteAccLederCheck(entityMap);
				
				if(!map.isEmpty()){
					accLederMapper.addAccLeder(entityMap);
					accLederCheckMapper.addBatchAccLederCheck(map);
				}
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
				
			}else{
				
				sqls.append("sum_oc,");
				
				sqls.append("sum_od,");
				
				entityMap.put("sqls", sqls);
				
				accLederMapper.deleteAccLeder(entityMap);
				
				accLederMapper.addAccLeder(entityMap);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
				
			}
			
		}catch (DataAccessException e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccLeder\"}";//JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}
		
	}
	/**
	 * @Description 
	 * 财务账表<BR> 查询AccLeder分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccLeder(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		if(entityMap.get("is_bal_show")!=null && entityMap.get("is_bal_show").toString().equals("1")){
			//无余额不显示
			entityMap.put("is_bal_show"," inner ");
		}else{
			entityMap.put("is_bal_show"," left ");
		}
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = accLederMapper.queryAccLeder(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = accLederMapper.queryAccLeder(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 财务账表<BR> 查询AccLederByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccLeder queryAccLederByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accLederMapper.queryAccLederByCode(entityMap);
		
	}
	
	
	/**
	 * @Description 
	 * 财务账表<BR> 导入AccLeder
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccLeder(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public boolean queryAccLederBySubjCode(Map<String, Object> entityMap)
			throws DataAccessException {
		List<AccLeder> list = accLederMapper.queryAccLederBySubjCode(entityMap);
		if(list.size() == 0 || list == null){
			return true;
		}
		return false;
	}

	@Override
	public  String getGridTitle(Map<String, Object> entityMap) throws DataAccessException {
		
		/*entityMap.put("subj_code", "10020101");
		
		entityMap.put("subj_id", "1707");
		
		entityMap.put("acc_year", "2015");*/
		
		AccSubj accSubj= accSubjMapper.queryAccSubjByCode(entityMap);
		
		StringBuilder json = new StringBuilder();
		
		if(accSubj != null){
			
			json.append("[");
				
				if(!"".equals(accSubj.getCheck1().toString())&&accSubj.getCheck1() !=null){
					json.append("{");
					json.append("\"id\":\"" + accSubj.getCheck1() + "\","
							+ "\"text\":" + "\"" + accSubj.getCheck1_name() + "\"");
					json.append("},");
					
				}
				if(!"".equals(accSubj.getCheck2().toString())&&accSubj.getCheck2() !=null){
					json.append("{");
					json.append("\"id\":\"" + accSubj.getCheck2() + "\","
							+ "\"text\":" + "\"" + accSubj.getCheck2_name()  + "\"");
					json.append("},");
					
				}
				if(!"".equals(accSubj.getCheck3().toString())&&accSubj.getCheck3() !=null){
					json.append("{");
					json.append("\"id\":\"" + accSubj.getCheck3() + "\","
							+ "\"text\":" + "\"" +accSubj.getCheck3_name() + "\"");
					json.append("},");
					
				}
				if(!"".equals(accSubj.getCheck4().toString())&&accSubj.getCheck4() !=null){
					json.append("{");
					json.append("\"id\":\"" + accSubj.getCheck4() + "\","
							+ "\"text\":" + "\"" + accSubj.getCheck4_name() + "\"");
					json.append("},");
					
				}
				
			}
			
			json.setCharAt(json.length() - 1, ']');
		
		return json.toString();
	}

	@Override
	public String queryDeptDict(Map<String, Object> entityMap)
			throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		
		}else{
		
			rowBounds = rowBoundsALL;
		
		}
		
		return JSON.toJSONString(accLederMapper.queryDeptDict(entityMap, rowBounds));
	}

	@Override
	public String queryEmp(Map<String, Object> entityMap)
			throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		
		}else{
		
			rowBounds = rowBoundsALL;
		
		}
		
		return JSON.toJSONString(accLederMapper.queryEmp(entityMap, rowBounds));
	}

	@Override
	public String queryProjDictDict(Map<String, Object> entityMap)
			throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		
		}else{
		
			rowBounds = rowBoundsALL;
		
		}
		
		return JSON.toJSONString(accLederMapper.queryProjDictDict(entityMap, rowBounds));
	}

	@Override
	public String queryStoreDictDict(Map<String, Object> entityMap)
			throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		
		}else{
		
			rowBounds = rowBoundsALL;
		
		}
		
		return JSON.toJSONString(accLederMapper.queryStoreDictDict(entityMap, rowBounds));
	}

	@Override
	public String querySupDictDict(Map<String, Object> entityMap)
			throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		
		}else{
		
			rowBounds = rowBoundsALL;
		
		}
		
		return JSON.toJSONString(accLederMapper.querySupDictDict(entityMap, rowBounds));
	}

	@Override
	public String queryCusDict(Map<String, Object> entityMap)
			throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		
		}else{
		
			rowBounds = rowBoundsALL;
		
		}
		
		return JSON.toJSONString(accLederMapper.queryCusDict(entityMap, rowBounds));
	}

	@Override
	public String querySourceDict(Map<String, Object> entityMap)
			throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		
		}else{
		
			rowBounds = rowBoundsALL;
		
		}
		
		return JSON.toJSONString(accLederMapper.querySourceDict(entityMap, rowBounds));
	}

	@Override
	public String queryCheckItem(Map<String, Object> entityMap)
			throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		
		}else{
		
			rowBounds = rowBoundsALL;
		
		}
	
		return JSON.toJSONString(accLederMapper.queryCheckItem(entityMap, rowBounds));
	}
	
	@Override
	public String queryModByModCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return JSON.toJSONString(accLederMapper.queryModByModCode(entityMap));
		
	}
	@Override
	public String queryGridTitle(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(accLederMapper.queryGridTitle(entityMap));
	}
	@Override
	public String queryAccLederCheckList(Map<String, Object> entityMap)
			throws DataAccessException {
		
		/*SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){*/
			
			List<AccLeder> list = accLederMapper.queryAccLederCheckList(entityMap);
			
			return ChdJson.toJson(list);
		/*}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccLeder> list = accLederMapper.queryAccLederCheckList(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}*/
	}
	//查询试算平衡
	@Override
	public List<AccLeder> queryAccLederIndex(Map<String, Object> entityMap)
			throws DataAccessException {
		return accLederMapper.queryAccLederIndex(entityMap);
		
	}
	@Override
	public Map<String, Object> queryTitle(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return accLederMapper.queryAccCheckItemList(entityMap);
	}
	@Override
	public Map<String, Object> queryAccCheckItem(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return accLederMapper.queryAccCheckItem(entityMap);
	}
	@Override
	public String addBatchAccLeder(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {
			
			    /*Integer accLeder =accLederMapper.queryAccLaderByImp(entityMap);
			
				if(accLeder==0){*/
					
					accLederCheckMapper.deleteAccLederCheck(entityMap);
					
					accLederCheckMapper.addBatchAccLederCheck(entityMap);
					
					accLederMapper.deleteAccLeder(entityMap);
					
					accLederMapper.addBatchAccLederByCheck(entityMap);
					
					return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
					
				/*}
			
			return "{\"msg\":\"该科目存在其他月份的数据,不允许导入.\",\"state\":\"false\"}";*/
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccLeder\"}";//JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}
		
	}
	@Override
	public String addBatchAccLederImport(Map<String,Object> entityMap)
			throws DataAccessException {
		
		try {

		//accLederMapper.addBatchAccLeder(entityMap);
			
			Integer accLeder =accLederMapper.queryAccLaderByImp(entityMap);
			
			if(accLeder ==0){
				/*//科目初始账已经存在，进行更新操作
				accLederMapper.updateAccLeder(entityMap);*/
				accLederMapper.deleteAccLeder(entityMap);
				
				accLederMapper.addAccLeder(entityMap);
				
				return "{\"msg\":\"导入成功.\",\"state\":\"false\"}";
			}
		
		    return "{\"error\":\"该科目存在其他月份数据,不允许导入.\",\"state\":\"error\"}";
		
	} catch (Exception e) {

		logger.error(e.getMessage(), e);

		return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码 addAccLeder\"}";//JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

	}
 }
	/**
	 * 通过科目ID查询科目的初始账是否允许删除或者修改
	 * 返回值:true 表示该科目已经参与业务操作（结账或者结账），不允许删除和修改
	 * 反之，则可以删除和修改
	 * */
	@Override
	public String queryAccLederBySubjId(Map<String, Object> entityMap)
			throws DataAccessException {
		
		List<AccLeder> listSubj= accLederMapper.queryAccLederBySubjCode(entityMap);
		
		if(listSubj.size()>0){
			
			return "false";
		}
		
		return "true";
	}
	@Override
	public String deleteAccLederBySubjId(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {

			accLederCheckMapper.deleteBatchAccLederCheck(entityMap);
			
			accLederMapper.deleteBatchAccLeder(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccLederBySubjId\"}";
		
			}
	}
	
	
	@Override
	public String verifyAccLederIndex(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {

			int res;
			
			if(!"02".equals(entityMap.get("copy_nature").toString())){//非企业单位
				// 1：校验资产-负债=净资产
				
				
				Map<String, Object> mapSubj=accLederMapper.verifyAccLederIndex1(entityMap);
				//float ass_money    =(Float) mapSubj.get("ASS_MONEY");
				
				BigDecimal ass_money = new BigDecimal(Double.parseDouble(mapSubj.get("ASS_MONEY").toString()));
				BigDecimal debt_money = new BigDecimal(Double.parseDouble(mapSubj.get("DEBT_MONEY").toString()));
				BigDecimal netass_money = new BigDecimal(Double.parseDouble(mapSubj.get("NETASS_MONEY").toString()));
				
				res = ass_money.setScale(2, BigDecimal.ROUND_HALF_UP).subtract(debt_money.setScale(2, BigDecimal.ROUND_HALF_UP)).compareTo(netass_money.setScale(2, BigDecimal.ROUND_HALF_UP));
				
				if(res !=0)
				{
					
					return "{\"error\":\"校验不平衡 （资产-负债  不等于  净资产）！\",\"state\":\"error\"}";
						
					
				}
			}else{//企业单位
				//1.校验资产-负债=所有者权益
				Map<String, Object> mapSubjFirm=accLederMapper.verifyAccLederIndexFirm(entityMap);
				
				BigDecimal ass_money = new BigDecimal(Double.parseDouble(mapSubjFirm.get("ASS_MONEY").toString()));
				BigDecimal debt_money = new BigDecimal(Double.parseDouble(mapSubjFirm.get("DEBT_MONEY").toString()));
				BigDecimal netass_money = new BigDecimal(Double.parseDouble(mapSubjFirm.get("NETASS_MONEY").toString()));
				
				res = ass_money.setScale(2, BigDecimal.ROUND_HALF_UP).subtract(debt_money.setScale(2, BigDecimal.ROUND_HALF_UP)).compareTo(netass_money.setScale(2, BigDecimal.ROUND_HALF_UP));
				
				if(res !=0)
				{
					
					return "{\"error\":\"校验不平衡 （资产-负债  不等于  所有者权益）！\",\"state\":\"error\"}";
						
					
				}
			}
			
			// 2：科目借贷相等
			Map<String, Object> mapSubj2=accLederMapper.verifyAccLederIndex2(entityMap);
			BigDecimal debit_money = new BigDecimal(Double.parseDouble(mapSubj2.get("DEBIT_MONEY").toString()));
			BigDecimal credit_money =new BigDecimal(Double.parseDouble(mapSubj2.get("CREDIT_MONEY").toString()));
			
			res = debit_money.setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(credit_money.setScale(2, BigDecimal.ROUND_HALF_UP));
			
			if(res !=0)
			{
				
				return "{\"error\":\"校验不平衡 （科目借贷不相等）！\",\"state\":\"error\"}";
					
				
			}
			
			List<Map<String, Object>> list = accLederMapper.verifyAccLederIndex3(entityMap);
			
			if(list.size()>0)
			{
				
				return "{\"error\":\"校验不平衡 （科目余额和辅助账不一致）！\",\"state\":\"error\"}";
			}
			
			
			return "{\"msg\":\"校验平衡.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"校验失败 数据库异常 请联系管理员! 错误编码  verifyAccLederIndex\"}";
		
			}
	}
	@Override
	public String queryHosInfo(Map<String, Object> entityMap)
			throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		
		}else{
		
			rowBounds = rowBoundsALL;
		
		}
		
		return JSON.toJSONString(accLederMapper.queryHosInfo(entityMap, rowBounds));
	}
	/**
	 * 查询科目所挂辅助核算项表
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryCheckItemTable(Map<String, Object> mapVo)  {
		
		return accLederMapper.queryCheckItemTable(mapVo);
	}
	@Override
	public List<Map<String, Object>> queryAccLederPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		if(entityMap.get("is_bal_show")!=null && entityMap.get("is_bal_show").toString().equals("1")){
			//无余额不显示
			entityMap.put("is_bal_show"," inner ");
		}else{
			entityMap.put("is_bal_show"," left ");
		}
		
		List<Map<String, Object>> list = accLederMapper.queryAccLeder(entityMap);
		
		return list;
	}
	@Override
	public List<Map<String, Object>> queryAccLederIndexPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accLederMapper.queryAccLederIndex1(entityMap);
		return list;
	}
	public String readAccLederFiles(Map<String, Object> mapVo) {
		int successNum = 2;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		Map<String, Object> yesOrNoMap = new HashMap<String, Object>();
		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("group_id", SessionManager.getGroupId());
		groupMap.put("hos_id", SessionManager.getHosId());
		groupMap.put("copy_code", SessionManager.getCopyCode());
		groupMap.put("acc_year", SessionManager.getAcctYear());
		yesOrNoMap.put("借", "0");
		yesOrNoMap.put("贷", "1");
		yesOrNoMap.put("1", "1");
		yesOrNoMap.put("0", "0");
		Map<String,Object> ledMap = new HashMap<String, Object>();
		List<Map<String,Object>> ledList = accLederMapper.queryAccLederByAccYear(mapVo);
			for (Map<String, Object> map : ledList) {
				ledMap.put(map.get("SUBJ_CODE").toString(), map.get("ACC_YEAR"));
			}
		Map<String,Object> sunjMap = new HashMap<String, Object>();
		Map<String,Object> direMap = new HashMap<String, Object>();
		List<Map<String,Object>> subjList = accLederMapper.queryAccSubjByYear(mapVo);
			for (Map<String, Object> map : subjList) {
				sunjMap.put(map.get("SUBJ_CODE").toString(), map.get("SUBJ_NAME"));
				direMap.put(map.get("SUBJ_CODE").toString(), map.get("SUBJ_DIRE"));
			}		
		List<Map<String,Object>> curList = accLederMapper.queryAccCurByYear(mapVo);
			for (Map<String, Object> map : curList) {
				yesOrNoMap.put(map.get("CUR_CODE").toString(), map.get("CUR_NAME"));
				yesOrNoMap.put(map.get("CUR_NAME").toString(), map.get("CUR_NAME"));
			}
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(mapVo);
			for (Map<String, List<String>> map : list) {
				Map<String,Object> saveMap = new HashMap<String, Object>();
				if(ledMap.containsKey(map.get("subj_code").get(1))){
					failureMsg.append("<br/>第"+successNum+"行,科目编码 "+map.get("subj_code").get(1)+" 已存在; ");
					successNum++;
					continue;
				}
				if(map.get("subj_code").get(1) == null){
					failureMsg.append("<br/>第"+successNum+"行,科目编码 为空 ");
					successNum++;
					continue;
				}
				if(map.get("subj_name").get(1) == null){
					failureMsg.append("<br/>第"+successNum+"行,科目编码 为空 ");
					successNum++;
					continue;
				}
				if(!sunjMap.containsKey(map.get("subj_code").get(1))){
					failureMsg.append("<br/>第"+successNum+"行,科目编码 "+map.get("subj_code").get(1)+" 不存在; ");
					successNum++;
					continue;
				}else if(!sunjMap.get(map.get("subj_code").get(1)).equals(map.get("subj_name").get(1))){
					failureMsg.append("<br/>第"+successNum+"行,科目名称 "+map.get("subj_name").get(1)+"与科目编码不匹配;");
					successNum++;
					continue;
				}
				if(!yesOrNoMap.containsKey(map.get("subj_dire").get(1))){
					failureMsg.append("<br/>第"+successNum+"行,科目方向 输入错误;");
					successNum++;
					continue;
				}else if(!direMap.get(map.get("subj_code").get(1)).toString().equals(yesOrNoMap.get(map.get("subj_dire").get(1)).toString())){
					failureMsg.append("<br/>第"+successNum+"行,科目方向与科目编码不匹配;");
					successNum++;
					continue;
				}
				if(!map.get("cur_code").get(1).equals("")){
					if(!yesOrNoMap.containsKey(map.get("cur_code").get(1)) ){
						failureMsg.append("<br/>第"+successNum+"行,币种错误;");
						successNum++;
						continue;
					}
				}
				saveMap.putAll(groupMap);
				saveMap.put("subj_code", map.get("subj_code").get(1));
				saveMap.put("subj_name", map.get("subj_name").get(1));
				saveMap.put("subj_dire", yesOrNoMap.get(map.get("subj_dire").get(1)));
				saveMap.put("cur_code",  yesOrNoMap.get(map.get("cur_code").get(1)));
				saveMap.put("bal_os",  StringUtils.isEmpty(map.get("bal_os").get(1)) ? "0" : map.get("bal_os").get(1));
				saveMap.put("sum_od",  StringUtils.isEmpty(map.get("sum_od").get(1)) ? "0" : map.get("sum_od").get(1));
				saveMap.put("sum_oc",  StringUtils.isEmpty(map.get("sum_oc").get(1)) ? "0" : map.get("sum_oc").get(1));
				saveMap.put("this_od", StringUtils.isEmpty(map.get("sum_od").get(1)) ? "0" : map.get("sum_od").get(1));
				saveMap.put("this_oc", StringUtils.isEmpty(map.get("sum_oc").get(1)) ? "0" : map.get("sum_oc").get(1));
				//计算期初余额  借方=年初+借方-贷方 贷方=年初-借方+贷方
				// 计算期初余额 借方=年初+借方-贷方 贷方=年初-借方+贷方
				if (yesOrNoMap.get(map.get("subj_dire").get(1)).equals("0")) {
					saveMap.put("end_os", Double
							.parseDouble(StringUtils.isEmpty(map.get("bal_os").get(1)) ? "0" : map.get("bal_os").get(1))
							+ Double.parseDouble(
									StringUtils.isEmpty(map.get("sum_od").get(1)) ? "0" : map.get("sum_od").get(1))
							- Double.parseDouble(
									StringUtils.isEmpty(map.get("sum_oc").get(1)) ? "0" : map.get("sum_oc").get(1)));
				} else {
					saveMap.put("end_os", Double
							.parseDouble(StringUtils.isEmpty(map.get("bal_os").get(1)) ? "0" : map.get("bal_os").get(1))
							- Double.parseDouble(
									StringUtils.isEmpty(map.get("sum_od").get(1)) ? "0" : map.get("sum_od").get(1))
							+ Double.parseDouble(
									StringUtils.isEmpty(map.get("sum_oc").get(1)) ? "0" : map.get("sum_oc").get(1)));
				}
				saveMap.put("bal_os_w", 0.00);
				saveMap.put("this_od_w", 0.00);
				saveMap.put("this_oc_w", 0.00);
				saveMap.put("sum_od_w", 0.00);
				saveMap.put("sum_oc_w", 0.00);
				saveMap.put("end_os_w", 0.00);
				//saveMap.put("acc_month", "00");
				
				//如果借贷余额全部为0就不要添加数据了
				if (saveMap.get("bal_os").toString().equals("0") && saveMap.get("sum_od").toString().equals("0")
						&& saveMap.get("sum_oc").toString().equals("0")) {
					successNum++;
				}else {
					saveList.add(saveMap);
					successNum++;
				}
				
			}
			if(failureMsg.length()>0){
				return "{\"error\":\"导入失败"+failureMsg.toString()+"\"}";
			}
			if (saveList != null && saveList.size() > 0) {
				accLederMapper.addAccLederBatch(saveList);
			}
			successNum=successNum-2;
			return "{\"msg\":\"导入成功，成功条数"+successNum+"\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
	}
	public String readAccLederRelaFiles(Map<String, Object> entityMap) {
		try {
			StringBuffer errorString = new StringBuffer();
			int row = 2;
			
			List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
			
			Map<String, Map<String,Object>> errorMap = new HashMap<String,  Map<String,Object>>();
			
			Map<String,Object>mapa=(Map<String, Object>) JSONArray.parse(entityMap.get("para").toString());
			
			entityMap.put("subj_code", mapa.get("subj_code"));
			
			Map<String,Map<String,Object>> checkDataMap = new HashMap<String, Map<String,Object>>();
			
			List<Map>colums =ChdJson.fromJsonAsList(Map.class,mapa.get("column").toString());
			Map<String, Object> modeStart = accLederMapper.querySysModStart(entityMap);
			if(modeStart.size()>0){
				if(modeStart.get("ACC_YEAR")==null){
					errorString.append("<br/> 财务系统未启用！");
					return "{\"error\":\"导入失败"+errorString.toString()+"\"}";
				}else {
					List<Map<String,Object>>list=accLederMapper.queryAccLederCheckByYear(entityMap);
					if(list.size()>0){
						errorString.append("<br/> 该年度已存在业务数据！");
						return "{\"error\":\"导入失败"+errorString.toString()+"\"}";
					}
				}
			}
			List<Map<String,Object>>subjMap=accLederMapper.queryAccSubjByYear(entityMap);
			
			StringBuffer column1 = new StringBuffer();
			
			StringBuffer column2 = new StringBuffer();
			
			for (Map map : colums) {
				
				Map<String,Object> checmMap = new HashMap<String, Object>(); 
				
				if(map.get("column_item").equals("")){
					
					continue;
					
				}
				map.put("group_id", SessionManager.getGroupId());
				
				map.put("hos_id", SessionManager.getHosId());
				
				map.put("copy_code", SessionManager.getCopyCode());
				if(map.get("name").toString().equals("CHECK_ITEM_CODE")){
					
					column1.append(map.get("column_item")+"||','||");
					
					column2.append(map.get("column_item")+",");
				}else{
					column1.append(map.get("column_item")+"_ID||','||");
					
					column2.append(map.get("column_item")+"_ID,");
				}
			
				Map<String,Object>tableName=accLederMapper.queryTableName(map);
			
				tableName.putAll(map);
				//动态列比对数据
				List<Map<String,Object>> checkData=accLederMapper.queryCheckData(tableName);
				
				for (Map<String, Object> map2 : checkData) {
					if(map.get("name").toString().equals("CHECK_ITEM_CODE")){
						checmMap.put(map2.get("CHECK_ITEM_CODE").toString(), map2.get("CHECK_ITEM_ID").toString());
						checmMap.put(map2.get("CHECK_ITEM_NAME").toString(), map2.get("CHECK_ITEM_ID").toString());
					}else if(map.get("name").toString().equals("DEPT_CODE")){
						checmMap.put(map2.get("DEPT_CODE").toString(), map2.get("DEPT_ID").toString()+","+map2.get("DEPT_NO").toString());
						checmMap.put(map2.get("DEPT_NAME").toString(), map2.get("DEPT_ID").toString()+","+map2.get("DEPT_NO").toString());
					}else if(map.get("name").toString().equals("EMP_CODE")){
						checmMap.put(map2.get("EMP_CODE").toString(), map2.get("EMP_ID").toString()+","+map2.get("EMP_NO").toString());
						checmMap.put(map2.get("EMP_NAME").toString(), map2.get("EMP_ID").toString()+","+map2.get("EMP_NO").toString());
					}else if(map.get("name").toString().equals("PROJ_CODE")){
						checmMap.put(map2.get("PROJ_CODE").toString(), map2.get("PROJ_ID").toString()+","+map2.get("PROJ_NO").toString());
						checmMap.put(map2.get("PROJ_NAME").toString(), map2.get("PROJ_ID").toString()+","+map2.get("PROJ_NO").toString());
					}else if(map.get("name").toString().equals("STORE_NAME")){
						checmMap.put(map2.get("STORE_NAME").toString(), map2.get("STORE_ID").toString()+","+map2.get("STORE_NO").toString());
						checmMap.put(map2.get("STORE_NAME").toString(), map2.get("STORE_ID").toString()+","+map2.get("STORE_NO").toString());
					}else if(map.get("name").toString().equals("CUS_CODE")){
						checmMap.put(map2.get("CUS_CODE").toString(), map2.get("CUS_ID").toString()+","+map2.get("CUS_NO").toString());
						checmMap.put(map2.get("CUS_NAME").toString(), map2.get("CUS_ID").toString()+","+map2.get("CUS_NO").toString());
					}else if(map.get("name").toString().equals("SUP_CODE")){
						checmMap.put(map2.get("SUP_CODE").toString(), map2.get("SUP_ID").toString()+","+map2.get("SUP_NO").toString());
						checmMap.put(map2.get("SUP_NAME").toString(), map2.get("SUP_ID").toString()+","+map2.get("SUP_NO").toString());
					}else if(map.get("name").toString().equals("SOURCE_CODE")){
						checmMap.put(map2.get("SOURCE_CODE").toString(), map2.get("SOURCE_ID").toString());
						checmMap.put(map2.get("SOURCE_NAME").toString(), map2.get("SOURCE_ID").toString());
					}else{
						checmMap.put(map2.get(map.get("name")).toString(), map2.get(map.get("colum_check")));
						checmMap.put(map2.get(map.get("colum_check")).toString(), map2.get(map.get("name")));
					}
				}
				
				checkDataMap.put(map.get("name").toString(), checmMap);
				
			}
			String key1=column1.substring(0, column1.length()-7);
			entityMap.put("column1", key1);
			entityMap.put("column2", column2);
			List<Map<String,Object>>LederCheck = accLederMapper.queryAccLederCheckBySubjCode(entityMap);
			if(LederCheck.size()>0){
				for (Map<String, Object> map : LederCheck) {
						errorMap.put(map.get("KEY").toString(),map);
					
				}
			}
			 List<Map<String,Object>> whereList = new ArrayList<Map<String,Object>>();
			 Map<String,Object> where = new HashMap<String,Object>();
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			
			//迭代excel，map为行数据
			for (Map<String, List<String>> excelRow : list) {
				Map<String,Object> saveMap = new HashMap<String, Object>();
				StringBuffer key = new StringBuffer();
				//column为动态表头colum_check不为null的是动态表头
				 for (Map<String, String> column : colums) {
					 if(!column.get("column_item").equals("")){
						 if(excelRow.get(column.get("name")).get(1)!=null){
							 if(!checkDataMap.get(column.get("name")).containsKey(excelRow.get(column.get("name")).get(1))){
								 errorString.append("<br/> 第"+row+"行 "+column.get("display")+"填写错误");
								 continue;
							 }
							 if(column.get("name").toString().equals("CHECK_ITEM_CODE")){
								 saveMap.put(column.get("column_item"), checkDataMap.get(column.get("name")).get(excelRow.get(column.get("name")).get(1)).toString());
								 where.put(column.get("column_item"), checkDataMap.get(column.get("name")).get(excelRow.get(column.get("name")).get(1)).toString());
								 key.append(checkDataMap.get(column.get("name")).get(excelRow.get(column.get("name")).get(1)).toString()+"-");
							 } else if(column.get("name").toString().equals("SOURCE_CODE")){
									saveMap.put(column.get("column_item")+"_ID", checkDataMap.get(column.get("name")).get(excelRow.get(column.get("name")).get(1)).toString());
								    where.put(column.get("column_item")+"_ID", checkDataMap.get(column.get("name")).get(excelRow.get(column.get("name")).get(1)).toString());
								    key.append(checkDataMap.get(column.get("name")).get(excelRow.get(column.get("name")).get(1)).toString()+"-");
							 }else {
									saveMap.put(column.get("column_item")+"_ID", checkDataMap.get(column.get("name")).get(excelRow.get(column.get("name")).get(1)).toString().split(",")[0]);
									saveMap.put(column.get("column_item")+"_NO", checkDataMap.get(column.get("name")).get(excelRow.get(column.get("name")).get(1)).toString().split(",")[1]);
									where.put(column.get("column_item")+"_ID", checkDataMap.get(column.get("name")).get(excelRow.get(column.get("name")).get(1)).toString().split(",")[0]);
									where.put(column.get("column_item")+"_NO", checkDataMap.get(column.get("name")).get(excelRow.get(column.get("name")).get(1)).toString().split(",")[1]);
									key.append(checkDataMap.get(column.get("name")).get(excelRow.get(column.get("name")).get(1)).toString().split(",")[0]+",");
							 }
							 
						 } 
					}
				}
				saveMap.put("subj_code", entityMap.get("subj_code"));
				saveMap.put("bal_os", excelRow.get("bal_os").get(1) == null ? 0:excelRow.get("bal_os").get(1));
				saveMap.put("sum_od", excelRow.get("sum_od").get(1) == null ? 0:excelRow.get("sum_od").get(1));
				saveMap.put("sum_oc", excelRow.get("sum_oc").get(1) == null ? 0:excelRow.get("sum_oc").get(1));
				saveMap.put("this_od",excelRow.get("sum_od").get(1) == null ? 0:excelRow.get("sum_od").get(1));
				saveMap.put("this_oc",excelRow.get("sum_oc").get(1) == null ? 0:excelRow.get("sum_oc").get(1));
				saveMap.put("bal_os_w", 0.00);
				saveMap.put("this_od_w", 0.00);
				saveMap.put("this_oc_w", 0.00);
				saveMap.put("sum_od_w", 0.00);
				saveMap.put("sum_oc_w", 0.00);
				saveMap.put("end_os_w", 0.00);
				//saveMap.put("acc_month", "00");
				saveMap.put("acc_year", SessionManager.getAcctYear());
				
				
				String check = key.substring(0, key.length()-1);
				if(errorMap.containsKey(check)){
					Map<String,Object> oldMap = errorMap.get(check);
						saveMap.put("bal_os", Double.parseDouble(oldMap.get("BAL_OS").toString())+Double.parseDouble(saveMap.get("bal_os").toString()));
						saveMap.put("sum_od", Double.parseDouble(oldMap.get("SUM_OD").toString())+Double.parseDouble(saveMap.get("sum_od").toString()));
						saveMap.put("sum_oc", Double.parseDouble(oldMap.get("SUM_OC").toString())+Double.parseDouble(saveMap.get("sum_oc").toString()));
						saveMap.put("this_od",Double.parseDouble(oldMap.get("SUM_OD").toString())+Double.parseDouble(saveMap.get("this_od").toString()));
						saveMap.put("this_oc",Double.parseDouble(oldMap.get("SUM_OC").toString())+Double.parseDouble(saveMap.get("this_od").toString()));
						saveMap.put("end_os",excelRow.get("sum_oc").get(1));
				}
				if(subjMap.get(0).get("SUBJ_DIRE").toString().equals(0)){
					saveMap.put("end_os", Double.parseDouble(excelRow.get("bal_os").get(1))+NumberUtil.sub(Double.parseDouble(excelRow.get("sum_od").get(1) == null ? "0":excelRow.get("sum_od").get(1)), Double.parseDouble(excelRow.get("sum_oc").get(1) == null ? "0":excelRow.get("sum_oc").get(1))));
				}else{
					saveMap.put("end_os", NumberUtil.sub(Double.parseDouble(excelRow.get("bal_os").get(1)),Double.parseDouble(excelRow.get("sum_od").get(1) == null ? "0":excelRow.get("sum_od").get(1)))+Double.parseDouble(excelRow.get("sum_oc").get(1) == null ? "0":excelRow.get("sum_oc").get(1)));
				}
				row++;
				saveMap.put("group_id", SessionManager.getGroupId());
				saveMap.put("hos_id", SessionManager.getHosId());
				saveMap.put("copy_code", SessionManager.getCopyCode());
				
				where.put("group_id", SessionManager.getGroupId());
				where.put("hos_id", SessionManager.getHosId());
				where.put("copy_code", SessionManager.getCopyCode());
				where.put("subj_code", entityMap.get("subj_code"));
				whereList.add(where);
				errorMap.put(key.toString(), saveMap);
				saveList.add(saveMap);
			}
			if(errorString.length()>0){
				return "{\"error\":\"导入失败"+errorString.toString()+"\"}";
			}
			accLederCheckMapper.deleteAccLederCheckBatch(whereList);
			accLederCheckMapper.addAccLederCheckBatch(saveList);
			row=row-2;
			return  "{\"msg\":\"导入成功!成功条数"+row+".\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e);
		}
	}
	 
	@Override
	public List<Map<String, Object>> queryAccLederCheckListPrint(Map<String, Object> paraMap)
			throws DataAccessException {
		if (paraMap.get("group_id") == null) {
			paraMap.put("group_id", SessionManager.getGroupId());
		}
		
		if (paraMap.get("hos_id") == null) {
			paraMap.put("hos_id", SessionManager.getHosId());
		}
		
		if (paraMap.get("copy_code") == null) {
			paraMap.put("copy_code", SessionManager.getCopyCode());
		}
		if (paraMap.get("acc_year") == null) {
			paraMap.put("acc_year", SessionManager.getAcctYear());
		}
		
		Map<String, Object> map = queryCheckItemTable(paraMap);
		
		if (map != null) {
			map.put("COLUMN_CODE1", String.valueOf(map.get("COLUMN_CODE1")).toLowerCase());
			map.put("COLUMN_CODE2", String.valueOf(map.get("COLUMN_CODE2")).toLowerCase());
			map.put("COLUMN_CODE3", String.valueOf(map.get("COLUMN_CODE3")).toLowerCase());
			map.put("COLUMN_CODE4", String.valueOf(map.get("COLUMN_CODE4")).toLowerCase());
			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE1")))) {
				if (map.get("TABLE1") != null) {
					if (StringUtil.isBlank(String.valueOf(map.get("COLUMN_CHECK1")))) {
						map.put("COLUMN_CHECK1", "");
					} else {
						map.put("COLUMN_CHECK1", map.get("COLUMN_CHECK1") + "_ID");
					}
					
					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE1")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE1")))) {
						map.put("COLUMN_NO1", "");
					} else {
						map.put("COLUMN_CHECK1_NO", map.get("COLUMN_CHECK1").toString().substring(0, 6) + "_NO");
					}
				}
				
			} else {
				map.put("COLUMN_NO1", "");
			}
			
			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE2")))) {
				if (map.get("TABLE2") != null) {
					if (map.get("COLUMN_CHECK2") == null) {
						map.put("COLUMN_CHECK2", "");
					} else {
						map.put("COLUMN_CHECK2", map.get("COLUMN_CHECK2") + "_ID");
					}
					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE2")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE2")))) {
						map.put("COLUMN_NO2", "");
					} else {
						map.put("COLUMN_CHECK2_NO", map.get("COLUMN_CHECK2").toString().substring(0, 6) + "_NO");
					}
				}
			} else {
				map.put("COLUMN_NO2", "");
			}
			
			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE3")))) {
				if (map.get("TABLE3") != null) {
					if (map.get("COLUMN_CHECK3") == null) {
						map.put("COLUMN_CHECK3", "");
					} else {
						map.put("COLUMN_CHECK3", map.get("COLUMN_CHECK3") + "_ID");
					}
					
					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE3")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE3")))) {
						map.put("COLUMN_NO3", "");
					} else {
						map.put("COLUMN_CHECK3_NO", map.get("COLUMN_CHECK3").toString().substring(0, 6) + "_NO");
					}
				}
				
			} else {
				map.put("COLUMN_NO3", "");
			}
			
			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE4")))) {
				if (map.get("TABLE4") != null) {
					if (map.get("COLUMN_CHECK4") == null) {
						map.put("COLUMN_CHECK4", "");
					} else {
						map.put("COLUMN_CHECK4", map.get("COLUMN_CHECK4") + "_ID");
						
					}
					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE4")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE4")))) {
						map.put("COLUMN_NO4", "");
					} else {
						map.put("COLUMN_CHECK4_NO", map.get("COLUMN_CHECK4").toString().substring(0, 6) + "_NO");
					}
				}
			} else {
				map.put("COLUMN_NO4", "");
			}
			
			paraMap.putAll(map);
		}
		
		List<Map<String, Object>> list = accLederMapper.queryAccLederCheckListPrint(paraMap);
		return list;
	}

}
