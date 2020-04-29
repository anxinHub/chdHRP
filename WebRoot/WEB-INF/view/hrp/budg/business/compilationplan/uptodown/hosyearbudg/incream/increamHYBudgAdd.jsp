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
     
     var get_way ;
     $(function (){
    	 loadHead(null);
    	 loadDict() ;
    	 
    	 loadForm();
    	 
    	 $("body").keydown(function() {
             if (event.keyCode == "9") {//keyCode=9是Tab
              grid.addRowEdited({
             		year: '' ,
                	index_code: '' ,
                	last_year_workload: '' ,
                	grow_rate: '' ,
                	grow_value: '' ,
                	budg_value: 0 ,
                	remark: '' ,
                	dept_suggest_sum : 0 
                	
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
                      { display: '指标编码', name: 'index_code', align: 'left' ,
 					 			valueField:"id",textField : 'text',
 					 			editor : {
 									type : 'select',
 									valueField : 'id',
 									textField : 'text',
 									url : '../../../../../qureyBudgIndexFromPlan.do?isCheck=false&budg_level=01&edit_method=02&budg_year='+budg_year,
 									keySupport : true,
 									autocomplete : true,
 									onChanged : queryLastYearWorkload
 								} 
 					 		},
 					  { display: '上年业务量', name: 'last_year_workload', align: 'right',
 					 			render:function(rowdata,rowindex,value){
					 				if(value){
					 					return formatNumber(value,2,1);
					 				}
					 			}
 					 		},
 					  { display: '增长比例(E)', name: 'grow_rate', align: 'left',editor : {type : 'float',onChanged : setCountValueAfterRate},
 					 			render:function(rowdata,rowindex,value){
					 				if(value){
					 					return value+"%";
					 				}
					 			}
 					 		},
 					  { display: '增长额(E)', name: 'grow_value', align: 'right',editor : {type : 'float',onChanged : setCountValueAfterValue},
 					 			render:function(rowdata,rowindex,value){
					 				if(value){
					 					return formatNumber(value,2,1);
					 				}
					 			}
 					 		},
 					  { display: '计算值(E)', name: 'count_value', align: 'right',
 					 			render:function(rowdata,rowindex,value){
					 				if(value){
					 					return formatNumber(value,2,1);
					 				}
					 			}
 					 		},
                      { display: '预算值(E)', name: 'budg_value', align: 'right',editor : {type : 'float'},
 					 			render:function(rowdata,rowindex,value){
					 				if(value){
					 					return formatNumber(value,2,1);
					 				}
					 			}
 					 		},
                      { display: '说明(E)', name: 'remark', align: 'left',editor : {type : 'string'}
 					 		},
 					 { display: '科室意见汇总(元/E)', name: 'dept_suggest_sum', align: 'left',editor : {type : 'float'},
 					 			render:function(rowdata,rowindex,value){
					 				if(value){
					 					return formatNumber(value,2,1);
					 				}
					 			}
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
     
     //选择 指标后 查询其上年业务量 并更新上年业务量单元格
     function queryLastYearWorkload(rowdata){
    	budg_year = liger.get("year").getValue();
    	if(rowdata.record.index_code){
    		$.post("queryLastYearWork.do?isCheck=false&budg_year="+budg_year+"&index_code="+rowdata.record.index_code,null,function(responseData){
       			
               	var para = eval("("+responseData+")") ;
               	
               	//alert(JSON.stringify(para));
               	get_way = para.get_way ;
               	if(get_way=="04"){
               		rowdata.record.notEidtColNames.push("grow_value");
               	}
               	if(get_way=="05"){
               		rowdata.record.notEidtColNames.push("grow_rate");
               	}
               	
               //	alert(get_way) ;
               	
               	 if(para.last_year_workload){
               		gridManager.updateCell('last_year_workload',para.last_year_workload,rowdata.record);
               	 }
            });
    	}else{
    		$.ligerDialog.error('指标编码不能为空');
    	}
     }
     
     //填写、修改 增长比例后  更新 计算值单元格
     function setCountValueAfterRate(rowdata){
    	 
    	 if(rowdata.record.grow_rate){
    		 if(rowdata.record.last_year_workload){
    	   		 var count_value = Number(rowdata.record.last_year_workload)*(1+Number(rowdata.record.grow_rate)/100) ;
    	   		 
    	   		 gridManager.updateCell('count_value',count_value,rowdata.record);
       		 }else{
       			$.ligerDialog.error('指标编码为'+rowdata.record.index_code+' 的数据,上年业务量未维护,请至\'医院年度历史指标数据采集\'处维护数据后操作!');
       		 }
    	 }else{
    		 $.ligerDialog.error('增长比例不能为空,且必须位数字');
    	 }
    	
     }
     
	//填写、修改 增长额后  更新 计算值单元格
     function setCountValueAfterValue(rowdata){
    	  if(rowdata.record.grow_value){
    		  if(rowdata.record.last_year_workload){	 
    	   		    var count_value = Number(rowdata.record.last_year_workload + Number(rowdata.record.grow_value)) ;
    	   		 
    	   		    gridManager.updateCell('count_value',count_value,rowdata.record);
    	   		}else{
    	   			$.ligerDialog.error('指标编码为'+rowdata.record.index_code+' 的数据,上年业务量未维护,请至\'医院年度历史指标数据采集\'处维护数据后操作!');
    	  		 }
     	 }else{
     		 $.ligerDialog.error('增长额不能为空,且必须位数字');
     	 }
      }
     //保存
     function  save(){
    	 if($("form").valid()){
    		 var data = gridManager.getData();
        	 if (data.length == 0){
             	$.ligerDialog.error('请添加行数据');
             }else{
            	 if(!validateGrid(data)){
            		 return false;
            	 }
    	    	 budg_year = liger.get("year").getValue();
    	    	 var ParamVo =[];
    	         $(data).each(function (){					
    	        	 ParamVo.push(
    					budg_year	+"@"+
    					this.index_code   +"@"+ 
    					(this.last_year_workload?this.last_year_workload:"-1")  +"@"+ 
    					(this.grow_rate? this.grow_rate:"")  +"@"+ 
    					(this.grow_value? this.grow_value:"")  +"@"+ 
    					(this.count_value? this.count_value:"")  +"@"+ 
    					(this.budg_value? this.budg_value:"")  +"@"+ 
    					(this.remark?this.remark:"")   	+"@"+ 
    					(this.dept_suggest_sum? this.dept_suggest_sum:"-1")
    					)
    	         });
    	        ajaxJsonObjectByUrl("addIncreamHYBudgUp.do?isCheck=false",{ParamVo : ParamVo.toString()},function(responseData){
    	            
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
			if (v.index_code == "" || v.index_code == null || v.index_code == 'undefined') {
				rowm+="[指标编码]、";
			}  
			if(rowm != ""){
				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.index_code 
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
    autocomplete("#year","../../../../../queryBudgYear.do?isCheck=false","id","text",true,true,"",true);
        
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
