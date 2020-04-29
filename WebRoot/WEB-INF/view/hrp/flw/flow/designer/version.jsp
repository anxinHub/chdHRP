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

<script type="text/javascript">
    var grid;
    var gridManager = null;
    $(function ()
    {
    	templateBar();
    	loadHead(null);	//加载数据

    });
   
   

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [
					{ display: '部署ID', name: 'deployment_id_', align: 'left',width:80
					}, 
					{ display: '流程分类', name: 'category_', align: 'left',width:120
					 },
                     { display: '流程名称', name: 'name_', align: 'left'
					 },
					 { display: '关联业务', name: 'template_business', align: 'left'
					 },
                     { display: '状态', name: 'suspension_state_', align: 'center',width:50,
						 render : function(rowdata, rowindex,value) {
								if(value == 1){
									return "<font style='font-weight:bold;color:green;'>激活</font>";
								}else{
									return "<font style='font-weight:bold;color:red;'>挂起</font>";
								}
							}
					 },
					 { display: '版本号', name: 'version_', align: 'center',width:50
					 },
                     { display: '部署人', name: 'user_name', align: 'left',width:80
					 },
					 { display: '部署时间', name: 'deploy_time_', align: 'left'
					 },
					 { display: '操作', align: 'center',isAllowHide: false,width:80,
						 render: function (row)
		                 {
							 	var image="<a href=\"#\" onclick=\"onImage('" + row.deployment_id_ + "','"+row.dgrm_resource_name_+"');\">流程图</a>";
		                        return image;
		                  }
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryFlowActReProcdefVersion.do',
                     urlParms:{key_:'${key_}',version_:${version_}},
                     width: '100%', height: $(window).height(), checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,delayLoad:false
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    
    //查询
    function  query(){
    	grid.options.parms=[];
        //根据表字段进行添加查询条件
        if($("#template_name").val()==""){
    		grid.options.parms.push({name:'name_',value:$("#template_name").val()}); 
        }else{
        	grid.options.parms.push({name:'name_',value:'%'+$("#template_name").val()+'%'});
        }
    	//加载查询条件
    	grid.loadData(grid.where);
     }

	//流程模板    
    function templateBar(){
   	 $("#tempToolBar").ligerToolBar({ items: [
   	         { text: '查询',id:'search',icon:'search', click: function(){
   	        	query();
   	         } },
   	         { line:true },
   	         { text: '删除',id:'delete',icon:'delete', click: function(){
   	        	 var data = gridManager.getCheckedRows();
                 if (data.length == 0){
                 	$.ligerDialog.error('请选择行');
                 }else{
                     $.ligerDialog.confirm('确定删除?', function (yes){
                     	if(yes){
                     		var paramVo =[];
                            $(data).each(function (){					
                            	paramVo.push(this.deployment_id_);
                            });
                         	ajaxJsonObjectByUrl("flowDeploymentDelete.do",{paramVo : paramVo},function (responseData){
                         		if(responseData.state=="ok"){
                         			query(1);
                         		}
                         	});
                     	}
                     }); 
                 }
   	         } },
   	      	{ line:true },
	         { text: '关闭',id:'candle',icon:'candle', click: function(){
	        	 frameElement.dialog.close();
	         } }
   	         ]
   	 });
   }
    
  //查看流程图
	function onImage(deployment_id_,dgrm_resource_name_){
		$.ligerDialog.open({
            title : '流程图',
            url: 'flowViewImagePage.do?deploymentId='+deployment_id_,
            width: $(window).width()+10,
            height: $(window).height(),
            buttons: [{ text: '打印',onclick: function (item, dialog) {
	         		var LODOP=getLodop();
	         		LODOP.PRINT_INIT("流程图打印");
	         		LODOP.SET_PREVIEW_WINDOW(1,0,1,-1,-1,"流程图打印.开始打印");
	         		LODOP.SET_PRINT_PAGESIZE(0,0,0,"A4");//设定纸张大小 
	         		LODOP.ADD_PRINT_IMAGE(1,1,'100%','100%',dialog.frame.imagePrint.innerHTML);
	         		LODOP.SET_SHOW_MODE("PREVIEW_NO_MINIMIZE",true);//预览窗口禁止最小化，并始终最前
	         		LODOP.PREVIEW();
           	 
            	},cls:'l-dialog-btn-highlight' },{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]
		 });
	
	}
	

    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">流程名称：</td>
			            <td align="left" class="l-table-edit-td"><input name="template_name" type="text" id="template_name" ltype="text" /></td>
			        </tr> 
	 </table>

	<div class="l-layout-header" id="tempToolBar"></div>
    <div  id="maingrid">
	</div>
</body>
</html>
