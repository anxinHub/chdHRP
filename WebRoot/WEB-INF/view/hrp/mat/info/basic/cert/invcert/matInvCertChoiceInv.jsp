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
    var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
    var sup_id = "";
    var sup_text = "";
    
    $(function (){
    	if(dialog.get("data")){
	        sup_id = dialog.get("data").sup_id;
	        sup_text = dialog.get("data").sup_text;
    	}
    	loadHead(null);
    	 loadDict()//加载下拉框
    	query();
		loadHotkeys();
    });
    //查询
    function  query(){
    		grid.options.parms=[];
			grid.options.newPage=1;
			grid.options.parms.push({name:'cert_id',value:parent.cert_id}); 
			grid.options.parms.push({name:'inv_msg',value:$("#inv_msg").val()}); 
			grid.options.parms.push({name:'bid_code',value:$("#bid_code").val()}); 
			 grid.options.parms.push({
				name : 'mat_type_code',//物资类别
				value : liger.get("mat_type_code").getText() == null ? "" :liger.get("mat_type_code").getText().split(" ")[0]
	    	}); 
	    	grid.options.parms.push({
				name : 'store_id',
				value : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[0]
			}); 
			grid.options.parms.push({name:'inv_model',value:$("#inv_model").val()}); 
			grid.options.parms.push({name:'fac_id',value:liger.get("fac_code").getValue()}); 
			grid.options.parms.push({name:'sup_id',value:liger.get("sup_id").getValue()}); 
    		//加载查询条件
    		grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					{ display: '交易编码', name: 'bid_code', align: 'left',width:'100'},
                     { display: '材料编码', name: 'inv_code', align: 'left', width: '120'
                     },
                     { display: '材料名称', name: 'inv_name', align: 'left',width:'240'},
                     { display: '规格型号', name: 'inv_model', align: 'left',width:'250'},
                     { display: '计量单位', name: 'unit_name', align: 'center',width: '50'},
                     {display : '单价',name : 'plan_price',align : 'left',width: '80'},
                     { display: '生产厂商', name: 'fac_name', align: 'left',width: '250'},
                     { display: '供应商', name: 'sup_name', align: 'left',width: '250'},
                     { display: '注册证号', name: 'cert_code', align: 'left',width: '200'}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatCertInvListObject.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,isAddRow:false,
                     delayLoad: true,
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
				detail_rows.append('"inv_no":"').append(data.inv_no).append('"}');
			});
			detail_rows.append("]");
			
			var formPara = {
				allData : detail_rows.toString()
			};
			
			ajaxJsonObjectByUrl("queryMatCertInvChoiceInvList.do?isCheck=false", formPara, function(responseData) {
				if(responseData.Rows.length > 0){
					//父页面回显材料
					parent.add_rows(responseData.Rows);
					/* parent.grid.addRow(); */
				}
				this_close();
			});
		}
		
	}
    
    function this_close(){
		frameElement.dialog.close();
	}
    //下拉框
    function loadDict(){
     	autocompleteAsync("#mat_type_code", "../../../../queryPermMatTypeDict.do?isCheck=false", "id", "text", true, true); 
    	autocomplete("#store_id", "../../../../queryMatStore.do?isCheck=false", "id", "text", true, true);
    	autocomplete("#fac_code", "../../../../queryHosFac.do?isCheck=false", "id", "text", true, true);
    	if(sup_id){
    		autocompleteAsync("#sup_id","../../../../queryHosSup.do?isCheck=false","id","text",true,true);
    		liger.get("sup_id").setValue(sup_id);
    		liger.get("sup_id").setText(sup_text);
    	}else{
        	autocomplete("#sup_id","../../../../queryHosSup.do?isCheck=false","id","text",true,true);
    	}
        $("#inv_msg").ligerTextBox({width:160});
        $("#inv_model").ligerTextBox({width:160});
        $("#bid_code").ligerTextBox({width:160});
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
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料信息：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="inv_msg" type="text" id="inv_msg" ltype="text" validate="{required:true,maxlength:50}" />
            </td>
            
            <td align="right" class="l-table-edit-td"  >材料分类：</td>
			<td align="left" class="l-table-edit-td">
            	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true}" />
			</td> 
         
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true}" />
            </td>
        </tr>
		<tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">规格型号：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:true,maxlength:100}" />
            </td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">生产厂商：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="fac_code" type="text" id="fac_code" ltype="text" validate="{required:true,maxlength:100}" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">交易编码：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="bid_code" type="text" id="bid_code" ltype="text" validate="{required:true,maxlength:50}" />
            </td>
		</tr>
		<tr>
		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:100}" />
            </td>
		</tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
