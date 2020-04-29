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
     
     $(function (){
		$("#layout1").ligerLayout({
			topHeight:60,
			centerWidth:888
		});
        loadHead();	
     });  
     
	function  save(){
		grid.endEdit();
 		var check_detail_data = gridManager.rows;     	 
        var formPara={
        		check_detail_data:JSON.stringify(check_detail_data),
         };
        console.log(gridManager.rows);
        ajaxJsonObjectByUrl("updateCheckInvBatchDetail.do?isCheck=false",formPara,function(responseData){
            if(responseData.state=="true"){
            	parentFrameUse().query();
            	this_refresh();
            }
        });
    }
   
    function saveMatOutMain(){
        if($("form").valid()){
            save();
        }
   }
    function this_refresh(){
        autodate("#create_date");//默认当前日期
		$("#brief").val("");
        grid.reload();
        is_addRow();
    }
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [   { display: '交易编码', name: 'bid_code', align: 'left',width:100},
                     { display: '明细id', name: 'detail_id',hide:true, align: 'left',width:100},
                     { display: '材料编码', name: 'inv_code', align: 'left',width:100},
                     { display: '材料名称(E)', name : 'inv_id', textField : 'inv_name',align: 'left',width:200},
                     { display: '材料信息', name: 'inv_msg',hide:true,align: 'left',width:100},
                     { display: '规格型号', name: 'inv_model', align: 'left',width:100},
                     { display: '计量单位', name: 'unit_name', align: 'left',width:60},
                     { display: '批号', name: 'batch_no', align: 'left',width:60},
                     { display: '条形码', name: 'bar_code', align: 'left',width:120},
                     { display: '单价', name: 'price', align: 'right',width:100,
                    	 render : function(rowdata, rowindex, value) {
                    		 rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
          					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
          				}
                     },
                     { display: '账面数量', name: 'cur_amount', align: 'right',width:90,
                    	 totalSummary: {
     						align: 'right',
     						render: function (suminf, column, cell) {
     							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
     						}
     					} 		 
                     },
                     { display: '账面金额', name: 'cur_money', align: 'right',width:100,
                    	 render : function(rowdata, rowindex, value) {
                    		 rowdata.cur_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
          					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
          				},totalSummary: {
    						align: 'right',
    						render: function (suminf, column, cell) {
    							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005 }', 1) + '</div>';
    						}
    					}
                     },
                     { display: '盘点数量(E)', name: 'chk_amount', align: 'right',width:90,editor : {type : 'float'},
                    	 render: function (rowdata, rowindex, value) {
                    		 if(rowdata.chk_amount==0){
                    			 rowdata.chk_amount = "0";
                    			 return "0" ;
                    		 }else{
                    			 return value;
                    		 }
                    	 },
                    	 totalSummary: {
     						align: 'right',
     						render: function (suminf, column, cell) {
     							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
     						}
     					} 		 
                     },
                     { display: '盘点金额', name: 'chk_money', align: 'right',width:100,
                    	 render : function(rowdata, rowindex, value) {
                    		 rowdata.chk_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
          					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
          				},totalSummary: {
    						align: 'right',
    						render: function (suminf, column, cell) {
    							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005 }', 1) + '</div>';
    						}
    					}
                     },
                     { display: '盈亏数量', name: 'pl_amount', align: 'right',width:90,
                    	 totalSummary: {
     						align: 'right',
     						render: function (suminf, column, cell) {
     							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
     						}
     					} 		 
                     },
                     { display: '盈亏金额', name: 'pl_money', align: 'right',width:100,
                    	 render : function(rowdata, rowindex, value) {
                    		 rowdata.pl_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
          					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
          				},totalSummary: {
    						align: 'right',
    						render: function (suminf, column, cell) {
    							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005 }', 1) + '</div>';
    						}
    					}
                     },
                     { display: '生产厂商', name: 'fac_name', align: 'left',width:180},
                     { display: '有效期', name: 'inva_date', align: 'left',width:120},
                     { display: '灭菌效期', name: 'disinfect_date', align: 'left',width:120},
                     { display: '货位', name: 'location_name', align: 'left',width:120,hide:true},
                     { display: '货位', name: 'location_new_name', align: 'left',width:120},
                     { display: '盈亏说明(E)',name : 'note', align: 'left',width:180,editor : {type : 'text'}}
                     ],
                     usePager : false, width : '100%', height : '100%',  enabledEdit : true, 
                     fixedCellHeight:true,url:'queryMatCheckDetailByInvBatch.do?isCheck=false&check_id=${check_id}&inv_msg=${inv_msg}',
					selectRowButtonOnly:true, checkbox: true, rownumbers:true, isScroll:true,isAddRow:false,
					onBeforeEdit: f_onBeforeEdit, onAfterEdit: f_onAfterEdit, heightDiff:-20,
					toolbar: { items: [
					                     	{ text: '保存', id:'add', click: saveMatOutMain,icon:'add' },
					                     	{ line:true },
					                     	{ text: '关闭', id:'colse', click: this_close,icon:'close' }
				]}
			});

        gridManager = $("#maingrid").ligerGetGridManager();

        
    }
    

	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;		
	}
	 function btn_saveColumn(){
	 		
		   var path = window.location.pathname+"/maingrid";
		   var url = '../../../sys/addBatchSysTableStyle.do?isCheck=false';
		   saveColHeader({
			   grid:grid,
			   path:path,
			   url:url,
			   callback:function(data){
				   $.ligerDialog.success("保存成功");
			   }
		   });
	  
	   return false;
}
	
    
    function f_onAfterEdit(e){
    	if("chk_amount" == e.column.columnname){
    		gridManager.updateCell('chk_money', e.record.chk_amount*e.record.price, e.record); 
    		gridManager.updateCell('pl_amount', (e.record.chk_amount -e.record.cur_amount), e.record); 
    		gridManager.updateCell('pl_money', (e.record.chk_amount -e.record.cur_amount)*e.record.price, e.record); 
    	}
		grid.updateTotalSummary();
    }

	
 	//关闭
	function this_close(){
 		frameElement.dialog.close();
 	}
 	
	
    </script>

  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
  	<input name="is_dir" type="hidden" id="is_dir" />
	<div id="layout1">
		<div position="top">
			<form name="form1" method="post"  id="form1" >
			</form>
		</div>
		<div position="center" >
			<div id="maingrid"></div>
		</div>
	</div>
    </body>
</html>
