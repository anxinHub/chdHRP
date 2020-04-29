<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
     var grid;     
     var gridManager = null;     
     var clicked = 0;     
     var gridFifo = null;     
     var dialog = frameElement.dialog;     
     $(function (){    	 
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
	         errorPlacement: function (lable, element){
	             if (element.hasClass("l-textarea")){
	                 element.ligerTip({ content: lable.html(), target: element[0] }); 
	             }else if (element.hasClass("l-text-field")){
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }else{
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable){
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function (){
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
    	//$("form").ligerForm();
 	}       
   
    function checkMedOutFifo(){	
    	//获取明细数据
    	var detail_history_data = grid.getCheckedRows();
    	parent.grid.addRows(detail_history_data);//添加上行数据    	
    	parent.$("#is_dir").val('0');
    	dialog.close();
   	}
    
  	//字典下拉框
    function loadDict(){
        
        autocomplete("#store_id", "../../queryMedStore.do?isCheck=false", "id", "text", true, true,"",false,'${store_id}','180');	
    	autocomplete("#inv_id", "../../queryMedInvDict.do?isCheck=false", "id", "text", true, true,"",false,false,'180');
    	autocomplete("#dept_id", "../../queryMedDeptDict.do?isCheck=false", "id", "text", true, true,"",false,'${dept_id}','180');
    	   	
    	$("#out_date_start").ligerTextBox({width:80});     	
    	$("#out_date_end").ligerTextBox({width:80});     	
    	$("#store_id").ligerComboBox({disabled: true});    	
    	$("#dept_id").ligerComboBox({disabled: true});
    
     } 
    
    //查询
    function  query(){
    	grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        	
        var store_id = liger.get("store_id").getValue();
        if(store_id){
        	grid.options.parms.push({name:'store_id',value:store_id.split(",")[0]}); 
        	grid.options.parms.push({name:'store_no',value:store_id.split(",")[1]}); 
        }

    	var inv_id =  liger.get("inv_id").getValue();
    	if(inv_id){
        	grid.options.parms.push({name:'inv_id',value:inv_id.split(",")[0]}); 
        	grid.options.parms.push({name:'inv_no',value:inv_id.split(",")[1]}); 
    	}

    	var dept_id = liger.get("dept_id").getValue();
    	if(dept_id){
        	grid.options.parms.push({name:'dept_id',value:dept_id.split(",")[0]}); 
        	grid.options.parms.push({name:'dept_no',value:dept_id.split(",")[1]}); 
    	}

    	grid.options.parms.push({name:'out_date_start',value:$("#out_date_start").val()}); 
    	grid.options.parms.push({name:'out_date_end',value:$("#out_date_end").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
    }
  
    function loadHead(){
    	var store_id = '${store_id}';
    	var dept_id = '${dept_id}';
    	var para = {
			store_id : store_id.split(",")[0],
			dept_id : dept_id.split(",")[0]
		};
    	grid = $("#maingrid").ligerGrid({
           columns: [
				{ display: '药品编码', name: 'inv_code', minWidth: 100},
                { display: '药品名称', name: 'inv_name', minWidth: 140},
                { display: '规格型号', name: 'inv_model', minWidth: 160},
                { display: '计量单位', name: 'unit_name', minWidth: 50},
                { display: '单价', name: 'price', align: 'right',width:80,
               	 render : function(rowdata, rowindex, value) {
    					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
    				}
                },
                { display: '上期耗用量', name: 'amount', minWidth: 70},
                { display: '批号', name: 'batch_no', minWidth: 50},
                { display: '条形码', name: 'bar_code', minWidth: 50},
                { display: '库存', name: 'cur_amount', minWidth: 50},
                { display: '即时库存', name: 'imme_amount', minWidth: 50,
                  	 render: function (rowdata, rowindex, value){
                		 if(typeof(rowdata.imme_amount) == "undefined"){
                  			 return "未出库";
                  		 }else{
                  			 return rowdata.imme_amount;
                  		 }
    		           } },
    		    { display: '有效日期', name: 'inva_date', minWidth: 50},
				],
	 			dataAction : 'server',dataType : 'server',
	 			url : 'queryMedAffiOutDetailHistory.do?isCheck=false',parms :para,
	 			usePager : false,width : '100%',height : '100%',fixedCellHeight:true,delayLoad:true,
				selectRowButtonOnly:true,checkbox: true,rownumbers:true,isScroll:true,
				toolbar: { 
					items: [
						{ text: '查询', id:'query', click: query,icon:'search' }
					]}
            });
        	gridManager = $("#maingrid").ligerGetGridManager();    	
    }
</script>
</head>
  
<body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="layout1">
		<div position="top">
			<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" class="l-table-edit" >
			        <tr>  
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">上期日期：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_date_start" type="text" id="out_date_start" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left" class="l-table-edit-td">-</td>
			            <td align="left" class="l-table-edit-td"><input name="out_date_end" type="text" id="out_date_end" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">领用科室：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" /></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品信息：</td>
			            <td align="left" class="l-table-edit-td" colspan="3"><input name="inv_id" type="text" id="inv_id" ltype="text"/></td>
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
