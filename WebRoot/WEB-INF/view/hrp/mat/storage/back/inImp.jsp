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
			topHeight:60,
			centerWidth:900
		});
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
   
    function imp(){
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
    			store_id:'${store_id}'.split(",")[0],
    			sql:sqlBuf.toString()
    	}
		
         ajaxJsonObjectByUrl("queryMatStorageBackDetailByImp.do?isCheck=false",para,function (responseData){
         	parent.add_Rows(responseData.Rows);//添加上行数据
         	dialog.close();
     	});
	}
    
    function loadDict(){
        //字典下拉框
		$("#store_code").ligerComboBox({width:160,disabled:true,cancelable: false});
        liger.get("store_code").setValue("${store_id}");
		liger.get("store_code").setText("${store_text}");
		$("#sup_code").ligerComboBox({width:160,disabled:true,cancelable: false});
        liger.get("sup_code").setValue("${sup_id}");
		liger.get("sup_code").setText("${sup_text}");
		
		$("#begin_in_date").ligerTextBox({width:120});
        autodate("#begin_in_date", "yyyy-mm-dd", "month_first");
        $("#end_in_date").ligerTextBox({width:120});
        autodate("#end_in_date", "yyyy-mm-dd", "month_last");
    	
    	$("#in_no").ligerTextBox({width:160});
     } 
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name: 'begin_in_date',
			value: $("#begin_in_date").val()
		}); 
		grid.options.parms.push({
			name: 'end_in_date',
			value: $("#end_in_date").val()
		}); 
		grid.options.parms.push({
			name: 'store_id',
			value: liger.get("store_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name: 'sup_id',
			value: liger.get("sup_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name: 'in_no',
			value: $("#in_no").val()
		}); 
		//加载查询条件
		grid.loadData(grid.where);
     }
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [
				{ display: '入库单号', name: 'in_no', width: 160},
                { display: '摘要', name: 'brief', width: 200},
                { display: '供应商', name: 'sup_name', width: 160},
                { display: '制单人', name: 'maker_name', width: 80},
                { display: '入库日期', name: 'in_date', width: 80 },
                { display: '确认人', name: 'confirmer_name', width: 80},
                { display: '确认日期', name: 'confirm_date', width: 80}
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatStorageBackIn.do?isCheck=false',
			fixedCellHeight:true,isScroll : true,selectRowButtonOnly:true,heightDiff: 10,checkbox: true,
			delayLoad: true,//初始化不加载，默认false
			width: '100%', height: '98%', detail: { onShowDetail: f_showOutDetail}, frozen:false,//这个属性有detail明细的时候必须为false,否则明细显示不出来
			toolbar: { items: [
				{ text: '查询', id:'query', click: query,icon:'search' },
				{ line:true },
				{ text: '生成退货单（<u>A</u>）', id:'add', click: imp, icon:'add' },
				{ line : true},
				{ text: '关闭（<u>C</u>）', id:'close', click: this_close, icon:'close' },
				{ line : true},
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
				store_id : liger.get("store_code").getValue().split(",")[0]
		}; 
		 
         var ele = document.createElement('div'); 
         $(detailPanel).append(ele);
         gridDetail = $(ele).css({'margin-top':10, 'margin-left':60}).ligerGrid({
             columns:
                         [
                         { display: '材料编码', name: 'inv_code',width: 100},
                         { display: '材料名称', name: 'inv_name',width: 180},
                         { display: '规格型号', name: 'inv_model',width: 100},
                         { display: '批号', name: 'batch_no',width: 100 },
                         { display: '批次', name: 'batch_sn',width: 100 },
                         { display: '条形码', name: 'bar_code' ,width: 100},
                         { display: '剩余数量', name: 'imme_amount', align: 'right' ,width: 50},
                         { display: '单价', name: 'price', align: 'right',width:80,
                        	 render : function(rowdata, rowindex, value) {
             					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
             				}
                         },
                         ], 
						dataAction : 'server',dataType : 'server',usePager : false,
			 			parms :para,
			 			url : 'queryMatStorageBackInDetail.do?isCheck=false',fixedCellHeight:true,
			 			width: '95%',height : '100%',
			 			frozen:false
         });  
     }
	
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
			        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入库日期：</td>
			            <td align="left" class="l-table-edit-td"><input name="begin_in_date" type="text" id="begin_in_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left" class="l-table-edit-td">-</td>
			            <td align="left" class="l-table-edit-td"><input name="end_in_date" type="text" id="end_in_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			
						<td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="store_code" type="text" id="store_code" ltype="text" /></td>
			            <td align="left"></td>
			            
			           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
			            <td align="left" class="l-table-edit-td"><input name="sup_code" type="text" id="sup_code" ltype="text" /></td>
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
