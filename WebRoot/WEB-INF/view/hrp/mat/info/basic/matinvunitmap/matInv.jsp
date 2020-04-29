<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		 
		
        $("#mat_type_id").ligerTextBox({width:160});
        $("#inv_code").ligerTextBox({width:160});
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        
     
        
        
    	  grid.options.parms.push({name:'mat_type_id',value:liger.get("mat_type_id").getValue().split(",")[0]}); 
        if(liger.get("mat_type_id").getValue().split(",")[0]=="")
        	{
        	grid.options.parms.push({name:'mat_type_code',value:""}); 
     	   
        	}
        else{
    	  grid.options.parms.push({name:'mat_type_code',value:liger.get("mat_type_id").getValue().split(",")[2]}); 
        }
    	  
          grid.options.parms.push({name:'inv_code',value:$("#inv_code").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '材料编码', name: 'inv_code', align: 'left'
					 		},
                     { display: '材料名称', name: 'inv_name', align: 'left'
					 		},
                     { display: '规格型号', name: 'inv_model', align: 'left'
					 		},
                     { display: '生产厂商', name: 'fac_name', align: 'left'
					 		},
				 	 { display: '计量单位', name: 'unit_name', align: 'left'
					 		}
					 ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatInv.do',
                     height: '100%', checkbox: true,rownumbers:true, 
                    //allowAdjustColWidth :true,
                     selectRowButtonOnly:true,heightDiff: -20,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '确定（<u>Q</u>）', id:'add', click: f_select,icon:'add' },
                     	{ line:true },
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function f_select()
    { 
        var rows = grid.getCheckedRows(); 
        
        if(rows.length == 0){
            alert('请选择行!');
            return;
        }else{
        	 for (var i = 0; i < rows.length; i++){
                 parent.grid.addRow(rows[i]);
             } 
        	 parent.$.ligerDialog.close();
        	 parent.$(".l-dialog,.l-window-mask").remove();
        }
    }
    function add_open(){
 		var data = grid.getCheckedRows();
 		var ParamVo =[];
        $(data).each(function (){					
				ParamVo.push(
				this.group_id   +"@"+ 
				this.hos_id   +"@"+ 
				this.copy_code   +"@"+ 
				this.inv_id + "@"+ 
				this.inv_code + "@"+
				this.inv_name + "@"+
				this.inv_model + "@"+
				this.fac_id + "@"+
				this.fac_name
				) });
        alert(ParamVo);
	        ajaxJsonObjectByUrl("returnValue.do", {ParamVo : ParamVo.toString()},function(responseData){
	            if(responseData.state=="true"){
	            	parent.loadHead(null);
	            }
	        });
    	}
   
    function loadDict(){
    	
    	
    	
            //字典下拉框
    		$("#mat_type_id").ligerComboBox({
               	url: '../../../queryMatTypeDictCode.do?isCheck=false',
               	valueField: 'id',
                textField: 'text', 
                selectBoxWidth: 160,
               	autocomplete: true,
               	initValue : 0,
               	width: 160
      		  });
         }  
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('S', f_select);
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
    	<tr>
    	 
    	
    	    
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资类别：</td>
            <td align="left" class="l-table-edit-td"><input name="mat_type_id" type="text" id="mat_type_id" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资材料信息：</td>
            <td align="left" class="l-table-edit-td"><input name="inv_code" type="text" id="inv_code" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
    </table>

	<div id="maingrid"></div>
</body>
</html>
