<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,dialog" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var state = '${state}';

    $(function() {
        loadDict();

        //根据状态置灰按钮
        if (state == "01") {
            $("#unaudit").attr('disabled', true);
            $("#state").html("新建");
        } else {
            $("#save").attr('disabled', true);
            $("#audit").attr('disabled', true);
            $("#state").html("已审核");
        }
    });

    function save() {
        var budg_year = '${budg_year}';
        var adj_code = '${adj_code}';
        var last_check_code = '${before_adj_code}';
        var old_adj_file = "${adj_file}";
        var adj_file = $("#adj_file").val();
        var adj_remark = $("#adj_remark").val();
        var state = '${state}';

        if (adj_file != null || adj_file != "") {
            var extname = adj_file.substring(adj_file.lastIndexOf(".") + 1, adj_file.length);
            extname = extname.toLowerCase(); //处理了大小写  
        }

        var file = document.getElementById("upFile").files;

        var loadIndex = $.etDialog.load();

        $.ajaxFileUpload({
            //用于文件上传的服务器端请求地址
            url: 'updateBudgCostAdj.do?isCheck=false&budg_year=' + budg_year + '&adj_code=' + adj_code + '&adj_file=' + adj_file +
                "&adj_remark=" + adj_remark + "&last_check_code=" + last_check_code + "&state=" + state + "&old_adj_file=" + old_adj_file,
            //一般设置为false
            secureuri: false, 
            //文件上传空间的id属性  <input type="file" id="file" name="file" />
            fileElementId: 'upFile', 
            type: 'post',
            //返回值类型 一般设置为json
            dataType: 'json', 
            //服务器成功响应处理函数
            success: function(data, status)  {
                $.etDialog.close(loadIndex);

                if (data.state == "true") {
                    $.etDialog.success(data.msg, function () {
                        parent.query();
                        close_iframe();
                    });
                } else {
                    $.etDialog.warn(data.msg);
                }
            },
            //服务器响应失败处理函数
            error: function(data, status, e) {
                $.etDialog.close(loadIndex);
            }
        });
    }

    //关闭当前页面
    function close_iframe() {
        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
        parent.$.etDialog.close(curIndex);
    }

    function saveBudgWorkCheck() {
        save();
    }
    //审核的功能
    function audit() {
        var ParamVo = [];

        if (state != '01') {

            $.etDialog.error("审核失败！该单据不是新建状态不允许审核！");
            return false;

        }
        ParamVo.push(
            "${budg_year}" + "@" +
            "${adj_code}" + "@" +
            '02'
        )

        $.etDialog.confirm('确定要审核?', function(yes) {
            ajaxPostData({
                url: "auditBudgCostAdj.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                    parent.query();
                    location.reload();
                },
                delayCallback: true
            });
        });
    }
    //消除审核的功能
    function unaudit() {
        var ParamVo = [];

        if (state != '02') {
            $.etDialog.error("销审失败!该单据不是审核状态不允许销审！");
            return false;
        }
        ParamVo.push(
            "${budg_year}" + "@" +
            "${adj_code}" + "@" +
            "${before_adj_code}" + "@" +
            '01'
        )

        $.etDialog.confirm('确定要销审?', function(yes) {
            ajaxPostData({
                url: "unAuditBudgCostAdj.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                    parent.query();
                    location.reload();
                },
                delayCallback: true
            });
        });
    }

    function loadDict() {
        var issue_data = '${issue_data}';
        $("#issue_data").val(issue_data.split(" ")[0]);

        $("#save").on("click", saveBudgWorkCheck);
        $("#audit").on("click", audit);
        $("#unaudit").on("click", unaudit);
        $("#print").on("click", print);
        $("#close").on("click", close_iframe);
        
        $("#upload_btn").click(function () {
            $("#upFile").click();
        })
        $("#upFile").change(function () {
            var arr = $(this).val().split("\\");
            var fileName = arr[arr.length - 1];
            $("#adj_file").val(fileName);
        })
    }
