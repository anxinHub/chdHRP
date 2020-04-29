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

    var copy_code = '${copy_code}';

    $(function ()
    	    {

    	        if(copy_code == 'copy_code'){
    	        	
    	        	$("#copy_code_1").show();$("#copy_code_2").show();$("#copy_code_3").show();
    	        	
    	        	autocompleteAsync("#copy_code","../queryHosCopy.do?isCheck=false","id","text",true,true,'',true,false,'180');
    	        	
    	        }else{
    	        	
    	        	$("#copy_code_1").hide();$("#copy_code_2").hide();$("#copy_code_3").hide();
    	        	
    	        	$("#copy_code").ligerComboBox({width:180 });
    	        	
    	        }
    	        
    			loadDict();//加载下拉框
    	    	
    	    	loadHead(null);	
    	        
    	        query();

    	    	$("#layout1").ligerLayout({rightWidth: 350});

    	    });
    //查询
    function  query(){
    	
		grid.options.parms = [];grid.options.newPage = 1;
		
		if(copy_code == 'copy_code'){
			
			grid.options.parms.push({name : 'copy_code',value : liger.get("copy_code").getValue()});
			
		}
		
		grid.options.parms.push({name : 'ds_code',value : liger.get("ds_code_search").getValue()});
	
		grid.loadData(grid.where);//加载查询条件
		
     }
    
    function loadHead(){
    	
		grid = $("#maingrid").ligerGrid({
			columns : [
					{display : '数据源编码',name : 'ds_code',width: 100,align : 'left',
						render: function (item) {
							
	                        return "<a onClick=openUpdate('"+item.copy_code+"','"+item.ds_code+"','"+item.hip_med_type_code+"','"+item.hrp_med_type_code+"') >"+item.ds_code+"</a>";
	                        
	                    }
					}, 
					
					{display : '数据源名称',name : 'ds_name',width: 150,align : 'left'}, 
					
					{display : '平台药品分类编码',name : 'hip_med_type_code',width: 100,align : 'left'},
					
					{display : '平台药品分类名称',name : 'hip_med_type_name',width: 150,align : 'left'},
					
					{display : 'HRP药品分类编码',name : 'hrp_med_type_code',align : 'left',width: 100,align : 'left'},
					
					{display : 'HRP药品分类名称',name : 'hrp_med_type_name',align : 'left',width: 150,align : 'left'}
					],
			dataAction : 'server',dataType : 'server',usePager : true,url : 'queryHipMedTypeRef.do',
			width : '100%',height : '100%',checkbox : true,selectRowButtonOnly : true,alternatingRow: false,//heightDiff: -10,
			toolbar : {
				items : [
				         {text : '查询',id : 'search',click : query,icon : 'search'},
				         {line : true},
				         {text : '删除',id : 'delete',click : delCheck,icon : 'delete'}, 
				         {line : true},
				         {text : '清空',id : 'delete',click : delAll,icon : 'delete'}, 
				         {line : true},
				         {text : '导入',id : 'delete',click : importData,icon : 'up'}, 
				         {line : true}, 
				         {text : '下载导入模板',id : 'downTemplate',click : templateDown,icon : 'down'}, 
				         {line : true}
				         ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
    }

    function templateDown() {

		location.href = "downTemplateHipMedTypeRef.do?isCheck=false"

	}
    
    function importData() {

		$.ligerDialog.open({
			url : 'HipMedTypeRefImportPage.do?isCheck=false',
			height : 430,
			width : 760,
			isResize : true,
			title : '导入'
		});

	}
    
    function delCheck(){
    	
    	var data = gridManager.getCheckedRows();

		if (data.length == 0) {
			
			$.ligerDialog.error('请选择行');
			
		} else {

			var ParamVo = [];
			
			$(data).each(function() {
				
				ParamVo.push(this.group_id + "@" +this.hos_id + "@" +this.copy_code + "@" +this.ds_code + "@" + this.hip_med_type_code + "@" + this.hrp_med_type_code)
				
			});
			
			$.ligerDialog.confirm('确定删除选择?', function (yes){if(yes){
				
				ajaxJsonObjectByUrl("delCheckHipMedTypeRef.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData) {
		
					if (responseData.state == "true") {
						
						query();
						
						$("#ds_code").val('');
				    	
				    	$("#hip_med_type_code").val('');
				    	
				    	$("#hrp_med_type_code").val('');
				    	
				    	saveFlag = 0;
						
					}
				});
			}});
		}

    }
	function delAll(){
		
		$.ligerDialog.confirm('确定清空所有数据?', function (yes){if(yes){
			ajaxJsonObjectByUrl("deleteHipMedTypeRef.do", "", function(responseData) {
	
				if (responseData.state == "true") {
					
					query();
					
					$("#ds_code").val('');
			    	
			    	$("#hip_med_type_code").val('');
			    	
			    	$("#hrp_med_type_code").val('');
			    	
			    	saveFlag = 0;
					
				}
			});
		}});
    }
    
    function openUpdate(obj,obj1,obj2,obj3){
    	
    	var formPara = {
    			
    			copy_code : obj,
				
    			ds_code : obj1,
    			
    			hip_med_type_code : obj2,
    			
    			hrp_med_type_code : obj3

		};
		
		ajaxJsonObjectByUrl("updateHipMedTypeRefPage.do?isCheck=false", formPara, function(responseData) {

			liger.get("ds_code").setValue(responseData.ds_code);
			
			liger.get("ds_code").setText(responseData.ds_name);
			
			liger.get("hip_med_type_code").setValue(responseData.hip_med_type_code);
			
			liger.get("hip_med_type_code").setText(responseData.hip_med_type_name);
			
			liger.get("hrp_med_type_code").setValue(responseData.hrp_med_type_code);
			
			liger.get("hrp_med_type_code").setText(responseData.hrp_med_type_name);
			
			$("#copy_code_del").val(obj);
			
			$("#old_data").val(responseData.copy_code+"@"+responseData.ds_code+"@"+responseData.hip_med_type_code+"@"+responseData.hrp_med_type_code);

			saveFlag = 1;

		});
    }

    function loadDict(){
    	
    	autocomplete("#ds_code_search","../queryHipDsCof.do?isCheck=false","id","text",true,true,"",false,false,'180');
    	

    	var count = 0;

    	$("#ds_code").ligerComboBox({
          	url: '../queryHipDsCof.do?isCheck=false',
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
    			
    			var hip_para = {ds_code : selectValue,copy_code : liger.get("copy_code").getValue()};

    			autocomplete("#hip_med_type_code","../queryHipMedTypeDict.do?isCheck=false","id","text",true,true,hip_para,true,false,'180');	
           }
    	 });
    	
    	$("#hip_med_type_code").ligerComboBox({width:180 });
    	
    	var hrp_para = {copy_code : liger.get("copy_code").getValue()};
    	
    	autocomplete("#hrp_med_type_code","../queryMedType.do?isCheck=false","id","text",true,true,hrp_para,true,false,'180');


	}  
    function btnAdd(){
    	
    	$("#ds_code").val('');
    	
    	$("#hip_med_type_code").val('');
    	
    	$("#hrp_med_type_code").val('');
    	
    	saveFlag = 0;
    	
    }
    
    function btnDel(){
    	
    	var copy_code_del = $("#copy_code_del").val(); 
    	
    	var ds_code = liger.get("ds_code").getValue();
    	
    	var hip_med_type_code = $("#hip_med_type_code").val();
    	
    	var hrp_med_type_code = $("#hrp_med_type_code").val();
    	
		var formPara = {
				
				copy_code : copy_code_del,
				
				ds_code : ds_code,
				
				hip_med_type_code:hip_med_type_code,
				
				hrp_med_type_code:hrp_med_type_code
				
		};
		$.ligerDialog.confirm('确定删除?', function (yes){if(yes){
		ajaxJsonObjectByUrl("deleteHipMedTypeRef.do", formPara, function(responseData) {

			if (responseData.state == "true") {
				
				query();
				
				$("#ds_code").val('');
		    	
		    	$("#hip_med_type_code").val('');
		    	
		    	$("#hrp_med_type_code").val('');
		    	
		    	saveFlag = 0;
				
			}
		});}});
    	
    }
    
    function btnSave(){
    	
    	var copy_code = liger.get("copy_code").getValue();

		var ds_code = liger.get("ds_code").getValue();
    	
    	if(!ds_code){
    		
    		alert("请输入数据源 ！");
    		
    	}
    	
		var hip_med_type_code = $("#hip_med_type_code").val();
    	
    	if(!hip_med_type_code){
    		
    		alert("请输入 平台药品分类 ！");
    		
    	}
    	
		var hrp_med_type_code = $("#hrp_med_type_code").val();
    	
    	if(!hrp_med_type_code){
    		
    		alert("请输入 HRP药品分类 ！");
    		
    	}

		var formPara = {
				
				copy_code:copy_code,
				
				ds_code : ds_code,
				
				hip_med_type_code : hip_med_type_code,
				
				hrp_med_type_code : hrp_med_type_code,
				
				old_data : $("#old_data").val(),
				
				saveFlag : saveFlag

		};

		ajaxJsonObjectByUrl("addHipMedTypeRef.do", formPara, function(responseData) {

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
			<td id="copy_code_1" align="right" class="l-table-edit-td"  style="padding-left:20px;"> 账套：</td>
			<td id="copy_code_2" align="left" class="l-table-edit-td"><input name="copy_code" type="text" id="copy_code" ltype="text" /></td>
			<td id="copy_code_3" align="left"></td>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;"> 数据源：</td>
            <td align="left" class="l-table-edit-td"><input name="ds_code_search" type="text" id="ds_code_search" ltype="text" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    <input name="old_data" type="hidden" id="old_data" ltype="hidden" />
    <input name="copy_code_del" type="hidden" id="copy_code_del" ltype="hidden" />
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
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">数据源：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="ds_code" type="text" id="ds_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">平台药品分类：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="hip_med_type_code" type="text" id="hip_med_type_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">HRP药品分类：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="hrp_med_type_code" type="text" id="hrp_med_type_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr>
			    </table>
            </div>  
	</div>

	
</body>
</html>
