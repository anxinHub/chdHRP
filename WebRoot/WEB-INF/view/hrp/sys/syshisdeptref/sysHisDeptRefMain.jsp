<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	
    var grid;
     
    var gridManager = null;
    
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
    	grid.options.parms.push({name:'his_dept_code',value:liger.get("his_dept_code").getValue()}); 
    	grid.options.parms.push({name:'dept_type',value:liger.get("dept_type").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where); 
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: 'HRP科室编码', name: 'hrp_dept_code', align: 'left',},
                     { display: 'HRP科室名称', name: 'hrp_dept_name', align: 'left'},
                     { display: 'HIS科室编码', name: 'his_dept_code', align: 'left',},
                     { display: 'HIS科室名称', name: 'his_dept_name', align: 'left'},
                     { display: '业务类型', name: 'dept_type', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
 								if(rowdata.dept_type==1)return "开单科室";
 								if(rowdata.dept_type==2)return "执行科室";
 								if(rowdata.dept_type==3)return "共用科室"
 						  }
                     },
                     { display: '科室性质', name: 'natur_code', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
 								if(rowdata.natur_code=='01')return "门诊";
 								if(rowdata.natur_code=='02')return "住院";
 						  }
                     },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'querySysHisDeptRef.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '添加', id:'add', click: add_open,icon:'add' },
                     	{ line:true },
    	                { text: '删除', id:'delete', click: remove,icon:'delete' },
    	                { line:true }, 
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function add_open(){
    	$.ligerDialog.open({url: 'sysHisDeptRefAddPage.do?isCheck=false', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveHisDeptRef(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

      }
    function remove(){
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
					this.hrp_dept_code   +"@"+
					this.his_dept_code   +"@"+
					this.dept_type  +"@"+
					this.natur_code 
				)
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteSysHisDeptRef.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }

    }
    function loadDict(){
            //字典下拉框
    	  $("#his_dept_code").ligerTextBox({ width:160 });

    	    $("#dept_type").ligerComboBox({
                width : 200,
                data: [
                    { text: '开单', id: '1' },
                    { text: '执行', id: '2' },
                    { text: '共用', id: '3' },
                ],  
            });
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">HIS科室编码：</td>
            <td align="left" class="l-table-edit-td"><input name="his_dept_code" type="text" id="his_dept_code" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">业务类型：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_type" type="text" id="dept_type" ltype="text" /></td>
            <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
