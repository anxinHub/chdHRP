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
        $("#inv_name").ligerTextBox({width:160});
        $("#mat_type_id").ligerTextBox({width:160});
        $("#inv_model").ligerTextBox({width:160});
        $("#fac_id").ligerTextBox({width:160});
        $("#state").ligerTextBox({width:160});
        $("#check_date").ligerTextBox({width:160});
        $("#checker").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
          //根据表字段进行添加查询条件
    	  //grid.options.parms.push({name:'inv_code',value:$("#inv_code").val()}); 
    	  grid.options.parms.push({name:'inv_name',value:$("#inv_name").val()}); 
    	  grid.options.parms.push({name:'mat_type_id',value:liger.get("mat_type_id").getValue() == null ? "" : liger.get("mat_type_id").getValue().split(",")[0]}); 
    	  grid.options.parms.push({name:'inv_model',value:$("#inv_model").val()}); 
    	  grid.options.parms.push({name:'fac_id',value:liger.get("fac_id").getValue() == null ? "" : liger.get("fac_id").getValue().split(",")[0]}); 
    	  grid.options.parms.push({name:'state',value:$("#state").val()}); 
    	  grid.options.parms.push({name:'sup_id',value:liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[0]});
    	  grid.options.parms.push({name:'check_date',value:$("#check_date").val()}); 
    	  grid.options.parms.push({name:'checker',value:$("#checker").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	/* if($("#inv_code").val()!=""){
                		return rowdata.inv_code.indexOf($("#inv_code").val()) > -1;	
                	} */
                	if($("#inv_name").val()!=""){
                		return rowdata.inv_name.indexOf($("#inv_name").val()) > -1;	
                	}
                	if($("#mat_type_id").val()!=""){
                		return rowdata.mat_type_id.indexOf($("#mat_type_id").val()) > -1;	
                	}
                	if($("#inv_model").val()!=""){
                		return rowdata.inv_model.indexOf($("#inv_model").val()) > -1;	
                	}
                	if($("#fac_id").val()!=""){
                		return rowdata.fac_id.indexOf($("#fac_id").val()) > -1;	
                	}
                	if($("#state").val()!=""){
                		return rowdata.state.indexOf($("#state").val()) > -1;	
                	}
                	if($("#check_date").val()!=""){
                		return rowdata.check_date.indexOf($("#check_date").val()) > -1;	
                	}
                	if($("#checker").val()!=""){
                		return rowdata.checker.indexOf($("#checker").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                    /*  { display: '物资材料编码', name: 'inv_code', align: 'left'
					 		}, */
                     { display: '物资材料名称', name: 'inv_name', align: 'left',width: '120'
					 		},
                     { display: '别名', name: 'alias', align: 'left',width: '120'
					 		},
                     { display: '物资类别', name: 'mat_type_name', align: 'left',width: '120'
					 		},
                     { display: '规格型号', name: 'inv_model', align: 'left',width: '80'
					 		},
                     { display: '计量单位', name: 'unit_name', align: 'left',width: '80'
					 		},
		             { display: '供应商', name: 'sup_name', align: 'left',width: '150'
							 		},
                     { display: '生产厂商', name: 'fac_name', align: 'left',width: '200'
					 		},
                     { display: '计划价', name: 'plan_price', align: 'left',width: '80'
					 		},
                     { display: '启用状态', name: 'state', align: 'left',width: '80',
					 			render:function(rowdata){
									if(rowdata.state == 1){
										return "启用";
									}else {
										return "未启用";
									}
								}
					 		},
                     { display: '启用日期', name: 'check_date', align: 'left',width: '80'
					 		},
                     { display: '审核人', name: 'checker_name', align: 'left',width: '80'
					 		} 
					 		, { display: '品牌', name: 'brand_name', align: 'left',width: '80'
					 		},
		                     { display: '备注', name: 'note', align: 'left',width: '80'
							 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'querySupMatInv.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true }/* ,
				    					{ text: '审核（<u>A</u>）', id:'add', click: audit_add_open, icon:'add' },
				    	                { line:true } */
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						/* openAuditUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.inv_id 
							); */
							audit_add_open(rowdata.group_id   + "|" + 
									rowdata.hos_id   + "|" + 
									rowdata.copy_code   + "|" + 
									rowdata.inv_id +"|"+
									rowdata.hrp_inv_id);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
		$.ligerDialog.open({ url : 'supMatInvAddPage.do?isCheck=false&',data:{}, height: 300,width: 900, title:'物资材料',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveSupMatInv(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
    }
    function audit_add_open(obj){
    	
    	var vo = obj.split("|");
		if(typeof(vo[4])!="undefined" && vo[4]!=null && vo[4]!="" &&  vo[4]!="null"){ 
			openAuditUpdate(obj); 
		} 
		else{
			
			var parm = 
				"group_id="+vo[0]   +"&"+ 
				"hos_id="+vo[1]   +"&"+ 
				"copy_code="+vo[2]   +"&"+ 
				"inv_id="+vo[3] ;
			
			parent.$.ligerDialog.open({
				title: '供应商录入材料审核',
				height: 550,
				width: 1150,
				url: 'hrp/sup/supmatinv/supMatInvAuditAddPage.do?isCheck=false&'+parm ,
				modal: true, showToggle: false, showMax: true, showMin:false, isResize: true, top : 1,
				parentframename: window.name,
				buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' },
				           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
				          ]
			});
			
		}
    }
    function audit_open(){
    	
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
				this.inv_id 
				) });
            $.ligerDialog.confirm('确定审核?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("auditSupMatInv.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
		
    	/*var index = layer.open({
					type : 2,
					title : '100003 物资材料表',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'supMatInvAddPage.do?isCheck=false'
				});
				layer.full(index);
				*/
    	}
    	
    function remove(){
    	
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
							this.inv_id 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteSupMatInv.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
   
    function openUpdate(obj){
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"inv_id="+vo[3] 
		 
		 
		 $.ligerDialog.open({ url : 'supMatInvUpdatePage.do?isCheck=false&'+parm,data:{}, height: 300,width: 900, title:'100003 物资材料表',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveSupMatInv(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
		 
    /*var index = layer.open({
					type : 2,
					title : '更新',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'supMatInvUpdatePage.do?isCheck=false&' + parm
				});
				layer.full(index);	
    */
    }
    function openAuditUpdate(obj){
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"inv_id="+vo[4] //HRP的材料id
		
		parent.$.ligerDialog.open({
			title: '供应商录入材料审核',
			height: 550,
			width: 1150,
			url: 'hrp/sup/supmatinv/supMatInvAuditUpdatePage.do?isCheck=false&'+parm ,
			modal: true, showToggle: false, showMax: true, showMin:false, isResize: true, top : 1,
			parentframename: window.name,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
		});
		
		
		 
    /*var index = layer.open({
					type : 2,
					title : '更新',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'supMatInvUpdatePage.do?isCheck=false&' + parm
				});
				layer.full(index);	
    */
    }
    function loadDict(){
            //字典下拉框
		autocomplete("#mat_type_id", "../../mat/queryMatTypeDict.do?isCheck=false", "id", "text", true, true, {is_last : '1'});
		
		autocomplete("#fac_id", "../../mat/queryHosFacDict.do?isCheck=false", "id", "text", true, true,"","","",400,"",400);
		autocomplete("#state", "../../mat/queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#sup_id", "../../mat/queryHosSupDict.do?isCheck=false", "id", "text", true, true,"","","","","",200);

         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

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
            <!-- <td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资材料编码：</td>
            <td align="left" class="l-table-edit-td"><input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td> -->
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资材料：</td>
            <td align="left" class="l-table-edit-td"><input name="inv_name" type="text" id="inv_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资类别：</td>
            <td align="left" class="l-table-edit-td"><input name="mat_type_id" type="text" id="mat_type_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">规格型号：</td>
            <td align="left" class="l-table-edit-td"><input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
           
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">生产厂商：</td>
            <td align="left" class="l-table-edit-td"><input name="fac_id" type="text" id="fac_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否启用：</td>
            <td align="left" class="l-table-edit-td"><input name="state" type="text" id="state" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  >
                    	供应商：
                    </td>
                    <td align="left" class="l-table-edit-td">
                    	<input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:20}" />
                    </td>
            
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
