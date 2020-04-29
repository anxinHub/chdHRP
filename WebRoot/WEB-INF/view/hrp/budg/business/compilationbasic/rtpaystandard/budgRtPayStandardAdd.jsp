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
    var dataFormat;
    var grid;
	var gridManager = null;
    var budg_year;
    $(function (){
    	loadHead() ;
        loadDict()//加载下拉框
        loadForm();
         budg_year = liger.get("budg_year").getValue();
         $("body").keydown(function() {
             if (event.keyCode == "9") {//keyCode=9是Tab
              grid.addRowEdited({
            	   insurance_code: '' ,
            	   outpatient_charge:0,
            	   day_bed_charge:0,
            	   o_workload_budg:0,
            	   i_workload_budg:0,
            	   remark:''
          		});
              }          
         });
         $("#budg_year").change(function(){
         	grid.deleteAllRows();
         	budg_year = liger.get("budg_year").getValue();
         	loadHead();
         })
     });   
  function loadHead(){
     	grid = $("#maingrid").ligerGrid({
     		columns: [ 
                { display: '医保类型', name: 'insurance_code', align: 'left',valueField : 'id',textField : 'text',
                	editor : {
						type : 'select',
						valueField : 'id',
						textField : 'text',
						url : '../../../queryBudgYBTypeByMode.do?isCheck=false&pay_mode_code=RT',
						keySupport : true,
						autocomplete : true,
				 		}
                },
                 { display: '门诊均次费用(元) (E)', name: 'outpatient_charge', align: 'right',editor :{type : 'float'},
	                	render:function(rowdata,rowindex,value){
			 				if(value){
			 					return formatNumber(value,2,1);
			 				}
			 			}
				 	}, 
                { display: '床日均次费用(人次) (E)', name: 'day_bed_charge', align: 'right',editor :{type : 'float'},
				 			render:function(rowdata,rowindex,value){
				 				if(value){
				 					return formatNumber(value,2,1);
				 				}
				 			}
				 		},
                { display: '门诊业务量预算(元) (E)', name: 'o_workload_budg', align: 'right',editor :{type : 'float'},
				 			render:function(rowdata,rowindex,value){
				 				if(value){
				 					return formatNumber(value,2,1);
				 				}
				 			}
				 		},
                { display: '床日业务量预算(床日) (E)', name: 'i_workload_budg', align: 'right',editor :{type : 'float'},
				 			render:function(rowdata,rowindex,value){
				 				if(value){
				 					return formatNumber(value,2,1);
				 				}
				 			}
				 		},
                { display: '备注 (E)', name: 'remark', align: 'left',editor :{type : 'string'}
				 		}
                ],
              	dataAction: 'server',dataType: 'server',checkbox: true,usePager:true,
                width: '100%', height: '100%',isAddRow:false,rownumbers:true,enabledEdit:true,
                selectRowButtonOnly:true,//heightDiff: -10,
                toolbar: { 
                	items: [
       						{ text: '保存', id:'add', click: saveBudgRtPayStandard , icon:'add' },
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
					this.insurance_code +"@"+
					(this.outpatient_charge?this.outpatient_charge:"")+"@"+
					(this.day_bed_charge?this.day_bed_charge:"")+"@"+
					(this.o_workload_budg?this.o_workload_budg:"")+"@"+
					(this.i_workload_budg?this.i_workload_budg:"")+"@"+
					(this.remark?this.remark:"-1")
					)
	         });
	        ajaxJsonObjectByUrl("addBudgRtPayStandard.do?isCheck=false",{ParamVo : ParamVo.toString()},function(responseData){
	            
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
			if (v.insurance_code== "" || v.insurance_code == null || v.insurance_code == 'undefined') {
				rowm+="[医保类型编码]、";
			} 
			if(rowm != ""){
  				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
  			}
  			msg += rowm;
  			var key=v.insurance_code 
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
   
    function saveBudgRtPayStandard(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	//加载年度   
        autocomplete("#budg_year","../../../queryBudgYear.do?isCheck=false","id","text",true,true,'',true);   
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
        </tr> 
    </table>
    </form>
     <div id="toptoolbar" ></div>
   	<div id="maingrid"></div>
    </body>
</html>
