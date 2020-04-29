<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/lib/hrp/hip/hip.js"	type="text/javascript"></script>
<script type="text/javascript">

    var grid;
    
    var gridManager = null;
    
    var saveFlag;

    $(function ()
    {
        loadDict();//加载下拉框
    	
    	loadHead(null);	

    	$("#layout1").ligerLayout({rightWidth: 350});

    });
    //查询
    function  query(){
    	
		grid.options.parms = [];grid.options.newPage = 1;
		
		grid.options.parms.push({name : 'ds_code',value : liger.get("ds_code_search").getValue()});
	
		grid.loadData(grid.where);//加载查询条件
		
     }
    
    function loadHead(){
    	
		grid = $("#maingrid").ligerGrid({
			columns : [
					{display : '数据源编码',name : 'ds_code',width: 100,align : 'left',
						render: function (item) {
							
	                        return "<a onClick=openUpdate('"+item.ds_code+"','"+item.hip_dept_code+"','"+item.hrp_dept_code+"','"+item.doc_type+"') >"+item.ds_code+"</a>";
	                        
	                    }
					}, 
					
					{display : '数据源名称',name : 'ds_name',width: 150,align : 'left'}, 
					
					{display : '平台科室编码',name : 'hip_dept_code',width: 100,align : 'left'},
					
					{display : '平台科室名称',name : 'hip_dept_name',width: 150,align : 'left'},
					
					{display : 'HRP科室编码',name : 'hrp_dept_code',align : 'left',width: 100,align : 'left'},
					
					{display : 'HRP科室名称',name : 'hrp_dept_name',align : 'left',width: 150,align : 'left'},
					
					{display : '科室性质',name : 'doc_type',align : 'left',width: 150,align : 'left', 
						render : function(rowdata, rowindex, value) {
							return value == 1 ? "住院" : "门诊";
						}
					}
					],
			dataAction : 'server',dataType : 'server',usePager : true,url : 'queryHipDeptRef.do',delayLoad:true,
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

		location.href = "downTemplateHipDeptRef.do?isCheck=false"

	}
    
    function importData() {

    	var para = {
				"column" : [ {
					"name" : "ds_code",
					"display" : "数据源编码",
					"width" : "300",
					"require" : true
				}, {
					"name" : "hip_dept_code",
					"display" : "平台科室编码",
					"width" : "300",
					"require" : true
				}, {
					"name" : "hrp_dept_code",
					"display" : "HRP科室编码 ",
					"width" : "300",
					"require" : true
				}, {
					"display" : '科室性质',
					"name" : 'doc_type',
					"width" : "300",
					"require" : true
				}

				]
    	};
		importSpreadView(
				"hrp/hip/hipdeptref/imphipDeptRefReadFiles.do?isCheck=false",
				para);


	}
    
    function delCheck(){
    	
    	var data = gridManager.getCheckedRows();

		if (data.length == 0) {
			
			$.ligerDialog.error('请选择行');
			
		} else {

			var ParamVo = [];
			
			$(data).each(function() {
				
				ParamVo.push(this.group_id + "@" +this.hos_id + "@" +this.ds_code + "@" + this.hip_dept_code + "@" + this.hrp_dept_code + "@" + this.doc_type)
				
			});
			
			$.ligerDialog.confirm('确定删除选择?', function (yes){if(yes){
				
				ajaxJsonObjectByUrl("delCheckHipDeptRef.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData) {
		
					if (responseData.state == "true") {
						
						query();
						
						$("#ds_code").val('');
				    	
				    	$("#hip_dept_code").val('');
				    	
				    	$("#hrp_dept_code").val('');
				    	
				    	saveFlag = 0;
						
					}
				});
			}});
		}

    }
	function delAll(){
		
		$.ligerDialog.confirm('确定清空所有数据?', function (yes){if(yes){
			ajaxJsonObjectByUrl("deleteHipDeptRef.do", "", function(responseData) {
	
				if (responseData.state == "true") {
					
					query();
					
					$("#ds_code").val('');
			    	
			    	$("#hip_dept_code").val('');
			    	
			    	$("#hrp_dept_code").val('');
			    	
			    	saveFlag = 0;
					
				}
			});
		}});
    }
    
    function openUpdate(obj,obj1,obj2, obj3){
    	
    	var formPara = {
				
    			ds_code : obj,
    			
    			hip_dept_code : obj1,
    			
    			hrp_dept_code : obj2, 
    			
    			doc_type : obj3 
				
		};
		
		ajaxJsonObjectByUrl("updateHipDeptRefPage.do?isCheck=false", formPara, function(responseData) {

			liger.get("ds_code").setValue(responseData.ds_code);
			
			liger.get("ds_code").setText(responseData.ds_name);
			
			liger.get("hip_dept_code").setValue(responseData.hip_dept_code);
			
			liger.get("hip_dept_code").setText(responseData.hip_dept_name);
			
			liger.get("hrp_dept_code").setValue(responseData.hrp_dept_code);
			
			liger.get("hrp_dept_code").setText(responseData.hrp_dept_name);
			
			liger.get("doc_type").setValue(responseData.doc_type);
			if(responseData.doc_type == 1){
				liger.get("doc_type").setText("住院");
			}else{
				liger.get("doc_type").setText("门诊");
			}
			
			
			$("#old_data").val(responseData.ds_code+"@"+responseData.hip_dept_code+"@"+responseData.hrp_dept_code);

			saveFlag = 1;

		});
    }

    function loadDict(){
    	
    	autocompleteAsync("#ds_code_search","../queryHipDsCof.do?isCheck=false","id","text",true,true,"",true,false,'180');

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
    			
    			var para = {ds_code : selectValue, is_last : 1}

    			autocompleteAsync("#hip_dept_code","../queryHipDeptDict.do?isCheck=false","id","text",true,true,para,true,false,'180');	
           }
    	 });
    	
    	$("#hip_dept_code").ligerComboBox({width:180 });

    	autocomplete("#hrp_dept_code","../queryHosDeptDict.do?isCheck=false","id","text",true, true, {is_last : 1},true,false,'180');
    	
    	autoCompleteByData("#doc_type", hipDeptRef_docType.Rows, "id", "text", true, true, "", true, false, "180");

	}  
    function btnAdd(){
    	
    	$("#ds_code").val('');
    	
    	$("#hip_dept_code").val('');
    	
    	$("#hrp_dept_code").val('');
    	
    	$("#doc_type").val('');
    	
    	saveFlag = 0;
    	
    }
    
    function btnDel(){
    	
    	var ds_code = liger.get("ds_code").getValue();
    	
    	var hip_dept_code = liger.get("hip_dept_code").getValue();
    	
    	var hrp_dept_code = liger.get("hrp_dept_code").getValue();
    	
    	var doc_type = liger.get("doc_type").getValue();
    	
		var formPara = {
				
				ds_code : ds_code,
				
				hip_dept_code:hip_dept_code,
				
				hrp_dept_code:hrp_dept_code,
				
				doc_type: doc_type
				
		};
		$.ligerDialog.confirm('确定删除?', function (yes){if(yes){
		ajaxJsonObjectByUrl("deleteHipDeptRef.do", formPara, function(responseData) {

			if (responseData.state == "true") {
				
				query();
				
				$("#ds_code").val('');
		    	
		    	$("#hip_dept_code").val('');
		    	
		    	$("#hrp_dept_code").val('');

		    	$("#doc_type").val('');
		    	
		    	saveFlag = 0;
				
			}
		});}});
    	
    }
    
    function btnSave(){

		var ds_code = liger.get("ds_code").getValue();
    	
    	if(!ds_code){
    		
    		alert("请输入数据源 ！");
    		
    	}
    	
		var hip_dept_code = liger.get("hip_dept_code").getValue();
    	
    	if(!hip_dept_code){
    		
    		alert("请输入 平台科室 ！");
    		
    	}
    	
		var hrp_dept_code = liger.get("hrp_dept_code").getValue();
    	
    	if(!hrp_dept_code){
    		
    		alert("请输入 HRP科室 ！");
    		
    	}
    	
		var doc_type = liger.get("doc_type").getValue();
    	
    	if(!doc_type){
    		
    		alert("请输入 科室性质 ！");
    		
    	}

		var formPara = {
				
				ds_code : ds_code,
				
				hip_dept_code : hip_dept_code,
				
				hrp_dept_code : hrp_dept_code,

				doc_type : doc_type,
				
				old_data : $("#old_data").val(),
				
				saveFlag : saveFlag

		};

		ajaxJsonObjectByUrl("addHipDeptRef.do", formPara, function(responseData) {

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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"> 数据源：</td>
            <td align="left" class="l-table-edit-td"><input name="ds_code_search" type="text" id="ds_code_search" ltype="text" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    <input name="old_data" type="hidden" id="old_data" ltype="hidden" />
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
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">平台科室：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="hip_dept_code" type="text" id="hip_dept_code" ltype="text" validate="{required:true}"/></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">HRP科室：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="hrp_dept_code" type="text" id="hrp_dept_code" ltype="text" validate="{required:true}"/></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">科室性质</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="doc_type" type="text" id="doc_type" ltype="text" validate="{required:true}"/></td>
			            <td align="left"></td>
			        </tr>
			    </table>
            </div>  
	</div>

	
</body>
</html>
