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

    var paraMoney = '${p04005}';
	var paraPrice = '${p04006}';
     
	$(function (){
        loadDict();
        loadForm();
        loadHead(null);
        query();
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
 
    function this_close(){
		frameElement.dialog.close();
	}
    
    function loadDict(){
        //字典下拉框
    	autocomplete("#dept_id", "../../queryHosDeptDictByPerm.do?isCheck=false", "id", "text", true, true,"",false,'','180');
    	liger.get("dept_id").setValue('${dept_id}');
		liger.get("dept_id").setText('${dept_text}');
        $("#dept_id").ligerComboBox({disabled: true});
    	autocomplete("#mat_type_id", "../../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true,'',false,"",'180');
    	$("#inv_id").ligerTextBox({width:180}); 
    } 
    //查询
    function  query(){ 
    	grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		var dept_id = '${dept_id}';
        if(dept_id){
        	grid.options.parms.push({name:'dept_id',value : dept_id.split(",")[0]}); 
          	grid.options.parms.push({name:'dept_no',value : dept_id.split(",")[1]}); 
        }

        var mat_type_id = liger.get("mat_type_id").getValue();
        
        if(mat_type_id){
        	grid.options.parms.push({name:'mat_type_id',value:mat_type_id.split(",")[0]}); 
        }
        grid.options.parms.push({name:'inv_code',value:liger.get("inv_id").getValue()}); 
       	
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
	}
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
				display: '交易编码', name: 'bid_code', align: 'left', width: 100
			}, { 
				display: '材料变更号', name: 'inv_no', align: 'left', width: 100, hide:true
			}, { 
				display: '材料编码', name: 'inv_code', align: 'left', width: 110
			}, { 
				display: '材料名称(E)', name: 'inv_id', textField: 'inv_name', align: 'left', width: 240
			}, { 
				display: '规格型号', name: 'inv_model', align: 'left', width: 180
			}, { 
				display: '计量单位', name: 'unit_name', align: 'left', width: 60
			}, { 
				display: '单价', name: 'price', align: 'right', width: 90, 
				render: function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, paraPrice, 0);
				}
			}, { 
				display: '条形码(E)', name: 'bar_code', align: 'left', width: 120, editor: {type: 'text'}
			}, { 
				display: '账面数量', name: 'cur_amount', align: 'left', width: 80, hide:true
			}, { 
				display: '盘点数量(E)', name: 'chk_amount', align: 'left', width: 80, editor: {type: 'float'}, 
				render: function (rowdata, rowindex, value) {
	           		 if(rowdata.chk_amount==0){
	           			 rowdata.chk_amount = "0";
	           			 return "0" ;
	           		 }else{
	           			 return value;
	           		 }
	           	}
			}, { 
				display: '盈亏数量', name: 'amount', align: 'left', width: 80, hide:true
			}, { 
				display: '盈亏金额', name: 'amount_money', align: 'right', width: 90, hide:true
			}, { 
				display: '生产厂商', name: 'fac_name', align: 'left', width: 180
			}, { 
				display: '备注(E)', name: 'note', align: 'left', width: 240, editor: {type: 'text'}
			} ],
			dataAction: 'server',dataType: 'server',usePager:false,url:'../store/queryMatDuraInvChoiceByStore.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true, rownumbers:true,
			enabledEdit: true, onAfterEdit: f_onAfterEdit, delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'query', click: query, icon:'search' },
				{ line : true},
				{ text: '选择完毕', id:'add', click: choice, icon:'add' },
				{ line : true},
				{ text: '关闭（<u>C</u>）', id:'close', click: this_close, icon:'close' },
				{ line : true},
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function choice(){
    	var data = gridManager.getCheckedRows();
    	
		if (data.length == 0){
			$.ligerDialog.warn('请选择需添加的材料！');
			return;
		}else{
			
			var msg = new StringBuffer();;
	  		var rows = 0;
			
			$.each(data,function(i, v){
	  			rows += 1;
	  			if(v.bar_code == null || v.bar_code == '' || v.bar_code == 'undefined'
	  					|| v.chk_amount == null || v.chk_amount == '' || v.chk_amount == 'undefined'){
	  				
	  				msg.append("第"+rows+"行[条码]未维护或[盘点数量]未维护</br>");
	  			}
	  		});
			
			if(msg.toString()  != ""){
	 			$.ligerDialog.warn(msg.toString());
	 			return false;
	 		}
			
			parentFrameUse().addParts(data);
			
			$.ligerDialog.confirm('是否继续选择?', function(yes) {
				if (yes) {
					grid.deleteSelectedRow();
				}else{
					this_close();
				}
			});
		}
    }
    
    function f_onAfterEdit(rowData){
    	if(rowData.column.columnname == 'chk_amount'){
			grid.updateCell('amount', rowData.record.chk_amount, rowData.record);
			grid.updateCell('amount_money', rowData.record.chk_amount*rowData.record.price, rowData.record);
    	}
    }
    </script>

  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="layout1">
		<div position="top">
			<form name="form1" method="post"  id="form1" >
			    <table cellpadding="2" cellspacing="10" class="l-table-edit" >
			        <tr>
						<td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" /></td>
			            <td align="left"></td>
			            
			           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资类别：</td>
			            <td align="left" class="l-table-edit-td"><input name="mat_type_id" type="text" id="mat_type_id" ltype="text" /></td>
			            <td align="left"></td>
			            
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料信息：</td>
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
