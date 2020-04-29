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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var thisDateB ;
	var thisDateE;
	var lastDateB;
	var lastDateE;
	$(function() {
	
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		query();
        
        
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件

    	grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()}); 
    	grid.options.parms.push({name : 'end_date',value : $("#end_date").val()}); 
    	grid.options.parms.push({name : 'dept_id',value : liger.get("dept_code").getValue().split(',')[0]}); 
    	grid.options.parms.push({name : 'brif',value : $("#brif").val()});
    	grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(',')[0]}); 
    	grid.options.parms.push({name : 'state',value : liger.get("state").getValue()});
    	grid.options.parms.push({name : 'req_code',value : $("#req_code").val()});
    	
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns: [ 
		                 { display: '需求计划单号', name: 'req_code', align: 'left',width:150,
		                	 render : function(rowdata, rowindex,value) {
									return "<a href=javascript:openUpdate('"
											+rowdata.req_id   + "|" 
											+ rowdata.req_code + "|" 
											+ rowdata.state  + "|"
											+"')>"+rowdata.req_code+"</a>"
								}	 
		                 },
		                 { display: '响应仓库', name: 'store_name', align: 'left',width:150 },
						 { display: '编制部门', name: 'dept_name', align: 'left' ,width:120},
						 { display: '摘要', name: 'brif', align: 'left' ,width:120},
						 { display: '编制人', name: 'maker', align: 'left',width:120},
						 { display: '编制日期', name: 'make_date', align: 'left',width:90},
						 { display: '是否提交', name: 'is_submit', align: 'left',width:120,
							 render : function(rowdata, rowindex,value) {
									if(rowdata.is_submit == 0){
										return "未提交";
									}else{
										return "已提交";
									}
							 }
						 },
						 { display: '审核人', name: 'checker', align: 'left' ,width:120},
						 { display: '审核日期', name: 'check_date', align: 'left',width:90 },
						 { display: '状态', name: 'state', align: 'left',width:100,
							 render : function(rowdata, rowindex,value) {
									if(rowdata.state == 0){
										return "中止计划";
									}else if(rowdata.state == 1){
										return "未审核";
									}else if(rowdata.state == 2){
										return "审核";
									}else{
										return "汇总执行";
									}
								}
						 },
						 { display: '其他需求药品', name: 'other_inv', align: 'left',width:150 },
						 { display: '退回原因', name: 'return_reason', align: 'left',width:150 }
		                ],
		                
		                 dataAction: 'server',dataType: 'server',usePager:true,isScroll:true,
		                 url:'queryMedDeptRequriedConfirm.do?isCheck=true',
		                 width: '100%', height: '100%', checkbox: true, rownumbers : true,delayLoad:true,
		                 selectRowButtonOnly:true,
		                 toolbar: { items: 
		                	 	[
		                          { text: '查询（<u>Q</u>）', id:'query', click: query ,icon:'search' },
		                            { line:true },
		                            {text: '审核（<u>A</u>）', id:'audit', click: audit ,icon:'bluebook' },
		                            { line:true },
		                            {text: '取消审核（<u>U</u>）', id:'unaudit', click: unaudit ,icon:'bookpen' },
		                            { line:true },
		                            {text: '退回科室（<u>R</u>）', id:'ret', click: ret ,icon:'back' },
		                            { line:true }/* ,
		                            {text : '打印（<u>P</u>）',id : 'print',click : printData ,icon : 'print'} */
		                   		]
		                 },
							onDblClickRow : function(rowdata, rowindex, value) {
								openUpdate(+rowdata.req_id   + "|" 
										+ rowdata.req_code + "|" 
										+ rowdata.state );
							}
		               });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
	//热键
	function loadHotkeys(){
		hotkeys('Q', query);//查询
		hotkeys('A', audit);//审核
		hotkeys('U', unaudit);//销审
		hotkeys('R', ret);//退回科室
		//hotkeys('P', printData);//打印
	}
	//查看订单
	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm = "req_id="+vo[0]+"&req_code="+vo[1]+"&state="+vo[2];
		
		parent.$.ligerDialog.open({ 
			title: '科室需求计划审核',
			height: $(window).height(),
			width: $(window).width(),
			url : 'hrp/med/require/dept/confirm/medDeptRequriedConfirmDetail.do?isCheck=false&'+parm, 
			modal:true,showToggle:false,showMax:true ,showMin: false,isResize:true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});

	}
	
	//审核
	function audit(){
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要审核的单据！');
        	return;
        }else{
            var ParamVo = [];
            var req_codes = "";
            $(data).each(function (){	
            	if(this.state != 1  ){
            		req_codes = req_codes + this.req_code+",";
            	}
            	
				ParamVo.push(
					//表的主键
					this.req_id +"@"+ this.req_code  
				)				
            });
            
            if(req_codes != ""){
            	$.ligerDialog.error("请选择未审核单据！");
				return;
            }
            
            $.ligerDialog.confirm('确定要审核吗?', 
            	function (yes){
                    if(yes){
                        ajaxJsonObjectByUrl("submitMedDeptRequriedConfirm.do?isCheck=true",{ParamVo : ParamVo.toString()},
                        	function (responseData){
	                            if(responseData.state=="true"){
	                            	query();
	                            }
                        	});
                    }else{
                    	return;
                    }
            	}); 
        }
	}
	//销审
	function unaudit(){
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要销审的单据！');
        	return;
        }else{
            var ParamVo =[];
            var req_codes = "";
            $(data).each(function (){	
            	if(this.state != 2  ){
            		req_codes = req_codes + this.req_code+",";
            	}
            	
				ParamVo.push(
					//表的主键
					this.req_id +"@"+ this.req_code  
				)
				
            });
            
            if(req_codes != ""){
            	$.ligerDialog.error("请选择已审核单据！");
				return;
            }
            
            $.ligerDialog.confirm('确定要取消审核吗?', 
            	function (yes){
                   	if(yes){
                        ajaxJsonObjectByUrl("unSubmitMedDeptRequriedConfirm.do?isCheck=true",{ParamVo : ParamVo.toString()},
                        	function (responseData){
	                           	if(responseData.state=="true"){
	                           		query();
	                          		}
                        	});
                    }else{
                    	return;
                   	}
            	}); 
        }
	}
	//退回科室
	function ret(){
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要退回的的单据！');
        	return;
        }else{
        	 var reqIds ="" ;
        	 var req_codes = "";
        	 $(data).each(function (){	
        		 if(this.state != 1  ){
        			 req_codes = req_codes + this.req_code +",";
              	 }else{
              		reqIds = reqIds + this.req_id +",";
              	 }	
             });
        	 
        	if(req_codes !=""){
        		$.ligerDialog.error("请选择未审核单据！");
            	return;
        	}
        	
         	$.ligerDialog.open({
         		url: 'medDeptRequriedConfirmReturnPage.do?isCheck=false&reqIds='+reqIds, 
         			height: 300,width:450, title:'退回科室',
         			modal:true, showToggle:false, showMax : true ,showMin: false, isResize:true,
       				buttons: [ 
       				           { text: '确定', onclick: function (item, dialog) { 
	          				        	   	dialog.frame.saveMedDepartRequriedConfirmReturn();	          				        	   	
	          				        	   	dialog.close(); 
	          				        	   	query();
       				        	    },cls:'l-dialog-btn-highlight' }, 
       				           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
       				         ] 
       			});	
		
		}
	}
	//打印
	function printData(){
		
	}
	
	//字典加载
	function loadDict() {
		
		autocomplete("#store_code", "../../../queryMedStore.do?isCheck=false", "id", "text", true, true,{is_purchase : '1'},false,false,'160',"",200);//仓库
		autocomplete("#dept_code", "../../../queryMedDept.do?isCheck=false", "id", "text", true, true,{is_last : '1'}, false,false,'160',"",200);//科室
		autoCompleteByData("#state", medRequireMain_state.Rows, "id", "text", true, true);
		
		autodate("#begin_date", "yyyy-mm-dd", "month_first");
	    autodate("#end_date", "yyyy-mm-dd", "month_last");
		
		$("#begin_date").ligerTextBox({width:160});
		$("#end_date").ligerTextBox({width:160});
		$("#dept_code").ligerTextBox({width:160});		
		$("#brif").ligerTextBox({width:360});
		$("#store_code").ligerTextBox({width:160});
        $("#state").ligerTextBox({width:160});
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
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">日期范围：</td>
			<td align="left" class="l-table-edit-td" style="width:160px;">
				<input class="Wdate" name="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" id="begin_date" />
			</td>
			<td align="center" class="l-table-edit-td" style="width: 10px;">至：</td>
			<td align="right" class="l-table-edit-td" style="width:160px;">
				<input class="Wdate" name="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" id="end_date" />
			</td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">编制科室：</td>
			<td align="left" class="l-table-edit-td">
				<input name="dept_code" type="text" requried="false" id="dept_code" />
			</td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">状态：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="state" type="text" requried="false" id="state" />
			</td>
			<td></td>
			
			
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">摘要：</td>
			<td align="left" class="l-table-edit-td" colspan="3">
				<input name="brif" type="text" requried="false" id="brif" style="width:360px;"/>
			</td>
			<td align="left"></td>

			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">响应库房：</td>
			<td align="left" class="l-table-edit-td">
				<input name="store_code" type="text" requried="false" id="store_code" />
			</td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">需求计划单号：</td>
			<td align="left" class="l-table-edit-td">
				<input name="req_code" type="text" requried="false" id="req_code" />
			</td>
			<td align="left"></td>
		</tr>

	</table>

	<div id="maingrid"></div>
	
</body>
</html>
