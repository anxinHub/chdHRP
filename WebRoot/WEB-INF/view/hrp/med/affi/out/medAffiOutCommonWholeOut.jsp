<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	
	$(function() {
		
		$("#layout1").ligerLayout({
			topHeight:60,
			centerWidth:900
		});
        loadDict();
        loadForm();
        loadHead(null);	
	});
	
	function loadForm(){    
	    $.metadata.setType("attr", "validate");
	     var v = $("form").validate({
	         errorPlacement: function (lable, element)
	         {
	             if (element.hasClass("l-textarea"))
	             {
	                 element.ligerTip({ content: lable.html(), target: element[0] }); 
	             }
	             else if (element.hasClass("l-text-field"))
	             {
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }
	             else
	             {
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable)
	         {
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function ()
	         {
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
	    //$("form").ligerForm();
	 }
	//查询
	function query() {
		grid.options.parms=[];
		grid.options.newPage=1;
    	//根据表字段进行添加查询条件
		var store_id = liger.get("store_id").getValue();		
    	if(store_id){
    		grid.options.parms.push({name:'store_id',value:store_id.split(",")[0]}); 
    	  	grid.options.parms.push({name:'store_no',value:store_id.split(",")[1]}); 
    	}
	  	grid.options.parms.push({name:'in_no',value:$("#in_no").val()}); 
	  	var dept_id = liger.get("dept_id").getValue();

    	if(dept_id){
    		grid.options.parms.push({name:'dept_id',value:dept_id.split(",")[0]}); 
    	  	grid.options.parms.push({name:'dept_no',value:dept_id.split(",")[1]}); 
    	}
    	
	  	grid.options.parms.push({name:'begin_date',value:$("#begin_date").val()}); 
	  	grid.options.parms.push({name:'end_date',value:$("#end_date").val()}); 
		//加载查询条件
		grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
		
	}
	
	
	function loadHead() {
		var store_id = '${store_id}';
    	var dept_id = '${dept_id}';
    	var para = {
				store_id : store_id.split(",")[0],
				store_no : store_id.split(",")[1]
				//dept_id : dept_id.split(",")[0],
				//dept_no : dept_id.split(",")[1]
		};
    	
		grid = $("#maingrid").ligerGrid({
		       columns: [ { display: '入库单号', name: 'in_no', width: 160},
		                  { display: '摘要', name: 'brief', width: 200},
		                  { display: '供应商', name: 'sup_name', width: 160},
		                  { display: '制单人', name: 'maker_name', width: 80},
		                  { display: '入库日期', name: 'in_date', width: 80 },
		                  { display: '确认人', name: 'confirmer_name', width: 80},
		                  { display: '确认日期', name: 'confirm_date', width: 80}
		                ],
		                dataAction: 'server',dataType: 'server',usePager:true,
		                url:'queryMedAffiOutWholeMain.do?isCheck=false',
						isScroll : true, selectRowButtonOnly:true, isSingleCheck : false, heightDiff: 10, checkbox: true,
	                     width: '100%', height: '80%', detail: { onShowDetail: f_showOutDetail},frozen: false,
	                     toolbar: { items: [
					              	{ text: '查询', id:'query', click: query,icon:'search' },
					                { line:true }
						 ]}
		                
		               });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function f_showOutDetail(row, detailPanel,callback){
		var para = {
			group_id : row.group_id,
			hos_id : row.hos_id,
			copy_code : row.copy_code,
			in_id : row.in_id,
			store_id : liger.get("store_id").getValue().split(",")[0]
		}; 
		 
        var ele = document.createElement('div'); 
        $(detailPanel).append(ele);
        gridDetail = $(ele).css('margin',10).ligerGrid({
             columns:[	 { display: '药品编码', name: 'inv_code',width: 100},
                         { display: '药品名称', name: 'inv_name',width: 180},
                         { display: '规格型号', name: 'inv_model',width: 100},
                         { display: '批号', name: 'batch_no',width: 100 },
                         { display: '批次', name: 'batch_sn',width: 100 },
                         { display: '条形码', name: 'bar_code' ,width: 100},
                         { display: '剩余数量', name: 'imme_amount' ,width: 50},
                         { display: '单价', name: 'price', align: 'right',width:80,
                        	 render : function(rowdata, rowindex, value) {
             					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
             				}
                         }
                     ], 
                   dataAction: 'server',dataType: 'server',usePager : false,
                   parms :para,
			 	   url : 'queryMedAffiOutWholeInv.do?isCheck=false',
				   isScroll : false,  isSingleCheck : false,  checkbox: false,
	               width: '100%', height: '100%',showToggleColBtn: false,onAfterShowData: callback,frozen: false

					
         });  
    }
	//确定生成出库单
	function checkMedOutFifo(){
    	var detailData = gridManager.getCheckedRows();
    	if (detailData.length == 0){
        	$.ligerDialog.error('请选择数据');
        	return false;
    	}
    	
    	var sqlBuf = new StringBuffer();
    	sqlBuf.append(" and (");
    	$.each(detailData, function(d_index, d_content){ 
    		sqlBuf.append("(in_id = "+this.in_id+")") ;
    		if(d_index !=detailData.length-1){
    			sqlBuf.append(" or ");
    		}
		});
    	sqlBuf.append(")");
    	
    	var para={
    			group_id : detailData[0].group_id,
    			hos_id : detailData[0].hos_id,
    			copy_code : detailData[0].copy_code,
    			store_id : '${store_id}'.split(",")[0],
    			sql : sqlBuf.toString()
    	}
		
         ajaxJsonObjectByUrl("queryMedAffiOutDetailByIsDir.do?isCheck=false",para,function (responseData){
         	parent.add_Rows(responseData.Rows);//添加上行数据
         	parent.$("#is_dir").val('1');
         	frameElement.dialog.close();
     	});
	}
	
    //关闭
	function this_close(){
		frameElement.dialog.close();
	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', addNew);
		hotkeys('C', this_close);
	}
	function loadDict(){
        //字典下拉框   	
    	$("#store_id").ligerComboBox({disabled: true});  	       
    	autocomplete("#store_id", "../../queryMedStore.do?isCheck=false", "id", "text", true, true,"",false,'${store_id}','180');    	
    	autocomplete("#dept_id", "../../queryMedDeptDict.do?isCheck=false", "id", "text", true, true,'',false,'${dept_id}','180');    	
    	$("#begin_date").ligerTextBox({width:100});    	
    	$("#end_date").ligerTextBox({width:100});    	
    	$("#in_no").ligerTextBox({width:220});    	
    	$("#store_id").ligerComboBox({disabled: true});    	
    	$("#dept_id").ligerComboBox({disabled: true});
    
     } 
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="layout1">
		<div position="top">
				<form name="form1" method="post"  id="form1" >
			        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入库日期：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_date_start" type="text" id="begin_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left" class="l-table-edit-td">-</td>
			            <td align="left" class="l-table-edit-td"><input name="out_date_end" type="text" id="end_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			
						<td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" /></td>
			            <td align="left"></td>
			            
			           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">定向科室：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" /></td>
			            <td align="left"></td>
			        </tr> 
			         <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入库单号：</td>
			            <td align="left" class="l-table-edit-td" colspan="3"><input name="in_no" type="text" id="in_no" ltype="text" /></td>
			        </tr> 

					</table>
			    </form>
		</div>
		<div position="center" >
			<div id="maingrid"></div>
		</div>
	</div>
</body>
</html>
