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
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<script src="<%=path%>/lib/et_assets/common.js"></script>
<script src="<%=path%>/lib/et_assets/hr/common.js"></script>
<script src="<%=path%>/lib/hrp/hr/hr.js"></script>

<script type="text/javascript">
    var configgrid, designCode;
	$(function() {
		toptoolbar = $("#toptoolbar").ligerToolBar(
            { items: [
                {text: '保存', id:"save", click: function (item){save()}, icon:'save'}, { line:true },
            ]}
        );
		loadGrid();
	});
	
    function loadGridData(designCode){
    	designCode = designCode;
    	configgrid.reload();
    	ajaxPostData({
            url: 'queryDesignQueryPage.do?isCheck=false',
            data: {
                'design_code': designCode
            },
            delayCallback: true,
            success: function(data) {
            	
            	if(data){
            	    configgrid.appendRange(data);
            	}
                if(designCode){
                    toptoolbar.setEnabled("save");
                }else{
                    toptoolbar.setDisabled("save");
                }
            }
        })
    	
    }
	
	function loadGrid() {
		configgrid= $("#configgrid").ligerGrid({
            columns: [
            	{display: '数据表编码', name: 'tab_code', align: 'left', width: 160,
                    render : function(rowdata, rowindex, value) {
                        return value;
                    }
                }, 
                {display: '数据表名称', name: 'tab_name', align: 'left', width: 160,
                    render : function(rowdata, rowindex, value) {
                        return value;
                    }
                },
                {display: '数据列编码', name: 'col_code', align: 'left', width: 200,
                    render : function(rowdata, rowindex, value) {
                        return value;
                    }
                }, 
                {display: '数据列名称', name: 'col_name', align: 'left', width: 160,
                    render : function(rowdata, rowindex, value) {
                        return value;
                    }
                },
                {display: '是否显示', name: 'is_view', align: 'center', width: 80,
                    render : function(rowdata, rowindex, value) {
                        if(value == 0){
                            return "<input name='is_view' type='checkbox' id='is_view"+rowdata.col_code+"'"
                                +" style='margin-top:5px;' />";
                        }
                        return "<input name='is_view' type='checkbox' checked='checked' id='is_view"+rowdata.col_code+"'" 
                            +" style='margin-top:5px;' />";
                    }
                },
                {display: '排序', name: 'sort', align: 'left', width: 80, editor:{type: 'text'},
                    render : function(rowdata, rowindex, value) {
                        return value;
                    }
                },
            ],
            data: null, usePager : false, width : '100%', height : '100%', checkbox : true, rownumbers : true, 
            enabledEdit: true, selectRowButtonOnly : true, isAddRow: false
        });
	}
	
	function save() {
        var configgrid = $("#configgrid").ligerGetGridManager();
        configgrid.endEdit();
        var gridData = configgrid.getData();
        var design_code = window.parent.actionNodeID;
        if (design_code != null && design_code != '') {
        	$(gridData).each(function(i) {
                this.is_view = $("#is_view" + this.col_code, $("#configgrid")).is(":checked") ? '1' : '0';
            });
            ajaxPostData({
                url: 'saveDesignQueryPage.do?isCheck=false',
                data: {
                    'design_code': design_code,
                    'design_query_page': JSON.stringify(gridData)
                },
                delayCallback: true,
                success: function(data) {
                }
            })
        } else {
            $.ligerDialog.error('请选择树节点');
        }
    }
</script>
</head>
<body style="padding: 0px;">
    <div position="center" title="">
        <div id="toptoolbar"></div>
        <div id="configgrid"></div>  
    </div>
</body>
</html>
