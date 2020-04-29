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
    <script type="text/javascript">
     var grid;
	 var gridManager = null;
     var dataFormat;
     var budg_year;
     var subj_type ;
     $(function (){
    	loadHead(null);
        loadDict();//加载下拉框
        loadForm();
    	
    	budg_year = liger.get("budg_year").getValue();
    	subj_type = liger.get("subj_type").getValue();
        $("body").keydown(function() {
            if (event.keyCode == "9") {//keyCode=9是Tab
             grid.addRowEdited({
            	acc_subj_code2: '' ,
               	subj_code: ''
         		});
             }
         });
        $("#budg_year").change(function(){
        	grid.deleteAllRows();
        	budg_year = $("#budg_year").val();
        	subj_type = liger.get("subj_type").getValue();
        	
        	loadHead();
        })
       
        $("#subj_type").change(function(){
        	grid.deleteAllRows();
        	budg_year = $("#budg_year").val();
        	subj_type = liger.get("subj_type").getValue();
        	
        	loadHead();
        })
     });  
     function loadHead(){
	     grid = $("#maingrid").ligerGrid({ 
			     columns : [ 
							{display : '会计科目名称', name : 'acc_subj_code2', textField : 'acc_subj_name', minWidth : 80, align : 'center',
									editor : {
										type : 'select',
										valueField : 'acc_subj_code2',
										textField : 'acc_subj_name',
										url : 'queryAccSubj.do?isCheck=false&acc_year='+budg_year+'&subj_type='+ '04',
										keySupport : true,
										autocomplete : true,
									}
								},
							{display : '预算科目名称', name : 'subj_code', textField : 'subj_name', minWidth : 80, align : 'center',
									editor : {
										type : 'select',
										valueField : 'subj_code',
										textField : 'subj_name',
										url : 'queryBudgIncomeTypeSet.do?isCheck=false&budg_year='+budg_year+'&subj_type='+ subj_type,
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
    	 subj_type = liger.get("subj_type").getValue();
    	 if (data.length == 0){
         	$.ligerDialog.error('请添加行数据');
         }else{
        	 if(!validateGrid(data)){
        		 return false;
        	 }
	    	 var ParamVo =[];
	         $(data).each(function (){					
	        	 ParamVo.push(
					//表的主键
					this.acc_subj_code2   +"@"+ 
					this.subj_code   +"@"+ 
					budg_year	+"@"+
					this.acc_subj_name   +"@"+ 
					this.subj_name   	+"@"+ 
					subj_type
					)
	         });
	        ajaxJsonObjectByUrl("addBudgAccSubjIncomeShip.do?isCheck=false",{ParamVo : ParamVo.toString()},function(responseData){
	            
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
   				$.ligerDialog.error('请选择行');
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
			if (v.acc_subj_code2 == "" || v.acc_subj_code2 == null || v.acc_subj_code2 == 'undefined') {
				rowm+="[会计科目名称]、";
			}  
			if (v.subj_code == "" || v.subj_code == null || v.subj_code == 'undefined') {
				rowm+="[预算科目名称]、";
			}  
			if(rowm != ""){
				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.acc_subj_code2 
			var value="第"+(i+1)+"行";
			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
				targetMap.put(key ,value);
			}else{
				msg += targetMap.get(key)+"与"+value+"会计科目相同 ,多个预算科目 不允许对应同一会计科目!!" + "\n\r";
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
   
   function saveBudgAccRelationship(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
         //字典下拉框
         
         //预算年度下拉框
    	 autocomplete("#budg_year","../../../queryBudgYear.do?isCheck=false","id","text",true,true,'',true);
         
    	 //收入预算科目类型下拉框
    	 autocomplete("#subj_type","../../../queryBudgIncomeSubjType.do?isCheck=false","id","text",true,true,"",true);
         
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>预算年度<span style="color:red">*</span>:</font></td>
                <td align="left" class="l-table-edit-td"><input  name="budg_year" type="text" id="budg_year"  ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>科目类型<span style="color:red">*</span>:</font></td>
                <td align="left" class="l-table-edit-td"><input  name="subj_type" type="text" id="subj_type"  ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
   	<div id="toptoolbar" ></div>
   	<div id="maingrid"></div>
    </body>
</html>
