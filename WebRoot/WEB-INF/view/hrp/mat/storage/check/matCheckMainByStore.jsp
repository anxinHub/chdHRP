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
     var gridDetail = null;
     var dialog = frameElement.dialog;
     var is_zero = 1;
     
     $(function (){
    	 
		$("#layout1").ligerLayout({
			topHeight:60,
			centerWidth:900
		});
        loadDict();
        loadForm();
        loadHead(null);	
        $("#is_zero").bind('change', function (){
			if($('#is_zero').is(':checked')) {
				is_zero = 1;
			}else{
				is_zero = 0;
			}
		});
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
   
    function checkMatOutFifo(){
    	//根据选择回冲数据
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择要盘点的材料！');
			return;
		}else{
			$.ligerDialog.confirm('确定生成盘点单吗?', function (yes){
				if(yes){
					parent.grid.options.data ={Rows:data}; 
					parent.grid._setData();
					parent.grid.addRow();
					this_close();
				}
			}); 
		}
    }
    
    function this_close(){
		frameElement.dialog.close();
	}
    
    function loadDict(){
        //字典下拉框
    	autocomplete("#store_id", "../../queryMatStore.do?isCheck=false", "id", "text", true, true,"",false,'${store_id}','160');
    	$("#store_id").ligerComboBox({disabled: true});
    	autocomplete("#mat_type_id", "../../queryMatTypeDictCode.do?isCheck=false", "id", "text", true, true,'',false,false,'160');
    	$("#inv_id").ligerTextBox({width:180}); 
    } 
    //查询
    function  query(){
    		grid.options.parms=[];
			grid.options.newPage=1;
        	//根据表字段进行添加查询条件
			var store_id = '${store_id}';
        	if(store_id){
        		grid.options.parms.push({name:'store_id',value:store_id.split(",")[0]}); 
        	  	grid.options.parms.push({name:'store_no',value:store_id.split(",")[1]}); 
        	}

        	var mat_type_id = liger.get("mat_type_id").getValue();
        	
        	if(mat_type_id){
        		grid.options.parms.push({name:'mat_type_id',value:mat_type_id.split(",")[2]}); 
        	}

        	grid.options.parms.push({name:'inv_id',value:liger.get("inv_id").getValue()}); 
        	grid.options.parms.push({name:'is_zero',value : is_zero}); 
        	
    		//加载查询条件
    		grid.loadData(grid.where);
			$("#resultPrint > table > tbody").empty();
     }
    
    function loadHead(){
    	var store_id = '${store_id}';
    	var para = {
				store_id : store_id.split(",")[0],
				store_no : store_id.split(",")[1],
				is_zero : is_zero
		};
    	
    	grid = $("#maingrid").ligerGrid({
           columns: [
							{display : '交易编码', name : 'bid_code', width : 100, align : 'left'}, 
							{display : '材料编码', name : 'inv_code', width : 100, align : 'left'}, 
               	            {display : '材料名称', name : 'inv_name', width : 120, align : 'left'}, 
               	            {display : '规格型号', name : 'inv_model', width : 80, align : 'left'},
               	            {display : '计量单位', name : 'unit_name', width : 80, align : 'left'}, 
               	            {display : '单价', name : 'price', width : 40, align : 'left'}, 
               	            {display : '账面数量', name : 'cur_amount', width : 80, align : 'right'}, 
               	            {display : '生产厂商', name : 'fac_name', width : 80, align : 'left'}, 
               	            {display : '货位编码', name : 'location_code', width : 80, align : 'left'}, 
               	            {display : '货位名称', name : 'location_new_name', width : 80, align : 'left'}
				],
					dataAction: 'server',dataType: 'server',usePager:false,url:'queryMatCheckMainByMatInv.do?isCheck=false',parms :para,fixedCellHeight:true,
					isScroll : true,heightDiff: 10,checkbox: true,parms :para,rownumbers: true,
                     width: '980', height: '90%',
                     isChecked: f_isChecked, onCheckRow: f_onCheckRow, onCheckAllRow: f_onCheckAllRow,
                     toolbar: { items: [
				                     	{ text: '查询', id:'query', click: query,icon:'search' },
				                     	{ line:true }
								]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    	
    }
    	
    function f_onCheckAllRow(checked)
    {
        for (var rowid in this.records)
        {
            if(checked)
                addCheckedCustomer(this.records[rowid]['inv_id']);
            else
                removeCheckedCustomer(this.records[rowid]['inv_id']);
        }
    }

    /*
    该例子实现 表单分页多选
    即利用onCheckRow将选中的行记忆下来，并利用isChecked将记忆下来的行初始化选中
    */
    var checkedCustomer = [];
    function findCheckedCustomer(inv_id)
    {
        for(var i =0;i<checkedCustomer.length;i++)
        {
            if(checkedCustomer[i] == inv_id) return i;
        }
        return -1;
    }
    function addCheckedCustomer(inv_id)
    {
        if(findCheckedCustomer(inv_id) == -1)
            checkedCustomer.push(inv_id);
    }
    function removeCheckedCustomer(inv_id)
    {
        var i = findCheckedCustomer(inv_id);
        if(i==-1) return;
        checkedCustomer.splice(i,1);
    }
    function f_isChecked(rowdata)
    {
        if (findCheckedCustomer(rowdata.inv_id) == -1)
            return false;
        return true;
    }
    function f_onCheckRow(checked, data)
    {
        if (checked) addCheckedCustomer(data.inv_id);
        else removeCheckedCustomer(data.inv_id);
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
						<td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" /></td>
			            <td align="left"></td>
			            
			           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资类别：</td>
			            <td align="left" class="l-table-edit-td"><input name="mat_type_id" type="text" id="mat_type_id" ltype="text" /></td>
			            <td align="left"></td>
			            
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料信息：</td>
			            <td align="left" class="l-table-edit-td" colspan="3"><input name="inv_id" type="text" id="inv_id" ltype="text"/></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
			        		<input type="checkbox" id="is_zero" name="is_quote" checked>不显示零库存
			            </td>
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