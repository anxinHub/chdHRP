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
    var store_id = "";
    $(function (){
        loadDict();//加载数据
    	loadHead(null);
    	
		loadHotkeys();
		store_id='${store_id}';
		query();
    });
    
   /*  $(function(){
    	setTimeout(function() { //当数据为空时 默认新增一行
			query();
		}, 500);
    }); */
    
    //查询
    function  query(){
	    	var allData = parent.gridManager.getData();
	    	var str = [];
	    	if(allData.length != 0){
		    	$.each(allData,function(i, v){
		    		if(v.inv_id == null){
		    			return;
		    		}
		    		str.push(v.inv_id);
		    	});
	    	}
    		grid.options.parms=[];
			grid.options.newPage=1;
        	//根据表字段进行添加查询条件
    	   grid.options.parms.push({
    			name : 'store_id',//物资类别
    			value : store_id.split(",")[0]
        	});
        	grid.options.parms.push({
    			name : 'mat_type_code',//物资类别
    			value : liger.get("mat_type_code").getText() == null ? "" :liger.get("mat_type_code").getText().split(" ")[0]
        	});
        	grid.options.parms.push({
        		name:'inv_code',
        		value: $("#inv_code").val() 
        	}); 
        	grid.options.parms.push({
        		name:'inv_model',
        		value: $("#inv_model").val() 
        	}); 
        	
        	grid.options.parms.push({
        		name:'inv_ids',
        		value: str
        	}); 
        	grid.options.parms.push({
    			name : 'sup_id',
    			value : liger.get("sup_code").getValue() == null ? "" :liger.get("sup_code").getValue().split(",")[0]
        	});
        	grid.options.parms.push({
    			name : 'source_plan',
    			value : liger.get("source_plan").getValue() == null ? "" :liger.get("source_plan").getValue()
        	});
        	/* grid.options.parms.push({
        		name:'inv_id',
        		value:liger.get("inv_code").getValue() == null ?"":liger.get("inv_code").getValue().split(",")[0]
        	});  */
        	//传递父页面inv_ids，不再执行两边
        	/*grid.set('url','../../queryMatInvList.do?isCheck=false');*/
    		//加载查询条件
    		grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					 { display: '交易编码', name: 'bid_code', align: 'left', width: '150'},
                     { display: '材料编码', name: 'inv_code', align: 'left', width: '120'},
                     { display: '材料名称', name: 'inv_name', align: 'left',width:'200'},
                     { display: '规格型号', name: 'inv_model', align: 'left',width:'200'},
                     { display: '生产厂商', name: 'fac_name', align: 'left',width: '200'},
                     { display: '计量单位', name: 'unit_name', align: 'left',width: '80'},
                     /* { display: '当前库存', name: 'cur_amount', align: 'right',width: '100',
                    	 render:function(rowdata){
			            	return formatNumber(rowdata.cur_amount ==null ? 0 : rowdata.cur_amount,2,1);
			             }	 
                     }, */
                     { display: '计划来源', name: 'source_plan', align: 'left',width: '100', reg:"1=科室申领,else=仓库报备",
           				render : function(rowdata, rowindex, value) {
           					return rowdata.source_plan == 1 ? "科室申领" : "仓库报备";
           				}
                     },
                     { display: '请领数量', name: 'app_amount', align: 'right',width:'80',
                    	 editor : {
         					type : 'number'
                    	 }
                     },
                     { display: '单价', name: 'price', align: 'right',width: '90',
                    	 render : function(rowdata, rowindex, value) {
           					return value == null ? "" : formatNumber(value, '${p04006}', 1);
           				}
                     },
                     { display: '是否代销材料', name: 'is_com', align: 'left',width: '90',render:
                    	 function(rowdata,index,value){
                    	 	if(value == 1){
                    	 		return "是";
                    	 	}
                    	 	if(value == 0 ){
                    	 		return "否";
                    	 	}
                     	}
                     },
                     { display: '供应商', name: 'sup_name', align: 'left',width: '200'},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatInvListByStoreMatch.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,isAddRow:false,
                     delayLoad:true,pageSize:500,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     enabledEdit : true,
					 toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },{ line:true },
                     	{ text: '关闭（<u>C</u>）', id:'close', click: this_close, icon:'close' },{ line:true }
                     	
					]} 
                   });
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function this_close() {
		frameElement.dialog.close();
	}
    
  //确定添加
	function addNew(){
		var data = gridManager.getCheckedRows();
		
		if (data.length == 0){
			$.ligerDialog.warn('请选择材料!');
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
				allData : detail_rows.toString()
			};
			
			ajaxJsonObjectByUrl("queryStoreInvData.do?isCheck=false", formPara, function(responseData) {
				if(responseData.Rows.length > 0){
					parent.add_rows(responseData.Rows);
				}
				this_close();
			});
		}
		
	}
    
    function loadDict(){
        //字典下拉框
        
    	$("#store_id").ligerComboBox({width:180,disabled:false,cancelable: false});
    	liger.get("store_id").setValue('${store_id}');
		liger.get("store_id").setText('${store_text}');
		
    	autocompleteAsync("#mat_type_code", "../../queryPermMatTypeDict.do?isCheck=false", "id", "text", true, true);
    	autoCompleteByData("#source_plan", matInv_sourcePlan.Rows, "id", "text", true, true, "", false);
    	autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false","id", "text", true, true, "", false, false, 200, false, 200);
    	$("#store_id").ligerTextBox({width:180}); 
    	$("#mat_type_code").ligerTextBox({width:180}); 
    	$("#inv_code").ligerTextBox({width:180}); 
    	$("#inv_model").ligerTextBox({width:180});
    	$("#sup_code").ligerTextBox({width:180});
    	$("#source_plan").ligerTextBox({width:180});
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
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border="0">
        <tr>
			<td align="right" class="l-table-edit-td"  width="10%" >仓库：</td>
			<td align="left" class="l-table-edit-td"  width="20%" >
				<input name="store_id" type="text" id="store_id" ltype="text"/>
			</td>
			
			<td align="right" class="l-table-edit-td"  width="10%" >物资类别：</td>
            <td align="left" class="l-table-edit-td" width="20%" >
            	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:200}" />
            </td>
            
			<td align="right" class="l-table-edit-td"  width="10%" >物资材料：</td>
			<td align="left" class="l-table-edit-td"  width="20%" >
				<input name="inv_code" type="text" id="inv_code" ltype="text"/>
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%" >规格型号：</td>
			<td align="left" class="l-table-edit-td"  width="20%" >
				<input name="inv_model" type="text" id="inv_model" ltype="text"/>
			</td>
			
			<td align="right" class="l-table-edit-td"  width="10%" >供应商：</td>
			<td align="left" class="l-table-edit-td" width="20%" >
				<input name="sup_code" type="text" id="sup_code" ltype="text"/>
			</td>
			
			<td align="right" class="l-table-edit-td"  width="10%" >计划来源：</td>
			<td align="left" class="l-table-edit-td" width="20%" >
				<input name="source_plan" type="text" id="source_plan" ltype="text"/>
			</td>
		</tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
