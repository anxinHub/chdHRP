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
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/hr/hr.js"></script> 
<script type="text/javascript">
	
    var grid,searchParam;;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    });
    //查询
    function  query(){
   		grid.options.parms=[];
   		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'emp_id',value:liger.get("emp_select").getValue()}); 
    	searchParam=grid.options.parms;
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	var columns=getGridColumns({ui:'liger',group_id:${group_id},hos_id:${hos_id},gridTables:['HR_PERSONNEL_DIMISSION'],design:'queryDimission.do'});
    	grid = $("#maingrid").ligerGrid({
          			 columns: columns,
                     dataAction: 'server',dataType: 'server',usePager:true,isScroll : true,
                     url:'queryDimission.do',delayLoad:false,
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '新增', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true },
    	                {text: '打印（<u>P</u>）',id:'print',click: print,icon: 'print'} ,
    	                { line:true },
    	                {text: '模板设置', click: function (item){printSet()}, icon:'settings'}, 
    	                { line:true },
    	                {text: '模板打印（<u>P</u>）',id:'templatePrint',click: templatePrint,icon: 'print'} ,
    	                { line:true },
                    	{text: '导入（<u>I</u>）',id:'importMainGrid',click: importMainGrid,icon: 'up' } , 
    	                { line:true },
    	                { text: '提交', id:'submit', click:itemclick,icon:'audit'},
    	                { line:true },
    	                { text: '审核', id:'audit01', click:itemclick,icon:'audit',hide:'${updatePersonnelDimission02Batch}'==""?true:false},
//     	                { line:true },
    	                { text: '审核', id:'audit02', click:itemclick,icon:'audit',hide:'${updatePersonnelDimission03Batch}'==""?true:false},
//     	                { line:true },
    	                { text: '审核', id:'audit03', click:itemclick,icon:'audit',hide:'${updatePersonnelDimission04Batch}'==""?true:false},
//     	                { line:true },
    	                { text: '审核', id:'audit04', click:itemclick,icon:'audit',hide:'${updatePersonnelDimission05Batch}'==""?true:false},
//     	                { line:true },
    	                { text: '审核', id:'audit05', click:itemclick,icon:'audit',hide:'${updatePersonnelDimission06Batch}'==""?true:false},
//     	                { line:true },
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.dim_id + "|" +
								rowdata.dim_emp_state
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
                	$.ligerDialog.open({url: 'personnelDimissionAddPage.do?isCheck=false', height: 300,width: 790, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});
              		return;
                case "submit":
                	var data = gridManager.getCheckedRows();
	                if (data.length == 0){
	                	$.ligerDialog.error('请选择行');
	                }else{
	                    var paramVo =[];
	                    var err ="";
	                    $(data).each(function (){	
		                  	  if(this.dim_emp_state == 0){
			      							paramVo.push({
				        						group_id: this.group_id,
				        						hos_id: this.hos_id,
			      								dim_id: this.dim_id,
			      								dim_emp_state : 1
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
	        				$.ligerDialog.warn("第["+err+"]行离职申请已经提交，提交失败！");
	        				return;
	        			}
	                   $.ligerDialog.confirm('确定提交?', function (yes){
		                   	if(yes){
		                       	ajaxJsonObjectByUrl("updatePersonnelDimission01Batch.do?tab_code=HR_PERSONNEL_DIMISSION",{ParamVo : JSON.stringify(paramVo)},function (responseData){
		                       		if(responseData.state=="true"){
// 		                       			$.ligerDialog.success("提交成功！");
		                       			query();
		                       		}
		                       	});
		                   	}
	                   }); 
	                }
                	return;
                //	科室部门审核
                case "audit01": 
                	 var data = gridManager.getCheckedRows();
                     if (data.length == 0){
                     	$.ligerDialog.error('请选择行');
                     }else{
                         var ParamVo =[];
                         var err ="";
                         
                         $(data).each(function (){
                       	 	 if(this.dim_emp_state == 1 && (this.dim_dept_state != 0 && this.dim_dept_state != 1)){
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
               				$.ligerDialog.warn("第["+err+"]行离职纪录未提交或科室部门已审核，审核失败！");
               				return;
               			}
                        $.ligerDialog.open({url: 'personnelDimissionAuditMainPage.do?isCheck=false&',data:ParamVo, height: 200,width: 400, title:'审核通过',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true}); }
                     return;
                //职能部门审核    
                case "audit02": 
               	 var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        var err ="";
                        
                        $(data).each(function (){
                      	 	 if(this.dim_dept_state == 1 && (this.dim_func_state != 0 && this.dim_func_state != 1)){
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
              				$.ligerDialog.warn("第["+err+"]行科室部门未审核或职能科室已审核，审核失败！");
              				return;
              			}
                       $.ligerDialog.open({url: 'personnelDimissionAuditMainPage.do?isCheck=false&',data:ParamVo, height: 200,width: 400, title:'审核通过',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true}); }
                    return;  
                //    分管副院长审核
                case "audit03": 
                  	 var data = gridManager.getCheckedRows();
                       if (data.length == 0){
                       	$.ligerDialog.error('请选择行');
                       }else{
                           var ParamVo =[];
                           var err ="";
                           
                           $(data).each(function (){
                         	 	 if(this.dim_func_state == 1 && (this.dim_charge_state != 0 && this.dim_charge_state != 1)){
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
                 				$.ligerDialog.warn("第["+err+"]行职能部门未审核或分管副院长已审核，审核失败！");
                 				return;
                 			}
                          $.ligerDialog.open({url: 'personnelDimissionAuditMainPage.do?isCheck=false&',data:ParamVo, height: 200,width: 400, title:'审核通过',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true}); }
                       return;  
				//        人力资源部审核
                case "audit04": 
                  	 var data = gridManager.getCheckedRows();
                       if (data.length == 0){
                       	$.ligerDialog.error('请选择行');
                       }else{
                           var ParamVo =[];
                           var err ="";
                           
                           $(data).each(function (){
                         	 	 if(this.dim_charge_state == 1 && (this.dim_hr_state != 0 && this.dim_hr_state != 1)){
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
                           if (err != "") {
                 				$.ligerDialog.warn("第["+err+"]行职能部门未审核或人力资源部已审核，审核失败！");
                 				return;
                 			}
                          $.ligerDialog.open({url: 'personnelDimissionAuditMainPage.do?isCheck=false&',data:ParamVo, height: 200,width: 400, title:'审核通过',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true}); }
                       return;        
//                     院长审核
                case "audit05": 
                  	 var data = gridManager.getCheckedRows();
                       if (data.length == 0){
                       	$.ligerDialog.error('请选择行');
                       }else{
                           var ParamVo =[];
                           var err ="";
                           
                           $(data).each(function (){
                         	 if(this.dim_hr_state == 1 && (this.dim_dean_state != 0 && this.dim_dean_state != 1)){
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
                 				$.ligerDialog.warn("第["+err+"]行职能部门未审核或院长已审核，审核失败！");
                 				return;
                 			}
                          $.ligerDialog.open({url: 'personnelDimissionAuditMainPage.do?isCheck=false&',data:ParamVo, height: 200,width: 400, title:'审核通过',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true}); }
                       return;         
                case "delete":
                	  var data = gridManager.getCheckedRows();
                      if (data.length == 0){
                      	$.ligerDialog.error('请选择行');
                      }else{
                          var paramVo =[];
                          var err ="";
                          $(data).each(function (){	
                        	  if(this.dim_emp_state == 0){
	      							paramVo.push({
		        						dim_id: this.dim_id,
		        						group_id: this.group_id,
		        						hos_id: this.hos_id,
		        						tab_code : 'HR_PERSONNEL_DIMISSION'
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
              				$.ligerDialog.warn("第["+err+"]行离职申请已经提交，删除失败！");
              				return;
              			}
                          $.ligerDialog.confirm('确定删除?', function (yes){
                          	if(yes){
                              	ajaxJsonObjectByUrl("deletePersonnelDimission.do?isCheck=false",{paramVo : JSON.stringify(paramVo)},function (responseData){
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
		if(vo[3]== 0 ){
			var parm = "group_id="+vo[0]+"&hos_id="+vo[1]+"&dim_id="+vo[2];
			
	    	$.ligerDialog.open({ url : 'personnelDimissionUpdatePage.do?isCheck=false&tab_code=HR_PERSONNEL_DIMISSION&' + parm,data:{}, height: 300,width: 790, title:'修改',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });

		}else{
			$.ligerDialog.warn("招聘需求已经审核，修改失败！");
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
		officeFormTemplate({template_code: "06107",use_id: 0});
	}
    
	  function templatePrint(){
		  var params = {
	  			template_code: '06107', //必填
	  			use_id: 0, //默认0，(0统一打印，1按用户打印，2按仓库打印，3按供应商打印)
	  			design_code: 'queryDimission.do', //必填 查询设计器编码
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
		
    //  
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
    	
    	var printPara=$.extend({
      		title: "离职",//标题
      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.hr.service.QueryService",
   			bean_name: "queryService",
   			method_name: "queryBaseInfoPtint",
   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			foots: '',//表尾需要打印的查询条件,可以为空 
   			design_code: 'queryDimission.do',
       	}, params);
    	
       	$.each(grid.options.parms,function(i,obj){
     		printPara[obj.name]=obj.value;
       	}); 
       	
       	officeGridPrint(printPara);
       	
    }
    
	
 	//导入
    function importMainGrid() {
    	$.ligerDialog.open({
            url: "personnelDimissionImprotPage.do?isCheck=false&tab_code=" + 'HR_PERSONNEL_DIMISSION'+"&ui="+"liger",
        	parentframename : window.name,
            height : $(window).height(),
				width : $(window).width(),
        })
    } 
  //导出excel
	function exportExcel(){
        location.href = "exportExcelStationBasics.do?isCheck=false&tab_code="+'HR_REGULAR';
		
	}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">离职人员：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_select" type="text" id="emp_select" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
