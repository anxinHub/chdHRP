<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
  <head> 
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
     var grid;
     var gridManager = null; 
     
     $(function (){

        loadDict();
        loadHead();	
		
     });  
     
	function  save(){
		grid.endEdit();
		//主表
 		
 		//处理明细
 		var maintain_detail_data = grid.getData();     	 
 		var insert_maintain_detail_data=[];
 		if(maintain_detail_data.length > 0){
 			$(maintain_detail_data).each(function(i,v){
 				v.pkg_quality=$("#pkg_quality"+i).val();
 				v.app_quality=$("#app_quality"+i).val();
 				v.mt_quality=$("#mt_quality"+i).val();
 				v.cold_quality=$("#cold_quality"+i).val();
 				insert_maintain_detail_data.push(v);
 			})
 		}else{
  			$.ligerDialog.warn('没有明细材料，不能保存！');  			
  			return false;
  		} 
        var formPara={
 				
        		mt_id:'${mt_id}',
        		brief:liger.get("store_id").getValue(),
        		brief_db:'${brief}',
        		maintain_detail_data:JSON.stringify(insert_maintain_detail_data)
         };
        ajaxJsonObjectByUrl("updateMatMaintainMainAndDetail.do?isCheck=false",formPara,function(responseData){
            if(responseData.state=="true"){
            	parentFrameUse().query();
       			parentFrameUse().openUpdate(responseData.update_para);
       			this_close();
            }
        });
    }
     

   function saveMaintainMainAndDetail(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
        //字典下拉框
    	$("#create_date").val("${make_date}");
    	$("#mt_no").val("${mt_no}");
    	autocompleteAsync("#store_id", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_write:1},true,false,'180');
    	liger.get("store_id").setValue("${store_id},${store_no}");
    	liger.get("store_id").setDisabled();
    	autocompleteAsync("#mat_type_code", "../../queryMatTypeByStoreID.do?isCheck=false", "id", "text", true, true, {store_id:"${store_id}"},false,false,'180');
    	$("#inv_code").ligerTextBox({width:180});
    } 
    
    //查询
    function  query(){
		grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name:'inv_code',value:$("#inv_code").val()});
		grid.options.parms.push({name:'mat_type_code',value:liger.get("mat_type_code").getValue().split(",")[2]});
		grid.options.parms.push({name:'mt_id',value:"${mt_id}"});
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

 	//关闭
	function this_close(){
 		frameElement.dialog.close();
 	}

    
    function this_refresh(){
        grid.reload();
    }
    
    function loadHead(){
    	var para = {
    			mt_id : '${mt_id}'
    		};
    	grid = $("#maingrid").ligerGrid({
           columns: [   
                     { display: '材料编码', name: 'inv_code', align: 'left',width:100,frozen:true},
                     { display: '材料名称', name : 'inv_name', align: 'left',width:140,frozen:true},
                     { display: '材料id', name : 'inv_id', align: 'left',width:240,hide:true},
                     { display: '单位', name: 'unit_name', align: 'left',width:60,frozen:true},
                     { display: '规格', name: 'inv_model', align: 'left',width:120,frozen:true},
                     { display: '生产厂家', name: 'fac_name', align: 'left',width:200,frozen:true},
                     { display: '批号', name: 'batch_no', align: 'left',width:100,frozen:true},
                     { display: '库存数量', name: 'store_amount', align: 'left',width:60},
                     { display: '养护数量', name: 'mt_amount', align: 'left',width:60, editor : {type : 'float'}},
                     { display: '库存效期', name: 'store_inva_date', align: 'left',width:80,type: 'date',formatter: 'yyyy-MM-dd'},
                     { display: '养护效期', name: 'mt_inva_date', align: 'left',width:80,type: 'date',formatter: 'yyyy-MM-dd',editor:{type:'date',format:'yyyy-MM-dd'}},
                     { display: '货位', name: 'location_name', align: 'left',width:60},
                     { display: '货位id', name: 'location_id', align: 'left',width:60,hide:true},
                     { display: '包装情况', name: 'pkg_quality', align: 'left',width:80,	reg:"1=合格,2=不合格",
                    	 render : function(rowdata, rowindex,value) {
							var str = "";
							var obj = eval('[' + rowdata.para_json + ']'); 
							var option = "";
							str = "<select id='pkg_quality"+rowindex+"'  name = 'pkg_quality"+rowindex+"' style='margin-top:5px;width:150;'>";
							str = str+"<option value='1' "+(value==1?'selected':'')+">合格</option>";
							str = str+"<option value='2' "+(value==2?'selected':'')+">不合格</option>";
							str = str + "</select>";
							return str;
						}
				     },
                     { display: '外观情况', name: 'app_quality', align: 'left',width:80,reg:"1=合格,2=不合格",
                    	 render : function(rowdata, rowindex,value) {
 							var str = "";
 							var obj = eval('[' + rowdata.para_json + ']'); 
 							var option = "";
 							str = "<select id='app_quality"+rowindex+"'  name = 'app_quality"+rowindex+"' style='margin-top:5px;width:150;'>";
 							str = str+"<option value='1' "+(value==1?'selected':'')+">合格</option>";
 							str = str+"<option value='2' "+(value==2?'selected':'')+">不合格</option>";
 							str = str + "</select>";
 							return str;
 						}
				     },
                     { display: '质量情况', name: 'mt_quality', align: 'left',width:80,reg:"1=合格,2=不合格",
                    	 render : function(rowdata, rowindex,value) {
  							var str = "";
  							var obj = eval('[' + rowdata.para_json + ']'); 
  							var option = "";
  							str = "<select id='mt_quality"+rowindex+"'  name = 'mt_quality"+rowindex+"' style='margin-top:5px;width:150;'>";
  							str = str+"<option value='1' "+(value==1?'selected':'')+">合格</option>";
  							str = str+"<option value='2' "+(value==2?'selected':'')+">不合格</option>";
  							str = str + "</select>";
  							return str;
  						}
				     },
                     { display: '冷链情况', name: 'cold_quality', align: 'left',width:80,reg:"1=合格,2=不合格",
                    	 render : function(rowdata, rowindex,value) {
  							var str = "";
  							var obj = eval('[' + rowdata.para_json + ']'); 
  							var option = "";
  							str = "<select id='cold_quality"+rowindex+"'  name = 'cold_quality"+rowindex+"' style='margin-top:5px;width:150;'>";
  							str = str+"<option value='1' "+(value==1?'selected':'')+">合格</option>";
  							str = str+"<option value='2' "+(value==2?'selected':'')+">不合格</option>";
  							str = str + "</select>";  
  							return str;
  						}
				     },
                     { display: '处理意见',name : 'suggestion', align: 'left',width:180,editor : {type : 'text'}},
                     { display: '证件号',name : 'cert_code', align: 'left',width:180},
                     { display: '备注',name : 'remark', align: 'left',width:180,editor : {type : 'text'}}
                    ],
                    dataAction: 'server',dataType: 'server',usePager:false,url:'queryMatMaintainDetailByMtID.do?isCheck=false',parms :para,
                    width : '100%', height : '100%', delayLoad:false,enabledEdit : true, rownumbers:true,isAddRow:false,
					toolbar: { items: [
									    { text: '查询', id:'query', click: query,icon:'search' },
										{ line:true },
				                     	{ text: '保存', id:'add', click: saveMaintainMainAndDetail,icon:'add' },
				                     	{ line:true },
				                     	{ text : '打印',id : 'print',click : print,icon : 'print'},
				                     	{ line : true},
				                     	{ text: '关闭', id:'colse', click: this_close,icon:'close' }
				]}
			});

        gridManager = $("#maingrid").ligerGetGridManager();

        
    }
    
	//打印
	function print() {
		var time = new Date(); //获得当前时间
		var year = time.getFullYear();//获得年、月、日
		var month = time.getMonth() + 1;
		var day = time.getDate(); 
		var date = year + "年" + month + "月" + day + "日";
		if (grid.getData().length <= 0) {
			$.ligerDialog.error("请先查询数据！");
			return;
		}
		var heads = {
				"isAuto" : true,//系统默认，页眉显示页码
				"rows" : [{"cell" : 0,"value" : "养护日期："},
						  {"cell" : 1,"value" : ""+$("#create_date").val()},
						  {"cell" : 3,"value" : "养护仓库："},
						  {"cell" : 4,"value" : "" + liger.get("store_id").getText().split(" ")[1]},
						  {"cell" : 5,"value" : "备注："},
						  {"cell" : 6,"value" : "" + $("#brief").val(),"colSpan":3}
				         ]
			};

		//表尾
		var foots = {
			rows : [ {"cell" : 0,"value" : "制单日期:"},
			         {"cell" : 1,"value" : date} ]
		};
		var printPara = {
			title : "养护记录表",//标题
			columns : JSON.stringify(grid.getPrintColumns()),//表头
			class_name : "com.chd.hrp.mat.service.maintain.MatMaintainService",
			method_name : "queryMatMaintainDetailByMtIDForPrint",
			bean_name : "matMaintainService",
			heads : JSON.stringify(heads),//表头需要打印的查询条件,可以为空
			foots : JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
			mt_id:"${mt_id}",
			inv_code:$("#inv_code").val()
		};
		
		officeGridPrint(printPara);
		
	}
    

    </script>

  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
  	<input name="is_dir" type="hidden" id="is_dir" />
	<div id="layout1">
		<div position="top">
			<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>养护单号：</td>
			            <td align="left" class="l-table-edit-td"><input name="mt_no" type="text" id="mt_no" disabled="disabled" ltype="text" value="自动生成"/></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>养护日期：</td>
			            <td align="left" class="l-table-edit-td"><input name="create_date" type="text" id="create_date"  disabled="disabled" ltype="text" validate="{required:true,maxlength:20}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资类别：</td>
			            <td align="left" class="l-table-edit-td"><input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			        	<td align="right" class="l-table-edit-td" width="10%">材料信息：</td>
			            <td align="left" class="l-table-edit-td" width="20%">
			            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
			            </td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
			            <td align="left" class="l-table-edit-td">
			            	<textarea name="brief" type="text" id="brief" ltype="text" cols="40" rows="2" class="l-textarea" style="width:400px">${brief }</textarea>
			            </td>
			        </tr>
				</table>
			</form>
		</div>
		<div position="center" >
			<div id="maingrid"></div>
		</div>
	</div>
	
    </body>
</html>
