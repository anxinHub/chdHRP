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
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var state = null;
	var is_submit = null;
	var is_store = '${p04032}';
	
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
       
	});
	
	//查询
	function query() {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val() });
		grid.options.parms.push({name : 'end_date',value : $("#end_date").val() });
		
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0] });
		grid.options.parms.push({name : 'dept_id',value : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0] });
		grid.options.parms.push({name : 'inv_code',value : $("#inv_code").val() });
		
		if('${p04031}' == 0){
			//是：提交状态; 否：审核状态
			grid.options.parms.push({name : 'state',value : 2 });
		}else{
			grid.options.parms.push({name : 'state',value : 3 });
		}	
		grid.options.parms.push({name : 'is_store',value : is_store });
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		       columns: [ 	
							{display : '需求单号',name : 'req_code',align : 'left',minWidth:'120'},
							{display : '仓库名称',name : 'store_name',align : 'left',minWidth:'120'},
                            {display : '材料编码',name : 'inv_code',align : 'left',minWidth:'120'}, 
                            {display : '材料名称',name : 'inv_name',align : 'left',minWidth:'180'}, 
                            {display : '规格型号',name : 'inv_model',align : 'left',minWidth:'150'}, 
                            {display : '计量单位',name : 'unit_name',align : 'left',minWidth:'100'},
                            {display : '包装单位',name : 'pack_name',align : 'left',minWidth:'100'}, 
                            {display : '转换量',name : 'num_exchange',align : 'right',minWidth:'100',
                            	render:function(rowdata){
				            		return formatNumber(rowdata.num_exchange ==null ? 0 : rowdata.num_exchange,2,1);
				             	}	
                            }, 
                            { display: '需求数量', name: 'amount', align: 'right' ,minWidth:'100',
	   							 render:function(rowdata){
	   				            		return formatNumber(rowdata.amount ==null ? 0 : rowdata.amount,2,1);
	   				             }
	   						 },{ 
	   							 display: '汇总数量', name: 'exec_amount', align: 'right' ,minWidth:'100',
	   							 render:function(rowdata){
	   				            		return formatNumber(rowdata.exec_amount ==null ? 0 : rowdata.exec_amount,2,1);
	   				             },editor : {type : 'number'}
	   						 },
	   						 {display : '需求ID',name : 'req_id',align : 'left',width:'90',hide:true}
		                ],
		                 dataAction: 'server',dataType: 'server',usePager:true,
		                 url:'queryMatStoreByDeptDetail.do?isCheck=false',
		                 width: '100%', height: '100%', checkbox: true, 
		                 rownumbers:false,delayLoad:true,
		                 enabledEdit : true,
				 		 fixedCellHeight:true, 
				 		 onBeforeEdit : f_detail_onBeforeEdit, 
				 		 onBeforeSubmitEdit : f_onBeforeSubmitEdit,
				 		 onAfterEdit : f_onAfterEdit,
		                 selectRowButtonOnly:true, isScroll :true ,
		                 toolbar: { items:[
		                            { text: '查询（<u>Q</u>）', id:'query', click: query ,icon:'search' },
		                            { line:true },
		                            { text: '删除（<u>D</u>）', id:'delete', click: deleteRows ,icon:'delete' },
		                            { line:true },
		                            { text: '生成仓库需求计划（<u>G</u>）', id:'add', click: addNew ,icon:'add' },
		                            { line:true },
		                            { text: '关闭（<u>L</u>）', id:'close', click: this_close ,icon:'close' }
		                   		]},
		                 onDblClickRow : function (rowdata, rowindex, value){
		                	 changeStore(rowdata);
		            	 }, 
		     			onBeforeCheckRow : function(checked, rowdata, rowindex, rowobj){
		    				if(checked){
		    					if(rowdata.store_id ==undefined){
		    						$.ligerDialog.warn("材料【"+rowdata.inv_name+"】没有维护默认库房");
		    						return false;
		    					}else{
		    						liger.get("store_code").setValue(rowdata.store_id+","+rowdata.store_no);
		    						liger.get("store_code").setText(rowdata.store_code+" "+rowdata.store_name);	
		    					}
		    				}
		    				return true;
		    			}
		               });

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function changeStore(rowdata){
		liger.get("store_code").setValue(rowdata.store_id+","+rowdata.store_no);
		liger.get("store_code").setText(rowdata.store_code+" "+rowdata.store_name);	
			
		query();
	}
	
	//编辑之前
	function f_detail_onBeforeEdit(e) {
		detail_id = e.rowindex;
		detailClicked = 0;
		detail_name = e.column.name;		
	}
	
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		if (e.column.name == "exec_amount" && e.value != ""){
			if(e.record.amount < e.value){
				return false;
			} 	
		}
		return true;
	}
	
	//编辑之后动作
	function f_onAfterEdit(e){
		if(e.value != "" && e.value != 0){
			if (e.column.name == "exec_amount"){
				if(e.record.amount < e.value){
					$.ligerDialog.warn("汇总数量不能大于需求数量！");	
				 	return false;
				} 		
			}
		}
		return true;
	}
	
	//删除
	function deleteRows(){
		var json = gridManager.getCheckedRows();
        if (json.length <= 0){
        	$.ligerDialog.error('请选择要删除的行！');
        	return;
        }else{
        	gridManager.deleteSelectedRow();
        }
	}
	
	//获得选中行数
	function getSelectRows() {
		var rows = grid.getCheckedRows();
		return rows;
	}
	
	//确定添加
	function addNew(){
		
		if(!liger.get("store_code").getValue()){
			$.ligerDialog.error('请选择响应库房！');
			return;
		}
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择材料！');
			return;
		}else{
			var detail_rows = new StringBuffer();
			detail_rows.append("[");
			$.each(data, function(index, data){
				if(index != 0){
					detail_rows.append(",");
				}
				//amount = 差量的绝对值; num= 包装差量的绝对值
				detail_rows.append('{"inv_id":').append(data.inv_id).append(',');
				detail_rows.append('"inv_no":').append(data.inv_no).append(','); 
				detail_rows.append('"dept_req_id":').append(data.req_id).append(',');
				detail_rows.append('"dept_detail_id":').append(data.req_detail_id).append(',');
				detail_rows.append('"amount":').append(data.amount).append(',');
				detail_rows.append('"exec_amount":').append(data.exec_amount).append('}');
			});
			detail_rows.append("]");
			
			var formPara = {
				store_id :  liger.get("store_code").getValue().split(",")[0],
				store_no :  liger.get("store_code").getValue().split(",")[1],
				store_text : liger.get("store_code").getText(),
				allData : detail_rows.toString()
			};
			
			ajaxJsonObjectByUrl("queryDeptCollectData.do?isCheck=false", formPara, function(responseData) {
				//console.log(responseData)
				var detailData = responseData;
				var paras ="store_id="+liger.get("store_code").getValue().split(",")[0] + 
					"&store_no="+liger.get("store_code").getValue().split(",")[1] + 
					"&store_text="+liger.get("store_code").getText();	
				
				parent.$.ligerDialog.open({ 
					title: '仓库需求计划添加',
					height: $(window).height(),
					width: $(window).width(),
					url : 'hrp/mat/require/store/plan/matStoreReqPlanByDeptAddPage.do?isCheck=false&'+paras, 
					data : detailData,
					modal:true,showToggle:false,showMax:true ,showMin: false,isResize:true,
					parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
				});
				liger.get("store_code").setValue("");
				liger.get("store_code").setText("");	
				
			});	
			query();
		}
	}
	
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('D', deleteRows);//删除
		hotkeys('N', addNew);//确定添加
		hotkeys('L', this_close);//关闭
	}
	
	
	//字典加载
	function loadDict() {
		autodate("#begin_date", "yyyy-mm-dd", "month_first");
	    autodate("#end_date", "yyyy-mm-dd", "month_last");
	    $("#begin_date").ligerTextBox({width:160});
		$("#end_date").ligerTextBox({width:160});
		
		autocomplete("#store_code", "../../../queryMatStore.do?isCheck=false", "id", "text", true, true,{is_purchase : '1'},false,false,'160',"",200);//仓库
		autocomplete("#dept_code", "../../../queryMatDept.do?isCheck=false", "id", "text", true, true,{is_last : '1'}, false,false,'160',"",200);//科室		
        
		$("#store_code").ligerTextBox({width:160});
		$("#dept_code").ligerTextBox({width:160});
		$("#inv_code").ligerTextBox({width:160});
		
	}
	
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">编制日期：</td>
			<td align="left" class="l-table-edit-td" style="width:160px;">
				<input class="Wdate" name="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" id="begin_date" />
			</td>
			<td align="center" class="l-table-edit-td" style="width: 10px;">至：</td>
			<td align="right" class="l-table-edit-td" style="width:160px;">
				<input class="Wdate" name="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" id="end_date" />
			</td>
			<td align="left"></td>
			
		  	<td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b><font color="red">*</font>响应库房：</b></td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="store_code" type="text" requried="true"  id="store_code"  />
	        </td>
	        <td align="left"></td>
	        
           	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>编制科室:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="dept_code" type="text" id="dept_code" ltype="text" validate="{maxlength:20}" />
            </td>
            <td align="left"></td>
      </tr>
      <tr>      
            <td align="right" class="l-table-edit-td" style="padding-left: 10px;">物资信息:</td>
			<td align="left" class="l-table-edit-td" colspan="3">
				<input name="inv_code" type="text" requried="false" id="inv_code" />
			</td>
			<td align="left"></td>
			
		</tr>
		
	</table>
	<div id="maingrid"></div>	
</body>
</html>
