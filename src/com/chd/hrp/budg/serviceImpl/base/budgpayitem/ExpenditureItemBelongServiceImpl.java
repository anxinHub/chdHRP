/** 
 * @Description:
 * @Copyright: Copyright (c) 2018-4-24 10:52:44
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.budg.serviceImpl.base.budgpayitem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.budg.dao.base.budgpayitem.ExpenditureItemBelongMapper;
import com.chd.hrp.budg.service.base.budgpayitem.ExpenditureItemBelongService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 支出项目归口设置
 * @Table: BUDG_DUTY_SET 支出项目归口设置表
 * @Author: zzb
 * @email: jonZhang@e-tonggroup.com
 * @Version: 1.0
 */
@Service("expenditureItemBelongService")
public class ExpenditureItemBelongServiceImpl implements ExpenditureItemBelongService {

	private static Logger logger = Logger.getLogger(ExpenditureItemBelongServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "expenditureItemBelongMapper")
	private final ExpenditureItemBelongMapper expenditureItemBelongMapper = null;
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	@Override
	public String queryExpenditureData(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) expenditureItemBelongMapper.querydata(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) expenditureItemBelongMapper.querydata(entityMap,rowBounds);

			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String queryDutyDept(Map<String, Object> entityMap) {
		
		
		return JSON.toJSONString(expenditureItemBelongMapper.queryDutyDept(entityMap));
	}

	@Override
	public String queryPaymentItem(Map<String, Object> entityMap) {
		
		return JSON.toJSONString(expenditureItemBelongMapper.queryPaymentItem(entityMap));
	}

	@Override
	public String addQueryExpenditureItemBelong(Map<String, Object> entityMap) {
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) expenditureItemBelongMapper.queryAddData(entityMap);
		return ChdJson.toJson(list);
	}

	@Override
	public String addExpenditureItemBelongData(Map<String, Object> mapVo) {
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		for (String dept_id :(String.valueOf(mapVo.get("ParamVo"))).split(",")) {
			
			HashMap<String,Object> map = new HashMap<String,Object>();


			map.put("group_id", mapVo.get("group_id"));
			map.put("hos_id", mapVo.get("hos_id"));
			map.put("copy_code", mapVo.get("copy_code"));
			map.put("duty_dept_id", mapVo.get("duty_dept_id"));
			map.put("payment_item_id", mapVo.get("payment_item_id"));
			map.put("dept_id", dept_id);
			map.put("deleteFlag", 1);
			list.add(map);
			
		}
		try {
			expenditureItemBelongMapper.delete(mapVo);
			expenditureItemBelongMapper.addExpenditureItemBelongData(list);
			return "{\"msg\":\"保存成功！\" ,\"state\":\"true\"}";
		} catch (Exception e) {
			
			logger.debug(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
		
		
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> listVo) {
		
		try {
			expenditureItemBelongMapper.deleteBatch(listVo);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}

	@Override
	public Map<String, Object> queryDictData(Map<String, Object> mapVo) {

		
		
		
		return expenditureItemBelongMapper.queryByCode(mapVo);
	}

	/**
	 * 查询所有的科室信息
	 */
	@Override
	public List<HashMap<String, Object>> queryAllDeptData(Map<String, Object> mapVo) {
		
		return expenditureItemBelongMapper.queryAllDeptData(mapVo);
	}
	
	/**
	 * 查询所有的支出项目信息
	 */
	@Override
	public List<HashMap<String, Object>> queryAllPayment_itemData(Map<String, Object> mapVo) {
		
		return expenditureItemBelongMapper.queryAllPayment_itemData(mapVo);
	}

	
	
	@Override
	public String readFilesImport(Map<String, Object> mapVo, List<HashMap<String, Object>> listDept,
			List<HashMap<String, Object>> listPayment_item) {
		
		List<Map<String, List<String>>> listData = null;
		try {
			listData = ReadFiles.readFiles(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (listData.size() == 0 || listData == null) {
			return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
		}
		
		List<String> listDeptCode = new ArrayList<String>();
		List<String> listDeptId = new ArrayList<String>();
		List<String> listPaymentItem_code = new ArrayList<String>();
		List<String> listPaymentItem_id = new ArrayList<String>();
		
		//准备数据
		for (HashMap<String, Object> map : listDept) {
			listDeptCode.add(map.get("dept_code").toString());
			listDeptId.add(map.get("dept_id").toString());
		}
		
		for (HashMap<String, Object> map : listPayment_item) {
			listPaymentItem_code.add(map.get("payment_item_code").toString());
			listPaymentItem_id.add(map.get("payment_item_id").toString());
		}
		
		
		ArrayList<Map<String,Object>> listPro = new ArrayList<Map<String,Object>>();
		
		
		
		StringBuffer err_str = new StringBuffer("");
		
		//循环行
		for (int i = 0; i < listData.size(); i++) {

			
			String dept_code = listData.get(i).get("dept_code").get(1);
			String payment_item_code = listData.get(i).get("payment_item_code").get(1);
			String duty_dept_code = listData.get(i).get("duty_dept_code").get(1);
			
			if (!listDeptCode.contains(dept_code)) {
				err_str.append("第"+(i+1)+"行的科室编码不存在，");
				continue;
			}
			if (!listPaymentItem_code.contains(payment_item_code)) {
				err_str.append("第"+(i+1)+"行的支出项目编码不存在，");
				continue;
			}
			if (!listDeptCode.contains(duty_dept_code)) {
				err_str.append("第"+(i+1)+"行的归口科室编码不存在，");
				continue;
			}
			
			
			HashMap<String,Object> mapPro = new HashMap<String, Object>();
			mapPro.put("group_id",mapVo.get("group_id") );
			mapPro.put("hos_id",mapVo.get("hos_id") );
			mapPro.put("copy_code",mapVo.get("copy_code") );
			mapPro.put("dept_id",listDeptId.get(listDeptCode.indexOf(dept_code)) );
			mapPro.put("payment_item_id",listPaymentItem_id.get(listPaymentItem_code.indexOf(payment_item_code)) );
			mapPro.put("duty_dept_id",listDeptId.get(listDeptCode.indexOf(duty_dept_code)) );
			
			listPro.add(mapPro);
			
		}
		
		mapVo.put("listPro", listPro);
		
		if (err_str.length()!=0) {
			
			return "{\"error\":\""+err_str.toString()+"\"}";
		}else {
			try {
				expenditureItemBelongMapper.deleteAll(mapVo);
				int add = expenditureItemBelongMapper.addBatchImport(mapVo);
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 budgExpenditureItemBelongImport\"}";
			}
			
		}
		
		
		
	}

}
