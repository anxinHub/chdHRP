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
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var store_id = null;
	var store_no;
	var store_name;
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
		
    	grid.options.parms.push({name : 'accYearB',value : $("#yearB").val()}); 
    	grid.options.parms.push({name : 'accMonthB',value : $("#monthB").val()}); 
    	grid.options.parms.push({name : 'accYearE',value : $("#yearE").val()}); 
    	grid.options.parms.push({name : 'accMonthE',value : $("#monthE").val()}); 
    	
    	grid.options.parms.push({name : 'stock_id',value : liger.get("store_code").getValue().split(',')[0]}); 
    	
    	grid.options.parms.push({name : 'req_code',value : $("#req_code").val()});
    	
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		       columns: [ 
		                 
		                 { display: '汇总需求计划单号', name: 'req_code', align: 'left',width :'12.5%',
		                	 render : function(rowdata, rowindex,value) {
									return "<a href=javascript:openReqDeatail('"
									+ rowdata.req_id   + "|" 
									+ rowdata.req_code + "|" 
									+ rowdata.maker + "|"
									+"')>"+rowdata.req_code+"</a>"
								}	  
		                 },
						 { display: '采购计划单号', name: 'pur_code', align: 'left',width :'12.5%',
		                	 	render : function(rowdata, rowindex,value) {
		                	 	
									return "<a href=javascript:openPurDeatail('"
									+ rowdata.pur_id   + "|" 
									+ rowdata.pur_code + "|" 
									+"')>"+ rowdata.pur_code != "undefined" ? rowdata.pur_code :"" +"</a>"
								}	  
						 },
						 { display: '汇总库房', name: 'store_name', align: 'left',width :'12.5%'},
						 { display: '汇总日期', name: 'make_date', align: 'left',width :'12.5%'},
						 { display: '定向需求', name: 'is_dir', align: 'left',
							 render : function(rowdata, rowindex,value) {
								 	if(rowdata.is_dir == 0){
										return "否";
									}else {
										return "是";
									}
								}		 
						 },
						 { display: '制单人', name: 'maker', align: 'left',width :'12.5%' },
						 { display: '状态', name: 'state', align: 'left',width :'12.5%',
							 render : function(rowdata, rowindex,value) {
								 	if(rowdata.state == 0){
										return "已终止";
									}else if(rowdata.state == 1){
										return "未审核";
									}else if(rowdata.state == 2){
										return "已审核";
									}else{
										return "执行完毕";
									}
								}	 
						 },
						 { display: '查看明细', name: 'other_inv', align: 'left',width :'12.5%',
							 render : function(rowdata, rowindex,value) {
									return "<a href=javascript:openCollectDeatail('"
									+ rowdata.req_id   + "|" 
									+ rowdata.req_code + "|"
									+ rowdata.maker + "|"
									+"')>"+rowdata.other_inv+"</a>"
								}	 
						 }
					
		                ],
		                
		                 dataAction: 'server',dataType: 'server',usePager:true,
		                 url:'queryMatDeptRequriedCollectQ.do?isCheck=true',
		                 width: '100%', height: '100%', checkbox : false, rownumbers : true, delayLoad : true,
		                 selectRowButtonOnly:true,
		                 toolbar: { items: 
		                	 	[
		                          {text: '查询', id:'query', click: query ,icon:'search'},
		                          {line:true }/* ,
		                          {text : '打印',id : 'print',click : print ,icon : 'print'} */
		                   		]
		                 }
		               });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//查看汇总数据 按材料汇总
	function openReqDeatail(obj){
		var vo = obj.split("|");
		var parm = "req_id="+vo[0]+"&req_code="+vo[1]+"&maker="+vo[2];

		$.ligerDialog.open({ 
			url : 'matDeptCollectStoreDetail.do?isCheck=false&' + parm, 
			height: 550,width: 1000, 
			title:'查看',modal:true,showToggle:false,showMax:true ,showMin: false,isResize:true
		});
	
	}
	
	//查看汇总明细数据  按照科室
	function openCollectDeatail(obj){
		var vo = obj.split("|");
		var parm = "req_id="+vo[0]+"&req_code="+vo[1]+"&maker="+vo[2];

		$.ligerDialog.open({ 
			url : 'matDeptCollectDeptDetail.do?isCheck=false&' + parm,
			height: 550,width: 1000, 
			title:'查看明细',modal:true,showToggle:false,showMax:true ,showMin: false,isResize:true
		});
		
	}
	
	//查看订购单
	function openPurDeatail(obj){
		var vo = obj.split("|");
		var parm = "pur_id="+vo[0]+"&pur_code="+vo[1]+"&maker="+vo[2];

		$.ligerDialog.open({ 
			url : 'matDeptCollectPurDetail.do?isCheck=false&' + parm,
			height: 550,width: 1000, 
			title:'查看明细',modal:true,showToggle:false,showMax:true ,showMin: false,isResize:true
		});
		
	}
	
	function printData(){
		
	}
	
	function loadDict() {
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		var date = getCurrentDate();
        var aa = date.split(';');
      
		autocomplete("#yearB","../../queryMatYear.do?isCheck=false","id","text",true,true);
        liger.get("yearB").setValue(aa[0]);
        liger.get("yearB").setText(aa[0]);
        
        autocomplete("#monthB","../../queryMatMonth.do?isCheck=false","id","text",true,true);
        liger.get("monthB").setValue(aa[1]);
        liger.get("monthB").setText(aa[1]);
		
        autocomplete("#yearE","../../queryMatYear.do?isCheck=false","id","text",true,true);
        liger.get("yearE").setValue(aa[0]);
        liger.get("yearE").setText(aa[0]);
        
        autocomplete("#monthE","../../queryMatMonth.do?isCheck=false","id","text",true,true);
        liger.get("monthE").setValue(aa[1]);
        liger.get("monthE").setText(aa[1]);
        
        autocomplete("#store_code", "../../queryMatStore.do?isCheck=false", "id", "text", true, true,{is_purchase : '1'}, true,false,'160',"",200);//仓库
		
		$("#yearB").ligerTextBox({width:80});
		$("#monthB").ligerTextBox({width:80});
		$("#yearE").ligerTextBox({width:80});
		$("#monthE").ligerTextBox({width:80});
		
		$("#store_code").ligerTextBox({width:160});
		$("#req_code").ligerTextBox({width:160});
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">
		<tr></tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">编制年月：</td>
			<td align="left" class="l-table-edit-td" style="width:80px;"> 
				<input name="yearB" type="text" id="yearB" />
			</td>
			<td align="center" class="l-table-edit-td" style="width:10px;">年</td>
			<td align="right" class="l-table-edit-td" style="width:80px;">
				<input name="monthB" type="text" id="monthB" />
			</td>
			<td align="center" class="l-table-edit-td" style="width:10px;">至：</td>
			<td align="left" class="l-table-edit-td" style="width:80px;">
				<input name="yearE" type="text" id="yearE" />
			</td>
			<td align="center" class="l-table-edit-td" style="width:10px;">年</td>
			<td align="right" class="l-table-edit-td" style="width:80px;">
				<input name="monthE" type="text" id="monthE" />
			</td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">汇总库房：</td>
			<td align="left" class="l-table-edit-td">
				<input name="store_code" type="text" requried="true" id="store_code" />
			</td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">需求计划单号：</td>
			<td align="left" class="l-table-edit-td">
				<input name="req_code" type="text" requried="false" id="req_code" />
			</td>
			
		</tr>
		
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
