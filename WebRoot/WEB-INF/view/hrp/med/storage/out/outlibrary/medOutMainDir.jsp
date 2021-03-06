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
     var gridDetail = null;
     var dialog = frameElement.dialog;
     
     $(function (){
    	 
		$("#layout1").ligerLayout({
			topHeight:70,
			centerWidth:900
		});
        loadDict();
        loadForm();
        loadHead(null);	
        setTimeout(query, 1000);
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
    			group_id:detailData[0].group_id,
    			hos_id:detailData[0].hos_id,
    			copy_code:detailData[0].copy_code,
    			store_id:liger.get("store_id").getValue().split(",")[0],
    			dept_id : liger.get("dept_id").getValue(),
    			sql:sqlBuf.toString()
    	}
		
         ajaxJsonObjectByUrl("queryMedOutDetailByIsDir.do?isCheck=false",para,function (responseData){
         	var rows = parent.grid.rows;
         	for(var i = 0; i < rows.length; i++) {
         		if(!rows[i].inv_code) {
         			parent.grid.deleteRow(rows[i]);
         		}
         	}
         	parent.grid.addRows(responseData.Rows);
         	parent.loadDictDeptId(liger.get("dept_id").getValue());
         	parent.$("#is_dir").val('1');
         	dialog.close();
     	});
	}
    
    function loadDict(){
        //字典下拉框
       var dept='${dept_id}';
       var id=dept.split(",")[0];
       console.log(dept)
    	autocomplete("#store_id", "../../../queryMedStoreDictDate.do?isCheck=false", "id", "text", true, true,"",false,'${store_id}','180');
    	autocomplete("#dept_id", "../../../queryMedDeptDict.do?isCheck=false", "id", "text", true, true,{is_last: 1},false,'${dept_id}','180');
//     	liger.get("dept_id").setValue("${dept_id}");
// 		liger.get("dept_id").setText("${dept_code} ${dept_name}");
    	
    	$("#in_date_start").ligerTextBox({width:100});
    	autodate("#in_date_start", "yyyy-mm-dd", "month_first");
    	$("#in_date_end").ligerTextBox({width:100});
    	autodate("#in_date_end", "yyyy-mm-dd", "month_last");
    	$("#in_no").ligerTextBox({width:238});
    	$("#store_id").ligerComboBox({disable:true, cancelable: false});
    	$("#dept_id").ligerComboBox({cancelable: false});
    	
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

    	  	grid.options.parms.push({name:'in_no',value:$("#in_no").val()}); 
    	  	
    	  	var dept_id = liger.get("dept_id").getValue();

        	if(dept_id){
        		grid.options.parms.push({name:'dept_id',value:dept_id.split(",")[0]}); 
        	  	grid.options.parms.push({name:'dept_no',value:dept_id.split(",")[1]}); 
        	}
        	
    	  	grid.options.parms.push({name:'in_date_start',value:$("#in_date_start").val()}); 
    	  	grid.options.parms.push({name:'in_date_end',value:$("#in_date_end").val()}); 
    		//加载查询条件
    		grid.loadData(grid.where);
     }
    
    function loadHead(){
    	var store_id = '${store_id}';
    	var dept_id = '${dept_id}';
    	var para = {
				store_id : store_id.split(",")[0],
				store_no : store_id.split(",")[1],
				dept_id : dept_id.split(",")[0],
				dept_no : dept_id.split(",")[1]
		};
    	grid = $("#maingrid").ligerGrid({
           columns: [
				{ display: '入库单号', name: 'in_no', align: 'left', width: 160},
                { display: '摘要', name: 'brief', align: 'left', width: 200},
                { display: '供应商', name: 'sup_name', align: 'left', width: 200},
                { display: '制单人', name: 'maker_name', align: 'left', width: 90},
                { display: '入库日期', name: 'in_date', align: 'left', width: 100 },
                { display: '确认人', name: 'confirmer_name', align: 'left', width: 90},
                { display: '确认日期', name: 'confirm_date', align: 'left', width: 100},
                { 
		 			display: '金额', name: 'amount_money', align: 'right', width: '100',
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '2', 1);
					}
		 		},
				],
					dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedInMainByIsDir.do?isCheck=false',fixedCellHeight:true,
					isScroll : true,selectRowButtonOnly:true,heightDiff: 10,checkbox: true,parms :para,delayLoad:true,
                     width: '100%', height: '98%', detail: { onShowDetail: f_showOutDetail,height:'auto',reload: true, single: true}, frozen:false,//这个属性有detail明细的时候必须为false,否则明细显示不出来
                     toolbar: { items: [
				                     	{ text: '查询（<u>Q</u>）', id:'query', click: query,icon:'search' },
				                     	{ line:true },
				                     	{ text: '生成出库单（<u>A</u>）', id:'createOut', click: checkMedOutFifo,icon:'add' },
				                     	{ line:true },
				                     	{ text: '关闭（<u>C</u>）', id:'close', click: this_close,icon:'close' },
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
         gridDetail = $(ele).css({'margin-top':10, 'margin-left':60}).ligerGrid({
             columns:
                         [
                         { display: '药品编码', name: 'inv_code', align: 'left',width: 100},
                         { display: '药品名称', name: 'inv_name', align: 'left',width: 180},
                         { display: '规格型号', name: 'inv_model', align: 'left',width: 100},
                         { display: '批号', name: 'batch_no', align: 'left',width: 100 },
                         { display: '批次', name: 'batch_sn', align: 'left',width: 100 },
                         { display: '条形码', name: 'bar_code', align: 'left',width: 100},
                         { display: '剩余数量', name: 'imme_amount', align: 'left',width: 50},
                         { display: '单价', name: 'price', align: 'right',width:80,
                        	 render : function(rowdata, rowindex, value) {
             					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
             				}
                         },
                         ], 
						dataAction : 'server',dataType : 'server',usePager : false,
			 			parms :para,
			 			url : 'queryMedInDetailByIsDir.do?isCheck=false',fixedCellHeight:true,
			 			width : '90%',
			 			frozen:false
         });  
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
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入库日期：</td>
			            <td align="left" class="l-table-edit-td">
			            	<table>
			            		<tr>
			            			<td align="left">
			            				<input name="in_date_start" type="text" id="in_date_start" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			            			</td>
			            			<td align="left" class="l-table-edit-td">至：</td>
			            			<td align="left" class="l-table-edit-td">
			            				<input name="in_date_end" type="text" id="in_date_end" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			            			</td>
			            		</tr>
			            	</table>
			            </td>
			
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
