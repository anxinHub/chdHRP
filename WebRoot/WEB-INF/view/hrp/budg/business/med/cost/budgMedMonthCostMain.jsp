<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,grid,dialog" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    $(function() {
        loadHead();
        loadHotkeys();
        init();
    });

    var year_input, subj_level_select, dept_name_select,state_select;
  //ajax获取数据
	function getData(url, callback) {
		$.ajax({
			url: url,
			dataType: "JSON",
			type: "post",
			success: function (res) {
				if (typeof callback === "function") {
					callback(res);
				}
			}
		})
	}
    function init() {
    	getData("../../../queryBudgYearTen.do?isCheck=false", function (data) {
			year_input = $("#year_input").etDatepicker({
				defaultDate: data[4].text,
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				minDate: data[0].text,
				maxDate: data[9].text,
				todayButton: false,
				onSelect: function (value) {
					reloadSubjName(value);
					setTimeout(function () {
						query();
					}, 10);
				}
			});
			reloadSubjName(data[4].text);
		});
        subj_name_select = $("#subj_name_select").etSelect({
            defaultValue: "none",
            onChange: query
        });

        function reloadSubjName(year) {
            subj_name_select.reload({
                url: "../../../queryBudgSubj.do?isCheck=false",
                para: {
                    subj_type: "05",
                    budg_year: year
                }
            });
        }

        dept_name_select = $("#dept_name_select").etSelect({
            url: "../../../queryDept.do?isCheck=false",
            defaultValue: "none",
            onChange: query
        });
        state_select = $("#state_select").etSelect({
            url: "../../../queryBudgSysDict.do?isCheck=false&f_code=STATE_A",
            defaultValue: "none",
            onChange: query
        });
    }
    
    function query() {
        var search = [
            { name: 'budg_year', value: $("#year_input").val() },
            { name: 'dept_id', value: dept_name_select.getValue() },
            { name: 'subj_code', value: subj_name_select.getValue() },
            { name: 'state', value: state_select.getValue() }
        ];
        grid.loadData(search, 'queryDeptMedMonthCost.do');
    }

    function loadHead() {
    	var columns = [
			{ display: '预算年度', name: 'budg_year', width: 80 },
            { display: '科室编码', name: 'dept_code', width: 100 },
            { display: '科室名称', name: 'dept_name', width: 180 },
            { display: '科目编码', name: 'subj_code', width: 150 },
            { display: '科目名称', name: 'subj_name', width: 120 },
            { display: '预算值', name: 'budg_value', align: 'right', width: 120,
                render: function(ui) {
                    return formatNumber(ui.rowData.budg_value, 2, 1);
                }},
            { display: '说明', name: 'remark', width: 120 },
            { display: '状态', name: 'state_name', width: 120 },
            { display: '审核人', name: 'fu_chk_name', width: 120 },
            { display: '审核日期', name: 'fu_chk_time', width: 120 },
            { display: '审核意见', name: 'fu_chk_op', width: 120 },
            { display: '确认人', name: 'fi_chk_name', width: 120 },
            { display: '确认日期', name: 'fi_chk_time', width: 120 },
            { display: '确认意见', name: 'fi_chk_op', width: 120 }
        ]; 

        
        var paramObj = {
        	height: '100%',
            checkbox: true,
             rowDblClick: function (event, ui) {
                var rowData = ui.rowData;
                var paramArray = [
                    rowData.budg_year,
                    rowData.dept_id,
                    rowData.subj_code
                ];

                openUpdate(paramArray);
            },  
			pageModel: {
				type: 'remote',
			},
            columns: columns,
            toolbar: {
                items: [
					{ type: 'button', label: '查询（<u>Q</u>）', listeners: [{ click: query }], icon: 'search' },
					{ type: 'button', label: '费用预算汇总', listeners: [{ click: collect }]},
                    { type: 'button', label: '添加（<u>A</u>）', listeners: [{ click: add_open }], icon: 'add' },
                    { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
                    { type: 'button', label: '审核（<u>S</u>）', listeners: [{ click: audit }], icon: 'audit' },
                    { type: 'button', label: '消审（<u>X</u>）', listeners: [{ click: reAudit }], icon: 'right' },
                    { type: 'button', label: '确认（<u>C</u>）', listeners: [{ click: affi }], icon: 'audit' },
                    { type: 'button', label: '取消确认（<u>C</u>）', listeners: [{ click: reAffi }], icon: 'audit' },
                    { type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
					{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] }
                ]
            }
        }
        grid = $("#maingrid").etGrid(paramObj);
    }

    
    
    function downTemplate(){
    	location.href = "downTemplateYear.do?isCheck=false";
    }	
    function imp(){
    	var index = layer.open({
			type : 2,
			title : '导入',
			shadeClose : false,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'budgMedCostYearImportPage.do?isCheck=false'
				
		});
		layer.full(index);
    }	
    function add_open() {
        $.etDialog.open({
            url: 'budgMedMonthCostAddPage.do?isCheck=false&',
            height: 450,
            width: 600,
            title: '科室医疗支出预算',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgMedMonth()
            }
        });
    }

    function remove() {
        var data = grid.selectGet();

        if (data.length == 0) {
            $.etDialog.warn('请选择行');
            return;
        }
        var ParamVo = [];
		var s=true;
        $(data).each(function() {
        	if(this.rowData.state!='01'){
        		s=false;
        		 return;
        	}
            ParamVo.push(
                this.rowData.budg_year + "@" +
                this.rowData.dept_id + "@" +
                this.rowData.subj_code
            )
        });
        if(!s){
        	 $.etDialog.warn('所选单据不是新建状态无法删除');
    		 return;
        }
       
		$.etDialog.confirm('确定删除?', function(yes) {
              ajaxPostData({
              	url: "deleteBudgMedMonthCost.do",
              	data: { ParamVo: ParamVo.toString() },
              	success: function(responseData) {
                      query();
                  }
              });
 	   $.etDialog.closeAll();
// 	   openAffi(ParamVo.toString(),"03","2");
    });
      
    }

    function openUpdate (obj) {
        var parm =
            "budg_year=" + obj[0] + "&" +
            "dept_id=" + obj[1] + "&" +
            "subj_code=" + obj[2];

        $.etDialog.open({
            url: 'budgMedCostMonthUpdatePage.do?isCheck=false&'+parm,
            height: 450,
            width: 450,
            title: '科室医疗支出预算',
            frameName :window.name,
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgMedMonth()
            }
        });
    }
    
   
    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', query);
        hotkeys('D', remove);
    }
    //审核
    function openAffi(param,state,type) {
        $.etDialog.open({
            url: 'budgMedCostMonthAuditPage.do?isCheck=false&paramVo='+param+"&type="+type+"&state="+state,
            height: 300,
            width: 400,
            title: '科室医疗支出预算确认',
            btn: ['确认','取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.audit();
                iframeWindow.close();
                query();
            }
        });
    }
    function affi(){
    	 var ParamVo = [];
         var data = grid.selectGet();
         var budg_year;
         
         if (data.length == 0) {
             $.etDialog.error('必须选择一行数据');
             return;
         }
         
         $(data).each(function (){
             ParamVo.push(
            		this.rowData.budg_year   +"@"+ 
  				this.rowData.dept_id   +"@"+ 
  				this.rowData.subj_code   +"@" 
             );
         })
  		
         //校验 选中数据状态
         ajaxPostData({
             url: "queryBudgCostMonthState.do?isCheck=false",
             data: {
             		paramVo : ParamVo.toString(),
  					state : '02'
  				
             },
             success: function(responseData) {
                 if (responseData.state == "false") {
                 	$.etDialog.warn("所选单据未审核不允许确认！");
                     return;
                 } else {
                     $.etDialog.confirm('是否确认单据?', function() {
                  	   $.etDialog.closeAll();
                  	   openAffi(ParamVo.toString(),"03","2");
                     });
                 }
             }
         })
   }
   
 //销审
   function reAffi() {
       var ParamVo = [];
       var data = grid.selectGet();
       var applyId = "";
       var budg_year;
       
       if (data.length == 0) {
           $.etDialog.error('请选择行');
           return;
       }

       $(data).each(function () {
           ParamVo.push(
	          	this.rowData.budg_year   +"@"+ 
				this.rowData.dept_id   +"@"+ 
				this.rowData.subj_code +"@"+"02"
           );
       })
		
        //校验 选中数据状态
       ajaxPostData({
           url: "queryBudgCostMonthState.do?isCheck=false",
           data: {
	           	paramVo : ParamVo.toString(),
				state :'03'
           },
           success: function(responseData) {
               if (responseData.state == "false") {
               	$.etDialog.error("取消确认失败！所选单据不是确认状态不允许取消确认！");
                   return;
               } else {
                   $.etDialog.confirm('是否取消确认?', function() {
                       ajaxPostData({
                           url: "auditOrUnAudit.do?isCheck=false",
                           data: { paramVo : ParamVo.toString(),state:"02",type:"2" },
                           success: function(responseData) {
                               query();
                           }
                       });
                   });
               }
           }
       })
   }
   //审核
    function openAudit (param,state,type) {
        $.etDialog.open({
            url: 'budgMedCostMonthAuditPage.do?isCheck=false&paramVo='+param+"&state="+state+"&type="+type,
            height: 300,
            width: 400,
            title: '科室医疗支出预算审核',
            btn: ['审核','取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.audit();
                iframeWindow.close();
                query();
            }
        });
    }
   function audit() {
       var ParamVo = [];
       var data = grid.selectGet();
       var budg_year;
       
       if (data.length == 0) {
           $.etDialog.error('必须选择一行数据');
           return;
       }
       
       $(data).each(function (){
           ParamVo.push(
          		this.rowData.budg_year   +"@"+ 
				this.rowData.dept_id   +"@"+ 
				this.rowData.subj_code   +"@" 
           );
       })
		
       //校验 选中数据状态
       ajaxPostData({
           url: "queryBudgCostMonthState.do?isCheck=false",
           data: {
           		paramVo : ParamVo.toString(),
				state : '01'
				
           },
           success: function(responseData) {
               if (responseData.state == "false") {
               	$.etDialog.warn("所选单据不是新建状态不允许审核！");
                   return;
               } else {
                   $.etDialog.confirm('确定审核?', function() {
                	   $.etDialog.closeAll();
                	   openAudit(ParamVo.toString(),'02','1');
                   });
               }
           }
       })
   }

   //销审
   function reAudit () {
       var ParamVo = [];
       var data = grid.selectGet();
       
       if (data.length == 0) {
           $.etDialog.error('请选择行');
           return;
       }

       $(data).each(function () {
           ParamVo.push(
	          	this.rowData.budg_year   +"@"+ 
				this.rowData.dept_id   +"@"+ 
				this.rowData.subj_code   +"@"+ 
			    '01' 
           );
       })
		
        //校验 选中数据状态
       ajaxPostData({
           url: "queryBudgCostMonthState.do?isCheck=false",
           data: {
        	  	paramVo : ParamVo.toString(),
				state :'02'
           },
           success: function(responseData) {
               if (responseData.state == "false") {
               	$.etDialog.warn("销审失败！所选单据不是审核状态不允许审核！");
                   return;
               } else {
                   $.etDialog.confirm('确定销审?', function() {
                       ajaxPostData({
                           url: "auditOrUnAudit.do",
                           data: { paramVo : ParamVo.toString(),state:"01",type:"1" },
                           success: function(responseData) {
                               query();
                           }
                       });
                   });
               }
           }
       })
   }
   //费用汇总
   function collect(){
   	var year = $("#year_input").val();
		if(!year){
			$.etDialog.error('预算年度不能为空');
			return false ;
		}
		
	   //校验 预算年度数据状态
       ajaxPostData({
           url: "queryYearDeptExpensesDataExistNoCheck.do?isCheck=false&year="+year,
           data: {},
           success: function(responseData) {
               if (responseData.work_state == "false") {
               	$.etDialog.warn("该年度费用申报存在未确认数据，请先确认后再汇总！");
                   return;
               } else {
               		ajaxPostData({
                       url: "queryYearDeptSubjDataExist.do?isCheck=false&year="+year,
                       data: {},
                       success: function(responseData) {
                           if (responseData.work_state == "false") {
                             return;
                           } 
                           else{
                           	var str ='确认汇总'+$("#year_input").val().toString()+'年度的费用预算?';
                       		$.etDialog.confirm(str, function(yes) {
                       			if (yes) {
                    				ajaxPostData({
                    					url: "collectMedMonthExpenses.do?isCheck=false&year="+year,
                    					data: {},
                    					success: function (res){
                    						if (res.state == "true") {
                    							query();
                    						}
                    					}
                    				});
                       			}
                      		  });
                           }
                       }
                   });
               }
           }
       });
   }
</script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">年度：</td>
                <td class="ipt">
                    <input type="text" id="year_input" />
                </td>
                <td class="label">科室名称：</td>
                <td class="ipt">
                    <select name="" id="dept_name_select" style="width:180px;"></select>
                </td>
            
                <td class="label">科目名称：</td>
                <td class="ipt"  >
                    <select name="" id="subj_name_select" style="width:180px;"></select>
                </td>
                 <td class="label">状态：</td>
                <td class="ipt"  >
                    <select name="" id="state_select" style="width:100px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>