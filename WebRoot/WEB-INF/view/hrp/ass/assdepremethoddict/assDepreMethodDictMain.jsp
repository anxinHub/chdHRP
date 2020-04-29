<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		
        $("#ass_depre_code").ligerTextBox({width:160});
      
        $("#ass_depre_name").ligerTextBox({width:160});
    
        $("#is_stop").ligerComboBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'ass_depre_code',value:$("#ass_depre_code").val()}); 
    	
    	  grid.options.parms.push({name:'ass_depre_name',value:$("#ass_depre_name").val()}); 
    	 
    	  grid.options.parms.push({
  			name : 'is_stop',
  			value : liger.get("is_stop").getValue()
  		}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '方法编码', name: 'ass_depre_code', align: 'left',width: '5%'/* ,
                    	 render : function(rowdata, rowindex,
								value) {
							return "<a href=javascript:openUpdate('"+ rowdata.ass_depre_code +"')>"+rowdata.ass_depre_code+"</a>";
					   } */
			 		},
                     { display: '方法名称', name: 'ass_depre_name', align: 'left',width: '20%'
					 		},
                     { display: '方法描述', name: 'ass_depre_define', align: 'left',width: '70%'
					 		},
                     
					 		{ display: '是否停用', name: 'is_stop', align: 'left',width: '5%',
		                    	 render : function(rowdata, rowindex,
											value) {
										if(rowdata.is_stop == 0){
											return "否";
										}else{
											return "是"
										}
									}
							 		}
		                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryAssDepreMethodDict.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad :false,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true }
				    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function loadDict(){
            //字典下拉框
             $('#is_stop').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true
		})
         }  
	 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧方法编码：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_depre_code" type="text" id="ass_depre_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧方法名称：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_depre_name" type="text" id="ass_depre_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
                	<!-- <select id="is_stop" name="is_stop" style="width: 135px;">
                	       <option value="">请选择</option>
			               <option value="0">否</option>
			               <option value="1">是</option>
                	</select> -->
                	<input name="is_stop" type="text" id="is_stop"  />
                </td>
                <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
