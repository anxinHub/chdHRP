package com.chd.hrp.hpm.serviceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.hpm.dao.AphiCostitemConfSeqMapper;
import com.chd.hrp.hpm.dao.AphiCostitemDataMapper;
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemConfSeqMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemDataMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemPointDataMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemPointSeqMapper;
import com.chd.hrp.hpm.dao.AphiSchemeConfMapper;
import com.chd.hrp.hpm.dao.AphiTemplateDataMapper;
import com.chd.hrp.hpm.dao.AphiWorkitemConfSeqMapper;
import com.chd.hrp.hpm.dao.AphiWorkitemDataMapper;
import com.chd.hrp.hpm.entity.AphiCostitemConfSeq;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.entity.AphiIncomeitemConfSeq;
import com.chd.hrp.hpm.entity.AphiIncomeitemPointSeq;
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.entity.AphiTemplateData;
import com.chd.hrp.hpm.entity.AphiWorkitemConfSeq;
import com.chd.hrp.hpm.service.AphiTemplateDataService;



@Service("aphiTemplateDataService")
public class AphiTemplateDataServiceImpl implements AphiTemplateDataService {

	private static Logger logger = Logger.getLogger(AphiTemplateDataServiceImpl.class);
	
	@Resource(name = "aphiTemplateDataMapper")
	private final AphiTemplateDataMapper aphiTemplateDataMapper = null;
	
	@Resource(name = "aphiWorkitemDataMapper")
	private final AphiWorkitemDataMapper aphiWorkitemDataMapper = null;
	
	@Resource(name = "aphiSchemeConfMapper")
	private final AphiSchemeConfMapper aphiSchemeConfMapper = null;
	
	@Resource(name = "aphiWorkitemConfSeqMapper")
	private final AphiWorkitemConfSeqMapper aphiWorkitemConfSeqMapper = null;
	
	@Resource(name = "aphiIncomeitemConfSeqMapper")
	private final AphiIncomeitemConfSeqMapper aphiIncomeitemConfSeqMapper = null;
	
	@Resource(name = "aphiIncomeitemDataMapper")
	private final AphiIncomeitemDataMapper aphiIncomeitemDataMapper = null;
	
	@Resource(name = "aphiCostitemDataMapper")
	private final AphiCostitemDataMapper aphiCostitemDataMapper = null;
	
	@Resource(name = "aphiCostitemConfSeqMapper")
	private final AphiCostitemConfSeqMapper aphiCostitemConfSeqMapper = null;
	
	@Resource(name = "aphiIncomeitemPointSeqMapper")
	private final AphiIncomeitemPointSeqMapper aphiIncomeitemPointSeqMapper = null;
	
	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;
	
	@Resource(name = "aphiIncomeitemPointDataMapper")
	private final AphiIncomeitemPointDataMapper aphiIncomeitemPointDataMapper = null;
	
