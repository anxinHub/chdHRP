<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

    var grid;
    
    var gridManager = null;
    
    var saveFlag;

    $(function ()
    {
        loadDict();//加载下拉框
    	
    	loadHead(null);	
    	
    	$("#layout1").ligerLayout({rightWidth: 450});

    });
    //查询
    function  query(){
    	
		grid.options.parms = [];grid.options.newPage = 1;
		
		grid.options.parms.push({name : 'ds_code_search',value : $("#ds_code_search").val()});
	
		grid.loadData(grid.where);//加载查询条件
		
     }
    
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns : [
					{display : '数据源代码',name : 'ds_code',width: 100,align : 'left',
						render: function (item) {
							
	                        return "<a onClick=openUpdate('"+item.ds_code+"') >"+item.ds_code+"</a>";
	                        
	                    }
					}, 
					{display : '数据源名称',name : 'ds_name',width: 250,align : 'left'},
					{display : '数据源描述',name : 'ds_note',align : 'left',width: 250,align : 'left'}
					],
			dataAction : 'server',dataType : 'server',usePager : true,url : 'queryHipDsCof.do',
			width : '100%',height : '100%',checkbox : true,selectRowButtonOnly : true,alternatingRow: false,//heightDiff: -10,
			toolbar : {
				items : [
				         {text : '查询',id : 'search',click : query,icon : 'search'},
				         {line : true}
				         ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function openUpdate(obj){
    	
    	var formPara = {
				
    			ds_code : obj

		};
		
		ajaxJsonObjectByUrl("updateHipDsCofPage.do?isCheck=false", formPara, function(responseData) {

			$("#ds_code").val(responseData.ds_code);$("#ds_code").ligerTextBox({disabled: true});
			
			$("#ds_name").val(responseData.ds_name);
			
			$("#url_str").val(responseData.url_str);
			
			if(responseData.db_user !='null'){
				
				$("#db_user").val(responseData.db_user);
				
			}else{
				
				$("#db_user").val('');
				
			}
			
			if(responseData.db_psw !='null'){
				
				$("#db_psw").val(responseData.db_psw);
				
			}else{
				
				$("#db_psw").val('');
				
			}
			
			if(responseData.ds_note !='null'){
				
				$("#ds_note").val(responseData.ds_note);
				
			}else{
				
				$("#ds_note").val('');
				
			}
			
			
			
			saveFlag = 1;

		});
    }

    function loadDict(){
    	
    	$("#ds_code_search").ligerTextBox({width:180});
    	
    	$("#ds_code").ligerTextBox({width:180});
    	
    	$("#ds_name").ligerTextBox({width:180});
    	
    	$("#db_user").ligerTextBox({width:180});
    	
    	$("#db_psw").ligerTextBox({width:180});
    	
    	$("#url_str").ligerTextBox({width:300});

	}  
    function btnAdd(){
    	
    	$("#ds_code").val('');
    	
    	$("#ds_name").val('');
    	
    	$("#db_user").val('');
    	
    	$("#db_psw").val('');
    	
    	$("#url_str").val('');
    	
    	$("#ds_note").val('');
    	
    	saveFlag = 0;
    	
    	$("#ds_code").ligerTextBox({disabled: false});
    	
    }
    
    function btnDel(){
    	
    	var ds_code = $("#ds_code").val();
    	
		var formPara = {
				
				ds_code : ds_code
				
		};
		$.ligerDialog.confirm('确定删除?', function (yes){if(yes){
		ajaxJsonObjectByUrl("deleteHipDsCof.do", formPara, function(responseData) {

			if (responseData.state == "true") {
				
				query();
				
		    	$("#ds_code").val('');
		    	
		    	$("#ds_name").val('');
		    	
		    	$("#db_user").val('');
		    	
		    	$("#db_psw").val('');
		    	
		    	$("#url_str").val('');
		    	
		    	$("#ds_note").val('');
		    	
		    	saveFlag = 0;
		    	
		    	$("#ds_code").ligerTextBox({disabled: false});
				
			}
		});}});
    	
    }
    
    function btnSave(){

		var ds_code = $("#ds_code").val();
    	
    	if(!ds_code){
    		
    		alert("请输入数据源代码 ！");
    		
    	}
    	
		var ds_name = $("#ds_name").val();
    	
    	if(!ds_code){
    		
    		alert("请输入 数据源名称 ！");
    		
    	}
    	
		var db_user = $("#db_user").val();
    	
    	if(!ds_code){
    		
    		alert("请输入 用户名 ！");
    		
    	}
    	
		var db_psw = $("#db_psw").val();
    	
    	if(!ds_code){
    		
    		alert("请输入 密码 ！");
    		
    	}
    	
		var url_str = $("#url_str").val();
    	
    	if(!ds_code){
    		
    		alert("请输入连接字符串 ！");
    		
    	}
    	
    	var ds_note = $("#ds_note").val();
    	
    	if(!ds_code){
    		
    		alert("请输入数据源描述 ！");
    		
    	}
    	
    	

		var formPara = {
				
				ds_code : ds_code,
				
				ds_name : ds_name,
				
				db_user : db_user,
				
				db_psw : db_psw,
				
				url_str : url_str,
				
				ds_note : ds_note,
				
				saveFlag : saveFlag

		};

		ajaxJsonObjectByUrl("addHipDsCof.do", formPara, function(responseData) {

			if (responseData.state == "true") {
				
				query();
				
			}
		});
    }

</script>

</head>
</head>

<body style="padding: 0px; overflow: hidden;" >

	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
   		 <tr>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;"> 数据源代码：</td>
            <td align="left" class="l-table-edit-td"><input name="ds_code_search" type="text" id="ds_code_search" ltype="text" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    
    <div id="layout1">
            <div position="center"><div id="maingrid"></div></div>
            <div position="right" title="明细">
	            <table cellpadding="0" cellspacing="0" class="l-table-edit" >
				   		 <tr>
				            <td align="right" class="l-table-edit-td"  style=padding-left:20px;padding-top:20px;">
				            	<input type="button" value="添加" id="btnAdd" class="l-button l-button-submit" onclick="btnAdd()"/> 
							</td>
				            <td align="left" class="l-table-edit-td" style=padding-left:20px;padding-top:20px;">
				            	<input type="button" value="保存" id="btnSave" class="l-button l-button-submit"  onclick="btnSave()"/> 
				            </td>
				            <td align="left" class="l-table-edit-td" style=padding-left:20px;padding-top:20px;">
				            	<input type="button" value="删除" id="btnDel" class="l-button l-button-submit"  onclick="btnDel()"/> 
				            </td>
				        </tr> 
				</table>
            	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
			   		 <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">数据源代码：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="ds_code" type="text" id="ds_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">数据源名称：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="ds_name" type="text" id="ds_name" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">用 户 名：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="db_user" type="text" id="db_user" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">密   码：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="db_psw" type="text" id="db_psw" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">连接字符串：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="url_str" type="text" id="url_str" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">数据源描述：</td>
			            <td align="left" class="l-table-edit-td"  style="padding-top:20px;">
			           		<textarea cols="100" rows="4" class="l-textarea" id="ds_note"  name="ds_note" style="width:300px" validate="{required:true}" ></textarea>
			            </td>
			            <td align="left"></td>
			        </tr> 
			    </table>
            </div>  
	</div>

	
</body>
</html>
