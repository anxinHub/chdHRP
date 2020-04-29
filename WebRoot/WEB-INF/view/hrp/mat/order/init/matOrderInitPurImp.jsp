<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <!-- jsp:include page="${path}/inc.jsp"/-->
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
    <script type="text/javascript">
	var grid;
	var detailGrid;
	var gridManager = null;
	var userUpdateStr;
	var pur_id;
	var purObj = {};
	var gridRowIndex;
	var gridRowData;
	var detailGridAllChecked;//0:全不选,1:全选,2:根据条件判断
	$(function (){
    	$("#divAll").ligerLayout({
        	topHeight:90,
        	centerBottomHeight:80
		});
    	
		loadDict();//加载下拉框
		loadHead();
		loadHotkeys();
		loadDetailHead(null);
		query();
	});  
	
	//初始化全局变量
    function initPara(){
        pur_id = "";
    	purObj = {};
		detailGrid.deleteAllRows();    
    }
	
    function loadDict(){
    	//字典下拉框
    	autocomplete("#store_id","../../queryMatStore.do?isCheck=false","id","text",true,true);//仓库信息
    	autocomplete("#pur_hos_id", "../../../sys/queryHosInfoDictPerm.do?isCheck=false", "id", "text", true, true);//采购单位
		autocomplete("#req_hos_id", "../../../sys/queryHosInfoDictPerm.do?isCheck=false", "id", "text", true, true);//请购单位
		autocomplete("#pay_hos_id", "../../../sys/queryHosInfoDictPerm.do?isCheck=false", "id", "text", true, true);//付款单位 
		
		$("#begin_pur_date").ligerTextBox({width:100});
        autodate("#begin_pur_date", "yyyy-mm-dd", "month_first");
        $("#end_pur_date").ligerTextBox({width:100});
        autodate("#end_pur_date", "yyyy-mm-dd", "month_last");
        $("#brif").ligerTextBox({width : 238});
        
        $("#query").ligerButton({click: query, width:90});
		$("#createOrder").ligerButton({click: createOrder, width:90});
		$("#close").ligerButton({click: this_close, width:90});
	} 
     
	function query(){
		grid.options.parms = [];
		grid.options.newPage = 1;
 		
		grid.options.parms.push({
			name : 'begin_pur_date',
			value : $("#begin_pur_date").val()
		});
		grid.options.parms.push({
			name : 'end_pur_date',
			value : $("#end_pur_date").val()
		}); 
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'pur_hos_id',
			value : liger.get("pur_hos_id").getValue() == null ? "" : liger.get("pur_hos_id").getValue()
		}); 
		grid.options.parms.push({
			name : 'req_hos_id',
			value : liger.get("req_hos_id").getValue() == null ? "" : liger.get("req_hos_id").getValue()
		});
		grid.options.parms.push({
			name : 'pay_hos_id',
			value : liger.get("pay_hos_id").getValue() == null ? "" : liger.get("pay_hos_id").getValue()
		});
		grid.options.parms.push({
			name : 'brif',
			value : $("#brif").val()
		}); 
		grid.options.parms.push({
			name : 'is_dir',
			value : $("#is_dir").prop("checked") ? 1 : 0
		}); 
    	
		//加载查询条件
		grid.loadData(grid.where);
    	initPara();
	}
    
    function changeDetailGrid(obj){
    	
    	pur_id = obj;
    	
    	queryDetail();
    }
	
    function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '采购计划编号',name : 'pur_code',width : 120,align : 'left',
				render : function(rowdata, rowindex, value) {
					return '<a href=javascript:update_open("' 
						+ rowdata.group_id 
						+ ',' + rowdata.hos_id 
						+ ',' + rowdata.copy_code 
						+ ',' + rowdata.pur_id
						+ '")>'+rowdata.pur_code+'</a>';
					}
			}, {
				display : '需求库房', name : 'store_name', align : 'left',width : 150
			}, {
				display : '计划类型', name : 'pur_type', width : 120, align : 'left',
				render:function(rowdata,rowindex,value){
					if(rowdata.pur_type == 2){
						return "统购计划";
					}else{
						return "自购计划";
					}
				}
			}, {
				display : '摘要', name : 'brif',  width : 150,align : 'left'
			}, {
				display : '采购单位', name : 'pur_hos_name', align : 'left',width : 120
			}, {
				display : '请购单位', name : 'req_hos_name',  align : 'left',width : 120	
			}, {
				display : '付款单位', name : 'pay_hos_name',     algn:'left',width : 120	
			}, {
				display : '制单人', name : 'maker',  align : 'left',width : 100
			}, {
				display : '编制日期', name : 'make_date', align : 'left',width : 100
			}, {
				display : '审核人', name : 'checker',  align : 'left'	,width : 100
			}, {
				display : '审核日期', name : 'check_date',  algn:'left',width : 100
			}, {
				display : '状态', name : 'state',  align : 'left',width : 100,
				render:function(rowdata,rowindex,value){
					if(rowdata.state == 2){
						return "已审核"
					}
				}
			}, {
				display : '定向', name : 'is_dir',  algn:'left',width : 100,
				render:function(rowdata){
					if(rowdata.is_dir=='0'){
						return '否';
					}else if(rowdata.is_dir=='1'){
						return '是';
					}	
				}   
			} ],
			dataAction: 'server', dataType: 'server', usePager:true, url:'queryMatPurMainForOrder.do?isCheck=false', 
			width: '100%', height: '55%', inWindow: false, 
			checkbox: true, rownumbers : false, isScroll:true, 
			delayLoad: true,//初始化不加载，默认false 
			selectRowButtonOnly : true, 
			toolbar: { items: [
				{ text : '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line : true },
				{ text : '关闭行（<u>G</u>）', id : 'closeRow', click : closeRow, icon : 'close'}, 
				{ line : true} 
			] }, 
			onClickRow : function (rowdata, rowindex, value){
				if(pur_id != rowdata.pur_id){
					gridRowIndex = rowindex;
					gridRowData = rowdata;
					detailGridAllChecked = 2;
					changeDetailGrid(rowdata.pur_id);
				}
			},
			onCheckRow : function(checked, rowdata, rowindex, rowobj){
				if(checked){//选中
					purObj[pur_id] = {};
					if(JSON.stringify(detailGrid.records) != "{}"){
						for (var rowid in detailGrid.records){
							if(pur_id == detailGrid.records[rowid].pur_id){
			                   	detailGrid.select(detailGrid.records[rowid]);
							}
						}
					}
				}else{//取消选中
					delete purObj[pur_id];
					if(JSON.stringify(detailGrid.records) != "{}"){
						for (var rowid in detailGrid.records){
							if(pur_id == detailGrid.records[rowid].pur_id){
								detailGrid.unselect(detailGrid.records[rowid]); 
							}
						}
					}
				}
				//明细grid是否全选(1)或全不选(0)
				detailGridAllChecked = checked ? 1 : 0;
			}, 
			onCheckAllRow : function(checked,element){
				if(checked){
					var data = gridManager.getData();
					$(data).each(function(){
						purObj[this.pur_id] = {};
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
 			name : 'pur_id',
 			value : pur_id
 		});
        
    	//加载查询条件
    	detailGrid.loadData(detailGrid.where);
	}

	function loadDetailHead(){
    	detailGrid = $("#detailgrid").ligerGrid({
			columns : [ {
				display : '材料编码', name : 'inv_code', width : 120, align : 'left',
				totalSummary: {
                    align : 'right',
                    render: function (suminf, column, cell) {
                    	return '<div>合计：</div>';
                    }
                }
			}, {
				display : '材料名称', name : 'inv_name', width : 300, align : 'left'
			}, {
				display : '规格型号', name : 'inv_model', width : 100, align : 'left'
			}, {
				display : '计量单位', name : 'unit_name', width : 80, align : 'left'
			}, {
				display : '供应商', name : 'sup_id', textField : 'sup_name', width : 200, align : 'left'
			}, {
				display : '采购数量', name : 'amount', width : 80, align : 'left',
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
				display : '单价', name : 'price', width : 120, align : 'right',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, '${p04006 }', 1);
				}
			}, {
				display : '金额', name : 'amount_money', width : 120, align : 'right',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, '${p04005 }', 1);
				},
				totalSummary: {
					align: 'right',
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005 }', 1) + '</div>';
				   }
				}
			}, {
				display : '申请科室', name : 'app_dept_id', textField : 'app_dept_name', width : 150, align : 'left',
			}, {
				display : '申请日期', name : 'app_date', width : 110, align : 'left',
			}, {
				display : '备注', name : 'memo', width : 120, align : 'left',
			} ],
			dataAction : 'server', dataType : 'server', usePager : false, 
			width : '100%', height : '45%', inWindow: false, heightDiff: 28, 
			url : 'queryMatPurDetailForOrder.do?isCheck=false',
			checkbox : true, enabledEdit : false, alternatingRow : true,
			isScroll : true, rownumbers : true, delayLoad : true,//初始化明细数据
			selectRowButtonOnly : true, isChecked: detail_isChecked,
			onSelectRow : function(rowdata, rowindex, rowobj){
				var purDetailObj; 
				if(purObj[pur_id]){
					purDetailObj = purObj[pur_id];
				}else{
					purDetailObj = {};
				}
				purDetailObj[rowdata.pur_detail_id] = rowdata.pur_detail_id;
				purObj[pur_id] = purDetailObj;
				if(!grid.isSelected(gridRowData)){
					grid.select(gridRowIndex);
				}
			}, 
			onUnSelectRow : function(rowdata, rowindex, rowobj){
				if(purObj[pur_id]){
					var purDetailObj = purObj[pur_id];
					delete purDetailObj[rowdata.pur_detail_id];
					if(JSON.stringify(purDetailObj) == "{}"){
						if(grid.isSelected(gridRowData)){
							grid.unselect(gridRowIndex);
						}
					}else{
						purObj[pur_id] = purDetailObj;
					}
				}
			}
		});
    }
    
    function detail_isChecked(rowdata){
    	if(detailGridAllChecked == 0){
    		return false;
    	}else if(detailGridAllChecked == 1){
    		return true;
    	}else if(detailGridAllChecked == 2){
	    	if(purObj[pur_id]){
		    	var purDetailObj = purObj[pur_id];
		    	if(purDetailObj[rowdata.pur_detail_id]){
		    		return true;
		    	}
	    	}
    	}
    	return false;
    }
    
  	//查看订单明细数据
	function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"pur_id="+vo[3] ;
		$.ligerDialog.open({
			title: '查看订单明细',
			height: 450,
			width: 950,
			url: 'matOrderInitGenPurDetailPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top : 1
		});   
    }
  	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('D', closeRow);
	}
	
	//关闭行
	function closeRow(){
		var data = detailGrid.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
	 		var ParamVo =[];
			$(data).each(function (){		
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.pur_id +"@"+ 
					this.pur_detail_id
				) 
			});
            $.ligerDialog.confirm('确定关闭行?', function(yes) {
            	if(yes){
					ajaxJsonObjectByUrl("updateMatPurDetailByOrderClose.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData){
			            if(responseData.state=="true"){
			            	detailGridAllChecked = 0;
			            	delete purObj[pur_id];
							grid.unselect(gridRowIndex);
			            	queryDetail();
			            }
					});
            	}
            });
		}
	}
	
  	//订单生成
  	function createOrder(){
  		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择采购订单！');
			return;
		}else{
			var pur_ids = "";
			var pur_detail_ids = "";
			for (var purid in purObj){
				details = purObj[purid];
				if(JSON.stringify(details) == "{}"){
					pur_ids = pur_ids + purid + ",";  
				}else{
					for(var detailid in details){
						pur_detail_ids = pur_detail_ids + detailid + ",";
					}
				}
            }
			$.ligerDialog.confirm('确定生成订单?', function (yes){
				if(yes){
					var para = '&pur_ids=' + pur_ids.substring(0, pur_ids.length - 1)
						+ '&pur_detail_ids=' + pur_detail_ids.substring(0, pur_detail_ids.length - 1);
					ajaxJsonObjectByUrl("addMatOrderByPurImp.do?isCheck=false", para, function(responseData){
			            if(responseData.state=="true"){
			            	query();
			            	parentFrameUse().query();
			            }
					});
				}
			}); 
		}
  	}
  	
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	
    </script>
  
