package com.chd.hrp.acc.serviceImpl.autovouch;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.autovouch.AccBusiTemplateDetailMapper;
import com.chd.hrp.acc.dao.autovouch.AccMedAutoVouchMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.service.autovouch.AccMedAutoVouchService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
import com.chd.hrp.sys.entity.BusiQuery;
import com.github.pagehelper.PageInfo;
@Service("accMedAutoVouchService")
public class AccMedAutoVouchServiceImpl implements AccMedAutoVouchService {
	
	private static Logger logger = Logger.getLogger(AccMedAutoVouchServiceImpl.class);

	@Resource(name = "accMedAutoVouchMapper")
	private final AccMedAutoVouchMapper accMedAutoVouchMapper = null;
	
	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;
	
	
	@Resource(name = "accBusiTemplateDetailMapper")
	private final AccBusiTemplateDetailMapper accBusiTemplateDetailMapper = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	
	//查询表头
	public String queryMedAutoVouchHead(Map<String, Object> entityMap) throws DataAccessException {
		
		List<BusiQuery> list = accMedAutoVouchMapper.queryMedAutoVouchHead(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	//查询
	@Override
	public String queryMedAutoVouch(Map<String, Object> entityMap) throws DataAccessException {
		
		List<BusiQuery> busiQueryList = accMedAutoVouchMapper.queryMedAutoVouchHead(entityMap);
		
		if(busiQueryList==null || busiQueryList.size()==0){
			throw new SysException("SYS_BUSI_QUERY表没有配置！");
		}
		
		BusiQuery busiQuery = busiQueryList.get(0);
		
		String view_name = busiQuery.getMain_table();
		
	//	String busi_type_code = busiQuery.getBusi_type_code();
		
		String detail_view_name = busiQuery.getDetail_table();
		
		String group_sql = busiQuery.getGroup_by();
		
		String where_sql=busiQuery.getWhere_sql()==null?"":busiQuery.getWhere_sql();
		
		String business_no = busiQuery.getBusiness_no();
		
		String log_name = busiQuery.getLog_name();
		
		String main_key_field = busiQuery.getMain_key_field();
		
		String head_names [] = busiQuery.getHead_name().split(",");
		
		StringBuilder field = new StringBuilder();
		
		StringBuilder fieldTotal = new StringBuilder();
		
		for(int i = 0 ; i < head_names.length; i++){
			
			if(head_names[i].split("\\|")[2].equalsIgnoreCase("VOUCH_NO")){
				continue;
			}
			
			if(head_names[i].split("\\|")[0].equalsIgnoreCase("AMOUNT_MONEY")){
				fieldTotal.append("sum("+head_names[i].split("\\|")[0]+") "+head_names[i].split("\\|")[0]+",");
			}else if(head_names[i].split("\\|")[0].equalsIgnoreCase("BUSINESS_NO")){
				fieldTotal.append("'合计' "+head_names[i].split("\\|")[0]+",");
			}else{
				fieldTotal.append("null "+head_names[i].split("\\|")[0]+",");
			}
			
			if(head_names[i].split("\\|")[1].indexOf("年月")!=-1){
				field.append("to_char("+head_names[i].split("\\|")[2]+",'yyyyMM') "+head_names[i].split("\\|")[0]+",");
			}else{
				field.append(head_names[i].split("\\|")[2]+",");
			}
			
		}
		
		if(field!=null && field.length()>0){
			field.setCharAt(field.length()-1,' ');
		}
		
		fieldTotal.append("null VOUCH_NO,0 VOUCH_ID");
		
		entityMap.put("view_name", view_name);
		
		entityMap.put("detail_view_name", detail_view_name);
		
		entityMap.put("log_table_name",log_name);
		
		entityMap.put("business_no", business_no);
		
		entityMap.put("main_key_field", main_key_field);
		
		entityMap.put("field", field.toString());
		
		entityMap.put("fieldTotal", fieldTotal.toString());
		
		if("080110".equals(entityMap.get("busi_type_code").toString())){
			//采购发票只查询暂估凭证
			where_sql+=" and v_med_bill_main.BUSINESS_NO in(select business_no from ACC_BUSI_LOG_080101 where v_med_bill_main.group_id=group_id and v_med_bill_main.hos_id=hos_id and v_med_bill_main.copy_code=copy_code and template_code='002' union select business_no from ACC_BUSI_LOG_080102 where v_med_bill_main.group_id=group_id and v_med_bill_main.hos_id=hos_id and v_med_bill_main.copy_code=copy_code and template_code='002')";
		}
		
		if(where_sql!=null && !where_sql.equals("")){
			entityMap.put("where_sql", where_sql);
		}
		
		if(group_sql!=null && !group_sql.equals("")){
			entityMap.put("group_sql", "group by "+group_sql);
		}
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Map<String, Object>> list = accMedAutoVouchMapper.queryMedAutoVouch(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
	}

	//根据业务类型查询凭证Json（停用，采用新的处理方式）
	@Override
	public String queryVouchJsonByMed(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			
			int initType=Integer.parseInt(map.get("init_type").toString());
			String groupId=map.get("group_id").toString();
			String hosId=map.get("hos_id").toString();
			String copyCode=map.get("copy_code").toString();
			String modCode=map.get("mod_code").toString();
			String busiDateB=map.get("busi_date_b").toString();
			String busiDateE=map.get("busi_date_e").toString();
			String vouch_date=map.get("vouch_date").toString();
			String accYear=vouch_date.substring(0, 4);
			String accMonth=vouch_date.substring(5, 7);
			map.put("acc_year", accYear);
			
			List<Map<String, Object>> tempList=accBusiTemplateDetailMapper.queryAccBusiTemplateByCode(map);
			if(tempList==null || tempList.size()==0){
				return "{\"error\":\"没有配置凭证模板！\"}";
			}
			
			//查询凭证视图字段
			List<Map<String, Object>> viewList=new ArrayList<Map<String, Object>>();
			viewList=accMedAutoVouchMapper.queryAutoVouchView(map);
			if(viewList==null || viewList.size()==0){
				return "{\"error\":\"没有配置内置数据：SYS_BUSI_VIEW！\"}";
			}
			
			
			String busNo="";
			String summary="";
			
			//按日期生成,拼日期摘要
			if(map.get("busi_date_b").toString().equalsIgnoreCase(map.get("busi_date_e").toString())){
				summary=map.get("busi_date_b").toString()+"日";
			}else{
				summary=Integer.parseInt(map.get("busi_date_b").toString().substring(8,10))+"-"+Integer.parseInt(map.get("busi_date_e").toString().substring(8,10))+"日";
			}
			
			
			List<BusiQuery> busiQueryList = accMedAutoVouchMapper.queryMedAutoVouchHead(map);
			if(busiQueryList==null || busiQueryList.size()==0){
				return "{\"error\":\"没有配置内置数据：SYS_BUSI_QUERY！\"}";
			}
			BusiQuery busiQuery = busiQueryList.get(0);
			map.put("query_view_name", busiQuery.getMain_table());
			map.put("query_main_key_fielde", busiQuery.getMain_key_field());
			map.put("query_where_sql", busiQuery.getWhere_sql());
			List<String> busiIdList = accMedAutoVouchMapper.queryMedAutoVouchBusiIdList(map);
			if(busiIdList==null || busiIdList.size()==0){
				return "{\"error\":\"请先查询数据！\"}";
			}
			
			busNo=busiIdList.toString().replace("[", "").replace("]", "").replace(" ", "");
			busNo=busNo.replace(",", "','");
			busNo="'"+busNo+"'";
			map.put("busi_no", busNo);
			
			//判断是否生成凭证
			int reCount=accMedAutoVouchMapper.queryAutoVouchIsCreate(map);
			if (reCount>0) {
				return "{\"error\":\"有数据已经生成凭证！\"}";
			}
			
			List<List<Map<String, Object>>> detailList=new ArrayList<List<Map<String, Object>>>();
			List<Map<String, Object>> metaList=null;
			List<Map<String, Object>> checkList=new ArrayList<Map<String, Object>>();
			Map<Integer,List<Map<String, Object>>> checkMap=new HashMap<Integer,List<Map<String, Object>>>();
			int isDetailSummary=Integer.parseInt(tempList.get(0).get("IS_DETAIL_SUMMARY").toString());
			
			
			int index=0;
			Map<String,String> checkEl=null;
			for(Map<String, Object> tempMap:tempList){
				
				String metaCode=tempMap.get("META_CODE").toString();
				map.put("direction", tempMap.get("DIRECTION"));
				map.put("vouch_row", tempMap.get("VOUCH_ROW"));
				
				//0不按明细摘要，1按明细摘要
				if(isDetailSummary==0){
					map.put("summary",tempMap.get("M_SUMMARY"));
				}else{
					map.put("summary",tempMap.get("D_SUMMARY")+summary);
				}
				
				String main_table="";
				String detail_table="";
				String item_table="";
				String amount_money="";
				String sub_type_id="";
				String while_sql="";
				String busi_date="";
				String main_key_filed="";
				
				String detail_key_filed="";
				StringBuilder inner_sql1=new StringBuilder();
				StringBuilder inner_sql2=new StringBuilder();
				StringBuilder vouch_sql=new StringBuilder();
				StringBuilder where_sql=new StringBuilder();
				checkEl=new HashMap<String,String>();//检索辅助核算规则
				checkEl.put("busi_type_code", map.get("busi_type_code").toString());
				for(Map<String,Object> view:viewList){
					//取当前元素的对应关系
					if(!view.get("META_CODE").equals(metaCode)){
						continue;
					}
					
					String FIELD_ALIAS=view.get("FIELD_ALIAS").toString();
					//存业务字段与辅助核算字段的关系，CHECK4_ID:V_MAT_IN_MAIN.STORE_ID
					if(FIELD_ALIAS.indexOf("CHECK")!=-1 && checkEl.get(FIELD_ALIAS)==null){
						checkEl.put(FIELD_ALIAS, view.get("VIEW_FIELD").toString());
					}
					
					//取主表、明细表、第三层表
					if(main_table.equals("") && detail_table.equals("") && item_table.equals("")){
						main_table=view.get("MAIN_TABLE")==null?"":view.get("MAIN_TABLE").toString();
						detail_table=view.get("DETAIL_TABLE")==null?"":view.get("DETAIL_TABLE").toString();
						item_table=view.get("ITEM_TABLE")==null?"":view.get("ITEM_TABLE").toString();
					}
					
					//取科目与对应关系的关联字段
					if(FIELD_ALIAS.equalsIgnoreCase("SUB_TYPE_ID")){
						sub_type_id=view.get("VIEW_FIELD")==null?"":view.get("VIEW_FIELD").toString();
						while_sql=view.get("WHILE_SQL")==null?"":view.get("WHILE_SQL").toString();
					}
					
					//取汇总金额
					if(FIELD_ALIAS.equalsIgnoreCase("AMT_MONEY")){
						amount_money=view.get("VIEW_FIELD")==null?"":view.get("VIEW_FIELD").toString();
					}
					
					//取单据日期
					if(FIELD_ALIAS.equalsIgnoreCase("OCCUR_DATE")){
						busi_date=view.get("VIEW_FIELD")==null?"":view.get("VIEW_FIELD").toString();
					}
					
					//取主从表关联字段
					if(main_key_filed.equals("") && view.get("MAIN_KEY_FIELD")!=null && !view.get("MAIN_KEY_FIELD").equals("")){
						main_key_filed=view.get("MAIN_KEY_FIELD").toString();
						
					}
					
					//取从表第三层表关联字段
					if(detail_key_filed.equals("") && view.get("ITEM_KEY_FIELD")!=null && !view.get("ITEM_KEY_FIELD").equals("")){
						detail_key_filed=view.get("ITEM_KEY_FIELD").toString();
					}
				}
				
				if(main_table.equals("") || amount_money.equals("") || sub_type_id.equals("")){
					throw new SysException(metaCode+"缺少内置数据：字段MAIN_TABLE，字段FIELD_ALIAS【amount_money】，字段FIELD_CODE【SUB_TYPE_ID】！");
				}
				
				if(detail_table.equals("")){
					throw new SysException(metaCode+"缺少内置数据：字段detail_table！");
				}
				
//				//关联主从表
//				if(!main_key_filed.equals("") && !detail_table.equals("")){
//					inner_sql1.append(" inner join "+detail_table+" on ");
//					inner_sql1.append(main_table+".group_id="+detail_table+".group_id and "+main_table+".hos_id="+detail_table+".hos_id and "+main_table+".copy_code="+detail_table+".copy_code ");
//					inner_sql1.append(" and "+main_table+"."+main_key_filed+"="+detail_table+"."+main_key_filed);
//				}
//				
//				//关联从表第三层表
//				if(!detail_key_filed.equals("") && !detail_table.equals("") && !item_table.equals("")){
//					inner_sql2.append(" inner join "+item_table+" on ");
//					inner_sql2.append(item_table+".group_id="+detail_table+".group_id and "+item_table+".hos_id="+detail_table+".hos_id and "+item_table+".copy_code="+detail_table+".copy_code ");
//					inner_sql2.append(" and "+item_table+"."+main_key_filed+"="+detail_table+"."+main_key_filed);
//				}
				
				//开始组装查询自动凭证SQL
				if(inner_sql1!=null && !inner_sql1.toString().equals("")){
					vouch_sql.append(inner_sql1.toString());
				}
				
				if(inner_sql2!=null && !inner_sql2.toString().equals("")){
					vouch_sql.append(inner_sql2.toString());
				}
				//关联科目关系
				vouch_sql.append(" inner join acc_busi_map b on b.group_id="+detail_table+".group_id and b.hos_id="+detail_table+".hos_id and b.copy_code="+detail_table+".copy_code "); 
				vouch_sql.append(" and b.acc_year='"+accYear+"' and b.mod_code='"+modCode+"' and b.meta_code='"+metaCode+"' and (b.sub_type_id	="+sub_type_id+" or b.sub_type_id is  null) ");
				//where条件
				where_sql.append(" where b.group_id="+groupId+" and b.hos_id="+hosId+" and b.copy_code='"+copyCode+"' ");
				
				
				if(initType==1 || initType==2){
					//按单据生成
					if(main_key_filed==null || main_key_filed.equals("")){
						throw new SysException("没有配置单据ID字段：main_key_filed！");
					}
					
					where_sql.append(" and "+detail_table+"."+main_key_filed+" in("+busNo+") ");
				}else if(initType==3){
					//按日期生成
					if(busi_date==null || busi_date.equals("")){
						throw new SysException("没有配置单据日期字段：busi_date！");
					}
					where_sql.append(" and to_char("+busi_date+",'yyyy-MM-dd') between '"+busiDateB+"' and '"+busiDateE+"' ");
					
				}
				
				//过滤业务类型
				if(!while_sql.equals("")){
					where_sql.append(" and "+while_sql);
				}
				
				String selSql="select b.subj_code,sum("+amount_money+") amount_money from "+main_table+" ";
				map.put("meta_code", metaCode);
				map.put("vouch_sql", selSql+vouch_sql.toString());
				map.put("where_sql", where_sql.toString());
				metaList=accMedAutoVouchMapper.queryVouchJsonByBusi(map);
				if(metaList!=null && metaList.size()>0){
					//分录
					detailList.add(metaList);
					
					//辅助核算
					for(Map<String, Object> detailMap:metaList){
						if(detailMap.get("is_check")==null || detailMap.get("is_check").toString().equals("0")){
							continue;
						}
						
						detailMap.put("vouch_date", vouch_date);
						List<String> checkSqlList=superVouchService.getCheckSqlBySubjId(detailMap,checkEl);
						if(checkSqlList!=null && checkSqlList.size()>0){
							String checkSelSql="select b.subj_code"+checkSqlList.get(2)+",sum("+amount_money+") amount_money from "+detail_table+" ";
							map.put("sel_sql", checkSqlList.get(0)+" ");
							map.put("vouch_sql", checkSelSql+vouch_sql.toString()+" ");
							map.put("left_sql", checkSqlList.get(1)+" ");
							map.put("where_sql", where_sql.toString()+" and b.subj_code="+detailMap.get("subj_code")+" ");
							map.put("group_sql", " group by b.subj_code"+checkSqlList.get(2)+" ");
							metaList=accMedAutoVouchMapper.queryVouchCheckJsonByBusi(map);
							if(metaList!=null && metaList.size()>0){
								for(Map<String,Object> cm:metaList){
									checkList.add(cm);
								}
								
							}
						}
						
					}
					checkMap.put(index, checkList);
					index++;
				}
				
				
			}
			
			map.put("vouch_date",vouch_date);
			map.put("acc_month",accMonth);
			map.put("vouch_type_code", tempList.get(0).get("VOUCH_TYPE_CODE"));
			String json=superVouchService.getVouchJon(map,detailList,checkMap,null);
			
			return "{\"busi_no\": \""+map.get("busi_no").toString().replace("'","")+"\",\"vouch\":"+json+"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}
	
	
	//查询药品入库退货明细数据
	public String queryMedInBackDetail(Map<String, Object> map) throws DataAccessException{
		try{
			map.put("table", "v_med_in_detail");
			//药品退库
			if(map.get("bus_type_code").toString().equals("080102")){
				map.put("table", "v_med_back_detail");
			}
			//采购发票
			if(map.get("bus_type_code").toString().equals("080110")){
				map.put("table", "v_med_bill_detail");
			}
			
//			if(map.get("vouch_id").toString().equals("0")){
//				return ChdJson.toJsonLower(accMedAutoVouchMapper.queryMedInBackBySup(map));
//			}else{
				return ChdJson.toJsonLower(accMedAutoVouchMapper.queryMedInBackDetail(map));
//			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}
	
	/*
	 * 生成自动凭证，返回{"auto_id":自动凭证主表vouch_id}
	 * 自动凭证主表：ACC_AUTO_VOUCH，保存凭证后删除
	 * 自动凭证明细表：ACC_AUTO_DETAIL，保存凭证后删除
	 * 自动凭证辅助核算表：ACC_AUTO_CHECK，保存凭证后删除
	 * 自动凭证现金流量标注：ACC_AUTO_CASH，保存凭证后删除
	 * 自动凭证日志临时表：ACC_BUSI_LOG_TEMP，保存凭证后删除
	 * */
	@Override
	public String saveAutoVouch(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			
			int initType=Integer.parseInt(map.get("init_type").toString());
			String groupId=map.get("group_id").toString();
			String hosId=map.get("hos_id").toString();
			String copyCode=map.get("copy_code").toString();
			String modCode=map.get("mod_code").toString();
			String busiDateB=map.get("busi_date_b").toString();
			String busiDateE=map.get("busi_date_e").toString();
			String vouch_date=map.get("vouch_date").toString();
			String accYear=vouch_date.substring(0, 4);
			if(Integer.parseInt(accYear)<2019){
				return "{\"error\":\"只能生成2019年以后的凭证！\"}";
			}
			map.put("acc_year", accYear);
			String busi_log_table=map.get("busi_log_table").toString();
			String busiTypeCode=map.get("busi_type_code").toString();
			String busi_type_code = map.get("busi_type_code").toString() ;
			
			String store_id = null ;
			if(null!=map.get("store_id") && !"".equals(map.get("store_id").toString())){
				store_id =  map.get("store_id").toString() ;
			}
			String sup_id = null ;
			if(null!=map.get("sup_id") && !"".equals(map.get("sup_id").toString())){
				sup_id =  map.get("sup_id").toString() ;
			}
			
			String type_flag = null ;
			if(null!=map.get("type_flag") && !"".equals(map.get("type_flag").toString())){
				type_flag =  map.get("type_flag").toString() ;
			}
			 
			List<Map<String, Object>> tempList=accBusiTemplateDetailMapper.queryAccBusiTemplateByCode(map);
			if(tempList==null || tempList.size()==0){
				return "{\"error\":\"没有配置凭证模板！\"}";
			}
			
			String vouchTypeCode=tempList.get(0).get("VOUCH_TYPE_CODE").toString();
			//查询凭证视图字段
			List<Map<String, Object>> viewList=new ArrayList<Map<String, Object>>();
			viewList=accMedAutoVouchMapper.queryAutoVouchView(map);
			if(viewList==null || viewList.size()==0){
				return "{\"error\":\"没有配置内置数据：SYS_BUSI_VIEW！\"}";
			}
			
			
			/*String busNo="";
			String summary="";
			
			//按日期生成,拼日期摘要
			if(map.get("busi_date_b").toString().equalsIgnoreCase(map.get("busi_date_e").toString())){
				summary=map.get("busi_date_b").toString()+"日";
			}else{
				summary=Integer.parseInt(map.get("busi_date_b").toString().substring(8,10))+"-"+Integer.parseInt(map.get("busi_date_e").toString().substring(8,10))+"日";
			}
			//按单据生成,需获取勾选的单据
			if(initType == 1){
				busNo = map.get("busi_no").toString();
				busNo=busNo.replace(",", "','");
				busNo="'"+busNo+"'";
			}
			
			List<BusiQuery> busiQueryList = accMedAutoVouchMapper.queryMedAutoVouchHead(map);
			if(busiQueryList==null || busiQueryList.size()==0){
				return "{\"error\":\"没有配置内置数据：SYS_BUSI_QUERY！\"}";
			}
			BusiQuery busiQuery = busiQueryList.get(0);
			map.put("query_view_name", busiQuery.getMain_table());
			map.put("query_main_key_fielde", busiQuery.getMain_key_field());
			map.put("query_where_sql", busiQuery.getWhere_sql());*/
			
			String summary="";
			String vouchId=UUIDLong.absStringUUID();
			map.put("vouch_id", vouchId);
			Date createDate=DateUtil.getCurrenDate();
			map.put("create_date",createDate);//这个时间很重要是验证是否生成凭证的重要的 
			int reCount=0;
			if(initType==1){
				//按单据生成
				
				//保存自动凭证日志临时表
				if(map.get("busi_no")!=null && !map.get("busi_no").equals("")){
					String[] busiNoArray=map.get("busi_no").toString().split(",");
					reCount=accMedAutoVouchMapper.saveAutoVouchLogTempByNo(map,busiNoArray);
				}
				if(reCount==0){
					return "{\"error\":\"请先查询数据！\"}";
				}
				
			
			}else{
				//按日期生成、按汇总生成
				//拼日期摘要
				if(map.get("busi_date_b").toString().equalsIgnoreCase(map.get("busi_date_e").toString())){
					summary=map.get("busi_date_b").toString()+"日";
				}else{
					summary=Integer.parseInt(map.get("busi_date_b").toString().substring(8,10))+"-"+Integer.parseInt(map.get("busi_date_e").toString().substring(8,10))+"日";
				}
				
				List<BusiQuery> busiQueryList = accMedAutoVouchMapper.queryMedAutoVouchHead(map);
				if(busiQueryList==null || busiQueryList.size()==0){
					return "{\"error\":\"没有配置内置数据：SYS_BUSI_QUERY！\"}";
				}
				BusiQuery busiQuery = busiQueryList.get(0);
				map.put("query_view_name", busiQuery.getMain_table());
				//map.put("huizong_sql", map.get("huizong_sql").toString().replace("{m_table}", busiQuery.getMain_table()));
				map.put("query_main_key_fielde", busiQuery.getMain_key_field());
				map.put("query_where_sql", busiQuery.getWhere_sql());
				map.put("left_sql", getLeftJoinSql(busiQuery.getMain_table(), busiQuery.getMain_key_field(), busiQuery.getDetail_table(), busiQuery.getWhere_sql()));
				//保存自动凭证日志临时表
				reCount=accMedAutoVouchMapper.saveAutoVouchLogTemp(map);
				if(reCount==0){
					return "{\"error\":\"请先查询数据！\"}";
				}
				
			}
			
//			int reCount=accMedAutoVouchMapper.saveAutoVouchLogTemp(map);
//			if(reCount==0){
//				return "{\"error\":\"请先查询数据！\"}";
//			}
			
			//判断是否生成凭证
			reCount=superVouchMapper.queryAutoVouchIsCreate(map);
			if(reCount>0){
				throw new SysException("有数据已经生成凭证！");
			}
			
			/*添加自动凭证主表*/
			map.put("vouch_att_num", 0);//凭证附件
			map.put("vouch_type_code", vouchTypeCode);//凭证类型
			Date vouchDate=DateUtil.stringToDate(vouch_date,"yyyy-MM-dd");
			map.put("vouch_date", vouchDate);
			superVouchMapper.saveAutoVouch(map);
			
			
			List<Map<String, Object>> detailList=new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> metaList=null;
			int isDetailSummary=Integer.parseInt(tempList.get(0).get("IS_DETAIL_SUMMARY").toString());
			int vouchRow=0;
			
			Map<String,String> checkEl=null;
			int direction=0;
			for(Map<String, Object> tempMap:tempList){
				
				String metaCode=tempMap.get("META_CODE").toString();
				direction=Integer.parseInt(tempMap.get("DIRECTION").toString());
				map.put("direction", direction);
				map.put("vouch_row", tempMap.get("VOUCH_ROW"));
				
				//0不按明细摘要，1按明细摘要
				if(isDetailSummary==0){
					map.put("summary",tempMap.get("M_SUMMARY"));
				}else{
					map.put("summary",tempMap.get("D_SUMMARY")+summary);
				}
				
				String main_table="";
				String detail_table="";
				String item_table="";
				String amount_money="";
				String sub_type_id="";
				String while_sql="";
				String busi_date="";
				String main_key_filed="";
				String detail_key_filed="";
				String business_no = "";
				
				StringBuilder inner_sql1=new StringBuilder();
				StringBuilder inner_sql2=new StringBuilder();
				StringBuilder vouch_sql=new StringBuilder();
				StringBuilder where_sql=new StringBuilder();
				StringBuilder group_sql=new StringBuilder();
				group_sql.append(" group by b.subj_code");
				checkEl=new HashMap<String,String>();//检索辅助核算规则
				checkEl.put("busi_type_code", map.get("busi_type_code").toString());
				
				for(Map<String,Object> view:viewList){
					//取当前元素的对应关系
					if(!view.get("META_CODE").equals(metaCode)){
						continue;
					}
					
					String FIELD_ALIAS=view.get("FIELD_ALIAS").toString();
					//存业务字段与辅助核算字段的关系，CHECK4_ID:V_MAT_IN_MAIN.STORE_ID
					if(FIELD_ALIAS.indexOf("CHECK")!=-1 && checkEl.get(FIELD_ALIAS)==null){
						checkEl.put(FIELD_ALIAS, view.get("VIEW_FIELD").toString());
					}
					
					//取主表、明细表、第三层表
					if(main_table.equals("") && detail_table.equals("") && item_table.equals("")){
						main_table=view.get("MAIN_TABLE")==null?"":view.get("MAIN_TABLE").toString();
						detail_table=view.get("DETAIL_TABLE")==null?"":view.get("DETAIL_TABLE").toString();
						item_table=view.get("ITEM_TABLE")==null?"":view.get("ITEM_TABLE").toString();
					}
					
					//取科目与对应关系的关联字段
					if(FIELD_ALIAS.equalsIgnoreCase("SUB_TYPE_ID")){
						sub_type_id=view.get("VIEW_FIELD")==null?"":view.get("VIEW_FIELD").toString();
						while_sql=view.get("WHILE_SQL")==null?"":view.get("WHILE_SQL").toString();
					}
					
					//取汇总金额
					if(FIELD_ALIAS.equalsIgnoreCase("AMT_MONEY")){
						amount_money=view.get("VIEW_FIELD")==null?"":view.get("VIEW_FIELD").toString();
					}
					
					//取单据日期
					if(FIELD_ALIAS.equalsIgnoreCase("OCCUR_DATE")){
						busi_date=view.get("VIEW_FIELD")==null?"":view.get("VIEW_FIELD").toString();
					}
					
					//取主从表关联字段
					if(main_key_filed.equals("") && view.get("MAIN_KEY_FIELD")!=null && !view.get("MAIN_KEY_FIELD").equals("")){
						main_key_filed=view.get("MAIN_KEY_FIELD").toString();
						
					}
					//取主表单据号字段
					if(business_no.equals("") && view.get("BUSINESS_NO")!=null && !view.get("BUSINESS_NO").equals("")){
						business_no = view.get("BUSINESS_NO").toString();
						
					}
					
					//取从表第三层表关联字段
					if(detail_key_filed.equals("") && view.get("ITEM_KEY_FIELD")!=null && !view.get("ITEM_KEY_FIELD").equals("")){
						detail_key_filed=view.get("ITEM_KEY_FIELD").toString();
					}
				}
				
				if(main_table.equals("") || amount_money.equals("") || sub_type_id.equals("")){
					throw new SysException(metaCode+"缺少内置数据：字段MAIN_TABLE，字段FIELD_ALIAS【amount_money】，字段FIELD_CODE【SUB_TYPE_ID】！");
				}
				
				/*if(detail_table.equals("")){
					throw new SysException(metaCode+"缺少内置数据：字段detail_table！");
				}*/
				
				//关联主从表
				if(!main_key_filed.equals("") && !detail_table.equals("")){
					inner_sql1.append(" inner join "+detail_table+" on ");
					inner_sql1.append(main_table+".group_id="+detail_table+".group_id and "+main_table+".hos_id="+detail_table+".hos_id and "+main_table+".copy_code="+detail_table+".copy_code ");
					inner_sql1.append(" and "+main_table+"."+main_key_filed+"="+detail_table+"."+main_key_filed);
				}
				
				//关联从表第三层表
				if(!detail_key_filed.equals("") && !detail_table.equals("") && !item_table.equals("")){
					inner_sql2.append(" inner join "+item_table+" on ");
					inner_sql2.append(item_table+".group_id="+detail_table+".group_id and "+item_table+".hos_id="+detail_table+".hos_id and "+item_table+".copy_code="+detail_table+".copy_code ");
					inner_sql2.append(" and "+item_table+"."+main_key_filed+"="+detail_table+"."+main_key_filed);
				}
				
				//开始组装查询自动凭证SQL
				if(inner_sql1!=null && !inner_sql1.toString().equals("")){
					vouch_sql.append(inner_sql1.toString());
				}
				
				if(inner_sql2!=null && !inner_sql2.toString().equals("")){
					vouch_sql.append(inner_sql2.toString());
				}
				//关联科目关系
				vouch_sql.append(" inner join acc_busi_map b on b.group_id="+main_table+".group_id and b.hos_id="+main_table+".hos_id and b.copy_code="+main_table+".copy_code "); 
				
				//判断是否按仓库做科目对应关系
				if(busiTypeCode.equals("080101") || busiTypeCode.equals("080102") || busiTypeCode.equals("080110") || busiTypeCode.equals("080111") || busiTypeCode.equals("080112")) {
                 vouch_sql.append("  and  (b.store_id="+main_table+".store_id_k or b.store_id is  null) "); 
										
				}
				//药库出库，贷药库库存
				if(busiTypeCode.equals("080103") && direction==1) {
                 vouch_sql.append("  and  (b.store_id="+main_table+".store_id_k or b.store_id is  null) "); 
										
				}
				//药库出库，借药房库存
				if(busiTypeCode.equals("080103") && direction==0) {
                 vouch_sql.append("  and  (b.store_id="+main_table+".store_id_f or b.store_id is  null) "); 
										
				}
				//药房退库，借药库库存
				if(busiTypeCode.equals("080105") && direction==0) {
                 vouch_sql.append("  and  (b.store_id="+main_table+".store_id_k or b.store_id is  null) "); 
										
				} 
				//药房退库，贷药房库存
				if(busiTypeCode.equals("080105") && direction==1) {
                 vouch_sql.append("  and  (b.store_id="+main_table+".store_id_f or b.store_id is  null) "); 
										
				}
				//药房出库，科室退库
				if(busiTypeCode.equals("080104") || busiTypeCode.equals("080106")) {
                 vouch_sql.append("  and  (b.store_id="+main_table+".store_id_f or b.store_id is  null) "); 
										
				} 
				//库房盘盈盘亏
				if ("080107".equals(busiTypeCode) || "080108".equals(busiTypeCode) ) {
					vouch_sql.append("  and  (b.store_id="+main_table+".store_id or b.store_id is  null) ");
				}
				//药房调拨，借调入
				if ("080113".equals(busiTypeCode) && direction==0) {
					checkEl.put("CHECK1_ID", "V_ACC_MED_F_TR.DEPT_ID_T");
					checkEl.put("CHECK1_NO", "V_ACC_MED_F_TR.DEPT_NO_T");
					vouch_sql.append("  and  (b.store_id="+main_table+".store_id_T or b.store_id is  null) ");
				}
				//药房调拨，贷调出
				if ("080113".equals(busiTypeCode) && direction==1) {
					checkEl.put("CHECK1_ID", "V_ACC_MED_F_TR.DEPT_ID_F");
					checkEl.put("CHECK1_NO", "V_ACC_MED_F_TR.DEPT_NO_F");
					vouch_sql.append("  and  (b.store_id="+main_table+".store_id_F or b.store_id is  null) ");
				}
				
				vouch_sql.append(" and b.acc_year='"+accYear+"' and b.mod_code='"+modCode+"' and b.meta_code='"+metaCode+"' and (b.sub_type_id	="+sub_type_id+" or b.sub_type_id is null) ");
				//where条件
				where_sql.append(" where b.group_id="+groupId+" and b.hos_id="+hosId+" and b.copy_code='"+copyCode+"' ");
				
				
				if(initType==1){
					//按单据生成
					/*药品接口中没有单据ID字段  这里使用单据号生成
					if(main_key_filed==null || main_key_filed.equals("")){
						throw new SysException("没有配置单据ID字段：main_key_filed！");
					}
					where_sql.append(" and "+main_table+"."+main_key_filed+" in("+busNo+") ");
					*/
					if(business_no == null || "".equals(business_no)){
						throw new SysException("没有配置单据号字段：business_no！");
					}
					where_sql.append(" and "+main_table+"."+business_no+" in('"+map.get("busi_no").toString().replace(",","','")+"') ");
				}else{
					//按日期生成、按汇总生成
					if(busi_date==null || busi_date.equals("")){
						throw new SysException("没有配置单据日期字段：busi_date！");
					}
					where_sql.append(" and to_char("+busi_date+",'yyyy-MM-dd') between '"+busiDateB+"' and '"+busiDateE+"' ");
					
					if(store_id != null){
						
						if("080101".equals(busi_type_code) || "080102".equals(busi_type_code) || "080107".equals(busi_type_code) || "080108".equals(busi_type_code)
								|| "080109".equals(busi_type_code) || "080110".equals(busi_type_code) || "080111".equals(busi_type_code) || "080112".equals(busi_type_code)){
							where_sql.append(" and "+ main_table +".STORE_ID_K = "+store_id );
						}else if("080103".equals(busi_type_code) ||"080105".equals(busi_type_code)){
							where_sql.append(" and "+ main_table +".STORE_ID_K = "+store_id );
						}else if("080104".equals(busi_type_code) || "080106".equals(busi_type_code)){
							where_sql.append(" and "+ main_table +".STORE_ID_F = "+store_id );
						}else if("080107".equals(busi_type_code) || "080108".equals(busi_type_code)){
							where_sql.append(" and "+ main_table +".STORE_ID = "+store_id );
						}else if("080113".equals(busi_type_code)){
							where_sql.append(" and "+ main_table +".STORE_ID_F = "+store_id );
						}
						
						
					}
					
					if(sup_id != null){
						where_sql.append(" and "+ main_table +".sup_id = "+sup_id );
					}
					
					//过滤掉已经生成的凭证
					where_sql.append(" and not exists(select 1 from "+busi_log_table+" where "+busi_log_table+".group_id="+main_table+".group_id ");
					where_sql.append(" and "+busi_log_table+".hos_id="+main_table+".hos_id and "+busi_log_table+".copy_code="+main_table+".copy_code and "+busi_log_table+".business_no="+main_table+"."+main_key_filed+")");
					
					if("080110".equals(busi_type_code)){
						//采购发票只查询暂估凭证
						where_sql.append(" and v_med_bill_main.BUSINESS_NO in(select business_no from ACC_BUSI_LOG_080101 where v_med_bill_main.group_id=group_id and v_med_bill_main.hos_id=hos_id and v_med_bill_main.copy_code=copy_code and template_code='002' union select business_no from ACC_BUSI_LOG_080102 where v_med_bill_main.group_id=group_id and v_med_bill_main.hos_id=hos_id and v_med_bill_main.copy_code=copy_code and template_code='002')");
					}
					
					if("080101".equals(busi_type_code) || "080102".equals(busi_type_code)){
						//发票状态
						if(map.get("bill_state")!=null && !map.get("bill_state").toString().equals("")){
							where_sql.append(" and "+main_table+".bill_state="+map.get("bill_state").toString());
						}
					}
					
					if("080104".equals(busi_type_code) || "080106".equals(busi_type_code)){
						//门诊住院
						if(map.get("doc_type")!=null && !map.get("doc_type").toString().equals("")){
							where_sql.append(" and "+main_table+".doc_type="+map.get("doc_type").toString());
						}
					}
				}
				
				if(type_flag != null){
					where_sql.append(" and "+ main_table +".type_flag = "+type_flag );
				}

				//过滤业务类型
				if(!while_sql.equals("")){
					where_sql.append(" and "+while_sql);
				}
				
				String selSql="select b.subj_code,sum("+amount_money+") amount_money from "+main_table+" ";
				map.put("meta_code", metaCode);
				map.put("vouch_sql", selSql+vouch_sql.toString());
				map.put("where_sql", where_sql.toString());
				map.put("group_sql", group_sql.toString());
				metaList=accMedAutoVouchMapper.queryVouchJsonByBusi(map);
				if(metaList!=null && metaList.size()>0){
					
					//分录
					for(Map<String, Object> detailMap:metaList){
						vouchRow++;
						detailMap.put("group_id", groupId);
						detailMap.put("hos_id", hosId);
						detailMap.put("copy_code", copyCode);
						detailMap.put("vouch_id", vouchId);//凭证ID
						detailMap.put("vouch_row", vouchRow);//分录行号
						detailMap.put("direction", direction);//方向
						detailList.add(detailMap);
						
						if(detailMap.get("is_check")!=null && detailMap.get("is_check").toString().equals("1")){
							//辅助核算
							detailMap.put("vouch_date", vouch_date);//凭证日期
							detailMap.put("amount_money", amount_money);//金额字段
							detailMap.put("detail_table", detail_table);//业务表名
							detailMap.put("from_table", main_table);//子查询表名
							detailMap.put("busi_type_code", busiTypeCode);//业务类型
							superVouchService.saveAccAutoCheck(detailMap,checkEl,vouch_sql.toString(),where_sql.toString(),group_sql.toString());
							
						}
						
					}

				}
				
			}
			
			if(detailList!=null && detailList.size()>0){
				//保存自动凭证明细表
				superVouchMapper.saveAutoVouchDetail(detailList);
				
				//根据自动凭证的模板的处理凭证分录的摘要,如：模板明细表摘要，[供应商]摘要=》[某某公司]摘要
				superVouchMapper.updateAutoVouchDetailSummary(map);
			}else{
				throw new SysException("没有明细表数据或者没有对应关系！");
			}
			
			return "{\"auto_id\": \""+vouchId+"\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}
		
		
//	public static void main(String[] args) {
//		
//		String beginDate="2011-02-14";
//	    String endDate="2011-03-01";
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//        Calendar start = Calendar.getInstance();
//        Calendar end = Calendar.getInstance();
//        try {
//            start.setTime(format.parse(beginDate));
//            end.setTime(format.parse(endDate));
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        while(start.before(end))
//        {
//            System.out.println(format.format(start.getTime()));
//            start.add(Calendar.DAY_OF_MONTH,1);
//        }
//	}

	@Override
	public List<Map<String, Object>> queryMedAutoVouchPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<BusiQuery> busiQueryList = accMedAutoVouchMapper.queryMedAutoVouchHead(entityMap);
		
		if(busiQueryList==null || busiQueryList.size()==0){
			throw new SysException("SYS_BUSI_QUERY表没有配置！");
		}
		
		BusiQuery busiQuery = busiQueryList.get(0);
		
		String view_name = busiQuery.getMain_table();
		
	//	String busi_type_code = busiQuery.getBusi_type_code();
		
		String detail_view_name = busiQuery.getDetail_table();
		
		String group_sql = busiQuery.getGroup_by();
		
		String where_sql = busiQuery.getWhere_sql();
		
		String business_no = busiQuery.getBusiness_no();
		
		String log_name = busiQuery.getLog_name();
		
		String main_key_field = busiQuery.getMain_key_field();
		
		String head_names [] = busiQuery.getHead_name().split(",");
		
		StringBuilder field = new StringBuilder();
		
		StringBuilder fieldTotal = new StringBuilder();
		
		for(int i = 0 ; i < head_names.length; i++){
			
			if(head_names[i].split("\\|")[2].equalsIgnoreCase("VOUCH_NO")){
				continue;
			}
			
			if(i==0){
				fieldTotal.append("null "+head_names[i].split("\\|")[0]+",");
					
			}else if(head_names[i].split("\\|")[2].indexOf("SUM(")!=-1){
				fieldTotal.append(head_names[i].split("\\|")[2]+",");
				
			}else{
				
				fieldTotal.append("null "+head_names[i].split("\\|")[0]+",");
				
			}
			
			if((i+1) == head_names.length){
				
				field.append(head_names[i].split("\\|")[2]);
				
			}else{
				
				field.append(head_names[i].split("\\|")[2]+",");
			}
			
		}
		
		fieldTotal.append("null VOUCH_NO,0 VOUCH_ID");
		
		entityMap.put("view_name", view_name);
		
		entityMap.put("detail_view_name", detail_view_name);
		
		entityMap.put("log_table_name",log_name);
		
		entityMap.put("business_no", business_no);
		
		entityMap.put("main_key_field", main_key_field);
		
		entityMap.put("field", field.toString());
		
		entityMap.put("fieldTotal", fieldTotal.toString());
		
		if(where_sql!=null && !where_sql.equals("")){
			entityMap.put("where_sql", where_sql);
		}
		
		if(group_sql!=null && !group_sql.equals("")){
			entityMap.put("group_sql", "group by "+group_sql);
		}
		
		List<Map<String, Object>> list = accMedAutoVouchMapper.queryMedAutoVouch(entityMap);
		
		return list;
	}
	
	
	public String getLeftJoinSql(String main, String main_key, String detail, String where_sql) throws DataAccessException{
		/*if(main == null || "".equals(main) || main_key == null || "".equals(main_key) 
				|| detail == null || "".equals(detail) || where_sql == null || "".equals(where_sql)){
			return "";
		}
		
		if(where_sql.indexOf(detail) <= 0){
			return "";
		}*/
		
		if(main_key == null || "".equals(main_key) 
				|| detail == null || "".equals(detail)){
			return "";
		}
		
		StringBuilder left_sql = new StringBuilder();
		left_sql.append(" left join ").append(detail);
		left_sql.append(" on ").append(main).append(".group_id = ").append(detail).append(".group_id");
		left_sql.append(" and ").append(main).append(".hos_id = ").append(detail).append(".hos_id");
		left_sql.append(" and ").append(main).append(".copy_code = ").append(detail).append(".copy_code");
		left_sql.append(" and ").append(main).append(".").append(main_key).append(" = ").append(detail).append(".").append(main_key);
		
		return left_sql.toString();
	}

}
