<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
     
    $(function (){ 
    	loadDict();
    	loadHead(null);
		 query(); 
    });
    //查询
    function  query(){
    		grid.options.parms=[];
			grid.options.newPage=1;
        	//根据表字段进行添加查询条件
  		grid.options.parms.push({
			name : 'fac_id',//生产厂商编码
			value : liger.get("fac_code").getValue() == null ? "" : liger.get("fac_code").getValue().split(",")[0]
    	});
  		grid.options.parms.push({
			name: 'bid_code',
			value: $("#bid_code").val()
		}); 
		grid.options.parms.push({
			name: 'inv_code',
			value: $("#inv_code").val()
		}); 
		
		grid.options.parms.push({
			name: 'sup_id',
			value: liger.get("sup_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name: 'store_id',
			value: liger.get("store_code").getValue().split(",")[0]
		}); 
		
		
    		//加载查询条件
    		grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
				{ 
					display: '材料编码', name: 'inv_code', align: 'left', width: '120'
				}, { 
					display: '材料名称', name: 'inv_name', align: 'left',width:'240'
				}, { 
					display: '规格', name: 'inv_model', align: 'left',width:'140'
				}, { 
					display: '单位', name: 'unit_name', align: 'left',width: '80'
				}, { 
					display: '生成厂商', name: 'fac_name', align: 'left',width: '180'
				}, { 
					display: '供应商', name: 'sup_name', align: 'left',width: '180'
				}, { 
					display: '批号', name: 'batch_no', align: 'left',width:'130'
				},{ 
					display: '条形码', name: 'bar_code', align: 'left',width:'130'
				},  { 
					display: '当前库存', name: 'cur_amount', align: 'left',width: '90'
				}, { 
					display: '及时库存', name: 'imme_amount', align: 'left',width: '90'
				}, { 
					display: '单价', name: 'price', align: 'left',width: '90',
					render : function(rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, '${p04006}', 1);
					}
				},  { 
					display: '交易编码', name: 'bid_code', align: 'right',width:'90',
					
				}
			],
			dataAction: 'local', dataType: 'server', usePager:true, url:'queryMatAffiBackInSup.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true, rownumbers:true, delayLoad:true, isAddRow:false,
			selectRowButtonOnly:true, enabledEdit : true, //heightDiff: -10,
			toolbar: { 
				items: [
					{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
					{ line:true }, 
				
					{ text: '关闭（<u>L</u>）', id:'close', click: this_close, icon:'close' }
				]
			} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
	//确定生成出库单
	  function checkMatBar(){
    	//根据选择回冲数据
    	//获取明细数据
    	var detail_history_data = grid.getCheckedRows();
/*     	parent.grid.deleteAllRows(); */
    	parent.grid.addRows(detail_history_data);//添加上行数据
    	parent.$("#is_dir").val('0');
    	frameElement.dialog.close();
   }
	
    //关闭
	function this_close(){
		frameElement.dialog.close();
	}
	function loadDict(){
	        //字典下拉框
	autocomplete("#fac_code", "../../queryHosFacInv.do?isCheck=false", "id", "text", true, true);
	$("#bid_code").ligerTextBox({width:160});
	$("#inv_code").ligerTextBox({width:160});
	$("#sup_code").ligerComboBox({width:160,disabled:true,cancelable: false});
	  liger.get("sup_code").setValue("${sup_id}");
		liger.get("sup_code").setText("${sup_text}");
		$("#store_code").ligerComboBox({width:160,disabled:true,cancelable: false});
        liger.get("store_code").setValue("${store_id}");
		liger.get("store_code").setText("${store_text}");
		
	//	$("#bar_code_new").val('${bar_code_new}');
	  }

    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" ></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
			 			<td align="right" class="l-table-edit-td"  width="10%">
	                    	生产厂商：
	                    </td>
	                    <td align="left" class="l-table-edit-td" width="20%">
	                    	<input name="fac_code" type="text" id="fac_code" ltype="text" validate="{required:true,maxlength:100}" />
	                    </td>
	                
	                      <td align="right" class="l-table-edit-td"  style="padding-left:20px;">交易编码：</td>
			            <td align="left" class="l-table-edit-td" colspan="3"><input name="bid_code" type="text" id="bid_code" ltype="text" /></td>
			             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料信息：</td>
			            <td align="left" class="l-table-edit-td" colspan="3"><input name="inv_code" type="text" id="inv_code"ltype="text" /></td>
			     
			
			
		</tr> 
		<tr>
		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="store_code" type="text" id="store_code" ltype="text" /></td>
			            <td align="left"></td>
			               <td align="right" class="l-table-edit-td"  width="10%">
	                    	供应商：
	                    </td>
	                    <td align="left" class="l-table-edit-td" width="20%">
	                    	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:true,maxlength:100}" />
	                    </td> 
		</tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
