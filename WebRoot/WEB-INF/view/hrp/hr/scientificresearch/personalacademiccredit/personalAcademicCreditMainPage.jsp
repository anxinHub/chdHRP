<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,dialog,grid,datepicker,select,validate" name="plugins" />
	</jsp:include>
    <script>
        var year;
        var grid;
        var formValidate;

        var initValidate = function () {
            formValidate = $.etValidate({
                items: [
                    { el: $("#year"), required: true },
                    { el: $("#acade_honor"), required: true },
                ]
            });
        };

        var initFrom = function () {
        	//$('#hos_name').hide();
        	
            year = $("#year").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                onChange: function (value) {
                    // 改变年度，请求获取满分
                    ajaxPostData({
                        url: 'queryAcadeHonor.do',
                        data: { year: value },
                        success: function (res) {
                            $("#acade_honor").val(res.acade_honor)
                        }
                    });
                    setTimeout(function () {
                        query();
                    }, 10);
                },
                defaultDate: true
            });

            $("#glory_name").on('change', function () {
                query();
            })
        };
        
        var query = function () {
            var params = [
                { name: 'year', value: year.getValue() },
                { name: 'glory_name', value: $("#glory_name").val() },
            ];
            grid.loadData(params);
        };
        
        var save = function () {
            var ParamVo = [];
            var acade_honor = $("#acade_honor").val();
            var isPass = grid.validateTest({
                required: {
                	honor_code: true,
                	field_col_name :true
                }
            });
            if (!isPass) {
                return;
            }
            
            var nos = "";
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return;
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      if(acade_honor){
                    	  if(parseInt(rowdata.score)>parseInt(acade_honor)){
                    		  nos = nos + rowdata.honor_code + " "+rowdata.field_col_name+ ",";
                    	  }
                      }
                      ParamVo.push(rowdata);
                  });
            }
            
            //验证重复数据
        	if (!grid.checkRepeat(grid.selectGet(),['honor_code','field_col_name'])) {
               	return;
           	}
            
            if(nos != ''){
            	$.etDialog.error("保存失败！"+nos+" 的积分不能高于最高分");
				return;
            }else{
            	ajaxPostData({
                    url: 'savePersonalAcademicCredit.do',
                    data: {
                        paramVo: JSON.stringify(ParamVo),
                        glory_name: $("#glory_name").val(),
                        year: year.getValue()
                    },
                    success: function () {
                        query();
                    }
                })
            }
            
            
        };
        
        var add = function () {
            grid.addRow();
        };
        
        var remove = function () {
            var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }

            var param = [];
            selectData.forEach(function (item) {
                param.push({
                    honor_code: item.rowData.honor_code,
                    year: year.getValue()
                });
            });
            $.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                url: 'deletePersonalAcademicCredit.do',
                data: { paramVo: JSON.stringify(param) },
                success: function () {
                    grid.deleteRows(selectData);
                }
            })
            });
        };
        var extend = function () {
        	ajaxPostData({
                url: 'inheritAcademicCredit.do',
                success: function () {
                	query();
                }
            })
        };
        
        var initGrid = function () {
            var columns = [
                { display: '学术荣誉编码', name: 'honor_code', width: 140, editable: false },
                { display: '学术荣誉名称', name: 'field_col_name', width: 140,
                    editor: {
                        type: 'select',
                        keyField: 'honor_code',
                        url: 'queryHonorsName.do?isCheck=false',
                        change: function (rowData, cellData) {
                            grid.updateRow(cellData.rowIndx, { a: cellData.selected.id });
                        }
                    }
                },
                { display: '人才称号(积分)', name: 'score', width: 140 }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                dataModel: {
                    url: 'queryPersonalAcademicCredit.do'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '继承', listeners: [{ click: extend }], icon: 'extend' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        $(function () {
            initGrid();
            initFrom();
            initValidate();
            
            
        })
        
     function saveHonor(){
		var year = $("#year").val();
		var acade_honor = $("#acade_honor").val(); 
		if(acade_honor){
			ajaxPostData({
		    	url: 'savePersonalAcadeHonor.do?isCheck=false',
		        data: {
		       		year: year, 
					acade_honor: acade_honor
		        },
		        success: function () {
		        	query();
		        }
		    })
		}
	}
        
        
    </script>
</head>

<body>
    <div class="main">
        <table class="table-layout" >
            <tr>
                <td class="label">年度：</td>
                <td class="ipt">
                    <input id="year" type="text" />
                </td>
                
                <td class="label" style="width: 120px;">学术荣誉名称：</td>
                <td class="ipt">
                    <input id="glory_name" type="text" />
                </td>
                
                <td align="right" class="l-table-edit-td" colspan="4" >
					<table>
						<tr>
							<td>设置最高分:</td>
							<td align="left" class="l-table-edit-td">
								<input  name="acade_honor" type="text" id="acade_honor" onkeyup="value=value.replace(/[^\d]/g,'')" ltype="text" validate="{required:false}"/>
								&nbsp;&nbsp;&nbsp;
								<button onClick="saveHonor();">保存</button>
							</td>
						</tr>
					</table>
					
				</td>
            </tr>
            
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>