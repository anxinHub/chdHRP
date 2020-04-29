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
    var checkedCustomer = [];
    var isHide = '${p08035 }' == 1 ? false : true;
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		 $("#store_id").ligerTextBox({width:160});
        $("#med_type_id").ligerTextBox({width:160});
        $("#inv_code").ligerTextBox({width:160});
        
        query();
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
         grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split(",")[0]}); 
    	  
       
    	  grid.options.parms.push({name:'med_type_id',value:liger.get("med_type_id").getValue().split(",")[0]}); 
    	  grid.options.parms.push({name:'inv_code',value:$("#inv_code").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '药品编码', name: 'inv_code', align: 'left'
					 		},
                     { display: '药品名称', name: 'inv_name', align: 'left'
					 		},
                     { display: '规格型号', name: 'inv_model', align: 'left'
					 		},
                     { display: '生产厂商', name: 'fac_name', align: 'left'
					 		},
					 { display: '货位', name: 'location_id',textField : 'location_name', align: 'left',hide:isHide,
		                    editor : {
		     						type : 'select',
		     						valueField : 'id',
		     						textField : 'text',
		     						url : '../../../../queryMedLocation.do?isCheck=false&store_id='+liger.get("store_id").getValue().split(",")[0],
		     						keySupport : true,
		     						autocomplete : true,
		     				},
		     				render : function(rowdata, rowindex, value) {
		     						return rowdata.location_name;
		     				} 
		     			},
					 ],
                     dataAction: 'server',dataType: 'server',usePager:true,
                     url:'queryMedInv.do',delayLoad:true,enabledEdit : true,
                     height: '100%', checkbox: true,rownumbers:true, allowAdjustColWidth :true,
                     selectRowButtonOnly:false,heightDiff: -20,onCheckRow: f_onCheckRow,isChecked: f_isChecked,
                      onCheckAllRow: f_onCheckAllRow,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '保存（<u>S</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '全部保存（<u>A</u>）', id:'addAll', click: addAll, icon:'add' },
    	                { line:true },
    	                { text: '关闭', id:'close', click: this_close,icon:'close' },
              			{ line:true }
    				]}
    				
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
     //关闭
	 function this_close(){
	  	frameElement.dialog.close();
	 }
  
    //全部保存
 	function addAll(){
 		
 		var ParamVo =[];
 		
 		store_id = liger.get("store_id").getValue().split(",")[0];
 		ParamVo.push(store_id 
//  				+"@"+ 
//  				this.inv_id +"@"+ 
//  				this.location_id
 		);
      
 		        ajaxJsonObjectByUrl("addMedStoreInvAll.do?isCheck=true",{ParamVo : ParamVo.toString()},function(responseData){
 		            if(responseData.state=="true"){
 		            	query();
 		            	parent.query();
 		            }
 		        });
	}
    
    function f_onCheckAllRow(checked)
    {
        for (var rowid in this.records)
        {
            if(checked)
                addCheckedCustomer(this.records[rowid]['inv_id']+"@"+'${store_id}'+"@"+(this.records[rowid]['location_id'] ? this.records[rowid]['location_id']:"-1"));
            else
                removeCheckedCustomer(this.records[rowid]['inv_id']+"@"+'${store_id}'+"@"+(this.records[rowid]['location_id'] ? this.records[rowid]['location_id']:"-1"));
        }
    }

    /*
    该例子实现 表单分页多选
    即利用onCheckRow将选中的行记忆下来，并利用isChecked将记忆下来的行初始化选中
    */
   
    function findCheckedCustomer(CustomerID)
    {
        for(var i =0;i<checkedCustomer.length;i++)
        {
            if(checkedCustomer[i] == CustomerID) return i;
        }
        return -1;
    }
    function addCheckedCustomer(CustomerID)
    {
        if(findCheckedCustomer(CustomerID) == -1)
            checkedCustomer.push(CustomerID);
    }
    function removeCheckedCustomer(CustomerID)
    {
        var i = findCheckedCustomer(CustomerID);
        if(i==-1) return;
        checkedCustomer.splice(i,1);
    }
    function f_isChecked(rowdata)
    {
        if (findCheckedCustomer(rowdata.inv_id+"@"+'${store_id}'+"@"+(rowdata.location_id?rowdata.location_id:"-1")) == -1)
            return false;
        return true;
    }
    function f_onCheckRow(checked, data)
    {
        if (checked){
        	addCheckedCustomer(data.inv_id+"@"+'${store_id}'+"@"+(data.location_id?data.location_id:"-1"));
        }else{
        	removeCheckedCustomer(data.inv_id+"@"+'${store_id}'+"@"+(data.location_id?data.location_id:"-1"));
        }
    }
    function f_getChecked()
    {
        alert(checkedCustomer.join(','));
    }

    
    
    function add_open(){
 		
    	if (checkedCustomer.length==0){
		  alert("请选择要保存的药品！");
		  
		 }else{
	        ajaxJsonObjectByUrl("addMedStoreInv.do?isCheck=false", {ParamVo : checkedCustomer.toString()},function(responseData){
	            if(responseData.state=="true"){
	            	checkedCustomer=[];
	            	query();
	            	parent.query();
	            	//parent.loadHead(null);
	            }
	        });
	        }
    	}
   
    function loadDict(){
            //字典下拉框
            
           
         autocomplete("#store_id","../../../../queryMedStore.do?isCheck=false","id","text",true,true,'',false,'','160');
    	liger.get("store_id").setValue("${store_id}");
		liger.get("store_id").setText("${store_name}");
		$("#store_id").ligerComboBox({disabled:true});
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

		hotkeys('S', add_open);
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
    	<tr>
    	
    	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库信息：</td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id"  ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
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
