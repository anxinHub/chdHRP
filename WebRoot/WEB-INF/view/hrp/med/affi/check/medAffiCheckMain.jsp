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
<script src="<%=path%>/lib/hrp/med/med.js" type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
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
		grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name:'create_date_start',value:$("#create_date_start").val()}); 
		grid.options.parms.push({name:'create_date_end',value:$("#create_date_end").val()}); 
		grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split(",")[0]}); 
		
		grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
		grid.options.parms.push({name:'emp_id',value:liger.get("emp_id").getValue()}); 
		grid.options.parms.push({
			name : 'check_code',
			value : $("#check_code").val()
		}); 

		
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					{ display: '盘点单号', name: 'check_code', align: 'left',
						 render : function(rowdata, rowindex, value) {
							return '<a href=javascript:openUpdate("' 
								+ rowdata.group_id
								+ ',' + rowdata.hos_id 
								+ ',' + rowdata.copy_code
								+ ',' + rowdata.check_id
								+ '")>'+rowdata.check_code+'</a>';
						},width : 110	 
					},
					{ display: '摘要', name: 'brif', align: 'left'},
					{ display: '仓库', name: 'store_name', align: 'left'},
					{ display: '经手人', name: 'emp_name', align: 'left'},
					{ display: '制单日期', name: 'create_date', align: 'left'},
					{ display: '制单人', name: 'maker_user_name', align: 'left'},
					{ display: '审核日期', name: 'check_date', align: 'left'},
					{ display: '审核人', name: 'checker_user_name', align: 'left'},
					{ display: '状态', name: 'state', align: 'left',render: fieldTypeRender},
					{ display: '盘盈单号', name: 'in_no', align: 'left',width : 150,
                   	 render : function(rowdata, rowindex, value) {
                   		 var result = value == '' || value == null ? ''  :value ;
	     						return "<a href=javascript:openInMain('"
	     							+ rowdata.group_id
	     							+ "," + rowdata.hos_id 
	     							+ "," + rowdata.copy_code
	     							+ "," + rowdata.in_id
	     							+ "')>"+ result +"</a>";
	     				}	
                    },
                    { display: '盘亏单号', name: 'out_no', align: 'left',width : 150,
                   	 render : function(rowdata, rowindex, value) {
                   		 var result = value == '' || value == null ? ''  :value ;
	     						return "<a href=javascript:openOutMain('"
	     							+ rowdata.group_id
	     							+ "," + rowdata.hos_id 
	     							+ "," + rowdata.copy_code
	     							+ "," + rowdata.out_id
	     							+ "')>"+ result +"</a>";
	     				}			 
                    }
					],
                     dataAction: 'server',dataType: 'server',usePager:true,
                     url:'queryMedAffiCheckMain.do?isCheck=true',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     					{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     					{ line:true },
				    					{ text: '添加（<u>N</u>）', id:'add', click: add_open, icon:'add' },
										{ line:true },
										{ text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
				    	                { line:true },
										{ text: '审核（<u>A</u>）', id:'audit', click: audit,icon:'audit' },
										{ line:true },
										{ text: '消审（<u>U</u>）', id:'unaudit', click: unAudit,icon:'unaudit' },
										{ line:true },
										{ text: '生成出入库单（<u>G</u>）', id:'createInOut', click: createInOut,icon:'account' },
										//{ line:true },
										//{ text: '模板打印（<u>P</u>）', id:'print', click: printDate,icon:'print' }
										{ line:true },
										{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
										{ line:true } ,
										{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' }
				    				]}
    		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    //字段类型渲染器
    function fieldTypeRender(r, i, value){
        for (var i = 0, l = medCheckMain_state.Rows.length; i < l; i++){
            var o = medCheckMain_state.Rows[i];
            if (o.id == r.state){
            	return o.text;
            }
        }
        return "未确认";
    }
    //添加
    function add_open(){
    	parent.$.ligerDialog.open({
			title: '代销药品盘点单添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/affi/check/medAffiCheckMainAddPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
    	
    	
    	
    }
    //删除
    function remove(){

		var data = gridManager.getCheckedRows();		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要删除的数据');
			return;
		} else {
			var ParamVo = [];
			var nos = "";
			$(data).each(
				function() {
					if(this.state > 1){nos = nos + this.check_code + ",";}
						ParamVo.push(
							this.hos_id + "@" + 
							this.group_id + "@"+ 
							this.copy_code + "@" +
							this.check_id
						)
				});
			if(nos != ""){
				$.ligerDialog.error("删除失败！"+nos+"单据不是未审核状态");
				return;
			}
			
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteMedAffiCheckMain.do?isCheck=true", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    //审核
    function audit(){
		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要审核的数据');
			return;
		} else {
			var ParamVo = [];
			var nos = "";
			$(data).each(
					function() {
						if(this.state != 1){nos = nos + this.check_code + "<br>";}
						ParamVo.push(
								this.hos_id + "@" + 
								this.group_id + "@"+ 
								this.copy_code + "@" +
								this.check_id
								)
			});
			if(nos != ""){
				$.ligerDialog.error("审核失败！"+nos+"单据不是未审核状态");
				return;
			}
			$.ligerDialog.confirm('确定审核?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("auditMedAffiCheckMain.do?isCheck=true", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    //销审
    function unAudit(){

		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要消审的数据');
			return;
		} else {
			var ParamVo = [];
			var nos = "";
			$(data).each(
					function() {
						if(this.state != 2){nos = nos + this.check_code + "<br>";}
						ParamVo.push(
								this.hos_id + "@" + 
								this.group_id + "@"+ 
								this.copy_code + "@" +
								this.check_id
								)
			});
			if(nos != ""){
				$.ligerDialog.error("消审失败！"+nos+"单据不是审核状态");
				return;
			}
			$.ligerDialog.confirm('确定消审?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("unAuditMedAffiCheckMain.do?isCheck=true", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	//生成出入库单
    function createInOut(){

		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要消审的数据');
			return;
		} else {
			var ParamVo = [];
			var nos = "";
			$(data).each(
					function() {
						if(this.state != 2){nos = nos + this.check_code + "<br>";}
						ParamVo.push(
								this.hos_id + "@" + 
								this.group_id + "@"+ 
								this.copy_code + "@" +
								this.check_id
								)
			});
			if(nos != ""){
				$.ligerDialog.error("生成出入库单失败！"+nos+"单据不是审核状态");
				return;
			}
			$.ligerDialog.confirm('确定生成出入库单?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("createMedAffiInOut.do?isCheck=true", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    //修改
	function openUpdate(obj) {
		var voStr = obj.split(",");
		var paras = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ " copy_code=" + voStr[2].toString() + "&" 
			+ "check_id=" + voStr[3].toString();
		parent.$.ligerDialog.open({
			title: '代销药品盘点单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/affi/check/medAffiCheckMainUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});  
		/* var index = layer.open({
			type : 2,
			title : '',
			shadeClose : false,
			shade : false,
			//maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'medAffiCheckMainUpdatePage.do?isCheck=false&' + parm.toString()
		});
		layer.full(index); */

	}
    
	//打开盘盈单
	function openInMain(obj){
		var voStr = obj.split(",");
		var paras = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ " copy_code=" + voStr[2].toString() + "&" 
			+ "in_id=" + voStr[3].toString();
		
		parent.$.ligerDialog.open({
			title: '代销盘盈单',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/affi/check/medAffiInPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
	//打开盘亏单
	function openOutMain(obj){
		var voStr = obj.split(",");
		var paras = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ " copy_code=" + voStr[2].toString() + "&" 
			+ "out_id=" + voStr[3].toString();
		
		parent.$.ligerDialog.open({
			title: '代销盘亏单',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/affi/check/medAffiOutPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
    
    
    
	function loadDict() {
		//字典下拉框
		autocompleteAsync("#store_id", "../../queryMedStore.do?isCheck=false", "id","text", true, true, {is_com : '1'}, true, false, '180');
		$("#create_date_start").ligerTextBox({width : 100});       
		$("#create_date_end").ligerTextBox({width : 100});
		autodate("#create_date_start", "yyyy-mm-dd", "month_first");
        autodate("#create_date_end", "yyyy-mm-dd", "month_last");
		autocomplete("#emp_id", "../../queryMedEmp.do?isCheck=false", "id","text", true, true, "", false, false, '180');
		autoCompleteByData("#state", medCheckMain_state.Rows, "id", "text",true, true);
		$("#check_code").ligerTextBox({width : 238});
	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('N', add_open);
		hotkeys('D', remove);
		hotkeys('A', audit);
		hotkeys('U', unAudit);
		hotkeys('G', createInOut);
		hotkeys('P', print);
	}

	//打印设置
	function printSet(){
		
		var useId=0;//统一打印
		
		officeFormTemplate({template_code:"08022",use_id:useId});
	}
	 
    //打印
    function print(){
    	
		var useId=0;//统一打印

		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			
			var check_id ="" ;
			var check_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					check_nos = check_nos + this.check_no + "<br>";
				}
				
				check_id  += this.check_id+","
					
			});
			 var para={
	    			paraId :check_id.substring(0,check_id.length-1) ,
	    			template_code:'08022',
	    			class_name:"com.chd.hrp.med.serviceImpl.affi.check.MedAffiCheckMainServiceImpl",
	    			method_name:"queryMedAffiCheckByPrintTemlate1",
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    	}; 
		 	
			officeFormPrint(para);
		}
    }
    
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit"  width="100%" border='0'>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单日期：</td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" >
							<input class="Wdate" name="create_date_start" id="create_date_start" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="create_date_end" id="create_date_end" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
	            	</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>仓  库：</td>
			<td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" /></td>
			           
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">状   态：</td>
			<td align="left" class="l-table-edit-td"><input name="state" type="text" id="state" ltype="text" /></td>
		</tr> 
		<tr>
			<td align="right" class="l-table-edit-td" >
				单据号：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="check_code" type="text" id="check_code" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">经 手 人：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="emp_id" type="text" id="emp_id" ltype="text" />
			</td>
			
			<td align="left"></td>
			<td align="left"></td>
		</tr> 
	</table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr>
                <th width="200">checkId</th>	
                <th width="200">checkCode</th>	
                <th width="200">storeId</th>	
                <th width="200">storeNo</th>	
                <th width="200">deptId</th>	
                <th width="200">deptNo</th>	
                <th width="200">checkDate</th>	
                <th width="200">empId</th>	
                <th width="200">maker</th>	
                <th width="200">checker</th>	
                <th width="200">state</th>	
                <th width="200">brif</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
