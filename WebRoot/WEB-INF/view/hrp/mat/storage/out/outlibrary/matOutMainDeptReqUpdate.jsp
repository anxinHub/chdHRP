<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
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
	var gridManager = null;
	var userUpdateStr;
	
	var dept_no ;
	var dept_id ;
	var acc_year = null;
	var acc_month = null;
	var state = '${matDeptMain.state}';
	var store_id = '${matDeptMain.stock_id}';
	var store_no = '${matDeptMain.stock_no}';
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		$("#dept_code").bind("change",function(){
	    	dept_id = liger.get("dept_code").getValue().split(",")[0];
	    	dept_no = liger.get("dept_code").getValue().split(",")[1];
		}); 
		$("#store_code").bind("change", function () {
			grid.columns[3].editor.grid.url = '../../../queryMatInvListDept.do?isCheck=false&store_id='+liger.get("store_code").getValue().split(",")[0];
		});

        query();
       
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'req_id',value : '${matDeptMain.req_id}' });
		grid.options.parms.push({name : 'req_code',value : '${matDeptMain.req_code}'});
		
		grid.options.parms.push({name : 'dept_id',value : '${matDeptMain.dept_id}'}); 
    	grid.options.parms.push({name : 'dept_no',value : '${matDeptMain.dept_no}'});
    	
    	grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
    	grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]});
    	
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		       columns: [ 
						 { display: '材料编码', name: 'inv_code', align: 'left', width:'120' ,
							 totalSummary: {
			                        type: 'sum',
			                        render: function (suminf, column, cell) {
			                            return '<div>合计</div>';
			                        }
			                    }	  
						 },{ display: '材料名称', name: 'inv_name', align: 'left',width:'150',
						 },{ display: '规格型号', name: 'inv_model', align: 'left',width:'180'
						 },{ display: '计量单位', name: 'unit_name', align: 'left',width:'80' 
						 },{ display: '包装规格',name : 'inv_structure',align : 'left',width:'100' 
						 },{ display: '计划数量', name: 'amount', align: 'right',width:'100',
							 render:function(rowdata){
				            		return formatNumber(rowdata.amount ==null ? 0 : rowdata.amount,2,1);
				             } ,
				             totalSummary: {
			                        type: 'sum',
			                        render: function (suminf, column, cell) {
			                            return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,2,1)+ '</div>';
			                        }
			                    } 
						 },{ display: '单价', name: 'price', align: 'right',width:'100',
								render : function(rowdata, rowindex, value) {
									
									rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
									return value == null ? "" : formatNumber(value, '${p04006 }', 1);
								}
						 },{ display: '金额', name: 'sum_money', align: 'right',width:'100',
							 render:function(rowdata){
				            		return formatNumber(rowdata.sum_money ==null ? 0 : rowdata.sum_money,'${p04005}',1);
				            } ,
				            totalSummary: {
		                        type: 'sum',
		                        render: function (suminf, column, cell) {
		                            return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,'${p04005}',1)+ '</div>';
		                        }
		                    } 
						 },{ display: '供应商', name : 'sup_id', textField : 'sup_name', width : 250, align : 'left',
							 render : function(rowdata, rowindex, value) {	
									if(rowdata.inv_id != undefined){
										inv_id = rowdata.inv_id;
									}
									return rowdata.sup_name;
								}
						 },{ display: '生产厂商', name: 'fac_name', align: 'left',width:'250' 
						 },{ display: '备注', name: 'memo', align: 'left',width:'120',
						 },{ display : '包装单位', name : 'pack_code', textField : 'pack_name', width : 80, align : 'left',
								render : function(rowdata, rowindex, value) {
									return rowdata.pack_name;
								} 
						 },{ display: '转换量', name: 'num_exchange', align: 'right',width:'120',
						 },{ display: '包装数量', name: 'num', align: 'right',width:'100',
						 },{ display: '明细ID', name: 'req_detail_id', align: 'left',width:'250',hide:true 
						 }
		                ],
		                 dataAction: 'server',dataType: 'server',usePager : false,
						 url:'queryMatDeptReqDetail.do?isCheck=false',
		                 checkbox : true,
		                 enabledEdit : false,
						 alternatingRow : true,
						 onLoaded:function(){
					     	this.addRow();
					     },
		                 width: '100%', height: '95%', checkbox: true,rownumbers : true ,delayLoad : true,
		                 isScroll :true ,   selectRowButtonOnly:true,
		                 toolbar: { items: 
		                	 	[	
		      						{text : '关闭',id : 'close',click : this_close ,icon : 'close'}
		                   		]
		                 }
		               });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
    
    function add_rows(data){
    	//先清除数据然后再添加
    	grid.deleteAllRows();
    	grid.addRows(data);
    }
	
	//当单据未提交 默认新增一行
    function is_addRow(){
    	if(state > 1){
	    	return;
    	}
    	setTimeout(function() {
			grid.addRow();
		}, 1000);
    }
	
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('L', this_close);//关闭
	}
	
	//字典加载
	function loadDict() {
		autocomplete("#store_code","../../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {is_read:'1'},false,false,'180');//仓库
		liger.get("store_code").setValue('${matDeptMain.stock_id},${matDeptMain.stock_no}');
		liger.get("store_code").setText('${matDeptMain.store_code} ${matDeptMain.store_name}');
		
		var flag = '${p04032}';
		
		if(flag == 1){
			$("#store_code").ligerComboBox({disabled:true});
			$("#store_code").attr("disabled",true);
			$("#store_code_font").show();
		}else{
			$("#store_code_font").hide();
		}
		
		autocomplete("#dept_code","../../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last : '1',is_write:'1'},false, false,'180');//部门
		liger.get("dept_code").setValue('${matDeptMain.dept_id},${matDeptMain.dept_no}');
		liger.get("dept_code").setText('${matDeptMain.dept_code} ${matDeptMain.dept_name}');
		
		$("#req_code").ligerTextBox({width:180,disabled:true});
        $("#make_date").ligerTextBox({width:180});
        $("#rdate").ligerTextBox({width:180});
        $("#brif").ligerTextBox({width:180});
        $("#other_inv").ligerTextBox({width:680});
		
	}
</script>
</head>

<body >
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;">计划单号：</td>
	        <td align="left" class="l-table-edit-td" >
	       		<input name="req_id" type="hidden"   id="req_id" value="${matDeptMain.req_id}" disabled="disabled"/>
	            <input name="req_code" type="text"   id="req_code" value="${matDeptMain.req_code}" disabled="disabled"/>
	        </td>
	        <td align="left"></td>
	        
	        <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red">*</font>编制日期：</td>
	        <td align="left" class="l-table-edit-td"  >
	            <input class="Wdate" requried="true"  name="make_date" type="text"  value="${matDeptMain.make_date}"
	            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" id="make_date"   />
	        </td>
	        <td align="left"></td>
	        
	        <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red">*</font>编制科室：</td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="dept_code" type="text" requried="true"  id="dept_code"  />
	        </td>
	        <td align="left"></td>
	        
		</tr>
		<tr>
		 	
	        <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><span id="store_code_font" >
	        <font color="red">*</font></span>响应库房：</td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="store_code" type="text" requried="true"  id="store_code" />
	        </td>
	        <td align="left"></td>
	        	
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red">*</font>需求日期：</td>
	        <td align="left" class="l-table-edit-td"  >
	            <input class="Wdate" requried="true"  name="rdate" type="text"  value="${matDeptMain.rdate}"
	            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" id="rdate"   />
	        </td>
	        <td align="left"></td>
	        
	        <td align="right" class="l-table-edit-td"  style="padding-left:10px;">摘要：</td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="brif" type="text" requried="false"  id="brif"  ltype="text" value="${matDeptMain.brif}"/>
	        </td>
	        <td align="left"></td> 
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;">其他需求物资：</td>
	        <td align="left" class="l-table-edit-td"  colspan="8">
	            <input name="other_inv" type="text"  id="other_inv"  value="${matDeptMain.other_inv}"  style="width:680px;"/>
	        </td>
	       <td align="left"></td>     
	        
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;"></td>
			<td align="left" class="l-table-edit-td"  colspan="8">
	            <font color="red">物资单价300元以下或总价1000元以下的个性化临购物资且在系统中找不到物资名称的情况请在此处填写</font>
	        </td>
	       <td align="left"></td>
		</tr>
		<tr>
			<input name="thisDateB" type="hidden"   id="thisDateB" />
			<input name="thisDateE" type="hidden"   id="thisDateE" />
			<input name="lastDateB" type="hidden"   id="lastDateB" />
			<input name="lastDateE" type="hidden"   id="lastDateE" />
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
