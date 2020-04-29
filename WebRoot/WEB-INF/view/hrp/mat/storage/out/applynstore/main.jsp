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
    var detailGrid;
    var apply_id;
    var apply_store_id;
    var renderFunc = {
    
			price:function(value){//单价
				return formatNumber(value, '${p04006 }', 1);
			},
			amount_money:function(value){//金额
				return formatNumber(value, '${p04005 }', 1);
			},
			app_amount:function(value){//请领数量
				return formatNumber(value==null?0:value,2,1);
			},
			out_amount:function(value){//出库数量
				return formatNumber(value==null?0:value),2,1;
			},
			pur_amount:function(value){//采购数量
				return formatNumber(value==null?0:value,2,1);
			},
			is_com:function(value){//是否代销
				if(value == 1){
 					return "是";	
 				}else if(value == 0){
 					return "否";
 				} 
			} ,
			is_sec_whg:function(value){//是否科室库管理
				if(value == 1){
 					return "是";	
 				}else if(value == 0){
 					return "否";
 				} 
			} ,
			is_closed:function(value){//是否关闭
				if(value == 1){
 					return "是";	
 				}else if(value == 0){
 					return "否";
 				} 
			} ,
			
			
			
	}; 
    
    $(function ()
    {
    	$("#divAll").ligerLayout({
        	topHeight: 100,
		});
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        loadDetailHead(null);
		loadHotkeys();
		query();
    });
    //初始化全局变量
    function initPara(){
    	store_dept = "";
        apply_id = "";
		detailGrid.deleteAllRows();    
    }
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
    	initPara();
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
		 			display: '申请日期', name: 'app_date', align: 'left', width: 130,
		 		},
		 		 { 
		 			display: '发送日期', name: 'send_date', align: 'left', width: 130,
		 		},{ 
		 			display: '摘要', name: 'brief', align: 'left', width: 200,
		 		}, { 
		 			display: '退回原因', name: 'back_reason', align: 'left', width: 150,
		 		} , { 
		 			display: '申请部门', name: 'dept_name', align: 'left', width: 150,
		 		}, { 
		 			display: '申请人', name: 'emp_name', align: 'left', width: 80,
		 		}, { 
		 			display: '状态', name: 'state', align: 'left', width: 80,hide:true
		 			
		 		},  { 
		 			display: '状态', name: 'state_name', align: 'left', width: 80,
		 			
		 		},{ 
		 			display: '处理状态', name: 'rela_state', align: 'left', width: 80,hide:true
		 			
		 		},{ 
		 			display: '处理状态', name: 'rela_state_name', align: 'left', width: 80,
		 			
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatCommonOutApplyN.do',
			inWindow: false, width: '100%', height: '60%', checkbox: true,rownumbers:true,
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
				{ text: '取消发送', id:'qxsend', click: qxsend, icon:'login' },
				{ line:true },
				{ text: '审核（<u>S</u>）', id:'audit', click: audit, icon:'audit', disabled: '${p04016 }' == 1 ? true : false },
				{ line:true }, 
				{ text: '消审（<u>U</u>）', id:'unAudit', click: unAudit, icon:'unaudit', disabled: '${p04016 }' == 1 ? true : false },
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
			},  
			onClickRow : function (rowdata, rowindex, value){
				if(apply_id != rowdata.apply_id){
					changeDetailGrid(rowdata.apply_id+","+rowdata.store_id);
				}
			},
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    //查询
    function  queryDetail(){
    	detailGrid.options.parms=[];
    	detailGrid.options.newPage=1;
        //根据表字段进行添加查询条件
 		detailGrid.options.parms.push({
 			name : 'apply_id',
 			value : apply_id
 		});
 		detailGrid.options.parms.push({
 			name : 'store_id',
 			value : apply_store_id
 		});
    	//加载查询条件
    	detailGrid.loadData(detailGrid.where);
     }
    
    function changeDetailGrid(obj){
    	var vo = obj.split(",");
    	
    	apply_id = vo[0];
    	apply_store_id = vo[1];
    	
    	queryDetail();
    }

    function loadDetailHead(){
    	detailGrid = $("#detailgrid").ligerGrid({
			columns : [ {
				display : '交易编码', name : 'bid_code', width : 100, align : 'left',
			},{
				display : '材料编码', name : 'inv_code', width : 120, align : 'left',
				totalSummary: {
                    align : 'right',
                    render: function (suminf, column, cell) {
                    	return '<div>合计：</div>';
                    }
                } 
			}, {
				display : '材料名称', name : 'inv_id', textField : 'inv_name', width : 240, align : 'left',
			}, {
				display : '规格型号', name : 'inv_model', width : 180, align : 'left'
			}, {
				display : '生成厂商', name : 'fac_name', width : 200, align : 'left'
			}, {
				display : '计量单位', name : 'unit_name', width : 80, align : 'left'
			}, {
				display : '请领数量', name : 'app_amount', width : 80, type : 'number', align : 'right',
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, 2, 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
			}, {
				display : '出库数量', name : 'out_amount', width : 80, type : 'number', align : 'right',
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, 2, 1);
				}
			}, {
				display : '采购数量', name : 'pur_amount', width : 80, type : 'number', align : 'right',
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, 2, 1);
				}
			}, {
				display : '单价', name : 'price', width : 80, align : 'right', 
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, {
				display : '金额', name : 'amount_money', width : 90, align : 'right',
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                } 
			}, {
				display : '是否代销材料', name : 'is_com', width : 100, align : 'left',
				render : function(rowdata, rowindex, value) {
					return value == 1 ? '是' : '否';
				}
			}, {
				display : '是否科室库管理', name : 'is_sec_whg', width : 100, align : 'left',
				render : function(rowdata, rowindex, value) {
					return value == 1 ? '是' : '否';
				}
			}, {
				display : '是否关闭', name : 'is_closed', width : 70, align : 'left',
				render : function(rowdata, rowindex, value) {
					return value == 1 ? '是' : '否';
				}
			}, {
				display : '备注(E)', name : 'note', width : 120, align : 'left',
			} ],
			dataAction : 'server', dataType : 'server', usePager : false, 
			width : '100%', height : '40%', inWindow: false, heightDiff: 28, 
     		gid: 'detailGrid', 
			url : 'queryMatCommonOutApplyDetailN.do?isCheck=false', 
			checkbox : false, enabledEdit : false, alternatingRow : true, 
			isScroll : true, rownumbers : true, delayLoad : true,//初始化明细数据 
		});
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>申请日期："+$("#begin_app_date").val() +" 至  "+ $("#end_app_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="科室申领(不按仓库)";
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
		hotkeys('H', qxsend);
	}
    
    //新增
    function add_open(){
    	parent.$.ligerDialog.open({
			title: '申领单添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/out/applynstore/addPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
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
			url: 'hrp/mat/storage/out/applynstore/updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
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
					ajaxJsonObjectByUrl("deleteMatCommonOutApplyN.do",{ParamVo : ParamVo.toString()},function (responseData){
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
				if('${p04016 }' == 0){
					if(this.state < 2 || this.state == 3){
						apply_nos = apply_nos + this.apply_no + ",";
					}	
				}else{
					if(this.state == 3){
						apply_nos = apply_nos + this.apply_no + ",";
					}
				}	
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.apply_id 
				) 
			});
			if(apply_nos != ""){
				$.ligerDialog.error("发送失败！<br>已发送状态不能继续发送<br>或未审核状态无法发送：<br>"+apply_nos);
				return;
			}
			$.ligerDialog.confirm('确定发送?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("sendMatCommonOutApplyN.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	  //取消发送
	function qxsend(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var apply_nos = "";
			$(data).each(function (){	
				
				if(this.state !=3 ||  this.rela_state >1){
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
				$.ligerDialog.error("操作失败！<br>以下单据存在处理数据或未发送过不能取消发送：<br>"+apply_nos);
				return;
			}
			
			$.ligerDialog.confirm('是否取消发送?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("backsendMatCommonOutApplyN.do",{ParamVo : ParamVo.toString()},function (responseData){
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
					ajaxJsonObjectByUrl("auditMatCommonOutApplyN.do",{ParamVo : ParamVo.toString()},function (responseData){
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
					ajaxJsonObjectByUrl("unAuditMatCommonOutApplyN.do",{ParamVo : ParamVo.toString()},function (responseData){
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
					ajaxJsonObjectByUrl("cancelMatCommonOutApplyN.do",{ParamVo : ParamVo.toString()},function (responseData){
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
	
		/* 	autocomplete("#apply_dept", "../../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last : 1,is_write:'1'}, false, false, 250, false, 250);
    	liger.get("apply_dept").setValue('${user_msg.dept_id},${user_msg.dept_no}');
		liger.get("apply_dept").setText('${user_msg.dept_code} ${user_msg.dept_name}');
		 */
			autocomplete("#apply_dept", "../../../queryMatDept.do?isCheck=false", "id", "text", true, true, {is_last: 1,read_or_write:'1'},true, true, 250, true, 200);
		autoCompleteByData("#check_state", matApplyMain_checkState.Rows, "id", "text", true, true,'',true);
		autocomplete("#store_code", "../../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_read:'1'});
        $("#begin_app_date").ligerTextBox({width:100});
        //autodate("#begin_app_date", "yyyy-mm-dd", "month_first");
        autodate("#begin_app_date", "yyyy-mm-dd", "before_month");
        $("#end_app_date").ligerTextBox({width:100});
        //autodate("#end_app_date", "yyyy-mm-dd", "month_last");
        autodate("#end_app_date",'yyyy-MM-dd', "new");
        $("#brief").ligerTextBox({width:238});
        $("#apply_no").ligerTextBox({width : 160});
        $("#inv_code").ligerTextBox({width : 238});
        autodate("#begin_app_date",'yyyy-MM-dd');
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <div id="divAll" style="width: 100%; height 100%; margin: 0px; padding: 0px;">
	    <div position="top">
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
						材料信息：
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
					</td>
		        </tr>
		    </table>
		</div>
    	<div position="center">
			<div id="maingrid"></div>
			<div id="detailgrid"></div>
		</div>
	</div>
</body>
</html>
