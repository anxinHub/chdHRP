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
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		query();
		loadHotkeys();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({
			name : 'begin_check_date',
			value : $("#begin_check_date").val()
		});
		grid.options.parms.push({
			name : 'end_check_date',
			value : $("#end_check_date").val()
		});
		grid.options.parms.push({
			name : 'begin_in_date',
			value : $("#begin_in_date").val()
		});
		grid.options.parms.push({
			name : 'end_in_date',
			value : $("#end_in_date").val()
		});
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'deliver_no',
			value : $("#deliver_no").val()
		});
		grid.options.parms.push({
			name : 'origin_no',
			value : $("#origin_no").val()
		});
		grid.options.parms.push({
			name : 'state',
			value : $("#state").val()
		});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
			    {display: '单据号', name: 'deliver_no', align: 'left', width: 130,
						render : function(rowdata, rowindex, value) {
							return '<a href=javascript:update_open("'+ rowdata.group_id	+ ',' + rowdata.hos_id + ',' + rowdata.copy_code + ',' + rowdata.deliver_id	+ ',' + rowdata.deliver_no+'")>'+rowdata.deliver_no+'</a>';
						}
					},
				{display: '原始单号', name: 'origin_no', align: 'left',width:100},
				{display: '仓库', name: 'store_name', align: 'left',width:160},
		 		{display: '供应商', name: 'sup_name', align: 'left',width:240},
		 		{display: '采购员', name: 'stocker_name', align: 'left',width:80},
		 		{display: '入库日期', name: 'confirm_date', align: 'left',width: 80},
		 		{display: '库管员', name: 'confirmer_name', align: 'left',width: 80},
		 		{display: '金额', name: 'amount_money', align: 'right',width: 100 ,
			 			render : function(rowdata, rowindex, value) {
							return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p08005}', 1);
						}
		 			}, 
				{display: '编制日期', name: 'in_date', align: 'left',width: 80}, 
		 		{display: '制单人', name: 'maker_name', align: 'left'	,width: 80},
		 		{display: '审核日期', name: 'check_date', align: 'left',width: 80	},
		 		{display: '审核人', name: 'checker_name', align: 'left',width: 80},
		 		{display: '状态', name: 'state', align: 'left',width: 80,
		 				render: function(rowdata,index,value){
		 					if(rowdata.state == 1){
		 						return "未审核";
		 					}else{
		 						return "审核";
		 					}
		 				}
		 			},
		 		{display: '备注', name: 'brief', align: 'left',width:160}
		 		],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedNopayDeliver.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true,rownumbers:true, forzen:false ,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				{ line:true },
				{ text: '删除（<u>D</u>）', id:'delete', click: remove, icon:'delete' },
				{ line:true }, 
				{ text: '审核（<u>S</u>）', id:'audit', click: audit, icon:'audit' },
				{ line:true }, 
				{ text: '消审（<u>U</u>）', id:'unaudit', click: unAudit, icon:'unaudit' },
				{ line:true },
				{ text: '复制（<u>F</u>）', id:'copy', click: copy, icon:'copy' },
				{ line:true },
				{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
				{ line:true } ,
				{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' }
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('S', audit);
		hotkeys('U', unAudit);
		hotkeys('F', confirm);
		hotkeys('P', print);
	}
    
    function add_open(){
    	
    	$.ligerDialog.open({
			title: '添加期初未付款送货单',
			height: 580,
			width: 1000,
			url: 'medNopayDeliverAddPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true, top: 0
		}); 
	}
    
    function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"deliver_id="+vo[3] +"&"+ 
			"deliver_no="+vo[4] ;
		$.ligerDialog.open({
			title: '期初未付款送货单修改',
			height: 500,
			width: 1000,
			url: 'medNopayDeliverUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true, top : 0,
		});   
    }
    //删除
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var deliver_nos = "";
			$(data).each(function (){		
				if(this.state == 1){
					ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.deliver_id   +"@"+ 
							this.deliver_no 
						) 
				}else{
					deliver_nos = deliver_nos + this.deliver_no + ",";
				}
				
			});
			if(deliver_nos != ""){
				$.ligerDialog.error("删除失败！"+deliver_nos+"单据不是未审核状态,不允许删除");
				return;
			}
			if(ParamVo != null && ParamVo != ''){
				$.ligerDialog.confirm('确定删除?', function (yes){
					if(yes){
						ajaxJsonObjectByUrl("deleteMedNopayDeliver.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								query();
							}
						});
					}
				}); 
			}else{
				$.ligerDialog.error("无删除数据");
			}
			
		}
	}
	
	// 审核
	function audit(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var deliver_nos = "";
			$(data).each(function (){		
				if(this.state != 1){
					deliver_nos = deliver_nos + this.deliver_no + ",";
				}else{
					ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.deliver_id  +"@"+ 
							this.deliver_no  +"@"+ 
							this.state  +"@"+ 3
						) 
				}
			});
			if(deliver_nos != ""){
				$.ligerDialog.error("审核失败！"+deliver_nos+"单据不是未审核状态");
				return;
			}
			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateStateMedNopayDeliver.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	//消审
	function unAudit(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var deliver_nos = "";
			$(data).each(function (){		
				if(this.state != 3){
					deliver_nos = deliver_nos + this.deliver_no + ",";
				}else{
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.deliver_id  +"@"+ 
						this.deliver_no  +"@"+ 
						this.state  +"@"+ 1
					) 
				}
				
			});
			if(deliver_nos != ""){
				$.ligerDialog.error("消审失败！"+deliver_nos+"单据不是已审核确认状态");
				return;
			}
			$.ligerDialog.confirm('确定消审确认吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateStateMedNopayDeliver.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	
	//复制
	 function copy(){
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
						this.deliver_id  +"@"+ 
						this.deliver_no
					) 
				});
				$.ligerDialog.confirm('确定复制?', function (yes){
					if(yes){
						ajaxJsonObjectByUrl("copyMedNopayDeliver.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								query();
							}
						});
					}
				}); 
			}
	 }
    function loadDict(){
		//字典下拉框
		autocomplete("#store_id", "../../queryMedStore.do?isCheck=false", "id", "text", true, true);
		autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',220);
		
	    autodate("#begin_in_date", "yyyy-mm-dd", "month_first");
	    autodate("#end_in_date", "yyyy-mm-dd", "month_last");
        $("#state").ligerTextBox({width:160});
        $("#begin_in_date").ligerTextBox({width:100});
        $("#end_in_date").ligerTextBox({width:100});
        $("#begin_check_date").ligerTextBox({width:100});
        $("#end_check_date").ligerTextBox({width:100});
        $("#deliver_no").ligerTextBox({width:160});
        $("#origin_no").ligerTextBox({width:160});
        $("#sup_id").ligerTextBox({width:220});
	}  

	//打印设置
	function printSet(){
		
		var useId=0;//统一打印
		
		officeFormTemplate({template_code:"08023",use_id:useId});
	}
	 
    //打印
    function print(){
    	
		var useId=0;//统一打印

		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			
			var deliver_id ="" ;
			var deliver_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					deliver_nos = deliver_nos + this.deliver_no + "<br>";
				}
				
				deliver_id  += this.deliver_id+","
					
			});
			 var para={
	    			paraId :deliver_id.substring(0,deliver_id.length-1) ,
	    			template_code:'08023',
	    			class_name:"com.chd.hrp.med.serviceImpl.mednopaydeliver.MedNopayDeliverServiceImpl",
	    			method_name:"queryMedNopaydeliverByPrintTemlate1",
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    	}; 
		 	
			officeFormPrint(para);
		}
    }
   </script>
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
       <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">编制日期：</td>
        	<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_in_date" id="begin_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            			</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_in_date" id="end_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            			</td>
            		</tr>
				</table>
	        </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
             <td align="left" class="l-table-edit-td">
            	<select name="state" id="state"style="width: 135px;" >
            			<option value="">请选择</option>
                		<option value="1">未审核</option>
                		<option value="2">审核</option>
            	</select>
            </td>
            
       </tr>
       <tr> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核日期：</td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_check_date" id="begin_check_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            			</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_check_date" id="end_check_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
           				</td>
            		</tr>
				</table>
	        </td> 
            <td align="left"></td>
           	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">原始单号：</td>
            <td align="left" class="l-table-edit-td"><input name="origin_no" type="text" id="origin_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
           	<td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">单据号：</td>
            <td align="left" class="l-table-edit-td" ><input name="deliver_no" type="text" id="deliver_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,}" /></td>
            
            <td align="left"></td>
        </tr>
    </table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
			<tr>
                <th width="200">单据号</th>	
                <th width="200">原单据号</th>
                <th width="200">仓库</th>	
                <th width="200">供应商</th>	
                <th width="200">采购员</th>	
                <th width="200">入库日期</th>	
                <th width="200">库管员</th>
                <th width="200">金额</th>
                <th width="200">编制日期</th>
                <th width="200">制单人</th>
                <th width="200">审核日期</th>	
                <th width="200">审核人</th>	
                <th width="200">状态</th>
                <th width="200">备注</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
