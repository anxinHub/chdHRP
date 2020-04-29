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
    	  
    	  grid.options.parms.push({name:'ass_nature',value:liger.get("ass_nature").getValue()}); 
    	  
    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
    	  
    	  grid.options.parms.push({name:'maintain_dept_id',value:liger.get("maintain_dept_id").getValue().split("@")[0]}); 
    	 
    	  grid.options.parms.push({name:'ass_card_no',value:'${ass_card_no}'});  
    	
    	  //grid.options.parms.push({name:'ass_card_no',value:$("#ass_card_no").val()});  
    	  //grid.options.parms.push({name:'maintain_dept_no',value:liger.get("maintain_dept_id").getValue().split("@")[1]}); 
    	  
    	  grid.options.parms.push({name:'create_emp',value:liger.get("create_emp").getValue()}); 
    	  
		  grid.options.parms.push({name:'create_date_begin',value:$("#create_date_begin").val()}); 
    	  
    	  grid.options.parms.push({name:'create_date_end',value:$("#create_date_end").val()});
    	  
    	  grid.options.parms.push({name:'audit_date_begin',value:$("#audit_date_begin").val()});
    	  
    	  grid.options.parms.push({name:'audit_date_end',value:$("#audit_date_end").val()});
    	  
    	  grid.options.parms.push({name:'audit_emp',value:liger.get("audit_emp").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
    	
		$("#resultPrint > table > tbody").empty();
		
     }
    

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '保养序号', name: 'rec_no', align: 'left',width:'100',frozen: true,
                    	 
                    	 render : function(rowdata, rowindex,value) {
                    		 
   							return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
																	rowdata.hos_id   + "|" + 
																	rowdata.copy_code   + "|" + 
																	rowdata.rec_id    +"')>"+
   									                                 rowdata.rec_no+"</a>";
   										}
					 		},
                     { display: '保养计划', name: 'plan_id', align: 'left',width:'100'
					 		},
                     { display: '资产性质', name: 'naturs_name', align: 'left',width:'100',
					 			
							render : function(rowdata, rowindex, value) {
					 				
									return rowdata.naturs_name;
									
									}
					 		
					 		},
                     { display: '计划执行日期', name: 'plan_exec_date', align: 'left',width:'100'
					 		},
                     { display: '实际执行日期', name: 'fact_exec_date', align: 'left',width:'100'
					 		},
		             { display: '执行人', name: 'fact_exec_emp', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
						 				
										return rowdata.fact_exec_emp_name;
										
										}
						 		
						    },
		             { display: '制单人', name: 'create_emp', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
						 				
										return rowdata.create_emp_name;
										
								 }
						    },
	                 { display: '制单时间', name: 'create_date', align: 'left',width:'100'
						    },
	                 { display: '审核时间', name: 'audit_date', align: 'left',width:'100'
						 		},
	                 { display: '审核人', name: 'audit_emp', align: 'left',width:'100',
						 			
								render : function(rowdata, rowindex, value) {
						 				
										return rowdata.audit_emp_name;
								 }
						    },
		             { display: '状态', name: 'state', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
						 				
											if (rowdata.state == 0) {
												
												return "新建";
												
											} else if (rowdata.state == 1){
												
												return "审核";
												
											}else if (rowdata.state == 2){
												
												return "终止";
												
											}
										
										}
						 		
						    },
		             { display: '保养部门', name: 'maintain_dept_id', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
						 				
										return rowdata.maintain_dept_name;
										
										}
						 	},
                     { display: '保养工时', name: 'maintain_hours', align: 'left',width:'100'
					 		},
                     { display: '保养费用', name: 'maintain_money', align: 'right',width:'100',
					 			render : function(rowdata, rowindex,
										value) {
									 return formatNumber(rowdata.maintain_money,'${ass_05005}',1);
								}
					 		},
                     { display: '保养说明', name: 'maintain_desc', align: 'left',width:'100'
					 		},
           ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssMaintainRec.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' }
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.rec_id 
							);
    				} 
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
	/* 	parent.$.ligerDialog.open({
			title: '科室申请资产明细',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asscard/assMaintainRecUpdatePage.do?isCheck=false&' + parm,
			 width: 1000,
			 height: 700,
			 allowClose: true,
			 slide: false

		}); */
		 parent.$.ligerDialog.open({
			 url: './assMaintainRecUpdatePage.do?isCheck=false&' + parm,
			 modal : true,
				showToggle : false,
				showMax : true,
				showMin : false,
				isResize : true
		 })
   /*  var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'assMaintainRecUpdatePage.do?isCheck=false&' + parm
				});
				layer.full(index);	 */
    
    }
    
    //键盘事件
	  function loadHotkeys() {

		hotkeys('E', query);

		hotkeys('A', add_open);
		
		hotkeys('D', remove);
		
		hotkeys('R', auditMaintainRec);
		
		hotkeys('B', backMaintainRec);
		
		hotkeys('S', stopMaintainRec);
		
		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		 
	 }
  
	   		
		

	   		
	    
	    function loadDict(){
	    	
			var param = {
	            	query_key:''
	        };
	
			
         //字典下拉框
    //保养部门
    autocomplete("#maintain_dept_id", "../queryDeptDict.do?isCheck=false", "id","text", true, true,param,true);
    	
 	//资产性质
  	autocomplete("#ass_nature", "../queryAssNaturs.do?isCheck=false", "id","text", true, true,param,true);
	
 	//资产卡片号
	autocomplete("#ass_card_no", "../queryAssCardNoDict.do?isCheck=false", "id","text", true, true,param,true);   
 	
 	//计划执行人
	autocomplete("#fact_exec_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true,param,true);
	
	//制单人
	autocomplete("#create_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true,param,true);
	
	//审核人
	autocomplete("#audit_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true,param,true);
	
	//保养计划
	autocomplete("#plan_id","../queryAssMaintainPlanDict.do?isCheck=false", "id","text", true, true,param,true);
	
	//状态
	/* $("#state").ligerComboBox({
		
		width : 160
		
	}); */
	$('#state').ligerComboBox({
		data:[{id:0,text:'新建'},{id:1,text:'审核'}],
		valueField: 'id',
        textField: 'text',
		cancelable:true,
		width:160
	}) 
	
    $("#create_date_begin").ligerTextBox({width:90});
    
    $("#create_date_end").ligerTextBox({width:90});
    
    $("#audit_date_begin").ligerTextBox({width:90});
    
    $("#audit_date_end").ligerTextBox({width:90});
    
      }  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        
         <tr>
         	<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date_begin" type="text" id="create_date_begin"
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date_end\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" >至：</td>
			<td align="left"><input name="create_date_end" type="text"
				id="create_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人：</td>
            <td align="left" class="l-table-edit-td"><input name="create_emp" type="text" id="create_emp"  /></td>
             
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">保养部门：</td>
            <td align="left" class="l-table-edit-td"><input name="maintain_dept_id" type="text" id="maintain_dept_id"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature"  /></td> 
        </tr> 
         <tr>
         
         	<td align="right" class="l-table-edit-td" style="padding-left: 20px;">审核日期：</td>
			<td align="left" class="l-table-edit-td"><input name="audit_date_begin" type="text" id="audit_date_begin"
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'audit_date_end\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"  >至：</td>
			<td align="left"><input name="audit_date_end" type="text" id="audit_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'audit_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核人：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_emp" type="text" id="audit_emp"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">保养计划：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_id" type="text" id="plan_id"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td">
						<!-- <select id="state" name="state"> 
			            			<option value="">请选择</option>
			                		<option value="0">新建</option>
			                		<option value="1">审核</option>
			                	</select> -->
			         <input name="state" type="text" id="state" />       	
			</td>
        </tr> 
    </table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr> 
                <th width="200">保养序号</th>	
                <th width="200">统计年度</th>	
                <th width="200">统计月份</th>	
                <th width="200">计划</th>	
                <th width="200">资产性质</th>	
                <th width="200">执行人</th>	
                <th width="200">计划执行日期</th>	
                <th width="200">实际执行日期</th>	
                <th width="200">保养工时</th>	
                <th width="200">保养费用</th>	
                <th width="200">保养说明</th>	
                <th width="200">状态</th>	
                <th width="200">保养部门</th> 	
                <th width="200">制单人</th>	
                <th width="200">制单时间</th>	
                <th width="200">审核时间</th>	
                <th width="200">审核人</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
