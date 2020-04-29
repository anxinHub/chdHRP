<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	 var grid;
	
	 var gridManager = null;
     var dataFormat;
     
     var budg_year ;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        loadHead(null);
       
        budg_year = liger.get("budg_year").getValue();
        
    	$("#budg_year").change(function(){
         	grid.deleteAllRows();
         	budg_year = liger.get("budg_year").getValue();
         	loadHead();
         })
        $("body").keydown(function() {
            if (event.keyCode == "9") {//keyCode=13是回车键
            	grid.addRowEdited({
                	type_code : '',
                	subj_code: ''
        		 });
            }
        });
        
     });  
     function loadHead(){
	     grid = $("#maingrid").ligerGrid({ 
			     columns : [ 
							{display : '科目类别名称', name : 'type_code', textField : 'type_name', minWidth : 80, align : 'center',
									editor : {
										type : 'select',
										valueField : 'type_code',
										textField : 'type_name',
										url : 'queryBudgCostSubjType.do?isCheck=false',
										keySupport : true,
										autocomplete : true,
									}
								},
							{display : '支出预算科目名称', name : 'subj_code', textField : 'subj_name', minWidth : 80, align : 'center',
									editor : {
										type : 'select',
										valueField : 'subj_code',
										textField : 'subj_name',
										url : 'queryBudgCostSubj.do?isCheck=false&budg_year='+budg_year,
										keySupport : true,
										autocomplete : true,
									}
								}
							],
							 dataAction: 'server',dataType: 'server',checkbox: true,usePager:true,
		                     width: '100%', height: '100%',isAddRow:false,rownumbers:true,enabledEdit:true,
		                    selectRowButtonOnly:true,//heightDiff: -10,
		                    toolbar: { 
		                    	items: [
                   						{ text: '保存', id:'add', click: save , icon:'add' },
                   	                	{ line:true },
                   	                	{ text: '删除', id:'delete', click: del ,icon:'delete' },
                   	               		{ line:true }
                   				]},
					});
	     		gridManager = $("#maingrid").ligerGetGridManager();
		}

     function  save(){
    	 var data = gridManager.getData();
    	 if (data.length == 0){
         	$.ligerDialog.error('请添加行数据');
         }else{
        	 if(!validateGrid(data)){
        		 return false;
        	 }
	    	 var budg_year = $("#budg_year").val();
	    	 var ParamVo =[];
	         $(data).each(function (){					
	        	 ParamVo.push(
					//表的主键
					this.type_code   +"@"+ 
					this.subj_code   +"@"+ 
					budg_year	+"@"+
					this.type_name   +"@"+ 
					this.subj_name   
					)
	         });
	        ajaxJsonObjectByUrl("addBudgCostTypeSet.do?isCheck=false",{ParamVo : ParamVo.toString()},function(responseData){
	            
	            if(responseData.state=="true"){
	                parent.query();
	                grid.deleteAllRows();
	                
	            }
	        });
        }
    }
    function del(){
    	 var data = grid.getCheckedRows();
   		 if(data.length == 0){
   				$.ligerDialog.warn('请选择要删除的行!');
                return;
            }else{
            	 for (var i = 0; i < data.length; i++){
            		 grid.remove(data[i]);
                 } 
            }
    }
    function validateGrid(data) {  
 		var msg="";
 		var rowm = "";
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
			if (v.type_code == "" || v.type_code == null || v.type_code == 'undefined') {
				rowm+="[科目类别名称]、";
			}  
			if (v.subj_code == "" || v.subj_code == null || v.subj_code == 'undefined') {
				rowm+="[支出预算科目名称]、";
			}  
			if(rowm != ""){
				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.type_code +"|"+v.subj_code;
			var value="第"+(i+1)+"行";
			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
				targetMap.put(key ,value);
			}else{
				msg += targetMap.get(key)+"与"+value+"科目类别名称 、支出预算科目名称 数据重复,请删除重复数据后再操作" + "\n\r";
			}
 		});
 		if(msg != ""){
 			$.ligerDialog.warn(msg);  
			return false;  
 		}else{
 			return true;  
 		} 	
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
   
    function loadDict(){
            //字典下拉框
    	autocomplete("#budg_year","../../../queryBudgYear.do?isCheck=false","id","text",true,true,"",true);
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>预算年度<font color ="red">*</font>:</b></td>
            	<td align="left" class="l-table-edit-td"><input  name="budg_year" type="text" id="budg_year"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            	<td align="left"></td>
            </tr> 
        </table>
    </form>
    <div id="toptoolbar" ></div>
   	<div id="maingrid"></div>
    </body>
</html>
