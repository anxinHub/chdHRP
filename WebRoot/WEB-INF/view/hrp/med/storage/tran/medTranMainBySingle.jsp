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
   
    function checkMedOutFifo(){
    	//根据选择回冲数据
    	//清空父页面表格

    	var detailData = gridManager.getCheckedRows();

    	if (detailData.length == 0){
    		
        	$.ligerDialog.error('请选择数据');
        	
        	return false;
        	
    	}
    	
    	var sqlBuf = new StringBuffer();
    	
    	sqlBuf.append(" and (");
    	
    	$.each(detailData, function(d_index, d_content){ 
    		
    		sqlBuf.append("(midet.in_id = "+this.in_id+")") ;
    		
    		if(d_index !=detailData.length-1){
    			
    			sqlBuf.append(" or ");
    			
    		}
    		
		});
    	sqlBuf.append(")");
    	
    	var store_id = liger.get("store_id").getValue();

    	var para={
    			
    			group_id:detailData[0].group_id,
    			
    			hos_id:detailData[0].hos_id,
    			
    			copy_code:detailData[0].copy_code,
    			
    			sql:sqlBuf.toString(),
    			
    			store_id:store_id.split(",")[0],
    			
    			store_no:store_id.split(",")[1]
    			
    	}
		
		ajaxJsonObjectByUrl("queryMedTranDetailBySingle.do?isCheck=false",para,function (responseData){
			parent.add_Rows(responseData.Rows);//添加上行数据
			dialog.close();
		}); 

   }
    function loadDict(){
        //字典下拉框
        
    	//$("#store_id").ligerComboBox({disabled: true});
    	  $("#store_id").ligerComboBox({width:180});
    	  
    	autocomplete("#store_id", "../../queryMedStoreDictDate.do?isCheck=false", "id", "text", true, true, {store_id:'${store_id}'}, false,"","","",200);
  		liger.get("store_id").setValue("${store_id}");
  		liger.get("store_id").setText("${store_code} ${store_name}");
  		
    	 
    	/*  autocomplete("#store_id", "../../queryMedStore.do?isCheck=false", "id", "text", true, true,"",true,'${store_id}','180');
    	 
    	 liger.get("store_id").setValue('${store_id}');
			
    	 liger.get("store_id").setText('${store_code}'+" "+'${store_name}'); */
    	 
    	 $("#begin_confirm_date").ligerTextBox({width:110});
         
    	 $("#end_confirm_date").ligerTextBox({width:110});
         
    	 autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first"); 
         
    	 autodate("#end_confirm_date", "yyyy-mm-dd", "month_last");
    	
    	
    	//autodate("#month","mm");
    	
    	$("#in_no").ligerTextBox({width:180});
    	$("#brief").ligerTextBox({width:180});
    	$("#sup_name").ligerTextBox({width:240});
    	$("#maker_name").ligerTextBox({width:180});
    
     } 
    //查询
    function  query(){
    		grid.options.parms=[];
			grid.options.newPage=1;
        	//根据表字段进行添加查询条件liger.get("tran_type").getValue()
        	var store_id = liger.get('store_id').getValue().split('@')[0]; 
        	if(store_id){ 
        		grid.options.parms.push({name:'store_id',value:store_id.split(",")[0]}); 
        	  	grid.options.parms.push({name:'store_no',value:store_id.split(",")[1]}); 
        	}
        	
    	  	grid.options.parms.push({name:'in_no',value:$("#in_no").val()}); 
    		grid.options.parms.push({
    			name : 'begin_confirm_date',
    			value : $("#begin_confirm_date").val()
    		});
    		grid.options.parms.push({
    			name : 'end_confirm_date',
    			value : $("#end_confirm_date").val()
    		}); 
    		grid.options.parms.push({
    			name : 'brief',
    			value : $("#brief").val()
    		});  
    		grid.options.parms.push({
    			name : 'sup_name',
    			value : $("#sup_name").val()
    		}); 
    		grid.options.parms.push({
    			name : 'maker_name',
    			value : $("#maker_name").val()
    		}); 
    		//加载查询条件
    		grid.loadData(grid.where);
			$("#resultPrint > table > tbody").empty();
     }
    
    function loadHead(){
    	var store_id = '${store_id}';
    	var para = {
				store_id : store_id.split(",")[0], 
				store_no : store_id.split(",")[1]
		};
    	
    	grid = $("#maingrid").ligerGrid({
           columns: [
				{ display: '入库单号', name: 'in_no', width: 160},
                { display: '摘要', name: 'brief', width: 200},
                { display: '供应商', name: 'sup_name', width: 160},
                { display: '制单人', name: 'maker_name', width: 80},
                { display: '入库日期', name: 'in_date', width: 80 },
                { display: '确认人', name: 'confirmer_name', width: 80},
                { display: '确认日期', name: 'confirm_date', width: 80},
                { display: '金额', name: 'money', width: 80,
                	render : function(rowdata, rowindex, value) {
        				rowdata.amount_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
        				return value == null ? "" : formatNumber(value, '${p08005 }', 1);
        			}	
                }
				],
					dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedInMainBySingle.do?isCheck=false',fixedCellHeight:true,
					isScroll : true,selectRowButtonOnly:true,isSingleCheck:false,heightDiff: 10,checkbox: true,
                     width: '98%', height: '98%', detail: { onShowDetail: f_showOutDetail},parms :para,
                     frozen:false,//这个属性有detail明细的时候必须为false,否则明细显示不出来
                     delayLoad:true,
                     toolbar: { items: [
				                     	{ text: '查询', id:'query', click: query,icon:'search' },
				                     	{ line:true },
				                     	{ text: '生成调拨单', id:'add', click: checkMedOutFifo,icon:'add' },
				                     	{ line:true },
				                     	{ text: '关闭', id:'close', click: this_close, icon:'close' },
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
			columns:[
				{ display: '药品编码', name: 'inv_code',width: 100},
				{ display: '药品名称', name: 'inv_name',width: 180},
				{ display: '规格型号', name: 'inv_model',width: 100},
				{ display: '批号', name: 'batch_no',width: 100 },
				{ display: '条形码', name: 'bar_code' ,width: 100},
				{ display: '剩余数量', name: 'imme_amount' ,width: 80},
				{ display: '单价', name: 'price' ,width: 80, 
					render : function(rowdata, rowindex, value) {
						rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
						return value == null ? "" : formatNumber(value, '${p08006 }', 1);
					}
				}
			], 
			dataAction : 'server',dataType : 'server',usePager : false,
			parms :para,
			url : 'queryMedInDetailBySingle.do?isCheck=false',fixedCellHeight:true,
			width: '90%',height : '100%',
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
 
				<table cellpadding="0" cellspacing="0" class="l-table-edit">
					<tr>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">仓库：</td>
						<td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id"  ltype="text" /></td>
						<td align="left"></td> 
						<td align="right" class="l-table-edit-td" >入库日期:</td>
						<td align="left" class="l-table-edit-td" width="20%">
							<table>
								<tr>
									<td><input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text"
										onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
									</td>
									<td align="left" class="l-table-edit-td">至</td>
									<td><input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text"
										onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
									</td>
								</tr>
							</table>
						</td> 
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">入库单号：</td>
						<td align="left" class="l-table-edit-td"  ><input name="in_no" type="text" id="in_no" ltype="text" /></td>
						<td align="left"></td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘要：</td>
						<td align="left" class="l-table-edit-td"  ><input name="brief" type="text" id="brief" ltype="text" /></td>
						<td align="left"></td>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">供应商：</td>
						<td align="left" class="l-table-edit-td"  ><input name="sup_name" type="text" id="sup_name" ltype="text" /></td>
						 
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单人：</td>
						<td align="left" class="l-table-edit-td"  ><input name="maker_name" type="text" id="maker_name" ltype="text" /></td>
						<td align="left"></td>
					</tr>
				</table>
			 
	 
			<div id="maingrid"></div>
	 
	 
    </body>
</html>
