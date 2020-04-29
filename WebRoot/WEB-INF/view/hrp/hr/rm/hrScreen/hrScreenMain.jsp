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
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_code").getValue().split("@")[0]}); 
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_code").getValue().split("@")[1]}); 
//     	grid.options.parms.push({name:'record_state',value: '03'}); 
//     	grid.options.parms.push({name:'is_stroge',value:'01'}); 
    	//加载查询条件
    	grid.loadData(grid.where);
    	searchParam=grid.options.parms;
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '招聘科室', name: 'dept_id_name', align: 'left'
					 },
                     { display: '招聘岗位', name: 'station_name', align: 'left'
					 },
                     { display: '姓名', name: 'app_name', align: 'left'
					 },
                     { display: '性别', name: 'app_sex_name', align: 'left',
					 },
                     { display: '民族', name: 'app_ethnic_name', align: 'left'
					 },
                     { display: '出生日期', name: 'app_birth', align: 'left'
					 },
                     { display: '身份证', name: 'app_cardid', align: 'left'
					 },
                     { display: '学历', name: 'app_edu_name', align: 'left'
					 },
                     { display: '学位', name: 'app_dege_name', align: 'left'
					 },
                     { display: '毕业院校', name: 'app_school', align: 'left'
					 },
                     { display: '专业及方向', name: 'app_major_name', align: 'left'
					 },
                     { display: '电子邮件', name: 'app_email', align: 'left'
					 },
                     { display: '联系电话', name: 'app_phone', align: 'left'
					 }],
                     dataAction: 'server',dataType: 'server',usePager:true,
                     url:'queryHrRecord.do?tab_code=HR_RECRUIT_RECORD&record_state=03&is_stroge=01',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    	                {text: '打印（<u>P</u>）',id:'',click: print,icon: 'print'} ,
    	                { line:true },
    	                { text: '查看简历', id:'openResume', click: itemclick,icon:'search2' },
    	                { line:true },
    	                { text: '人才储备', id:'storge', click: itemclick, icon:'plus' },
    	                { line:true },
    					{ text: '确定录用', id:'hrPass', click: itemclick, icon:'right' },
    	                
    	                
    	                
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "hrPass":
                	var data = gridManager.getCheckedRows();
        			if (data.length == 0) {
        				$.ligerDialog.error('请选择应聘人员');
        			} else {
        				var ParamVo = [];
        				$(data).each(function(i) {
        					ParamVo.push({
        						record_id: this.record_id,
        						app_name: this.app_name,
        						group_id: this.group_id,
        						hos_id: this.hos_id,
        						dept_no : this.dept_no,
        						dept_id : this.dept_id,
        						emp_name : this.app_name,
        						app_cardid :this.app_cardid,
        						app_phone :this.app_phone,
        						app_email :this.app_email,
        						app_sex :this.app_sex,
        						app_birth :this.app_birth
        					});
        				});
        			
        				$.ligerDialog.open({url: 'addEmpIDMainPage.do?isCheck=false&',data:ParamVo, height: 160,width: 300, title:'确定录用',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});
        			}
        						
              		return;
                case "storge":
                	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }
                    else{
                        var param =[];
                        var err ="",i=0;
                        $(data).each(function (){	
                        	i++;
                        	var rowdata = this;
                        	if(rowdata.is_stroge != '02'){
                        		rowdata.is_stroge = '02';
    							rowdata.tab_code = 'HR_RECRUIT_APP';
    							param.push(rowdata);
                        	}else{ 
                        		if(err == ""){
    								err = i;
    							}else{
    								err += "、"+i;
    							}
                        	}
                        });
                        if (err != "") {
            				$.ligerDialog.warn("第["+i+"]行应聘人员已经加入人才储备库！");
            				return;
            			}
                        $.ligerDialog.confirm('确定添加人才库?', function (yes){
                        	if(yes){
                            	$.post("updateAppStorgeBatch.do?isCheck=false&tab_code=HR_RECRUIT_APP",  {ParamVo:JSON.stringify(param)},
										   function(data,status){
											   if(data.state == "true"){
												   $.ligerDialog.success("添加人才库成功！");
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
            }   
        }
        
    }

    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "group_id="+vo[0]+"&hos_id="+vo[1]+"&kind_code="+vo[2];
		
    	$.ligerDialog.open({ url : 'deptKindUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveDeptKind(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
  //打印
    function print() {
    	if(grid.getData()==null){
    		$.etDialog.error("请先查询数据！");
			return;
		}
    	
    	var params = {};
    	searchParam.push({name:'record_state',value: '03'}); 
    	searchParam.push({name:'is_stroge',value:'01'}); 
        if(searchParam && Array.isArray(searchParam)){
        	$.each(searchParam, function(i, v){
                params[v.name] = v.value;
            });
        }
    	
    	var heads = {};
    	
    	heads={
    		"isAuto":true,//系统默认，页眉右上角默认显示页码
    		"rows": [{"cell":0,"value":"科室","from":"left","align":"left"},
    		         {"cell":1,"value":$("#dept_code").val(),"from":"left","align":"left"}]
    	};
    	var printPara=$.extend({
      		title: "人事科筛选",//标题
      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.hr.service.QueryService",
   			bean_name: "queryService",
   			method_name: "queryBaseInfoPtint",
   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			foots: '',//表尾需要打印的查询条件,可以为空 
   			design_code: 'queryHrRecord.do',
       	}, params);
    	
       	$.each(grid.options.parms,function(i,obj){
     		printPara[obj.name]=obj.value;
       	}); 
       	
       	officeGridPrint(printPara);
       	
    }
    function loadDict(){
            //字典下拉框
    	autocomplete("#dept_code","../../queryHosDeptSelect.do?isCheck=false","id","text",true,true);
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
