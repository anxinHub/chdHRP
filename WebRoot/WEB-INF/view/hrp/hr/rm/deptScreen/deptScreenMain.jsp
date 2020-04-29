<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<script src="<%=path%>/lib/et_assets/common.js"></script>
<script src="<%=path%>/lib/et_assets/hr/common.js"></script>
<script src="<%=path%>/lib/hrp/hr/hr.js"></script>
<script src="<%=path%>/lib/et_components/et_plugins/etUpload.min.js"></script>
<script type="text/javascript">
	
    var grid,searchParam=[];
    
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
    	grid.options.parms.push({name:'theme_id',value:liger.get("theme_select").getValue()}); 
    	grid.options.parms.push({name:'station_code',value:liger.get("station_select").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);

    	searchParam=grid.options.parms;
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [{ display: '招聘科室', name: 'dept_id_name', align: 'left',width:100
					 },
                     { display: '招聘岗位', name: 'station_name', align: 'left',width:100
					 },
                     { display: '姓名', name: 'app_name', align: 'left',width:100
					 },
                     { display: '性别', name: 'app_sex_name', align: 'left',width:100
					 },
                     { display: '民族', name: 'app_ethnic_name', align: 'left',width:100
					 },
                     { display: '出生日期', name: 'app_birth', align: 'left',width:100
					 },
                     { display: '身份证', name: 'app_cardid', align: 'left',width:100
					 },
                     { display: '学历', name: 'app_edu_name', align: 'left',width:100
					 },
                     { display: '学位', name: 'app_dege_name', align: 'left',width:100
					 },
                     { display: '毕业院校', name: 'app_school', align: 'left',width:100
					 },
                     { display: '专业及方向', name: 'app_major_name', align: 'left',width:100
					 },
                     { display: '电子邮件', name: 'app_email', align: 'left',width:100
					 },
                     { display: '联系电话', name: 'app_phone', align: 'left',width:100
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,
                     url:'queryDeptRecord.do?tab_code=HR_RECRUIT_RECORD&record_state=02',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    	                {text: '打印（<u>P</u>）',id:'',click: print,icon: 'print'} ,
    	                { line:true },
                     	{ text: '查看简历', id:'openResume', click: itemclick,icon:'search2' },
    	                { line:true },
    					{ text: '拟录用', id:'deptPass', click: itemclick, icon:'right' },
    	                
    	                
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'addDeptKindPage.do?isCheck=false', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveDeptKind(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
                case "deptPass":
                	var data = gridManager.getCheckedRows();
        			if (data.length == 0) {
        				$.ligerDialog.error('请选择应聘人员');
        			} else {
        				var ParamVo = [];
        				$(data).each(function(i) {
        					ParamVo.push({
        						record_id: this.record_id,
        						demand_id: this.demand_id,
        						app_id: this.app_id,
        						group_id: this.group_id,
        						hos_id: this.hos_id,
        						record_state: '03',
        						record_note: this.record_note
        					});
        				});
        				$.ligerDialog.confirm(
        								'确定优先通过?',
        								function(yes) {
        									if (yes) {
        										$.post("updateRecordStage02Batch.do?isCheck=false&tab_code=HR_RECRUIT_RECORD",  {ParamVo:JSON.stringify(ParamVo)},
     												   function(data,status){
     													   if(data.state == "true"){
     														   $.ligerDialog.success("优先通过成功！");
     														   query();
     													   }else{
     															$.ligerDialog.warn(data.error);
     													   }
     														
     												 },"json");
        									}
        								});
        			}
                    
                    return;
                case "openResume":
                    var data = gridManager.getCheckedRows();
            		if (data.length == 0 || data.length > 1) {
            			$.ligerDialog.error('请选择应聘人员');
            		} else {
            			var fileUpload;
            			var dialogIndex = $.etDialog.open({
                            content: '<div id="fileUpload"></div>',
                            title: '文件查看',
                            width: 400,
                            height: 350,
                            btn: ['关闭'],
                            btn1: function () {
                                $.etDialog.close(dialogIndex);
                            },
                            success: function () {
                            	fileUpload = $('#fileUpload').etUpload({
                                    type: 'file'
                                });

                                var valueStr = data[0].app_resumes;
                                if (valueStr && Array.isArray(JSON.parse(valueStr))) {
                                    var fileArr = [];
                                    JSON.parse(valueStr).forEach((item) => {
                                        fileArr.push(item.url);
                                    });
                                    fileUpload.setValues(fileArr);
                                }
                            }
                        });
            		}
                    return;
                case "downTemplate":location.href = "downTemplate.do?isCheck=false" ;
				 return;
			    case "import":
             	$.ligerDialog.open({url: 'deptKindImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
             	return;
            }   
        }
        
    }
	
  //打印
    function print() {
    	if(grid.getData()==null){
    		$.etDialog.error("请先查询数据！");
			return;
		}
    	
    	var params = {};
    	searchParam.push({name:'record_state',value: '02'}); 
        if(searchParam && Array.isArray(searchParam)){
        	$.each(searchParam, function(i, v){
                params[v.name] = v.value;
            });
        }
    	
    	var heads = {};
    	
    	heads={
    		"isAuto":true,//系统默认，页眉右上角默认显示页码
    		"rows": [{"cell":0,"value":"招聘主题","from":"left","align":"left"},
    		         {"cell":1,"value":$("#theme_select").val(),"from":"left","align":"left"},
    		         {"cell":2,"value":"招聘岗位","from":"left","align":"left"},
    		         {"cell":3,"value":$("#station_select").val(),"from":"left","align":"left"}]
    	};
    	var printPara=$.extend({
      		title: "科室筛选",//标题
      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.hr.service.QueryService",
   			bean_name: "queryService",
   			method_name: "queryBaseInfoPtint",
   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			foots: '',//表尾需要打印的查询条件,可以为空 
   			design_code: 'queryDeptRecord.do',
       	}, params);
    	
       	$.each(grid.options.parms,function(i,obj){
     		printPara[obj.name]=obj.value;
       	}); 
       	
       	officeGridPrint(printPara);
       	
    }
    
    
    function loadDict(){
            //字典下拉框
    	autocomplete("#theme_select","../../baseSelect.do?isCheck=false&&field_tab_code=DIC_RECRUIT_THEME", "id", "text",
    			true, true, "", false, null, "180");
		autocomplete("#station_select","../../baseSelect.do?isCheck=false&&field_tab_code=DIC_STATION_MANAGE", "id", "text",
			true, true, "", false, null, "180");
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">招聘主题：</td>
            <td align="left" class="l-table-edit-td"><input name="theme_select" type="text" id="theme_select" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">招聘岗位：</td>
            <td align="left" class="l-table-edit-td"><input name="station_select" type="text" id="station_select" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
