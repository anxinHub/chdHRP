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
	
    var grid,searchParam=[];
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead();	//加载数据
    	
    });
    //查询
    function  query(){
   		grid.options.parms=[];
   		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'start_time',value:$("#start_time").val()}); 
        grid.options.parms.push({name:'end_time',value:$("#end_time").val()}); 
    	grid.options.parms.push({name:'app_label',value:$("#app_label").val()}); 
    	grid.options.parms.push({name:'app_edu',value:liger.get("edu_select").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);

    	searchParam=grid.options.parms;
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#kind_code").val()!=""){
                		return rowdata.kind_code.indexOf($("#kind_code").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '姓名', name: 'app_name', align: 'left',width:100
					 },
                     { display: '性别', name: 'app_sex_name', align: 'left',width:100
					 },
                     { display: '申请岗位', name: 'station_name', align: 'left',width:100
					 },
//                      { display: '笔试成绩', name: 'note', align: 'left'
// 					 },
//                      { display: '面试成绩', name: 'note', align: 'left'
// 					 },
                     { display: '出生年月', name: 'app_birth', align: 'left',width:100
					 },
                     { display: '身份证', name: 'app_cardid', align: 'left',width:100
					 },
                     { display: '学历', name: 'app_edu_name', align: 'left',width:100
					 },
                     { display: '学位', name: 'app_dege_name', align: 'left',width:100
					 },
                     { display: '专业方向', name: 'app_major_name', align: 'left',width:100
					 },
                     { display: '英语水平', name: 'app_cet_name', align: 'left',width:100
					 },
                     { display: '电子邮件', name: 'app_email', align: 'left',width:100
					 },
                     { display: '联系电话', name: 'app_phone', align: 'left',width:100
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,
                     url:"queryAppStorge.do?tab_code=HR_RECRUIT_RECORD&is_stroge=02",
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' }  ,
                     	{ line:true },   
    	                {text: '打印（<u>P</u>）',id:'',click: print,icon: 'print'} ,
    	               
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
                case "modify":
                    return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							//表的主键
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.kind_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteDeptKind.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "downTemplate":location.href = "downTemplate.do?isCheck=false" ;
				 return;
			    case "import":
             	$.ligerDialog.open({url: 'deptKindImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
             	return;
                case "Excel":
                case "Word":
                case "PDF":
                case "TXT":
                case "XML":
                    $.ligerDialog.waitting('导出中，请稍候...');
                    setTimeout(function ()
                    {
                        $.ligerDialog.closeWaitting();
                        if (item.id == "Excel")
                            $.ligerDialog.success('导出成功');
                        else
                            $.ligerDialog.error('导出失败');
                    }, 1000);
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
    	searchParam.push({name:'is_stroge',value:'02'});
        if(searchParam && Array.isArray(searchParam)){
        	$.each(searchParam, function(i, v){
                params[v.name] = v.value;
            });
        }
    	
    	var heads = {};
    	
    	heads={
    		"isAuto":true,//系统默认，页眉右上角默认显示页码
    		"rows": [{"cell":0,"value":"开始日期","from":"left","align":"left"},
    		         {"cell":1,"value":$("#start_time").val(),"from":"left","align":"left"},
    		         {"cell":2,"value":"结束日期","from":"left","align":"left"},
    		         {"cell":3,"value":$("#end_time").val(),"from":"left","align":"left"},
    		         {"cell":4,"value":"简历标签","from":"left","align":"left"},
    		         {"cell":5,"value":$("#app_label").val(),"from":"left","align":"left"},
    		         {"cell":6,"value":"学历","from":"left","align":"left"},
    		         {"cell":7,"value":$("#edu_select").val(),"from":"left","align":"left"}]
    	};
    	var printPara=$.extend({
      		title: "人才储备",//标题
      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.hr.service.QueryService",
   			bean_name: "queryService",
   			method_name: "queryBaseInfoPtint",
   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			foots: '',//表尾需要打印的查询条件,可以为空 
   			design_code: 'queryAppStorge.do',
       	}, params);
    	
       	$.each(grid.options.parms,function(i,obj){
     		printPara[obj.name]=obj.value;
       	}); 
       	
       	officeGridPrint(printPara);
       	
    }
    
    function loadDict(){
            //字典下拉框
    	autocomplete("#kind_code","queryDeptKindDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#edu_select",
				"../../baseSelect.do?isCheck=false&&field_tab_code=DIC_EDUCATION", "id", "text",
				true, true, "", false, null, "180");
    	$("#app_label").ligerTextBox({width:180}); 
		$("#start_time").ligerTextBox({width : 180});
		$("#end_time").ligerTextBox({width : 180});
		} 
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="70">开始日期<span style="color: red">*</span>：</td>
            	<td align="left" class="l-table-edit-td" ><input name="start_time" class="Wdate" type="text" id="start_time" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
        	<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;" width="70">结束日期<span style="color: red">*</span>：</td>
           	<td align="left" class="l-table-edit-td" ><input name="end_time" class="Wdate" type="text" id="end_time" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
			<td align="left"></td>
       		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">简历标签：</td>
            <td align="left" class="l-table-edit-td"><input name="app_label" type="text" id="app_label" ltype="text" validate="{required:true,maxlength:50}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">学历：</td>
            <td align="left" class="l-table-edit-td"><input name="edu_select" type="text" id="edu_select" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
<!--             <td align="right" class="l-table-edit-td"  style="padding-left:10px;">职称：</td> -->
<!--             <td align="left" class="l-table-edit-td"><input name="kind_code1" type="text" id="kind_code1" ltype="text" validate="{required:true,maxlength:20}" /></td> -->
<!--             <td align="left"></td> -->
            
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
