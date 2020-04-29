<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
   <script src="<%=path%>/lib/hrp/hr/hr.js"></script> 

<script type="text/javascript">
    var grid;
    var gridManager = null;           
        $(function () {
        	 // loadDict()//加载下拉框
          	//加载数据
          	loadHead(null);	
          	 $("#transfer_date").ligerTextBox({width:160});

        })
        
        //查询方法
           function query() {
        	grid.options.parms=[];
        	  grid.options.parms.push({name:'stu_time',value:$("#stu_time").val()}); 
        	  grid.options.parms.push({name:'tab_cod e',value:'HR_STUTRAIN_CHECK'}); 
        	
        	  grid.options.parms.push({name:'design_code',value:"queryHrStuTrainCheck.do"}); 
        	  
            grid.loadData(grid.where);
           
        	
        }
        //表格加载
            function loadHead(){
            	  var columns=getGridColumns({ui:'liger',group_id:${group_id},hos_id:${hos_id},gridTables:['HR_STUTRAIN_CHECK'],desin:'queryHrStuTrainCheck.do'});
            	   grid = $("#maingrid").ligerGrid({
                    columns:columns,
                              dataAction: 'server',dataType: 'server',url:'queryHrStuTrainCheck.do',delayLoad :true,
                              width: '100%', height: '100%', checkbox: true,rownumbers:true,usePager :true,
                              enabledEdit : true,
  						onBeforeEdit : f_onBeforeEdit,
                        toolbar: {
                            items: [
                                {text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' }, 
                            	{text: '添加（<u>A</u>）', id:'add',click: add,icon: 'add' },
                            	{text: '删除（<u>D</u>）',id:'remove',click: remove,icon: 'delete'}, 
                            	{text: '导入（<u>I</u>）',id:'importMainGrid',click: importMainGrid,icon: 'up' }, 
                            	{ text: '提交', id:'auditSelf', click:auditSelf,icon:'audit'},
            	                { text: '科室审核', id:'auditDept', click:auditDept,icon:'audit' },
            	                { text: '科室分管副院长审核', id:'auditDeptSubdecanal', click:auditDeptSubdecanal,icon:'audit'},
            	                { text: '人力资源部审核', id:'auditHr', click:auditHr,icon:'audit' },
            	                { text: '人力资源部分管副院长审核', id:'auditHrSubdecanal', click:auditHrSubdecanal,icon:'audit'},
            	                { text: '财务部分管副院长审核', id:'auditFinSubdecanal', click:auditFinSubdecanal,icon:'audit' },
            	                { text: '董事长审核', id:'auditChairman', click:auditChairman,icon:'audit' , hide:'${updateChairmanStateBatch}'==""?true:false} ,
            	                {text: '模板设置', click: function (item){printSet()}, icon:'settings'}, { line:true },
            					{text: '模板打印', click: function (item){templatePrint()}, icon:'print'}, { line:true }
            	            ]
                        },
                        onDblClickRow  : function(data,rowid,rowdata)  {
							openUpdate(data);
						}
                            });

                 gridManager = $("#maingrid").ligerGetGridManager();
          
        }

        function add() {
        	$.ligerDialog.open({
                url: 'hrStuTrainCheckAddPage.do?isCheck=false',
                title: '添加',
                width: 700,
                height: 500,
                buttons : [ {
					text : '确定',
					onclick : function(item, dialog) {
						dialog.frame.saveData();
					},
					cls : 'l-dialog-btn-highlight'
				}, {
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ] });
        }
  function auditSelf(){
        	var data = gridManager.getCheckedRows();
       
        if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         }else{
        		var ParamVo  = [];
             var err ="";
             
             $(data).each(function (){
             	
           	 	 if(this.state == '0'){
						var rowdata = this;
							ParamVo.push({
								 state:'1',
				                   	tab_code: 'HR_STUTRAIN_CHECK',
				                   	stutrain_id:rowdata.stutrain_id,
				                   	emp_id:rowdata.emp_id
							});
               	  }else{
						if(err == ""){
							err = this.row_id;
						}else{
							err += "、"+this.row_id;
						}
					}
             });
             if (err != "") {
   				$.ligerDialog.warn("第["+err+"]行进修计划已经提交，提交失败！");
   				return;
   			}
             ajaxJsonObjectByUrl("updateSelfStateBatch.do?isCheck=false",{ParamVo : JSON.stringify(ParamVo)}, function(
 					responseData) {
 		   if(responseData.state=="true"){
 				
                query();
             }
 			});
             }
        }
        function auditDept(){
        	var data = gridManager.getCheckedRows();
       
        if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         }else{
             var ParamVo =[];
             var err ="";
             
             $(data).each(function (){
           	 	 if(this.state == '1'){
						var rowdata = this;
						rowdata.check_status='auditDept';
						ParamVo.push(rowdata);
               	  }else{
						if(err == ""){
							err = this.row_id;
						}else{
							err += "、"+this.row_id;
						}
					}
             });
             if (err != "") {
   				$.ligerDialog.warn("第["+err+"]行未提交，请提交后审核！");
   				return;
   			}
             $.ligerDialog.open({url: 'hrStuTrainCheckStateMainPage.do?isCheck=false&',data:ParamVo, height: 400,width: 400, title:'审核',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});

             }
        }
        function auditDeptSubdecanal(){
            var data = gridManager.getCheckedRows();
                
                if (data.length == 0){
                 	$.ligerDialog.error('请选择行');
                 }else{
                     var ParamVo =[];
                     var err ="";
                     
                     $(data).each(function (){
                   	 	 if(this.dept_state == '1'){
        						var rowdata = this;
        						rowdata.check_status='auditDeptSubdecanal';
        						ParamVo.push(rowdata);
                       	  }else{
        						if(err == ""){
        							err = this.row_id;
        						}else{
        							err += "、"+this.row_id;
        						}
        					}
                     });
                     if (err != "") {
           				$.ligerDialog.warn("第["+err+"]行部门未审核，审核失败！");
           				return;
           			}
                     $.ligerDialog.open({url: 'hrStuTrainCheckStateMainPage.do?isCheck=false&',data:ParamVo, height: 400,width: 400, title:'审核',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});

                     }
            }
        function auditHr(){

        	var data = gridManager.getCheckedRows();
       
        if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         }else{
             var ParamVo =[];
             var err ="";
             
             $(data).each(function (){
           	 	 if(this.deptsubdecanal_state == '1'){
						var rowdata = this;
						rowdata.check_status='auditHr';
						ParamVo.push(rowdata);
               	  }else{
						if(err == ""){
							err = this.row_id;
						}else{
							err += "、"+this.row_id;
						}
					}
             });
             if (err != "") {
   				$.ligerDialog.warn("第["+err+"]行部门分管院长未审核，审核失败！");
   				return;
   			}
             $.ligerDialog.open({url: 'hrStuTrainCheckStateMainPage.do?isCheck=false&',data:ParamVo, height: 400,width: 400, title:'审核',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});

             }
        }
        
        function auditHrSubdecanal(){
        	var data = gridManager.getCheckedRows();
            
            if (data.length == 0){
             	$.ligerDialog.error('请选择行');
             }else{
                 var ParamVo =[];
                 var err ="";
                 
                 $(data).each(function (){
               	 	 if(this.hr_state == '1'){
    						var rowdata = this;
    						rowdata.check_status='auditHrSubdecanal';
    						ParamVo.push(rowdata);
                   	  }else{
    						if(err == ""){
    							err = this.row_id;
    						}else{
    							err += "、"+this.row_id;
    						}
    					}
                 });
                 if (err != "") {
       				$.ligerDialog.warn("第["+err+"]行人事科未审核，审核失败！");
       				return;
       			}
                 $.ligerDialog.open({url: 'hrStuTrainCheckStateMainPage.do?isCheck=false&',data:ParamVo, height: 400,width: 400, title:'审核',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});

                 }
        }
        function auditFinSubdecanal(){
        var data = gridManager.getCheckedRows();
            
            if (data.length == 0){
             	$.ligerDialog.error('请选择行');
             }else{
                 var ParamVo =[];
                 var err ="";
                 
                 $(data).each(function (){
               	 	 if(this.hrsubdecanal_state == '1'){
    						var rowdata = this;
    						rowdata.check_status='auditFinSubdecanal';
    						ParamVo.push(rowdata);
                   	  }else{
    						if(err == ""){
    							err = this.row_id;
    						}else{
    							err += "、"+this.row_id;
    						}
    					}
                 });
                 if (err != "") {
       				$.ligerDialog.warn("第["+err+"]行人事科分管副院长未审核，审核失败！");
       				return;
       			}
                 $.ligerDialog.open({url: 'hrStuTrainCheckStateMainPage.do?isCheck=false&',data:ParamVo, height: 400,width: 400, title:'审核',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});

                 }
        }
        function auditChairman(){
            var data = gridManager.getCheckedRows();
                
                if (data.length == 0){
                 	$.ligerDialog.error('请选择行');
                 }else{
                     var ParamVo =[];
                     var err ="";
                     
                     $(data).each(function (){
                   	 	 if(this.finsubdecanal_state == '1'){
        						var rowdata = this;
        						rowdata.check_status='auditChairman';
        						ParamVo.push(rowdata);
                       	  }else{
        						if(err == ""){
        							err = this.row_id;
        						}else{
        							err += "、"+this.row_id;
        						}
        					}
                     });
                     if (err != "") {
           				$.ligerDialog.warn("第["+err+"]行财务科分管副院长未审核，审核失败！");
           				return;
           			}
                     $.ligerDialog.open({url: 'hrStuTrainCheckStateMainPage.do?isCheck=false&',data:ParamVo, height: 400,width: 400, title:'审核',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});

                     }
            }
    	function f_onBeforeEdit(rowData) {
    		 if(rowData.record.__status!="add"&&rowData.column.columnindex == 2){
       			 return false ;
       		 }else{
       			 return true ;
       		 }
    		
    	}
    	
       function openUpdate(data) {
       
			var parm ="&group_id="+ 
			 data.group_id + "&hos_id=" + 
			  data.hos_id+ "&stutrain_id="+
			  data.transfer_id+"&tab_code="+"HR_STUTRAIN_CHECK";
			
       
            $.ligerDialog.open({
                url: 'hrStuTrainCheckUpdatePage.do?isCheck=false' +parm,
                title: '修改',
                width: 700,
                height: 500,
                buttons: [ { text: '确定', onclick: function (item, dialog) {dialog.frame.saveData();},cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]
            
            })
        	
        }
        
        function remove() {
        	var data = grid.getCheckedRows();
            if (data.length == 0) {
            	$.ligerDialog.error('请选择行');
            } else {
            	var param = [];
            	  var err ="";
                $(data).each(function () {
                	 if(this.state == '0'){
                	  var rowdata = this;
                      rowdata.tab_code='HR_STUTRAIN_CHECK';
                      param.push(rowdata);
                	 }else{
                		 if(err == ""){
 							err = this.row_id;
 						}else{
 							err += "、"+this.row_id;
 						} 
                	 }
                });
                if (err != "") {
       				$.ligerDialog.warn("第["+err+"]行进修申请已提交！");
       				return;
       			}
                $.ligerDialog.confirm('确定删除?' , function (yes){
                    	if(yes){
                        	ajaxJsonObjectByUrl("deleteStutrainCheck.do",{paramVo: JSON.stringify(param)},function (responseData){
                        		if(responseData.state=="true"){
                        			query();
                        		}
                        	});
                    	}
                });
            }
        }
         //打印模板设置
    	function printSet(){
    		//use_id (0统一打印，1按用户打印，2按仓库打印，3按供应商打印)
    		officeFormTemplate({template_code: "06001",use_id: 0});
    	}
    	
    	//模板打印
        function templatePrint(){
        	var params = {
        			template_code: '06104', //必填
        			use_id: 0, //默认0，(0统一打印，1按用户打印，2按仓库打印，3按供应商打印)
        			design_code: 'queryHrStuTrainCheck.do', //必填 查询设计器编码
        			//id_column: 'EMP_ID', //默认'EMP_ID'
        	};
        	
        	//查询条件
            if(searchParam && Array.isArray(searchParam)){
                $.each(searchParam, function(i, v){
                    params[v.name] = v.value;
                });
            }
            hrTemplatePrint(params);
        }
	 	//导入
        function importMainGrid() {
       
        	 $.ligerDialog.open({
                 url: "hrStuTrainCheckImprotPage.do?isCheck=false&tab_code=" + 'HR_STUTRAIN_CHECK'+"&ui="+"liger",
             	parentframename : window.name,
                 height : $(window).height(),
					width : $(window).width(),
             })
	 	} 
      //导出excel
		function exportExcel(){
            location.href = "exportExcelStationBasics.do?isCheck=false&tab_code="+'HR_STUTRAIN_CHECK';
			
		}
		 
    </script>
</head>

<body>
  
<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">进修日期：</td>
            <td align="left" class="l-table-edit-td"><input name="stu_date" ltype="text"  class="Wdate" id="transfer_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>

        </tr> 
    </table>
            <div id="maingrid"></div>
     
</body>

</html>