<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
	<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
	<style>
		#tree {
			border: 1px solid #aecaf0;
			padding: 20px 30px 30px 30px;
			display: inline-box;
			width: 400px;
			margin: 0 30px;
			boxsizing: border-box;
			overflow: auto;
			height: 300px;
		}
		#tree:before {
			content: '选择对应科室:';
			display: block;
			text-align: center;
			margin-bottom: 20px;
		}
	</style>
    <script type="text/javascript">
     var dataFormat;
     var tree;
     
     $(function (){
         loadDict()//加载下拉框
        loadForm();
         loadTree();
         
         
     });  
     function loadTree(){
   	 	var setting={
       		 async:{
       			 url : '../../queryHosDeptTerr.do?isCheck=false',
       					 dataType:'JSON',
       		 },
       		 data: {simpleData: {enable: true}},
       	   		
       	   		treeNode:{open:true},
       	   		
       	   		check: {
       	   			enable: true,
       	   			chkStyle: "checkbox"
       	   		},
       		 };
	   	 ajaxJsonObjectByUrl("../../queryHosDeptTerr.do?isCheck=false",{},function(responseData){
	   		 //responseData.Rows.shift();
			  tree = $.fn.zTree.init($("#tree"), setting, responseData.Rows);
			  tree.expandAll(true);
			  getCheckNodes();
		 });
     }
    function save(){

   	 var checkedNode = tree.getCheckedNodes();
		  var deptidArr = [];
		  
		  checkedNode.map(function (item ,index) {
			  if (item.deptId) {
				  deptidArr.push({dept_id: item.deptId});
			  }
		  })
       var formPara={
          rep_team_code:$("#rep_team_code").val(),
           
          rep_team_name:$("#rep_team_name").val(),
           
          is_stop:liger.get("is_stop").getValue(),
          ParamVo : JSON.stringify(deptidArr)
        };
        ajaxJsonObjectByUrl("updateAssRepairTeamDict.do",formPara,function(responseData){
            
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
       function saveAssRepairTeamDict(){
           if($("form").valid()){
               save();
           }
      }
       
       
   	function is_addRow() {
   		setTimeout(function() { //当数据为空时 默认新增一行
   			grid.addRow();
   		}, 1000);
   	}
       function loadDict(){
               //字典下拉框
       	autocomplete("#is_stop", "../../queryAssYesAndNo.do?isCheck=false", "id","text", true, true,null,true,${is_stop});
       	$("#rep_team_code").ligerTextBox({width:160,disabled:true});
        } 
       
       function getCheckNodes(){
    	   var url = "queryAssRepTeamDeptMapByTeamCode.do?isCheck=false&rep_team_code=${rep_team_code}";
    	   $.ajax({
    		   url:url,
    		   dataType:"JSON",
    		   type:"POST",
    		   success:function(res){
    			   var data = [];
    			   for(var j = 0 ; j<res.Rows.length; j++){
    				   data.push(res.Rows[j].dept_id.toString());
    			   }
    			   var treeNodes = tree.getNodesByFilter(function(node){
    				   if(node.deptId&&data.indexOf(node.deptId)!=-1){
    					   return true;
    				   }else{
    					   return false;
    				   }
    			   });
    			   for(var i =0;i<treeNodes.length;i++){
    				   tree.checkNode(treeNodes[i], true, true);
    			   }
    			  
    		   }
    	   })
       }
       </script>
     
     </head>
     
      <body>
      <div id="pageloading" class="l-loading" style="display: none"></div>
      <form name="form1" method="post"  id="form1" >
           <table cellpadding="0" cellspacing="0" class="l-table-edit" >
   		
           <tr>
               <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font><b/>班组编码：</td>
               <td align="left" class="l-table-edit-td"><input name="rep_team_code" type="text" id="rep_team_code" value="${rep_team_code}" ltype="text" validate="{required:true,maxlength:20}" /></td>
               <td align="left"></td>
           </tr> 
           <tr>
               <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font><b/>班组名称：</td>
               <td align="left" class="l-table-edit-td"><input name="rep_team_name" type="text" id="rep_team_name" value="${rep_team_name}" ltype="text" validate="{required:true,maxlength:20}" /></td>
               <td align="left"></td>
           </tr> 
           <tr>
               <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font><b/>是否停用：</td>
               <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}" /></td>
               <td align="left"></td>
           </tr> 
       </table>
       </form>
       <div id="tree" class="ztree"></div>
       </body>
   </html>
