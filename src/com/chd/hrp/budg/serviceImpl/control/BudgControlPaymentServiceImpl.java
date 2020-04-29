package com.chd.hrp.budg.serviceImpl.control;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chd.hrp.budg.dao.control.BudgControlPaymentMapper;
import com.chd.hrp.budg.dao.control.BudgControlPlanMapper;
import com.chd.hrp.budg.service.control.BudgControlPaymentService;

@Service("budgControlPaymentService")
public class BudgControlPaymentServiceImpl implements BudgControlPaymentService {


	@Resource(name = "budgControlPlanMapper")
	private final BudgControlPlanMapper budgControlPlanMapper = null;
	
	@Resource(name = "budgControlPaymentMapper")
	private final BudgControlPaymentMapper budgControlPaymentMapper = null;


	/**
	 *  work_link_code: 'MAT_OUT'   --  控制环节 “材料领用出库”
        work_note_code: '1'   --控制节点  "新建" 新增保存
        work_select_id: '',   --单据主键  -- out_id 值
        work_update_id:'',  --修改单据主键为空
        work_proj_flag:'', --是否项目  只有支出费用使用 为空  
        work_dept_id : '5'  --取单据 科室Id
        work_proj_id : ''  --取单据 项目Id
        work_detail_data:
        		JSON.stringify( [{ work_proj_id: '', work_item_code:'1',work_value:'10.00'｝， { work_proj_id: '', work_item_code:'1',work_value:10.00｝]) --明细数据
                 --work_item_code  材料领用  inv_ id 材料id，
                 --work_value      单据发生值   amount_money 材料金额
                 --work_proj_id  项目id , 材料领用 不用 
          }
	 */
	@Override
	public String control(Map<String, Object> map) {
		String result="{\"work_msg\":\"没有控制方案\",\"work_flag\":0}";
		//1.根据work_link_code,work_note_code查找控制方案
		List<Map<String,Object>> list=budgControlPlanMapper.queryControlPlan(map);
		Map<String,Object> tmplanMap=null;
		List<String> applyCodes=null;
		if(map.get("work_select_id")!=null)
			applyCodes=Arrays.asList(map.get("work_select_id").toString().split(","));
		if(applyCodes==null||applyCodes.size()==0){
			return "{\"work_msg\":\"数据格式错误\",\"work_flag\"3\"}";
		}
		int r=0;
		for(Map<String,Object> controlPlan:list){//循环处理控制方案
			//关联方案控制？？？？？？？？？？？？
			tmplanMap=convertMapCase(controlPlan);
			tmplanMap.put("applyCodes",applyCodes);
			if(tmplanMap.get("cont_m").toString().equals("01")){//主表控制
				result=controlByMainForProjYear(tmplanMap);
			}else if(tmplanMap.get("cont_m").toString().equals("02")){//如果是明细控制，按明细数据处理
				if(tmplanMap.get("tab_code").toString().equals("BUDG_MED_COST")){
					result=controlByDetailForMedCost(tmplanMap);
				}else if(tmplanMap.get("tab_code").toString().equals("BUDG_EXPENSE_APPLY")){
					result=controlByDetailForExpenseApply(tmplanMap);
				}
			}		
			r=JSONObject.parseObject(result).getIntValue("work_flag");
			if(r==4)
				break;
		}
		return result;
	}
	
