<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var dialog = frameElement.dialog; 
	var grid;
	
	var gridManager = null;
	
	var meta_code = '${meta_code}';
	
	var mod_code = '${mod_code}';
	
	var subj_code =  '${subj_code}';
	var acc_year =  '${acc_year}';
	
	var table_id =  '${table_id}';
	
	
	var store_id =  '${store_id}';
	
	var source_id =  '${source_id}';
	
	if(table_id==""){
		table_id="AAAAAAAAAAAA";//sql需要用到这个参数,不允许为空,否则会返回list
	}
	
	var type_id =  '${type_id}';
	
	var sub_type_id =  '${type_id}';
	
	var code_field = '${code_field}';
	
	var parms ={meta_code:meta_code,mod_code:mod_code,acc_year:acc_year,store_id:store_id,source_id:source_id};

	$(function() {
		
		
		loadDict();
	
		loadHead(null); 
	
	});

	//查询
	function query() {//根据表字段进行添加查询条件
		
		grid.options.parms = [];grid.options.newPage = 1;
	
		grid.options.parms.push({name : 'subj_like',value : $("#subj_like").val()});
		
		grid.options.parms.push({name : 'meta_code',value : meta_code});
		
		grid.options.parms.push({name : 'mod_code',value : mod_code});
		
		grid.options.parms.push({name : 'acc_year',value : acc_year});
		
		grid.options.parms.push({name : 'store_id',value : store_id});
		
		grid.options.parms.push({name : 'source_id',value : source_id});
	
		grid.loadData(grid.where);//加载查询条件
		
	}

	function loadHead() {

		grid = $("#maingrid").ligerGrid({
					columns : [
							{display : '科目编码',name : 'subj_code',width: 150,align : 'left'}, 
							{display : '科目名称',name : 'subj_name',width: 250,align : 'left'}
							],
					dataAction : 'server',dataType : 'server',usePager : false,url : 'queryAccBusiRelaForAccSubj.do?isCheck=false',parms:parms,isChecked: f_isChecked,
					width : '100%',height : '100%',checkbox : true,selectRowButtonOnly : true,isSingleCheck:true,alternatingRow: false,//heightDiff: -10,
					toolbar : {
						items : [
						         {text : '查询',id : 'search',click : query,icon : 'search'},
						         {line : true},
						         {text : '保存',id : 'save',click : saveAccBusiMap,icon : 'save'},
						         {line : true},
						         {text : '关闭',id : 'close',click : myClose,icon : 'close'},
						         {line : true}
						         ]
					}
				});
	
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function f_isChecked(rowdata){
		
		  if(subj_code ){
			
			if (rowdata.subj_code == subj_code){
				
				return true;
				
			}
			
			return false;	
		}
        
		return false; 
        
    }


	function loadDict() {

    	$("#subj_like").ligerTextBox({width:180});
    	
	}
	
	function saveAccBusiMap(){
		
		var row = grid.getSelectedRow();
		  
		if(row == null){ 
			
			var formPara = {
					
					meta_code : meta_code,
					
					mod_code : mod_code,
					
					table_name : table_id,
					
					type_id : type_id,
					
					sub_type_id : sub_type_id,
					
					acc_year : acc_year,
					
					store_id:store_id,
					
					source_id:source_id

			};
			
			ajaxJsonObjectByUrl("deleteAutoSet.do?isCheck=false", formPara, function(responseData) {

				if (responseData.state == "true") {
					if(type_id!=""){
					    parent.query();
					}
					dialog.close();
				} 
			}); 
		}else{
		 
		var formPara = {
				
				code_field:code_field,
				
				table_id:table_id,
				
				meta_code :meta_code,
				
				mod_code : mod_code,
				
				type_id : type_id,
				
				sub_type_id : sub_type_id,
				
				subj_code : row.subj_code,
				
				acc_year:acc_year,
				
				store_id:store_id,
				
				source_id:source_id
				
		};
		
		ajaxJsonObjectByUrl("saveAccBusiMap.do?isCheck=false", formPara, function(responseData) {

				if (responseData.state == "true") {
					if(type_id!=""){
						parent.query();
					}
					dialog.close();
				} 
		});
		
		}
	}
	
	 function myClose(){
	    	dialog.close();
	    }


</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	
   <table cellpadding="0" cellspacing="0" class="l-table-edit" >
   		 <tr>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">科目信息：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_like" type="text" id="subj_like" ltype="text" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