</script>
<script>
    jQuery.extend({

        createUploadIframe: function(id, uri) {
            //create frame  
            var frameId = 'jUploadFrame' + id;
            var iframeHtml = '<iframe id="' + frameId + '" name="' + frameId + '" style="position:absolute; top:-9999px; left:-9999px"';
            if (window.ActiveXObject) {
                if (typeof uri == 'boolean') {
                    iframeHtml += ' src="' + 'javascript:false' + '"';

                } else if (typeof uri == 'string') {
                    iframeHtml += ' src="' + uri + '"';

                }
            }
            iframeHtml += ' />';

            jQuery(iframeHtml).appendTo(document.body);
            return jQuery('#' + frameId).get(0);
        },
        createUploadForm: function(id, fileElementId, data) {
            //create form     
            var formId = 'jUploadForm' + id;
            var fileId = 'jUploadFile' + id;
            var form = jQuery('<form  action="" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');
            if (data) {
                for (var i in data) {
                    jQuery('<input type="hidden" name="' + i + '" value="' + data[i] + '" />').appendTo(form);
                }
            }
            var oldElement = jQuery('#' + fileElementId);
            var newElement = jQuery(oldElement).clone();
            jQuery(oldElement).attr('id', fileId);
            jQuery(oldElement).before(newElement);
            jQuery(oldElement).appendTo(form);

            //set attributes  
            jQuery(form).css('position', 'absolute');
            jQuery(form).css('top', '-1200px');
            jQuery(form).css('left', '-1200px');
            jQuery(form).appendTo('body');
            return form;
        },

        ajaxFileUpload: function(s) {
            // TODO introduce global settings, allowing the client to modify them for all requests, not only timeout          
            s = jQuery.extend({}, jQuery.ajaxSettings, s);
            var id = new Date().getTime()
            var form = jQuery.createUploadForm(id, s.fileElementId, (typeof(s.data) == 'undefined' ? false : s.data));
            var io = jQuery.createUploadIframe(id, s.secureuri);
            var frameId = 'jUploadFrame' + id;
            var formId = 'jUploadForm' + id;
            // Watch for a new set of requests  
            if (s.global && !jQuery.active++) {
                jQuery.event.trigger("ajaxStart");
            }
            var requestDone = false;
            // Create the request object  
            var xml = {}
            if (s.global)
                jQuery.event.trigger("ajaxSend", [xml, s]);
            // Wait for a response to come back  
            var uploadCallback = function(isTimeout) {
                var io = document.getElementById(frameId);
                try {
                    if (io.contentWindow) {

                        // xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;  
                        // xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document.children[0].outerHTML;
                        //console.log(io.contentWindow.document.children[0].outerHTML)
                        xml.responseText = io.contentWindow.document.children[0].outerHTML;
                        xml.responseXML = io.contentWindow.document.children[0].outerHTML;
                    } else if (io.contentDocument) {
                        xml.responseText = io.contentDocument.document.body ? io.contentDocument.document.body.innerHTML : null;
                        xml.responseXML = io.contentDocument.document.XMLDocument ? io.contentDocument.document.XMLDocument : io.contentDocument.document;
                    }
                } catch (e) {
                    jQuery.handleError(s, xml, null, e);
                }
                if (xml || isTimeout == "timeout") {
                    requestDone = true;
                    var status;
                    try {
                        status = isTimeout != "timeout" ? "success" : "error";
                        // Make sure that the request was successful or notmodified  
                        if (status != "error") {
                            // process the data (runs the xml through httpData regardless of callback)  
                            var data = jQuery.uploadHttpData(xml, s.dataType);
                            // If a local callback was specified, fire it and pass it the data 
                            if (s.success)
                                s.success(data, status);

                            // Fire the global callback  
                            if (s.global)
                                jQuery.event.trigger("ajaxSuccess", [xml, s]);
                        } else
                            jQuery.handleError(s, xml, status);
                    } catch (e) {
                        status = "error";
                        jQuery.handleError(s, xml, status, e);
                    }

                    // The request was completed  
                    if (s.global)
                        jQuery.event.trigger("ajaxComplete", [xml, s]);

                    // Handle the global AJAX counter  
                    if (s.global && !--jQuery.active)
                        jQuery.event.trigger("ajaxStop");

                    // Process result  
                    if (s.complete)
                        s.complete(xml, status);

                    jQuery(io).unbind()

                    setTimeout(function() {
                        try {
                            jQuery(io).remove();
                            jQuery(form).remove();

                        } catch (e) {
                            jQuery.handleError(s, xml, null, e);
                        }

                    }, 100)

                    xml = null
                }
            }
            // Timeout checker  
            if (s.timeout > 0) {
                setTimeout(function() {
                    // Check to see if the request is still happening  
                    if (!requestDone) uploadCallback("timeout");
                }, s.timeout);
            }
            try {

                var form = jQuery('#' + formId);
                jQuery(form).attr('action', s.url);
                jQuery(form).attr('method', 'POST');
                jQuery(form).attr('target', frameId);
                if (form.encoding) {
                    jQuery(form).attr('encoding', 'multipart/form-data');
                } else {
                    jQuery(form).attr('enctype', 'multipart/form-data');
                }
                jQuery(form).submit();

            } catch (e) {
                jQuery.handleError(s, xml, null, e);
            }

            jQuery('#' + frameId).load(uploadCallback);
            return { abort: function() {} };

        },

        uploadHttpData: function(r, type) {
            var data = !type;
            data = type == "xml" || data ? r.responseXML : r.responseText;
            // If the type is "script", eval it in global context 
            if (type == "script") {
                jQuery.globalEval(data);

                // Get the JavaScript object, if JSON is used.
            } else if (type == "json") {
                data = data.replace(/<\/\w{0,}>/g, "");
                data = data.replace(/<JSONObject>/g, "");
                data = data.replace(/</g, "\",\"");
                data = data.replace(/\",\"/, "");
                data = data.replace(/>/g, "\":\"");
                data = "{\"" + data + "\"}";
                data = JSON.parse(data);

                // evaluate scripts within html
            } else if (type == "html") {
                jQuery("<div>").html(data).evalScripts();
            }
            return data;
        },
        handleError: function(s, xhr, status, e) {
            // If a local callback was specified, fire it  
            if (s.error) {
                s.error.call(s.context || s, xhr, status, e);
            }

            // Fire the global callback  
            if (s.global) {
                (s.context ? jQuery(s.context) : jQuery.event).trigger("ajaxError", [xhr, s, e]);
            }
        }

    }); 
</script>
</head>

<body>
    <div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label">调整单号：</td>
                <td class="ipt">
                    <input id="adj_code" type="text" value="${adj_code}" disabled />
                </td>

                <td class="label">预算年度：</td>
                <td class="ipt">
                    <input id="budg_year" type="text" value="${budg_year}" disabled />
                </td>

                <td class="label">调整前审批单号：</td>
                <td class="ipt">
                    <input id="before_adj_code" type="text" value="${before_adj_code}" disabled />
                </td>

                <td class="label">上次下达日期：</td>
                <td class="ipt">
                    <input id="issue_data" type="text" disabled />
                </td>
            </tr>
            <tr>
                <td class="label">调整文号：</td>
                <td class="ipt" colspan="3">
                    <input id="adj_file" name="adj_file" type="text" disabled value="${adj_file}"/>
                    <button id="upload_btn">选择文件</button>
                    <input id ="upFile" name="upFile" type="file" hidden />
                </td>
            </tr>
            <tr>
                <td class="label">调整说明：</td>
                <td class="ipt" colspan="3">
                    <textarea id="adj_remark" style="width: 100%;height: 100px;">${adj_remark}</textarea>
                </td>
            </tr>
            <tr>
                <td class="label">制单人：</td>
                <td class="ipt">${make_name}</td>

                <td class="label">制单日期：</td>
                <td class="ipt">${make_data}</td>

                <td class="label">审核人：</td>
                <td class="ipt">${check_name}</td>

                <td class="label">审核日期：</td>
                <td class="ipt">${check_date}</td>

                <td class="label">状态：</td>
                <td class="ipt"><span id="state"></span></td>
            </tr>
        </table>
    </div>
    <br />
    <br />
    <div class="button-group">
        <button id="save"><b>保存</b></button>
        <button id="audit"><b>审核</b></button>
        <button id="unaudit"><b>消审</b></button>
        <button id="print"><b>打印</b></button>
        <button id="close"><b>关闭</b></button>
    </div>
</body>
</html>