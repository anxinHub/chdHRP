<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var store_id = null;
	var store_no;
	var store_name;
	
	var gridDetail = null;
	
	var is_check = 3;
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);

	});
	
	//查询
	function query() {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
		
    	grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()}); 
    	grid.options.parms.push({name : 'end_date',value : $("#end_date").val()}); 
    	
    	grid.options.parms.push({name : 'year', value : liger.get("year").getValue()});
    	grid.options.parms.push({name : 'month',value : liger.get("month").getValue()});
    	
    	grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(',')[0]}); 
    	grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(',')[1]});
    	
    	grid.options.parms.push({name : 'dept_id',value : liger.get("dept_code").getValue().split(',')[0]}); 
    	grid.options.parms.push({name : 'dept_no',value : liger.get("dept_code").getValue().split(',')[1]});
    	
    	grid.options.parms.push({name : 'inv_code',value : $("#inv_code").val()}); 
    	grid.options.parms.push({name : 'req_code',value : $("#req_code").val()});
    	grid.options.parms.push({name : 'is_check',value : is_check});
		//加载查询条件
		grid.loadData(grid.where);
	}
	

	function loadHead() {
		
		grid = $("#maingrid").ligerGrid({
		       columns: [ 
						  { display: '需求计划单号', name: 'req_code', align: 'left' ,width :'120'},
						  { display: '编制部门', name: 'dept_name', align: 'left' ,width :'100'},
		                  { display: '材料编码', name: 'inv_code', align: 'left',width :'150',
		                	 /*render : function(rowdata, rowindex,value) {
									return "<a href=javascript:openUpdate('"
											+rowdata.inv_code   + "|" 
											+ rowdata.inv_id + "|" 
											+ rowdata.inv_no  + "|"
											+ rowdata.inv_name  + "|"
											+"')>"+rowdata.inv_code+"</a>"
								}*/		 
		                 },
						 { display: '材料名称(E)', name: 'inv_name', align: 'left' ,width :'150'},
						 { display: '规格型号', name: 'inv_model', align: 'left',width :'150'},
						 { display: '计量单位', name: 'unit_name', align: 'left',width :'90'},
						 { display: '供应商', name: 'sup_name', align: 'left',width :'90'},
						 { display: '单价', name: 'price', align: 'right' ,width :'90',
							 render:function(rowdata){
				            		return formatNumber(rowdata.price ==null ? 0 : rowdata.price,'${p04006}',1);
				             }
						 },
						 /*{ display: '期初库存', name: 'begin_amount', align: 'right',width :'90',
							 render:function(rowdata){
				            		return formatNumber(rowdata.begin_amount ==null ? 0 : rowdata.begin_amount,2,1);
				             }
						 },{ 
							 display: '当前库存', name: 'cur_amount', align: 'right',width :'90',
							 render:function(rowdata){
				            		return formatNumber(rowdata.cur_amount ==null ? 0 : rowdata.cur_amount,2,1);
				             }	 
						 },*/
						 { 
							 display: '需求数量', name: 'req_amount', align: 'right' ,width :'90',
							 render:function(rowdata){
				            		return formatNumber(rowdata.req_amount ==null ? 0 : rowdata.req_amount,2,1);
				             }
						 },{ 
							 display: '汇总数量(E)', name: 'exec_amount', align: 'right' ,editor : {type : 'int'},width :'90',
							 render:function(rowdata){
				            		return formatNumber(rowdata.exec_amount ==null ? 0 : rowdata.exec_amount,2,1);
				             }
						 },{ 
							 display: '需求金额', name: 'req_money', align: 'right' ,width :'90',
							 render:function(rowdata){
				            		return formatNumber(rowdata.req_money ==null ? 0 : rowdata.req_money,'${p04005}',1);
				             }	 
						 },{ 
							 display: '状态', name: 'is_collec', align: 'left' ,width :'90',
							 render : function(rowdata, rowindex,value) {
									if(rowdata.is_collect == 1){
										return "已汇总";
									}else {
										return "未汇总";
									}
							 } 
						 }
					
		                ],
		                dataAction: 'server',dataType: 'server',usePager : false,
		                url:'queryMatDeptRequriedDirCollect.do?isCheck=false',
		                checkbox : false,
						enabledEdit : true,
						alternatingRow : true,
						onBeforeEdit : f_onBeforeEdit,
						onBeforeSubmitEdit : f_onBeforeSubmitEdit,
						onAfterEdit : f_onAfterEdit,
						width: '100%', height: '100%', 
		                rownumbers : true ,delayLoad : true,
		                isScroll :true ,   selectRowButtonOnly:true,
		 
		               });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	var rowindex_id = "";
	var column_name="";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name=e.column.name;
	}
	
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		
		//alert(column_name)
		return true;
	}
	function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	}
	
	//编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		
		return true;
		
	}
	
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		return true;
	}
	
	
	/*function openUpdate(obj) {
		var vo = obj.split("|");
		var parm = "inv_code="+vo[0]
		+"&inv_id="+vo[1]
		+"&inv_no="+vo[2]
		+"&inv_name="+vo[3]
		+"&store_id="+liger.get("store_code").getValue().split(',')[0]
		+"&store_no="+liger.get("store_code").getValue().split(',')[1]
		+"&acc_year="+liger.get("year").getValue()
		+"&acc_month="+liger.get("month").getValue()
		+"&begin_date="+$("#begin_date").val()
		+"&end_date="+$("#end_date").val();
		
		$.ligerDialog.open({ 
			url : 'matDeptCollectDetailPage.do?isCheck=false&'+parm,data:{}, 
			height: 550,width: 900, 
			title:'查看' , modal:true , showToggle:false , showMax:true , showMin: false , isResize:true
		});

	}*/
	
	//汇总
	function save(){
		
		var allData = gridManager.getData();
		if (allData.length == 0){
        	$.ligerDialog.error('没有要汇总的单据！');
        	return;
        }else{

        	 var formPara={
        			 store_id : liger.get("store_code").getValue().split(',')[0],
        			 store_no : liger.get("store_code").getValue().split(',')[1],
        			 dept_id : liger.get("dept_code").getValue().split(',')[0],
        			 dept_no : liger.get("dept_code").getValue().split(',')[1],
        			 acc_year : liger.get("year").getValue(),
        			 acc_month : liger.get("month").getValue(),
        			 make_date : $("#make_date").val(),
        			 begin_date : $("#begin_date").val(),
        			 end_date : $("#end_date").val()
        			 
        	 };
        	
        	$.ligerDialog.confirm('确定要汇总吗?', 
            	function (yes){
                	if(yes){
                    	ajaxJsonObjectByUrl("saveMatDeptRequriedDirCollect.do?isCheck=true",formPara,
                        	function (responseData){	
        	                	 if(responseData.state=="true"){
        	                		//alert(parent.flag);
        	                		parent.flag = 1;
        	                		//alert(parent.flag);
        	                		this_close();
        	                    }
                           	});
                    }else{
                      return;
             		}
           	}); 
        } 
	}
	
	
	//打印
	function print(){
		
	}
	
	//加载字典
	function loadDict() {
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		var date = getCurrentDate();
        var aa = date.split(';');
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
	    autodate("#end_date", "yyyy-mm-dd", "month_last");
       
		autocomplete("#year","../../queryMatYear.do?isCheck=false","id","text",true,true);
        liger.get("year").setValue(aa[0]);
        liger.get("year").setText(aa[0]);
        
        autocomplete("#month","../../queryMatMonth.do?isCheck=false","id","text",true,true);
        liger.get("month").setValue(aa[1]);
        liger.get("month").setText(aa[1]);
        
        autocomplete("#store_code", "../../queryMatStore.do?isCheck=false", "id", "text", true, true,{is_purchase : '1'},false,false,'160',"",200);//仓库
        liger.get("store_code").setValue('${store_id}');
        liger.get("store_code").setText('${store_code}');
        autocomplete("#dept_code", "../../queryMatDept.do?isCheck=false", "id", "text", true, true,{is_last : '1'}, false,false,'160',"",200);//科室
        
        autodate("#make_date");
		$("#year").ligerTextBox({width:160});
		$("#month").ligerTextBox({width:160});
		$("#dept_code").ligerTextBox({width:160});
		$("#inv_code").ligerTextBox({width:160});
		$("#req_code").ligerTextBox({width:160});
		
		$("#dept_code").change(function(){
			query();
		});
		
	}
	
	//年月改变
	function loadSelDate(){//年月的onchange方法
		if(year==null||year==''||month==null||month==''){
			return;
		}else{
			var str = getMonthDate(liger.get("year").getValue() , liger.get("month").getValue());
	        var p = str.split(';');
	        $("#begin_date").val(p[0]);
	        $("#end_date").val(p[1]);	      
		}
	}
	
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">
		<tr></tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><b>编制年月：</b></td>
			<td align="left" class="l-table-edit-td">
				<input name="year" type="text" id="year" requried="true"/>
			</td>
			<td align="center" class="l-table-edit-td" style="width: 10px;">至：</td>
			<td align="right" class="l-table-edit-td">
				<input name="month" type="text" id="month" requried="true" onchange="loadSelDate();"/>
			</td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><b><font color="red">*</font>编制科室：</b></td>
			<td align="left" class="l-table-edit-td">
				<input name="dept_code" type="text" requried="true" id="dept_code"  />
			</td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><b><font color="red">*</font>响应库房：</b></td>
			<td align="left" class="l-table-edit-td">
				<input name="store_code" type="text" requried="true" id="store_code" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">材料信息：</td>
			<td align="left" class="l-table-edit-td">
				<input name="inv_code" type="text" requried="false" id="inv_code" />
			</td>
			
			<td align="left"></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">单据号：</td>
			<td align="left" class="l-table-edit-td">
				<input name="req_code" type="text" requried="false" id="req_code" />
			</td>
			<td align="left"></td>
			
			<td >
				<input name="begin_date" type="hidden" id="begin_date" requried="false"/>
				<input name="end_date" type="hidden" id="end_date" requried="false"/>
				<input name="make_date" type="hidden" id="make_date" requried="false"/>
			</td>
		</tr>
		
	</table>

	<div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		
	</div>
	
</body>
</html>
