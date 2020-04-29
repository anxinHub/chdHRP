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
     var index_code ;
     $(function (){
    	 
    	 loadHeadRight();
    	 loadDict() ;
    	 
    	 loadForm();
    	 
    	 $("body").keydown(function() {
             if (event.keyCode == "9") {//keyCode=9是Tab
              	grid.addRowEdited({
                	dept_id: '' ,
                	count_value: '' ,
                	budg_value: 0 ,
                	remark: '' ,
                	hos_suggest_resolve : 0 
                	
          		});
             
             	var data = gridRight.getData();
             	
             	//如果右侧表格有数据 则全部清除
             	
             	if(data.length > 0){
             		
             		gridRight.deleteAllRows();
             	}
              	
              }
         });
    	 
		$("#year").change(function(){
    		 
    		 budg_year = liger.get("year").getValue();
	       	  
    		 autocomplete("#index_code","../../../../../qureyBudgIndexFromPlan.do?isCheck=false&budg_level=03&edit_method=04&budg_year="+budg_year,"id","text",true,true,'',true,'',200);
        })
        
    	$("#index_code").change(function(){
       	  
    		
	       	 index_code = liger.get("index_code").getValue();
	       	  
	       	 loadHead();
	       	 
	       	 //grid.deleteAllRows();
    		 
        })
     });  
     
     function loadHead(){
     	grid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '科室', name: 'dept_id', align: 'left' ,
				 			valueField:"id",textField : 'text',
				 			editor : {
								type : 'select',
								valueField : 'id',
								textField : 'text',
								url : '../../../../../queryBudgIndexDeptSet.do?isCheck=false&index_code='+index_code,
								keySupport : true,
								autocomplete : true
							} 
 					 	},
 					  { display: '计算值', name: 'count_value', align: 'right'	,
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
 					 		}
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true, width: '100%', height: '100%', 
                      checkbox: true,rownumbers:true,enabledEdit :true ,isAddRow:false ,isSingleCheck:true,
                      delayLoad:true ,
                      selectRowButtonOnly:true,//heightDiff: -10,
                      onSelectRow : addRightData,//选中行 加载右侧表格 数据
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
     function loadHeadRight(){
   		gridRight = $("#maingridRight").ligerGrid({
            columns: [ 
                      { display: '运营尺度(E)', name: 'measure_name', align: 'center',editor : {type : 'string'},
	                    	  totalSummary: {
	      						align: 'right',
	      						render: function (suminf, column, cell) {
	      							return '<div>合计：</div>';
	      						}
	      					}
 					 	},
                      { display: '运营预期(E)', name: 'measure_value', align: 'right',editor : {type : 'float'},
 					 			render:function(rowdata,rowindex,value){
					 				if(value){
					 					return formatNumber(value,2,1);
					 				}
					 			},
 					 		},
 				 	 { display: '概率(E)', name: 'rate', align: 'right',editor : {type : 'float'},
 					 			render:function(rowdata,rowindex,value){
 					 				if(value){
 					 					return formatNumber(value,2,0)+"%" ;
 					 				}
 					 			},
 					 			totalSummary: {
								align: 'left',
								render: function (suminf, column, cell) {
									return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum,2,0)+"%" + '</div>';
								}
							}
 					 	},
 					 { display: '计算值', name: 'count_value', align: 'right',
 					 		totalSummary: {
 								align: 'left',
 								render: function (suminf, column, cell) {
 									return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum,2,1) + '</div>';
 								}
 							}
 					 	}
                      ],
                      dataAction: 'server',dataType: 'server',usePager:false,
                      //url:'queryBudgWorkHosRate.do?isCheck=false',
                      //data:row.detail,
                      width: '100%', height: '100%', checkbox: true,rownumbers:false,enabledEdit :true ,isAddRow:false ,
                      delayLoad:true ,
                      onAfterEdit :  updateTotal,
                      selectRowButtonOnly:true,//heightDiff: -10,
                      toolbar: { items: [
                      	{ text: '生成（<u>G</u>）', id:'add', click: generate, icon:'add' },
     	                { text: '删除（<u>R</u>）', id:'delete', click: removeRows,icon:'delete' },
 		                { text: '计算（<u>C</u>）', id:'collect', click: count,icon:'collect' }
     				]},
     				
                    });

         gridManagerRight = $("#maingridRight").ligerGetGridManager();
	}
     
     //选中行后  如果右侧数据已设置则加载设置的 数据
     function addRightData(rowdata){
    	 
    	 var data = gridRight.getData();
       	//如果右侧表格有数据 则全部清除
       	if(data.length > 0){
       		gridRight.deleteAllRows();
       	}
       	
    	if(rowdata.detail){
          	
    		gridManagerRight.reload(rowdata.detail);
    		 
    	 }
    	 
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
    	    	 budg_year = liger.get("year").getValue();
    	    	 index_code = liger.get("index_code").getValue();
    	    	 var ParamVo =[];
    	         $(data).each(function (){	
    	        		 ParamVo.push(
   	     					budg_year +"@"+
   	     					index_code  +"@"+ 
   	     					this.dept_id  +"@"+ 
   	     					this.text  +"@"+ 
   	     					this.count_value +"@"+
   	     					(this.budg_value? this.budg_value:"")  +"@"+
   	     					(this.remark?this.remark:"")  +"@"+
   	     					(this.hos_suggest_resolve? this.hos_suggest_resolve:"-1") +"@"+
   	     					(this.datail?JSON.stringify(this.detail.Rows):"-1") +"@"
   	     					)
    	        	
    	         });
   	        	 ajaxJsonObjectByUrl("addProbDYBudgDown.do?isCheck=false",{ParamVo : ParamVo.toString()},function(responseData){
   	    	            
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
    
    
    //右侧grid 生成
    function generate(){
    	var data = grid.getCheckedRows();
    	
    	if(data.length == 1){
    		ajaxJsonObjectByUrl("setProbBudgRate.do?isCheck=false" ,{},function(responseData){
    			gridRight.deleteAllRows();
    			gridRight.addRows(responseData.Rows);
 	        });
    		
    	}else{
    		$.ligerDialog.error('请选择一行数据进行生成');
    	}
    	
    }
    
    //右侧grid 删除
	function removeRows(){
		var data = gridManagerRight.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
        	gridManagerRight.deleteRange(data);
        }
    }
    
	//右侧grid 计算
	function count(){
		var data = gridManagerRight.getData();
		
		var dataLeft = grid.getSelectedRow();
		
		if(data.length == 0){
			$.ligerDialog.error('没有需要计算的数据');
		}else{
			if(!dataLeft){
				
				$.ligerDialog.error('请在左侧选择一行数据再操作');
				
			}else{
				var count_value = 0; //存储 总计算值
				//var countValue = 0;// 存储 右侧表格每行 计算值
				var falg= 0 ; // 记录 总概率
				$(data).each(function (){
					falg = falg + Number(this.rate) ;
				})
				if(falg == 100){
					$(data).each(function (){
						this.count_value = Number(this.measure_value) * Number(this.rate) / 100 ;//计算右侧每行 计算值
						
						count_value = count_value + this.count_value ;
						//gridRight.updateCell('count_value',countValue,this);
						
					})
					
					gridManagerRight.updateTotalSummary() ;
					
					grid.updateCell('count_value',count_value,dataLeft);
					
					dataLeft.detail = {"Rows":data,"Total":data.length};
					
					gridManagerRight.reload(dataLeft.detail);
					
				}else{
					$.ligerDialog.error('总概率不等于100%,不能计算');
				}
			}
		}
		
    }
	
	function updateTotal(){
		gridRight.updateTotalSummary();
	}
    
    function validateGrid(data) {  
    	var msg="";
 		var rowm = "";
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
			if (v.dept_id == "" || v.dept_id == null || v.dept_id == 'undefined') {
				rowm+="[指标编码]、";
			}
			if(rowm != ""){
				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.dept_id 
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
    
  //初始化 预算指标下拉框 （预算年度下拉框 与 预算指标下拉框下拉框 联动）
	$("#index_code").ligerComboBox({});
   
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
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>预算指标<span style="color:red">*</span>:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="index_code" type="text" id="index_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr> 
    	</table>
   	</form>
   	
  	<div id="toptoolbar" ></div>
   	<div >
   		<div  style="float: left; width: 65%;">
			<div id="toptoolbar" ></div>
		
			<div id="maingrid"></div>
		</div>
		<div  style="float: left; width: 35%;">
			<div id="righttoolbar" ></div>
		
			 <div id="maingridRight"></div>
		</div>
   	</div>
   </body>
</html>
