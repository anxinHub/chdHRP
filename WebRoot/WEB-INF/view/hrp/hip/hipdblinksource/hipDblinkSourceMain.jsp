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
    
    var is_sync_data =[{ id: 0, text: '否' },{ id: 1, text: '是'}];

    $(function ()
    {
        loadDict();//加载下拉框
    	
    	loadHead(null);	
    	
    	$("#layout1").ligerLayout({rightWidth: 450});

    });
    //查询
    function  query(){
    	
		grid.options.parms = [];grid.options.newPage = 1;
		
		//grid.options.parms.push({name : 'mod_code',value : liger.get("mod_code_search").getValue()});
	
		grid.loadData(grid.where);//加载查询条件
     }
    
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns : [
			           
					{display : 'Dblink视图编码',name : 'dblink_view_code',width: 100,align : 'left',
						render: function (item) {
						
					    return "<a onClick=openUpdate('"+item.dblink_code+"','"+item.mod_code+"','"+item.hip_view_code+"','"+item.his_view_code+"') >"+item.his_view_code+"</a>";
					    
						}
					},
					
					{display : '数据源代码',name : 'ds_name',width: 100,align : 'left',}, 
					
					{display : '模块编码',name : 'mod_name',width: 100,align : 'left'},
					
					{display : 'HIP视图编码',name : 'hip_view_name',width: 100,align : 'left'},
					
					{display : 'HIS视图名称',name : 'his_view_name',width: 100,align : 'left'},
					
					{display : '是否同步',name : 'is_sync',align : 'left',width: 100,align : 'left',
						
						render: function (item) {

							for (var i = 0; i < is_sync_data.length; i++){
	                        	
	                            if (is_sync_data[i]['id'] == item.is_sync){

	                            	return is_sync_data[i]['text'];
	                            	
	                            }
	                            
	                        }

	                    }
						
					}
					],
			dataAction : 'server',dataType : 'server',usePager : true,url : 'queryHipViewSource.do',
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
    
    function openUpdate(obj,obj1,obj2,obj3){
    	
    	var formPara = {
				
    			dblink_code : obj,
    			
    			mod_code : obj1,
    			
    			hip_view_code : obj2,
    			
    			his_view_code : obj3

		};
		
		ajaxJsonObjectByUrl("updateHipViewSourcePage.do?isCheck=false", formPara, function(responseData) {

			liger.get("dblink_code").setValue(responseData.dblink_code);
			
			liger.get("dblink_code").setText(responseData.ds_name);
			
			$("#dblink_code").ligerTextBox({disabled: true});
			
			liger.get("mod_code").setValue(responseData.mod_code);
			
			liger.get("mod_code").setText(responseData.mod_name);
			
			$("#mod_code").ligerTextBox({disabled: true});
			
			liger.get("hip_view_code").setValue(responseData.hip_view_code);
			
			liger.get("hip_view_code").setText(responseData.hip_view_name);
			
			$("#hip_view_code").ligerTextBox({disabled: true});

			$("#his_view_code").val(responseData.his_view_code);$("#his_view_code").ligerTextBox({disabled: true});
			
			$("#his_view_name").val(responseData.his_view_name);
			
			if(responseData.is_sync !='null'){
				
				for (var i = 0; i < is_sync_data.length; i++){
                	
                    if (is_sync_data[i]['id'] == responseData.is_sync){
                    	
                    	liger.get("is_sync").setValue(is_sync_data[i]['id']);
        				
        				liger.get("is_sync").setText(is_sync_data[i]['text']);
                    	
                    }
                    
                }
				
			}
			
			saveFlag = 1;

		});
    }

    function loadDict(){
    	
    	autocompleteAsync("#dblink_code", "../queryHipDblink.do?isCheck=false", "id", "text", true, true, "", true);
    	
    	autocompleteAsync("#hip_view_code", "../queryHipInitView.do?isCheck=false", "id", "text", true, true, "", true);
    	
    	
    	$("#hip_view_code").ligerComboBox({width:180 });
    	
    	$("#his_view_code").ligerTextBox({width:180});
    	
    	$("#his_view_name").ligerTextBox({width:180});

    	$("#is_sync").ligerComboBox({data: is_sync_data,width: 180,value: '0'});

	}  
    function btnAdd(){
    	
    	$("#dblink_code").val('');
    	
    	$("#mod_code").val('');
    	
    	$("#hip_view_code").val('');
    	
    	$("#his_view_code").val('');
    	
    	$("#his_view_name").val('');
    	
    	$("#is_sync").val('');
    	
    	saveFlag = 0;
    	
    	$("#dblink_code").ligerComboBox({disabled: false});
    	
    	$("#mod_code").ligerComboBox({disabled: false});
    	
    	$("#hip_view_code").ligerComboBox({disabled: false});
    	
    	$("#his_view_code").ligerComboBox({disabled: false});
    	
    }
    
    function btnDel(){
    	
    	var dblink_code = liger.get("dblink_code").getValue();
    	
    	var mod_code = liger.get("mod_code").getValue();
    	
    	var hip_view_code = liger.get("hip_view_code").getValue();
    	
    	var his_view_code = $("#his_view_code").val();
    	
		var formPara = {
				
				dblink_code : dblink_code,
    			
    			mod_code : mod_code,
    			
    			hip_view_code : hip_view_code,
    			
    			his_view_code : his_view_code

		};
		$.ligerDialog.confirm('确定删除?', function (yes){if(yes){
			
		ajaxJsonObjectByUrl("deleteHipViewSource.do", formPara, function(responseData) {

			if (responseData.state == "true") {
				
				query();
				
		    	$("#dblink_code").val('');
		    	
		    	$("#mod_code").val('');
		    	
		    	$("#hip_view_code").val('');
		    	
		    	$("#his_view_code").val('');
		    	
		    	$("#his_view_name").val('');
		    	
		    	$("#is_sync").val('');
		    	
		    	saveFlag = 0;
		    	
		    	$("#dblink_code").ligerComboBox({disabled: false});
		    	
		    	$("#mod_code").ligerComboBox({disabled: false});
		    	
		    	$("#hip_view_code").ligerComboBox({disabled: false});
		    	
		    	$("#his_view_code").ligerComboBox({disabled: false});
				
			}
		});}});
    	
    }
    
    function btnSave(){

		var dblink_code = liger.get("dblink_code").getValue();
    	
    	if(!dblink_code){
    		
    		alert("请输入数据源代码 ！");
    	}
    	
		var mod_code = liger.get("mod_code").getValue();
    	
    	if(!mod_code){
    		
    		alert("请输入 模块编码 ！");
    	}
    	
		var hip_view_code = liger.get("hip_view_code").getValue();
    	
    	if(!hip_view_code){
    		
    		alert("请输入 HIP视图编码 ！");
    	}
    	
		var his_view_code = $("#his_view_code").val();
    	
    	if(!his_view_code){
    		
    		alert("请输入 HIS视图编码 ！");
    	}
    	
		var his_view_name = $("#his_view_name").val();
    	
    	if(!his_view_name){
    		
    		alert("请输入 HIS视图名称 ！");
    	}
    	
    	var is_sync = liger.get("is_sync").getValue();
    	
    	if(!is_sync){
    		
    		alert("请输入是否同步 ！");
    	}

		var formPara = {
				
				dblink_code : dblink_code,
				
				mod_code : mod_code,
				
				hip_view_code : hip_view_code,
				
				his_view_code : his_view_code,
				
				his_view_name : his_view_name,
				
				is_sync : is_sync,
				
				saveFlag : saveFlag

		};

		ajaxJsonObjectByUrl("addHipViewSource.do", formPara, function(responseData) {

			if (responseData.state == "true") {
				
				query();
				
			}
		});
    }

