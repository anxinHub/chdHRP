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
     var dataFormat;
     var budg_year ;
     $(function (){
    	loadHead()
        loadDict()//加载下拉框
        loadForm();
         $("body").keydown(function() {
             if (event.keyCode == "9") {//keyCode=9是Tab
              grid.addRowEdited({
                	subj_code: '' ,
                	dept_id:'',
                	last_year_income: 0 ,
                	budg_value: 0 ,
                	remark: '' ,
                	dept_suggest : 0 
                	
          		});
              }
          });
         $("#year").change(function(){
         	grid.deleteAllRows();
         	budg_year = liger.get("year").getValue();
         	
         	loadHead();
         })
     });  
     
     function loadHead(){
     	grid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '科室编码', name: 'dept_id', align: 'left' ,
					 			valueField:"dept_id",textField : 'dept_name',
					 			editor : {
									type : 'select',
									valueField : 'dept_id',
									textField : 'dept_name',
									url : 'queryDept.do?isCheck=false',
									keySupport : true,
									autocomplete : true,
								} 
					 		},
                      { display: '科目编码', name: 'subj_code', align: 'left' ,
 					 			valueField:"id",textField : 'text',
 					 			editor : {
 									type : 'select',
 									valueField : 'id',
 									textField : 'text',
 									url : '../../../queryBudgSubj.do?isCheck=false&subj_type='+'04'+'&budg_year='+budg_year+'&is_last='+'1',
 									keySupport : true,
 									autocomplete : true,
 								} 
 					 		},
 					  { display: '上年收入', name: 'last_year_income', align: 'left',editor : {type : 'float'}
  					 		},
                      { display: '预算值(E)', name: 'budg_value', align: 'left',editor : {type : 'float'}
 					 		},
                      { display: '说明(E)', name: 'remark', align: 'left',editor : {type : 'string'}
 					 		},
 					 { display: '科室意见(元/E)', name: 'dept_suggest', align: 'left',editor : {type : 'float'}
 					 		}
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true, width: '100%', height: '100%', 
                      checkbox: true,rownumbers:true,enabledEdit :true ,isAddRow:false ,
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
    	 if($("form").valid()){
    		 var data = gridManager.getData();
        	 if (data.length == 0){
             	$.ligerDialog.error('请添加行数据');
             }else{
            	 if(!validateGrid(data)){
            		 return false;
            	 }
    	    	 var budg_year = liger.get("year").getValue();
    	    	 var ParamVo =[];
    	         $(data).each(function (){					
    	        	 ParamVo.push(
    					budg_year	+"@"+
    					this.dept_id   +"@"+ 
    					this.dept_name   +"@"+
    					this.subj_code   +"@"+ 
    					this.text   +"@"+ 
    					(this.last_year_income? this.last_year_income:"")  +"@"+ 
    					(this.budg_value? this.budg_value:"")  +"@"+ 
    					(this.remark?this.remark:"")   	+"@"+ 
    					(this.dept_suggest? this.dept_suggest:"-1")
    				)
    	         });
    	        ajaxJsonObjectByUrl("addMedInDYBudgUp.do?isCheck=false",{ParamVo : ParamVo.toString()},function(responseData){
    	            
    	            if(responseData.state=="true"){
    	                parent.query();
    	                grid.deleteAllRows();
    	                
    	            }
    	        });
            }
    	 }
    }
    function del(){
    	 var data = grid.getCheckedRows();
   		 if(data.length == 0){
   			$.ligerDialog.error('请选择要删除的行!');
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
 			if (v.dept_id == "" || v.dept_id == null || v.dept_id == 'undefined') {
				rowm+="[科室编码]、";
			} 
			if (v.subj_code == "" || v.subj_code == null || v.subj_code == 'undefined') {
				rowm+="[科目编码]、";
			}
			 
			if(rowm != ""){
				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.dept_id+v.subj_code 
			var value="第"+(i+1)+"行";
			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
				targetMap.put(key ,value);
			}else{
				msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
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
       //$("form").ligerForm();
    }       
    function loadDict(){
        //字典下拉框
        
	 //预算年度下拉框
    autocomplete("#year","../../../queryBudgYear.do?isCheck=false","id","text",true,true,"",true);
        
    budg_year = liger.get("year").getValue();
   
	}  
    </script>
  
  </head>
  
   <body>
   	<div id="pageloading" class="l-loading" style="display: none"></div>
   	<form name="form1" method="post"  id="form1" >
   		<table cellpadding="0" cellspacing="0" class="l-table-edit" >
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>预算年度<span style="color:red">*</span>:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="year" type="text" id="year" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr> 
    	</table>
   	</form>
   	
  	<div id="toptoolbar" ></div>
   	<div id="maingrid"></div>
   </body>
</html>
