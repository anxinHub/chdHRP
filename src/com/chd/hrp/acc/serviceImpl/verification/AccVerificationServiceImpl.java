/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.verification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccLederCheckMapper;
import com.chd.hrp.acc.dao.verification.AccNostroMapper;
import com.chd.hrp.acc.dao.verification.AccVerificationMapper;
import com.chd.hrp.acc.entity.AccLederCheck;
import com.chd.hrp.acc.service.verification.AccVerificationService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 财务账表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accVerificationService")
public class AccVerificationServiceImpl implements AccVerificationService {

	private static Logger logger = Logger.getLogger(AccVerificationServiceImpl.class);

	@Resource(name = "accLederCheckMapper")
	private final AccLederCheckMapper accLederCheckMapper = null;

	@Resource(name = "accVerificationMapper")
	private final AccVerificationMapper accVerificationMapper = null;
	
	@Resource(name = "accNostroMapper")
	private final AccNostroMapper accNostroMapper = null;
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	/**
	 * 往来核销 -- 往来核销左侧
	 * 
	 * @param listVo1
	 * @return
	 */
	@Override
	public String queryAccVerificationL(Map<String, Object> entityMap) throws DataAccessException {
		StringBuffer  check = new StringBuffer();
        StringBuffer checkCol= new StringBuffer();
        
        StringBuffer is_autoWhere = new StringBuffer();
        is_autoWhere.append("");
        StringBuffer tableName = new StringBuffer();
        
        String item_name = "";
      
        if(entityMap.get("acc_monthLB") != null){
        	if(entityMap.get("acc_monthLB").equals("0") ){
        		entityMap.put("acc_monthLB", "00");
            }
        }
        if(entityMap.get("acc_monthRB") != null){
        	if(entityMap.get("acc_monthRB").equals("0") ){
        		entityMap.put("acc_monthRB", "00");
            }
        }
        //是否记账状态
        if(entityMap.get("is_check") !=null){
        	if(entityMap.get("is_check").equals("1")){
        		entityMap.put("state", " and av.state > 0 and av.state <= 99");
            }else{
            	entityMap.put("state",  " and (av.state = 99 or av.state is null)");
            }
        }
       
        if(entityMap.get("check_type") !=null){
    	  if(entityMap.get("check_type").equals("HOS_EMP_DICT")){
        	tableName.append("HOS_EMP_DICT");
        	checkCol.append("b.emp_code ||' '|| b.emp_name as check_name,b.emp_id ||' '|| b.emp_no as check_code ,");
        	check.append(" and avc.CHECK2_ID = b.emp_id and b.is_stop = 0 and avc.CHECK2_NO = b.emp_no ");
        	if(entityMap.get("proj1").equals("")){
        		check.append("");
        	}else{
        		if(entityMap.get("proj2").equals("")){
        			check.append("");
        		}else{
        			check.append(" and b.emp_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
        		}
        	}
        	
        }else if(entityMap.get("check_type").equals("HOS_DEPT_DICT")){
        	tableName.append("HOS_DEPT_DICT");
        	checkCol.append("b.dept_code ||' '|| b.dept_name as check_name, b.dept_id ||' '|| b.dept_no as check_code ,");
        	check.append(" and avc.CHECK1_ID = b.dept_id and b.is_stop = 0 AND avc.CHECK1_NO = b.dept_no");
        	if(entityMap.get("proj1").equals("")){
        		check.append("");
        	}else{
        		if(entityMap.get("proj2").equals("")){
        			check.append("");
        		}else{
        		check.append(" and b.dept_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"'");
        		}
        	}
        	
        }else if(entityMap.get("check_type").equals("HOS_PROJ_DICT")){
        	tableName.append("HOS_PROJ_DICT");
        	checkCol.append("b.proj_code ||' '|| b.proj_name as check_name,b.proj_id ||' '|| b.proj_no as check_code,");
        	check.append(" and avc.CHECK3_ID = b.proj_id and b.is_stop = 0 and avc.CHECK3_NO = b.proj_no");
        	if(entityMap.get("proj1").equals("")){
        		check.append("");
        	}else{
        		if(entityMap.get("proj2").equals("")){
        			check.append("");
        		}else{
        			check.append(" and b.proj_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
        		}
        	}      
        	
        }else if(entityMap.get("check_type").equals("HOS_STORE_DICT")){
        	tableName.append("HOS_STORE_DICT");
        	checkCol.append("b.store_code ||' '|| b.store_name as check_name , b.store_id ||' '|| b.store_no as check_code, ");
        	check.append(" and avc.CHECK4_ID = b.store_id and b.is_stop = 0 and avc.CHECK4_NO =b.store_no");
        	if(entityMap.get("proj1").equals("")){
        		check.append("");
        	}else{
        		if(entityMap.get("proj2").equals("")){
        			check.append("");
        		}else{
        			check.append(" and b.store_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
        		}
        	}     
        	
        }else if(entityMap.get("check_type").equals("HOS_CUS_DICT")){
        	tableName.append("HOS_CUS_DICT");
        	checkCol.append("b.cus_code ||' '|| b.cus_name as check_name, b.cus_id ||' '|| b.cus_no as check_code, ");
        	check.append(" and avc.CHECK5_ID = b.cus_id and b.is_stop = 0 and avc.CHECK5_NO = b.cus_no");
        	if(entityMap.get("proj1").equals("")){
        		check.append("");
        	}else{
        		if(entityMap.get("proj2").equals("")){
        			check.append("");
        		}else{
        			check.append(" and b.cus_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
        		}
        	}      
        	
        }else if(entityMap.get("check_type").equals("HOS_SUP_DICT")){
        	tableName.append("HOS_SUP_DICT");
        	checkCol.append("b.sup_code ||' '|| b.sup_name as check_name, b.sup_id ||' '|| b.sup_no as check_code, ");
        	check.append(" and avc.CHECK6_ID = b.sup_id and b.is_stop = 0 and avc.CHECK6_NO = b.sup_no");
        	if(entityMap.get("proj1").equals("")){
        		check.append("");
        	}else{
        		if(entityMap.get("proj2").equals("")){
        			check.append("");
        		}else{
        			check.append(" and b.sup_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
        		}
        	}       
        	
        }else if(entityMap.get("check_type").equals("HOS_SOURCE")){
        	tableName.append("HOS_SOURCE");
        	checkCol.append("b.source_code ||' '|| b.source_name as check_name, b.source_id ||' '|| b.source_no as check_code,");
        	check.append(" and avc.CHECK7_ID = b.source_id and b.is_stop=0 ");
        	if(entityMap.get("proj1").equals("")){
        		check.append("");
        	}else{
        		if(entityMap.get("proj2").equals("")){
        			check.append("");
        		}else{
        			check.append(" and b.source_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
        		}
        	}      
        }else if(entityMap.get("check_type").equals("ACC_CHECK_ITEM")){//自定义核算
        	tableName.append("ACC_CHECK_ITEM");
        	checkCol.append("b.CHECK_ITEM_CODE ||' '|| b.CHECK_ITEM_NAME as check_name,b.CHECK_ITEM_CODE ||' '|| b.CHECK_ITEM_NAME as check_code,");  	
        	//首先查一下自定义辅助核算挂在哪一个下面
        	String column_check ="";
        	Map<String, Object> mapVo1 = new HashMap<String, Object>();
        	mapVo1.put("group_id", SessionManager.getGroupId());		
    		mapVo1.put("hos_id", SessionManager.getHosId());		
            mapVo1.put("copy_code", SessionManager.getCopyCode());           
            
            mapVo1.put("check_type_id", entityMap.get("check_type_id"));           
            column_check = queryColumnName(mapVo1);   
            
            
        	if(entityMap.get("proj1").equals("")){
        		check.append("");
        	}else{
        		if(entityMap.get("proj2").equals("")){
        			check.append("");
        		}else{
        			check.append(" and avc."+column_check+"  between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"'");	
        			
        		}
        	}
        	check.append(" and b.check_type_id="+entityMap.get("check_type_id"));
            check.append(" and b.check_item_id=avc."+column_check);
        }
      } 
      entityMap.put("tableName", tableName);
      entityMap.put("checkCol", checkCol);
      entityMap.put("check", check);
      
      SysPage sysPage = new SysPage();
      sysPage = (SysPage) entityMap.get("sysPage");
      RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
      List<AccLederCheck> listLast = new ArrayList<AccLederCheck>();	
      listLast = accVerificationMapper.queryAccVerificationL(entityMap, rowBounds);
      PageInfo page = new PageInfo(listLast);
  	  
      return ChdJson.toJson(listLast, page.getTotal());
    	
	}

	/**
	 * 往来核销 右侧查询
	 */
	@Override
	public String queryAccVerificationR(Map<String, Object> entityMap) throws DataAccessException {
		StringBuffer check = new StringBuffer();
		StringBuffer checkCol = new StringBuffer();

		StringBuffer is_autoWhere = new StringBuffer();
		is_autoWhere.append("");
		StringBuffer tableName = new StringBuffer();

		String item_name = "";

		if (entityMap.get("acc_monthLB") != null) {
			if (entityMap.get("acc_monthLB").equals("0")) {
				entityMap.put("acc_monthLB", "00");
			}
		}
		if (entityMap.get("acc_monthRB") != null) {
			if (entityMap.get("acc_monthRB").equals("0")) {
				entityMap.put("acc_monthRB", "00");
			}
		}
		// 是否记账状态
		if (entityMap.get("is_check") != null) {
			if (entityMap.get("is_check").equals("1")) {
				entityMap.put("state", " and ((av.state > 0 and av.state <= 99) or av.state is null)");
			} else {
				entityMap.put("state", " and (av.state = 99 or av.state is null)");
			}
		}

		if (entityMap.get("check_type") != null) {
			if (entityMap.get("check_type").equals("HOS_EMP_DICT")) {
				tableName.append("HOS_EMP_DICT");
				checkCol.append("b.emp_code ||' '|| b.emp_name as check_name,b.emp_id ||' '|| b.emp_no as check_code ,");
				check.append(" and avc.CHECK2_ID = b.emp_id and b.is_stop = 0 and avc.CHECK2_NO = b.emp_no ");
				if (entityMap.get("proj1").equals("")) {
					check.append("");
				} else {
					if (entityMap.get("proj2").equals("")) {
						check.append("");
					} else {
						check.append(" and b.emp_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2") + "' ");
					}
				}

			} else if (entityMap.get("check_type").equals("HOS_DEPT_DICT")) {
				tableName.append("HOS_DEPT_DICT");
				checkCol.append("b.dept_code ||' '|| b.dept_name as check_name, b.dept_id ||' '|| b.dept_no as check_code ,");
				check.append(" and avc.CHECK1_ID = b.dept_id and b.is_stop = 0 AND avc.CHECK1_NO = b.dept_no");
				if (entityMap.get("proj1").equals("")) {
					check.append("");
				} else {
					if (entityMap.get("proj2").equals("")) {
						check.append("");
					} else {
						check.append(" and b.dept_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2") + "'");
					}
				}

			} else if (entityMap.get("check_type").equals("HOS_PROJ_DICT")) {
				tableName.append("HOS_PROJ_DICT");
				checkCol.append("b.proj_code ||' '|| b.proj_name as check_name,b.proj_id ||' '|| b.proj_no as check_code,");
				check.append(" and avc.CHECK3_ID = b.proj_id and b.is_stop = 0 and avc.CHECK3_NO = b.proj_no");
				if (entityMap.get("proj1").equals("")) {
					check.append("");
				} else {
					if (entityMap.get("proj2").equals("")) {
						check.append("");
					} else {
						check.append(" and b.proj_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2") + "' ");
					}
				}

			} else if (entityMap.get("check_type").equals("HOS_STORE_DICT")) {
				tableName.append("HOS_STORE_DICT");
				checkCol.append("b.store_code ||' '|| b.store_name as check_name , b.store_id ||' '|| b.store_no as check_code, ");
				check.append(" and avc.CHECK4_ID = b.store_id and b.is_stop = 0 and avc.CHECK4_NO =b.store_no");
				if (entityMap.get("proj1").equals("")) {
					check.append("");
				} else {
					if (entityMap.get("proj2").equals("")) {
						check.append("");
					} else {
						check.append(" and b.store_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2") + "' ");
					}
				}

			} else if (entityMap.get("check_type").equals("HOS_CUS_DICT")) {
				tableName.append("HOS_CUS_DICT");
				checkCol.append("b.cus_code ||' '|| b.cus_name as check_name, b.cus_id ||' '|| b.cus_no as check_code, ");
				check.append(" and avc.CHECK5_ID = b.cus_id and b.is_stop = 0 and avc.CHECK5_NO = b.cus_no");
				if (entityMap.get("proj1").equals("")) {
					check.append("");
				} else {
					if (entityMap.get("proj2").equals("")) {
						check.append("");
					} else {
						check.append(" and b.cus_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2") + "' ");
					}
				}

			} else if (entityMap.get("check_type").equals("HOS_SUP_DICT")) {
				tableName.append("HOS_SUP_DICT");
				checkCol.append("b.sup_code ||' '|| b.sup_name as check_name, b.sup_id ||' '|| b.sup_no as check_code, ");
				check.append(" and avc.CHECK6_ID = b.sup_id and b.is_stop = 0 and avc.CHECK6_NO = b.sup_no");
				if (entityMap.get("proj1").equals("")) {
					check.append("");
				} else {
					if (entityMap.get("proj2").equals("")) {
						check.append("");
					} else {
						check.append(" and b.sup_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2") + "' ");
					}
				}

			} else if (entityMap.get("check_type").equals("HOS_SOURCE")) {
				tableName.append("HOS_SOURCE");
				checkCol.append("b.source_code ||' '|| b.source_name as check_name, b.source_id ||' '|| b.source_no as check_code,");
				check.append(" and avc.CHECK7_ID = b.source_id and b.is_stop=0 ");
				if (entityMap.get("proj1").equals("")) {
					check.append("");
				} else {
					if (entityMap.get("proj2").equals("")) {
						check.append("");
					} else {
						check.append(" and b.source_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2") + "' ");
					}
				}
			} else if (entityMap.get("check_type").equals("ACC_CHECK_ITEM")) {// 自定义核算
				tableName.append("ACC_CHECK_ITEM");
				checkCol.append("b.CHECK_ITEM_CODE ||' '|| b.CHECK_ITEM_NAME as check_name,b.CHECK_ITEM_CODE ||' '|| b.CHECK_ITEM_NAME as check_code,");
				// 首先查一下自定义辅助核算挂在哪一个下面
				String column_check = "";
				Map<String, Object> mapVo1 = new HashMap<String, Object>();
				mapVo1.put("group_id", SessionManager.getGroupId());
				mapVo1.put("hos_id", SessionManager.getHosId());
				mapVo1.put("copy_code", SessionManager.getCopyCode());

				mapVo1.put("check_type_id", entityMap.get("check_type_id"));
				column_check = queryColumnName(mapVo1);
				if (entityMap.get("proj1").equals("")) {
					check.append("");
				} else {
					if (entityMap.get("proj2").equals("")) {
						check.append("");
					} else {
						check.append(" and b.CHECK_ITEM_CODE  between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2") + "'");

					}
				}
				check.append(" and b.check_type_id="+entityMap.get("check_type_id"));
	            check.append(" and b.check_item_id=avc."+column_check);
			}
		}
		entityMap.put("tableName", tableName);
		entityMap.put("checkCol", checkCol);
		entityMap.put("check", check);

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccLederCheck> listRright = new ArrayList<AccLederCheck>();

		listRright = accVerificationMapper.queryAccVerificationR(entityMap, rowBounds);// 右侧结果集
		PageInfo page = new PageInfo(listRright);
		return ChdJson.toJson(listRright, page.getTotal());

	}
	
