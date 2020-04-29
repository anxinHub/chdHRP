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
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		 query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'begin_app_date',
			value : $("#begin_app_date").val()
		});
		grid.options.parms.push({
			name : 'end_app_date',
			value : $("#end_app_date").val()
		}); 
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("apply_dept").getValue() == null ? "" : liger.get("apply_dept").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'dept_no',
			value : liger.get("apply_dept").getValue() == null ? "" : liger.get("apply_dept").getValue().split(",")[1]
		}); 
		grid.options.parms.push({
			name : 'brief',
			value : $("#brief").val()
		}); 
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'store_no',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1]
		});
		grid.options.parms.push({
			name : 'state',
			value : liger.get("check_state").getValue() == null ? "" : liger.get("check_state").getValue()
		}); 
		grid.options.parms.push({
			name : 'apply_no',
			value : $("#apply_no").val()
		}); 
		grid.options.parms.push({
			name : 'inv_code',
			value : $("#inv_code").val()
		}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '申请单号', name: 'apply_no', align: 'left', width: 150,
					render : function(rowdata, rowindex, value) {
						return '<a href=javascript:update_open("' 
							+ rowdata.group_id 
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code 
							+ ',' + rowdata.apply_id
							+ '")>'+rowdata.apply_no+'</a>';
					}
				}, { 
		 			display: '申请日期', name: 'app_date', align: 'left', width: 80,
		 		}, { 
		 			display: '摘要', name: 'brief', align: 'left', width: 200,
		 		},{ 
		 			display: '部门', name: 'dept_name', align: 'left', width: 200,
		 		},{ 
		 			display: '药品编码', name: 'inv_code', align: 'left', width: 150,
		 		},{ 
		 			display: '药品名称', name: 'inv_name', align: 'left', width: 150,
		 		},{ 
		 			display: '规格型号', name: 'inv_model', align: 'left', width: 150,
		 		},{ 
		 			display: '生产厂商', name: 'fac_name', align: 'left', width: 150,
		 		},{ 
		 			display: '计量单位', name: 'unit_name', align: 'left', width: 80,
		 		},{ 
		 			display : '请领数量', name : 'app_amount', width : 80,  align : 'right',
					
					render : function(rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, 2, 1);
					},
		 		},{ 
		 			display : '单价', name : 'price', width : 80, align : 'right', 
					render : function(rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, '${sessionScope.med_para_map["08006"] }', 1);
					}
		 		},{ 
		 			display : '金额', name : 'amount_money', width : 80, align : 'right',
					render : function(rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, '${sessionScope.med_para_map["08005"] }', 1);
					},
		 		},{ 
		 			display : '是否代销药品', name : 'is_com', width : 80, align : 'left',
					render : function(rowdata, rowindex, value) {
						return value == 1 ? '是' : '否';
					}
		 		},{
					display : '是否科室库管理', name : 'is_sec_whg', width : 80, align : 'left',
					render : function(rowdata, rowindex, value) {
						return value == 1 ? '是' : '否';
					}
				}, {
					display : '备注(E)', name : 'note', width : 80, align : 'left',
					
				},{ 
		 			display: '状态', name: 'state', align: 'left', minWidth: 80,
		 			render: function(rowdata, rowindex, value){
		 				if(value == 0){
		 					return "已作废";
		 				}else if(value == 1){
		 					return "未审核";
		 				}else if(value == 2){
		 					return "已审核";
		 				}else if(value == 3){
		 					return "已发送";
		 				}else if(value == 4){
		 					return "退回";
		 				}
		 			}
		 		}, { 
		 			display: '处理状态', name: 'rela_state', align: 'left', minWidth: 80,
		 			render: function(rowdata, rowindex, value){
		 				if(value == 1){
		 					return "待处理";	
		 				}else if(value == 2){
		 					return "部分处理";
		 				}else if(value == 3){
		 					return "全部处理";
		 				}
		 			}
		 		} /* { 
		 			display: '申请部门', name: 'dept_name', align: 'left', minWidth: 150,
		 		}, { 
		 			display: '申请人', name: 'emp_name', align: 'left', minWidth: 80,
		 		}, { 
		 			display: '状态', name: 'state', align: 'left', minWidth: 80,
		 			render: function(rowdata, rowindex, value){
		 				if(value == 0){
		 					return "已作废";
		 				}else if(value == 1){
		 					return "未审核";
		 				}else if(value == 2){
		 					return "已审核";
		 				}else if(value == 3){
		 					return "已发送";
		 				}else if(value == 4){
		 					return "退回";
		 				}
		 			}
		 		}, { 
		 			display: '处理状态', name: 'rela_state', align: 'left', minWidth: 80,
		 			render: function(rowdata, rowindex, value){
		 				if(value == 1){
		 					return "待处理";	
		 				}else if(value == 2){
		 					return "部分处理";
		 				}else if(value == 3){
		 					return "全部处理";
		 				}
		 			}
		 		}, { 
		 			display: '退回原因', name: 'back_reason', align: 'left', minWidth: 150,
		 		} */ 
		 		],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedCommonOutApplyByCheck.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				{ line:true },
				{ text: '删除（<u>D</u>）', id:'delete', click: remove, icon:'delete' },
				{ line:true }, 
				{ text: '发送（<u>F</u>）', id:'send', click: send, icon:'logout' },
				{ line:true }, 
				{ text: '审核（<u>S</u>）', id:'audit', click: audit, icon:'audit' },
				{ line:true }, 
				{ text: '消审（<u>U</u>）', id:'unAudit', click: unAudit, icon:'unaudit' },
				{ line:true }/*, 
				{ text: '作废（<u>C</u>）', id:'cancel', click: cancel, icon:'bcancle' },
				{ line:true },
				{ text: '历史引入（<u>I</u>）', id:'hosImp', click: hosImp, icon:'up' }
				*/
			]},  
			onDblClickRow : function (rowdata, rowindex, value){
				update_open(
					rowdata.group_id + "," + 
					rowdata.hos_id + "," + 
					rowdata.copy_code + "," + 
					rowdata.apply_id 
				);
			} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('F', send);
		hotkeys('S', audit);
		hotkeys('U', unAudit);
		hotkeys('C', cancel);
		hotkeys('I', hosImp);
	}
    
    //新增
    function add_open(){
    	parent.$.ligerDialog.open({
			title: '申领单添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/storage/out/applynstore/addPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
    
    //修改
    function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"apply_id="+vo[3] ;
		
		parent.$.ligerDialog.open({
			title: '申请单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/storage/out/applynstore/updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
    }
    	
    //删除
    function remove(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var apply_nos = "";
			$(data).each(function (){	
				if(this.state != 1){
					apply_nos = apply_nos + this.apply_no + "<br>";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.apply_id 
				) 
			});
			if(apply_nos != ""){
				$.ligerDialog.error("删除失败！<br>以下单据不是未审核状态：<br>"+apply_nos);
				return;
			} 
			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteMedCommonOutApplyN.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    
    //发送
	function send(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var apply_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					apply_nos = apply_nos + this.apply_no + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.apply_id 
				) 
			});
			if(apply_nos != ""){
				$.ligerDialog.error("发送失败！<br>以下单据不是审核状态：<br>"+apply_nos);
				return;
			}
			$.ligerDialog.confirm('确定发送?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("sendMedCommonOutApplyN.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	
    //审核
	function audit(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var apply_nos = "";
			$(data).each(function (){		
				if(this.state != 1){
					apply_nos = apply_nos + this.apply_no + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.apply_id 
				) 
			});
			if(apply_nos != ""){
				$.ligerDialog.error("审核失败！<br>以下单据不是未审核状态：<br>"+apply_nos);
				return;
			}
			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("auditMedCommonOutApplyN.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}

    //消审
	function unAudit(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var apply_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					apply_nos = apply_nos + this.apply_no + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.apply_id 
				) 
			});
			if(apply_nos != ""){
				$.ligerDialog.error("消审失败！<br>以下单据不是审核状态：<br>"+apply_nos);
				return;
			}
			$.ligerDialog.confirm('确定消审?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("unAuditMedCommonOutApplyN.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	
    //作废
    function cancel(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var apply_nos = "";
			$(data).each(function (){		
				if(this.check_state != 2){
					apply_nos = apply_nos + this.apply_no + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.apply_id 
				) 
			});
			if(apply_nos != ""){
				$.ligerDialog.error("作废失败！<br>以下单据不是已审核状态：<br>"+apply_nos);
				return;
			}
			$.ligerDialog.confirm('确定作废单据?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("cancelMedCommonOutApplyN.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    
    //历史导入
    function hosImp(){
    	return ;
    }
   
    function loadDict(){
		//字典下拉框
		autocomplete("#apply_dept", "../../../queryHosDeptDictByPerm.do?isCheck=false", "id", "text", true, true, {is_last: 1});
		autoCompleteByData("#check_state", medApplyMain_checkState.Rows, "id", "text", true, true);
		autocomplete("#store_code", "../../../queryMedStore.do?isCheck=false", "id", "text", true, true);
        $("#begin_app_date").ligerTextBox({width:100});
        autodate("#begin_app_date", "yyyy-mm-dd", "month_first");
        $("#end_app_date").ligerTextBox({width:100});
        autodate("#end_app_date", "yyyy-mm-dd", "month_last");
        $("#brief").ligerTextBox({width:238});
        $("#apply_no").ligerTextBox({width : 160});
        $("#inv_code").ligerTextBox({width : 238});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  width="10%">
            	申请日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" >
							<input class="Wdate" name="begin_app_date" id="begin_app_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_app_date" id="end_app_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
			<td align="right" class="l-table-edit-td"  width="14%">
				申请科室：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="apply_dept" type="text" id="apply_dept" ltype="text" validate="{required:false}" />
            </td>
        	<td align="right" class="l-table-edit-td" width="14%">
            	响应库房：
            </td>
            <td align="left" class="l-table-edit-td" width="20%">
				<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
	        </td>
        </tr> 
        <tr>
			<td align="right" class="l-table-edit-td" >
				摘要：
			</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="brief" type="text" id="brief" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
			<td align="right" class="l-table-edit-td" >
				状态：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="check_state" type="text" id="check_state" ltype="text" validate="{required:false}" />
            </td>
			<td align="right" class="l-table-edit-td" >
				单据号：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="apply_no" type="text" id="apply_no" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td" >
				药品编码：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
        </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
