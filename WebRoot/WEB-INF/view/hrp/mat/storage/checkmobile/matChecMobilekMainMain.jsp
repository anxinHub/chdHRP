<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;" xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
    <script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;

    $(function() {
        // 加载下拉框
        loadDict();
        // 加载数据
        loadHead();
        // 设置键盘热键
        loadHotkeys();
        // 查询
        query();
    });
    //查询
    function query() {
        grid.options.parms = [];
        grid.options.newPage = 1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({ name: 'create_date_start', value: $("#create_date_start").val() });
        grid.options.parms.push({ name: 'create_date_end', value: $("#create_date_end").val() });
        grid.options.parms.push({ name: 'store_id', value: liger.get("store_id").getValue().split(",")[0] });
        grid.options.parms.push({ name: 'is_com', value: liger.get("is_com").getValue() });
        grid.options.parms.push({ name: 'emp_id', value: liger.get("emp_id").getValue() });
        grid.options.parms.push({ name: 'upload_date_start', value: liger.get("upload_date_start").getValue() });
        grid.options.parms.push({ name: 'upload_date_end', value: liger.get("upload_date_end").getValue() });

        //加载查询条件
        grid.loadData(grid.where);
        //$("#resultPrint > table > tbody").empty();
    }


    function loadHead() {
        grid = $("#maingrid").ligerGrid({
            columns: [{display: '盘点单号', name: 'CHECK_CODE', align: 'left', width: 110},
                { display: '摘要', name: 'NOTE', align: 'left', width: 100 },
                { display: '仓库', name: 'STORE_NAME', align: 'left', width: 100 },
                { display: '是否代销', name: 'IS_COM', render: function(r, i, v) { return v == '1' ? '是' : '否' }, align: 'center', width: 60 },
                { display: '制单日期', name: 'CREATE_DATE', align: 'left', width: 100 },
                { display: '盘点人', name: 'EMP_NAME', align: 'left', width: 100 },
                { display: '状态', name: 'STATE', render: stateRender, align: 'center', width: 60 },
                { display: '上传日期', name: 'UPLOAD_DATE', align: 'left', width: 80 },
                { display: '物料编码', name: 'INV_CODE', align: 'left', width: 80 },
                { display: '物料名称', name: 'INV_NAME', align: 'left', width: 80 },
                { display: '规格型号', name: 'INV_MODEL', align: 'left', width: 80 },
                { display: '单位', name: 'UNIT_NAME', align: 'center', width: 50 },
                { display: '条形码', name: 'BAR_CODE', align: 'left', width: 100 },
                { display: '价格', name: 'PRICE', align: 'left', width: 80 },
                { display: '账面数量', name: 'CUR_AMOUNT', align: 'left', width: 50 },
                { display: '盘点数量', name: 'CHK_AMOUNT', align: 'left', width: 50 },
                { display: '供货商', name: 'SUP_NAME', align: 'left', width: 80 },
                { display: '生产厂家', name: 'FAC_NAME', align: 'left', width: 80 },
                { display: '消毒日期', name: 'DISINFECT_DATE', align: 'left', width: 80 }
            ],
            dataAction: 'server',
            dataType: 'server',
            usePager: true,
            url: 'queryMatCheckMobileMain.do',
            width: '100%',
            height: '100%',
            rownumbers: true,
            delayLoad: true,
            selectRowButtonOnly: true, //heightDiff: -10,
            toolbar: {
                items: [
                    { text: '查询', id: 'search', click: query, icon: 'search' },
                    { line: true },
                    { text: '生成', id: 'add', click: add_open, icon: 'add' },
                    { line: true },
//                     { text: '删除', id: 'delete', click: remove, icon: 'delete' },
//                     { line: true },
                ]
            }
        });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function stateRender(r, i, v) {
        if (v == 1) {
            return "生成";
        } else if (v == 2) {
            return "上传";
        } else if (v == 3){
            return "结束";
        } else {
        	return "-";
        }
    }


    function add_open() {
        $.ligerDialog.open({
            title: '高值耗材盘点单生成',
            // height: $(window).height(),
            width: 300,
            height: 300,
            url: 'matCheckMobileMainAddPage.do?isCheck=false',
            modal: true,
            showMax: false,
            showMin: false,
            isResize: true,
            allowClose: true,
			
            buttons: [{
                text: '生成',
                onclick: function(item, dialog) {

                	// 获取子窗口数据方法
//                     var create_date = $(dialog.frame.document).contents().find("#add_check_date").val();
                    var create_date = dialog.frame.liger.get("add_check_date").getValue();
                    var store_id = dialog.frame.liger.get("add_store_id").getValue().split(",")[0];
                    if (!create_date) {
                        $.ligerDialog.error('请选择盘点日期!');
                        return;
                    }

                     var formPara = {
                         create_date: create_date,
                         store_id: store_id,
                     }
                     
                     ajaxJsonObjectByUrl("existMatCheckMobileByCreateDate.do?isCheck=false", formPara, function(responseData) {
                    	// 盘点日期内已经存在 上传过的数据
                    	 if(responseData.isExist == 1){
                    		 $.ligerDialog.confirm('当前盘点日期已生成过记录是否覆盖?', function(yes) {
                    			 if(yes){
                    				 ajaxJsonObjectByUrl("addMatCheckMobileMain.do?isCheck=false", formPara, function(responseData) {
                    					 grid.reload();
                    				 });
                    			 }
                    		 });
                    	 } else {
                    		 ajaxJsonObjectByUrl("addMatCheckMobileMain.do", formPara, function(responseData) {
			                     grid.reload();
			                 });
                    	 }
                     });
                },
                cls: 'l-dialog-btn-highlight'
            }, {
                text: '取消',
                onclick: function(item, dialog) {
                    dialog.close();
                }
            }],
            name: 'checkMobileAddIFrame' //用于parent弹出层调用本页面的方法或变量
        });
    }

//     function remove() {

//         var data = gridManager.getCheckedRows();

//         if (data.length == 0) {
//             $.ligerDialog.error('请选择要删除的数据');
//         } else {
//             var ParamVo = [];
//             $(data).each(function() {
//                     ParamVo.push(
//                         this.HOS_ID + "@" +
//                         this.GROUP_ID + "@" +
//                         this.COPY_CODE + "@" +
//                         this.CHECK_ID + "@" +
//                         this.IS_COM + "@" +
//                         this.DETAIL_ID
//                     )
//                 });
            
//             console.log(ParamVo)
            
//             $.ligerDialog.confirm('确定删除选中的数据?', function(yes) {
//                 if (yes) {
//                     ajaxJsonObjectByUrl("deleteMatCheckMain.do", { ParamVo: ParamVo.toString() }, function(responseData) {
//                         if (responseData.state == "true") {
//                             query();
//                         }
//                     });
//                 }
//             });
//         }
//     }

    function loadDict() {
        //字典下拉框
        //autocompleteAsync("#store_id", "../../queryMatStore.do?isCheck=false", "id","text", true, true, "", true, false, '180');
        autocompleteAsync("#store_id", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, { read_or_write: 1 }, false, false, '180');

        // 日期初始化
        $("#create_date_start").ligerTextBox({ width: 100 });
        autodate("#create_date_start", "yyyy-mm-dd", "month_first");
        $("#create_date_end").ligerTextBox({ width: 100 });
        autodate("#create_date_end", "yyyy-mm-dd", "month_last");
        $("#upload_date_start").ligerTextBox({ width: 100 });
        autodate("#create_date_start", "yyyy-mm-dd", "month_first");
        $("#upload_date_end").ligerTextBox({ width: 100 });
        autodate("#create_date_end", "yyyy-mm-dd", "month_last");

        // 员工
        autocomplete("#emp_id", "../../queryMatEmp.do?isCheck=false", "id", "text", true, true, "", false, false, '180');

        // 是否代销
        $("#is_com").ligerComboBox({ data: [{ id: 0, text: '否' }, { id: 1, text: '是' }], isShowCheckBox: false });
    }
    //键盘事件
    function loadHotkeys() {

        hotkeys('Q', query);

        hotkeys('A', add);
//         hotkeys('D', remove);


//         hotkeys('E', exportExcel);


    }
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
    <div id="pageloading" class="l-loading" style="display: none"></div>
    <div id="toptoolbar"></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
            <td align="right" class="l-table-edit-td"> <span style="color:red">*</span>盘点日期：</td>
            <td align="left" class="l-table-edit-td">
                <table>
                    <tr>
                        <td align="left">
                            <input class="Wdate" name="create_date_start" id="create_date_start" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
                        </td>
                        <td align="right" class="l-table-edit-td">至：</td>
                        <td align="left" class="l-table-edit-td">
                            <input class="Wdate" name="create_date_end" id="create_date_end" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
                        </td>
                    </tr>
                </table>
            </td>
            <td align="right" class="l-table-edit-td">仓 库：</td>
            <td align="left" class="l-table-edit-td">
                <input name="store_id" type="text" id="store_id" ltype="text" />
            </td>
            <td align="right" class="l-table-edit-td" ">是否代销：</td>
          <td align=" left" class="l-table-edit-td">
                <input name="is_com" type="text" id="is_com" />
            </td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td">上传日期：</td>
            <td align="left" class="l-table-edit-td">
                <table>
                    <tr>
                        <td align="left">
                            <input class="Wdate" name="upload_date_start" id="upload_date_start" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                        </td>
                        <td align="right" class="l-table-edit-td">至：</td>
                        <td align="left" class="l-table-edit-td">
                            <input class="Wdate" name="upload_date_end" id="upload_date_end" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                        </td>
                    </tr>
                </table>
            </td>
            <td align="right" class="l-table-edit-td">盘点用户：</td>
            <td align="left" class="l-table-edit-td">
                <input name="emp_id" type="text" id="emp_id" ltype="text" />
            </td>
            <td></td>
            <td></td>
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