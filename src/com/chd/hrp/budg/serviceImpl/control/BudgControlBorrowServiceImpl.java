package com.chd.hrp.budg.serviceImpl.control;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chd.hrp.budg.dao.control.BudgControlBorrowMapper;
import com.chd.hrp.budg.dao.control.BudgControlPlanMapper;
import com.chd.hrp.budg.service.control.BudgControlBorrowService;

@Service("budgControlBorrowService")
public class BudgControlBorrowServiceImpl implements BudgControlBorrowService{
	
	@Resource(name = "budgControlPlanMapper")
	private final BudgControlPlanMapper budgControlPlanMapper = null;
	@Resource(name = "budgControlBorrowMapper")
	private final BudgControlBorrowMapper budgControlBorrowMapper = null;

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
		//0.解析参数，明细数据
		//select * from budg_c_main where link_code in ('BUDG_PAYMEN','BUDG_CHARGE');
		//2.获取控制节点的控制方案
		//3.根据获取预算值
		String flag="0";
		//1.根据work_link_code,work_note_code查找控制方案
		List<Map<String,Object>> list=budgControlPlanMapper.queryControlPlan(map);
		Map<String,Object> budgValueMap=new HashMap<String,Object>();
		double budgValue=0,hisValue=0;
		Map<String,Object> tmplanMap=null;
		for(Map<String,Object> controlPlan:list){//循环处理控制方案
			//关联方案控制？？？？？？？？？？？？
			tmplanMap=convertMapCase(controlPlan);
			cont_list:
			if(tmplanMap.get("cont_m").toString().equals("02")){//如果是明细控制，按明细数据处理
				List<Map<String,Object>> listDetail=budgControlPlanMapper.queryControlDetail(tmplanMap);//获取控制方案明细
				for(Map<String,Object> contDMap:listDetail){//循环验证明细控制方案是否通过
					if(contDMap.get("cont_w").toString().equals("03")){//不控制
							continue;
					}
					contDMap.put("group_id", map.get("group_id"));
					contDMap.put("hos_id", map.get("hos_id"));
					contDMap.put("copy_code", map.get("copy_code"));
					contDMap.put("apply_codes", map.get("work_select_id"));
					List<Map<String,Object>> userData=budgControlBorrowMapper.queryUserDataValue(contDMap);//根据组装控制条件获取用户不同维度数据
					for(Map<String,Object> userDMap:userData){
						if(userDMap.get("payment_item_id").equals(contDMap.get("item_code"))){//用户数据存在控制项
							//获取该预算条件预算值
							budgValueMap.put("group_id", map.get("group_id"));
							budgValueMap.put("hos_id", map.get("hos_id"));
							budgValueMap.put("copy_code", map.get("copy_code"));
							budgValueMap.put("budg_year", map.get("budg_year"));
							budgValueMap.put("payment_item_id",userDMap.get("payment_item_id"));
							if(map.get("cont_l").toString().equals("03"))
								budgValueMap.put("dept_id", userDMap.get("dept_id"));
							if(map.get("cont_p").toString().equals("03"))
								budgValueMap.put("month", userDMap.get("month"));
							budgValue=budgControlBorrowMapper.queryBudgValue(budgValueMap);//获取预算值
							hisValue=budgControlBorrowMapper.queryExeDataValue(budgValueMap);//获取历史数据值
							if(budgValue<hisValue+Double.parseDouble(userDMap.get("amount").toString())){
								if(contDMap.get("cont_w").toString().equals("01")){
									flag="4";
									break cont_list;
								}else if(contDMap.get("cont_w").toString().equals("02")){
										flag="3";
								}else{
									flag="1";
								}
							}
						}
					}
				}
			}
		}
		return flag;
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
