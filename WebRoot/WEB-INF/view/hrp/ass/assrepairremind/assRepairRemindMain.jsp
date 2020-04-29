<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<!-- 维修提醒页面 -->
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var state_name ;
    $(function ()
    {
    	 loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	 

		$("#near").change(function(){
			if($("#near").prop("checked") == true){
				state_name = 1 ;
				$('#last').prop('checked',false) ;
				$('#past').prop('checked',false) ;
			}else{
				state_name = '' ;
			}
			query();
		})
		$("#last").change(function(){
			if($("#last").prop("checked") == true){
				state_name = 2 ;
				$('#near').prop('checked',false) ;
				$('#past').prop('checked',false) ;
			}else{
				state_name = '' ;
			}
			query();
		})
		$("#past").change(function(){
			if($("#past").prop("checked") == true){
				state_name = 3 ;
				$('#near').prop('checked',false) ;
				$('#last').prop('checked',false) ;
			}else{
				state_name = '';
			}
			query();
		})
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1; 
 
        //根据表字段进行添加查询条件  
        	grid.options.parms.push({name:'queryDate',value:$("#queryDate").val()});
        	/*   grid.options.parms.push({name:'apply_emp',value:liger.get("create_emp").getValue().split(",")[0]});
    	   grid.options.parms.push({name:'war_day',value:${ass_05002}});  */
    	   grid.options.parms.push({name:'ass_card_no',value:$("#ass_card_no").val()});
    	   grid.options.parms.push({name:'apply_no',value:$("#apply_no").val()});
    	   grid.options.parms.push({name:'state_name',value:state_name}); 
    	//加载查询条件
    	grid.loadData(grid.where);
    	$("#resultPrint > table > tbody").empty();
     }
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '申请单号', name: 'apply_no', align: 'left',
                    	    },
                     { display: '申请人', name: 'APPLY_NAME', align: 'left'
					 		},
                     { display: '申请日期', name: 'apply_date', align: 'left'
					 		},
                     { display: '制单人', name: 'CREATE_NAME', align: 'left'
					 		},
                     { display: '制单日期', name: 'create_date', align: 'left'
					 		},
				     { display: '审核人', name: 'AUDIT_NAME', align: 'left'
					 		},
                     { display: '审核日期', name: 'audit_date', align: 'left'
					 		},
                     { display: '维修部门', name: 'DEPT_NAME', align: 'left'
					 		},
                     { display: '紧急程度', name: 'SHARP_NAME', align: 'left',
					 			/* render : function(rowdata, rowindex,value) {
					 				
									if(rowdata.sharp_degree == 1){
										
										return "中";
										
									}else if(rowdata.sharp_degree == 0){
										
										return "高";
										
									}else if(rowdata.sharp_degree == 2){
										
										return "低";
										
									}

								} */
					 		},
                     { display: '备注', name: 'note', align: 'left'
					 		},
			 		{ display: '状态', name: 'state_name', align: 'center',width:60,
			 		  	render:function(rowdata,index,value){
			 				if(value =='过期'){
			 					return '<span style="color:red">过期</span>';
			 				}else if(value == '到期'){
			 					return '<span style="color:blue">到期</span>';
			 				}else if(value =='临近'){
			 					return '<span style="color:#00009C">临近</span>';
			 				}
			 			}  
			 		}	
					 		/* { display: '状态', name: 'st_name', align: 'center',width:60 ,
					 			render:function(rowdata,index,value){
					 				if(rowdata.days < 0){
					 					return '<span style="color:red">过期</span>';
					 				}else if(rowdata.days == 0){
					 					return '<span style="color:blue">到期</span>';
					 				}else if(rowdata.days < rowdata.warn_days){
					 					return '<span style="color:#00009C">临近</span>';
					 				}
					 			} 
					 		}*/
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssRepairRemind.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,delayLoad :true,
                      toolbar: { items: [
                                     	{ text: '查询', id:'search', click: query,icon:'search' },
                				    				]} 
    			 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
    function loadDict(){
    	var param = {query_key:''};

    	/* autocomplete("#create_emp", "../queryUserDict.do?isCheck=false","id", "text",true,true,param,true,null,"160"); */

    	autodate("#queryDate",'yyyy-MM-dd');
    	
    
		$("#queryDate").ligerTextBox({width:200});
		$("#ass_card_no").ligerTextBox({width:200});
		$("#apply_no").ligerTextBox({width:200});
     }

 
  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
         <tr>
        	
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">查询日期：</td>
            <td align="left" class="l-table-edit-td">
            	<input class="Wdate" name="queryDate" type="text" id="queryDate" ltype="text" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            </td>
            <td align="left"></td>
        <!--  <td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单人：</td>
			<td align="left" class="l-table-edit-td"><input name="create_emp"
				type="text" id="create_emp" 
				 /></td> -->
				 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">申请单号：</td>
            <td align="left" class="l-table-edit-td"><input name="apply_no" type="text" id="apply_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
				 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">卡片编码：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="ass_card_no" type="text" id="ass_card_no" ltype="text" type="text" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">查询内容：</td>
            <td align="left" class="l-table-edit-td" colspan="2">
	            <input id="near" type="checkbox" ltype="text" />临近
	            <input id="last" type="checkbox" ltype="text" />到期
	            <input id="past" type="checkbox" ltype="text" />过期
	        </td>
	        <td align="left"></td>
	       
	        
        </tr> 
    </table>
	<div id="maingrid"></div>
 
 
 
</body>
</html>
