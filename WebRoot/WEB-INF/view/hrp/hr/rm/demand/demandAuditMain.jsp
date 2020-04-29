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
    function  query(){
   		grid.options.parms=[];
   		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'demand_state',value:liger.get("demand_state").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
    	searchParam=grid.options.parms;
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
						{ display: '招聘科室', name: 'dept_id_name', align: 'left',width:100,
						},
						{ display: '招聘岗位', name: 'station_code_name', align: 'left',width:100,
						},
						{ display: '招聘主题', name: 'theme_name', align: 'left',width:100,
						},
						{ display: '专业要求', name: 'demand_major_name', align: 'left',width:100,
						},
						{ display: '招聘人数', name: 'demand_num', align: 'left',width:100,
						},
// 						{ display: '定岗人数', name: 'note', align: 'left',width:100,
// 						},
// 						{ display: '实际人数', name: 'note', align: 'left',width:100,
// 						},
// 						{ display: '预计退休人数', name: 'note', align: 'left',width:80
// 						},
						{ display: '用工形式', name: 'form_code', align: 'left',width:100,
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.form_code == '001'){
									return "正式工";
								}
								if(rowdata.form_code == '002'){
									return '合同工'
								}
								if(rowdata.form_code == '003'){
									return '临时工'
								}
								if(rowdata.form_code == '004'){
									return '聘用'
								}
								if(rowdata.form_code == '005'){
									return '劳务派遣'
								}
								if(rowdata.form_code == '006'){
									return '借调'
								}
								if(rowdata.form_code == '007'){
									return '调出'
								}
								}
						},
						{ display: '学历', name: 'demand_edu_name', align: 'left',width:120,
						},
						{ display: '学位', name: 'demand_deg_name', align: 'left',width:100,
						},
						{ display:'年龄范围', name: 'demand_age', align: 'left',width:100,
						},
						{ display:'任职资格', name: 'demand_qualify', align: 'left',width:100,
						},
						{ display:'任职要求', name: 'demand_require', align: 'left',width:100,
						},
						{ display:'申请是否批准', name: 'demand_state', align: 'left',width:80,
								render : function(rowdata, rowindex,
										value) {
									if(rowdata.demand_state == "01"){
										return "审核中";
									}
									if(rowdata.demand_state == "02"){
										return '通过'
									}
									if(rowdata.demand_state == "03"){
										return '拒绝'
									}
									if(rowdata.demand_state == "04"){
										return '删除'
									}
									if(rowdata.demand_state == "05"){
										return '存档'
									}
								}
						}
						],
                     dataAction: 'server',dataType: 'server',usePager:true,isScroll : true,
                     url:'queryAuditDemand.do?tab_code=HR_RECRUIT_DEMAND',
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
    	                { text: '审核', id:'audit', click:itemclick,icon:'audit' },
    	                { line:true },
    	                { text: '拒绝', id:'refuse', click:itemclick,icon:'close' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.demand_id + "|" + 
								rowdata.demand_state
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
                	$.ligerDialog.open({url: 'recruitDemandAuditAddPage.do?isCheck=false', height: 480,width: 790, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});
              		return;
                case "modify":
                    return;
                case "audit": 
                	 var data = gridManager.getCheckedRows();
                     if (data.length == 0){
                     	$.ligerDialog.error('请选择行');
                     }else{
                         var ParamVo =[];
                         var err ="";
                         
                         $(data).each(function (){
                       	 	 if(this.demand_state == '01'){
     							var rowdata = this;
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
               				$.ligerDialog.warn("第["+err+"]行招聘需求已经审核，审核失败！");
               				return;
               			}
                          $.ligerDialog.open({url: 'recruitAuditPassMainPage.do?isCheck=false&',data:ParamVo, height: 200,width: 400, title:'审核通过',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});

                         }
                     return;
                case "refuse":
                	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        var err ="";
                        $(data).each(function (){	
                       	 	 if(this.demand_state == '01'){
                       	 		ParamVo.push({
	        						demand_id: this.demand_id,
	        						group_id: this.group_id,
	        						hos_id: this.hos_id,
	        						demand_state: '03'
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
               				$.ligerDialog.warn("第["+err+"]行招聘需求已经审核，审核失败！");
               				return;
               			}
                        $.ligerDialog.confirm('确定拒绝?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("updateDemandState03Batch.do?isCheck=false&tab_code=HR_RECRUIT_DEMAND",{ParamVo : JSON.stringify(ParamVo)},function (responseData){
                            		if(responseData.state=="true"){
//                             			$.ligerDialog.success("操作成功！");
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "delete":
                	  var data = gridManager.getCheckedRows();
                      if (data.length == 0){
                      	$.ligerDialog.error('请选择行');
                      }else{
                          var paramVo =[];
                          var err ="";
                          $(data).each(function (){	
                        	  if(this.demand_state == '01'){
		                          	if (isnull(this.group_id)) {
		      							cardgridManager.deleteSelectedRow();
		      						} else {
		      							paramVo.push({
			        						demand_id: this.demand_id,
			        						group_id: this.group_id,
			        						hos_id: this.hos_id,
			        						demand_state: '04'
			        					});
		      						}
                        	  }else{
        							if(err == ""){
        								err = this.row_id;
        							}else{
        								err = err+ "、"+this.row_id;
        							}
        						}
                          });
                          
                         if (err != "") {
              				$.ligerDialog.warn("第["+err+"]行招聘需求已经审核，删除失败！");
              				return;
              			}
                          $.ligerDialog.confirm('确定删除?', function (yes){
                          	if(yes){
                              	ajaxJsonObjectByUrl("updateDemandState05Batch.do?isCheck=false&tab_code=HR_RECRUIT_DEMAND",{ParamVo : JSON.stringify(paramVo)},function (responseData){
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
		if(vo[3]== '01' ){
			var parm = "group_id="+vo[0]+"&hos_id="+vo[1]+"&demand_id="+vo[2];
			
	    	$.ligerDialog.open({ url : 'recruitDemandUpdatePage.do?isCheck=false&tab_code=HR_RECRUIT_DEMAND&' + parm,data:{}, height:380,width: 790, title:'修改',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });

		}else{
			$.ligerDialog.warn("招聘需求已经审核，修改失败！");
		}
    }
    function loadDict(){
        //字典下拉框
		$("#demand_state").ligerComboBox({
       		 data:[
       		       {id:'01',text:'审核中'},
       		       {id:'02',text:'通过'},
       		       {id:'03',text:'拒绝'},
       		       {id:'04',text:'删除'},
       		       {id:'05',text:'存档'} 
       		       ],
       		 valueField: 'id',
             textField: 'text',
			 cancelable:true,width:160
			 }); 
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
    		"rows": [{"cell":0,"value":"需求状态","from":"left","align":"left"},
    		         {"cell":1,"value":$("#demand_state").val(),"from":"left","align":"left"}]
    	};
    	var printPara=$.extend({
      		title: "招聘审核",//标题
      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.hr.service.QueryService",
   			bean_name: "queryService",
   			method_name: "queryBaseInfoPtint",
   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			foots: '',//表尾需要打印的查询条件,可以为空 
   			design_code: 'queryCollectDemand.do',
       	}, params);
    	
       	$.each(grid.options.parms,function(i,obj){
     		printPara[obj.name]=obj.value;
       	}); 
       	
       	officeGridPrint(printPara);
       	
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">需求状态：</td>
            <td align="left" class="l-table-edit-td"><input name="demand_state" type="text" id="demand_state" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
