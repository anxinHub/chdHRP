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
        loadHead(null);
    });  
    
  //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	//加载查询条件
    	grid.loadData(grid.where);
    	parent.query();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '职工编码', name: 'emp_code', align: 'left'
					 },
                     { display: '职工名称', name: 'emp_name', align: 'left'
					 },
					 { display: '职工分类', name: 'kind_code', align: 'left'
					 },
					 { display: '部门', name: 'dept_name', align: 'left'
					 },
					 { display: '备注', name: 'note', align: 'left'
					 },
					 { display: '排序号', name: 'sort_code', align: 'left'
					 },
                     { display: '操作人', name: 'user_code', align: 'left'
					 },
                     { display: '操作时间', name: 'create_date', align: 'left'
					 },
                     { display: '变更原因', name: 'dlog', align: 'left'
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
					 }
					 
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../empdict/queryEmpDict.do?isCheck=false&emp_id=${emp_id}',
                     width: '100%', height: '100%', checkbox: false,rownumbers:false,
                     selectRowButtonOnly:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
     
    function save(){
    	var kind_code ;
    	if($("#kind_code").val() != null && $("#kind_code").val() != ''){
    		kind_code = liger.get("kind_code").getValue();
    	}else{
    		kind_code = '';
    	}
        var formPara={
        group_id:'${group_id}',
        hos_id:'${hos_id}',
        emp_id:'${emp_id}',
        emp_no:'${emp_no}',
        emp_codeOld:'${emp_code}',
        emp_code:$("#emp_code").val(),
        
        emp_name:$("#emp_name").val(),
        dept_no:liger.get("dept").getValue().split(".")[1],
        dept_id:liger.get("dept").getValue().split(".")[0],
        kind_code:kind_code,
        kind_name:liger.get("kind_code").getText(),
        sort_code:$("#sort_code").val(),
        is_disable:$("#is_disable").val(),
        note:$("#note").val(),
        spell_code:$("#spell_code").val(),
        wbx_code:$("#wbx_code").val(),
        history:liger.get("history").getValue(),
        is_auto:liger.get("is_auto").getValue()
        };
        ajaxJsonObjectByUrl("updateEmp.do",formPara,function(responseData){
            
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
   
    function saveEmp(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    		 autocomplete("#dept","../queryDeptDictLast.do?isCheck=false","id","text",true,true);
		      liger.get("dept").setValue('${dept_id}.${dept_no}');
		      liger.get("dept").setText('${dept_code} ${dept_name}');
		      autocomplete("#kind_code","../queryEmpKindDict.do?isCheck=false","id","text",true,true);
		      liger.get("kind_code").setValue('${kind_code}');
		      liger.get("kind_code").setText('${kind_name}');
		      $("#is_disable").val('${is_disable}');
		      $("#emp_code").ligerTextBox({disabled:true});
	     }   
    
    /* function empChange(){
    	var param = "&group_id=${group_id}&hos_id=${hos_id}&emp_id=${emp_id}&emp_code=${emp_code}&emp_name=${emp_name}";
    	$.ligerDialog.open({ url : 'empChangePage.do?isCheck=false'+param,data:{}, 
    		height: 300,width: 400, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    		           { text: '保存', onclick: function (item, dialog) {dialog.frame.save(); },cls:'l-dialog-btn-highlight' },
    				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
    				]
    	});
    }  */
    
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
   		<input type="hidden" id="dept_id" name="dept_id" value="${dept_id }"/>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>职工编码<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="emp_code" type="text"  value="${emp_code}" id="emp_code"  ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>职工名称<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="emp_name" type="text" id="emp_name" value="${emp_name }" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用<font color ="red">*</font>:</b></td>
			    <td align="left" class="l-table-edit-td">
			                	<select id="is_disable" name="is_disable" style="width: 135px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                				</select>
			    </td>
			    <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>职工分类:</b></td>
                <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" ltype="text" validate="{required:false}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
               <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>部门<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="dept" type="text" id="dept" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>排序号<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="sort_code" value="${sort_code }" type="text" id="sort_code" ltype="text" validate="{required:true,digits:true ,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
               <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>拼音码:</b></td>
                <td align="left" class="l-table-edit-td"><input name="spell_code" value="${spell_code }" type="text" id="spell_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>五笔码:</b></td>
                <td align="left" class="l-table-edit-td"><input name="wbx_code" value="${wbx_code }" type="text" id="wbx_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
			    <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>备注:</b></td>
			    <td align="left" class="l-table-edit-td" >
			          <textarea rows="3" cols="40" id="note" name="note" ltype="text" validate="{maxlength:50}">${note }</textarea>
			    </td>
			    <td align="left"></td>
                <td align="left" class="l-table-edit-td"><input name="history" id="history" type="checkbox" />是否保留历史记录</td>
                <td align="left" class="l-table-edit-td"><input name="is_auto" id="is_auto" type="checkbox" checked="checked" />是否自动生成拼音码、五笔码</td>
                <td align="left"></td>
			</tr> 
        </table>
    </form>
    <div id="maingrid"></div>
    </body>
</html>
