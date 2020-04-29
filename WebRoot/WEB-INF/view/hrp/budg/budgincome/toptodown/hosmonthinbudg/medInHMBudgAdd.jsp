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
    	 loadHead();
         loadDict()//加载下拉框
         loadForm();
         $("body").keydown(function() {
             if (event.keyCode == "9") {//keyCode=9是Tab
              grid.addRowEdited({
                	subj_code: '' ,
                	m01: 0 ,
                	m02: 0 ,
                	m03 : 0 ,
                	m04: 0 ,
                	m05: 0 ,
                	m06: 0 ,
                	m07: 0 ,
                	m08: 0 ,
                	m09: 0 ,
                	m10: 0 ,
                	m11: 0 ,
                	m12: 0 
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
                      { display: '科目编码', name: 'subj_code', align: 'left' ,width:150,
 					 			valueField:"id",textField : 'text',
 					 			editor : {
 									type : 'select',
 									valueField : 'id',
 									textField : 'text',
 									url : '../../../queryBudgSubj.do?isCheck=false&subj_type='+'04'+'&budg_year='+budg_year,
 									keySupport : true,
 									autocomplete : true,
 								} 
 					 		},
 					 		{ display: '01月(元)', name: 'm01', align: 'right',width:120,editor : {type : 'float'}},
 		                    { display: '02月(元)', name: 'm02', align: 'right',width:120,editor : {type : 'float'}},
 		                    { display: '03月(元)', name: 'm03', align: 'right',width:120,editor : {type : 'float'}},
 		                    { display: '04月(元)', name: 'm04', align: 'right',width:120,editor : {type : 'float'}},
 		                    { display: '05月(元)', name: 'm05', align: 'right',width:120,editor : {type : 'float'}},
 		                    { display: '06月(元)', name: 'm06', align: 'right',width:120,editor : {type : 'float'}},
 		                    { display: '07月(元)', name: 'm07', align: 'right',width:120,editor : {type : 'float'}},
 		                    { display: '08月(元)', name: 'm08', align: 'right',width:120,editor : {type : 'float'}},
 		                    { display: '09月(元)', name: 'm09', align: 'right',width:120,editor : {type : 'float'}},
 		                    { display: '10月(元)', name: 'm10', align: 'right',width:120,editor : {type : 'float'}},
 		                    { display: '11月(元)', name: 'm11', align: 'right',width:120,editor : {type : 'float'}},
 		                    { display: '12月(元)', name: 'm12', align: 'right',width:120,editor : {type : 'float'}}
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
    	        	ParamVo.push( budg_year +"@"+ "01" +"@"+ this.subj_code +"@"+ this.text +"@"+ (this.m01? this.m01:"-1"));
    	        	ParamVo.push( budg_year +"@"+ "02" +"@"+ this.subj_code +"@"+ this.text +"@"+ (this.m02? this.m02:"-1"));
    	        	ParamVo.push( budg_year +"@"+ "03" +"@"+ this.subj_code +"@"+ this.text +"@"+ (this.m03? this.m03:"-1"));
    	        	ParamVo.push( budg_year +"@"+ "04" +"@"+ this.subj_code +"@"+ this.text +"@"+ (this.m04? this.m04:"-1"));
    	        	ParamVo.push( budg_year +"@"+ "05" +"@"+ this.subj_code +"@"+ this.text +"@"+ (this.m05? this.m05:"-1"));
    	        	ParamVo.push( budg_year +"@"+ "06" +"@"+ this.subj_code +"@"+ this.text +"@"+ (this.m06? this.m06:"-1"));
    	        	ParamVo.push( budg_year +"@"+ "07" +"@"+ this.subj_code +"@"+ this.text +"@"+ (this.m07? this.m07:"-1"));
    	        	ParamVo.push( budg_year +"@"+ "08" +"@"+ this.subj_code +"@"+ this.text +"@"+ (this.m08? this.m08:"-1"));
    	        	ParamVo.push( budg_year +"@"+ "09" +"@"+ this.subj_code +"@"+ this.text +"@"+ (this.m09? this.m09:"-1"));
    	        	ParamVo.push( budg_year +"@"+ "10" +"@"+ this.subj_code +"@"+ this.text +"@"+ (this.m10? this.m10:"-1"));
    	        	ParamVo.push( budg_year +"@"+ "11" +"@"+ this.subj_code +"@"+ this.text +"@"+ (this.m11? this.m11:"-1"));
    	        	ParamVo.push( budg_year +"@"+ "12" +"@"+ this.subj_code +"@"+ this.text +"@"+ (this.m12? this.m12:"-1"));
    	         });
    	        ajaxJsonObjectByUrl("addMedInHMBudgUp.do?isCheck=false",{ParamVo : ParamVo.toString()},function(responseData){
    	            
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
 			if (v.subj_code == "" || v.subj_code == null || v.subj_code == 'undefined') {
 				rowm+="[科目编码]、";
 			}
 			 
 			if(rowm != ""){
 				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
 			}
 			msg += rowm;
 			var key=v.subj_code 
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
