<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>

<style>
   input {
      border: 1px solid #aecaf0;
      height: 20px;
  } 
</style>
<script type="text/javascript">
  var grid;
  var gridManager = null;
  var userUpdateStr;
  $(function () {
      var $wdate = document.getElementsByClassName("Wdate");
      // 给每个wdatede表单添加聚焦事件，加载日期框
      for (var i = 0, len = $wdate.length; i < len; i++) {
          $wdate[i].onfocus = function () {
              WdatePicker({
                  isShowClear: true,
                  readOnly: false,
                  dateFmt: 'yyyy-MM-dd'
              });
          }
      }
      loadDict() //加载下拉框
      //加载数据
      loadHead(null);
      loadHotkeys();

  });
  //查询
  function query() {
      grid.options.parms = [];
      grid.options.newPage = 1;
      //根据表字段进行添加查询条件
      grid.options.parms.push({
          name: 'level_code',
          value: liger.get("level_code").getValue()
      });
      grid.options.parms.push({
          name: 'app_date',
          value: $("#app_date").val()
      });
      grid.options.parms.push({
          name: 'end_app_date',
          value: $("#end_app_date").val()
      });
      grid.options.parms.push({
          name: 'type_code',
          value: liger.get("type_code").getValue()
      });
      grid.options.parms.push({
          name: 'proj_name',
          value: $("#proj_name").val().toUpperCase()
      });
      grid.options.parms.push({
          name: 'proj_state',
          value: liger.get("proj_state").getValue()
      });
      grid.options.parms.push({
          name: 'con_emp_id',
          value: liger.get("con_emp_id").getValue()
      });
      grid.options.parms.push({
          name: 'is_stop',
          value: liger.get("is_stop").getValue()
      });
      //加载查询条件
      grid.loadData(grid.where);
  }

  function loadHead() {
      grid = $("#maingrid").ligerGrid({
          columns: [
              {
                  display: '项目编码',
                  name: 'proj_code',
                  align: 'left',
                  width: 100
              },
              {
                  display: '项目名称',
                  name: 'proj_name',
                  align: 'left',
                  width: 300
              },
              {
                  display: '项目类型',
                  name: 'type_name',
                  align: 'left',
                  width: 100
              },
              {
                  display: '项目级别',
                  name: 'level_name',
                  align: 'left',
                  width: 80
              },
              {
                  display: '项目负责人',
                  name: 'con_emp_name',
                  align: 'left',
                  width: 80
              },
              {
                  display: '财务负责人',
                  name: 'acc_emp_name',
                  align: 'left',
                  width: 80
              },
              {
                  display: '填报部门',
                  name: 'dept_name',
                  align: 'left',
                  width: 100
              },
              {
                  display: '填报人',
                  name: 'app_emp_name',
                  align: 'left',
                  width: 80
              },
              {
                  display: '填报日期',
                  name: 'app_date',
                  align: 'left',
                  width: 80
              },
              {
                  display: '立项日期',
                  name: 'set_up_date',
                  align: 'left',
                  width: 80
              },
              {
                  display: '结题日期',
                  name: 'complete_date',
                  align: 'left',
                  width: 80
              },
              {
                  display: '报销截止日期',
                  name: 'pay_end_date',
                  align: 'left',
                  width: 80
              },
              {
                  display: '中止日期',
                  name: 'sespend_date',
                  align: 'left',
                  width: 80
              },
              {
                  display: '项目状态',
                  name: 'value_name',
                  align: 'left',
                  width: 80
              },
              {
                  display: '是否停用',
                  name: 'is_stop',
                  align: 'center',
                  width: 80,
                  render: function (item) {
                      if (parseInt(item.is_stop) == 1) {
                          return '是';
                      }
                      return '否';
                  }
              },
              {
                  display: '是否结转',
                  name: 'is_carry',
                  align: 'left',
                  width: 80,
                  render: function (item) {
                      if (parseInt(item.is_carry) == 1) {
                          return '是';
                      } else if (parseInt(item.is_carry) == 0) {
                          return '否';
                      }

                  }
              }
          ],
          dataAction: 'server',
          dataType: 'server',
          usePager: true,
          url: 'queryHosProj.do',
          width: '100%',
          height: '100%',
          checkbox: true,
          rownumbers: true,
          selectRowButtonOnly: true,
          toolbar: {
              items: [
                  {
                      text: '查询（<u>Q</u>）',
                      id: 'search',
                      click: query,
                      icon: 'search'
                  },
                  { line: true },
                  {
                      text: '添加（<u>A</u>）',
                      id: 'add',
                      click: add_open,
                      icon: 'add'
                  },
                  { line: true },
                  {
                      text: '删除（<u>D</u>）',
                      id: 'delete',
                      click: remove,
                      icon: 'delete'
                  },
                  { line: true },
                  {
                      text: '下载导入模板（<u>B</u>）',
                      id: 'downTemplate',
                      click: downTemplate,
                      icon: 'down'
                  },
                  { line: true },
                  {
                      text: '导入（<u>I</u>）',
                      id: 'import',
                      click: imp,
                      icon: 'up'
                  }
              ]
          },
          onDblClickRow: function (rowdata, rowindex, value) {
              openUpdate(
                  rowdata.proj_id
              );
          }
      });

      gridManager = $("#maingrid").ligerGetGridManager();
  }

  function add_open() {

      $.ligerDialog.open({
          url: 'hosProjAddPage.do?isCheck=false&',
          data: {},
          height: 510,
          width: 800,
          title: '添加',
          modal: true,
          showToggle: false,
          showMax: false,
          showMin: false,
          isResize: true,
          buttons: [
              {
                  text: '确定',
                  onclick: function (item, dialog) {
                      dialog.frame.savehosProj();
                  },
                  cls: 'l-dialog-btn-highlight'
              },
              {
                  text: '取消',
                  onclick: function (item, dialog) {
                      dialog.close();
                  }
              }
          ]
      });
  }

  function remove() {

      var data = gridManager.getCheckedRows();
      if (data.length == 0) {
          $.ligerDialog.error('请选择行');
      } else {
          var ParamVo = [];
          $(data).each(function () {
              ParamVo.push(
                  this.group_id + "@" +
                  this.hos_id + "@" +
                  this.proj_id
              )
          });
          $.ligerDialog.confirm('确定删除?', function (yes) {
              if (yes) {
                  ajaxJsonObjectByUrl("deleteHosProj.do", {
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

  function imp() {
      var index = layer.open({
          type: 2,
          title: '导入',
          shadeClose: false,
          shade: false,
          maxmin: true, //开启最大化最小化按钮
          area: ['893px', '500px'],
          content: 'hosProjImportPage.do?isCheck=false'
      });
      layer.full(index);
  }

  function downTemplate() {
      location.href = "downTemplate.do?isCheck=false";
  }

  function openUpdate(obj) {
      $.ligerDialog.open({
          url: 'hosProjUpdatePage.do?isCheck=false&proj_id=' + obj,
          height: 510,
          width: 800,
          title: '修改',
          modal: true,
          showToggle: false,
          showMax: false,
          showMin: false,
          isResize: true,
          buttons: [{
              text: '确定',
              onclick: function (item, dialog) {
                  dialog.frame.saveHosProj();
              },
              cls: 'l-dialog-btn-highlight'
          }, {
              text: '取消',
              onclick: function (item, dialog) {
                  dialog.close();
              }
          }]
      });
  }

  function loadDict() {
      //字典下拉框
      autocomplete("#level_code", "../../../sys/queryProjLevelDict.do?isCheck=false", "id", "text", true, true); //项目级别
      autocomplete("#con_emp_id", "../../../sys/queryEmpDict.do?isCheck=false", "id", "text", true, true); //项目负责人
      autocomplete("#type_code", "../../../sys/queryProjTypeDict.do?isCheck=false", "id", "text", true, true); //项目类别

      autocomplete("#proj_state", "../../qureyProjStateSelect.do?isCheck=false", "id", "text", true, true);
      
      $("#is_stop").ligerComboBox({width: 80,data: [{text: '否',id: '0'}, {text: '是',id: '1'}],value: '0'}) ;
      
      $("#type_code").ligerTextBox({width:180});
      $("#con_emp_id").ligerTextBox({width:203});
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
  var proj_name_select, date_input, proj_state_select, proj_type_select, proj_level_select, con_emp_select, is_stop_check;
  $(function () {
      //init();
  });

  function init() {
      date_input = $("#date_input").etDatepicker({
          range: true,
      });

      // proj_name_select = $("#proj_name_select").etSelect({
      //     url: "",
      //     defaultValue: "none"
      // });

      proj_state_select = $("#proj_state_select").etSelect({
          url: "../../qureyProjStateSelect.do?isCheck=false",
          defaultValue: "none"
      });

      proj_type_select = $("#proj_type_select").etSelect({
          url: "../../../sys/queryProjTypeDict.do?isCheck=false",
          defaultValue: "none"
      });

      proj_level_select = $("#proj_level_select").etSelect({
          url: "../../../sys/queryProjLevelDict.do?isCheck=false",
          defaultValue: "none"
      });

      con_emp_select = $("#con_emp_select").etSelect({
          url: "../../../sys/queryEmpDict.do?isCheck=false",
          defaultValue: "none"
      });

      is_stop_check = $("#is_stop_check").etCheck({

      })

  }
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
    <div id="pageloading" class="l-loading" style="display: none"></div>

    <div id="toptoolbar"></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit">
        <tr>
            <td align="left" class="l-table-edit-td"><input name="proj_id" type="hidden" id="proj_id" /></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td" style="padding-left:20px;">项目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="proj_name" id="proj_name" style="width: 180px"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td" style="padding-left:20px;">项目时间：</td>
            <td>
            	<table>
            		<tr>
            			<td align="left" class="l-table-edit-td"><input name="app_date" id="app_date" class="Wdate" style="width: 90px">至</td>
            			<td align="left" class="l-table-edit-td"><input name="end_app_date" id="end_app_date" class="Wdate" style="width: 90px"></td>
            		</tr>
            	</table>
            </td>
            <td align="right" class="l-table-edit-td" style="padding-left:20px;">项目状态：</td>
            <td align="left" class="l-table-edit-td"><input name="proj_state" id="proj_state" /></td>
            <td align="left"></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td" style="padding-left:20px;">项目类型：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" id="type_code" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td" style="padding-left:20px;">项目负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="con_emp_id" id="con_emp_id" /></td>
            <td align="right" class="l-table-edit-td" style="padding-left:20px;">项目级别：</td>
            <td align="left" class="l-table-edit-td"><input name="level_code" id="level_code" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td" style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" id="is_stop" /></td>
            <td align="left"></td>
        </tr>
    </table>
    <!-- <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">项目名称：</td>
                <td class="ipt">
                    <input type="text" id="proj_name_input" style="width:150px;">
                </td>
                <td class="label">项目时间：</td>
                <td class="ipt">
                    <input type="text" id="date_input" style="width:180px;">
                </td>
                <td class="label">项目状态：</td>
                <td class="ipt">
                    <select name="" id="proj_state_select" style="width:150px;"></select>
                </td>
                <td class="label">项目类型：</td>
                <td class="ipt">
                    <select name="" id="proj_type_select" style="width:150px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">项目级别：</td>
                <td class="ipt">
                    <select name="" id="proj_level_select" style="width:150px;"></select>
                </td>
                <td class="label">负责人：</td>
                <td class="ipt">
                    <select name="" id="con_emp_select" style="width:180px;"></select>
                </td>
                <td class="label">
                    <label for="is_stop_check">是否停用：</label>
                </td>
                <td>
                    <input type="checkbox" name="" id="is_stop_check">
                </td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </div> -->
    <div id="maingrid"></div>

</body>

</html>