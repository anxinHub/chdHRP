<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	var grid;
	
	var gridManager = null;
	
	var meta_code = '${meta_code}';
	
	var mod_code = '${mod_code}';
	
	var table_id =  '${table_id}';
	var acc_year =  '${acc_year}';
	//var level_field = '${level_field}';
	
	var level_field_data;
	
	var parms ={meta_code:meta_code,mod_code:mod_code,table_id:table_id,acc_year:acc_year};

	$(function() {

		loadDict();
	
		loadHead(null); 
	
	});

	//查询
	function query() {//根据表字段进行添加查询条件
		
		grid.options.parms = [];grid.options.newPage = 1;
	
		grid.options.parms.push({name : 'type_code',value : liger.get("type_code").getValue()});
		
		/* if(level_field !=''){
			
			grid.options.parms.push({name : 'level_field',value : liger.get("level_field").getValue()});
			
		} */
		
		
		grid.options.parms.push({name : 'table_id',value : table_id});
		
		grid.options.parms.push({name : 'meta_code',value : meta_code});
		
		grid.options.parms.push({name : 'mod_code',value : mod_code});
		grid.options.parms.push({name : 'acc_year',value : acc_year});
		
	
		grid.loadData(grid.where);//加载查询条件
		
	}

	function loadHead() {
		
		grid = $("#maingrid").ligerGrid({
					columns : [
							{ display: 'HRP库房编码', name: 'store_code', align: 'left'},
		                    { display: 'HRP库房名称', name: 'store_name', align: 'left'
							 }
							],
						dataAction : 'server',dataType : 'server',usePager : false,url : '../../../../sys/store/queryStore.do?isCheck=false',parms:parms,
						width : '100%',height : '100%',checkbox : true,selectRowButtonOnly : true,alternatingRow: false,//heightDiff: -10,
						toolbar : {
						items : [
				         {text : '查询',id : 'search',click : query,icon : 'search'},
				         {line : true},
				         {text : '关闭',id : 'close',click : myClose,icon : 'close'},
				         {line : true}
				         ]
					}
				});
	
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function loadDict() {

    	$("#type_code").ligerTextBox({width:180});

    	if(level_field !=''){
    		
    		level_field_data =  "[";
    		
			for(var i=1;i<parseInt(level_field)+1;i++){
				
				level_field_data =level_field_data +"{text: '"+i+"', id: "+i+"},";
				
			}
			
			level_field_data = level_field_data.substring(0,level_field_data.length-1);
    		
			level_field_data = level_field_data+"]";

			$("#level_field").ligerComboBox({data: eval(level_field_data),width:180,valueField:'id',textField:'text'}); 

    	}else{
    		
    		$("#level_field").ligerComboBox({disabled: true,width:180}); 
    		
    	}
	}
	
	function saveAccMedStoreRef(obj){
    	
		var data = grid.getSelectedRows();
		
        if (data.length == 0){
        	
        	$.ligerDialog.error('请选择行');
        	
        }else{
            var ParamVo =[];
         
            $(data).each(function (){
            	
				ParamVo.push(
				
				this.store_id   +"@"+ 
				'${his_store_code}'+"@"+
				'${store_flag}'
				)
            });
            
            ajaxJsonObjectByUrl("addHisAccMedStoreRef.do",{ParamVo : ParamVo.toString()},function (responseData){
             	
            	if(responseData.state=="true"){
             			
            		parent.query();
             	
            	}
           
            });
        
        }
		
    }
	
	
	function loadDict(){
		autocomplete("#type_code","../../../../mat/queryMatStoreType.do?isCheck=false","id","text",true,true);

	}
	
    function myClose(){
    	
    	dialog.close();
    	
    }

</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	
   <table cellpadding="0" cellspacing="0" class="l-table-edit" >
   		 <tr>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">类别编码：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" /></td>
            <td align="left"></td>
            <!-- <td align="right" class="l-table-edit-td"  style=padding-left:20px;">类别级次：</td>
            <td align="left" class="l-table-edit-td"><input name="level_field" type="text" id="level_field" ltype="text" /></td>
            <td align="left"></td> -->
        </tr>
    </table>

	<div id="maingrid"></div>

</body>
</html>
