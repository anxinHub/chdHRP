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
   
 function checkMedOutFifo(){
	//根据选择回冲数据
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择要盘点的药品！');
			return;
		}else{
			var detail_rows = new StringBuffer();
			detail_rows.append("[");
			$.each(data, function(index, data){
				if(index != 0){
					detail_rows.append(",");
				}
				detail_rows.append('{"inv_id":').append(data.inv_id).append(',');
				detail_rows.append('"price":').append(data.price).append(',');
				detail_rows.append('"bar_code":"').append(data.bar_code).append('",');
				detail_rows.append('"batch_no":"').append(data.batch_no).append('",');
				detail_rows.append('"inv_no":').append(data.inv_no).append('}');; 
			});
			detail_rows.append("]");
			
			var formPara = {
				store_id :  '${store_id}'.split(",")[0],
				allData : detail_rows.toString()
			};
		 
			$.ligerDialog.confirm('确定生成盘点单吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("queryMedAffiStoreInvDetail.do?isCheck=false", formPara, function(responseData) {
						console.log(responseData)
						if(responseData.Rows.length > 0){
							//盘点药品
							parent.grid.options.data = {Rows:responseData.Rows};
							parent.grid._setData();
							parent.grid.addRow();
						}
						this_close();
					});
				}
			});
		}
	 	
    	
    	//parent.grid.deleteAllRows();  //清空父页面数据
       	//parent.grid.addRows(eval(detail_rows.toString()));  //添加上行数据
       	//parent.is_addRow();
	}
 	
 	function this_close(){
		frameElement.dialog.close();
	}
 
    function loadDict(){
    	//字典下拉框
    	autocomplete("#store_id", "../../queryMedStore.do?isCheck=false", "id", "text", true, true,"",false,'${store_id}','160');       
    	$("#store_id").ligerComboBox({disabled: true});   	
    	autocomplete("#med_type_id", "../../queryMedTypeDict.do?isCheck=false", "id", "text", true, true,'',false,false,'160');  	
    	autocomplete("#inv_id", "../../queryMedInvDict.do?isCheck=false", "id", "text", true, true,"",false,false,'230');
    
     } 
    //查询
    function  query(){
    	grid.options.parms=[];
		grid.options.newPage=1;
    	//根据表字段进行添加查询条件
		var store_id = '${store_id}';;

    	if(store_id){
    		grid.options.parms.push({name:'store_id',value:store_id.split(",")[0]}); 
    	}

    	var med_type_id = liger.get("med_type_id").getValue();
    	
    	if(med_type_id){
    		grid.options.parms.push({name:'med_type_id',value:med_type_id.split(",")[0]}); 
    	}
    	
    	var inv_id = liger.get("inv_id").getValue();
    	
    	if(inv_id){
    		grid.options.parms.push({name:'inv_id',value:inv_id.split(",")[0]}); 
    	  	grid.options.parms.push({name:'inv_no',value:inv_id.split(",")[1]}); 
    	}
    	grid.options.parms.push({name:'is_zero',value : is_zero});
		//加载查询条件
		grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    
    function loadHead(){
    	var store_id = '${store_id}';
    	var para = {
				store_id : store_id.split(",")[0],
				is_zero : is_zero
		};
    	grid = $("#maingrid").ligerGrid({
           columns: [
				{display : '药品编码', name : 'inv_code', minWidth : 100, align : 'left'}, 
				{display : '药品名称', name : 'inv_name', minWidth : 120, align : 'left'}, 
				{display : '规格型号', name : 'inv_model', minWidth : 80, align : 'left'},
				{display : '计量单位', name : 'unit_name', minWidth : 80, align : 'left'}, 
				{display : '单价', name : 'price', minWidth : 40, align : 'left'}, 
				{display : '批号', name : 'batch_no', minWidth : 60, align : 'left'}, 
				{display : '条码', name : 'bar_code', minWidth : 60, align : 'left'}, 
				{display : '账面数量', name : 'left_amount', minWidth : 80, align : 'left'}, 
				{display : '有效日期', name : 'inv_date', minWidth : 80, align : 'left'}, 
				{display : '供应商', name : 'sup_name', minWidth : 80, align : 'left'}, 
				{display : '生产厂商', name : 'fac_name', minWidth : 80, align : 'left'}, 
				{display : '货位编码', name : 'location_code', minWidth : 80, align : 'left'}, 
				{display : '货位名称', name : 'location_name', minWidth : 80, align : 'left'}
			],
			dataAction: 'server',dataType: 'server',usePager:false,
			url:'queryMedAffiCheckMainByMedInv.do?isCheck=false',fixedCellHeight:true,
			isScroll : true,heightDiff: 10,checkbox: true,parms :para,rownumbers: true,
            width: '98%', height: '98%',
            toolbar: { items: [
				{ text: '查询', id:'query', click: query,icon:'search' },
				{ line:true }
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
							<td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
				            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" /></td>
				            <td align="left"></td>
				            
				           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品类别：</td>
				            <td align="left" class="l-table-edit-td"><input name="med_type_id" type="text" id="med_type_id" ltype="text" /></td>
				            <td align="left"></td>
				            
				            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品信息：</td>
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
