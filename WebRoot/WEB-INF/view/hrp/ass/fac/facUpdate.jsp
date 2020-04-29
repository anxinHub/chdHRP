<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
        
        loadForm();

        if("${is_ass}" == 1){
        	liger.get("is_ass").setValue(true)
		} 
        
        loadHead(null);
    });  
  //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '生产厂商编码', name: 'fac_code', align: 'left'},
                     { display: '生产厂商名称', name: 'fac_name', align: 'left'},
					 { display: '类型编码', name: 'type_code', align: 'left'},
                     { display: '排序号', name: 'fac_sort', align: 'left'},
					 { display: '备注', name: 'note', align: 'left'},
                     { display: '是否停用', name: 'is_disable', align: 'left' ,
						 render : function(rowdata, rowindex,value) {
								if(rowdata.is_disable == 0){
									return "否";
								}else{
									return "是"
								}
						 }
					 },
                     { display: '操作人', name: 'user_code', align: 'left'},
                     { display: '操作时间', name: 'create_date', align: 'left' , width:80},
                     { display: '变更原因', name: 'dlog', align: 'left'}
              ],
              dataAction: 'server',dataType: 'server',usePager:true,url:'../facdict/assqueryFacDict.do?isCheck=false&group_id=${group_id}&hos_id=${hos_id}&fac_id=${fac_id}',
              width: '100%', height: '100%', checkbox: false,rownumbers:false,
              selectRowButtonOnly:true
          });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function save(){
    	mat=$("#is_mat").is(":checked") ? 1 : 0;
    	med=$("#is_med").is(":checked") ? 1 : 0;
    	ass=$("#is_ass").is(":checked") ? 1 : 0;
    	sup=$("#is_sup").is(":checked") ? 1 : 0 ;		    
    	if(mat=='0' && med=='0' && ass=='0' && sup=='0'){
    		 $.ligerDialog.error("系统模块不能为空");
    		 return ; 
    	}
    	var type_code ;
    	if($("#type_code").val() != null && $("#type_code").val() != ''){
    		type_code = liger.get("type_code").getValue();
    	}else{
    		type_code = '';
    	}
        var formPara={
        	group_id : '${group_id}',
        	hos_id : '${hos_id}',
        	fac_id : '${fac_id}',
        	fac_codeOld : '${fac_code}',
        	fac_code : $("#fac_code").val(),
        	type_code : type_code,
        	fac_name : $("#fac_name").val(),
	        fac_sort : $("#fac_sort").val(),
	        is_stop : liger.get("is_stop").getValue(),
	        note : $("#note").val(),
	        spell_code : $("#spell_code").val(),
	        wbx_code : $("#wbx_code").val(),
	        history : liger.get("history").getValue(),
	        is_auto : liger.get("is_auto").getValue(),
	        is_mat : $("#is_mat").is(":checked") ? 1 : 0,
	        is_med : $("#is_med").is(":checked") ? 1 : 0,
	        is_sup:$("#is_sup").is(":checked") ? 1 : 0 ,
        	is_ass : $("#is_ass").is(":checked") ? 1 : 0 
        };
        ajaxJsonObjectByUrl("assupdateFac.do",formPara,function(responseData){
            if(responseData.state=="true"){
                parent.query();
            }
        });
    }
     
    function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("form").validate({
         errorPlacement: function (lable, element)
         {
             if (element.hasClass("l-textarea"))
             {
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }
             else if (element.hasClass("l-text-field"))
             {
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }
             else
             {
                 lable.appendTo(element.parents("td:first").next("td"));
             }
         },
         success: function (lable)
         {
             lable.ligerHideTip();
             lable.remove();
         },
         submitHandler: function ()
         {
             $("form .l-text,.l-textarea").ligerHideTip();
         }
     });
     $("form").ligerForm();
    }       
   
    function saveFac(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	autocomplete("#type_code","../queryFacTypeDict.do?isCheck=false","id","text",true,true);
        liger.get("type_code").setValue("${type_code}");
        liger.get("type_code").setText("${type_name}");
    	$('#is_stop').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width : 180
	});
		liger.get("is_stop").setValue("${is_stop}");
        $("#fac_code").ligerTextBox({width:160,disabled:true});
     }   
    
    function facChange(){
    	var param = "&group_id=${group_id}&hos_id=${hos_id}&fac_id=${fac_id}&fac_code=${fac_code}&fac_name=${fac_name}";
    	$.ligerDialog.open({ url : 'facChangePage.do?isCheck=false'+param,data:{}, 
    		height: 300,width: 400, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    		           { text: '保存', onclick: function (item, dialog) {dialog.frame.save(); },cls:'l-dialog-btn-highlight' },
    				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
    				]
    	});
    }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>生产厂商编码<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="fac_code" type="text" id="fac_code"  value="${fac_code }" disabled="disabled" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>生产厂商名称<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="fac_name" type="text" id="fac_name"  value="${fac_name }" ltype="text" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td">
                	<!-- <select id="is_stop" name="is_stop" style="width: 135px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                	</select> -->
                	<input name="is_stop" type="text" id="is_stop"  validate="{required:true,maxlength:50}"/>
                </td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>生产厂商类别:</b></td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>拼音码:</b></td>
                <td align="left" class="l-table-edit-td"><input name="spell_code" type="text" id="spell_code"  value="${spell_code }" ltype="text" validate="{maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>五笔码:</b></td>
                <td align="left" class="l-table-edit-td"><input name="wbx_code" type="text" id="wbx_code"  value="${wbx_code}" ltype="text" validate="{maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>排序号<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td" ><input name="fac_sort" type="text" id="fac_sort" value="${fac_sort }" ltype="text" validate="{required:true,digits:true ,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="left" class="l-table-edit-td"><input name="history"  id ="history" checked="checked" type="checkbox" />是否保留历史记录</td>
                <td align="left" class="l-table-edit-td"><input name="is_auto"  id ="is_auto" checked="checked" type="checkbox" />是否自动生成拼音码、五笔码</td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>备注:</b></td>
                <td align="left" class="l-table-edit-td" colspan="4">
                	<textarea rows="3" cols="90" id="note" name="note" ltype="text" validate="{maxlength:20}">${note }</textarea>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
            	<td align="left" class="l-table-edit-td" ></td>
             	<td align="left" class="l-table-edit-td" >
             		<!-- <input name=is_mat"  id ="is_mat"  type="checkbox" />物流管理&nbsp;&nbsp;
             		<input name="is_med"  id ="is_med" type="checkbox" />药品管理&nbsp;&nbsp; -->
             		<input name="is_ass"  id ="is_ass" type="checkbox" checked="checked"/>固定资产&nbsp;&nbsp;
             		<!-- <input name="is_sup"  id ="is_sup" type="checkbox" />供应商平台&nbsp;&nbsp; -->
             	</td>
            </tr>
        </table>
    </form>
    <div id="maingrid"></div>
    </body>
</html>
