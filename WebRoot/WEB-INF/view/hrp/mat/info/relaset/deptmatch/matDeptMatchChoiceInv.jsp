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
        loadDict();//加载数据
    	loadHead(null);
    	query();
		loadHotkeys();
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
                     { display: '材料编码', name: 'inv_code', align: 'left', width: '150'
                     },
                     { display: '材料名称', name: 'inv_name', align: 'left',width:'200'},
                     { display: '规格型号', name: 'inv_model', align: 'left',width:'200'},
                     { display: '包装规格', name: 'inv_structure', align: 'left'},
                     { display: '生产厂商', name: 'fac_name', align: 'left',width: '200'},
                     { display: '计量单位', name: 'unit_name', align: 'left',width: '80'},
                     /* { display: '批号', name: 'batch_no', align: 'left',width:'130'},
                     { display: '条形码', name: 'bar_code', align: 'left',width:'100'},
                     { display: '当前库存', name: 'cur_amount', align: 'left',width: '100'},
                     { display: '即时库存', name: 'imme_amount', align: 'right',width:'130'
                     }, */
                     { display: '请领数量', name: 'app_amount', align: 'right',width:'80',
                    	 editor : {
         					type : 'number'
                    	 }
                     },
                     { display: '单价', name: 'price', align: 'left',width: '130',
                    	 render : function(rowdata, rowindex, value) {
           					return value == null ? "" : formatNumber(value, '${p04006}', 1);
           				}
                     },
                     { display: '是否代销材料', name: 'is_com', align: 'center',width: '130',render:
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
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../../queryMatInvListByMatch.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,isAddRow:false,
                     //delayLoad:true,
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
			
			ajaxJsonObjectByUrl("queryDeptInvData.do?isCheck=false", formPara, function(responseData) {
				if(responseData.Rows.length > 0){
					//订单材料
					/*parent.grid.options.data = {Rows:responseData.Rows};
					parent.grid._setData();
					parent.grid.addRow();*/
					parent.add_rows(responseData.Rows);
					/* parent.grid.addRow(); */
				}
				this_close();
			});
		}
		
	}
    
    function loadDict(){
        //字典下拉框
    	
    	//autocomplete("#inv_code", "../../../queryMatInvDict.do?isCheck=false", "id", "text", true, true);
    	autocompleteAsync("#mat_type_code", "../../queryPermMatTypeDict.do?isCheck=false", "id", "text", true, true, "", false, false, 200, false, 200);
    	$("#inv_code").ligerTextBox({width:180}); 
    	$("#inv_model").ligerTextBox({width:180}); 
    	autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false","id", "text", true, true, "", false, false, 200, false, 200);
    	autoCompleteByData("#source_plan", matInv_sourcePlan.Rows, "id", "text", true, true, "", false);
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
    <table cellpadding="0" cellspacing="0" class="l-table-edit">
        <tr>
			<td align="right" class="l-table-edit-td"  >物资类别：</td>
            <td align="left" class="l-table-edit-td">
                    	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:200}" />
            </td>
          <!--   <td align="left"></td> -->
            
			<td align="left" class="l-table-edit-td"  style="padding-left:20px;">物资材料：</td>
			<td align="left" class="l-table-edit-td" style="width:200px;"><input name="inv_code" type="text" id="inv_code" ltype="text"/></td>
<!-- 			<td align="left"></td> -->
			
			<td align="left" class="l-table-edit-td"  style="padding-left:20px;">规格型号：</td>
			<td align="left" class="l-table-edit-td" style="width:200px;"><input name="inv_model" type="text" id="inv_model" ltype="text"/></td>
	<!-- 		<td align="left"></td> -->
			
		</tr> 
		<tr>
				<td align="left" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
			<td align="left" class="l-table-edit-td" style="width:200px;">
				<input name="sup_code" type="text" id="sup_code" ltype="text"/>
			</td>
			
			
			<td align="right" class="l-table-edit-td" >计划来源：</td>
			<td align="left" class="l-table-edit-td">
				<input name="source_plan" type="text" id="source_plan" ltype="text"/>
			</td>
			
			
		</tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