</script>

</head>

<body style="padding: 0px; overflow: hidden;" >

	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
   		 <tr>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;"> 模块编码：</td>
            <td align="left" class="l-table-edit-td"><input name="mod_code_search" type="text" id="mod_code_search" ltype="text" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    
    <div id="layout1">
            <div position="center"><div id="maingrid"></div></div>
            <div position="right" title="明细">
	            <table cellpadding="0" cellspacing="0" class="l-table-edit" >
				   		 <tr>
				            <td align="right" class="l-table-edit-td"  style="padding-left: 20px;padding-top: 20px;"> 
				            	<input type="button" value="添加" id="btnAdd" class="l-button l-button-submit" onclick="btnAdd()"/> 
							</td>
				            <td align="left" class="l-table-edit-td" style="padding-left: 20px;padding-top: 20px;">
				            	<input type="button" value="保存" id="btnSave" class="l-button l-button-submit"  onclick="btnSave()"/> 
				            </td>
				            <td align="left" class="l-table-edit-td" style="padding-left: 20px;padding-top: 20px;">
				            	<input type="button" value="删除" id="btnDel" class="l-button l-button-submit"  onclick="btnDel()"/> 
				            </td>
				        </tr> 
				</table>
            	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">信息同步名称：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="dblink_sync_name" type="text" id="dblink_sync_name" ltype="text" validate="{required:true,maxlength:50}"/></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">Dblink源代码：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="dblink_code" type="text" id="dblink_code" ltype="text" validate="{required:true}"/></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">Dblink视图编码：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="dblink_view_code" type="text" id="dblink_view_code" ltype="text" validate="{required:true}"/></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">Dblink视图名称：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="dblink_view_name" type="text" id="dblink_view_name" ltype="text" validate="{required:true}"/></td>
			            <td align="left"></td>
			        </tr> 
					<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">Dblink字段：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="dblink_columns" type="text" id="dblink_columns" ltype="text" validate="{required:true,maxlength:200}"/></td>
			            <td align="left">多个字段","(非中文符号)隔开</td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">HIP视图：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="hip_view_code" type="text" id="his_view_code" ltype="text" validate="{required:true}"/></td>
			            <td align="left"></td>
			        </tr> 
					<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">HIP字段：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="hip_columns" type="text" id="hip_columns" ltype="text" validate="{required:true,maxlength:200}"/></td>
			            <td align="left">需与Dblink字段个数一致</td>
			        </tr> 
					<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">唯一字段标识：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="unique_column" type="text" id="unique_column" ltype="text" validate="{required:true,maxlength:50}"/></td>
			            <td align="left"></td>
			        </tr>
					<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">是否定时同步：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="is_sync" type="text" id="is_sync" ltype="text" validate="{required:true}"/></td>
			            <td align="left"></td>
			        </tr> 
			    </table>
            </div>  
	</div>
</body>
</html>
