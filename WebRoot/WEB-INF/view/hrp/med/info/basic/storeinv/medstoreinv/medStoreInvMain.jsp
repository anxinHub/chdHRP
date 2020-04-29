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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var store_id ;
    var checkedCustomer = [];
    var isHide = '${p08035 }' == 1 ? false : true;
    $(function () 
    {
        loadDict();//加载下拉框
    	//加载数据
        loadHead(null);
		loadHotkeys();
		
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
        
    	grid.options.parms.push({name:'store_id',value : liger.get("store_id").getValue().split(",")[0]}); 
    	grid.options.parms.push({name:'med_type_id',value : liger.get("med_type_id").getValue().split(",")[0]}); 
    	grid.options.parms.push({name:'med_type_code',value : liger.get("med_type_id").getValue().split(",")[2]}); 
    	grid.options.parms.push({name:'is_apply',value : liger.get("is_apply").getValue()});
    	grid.options.parms.push({name:'is_app',value : liger.get("is_app").getValue()}); 
    	grid.options.parms.push({name:'inv_code',value : $("#inv_code").val()}); 
    	//生产厂商编码
    	grid.options.parms.push({name : 'fac_id',value : liger.get("fac_code").getValue() == null ? "" : liger.get("fac_code").getValue().split(",")[0]
    	});
    	grid.options.parms.push({
			name : 'inv_model',//规格型号
			value : $("#inv_model").val()
		});
    	grid.options.parms.push({
			name : 'fac_id',//生产厂商编码
			value : liger.get("fac_code").getValue() == null ? "" : liger.get("fac_code").getValue().split(",")[0]
    	});
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '药品编码', name: 'inv_code', align: 'left',width:120},
                     { display: '药品名称', name: 'inv_name', align: 'left',width:300},
                     /* { display: '货位', name: 'location_name',align: 'left',width:150,
				 			//valueField : 'id',textField: 'text',
				 			editor: { 
				 				type: 'select', 
				 				url:'../../../../queryMedInvLocationDict.do?isCheck=false&store_id=',
				 				valueField : 'id',textField: 'text',
				 				keySupport: true,
				 				autocomplete : true 
				 			}
						},    */
                     { display: '药品类别', name: 'med_type_name', align: 'left',width:200},
                     { display: '规格', name: 'inv_model', align: 'left',width:150},
                     { display: '是否申领', name: 'is_apply', align: 'center',width:100,
                     	render : function(rowdata){
                     		if(rowdata.is_apply == '1'){
                     			return "是";
                     		}else{
                     			return "否";
                     		}
                     	}
                     },
                      { display: '是否已设置申领', name: 'is_set', align: 'center',width:100,
                         	render : function(rowdata){
                         		if(rowdata.is_set == '1'){
                         			return "是";
                         		}else{
                         			return "否";
                         		}
                         	}
                     },
                     { display: '货位', name: 'location_code', align: 'left',width:90,hide:isHide,
                      	render : function(rowdata, rowindex,value){
                      		if(rowdata.location_code==null){
                      			//return "编辑";
                      			return '<a href=javascript:openUpdate("' 
    							+ rowdata.group_id + ',' + rowdata.hos_id 
    							+ ',' + rowdata.copy_code + ',' + rowdata.store_id
    							+ ',' + rowdata.inv_id +',' + rowdata.location_id + '")>'+"编辑"+'</a>';
                      		}else{
                      			//return rowdata.location_name;
                      			return '<a href=javascript:openUpdate("' 
    							+ rowdata.group_id + ',' + rowdata.hos_id 
    							+ ',' + rowdata.copy_code + ',' + rowdata.store_id
    							+ ',' + rowdata.inv_id +',' + rowdata.location_id + '")>'+rowdata.location_name+'</a>';
                      		}
                      	}
                  	  },
                     { display: '生产厂商', name: 'fac_name', align: 'left',width:300},
                     { display: '操作人', name: 'user_name', align: 'left',width:100},
                     { display: '操作时间', name: 'oper_date', align: 'left',width:100}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,
                     url:'queryMedStoreInvNew.do?isCheck=true',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:false,heightDiff: -20
                     ,onCheckRow : f_onCheckRow,isChecked: f_isChecked,
                     onCheckAllRow: f_onCheckAllRow,
                     enabledEdit: true,
                     delayLoad:true,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '添加药品', id:'add', click: addInv,icon:'add' },
                     	{ line:true },
						{ text: '选择药品（<u>S</u>）', id:'select', click: selectMedInv,icon:'add' },
						{ line:true },
						{text: '设置申领仓库（<u>A</u>）', id:'audit', click: audit ,icon:'bluebook' },
                        { line:true },
                        {text: '取消申领仓库（<u>U</u>）', id:'unaudit', click: unaudit ,icon:'bookpen' },
                        { line:true },
    					{ text: '删除（<u>S</u>）', id:'delete', click: delete_inv, icon:'delete' },
    	                { line:true },
    	                { text: '全部删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
						{ line:true }
    				]},
    				 onDblClickRow : function (rowdata, rowindex, value)
    				{
						 alert(0)
    				}, 
    				/*onSelectRow : function (data, rowindex, rowobj)
                    {
    					if(!$("input[name='"+data.__id+"']").attr("checked")){
                        	$("input[name='"+data.__id+"']").attr("checked",true);
                        }
                    },
                    onUnSelectRow : function (data, rowindex, rowobj)
                    {
                        if($("input[name='"+data.__id+"']").attr("checked")){
                        	$("input[name='"+data.__id+"']").attr("checked",false);
                        }
                    }  */
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function f_onCheckAllRow(checked)
    { 
        for (var rowid in this.records)
        {
            if(checked)
                addCheckedCustomer(this.records[rowid]['inv_id']+"@"+liger.get("store_id").getValue().split(",")[0]+"@"+(this.records[rowid]['location_id'] ? this.records[rowid]['location_id']:"-1"));
            else
                removeCheckedCustomer(this.records[rowid]['inv_id']+"@"+liger.get("store_id").getValue().split(",")[0]+"@"+(this.records[rowid]['location_id'] ? this.records[rowid]['location_id']:"-1"));
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
        if(findCheckedCustomer(CustomerID) == -1){
        	 checkedCustomer.push(CustomerID);
        }
           
    }
    function removeCheckedCustomer(CustomerID)
    {
        var i = findCheckedCustomer(CustomerID);
        if(i==-1) return;
        checkedCustomer.splice(i,1);
    }
    function f_isChecked(rowdata){
        if (findCheckedCustomer(rowdata.inv_id+"@"+liger.get("store_id").getValue().split(",")[0]+"@"+(rowdata.location_id?rowdata.location_id:"-1"))== -1)
            return false;
        return true;
    }
    function f_onCheckRow(checked, data)
    { 
        if (checked){
        	
        	addCheckedCustomer(data.inv_id+"@"+liger.get("store_id").getValue().split(",")[0]+"@"+(data.location_id?data.location_id:"-1"));
     
        }else {
        	removeCheckedCustomer(data.inv_id+"@"+liger.get("store_id").getValue().split(",")[0]+"@"+(data.location_id?data.location_id:"-1"));
        }
    }
    function f_getChecked()
    {
        alert(checkedCustomer.join(','));
    }
    
    
    
    //存在对应关系则选中
    function getCheck(rowdata){
    	//if(typeof(rowdata.store_id) =="number"){
          //  return true;
    	//}
        return false;
    }
    
    //添加药品
    function addInv(){
    	var paras = "store_id="+liger.get("store_id").getValue().split(",")[0]
    	+"&"+ "store_name="+liger.get("store_id").getText(); 
    	
 		$.ligerDialog.open({
 			url: 'medAddInvPage.do?isCheck=false&'+ paras.toString(),height: 500,width: 1000, 
 			title:'添加药品',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
 		});
    }
    
    //删除
    function delete_inv(){
		if (checkedCustomer.length==0){
 			  $.ligerDialog.warn("请选择要删除的药品");
 		}else{
 			//alert(JSON.stringify(checkedCustomer));
			$.ligerDialog.confirm('确定删除?',function(yes){
	 			 ajaxJsonObjectByUrl("addMedStoreInvNew.do?isCheck=true", {ParamVo : checkedCustomer.toString()},function(responseData){
	 	         	if(responseData.state=="true"){
	 	            	checkedCustomer=[];
	 	            	query();
	 	            }
				});
 	        });
		}
    }
    //选择药品
    function selectMedInv(){
    	var paras = "store_id="+liger.get("store_id").getValue().split(",")[0]
    	+"&"+ "store_name="+liger.get("store_id").getText(); 
    	
    	
 		$.ligerDialog.open({url: 'medInvPage.do?isCheck=false&'+ paras.toString(),height: 500,width: 1000, 
 				title:'选择 药品',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
 					});
 		
	}
    //全部保存
 	function addAll(){
 		var data = grid.getData();
 		var ParamVo =[];
 		store_id = liger.get("store_id").getValue().split(",")[0];
        $(data).each(function (){					
				ParamVo.push(
				this.group_id   +"@"+ 
				this.hos_id   +"@"+ 
				this.copy_code   +"@"+ 
				this.store_id   +"@"+
				this.inv_id +"@" + store_id
				) });
 		        ajaxJsonObjectByUrl("addMedStoreInvAll.do?isCheck=true",{ParamVo : ParamVo.toString()},function(responseData){
 		            if(responseData.state=="true"){
 		            	query();
 		            }
 		        });
	}
    
    
    //删除
    function remove(){
 		var ParamVo =[];
 		store_id = liger.get("store_id").getValue().split(",")[0];
  					
		ParamVo.push( 'null' +"@"+ 'null' +"@"+ 'null' +"@"+ store_id +"@"+ 'null' ) ;
        $.ligerDialog.confirm('确定删除?', function (yes){
         	if(yes){
             	ajaxJsonObjectByUrl("deleteMedStoreInv.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
             		if(responseData.state=="true"){
             			query();
             		}
             	});
         	}
        }); 
    }
   
    function openUpdate(obj){
    	
		var vo = obj.split(",");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"store_id="+vo[3]   +"&"+ 
			"inv_id="+vo[4] +"&"+ 
 			"location_id="+ (vo[5] == "undefined" || vo[5] == "null" ?"":vo[5]);  
		$.ligerDialog.open({url: 'medStoreInvUpdatePage.do?isCheck=false&'+ parm.toString(),height: 300,width: 400, 
				title:'维护货位',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				buttons: [ 
						    { text: '保存', 
							onclick: function (item, dialog) { 
									dialog.frame.addStoreInv(); 
								},
							cls:'l-dialog-btn-highlight' }, 
							{ text: '取消', 
							onclick: function (item, dialog) { 
								dialog.close(); 
							}} 
						] 
					});
		
    } 
    
    function getStore_id(){
    	store_id = liger.get("store_id").getValue().split(",")[0];
    	//query();
    }
    
    //字典下拉框
    function loadDict(){
        autocomplete("#store_id","../../../../queryMedStore.do?isCheck=false","id","text",true,true,'',true,'','160');
        autocomplete("#med_type_id","../../../../queryMedTypeDictCode.do?isCheck=false","id","text",true,true,'',false,'','200');
        autoCompleteByData("#is_apply", yes_or_no.Rows, "id", "text", true, true);//是否已设置申领仓库
        autoCompleteByData("#is_app", yes_or_no.Rows, "id", "text", true, true);//是否申领
        autocomplete("#fac_code", "../../../../queryHosFacDict.do?isCheck=false", "id", "text", true, true);
        $("#inv_code").ligerTextBox({width:160});
        $("#inv_model").ligerTextBox({width:160});
    }
    
    //设置申领仓库
	function audit(){
		var data = gridManager.getCheckedRows();
		store_id = liger.get("store_id").getValue().split(",")[0];
		
        if (data.length == 0){
        	$.ligerDialog.warn('请选择药品');
        	return;
        }else{
            var ParamVo = [];
            var inv_ids = '';
            var msg = '';
            $.each(data,function(i,v){
            	
            	/* if(v.is_apply == '1'){
            		msg = '请选择未设置申领库房的药品';
            		return ;
            	} */
            	
            	ParamVo.push(
            		store_id + "@" +  v.inv_id 
            	);
            	
            	inv_ids += v.inv_id + ',';
            });
            
           /*  if(msg != ''){
            	$.ligerDialog.warn(msg);
            	return ; 
            } */
            
            inv_ids = inv_ids.substring(0,inv_ids.length-1),
            //查询药品申领库房
            ajaxJsonObjectByUrl("queryMedInvApplyStore.do?isCheck=false&store_id="+store_id+"&inv_ids=" + inv_ids,{},
                	function (responseData){
                        if(responseData.state=="false"){
                        	$.ligerDialog.warn('已设置申领库房的药品不能重复设置');
                        	return ; 
                        }else{
                        	$.ligerDialog.confirm('确定设置申领库房吗?', 
                                    function (yes){
                                    	if(yes){
                                        	ajaxJsonObjectByUrl("setApplyStore.do?isCheck=false",{ParamVo : ParamVo.toString()},
                                            function (responseData){
                        	                	if(responseData.state=="true"){
                        	                    	query();
                        	                    }
                                            });
                                        }else{
                                            return;
                                        }
                                     }
                            );
                        }
                	}
            );
        }
	}
	//取消申领仓库
	function unaudit(){
		var data = gridManager.getCheckedRows();
		store_id = liger.get("store_id").getValue().split(",")[0];
        if (data.length == 0){
        	$.ligerDialog.warn('请选择药品');
        	return;
        }else{
            var ParamVo = [];
            var msg = '';
            $.each(data,function(i,v){
            	
            	/* if(v.is_apply != '1'){
            		msg = '请选择已设置申领库房的药品';
            		return ;
            	} */
            	
            	ParamVo.push(
            		store_id + "@" +  v.inv_id 
            	);
            });
            
            if(msg != ''){
            	$.ligerDialog.warn(msg);
            	return ; 
            }
            
            $.ligerDialog.confirm('确定取消申领库房吗?', 
            	function (yes){
                   	if(yes){
                        ajaxJsonObjectByUrl("cancelSetApplyStore.do?isCheck=false",{ParamVo : ParamVo.toString()},
                        	function (responseData){
	                           	if(responseData.state=="true"){
	                           		query();
	                          	}
                        	});
                    }else{
                    	return;
                   	}
            	}); 
        }
	}
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', addAll);
		hotkeys('D', remove);
    }
    
    //仓库信息改变事件 重新查询
	$(function () { 
		$("#store_id").change(function () { 
			query();
		}) 
	}); 

    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
    	<tr>
         	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>仓库信息<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="store_id" type="text" id="store_id" ltype="text" onChange="getStore_id()" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>药品类别:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="med_type_id" type="text" id="med_type_id" ltype="text" validate="{maxlength:20}" />
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>药品信息:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{maxlength:20}" />
            </td>
            <td align="left"></td>
            
        </tr>
        
      	<tr>
      		<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否已设置申领仓库:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="is_apply" type="text" id="is_apply" ltype="text" validate="{maxlength:20}" />
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否申领:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="is_app" type="text" id="is_app" ltype="text" validate="{maxlength:20}" />
            </td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  >
                    	生产厂商：
              </td>
             <td align="left" class="l-table-edit-td">
                    <input name="fac_code" type="text" id="fac_code" ltype="text" validate="{required:true,maxlength:20}" />
             </td>
               <td align="right" class="l-table-edit-td"  >
                		规格型号：
                	</td>
                    <td align="left" class="l-table-edit-td">
                    	<input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:true,maxlength:200}" />
                    </td>
      	</tr>
    </table>

	<div id="maingrid"></div>
</body>
</html>
