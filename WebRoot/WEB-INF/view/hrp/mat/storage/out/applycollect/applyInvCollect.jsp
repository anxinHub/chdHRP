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
    var detailGrid;
    var gridManager = null;
    var p_begin_date, p_end_date, p_store_id, p_store_no, p_inv_code, p_inv_id, p_dept_id, p_is_com;
	var applyObj = {};
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
        loadDetailHead(null);
		//query();
    });
    //初始化全局变量
    function initPara(){
        inv_id = "";
		p_inv_id = ""; 
		applyObj = {};
        if(detailGrid.getData().length > 0){
			detailGrid.deleteAllRows();    
		}
    }
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        //记录变量保持主从表查询条件一致
		p_begin_date = $("#begin_app_date").val();
		p_end_date = $("#end_app_date").val();
		p_dept_id = liger.get("apply_dept").getValue() == null ? "" : liger.get("apply_dept").getValue().split(",")[0];
		p_store_id = liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0];
		p_store_no = liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1];
		p_inv_code = $("#inv_code").val();
    	p_is_com = $("#is_com input:radio:checked").val();
		grid.options.parms.push({ name : 'begin_app_date', value : p_begin_date});
		grid.options.parms.push({ name : 'end_app_date', value : p_end_date}); 
		grid.options.parms.push({ name : 'dept_id', value : p_dept_id}); 
		grid.options.parms.push({ name : 'store_id', value : p_store_id});
		grid.options.parms.push({ name : 'inv_code', value : p_inv_code}); 
		grid.options.parms.push({ name : 'is_com', value : p_is_com}); 
		grid.options.parms.push({
			name : 'mat_type_code',//物资类别
			value : liger.get("mat_type_code").getText() == null ? "" :liger.get("mat_type_code").getText().split(" ")[0]
    	});
    	//加载查询条件
    	grid.loadData(grid.where);
    	initPara();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
			     {
					display: '交易编码', name: 'bid_code', align: 'left', width: '100',
				},{
					display: '材料编码', name: 'inv_code', align: 'left', width: '100',
				}, { 
		 			display: '材料名称', name: 'inv_name', align: 'left', width: '200',
				}, { 
		 			display: '单价', name: 'price', align: 'left', width: '100',
				},   { 
		 			display: '物资类别编码', name: 'mat_type_code', align: 'left', width: '100',
				},   { 
		 			display: '物资类别', name: 'mat_type_name', align: 'left', width: '100',
				},  { 
		 			display: '库房', name: 'store_name', align: 'left', width: '100',
				}, {
					display : '规格型号', name : 'inv_model', width : '100', align : 'left'
				}, {
					display : '计量单位', name : 'unit_name', width : '60', align : 'left'
				}, {
					display : '生成厂商', name : 'fac_name', width : '200', align : 'left'
		 		}, { 
					display : '供应商', name : 'sup_name', width : '200', align : 'left'
		 		}, { 
		 			display: '申请数量', name: 'app_amount', align: 'right', width: '90',
		 		}, { 
		 			display: '库存数量', name: 'cur_amount', align: 'right', width: '90',
		 		}, { 
		 			display: '及时库存', name: 'imme_amount', align: 'right', width: '90',
		 		}, { 
		 			display: '已采购数量', name: 'pur_amount', align: 'right', width: '90',
		 		}, { 
		 			display: '未采购数量', name: 'not_pur_amount', align: 'right', width: '90',
		 		} ],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatCommonOutApplyInvCollect.do?isCheck=false',
			width: '100%', height: '55%', inWindow: false,
			checkbox: true,rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,
			onDblClickRow : function (rowdata, rowindex, value){
				
			},
			onClickRow : function (rowdata, rowindex, value){
				if(p_inv_id != rowdata.inv_id){
					gridRowIndex = rowindex;
					gridRowData = rowdata;
					detailGridAllChecked = 2;
					p_inv_id = rowdata.inv_id;
					queryDetail();
				}
			},
			onCheckRow : function(checked, rowdata, rowindex, rowobj){
				if(checked){//选中
					applyObj[p_inv_id] = {};
					if(JSON.stringify(detailGrid.records) != "{}"){
						for (var rowid in detailGrid.records){
							if(p_inv_id == detailGrid.records[rowid].inv_id){
			                   	detailGrid.select(detailGrid.records[rowid]);
							}
						}
					}
				}else{//取消选中
					delete applyObj[p_inv_id];
					if(JSON.stringify(detailGrid.records) != "{}"){
						for (var rowid in detailGrid.records){
							if(p_inv_id == detailGrid.records[rowid].inv_id){
								detailGrid.unselect(detailGrid.records[rowid]); 
							}
						}
					}
					//如果主表数据未选中任何行，则清空判断仓库科室一致的参数'store_dept'
					/* if(gridManager.getSelectedRows().length == 0){
						store_dept = "";
					} */
				}
				//明细grid是否全选(1)或全不选(0)
				detailGridAllChecked = checked ? 1 : 0;
			}, 
			onCheckAllRow : function(checked,element){
				if(checked){
					var data = gridManager.getData();
					$(data).each(function(){
						applyObj[this.inv_id] = {};
					})
					if(JSON.stringify(detailGrid.records) != "{}"){
						for (var rowid in detailGrid.records){
							detailGrid.select(detailGrid.records[rowid]);
						}
					}
				}else{
					var data = gridManager.getData();
					$(data).each(function(){
						delete applyObj[this.inv_id];
					})
					if(JSON.stringify(detailGrid.records) != "{}"){
						for (var rowid in detailGrid.records){
							detailGrid.unselect(detailGrid.records[rowid]); 
						}
					}
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
		detailGrid.options.parms.push({ name : 'begin_app_date', value : p_begin_date});
		detailGrid.options.parms.push({ name : 'end_app_date', value : p_end_date}); 
		detailGrid.options.parms.push({ name : 'dept_id', value : p_dept_id}); 
		detailGrid.options.parms.push({ name : 'store_id', value : p_store_id});
		detailGrid.options.parms.push({ name : 'inv_code', value : p_inv_code}); 
 		detailGrid.options.parms.push({name : 'inv_id', value : p_inv_id});
    	//加载查询条件
    	detailGrid.loadData(detailGrid.where);
     }

    function loadDetailHead(){
    	detailGrid = $("#detailgrid").ligerGrid({
			columns : [{
				display : '科室编码', name : 'dept_code', width : 100, align : 'left',
			}, {
				display : '科室名称', name : 'dept_name', width : 200, align : 'left'
			}, {
				display : '申请单号', name : 'apply_no', width : 110, align : 'left',
				render : function(rowdata, rowindex, value) {
					return '<a href=javascript:openUpdate("' 
						+ rowdata.group_id 
						+ ',' + rowdata.hos_id 
						+ ',' + rowdata.copy_code 
						+ ',' + rowdata.apply_id
						+ '")>'+rowdata.apply_no+'</a>';
				}
			}, {
				display : '申请日期', name : 'app_date', width : 130, align : 'left'
			}, {
	 			display: '申领人', name: 'emp_name', align: 'left', width: 80,
	 		} ,{ 
				display : '请领数量', name : 'app_amount', width : 90, align : 'right',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, 2, 1);
				}
			}, {
	 			display : '出库数量', name : 'out_amount', width : 90,  align : 'right',
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, 2, 1);
				},
	 		},{ 
	 			display : '采购数量', name : 'pur_amount', width : 90,  align : 'right',
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, 2, 1);
				},
	 		},{ 
	 			display : '未采购数量', name : 'not_pur_amount', width : 90,  align : 'right',
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, 2, 1);
				},
	 		},{ 
	 			display : '库存数量', name : 'cur_amount', width : 90,  align : 'right',
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, 2, 1);
				},
	 		},{ 
	 			display : '即时库存', name : 'imme_amount', width : 90,  align : 'right',
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, 2, 1);
				},
	 		},{ 
	 			display: '摘要', name: 'brief', align: 'left', width: 200,
	 		},{
				display : '是否代销材料', name : 'is_com', width : 120, align : 'left',
				render : function(rowdata, rowindex, value) {
					return value == 1 ? '是' : '否';
				}
			} ],
			dataAction : 'server', dataType : 'server', usePager : false, 
			width : '100%', height : '45%', inWindow: false, heightDiff: 28, 
			url : 'queryMatCommonOutApplyInvCollectDetail.do?isCheck=false',
			checkbox : true, enabledEdit : false, alternatingRow : true,
			isScroll : true, rownumbers : true, delayLoad : true,//初始化明细数据
			selectRowButtonOnly : true, isChecked: detail_isChecked,
			toolbar: { items: [
			   	{ text: '关闭材料', id:'closeInv', click: closeInv, icon:'close' },
   				{ line : true }
   			]}, 
			onBeforeCheckAllRow : detailBeforeCheckRow, 
			onBeforeCheckRow : detailBeforeCheckRow, 
			onSelectRow : function(rowdata, rowindex, rowobj){
				var applyDetailObj; 
				if(applyObj[p_inv_id]){
					applyDetailObj = applyObj[p_inv_id];
				}else{
					applyDetailObj = {};
				}
				applyDetailObj[rowdata.detail_id] = rowdata.detail_id;
				applyObj[p_inv_id] = applyDetailObj;
				if(!grid.isSelected(gridRowData)){
					grid.select(gridRowIndex);
				}
			}, 
			onUnSelectRow : function(rowdata, rowindex, rowobj){
				if(applyObj[p_inv_id]){
					var applyDetailObj = applyObj[p_inv_id];
					delete applyDetailObj[rowdata.detail_id];
					if(JSON.stringify(applyDetailObj) == "{}"){
						if(grid.isSelected(gridRowData)){
							grid.unselect(gridRowIndex);
						}
					}else{
						applyObj[p_inv_id] = applyDetailObj;
					}
				}
			}
		});
    }
    
	//是否可以单击复选框选中（含全选）
	function detailBeforeCheckRow(rowdata, rowindex, rowobj){
		return true;
	}
    
    function detail_isChecked(rowdata){
    	if(detailGridAllChecked == 0){
    		return false;
    	}else if(detailGridAllChecked == 1){
    		return true;
    	}else if(detailGridAllChecked == 2){
	    	if(applyObj[p_inv_id]){
		    	var applyDetailObj = applyObj[p_inv_id];
		    	if(applyDetailObj[rowdata.detail_id]){
		    		return true;
		    	}
	    	}
    	}
    	return false;
    }
    
    //修改
    function openUpdate(obj){		
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
			url: 'hrp/mat/storage/out/applycheck/updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
    }
	
	//关闭材料
	function closeInv(){
		var data = detailGrid.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
	 		/*校验grid---begin-----*/
	 		var targetMap = new HashMap();
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
			$.ligerDialog.confirm('确定要关闭该科室的材料？', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("../applycheck/updateMatCommonOutApplyCheckCloseInv.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData){
			            if(responseData.state=="true"){
			            	query();
			            }
					});
				}
			});
		}
	}
	
	//汇总生成采购计划
	function batchPur(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var inv_ids = "";
			var detail_ids = "";
			for (var inv_id in applyObj){
				details = applyObj[inv_id];
				if(JSON.stringify(details) == "{}"){
					inv_ids = inv_ids + inv_id + ",";  
				}else{
					for(var detailid in details){
						detail_ids = detail_ids + detailid + ",";
					}
				}
            }
			var paras = "&is_invCollect=1&begin_app_date=" + p_begin_date + 
					"&end_app_date=" + p_end_date + "&store_id=" + p_store_id + 
					"&store_no=" + p_store_no + "&dept_id=" + p_dept_id + 
					"&inv_ids=" + inv_ids.substring(0, inv_ids.length - 1) + 
					"&detail_ids=" + detail_ids.substring(0, detail_ids.length - 1);
			$.ligerDialog.confirm('确定汇总生成采购计划？', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("addMatPurByApplyInvCollect.do?isCheck=false", paras, function(responseData){
			            if(responseData.state=="true"){
							query();
							queryDetail();
			            }
					});
				}
			});	
		}
	}
    
    function loadDict(){
		//字典下拉框
		autocompleteAsync("#store_code", "../../../queryMatStore.do?isCheck=false", "id", "text", true, true, "", true);
		autocomplete("#apply_dept", "../../../queryHosDeptDictByPerm.do?isCheck=false", "id", "text", true, true, {is_last: 1});
        $("#begin_app_date").ligerTextBox({width:100});
        autodate("#begin_app_date", "yyyy-mm-dd", "before_month");
        $("#end_app_date").ligerTextBox({width:100});
        autodate("#end_app_date", "yyyy-mm-dd", "new");
        $("#inv_code").ligerTextBox({width : 238});
    	autocompleteAsync("#mat_type_code", "../../../queryPermMatTypeDict.do?isCheck=false", "id", "text", true, true);
        $("#is_com input:radio").ligerRadio();
        
		//格式化按钮
		$("#query").ligerButton({ click: query, width: 90 }); 
		$("#batchPur").ligerButton({ click: batchPur, width: 120 });
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
						材料信息：
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
					</td>
					    <td align="right" class="l-table-edit-td"  width="10%">
	                		物资类别： 
	                	</td>
	                    <td align="left" class="l-table-edit-td" width="20%">
	                    	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:200}" />
	                    </td>
		        	<td align="right" class="l-table-edit-td" >
						处理类别：
					</td>
					<td id="is_com" align="left" class="l-table-edit-td">
						<input type="radio" name="is_com" value="0" checked="checked" />常备
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    			<input type="radio" name="is_com" value="1" />代销
					</td>
					<td align="right" colspan="2" class="l-table-edit-td">
						<button id="query" accessKey="Q"><b>查询（<u>Q</u>）</b></button>&nbsp;&nbsp;&nbsp;&nbsp;
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
						<button id="batchPur" accessKey="S"><b>生成请购单（<u>S</u>）</b></button> &nbsp;&nbsp;
						<button id="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
