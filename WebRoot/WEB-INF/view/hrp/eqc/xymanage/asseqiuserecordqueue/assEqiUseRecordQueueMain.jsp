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
        $("#urq_rowid").ligerTextBox({width:160});
        $("#busi_data_source_code").ligerTextBox({width:160});
        $("#urq_exdevicecode").ligerTextBox({width:160});
        $("#urq_exitemcode").ligerTextBox({width:160});
        $("#urq_exitemdcode").ligerTextBox({width:160});
        $("#urq_exbusscode").ligerTextBox({width:160});
        $("#urq_exuseloccode").ligerTextBox({width:160});
        $("#urq_usedate").ligerTextBox({width:160});
        $("#urq_workloadnum").ligerTextBox({width:160});
        $("#urq_price").ligerTextBox({width:160});
        $("#urq_totalfee").ligerTextBox({width:160});
        $("#urq_alonepaynum").ligerTextBox({width:160});
        $("#urq_patientid").ligerTextBox({width:160});
        $("#urq_patientname").ligerTextBox({width:160});
        $("#urq_patientsex").ligerTextBox({width:160});
        $("#urq_patientage").ligerTextBox({width:160});
        $("#urq_startdate").ligerTextBox({width:160});
        $("#urq_starttime").ligerTextBox({width:160});
        $("#urq_enddate").ligerTextBox({width:160});
        $("#urq_endtime").ligerTextBox({width:160});
        $("#urq_operator").ligerTextBox({width:160});
        $("#urq_otherinfo").ligerTextBox({width:160});
        $("#urq_cancelflag").ligerTextBox({width:160});
        $("#urq_remark").ligerTextBox({width:160});
        $("#urq_doctororderid").ligerTextBox({width:160});
        $("#urq_positiveflag").ligerTextBox({width:160});
        $("#urq_sampleno").ligerTextBox({width:160});
        $("#urq_exposurenum").ligerTextBox({width:160});
        $("#urq_adddate").ligerTextBox({width:160});
        $("#urq_addtime").ligerTextBox({width:160});
        $("#urq_finishdate").ligerTextBox({width:160});
        $("#urq_finishtime").ligerTextBox({width:160});
        $("#urq_lastexecutedate").ligerTextBox({width:160});
        $("#urq_lastexecutetime").ligerTextBox({width:160});
        $("#urq_executetimes").ligerTextBox({width:160});
        $("#urq_status").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'urq_rowid',value:$("#urq_rowid").val()}); 
    	  grid.options.parms.push({name:'busi_data_source_code',value:$("#busi_data_source_code").val()}); 
    	  grid.options.parms.push({name:'urq_exdevicecode',value:$("#urq_exdevicecode").val()}); 
    	  grid.options.parms.push({name:'urq_exitemcode',value:$("#urq_exitemcode").val()}); 
    	  grid.options.parms.push({name:'urq_exitemdcode',value:$("#urq_exitemdcode").val()}); 
    	  grid.options.parms.push({name:'urq_exbusscode',value:$("#urq_exbusscode").val()}); 
    	  grid.options.parms.push({name:'urq_exuseloccode',value:$("#urq_exuseloccode").val()}); 
    	  grid.options.parms.push({name:'urq_usedate',value:$("#urq_usedate").val()}); 
    	  grid.options.parms.push({name:'urq_workloadnum',value:$("#urq_workloadnum").val()}); 
    	  grid.options.parms.push({name:'urq_price',value:$("#urq_price").val()}); 
    	  grid.options.parms.push({name:'urq_totalfee',value:$("#urq_totalfee").val()}); 
    	  grid.options.parms.push({name:'urq_alonepaynum',value:$("#urq_alonepaynum").val()}); 
    	  grid.options.parms.push({name:'urq_patientid',value:$("#urq_patientid").val()}); 
    	  grid.options.parms.push({name:'urq_patientname',value:$("#urq_patientname").val()}); 
    	  grid.options.parms.push({name:'urq_patientsex',value:$("#urq_patientsex").val()}); 
    	  grid.options.parms.push({name:'urq_patientage',value:$("#urq_patientage").val()}); 
    	  grid.options.parms.push({name:'urq_startdate',value:$("#urq_startdate").val()}); 
    	  grid.options.parms.push({name:'urq_starttime',value:$("#urq_starttime").val()}); 
    	  grid.options.parms.push({name:'urq_enddate',value:$("#urq_enddate").val()}); 
    	  grid.options.parms.push({name:'urq_endtime',value:$("#urq_endtime").val()}); 
    	  grid.options.parms.push({name:'urq_operator',value:$("#urq_operator").val()}); 
    	  grid.options.parms.push({name:'urq_otherinfo',value:$("#urq_otherinfo").val()}); 
    	  grid.options.parms.push({name:'urq_cancelflag',value:$("#urq_cancelflag").val()}); 
    	  grid.options.parms.push({name:'urq_remark',value:$("#urq_remark").val()}); 
    	  grid.options.parms.push({name:'urq_doctororderid',value:$("#urq_doctororderid").val()}); 
    	  grid.options.parms.push({name:'urq_positiveflag',value:$("#urq_positiveflag").val()}); 
    	  grid.options.parms.push({name:'urq_sampleno',value:$("#urq_sampleno").val()}); 
    	  grid.options.parms.push({name:'urq_exposurenum',value:$("#urq_exposurenum").val()}); 
    	  grid.options.parms.push({name:'urq_adddate',value:$("#urq_adddate").val()}); 
    	  grid.options.parms.push({name:'urq_addtime',value:$("#urq_addtime").val()}); 
    	  grid.options.parms.push({name:'urq_finishdate',value:$("#urq_finishdate").val()}); 
    	  grid.options.parms.push({name:'urq_finishtime',value:$("#urq_finishtime").val()}); 
    	  grid.options.parms.push({name:'urq_lastexecutedate',value:$("#urq_lastexecutedate").val()}); 
    	  grid.options.parms.push({name:'urq_lastexecutetime',value:$("#urq_lastexecutetime").val()}); 
    	  grid.options.parms.push({name:'urq_executetimes',value:$("#urq_executetimes").val()}); 
    	  grid.options.parms.push({name:'urq_status',value:$("#urq_status").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#urq_rowid").val()!=""){
                		return rowdata.urq_rowid.indexOf($("#urq_rowid").val()) > -1;	
                	}
                	if($("#busi_data_source_code").val()!=""){
                		return rowdata.busi_data_source_code.indexOf($("#busi_data_source_code").val()) > -1;	
                	}
                	if($("#urq_exdevicecode").val()!=""){
                		return rowdata.urq_exdevicecode.indexOf($("#urq_exdevicecode").val()) > -1;	
                	}
                	if($("#urq_exitemcode").val()!=""){
                		return rowdata.urq_exitemcode.indexOf($("#urq_exitemcode").val()) > -1;	
                	}
                	if($("#urq_exitemdcode").val()!=""){
                		return rowdata.urq_exitemdcode.indexOf($("#urq_exitemdcode").val()) > -1;	
                	}
                	if($("#urq_exbusscode").val()!=""){
                		return rowdata.urq_exbusscode.indexOf($("#urq_exbusscode").val()) > -1;	
                	}
                	if($("#urq_exuseloccode").val()!=""){
                		return rowdata.urq_exuseloccode.indexOf($("#urq_exuseloccode").val()) > -1;	
                	}
                	if($("#urq_usedate").val()!=""){
                		return rowdata.urq_usedate.indexOf($("#urq_usedate").val()) > -1;	
                	}
                	if($("#urq_workloadnum").val()!=""){
                		return rowdata.urq_workloadnum.indexOf($("#urq_workloadnum").val()) > -1;	
                	}
                	if($("#urq_price").val()!=""){
                		return rowdata.urq_price.indexOf($("#urq_price").val()) > -1;	
                	}
                	if($("#urq_totalfee").val()!=""){
                		return rowdata.urq_totalfee.indexOf($("#urq_totalfee").val()) > -1;	
                	}
                	if($("#urq_alonepaynum").val()!=""){
                		return rowdata.urq_alonepaynum.indexOf($("#urq_alonepaynum").val()) > -1;	
                	}
                	if($("#urq_patientid").val()!=""){
                		return rowdata.urq_patientid.indexOf($("#urq_patientid").val()) > -1;	
                	}
                	if($("#urq_patientname").val()!=""){
                		return rowdata.urq_patientname.indexOf($("#urq_patientname").val()) > -1;	
                	}
                	if($("#urq_patientsex").val()!=""){
                		return rowdata.urq_patientsex.indexOf($("#urq_patientsex").val()) > -1;	
                	}
                	if($("#urq_patientage").val()!=""){
                		return rowdata.urq_patientage.indexOf($("#urq_patientage").val()) > -1;	
                	}
                	if($("#urq_startdate").val()!=""){
                		return rowdata.urq_startdate.indexOf($("#urq_startdate").val()) > -1;	
                	}
                	if($("#urq_starttime").val()!=""){
                		return rowdata.urq_starttime.indexOf($("#urq_starttime").val()) > -1;	
                	}
                	if($("#urq_enddate").val()!=""){
                		return rowdata.urq_enddate.indexOf($("#urq_enddate").val()) > -1;	
                	}
                	if($("#urq_endtime").val()!=""){
                		return rowdata.urq_endtime.indexOf($("#urq_endtime").val()) > -1;	
                	}
                	if($("#urq_operator").val()!=""){
                		return rowdata.urq_operator.indexOf($("#urq_operator").val()) > -1;	
                	}
                	if($("#urq_otherinfo").val()!=""){
                		return rowdata.urq_otherinfo.indexOf($("#urq_otherinfo").val()) > -1;	
                	}
                	if($("#urq_cancelflag").val()!=""){
                		return rowdata.urq_cancelflag.indexOf($("#urq_cancelflag").val()) > -1;	
                	}
                	if($("#urq_remark").val()!=""){
                		return rowdata.urq_remark.indexOf($("#urq_remark").val()) > -1;	
                	}
                	if($("#urq_doctororderid").val()!=""){
                		return rowdata.urq_doctororderid.indexOf($("#urq_doctororderid").val()) > -1;	
                	}
                	if($("#urq_positiveflag").val()!=""){
                		return rowdata.urq_positiveflag.indexOf($("#urq_positiveflag").val()) > -1;	
                	}
                	if($("#urq_sampleno").val()!=""){
                		return rowdata.urq_sampleno.indexOf($("#urq_sampleno").val()) > -1;	
                	}
                	if($("#urq_exposurenum").val()!=""){
                		return rowdata.urq_exposurenum.indexOf($("#urq_exposurenum").val()) > -1;	
                	}
                	if($("#urq_adddate").val()!=""){
                		return rowdata.urq_adddate.indexOf($("#urq_adddate").val()) > -1;	
                	}
                	if($("#urq_addtime").val()!=""){
                		return rowdata.urq_addtime.indexOf($("#urq_addtime").val()) > -1;	
                	}
                	if($("#urq_finishdate").val()!=""){
                		return rowdata.urq_finishdate.indexOf($("#urq_finishdate").val()) > -1;	
                	}
                	if($("#urq_finishtime").val()!=""){
                		return rowdata.urq_finishtime.indexOf($("#urq_finishtime").val()) > -1;	
                	}
                	if($("#urq_lastexecutedate").val()!=""){
                		return rowdata.urq_lastexecutedate.indexOf($("#urq_lastexecutedate").val()) > -1;	
                	}
                	if($("#urq_lastexecutetime").val()!=""){
                		return rowdata.urq_lastexecutetime.indexOf($("#urq_lastexecutetime").val()) > -1;	
                	}
                	if($("#urq_executetimes").val()!=""){
                		return rowdata.urq_executetimes.indexOf($("#urq_executetimes").val()) > -1;	
                	}
                	if($("#urq_status").val()!=""){
                		return rowdata.urq_status.indexOf($("#urq_status").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: 'URQ_RowID', name: 'urq_rowid', align: 'left'
					 		},
                     { display: '仪器来源编码（仪器来源类型：3.设备效益分析）COST_BUSI_SOUREC_DICT', name: 'busi_data_source_code', align: 'left'
					 		},
                     { display: '外部系统设备ID', name: 'urq_exdevicecode', align: 'left'
					 		},
                     { display: '外部系统项目ID', name: 'urq_exitemcode', align: 'left'
					 		},
                     { display: 'urqExitemdcode', name: 'urq_exitemdcode', align: 'left'
					 		},
                     { display: '外部系统业务ID', name: 'urq_exbusscode', align: 'left'
					 		},
                     { display: '外部系统科室ID', name: 'urq_exuseloccode', align: 'left'
					 		},
                     { display: '使用日期', name: 'urq_usedate', align: 'left'
					 		},
                     { display: '工作量', name: 'urq_workloadnum', align: 'left'
					 		},
                     { display: '单价', name: 'urq_price', align: 'left'
					 		},
                     { display: '总金额', name: 'urq_totalfee', align: 'left'
					 		},
                     { display: '为空表示按标准配备数量或者没有单独收费， 不为空表示特殊的单独收费项数量', name: 'urq_alonepaynum', align: 'left'
					 		},
                     { display: '使用患者ID', name: 'urq_patientid', align: 'left'
					 		},
                     { display: '记录患者姓名', name: 'urq_patientname', align: 'left'
					 		},
                     { display: '记录患者性别', name: 'urq_patientsex', align: 'left'
					 		},
                     { display: '记录患者年龄', name: 'urq_patientage', align: 'left'
					 		},
                     { display: '开始使用日期', name: 'urq_startdate', align: 'left'
					 		},
                     { display: '开始使用时间', name: 'urq_starttime', align: 'left'
					 		},
                     { display: '结束使用日期', name: 'urq_enddate', align: 'left'
					 		},
                     { display: '结束使用时间', name: 'urq_endtime', align: 'left'
					 		},
                     { display: '操作员', name: 'urq_operator', align: 'left'
					 		},
                     { display: '其他信息', name: 'urq_otherinfo', align: 'left'
					 		},
                     { display: '取消标志', name: 'urq_cancelflag', align: 'left'
					 		},
                     { display: '备注', name: 'urq_remark', align: 'left'
					 		},
                     { display: '记录医嘱OrderID', name: 'urq_doctororderid', align: 'left'
					 		},
                     { display: '用做阳性标志', name: 'urq_positiveflag', align: 'left'
					 		},
                     { display: '标本号/检查号', name: 'urq_sampleno', align: 'left'
					 		},
                     { display: '曝光次数', name: 'urq_exposurenum', align: 'left'
					 		},
                     { display: '新增日期', name: 'urq_adddate', align: 'left'
					 		},
                     { display: '新增时间', name: 'urq_addtime', align: 'left'
					 		},
                     { display: '完成日期', name: 'urq_finishdate', align: 'left'
					 		},
                     { display: '完成时间', name: 'urq_finishtime', align: 'left'
					 		},
                     { display: '最后执行日期', name: 'urq_lastexecutedate', align: 'left'
					 		},
                     { display: '最后执行时间', name: 'urq_lastexecutetime', align: 'left'
					 		},
                     { display: '执行次数', name: 'urq_executetimes', align: 'left'
					 		},
                     { display: '状态', name: 'urq_status', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssEqiuserecordqueue.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
						                { line:true },
						                { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate,icon:'down' },
						                { line:true },
						                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' }
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.urq_rowid 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
		$.ligerDialog.open({ url : 'assEqiuserecordqueueAddPage.do?isCheck=false&',data:{}, height: 300,width: 450, title:'13设备使用记录采集队列 ASS_EQIUseRecordQueue',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveassEqiuserecordqueue(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
		
    	/*var index = layer.open({
					type : 2,
					title : '13设备使用记录采集队列 ASS_EQIUseRecordQueue',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'assEqiuserecordqueueAddPage.do?isCheck=false'
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
							this.urq_rowid 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssEqiuserecordqueue.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    function imp(){
    	
    	var index = layer.open({
					type : 2,
					title : '导入',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'assEqiuserecordqueueImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"urq_rowid="+vo[3] 
		 
		 
		 $.ligerDialog.open({ url : 'assEqiuserecordqueueUpdatePage.do?isCheck=false&',data:{}, height: 300,width: 450, title:'13设备使用记录采集队列 ASS_EQIUseRecordQueue',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveassEqiuserecordqueue(); },cls:'l-dialog-btn-highlight' },
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
					content : 'assEqiuserecordqueueUpdatePage.do?isCheck=false&' + parm
				});
				layer.full(index);	
    */
    }
    function loadDict(){
            //字典下拉框
            
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('I', imp);
		

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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">URQ_RowID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_rowid" type="text" id="urq_rowid" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仪器来源编码（仪器来源类型：3.设备效益分析）COST_BUSI_SOUREC_DICT：</td>
            <td align="left" class="l-table-edit-td"><input name="busi_data_source_code" type="text" id="busi_data_source_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">外部系统设备ID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exdevicecode" type="text" id="urq_exdevicecode" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">外部系统项目ID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exitemcode" type="text" id="urq_exitemcode" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">urqExitemdcode：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exitemdcode" type="text" id="urq_exitemdcode" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">外部系统业务ID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exbusscode" type="text" id="urq_exbusscode" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">外部系统科室ID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exuseloccode" type="text" id="urq_exuseloccode" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_usedate" type="text" id="urq_usedate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工作量：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_workloadnum" type="text" id="urq_workloadnum" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单价：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_price" type="text" id="urq_price" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">总金额：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_totalfee" type="text" id="urq_totalfee" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">为空表示按标准配备数量或者没有单独收费， 不为空表示特殊的单独收费项数量：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_alonepaynum" type="text" id="urq_alonepaynum" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用患者ID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_patientid" type="text" id="urq_patientid" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">记录患者姓名：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_patientname" type="text" id="urq_patientname" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">记录患者性别：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_patientsex" type="text" id="urq_patientsex" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">记录患者年龄：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_patientage" type="text" id="urq_patientage" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开始使用日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_startdate" type="text" id="urq_startdate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开始使用时间：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_starttime" type="text" id="urq_starttime" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结束使用日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_enddate" type="text" id="urq_enddate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结束使用时间：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_endtime" type="text" id="urq_endtime" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">操作员：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_operator" type="text" id="urq_operator" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">其他信息：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_otherinfo" type="text" id="urq_otherinfo" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取消标志：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_cancelflag" type="text" id="urq_cancelflag" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_remark" type="text" id="urq_remark" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">记录医嘱OrderID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_doctororderid" type="text" id="urq_doctororderid" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用做阳性标志：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_positiveflag" type="text" id="urq_positiveflag" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">标本号/检查号：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_sampleno" type="text" id="urq_sampleno" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">曝光次数：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exposurenum" type="text" id="urq_exposurenum" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">新增日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_adddate" type="text" id="urq_adddate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">新增时间：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_addtime" type="text" id="urq_addtime" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">完成日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_finishdate" type="text" id="urq_finishdate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">完成时间：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_finishtime" type="text" id="urq_finishtime" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">最后执行日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_lastexecutedate" type="text" id="urq_lastexecutedate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">最后执行时间：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_lastexecutetime" type="text" id="urq_lastexecutetime" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">执行次数：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_executetimes" type="text" id="urq_executetimes" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_status" type="text" id="urq_status" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
