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
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
	var budg_year;
	var grid;
	var gridManager = null;
	$(function() {
		loadDict()//加载下拉框
		loadHead(null);//加载数据
		loadHotkeys();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'store_no',
			value : liger.get("store_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'dept_no',
			value : liger.get("dept_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name : 'proj_id',
			value : liger.get("proj_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'proj_no',
			value : liger.get("proj_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name : 'create_date_beg',
			value : liger.get("create_date_beg").getValue()
		});
		grid.options.parms.push({
			name : 'create_date_end',
			value : liger.get("create_date_end").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'in_date_beg',
			value : liger.get("in_date_beg").getValue()
		});
		grid.options.parms.push({
			name : 'in_date_end',
			value : liger.get("in_date_end").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'state',
			value : liger.get("state").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
	}
	
	//加载表格
	function loadHead() {
		var flag;
		grid = $("#maingrid").ligerGrid(
				{
					columns : [ {
						display : '入库单号',
						name : 'ass_in_no',
						align : 'left',
						width : '10%',
						render : function(rowdata, rowindex, value) {
							if(rowdata.note == "合计"){
								return '';
							}
							return "<a href=javascript:openUpdate('"
									+ rowdata.group_id + "|"
									+ rowdata.hos_id + "|"
									+ rowdata.copy_code + "|"
									+ rowdata.ass_in_no + "')>"
									+ rowdata.ass_in_no + "</a>";
						}
					}, {
						display : '备注',
						name : 'note',
						align : 'left',
					}, {
						display : '仓库',
						name : 'store_id',
						textField : 'store_name',
						align : 'left',
					}, {
						display : '领用科室',
						name : 'dept_id',
						textField : 'dept_name',
						align : 'left',
					}, {
						display : '项目',
						name : 'proj_id',
						textField : 'proj_name',
						align : 'left',
					}, {
						display : '入库金额',
						name : 'in_money',
						align : 'right',
						render: function(item)
			            {
			                    return formatNumber(item.in_money,'${ass_05005}',1);
			            }
					}, {
						display : '制单人',
						name : 'create_emp',
						textField : 'create_emp_name',
						align : 'left',
					}, {
						display : '制单日期',
						name : 'create_date',
						align : 'left',
					}, {
						display : '确认人',
						name : 'confirm_emp',
						textField : 'confirm_emp_name',
						align : 'left',
					}, {
						display : '确认日期',
						name : 'in_date',
						align : 'left',
					}, {
						display : '状态',
						name : 'state',
						textField : 'state_name',
						align : 'left',
					}],
					usePager : false,
					url : 'queryAssChkInInassets.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad : true,
					onBeforeEdit : function() {
						flag = true;
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|" + rowdata.ass_in_no);
					},
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '查询（<u>Q</u>）',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						}, {
							text : '添加（<u>A</u>）',
							id : 'add',
							click : add_open,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '删除（<u>D</u>）',
							id : 'delete',
							click : remove,
							icon : 'delete'
						}, {
							line : true
						/* }, {
							text : '导入（<u>I</u>）',
							id : 'import',
							click : imp,
							icon : 'up'
						}, {
							line : true */
						}, {
							text : '入库确认（<u>S</u>）',
							id : 'audit',
							click : audit,
							icon : 'right'
						}, {
							line : true
						} ,
						{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
						{ line:true } ,
						{ text: '批量打印', id:'print', click: print, icon:'print' }]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	//审核
	function audit() {
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
							ParamVo.push(this.group_id + "@" + this.hos_id + "@"
									+ this.copy_code + "@" + this.ass_in_no);
						
					});
			$.ligerDialog.confirm('处置确认?', function (yes){
            	if(yes){
					ajaxJsonObjectByUrl("updateConfirmChkInInassets.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
            	}
			});
		}
	}

	//导入
	function imp() {
	}
	
	//添加
	function add_open() {
		parent.$.ligerDialog.open({
			url : 'hrp/ass/assinassets/check/checkin/assChkInInassetsAddPage.do?isCheck=false',
			title : '申报信息录入',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			slide:false,
			data: {
            },
            parentframename: window.name
		});
	}

	//修改
	function openUpdate(obj) {
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "ass_in_no=" + vo[3];
		parent.$.ligerDialog.open({
			url : 'hrp/ass/assinassets/check/checkin/assChkInInassetsUpdatePage.do?isCheck=false&' + parm.toString(),
			title : '申报信息修改',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			slide:false,
			parentframename: window.name
		});
	}
	
	//删除
	function remove() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.ass_in_no)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssChkInInassets.do?isCheck=false",
							{
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
				}
			});
		}
	}
	
	//打印模板设置
    function printSet(){
	  
    	var useId=0;//统一打印
		if('${ass_05050}'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${ass_05050}'==2){
			//按仓库打印
			if(liger.get("store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_id").getValue().split(",")[0];
		}
    	
		officeFormTemplate({template_code:"0505001",use_id : useId});
    }


    //打印
    function print(){
    	var useId=0;//统一打印
 		if('${ass_05050}'==1){
 			//按用户打印
 			useId='${user_id }';
 		}else if('${ass_05050}'==2){
 			//按仓库打印
 			if(liger.get("store_id").getValue()==""){
 				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
 				return;
 			}
 			useId=liger.get("store_id").getValue().split(",")[0];
 		}

 		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
		
			var ass_in_no ="" ;
			$(data).each(function (){
				
				ass_in_no  += "'"+this.ass_in_no+"',"
					
			});
			
			 var para={
	    			paraId :ass_in_no.substring(0,ass_in_no.length-1) ,
	    			class_name:"com.chd.hrp.ass.serviceImpl.check.inassets.AssChkInMainInassetsServiceImpl",
	    			method_name:"assChkInMainInassetsByPrintTemlate",
	    			template_code:'0505001',
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	 }; 
		 
           	ajaxJsonObjectByUrl("queryState.do?isCheck=false",{paraId :ass_in_no.substring(0,ass_in_no.length-1) },function (responseData){
           		if(responseData.state=="true"){
           		   officeFormPrint(para);
           		}
           	});
	   }
    }
	
	//字典下拉框
	function loadDict() {
		var param = {query_key:''};
		
		autocomplete("#store_id", "../../../queryHosStoreDict.do?naturs_code=05&isCheck=false","id", "text",true,true,param,true,null,"160");
		
		autocomplete("#dept_id", "../../../queryDeptDict.do?isCheck=false","id", "text",true,true,param,true,null,"160");
		
		autocomplete("#proj_id", "../../../queryAssProjDict.do?isCheck=false","id", "text",true,true,param,true,null,"160");

		//状态
		/* $("#state").ligerTextBox({width : 160}); */
		$('#state').ligerComboBox({
			data:[{id:0,text:'新建'},{id:1,text:'审核'},{id:2,text:'确认'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		});
		
		$("#create_date_beg,#create_date_end,#in_date_beg,#in_date_end").ligerTextBox({width : 100});
		autodate("#create_date_beg","YYYY-mm-dd","month_first");

		autodate("#create_date_end","YYYY-mm-dd","month_last");
		
	}
	
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('I', imp);
		hotkeys('S', audit);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">制单日期：</td>
            <td align="left" class="l-table-edit-td" >
            <div style="float:left;">
            	<input name="create_date_beg" class="Wdate" type="text" id="create_date_beg" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            </div>
            <span style="float:left;margin: 0 3px;">至</span>
            <div style="float:left;">
            	<input name="create_date_end" class="Wdate" type="text" id="create_date_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
			</div>
			</td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">仓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库：</td>
			<td align="left" class="l-table-edit-td">
				<input name="store_id"type="text" id="store_id" />
			</td>
			<td align="left"></td>
			
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">领用科室：</td>
			<td align="left"  class="l-table-edit-td">
				<input name="dept_id" type="text" id="dept_id" />
			</td>		 
			<td align="left"></td>
			
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：</td>
			<td align="left"  class="l-table-edit-td">
				<input name="proj_id" type="text" id="proj_id" />
			</td>		 
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">确认日期：</td>
            <td align="left" class="l-table-edit-td" >
            <div style="float:left;">
            	<input name="in_date_beg" class="Wdate" type="text" id="in_date_beg" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            </div>
            <span style="float:left;margin: 0 3px;">至</span>
            <div style="float:left;">
            	<input name="in_date_end" class="Wdate" type="text" id="in_date_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
			</div>
			</td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
			<td align="left" class="l-table-edit-td">
				<!-- <select id="state" name="state">
            		<option value="">全部</option>
            		<option value="0">新建</option>
            		<option value="1">审核</option>
            		<option value="2">确认</option>
            	</select> -->
            	<input  name="state" type="text" id="state"/>
            </td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
