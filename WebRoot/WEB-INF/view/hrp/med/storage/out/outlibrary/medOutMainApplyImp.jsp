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
    var detailGrid;
    var gridManager = null;
    var userUpdateStr;
    var apply_id;
    var apply_store_id;
	var applyObj = {};
	var store_dept = "";
	var gridRowIndex;
	var gridRowData;
	var detailGridAllChecked;//0:全不选,1:全选,2:根据条件判断
	
    $(function ()
    { 
    	$("#divAll").ligerLayout({
        	topHeight:70,
        	centerBottomHeight:80
		});

        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);
		loadHotkeys();
        loadDetailHead(null);
		query();
    });
    //初始化全局变量
    function initPara(){
    	store_dept = "";
        apply_id = "";
        apply_store_id = "";
    	applyObj = {};
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
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'apply_no',
			value : $("#apply_no").val()
		}); 
		grid.options.parms.push({
			name : 'is_zero',
			value : $("#is_zero").prop("checked") ? 1 : 0
		}); 
		/* grid.options.parms.push({
			name : 'is_back',
			value : $("#is_back").prop("checked") ? 1 : 0
		});  */
		
    	//加载查询条件
    	grid.loadData(grid.where);
    	initPara();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '申请单号', name: 'apply_no', align: 'left', minWidth: '150',
					render : function(rowdata, rowindex, value) {
						return '<a href=javascript:update_open("' 
							+ rowdata.group_id 
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code 
							+ ',' + rowdata.apply_id
							+ ',' + rowdata.rela_state
							+ '")>'+rowdata.apply_no+'</a>';
					}
				}, { 
		 			display: '申请日期', name: 'app_date', align: 'left', minWidth: '90',
		 		}, { 
		 			display: '摘要', name: 'brief', align: 'left', minWidth: '150',
		 		}, { 
		 			display: '响应仓库', name: 'store_name', align: 'left', minWidth: '120',
		 		}, { 
		 			display: '申请部门', name: 'dept_name', align: 'left', minWidth: '120',
		 		}, { 
		 			display: '申请人', name: 'emp_name', align: 'left', minWidth: '80',
		 		}, { 
		 			display: '发送日期', name: 'send_date', align: 'left', minWidth: '90',
		 		}, { 
		 			display: '处理状态', name: 'rela_state', align: 'left', minWidth: '80',
		 			render: function(rowdata, rowindex, value){
		 				if(value == 1){
		 					return "待处理";	
		 				}else if(value == 2){
		 					return "部分完成";
		 				}else if(value == 3){
		 					return "全部完成";
		 				}
		 			}
		 		} ],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedApplyMainOut.do',
			width: '100%', height: '55%', inWindow: false,
			checkbox: true,rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,
			toolbar: { items: [
				{ text : '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line : true },
				/* { text : '生成出库单（<u>H</u>）', id:'createOutCollect', click: createOutCollect, icon:'settle' },
				{ line : true },  */
				{ text : '整单退回科室（<u>B</u>）', id:'back', click: back, icon:'back' }, 
				{ line : true }, 
				{ text : '关闭行（<u>G</u>）', id : 'closeInv', click : closeInv, icon : 'close'}, 
				{ line : true} 
			]}, 
			onDblClickRow : function (rowdata, rowindex, value){
				changeDept(
					rowdata.dept_id + "," + 
					rowdata.dept_no + "," + 
					rowdata.dept_code + "," + 
					rowdata.dept_name 
				);
			},
			onClickRow : function (rowdata, rowindex, value){
				if(apply_id != rowdata.apply_id){
					gridRowIndex = rowindex;
					gridRowData = rowdata;
					detailGridAllChecked = 2;
					changeDetailGrid(rowdata.apply_id+","+rowdata.store_id);
				}
			},
			onBeforeCheckRow : function(checked, rowdata, rowindex, rowobj){
				if(checked){
					var para = rowdata.store_id+","+rowdata.dept_id;
					if(store_dept != ""){
						if(store_dept != para){
							$.ligerDialog.warn("所选单据响应库房与领用科室需一致");
							return false;
						}
					}else{
						store_dept = para;
					}
				}
				return true;
			},
			onCheckRow : function(checked, rowdata, rowindex, rowobj){
				if(checked){//选中
					applyObj[apply_id] = {};
					if(JSON.stringify(detailGrid.records) != "{}"){
						for (var rowid in detailGrid.records){
							if(apply_id == detailGrid.records[rowid].apply_id){
			                   	detailGrid.select(detailGrid.records[rowid]);
							}
						}
					}
				}else{//取消选中
					delete applyObj[apply_id];
					if(JSON.stringify(detailGrid.records) != "{}"){
						for (var rowid in detailGrid.records){
							if(apply_id == detailGrid.records[rowid].apply_id){
								detailGrid.unselect(detailGrid.records[rowid]); 
							}
						}
					}
					//如果主表数据未选中任何行，则清空判断仓库科室一致的参数'store_dept'
					if(gridManager.getSelectedRows().length == 0){
						store_dept = "";
					}
				}
				//明细grid是否全选(1)或全不选(0)
				detailGridAllChecked = checked ? 1 : 0;
			}, 
			onCheckAllRow : function(checked,element){
				if(checked){
					var data = gridManager.getData();
					$(data).each(function(){
						applyObj[this.apply_id] = {};
					})
				}
			} 
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

    function loadDetailHead(){
    	detailGrid = $("#detailgrid").ligerGrid({
			columns : [{
				display : '药品编码', name : 'inv_code', width : 120, align : 'left',
				totalSummary: {
                    align : 'right',
                    render: function (suminf, column, cell) {
                    	return '<div>合计：</div>';
                    }
                }
			}, {
				display : '药品名称', name : 'inv_name', width : 340, align : 'left'
			}, {
				display : '规格型号', name : 'inv_model', width : 100, align : 'left'
			}, {
				display : '计量单位', name : 'unit_name', width : 80, align : 'left'
			}, {
				display : '生成厂商', name : 'fac_name', width : 200, align : 'left'
			}, {
				display : '请领数量', name : 'app_amount', width : 80, align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, 2, 1);
				},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
			}, {
				display : '已处理数量', name : 'rela_amount', width : 80, align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, 2, 1);
				},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
			}, {
				display : '是否代销药品', name : 'is_com', width : 100, align : 'left',
				render : function(rowdata, rowindex, value) {
					return value == 1 ? '是' : '否';
				}
			}, {
				display : '非代销药品库存量', name : 'common_amount', width : 110, align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, 2, 1);
				},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
			}, {
				display : '非代销药品即时库存量', name : 'imme_amount', width : 110, align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, 2, 1);
				},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
			}, {
				display : '代销药品库存量', name : 'affi_amount', width : 110, align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, 2, 1);
				},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
			}, {
				display : '代销药品即时库存量', name : 'affi_imme_amount', width : 110, align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, 2, 1);
				},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
			}, {
				display : '单价', name : 'price', width : 80, align : 'right',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, '${p08006 }', 1);
				}
			}, {
				display : '是否科室库管理', name : 'is_sec_whg', width : 100, align : 'left',
				render : function(rowdata, rowindex, value) {
					return value ? "是" : "否";
				},
			}, {
				display : '备注', name : 'note', width : 120, align : 'left',
			}, {
				display : '是否关闭', name : 'is_closed', width : 80, align : 'left',
				render : function(rowdata, rowindex, value) {
					return value ? "是" : "否";
				},
			} ],
			dataAction : 'server', dataType : 'server', usePager : false, 
			width : '100%', height : '45%', inWindow: false, heightDiff: 28, 
			url : 'queryMedApplyDetailOut.do?isCheck=false',
			checkbox : true, enabledEdit : false, alternatingRow : true,
			isScroll : true, rownumbers : true, delayLoad : true,//初始化明细数据
			selectRowButtonOnly : true, isChecked: detail_isChecked,
			onBeforeCheckAllRow : detailBeforeCheckRow, 
			onBeforeCheckRow : detailBeforeCheckRow, 
			onSelectRow : function(rowdata, rowindex, rowobj){
				var applyDetailObj; 
				if(applyObj[apply_id]){
					applyDetailObj = applyObj[apply_id];
				}else{
					applyDetailObj = {};
				}
				applyDetailObj[rowdata.detail_id] = rowdata.detail_id;
				applyObj[apply_id] = applyDetailObj;
				if(!grid.isSelected(gridRowData)){
					grid.select(gridRowIndex);
				}
			}, 
			onUnSelectRow : function(rowdata, rowindex, rowobj){
				if(applyObj[apply_id]){
					var applyDetailObj = applyObj[apply_id];
					delete applyDetailObj[rowdata.detail_id];
					if(JSON.stringify(applyDetailObj) == "{}"){
						if(grid.isSelected(gridRowData)){
							grid.unselect(gridRowIndex);
							//如果主表数据未选中任何行，则清空判断仓库科室一致的参数'store_dept'
							if(gridManager.getSelectedRows().length == 0){
								store_dept = "";
							}
						}
					}else{
						applyObj[apply_id] = applyDetailObj;
					}
				}
			}
		});
    }
    
	//是否可以单击复选框选中（含全选）
	function detailBeforeCheckRow(rowdata, rowindex, rowobj){
		var date = grid.getRow(gridRowIndex);
		var para = date.store_id+","+date.dept_id;
		if(store_dept != ""){
			if(store_dept != para){
				$.ligerDialog.warn("所选单据响应库房与领用科室需一致");
				return false;
			}
		}else{
			store_dept = para;
		}
		return true;
	}
    
    function detail_isChecked(rowdata){
    	if(detailGridAllChecked == 0){
    		return false;
    	}else if(detailGridAllChecked == 1){
    		return true;
    	}else if(detailGridAllChecked == 2){
	    	if(applyObj[apply_id]){
		    	var applyDetailObj = applyObj[apply_id];
		    	if(applyDetailObj[rowdata.detail_id]){
		    		return true;
		    	}
	    	}
    	}
    	return false;
    }
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('H', createOutCollect);
		hotkeys('B', back);
	}
    
    //修改
    function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"apply_id="+vo[3] +"&"+ 
			"rela_state="+vo[4] ;
		
		parentFrameUse().parent.$.ligerDialog.open({
			title: '申请单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/storage/out/applycheck/updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: parentFrameUse().window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
    }
    
    function changeDept(obj){		
		var vo = obj.split(",");
		
		liger.get("apply_dept").setValue(vo[0]+","+vo[1]);
		liger.get("apply_dept").setText(vo[2]+" "+vo[3]);
		
		query();
    }
    
    function changeDetailGrid(obj){
    	var vo = obj.split(",");
    	
    	apply_id = vo[0];
    	apply_store_id = vo[1];
    	
    	queryDetail();
    }
    
    //批量生成出库单
	function createOutCollect(){
		if (JSON.stringify(applyObj) == "{}"){
			$.ligerDialog.error('请选择行');
		}else{
			var apply_ids = "";
			var detail_ids = "";
			for (var applyid in applyObj){
				details = applyObj[applyid];
				if(JSON.stringify(details) == "{}"){
					apply_ids = apply_ids + applyid + ",";  
				}else{
					for(var detailid in details){
						detail_ids = detail_ids + detailid + ",";
					}
				}
            }
			$.ligerDialog.confirm('确定生成出库单?', function (yes){
				if(yes){
					var para = {
						apply_ids : apply_ids.substring(0, apply_ids.length - 1), 
						detail_ids : detail_ids.substring(0, detail_ids.length - 1)
					}
					parentFrameUse().openAddByApplyImp(para);
					this_close();
				}
			}); 
		}
	}
	
    //退回
	function back(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var apply_nos = "";
			$(data).each(function (){		
				if(this.rela_state > 1){
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
				$.ligerDialog.error("操作失败！<br>以下单据存在已处理数据不允许退回：<br>"+apply_nos);
				return;
			}
			
			$.ligerDialog.prompt('退回原因', true, function (yes, value){
				if(yes){
					var paras = {
						back_reason : value,
						ids : ParamVo.toString()
					}
					ajaxJsonObjectByUrl("../applycheck/backMedCommonOutApplyCheck.do?",paras,function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	
	//关闭行
	function closeInv(){
		var data = detailGrid.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
	 		/*校验grid---begin-----*/
	 		var targetMap = new HashMap();
			var amount = 0;
	 		$.each(data,function(i, v){
				if(v.app_amount != v.rela_amount){
					amount += v.app_amount - v.rela_amount;
				}
	 		});
	 		if(amount == 0){
	 			$.ligerDialog.warn("所选药品的申请数量已全部处理！");  
				return;
	 		}
	 		var ParamVo =[];
			$(data).each(function (){		
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.apply_id +"@"+ 
					this.detail_id
				) 
			});
            $.ligerDialog.confirm('确定关闭行?', function(yes) {
            	if(yes){
					ajaxJsonObjectByUrl("../applycheck/updateMedCommonOutApplyCheckCloseInv.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData){
			            if(responseData.state=="true"){
			            	detailGridAllChecked = 0;
			            	delete applyObj[apply_id];
							grid.unselect(gridRowIndex);
			            	queryDetail();
			            }
					});
            	}
            });
		}
	}
    
    function loadDict(){
		//字典下拉框
		autocompleteAsync("#store_code", "../../../queryMedStore.do?isCheck=false", "id", "text", true, true, "", true);
		autocomplete("#apply_dept", "../../../queryHosDeptDictByPerm.do?isCheck=false", "id", "text", true, true, {is_last: 1});
        $("#begin_app_date").ligerTextBox({width:100});
        autodate("#begin_app_date", "yyyy-mm-dd", "month_first");
        $("#end_app_date").ligerTextBox({width:100});
        autodate("#end_app_date", "yyyy-mm-dd", "month_last");
        $("#apply_no").ligerTextBox({width : 238});
        
		//格式化按钮
		$("#createOutCollect").ligerButton({ click: createOutCollect, width: 120 });
		$("#close").ligerButton({ click: this_close, width: 90 });
	}  
    
    //关闭页面
	function this_close() {
		frameElement.dialog.close();
	}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
    <div id="divAll" style="width: 100%; height 100%; margin: 0px; padding: 0px;">
	    <div position="top">
		    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
		        <tr>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td"  width="120">
		            	申请日期：
		            </td>
		            <td align="left" class="l-table-edit-td" >
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
		        	<td align="right" class="l-table-edit-td" width="120">
		            	响应库房：
		            </td>
		            <td align="left" class="l-table-edit-td" >
						<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
			        </td>
					<td align="right" class="l-table-edit-td"  width="120">
						申请科室：
					</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="apply_dept" type="text" id="apply_dept" ltype="text" validate="{required:false}" />
		            </td>
		        </tr> 
		        <tr>
					<td align="right" class="l-table-edit-td" >
						单据号：
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="apply_no" type="text" id="apply_no" ltype="text" validate="{required:false,maxlength:100}" />
					</td>
					<td align="right" class="l-table-edit-td" colspan="4" style="padding-right: 80px">
						<input name="is_zero" type="checkbox" id="is_zero" ltype="text" />显示零库存
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<!-- <input name="is_back" type="checkbox" id="is_back" ltype="text" />退库 -->
					</td>
		        </tr>
		    </table>
	    </div>
    	<div position="center">
			<div id="maingrid"></div>
			<div id="detailgrid"></div>
		</div>
		<div position="bottom">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="margin-top: 5px;">
				<tr>
					<td align="center" class="l-table-edit-td">
						<button id="createOutCollect" accessKey="H"><b>生成出库单（<u>H</u>）</b></button> &nbsp;&nbsp;
						<button id="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
