<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

	var grid;
	
	var gridManager = null;

	$(function() {
	
		loadDict();
	
		loadHead(null); 
	
	});

	//查询
	function query(code) {//根据表字段进行添加查询条件
		
		grid.options.parms = [];grid.options.newPage = 1;
	
		grid.options.parms.push({name : 'mod_code',value : liger.get("mod_code").getValue()});
	
		grid.loadData(grid.where);//加载查询条件
		
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
					columns : [
							{display : '业务编码',name : 'busi_type_code',id: 'busi_type_name',width: 300,align : 'left'}, 
							{display : '业务名称',name : 'busi_type_name',width: 300,align : 'left'},
							{display : '是否自动',name : 'is_vouch',align : 'left',width: 130,render:checkboxRender},
							],
					dataAction : 'server',dataType : 'server',usePager : false,url : 'queryAccBusiType.do',tree: {columnId: 'busi_type_name',idField: 'busi_type_code',parentIDField: 'super_code'},
					width : '100%',height : '100%',checkbox : false,selectRowButtonOnly : true,delayLoad:true,alternatingRow: false,//heightDiff: -10,
					toolbar : {
						items : [
						         {text : '查询',id : 'search',click : query,icon : 'search'},
						         {line : true}
						         ]
					}
				});
	
		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function loadDict() {
		
    	autocomplete("#mod_code","../../../sys/querySysBusiMod.do?isCheck=false","id","text",true,true,"",true);
    	
	}
	
	function checkboxRender(rowdata, rowindex, value, column){
		if(rowdata.is_last == 1){
			 var iconHtml = '<div class="chk-icon';
		        if (value) iconHtml += " chk-icon-selected";
		        iconHtml += '"';
		        iconHtml += ' rowid = "' + rowdata['__id'] + '"';
		        iconHtml += ' gridid = "' + this.id + '"';
		        iconHtml += ' columnname = "' + column.name + '"';
		        iconHtml += '></div>';
		        return iconHtml;
		}
       
    }
	  //是否类型的模拟复选框的点击事件
    $(document).on('click','.chk-icon', function (){
    	
        var gridChk = $.ligerui.get($(this).attr("gridid"));
        
        var rowdata = gridChk.getRow($(this).attr("rowid"));
        
        var columnname = $(this).attr("columnname");
        
        var checked = rowdata[columnname];

         if(checked == true || checked==1){
        	 
        	 var para={
     				
        			 group_id : rowdata.group_id,

        			 hos_id : rowdata.hos_id,
        				
        			 copy_code : rowdata.copy_code,
        			    
        			 mod_code : rowdata.mod_code,
        			 
        			 busi_type_code : rowdata.busi_type_code,
        			 
        			 is_vouch : 0
	        	            
				};

			ajaxJsonObjectByUrl("updateAccBusiTypeForIsVouch.do", para, function(responseData) {
        		 if (responseData.state == "true") {}
        	});
        	 
         }else{
        	 
        	 var para={
      				
        			 group_id : rowdata.group_id,

        			 hos_id : rowdata.hos_id,
        				
        			 copy_code : rowdata.copy_code,
        			    
        			 mod_code : rowdata.mod_code,
        			 
        			 busi_type_code : rowdata.busi_type_code,
        			 
        			 is_vouch : 1
	        	            
				};

			ajaxJsonObjectByUrl("updateAccBusiTypeForIsVouch.do", para, function(responseData) {
        		 if (responseData.state == "true") {}
        	});
         
         }

 			
        grid.updateCell(columnname, !checked, rowdata);
        
        
    });

</script>


</head>

<body style="padding: 0px; overflow: hidden;">
	
   <table cellpadding="0" cellspacing="0" class="l-table-edit" >
   		 <tr>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">系统名称：</td>
            <td align="left" class="l-table-edit-td"><input name="mod_code" type="text" id="mod_code" ltype="text" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
