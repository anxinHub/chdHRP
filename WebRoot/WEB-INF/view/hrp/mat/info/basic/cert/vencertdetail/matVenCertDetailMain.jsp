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
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'exsit',value:$("#exsit").val()}); 
    	  grid.options.parms.push({name:'sup_id',value:liger.get("sup_id").getValue().split(",")[0]}); 
    	  grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '供应商编码', name: 'sup_code', align: 'left'
					 		},
                     { display: '供应商名称', name: 'sup_name', align: 'left'
					 		},
                     { display: '是否存在证件信息', name: 'cert_code', align: 'left',
					 			render : function(rowdata,index,value){
					 				if(rowdata.cert_code != null){
					 					return "是";
					 				}else{
					 					return "否";
					 				}
					 			}
					 		},
                     { display: '编辑证件信息', name: '', align: 'left',
					 			render:function(rowdata,index,value){
					 				return "<a href=javascript:openDetail('"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.cert_code+"|"+rowdata.sup_id+"|"+rowdata.sup_code+"|"+rowdata.sup_name+"')>编辑</a>";
					 			}
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatVenCertDetail.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,heightDiff: 0,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true }
    				]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
    function openDetail(obj){
    	var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"cert_code="+vo[3]   +"&"+ 
			"sup_id="+vo[4] 	 +"&"+ 
			"sup_code="+vo[5]   +"&"+ 
			"sup_name="+vo[6] 	  
			
		$.ligerDialog.open({url: 'matVenCertDetailPage.do?isCheck=false&'+ parm, height: 600,width: 1000, title:'供应商证件明细',
			modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,top:0
		})
    }
    function loadDict(){
            //字典下拉框
            //供货单位
    		autocomplete("#sup_id", "../../../../queryHosSupDict.do?isCheck=false", "id","text", true, true,'',false,'',240);
            //供应商分类
    		autocomplete("#type_code", "../../../../queryHosSupType.do?isCheck=false", "id","text", true, true);
            
    		 $("#exsit").ligerTextBox({width:160});
    	     $("#sup_id").ligerTextBox({width:240});
    	     $("#type_code").ligerTextBox({width:160});
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);
	 }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商类别：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供货单位：</td>
            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否存在证件信息：</td>
            <td align="left" class="l-table-edit-td">
            	<select name="exsit" id="exsit"style="width: 135px;" >
                		<option value="">请选择</option>
                		<option value="1">否</option>
                		<option value="2">是</option>
            	</select>
            </td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
