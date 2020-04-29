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
			topHeight:70,
			centerWidth:900
		});
    	 
        loadDict();
        //loadForm();
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
   
    function checkMatOutFifo(){
    	//根据选择回冲数据
    	//获取明细数据
    	var detail_history_data = grid.getCheckedRows();
    	parent.grid.deleteAllRows();
    	parent.grid.addRows(detail_history_data);//添加上行数据
    	parent.$("#is_dir").val('0');
    	dialog.close();
   }
    function loadDict(){
        //字典下拉框

		$("#store_id").ligerComboBox({width:180,disabled:true,cancelable: false});
        liger.get("store_id").setValue("${store_id}");
		liger.get("store_id").setText("${store_text}");
		$("#dept_id").ligerComboBox({width:180,disabled:true,cancelable: false});
        liger.get("dept_id").setValue("${dept_id}");
		liger.get("dept_id").setText("${dept_text}");
    	autocomplete("#inv_id", "../../../queryMatInvDict.do?isCheck=false", "id", "text", true, true,"",false,false,'238', "", '238');
    	
    	$("#begin_out_date").ligerTextBox({width:100}); 
    	autodate("#begin_out_date", "yyyy-mm-dd", "month_first");
    	$("#end_out_date").ligerTextBox({width:100}); 
    	autodate("#end_out_date", "yyyy-mm-dd", "month_last");
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

    	  	grid.options.parms.push({name:'begin_out_date',value:$("#begin_out_date").val()}); 
    	  	grid.options.parms.push({name:'end_out_date',value:$("#end_out_date").val()}); 
    	  	grid.options.parms.push({name:'is_zero',value:$("#is_zero").prop("checked") ? 1 : 0}); 
    		//加载查询条件
    		grid.loadData(grid.where);
     }
  
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [
				{ display: '材料编码', name: 'inv_code', width: 100},
                { display: '材料名称', name: 'inv_name', width: 140},
                { display: '规格型号', name: 'inv_model', width: 160},
                { display: '计量单位', name: 'unit_name', width: 50},
                { display: '单价', name: 'price', align: 'right',width:80,
               	 render : function(rowdata, rowindex, value) {
    					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
    				}
                },
                { display: '上期耗用量', name: 'old_amount', width: 70},
                { display: '批号', name: 'batch_no', width: 50},
                { display: '条形码', name: 'bar_code', width: 50},
                { display: '库存', name: 'cur_amount', align: 'right', width: 80},
                { display: '即时库存', name: 'imme_amount', align: 'right', width: 80,},
    		    { display: '有效日期', name: 'inva_date', width: 90},
    		    { display: '灭菌日期', name: 'disinfect_date', width: 90},
				],

	 			dataAction : 'server', dataType : 'server', url : 'queryMatOutDetailHistory.do?isCheck=false',
	 			 usePager : false, width : '100%', height : '100%', fixedCellHeight: true, delayLoad: false,
					selectRowButtonOnly:true,checkbox: true,rownumbers:true, isScroll:true,
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
			        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			        <tr>
			            
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">上期日期：</td>
			            <td align="left" class="l-table-edit-td">
			            	<table>
			            		<tr>
			            			<td>
										<input name="begin_out_date" type="text" id="begin_out_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			            			</td>
						            <td align="left" class="l-table-edit-td">至：</td>
						            <td align="left" class="l-table-edit-td">
						            	<input name="end_out_date" type="text" id="end_out_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						            </td>
			            		</tr>
			            	</table>
			            </td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">领用科室：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" /></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料信息：</td>
			            <td align="left" class="l-table-edit-td" colspan="3"><input name="inv_id" type="text" id="inv_id" ltype="text"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  >
			            	<input name="is_zero" type="checkbox" id="is_zero" ltype="text" validate="{required:false}" />
			            </td>
			            <td align="left" class="l-table-edit-td">
			            	是否显示零库存材料
			            </td>
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
