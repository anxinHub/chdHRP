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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict();//加载下拉框
        
    	//加载数据
    	loadHead(null);	
        
		 loadHotkeys();
		
    });
    //查询
    function  query(){
    	
    		grid.options.parms=[];
    		
    		grid.options.newPage=1;
    		
        //根据表字段进行添加查询条件 
    	  
    	  grid.options.parms.push({name:'plan_id',value:liger.get("plan_id").getValue()}); 
    	  
    	  grid.options.parms.push({name:'measure_kind',value:liger.get("measure_kind").getValue(),}); 
    	  
    	  grid.options.parms.push({name:'ass_card_no',value:'${ass_card_no}'});  
    	  
    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue(),}); 
    	  
    	  grid.options.parms.push({name:'measure_result',value:liger.get("measure_result").getValue(),}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [  
                     { display: '计量记录编号', name: 'seq_no', align: 'left',width:'100',frozen: true
					 		},
                     { display: '计量计划', name: 'plan_id', align: 'left',width:'100',
					 			
							render : function(rowdata, rowindex, value) {
					 				
									return rowdata.plan_name;
									
									}
					 		},
                     { display: '计量证书号', name: 'cert_no', align: 'left',width:'100'
					 		},
			        { display: '检验单位', name: 'outer_measure_org', align: 'left',width:'100'
							 },
                     { display: '检验人', name: 'outer_measure_engr', align: 'left',width:'100'
					 		},
                     { display: '内部计量员', name: 'inner_measure_emp', align: 'left',width:'100',
					 			
							render : function(rowdata, rowindex, value) {
					 				
									return rowdata.inner_measure_emp_name;
									
									}
					 		},
                     { display: '计划计量日期', name: 'plan_measure_date', align: 'left',width:'100'
					 		},
                     { display: '实际计量日期', name: 'fact_measure_date', align: 'left',width:'100'
					 		},
                     { display: '预计下次日期', name: 'pre_next_date', align: 'left',width:'100'
					 		},
		             { display: '制单人', name: 'create_emp', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
						 				
										return rowdata.create_emp_name;
										
										}
						      },
	                 { display: '制单时间', name: 'create_date', align: 'left',width:'100'
						 	 },
	                 { display: '审核人', name: 'audit_emp', align: 'left',width:'100',
						 			
								render : function(rowdata, rowindex, value) {
						 				
										return rowdata.audit_emp_name;
										
										}
						 	  },
	                 { display: '审核时间', name: 'audit_date', align: 'left',width:'100'
						 	  },
 					 { display: '状态', name: 'state', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
					 				
									if (rowdata.state == 0) {
										
										return "新建";
										
									} else {
										
										return "审核";
										
									}
								
								}
					 		
					 		},

                     { display: '计量类别', name: 'measure_type', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
					 				
									if (rowdata.measure_type == 0) {
										
										return "一级类别";
										
									} else {
										
										return "二级类别";
										
									}
								
								}
					 		
					 		},
                   
                     { display: '检定结果', name: 'measure_result', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
									if (parseInt(rowdata.measure_result) == 1) {
			                        	return '合格';
			                        }else if(parseInt(rowdata.measure_result) == 0){
			                        	return '不合格';
			                        }else if(parseInt(rowdata.measure_result) == 2){
			                        	return '修复后合格';
			                        }
								}
					 		
					 		},
                     { display: '检测方式', name: 'measure_kind', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
					 				
									if (rowdata.measure_kind == 0) {
										
										return "机器检测";
										
									} else {
										
										return "手工检测";
										
									}
								
								}
					 		
					 		},
                     { display: '处理意见', name: 'measure_idea', align: 'left',width:'100'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssMeasureRec.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' }
    
				    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    

  
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			" copy_code="+vo[2]   +"&"+ 
			"rec_id="+vo[3] 
		 
		 parent.$.ligerDialog.open({
			 url: './assMeasureRecUpdatePage.do?isCheck=false&' + parm,
			 modal : true,
				showToggle : false,
				showMax : true,
				showMin : false,
				isResize : true
		 })

				layer.full(index);	
    }
 
    //键盘事件
	  function loadHotkeys() {

		hotkeys('E', query);
		
		 
	 }

	 
	 
	    function loadDict(){
	    	
			var param = {
	            	query_key:''
	        };
			
            //字典下拉框
    	
        //计量计划ID
      	autocomplete("#plan_id", "../queryAssMeasurePlanDict.do?isCheck=false", "id","text", true, true,param,true);
     	
    	//状态
    	/* $("#state").ligerComboBox({
    		
    		width : 160
    		
    	}); */
    	 $('#state').ligerComboBox({
 			data:[{id:1,text:'审核'},{id:0,text:'新建'}],
 			valueField: 'id',
             textField: 'text',
 			cancelable:true,
 			width:160
 		});
    	
    	//计量类别
    	/* $("#measure_type").ligerComboBox({
    		
    		width : 160
    		
    	}); */
    	 $('#measure_type').ligerComboBox({
 			data:[{id:0,text:'一级类别'},{id:1,text:'二级类别'}],
 			valueField: 'id',
             textField: 'text',
 			cancelable:true,
 			width:160
 		});
 		
    	//检测方式
    	/* $("#measure_kind").ligerComboBox({
    		
    		width : 160
    		
    	}); */
    	 $('#measure_kind').ligerComboBox({
 			data:[{id:1,text:'手工检测'},{id:0,text:'机器检测'}],
 			valueField: 'id',
             textField: 'text',
 			cancelable:true,
 			width:160
 		});
 		
    	//检测结果
    	/* $("#measure_result").ligerComboBox({
    		
    		width : 160
    		
    	}); */
    	 $('#measure_result').ligerComboBox({
 			data:[{id:1,text:'未完成'},{id:0,text:'完成'}],
 			valueField: 'id',
             textField: 'text',
 			cancelable:true,
 			width:160
 		})
         } 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>  
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计划：</td>
            <td align="left" width="5%" class="l-table-edit-td"><input name="plan_id" type="text" id="plan_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>  
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">检测方式：</td>
            <td align="left" class="l-table-edit-td">
            			<!-- <select id="measure_kind" name="measure_kind"> 
			            			<option value="">请选择</option>
			                		<option value="0">机器检测</option>
			                		<option value="1">手工检测</option>
			                	</select> -->
	                	 <input name="measure_kind"  type="text" id="measure_kind" />  
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td">
            		<!-- <select id="state" name="state"> 
			            			<option value="">请选择</option>
			                		<option value="0">新建</option>
			                		<option value="1">审核</option>
			                	</select> -->
	                	 <input name="state"  type="text" id="state" />  
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计量结果：</td>
            <td align="left" class="l-table-edit-td">
            			<!-- <select id="measure_result" name="measure_result"> 
			            			<option value="">请选择</option>
			                		<option value="0">完成</option>
			                		<option value="1">未完成</option>
			                	</select> -->
			           <input name="measure_result"  type="text" id="measure_result" />     	
            </td>
            <td align="left"></td> 
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
