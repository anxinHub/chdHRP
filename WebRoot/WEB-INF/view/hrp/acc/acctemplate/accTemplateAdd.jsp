<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>

<script type="text/javascript">
	
     var dataFormat;
     
     var grid;
     
     var gridManager = null;
     
     var userUpdateStr;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
        loadHead(null);	//加载数据
        
        $("#note").ligerTextBox({width:400 });
        
        $("#firstMenu").ligerMenuBar({
			items : [ {
				text : '添加',
				id : 'add',
				click : itemclick
			},{
				text : '删除',
				id : 'delete',
				click : itemclick
			}]
		});
	});
     
     function loadHead(){
     	grid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '摘要', name: 'summary', align: 'left'
 					 },
                      { display: '科目编码', name: 'subj_code', align: 'left',
                     	 render:function(rowdata,index,value){
                     		 return "<a href='javacript:update('"+rowdata.subj_code+"')'>"+rowdata.subj_code+"</a>";
                     	 }
 					 },
                      { display: '科目全称', name: 'subj_name', align: 'left'
 					 },
                      { display: '方向', name: 'subj_dire', align: 'left',
                     	 render:function(rowdata,index,value){
                     		 if(rowdata.subj_dire==0){
                     			 return "借";
                     		 }
                     		 return "贷";
                     	 }
 					 },
                      { display: '金额', name: 'money', align: 'left',
                     	 render:function(rowdata,index,value){
                     		 if(rowdata.subj_dire==0){
                     			 return rowdata.debit;
                     		 }
                     		 return rowdata.credit;
                     	 }
 					 }
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccVouchSubj.do',
                      width: '100%', height: '150px', checkbox: true,rownumbers:true,
                      selectRowButtonOnly:true,onDblClickRow : function (rowdata, rowindex, value)
      				{
  						if(rowdata.is_check==1){
  					        reloadHead(null);
  					      $("#secondMenu").ligerMenuBar({
  							items : [ {
  								text : '添加',
  								id : 'add',
  								click : itemclick1
  							},{
  								text : '删除',
  								id : 'delete',
  								click : itemclick1
  							}]
  						});
  						}else{
  							$("#grid").remove();
  							$("#secondMenu").remove();
  						}
      				} 
                   });

         gridManager = $("#maingrid").ligerGetGridManager();
     }
     
     function reloadHead(){
      	grid = $("#grid").ligerGrid({
             columns: [ 
                       { display: '摘要', name: 'vouch_type_name', align: 'left'
  					 },
                       { display: '流量标注', name: 'user_name', align: 'left'
  					 },
                       { display: '金额', name: 'note', align: 'left'
  					 }
                       ],
                       dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccTemplate.do',
                       width: '100%', height: '150px', checkbox: true,rownumbers:true,
                       selectRowButtonOnly:true
                     });

          gridManager = $("#maingrid").ligerGetGridManager();
      }
     
     function  save(){
        var formPara={
           vouch_id:$("#vouch_id").val(),
           acc_year:$("#acc_year").val(),
            
           vouch_type_code:$("#vouch_type_code").val(),
            
           vouch_date:$("#vouch_date").val(),
            
           vouch_att_num:$("#vouch_att_num").val(),
           template_id:$("#template_id").val(),
           template_name:$("#template_name").val(),
            
           template_sort:$("#template_sort").val(),
            
           note:$("#note").val()
            
         };
        
        ajaxJsonObjectByUrl("addAccTemplate.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='vouch_id']").val('');
				 $("input[name='acc_year']").val('');
				 $("input[name='vouch_type_code']").val('');
				 $("input[name='vouch_date']").val('');
				 $("input[name='vouch_att_num']").val('');
				 $("input[name='template_sort']").val('');
				 $("input[name='template_id']").val('');
				 $("input[name='template_name']").val('');
				 $("input[name='note']").val('');
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
   
    function saveAccTemplate(){
        if($("form").valid()){
            save();
        }
   }
    
    function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				return;
			case "modify":
				return;
			case "del":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(function() {
						ParamVo.push(
						//表的主键
						this.vouch_id)
					});
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAccVouch.do", {
								ParamVo : ParamVo
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
				return;
			}
		}

	}
    
    function itemclick1(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				return;
			case "del":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(function() {
						ParamVo.push(
						//表的主键
						this.vouch_id)
					});
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAccVouch.do", {
								ParamVo : ParamVo
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
				return;
			}
		}

	}
    function loadDict(){
            //字典下拉框
           
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">模板名称：</td>
                <td align="left" class="l-table-edit-td"><input name="template_name" type="text" id="template_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">模板排序号：</td>
                <td align="left" class="l-table-edit-td"><input name="template_sort" type="text" id="template_sort" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">说明：</td>
                <td align="left" class="l-table-edit-td" colspan="4"><input name="note" type="text" id="note" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    <div id="firstMenu"></div>
   <div id="maingrid"></div>
   <div id="secondMenu" ></div>
   <div id="grid" ></div>
    </body>
</html>
