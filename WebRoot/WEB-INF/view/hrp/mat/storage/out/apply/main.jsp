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
    var show_detail=0;
    var renderFunc = {
    		state:function(value){//状态
    			if(value == 0){
 					return "已作废";
 				}else if(value == 1){
 					return "新建";
 				}else if(value == 2){
 					return "已审核";
 				}else if(value == 3){
 					return "已发送";
 				}else if(value == 4){
 					return "退回";
 				}
			},
			rela_state:function(value){//处理状态
				if(value == 1 ){
 					return "待处理";	
 				}else if(value == 2){
 					return "部分处理";
 				}else if(value == 3){
 					return "全部处理";
 				}else if(value == 4){
 					return "已采购";
 				}
			} 
	}; 
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	//loadHead(null);	
		loadHotkeys();
		showDetail();
		show_detail = $("#show_detail").is(":checked") ? 1 : 0 ;
		//query();
    });
    
    function showDetail(){
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		if(show_detail==0){
			$("#batch_no").val();
		}
		if (grid) {
			//由于一个对象多次绑定相同的事件，需要进行解绑在绑定 
			grid.unbind(); 
		}
		loadHead();
		//query();
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
		if(show_detail == 1){
			   	grid.options.parms.push({name:'inv_code',value:$("#inv_code").val()});
		}
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	if(show_detail=="1"){ 
    		grid = $("#maingrid").ligerGrid({
    			columns: [{
    					display: '申请单号', name: 'apply_no', align: 'left', width: 150,
    					
    					render : function(rowdata, rowindex, value) {
    						if(value == '合计'){
    							return value;
    						}else{
	    						return '<a href=javascript:update_open("' 
	    							+ rowdata.group_id 
	    							+ ',' + rowdata.hos_id 
	    							+ ',' + rowdata.copy_code 
	    							+ ',' + rowdata.apply_id
	    							+ '")>'+rowdata.apply_no+'</a>';
    						}		
    					}
    				},{ 
    		 			display: '摘要', name: 'brief', align: 'left', width: 200,
    		 		},  { display: '材料编码', name: 'inv_code', align: 'left', width: '120'},
     		 		{ display: '材料名称', name: 'inv_name', align: 'left', width: '120'},
     		 		{ display: '计量单位', name: 'unit_name', align: 'left', width: '60'},
     		 		{ display: '规格型号', name: 'inv_model', align: 'left', width: '120'},
     		 		{ display: '生产厂商', name: 'fac_name', align: 'left', width: '80'},
     		 		{
     					display : '包装规格', name : 'inv_structure', width : 90, align : 'left'
     				},{
     					display : '请领数量(E)', name : 'app_amount', width : 90, type : 'number', align : 'right',
     					editor : {
     						type : 'number',
     					},
     					render : function(rowdata, rowindex, value) {
     						return value == null ? "" : formatNumber(value, 2, 1);
     					}/* ,
     					totalSummary: {
     						align : 'left',
     	                    render: function (suminf, column, cell) {
     	                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
     	                    }
     	                } */
     				},
     		 		{ display: '单价', name: 'price', align: 'right', width: '80',
     		 			render: function (rowdata, rowindex, value) {
 							return formatNumber(value, '${p04006 }', 1);
 						}
     		 		}, {
     					display : '金额', name : 'amount_money', width : 100, align : 'right',
     					render : function(rowdata, rowindex, value) {
     						return value == null ? "" : formatNumber(value, '2', 1);
     					}/* ,
     					totalSummary: {
     						align : 'right',
     	                    render: function (suminf, column, cell) {
     	                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '2', 1)+ '</div>';
     	                    }
     	                }  */
     				}, {
     					display : '是否代销材料', name : 'is_com', width : 90, align : 'left',
     					render : function(rowdata, rowindex, value) {
     						return value == 1 ? '是' : '否';
     					}
     				}, {
     					display : '是否关闭', name : 'is_closed', width : 60, align : 'left',
     					render : function(rowdata, rowindex, value) {
     						return value == 1 ? '是' : '否';
     					}
     				}, 
    		 		{ 
    		 			display: '申请日期', name: 'app_date', align: 'left', width: 130,
    		 		}, { 
    		 			display: '响应仓库', name: 'store_name', align: 'left', width: '120',
    		 		}, { 
    		 			display: '状态', name: 'state', align: 'left', width: 80,hide:true
    		 			
    		 		},{ 
    		 			display: '状态', name: 'state_name', align: 'left', width: 80,
    		 			
    		 		},
    		 		{ 
    		 			display: '退回原因', name: 'back_reason', align: 'left', width: 150,
    		 		}, { 
    		 			display: '申请部门', name: 'dept_name', align: 'left', width: 150,
    		 		}, { 
    		 			display: '申请人', name: 'emp_name', align: 'left', width: 80,
    		 		}, { 
    		 			display: '制单人', name: 'maker', align: 'left', width: 80,
    		 		}, { 
    		 			display: '发送人', name: 'sender', align: 'left', width: 80,
    		 		}, { 
    		 			display: '响应人', name: 'response_emp', align: 'left', width: 80,
    		 		}
    		 	],
    	 		rowAttrRender: function(rowdata,rowid){
    	 			if(rowdata.rela_state != 3){
    	 				return "style='color: #FF0000'"
    	 			}
     			},
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatCommonOutApply.do?isCheck=true&show_detail=1',
    			width: '100%', height: '100%', checkbox: true,rownumbers:true,
         		gid: 'detailGrid', 
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
    				{ text: '审核（<u>S</u>）', id:'audit', click: audit, icon:'audit'/*, disabled: '${p04016 }' == 1 ? true : false */},
    				{ line:true }, 
    				{ text: '消审（<u>U</u>）', id:'unAudit', click: unAudit, icon:'unaudit'/*, disabled: '${p04016 }' == 1 ? true : false*/ },
    				{ line:true }/*, 
    				{ text: '作废（<u>C</u>）', id:'cancel', click: cancel, icon:'bcancle' },
    				{ line:true },
    				{ text: '历史引入（<u>I</u>）', id:'hosImp', click: hosImp, icon:'up' }
    				*/
    			]},  
    			onDblClickRow : function (rowdata, rowindex, value){
    				if(!rowdata.apply_id){ 
    					$.ligerDialog.warn('请选择数据 ');
						return ; 
    				} 
    				   update_open(
    					rowdata.group_id + "," + 
    					rowdata.hos_id + "," + 
    					rowdata.copy_code + "," + 
    					rowdata.apply_id 
    				);  
    			} 
    		});
    	}else{ 
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
    		 		}, { 
    		 			display: '响应仓库', name: 'store_name', align: 'left', width: '120',
    		 		}, { 
    		 			display: '状态', name: 'state', align: 'left', width: 80,hide:true
    		 		
    		 		},  { 
    		 			display: '状态', name: 'state_name', align: 'left', width: 80,
    		 		
    		 		},{ 
    		 			display: '处理状态', name: 'rela_state', align: 'left', width: 80,hide:true
    		 			
    		 		} , { 
    		 			display: '处理状态', name: 'rela_state_name', align: 'left', width: 80
    		 			},
    		 		{ 
    		 			display: '摘要', name: 'brief', align: 'left', width: 200,
    		 		}, { 
    		 			display: '退回原因', name: 'back_reason', align: 'left', width: 150,
    		 		}, { 
    		 			display: '申请部门', name: 'dept_name', align: 'left', width: 150,
    		 		}, { 
    		 			display: '申请人', name: 'emp_name', align: 'left', width: 80,
    		 		}, { 
    		 			display: '制单人', name: 'maker', align: 'left', width: 80,
    		 		}, { 
    		 			display: '发送人', name: 'sender', align: 'left', width: 80,
    		 		}, { 
    		 			display: '响应人', name: 'response_emp', align: 'left', width: 80,
    		 		}
    		 	],
    	 		rowAttrRender: function(rowdata,rowid){
    	 			if(rowdata.rela_state != 3){
    	 				return "style='color: #FF0000'"
    	 			}
     			},
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatCommonOutApply.do?isCheck=true&show_detail=0',
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
    				{ text: '取消发送', id:'qxsend', click: qxsend, icon:'login' },
    				{ line:true }, 
    				{ text: '审核（<u>S</u>）', id:'audit', click: audit, icon:'audit'/*, disabled: '${p04016 }' == 1 ? true : false */},
    				{ line:true }, 
    				{ text: '消审（<u>U</u>）', id:'unAudit', click: unAudit, icon:'unaudit'/*, disabled: '${p04016 }' == 1 ? true : false*/ },
    				{ line:true }
    				/*, 
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
    	}
    	
    	

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>申请日期："+$("#begin_app_date").val() +" 至  "+ $("#end_app_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="科室申领";
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
			url: 'hrp/mat/storage/out/apply/addPage.do?isCheck=false',
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
			url: 'hrp/mat/storage/out/apply/updatePage.do?isCheck=false&' + paras.toString(),
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
			/* if(apply_nos != ""){
				$.ligerDialog.error("删除失败！<br>以下单据不是未审核状态：<br>"+apply_nos);
				return;
			}  */
			if(apply_nos != ""){
				$.ligerDialog.error("删除失败！<br>以下单据不是新建状态：<br>"+apply_nos);
				return;
			} 
			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteMatCommonOutApply.do",{ParamVo : ParamVo.toString()},function (responseData){
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
				$.ligerDialog.error("发送失败！<br>已发送状态不能继续发送<br>或未审核状态无法发送:<br>"+apply_nos);
				return;
			}  
			$.ligerDialog.confirm('确定发送?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("sendMatCommonOutApply.do",{ParamVo : ParamVo.toString()},function (responseData){
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
				if(this.state != 3 ||this.rela_state > 1 ){
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
				$.ligerDialog.error("操作失败！<br>以下单据不能取消发送：<br>"+apply_nos);
				return;
			}
			$.ligerDialog.confirm('是否取消发送?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("backsendMatCommonOutApply.do",{ParamVo : ParamVo.toString()},function (responseData){
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
					ajaxJsonObjectByUrl("auditMatCommonOutApply.do",{ParamVo : ParamVo.toString()},function (responseData){
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
					ajaxJsonObjectByUrl("unAuditMatCommonOutApply.do",{ParamVo : ParamVo.toString()},function (responseData){
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
					ajaxJsonObjectByUrl("cancelMatCommonOutApply.do",{ParamVo : ParamVo.toString()},function (responseData){
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
		autocomplete("#apply_dept", "../../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last: 1,read_or_write:'1'},true);
		autoCompleteByData("#check_state", matApplyMain_checkState.Rows, "id", "text", true, true);
		autocomplete("#store_code", "../../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_read:'1'},true);
        $("#begin_app_date").ligerTextBox({width:100});
        //autodate("#begin_app_date", "yyyy-mm-dd", "month_first");
        autodate("#begin_app_date", "yyyy-mm-dd", "before_month");
        $("#end_app_date").ligerTextBox({width:100});
        //autodate("#end_app_date", "yyyy-mm-dd", "month_last");
        autodate("#end_app_date",'yyyy-MM-dd', "new");
        $("#brief").ligerTextBox({width:238});
        $("#inv_code").ligerTextBox({width:238});
        $("#apply_no").ligerTextBox({width : 160});
        $("#check_state").ligerTextBox({width:160});
        $("#inv_code").ligerTextBox({width:220});
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
            
            	<!-- <select  id="check_state">
            		<option value="">请选择</option>
            		<option value="1" selected = "selected">新建</option>
            		<option value="3">发送</option>
            		<option value="4">退回</option>
            		<option value="0">作废</option>
            	</select> -->
            	<input name="check_state" type="text" id="check_state" ltype="text" validate="{required:false}" />
            </td>
			<td align="right" class="l-table-edit-td" >
				单据号：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="apply_no" type="text" id="apply_no" ltype="text" validate="{required:false}" />
			</td>
        </tr> 
        <tr>
			<td align="right" class="l-table-edit-td" >
				材料信息：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false}" />
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%"></td>
			<td width="20%">
            	<input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示明细
            </td>
        </tr> 
       
    </table>
	<div id="maingrid"></div>
</body>
</html>
