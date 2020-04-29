<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<script src="<%=path%>/lib/hrp/hr/hr.js"></script> 
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	
    var grid,searchParam;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    });
    //查询
    function  query(param){
//    		grid.options.parms=[];
		grid.options.parms = param && Array.isArray(param) ? param : [];
   		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'emp_id',value:liger.get("emp_select").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
    	searchParam=grid.options.parms;
     }

    function loadHead(){
        	var columns=getGridColumns({ui:'liger',group_id:${group_id},hos_id:${hos_id},gridTables:['HR_PERSONNEL_CHANGE'],design:'queryPersonChange.do'});
//         	console.log(columns);
    		grid = $("#maingrid").ligerGrid({
           			 columns: columns ,
                     dataAction: 'server',dataType: 'server',usePager:true,isScroll : true,
                     url:'queryPersonChange.do',delayLoad:false,
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '新增（<u>A</u>）', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true },
    	                {text: '打印（<u>P</u>）',id:'print',click: print,icon: 'print'} ,
    	                { line:true },
    	                {text: '模板设置', click: function (item){printSet()}, icon:'settings'}, 
    	                { line:true },
    	                {text: '模板打印（<u>P</u>）',id:'templatePrint',click: templatePrint,icon: 'print'} ,
    	                { line:true },
                    	{text: '导入（<u>I</u>）',id:'importMainGrid',click: importMainGrid,icon: 'up' } , 
    	                { line:true },
    	                { text: '提交', id:'submit', click:itemclick,icon:'audit' },
    	                { line:true },
    	                { text: '审核', id:'audit', click:itemclick,icon:'audit',hide:'${updatePersonnelChange02Batch}'==""?true:false},
//     	                { line:true } ,
    	                { text: '审核', id:'audit01', click:itemclick,icon:'audit',hide:'${updatePersonnelChange03Batch}'==""?true:false},
//     	                { line:true },
    	                { text: '审核', id:'audit02', click:itemclick,icon:'audit',hide:'${updatePersonnelChange04Batch}'==""?true:false},
//     	                { line:true },
    	                { text: '审核', id:'audit03', click:itemclick,icon:'audit',hide:'${updatePersonnelChange05Batch}'==""?true:false},
//     	                { line:true },
    	                { text: '审核', id:'audit04', click:itemclick,icon:'audit',hide:'${updatePersonnelChange06Batch}'==""?true:false},
//     	                { line:true },
    	                { text: '审核', id:'audit05', click:itemclick,icon:'audit',hide:'${updatePersonnelChange07Batch}'==""?true:false},
//     	                { line:true },
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.change_id  + "|" + 
								rowdata.chang_emp_state
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
                	$.ligerDialog.open({url: 'personnelChangeAddPage.do?isCheck=false', height: 530,width: 790, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true, 
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
    					} ]
                	});
              		return;
                case "submit":
                	var data = gridManager.getCheckedRows();
	                if (data.length == 0){
	                	$.ligerDialog.error('请选择行');
	                }else{
	                    var paramVo =[];
	                    var err ="";
	                    $(data).each(function (){	
		                  	  if(this.chang_emp_state == 0){
			      							paramVo.push({
				        						group_id: this.group_id,
				        						hos_id: this.hos_id,
			      								change_id: this.change_id,
			      								chang_emp_state : 1
				        					});
		                  	  }else{
		  							if(err == ""){
		  								err = this.row_id;
		  							}else{
		  								err = err+ "、"+this.row_id;
		  							}
		  						}
	                    });
	                    
	                   if (err != "") {
	        				$.ligerDialog.warn("第["+err+"]行人事变更已经提交，提交失败！");
	        				return;
	        			}
	                   $.ligerDialog.confirm('确定提交?', function (yes){
		                   	if(yes){
		                       	ajaxJsonObjectByUrl("updatePersonnelChange01Batch.do?tab_code=HR_PERSONNEL_CHANGE",{ParamVo : JSON.stringify(paramVo)},function (responseData){
		                       		if(responseData.state=="true"){
		                       			query();
		                       		}
		                       	});
		                   	}
	                   }); 
	                }
                	return;
                case "audit": 
                	//原用人部门审核
               	 var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        var err ="";
                        
                        $(data).each(function (){
                      	 	 if(this.chang_emp_state == 1 && (this.bef_dept_state != 0 && this.bef_dept_state != 1)){
    							var rowdata = this;
    							rowdata.flag = 1;
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
              				$.ligerDialog.warn("第["+err+"]行人事变更记录未提交或原部门已经审核，审核失败！");
              				return;
              			}
                         $.ligerDialog.open({url: 'personnelChangeAuditMainPage.do?isCheck=false&',data:ParamVo, height: 200,width: 400, title:'审核通过',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});}
                    return;
                case "audit01": 
                	//变更后用人部门审核
                	 var data = gridManager.getCheckedRows();
                     if (data.length == 0){
                     	$.ligerDialog.error('请选择行');
                     }else{
                         var ParamVo =[];
                         var err ="";
                         
                         $(data).each(function (){
                       	 	 if(this.bef_dept_state == 1 && (this.aft_dept_state != 0 &&  this.aft_dept_state != 1)){
     							var rowdata = this;
     							rowdata.flag = 2;
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
               				$.ligerDialog.warn("第["+err+"]行人事变更记录未提交或变更科室已审核，审核失败！");
               				return;
               			}
                          $.ligerDialog.open({url: 'personnelChangeAuditMainPage.do?isCheck=false&',data:ParamVo, height: 200,width: 400, title:'审核通过',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});}
                     return;
                case "audit02": 
                	//职能科室审核
               		 var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        var err ="";
                        
                        $(data).each(function (){
                      	 	 if(this.aft_dept_state == 1 && (this.function_dept_state != 0 &&  this.function_dept_state != 1)){
    							var rowdata = this;
    							rowdata.flag = 3;
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
              				$.ligerDialog.warn("第["+err+"]行人事变更记录未提交或职能科室已审核，审核失败！");
              				return;
              			}
                         $.ligerDialog.open({url: 'personnelChangeAuditMainPage.do?isCheck=false&',data:ParamVo, height: 200,width: 400, title:'审核通过',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});}
                    return;
                case "audit03": 
                	//分管副院长审核
              		 var data = gridManager.getCheckedRows();
	                   if (data.length == 0){
	                   	$.ligerDialog.error('请选择行');
	                   }else{
	                       var ParamVo =[];
	                       var err ="";
	                       
	                       $(data).each(function (){
	                     	 	 if(this.function_dept_state == 1 && (this.chang_charge_state != 0 && this.chang_charge_state != 1)){
	   							var rowdata = this;
	   							rowdata.flag = 4;
	   							ParamVo.push(rowdata);
		                       	  }else{
		  							if(err == ""){
		  								err = this.row_id;
		  							}else{
		  								err += "、"+this.row_id;
		  							}
		  						}
	                     	});
	                       //
                       if (err != "") {
             				$.ligerDialog.warn("第["+err+"]行人事变更记录未提交或职能科室已审核，审核失败！");
             				return;
             			}
                        $.ligerDialog.open({url: 'personnelChangeAuditMainPage.do?isCheck=false&',data:ParamVo, height: 200,width: 400, title:'审核通过',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});}
                   return;
                case "audit04": 
                	//人力资源部审核
             		 var data = gridManager.getCheckedRows();
	                  if (data.length == 0){
	                  	$.ligerDialog.error('请选择行');
	                  }else{
	                      var ParamVo =[];
	                      var err ="";
	                      
	                      $(data).each(function (){
	                    	 	 if(this.chang_charge_state == 1 && (this.chang_hr_state != 0 &&  this.chang_hr_state != 1)){
	  							var rowdata = this;
	  							rowdata.flag = 5;
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
            				$.ligerDialog.warn("第["+err+"]行人事变更记录未提交或人力资源部已审核，审核失败！");
            				return;
            			}
                       $.ligerDialog.open({url: 'personnelChangeAuditMainPage.do?isCheck=false&',data:ParamVo, height: 200,width: 400, title:'审核通过',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});}
                  return;
                case "audit05": 
                	//院长审核
            		 var data = gridManager.getCheckedRows();
	                 if (data.length == 0){
	                 	$.ligerDialog.error('请选择行');
	                 }else{
	                     var ParamVo =[];
	                     var err ="";
	                     
	                     $(data).each(function (){
	                   	 	 if(this.chang_hr_state == 1 && (this.chang_dean_state != 0 &&  this.chang_dean_state != 1)){
	 							var rowdata = this;
	 							rowdata.flag = 6;
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
	           				$.ligerDialog.warn("第["+err+"]行人事变更记录未提交或院长已审核，审核失败！");
	           				return;
	           			}
                      $.ligerDialog.open({url: 'personnelChangeAuditMainPage.do?isCheck=false&',data:ParamVo, height: 200,width: 400, title:'审核通过',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});}
                 return;
                case "delete":
                	  var data = gridManager.getCheckedRows();
                      if (data.length == 0){
                      	$.ligerDialog.error('请选择行');
                      }else{
                          var paramVo =[];
                          var err ="";
                          $(data).each(function (){	
                        	  if(this.chang_emp_state == 0){
		      							paramVo.push({
			        						group_id: this.group_id,
			        						hos_id: this.hos_id,
		      								change_id: this.change_id,
		      								tab_code : 'HR_PERSONNEL_CHANGE',
			        					});
                        	  }else{
        							if(err == ""){
        								err = this.row_id;
        							}else{
        								err = err+ "、"+this.row_id;
        							}
        						}
                          });
                          
                         if (err != "") {
              				$.ligerDialog.warn("第["+err+"]行人事变更已经提交，删除失败！");
              				return;
              			}
                         $.ligerDialog.confirm('确定删除?', function (yes){
                         	if(yes){
                             	ajaxJsonObjectByUrl("deletePersonnelChange.do",{paramVo : JSON.stringify(paramVo)},function (responseData){
                             		if(responseData.state=="true"){
                             			$.ligerDialog.success("删除成功！");
                             			query();
                             		}
                             	});
                         	}
                         }); 
                      }
                      return;
            }   
        }
        
    }
	
    function openUpdate(obj){
		var vo = obj.split("|");
		if(vo[3]== 0){
			var parm = "group_id="+vo[0]+"&hos_id="+vo[1]+"&change_id="+vo[2];
			
	    	$.ligerDialog.open({ url : 'personnelChangeUpdatePage.do?isCheck=false&tab_code=HR_PERSONNEL_CHANGE&' + parm,data:{}, height:530,width: 790, title:'修改',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
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
					} ]
	    	});

		}else{
			$.ligerDialog.warn("人事变动已经提交，修改失败！");
		}
    }
    function loadDict(){
        //字典下拉框
        
		 autocomplete("#emp_select","../../baseSelect.do?isCheck=false&&field_tab_code=HOS_EMP", "id", "text",
	    			true, true, "", false, null, "180");
    } 
    
  //打印模板设置
	function printSet(){
		//use_id (0统一打印，1按用户打印，2按仓库打印，3按供应商打印)
		officeFormTemplate({template_code: "06106",use_id: 0});
	}
  //打印
    function print() {
    	if(grid.getData()==null){
    		$.etDialog.error("请先查询数据！");
			return;
		}
    	
    	var params = {};
        if(searchParam && Array.isArray(searchParam)){
        	$.each(searchParam, function(i, v){
                params[v.name] = v.value;
            });
        }
    	
    	var heads = {};
    	
    	heads={
    		"isAuto":true,//系统默认，页眉右上角默认显示页码
    		"rows": [{"cell":0,"value":"变更人员","from":"left","align":"left"},
    		         {"cell":1,"value":$("#emp_select").val(),"from":"left","align":"left"}]
    	};
    	var printPara=$.extend({
      		title: "人事变更",//标题
      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.hr.service.QueryService",
   			bean_name: "queryService",
   			method_name: "queryBaseInfoPtint",
   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			foots: '',//表尾需要打印的查询条件,可以为空 
   			design_code: 'queryPersonChange.do',
       	}, params);
    	
       	$.each(grid.options.parms,function(i,obj){
     		printPara[obj.name]=obj.value;
       	}); 
       	
       	officeGridPrint(printPara);
       	
    }
  
  function templatePrint(){
	  var params = {
  			template_code: '06106', //必填
  			use_id: 0, //默认0，(0统一打印，1按用户打印，2按仓库打印，3按供应商打印)
  			design_code: 'queryPersonChange.do', //必填 查询设计器编码
  			id_column: 'EMP_ID', //默认'EMP_ID'
  	};
  	// 
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
    	parent.$.ligerDialog.open({
            url: "hrp/hr/pt/personChange/personnelChangeImprotPage.do?isCheck=false&tab_code=" + 'HR_PERSONNEL_CHANGE'+"&ui="+"liger",
        	parentframename : window.name,
            height : $(window).height(),
				width : $(window).width(),
        })
//  			var value = main_select.getValue();
           
//             	 parent.$.etDialog.open({
//                     url: "hrp/hr/pf/hosemp/personnelChangeImprotPage.do?isCheck=false&store_type_code=" + archives_select.getValue() + "&tab_code=" + value+"&ui="+"et",
//                     frameName: window.name,
//                     isMax: true
//                 })
    } 
  //导出excel
	function exportExcel(){
        location.href = "exportExcelStationBasics.do?isCheck=false&tab_code="+'HR_PERSONNEL_CHANGE';
		
	}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">变动人员：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_select" type="text" id="emp_select" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
