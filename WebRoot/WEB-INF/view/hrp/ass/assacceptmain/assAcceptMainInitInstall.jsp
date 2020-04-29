<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
    var grid;
	var gridManager = null;
	var userUpdateStr;
	var selectData = "";
	var clicked = 0;
     $(function (){
         loadDict();//加载下拉框
        loadHead(null);	
        loadButton();
        query();
        $("#ins_date").ligerTextBox({width:100}); 
        $("#ven").ligerTextBox({width:160});
        $("#contract_no").ligerTextBox({width:160});
        $("#contract_type").ligerTextBox({width:160});
        $("#contract_ori_no").ligerTextBox({width:160});
        $("#contract_name").ligerTextBox({width:245});
        $("#ass_year").ligerTextBox({width:160});
        $("#ven_id").ligerTextBox({width:160});
        $("#sign_date1").ligerTextBox({width:100});
        $("#sign_date2").ligerTextBox({width:100});
        $("#buy_type").ligerTextBox({width:160});
        $("#emp_main").ligerTextBox({width:160});
        $("#provider").ligerTextBox({width:160});
        $("#is_bid").ligerTextBox({width:160});
        $("#create_date1").ligerTextBox({width:100});
        $("#create_date2").ligerTextBox({width:100});
        $("#check_date1").ligerTextBox({width:100});
        $("#check_date2").ligerTextBox({width:100});
        $("#state").ligerTextBox({width:160});
        $("#ven_id_in").ligerTextBox({width:160});
        $("#contract_id").ligerTextBox({width:160});
        $("#ven_id").ligerTextBox({width:160});
        $("#dept_id").ligerTextBox({width:160});
        $("#accept_emp").ligerTextBox({width:160});
        $("#accept_date").ligerTextBox({width:160});
        $("#ins_ass_year").ligerTextBox({
			width : 160
		});
		$("#ins_ass_month").ligerTextBox({
			width : 160
		});
     });  
     
     function  query(){
 		grid.options.parms=[];
 		grid.options.newPage=1;
 		 grid.options.parms.push({name:'ins_date1',value:$("#ins_date_begin").val()}); 
   	  grid.options.parms.push({name:'ins_date2',value:$("#ins_date_end").val()});
   	  grid.options.parms.push({name:'ins_no',value:$("#ins_no").val()}); 
   	  
   	  grid.options.parms.push({name:'ass_month',value:$("#ass_month").val()}); 
   	  
   	  grid.options.parms.push({name:'ass_month1',value:$("#ass_month1").val()}); 
   	  
   	  grid.options.parms.push({name:'contract_id',value:liger.get("contract_id").getValue().split("@")[0]}); 
   	  
   	  grid.options.parms.push({name:'ven_id',value:liger.get("ven_id").getValue().split("@")[0]}); 
   	  
   	  grid.options.parms.push({name:'audit_date',value:$("#audit_date").val()}); 
   	  
   	  grid.options.parms.push({name:'audit_date1',value:$("#audit_date1").val()}); 
   
   	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split("@")[0]}); 
   	  
   	  grid.options.parms.push({name:'audit_emp',value:liger.get("audit_emp").getValue()}); 
   	    
   	  grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
 	    grid.options.parms.push({name:'isExistsAccept',value:"0"}); 
 	grid.loadData(grid.where);
  }
     
     function loadHead(){
    	 grid = $("#maingrid")
			.ligerGrid(
					{
						columns : [ 
		                    
		                     { display: '安装单号', name: 'ins_no', align: 'left',width:100,frozen: true,
							 			render : function(rowdata, rowindex,
												value) {
								 				if(rowdata.accept_desc == "合计"){
													return '';
												}
												return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id + "|" + rowdata.copy_code  + "|" + rowdata.ins_id  + "|" + rowdata.ins_no +"')>"+rowdata.ins_no+"</a>";
										   }
							 		},
							 { display: '摘要', name: 'accept_desc', align: 'left',width:150,frozen: true
							 		},		
		                     { display: '购置年度', name: 'ass_year', align: 'left',width:80,frozen: true
							 		},
		                     { display: '购置月份', name: 'ass_month', align: 'left',width:80,frozen: true
							 		},
		                     { display: '安装日期', name: 'ins_date', align: 'left',width:130
							 		},
		                     { display: '合同名称', name: 'contract_name', align: 'left',width:190
							 		},
		                     { display: '供应商', name: 'sup_name', align: 'left',width:190
							 		},
							 { display: '安装费用', name: 'ins_money', align: 'right',width:120,
										render : function(item) {
											return formatNumber(
													item.ins_money == null ? 0
															: item.ins_money,
													'${ass_05005}',
													1);
										}
							 		},
		                     { display: '安装科室', name: 'dept_name', align: 'left',width:100
							 		},
		                     { display: '制单人', name: 'create_emp_name', align: 'left',width:100
							 		},
		                     { display: '制单日期', name: 'create_date', align: 'left',width:130
							 		},
		                     { display: '审核人', name: 'audit_emp_name', align: 'left',width:100
							 		},
		                     { display: '审核日期', name: 'audit_date', align: 'left',width:130
							 		},
		                     { display: '状态', name: 'state', align: 'left',width:100,
										render : function(rowdata, rowindex,
												value) {
												if(rowdata.state==0){
													return "新建";
												}
												else{
													return "审核";
												}
										   }
							 		}
		                     ],
						dataAction : 'server',
						dataType : 'server',
						usePager : false,
						url : 'queryAssInsByAccept.do?isCheck=false',
						width : '100%',
						height : '100%',
						checkbox : true,
						delayLoad : true,//初始化明细数据
						checkBoxDisplay : isCheckDisplay,
						toolbar : {
							items : [ 
								{ text: '查询', id:'search', click: query,icon:'search' },
		                     	{ line:true },
							          {
								text : '保存',
								id : 'saveDetail',
								click : itemclick,
								icon : 'save'
							},
					        {line : true}, 
					        {
								text : '关闭',
								id : 'close',
								click : this_close,
								icon : 'candle'
						    } 

							]
						}
					});

    	 gridManager = $("#maingrid").ligerGetGridManager();
 		
     }
     function isCheckDisplay(rowdata) {
 		if (rowdata.accept_desc == "合计")
 			return false;
 		return true;
 	}
     function this_close(){
 		frameElement.dialog.close();
 	}
     
 	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			
			case "saveDetail":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					$.ligerDialog.confirm('确认保存?',function(yes) {
					    var ass_year=$("#ass_year").val();
					    var ass_month=$("#ass_month").val();
						var contract_id=liger.get("contract_id_in").getValue();//.split("@")[0];            
						var ven_id=liger.get("ven_id_in").getValue();//.split("@")[0];                  
						var dept_id=liger.get("dept_id_in").getValue();//.split("@")[0];       	            
						var accept_emp=liger.get("accept_emp").getValue();            
						var accept_date=$("#accept_date").val();
						
						if(accept_emp == ""){
							$.ligerDialog.error('请选择验收人');
							return;
						}
						if(accept_date == ""){
							$.ligerDialog.error('请选择验收日期');
							return;
						}
						if(dept_id == ""){
							$.ligerDialog.error('请选择验收科室');
							return;
						}
						if(contract_id == ""){
							$.ligerDialog.error('请选择行合同');
							return;
						}
						if(ven_id == ""){
							$.ligerDialog.error('请选择供应商');
							return;
						}
						var ins_ids =[];
			        	$(data).each(function (){	
			        		
			        		contract_ids.push(this.ins_id);
								});
							if (yes) {ajaxJsonObjectByUrl(
									"initAssAcceptByIns.do?isCheck=false",
							   {
								  ass_year:ass_year,
								  ass_month:ass_month,
								  contract_id:contract_id,
								  ven_id:ven_id,
								  dept_id:dept_id,
								  accept_emp:accept_emp,
								  accept_date:accept_date,
								  ins_ids : ins_ids
																
									},
									function(responseData) {
										if (responseData.state == "true") {
											query();
											parentFrameUse().query();
											parentFrameUse().openUpdate(responseData.update_para);
											}
										});
									}
								});
				}
				return;
			
			}
		}
	}
    function loadDict(){
        //字典下拉框
        
        var param = {query_key:''};
    	
    	autocomplete("#contract_id_in", "../queryContractMain.do?isCheck=false", "id",
				"text", true, true);
   
    	autocomplete("#contract_id_in", "../queryContractMain.do?isCheck=false", "id",
				"text", true, true);
    	autocomplete("#ven_id_in", "../queryHosSupDict.do?isCheck=false", "id",
				"text", true, true,null,null,null,"400");
    	autocomplete("#dept_id_in", "../queryDeptDict.do?isCheck=false", "id",
				"text", true, true);
    	autocomplete("#accept_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true);
    	
    	
		
            //字典下拉框
    	autocomplete("#audit_emp", "../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true,param,true);
		
		autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id", "text", true, true,param,true);
		
		autocomplete("#create_emp", "../../../hrp/sys/queryUserDict.do?isCheck=false","id", "text",true,true,param,true);
		
		autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false","id", "text",true,true,param,true,null,"400");
		
		autocomplete("#contract_id", "../queryContractMain.do?isCheck=false", "id", "text", true, true,param,true); 
		 
        $("#ins_no").ligerTextBox({width:160});
        
        $("#ass_month").ligerTextBox({width:110});
        
        $("#ass_month1").ligerTextBox({width:110});
        
        $("#ins_date_begin").ligerTextBox({width:110});
        
        $("#ins_date_end").ligerTextBox({width:110});
          
        $("#create_date").ligerTextBox({width:110}); 
        
        $("#audit_date").ligerTextBox({width:110});
        
        $("#audit_date1").ligerTextBox({width:110});
        
        $("#state").ligerComboBox({width:160});
    	
    	 
     }  
//键盘事件

 function loadButton(){
		$("#but_query").ligerButton({click: query, width:90});
	}


    </script>
  
  </head>
  
   <body >
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <input type="hidden" id="sequence" name="sequence"/>
   <form name="form1" method="post"  id="form1" >
   <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%;" border="0">
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">购置年月：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_month" type="text" id="ass_month" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" /></td>
            <td >至：</td>
            <td><input name="ass_month1" type="text" id="ass_month1" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">安装单号：</td>
            <td class="l-table-edit-td"><input name="ins_no" type="text" id="ins_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
            <td align="left" class="l-table-edit-td"><select id="state" name="state">
						<option value="">全部</option>
                		<option value="0">新建</option>
                		<option value="1">审核</option>
                	</select></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">安装日期：</td>
            <td align="left" class="l-table-edit-td" width="5%"><input name="ins_date_begin" type="text" id="ins_date_begin" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'ins_date_end\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td>至：</td>
			<td><input name="ins_date_end" type="text"
				id="ins_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'ins_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同名称：</td>
            <td align="left" class="l-table-edit-td"><input name="contract_id" type="text" id="contract_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核日期：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_date" type="text" id="audit_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td>至：</td>
            <td><input name="audit_date1" type="text" id="audit_date1" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
       
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核人：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_emp" type="text" id="audit_emp" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
         </tr> 
    </table>
    <hr size="1" width="1400" color="#A3COE8" align="left" style="position:absolute;top:84px;"/>
   
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" bgcolor="#DDDDDD">
       
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>验收人：</td>
            <td align="left" class="l-table-edit-td"><input name="accept_emp" type="text" id="accept_emp"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>验收日期：</td>
            <td align="left" class="l-table-edit-td"><input name="accept_date" type="text" id="accept_date"   class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>验收科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id_in" type="text" id="dept_id_in"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>合同：</td>
            <td align="left" class="l-table-edit-td"><input name="contract_id_in" type="text" id="contract_id_in"   /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>统计年度：</td>
            <td align="left" class="l-table-edit-td"><input name="ins_ass_year" type="text" id="ins_ass_year" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
            <td align="left"></td>
      
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>统计月份：</td>
            <td align="left" class="l-table-edit-td"><input name="ins_ass_month" type="text" id="ins_ass_month" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id_in" type="text" id="ven_id_in"   /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
     
   <div id="maingrid"></div>
    </body>
</html>
