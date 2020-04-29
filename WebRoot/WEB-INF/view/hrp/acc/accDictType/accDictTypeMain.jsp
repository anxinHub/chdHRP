<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String path = request.getContextPath(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

	var griddic;
	var gridManager = null;
	var userUpdateStr;
	var curGridData = {}

	$(function () {
		loadDict();
  		loadHead(null); //加载数据
	});
      
      
	var toolbarData =  {
		items: [
				{text: '查询', id: 'query', click: querydic, icon: 'search'},
               {line: true},
               {text: '添加', id: 'add', click: adddic, icon: 'add'},
               {line: true},
               {text: '删除', id: 'del', click: deldic, icon: 'delete'},
               {line: true},
               {text: '打印', id: 'print', click: printdic, icon: 'print'}
           ]
	}
	
	var girdColumnDict = [
		{display: '字典编码', name: 'dict_code', align: 'left',
			render: function (rowdata, rowindex, value) {
				var curcode = rowdata.dict_code;
				//将查询值保存到curGridData
				curGridData[curcode] = rowdata
				var ret = ' <a href=javascript:upddic("'+ curcode +'")>'+rowdata.dict_code+'</a>'
          		return ret
		}},
		{display: '字典名称', name: 'dict_name', align: 'left'},
		{display: '业务类型', name: 'ywtype_name', align: 'left'},
		{display: '类型名称', name: 'type_name', align: 'left'},
		{display: '状态', name: 'is_stop', align: 'left'},
		{display: '备注', name: 'note', align: 'left', width: 250},
	]
	
	var gridDataDict = {
		columns: girdColumnDict,
		toolbar: toolbarData,
		dataAction: 'server', dataType: 'server', usePager: false, isAddRow: false,
		url: 'queryDictType.do', width: '100%', height: '100%',
		checkbox: true, rownumbers: true, delayLoad: true, frozen: false,
		selectRowButtonOnly: true, heightDiff: 30,
	}

	function loadHead() {
          griddic = $("#maingrid").ligerGrid(gridDataDict);
          gridManager = $("#maingrid").ligerGetGridManager();
    }

	function loadDict() {
        autocomplete("#yw_type","../queryAccYewuType.do?isCheck=false","id", "text",true,true,'',false,'01001',100)
    }
	
    //查询
	function querydic() {
		if($("#yw_type").val()==""||$("#yw_type").val()==null){
			$.ligerDialog.warn('业务类型不能为空！');
			return;
		}
		griddic.options.parms = [];
		griddic.options.newPage = 1;
        //根据表字段进行添加查询条件
		griddic.options.parms.push({
        	  name: 'yw_type', 
        	  value: liger.get("yw_type").getValue()
		});
         griddic.options.parms.push({
        	 name: 'yw_dict', 
        	 value: $("yw_dict").val()
        });
        griddic.options.parms.push({
        	name: 'data_type', 
        	value: $("#data_type").val()
        });
        //加载查询条件
        griddic.loadData(griddic.where);
	}
    
    //删除
    function deldic() {
		var selectedArr = griddic.selected
		var datadel = gridManager.getCheckedRows();
          if (datadel.length == 0) {
              $.ligerDialog.error('请选择行');
          } else {
              var ParamVo = [];
              $(datadel).each(function () {
                  ParamVo.push(  //表的主键  
                      this.dict_code + "@" +
                      this.table_code
                   )
              });
              var ParamVoData = ParamVo.toString()
              $.ligerDialog.confirm('确定删除?', function (yes) {
                  if (yes) {
                      ajaxJsonObjectByUrl("deleteDictType.do", {ParamVo: ParamVo.toString()}, function (responseData) {
                          if (responseData.state == "true") {
                              querydic();
                          }
                      });
                  }
              });
          }
              
      }
    
    
    //打印
	function printdic () {
    	if(griddic.getData()==null){
    		$.ligerDialog.warn("请先查询数据！");
			return;
		}
    	var heads={}; 
    	var printPara={
            title: "业务字典",//标题
            columns: JSON.stringify(griddic.getPrintColumns()),//表头
            class_name: "com.chd.hrp.acc.serviceImpl.accDictType.AccDictTypeServiceImpl",
            method_name: "queryDictPrint",
            bean_name: "accDictTypeService",
            heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
            foots: '',//表尾需要打印的查询条件,可以为空 
        };
      //执行方法的查询条件
   		$.each(griddic.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
    	});
        officeGridPrint(printPara);
    }

    
    //添加
    function adddic() {
		var ywtype_name = $("#yw_type").val()
		var ywtype_code = liger.get("yw_type").getValue()
		if($("#yw_type").val()==""||$("#yw_type").val()==null){
			$.ligerDialog.warn('业务类型不能为空！');
			return;
		}
    	
        var url = 'hrp/acc/accdicttype/dictAddPage.do?isCheck=false'
        var addtit = "添加"	
        openaddupdat(addtit,url,{ywtype_name:ywtype_name,ywtype_code:ywtype_code})
    }
    //修改
    function upddic(curcode) {
        var url = 'hrp/acc/accdicttype/dictUpdatePage.do?isCheck=false'
        var updtit = "修改"		
        openaddupdat(updtit,url,curGridData[curcode])
    }
    //调用添加修改页面
    function openaddupdat(title,url,curData){
        parent.$.ligerDialog.open({
            height: 480,width: 660,modal: true,showToggle: false,
            showMax: false,showMin: false,isResize: true,
            title: title,
            url: url,
            data:curData,
            buttons: [{
                text: '确定', onclick: function (item, dialog) {
                	var forms = dialog.frame.$("#acc_element_sf").serialize();
					dialog.frame.save(forms,dialog)
                    querydic();
                }, cls: 'l-dialog-btn-highlight'
            }, {
                text: '取消', onclick: function (item, dialog) {
                    dialog.close();
                }
            }]
        });
    }

  </script>

</head>
<body style="padding: 0px; overflow: hidden;">
<div id="pageloading" class="l-loading" style="display: none"></div>
<table cellpadding="0" cellspacing="0" class="l-table-edit">
  <tr>
    <td align="right" class="l-table-edit-td" style="padding-left: 20px;">业务类型：</td>
    <td align="left" class="l-table-edit-td">
      <input name="yw_type" type="text" id="yw_type" ltype="text"/>
    </td>
    <td align="left"></td>
    
    <td align="right" class="l-table-edit-td" style="padding-left: 20px;">业务字典：</td>
    <td align="left" class="l-table-edit-td">
      <input name="yw_dict" type="text" id="yw_dict" ltype="text"/>
    </td>
    <td align="left"></td>
    
    <td align="right" class="l-table-edit-td" style="padding-left: 20px;">数据类型：</td>
    <td align="left" class="l-table-edit-td">
      <input name="data_type" type="text" id="data_type" ltype="text"/>
    </td>
    <td align="left"></td>
  </tr>
</table>
<div id="maingrid"></div>
</body>
</html>