	/**
	 * med_cost的明细控制
	 * @param mapVo 参数为控制方案主表信息
	 * @return “XX方案" ，"XX科室" ，"XX项目" ,“XX预算项” , 预算值："XX"，已发生值："XX"，单据值: "XX",“XX控制方式" 
	 */
	private String controlByDetailForExpenseApply(Map<String,Object> mapVo){
		int work_flag=1;
		String work_msg="不控制";
		String plan=null;
		String dept=null;
		String item=null;
		String apply_code=null;
		double budgValue=0;
		double hisValue=0;
		List<Map<String,Object>> listDetail=budgControlPlanMapper.queryControlDetail(mapVo);//获取控制方案明细
		List<Map<String,Object>> userData=null;
		Map<String,Object> budgValueMap=new HashMap<String,Object>();
		String cont_w=null;
		cont_list:
		for(Map<String,Object> contDMap:listDetail){//循环验证明细控制方案是否通过
			cont_w=contDMap.get("cont_w").toString();
			if("01".equals(cont_w)){//不控制
				continue;
			}else{
				work_flag=1;
				work_msg="未超出预算";
				contDMap.put("group_id", mapVo.get("group_id"));
				contDMap.put("hos_id", mapVo.get("hos_id"));
				contDMap.put("copy_code", mapVo.get("copy_code"));
				contDMap.put("apply_codes", mapVo.get("work_select_id"));
				userData=budgControlPaymentMapper.queryUserDataValueForDetail(contDMap);//根据组装控制条件获取用户不同维度数据					
				for(Map<String,Object> userDMap:userData){
					if(userDMap.get("payment_item_id").equals(contDMap.get("item_code"))){//用户数据存在控制项
						//获取该预算条件预算值
						budgValueMap.put("group_id", mapVo.get("group_fid"));
						budgValueMap.put("hos_id", mapVo.get("hos_id"));
						budgValueMap.put("copy_code", mapVo.get("copy_code"));
						budgValueMap.put("budg_year", mapVo.get("budg_year"));
						budgValueMap.put("payment_item_id",userDMap.get("payment_item_id"));
						if(contDMap.get("cont_l").toString().equals("03"))
							budgValueMap.put("dept_id", userDMap.get("dept_id"));
						if(contDMap.get("cont_p").toString().equals("03"))
							budgValueMap.put("month", userDMap.get("month"));
						budgValue=budgControlPaymentMapper.queryBudgValueForExpenseApply(budgValueMap);//获取预算值
						hisValue=budgControlPaymentMapper.queryExeDataValueForDetail(budgValueMap);//获取历史数据值
						if(budgValue<hisValue+Double.parseDouble(userDMap.get("amount").toString())){
							if("02".equals(cont_w)){
								work_flag=3;
								plan=mapVo.get("plan_code").toString();
								work_msg=plan+"方案,";
								if(contDMap.get("cont_l").toString().equals("03")){
									dept=userDMap.get("dept_id").toString();
									work_msg+=dept+"科室,";
								}
								item=contDMap.get("item_code").toString();
								work_msg+=item+"预算项,";
								work_msg+="预算值："+budgValue+",";
								work_msg+="已发生值:"+hisValue;
								work_msg+="单据值:"+apply_code+"提示控制";
								break;
							}else if("03".equals(cont_w)){
								work_flag=4;
								plan=mapVo.get("plan_code").toString();
								work_msg=plan+"方案,";
								if(contDMap.get("cont_l").toString().equals("03")){
									dept=userDMap.get("dept_id").toString();
									work_msg+=dept+"科室,";
								}
								item=contDMap.get("item_code").toString();
								work_msg+=item+"预算项,";
								work_msg+="预算值："+budgValue+",";
								work_msg+="已发生值:"+hisValue;
								work_msg+="单据值:"+apply_code+"提示控制";
								break cont_list;
							}
						}
					}
				}
			}
		}
		return "{\"work_msg\":\""+work_msg+"\",\"work_flag\":"+work_flag+"}";
	}
	/**
	 * med_cost的明细控制
	 * @param mapVo 参数为控制方案主表信息
	 * @return
	 */
	private String controlByDetailForMedCost(Map<String,Object> mapVo){
		int work_flag=1;
		String work_msg="不控制";
		String plan=null;
		String dept=null;
		String item=null;
		String apply_code=null;
		double budgValue=0;
		double hisValue=0;
		List<Map<String,Object>> listDetail=budgControlPlanMapper.queryControlDetail(mapVo);//获取控制方案明细
		List<Map<String,Object>> userData=null;
		Map<String,Object> budgValueMap=new HashMap<String,Object>();
		String cont_w=null;
		cont_list:
		for(Map<String,Object> contDMap:listDetail){//循环验证明细控制方案是否通过
			cont_w=contDMap.get("cont_w").toString();
			if("01".equals(cont_w)){//不控制
				continue;
			}else{
				work_flag=1;
				work_msg="数据未有超过预算的";
				contDMap.put("group_id", mapVo.get("group_id"));
				contDMap.put("hos_id", mapVo.get("hos_id"));
				contDMap.put("copy_code", mapVo.get("copy_code"));
				contDMap.put("applyCodes",mapVo.get("applyCodes"));
				userData=budgControlPaymentMapper.queryUserDataValueForDetail(contDMap);//根据组装控制条件获取用户不同维度数据					
				for(Map<String,Object> userDMap:userData){
					if(userDMap.get("payment_item_id").equals(contDMap.get("item_code"))){//用户数据存在控制项
						//获取该预算条件预算值
						budgValueMap.put("group_id", mapVo.get("group_id"));
						budgValueMap.put("hos_id", mapVo.get("hos_id"));
						budgValueMap.put("copy_code", mapVo.get("copy_code"));
						budgValueMap.put("budg_year", mapVo.get("budg_year"));
						budgValueMap.put("payment_item_id",userDMap.get("payment_item_id"));
						if(contDMap.get("cont_l").toString().equals("03"))
							budgValueMap.put("dept_id", userDMap.get("dept_id"));
						if(contDMap.get("cont_p").toString().equals("03"))
							budgValueMap.put("month", userDMap.get("month"));
						budgValue=budgControlPaymentMapper.queryBudgValueForMedCost(budgValueMap);//获取预算值
						hisValue=budgControlPaymentMapper.queryExeDataValueForMedCost(budgValueMap);//获取历史数据值
						if(budgValue<hisValue+Double.parseDouble(userDMap.get("amount").toString())){
							if("02".equals(cont_w)){
								work_flag=3;
								plan=mapVo.get("plan_code").toString();
								work_msg=plan+"方案,";
								if(contDMap.get("cont_l").toString().equals("03")){
									dept=userDMap.get("dept_id").toString();
									work_msg+=dept+"科室,";
								}
								item=contDMap.get("item_code").toString();
								work_msg+=item+"预算项,";
								work_msg+="预算值："+budgValue+",";
								work_msg+="已发生值:"+hisValue;
								work_msg+="单据值:"+apply_code+"提示控制";
								break;
							}else if("03".equals(cont_w)){
								work_flag=4;
								plan=mapVo.get("plan_code").toString();
								work_msg=plan+"方案,";
								if(contDMap.get("cont_l").toString().equals("03")){
									dept=userDMap.get("dept_id").toString();
									work_msg+=dept+"科室,";
								}
								item=contDMap.get("item_code").toString();
								work_msg+=item+"预算项,";
								work_msg+="预算值："+budgValue+",";
								work_msg+="已发生值:"+hisValue;
								work_msg+="单据值:"+apply_code+"提示控制";
								break cont_list;
							}
						}
					}
				}
			}
		}
		return "{\"work_msg\":\""+work_msg+"\",\"work_flag\":"+work_flag+"}";
	}
	/**
	 * 主表控制逻辑
	 * @param mapVo 参数为主表控制方案数据
	 * @return
	 */
	private String controlByMainForProjYear(Map<String,Object> mapVo){
		int work_flag=1;
		String work_msg="不控制";
		String plan=null;
		String dept=null;
		String item=null;
		String apply_code=null;
		double budgValue=0;
		double hisValue=0;
		String cont_w=mapVo.get("cont_w").toString();
		List<Map<String,Object>> userData=budgControlPaymentMapper.queryUserDataValueForMain(mapVo);//根据组装控制条件获取用户不同维度数据
		if("02".equals(cont_w)){//提示不严格控制		
			work_flag=1;
			work_msg="数据未有超过预算的";
			for(Map<String,Object> mapm:userData){
				//2.获取预算可用余额
				hisValue=budgControlPaymentMapper.queryBudgValueForProjYear(mapm);
				if(hisValue<Double.parseDouble(mapm.get("amount").toString())){
					work_flag=3;
					plan=mapVo.get("plan_code").toString();
					work_msg=plan+"方案,";
					if(mapVo.get("cont_l").toString().equals("03")){
						dept=mapVo.get("dept_id").toString();
						work_msg+=dept+"科室,";
					}
					item=mapVo.get("item_code").toString();
					work_msg+=item+"预算项,";
					work_msg+="预算值："+budgValue+",";
					work_msg+="已发生值:"+hisValue;
					work_msg+="单据值:"+apply_code+"提示控制";
					break;
				}
			}
		}else if("03".equals(cont_w)){//严格控制	
			work_flag=1;
			work_msg="数据未有超过预算的";
			for(Map<String,Object> mapm:userData){
				mapm.put("budg_year", mapVo.get("budg_year"));
				hisValue=budgControlPaymentMapper.queryBudgValueForProjYear(mapm);
				if(hisValue<Double.parseDouble(mapm.get("amount").toString())){
					work_flag=4;
					plan=mapVo.get("plan_code").toString();
					work_msg=plan+"方案,";
					if(mapVo.get("cont_l").toString().equals("03")){
						dept=mapVo.get("dept_id").toString();
						work_msg+=dept+"科室,";
					}
					item=mapVo.get("item_code").toString();
					work_msg+=item+"预算项,";
					work_msg+="预算值："+budgValue+",";
					work_msg+="已发生值:"+hisValue;
					work_msg+="单据值:"+apply_code+"提示控制";
					break;
				}
			}
		}
		return "{\"work_msg\":\""+work_msg+"\",\"work_flag\":"+work_flag+"}";
	}
	private Map<String,Object> convertMapCase(Map<String,Object> map){
		Map<String,Object> nmap=new HashMap<String,Object>();
		Iterator<String> it=map.keySet().iterator();
		String tmpKey=null;
		while(it.hasNext()){
			tmpKey=it.next().toString();
			nmap.put(tmpKey.toLowerCase(), map.get(tmpKey));
		}
		return nmap;
	}
	

}
