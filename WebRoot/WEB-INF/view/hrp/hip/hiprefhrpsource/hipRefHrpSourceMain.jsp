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
    
    var dfd_flag_data =[{ id: '01', text: '接收' },{ id: '02', text: '推送'},{ id: '03', text: '双向'}];

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
					{display : 'HRP视图编码',name : 'hrp_view_code',width: 100,align : 'left',
						render: function (item) {
							
	                        return "<a onClick=openUpdate('"+item.mod_code+"','"+item.hip_view_code+"','"+item.hrp_view_code+"') >"+item.hrp_view_code+"</a>";
	                        
	                    }
					}, 
					
					{display : 'HRP视图名称',name : 'hrp_view_name',width: 160,align : 'left'},
					
					{display : '模块名称',name : 'mod_name',width: 160,align : 'left'},
					
					{display : 'HIP视图名称',name : 'hip_view_name',align : 'left',width: 160,align : 'left'},
					
					{display : '流向标识',name : 'dfd_flag',align : 'left',width: 80,align : 'left',
						
						render: function (item) {

							for (var i = 0; i < dfd_flag_data.length; i++){
	                        	
	                            if (dfd_flag_data[i]['id'] == item.dfd_flag){

	                            	return dfd_flag_data[i]['text'];
	                            	
	                            }
	                            
	                        }

	                    }
						
					}
					],
			dataAction : 'server',dataType : 'server',usePager : true,url : 'queryHipRefHrpSource.do',
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
    
    function openUpdate(obj,obj1,obj2){
    	
    	var formPara = {
				
    			mod_code : obj,
    			
    			hip_view_code:obj1,
    			
    			hrp_view_code:obj2
    			

		};
		
		ajaxJsonObjectByUrl("updateHipRefHrpSourcePage.do?isCheck=false", formPara, function(responseData) {
			
			liger.get("mod_code").setValue(responseData.mod_code);
			
			liger.get("mod_code").setText(responseData.mod_name);
			
			$("#mod_code").ligerTextBox({disabled: true});
			
			liger.get("hip_view_code").setValue(responseData.hip_view_code);
			
			liger.get("hip_view_code").setText(responseData.hip_view_name);
			
			$("#hip_view_code").ligerTextBox({disabled: true});
			
			$("#hrp_view_code").val(responseData.hrp_view_code);
			
			$("#hrp_view_code").ligerTextBox({disabled: true});
			
			$("#hrp_view_name").val(responseData.hrp_view_name);
			
			alert(responseData.dfd_flag);
			
			if(responseData.dfd_flag !='null'){
				
				for (var i = 0; i < dfd_flag_data.length; i++){
                	
                    if (dfd_flag_data[i]['id'] == responseData.dfd_flag){
                    	
                    	liger.get("dfd_flag").setValue(dfd_flag_data[i]['id']);
        				
        				liger.get("dfd_flag").setText(dfd_flag_data[i]['text']);
                    	
                    }
                    
                }
				
			}

			saveFlag = 1;

		});
    }

    function loadDict(){
    	
    	autocomplete("#mod_code_search","../querySysMod.do?isCheck=false","id","text",true,true,"",false,false,'180');
    	
		var count = 0;
    	
    	$("#mod_code").ligerComboBox({
          	url: '../querySysMod.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 180,
          	autocomplete: true,
          	width: 180,
          	onSuccess: function (data) {
          		
          		if (count == 0) {
          			
					this.setValue(data[0].id);
					
				}
          		
    			count++;

    		},
    		onSelected: function (selectValue){
    			
    			var para = {mod_code : selectValue}
               		
    			autocomplete("#hip_view_code","../queryHipInitView.do?isCheck=false","id","text",true,true,para,true,false,'180');
               		
                }
    	 });
    	
    	$("#hip_view_code").ligerComboBox({width:180 });
    	
    	$("#hrp_view_code").ligerTextBox({width:180});
    	
    	$("#hrp_view_name").ligerTextBox({width:180});
    	
    	$("#dfd_flag").ligerComboBox({data: dfd_flag_data,width: 180,value: '0'});

	}  
    function btnAdd(){
    	
    	$("#hrp_view_code").val('');
    	
    	$("#mod_code").val('');
    	
    	$("#hip_view_code").val('');
    	
    	$("#hrp_view_name").val('');
    	
    	$("#dfd_flag").val('');
    	
    	$("#ds_note").val('');
    	
    	saveFlag = 0;
    	
    	$("#hrp_view_code").ligerTextBox({disabled: false});
    	
    	$("#mod_code").ligerTextBox({disabled: false});
    	
    	$("#hip_view_code").ligerTextBox({disabled: false});
    	
    }
    
    function btnDel(){
    	
    	var mod_code = liger.get("mod_code").getValue();
    	
    	var hip_view_code = liger.get("hip_view_code").getValue();
    	
    	var hrp_view_code = $("#hrp_view_code").val();
    	
		var formPara = {
				
    			mod_code : mod_code,
    			
    			hip_view_code : hip_view_code,
    			
    			hrp_view_code : hrp_view_code

		};
		$.ligerDialog.confirm('确定删除?', function (yes){if(yes){
		ajaxJsonObjectByUrl("deleteHipRefHrpSource.do", formPara, function(responseData) {

			if (responseData.state == "true") {
				
				query();
				
		    	$("#hrp_view_code").val('');
		    	
		    	$("#mod_code").val('');
		    	
		    	$("#hip_view_code").val('');
		    	
		    	$("#hrp_view_name").val('');
		    	
		    	$("#dfd_flag").val('');
		    	
		    	$("#ds_note").val('');
		    	
		    	saveFlag = 0;
		    	
		    	$("#hrp_view_code").ligerTextBox({disabled: false});
		    	
		    	$("#mod_code").ligerTextBox({disabled: false});
		    	
		    	$("#hip_view_code").ligerTextBox({disabled: false});
				
			}
		});}});
    	
    }
    
    function btnSave(){

		var hrp_view_code = $("#hrp_view_code").val();
    	
    	if(!hrp_view_code){
    		
    		alert("请输入HRP视图编码 ！");
    		
    	}
    	
		var mod_code = liger.get("mod_code").getValue();
    	
    	if(!mod_code){
    		
    		alert("请输入 模块编码 ！");
    		
    	}
    	
		var hip_view_code = liger.get("hip_view_code").getValue();
    	
    	if(!hip_view_code){
    		
    		alert("请输入 HIP视图编码 ！");
    		
    	}
    	
		var hrp_view_name = $("#hrp_view_name").val();
    	
    	if(!hrp_view_name){
    		
    		alert("请输入 HRP视图名称 ！");
    		
    	}
    	
		var dfd_flag = liger.get("dfd_flag").getValue();
    	
    	if(!dfd_flag){
    		
    		alert("请输入流向标识 ！");
    		
    	}
		
		var formPara = {
				
				hrp_view_code : hrp_view_code,
				
				mod_code : mod_code,
				
				hip_view_code : hip_view_code,
				
				hrp_view_name : hrp_view_name,
				
				dfd_flag : dfd_flag,
				
				saveFlag : saveFlag

		};

		ajaxJsonObjectByUrl("addHipRefHrpSource.do", formPara, function(responseData) {

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
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">HIP视图编码：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="hip_view_code" type="text" id="hip_view_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">HRP视图编码：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="hrp_view_code" type="text" id="hrp_view_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">HRP视图名称：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="hrp_view_name" type="text" id="hrp_view_name" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">流向标识：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="dfd_flag" type="text" id="dfd_flag" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr> 
			    </table>
            </div>  
	</div>

	
</body>
</html>
