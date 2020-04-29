<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">	
    var grid;
    
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
    		grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue().split(".")[0]}); 
    		
    		//加载查询条件
    		grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '发生日期', name: 'occur_date', align: 'left',
					 },
					 { display: '凭证编号', name: 'vouch_no', align: 'left'
					 },
					 { display: '摘要', name: 'summary', align: 'left',
					 },
					 { display: '科目', name: 'subj_name', align: 'left'
					 },
                     { display: '借方金额', name: 'debit', align: 'right',
   						 render:function(rowdata){
   	   						 return formatNumber(rowdata.debit, 2, 1);
   						 }
					 },
                     { display: '制单人', name: 'create_username', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryGroupAccVouchImport.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function loadDict(){
    	
    	var data={
    			
    			subj_type_code:'05'
    			
    		};
            //字典下拉框
    	autocomplete("#subj_code","../../querySubj.do?isCheck=false","id","text",true,true,data);
         } 
    
    function save(){
    	
    	var data = gridManager.getCheckedRows();
    	
    	if (data.length == 0){
    		
        	$.ligerDialog.error('请选择行');
        	
        }else{
        	
            var ParamVo =[];
            
            $(data).each(function (){	
            	
				ParamVo.push(
				this.group_id   +"@"+ 
				this.hos_id   +"@"+ 
				this.copy_code   +"@"+  
				this.vouch_no +"@"+ 
				this.vouch_date +"@"+ 
				this.summary +"@"+ 
				this.subj_id +"@"+ 
				this.debit +"@"+ 
				this.user_id
				)
            });
            ajaxJsonObjectByUrl("addBatchGroupAccItialExpend.do",{ParamVo : ParamVo},function (responseData){
        		if(responseData.state=="true"){
        			query();
        		}
        	});
        }
    	
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<!-- <div id="topmenu"></div> -->
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目编码：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
