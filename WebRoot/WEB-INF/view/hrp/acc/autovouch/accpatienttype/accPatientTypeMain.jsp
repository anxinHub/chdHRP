<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">

    var grid;
    
    var gridManager = null;
    
    var saveFlag;
    
    var is_stop_data =[{ id: 0, text: '否' },{ id: 1, text: '是'}];

    $(function ()
    {
        loadDict();//加载下拉框
    	
    	loadHead(null);	
    	
    	$("#layout1").ligerLayout({rightWidth: 450});

    });
    //查询
    function  query(){
    	
		grid.options.parms = [];grid.options.newPage = 1;
		
		grid.options.parms.push({name : 'patient_type_code',value : liger.get("patient_type_code_search").getValue()});
	
		grid.loadData(grid.where);//加载查询条件
		
     }
    
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns : [
					{display : '病人类别编码',name : 'patient_type_code',width: 100,align : 'left',
						render: function (item) {
							
	                        return "<a onClick=openUpdate('"+item.patient_type_code+"') >"+item.patient_type_code+"</a>";
	                        
	                    }
					}, 
					{display : '病人类别名称',name : 'patient_type_name',width: 250,align : 'left'},
					{display : '是否停用',name : 'is_stop',align : 'left',width: 60,align : 'left',
						render: function (rowdata, rowindex, value){
                   		 if(rowdata.is_stop == '1'){
                     			 return "是";
                     		 }else{
                     			 return "否";
                     		 }
 							return "";
 						}
					}
					],
			dataAction : 'server',dataType : 'server',usePager : true,url : 'queryAccPatientType.do',
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
				
    			patient_type_code : obj

		};
		
		ajaxJsonObjectByUrl("updateAccPatientTypePage.do?isCheck=false", formPara, function(responseData) {

			$("#patient_type_code").val(responseData.patient_type_code);$("#patient_type_code").ligerTextBox({disabled: true});
			
			$("#patient_type_name").val(responseData.patient_type_name);
			
			if(responseData.note !='null'){
				
				$("#note").val(responseData.note);
				
			}else{
				
				$("#note").val('');
				
			}
			
			if(responseData.is_stop == '1'){
				
				liger.get("is_stop").setValue("1");
				
				liger.get("is_stop").setText("是");
				
			}else{
				
				liger.get("is_stop").setValue("0");
				
				liger.get("is_stop").setText("否");
				
			}
			
			saveFlag = 1;

		});
    }

    function loadDict(){
    	
    	autocomplete("#patient_type_code_search","../../queryAccPatientType.do?isCheck=false","id","text",true,true,"",false,false,'180');
    	
    	$("#patient_type_code").ligerTextBox({width:180});
    	
    	$("#patient_type_name").ligerTextBox({width:180});

		$("#is_stop").ligerComboBox({data: is_stop_data,width: 180,value: '0'});

	}  
    function btnAdd(){
    	
    	$("#patient_type_code").val('');
    	
    	$("#patient_type_name").val('');
    	
    	$("#note").val('');
    	
    	saveFlag = 0;
    	
    	$("#patient_type_code").ligerTextBox({disabled: false});
    	
    	$("#is_stop").ligerComboBox({data: is_stop_data,width: 180,value: '0'});
    	
    }
    
    function btnDel(){
    	
    	var patient_type_code = $("#patient_type_code").val();
    	
		var formPara = {
				
				patient_type_code : patient_type_code
				
		};
		$.ligerDialog.confirm('确定删除?', function (yes){if(yes){
		ajaxJsonObjectByUrl("deleteAccPatientType.do", formPara, function(responseData) {

			if (responseData.state == "true") {
				
				query();
				
		    	$("#patient_type_code").val('');
		    	
		    	$("#patient_type_name").val('');
		    	
		    	$("#note").val('');
		    	
		    	saveFlag = 0;
		    	
		    	$("#patient_type_code").ligerTextBox({disabled: false});
		    	
		    	$("#is_stop").ligerComboBox({data: is_stop_data,width: 180,value: '0'});
				
			}
		});}});
    	
    }
    
    function btnSave(){

    	var patient_type_code = $("#patient_type_code").val();
    	
    	if(!patient_type_code){
    		
    		alert("请输入 病人类别编码 ！");
    		
    	}
    		
    	var patient_type_name = $("#patient_type_name").val();
    	
		if(!patient_type_name){
    		
    		alert("请输入 病人类别名称 ！");
    		
    	}
    		
    	var is_stop = liger.get("is_stop").getValue();
    	
		if(!is_stop){
    		
    		alert("请选择 是否停用 ！");
    		
    	}
    		
    	var note = $("#note").val();

		var formPara = {
				
				patient_type_code : patient_type_code,
				
				patient_type_name : patient_type_name,
				
				is_stop : is_stop,
				
				note : note,
				
				saveFlag : saveFlag

		};

		ajaxJsonObjectByUrl("addAccPatientType.do", formPara, function(responseData) {

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
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;"> 病人类别编码：</td>
            <td align="left" class="l-table-edit-td"><input name="patient_type_code_search" type="text" id="patient_type_code_search" ltype="text" /></td>
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
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">  病人类别编码：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="patient_type_code" type="text" id="patient_type_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;"> 病人类别名称：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="patient_type_name" type="text" id="patient_type_name" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">是否停用：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:20px;">备    注：</td>
			            <td align="left" class="l-table-edit-td"  style="padding-top:20px;">
			           		<textarea cols="100" rows="4" class="l-textarea" id="note"  name="note" style="width:300px" validate="{required:true}" ></textarea>
			            </td>
			            <td align="left"></td>
			        </tr> 
			    </table>
            </div>  
	</div>

	
</body>
</html>
