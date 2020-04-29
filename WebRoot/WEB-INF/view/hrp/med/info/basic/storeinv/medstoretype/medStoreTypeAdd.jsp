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
     var dataFormat;
     var grid; 
     var gridManager = null;
     var checkedCustomer = [];
     $(function (){
         loadDict()//加载下拉框
         loadHead(null);
        
     });  
     
     function  save(){
        var formPara={
            
            
            
           store_id:$("#store_id").val(),
            
           med_type_id:$("#med_type_id").val()
            
         };
        
        ajaxJsonObjectByUrl("addMedStoreType.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='store_id']").val('');
				 $("input[name='med_type_id']").val('');
                parent.query();
            }
        });
    }
     
     function loadHead(){
    
     	grid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '物质类别编码', name: 'med_type_code', align: 'left'
 					 		},
                      { display: '物质类别名称', name: 'med_type_name', align: 'left'
 					 		}
 					 ],
                      dataAction: 'server',dataType: 'server',usePager:false,
                      url:'queryMedTypeByStore.do',delayLoad:true,enabledEdit : true,
                      height: '100%', checkbox: true,rownumbers:true, allowAdjustColWidth :true,
                      selectRowButtonOnly:false,heightDiff: -20,onCheckRow: f_onCheckRow,isChecked: f_isChecked,
                       onCheckAllRow: f_onCheckAllRow,
                       delayLoad:true,
                      toolbar: { items: [
                      	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                      	{ line:true },
     					{ text: '保存（<u>S</u>）', id:'add', click: add_open, icon:'add' },
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
   
	 //查询
	    function  query(){
	    		grid.options.parms=[];
	    		
	        //根据表字段进行添加查询条件
	         grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split(",")[0]}); 
	    	  
	       
	    	  grid.options.parms.push({name:'med_type_id',value:liger.get("med_type_id").getValue().split(",")[0]}); 

	    	//加载查询条件
	    	grid.loadData(grid.where);
	    	
			$("#resultPrint > table > tbody").empty();
	     }
     
  
	 function f_onCheckAllRow(checked)
	    {
	        for (var rowid in this.records)
	        {
	            if(checked)
	                addCheckedCustomer(this.records[rowid]['med_type_id']+"@"+'${store_id}');
	            else
	                removeCheckedCustomer(this.records[rowid]['med_type_id']+"@"+'${store_id}');
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
	        if (findCheckedCustomer(rowdata.med_type_id+"@"+'${store_id}') == -1)
	            return false;
	        return true;
	    }
	    function f_onCheckRow(checked, data)
	    {
	        if (checked){
	        	addCheckedCustomer(data.med_type_id+"@"+'${store_id}');
	        }else{
	        	removeCheckedCustomer(data.med_type_id+"@"+'${store_id}');
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
		        ajaxJsonObjectByUrl("addMedTypeByStore.do?isCheck=false", {ParamVo : checkedCustomer.toString()},function(responseData){
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
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
  
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库ID：</td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品类别ID：</td>
            <td align="left" class="l-table-edit-td"><input name="med_type_id" type="text" id="med_type_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    <div id="maingrid"></div>
   
   
    </body>
</html>
