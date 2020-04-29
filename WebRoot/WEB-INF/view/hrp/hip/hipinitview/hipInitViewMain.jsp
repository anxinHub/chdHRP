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
    
    var view_level_data =[{ id: 0, text: '集团' },{ id: 1, text: '医院'},{ id: 2, text: '账套'},{ id: 3, text: '年度'}];

    $(function ()
    {
        loadDict();//加载下拉框
    	
    	loadHead(null);	
    	
    	$("#layout1").ligerLayout({rightWidth: 450});

    });
    //查询
    function  query(){
    	
		grid.options.parms = [];grid.options.newPage = 1;
		
		grid.options.parms.push({name : 'mod_code',value : liger.get("mod_code_search").getValue()});
	
		grid.loadData(grid.where);//加载查询条件
		
     }
    
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns : [
					{display : 'HIP视图编码',name : 'hip_view_code',width: 100,align : 'left',
						render: function (item) {
							
	                        return "<a onClick=openUpdate('"+item.mod_code+"','"+item.hip_view_code+"') >"+item.hip_view_code+"</a>";
	                        
	                    }
					},
					
					{display : '模块编码',name : 'mod_name',width: 100,align : 'left'}, 
					
					{display : 'HIP视图名称',name : 'hip_view_name',width: 250,align : 'left'},
					
					{display : '映射关系表',name : 'ref_tab_name',width: 100,align : 'left'},
					
					{display : '层级',name : 'view_level',align : 'left',width: 60,align : 'left',
						
						render: function (item){
							for (var i = 0; i < view_level_data.length; i++){
	                        	
	                            if (view_level_data[i]['id'] == item.view_level){

	                            	return view_level_data[i]['text'];
	                            	
	                            }
	                            
	                        }
 						}
					}
					],
			dataAction : 'server',dataType : 'server',usePager : true,url : 'queryHipInitView.do',
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
    
    function openUpdate(obj,obj1){
    	
    	var formPara = {
				
    			mod_code : obj,
    			
    			hip_view_code : obj1

		};
		
		ajaxJsonObjectByUrl("updateHipInitViewPage.do?isCheck=false", formPara, function(responseData) {

			liger.get("mod_code").setValue(responseData.mod_code);
			
			liger.get("mod_code").setText(responseData.mod_name);
			
			$("#mod_code").ligerTextBox({disabled: true});
			
			$("#hip_view_code").val(responseData.hip_view_code);$("#hip_view_code").ligerTextBox({disabled: true});
			
			$("#hip_view_name").val(responseData.hip_view_name);
			
			$("#ref_tab_name").val(responseData.ref_tab_name);
			
			if(responseData.view_level !='null'){
				
				for (var i = 0; i < view_level_data.length; i++){
                	
                    if (view_level_data[i]['id'] == responseData.view_level){
                    	
                    	liger.get("view_level").setValue(view_level_data[i]['id']);
        				
        				liger.get("view_level").setText(view_level_data[i]['text']);
                    	
                    }
                    
                }
				
			}
			
			saveFlag = 1;

		});
    }

    function loadDict(){
    	
    	autocomplete("#mod_code_search","../querySysMod.do?isCheck=false","id","text",true,true,"",false,false,'180');
    	
    	autocomplete("#mod_code","../querySysMod.do?isCheck=false","id","text",true,true,"",true,false,'180');
    	
    	$("#hip_view_code").ligerTextBox({width:180});
    	
    	$("#hip_view_name").ligerTextBox({width:180});
    	
    	$("#ref_tab_name").ligerTextBox({width:180});

		$("#view_level").ligerComboBox({data: view_level_data,width: 180,value: '0'});

	}  
    function btnAdd(){

    	$("#hip_view_code").val('');
    	
    	$("#hip_view_name").val('');
    	
    	$("#ref_tab_name").val('');

    	$("#mod_code").ligerTextBox({disabled: false});$("#hip_view_code").ligerTextBox({disabled: false});

    	
    	saveFlag = 0;
    	
    }
    
    function btnDel(){
    	
    	var mod_code = liger.get("mod_code").getValue();
    	
    	var hip_view_code = $("#hip_view_code").val();
    	
		var formPara = {
				
				mod_code : mod_code,
				
				hip_view_code : hip_view_code
				
		};
		$.ligerDialog.confirm('确定删除?', function (yes){if(yes){
		ajaxJsonObjectByUrl("deleteHipInitView.do", formPara, function(responseData) {

			if (responseData.state == "true") {
				
				query();
				
				$("#mod_code").val('');
		    	
		    	$("#hip_view_code").val('');
		    	
		    	$("#hip_view_name").val('');
		    	
		    	$("#ref_tab_name").val('');

		    	$("#mod_code").ligerTextBox({disabled: false});$("#hip_view_code").ligerTextBox({disabled: false});
		    	
		    	saveFlag = 0;
				
			}
		});}});
    	
    }
    
    function btnSave(){

    	var mod_code = liger.get("mod_code").getValue();
    	
    	if(!mod_code){
    		
    		alert("请选择 模块编码 ！");
    		
    	}
    		
    	var hip_view_code = $("#hip_view_code").val();
    	
		if(!hip_view_code){
    		
    		alert("请输入 HIP视图编码 ！");
    		
    	}
		
		var hip_view_name = $("#hip_view_name").val();
    	
		if(!hip_view_name){
    		
    		alert("请输入 HIP视图名称 ！");
    		
    	}
		
		var ref_tab_name = $("#ref_tab_name").val();
    	
		if(!ref_tab_name){
    		
    		alert("请输入 映射关系表 ！");
    		
    	}
    		
    	var view_level = liger.get("view_level").getValue();
    	
		if(!view_level){
    		
    		alert("请选择 层级 ！");
    		
    	}
		
		var formPara = {
				
				mod_code : mod_code,
				
				hip_view_code : hip_view_code,
				
				hip_view_name : hip_view_name,
				
				ref_tab_name : ref_tab_name,
				
				view_level : view_level,
				
				saveFlag : saveFlag

		};

		ajaxJsonObjectByUrl("addHipInitView.do", formPara, function(responseData) {

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
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">模块编码：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="mod_code" type="text" id="mod_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;"> HIP视图编码：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="hip_view_code" type="text" id="hip_view_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">HIP视图名称：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="hip_view_name" type="text" id="hip_view_name" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">映射关系表：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="ref_tab_name" type="text" id="ref_tab_name" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">层级：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="view_level" type="text" id="view_level" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr>  
			    </table>
            </div>  
	</div>

	
</body>
</html>
