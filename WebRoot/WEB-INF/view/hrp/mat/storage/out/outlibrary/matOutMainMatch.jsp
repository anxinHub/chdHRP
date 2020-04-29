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
    	var detail_data = gridManager.getCheckedRows();
    	var para={
				store_id : liger.get("store_id").getValue().split(",")[0], 
				num : $("#num").val(), 
				detail_data : JSON.stringify(detail_data)
         };
 		
        ajaxJsonObjectByUrl("queryMatOutDetailByMatch.do?isCheck=false",para,function(responseData){
        	if(responseData.Rows.length > 0){
				//订单材料
				parent.add_Rows(responseData.Rows);
	        	parent.$("#is_dir").val('0');
			}
        	dialog.close();
        });


   }
    function loadDict(){
        //字典下拉框
		$("#store_id").ligerComboBox({width:180,disabled:true,cancelable: false});
        liger.get("store_id").setValue("${store_id}");
		liger.get("store_id").setText("${store_text}");
		$("#dept_id").ligerComboBox({width:180,disabled:true,cancelable: false});
        liger.get("dept_id").setValue("${dept_id}");
		liger.get("dept_id").setText("${dept_text}");
    	
    	autocomplete("#dept_match_id", "../../../queryMatDeptMatch.do?isCheck=false", "id", "text", true, true,"",true,false,'180');
    	
    	$("#num").ligerTextBox({width:180});
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

    	  	var dept_id = liger.get("dept_id").getValue();
    	  	if(dept_id){
        	  	grid.options.parms.push({name:'dept_id',value:dept_id.split(",")[0]}); 
        	  	grid.options.parms.push({name:'dept_no',value:dept_id.split(",")[1]}); 
    	  	}
    	  	
    	  	var dept_match_id = liger.get("dept_match_id").getValue();
    	  	if(dept_match_id){
        	  	grid.options.parms.push({name:'dept_match_id',value:dept_match_id}); 
    	  	}
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
				{ display: '材料编码', name: 'inv_code', align: 'left', width: 100},
                { display: '材料名称', name: 'inv_name', align: 'left', width: 140},
                { display: '规格型号', name: 'inv_model', align: 'left', width: 160},
                { display: '计量单位', name: 'unit_name', align: 'left', width: 60},
                { display: '数量', name: 'amount', align: 'right', width: 80},
                {display: '库存数量', name: 'cur_amount', align: 'right',width:'80',}, 
                { display: '存放仓库名称', name: 'store_name', width: 80},
				],
				usePager : false,fixedCellHeight:true,
				dataAction: 'local',dataType: 'server',usePager:true,url:'queryMatDeptMatchByMatchd.do?isCheck=false',parms :para,
				width: '100%', height: '100%', checkbox: true, rownumbers:true, delayLoad:true, isAddRow:false,
				selectRowButtonOnly:true, enabledEdit : true, 
				
				toolbar: { items: [
			                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
			                     	{ line:true },
			                     	{ text: '生成出库单（<u>A</u>）', id:'createOut', click: checkMatOutFifo,icon:'add' },
			                     	{ line:true },
			                     	{ text: '关闭（<u>C</u>）', id:'close', click: this_close,icon:'close' },
			                     	{ line:true },
				]},
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    	
    }
	//关闭当前弹出框
	function this_close(){
		frameElement.dialog.close();
	}
    </script>

  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="layout1">
		<div position="top">
				<form name="form1" method="post"  id="form1" >
			        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>科   室：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>配套表：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_match_id" type="text" id="dept_match_id" ltype="text"/></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>套数：</td>
			            <td align="left" class="l-table-edit-td"><input name="num" type="text" id="num" value="1" ltype="text"/></td>
			            <td align="left"></td>
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
