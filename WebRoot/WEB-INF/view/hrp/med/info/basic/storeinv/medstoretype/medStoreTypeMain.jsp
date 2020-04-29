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
    var store_id ;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
        $("#store_id").ligerTextBox({width:160});
        $("#med_type_id").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split(",")[0]}); 
    	  grid.options.parms.push({name:'med_type_id',value:liger.get("med_type_id").getValue().split(",")[0]}); 
    	  grid.options.parms.push({name:'type_level',value:$("#type_level").val()});
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#store_id").val()!=""){
                		return rowdata.store_id.indexOf(liger.get("store_id").getValue().split(",")[0]) > -1;	
                	}
                	if($("#med_type_id").val()!=""){
                		return rowdata.med_type_id.indexOf(liger.get("med_type_id").getValue().split(",")[0]) > -1;	
                	}
                	if($("#type_level").val()!=""){
                		return rowdata.type_level.indexOf($("#type_level").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '类别编码', name: 'med_type_code', align: 'left'
					 		},
                     { display: '类别名称', name: 'med_type_name', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryStoreType.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:false,heightDiff: -10,isChecked:getCheck,
                   //  delayLoad :true,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '选择类别（<u>Q</u>）', id:'select', click: selectMedType,icon:'add' },
                     	{ line:true },
    					{ text: '保存（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    				]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  //选择类别
    function selectMedType(){
    	var paras = "store_id="+liger.get("store_id").getValue().split(",")[0]
    	+"&"+ "store_name="+liger.get("store_id").getText(); 
    	
    	
 		$.ligerDialog.open({url: 'medStoreTypeAddPage.do?isCheck=false&'+ paras.toString(),height: 500,width: 1000, 
 				title:'选择 类别',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
 					});
 		
	}
    
    //存在对应关系则选中
    function getCheck(rowdata){
    	if(typeof(rowdata.store_id) =="number"){
            return true;
    	}
        return false;
    }
    
    function add_open(){
    	
    	var data = grid.getCheckedRows();
 		store_id = liger.get("store_id").getValue().split(",")[0];
 		var ParamVo =[];
 		if(data.length == 0){
 			ParamVo.push( store_id );
 			ajaxJsonObjectByUrl("saveMedStoreType.do?isCheck=false", {ParamVo : ParamVo.toString()},function(responseData){
	            if(responseData.state=="true"){
	            	query();
	            }
 			})
 		}else{
	        $(data).each(function (){					
					ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.med_type_id + "@"+ store_id
					) });
		        ajaxJsonObjectByUrl("addMedStoreType.do?isCheck=false", {ParamVo : ParamVo.toString()},function(responseData){
		            if(responseData.state=="true"){
		            	query();
		            }
		        });
 			}
    }
    /* 仓库信息 onChange 事件 */
    function getStore_id(){
    	store_id = liger.get("store_id").getValue().split(",")[0];
    }
    function loadDict(){
            //字典下拉框
	    	autocomplete("#store_id","../../../../queryMedStore.do?isCheck=false","id","text",true,true,'',true,'','160');
	        
	    	autocomplete("#type_level","../../../../queryMedTypeLevel.do?isCheck=false","id","text",true,true,'',false,'','160');
	    	
			$("#med_type_id").ligerComboBox({
	           	url: '../../../../queryMedTypeDict.do?isCheck=false',
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

		hotkeys('A', add_open);
		

	 }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>仓库信息<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" onChange="getStore_id()" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>药品类别:</b></td>
            <td align="left" class="l-table-edit-td"><input name="med_type_id" type="text" id="med_type_id" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>级次:</b></td>
            <td align="left" class="l-table-edit-td"><input name="type_level" type="text" id="type_level" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
