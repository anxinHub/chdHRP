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
    <script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
    <script type="text/javascript">
     var grid;
	 var gridManager = null;
     var dataFormat;
     var budg_year;
     $(function (){
    	loadHead(null);
    	
        loadDict();//加载下拉框
        loadForm();
        
   
       
        $("body").keydown(function() {
            if (event.keyCode == "9") {//keyCode=9是Tab
             grid.addRowEdited({
            	 measure_code: '' ,
            	 measure_code: '',
            	 is_stop : '0',
            	 id:0 ,
            	 text:'否'
         		});
             }
         });
       
     });  
     function loadHead(){
	     grid = $("#maingrid").ligerGrid({ 
			     columns : [ 
							{display : '运营编码', name : 'measure_code', minWidth : 80, align : 'center',editor : {type : 'text'}
								},
							{display : '运营名称', name : 'measure_name', minWidth : 80, align :'center',	editor : {type : 'text'}
								},
							{display : '是否停用', name : 'is_stop', textField : 'text', minWidth : 80, align : 'center',
									editor : {
										type : 'select',
										valueField : 'id',
										textField : 'text',
										data:yes_or_no.Rows,
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
	    	 var ParamVo =[];
	         $(data).each(function (){					
	        	 ParamVo.push(
					//表的主键
	        			this.measure_code   +"@"+ 
						this.measure_name   +"@"+ 
						this.is_stop   
					)
	         });
	        ajaxJsonObjectByUrl("addBudgOperationMeasure.do?isCheck=false",{ParamVo : ParamVo.toString()},function(responseData){
	            
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
			if (v.measure_code == "" || v.measure_code == null || v.measure_code == 'undefined') {
				rowm+="[运营尺度编码]、";
			}  
			if (v.measure_name == "" || v.measure_name == null || v.measure_name == 'undefined') {
				rowm+="[运营尺度名称]、";
			}  
			if (v.is_stop == "" || v.is_stop == null || v.is_stop == 'undefined') {
				rowm+="[是否停用]、";
			}  
			if(rowm != ""){
				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.measure_code ;
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
	    $("form").ligerForm();
	}       
   function setBudgYear(){
	   is_stop = $("#is_stop").val();
	   loadHead();
   }
   function saveBudgWageItemCostShip(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
         //字典下拉框
    	//autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text", true, true);
	        
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        </table>
    </form>
   	<div id="toptoolbar" ></div>
   	<div id="maingrid"></div>
    </body>
</html>
