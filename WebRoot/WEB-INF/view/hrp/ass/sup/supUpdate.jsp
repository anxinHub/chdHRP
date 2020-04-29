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
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
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
                     { display: '供应商编码', name: 'sup_code', align: 'left'
					 },
                     { display: '供应商名称', name: 'sup_name', align: 'left'
					 },
					 { display: '类型编码', name: 'type_code', align: 'left'
					 },
                     { display: '排序号', name: 'sort_code', align: 'left'
					 },
					 { display: '备注', name: 'note', align: 'left'
					 },
                     { display: '是否停用', name: 'is_disable', align: 'left',
						 render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_disable == 0){
									return "启用";
								}else{
									return "停用"
								}
							}
					 },
                     { display: '操作人', name: 'user_code', align: 'left'
					 },
                     { display: '操作时间', name: 'create_date', align: 'left' , width:80
					 },
					 { display: '变更原因', name: 'dlog', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../supdict/assquerySupDict.do?isCheck=false&group_id=${group_id}&hos_id=${hos_id}&sup_id=${sup_id}',
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
    	if($("#sup_type").val() != null && $("#sup_type").val() != ''){
    		type_code = liger.get("sup_type").getValue();
    	}else{
    		type_code = '';
    	}
        var formPara={
        group_id:'${group_id}',
        hos_id:'${hos_id}',
        sup_id:'${sup_id}',
        sup_codeOld:'${sup_code}',
        sup_code: $("#sup_code").val(),
        sup_name:$("#sup_name").val(),
        sup_simple:$("#sup_simple").val(),
        type_code:type_code,
        sort_code:$("#sort_code").val(),
        is_stop:liger.get("is_stop").getValue(),
        note:$("#note").val(),
        spell_code:$("#spell_code").val(),
        wbx_code:$("#wbx_code").val(),
        history: liger.get("history").getValue(),
        is_auto: liger.get("is_auto").getValue(),
        is_mat:$("#is_mat").is(":checked") ? 1 : 0,
        is_med:$("#is_med").is(":checked") ? 1 : 0,
        is_sup:$("#is_sup").is(":checked") ? 1 : 0 ,
        is_ass:$("#is_ass").is(":checked") ? 1 : 0 
        };
        ajaxJsonObjectByUrl("assupdateSup.do",formPara,function(responseData){
            
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
   
    function saveProj(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    		autocomplete("#sup_type","../queryassSupTypeDict.do?isCheck=false","id","text",true,true);
		      liger.get("sup_type").setValue('${type_code}');
		      liger.get("sup_type").setText('${type_name}');
		      $('#is_stop').ligerComboBox({
					data:[{id:1,text:'是'},{id:0,text:'否'}],
					valueField: 'id',
		            textField: 'text',
					cancelable:true,
					width : 180
			});
				liger.get("is_stop").setValue("${is_stop}");
		      
	     }   
    
  /*   function projChange(){
    	var param = "&group_id=${group_id}&hos_id=${hos_id}&sup_id=${sup_id}&sup_code=${sup_code}&sup_name=${sup_name}";
    	$.ligerDialog.open({ url : 'supChangePage.do?isCheck=false'+param,data:{}, 
    		height: 300,width: 400, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    		           { text: '保存', onclick: function (item, dialog) {dialog.frame.save(); },cls:'l-dialog-btn-highlight' },
    				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
    				]
    	});
    } */
    
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>供应商编码<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="sup_code" type="text"  value="${sup_code }" disabled="disabled" id="sup_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>供应商名称<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="sup_name" type="text"  id="sup_name" value="${sup_name }" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用<font color ="red">*</font>:</b></td>
			    <td align="left" class="l-table-edit-td">
			                	<!-- <select id="is_stop" name="is_stop" style="width: 135px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                				</select> -->
                	<input name="is_stop" type="text" id="is_stop" validate="{required:true,maxlength:20}" />
			    </td>
			    <td align="left"></td>
			    <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>供应商类别<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="sup_type" type="text" id="sup_type" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>拼音码:</b></td>
                <td align="left" class="l-table-edit-td"><input name="spell_code" type="text"  value="${spell_code }" id="spell_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>五笔码:</b></td>
                <td align="left" class="l-table-edit-td"><input name="wbx_code" type="text"  id="wbx_code" value="${wbx_code }" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>排序号<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="sort_code" value="${sort_code }" type="text" id="sort_code" ltype="text" validate="{required:true,digits:true ,maxlength:20}" /></td>
                <td align="left"></td>
			    <td align="left" class="l-table-edit-td"><input name ="history" id ="history" checked="checked" type="checkbox" />是否保留历史记录</td>
			    <td align="left" class="l-table-edit-td"><input name ="is_auto" id ="is_auto" checked="checked" type="checkbox" />是否自动生成拼音码、五笔码</td>
			    <td align="left"></td>
            </tr> 
            <tr>
			    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
			    <td align="left" class="l-table-edit-td" colspan="4">
			          <textarea rows="3" cols="85" id="note" name="note" ltype="text">${note }</textarea>
			    </td>
			    <td align="left"></td>
			</tr> 
			<tr>
            	<td align="left" class="l-table-edit-td" ></td>
             	 <td align="left" class="l-table-edit-td">
                
                	<input name="is_mat"  id ="is_mat" type="hidden" />
                	<input name="is_med"  id ="is_med"  type="hidden" />
                	<input name="is_ass"  id ="is_ass" type="checkbox"  checked="checked"/>固定资产&nbsp;&nbsp;
                	<input name="is_sup"  id ="is_sup" type="hidden" />
                </td>
            </tr>
        </table>
    </form>
    <div id="maingrid"></div>
    </body>
</html>
