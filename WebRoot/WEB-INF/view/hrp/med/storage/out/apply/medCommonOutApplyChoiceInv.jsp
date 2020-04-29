<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="<%=path%>/lib/hrp/med/med.js" type="text/javascript"></script>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    $(function (){
        loadDict();//加载数据
    	loadHead(null);
    	query();
		loadHotkeys();
		$('#show_zero').bind("change",function(){
			 query();
		 });
		$('#is_stop').bind("change",function(){
			 query();
		 });
    });
    
    
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
        	grid.options.parms.push({
    			name : 'med_type_code',//药品类别
    			value : liger.get("med_type_code").getValue() == null ? "" :liger.get("med_type_code").getValue().split(",")[0]
        	});
        	grid.options.parms.push({
        		name:'inv_code',
        		value: $("#inv_code").val() 
        	}); 

        	grid.options.parms.push({
    			name : 'is_zero',
    			value : $("#show_zero").prop("checked") ? 1 : 0
    		}); 
        	grid.options.parms.push({
    			name : 'is_stop',
    			value : $("#is_stop").prop("checked") ? 1 : 0
    		}); 

        	grid.options.parms.push({
        		name:'bid_code',
        		value: $("#bid_code").val() 
        	});
        	grid.options.parms.push({
        		name:'inv_model',
        		value: $("#inv_model").val() 
        	});

        	/* grid.options.parms.push({
        		name:'inv_id',
        		value:liger.get("inv_code").getValue() == null ?"":liger.get("inv_code").getValue().split(",")[0]
        	});  */
        	
    		//加载查询条件
    		grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					{ display: '交易编码', name: 'bid_code', align: 'left', width: '120'},
                     { display: '药品编码', name: 'inv_code', align: 'left', width: '150'},
                     { display: '药品名称', name: 'inv_name', align: 'left',width:'200'},
                     { display: '规格型号', name: 'inv_model', align: 'left',width:'200'},
                     { display: '生产厂商', name: 'fac_name', align: 'left',width: '200'},
                     { display: '计量单位', name: 'unit_name', align: 'left',width: '80'},
                     //{ display: '批号', name: 'batch_no', align: 'left',width:'130'},
                     //{ display: '条形码', name: 'bar_code', align: 'left',width:'100'},
                     { display: '当前库存', name: 'cur_amount', align: 'left',width: '100'},
                     //{ display: '即时库存', name: 'imme_amount', align: 'right',width:'130'
                     //}, 
                     { display: '请领数量', name: 'app_amount', align: 'right',width:'80',
                    	 editor : {
         					type : 'number'
                    	 }
                     },
                     { display: '单价', name: 'price', align: 'left',width: '130',
                    	 render : function(rowdata, rowindex, value) {
           					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
           				}
                     },
                     { display: '是否代销药品', name: 'is_com', align: 'center',width: '130',render:
                    	 function(rowdata,index,value){
                    	 	if(value == 1){
                    	 		return "是";
                    	 	}
                    	 	if(value == 0 ){
                    	 		return "否";
                    	 	}
                     	}
                     }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../../../queryMedInvListApply.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,isAddRow:false,
                     delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     enabledEdit : true,
					 toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true }
					]} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  //确定添加
	function addNew(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.warn('请选择药品!');
			return;
		}else{
			var detail_rows = new StringBuffer();
			detail_rows.append("[");
			$.each(data, function(index, data){
				if(index != 0){
					detail_rows.append(",");
				}
				detail_rows.append('{"inv_id":').append(data.inv_id).append(',');
				detail_rows.append('"inv_no":"').append(data.inv_no).append('",');
				detail_rows.append('"app_amount":').append(data.app_amount==undefined?0:data.app_amount).append('}');
			});
			detail_rows.append("]");
			
			var formPara = {
				store_id :  liger.get("store_id").getValue().split(",")[0],
				store_no :  liger.get("store_id").getValue().split(",")[1],
				allData : detail_rows.toString()
			};
			
			ajaxJsonObjectByUrl("queryStoreInvData.do?isCheck=false", formPara, function(responseData) {
				if(responseData.Rows.length > 0){
					//订单药品
					parent.add_rows(responseData.Rows);
					/* parent.grid.addRow(); */
				}
				this_close();
			});
		}
		
	}
    
    function loadDict(){ 
        //字典下拉框
    	autocompleteAsync("#store_id", "../../../queryMedStore.do?isCheck=false", "id", "text", true, true,{store_id : '${store_id}'},true,'${store_id}');
    	$("#store_id").ligerComboBox({width:180});  
    	$("#store_id").ligerComboBox({disabled:true}); 
    	//autocomplete("#inv_code", "../../../queryMedInvDict.do?isCheck=false", "id", "text", true, true);
    	autocompleteAsync("#med_type_code", "../../../queryPermMedTypeDict.do?isCheck=false", "id", "text", true, true);
    	$("#inv_code").ligerTextBox({width:180}); 

//     	$("#show_zero").prop("checked",true);
//     	$("#is_stop").prop("checked",true);

    	$("#bid_code").ligerTextBox({width:180}); 
    	$("#inv_model").ligerTextBox({width:180}); 

    } 
    
    function this_close(){
		frameElement.dialog.close();
	}
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
	}

    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit">
        <tr>
        </tr>
        <tr>
			<td align="left" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
			<td align="left" class="l-table-edit-td" style="width:200px;"><input name="store_id" type="text" id="store_id" ltype="text" readonly="readonly" disabled="disabled"/></td>
			<td align="left"></td>
			<td align="left" class="l-table-edit-td"  style="padding-left:20px;">药品名称：</td>
			<td align="left" class="l-table-edit-td" style="width:200px;"><input name="inv_code" type="text" id="inv_code" ltype="text"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  >药品类别： </td>
            <td align="left" class="l-table-edit-td">
                <input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:true,maxlength:200}" />
            </td>
            <td align="left"></td>
		</tr> 
		<tr>
		<td align="left" class="l-table-edit-td"  style="padding-left:20px;">规格型号：</td>
			<td align="left" class="l-table-edit-td" style="width:200px;"><input name="inv_model" type="text" id="inv_model" ltype="text"/></td>
			<td align="left"></td>
		<td align="left" class="l-table-edit-td"  style="padding-left:20px;">交易编码：</td>
			<td align="left" class="l-table-edit-td" style="width:200px;"><input name="bid_code" type="text" id="bid_code" ltype="text"/></td>
			<td align="left"></td>
		
		<td>不显示零库存<input name="show_zero" type="checkbox" id="show_zero" ltype="text" /></td>
		<td>不显示停用药品<input name="is_stop" type="checkbox" id="is_stop" ltype="text" /></td>
		 	
			
		</tr>

    </table>
	<div id="maingrid"></div>
</body>
</html>
