<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <%
String path = request.getContextPath();
%>
        <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

        <html style="overflow:hidden;">

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <jsp:include page="${path}/static_resource.jsp">
                <jsp:param value="grid,select,datepicker,ligerUI" name="plugins" />
            </jsp:include>

            <script type="text/javascript">
                var gridDetail, grid;
                var gridManager = null;
                var gridManagerDetail = null;
                var state = '${state}';
                var userUpdateStr;
                var budg_year;
                var rowdata;
                var gridHos = "#maingrid";
                var gridDept = "#maingridDetail";
                var addData; //记录 主表 添加数据用变量
                var addDataDetail; //记录 明细表 添加数据用变量


                $(function () {
                    //加载数据
                    loadHeadDetail(null);
                    loadHead(null);
                    loadHotkeys();
                    // $("#budg_year").change(function () {
                    //     query();
                    //     queryDatail();
                    // });

                    $("#hosItem").show(); //显示主表信息
                    $("#deptItem").hide(); //隐藏明细表信息
                    $("#item_tab").ligerTab({
                        onAfterSelectTabItem: function (tabid) {
                            selectTab(tabid);
                        }
                    });
                });
                //查询
                function query() {
                    grid.options.parms = [
                        { name: 'budg_year', value: year_input.getValue() },
                        { name: 'con_emp_id', value: con_emp_select.getValue() },
                        { name: 'proj_name', value: $("#proj_name_input").val() },
                        { name: 'state', value: state_select.getValue() },
                        { name: 'source_id', value: source_select.getValue() }
                    ];
                    grid.options.newPage = 1;

                    //加载查询条件
                    grid.loadData(grid.where);
                }
                function queryDatail() {
                    gridDetail.options.parms = [
                        { name: 'budg_year', value: year_input.getValue() },
                        { name: 'con_emp_id', value: con_emp_select.getValue() },
                        { name: 'proj_name', value: $("#proj_name_input").val() },
                        { name: 'state', value: state_select.getValue() },
                        { name: 'source_id', value: source_select.getValue() },
                        { name: 'payment_item_id', value: payment_item_select.getValue().split(",")[0] }
                    ];
                    gridDetail.options.newPage = 1;
                   
                    //加载查询条件
                    gridDetail.loadData(gridDetail.where);
                }
                function loadHead() {
                    grid = $(gridHos).ligerGrid({
                        columns: [
                            {
                                display: '项目名称',
                                name: 'proj_name',
                                align: 'left',
                                width: '240',
                                valueField: 'id',
                                textField: 'text',
                                editor: {
                                    type: 'select',
                                    valueField: 'id',
                                    textField: 'text',
                                    url: '../../queryProjName.do?isCheck=false',
                                    keySupport: true,
                                    autocomplete: true,
                                    onChanged: setInfo,
                                },
                                render: function (rowdata, rowindex, value) {
                                    if (rowdata.group_id) {
                                        rowdata.notEidtColNames.push("proj_name");
                                        rowdata.notEidtColNames.push("source_name");
                                        rowdata.notEidtColNames.push("con_emp_name");

                                    }
                                    return rowdata.text;
                                }
                            }, {
                                display: '资金来源',
                                name: 'source_name',
                                align: 'left',
                                width: '150',
                                valueField: 'ids',
                                textField: 'texts',
                                editor: {
                                    type: 'select',
                                    valueField: 'ids',
                                    textField: 'texts',
                                    url: 'queryBudgSourceId.do?isCheck=false',
                                    keySupport: true,
                                    autocomplete: true,
                                    onChanged: setInfo,
                                }
                            },
                            {
                                display: '项目负责人',
                                name: 'con_emp_name',
                                align: 'left',
                                width: '100',
                            }, {
                                display: '期初预算余额',
                                name: 'b_remain_amount',
                                align: 'right',
                                width: '90',
                                editor: {
                                    type: 'float'
                                },
                                render: function (rowdata, rowindex, value) {

                                    return formatNumber(value, 2, 1);

                                }
                            }, {
                                display: '期初可用余额',
                                name: 'b_usable_amount',
                                align: 'right',
                                width: '90',
                                editor: {
                                    type: 'float'
                                },
                                render: function (rowdata, rowindex, value) {

                                    return formatNumber(value, 2, 1);

                                }
                            }, {
                                display: '本期增加',
                                name: 'budg_amount',
                                align: 'right',
                                width: '70',
                                editor: {
                                    type: 'float'
                                },
                                render: function (rowdata, rowindex, value) {
                                    if (value) {
                                        return formatNumber(value, 2, 1);
                                    }
                                }
                            }, {
                                display: '本期到账',
                                name: 'in_amount',
                                align: 'right',
                                width: '85',
                                editor: {
                                    type: 'float'
                                },
                                render: function (rowdata, rowindex, value) {
                                    if (value) {
                                        return formatNumber(value, 2, 1);
                                    }
                                }
                            }, {
                                display: '本期执行',
                                name: 'cost_amount',
                                align: 'right',
                                width: '85',
                                editor: {
                                    type: 'float'
                                },
                                render: function (rowdata, rowindex, value) {
                                    if (value) {
                                        return formatNumber(value, 2, 1);
                                    }
                                }
                            }, {
                                display: '预算余额',
                                name: 'remain_amount',
                                align: 'right',
                                width: '85',
                                render: function (rowdata, rowindex, value) {
                                    if (value) {
                                        return formatNumber(value, 2, 1);
                                    }
                                }
                            }, {
                                display: '可用余额',
                                name: 'usable_amount',
                                align: 'right',
                                width: '85',
                                render: function (rowdata, rowindex, value) {
                                    if (value) {
                                        return formatNumber(value, 2, 1);
                                    }
                                }
                            }, {
                                display: '审核人',
                                name: 'check_name',
                                align: 'left',
                                width: '70',
                            }, {
                                display: '审核日期',
                                name: 'check_date',
                                align: 'left',
                                width: '100'
                            }, {
                                display: '状态',
                                name: 'state',
                                align: 'left',
                                width: '70',
                                render: function (rowdata, rowindex, value) {
                                    if (rowdata.state == 01) {
                                        return "新建";
                                    } else if (rowdata.state == 02) {
                                        return "审核";
                                    }
                                }
                            }
                        ],
                        dataAction: 'server',
                        dataType: 'server',
                        usePager: true,
                        url: 'queryBeginProject.do?isCheck=false', width: '100%', height: '100%', checkbox: true, rownumbers: true,
                        delayLoad: true, isAddRow: false, enabledEdit: true,
                        onChangeRow: savaData,
                        onBeforeEdit: f_onBeforeEdit,
                        onAfterEdit: f_onAfterEdit,
                        selectRowButtonOnly: true, //heightDiff: -10,
                        toolbar: {
                            items: [
                                { text: '查询（<u>E</u>）', id: 'search', click: query, icon: 'search' },
                                { line: true },
                                { text: '添加行（<u>A</u>）', id: 'add', click: add_open, icon: 'add' },
                                { line: true },
                                { text: '保存（<u>B</u>）', id: 'save', click: save, icon: 'save' },
                                { line: true },
                                { text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete' },
                                { line: true },
                                {
                                    text: '导入（<u>I</u>）',
                                    id: 'import',
                                    click: impNew,
                                    icon: 'up'
                                },
                                /* { text: '下载导入模板（<u>B</u>）', id: 'downTemplate', click: downTemplate, icon: 'down' },
                                { line: true }, */
                                { text: '审核（<u>B</u>）', id: 'audit', click: audit, icon: 'audit' },
                                { line: true },
                                { text: '销审（<u>B</u>）', id: 'unAudit', click: unAudit, icon: 'unAudit' },
                            ]
                        },
                    });

                    gridManager = $("#maingrid").ligerGetGridManager();
                }

                function loadHeadDetail() {
                    gridDetail = $(gridDept).ligerGrid({
                        columns: [{
                            display: '项目名称',
                            name: 'proj_name',
                            align: 'left',
                            width: '240',
                            valueField: 'id',
                            textField: 'text',
                            editor: {
                                type: 'select',
                                valueField: 'id',
                                textField: 'text',
                                url: '../../queryProjName.do?isCheck=false',
                                keySupport: true,
                                autocomplete: true,
                                onChanged: setInfoDetail,
                            },
                            render: function (rowdata, rowindex, value) {
                                if (rowdata.group_id) {
                                    rowdata.notEidtColNames.push("proj_name");
                                    rowdata.notEidtColNames.push("source_name");
                                    rowdata.notEidtColNames.push("payment_item_name");

                                }
                                return rowdata.text;
                            }
                        }, {
                            display: '资金来源',
                            name: 'source_name',
                            align: 'left',
                            width: '240',
                            valueField: 'ids',
                            textField: 'texts',
                            editor: {
                                type: 'select',
                                valueField: 'ids',
                                textField: 'texts',
                                url: 'queryBudgSourceId.do?isCheck=false',
                                keySupport: true,
                                autocomplete: true,
                                onChanged: setInfoDetail,
                            }
                        }, {
                            display: '支出项目',
                            name: 'payment_item_name',
                            align: 'left',
                            width: '240',
                            valueField: 'idno',
                            textField: 'textno',
                            editor: {
                                type: 'select',
                                valueField: 'idno',
                                textField: 'textno',
                                url: 'queryBudgPaymentItemNo.do?isCheck=false',
                                keySupport: true,
                                autocomplete: true,
                                onChanged: setInfoDetail,
                            }
                        },

                        {
                            display: '期初预算余额',
                            name: 'b_remain_amount',
                            align: 'right',
                            width: '150',
                            editor: {
                                type: 'float'
                            },
                            render: function (rowdata, rowindex, value) {

                                return formatNumber(value, 2, 1);

                            }
                        }, {
                            display: '本期增加',
                            name: 'budg_amount',
                            align: 'right',
                            width: '150',
                            editor: {
                                type: 'float'
                            },
                            render: function (rowdata, rowindex, value) {
                                if (value) {
                                    return formatNumber(value, 2, 1);
                                }
                            }
                        }, {
                            display: '本期执行',
                            name: 'cost_amount',
                            align: 'right',
                            width: '150',
                            editor: {
                                type: 'float'
                            },
                            render: function (rowdata, rowindex, value) {
                                if (value) {
                                    return formatNumber(value, 2, 1);
                                }
                            }
                        }, {
                            display: '预算余额',
                            name: 'remain_amount',
                            align: 'right',
                            width: '150', render: function (rowdata, rowindex, value) {
                                if (value) {
                                    return formatNumber(value, 2, 1);
                                }
                            }
                        },

                        ],
                        dataAction: 'server', dataType: 'server', usePager: true, url: 'queryBeginProjectDetail.do?isCheck=false',
                        width: '100%', height: '100%', checkbox: true, rownumbers: true, isAddRow: false, enabledEdit: true,
                        delayLoad: true,
                        onChangeRow: savaDataDetail,
                        onBeforeEdit: f_onBeforeEditDetail,
                        onAfterEdit: f_onAfterEditDetail,
                        selectRowButtonOnly: true, //heightDiff: -10,
                        toolbar: {
                            items: [{
                                text: '查询（<u>E</u>）',
                                id: 'search',
                                click: queryDatail,
                                icon: 'search'
                            }, {
                                line: true
                            }, {
                                text: '添加行（<u>A</u>）',
                                id: 'add',
                                click: add_open_detail,
                                icon: 'add'
                            }, {
                                line: true
                            }, {
                                text: '删除（<u>D</u>）',
                                id: 'delete',
                                click: removeDetail,
                                icon: 'delete'
                            }, {
                                line: true
                            },
                                , {
                                text: '保存（<u>S</u>）',
                                id: 'save',
                                click: saveDetail,
                                icon: 'save'
                            }, {
                                line: true
                            }, {
                                text: '导入（<u>I</u>）',
                                id: 'import',
                                click: impDetailNew,
                                icon: 'up'
                            }, /* {
                                text: '下载导入模板（<u>B</u>）',
                                id: 'downTemplate',
                                click: downTemplate,
                                icon: 'down'
                            }, {
                                line: true
                            } */

                            ]
                        },
                    });

                    gridManagerDetail = $("#maingridDetail").ligerGetGridManager();

                }

                // 主表 编辑前记录  将行数据赋给 变量 addData

                function f_onBeforeEdit(e) {
                    addData = e;
                }

                // 主表 添加行 编辑后  将行数据赋给 变量 addData
                function f_onAfterEdit(e) {
                    addData = e;

                }


                // 明细表 编辑前记录   将行数据赋给 变量 addDataDetail 

                function f_onBeforeEditDetail(e) {
                    addDataDetail = e;
                }

                // 明细表 添加行 编辑后  将行数据赋给 变量 addData
                function f_onAfterEditDetail(e) {
                    addDataDetail = e;

                }

                // 主表 选定 项目、资金来源后  查询 项目负责人、期初预算余额、期初可用余额 并回显
                function setInfo() {
                    budg_year = year_input.getValue();
                    if (budg_year && addData.record.proj_name && addData.record.source_name) {
                        var formPara = {
                            budg_year: budg_year,

                            proj_id: addData.record.proj_name.split(",")[0],

                            source_id: addData.record.source_name,

                        };
                        ajaxJsonObjectByUrl("qureyInfoData.do?isCheck=false", formPara, function (responseData) {
                            if (responseData.state == "true") {

                                grid.updateRow(addData.rowindex, {
                                    con_emp_name: responseData.con_emp_name ? responseData.con_emp_name : "",
                                    b_remain_amount: responseData.b_remain_amount ? responseData.b_remain_amount : "",
                                    b_usable_amount: responseData.b_usable_amount ? responseData.b_usable_amount : ""
                                });
                            }

                        });
                    }
                }

                // 明细表 选定 项目、资金来源、支出项目后  查询 期初预算余额 并回显
                function setInfoDetail() {
                    budg_year = year_input.getValue();
                    if (budg_year && addDataDetail.record.proj_name && addDataDetail.record.source_name && addDataDetail.record.payment_item_name) {

                        var formPara = {
                            budg_year: budg_year,

                            proj_id: addDataDetail.record.proj_name.split(",")[0],

                            source_id: addDataDetail.record.source_name,

                            payment_item_id: addDataDetail.record.payment_item_name.split(",")[0],

                        };
                        ajaxJsonObjectByUrl("qureyInfoDataDetail.do?isCheck=false", formPara, function (responseData) {
                            if (responseData.state == "true") {
                                gridDetail.updateRow(addDataDetail.rowindex, {
                                    b_remain_amount: responseData.b_remain_amount ? responseData.b_remain_amount : ""
                                });
                            }
                        });
                    }
                }
                function selectTab(item) {
                    switch (item) {
                        case "tabitem1":
                            gridHos = "#maingrid";
                            loadHead();
                            $("#hosItem").show();
                            $("#deptItem").hide();
                            $(".name").hide();
                            $(".name1").show();

                            return;
                        case "tabitem2":
                            gridDept = "#maingridDetail";
                            loadHeadDetail();
                            $("#hosItem").hide();
                            $("#deptItem").show();
                            $(".name").show();
                            $(".name1").hide();
                            grid.reRender();
                            return;

                    }
                }

                function add_open() {
                    grid.addRowEdited({});

                }

                function savaData(e) {
                    if (!validateGrid(e)) {
                        return false;
                    }
                    budg_year = year_input.getValue();
                    var ParamVo = [];
                    var theData = e.record;

                    var formPara = {
                        budg_year: budg_year,

                        source_id: theData.source_name,

                        proj_id: theData.proj_name.split(",")[0],

                        budg_amount: theData.budg_amount,

                        in_amount: theData.in_amount,

                        cost_amount: theData.cost_amount,

                        b_remain_amount: theData.b_remain_amount,

                        b_usable_amount: theData.b_usable_amount
                    };


                    if (state != 1) {
                        ajaxJsonObjectByUrl(" addBudgProjBegain.do?isCheck=false", formPara, function (responseData) {
                            if (responseData.state == "true") {
                                query();

                            }
                        });
                    } else {
                        $.ligerDialog.warn('期初已记账！不能进行该操作.');
                    }
                }

                function add_open_detail() {
                    gridDetail.addRowEdited({});
                }

                function savaDataDetail(e) {
                    if (!validateGridDetail(e)) {
                        return false;
                    }
                    budg_year = year_input.getValue();
                    var ParamVo = [];
                    var theData = e.record;
                    var formParaDetail = {
                        budg_year: budg_year,

                        source_id: theData.source_name,

                        proj_id: theData.proj_name.split(",")[0],

                        budg_amount: theData.budg_amount,

                        payment_item_id: theData.payment_item_name.split(",")[0],

                        payment_item_no: theData.payment_item_name.split(",")[1],

                        cost_amount: theData.cost_amount,

                        b_remain_amount: theData.b_remain_amount

                    };

                    if (state != 1) { //判断状态是不是未记账
                        ajaxJsonObjectByUrl(" addBudgProjBegainDetail.do?isCheck=false", formParaDetail, function (responseData) {
                            if (responseData.state == "true") {
                                queryDatail();
                            }
                        });
                    } else {
                        $.ligerDialog.warn('期初已记账！不能进行该操作.');
                    }
                }
                //导入主表数据
                function impNew() {
                    parent.$.ligerDialog.open({
                        url: 'hrp/budg/project/begin/budgProjBegainImportPage.do?isCheck=false',
                        data: { columns: grid.columns, grid: grid }, height: 300, width: 450, title: '项目期初预算', modal: true, showToggle: false, showMax: true,
                        showMin: false, isResize: true, parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
                    });
                }


                //导入明细表数据
                function impDetailNew() {
                    parent.$.ligerDialog.open({
                        url: 'hrp/budg/project/begin/budgProjBegainDetailImportPage.do?isCheck=false',
                        data: { columns: grid.columns, grid: grid }, height: 300, width: 450, title: '项目期初预算明细', modal: true, showToggle: false, showMax: true,
                        showMin: false, isResize: true, parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
                    });
                }
                function validateGrid(e) {
                    var rowm = "";

                    if (!e.record.source_name) {
                        rowm += "[资金来源] 不能为空</br>";
                    }
                    if (!e.record.proj_name) {
                        rowm += "[项目名称] 不能为空</br>";
                    }
                    if (!e.record.b_remain_amount && !e.record.b_usable_amount && !e.record.budg_amount && !e.record.in_amount && !e.record.cost_amount) {
                        rowm += "不允许添加空数据</br>";
                    }
                    if (rowm) {
                        setTimeout(function () {
                            $.ligerDialog.warn(rowm);
                        }, 10)
                        return false;
                    } else {
                        return true;
                    }
                }

                function validateGridDetail(e) {
                    var rowm = "";

                    if (!e.record.source_name) {
                        rowm += "[资金来源] 不能为空</br>";
                    }
                    if (!e.record.proj_name) {
                        rowm += "[项目名称] 不能为空</br>";
                    }
                    if (!e.record.b_remain_amount && !e.record.budg_amount && !e.record.cost_amount) {
                        rowm += "不允许添加空数据</br>";
                    }
                    if (rowm) {
                        setTimeout(function () {
                            $.ligerDialog.warn(rowm);
                        }, 10)
                        return false;
                    } else {
                        return true;
                    }
                }
                function isObjEmpty(obj) {      //判断对象是否为空对象
//                    for (var i in obj) {
//                        return false;
//                    }
                	if(obj!=null&&obj!='{}')
                   	 	return false;
                	else
                		return true;
                }
                function save() {
                	if(!grid.isDataChanged){
                		 $.ligerDialog.warn('数据没有变更,无需保存');
                		 return false;
                	}
                    budg_year = year_input.getValue();
                    var addData = grid.getAdded();
                    var updateData = grid.getUpdated();
                    var ParamVo = [];
                    $(addData).each(function () {
                    	if(isNaN(this.b_remain_amount)||isNaN(this.b_usable_amount)||isNaN(this.budg_amount)||isNaN(this.in_amount)||isNaN(this.cost_amount)){
                    		$.ligerDialog.error('请填写所有数字数据');
                    		return;
                    	}
                    	ParamVo.push(
                            budg_year + "@" +
                            this.proj_name.split(",")[0] + "@" +
                            this.source_name + "@" +
                            this.b_remain_amount + "@" +
                            this.b_usable_amount + "@" +
                            this.budg_amount + "@" +
                            this.in_amount + "@" +
                            this.cost_amount
                        )
                    }); 
                    $(updateData).each(function () {
                    	if(isNaN(this.b_remain_amount)||isNaN(this.b_usable_amount)||isNaN(this.budg_amount)||isNaN(this.in_amount)||isNaN(this.cost_amount)){
                    		$.ligerDialog.error('请填写所有数字数据');
                    		return;
                    	}
                    	ParamVo.push(
                            budg_year + "@" +
                            this.proj_id + "@" +
                            this.source_id + "@" +
                            this.b_remain_amount + "@" +
                            this.b_usable_amount + "@" +
                            this.budg_amount + "@" +
                            this.in_amount + "@" +
                            this.cost_amount
                        )
                    }); 
                    if(ParamVo.length != 0){
	                    ajaxJsonObjectByUrl("updateBudgProjBegain.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
	                        if (responseData.state == "true") {
	                            query();
	                        }
	                    });
                    }
                    
                }
                function saveDetail() {
                    
                    budg_year = year_input.getValue();
                    if(!gridDetail.isDataChanged){
               		 	$.ligerDialog.warn('数据没有变更,无需保存');
               		 	return false;
               		}
                    var addData = gridDetail.getAdded();
                    var updateData = gridDetail.getUpdated();
                    var ParamVo = [];
					$(addData).each(function () {
						this.payment_item_no=this.proj_name.split(",")[0];
                            ParamVo.push(
                                budg_year + "@" +
                                this.proj_name.split(",")[0] + "@" +
                                this.source_name + "@" +
                                this.payment_item_name.split(",")[0] + "@" +
                                this.budg_amount + "@" +
                                this.b_remain_amount + "@" +
                                this.cost_amount + "@" +
                                this.payment_item_no
                            )
                        });
						$(updateData).each(function () {
	                        ParamVo.push(
	                        		this.budg_year + "@" +
	                                this.proj_id + "@" +
	                                this.source_id + "@" +
	                                this.payment_item_id + "@" +
	                                this.budg_amount + "@" +
	                                this.b_remain_amount + "@" +
	                                this.cost_amount + "@" +
	                                this.payment_item_no
	                        )
	                    });
                        ajaxJsonObjectByUrl("updateBudgProjBegainDetail.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {

                            if (responseData.state == "true") {
                                query();
                            }
                        });
                }
                function remove() {
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0) {
                        $.ligerDialog.error('请选择行');
                    } else {
                        var ParamVo = [];
                        budg_year = year_input.getValue();
                        $(data).each(function () {
                            ParamVo.push(
                                this.group_id + "@" +
                                this.hos_id + "@" +
                                this.copy_code + "@" +
                                this.proj_id + "@" +
                                this.source_id + "@" +
                                budg_year
                            )
                        });
                        if (state != 1) {
                            $.ligerDialog.confirm('确定删除吗?', function (yes) {
                                if (yes) {
                                    ajaxJsonObjectByUrl("deleteBeginProject.do", {
                                        ParamVo: ParamVo.toString()
                                    }, function (responseData) {
                                        if (responseData.state == "true") {
                                            query();
                                        }
                                    });
                                }
                            });
                        } else {

                            $.ligerDialog.warn('期初已记账！不能进行该操作.');
                        }

                    }
                }

                function removeDetail() {
                    var data = gridManagerDetail.getCheckedRows();
                    if (data.length == 0) {
                        $.ligerDialog.error('请选择行');
                    } else {
                        var ParamVo = [];
                        budg_year = year_input.getValue();
                        $(data).each(function () {
                            ParamVo.push(
                                this.group_id + "@" +
                                this.hos_id + "@" +
                                this.copy_code + "@" +
                                this.proj_id + "@" +
                                this.source_id + "@" +
                                this.payment_item_id + "@" +
                                budg_year
                            )
                        });
                        if (state != 1) {
                            $.ligerDialog.confirm('确定删除吗?', function (yes) {
                                if (yes) {
                                    ajaxJsonObjectByUrl("deleteBeginProjectDetail.do?isCheck=false", {
                                        ParamVo: ParamVo.toString()
                                    }, function (responseData) {
                                        if (responseData.state == "true") {
                                            queryDatail();
                                        }
                                    });
                                }
                            });
                        } else {
                            $.ligerDialog.warn('期初已记账！不能进行该操作.');
                        }
                    }
                }

                function imp() {

                    var index = layer.open({
                        type: 2,
                        title: '导入',
                        shadeClose: false,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['893px', '500px'],
                        content: 'importBeginProject.do?isCheck=false'
                    });
                    layer.full(index);
                }

                function downTemplate() {

                    location.href = "downTemplate.do?isCheck=false";
                }
                //审核的功能
                function audit() {
                    var data = gridManager.getCheckedRows();
                    var ParamVo = [];
                    if (data.length == 0) {
                        $.ligerDialog.error('请选择行');
                        return;
                    } else {


                        var order_nos = "";
                        var notice_nos = "";
                        var ParamVo = [];
                        var stateFlag = false;

                        $(data).each(function () {
                            if (this.state == '01') {

                                ParamVo.push(
                                    this.budg_year + "@" +
                                    this.proj_id + "@" +
                                    this.source_id + "@" +
                                    this.b_remain_amount + "@" +
                                    this.budg_amount + "@" +
                                    this.cost_amount + "@" +
                                    this.remain_amount + "@" +
                                    '02' + "@" +
                                    this.b_usable_amount + "@" +
                                    this.proj_name + "@" +
                                    this.source_name
                                )

                            } else {
                                stateFlag = true;
                            }


                        });

                        //发送状态单据不允许再次审核
                        if (stateFlag) {
                            $.ligerDialog.error("审核失败！" + order_nos + "单据已发送，不允许再次审核！");
                            return;
                        }


                        $.ligerDialog.confirm('确定要审核?', function (yes) {
                            if (yes) {
                                ajaxJsonObjectByUrl("BudgProjBegainAudit.do?isCheck=false", {
                                    ParamVo: ParamVo.toString()
                                }, function (responseData) {
                                    if (responseData.state == "true") {
                                        query();
                                    }
                                });
                            }
                        });
                    }

                }
                //消除审核的功能
                function unAudit() {
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0) {
                        $.ligerDialog.error('请选择行');
                        return;
                    } else {
                        var ParamVo = [];
                        var order_nos = "";
                        var notice_nos = "";
                        var ParamVo = [];
                        var flag = false;
                        $(data).each(function () {

                            if (this.state == '02') {
                                order_nos = order_nos + this.order_code + ",";

                                ParamVo.push(
                                    this.budg_year + "@" +
                                    this.proj_id + "@" +
                                    '01'
                                )
                            } else {
                                flag = true;
                            }


                        });
                        //发送的单据不允许销审
                        if (flag) {
                            $.ligerDialog.error("销审失败！" + notice_nos + "单据已发送，不允许销审！");
                            return;
                        }
                        $.ligerDialog.confirm('确定要销审?', function (yes) {
                            if (yes) {
                                ajaxJsonObjectByUrl("UnBudgProjBegainAudit.do?isCheck=false", {
                                    ParamVo: ParamVo.toString()
                                }, function (responseData) {
                                    if (responseData.state == "true") {
                                        query();
                                    }
                                });
                            }
                        });
                    }
                }


                function loadDict() {
                    //字典下拉框
                    //加载年度   
                    autocomplete("#budg_year", "../../queryBudgYear.do?isCheck=false", "id", "text", true, true, '', true);
                    //加载项目负责人
                    autocomplete("#con_emp_id", "../../queryConEmpId.do?isCheck=false", "id", "text", true, true);
                    //加载状态
                    autocomplete("#state", "../../queryBudgState.do?isCheck=false", "id", "text", true, true);
                    //资金来源
                    autocomplete("#source_id", "../../queryBudgSource.do?isCheck=false", "id", "text", true, true);
                    //支出项目下拉框
                    autocomplete("#payment_item_id", "queryBudgPaymentItemNo.do?isCheck=false", "idno", "textno", true, true);

                    $("#budg_year").ligerTextBox({
                        width: 160
                    });
                    $("#proj_name").ligerTextBox({
                        width: 160
                    });
                    $("#con_emp_id").ligerTextBox({
                        width: 160
                    });
                    $("#state").ligerTextBox({
                        width: 160
                    });
                }
                //键盘事件
                function loadHotkeys() {
                    hotkeys('Q', query);
                    hotkeys('A', add);
                    hotkeys('D', remove);
                    hotkeys('B', downTemplate);
                    hotkeys('I', imp);
                }
            </script>
            <script>
                var year_input, con_emp_select, source_select, state_select, payment_item_select;
                $(function () {
                    init();
                });

                function init() {
                    getData("../../queryBudgYear.do?isCheck=false", function (data) {
                        year_input = $("#year_input").etDatepicker({
                            defaultDate: data[0].text,
                            view: "years",
                            minView: "years",
                            dateFormat: "yyyy",
                            minDate: data[data.length - 1].text,
                            maxDate: data[0].text,
                            todayButton: false,
                            onChanged: function (value) {
                                query();
                                queryDatail();
                            }
                        });
                    });

                    con_emp_select = $("#con_emp_select").etSelect({
                        url: "../../queryConEmpId.do?isCheck=false",
                        defaultValue: "none",
                        onChange: query
                    });

                    source_select = $("#source_select").etSelect({
                        url: "../../queryBudgSource.do?isCheck=false",
                        defaultValue: "none",
                        onChange: query
                    });

                    state_select = $("#state_select").etSelect({
                        url: "../../queryBudgState.do?isCheck=false",
                        defaultValue: "none",
                        onChange: query
                    })

                    payment_item_select = $("#payment_item_select").etSelect({
                        url: "queryBudgPaymentItemNo.do?isCheck=false",
                        valueField: "idno",
                        labelField: "textno",
                        defaultValue: "none",
                        onChange: query
                    });

                }

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
                    });
                }
            </script>
        </head>

        <body style="padding: 0px; overflow: hidden;">
            <div id="pageloading" class="l-loading" style="display: none"></div>
            <div id="toptoolbar"></div>
            
            <div class="main">
                <table class="table-layout">
                    <tr>
                        <td class="label">预算年度：</td>
                        <td class="ipt">
                            <input type="text" id="year_input">
                        </td>
                        <td class="label">项目名称：</td>
                        <td class="ipt">
                            <input type="text" id="proj_name_input" style="width:180px;">
                        </td>
                        <td class="label">负责人：</td>
                        <td class="ipt">
                            <select name="" id="con_emp_select" style="width:180px;"></select>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">资金来源：</td>
                        <td class="ipt">
                            <select name="" id="source_select" style="width:180px;"></select>
                        </td>
                        <td class="label">状态：</td>
                        <td class="ipt">
                            <select name="" id="state_select" style="width:180px;"></select>
                        </td>
                        <td class="label">支出项目：</td>
                        <td class="ipt">
                            <select name="" id="payment_item_select" style="width:180px;"></select>
                        </td>
                    </tr>
                </table>
            </div>


            <div id="item_tab" style="width: 100%;overflow:hidden; border:1px solid #A3C0E8; ">
                <div id="maingrid" title="期初项目预算"></div>
                <div id="maingridDetail" title="期初项目预算明细"></div>
            </div>
        </body>

        </html>