	@Override
	public String addTemplateData(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
			
			//获取模板操作表配置明细JSON
			JSONArray detailDataJson = JSONArray.parseArray(String.valueOf(entityMap.get("detailData")));
			
			Iterator iterator = detailDataJson.iterator();
			
			//遍历明细数据
			while (iterator.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
				//过滤空行
				if(jsonObj.get("template_detail_code") != null && !"".equals(jsonObj.get("template_detail_code"))){
					
					Map<String,Object> detailMap = new HashMap<String,Object>();
					
					detailMap.put("group_id",entityMap.get("group_id"));
					detailMap.put("hos_id",entityMap.get("hos_id"));
					detailMap.put("copy_code",entityMap.get("copy_code"));
					detailMap.put("template_code",entityMap.get("template_code"));
					detailMap.put("template_detail_code",jsonObj.get("template_detail_code"));
					if(ChdJson.validateJSON(String.valueOf(jsonObj.get("group_view")))){
						detailMap.put("group_view",jsonObj.get("group_view"));
					}else{
						detailMap.put("group_view","");
					}
					if(ChdJson.validateJSON(String.valueOf(jsonObj.get("columns_type")))){
						detailMap.put("columns_type",jsonObj.get("columns_type"));
					}else{
						detailMap.put("columns_type","");
					}
					if(ChdJson.validateJSON(String.valueOf(jsonObj.get("columns_view")))){
						detailMap.put("columns_view",jsonObj.get("columns_view"));
					}else{
						detailMap.put("columns_view","");
					}
					if(ChdJson.validateJSON(String.valueOf(jsonObj.get("columns_table")))){
						detailMap.put("columns_table",jsonObj.get("columns_table"));
					}else{
						detailMap.put("columns_table","");
					}
					
					detailList.add(detailMap);
				}
			}
			
			if("true".equals(entityMap.get("saveFlag"))){//新增
				
				AphiTemplateData atd = aphiTemplateDataMapper.queryAphiTemplateDataByCode(entityMap);
				if(atd != null ){
					return "{\"warn\":\"模板编码已经存在.\",\"state\":\"false\"}";
				}
				//保存主表
				aphiTemplateDataMapper.addAphiTemplateData(entityMap);
				if(detailList.size() > 0 ){
					//保存明细表
					aphiTemplateDataMapper.addBatchAphiTemplateDetailData(detailList);
				}
			}else{//修改
				
				//1.修改主表
				aphiTemplateDataMapper.updateAphiTemplateData(entityMap);
				
				if(detailList.size() > 0 ){//存在明细数据
					//2.删除当前模板编码所有明细
					aphiTemplateDataMapper.deleteAphiTemplateDetailDataByTemplateCode(entityMap);
					//3.重新添加新的明细
					aphiTemplateDataMapper.addBatchAphiTemplateDetailData(detailList);
				}
			}
			
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败 \"}");
		}
	}
	

	@Override
	public String queryAphiTemplateDataTree(Map<String, Object> entityMap) throws DataAccessException {
		
		StringBuilder json = new StringBuilder();
		
		try {
			
			//查询所有模板
			List<AphiTemplateData> list = aphiTemplateDataMapper.queryAphiTemplateData(entityMap);
			if (list.size() ==0 && list.size() ==0) {
				json.append("{Rows:[]}");
				return json.toString();
			}

			
			json.append("{Rows:[");
			for (AphiTemplateData atd : list) {
				json.append("{");
				json.append("\"pid\":\"-1\"," + "\"id\":\"" + atd.getTemplate_code()+ "\"," + "\"text\":" + "\"" + atd.getTemplate_name()+ "\"");
				json.append("},");
			}
			json.setCharAt(json.length() - 1, ']');
			json.append("}");
			
		} catch (Exception e) {
			
			json.append("{Rows:[]}");
			return json.toString();
		}
		
		return json.toString();
	}

	@Override
	public String queryAphiTemplateDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return ChdJson.toJson(aphiTemplateDataMapper.queryAphiTemplateDataByCode(entityMap));
	}

	@Override
	public String queryAphiTemplateDetailDataByTemplateCode( Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		return ChdJson.toJson(aphiTemplateDataMapper.queryAphiTemplateDetailDataByTemplateCode(entityMap));
	}

	@Override
	public String deleteBatchAphiTemplateDetailData(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			aphiTemplateDataMapper.deleteBatchAphiTemplateDetailData(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}

	@Override
	public String deleteAphiTemplateData(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			//删除模板主表
			aphiTemplateDataMapper.deleteAphiTemplateData(entityMap);
			//删除模板明细
			aphiTemplateDataMapper.deleteAphiTemplateDetailDataByTemplateCode(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}

	@Override
	public String queryAphiTemplateDataColumnHeads(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		 try {
			 
			 //templateDetailList ：查询模板明细
			 List<AphiTemplateData> templateDetailList = aphiTemplateDataMapper.queryAphiTemplateDetailDataByTemplateCode(entityMap);
			
			 
			 Map<String,String> tempDataMap = new HashMap<String,String>();//用于判断多表头组是否已经存在
			 StringBuffer json = new StringBuffer();//表头JSON串
			 
			 String template_table = String.valueOf(entityMap.get("template_table"));//模板操作表名称
			 String group_name = "";//记录组名称,用于记录同一个组
			 int count = 0 ;//判断组标签是否存在,用于拼装JSON中,一组结束时的结束括号
			 int listSize = 0;//记录数据条数,用于判断JSON结束时串拼接
			 json.append("{Rows:[");
			 
			 
			 
			 for(AphiTemplateData atd:templateDetailList){
				 
				 listSize ++;
				 String columns_view = atd.getColumns_view();//获取列字段
				 String group_view = atd.getGroup_view();//获取组名称
				 String columns_type = atd.getColumns_type();//获取列类型:01 取值项 、02 关联项、 03 小计、 04 合计
				 
				 if(!group_name.equals(group_view) && count > 0){//如果当前的组名称不等于上一组并且组开始标签已经存在,上一组结束
					 json = json.deleteCharAt(json.length() - 1);
					 json.append("]},");//上一组结束
					 group_name = group_view;//记录、统计下一组
					 count = 0 ;//归0,重新统计
				 }
				 
				 
				 
				 if("01".equals(columns_type) || "02".equals(columns_type)){//取值项或者关联项
					 
					 String arr [] = columns_view.split(",");//分割列字段
					 if(arr.length < 2){
						 return "{\"warn\":\"明细编码"+atd.getTemplate_detail_code()+"列字段值错误 \"}";
					 }
					 
					 
					 String display = arr[0]; //grid显示字段名称
					 String cid = "";//grid列id,用于删除和修改获取表名字段名
					 String name = StringTool.toPinyinShouZiMu(display).toLowerCase() + "__" +arr[1].toLowerCase() ;//对应查询语句字段
					 
					 
					 if(arr.length == 2){//自定义工作量数据准备表 列字段配置的是2个
						 cid = 
							StringTool.toPinyinShouZiMu(display) + "__" //grid显示字段的首字母(保证cid唯一)
							+ atd.getColumns_table().toLowerCase() + "__" //表名
							+ arr[1].toLowerCase()  //查询条件编码
							 ;//字段显示名称首字母
					 }
					 
					 
					 
					 if(arr.length > 2){//非自定义工作量数据准备表 列字段配置的是3个
						 cid = 
							StringTool.toPinyinShouZiMu(display) + "__" //grid显示字段的首字母(保证cid唯一)
							+ atd.getColumns_table().toLowerCase() + "__" //表名
							+ arr[1].toLowerCase() + "__" //查询条件编码
							+ arr[2].toLowerCase()  //列名称
							;
					 }
					 
					 
					 
					 //如果组不存在,并且是取值项
					 if(group_view == null && "01".equals(columns_type)){
						 
						 json.append("{ display: \'" + display + "\',");
						 json.append("id: \'" + cid + "\',");
						 json.append("name: \'" + name + "\',");
						 json.append("align: \'right\',");
						 json.append("minWidth:180,");
						 json.append("totalSummary:{type: \'sum\',render: function (suminf, column, cell){ return \'<div> 总计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';}}");
								
					 if(template_table.equals(atd.getColumns_table())){
							 json.append(",editor:{type : 'float'}");
						 }
						 json.append("},");
						 
						 continue;
					 }
					 
					 
					 
					 //如果组名称为空,并且是关联项
					 if(group_view == null && "02".equals(columns_type)){
						 json.append("{ display: \'" + display + "\',");
						 json.append("name:\'" +arr[1]+ "\',");
						 json.append("align: \'left\',");
						 json.append("minWidth:180,");
						 json.append("frozen:true");
						 
						 json.append("},");
						 
						 continue;
					 }
					 
					 
					 //如果组名称没有在Map中存在,就在grid列中拼装组的表头
					 if(tempDataMap.get(group_view) == null){
						 json.append("{display:\'"+group_view+"\',columns :[");
						 json.append("{display: \'" + display + "\',");
						 json.append("id:\'" + cid + "\',");
						 json.append("name:\'" + name + "\',");
						 json.append("align: \'right\',");
						 json.append("minWidth:120,");
						 json.append("totalSummary:{type: \'sum\',render: function (suminf, column, cell){ return \'<div> 总计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';}}");
						 
						 if(template_table.equals(atd.getColumns_table())){
							 json.append(",editor:{type : 'float'}");
						 }
						 json.append("},");
						 
						 
						 tempDataMap.put(group_view, group_view);//记录已有的组
						 count++;
					 }else{//组名称在Map中存在,说明多表头中已经拼装组
						 json.append("{display: \'" + display + "\',");
						 json.append("id:\'" + cid + "\',");
						 json.append("name:\'" + name + "\',");
						 json.append("align: \'right\',");
						 json.append("minWidth:120,");
						 json.append("totalSummary:{type: \'sum\',render: function (suminf, column, cell){ return \'<div> 总计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';}}");
						 
						 if(template_table.equals(atd.getColumns_table())){
							 json.append(",editor:{type : 'float'}");
						 }
						 json.append("},");
					 }
					 
				 }
				 
				 if("03".equals(columns_type) || "04".equals(columns_type)){//03 小计 或者 04 合计
					 
					 Map<String,AphiTemplateData> templateDetailMap = templateListToMap(templateDetailList);
					 //将正则表达式对象作用在字符串上,获取匹配对象
					 Matcher matcher = Pattern.compile("\\{.*?\\}").matcher(columns_view);
					 
					 //匹配、获取、替换
					 while (matcher.find()) {
						 
						String key = matcher.group() ;
						//根据匹配到的编码获取模板明细
						if(templateDetailMap.get(key) == null){
							
							return "{\"warn\":\""  + key + ",在模板明细中不存在,请正确配置 \"}";
						}
						
						AphiTemplateData data = templateDetailMap.get(key);
						//取出明细数据中的列字段,拼需要合计的列字段名称
						String[] dataViews = data.getColumns_view().split(",");
						columns_view = columns_view.replace(key,"rowdata." + StringTool.toPinyinShouZiMu(dataViews[0]).toLowerCase()+ "__" +dataViews[1].toLowerCase());
					 }
					 
					 
					 columns_view = columns_view.replaceAll("\\{", "\\(").replaceAll("\\}", "\\)");
					 
					 
					 String display = "";
					 String name = "";
					 String attrName = "";//属性名称,用于记录小计或者合计每列的值
					 
					 if("03".equals(columns_type)){
						 display = "\'小计\'";
						 name = "\'mincount_" + atd.getTemplate_detail_code() + "\'";
						 attrName = "mincount_" + atd.getTemplate_detail_code();
					 }
					 
					 if("04".equals(columns_type)){
						 display = "\'合计\'";
						 name = "\'totalCount\'";
						 attrName = "totalCount";
					 }
					 
					 json.append("{display:" + display + ",");
					 json.append("name:" + name + ",");
					 json.append("align: \'right\',");
					 json.append("minWidth:100,");
					 json.append("render:function(rowdata){ ");
					 json.append("	rowdata."+ attrName + " = " + columns_view + ";");
					 json.append("	return " + columns_view + "},");
					 json.append("totalSummary:{type: \'sum\',render: function (suminf, column, cell){ return \'<div> 总计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';}}},");
				 }
				 
				 
				 if(count > 0 && listSize == templateDetailList.size()){
					 json = json.deleteCharAt(json.length() - 1);
					 json.append("]},");
				 }
				 
				 group_name = group_view == null ? "" : group_view;//记录当前组,用于下行数据的判断
			 }
			 
			 json = json.deleteCharAt(json.length() - 1);
			 
			 json.append("]}");
			 
			return json.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"操作失败 \"}";
		}
	}

	@Override
	public String queryHpmTemplateDataForParseData( Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			// 1.根据年月查询方案序号
			AphiSchemeConf sc = aphiSchemeConfMapper.querySchemeConfByYM(entityMap);
			if(sc == null){
				return "{\"warn\":\"当前核算年月未配置方案 \"}";
			}
			
			//2.查询模板明细
			List<AphiTemplateData> atdList = aphiTemplateDataMapper.queryAphiTemplateDetailDataByTemplateCode(entityMap);
			if(atdList.size() == 0){
				return "{\"warn\":\"模板编码"+entityMap.get("template_code")+"未配置明细 \"}";
			}
			
			//3.获取模板名称
			String alias = String.valueOf(entityMap.get("template_table"));
			
				
				StringBuffer sql = new StringBuffer();//拼写SQL
				StringBuffer sql2 = new StringBuffer();//拼写SQL查询条件中的in()中的字段
				StringBuffer sql3 = new StringBuffer();//拼写关联项添加中的字段
				sql.append("SELECT ");
				sql.append(alias + ".group_id,");
				sql.append(alias + ".hos_id,");
				sql.append(alias + ".copy_code,");
				sql.append(alias + ".acct_year,");
				sql.append(alias + ".acct_month,");
				sql.append(alias + ".dept_id,");
				sql.append(alias + ".dept_no,");
				/*sql.append("aphi_dept_dict.dept_code,");
				sql.append("aphi_dept_dict.dept_name,");*/
				
				for(AphiTemplateData atd:atdList){
					
					if("01".equals(atd.getColumns_type()) || "02".equals(atd.getColumns_type())){//列类型:关联项、取值项
						
						String[] columns_views = atd.getColumns_view().split(",");//列字段:如  计提直接成本,001,PRIM_COST_RET
						
						String columns_table = 	atd.getColumns_table();	//列表名			
						
						 if(columns_views.length < 2){
							 return "{\"warn\":\"明细编码"+atd.getTemplate_detail_code()+"列字段值错误 \"}";
						 }
						 
						 if("02".equals(atd.getColumns_type())){
							 sql3.append(columns_table + "." + columns_views[1] + ",");
							 continue;
						 }
						 
						 //自定义工作量数据准备
						 if("aphi_workitem_data".equals(columns_table)){
							 sql.append("sum(case when " + columns_table + ".work_item_code ='" + columns_views[1] + "' then " + columns_table +".work_amount end) as "+ StringTool.toPinyinShouZiMu(columns_views[0]).toLowerCase() +"__" + columns_views[1] +",");
						 }
						 
						//自定义工作量数据准备
						 if("aphi_workitem_conf_seq".equals(columns_table)){
							 sql.append("sum(case when " + columns_table + ".work_item_code ='" + columns_views[1] + "' then " + columns_table +".work_standard end) as " + StringTool.toPinyinShouZiMu(columns_views[0]).toLowerCase() + "__" + columns_views[1] +",");
						 }
						 
						//自定义收入数据准备
						 if("aphi_incomeitem_data".equals(columns_table)){
							 sql.append("sum(case when " + columns_table + ".income_item_code ='" + columns_views[1] + "' then " + columns_table +"." + columns_views[2] + " end) as "+ StringTool.toPinyinShouZiMu(columns_views[0]).toLowerCase() +"__" + columns_views[1] +",");
						 }
						 
						 //自定义支出数据准备
						 if("aphi_costitem_data".equals(columns_table)){
							 sql.append("sum(case when " + columns_table + ".cost_item_code ='" + columns_views[1] + "' then " + columns_table +"." + columns_views[2] + " end) as "+ StringTool.toPinyinShouZiMu(columns_views[0]).toLowerCase() +"__" + columns_views[1] +",");
						 }
						 
						 //自定义核算项目点数准备
						 if("aphi_incomeitem_point_data".equals(columns_table)){
							 sql.append("sum(case when " + columns_table + ".income_item_code ='" + columns_views[1] + "' then " + columns_table +"." + columns_views[2] + " end) as "+ StringTool.toPinyinShouZiMu(columns_views[0]).toLowerCase() +"__" + columns_views[1] +",");
						 }
						 
						//sum(case when aphi_workitem_data.work_item_code ='G061' then aphi_workitem_data.work_amount end) as G061 ,
						sql2.append("'" + columns_views[1] + "',");
					}
				}
				
				if(sql3.length() > 0 ){
					sql.append(sql3);//拼接关联字段名称
				}
				sql = sql.deleteCharAt(sql.length() -1 );
				sql2 = sql2.deleteCharAt(sql2.length() - 1);
				
			//1.自定义工作量数据准备 aphi_workitem_data
			if("aphi_workitem_data".equals(alias)){
				
				sql.append(" from aphi_workitem_data aphi_workitem_data ");
				
				sql.append("left join aphi_dept_dict aphi_dept_dict ");
				sql.append("on aphi_workitem_data.dept_id = aphi_dept_dict.dept_id ");
				sql.append("and aphi_workitem_data.dept_no = aphi_dept_dict.dept_no ");
				sql.append("and aphi_workitem_data.copy_code = aphi_dept_dict.copy_code ");
				sql.append("and aphi_workitem_data.group_id = aphi_dept_dict.group_id ");
				sql.append("and aphi_workitem_data.hos_id = aphi_dept_dict.hos_id ");
				sql.append("and aphi_workitem_data.hos_id = aphi_dept_dict.hos_id ");
				
				sql.append("left join aphi_workitem aphi_workitem ");
				sql.append("on aphi_workitem_data.work_item_code = aphi_workitem.work_item_code ");
				sql.append("and aphi_workitem_data.group_id = aphi_workitem.group_id ");
				sql.append("and aphi_workitem_data.copy_code = aphi_workitem.copy_code ");
				sql.append("and aphi_workitem_data.hos_id = aphi_workitem.hos_id ");
				
				sql.append("left join aphi_workitem_conf aphi_workitem_conf ");
				sql.append("on aphi_workitem_data.group_id = aphi_workitem_conf.group_id ");
				sql.append("and aphi_workitem_data.hos_id = aphi_workitem_conf.hos_id ");
				sql.append("and aphi_workitem_data.copy_code = aphi_workitem_conf.copy_code ");
				sql.append("and aphi_workitem_data.work_item_code = aphi_workitem_conf.work_item_code ");
				sql.append("and aphi_workitem_data.dept_id = aphi_workitem_conf.dept_id ");
				sql.append("and aphi_workitem_data.dept_no = aphi_workitem_conf.dept_no ");
				
				sql.append("left join aphi_workitem_conf_seq aphi_workitem_conf_seq ");
				sql.append("on aphi_workitem_data.group_id = aphi_workitem_conf_seq.group_id ");
				sql.append("and aphi_workitem_data.hos_id = aphi_workitem_conf_seq.hos_id ");
				sql.append("and aphi_workitem_data.copy_code = aphi_workitem_conf_seq.copy_code ");
				sql.append("and aphi_workitem_data.work_item_code = aphi_workitem_conf_seq.work_item_code ");
				sql.append("and aphi_workitem_data.dept_id = aphi_workitem_conf_seq.dept_id ");
				sql.append("and aphi_workitem_data.dept_no = aphi_workitem_conf_seq.dept_no ");
				sql.append("and aphi_workitem_conf_seq.scheme_seq_no = '" +  String.valueOf(sc.getScheme_seq_no()) + "' ");
				
				sql.append("where aphi_workitem_data.group_id = '"+String.valueOf(entityMap.get("group_id"))+"' ");
				sql.append("and aphi_workitem_data.hos_id = '" +String.valueOf(entityMap.get("hos_id"))+ "' ");
				sql.append("and aphi_workitem_data.copy_code = '" + String.valueOf(entityMap.get("copy_code")) + "' ");
				sql.append("and aphi_workitem_data.acct_year = '"+String.valueOf(entityMap.get("acct_year"))+"'");
				sql.append("and aphi_workitem_data.acct_month = '" +String.valueOf(entityMap.get("acct_month"))+ "' ");
				if(entityMap.get("dept_id") != null && !"".equals(entityMap.get("dept_id"))){
					sql.append("and aphi_workitem_data.dept_id = '" +String.valueOf(entityMap.get("dept_id"))+ "' ");
				}
				
				if(entityMap.get("dept_no") != null && !"".equals(entityMap.get("dept_no"))){
					sql.append("and aphi_workitem_data.dept_no = '" +String.valueOf(entityMap.get("dept_no"))+ "' ");
				}
				
				sql.append("and aphi_workitem_data.work_item_code in (" + sql2 + ") ");
				sql.append("group by aphi_workitem_data.group_id,");
				sql.append("aphi_workitem_data.hos_id,");
				sql.append("aphi_workitem_data.copy_code,");
				sql.append("aphi_workitem_data.acct_year,");
				sql.append("aphi_workitem_data.acct_month,");
				sql.append(sql3);
				sql.append("aphi_workitem_data.dept_id,");
				sql.append("aphi_workitem_data.dept_no ");
				sql.append("and exists(select 1 from v_user_data_perm a where a.group_id = #{group_id} and a.hos_id = #{hos_id} and a.copy_code = #{copy_code} and a.user_id = #{user_id} and a.mod_code = '09' and a.table_code = 'APHI_DEPT_DICT' and a.perm_code = aphi_workitem_data.dept_id and a.is_read = 1 and a.is_write = 1)");
				sql.append(" order by aphi_workitem_data.dept_id asc");
				entityMap.put("sql", sql.toString());
			
			}
			
			
			//2.自定义收入数据准备表 aphi_incomeitem_data
			if("aphi_incomeitem_data".equals(alias)){
				
				
				sql.append(" from aphi_incomeitem_data aphi_incomeitem_data ");
				
				sql.append("left join aphi_dept_dict aphi_dept_dict ");
				sql.append("on aphi_incomeitem_data.group_id = aphi_dept_dict.group_id ");
				sql.append("and aphi_incomeitem_data.hos_id = aphi_dept_dict.hos_id ");
				sql.append("and aphi_incomeitem_data.copy_code = aphi_dept_dict.copy_code ");
				sql.append("and aphi_incomeitem_data.dept_id = aphi_dept_dict.dept_id ");
				sql.append("and aphi_incomeitem_data.dept_no = aphi_dept_dict.dept_no ");
				
				sql.append("left join aphi_incomeitem aphi_incomeitem ");
				sql.append("on aphi_incomeitem_data.group_id = aphi_incomeitem.group_id ");
				sql.append("and aphi_incomeitem_data.hos_id = aphi_incomeitem.hos_id ");
				sql.append("and aphi_incomeitem_data.copy_code = aphi_incomeitem.copy_code ");
				sql.append("and aphi_incomeitem_data.income_item_code = aphi_incomeitem.income_item_code ");
				sql.append("and aphi_incomeitem.is_stop = 0  ");
				
				sql.append("left join aphi_incomeitem_conf aphi_incomeitem_conf ");
				sql.append("on aphi_incomeitem_data.group_id = aphi_incomeitem_conf.group_id ");
				sql.append("and aphi_incomeitem_data.hos_id = aphi_incomeitem_conf.hos_id ");
				sql.append("and aphi_incomeitem_data.copy_code = aphi_incomeitem_conf.copy_code ");
				sql.append("and aphi_incomeitem_data.dept_id = aphi_incomeitem_conf.dept_id ");
				sql.append("and aphi_incomeitem_data.dept_no = aphi_incomeitem_conf.dept_no ");
				sql.append("and aphi_incomeitem_data.income_item_code = aphi_incomeitem_conf.income_item_code ");
				
				sql.append("left join aphi_incomeitem_conf_seq aphi_incomeitem_conf_seq ");
				sql.append("on aphi_incomeitem_data.group_id = aphi_incomeitem_conf_seq.group_id ");
				sql.append("and aphi_incomeitem_data.hos_id = aphi_incomeitem_conf_seq.hos_id ");
				sql.append("and aphi_incomeitem_data.copy_code = aphi_incomeitem_conf_seq.copy_code ");
				sql.append("and aphi_incomeitem_data.income_item_code = aphi_incomeitem_conf_seq.income_item_code ");
				sql.append("and aphi_incomeitem_data.dept_id = aphi_incomeitem_conf_seq.dept_id ");
				sql.append("and aphi_incomeitem_data.dept_no = aphi_incomeitem_conf_seq.dept_no ");
				sql.append("and aphi_incomeitem_conf_seq.scheme_seq_no = '" +  String.valueOf(sc.getScheme_seq_no()) + "' ");
				
				sql.append("where aphi_incomeitem_data.group_id = '"+String.valueOf(entityMap.get("group_id"))+"' ");
				sql.append("and aphi_incomeitem_data.hos_id = '" +String.valueOf(entityMap.get("hos_id"))+ "' ");
				sql.append("and aphi_incomeitem_data.copy_code = '" + String.valueOf(entityMap.get("copy_code")) + "' ");
				sql.append("and aphi_incomeitem_data.acct_year = '"+String.valueOf(entityMap.get("acct_year"))+"'");
				sql.append("and aphi_incomeitem_data.acct_month = '" +String.valueOf(entityMap.get("acct_month"))+ "' ");
				if(entityMap.get("dept_id") != null && !"".equals(entityMap.get("dept_id"))){
					sql.append("and aphi_incomeitem_data.dept_id = '" +String.valueOf(entityMap.get("dept_id"))+ "' ");
				}
				
				if(entityMap.get("dept_no") != null && !"".equals(entityMap.get("dept_no"))){
					sql.append("and aphi_incomeitem_data.dept_no = '" +String.valueOf(entityMap.get("dept_no"))+ "' ");
				}
				
				sql.append("and aphi_incomeitem_data.income_item_code in (" + sql2 + ") ");
				sql.append("group by aphi_incomeitem_data.group_id,");
				sql.append("aphi_incomeitem_data.hos_id,");
				sql.append("aphi_incomeitem_data.copy_code,");
				sql.append("aphi_incomeitem_data.acct_year,");
				sql.append("aphi_incomeitem_data.acct_month,");
				sql.append(sql3);
				sql.append("aphi_incomeitem_data.dept_id,");
				sql.append("aphi_incomeitem_data.dept_no ");
				sql.append("and exists(select 1 from v_user_data_perm a where a.group_id = #{group_id} and a.hos_id = #{hos_id} and a.copy_code = #{copy_code} and a.user_id = #{user_id} and a.mod_code = '09' and a.table_code = 'APHI_DEPT_DICT' and a.perm_code = aphi_incomeitem_data.dept_id and a.is_read = 1 and a.is_write = 1)");
				sql.append(" order by aphi_incomeitem_data.dept_id asc");
				entityMap.put("sql", sql.toString());
			}
			
			
			//3.自定义支出数据准备表 aphi_costitem_data
			if("aphi_costitem_data".equals(alias)){
				
				sql.append(" from aphi_costitem_data aphi_costitem_data ");
				
				sql.append("left join aphi_dept_dict aphi_dept_dict ");
				sql.append("on aphi_costitem_data.group_id = aphi_dept_dict.group_id ");
				sql.append("and aphi_costitem_data.hos_id = aphi_dept_dict.hos_id ");
				sql.append("and aphi_costitem_data.copy_code = aphi_dept_dict.copy_code ");
				sql.append("and aphi_costitem_data.dept_id = aphi_dept_dict.dept_id ");
				sql.append("and aphi_costitem_data.dept_no = aphi_dept_dict.dept_no ");
				
				sql.append("left join aphi_costitem aphi_costitem ");
				sql.append("on aphi_costitem_data.group_id = aphi_costitem.group_id ");
				sql.append("and aphi_costitem_data.hos_id = aphi_costitem.hos_id ");
				sql.append("and aphi_costitem_data.copy_code = aphi_costitem.copy_code ");
				sql.append("and aphi_costitem_data.cost_item_code = aphi_costitem.cost_item_code ");
				sql.append("and aphi_costitem.is_stop = 0  ");
				
				sql.append("left join aphi_costitem_conf aphi_costitem_conf ");
				sql.append("on aphi_costitem_data.group_id = aphi_costitem_conf.group_id ");
				sql.append("and aphi_costitem_data.hos_id = aphi_costitem_conf.hos_id ");
				sql.append("and aphi_costitem_data.copy_code = aphi_costitem_conf.copy_code ");
				sql.append("and aphi_costitem_data.dept_id = aphi_costitem_conf.dept_id ");
				sql.append("and aphi_costitem_data.dept_no = aphi_costitem_conf.dept_no ");
				sql.append("and aphi_costitem_data.cost_item_code = aphi_costitem_conf.cost_item_code ");
				
				sql.append("left join aphi_costitem_conf_seq aphi_costitem_conf_seq ");
				sql.append("on aphi_costitem_data.group_id = aphi_costitem_conf_seq.group_id ");
				sql.append("and aphi_costitem_data.hos_id = aphi_costitem_conf_seq.hos_id ");
				sql.append("and aphi_costitem_data.copy_code = aphi_costitem_conf_seq.copy_code ");
				sql.append("and aphi_costitem_data.cost_item_code = aphi_costitem_conf_seq.cost_item_code ");
				sql.append("and aphi_costitem_data.dept_id = aphi_costitem_conf_seq.dept_id ");
				sql.append("and aphi_costitem_data.dept_no = aphi_costitem_conf_seq.dept_no ");
				sql.append("and aphi_costitem_conf_seq.scheme_seq_no = '" +  String.valueOf(sc.getScheme_seq_no()) + "' ");
				
				sql.append("where aphi_costitem_data.group_id = '"+String.valueOf(entityMap.get("group_id"))+"' ");
				sql.append("and aphi_costitem_data.hos_id = '" +String.valueOf(entityMap.get("hos_id"))+ "' ");
				sql.append("and aphi_costitem_data.copy_code = '" + String.valueOf(entityMap.get("copy_code")) + "' ");
				sql.append("and aphi_costitem_data.acct_year = '"+String.valueOf(entityMap.get("acct_year"))+"'");
				sql.append("and aphi_costitem_data.acct_month = '" +String.valueOf(entityMap.get("acct_month"))+ "' ");
				if(entityMap.get("dept_id") != null && !"".equals(entityMap.get("dept_id"))){
					sql.append("and aphi_costitem_data.dept_id = '" +String.valueOf(entityMap.get("dept_id"))+ "' ");
				}
				
				if(entityMap.get("dept_no") != null && !"".equals(entityMap.get("dept_no"))){
					sql.append("and aphi_costitem_data.dept_no = '" +String.valueOf(entityMap.get("dept_no"))+ "' ");
				}
				
				sql.append("and aphi_costitem_data.cost_item_code in (" + sql2 + ") ");
				sql.append("group by aphi_costitem_data.group_id,");
				sql.append("aphi_costitem_data.hos_id,");
				sql.append("aphi_costitem_data.copy_code,");
				sql.append("aphi_costitem_data.acct_year,");
				sql.append("aphi_costitem_data.acct_month,");
				sql.append(sql3);
				sql.append("aphi_costitem_data.dept_id,");
				sql.append("aphi_costitem_data.dept_no ");
				sql.append("and exists(select 1 from v_user_data_perm a where a.group_id = #{group_id} and a.hos_id = #{hos_id} and a.copy_code = #{copy_code} and a.user_id = #{user_id} and a.mod_code = '09' and a.table_code = 'APHI_DEPT_DICT' and a.perm_code = aphi_costitem_data.dept_id and a.is_read = 1 and a.is_write = 1)");
				sql.append(" order by aphi_costitem_data.dept_id asc");
				entityMap.put("sql", sql.toString());
			}
			
			
			//4.自定义核算项目点数准备表 aphi_incomeitem_point_data
			if("aphi_incomeitem_point_data".equals(alias)){
				
				sql.append(" from aphi_incomeitem_point_data aphi_incomeitem_point_data ");
				
				sql.append("left join aphi_dept_dict aphi_dept_dict ");
				sql.append("on aphi_incomeitem_point_data.group_id = aphi_dept_dict.group_id ");
				sql.append("and aphi_incomeitem_point_data.hos_id = aphi_dept_dict.hos_id ");
				sql.append("and aphi_incomeitem_point_data.copy_code = aphi_dept_dict.copy_code ");
				sql.append("and aphi_incomeitem_point_data.dept_id = aphi_dept_dict.dept_id ");
				sql.append("and aphi_incomeitem_point_data.dept_no = aphi_dept_dict.dept_no ");
				
				sql.append("left join aphi_incomeitem_point aphi_incomeitem_point ");
				sql.append("on aphi_incomeitem_point_data.group_id = aphi_incomeitem_point.group_id ");
				sql.append("and aphi_incomeitem_point_data.hos_id = aphi_incomeitem_point.hos_id ");
				sql.append("and aphi_incomeitem_point_data.copy_code = aphi_incomeitem_point.copy_code ");
				sql.append("and aphi_incomeitem_point_data.income_item_code = aphi_incomeitem_point.income_item_code ");
				//sql.append("and aphi_incomeitem_point.is_stop = 0  ");
				
				sql.append("left join aphi_incomeitem_conf aphi_incomeitem_conf ");
				sql.append("on aphi_incomeitem_point_data.group_id = aphi_incomeitem_conf.group_id ");
				sql.append("and aphi_incomeitem_point_data.hos_id = aphi_incomeitem_conf.hos_id ");
				sql.append("and aphi_incomeitem_point_data.copy_code = aphi_incomeitem_conf.copy_code ");
				sql.append("and aphi_incomeitem_point_data.dept_id = aphi_incomeitem_conf.dept_id ");
				sql.append("and aphi_incomeitem_point_data.dept_no = aphi_incomeitem_conf.dept_no ");
				sql.append("and aphi_incomeitem_point_data.income_item_code = aphi_incomeitem_conf.income_item_code ");
				
				sql.append("left join aphi_incomeitem_conf_seq aphi_incomeitem_conf_seq ");
				sql.append("on aphi_incomeitem_point_data.group_id = aphi_incomeitem_conf_seq.group_id ");
				sql.append("and aphi_incomeitem_point_data.hos_id = aphi_incomeitem_conf_seq.hos_id ");
				sql.append("and aphi_incomeitem_point_data.copy_code = aphi_incomeitem_conf_seq.copy_code ");
				sql.append("and aphi_incomeitem_point_data.dept_id = aphi_incomeitem_conf_seq.dept_id ");
				sql.append("and aphi_incomeitem_point_data.dept_no = aphi_incomeitem_conf_seq.dept_no ");
				sql.append("and aphi_incomeitem_point_data.income_item_code = aphi_incomeitem_conf_seq.income_item_code ");
				sql.append("and aphi_incomeitem_conf_seq.scheme_seq_no = '" +  String.valueOf(sc.getScheme_seq_no()) + "' ");
				
				sql.append("where aphi_incomeitem_point_data.group_id = '"+String.valueOf(entityMap.get("group_id"))+"' ");
				sql.append("and aphi_incomeitem_point_data.hos_id = '" +String.valueOf(entityMap.get("hos_id"))+ "' ");
				sql.append("and aphi_incomeitem_point_data.copy_code = '" + String.valueOf(entityMap.get("copy_code")) + "' ");
				sql.append("and aphi_incomeitem_point_data.acct_year = '"+String.valueOf(entityMap.get("acct_year"))+"'");
				sql.append("and aphi_incomeitem_point_data.acct_month = '" +String.valueOf(entityMap.get("acct_month"))+ "' ");
				if(entityMap.get("dept_id") != null && !"".equals(entityMap.get("dept_id"))){
					sql.append("and aphi_incomeitem_point_data.dept_id = '" +String.valueOf(entityMap.get("dept_id"))+ "' ");
				}
				
				if(entityMap.get("dept_no") != null && !"".equals(entityMap.get("dept_no"))){
					sql.append("and aphi_incomeitem_point_data.dept_no = '" +String.valueOf(entityMap.get("dept_no"))+ "' ");
				}
				
				sql.append("and aphi_incomeitem_point_data.income_item_code in (" + sql2 + ") ");
				sql.append("group by aphi_incomeitem_point_data.group_id,");
				sql.append("aphi_incomeitem_point_data.hos_id,");
				sql.append("aphi_incomeitem_point_data.copy_code,");
				sql.append("aphi_incomeitem_point_data.acct_year,");
				sql.append("aphi_incomeitem_point_data.acct_month,");
				sql.append(sql3);
				sql.append("aphi_incomeitem_point_data.dept_id,");
				sql.append("aphi_incomeitem_point_data.dept_no ");
				sql.append("and exists(select 1 from v_user_data_perm a where a.group_id = #{group_id} and a.hos_id = #{hos_id} and a.copy_code = #{copy_code} and a.user_id = #{user_id} and a.mod_code = '09' and a.table_code = 'APHI_DEPT_DICT' and a.perm_code = aphi_incomeitem_point_data.dept_id and a.is_read = 1 and a.is_write = 1)");
				sql.append(" order by aphi_incomeitem_point_data.dept_id asc");
				entityMap.put("sql", sql.toString());
			}
			
			List<Map<String,Object>> list = aphiTemplateDataMapper.queryAphiTemplateDataForParseSql(entityMap);

			return ChdJson.toJsonLower(list);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"操作失败 \",\"state\":\"false \"}";
		}
	}

	@Override
	public String updateHpmTemplateDataForParseData( Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			String group_id = String.valueOf(entityMap.get("group_id"));//集团
			String hos_id = String.valueOf(entityMap.get("hos_id"));//医院
			String copy_code = "'" + String.valueOf(entityMap.get("copy_code")) + "'";//账套
			String dept_id = String.valueOf(entityMap.get("dept_id"));//科室id
			String dept_no = String.valueOf(entityMap.get("dept_no"));//科室变更号
			String acct_year = "'" + String.valueOf(entityMap.get("acct_year")) + "'";//核算年度
			String acct_month = "'" + String.valueOf(entityMap.get("acct_month")) + "'";//核算月份
			String table = String.valueOf(entityMap.get("columns_table"));//表名
			String columns_colname = String.valueOf(entityMap.get("columns_colname"));//列字段
			String code = "'" + String.valueOf(entityMap.get("code")) + "'";//编码
			String value = String.valueOf(entityMap.get("value"));//值
			String column = "";//存储列名称
			
			
			StringBuffer whereSql = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			
			//1.工作量数据准备
			if("aphi_workitem_conf_seq".equalsIgnoreCase(table)){
				// 根据年月查询方案序号
				AphiSchemeConf sc = aphiSchemeConfMapper.querySchemeConfByYM(entityMap);
				column = "work_standard";
				whereSql.append(" AND scheme_seq_no=" + sc.getScheme_seq_no());
				whereSql.append(" AND lower(work_item_code) =" + code);
			}
			
			//1.1工作量数据准备
			if("aphi_workitem_data".equalsIgnoreCase(table)){
				column = "work_amount";
				whereSql.append(" AND acct_year=" + acct_year);
				whereSql.append(" AND acct_month=" + acct_month);
				whereSql.append(" AND lower(work_item_code) =" + code);
			}
			
			//2收入数据准备
			if("aphi_incomeitem_data".equalsIgnoreCase(table)){
				column = columns_colname;
				whereSql.append(" AND acct_year=" + acct_year);
				whereSql.append(" AND acct_month=" + acct_month);
				whereSql.append(" AND lower(income_item_code)=" + code);
			}
			
			//3 支出数据准备
			if("aphi_costitem_data".equalsIgnoreCase(table)){
				column = columns_colname;
				whereSql.append(" AND acct_year=" + acct_year);
				whereSql.append(" AND acct_month=" + acct_month);
				whereSql.append(" AND lower(cost_item_code)=" + code);
			}
			
			//4自定义核算项目点数准备
			if("aphi_incomeitem_point_data".equalsIgnoreCase(table)){
				column = columns_colname;
				whereSql.append(" AND acct_year=" + acct_year);
				whereSql.append(" AND acct_month=" + acct_month);
				whereSql.append(" AND lower(income_item_code)=" + code);
			}
			
			
			sql.append("UPDATE " + table);
			sql.append(" SET " + column + "=" + value);
			sql.append(" WHERE");
			sql.append(" group_id=" + group_id);
			sql.append(" AND hos_id=" + hos_id);
			sql.append(" AND copy_code=" + copy_code);
			sql.append(" AND dept_id=" + dept_id);
			sql.append(" AND dept_no=" + dept_no);
			sql.append(whereSql.toString());
			
			
			entityMap.put("sql", sql.toString());
			aphiTemplateDataMapper.updateAphiTemplateDataForParseSql(entityMap);
			//不提示,state名称改变
			return "{\"msg1\":\"更新成功.\",\"state1\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \",\"state\":\"false \"}");
		}
	}

	@Override
	public String deleteHpmTemplateDataForParseData(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			JSONArray dataJson = JSONArray.parseArray(entityMap.get("detail").toString());
			Iterator iterator = dataJson.iterator();
			String template_table = String.valueOf(entityMap.get("template_table"));
			
			while (iterator.hasNext()) {
				
				JSONObject data = JSONObject.parseObject(iterator.next().toString());
				String table = String.valueOf(data.get("columns_table"));//表名
				
				if(!template_table.equals(table)){//如果列中的表字段不等于操作表,结束本次
					continue;
				}
				
				String group_id = String.valueOf(data.get("group_id"));//集团
				String hos_id = String.valueOf(data.get("hos_id"));//医院
				String copy_code = "'" + String.valueOf(data.get("copy_code")) + "'";//账套
				String dept_id = String.valueOf(data.get("dept_id"));//科室id
				String dept_no = String.valueOf(data.get("dept_no"));//科室变更号
				String acct_year = "'" + String.valueOf(data.get("acct_year")) + "'";//核算年度
				String acct_month = "'" + String.valueOf(data.get("acct_month")) + "'";//核算月份
				String code = "'" + String.valueOf(data.get("code")) + "'";//编码
				
				
				StringBuffer sql = new StringBuffer();
				
				sql.append("DELETE FROM " + table);
				sql.append(" WHERE");
				sql.append(" group_id=" + group_id);
				sql.append(" AND hos_id=" + hos_id);
				sql.append(" AND copy_code=" + copy_code);
				sql.append(" AND dept_id=" + dept_id);
				sql.append(" AND dept_no=" + dept_no);
				sql.append(" AND acct_year=" + acct_year);
				sql.append(" AND acct_month=" + acct_month);
				
				
				//1.工作量数据准备表
				if("aphi_workitem_data".equals(table)){
					sql.append(" AND lower(work_item_code) =" + code);
				}
					
				//2.收入数据准备表
				if("aphi_incomeitem_data".equals(table)){	
					sql.append(" AND lower(income_item_code) =" + code);
				}
				
				//3.支出数据准备表
				if("aphi_costitem_data".equals(table)){	
					sql.append(" AND lower(cost_item_code) =" + code);
				}
				
				//4.自定义核算项目点数准备表
				if("aphi_incomeitem_point_data".equals(table)){	
					sql.append(" AND lower(income_item_code) =" + code);
				}
					
				entityMap.put("sql", sql.toString());
					
				aphiTemplateDataMapper.deleteAphiTemplateDataForParseSql(entityMap);
			}
			
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \",\"state\":\"false \"}");
		}
	}

	@Override
	public String initHpmTemplateDataForParseData( Map<String, Object> entityMap) throws DataAccessException {
		try {
			if (entityMap.get("user_id") == null) {
				entityMap.put("user_id", SessionManager.getUserId());
			}
			AphiSchemeConf sc = aphiSchemeConfMapper.querySchemeConfByYM(entityMap);
			if(sc == null){
				return "{\"warn\":\"当前核算年月未配置方案 \"}";
			}
			
			entityMap.put("scheme_seq_no", sc.getScheme_seq_no().toString());//当前年月审核序号
			entityMap.put("conf_code", entityMap.get("conf_code"));//当前配置编码
			
			String table = String.valueOf(entityMap.get("template_table"));//当前模板操作表
			
			
			//1.工作量数据准备表
			if("aphi_workitem_data".equals(table)){
				
				// 根据当前年月,序号,编码,查询方案
				List<AphiWorkitemConfSeq> list = aphiWorkitemConfSeqMapper.queryWorkitemConfSeqByWorkItemCode(entityMap);
				
				if(list.size() == 0){
					return "{\"warn\":\"没有数据生成 \"}";
				}
				for(AphiWorkitemConfSeq awcf : list){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", awcf.getGroup_id());
					map.put("hos_id", awcf.getHos_id());
					map.put("copy_code", awcf.getCopy_code());
					map.put("acct_year",entityMap.get("acct_year"));
					map.put("acct_month", entityMap.get("acct_month"));
					map.put("dept_id", awcf.getDept_id());
					map.put("dept_no", awcf.getDept_no());
					map.put("work_item_code", awcf.getWork_item_code());
					aphiWorkitemDataMapper.deleteWorkitemData(map);
					
					StringBuffer sql = new StringBuffer();
					sql.append("INSERT INTO " + table);
					sql.append(" (group_id,hos_id,copy_code,acct_year,acct_month,dept_id,dept_no,work_item_code,work_amount,work_money)");
					sql.append(" VALUES(");
					sql.append( awcf.getGroup_id() + ",");
					sql.append( awcf.getHos_id() + ",");
					sql.append( "'"+ awcf.getCopy_code() + "',");
					sql.append( "'"+entityMap.get("acct_year") + "',");
					sql.append( "'"+entityMap.get("acct_month") + "',");
					sql.append( awcf.getDept_id() + ",");
					sql.append( awcf.getDept_no() + ",");
					sql.append( "'"+awcf.getWork_item_code() + "',");
					sql.append("0,");
					sql.append("'')");
					
					entityMap.put("sql", sql.toString());
					aphiTemplateDataMapper.initAphiTemplateDataForParseSql(entityMap);
				}
				
			}
			
			//2.收入数据准备表
			if("aphi_incomeitem_data".equals(table)){
				
				// 根据当前年月,序号,编码,查询方案
				List<AphiIncomeitemConfSeq> list = aphiIncomeitemConfSeqMapper.queryIncomeitemConfSeq(entityMap);
				
				if(list.size() == 0){
					return "{\"warn\":\"没有数据生成 \"}";
				}
				for(AphiIncomeitemConfSeq aicf : list){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", aicf.getGroup_id());
					map.put("hos_id", aicf.getHos_id());
					map.put("copy_code", aicf.getCopy_code());
					map.put("acct_year",entityMap.get("acct_year"));
					map.put("acct_month", entityMap.get("acct_month"));
					map.put("dept_id", aicf.getDept_id());
					map.put("dept_no", aicf.getDept_no());
					map.put("income_item_code", aicf.getIncome_item_code());
					aphiIncomeitemDataMapper.deleteIncomeitemData(map);
					
					StringBuffer sql = new StringBuffer();
					sql.append("INSERT INTO " + table);
					sql.append(" (group_id,hos_id,copy_code,acct_year,acct_month,dept_id,dept_no,income_item_code,order_money,order_ret_money,perform_money,perform_ret_money,income_tot_money)");
					sql.append(" VALUES(");
					sql.append( aicf.getGroup_id() + ",");
					sql.append( aicf.getHos_id() + ",");
					sql.append( "'"+ aicf.getCopy_code() + "',");
					sql.append( "'"+entityMap.get("acct_year") + "',");
					sql.append( "'"+entityMap.get("acct_month") + "',");
					sql.append( aicf.getDept_id() + ",");
					sql.append( aicf.getDept_no() + ",");
					sql.append( "'"+aicf.getIncome_item_code() + "',");
					sql.append("0,");
					sql.append("0,");
					sql.append("0,");
					sql.append("0,");
					sql.append("0");
					sql.append(")");
					
					entityMap.put("sql", sql.toString());
					aphiTemplateDataMapper.initAphiTemplateDataForParseSql(entityMap);
				}
			}
			
			//3.支出数据准备表
			if("aphi_costitem_data".equals(table)){
				
				// 根据当前年月,序号,编码,查询方案
				List<AphiCostitemConfSeq> list = aphiCostitemConfSeqMapper.queryCostitemConfSeq(entityMap);
				
				if(list.size() == 0){
					return "{\"warn\":\"没有数据生成 \"}";
				}
				
				for(AphiCostitemConfSeq accs : list){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", accs.getGroup_id());
					map.put("hos_id", accs.getHos_id());
					map.put("copy_code", accs.getCopy_code());
					map.put("acct_year",entityMap.get("acct_year"));
					map.put("acct_month", entityMap.get("acct_month"));
					map.put("dept_id", accs.getDept_id());
					map.put("dept_no", accs.getDept_no());
					map.put("cost_item_code", accs.getCost_item_code());
					aphiCostitemDataMapper.deleteCostitemData(map);
					
					StringBuffer sql = new StringBuffer();
					sql.append("INSERT INTO " + table);
					sql.append(" (group_id,hos_id,copy_code,acct_year,acct_month,dept_id,dept_no,cost_item_code,prim_cost,prim_cost_ret,calc_cost,calc_cost_ret,cost_tot_ret)");
					sql.append(" VALUES(");
					sql.append( accs.getGroup_id() + ",");
					sql.append( accs.getHos_id() + ",");
					sql.append( "'"+ accs.getCopy_code() + "',");
					sql.append( "'"+entityMap.get("acct_year") + "',");
					sql.append( "'"+entityMap.get("acct_month") + "',");
					sql.append( accs.getDept_id() + ",");
					sql.append( accs.getDept_no() + ",");
					sql.append( "'"+ accs.getCost_item_code() + "',");
					sql.append("0,");
					sql.append("0,");
					sql.append("0,");
					sql.append("0,");
					sql.append("0");
					sql.append(")");
					
					entityMap.put("sql", sql.toString());
					aphiTemplateDataMapper.initAphiTemplateDataForParseSql(entityMap);
				}
			}
			
			
			//4.自定义核算项目点数准备表
			if("aphi_incomeitem_point_data".equals(table)){
				
				// 根据当前年月,序号,编码,查询方案
				List<AphiIncomeitemPointSeq> list = aphiIncomeitemPointSeqMapper.queryIncomeitemPointSeq(entityMap);
				
				if(list.size() == 0){
					return "{\"warn\":\"没有数据生成 \"}";
				}
				
				entityMap.put("is_stop", 0);
				List<AphiDeptDict> deptList = aphiDeptDictMapper.queryPrmDeptDict(entityMap);
				
				if(deptList.size() == 0){
					return "{\"warn\":\"请维护科室 \"}";
				}
				 
				for(AphiDeptDict dept : deptList){
					
					for(AphiIncomeitemPointSeq aips : list){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("group_id", aips.getGroup_id());
						map.put("hos_id", aips.getHos_id());
						map.put("copy_code", aips.getCopy_code());
						map.put("acct_year",entityMap.get("acct_year"));
						map.put("acct_month", entityMap.get("acct_month"));
						map.put("dept_id", dept.getDept_id());
						map.put("dept_no", dept.getDept_no());
						map.put("income_item_code", aips.getIncome_item_code());
						
						aphiIncomeitemPointDataMapper.deleteIncomeitemPointData(map);
						
						StringBuffer sql = new StringBuffer();
						sql.append("INSERT INTO " + table);
						sql.append(" (group_id,hos_id,copy_code,acct_year,acct_month,dept_id,dept_no,income_item_code,work_amount,work_point)");
						sql.append(" VALUES(");
						sql.append( aips.getGroup_id() + ",");
						sql.append( aips.getHos_id() + ",");
						sql.append( "'"+ aips.getCopy_code() + "',");
						sql.append( "'"+entityMap.get("acct_year") + "',");
						sql.append( "'"+entityMap.get("acct_month") + "',");
						sql.append( dept.getDept_id() + ",");
						sql.append( dept.getDept_no() + ",");
						sql.append( "'"+ aips.getIncome_item_code() + "',");
						sql.append("0,");
						sql.append("0");
						sql.append(")");
						
						entityMap.put("sql", sql.toString());
						aphiTemplateDataMapper.initAphiTemplateDataForParseSql(entityMap);
					}
				}
			} 
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \",\"state\":\"false \"}");
		}
	}
	
	public Map<String,AphiTemplateData> templateListToMap(List<AphiTemplateData> list){
		
		if(list.size() == 0){
			
			return null;
		}
		
		Map<String,AphiTemplateData> map = new HashMap<String,AphiTemplateData>();//用于存储数据
		
		for(int x = 0 ;x < list.size() ; x++){
			
			AphiTemplateData templateData = list.get(x);
			
			map.put("{"+templateData.getTemplate_detail_code()+"}", templateData);
		}
		
		
		return map;
	}


	@Override
	public List<Map<String, Object>> queryHpmTemplateDataForParseDataPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
			
			// 1.根据年月查询方案序号
			AphiSchemeConf sc = aphiSchemeConfMapper.querySchemeConfByYM(entityMap);
			
			//2.查询模板明细
			List<AphiTemplateData> atdList = aphiTemplateDataMapper.queryAphiTemplateDetailDataByTemplateCode(entityMap);
			
			//2.1 将明细结果集封装到Map,用于匹配替换
			Map<String,AphiTemplateData> templateDetailMap = templateListToMap(atdList);
			
			//3.获取模板名称
			String alias = String.valueOf(entityMap.get("template_table"));
			
				
				StringBuffer sql = new StringBuffer();//拼写SQL
				StringBuffer sql2 = new StringBuffer();//拼写SQL查询条件中的in()中的字段
				StringBuffer sql3 = new StringBuffer();//拼写关联项添加中的字段
				StringBuffer sum_sql = new StringBuffer();
				sql.append("SELECT ");
				sql.append(alias + ".group_id,");
				sql.append(alias + ".hos_id,");
				sql.append(alias + ".copy_code,");
				sql.append(alias + ".acct_year,");
				sql.append(alias + ".acct_month,");
				sql.append(alias + ".dept_id,");
				sql.append(alias + ".dept_no,");
				/*sql.append("aphi_dept_dict.dept_code,");
				sql.append("aphi_dept_dict.dept_name,");*/
				
				//----------------------------------------------根据配置拼写查询SQL begin-----------------------------------------------
				for(AphiTemplateData atd:atdList){
					
					if("01".equals(atd.getColumns_type()) || "02".equals(atd.getColumns_type())){//列类型:01为取值项,02为关联项
						
						String[] columns_views = atd.getColumns_view().split(",");//列字段:如  计提直接成本,001,PRIM_COST_RET
						
						String columns_table = 	atd.getColumns_table();	//列表名			
						 
						 if("02".equals(atd.getColumns_type())){
							 sql3.append(columns_table + "." + columns_views[1] + ",");
							 continue;
						 }
						 
						 //自定义工作量数据准备
						 if("aphi_workitem_data".equals(columns_table)){
							 sql.append("sum(case when " + columns_table + ".work_item_code ='" + columns_views[1] + "' then " + columns_table +".work_amount end) as "+ StringTool.toPinyinShouZiMu(columns_views[0]).toLowerCase() +"__" + columns_views[1] +",");
						 }
						 
						//自定义工作量数据准备
						 if("aphi_workitem_conf_seq".equals(columns_table)){
							 sql.append("sum(case when " + columns_table + ".work_item_code ='" + columns_views[1] + "' then " + columns_table +".work_standard end) as " + StringTool.toPinyinShouZiMu(columns_views[0]).toLowerCase() + "__" + columns_views[1] +",");
						 }
						 
						//自定义收入数据准备
						 if("aphi_incomeitem_data".equals(columns_table)){
							 sql.append("sum(case when " + columns_table + ".income_item_code ='" + columns_views[1] + "' then " + columns_table +"." + columns_views[2] + " end) as "+ StringTool.toPinyinShouZiMu(columns_views[0]).toLowerCase() +"__" + columns_views[1] +",");
						 }
						 
						 //自定义支出数据准备
						 if("aphi_costitem_data".equals(columns_table)){
							 sql.append("sum(case when " + columns_table + ".cost_item_code ='" + columns_views[1] + "' then " + columns_table +"." + columns_views[2] + " end) as "+ StringTool.toPinyinShouZiMu(columns_views[0]).toLowerCase() +"__" + columns_views[1] +",");
						 }
						 
						 //自定义核算项目点数准备
						 if("aphi_incomeitem_point_data".equals(columns_table)){
							 sql.append("sum(case when " + columns_table + ".income_item_code ='" + columns_views[1] + "' then " + columns_table +"." + columns_views[2] + " end) as "+ StringTool.toPinyinShouZiMu(columns_views[0]).toLowerCase() +"__" + columns_views[1] +",");
						 }
						 
						sql2.append("'" + columns_views[1] + "',");
					}
					
					
					if("03".equals(atd.getColumns_type()) || "04".equals(atd.getColumns_type())){//列类型:小计、合计
						
						String sum_alias = "";//SQL合计值别名,与grid的列name一致
						if("03".equals(atd.getColumns_type())){//小计
							sum_alias = "mincount_" + atd.getTemplate_detail_code();
						}
						
						if("04".equals(atd.getColumns_type())){//合计
							sum_alias = "totalCount";
						}
						
						String columns_view = atd.getColumns_view();//获取小计或者合计的列字段
						
						
						//将正则表达式对象作用在字符串上,获取匹配对象
						Matcher matcher = Pattern.compile("\\{.*?\\}").matcher(columns_view);
						 
						//匹配、获取、替换
						while (matcher.find()) {
							 
							String key = matcher.group() ;
							//根据明细编码获取明细数据
							AphiTemplateData data = templateDetailMap.get(key);
							//取出明细数据中的列字段,拼需要合计的列字段名称
							String[] dataViews = data.getColumns_view().split(",");
							//获取表名称
							String detailTable = data.getColumns_table();
							
							//将匹配到的明细编码,替换成SQL
							
							//自定义工作量数据准备
							if("aphi_workitem_data".equals(detailTable)){
								columns_view = columns_view.replace(key,
									"sum("
									+ "(case when " + detailTable + ".work_item_code = '" + dataViews[1] 
										+ "' then " + detailTable + ".work_amount end ))");
								continue;
							}
							//自定义工作量数据准备
							if("aphi_workitem_conf_seq".equals(detailTable)){
								columns_view = columns_view.replace(key,
									"sum("
									+ "(case when " + detailTable + ".work_item_code = '" + dataViews[1] 
										+ "' then " + detailTable + ".work_standard end ))");
								continue;
							}
							
							//自定义收入数据准备
							if("aphi_incomeitem_data".equals(detailTable)){
								columns_view = columns_view.replace(key,
									"sum("
									+ "(case when " + detailTable + ".work_item_code = '" + dataViews[1] 
										+ "' then " + detailTable + "." + dataViews[2] + "end ))");
								continue;
							}
							 
							 //自定义支出数据准备
							if("aphi_costitem_data".equals(detailTable)){
								columns_view = columns_view.replace(key,
									"sum("
									+ "(case when " + detailTable + ".cost_item_code = '" + dataViews[1] 
										+ "' then " + detailTable + "." + dataViews[2] +"end ))");
								continue;
							}
							 
							 //自定义核算项目点数准备
							 if("aphi_incomeitem_point_data".equals(detailTable)){
								columns_view = columns_view.replace(key,
									"sum("
									+ "(case when " + detailTable + ".income_item_code = '" + dataViews[1] 
										+ "' then " + detailTable + "." + dataViews[2] +"end ))");
							 }
						 }
						 
						 sum_sql.append(columns_view + " as " + sum_alias + ",");
					}
				}
				
				if(sql3.length() > 0 ){
					sql.append(sql3);//拼接关联字段名称
				}
				
				if(sum_sql.length() > 0 ){
					sql.append(sum_sql);//拼接合计串
				}
				
				sql = sql.deleteCharAt(sql.length() -1 );
				sql2 = sql2.deleteCharAt(sql2.length() - 1);
				
				
			//----------------------------------------------根据配置拼写查询SQL end-----------------------------------------------
				
			//1.自定义工作量数据准备 aphi_workitem_data
			if("aphi_workitem_data".equals(alias)){
				
				sql.append(" from aphi_workitem_data aphi_workitem_data ");
				
				sql.append("left join aphi_dept_dict aphi_dept_dict ");
				sql.append("on aphi_workitem_data.dept_id = aphi_dept_dict.dept_id ");
				sql.append("and aphi_workitem_data.dept_no = aphi_dept_dict.dept_no ");
				sql.append("and aphi_workitem_data.copy_code = aphi_dept_dict.copy_code ");
				sql.append("and aphi_workitem_data.group_id = aphi_dept_dict.group_id ");
				sql.append("and aphi_workitem_data.hos_id = aphi_dept_dict.hos_id ");
				sql.append("and aphi_workitem_data.hos_id = aphi_dept_dict.hos_id ");
				
				sql.append("left join aphi_workitem aphi_workitem ");
				sql.append("on aphi_workitem_data.work_item_code = aphi_workitem.work_item_code ");
				sql.append("and aphi_workitem_data.group_id = aphi_workitem.group_id ");
				sql.append("and aphi_workitem_data.copy_code = aphi_workitem.copy_code ");
				sql.append("and aphi_workitem_data.hos_id = aphi_workitem.hos_id ");
				
				sql.append("left join aphi_workitem_conf aphi_workitem_conf ");
				sql.append("on aphi_workitem_data.group_id = aphi_workitem_conf.group_id ");
				sql.append("and aphi_workitem_data.hos_id = aphi_workitem_conf.hos_id ");
				sql.append("and aphi_workitem_data.copy_code = aphi_workitem_conf.copy_code ");
				sql.append("and aphi_workitem_data.work_item_code = aphi_workitem_conf.work_item_code ");
				sql.append("and aphi_workitem_data.dept_id = aphi_workitem_conf.dept_id ");
				sql.append("and aphi_workitem_data.dept_no = aphi_workitem_conf.dept_no ");
				
				sql.append("left join aphi_workitem_conf_seq aphi_workitem_conf_seq ");
				sql.append("on aphi_workitem_data.group_id = aphi_workitem_conf_seq.group_id ");
				sql.append("and aphi_workitem_data.hos_id = aphi_workitem_conf_seq.hos_id ");
				sql.append("and aphi_workitem_data.copy_code = aphi_workitem_conf_seq.copy_code ");
				sql.append("and aphi_workitem_data.work_item_code = aphi_workitem_conf_seq.work_item_code ");
				sql.append("and aphi_workitem_data.dept_id = aphi_workitem_conf_seq.dept_id ");
				sql.append("and aphi_workitem_data.dept_no = aphi_workitem_conf_seq.dept_no ");
				sql.append("and aphi_workitem_conf_seq.scheme_seq_no = '" +  String.valueOf(sc.getScheme_seq_no()) + "' ");
				
				sql.append("where aphi_workitem_data.group_id = '"+String.valueOf(entityMap.get("group_id"))+"' ");
				sql.append("and aphi_workitem_data.hos_id = '" +String.valueOf(entityMap.get("hos_id"))+ "' ");
				sql.append("and aphi_workitem_data.copy_code = '" + String.valueOf(entityMap.get("copy_code")) + "' ");
				sql.append("and aphi_workitem_data.acct_year = '"+String.valueOf(entityMap.get("acct_year"))+"'");
				sql.append("and aphi_workitem_data.acct_month = '" +String.valueOf(entityMap.get("acct_month"))+ "' ");
				if(entityMap.get("dept_id") != null && !"".equals(entityMap.get("dept_id"))){
					sql.append("and aphi_workitem_data.dept_id = '" +String.valueOf(entityMap.get("dept_id"))+ "' ");
				}
				
				if(entityMap.get("dept_no") != null && !"".equals(entityMap.get("dept_no"))){
					sql.append("and aphi_workitem_data.dept_no = '" +String.valueOf(entityMap.get("dept_no"))+ "' ");
				}
				
				sql.append("and aphi_workitem_data.work_item_code in (" + sql2 + ") ");
				sql.append("group by aphi_workitem_data.group_id,");
				sql.append("aphi_workitem_data.hos_id,");
				sql.append("aphi_workitem_data.copy_code,");
				sql.append("aphi_workitem_data.acct_year,");
				sql.append("aphi_workitem_data.acct_month,");
				sql.append(sql3);
				sql.append("aphi_workitem_data.dept_id,");
				sql.append("aphi_workitem_data.dept_no ");
				sql.append("and exists(select 1 from v_user_data_perm a where a.group_id = #{group_id} and a.hos_id = #{hos_id} and a.copy_code = #{copy_code} and a.user_id = #{user_id} and a.mod_code = '09' and a.table_code = 'APHI_DEPT_DICT' and a.perm_code = aphi_workitem_data.dept_id and a.is_read = 1 and a.is_write = 1)");
				sql.append(" order by aphi_workitem_data.dept_id asc");
				entityMap.put("sql", sql.toString());
			
			}
			
			
			//2.自定义收入数据准备表 aphi_incomeitem_data
			if("aphi_incomeitem_data".equals(alias)){
				
				
				sql.append(" from aphi_incomeitem_data aphi_incomeitem_data ");
				
				sql.append("left join aphi_dept_dict aphi_dept_dict ");
				sql.append("on aphi_incomeitem_data.group_id = aphi_dept_dict.group_id ");
				sql.append("and aphi_incomeitem_data.hos_id = aphi_dept_dict.hos_id ");
				sql.append("and aphi_incomeitem_data.copy_code = aphi_dept_dict.copy_code ");
				sql.append("and aphi_incomeitem_data.dept_id = aphi_dept_dict.dept_id ");
				sql.append("and aphi_incomeitem_data.dept_no = aphi_dept_dict.dept_no ");
				
				sql.append("left join aphi_incomeitem aphi_incomeitem ");
				sql.append("on aphi_incomeitem_data.group_id = aphi_incomeitem.group_id ");
				sql.append("and aphi_incomeitem_data.hos_id = aphi_incomeitem.hos_id ");
				sql.append("and aphi_incomeitem_data.copy_code = aphi_incomeitem.copy_code ");
				sql.append("and aphi_incomeitem_data.income_item_code = aphi_incomeitem.income_item_code ");
				sql.append("and aphi_incomeitem.is_stop = 0  ");
				
				sql.append("left join aphi_incomeitem_conf aphi_incomeitem_conf ");
				sql.append("on aphi_incomeitem_data.group_id = aphi_incomeitem_conf.group_id ");
				sql.append("and aphi_incomeitem_data.hos_id = aphi_incomeitem_conf.hos_id ");
				sql.append("and aphi_incomeitem_data.copy_code = aphi_incomeitem_conf.copy_code ");
				sql.append("and aphi_incomeitem_data.dept_id = aphi_incomeitem_conf.dept_id ");
				sql.append("and aphi_incomeitem_data.dept_no = aphi_incomeitem_conf.dept_no ");
				sql.append("and aphi_incomeitem_data.income_item_code = aphi_incomeitem_conf.income_item_code ");
				
				sql.append("left join aphi_incomeitem_conf_seq aphi_incomeitem_conf_seq ");
				sql.append("on aphi_incomeitem_data.group_id = aphi_incomeitem_conf_seq.group_id ");
				sql.append("and aphi_incomeitem_data.hos_id = aphi_incomeitem_conf_seq.hos_id ");
				sql.append("and aphi_incomeitem_data.copy_code = aphi_incomeitem_conf_seq.copy_code ");
				sql.append("and aphi_incomeitem_data.income_item_code = aphi_incomeitem_conf_seq.income_item_code ");
				sql.append("and aphi_incomeitem_data.dept_id = aphi_incomeitem_conf_seq.dept_id ");
				sql.append("and aphi_incomeitem_data.dept_no = aphi_incomeitem_conf_seq.dept_no ");
				sql.append("and aphi_incomeitem_conf_seq.scheme_seq_no = '" +  String.valueOf(sc.getScheme_seq_no()) + "' ");
				
				sql.append("where aphi_incomeitem_data.group_id = '"+String.valueOf(entityMap.get("group_id"))+"' ");
				sql.append("and aphi_incomeitem_data.hos_id = '" +String.valueOf(entityMap.get("hos_id"))+ "' ");
				sql.append("and aphi_incomeitem_data.copy_code = '" + String.valueOf(entityMap.get("copy_code")) + "' ");
				sql.append("and aphi_incomeitem_data.acct_year = '"+String.valueOf(entityMap.get("acct_year"))+"'");
				sql.append("and aphi_incomeitem_data.acct_month = '" +String.valueOf(entityMap.get("acct_month"))+ "' ");
				if(entityMap.get("dept_id") != null && !"".equals(entityMap.get("dept_id"))){
					sql.append("and aphi_incomeitem_data.dept_id = '" +String.valueOf(entityMap.get("dept_id"))+ "' ");
				}
				
				if(entityMap.get("dept_no") != null && !"".equals(entityMap.get("dept_no"))){
					sql.append("and aphi_incomeitem_data.dept_no = '" +String.valueOf(entityMap.get("dept_no"))+ "' ");
				}
				
				sql.append("and aphi_incomeitem_data.income_item_code in (" + sql2 + ") ");
				sql.append("group by aphi_incomeitem_data.group_id,");
				sql.append("aphi_incomeitem_data.hos_id,");
				sql.append("aphi_incomeitem_data.copy_code,");
				sql.append("aphi_incomeitem_data.acct_year,");
				sql.append("aphi_incomeitem_data.acct_month,");
				sql.append(sql3);
				sql.append("aphi_incomeitem_data.dept_id,");
				sql.append("aphi_incomeitem_data.dept_no ");
				sql.append("and exists(select 1 from v_user_data_perm a where a.group_id = #{group_id} and a.hos_id = #{hos_id} and a.copy_code = #{copy_code} and a.user_id = #{user_id} and a.mod_code = '09' and a.table_code = 'APHI_DEPT_DICT' and a.perm_code = aphi_incomeitem_data.dept_id and a.is_read = 1 and a.is_write = 1)");
				sql.append(" order by aphi_incomeitem_data.dept_id asc");
				entityMap.put("sql", sql.toString());
			}
			
			
			//3.自定义支出数据准备表 aphi_costitem_data
			if("aphi_costitem_data".equals(alias)){
				
				sql.append(" from aphi_costitem_data aphi_costitem_data ");
				
				sql.append("left join aphi_dept_dict aphi_dept_dict ");
				sql.append("on aphi_costitem_data.group_id = aphi_dept_dict.group_id ");
				sql.append("and aphi_costitem_data.hos_id = aphi_dept_dict.hos_id ");
				sql.append("and aphi_costitem_data.copy_code = aphi_dept_dict.copy_code ");
				sql.append("and aphi_costitem_data.dept_id = aphi_dept_dict.dept_id ");
				sql.append("and aphi_costitem_data.dept_no = aphi_dept_dict.dept_no ");
				
				sql.append("left join aphi_costitem aphi_costitem ");
				sql.append("on aphi_costitem_data.group_id = aphi_costitem.group_id ");
				sql.append("and aphi_costitem_data.hos_id = aphi_costitem.hos_id ");
				sql.append("and aphi_costitem_data.copy_code = aphi_costitem.copy_code ");
				sql.append("and aphi_costitem_data.cost_item_code = aphi_costitem.cost_item_code ");
				sql.append("and aphi_costitem.is_stop = 0  ");
				
				sql.append("left join aphi_costitem_conf aphi_costitem_conf ");
				sql.append("on aphi_costitem_data.group_id = aphi_costitem_conf.group_id ");
				sql.append("and aphi_costitem_data.hos_id = aphi_costitem_conf.hos_id ");
				sql.append("and aphi_costitem_data.copy_code = aphi_costitem_conf.copy_code ");
				sql.append("and aphi_costitem_data.dept_id = aphi_costitem_conf.dept_id ");
				sql.append("and aphi_costitem_data.dept_no = aphi_costitem_conf.dept_no ");
				sql.append("and aphi_costitem_data.cost_item_code = aphi_costitem_conf.cost_item_code ");
				
				sql.append("left join aphi_costitem_conf_seq aphi_costitem_conf_seq ");
				sql.append("on aphi_costitem_data.group_id = aphi_costitem_conf_seq.group_id ");
				sql.append("and aphi_costitem_data.hos_id = aphi_costitem_conf_seq.hos_id ");
				sql.append("and aphi_costitem_data.copy_code = aphi_costitem_conf_seq.copy_code ");
				sql.append("and aphi_costitem_data.cost_item_code = aphi_costitem_conf_seq.cost_item_code ");
				sql.append("and aphi_costitem_data.dept_id = aphi_costitem_conf_seq.dept_id ");
				sql.append("and aphi_costitem_data.dept_no = aphi_costitem_conf_seq.dept_no ");
				sql.append("and aphi_costitem_conf_seq.scheme_seq_no = '" +  String.valueOf(sc.getScheme_seq_no()) + "' ");
				
				sql.append("where aphi_costitem_data.group_id = '"+String.valueOf(entityMap.get("group_id"))+"' ");
				sql.append("and aphi_costitem_data.hos_id = '" +String.valueOf(entityMap.get("hos_id"))+ "' ");
				sql.append("and aphi_costitem_data.copy_code = '" + String.valueOf(entityMap.get("copy_code")) + "' ");
				sql.append("and aphi_costitem_data.acct_year = '"+String.valueOf(entityMap.get("acct_year"))+"'");
				sql.append("and aphi_costitem_data.acct_month = '" +String.valueOf(entityMap.get("acct_month"))+ "' ");
				if(entityMap.get("dept_id") != null && !"".equals(entityMap.get("dept_id"))){
					sql.append("and aphi_costitem_data.dept_id = '" +String.valueOf(entityMap.get("dept_id"))+ "' ");
				}
				
				if(entityMap.get("dept_no") != null && !"".equals(entityMap.get("dept_no"))){
					sql.append("and aphi_costitem_data.dept_no = '" +String.valueOf(entityMap.get("dept_no"))+ "' ");
				}
				
				sql.append("and aphi_costitem_data.cost_item_code in (" + sql2 + ") ");
				sql.append("group by aphi_costitem_data.group_id,");
				sql.append("aphi_costitem_data.hos_id,");
				sql.append("aphi_costitem_data.copy_code,");
				sql.append("aphi_costitem_data.acct_year,");
				sql.append("aphi_costitem_data.acct_month,");
				sql.append(sql3);
				sql.append("aphi_costitem_data.dept_id,");
				sql.append("aphi_costitem_data.dept_no ");
				sql.append("and exists(select 1 from v_user_data_perm a where a.group_id = #{group_id} and a.hos_id = #{hos_id} and a.copy_code = #{copy_code} and a.user_id = #{user_id} and a.mod_code = '09' and a.table_code = 'APHI_DEPT_DICT' and a.perm_code = aphi_costitem_data.dept_id and a.is_read = 1 and a.is_write = 1)");
				sql.append(" order by aphi_costitem_data.dept_id asc");
				entityMap.put("sql", sql.toString());
			}
			
			
			//4.自定义核算项目点数准备表 aphi_incomeitem_point_data
			if("aphi_incomeitem_point_data".equals(alias)){
				
				sql.append(" from aphi_incomeitem_point_data aphi_incomeitem_point_data ");
				
				sql.append("left join aphi_dept_dict aphi_dept_dict ");
				sql.append("on aphi_incomeitem_point_data.group_id = aphi_dept_dict.group_id ");
				sql.append("and aphi_incomeitem_point_data.hos_id = aphi_dept_dict.hos_id ");
				sql.append("and aphi_incomeitem_point_data.copy_code = aphi_dept_dict.copy_code ");
				sql.append("and aphi_incomeitem_point_data.dept_id = aphi_dept_dict.dept_id ");
				sql.append("and aphi_incomeitem_point_data.dept_no = aphi_dept_dict.dept_no ");
				
				sql.append("left join aphi_incomeitem_point aphi_incomeitem_point ");
				sql.append("on aphi_incomeitem_point_data.group_id = aphi_incomeitem_point.group_id ");
				sql.append("and aphi_incomeitem_point_data.hos_id = aphi_incomeitem_point.hos_id ");
				sql.append("and aphi_incomeitem_point_data.copy_code = aphi_incomeitem_point.copy_code ");
				sql.append("and aphi_incomeitem_point_data.income_item_code = aphi_incomeitem_point.income_item_code ");
				//sql.append("and aphi_incomeitem_point.is_stop = 0  ");
				
				sql.append("left join aphi_incomeitem_conf aphi_incomeitem_conf ");
				sql.append("on aphi_incomeitem_point_data.group_id = aphi_incomeitem_conf.group_id ");
				sql.append("and aphi_incomeitem_point_data.hos_id = aphi_incomeitem_conf.hos_id ");
				sql.append("and aphi_incomeitem_point_data.copy_code = aphi_incomeitem_conf.copy_code ");
				sql.append("and aphi_incomeitem_point_data.dept_id = aphi_incomeitem_conf.dept_id ");
				sql.append("and aphi_incomeitem_point_data.dept_no = aphi_incomeitem_conf.dept_no ");
				sql.append("and aphi_incomeitem_point_data.income_item_code = aphi_incomeitem_conf.income_item_code ");
				
				sql.append("left join aphi_incomeitem_conf_seq aphi_incomeitem_conf_seq ");
				sql.append("on aphi_incomeitem_point_data.group_id = aphi_incomeitem_conf_seq.group_id ");
				sql.append("and aphi_incomeitem_point_data.hos_id = aphi_incomeitem_conf_seq.hos_id ");
				sql.append("and aphi_incomeitem_point_data.copy_code = aphi_incomeitem_conf_seq.copy_code ");
				sql.append("and aphi_incomeitem_point_data.dept_id = aphi_incomeitem_conf_seq.dept_id ");
				sql.append("and aphi_incomeitem_point_data.dept_no = aphi_incomeitem_conf_seq.dept_no ");
				sql.append("and aphi_incomeitem_point_data.income_item_code = aphi_incomeitem_conf_seq.income_item_code ");
				sql.append("and aphi_incomeitem_conf_seq.scheme_seq_no = '" +  String.valueOf(sc.getScheme_seq_no()) + "' ");
				
				sql.append("where aphi_incomeitem_point_data.group_id = '"+String.valueOf(entityMap.get("group_id"))+"' ");
				sql.append("and aphi_incomeitem_point_data.hos_id = '" +String.valueOf(entityMap.get("hos_id"))+ "' ");
				sql.append("and aphi_incomeitem_point_data.copy_code = '" + String.valueOf(entityMap.get("copy_code")) + "' ");
				sql.append("and aphi_incomeitem_point_data.acct_year = '"+String.valueOf(entityMap.get("acct_year"))+"'");
				sql.append("and aphi_incomeitem_point_data.acct_month = '" +String.valueOf(entityMap.get("acct_month"))+ "' ");
				if(entityMap.get("dept_id") != null && !"".equals(entityMap.get("dept_id"))){
					sql.append("and aphi_incomeitem_point_data.dept_id = '" +String.valueOf(entityMap.get("dept_id"))+ "' ");
				}
				
				if(entityMap.get("dept_no") != null && !"".equals(entityMap.get("dept_no"))){
					sql.append("and aphi_incomeitem_point_data.dept_no = '" +String.valueOf(entityMap.get("dept_no"))+ "' ");
				}
				
				sql.append("and aphi_incomeitem_point_data.income_item_code in (" + sql2 + ") ");
				sql.append("group by aphi_incomeitem_point_data.group_id,");
				sql.append("aphi_incomeitem_point_data.hos_id,");
				sql.append("aphi_incomeitem_point_data.copy_code,");
				sql.append("aphi_incomeitem_point_data.acct_year,");
				sql.append("aphi_incomeitem_point_data.acct_month,");
				sql.append(sql3);
				sql.append("aphi_incomeitem_point_data.dept_id,");
				sql.append("aphi_incomeitem_point_data.dept_no ");
				sql.append("and exists(select 1 from v_user_data_perm a where a.group_id = #{group_id} and a.hos_id = #{hos_id} and a.copy_code = #{copy_code} and a.user_id = #{user_id} and a.mod_code = '09' and a.table_code = 'APHI_DEPT_DICT' and a.perm_code = aphi_incomeitem_point_data.dept_id and a.is_read = 1 and a.is_write = 1)");
				sql.append(" order by aphi_incomeitem_point_data.dept_id asc");
				entityMap.put("sql", sql.toString());
			}
			
			List<Map<String,Object>> list = aphiTemplateDataMapper.queryAphiTemplateDataForParseSql(entityMap);

			return list;
			
	}
}