</head>
  
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="divAll" style="width: 100%; height 100%; margin: 0px; padding: 0px;">
	    <div position="top">
		    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
		        <tr>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td"  width="120">
		            	日期范围：
		            </td>
		            <td align="left" class="l-table-edit-td" >
						<table>
							<tr>
								<td align="left" >
									<input class="Wdate" name="begin_pur_date" id="begin_pur_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
								</td>
								<td align="right" class="l-table-edit-td"  >
									至：
								</td>
								<td align="left" class="l-table-edit-td">
									<input class="Wdate" name="end_pur_date" id="end_pur_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
								</td>
		            		</tr>
						</table>
			        </td>
			        
		            <td align="right" class="l-table-edit-td">需求库房：</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="store_id" type="text" id="store_id" required="false" ltype="text" validate="{required:true}" />
		            </td>
		            
		            <td align="right" class="l-table-edit-td">采购单位：</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="pur_hos_id" type="text" id="pur_hos_id" required="false" ltype="text" validate="{required:true}" />
		            </td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td" >摘要：</td>
		            <td align="left" class="l-table-edit-td" >
						<input name="brif" type="text" requried="false" id="brif" />
					</td>
		            
		            <td align="right" class="l-table-edit-td">请购单位：</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="req_hos_id" type="text" id="req_hos_id" required="false" ltype="text" validate="{required:true}" />
		            </td>
		            
		         	<td align="right" class="l-table-edit-td">付款单位：</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="pay_hos_id" type="text" id="pay_hos_id" required="false" ltype="text" validate="{required:true}" />
		            </td>
				</tr>
				<tr>
		            <td></td>
					<td align="left" class="l-table-edit-td" >
						<input name="is_dir" type="checkbox" id="is_dir" ltype="text" />&nbsp;&nbsp;是否定向
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
						<button id="createOrder" accessKey="H"><b>生成（<u>H</u>）</b></button> &nbsp;&nbsp;
						<button id="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>