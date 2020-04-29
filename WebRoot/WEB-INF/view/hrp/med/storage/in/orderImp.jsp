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
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var state = '${p08033}'== 1 ? 2 : 1;
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
			name : 'begin_order_date',
			value : $("#begin_order_date").val()
		});
		grid.options.parms.push({
			name : 'end_order_date',
			value : $("#end_order_date").val()
		}); 
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'sup_no',
			value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1]
		}); 
		grid.options.parms.push({
			name : 'stocker',
			value : liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue()
		}); 
		grid.options.parms.push({
			name : 'order_code',
			value : $("#order_code").val()
		});
		grid.options.parms.push({
			name : 'inv_code',
			value : $("#inv_code").val()
		}); 
		grid.options.parms.push({
			name : 'brif',
			value : $("#brif").val()
		}); 
		grid.options.parms.push({
			name : 'state',
			value : state
		}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '订单号', name: 'order_code', align: 'left',width:'120',
					render : function(rowdata, rowindex, value) {
						return '<a href=javascript:update_open("' 
							+ rowdata.group_id 
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code 
							+ ',' + rowdata.order_id
							+ '")>'+rowdata.order_code+'</a>';
					}
				}, {display: '摘要', name: 'brif', align: 'left',width:'200'
		 		}, {display: '编制日期', name: 'order_date', align: 'left',width:'80'
		 		}, {display: '制单人', name: 'maker_name', align: 'left',width:'80'
		 		}, {display: '供应商', name: 'sup_name', align: 'left',width:'200'
		 		}, {display: '药品编码', name: 'inv_code', align: 'left',width:'150'
		 		}, {display: '药品名称', name: 'inv_name', align: 'left',width:'200'
		 		}, {display: '规格型号', name: 'inv_model', align: 'left',width:'180'
		 		}, {display: '计量单位', name: 'unit_name', align: 'left',width:'80'
		 		}, {display: '单价', name: 'price', align: 'right',width:'80',
		 			render: function (rowdata, rowindex, value) {
						return formatNumber(value, '${p08006 }', 1);
					}
		 		}, {display: '数量', name: 'amount', align: 'right',width:'80'
		 		}, /* {display: '采购单位', name: 'pur_hos_name', align: 'left',width:'150'
		 		}, {display: '收货单位', name: 'take_hos_name', align: 'left',width:'150'
		 		}, {display: '付款单位', name: 'pay_hos_name', align: 'left',width:'150'
		 		}, */  { 
		 			display: '状态', name: 'state', align: 'right',width:'80',
		 			render:function(rowdata){
						 if(rowdata.state == '2'){
						 	return "已审核";
						 }
					 }
		 		} ],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedStorageInOrder.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'query', click: query, icon:'search' },
				{ line:true },
				{ text: '生成入库单（<u>A</u>）', id:'add', click: imp, icon:'add' },
				{ line:true },
				{ text: '关闭（<u>C</u>）', id:'close', click: this_close, icon:'close' }
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('L', this_close);
	}
    
	//修改
	function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"order_id="+vo[3] ;
		$.ligerDialog.open({
			title: '订单修改',
			height: 500,
			width: 1000,
			url: '../../order/init/medOrderInitUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top : 1
		});   
    }
    
    function imp(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			$(data).each(function (){		
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.order_id  +"@"+ 
					this.order_detail_id
				) 
			});
			$.ligerDialog.confirm('确定生成入库单?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("queryMedStorageInOrderDetail.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.Rows.length > 0){
							//订单药品
							parent.add_rows(responseData.Rows);
							
							parent.grid.addRow();
						}
						this_close();
					});
				}
			}); 
		}
    }
    
	function this_close(){
		frameElement.dialog.close();
	}
   
    function loadDict(){
		//字典下拉框
		$("#sup_code").ligerComboBox({width:160,disabled:true,cancelable: false});
        liger.get("sup_code").setValue("${sup_id}");
		liger.get("sup_code").setText("${sup_text}");
		//autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true, "", true);
		autocomplete("#stocker", "../../queryMedStockEmp.do?isCheck=false", "id", "text", true, true);
        $("#begin_order_date").ligerTextBox({width:100});
        autodate("#begin_order_date", "yyyy-mm-dd", "month_first");
        $("#end_order_date").ligerTextBox({width:100});
        autodate("#end_order_date", "yyyy-mm-dd", "month_last");
        $("#order_code").ligerTextBox({width:160});
        $("#inv_code").ligerTextBox({width:160});
        $("#brif").ligerTextBox({width:160});
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
            	订单日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" >
							<input class="Wdate" name="begin_order_date" id="begin_order_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_order_date" id="end_order_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				供货单位：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:false}" />
            </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				采购员：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="stocker" type="text" id="stocker" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr> 
        <tr>
			<td align="right" class="l-table-edit-td"  width="10%">
				订单号：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="order_code" type="text" id="order_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
			<td align="right" class="l-table-edit-td" >
				药品信息：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
			<td align="right" class="l-table-edit-td" >
				摘要：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="brif" type="text" id="brif" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
