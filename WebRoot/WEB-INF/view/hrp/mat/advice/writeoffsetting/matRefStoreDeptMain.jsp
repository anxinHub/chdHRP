<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<title></title>
<script type="text/javascript">
	
    var grid1;
    
    var gridManager1 = null;
    
	var grid2;
    
    var gridManager2 = null;
    
    var userUpdateStr;
	    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	$("#panel1-1").ligerPanel({
            title: '仓库'
        });
    	$("#panel1-2").ligerPanel({
            title: '科室'
        });
    	 $(':button').ligerButton({width:80});
    });
    //查询
    function  query1(){
    		grid1.options.parms=[];
    		grid1.options.newPage=1;
        	//根据表字段进行添加查询条件
    		grid1.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split(",")[0]}); 

    		//加载查询条件
    		grid1.loadData(grid1.where);
     }
    
    function  query2(){
		grid2.options.parms=[];
		grid2.options.newPage=1;
    	//根据表字段进行添加查询条件
    	
    	
    	if($("#store").val() == "" || $("#store").val() == null){
    		$.ligerDialog.error('请选择仓库');
 			return;
    	}
    	
    	grid2.options.parms.push({name:'store_id',value:$("#store").val()}); 
		grid2.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(",")[0]}); 

		//加载查询条件
		grid2.loadData(grid2.where);
 	}
    
    function queryDept(store_id,store_name){
    	$("#store").val(store_id);
    	$("#store_info").val(store_name);
    	query2();
    }

    function loadHead(){
    	grid1 = $("#maingrid1").ligerGrid({
           columns: [ 
                     { display: '仓库编码', name: 'store_code', align: 'left',width:120
					 },
                     { display: '仓库名称', name: 'store_name', align: 'left',width:150
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryMatStoreDict.do',
                     width: '350px', height: ($(window).height()-85)+"px", checkbox: true,rownumbers:true,isSingleCheck : true,
                     selectRowButtonOnly:true,onCheckRow :function(checked,data,rowid,rowdata){
                    	 queryDept(data.store_id,""+data.store_name+"")
                     }
                   });

        gridManager1 = $("#maingrid1").ligerGetGridManager();
        
        grid2 = $("#maingrid2").ligerGrid({
            columns: [ 
                      { display: '科室编码', name: 'dept_code', align: 'left'
 					 },
                      { display: '科室名称', name: 'dept_name', align: 'left'
 					 }
                      ],
                      dataAction: 'server',dataType: 'server',usePager:false,url:'queryDeptDict.do?isCheck=false',
                      width: '100%', height: ($(window).height()-85)+"px", checkbox: true,rownumbers:true,delayLoad :true,
                      selectRowButtonOnly:true
                    });

         gridManager2 = $("#maingrid2").ligerGetGridManager();
    }

    function del(){
    	var ParamVo = [];
		var data = gridManager2.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
							ParamVo.push(this.dept_id+"@"+$("#store").val());
						
					});
			 $.ligerDialog.confirm('确定删除?', function (yes){
			        if (yes) {
			        	ajaxJsonObjectByUrl("deleteMatRefStoreDept.do?isCheck=false", {
							ParamVo : ParamVo.toString()
						}, function(responseData) {
							if (responseData.state == "true") {
								query2();
							}
						});
			        }
			 });
		}
    }
    
    function settingDept(){
    	if($("#store").val() == "" || $("#store").val() == null){
    		$.ligerDialog.error('请选择仓库');
 			return;
    	}
    	$.ligerDialog.open({
			title: '设置科室',
			height: 600,
			width: 1000,
			url: 'matRefStoreDeptSetPage.do?isCheck=false&store_id='+$("#store").val(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top : 1,
		}); 
    }
    
    function loadDict(){
            //字典下拉框
	$("#store_info").ligerTextBox({width:160,disabled:true,cancelable: false});
	autocomplete("#store_id", "../../queryMatStore.do?isCheck=false", "id", "text", true, true);
	autocomplete("#dept_id", "../../queryMatDept.do?isCheck=false", "id", "text", true, true);
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<input type="hidden" id="store" name="store"/>
	<div style="width:100%;">
        <div id="panel1-1" style="float: left; " class="l-panel" ligeruiid="panel1-1">
        	<table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-top: 33px;">
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px; padding-bottom: 30px;">仓库信息：</td>
		            <td align="left" class="l-table-edit-td" style="padding-bottom: 30px;"><input name="store_id" type="text" id="store_id"  /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td" style=" padding-bottom: 30px;"></td>
		            <td align="left" class="l-table-edit-td" style="padding-left:20px; padding-bottom: 30px;"><button onclick="query1();"><b>查询（<u>Q</u>）</b></button></td>
		            <td align="left"></td>
		        </tr> 
    		</table>
    		<div id="maingrid1"></div>
        </div>
         <div id="panel1-2" style="float: left;width:600px; margin-left: 20px;"  class="l-panel" ligeruiid="panel1-2">
         	<table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-top: 20px;">
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室信息：</td>
		            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"  /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
		            <td align="left" class="l-table-edit-td"><button  onclick="query2();"><b>查询</b></button></td>
		            <td align="left"></td>
		        </tr> 
    		</table>
    		<hr size="1" width="100%" color="#A3COE8" align="left" style="position:absolute;top:45px;">
    		<table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-top: 20px;">
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px; ">仓库信息：</td>
		            <td align="left" class="l-table-edit-td"><input disabled="disabled" name="store_info" type="text" id="store_info"  /></td>
		            <td align="left"></td>
		            <td align="left" class="l-table-edit-td" style="padding-left:20px;"><button  onclick="settingDept();"><b>设置科室</b></button></td>
		            <td align="left" class="l-table-edit-td" style="padding-left:20px;"><button  onclick="del();"><b>删除</b></button></td>
		            <td align="left"></td>
		        </tr> 
    		</table>
    		
    		<div id="maingrid2"></div>
         </div>
	</div>

</body>
</html>