	/**
	 * 往来核销--查看自定义辅助核算挂在哪个科目下面
	 */
	@Override
	public String queryColumnName(Map<String, Object> entityMap) throws DataAccessException {
		String result = "";
		AccLederCheck accLederCheck = accVerificationMapper.queryColumnName(entityMap);

		if (accLederCheck != null) {
			result = accLederCheck.getCheck_name();
			return result;
		}
		return result;
	}

	/**
	 * 往来核销 -- 勾选核销
	 */
	public String addAccVerification(Map<String, Object> entityMap) throws DataAccessException {
		
			//首先循环借方的金额，记录借方第一条数据的总金额，然后循环贷方，直至金额相等为止
			int resultJ= 0;  //要核销的借方金额
			int y_resultJ=0;//用来记录已经参加核销的金额
			int y_resultD = 0;
			int resultD= 0;  //用来核销的贷方的金额
			
			List<Map<String, Object>> vouchLList = new ArrayList<Map<String,Object>>();//贷方
			List<Map<String, Object>> vouchRList = new ArrayList<Map<String,Object>>();//借方
			List<Map<String, Object>> veriList = new ArrayList<Map<String,Object>>();
			
			JSONArray jsonL = JSONArray.parseArray((String)entityMap.get("check_dataJ"));
			JSONArray jsonR = JSONArray.parseArray((String)entityMap.get("check_dataD"));
			//判断两端单位是否相同
			String tmp=null;
			String litem="";
			for(int i=0;i<jsonL.size();i++){
				tmp=jsonL.getJSONObject(i).getString("check_code");
				if(litem.equals("")||!litem.contains(tmp)){
					litem+=tmp+"_";
				}
			}
			String ritem="";
			for(int i=0;i<jsonL.size();i++){
				tmp=jsonR.getJSONObject(i).getString("check_code");
				if(ritem.equals("")||!litem.contains(tmp)){
					ritem+=tmp+"_";
				}
			}
			if(!ritem.equals(litem)){
				return "{\"error\":\"核销失败，借贷双方项目不一致.\",\"state\":\"false\"}";
			}
			Map<String, Map<String,Object>> leftMap = new HashMap<String,Map<String,Object>>();
			Map<String, Map<String,Object>> rightMap = new HashMap<String,Map<String,Object>>();
	        
	        int lnum = 0;
			Iterator itL = jsonL.iterator();
			while (itL.hasNext()) {
				JSONObject jsonObjL = JSONObject.parseObject(itL.next().toString());
				Map<String, Object> mapL = new HashMap<String, Object>();

				StringBuffer conL = new StringBuffer();
				conL.append(String.valueOf(lnum) + '@');
				conL.append(jsonObjL.get("group_id").toString() + jsonObjL.get("hos_id").toString() + jsonObjL.get("copy_code").toString()
						+ jsonObjL.get("acc_year").toString());
				conL.append("@").append(jsonObjL.get("wbal_amt"));
				
				mapL.put("group_id", jsonObjL.get("group_id"));
				mapL.put("hos_id", jsonObjL.get("hos_id"));
				mapL.put("copy_code", jsonObjL.get("copy_code"));
				mapL.put("acc_year", jsonObjL.get("acc_year"));
				mapL.put("debit_check_id", jsonObjL.get("check_id"));
				mapL.put("credit_check_id", 0);
				mapL.put("veri_money", jsonObjL.get("wbal_amt"));
				mapL.put("veri_money_w", 0);
				mapL.put("isCheck", "0");
				mapL.put("veri_check_id", "0");
				
				String key = conL.toString();
				leftMap.put(key, mapL);
				lnum++;
			}

			// 处理右侧json串
			int rnum = 0;
			Iterator itR = jsonR.iterator();
			while (itR.hasNext()) {
				JSONObject jsonObjR = JSONObject.parseObject(itR.next().toString());
				Map<String, Object> mapR = new HashMap<String, Object>();

				StringBuffer conR = new StringBuffer();
				conR.append(String.valueOf(rnum) + '@');
				conR.append(jsonObjR.get("group_id").toString() + jsonObjR.get("hos_id").toString() + jsonObjR.get("copy_code").toString()
						+ jsonObjR.get("acc_year").toString());
				conR.append("@").append(jsonObjR.get("wbal_amt"));

				mapR.put("group_id", jsonObjR.get("group_id"));
				mapR.put("hos_id", jsonObjR.get("hos_id"));
				mapR.put("copy_code", jsonObjR.get("copy_code"));
				mapR.put("acc_year", jsonObjR.get("acc_year"));
				mapR.put("debit_check_id", 0);
				mapR.put("credit_check_id", jsonObjR.get("check_id"));
				mapR.put("veri_money", jsonObjR.get("wbal_amt"));
				mapR.put("veri_money_w", 0);
				mapR.put("isCheck", "0");
				if(jsonObjR.get("veri_check_id")==null)
					mapR.put("veri_check_id","0");
				else
					mapR.put("veri_check_id", jsonObjR.get("veri_check_id"));

				String key = conR.toString();
				rightMap.put(key, mapR);
				rnum++;
			}

			// 处理对应关系
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateString = formatter.format(new Date());
			int a = 0;
			Iterator itl = leftMap.keySet().iterator();
			while (itl.hasNext()) {
				String key1 = String.valueOf(itl.next());
				String keyL = key1.split("@")[1];
				double yhx_l=0;
				Iterator itr = rightMap.keySet().iterator();
				while (itr.hasNext()) {
					a++;
					String key2 = String.valueOf(itr.next());
					String keyR = key2.split("@")[1];
					if (keyR.equals(keyL)) {
						Map<String, Object> lMap = leftMap.get(key1);
						Map<String, Object> rMap = rightMap.get(key2);
						//左右两侧均未对账
						if ("0".equals(lMap.get("isCheck").toString())) {
							if ("0".equals(rMap.get("isCheck").toString())) {
								Map<String, Object> vmap = new HashMap<String, Object>();// 对应关系map
								Map<String, Object> vouchLMap = new HashMap<String, Object>();// 更新map
								Map<String, Object> vouchRMap = new HashMap<String, Object>();// 更新map
								
								Long veri_check_id =Long.parseLong(dateString+a);
//								if(rMap.get("veri_check_id")==null)
//									veri_check_id=Long.parseLong(dateString+a);
//								else
//									veri_check_id=Long.parseLong(rMap.get("veri_check_id").toString());
								if("0".equals(lMap.get("veri_check_id").toString()) && "0".equals(rMap.get("veri_check_id").toString())){
									vmap.put("veri_check_id", veri_check_id);
								}else{
									vmap.put("veri_check_id", lMap.get("veri_check_id").toString()=="0" ? rMap.get("veri_check_id").toString() : lMap.get("veri_check_id").toString());
								}
								
								vmap.put("group_id", entityMap.get("group_id"));
								vmap.put("hos_id", entityMap.get("hos_id"));
								vmap.put("copy_code", entityMap.get("copy_code"));
								vmap.put("acc_year", entityMap.get("acc_year"));
								vmap.put("create_user", SessionManager.getUserId());
								vmap.put("create_date", date);
								vmap.put("veri_money_w", "0");
								
								vouchLMap.put("group_id", entityMap.get("group_id"));
								vouchLMap.put("hos_id", entityMap.get("hos_id"));
								vouchLMap.put("copy_code", entityMap.get("copy_code"));
								vouchLMap.put("acc_year", entityMap.get("acc_year"));
								
								vouchRMap.putAll(vouchLMap);
									
								double num = Double.parseDouble(lMap.get("veri_money").toString())- Double.parseDouble(rMap.get("veri_money").toString());
								// 若借方金额 >= 贷方金额 借方金额=借方金额-贷方金额
								if (num>=0) {
									yhx_l=Double.parseDouble(rMap.get("veri_money").toString());
									vmap.put("veri_money", rMap.get("veri_money").toString());
									vmap.put("credit_check_id", rMap.get("credit_check_id"));
									vmap.put("debit_check_id", lMap.get("debit_check_id"));
									//double yveri_money=Double.parseDouble((lMap.get("veri_money")==null?"0":lMap.get("veri_money").toString()));
									lMap.put("veri_money", num);
									lMap.put("veri_check_id", vmap.get("veri_check_id"));
									rMap.put("veri_money", 0);
									rMap.put("isCheck", "1");
									rMap.put("veri_check_id", vmap.get("veri_check_id"));
									
									vouchRMap.put("bal_amt", vmap.get("veri_money"));
									vouchRMap.put("vouch_check_id", rMap.get("credit_check_id"));
									vouchRMap.put("check1_id", vmap.get("veri_check_id"));
									vouchRList.add(vouchRMap);
									if(num==0){//如果左侧正好完成核销
										lMap.put("isCheck", "1");
									}
									
									vouchLMap.put("bal_amt", yhx_l);
									vouchLMap.put("vouch_check_id", lMap.get("debit_check_id"));
									vouchLList.add(vouchLMap);										
																		
									veriList.add(vmap);
								} else {
									// 单位账金额<银行行金额 直接更新单位账isCheck,记录单位账id
								//	double num = Double.parseDouble(rMap.get("veri_money").toString())- Double.parseDouble(lMap.get("veri_money").toString());
									vmap.put("veri_money", lMap.get("veri_money").toString());
									yhx_l=Double.parseDouble(lMap.get("veri_money").toString());
									vmap.put("credit_check_id", rMap.get("credit_check_id"));
									vmap.put("debit_check_id", lMap.get("debit_check_id"));
									
									lMap.put("veri_money", 0);
									lMap.put("veri_check_id", vmap.get("veri_check_id"));
									rMap.put("veri_money", num);
									lMap.put("isCheck", "1");
									rMap.put("veri_check_id", vmap.get("veri_check_id"));
									
									if(num==0){
										rMap.put("isCheck", "1");
									}
									vouchLMap.put("bal_amt", vmap.get("veri_money"));
									vouchLMap.put("vouch_check_id", lMap.get("debit_check_id"));
									
									vouchRMap.put("bal_amt", vmap.get("veri_money"));
									vouchRMap.put("vouch_check_id", rMap.get("credit_check_id"));
									vouchLList.add(vouchLMap);
									vouchRList.add(vouchRMap);
									
									veriList.add(vmap);
								}
							}
						}
					}
				}
			}
			
	        //logMap
			Map<String,Object> logMap = new HashMap<String,Object>();
			logMap.put("group_id", entityMap.get("group_id"));
			logMap.put("hos_id", entityMap.get("hos_id"));
			logMap.put("copy_code", entityMap.get("copy_code"));
			logMap.put("acc_year", entityMap.get("acc_year"));
			logMap.put("create_user", entityMap.get("user_id"));
			logMap.put("create_date", new Date());
			logMap.put("note", "记录 " + entityMap.get("subj_name").toString() + " 的对账记录！");	
			
			try{
				if (veriList.size() > 0) {
					// 1、往 acc_vouch_veri中添加记录
					accVerificationMapper.addAccVericationVer(veriList);
					// 2、往 表acc_vouch_veri_log中添加记录
					accVerificationMapper.addAccVericationLog(logMap);
					// 3、回写辅助核算表
					if(vouchLList.size() > 0){
						accVerificationMapper.updateAccVouchCheck(vouchLList);
					}
					if(vouchRList.size() > 0){
						accVerificationMapper.updateAccVouchCheck(vouchRList);
					}
					
					return "{\"msg\":\"核销成功！\",\"state\":\"true\"}";
				} else {
					return "{\"msg\":\"核销失败，没有要核销的数据.\",\"state\":\"false\"}";
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return "{\"error\":\"核销失败! 错误编码  accVerification\"}";
			}
			
			
			/*	//借方勾选的条数小于贷方，先循环借方，然后循环贷方
			 * 	//暂时不去掉，为部分核销做准备
			 * if(J_length <= D_length){
				Iterator it1 = json.iterator();
				try{
					while (it1.hasNext()) {
						JSONObject jsonObj = JSONObject.parseObject(it1.next().toString());
						resultJ = resultJ + Integer.parseInt(jsonObj.get("wbal_amt").toString());
						Iterator it2 = json1.iterator();
						while(it2.hasNext()){
							Map<String, Object> mapVo1 = new HashMap<String, Object>();
							JSONObject jsonObj2 = JSONObject.parseObject(it2.next().toString());
			
							resultD = resultD + Integer.parseInt(jsonObj2.get("wbal_amt").toString());
							
							mapVo1.put("subj_id", mapVo.get("subj_id"));
							mapVo1.put("check_idJ", jsonObj.get("check_id"));
							mapVo1.put("group_id", SessionManager.getGroupId());
							mapVo1.put("hos_id", SessionManager.getHosId());
							mapVo1.put("copy_code", SessionManager.getCopyCode());
							mapVo1.put("acc_year", SessionManager.getAcctYear());
							mapVo1.put("user_id", SessionManager.getUserId());
							mapVo1.put("veri_money_w", 0);
							mapVo1.put("create_date", dateFormat.format(now));
							mapVo1.put("veri_check_id", veri_check_id);
							if(resultJ >= resultD){
								mapVo1.put("check_idD", jsonObj2.get("check_id"));
								mapVo1.put("bal_amt", jsonObj2.get("wbal_amt"));
								y_resultJ = y_resultJ + Integer.parseInt(jsonObj2.get("wbal_amt").toString());							
							}else{							
								mapVo1.put("check_idD", jsonObj2.get("check_id"));
								mapVo1.put("bal_amt", resultJ-y_resultJ);							
							}
							listVo1.add(mapVo1);
						}	
					}
				}catch (DataAccessException e) {
					return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
				}
			}else{//借方勾选的条数大于贷方，循环借方，以贷方为基准
				Iterator it1 = json1.iterator();
				try{
					while (it1.hasNext()) {
						JSONObject jsonObj = JSONObject.parseObject(it1.next().toString());
						resultD = resultD + Integer.parseInt(jsonObj.get("wbal_amt").toString());
						Iterator it2 = json.iterator();
						while(it2.hasNext()){
							Map<String, Object> mapVo1 = new HashMap<String, Object>();
							JSONObject jsonObj2 = JSONObject.parseObject(it2.next().toString());
							resultJ = resultJ + Integer.parseInt(jsonObj2.get("wbal_amt").toString());				
							mapVo1.put("subj_id", mapVo.get("subj_id"));
							mapVo1.put("group_id", SessionManager.getGroupId());
							mapVo1.put("hos_id", SessionManager.getHosId());
							mapVo1.put("copy_code", SessionManager.getCopyCode());
							mapVo1.put("acc_year", SessionManager.getAcctYear());
							mapVo1.put("user_id", SessionManager.getUserId());
							mapVo1.put("veri_money_w", 0);
							mapVo1.put("create_date", dateFormat.format(now));
							mapVo1.put("veri_check_id", veri_check_id);
							if(resultD >= resultJ){		
								mapVo1.put("check_idD", jsonObj.get("check_id"));
								mapVo1.put("check_idJ", jsonObj2.get("check_id"));
								mapVo1.put("bal_amt", jsonObj2.get("wbal_amt"));	
								y_resultD = y_resultD + Integer.parseInt(jsonObj2.get("wbal_amt").toString());		
							}else{
								mapVo1.put("check_idD", jsonObj.get("check_id"));
								mapVo1.put("bal_amt", resultD-y_resultD);			
								mapVo1.put("check_idJ", jsonObj2.get("check_id"));
							}
							listVo1.add(mapVo1);
						}		
					}
				}catch (DataAccessException e) {
					return JSONObject.parseObject("{\"msg\":\"核销失败！\",\"state\":\"err\"}");
				}
			}*/
		
	}

	/**
	 * 勾选取消核销数据
	 */
	@Override
	public String deleteAccVeriIsChecked(Map<String, Object> mapVo) throws DataAccessException {
			
		List<Map<String, Object>> debitList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> creditList = new ArrayList<Map<String, Object>>();

		JSONArray debitJson = JSONArray.parseArray((String) mapVo.get("check_dataJ"));
		JSONArray creditJson = JSONArray.parseArray((String) mapVo.get("check_dataD"));

		Map<String, Double> mapLeft = new HashMap<String, Double>(); // 要比较的对象
		Map<String, Double> mapRight = new HashMap<String, Double>(); // 要比较的对象
		String veri_check_id=null;
		Iterator debitIt = debitJson.iterator();

		Map<String, Object> debitMap=null;
		JSONObject jsonObj=null;
		while (debitIt.hasNext()) {
			 jsonObj= JSONObject.parseObject(debitIt.next().toString());
			 debitMap= new HashMap<String, Object>();
			 debitMap.put("group_id", SessionManager.getGroupId());
			 debitMap.put("hos_id", SessionManager.getHosId());
			 debitMap.put("copy_code", SessionManager.getCopyCode());
			 debitMap.put("acc_year", SessionManager.getAcctYear());
			 debitMap.put("subj_code", mapVo.get("subj_code"));
			 debitMap.put("veri_check_id", jsonObj.get("veri_check_id"));
			 debitMap.put("debit_check_id", jsonObj.get("check_id"));
			 debitMap.put("veri_money", jsonObj.get("ybal_amt"));
			
			 if(jsonObj.get("veri_check_id")==null){
					return "{\"msg\":\"借方未进行核销，无法取消核销.\",\"state\":\"err\"}";
			}else{
				if(veri_check_id==null){
					veri_check_id=jsonObj.get("veri_check_id").toString();
				}else{
					if(!veri_check_id.equals(jsonObj.get("veri_check_id").toString())){
						return "{\"msg\":\"所选借贷方不属于同一核销序列，无法取消核销.\",\"state\":\"err\"}";
					}
				}
				if (mapLeft.get(debitMap.get("debit_check_id").toString()) == null) {
					mapLeft.put(jsonObj.get("check_id").toString(), Double.parseDouble(jsonObj.get("ybal_amt").toString()));
				} else {
					mapLeft.put(jsonObj.get("check_id").toString(),Double.parseDouble(mapLeft.get(jsonObj.get("check_id").toString()).toString())+ Double.parseDouble(jsonObj.get("ybal_amt").toString()));
				}
			}
			debitList.add(debitMap);
		}

		Iterator it2 = creditJson.iterator();
		Map<String, Object> creditMap=null;
		while (it2.hasNext()) {
			jsonObj = JSONObject.parseObject(it2.next().toString());
			creditMap= new HashMap<String, Object>();

			creditMap.put("group_id", SessionManager.getGroupId());
			creditMap.put("hos_id", SessionManager.getHosId());
			creditMap.put("copy_code", SessionManager.getCopyCode());
			creditMap.put("acc_year", SessionManager.getAcctYear());
			creditMap.put("subj_code", mapVo.get("subj_code"));
			creditMap.put("veri_check_id", jsonObj.get("veri_check_id"));
			creditMap.put("veri_money", jsonObj.get("ybal_amt"));
			creditMap.put("credit_check_id", jsonObj.get("check_id"));
			
			if(jsonObj.get("veri_check_id")==null){
				return "{\"msg\":\"贷方未进行核销，无法取消核销.\",\"state\":\"err\"}";
			}else{
				if(veri_check_id==null){
					veri_check_id=jsonObj.get("veri_check_id").toString();
				}else{
					if(!veri_check_id.equals(jsonObj.get("veri_check_id").toString())){
						return "{\"msg\":\"所选借贷方不属于同一核销序列，无法取消核销.\",\"state\":\"err\"}";
					}
				}
				if (mapRight.get(creditMap.get("credit_check_id"))== null) {
					mapRight.put(jsonObj.get("check_id").toString(), Double.parseDouble(jsonObj.get("ybal_amt").toString()));
				} else {
					mapRight.put(jsonObj.get("check_id").toString(),Double.parseDouble(mapRight.get(jsonObj.get("check_id").toString()).toString())+ Double.parseDouble(jsonObj.get("ybal_amt").toString()));
				}
				
			}
			creditList.add(creditMap);
		}

		Map<String, Object> logMap = new HashMap<String, Object>();

		logMap.put("group_id", SessionManager.getGroupId());
		logMap.put("hos_id", SessionManager.getHosId());
		logMap.put("copy_code", SessionManager.getCopyCode());
		logMap.put("acc_year", SessionManager.getAcctYear());
		logMap.put("create_user", SessionManager.getUserId());
		logMap.put("create_date", new Date());
		String message = "勾选取消科目：" + mapVo.get("subj_name").toString() + "的核销记录！";
		logMap.put("note", message);

		int count = 0;// 查看是否勾选同一时间核销的ID
		int countC = 0;
//		for (String key : mapLeft.keySet()) {
//			if (mapRight.get(key) == null) {
//				count++;
//			} else {
//				if (!mapLeft.get(key).equals(mapRight.get(key))) {
//					countC++;
//				}
//			}
//		}
		
		try {
			if (count != 0) {
				return "{\"msg\":\"取消核销失败，请勾选相对应的借贷方.\",\"state\":\"err\"}";
			} else {
				//if (countC != 0) {
				//	return "{\"msg\":\"取消核销失败，金额不相等！\",\"state\":\"err\"}";
				//} else {
					if (creditList.size() > 0&&debitList.size()>0) {
						//获取本次核销所对应的借贷方mapVo veri_check_id
						Map<String,Object> queryMap=new HashMap<String,Object>();
						queryMap.put("veri_check_id", veri_check_id);
						queryMap.put("group_id", SessionManager.getGroupId());
						queryMap.put("hos_id", SessionManager.getHosId());
						queryMap.put("copy_code", SessionManager.getCopyCode());
						queryMap.put("acc_year", SessionManager.getAcctYear());
						queryMap.put("subj_code", mapVo.get("subj_code"));
						List<Map<String,Object>> unCheckList=accVerificationMapper.queryVeriByCheckId(queryMap);
						String creditId=null;
						String debitId=null;
						List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
						Map<String, Object> tmp = null;
						for(Map<String,Object> map:unCheckList){
							creditId=map.get("CREDIT_CHECK_ID").toString();
							debitId=map.get("DEBIT_CHECK_ID").toString();
							if(mapLeft.containsKey(debitId)&&mapRight.containsKey(creditId)){
								//核销对象
								tmp=new HashMap<String,Object>();
								tmp.put("group_id", map.get("GROUP_ID"));
								tmp.put("hos_id", map.get("HOS_ID"));
								tmp.put("copy_code", map.get("COPY_CODE"));
								tmp.put("acc_year", map.get("ACC_YEAR"));
								tmp.put("bal_amt",  Double.parseDouble(map.get("VERI_MONEY").toString())*-1);
								tmp.put("vouch_check_id", creditId);
								list.add(tmp);
								tmp=new HashMap<String,Object>();
								tmp.put("group_id", map.get("GROUP_ID"));
								tmp.put("hos_id", map.get("HOS_ID"));
								tmp.put("copy_code", map.get("COPY_CODE"));
								tmp.put("acc_year", map.get("ACC_YEAR"));
								tmp.put("bal_amt", Double.parseDouble(map.get("VERI_MONEY").toString())*-1);
								tmp.put("vouch_check_id", debitId);
								list.add(tmp);		
								
							}
						}
						if(list.size()==0){
							return "{\"msg\":\"取消核销失败，没有对应的借贷方数据.\",\"state\":\"err\"}";
						}else
							accVerificationMapper.updateAccVouchCheck(list);
						
						
						
						
						//修改核算表核算金额
						//accVerificationMapper.updateAccVouchCheck(list);
						// 1、删除acc_vouch_ver 中的记录
						double jf=0.0d;
						double df=0.0d;
						List<Map<String,Object>> veriList=new ArrayList<Map<String,Object>>();
						for(Map<String,Object> mapj:debitList){
							for(Map<String,Object> mapd:creditList){
								tmp=new HashMap<String,Object>();
								tmp.put("debit_check_id", mapj.get("debit_check_id"));
								tmp.put("credit_check_id", mapd.get("credit_check_id"));
								tmp.put("veri_check_id", mapd.get("veri_check_id"));
								tmp.put("group_id", SessionManager.getGroupId());
								tmp.put("hos_id", SessionManager.getHosId());
								tmp.put("copy_code", SessionManager.getCopyCode());
								tmp.put("acc_year", SessionManager.getAcctYear());
								tmp.put("subj_code", mapd.get("subj_code"));
								jf=Double.parseDouble(mapj.get("veri_money").toString());
								df=Double.parseDouble(mapd.get("veri_money").toString());
								if(jf<df)
									tmp.put("veri_money", jf);
								else
									tmp.put("veri_money",df);
								veriList.add(tmp);
							}
						}
						accVerificationMapper.deleteAccVericationVeri(veriList);
						// 2、往acc_vouch_veri_log中插入记录
						accVerificationMapper.addAccVericationLog(logMap);

						// 3、回写辅助核算表
						
						
						return "{\"msg\":\"取消核销成功！\",\"state\":\"true\"}";
					}else{
						return "{\"msg\":\"取消核销失败！\",\"state\":\"false\"}";
					}
				//}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			// return "{\"error\":\"对账失败 数据库异常 请联系管理员! 错误编码 addAccTellVeri\"}";
		}

	}
	
	
	/**
	 * 批量核销
	 */
	@Override
	public String addBatchAccVerica(Map<String, Object> entityMap) throws DataAccessException {
		List<AccLederCheck> listLN = new ArrayList<AccLederCheck>();
		List<AccLederCheck> listRN = new ArrayList<AccLederCheck>();
		//****************  条件 begin  
		// 是否记账状态
		if (entityMap.get("is_check") != null) {
			if (entityMap.get("is_check").equals("1")) {
				entityMap.put("state", " and av.state > 0 and av.state <= 99");
			} else {
				entityMap.put("state", " and av.state = 99");
			}
		}
		
		StringBuffer check = new StringBuffer();
		StringBuffer checkCol = new StringBuffer();

		StringBuffer is_autoWhere = new StringBuffer();
		is_autoWhere.append("");
		StringBuffer tableName = new StringBuffer();

		if (entityMap.get("check_type") != null) {
			if (entityMap.get("check_type").equals("HOS_EMP_DICT")) {
				tableName.append("HOS_EMP_DICT");
				checkCol.append("b.emp_code ||' '|| b.emp_name as check_name,b.emp_id ||' '|| b.emp_no as check_code ,");
				check.append(" and avc.CHECK2_ID = b.emp_id and b.is_stop = 0 and avc.CHECK2_NO = b.emp_no ");
				if (entityMap.get("proj1").equals("")) {
					check.append("");
				} else {
					if (entityMap.get("proj2").equals("")) {
						check.append("");
					} else {
						check.append(" and b.emp_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2") + "' ");
					}
				}

			} else if (entityMap.get("check_type").equals("HOS_DEPT_DICT")) {
				tableName.append("HOS_DEPT_DICT");
				checkCol.append("b.dept_code ||' '|| b.dept_name as check_name, b.dept_id ||' '|| b.dept_no as check_code ,");
				check.append(" and avc.CHECK1_ID = b.dept_id and b.is_stop = 0 AND avc.CHECK1_NO = b.dept_no");
				if (entityMap.get("proj1").equals("")) {
					check.append("");
				} else {
					if (entityMap.get("proj2").equals("")) {
						check.append("");
					} else {
						check.append(" and b.dept_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2") + "'");
					}
				}

			} else if (entityMap.get("check_type").equals("HOS_PROJ_DICT")) {
				tableName.append("HOS_PROJ_DICT");
				checkCol.append("b.proj_code ||' '|| b.proj_name as check_name,b.proj_id ||' '|| b.proj_no as check_code,");
				check.append(" and avc.CHECK3_ID = b.proj_id and b.is_stop = 0 and avc.CHECK3_NO = b.proj_no");
				if (entityMap.get("proj1").equals("")) {
					check.append("");
				} else {
					if (entityMap.get("proj2").equals("")) {
						check.append("");
					} else {
						check.append(" and b.proj_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2") + "' ");
					}
				}

			} else if (entityMap.get("check_type").equals("HOS_STORE_DICT")) {
				tableName.append("HOS_STORE_DICT");
				checkCol.append("b.store_code ||' '|| b.store_name as check_name , b.store_id ||' '|| b.store_no as check_code, ");
				check.append(" and avc.CHECK4_ID = b.store_id and b.is_stop = 0 and avc.CHECK4_NO =b.store_no");
				if (entityMap.get("proj1").equals("")) {
					check.append("");
				} else {
					if (entityMap.get("proj2").equals("")) {
						check.append("");
					} else {
						check.append(" and b.store_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2") + "' ");
					}
				}

			} else if (entityMap.get("check_type").equals("HOS_CUS_DICT")) {
				tableName.append("HOS_CUS_DICT");
				checkCol.append("b.cus_code ||' '|| b.cus_name as check_name, b.cus_id ||' '|| b.cus_no as check_code, ");
				check.append(" and avc.CHECK5_ID = b.cus_id and b.is_stop = 0 and avc.CHECK5_NO = b.cus_no");
				if (entityMap.get("proj1").equals("")) {
					check.append("");
				} else {
					if (entityMap.get("proj2").equals("")) {
						check.append("");
					} else {
						check.append(" and b.cus_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2") + "' ");
					}
				}

			} else if (entityMap.get("check_type").equals("HOS_SUP_DICT")) {
				tableName.append("HOS_SUP_DICT");
				checkCol.append("b.sup_code ||' '|| b.sup_name as check_name, b.sup_id ||' '|| b.sup_no as check_code, ");
				check.append(" and avc.CHECK6_ID = b.sup_id and b.is_stop = 0 and avc.CHECK6_NO = b.sup_no");
				if (entityMap.get("proj1").equals("")) {
					check.append("");
				} else {
					if (entityMap.get("proj2").equals("")) {
						check.append("");
					} else {
						check.append(" and b.sup_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2") + "' ");
					}
				}

			} else if (entityMap.get("check_type").equals("HOS_SOURCE")) {
				tableName.append("HOS_SOURCE");
				checkCol.append("b.source_code ||' '|| b.source_name as check_name, b.source_id ||' '|| b.source_no as check_code,");
				check.append(" and avc.CHECK7_ID = b.source_id and b.is_stop=0 ");
				if (entityMap.get("proj1").equals("")) {
					check.append("");
				} else {
					if (entityMap.get("proj2").equals("")) {
						check.append("");
					} else {
						check.append(" and b.source_code between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2") + "' ");
					}
				}
			} else if (entityMap.get("check_type").equals("ACC_CHECK_ITEM")) {// 自定义核算
				tableName.append("ACC_CHECK_ITEM");
				checkCol.append("b.CHECK_ITEM_CODE ||' '|| b.CHECK_ITEM_NAME as check_name,b.CHECK_ITEM_CODE ||' '|| b.CHECK_ITEM_NAME as check_code,");
				// 首先查一下自定义辅助核算挂在哪一个下面
				String column_check = "";
				Map<String, Object> mapVo1 = new HashMap<String, Object>();
				mapVo1.put("group_id", SessionManager.getGroupId());
				mapVo1.put("hos_id", SessionManager.getHosId());
				mapVo1.put("copy_code", SessionManager.getCopyCode());

				mapVo1.put("check_type_id", entityMap.get("check_type_id"));
				column_check = queryColumnName(mapVo1);
				if (entityMap.get("proj1").equals("")) {
					check.append("");
				} else {
					if (entityMap.get("proj2").equals("")) {
						check.append("");
					} else {
						check.append(" and avc." + column_check + "  between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2") + "'");

					}
				}
			}
		}
		entityMap.put("tableName", tableName);
		entityMap.put("checkCol", checkCol);
		entityMap.put("check", check);

		List<AccLederCheck> listL = accVerificationMapper.queryAccVerificationL(entityMap);// 左侧结果集
		List<AccLederCheck> listR = accVerificationMapper.queryAccVerificationR(entityMap);// 右侧结果集

		Map<String, AccLederCheck> mapL = new HashMap<String, AccLederCheck>();// 存放比较键
		int lnum = 1;// 用于处理重复的添加不上去问题；
		// 处理左侧结果集
		for (AccLederCheck acl : listL) {
			StringBuffer condition = new StringBuffer();
			condition.append(String.valueOf(lnum) + '@');
			// 条件判断 帐套+年
			condition.append((String.valueOf(acl.getGroup_id()) + String.valueOf(acl.getHos_id()) + acl.getCopy_code() + acl.getAcc_year()).toString());
			// 未核销金额
			if (entityMap.get("wbal_amt_check").equals("1")) {
				condition.append(acl.getWbal_amt());
			}
			// 项目
			if (entityMap.get("check_code_check").equals("1")) {
				condition.append(acl.getCheck_code());
			}
			// 合同号
			if (entityMap.get("con_no_check").equals("1")) {
				condition.append(acl.getCon_no());
			}
			// 单据号
			if (entityMap.get("business_no_check").equals("1")) {
				condition.append(acl.getBusiness_no());
			}
			// 发生日期
			if (entityMap.get("occur_date_check").equals("1")) {
				condition.append(acl.getOccur_date());
			}
			// 到期日期
			if (entityMap.get("due_date_check").equals("1")) {
				condition.append(acl.getDue_date());
			}
			// 摘要
			if (entityMap.get("summary_check").equals("1")) {
				condition.append(acl.getSummary());
			}

			String key = condition.toString();
			mapL.put(key, acl);
			lnum++;
		}

		Map<String, AccLederCheck> mapR = new HashMap<String, AccLederCheck>();// 存放比较键
		int rnum = 1;// 用于处理重复的添加不上去问题；
		// 处理右侧结果集
		for (AccLederCheck acr : listR) {
			StringBuffer conditionR = new StringBuffer();
			conditionR.append(String.valueOf(rnum) + '@');
			// 条件判断 帐套+年
			conditionR.append((String.valueOf(acr.getGroup_id()) + String.valueOf(acr.getHos_id()) + acr.getCopy_code() + acr.getAcc_year()).toString());
			// 未核销金额
			if (entityMap.get("wbal_amt_check").equals("1")) {
				conditionR.append(acr.getWbal_amt());
			}
			// 项目
			if (entityMap.get("check_code_check").equals("1")) {
				conditionR.append(acr.getCheck_code());
			}
			// 合同号
			if (entityMap.get("con_no_check").equals("1")) {
				conditionR.append(acr.getCon_no());
			}
			// 单据号
			if (entityMap.get("business_no_check").equals("1")) {
				conditionR.append(acr.getBusiness_no());
			}
			// 发生日期
			if (entityMap.get("occur_date_check").equals("1")) {
				conditionR.append(acr.getOccur_date());
			}
			// 到期日期
			if (entityMap.get("due_date_check").equals("1")) {
				conditionR.append(acr.getDue_date());
			}
			// 摘要
			if (entityMap.get("summary_check").equals("1")) {
				conditionR.append(acr.getSummary());
			}

			String key = conditionR.toString();
			mapR.put(key, acr);
			rnum++;
		}

		List<Map<String, Object>> vList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> rList = new ArrayList<Map<String, Object>>();
		// 对应关系开始
		int a = 0;
		Iterator itl = mapL.keySet().iterator();
		while (itl.hasNext()) {
			a++;
			String key = String.valueOf(itl.next());
			String keyl = key.split("@")[1];

			Iterator itr = mapR.keySet().iterator();
			while (itr.hasNext()) {
				String key2 = String.valueOf(itr.next());
				String keyr = key2.split("@")[1];
				if (keyr.equals(keyl)) {
					AccLederCheck arL = mapL.get(key);
					AccLederCheck arR = mapR.get(key2);
					// 左右两侧均为对账
					if (!"0".equals(String.valueOf(arL.getWbal_amt()))) {
						if (!"0".equals(String.valueOf(arR.getWbal_amt()))) {
							Map<String, Object> vmap = new HashMap<String, Object>();
							Map<String, Object> lmap = new HashMap<String, Object>();
							Map<String, Object> rmap = new HashMap<String, Object>();

							if (arL.getWbal_amt().equals(arR.getWbal_amt())) {
								Date date = new Date();
								SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
								String dateString = formatter.format(new Date());
								Long veri_check_id = Long.parseLong(dateString + a);

								vmap.put("veri_check_id", veri_check_id);
								vmap.put("group_id", entityMap.get("group_id"));
								vmap.put("hos_id", entityMap.get("hos_id"));
								vmap.put("copy_code", entityMap.get("copy_code"));
								vmap.put("acc_year", entityMap.get("acc_year"));
								vmap.put("create_user", SessionManager.getUserId());
								vmap.put("create_date", date);
								vmap.put("veri_money", arL.getWbal_amt());
								vmap.put("veri_money_w", "0");

								vmap.put("debit_check_id", arL.getCheck_id());
								vmap.put("credit_check_id", arR.getCheck_id());

								lmap.put("group_id", entityMap.get("group_id"));
								lmap.put("hos_id", entityMap.get("hos_id"));
								lmap.put("copy_code", entityMap.get("copy_code"));
								lmap.put("acc_year", entityMap.get("acc_year"));

								rmap.putAll(lmap);

								lmap.put("vouch_check_id", arL.getCheck_id());
								lmap.put("bal_amt", arL.getWbal_amt());

								rmap.put("vouch_check_id", arR.getCheck_id());
								rmap.put("bal_amt", arR.getWbal_amt());
								
								arL.setWbal_amt("0");
								arR.setWbal_amt("0");

								vList.add(vmap);
								lList.add(lmap);
								rList.add(rmap);
							}
						}
					}

				}
			}
		}
		// logMap
		Map<String, Object> logMap = new HashMap<String, Object>();
		logMap.put("group_id", entityMap.get("group_id"));
		logMap.put("hos_id", entityMap.get("hos_id"));
		logMap.put("copy_code", entityMap.get("copy_code"));
		logMap.put("acc_year", entityMap.get("acc_year"));
		logMap.put("create_user", entityMap.get("user_id"));
		logMap.put("create_date", new Date());
		String message = "批量取消  科目：" + entityMap.get("subj_name").toString() + " 在  " + entityMap.get("begin_date").toString() + " 至 "
				+ entityMap.get("end_date").toString() + " 时间范围内 " + entityMap.get("user_name") + " 的核销记录！";
		logMap.put("note", "记录 " + entityMap.get("subj_name").toString() + " 的对账记录！");

		try {
			if (vList.size() > 0) {
				// 1、插入核销表
				accVerificationMapper.addAccVericationVer(vList);
				// 2、更新借方数据 核销金额
				if (lList.size() > 0) {
					accVerificationMapper.updateAccVouchCheck(lList);
				}
				// 3、更新贷方数据 核销金额
				if (rList.size() > 0) {
					accVerificationMapper.updateAccVouchCheck(rList);
				}
				// 4、插入日志表
				accVerificationMapper.addAccVericationLog(logMap);

				return "{\"msg\":\"核销成功！\",\"state\":\"true\"}";
			} else {
				return "{\"msg\":\"核销失败，此期间没有该会计科目需要核销的数据！\",\"state\":\"false\"}";
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			// return "{\"error\":\"对账失败 数据库异常 请联系管理员! 错误编码 addAccTellVeri\"}";
		}
	}

	/**
	 * 批量取消核销数据
	 */
	@Override
	public String deleteBatchAccVeri(Map<String, Object> entityMap) throws DataAccessException {
		try {
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put("group_id", entityMap.get("group_id"));
			logMap.put("hos_id", entityMap.get("hos_id"));
			logMap.put("copy_code", entityMap.get("copy_code"));
			logMap.put("acc_year", entityMap.get("acc_year"));
			logMap.put("create_user", SessionManager.getUserId());
			logMap.put("create_date", new Date());
			String message = "批量取消  科目：" + entityMap.get("subj_name").toString() + " 在  " + entityMap.get("begin_date").toString() + " 至 "
					+ entityMap.get("end_date").toString() + " 时间范围内 " + entityMap.get("user_name") + " 的核销记录！";
			logMap.put("note", message);

			entityMap.put("create_user", SessionManager.getUserId());

			// 1、首次查看再选定的时间范围内是否有核销记录
			List<Map<String, Object>> list = ChdJson.toListLower(accVerificationMapper.queryAccVericationCount(entityMap));
			if (list.size() > 0) {
				// 1、辅助核算表中的数据清空
				accVerificationMapper.updateAccVouchCheck(list);
				// 2、删除acc_vouch_veri中的记录 借、贷
				accVerificationMapper.detelBatchAccVeriByTime(entityMap);
				// 3、往acc_vouch_veri_log中插入记录
				accVerificationMapper.addAccVericationLog(logMap);
				return "{\"msg\":\"删除成功！\",\"state\":\"true\"}";
			} else {
				return "{\"msg\":\"删除失败，此期间没有该会计科目的核销记录！\",\"state\":\"false\"}";
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			// return "{\"error\":\"对账失败 数据库异常 请联系管理员! 错误编码 addAccTellVeri\"}";
		}
		
	}

	/**
	 * 往来核销 已核销数据查询
	 */
	@Override
	public String queryAccVerDetail(Map<String, Object> entityMap)throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());

		StringBuffer columnName= new StringBuffer();
        StringBuffer  joinSql = new StringBuffer();
        if(entityMap.get("check_type")!=null && !"".equals(entityMap.get("check_type").toString())){
        	//查看当前会计科目挂哪个辅助核算
			Map<String, Object> sqlMap = accNostroMapper.querySubjCheckColumnBySubj(entityMap);
			columnName.append(" dd.").append(sqlMap.get("COLUMN_CODE")).append(" ||' '|| dd.").append(sqlMap.get("COLUMN_NAME").toString()).append(" as check_code,");
		     
			//是否是变更表
		    if(sqlMap.get("CHECK_TABLE")!=null && !"".equals(sqlMap.get("CHECK_TABLE").toString())){
		    	if(sqlMap.get("IS_CHANGE") != null &&  "1".equals(sqlMap.get("IS_CHANGE").toString())){
		    		
		    		joinSql.append("left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd ");
		    		joinSql.append(" on dd.group_id = avc.group_id and dd.hos_id = avc.hos_id  and dd.").append(sqlMap.get("COLUMN_ID")).append("= avc.").append(sqlMap.get("COLUMN_CHECK")).append("_ID");
		    		joinSql.append(" and dd.").append(sqlMap.get("COLUMN_NO")).append(" = avc.").append(sqlMap.get("COLUMN_CHECK").toString()).append("_NO");	
		    	}else{
			    	//自定义辅助核算
			        if(sqlMap.get("CHECK_TABLE").equals("ACC_CHECK_ITEM")){ 
			        	joinSql.append(" left join acc_check_item dd on dd.group_id = avc.group_id and dd.hos_id = avc.hos_id and dd.copy_code = avc.copy_code and dd.").append(sqlMap.get("COLUMN_ID")).append(" = avc.").append(sqlMap.get("COLUMN_CHECK"));
			        }else{
				       	//资金来源、单位
			        	joinSql.append("left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd ");
			        	joinSql.append(" on dd.group_id = avc.group_id and dd.hos_id = avc.hos_id and dd.").append(sqlMap.get("COLUMN_ID")).append("= avc.").append(sqlMap.get("COLUMN_CHECK")).append("_ID");
			        }	
		    	}   
		    }
        }else{
        	columnName.append("");
        	joinSql.append("");
	    }
        entityMap.put("columnName", columnName.toString());
        entityMap.put("joinSql", joinSql.toString());
        List<AccLederCheck> list = new ArrayList<AccLederCheck>();
        
        if(entityMap.get("subj_dire").equals("0")){
        	//借方
        	list = accVerificationMapper.queryAccVerDetailJ(entityMap, rowBounds);
		}else{
			//贷方
			list = accVerificationMapper.queryAccVerDetailD(entityMap, rowBounds);
		}

		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	/**
	 * 往来管理--应付账龄分析
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectReceivableAccount(Map<String, Object> entityMap) throws DataAccessException {
		
		String check_table = "";//辅助核算表
		String column_no = "";//辅助核算字段NO
		String column_id = "";//辅助核算字段ID
		String column_code = "";//辅助核算字段Code
		String column_name = "";//辅助核算字段name
		String column_check = "";//自定义辅助核算项
		String is_change ="";//是否变更表
		
		
		if(entityMap.get("check_type")!=null && !"".equals(entityMap.get("check_type").toString())){
			//查看当前会计科目挂哪个辅助核算
			Map<String, Object> sqlMap = accNostroMapper.querySubjCheckColumnBySubj(entityMap);
			check_table = sqlMap.get("CHECK_TABLE").toString();
			if(sqlMap.get("IS_CHANGE") != null &&  "1".equals(sqlMap.get("IS_CHANGE").toString())){
				if(sqlMap.containsKey("COLUMN_ID")){
					column_id = sqlMap.get("COLUMN_ID").toString();
				}
				if(sqlMap.containsKey("COLUMN_NO")){
					column_no = sqlMap.get("COLUMN_NO").toString();
				}
			}else{
				if(sqlMap.containsKey("COLUMN_ID")){
					column_id = sqlMap.get("COLUMN_ID").toString();
					column_no = "";
				}
			}
			
			column_code = sqlMap.get("COLUMN_CODE").toString();
			column_name = sqlMap.get("COLUMN_NAME").toString();
			column_check = sqlMap.get("COLUMN_CHECK").toString();
			is_change = sqlMap.get("IS_CHANGE").toString();
			
	    }
		
		entityMap.put("check_table", check_table);
		entityMap.put("column_no", column_no);
		entityMap.put("column_id", column_id);
		entityMap.put("column_code", column_code);
		entityMap.put("column_name", column_name);
		entityMap.put("column_check", column_check);
		entityMap.put("is_change", is_change);
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		
		accVerificationMapper.queryReceivableAccount(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}
	
	/**
	 * 查询会计科目  带方向
	 * @param mapVo
	 * @return
	 */
	@Override
	public String querySubjDirect(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(accVerificationMapper.querySubjDirect(mapVo, rowBounds));
	}
	
	/**
	 * 查询会计科目  带方向
	 * @param mapVo
	 * @return
	 */
	@Override
	public String querySubjIsCheck(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(accVerificationMapper.querySubjIsCheck(mapVo, rowBounds));
	}
	
	/**
	 * 应收账龄分析  打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectReceivableAccountPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		String check_table = "";//辅助核算表
		String column_no = "";//辅助核算字段NO
		String column_id = "";//辅助核算字段ID
		String column_code = "";//辅助核算字段Code
		String column_name = "";//辅助核算字段name
		String column_check = "";//自定义辅助核算项
		String is_change ="";//是否变更表
		
		
		if(entityMap.get("check_type")!=null && !"".equals(entityMap.get("check_type").toString())){
			
			//查看当前会计科目挂哪个辅助核算
			Map<String, Object> sqlMap = accNostroMapper.querySubjCheckColumnBySubj(entityMap);
			check_table = sqlMap.get("CHECK_TABLE").toString();
			if(sqlMap.get("IS_CHANGE") != null &&  "1".equals(sqlMap.get("IS_CHANGE").toString())){
				if(sqlMap.containsKey("COLUMN_ID")){
					column_id = sqlMap.get("COLUMN_ID").toString();
				}
				if(sqlMap.containsKey("COLUMN_NO")){
					column_no = sqlMap.get("COLUMN_NO").toString();
				}
			}else{
				if(sqlMap.containsKey("COLUMN_ID")){
					column_id = sqlMap.get("COLUMN_ID").toString();
					column_no = "";
				}
			}
			
			
			column_code = sqlMap.get("COLUMN_CODE").toString();
			column_name = sqlMap.get("COLUMN_NAME").toString();
			column_check = sqlMap.get("COLUMN_CHECK").toString();
			is_change = sqlMap.get("IS_CHANGE").toString();
			
	    }
		
		entityMap.put("check_table", check_table);
		entityMap.put("column_no", column_no);
		entityMap.put("column_id", column_id);
		entityMap.put("column_code", column_code);
		entityMap.put("column_name", column_name);
		entityMap.put("column_check", column_check);
		entityMap.put("is_change", is_change);
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accVerificationMapper.queryReceivableAccount(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> list = (List<Map<String, Object>>)entityMap.get("rst");
		
		//reMap.put("detail", (List<Map<String, Object>>)entityMap.get("rst"));
		
		return list;
	}

	@Override
	public String queryVeriCheckId(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return accVerificationMapper.queryVeriCheckId(entityMap);
	}

	
	
}
