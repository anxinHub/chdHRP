<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var invGrid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
        $("#med_type_id").ligerTextBox({width:160});
        $("#inv_code").ligerTextBox({width:160});
    });
    //查询
    function  query(){
    	invGrid.options.parms=[];
    	invGrid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  invGrid.options.parms.push({name:'med_type_id',value:liger.get("med_type_id").getValue().split(",")[0]}); 
    	  invGrid.options.parms.push({name:'inv_code',value:$("#inv_code").val()}); 

    	//加载查询条件
    	invGrid.loadData(invGrid.where);
		$("#resultPrint > table > tbody").empty();
     }
    function loadHead(){
    	invGrid = $("#maingrid").ligerGrid({
	           columns: [ 
	             { display: '药品名称', name: 'inv_name', align: 'left',width:150},
              	 { display: '规格型号', name: 'inv_model', align: 'left',width:250},
				 { display: '计量单位', name: 'unit_name', align: 'left',width:80},
			 	 { display: '单价', name: 'price', align: 'right',editor: { type: 'float'},width:90,
					 render:function(rowdata){
		            		return formatNumber(rowdata.price ==null ? 0 : rowdata.price,2,1);
		             }		
				 },
				 { display: '生产厂商', name: 'fac_name', align: 'left',width:250},
				 { display: '备注', name: 'note', align: 'left',editor: { type: 'text'},width:250,
					 	render:function(rowdata,rowindex,value){
					 		if(rowdata.note == null || rowdata.note == ''){
					 			rowdata.note == ''
					 			return '';
					 		}else{
					 			return rowdata.note;
					 		}
					 	}
					 }
              ],
              dataAction: 'server',dataType: 'server',usePager:true,url:'../medprotocolmain/queryMedInvDetail.do?isCheck=false',
              width:'100%',height:'100%', checkbox: true,rownumbers:true, allowAdjustColWidth : true ,frozen : false ,
              selectRowButtonOnly:false,heightDiff: 0,fixedCellHeight:true,
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
        var rows = invGrid.getCheckedRows(); 
        
        if(rows.length == 0){
            alert('请选择行!');
            return;
        }else{
        	 for (var i = 0; i < rows.length; i++){
                 parent.detailGrid.addRow(rows[i]);
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
				) 
			});
	        ajaxJsonObjectByUrl("returnValue.do?isCheck=false", {ParamVo : ParamVo.toString()},function(responseData){
	            if(responseData.state=="true"){
	            	parent.loadHead(null);
	            }
	        });
    	}
   
    function loadDict(){
            //字典下拉框
    		$("#med_type_id").ligerComboBox({
               	url: '../../queryMedTypeDict.do?isCheck=false',
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品类别：</td>
            <td align="left" class="l-table-edit-td"><input name="med_type_id" type="text" id="med_type_id" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品信息：</td>
            <td align="left" class="l-table-edit-td"><input name="inv_code" type="text" id="inv_code" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
    </table>

	<div id="maingrid"></div>
</body>
</html>
