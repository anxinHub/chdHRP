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
    var grid ;
    var budg_year;
    var insurance_code ;
	var gridManager = null;
    $(function (){
    	loadHead() ;
        loadDict()//加载下拉框
        loadForm();
        $("body").keydown(function() {
             if (event.keyCode == "9") {//keyCode=9是Tab
              grid.addRowEdited({
            	   insurance_code: '' ,
            	   disease_code:'',
            	   charge_standard:0
          		});
              }          
         });
         
         $("#insurance_code").change(function(){
         	grid.deleteAllRows();
         	insurance_code = liger.get("insurance_code").getValue();
         	loadHead();
         })
         
         
     });   
    function loadHead(){
     	grid = $("#maingrid").ligerGrid({
     		columns: [ 
                { display: '医保单病种', name: 'disease_code', align: 'left',
                	valueField : 'idDC',
					textField : 'textDC',
				 			editor : {
								type : 'select',
								valueField : 'idDC',
								textField : 'textDC',
								url : 'queryBudgSingleDC.do?isCheck=false&insurance_code='+insurance_code,
								keySupport : true,
								autocomplete : true,
							} 
				 		},
                { display: '费用标准(元)(E)', name: 'charge_standard', align: 'right',editor :{type : 'float'},
				 			render:function(rowdata,rowindex,value){
				 				if(value){
				 					return formatNumber(value,2,1);
				 				}
				 			}
				 		}
                ],
                  	dataAction: 'server',dataType: 'server',checkbox: true,usePager:true,
                    width: '100%', height: '100%',isAddRow:false,rownumbers:true,enabledEdit:true,
                    selectRowButtonOnly:true,//heightDiff: -10,
                    toolbar: { 
                    	items: [
           						{ text: '保存', id:'add', click: saveBudgDbzPrice , icon:'add' },
           	                	{ line:true },
           	                	{ text: '删除', id:'delete', click: del ,icon:'delete' },
           	               		{ line:true }
           				]},
			});
 		gridManager = $("#maingrid").ligerGetGridManager();
}      

     function  save(){
    	 var data = gridManager.getData();
    	 budg_year = liger.get("budg_year").getValue();
    	 insurance_code = liger.get("insurance_code").getValue();
    	 if (data.length == 0){
         	$.ligerDialog.error('请添加行数据');
         }else{
        	 if(!validateGrid(data)){
        		 return false;
        	 }
	    	 var ParamVo =[];
	         $(data).each(function (){					
	        	 ParamVo.push(					
	        		budg_year   +"@"+ 
					insurance_code  +"@"+
					this.disease_code+"@"+
					(this.charge_standard?this.charge_standard:"-1")

					)
	         });
	        ajaxJsonObjectByUrl(" addBudgDbzPrice.do?isCheck=false",{ParamVo : ParamVo.toString()},function(responseData){
	            
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
			if (v.disease_code == "" || v.disease_code == null || v.disease_code == 'undefined') {
				rowm+="[医保单病种]、";
			} 
			if(rowm != ""){
  				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
  			}
  			msg += rowm;
  			var key=v.disease_code 
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
   
    function saveBudgDbzPrice(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	//加载年度   
        autocomplete("#budg_year","../../../queryBudgYear.do?isCheck=false","id","text",true,true,'',true);  
      	//加载医保类型
        autocomplete("#insurance_code","../../../queryBudgYBType.do?isCheck=false","id","text",true,true,'',true);
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年度：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>  
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医保类型：</td>
            <td align="left" class="l-table-edit-td"><input name="insurance_code" type="text" id="insurance_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>      
        </tr>       
    </table>
    </form>   
    <div id="toptoolbar" ></div>
   	<div id="maingrid"></div>
    </body>
</html>
