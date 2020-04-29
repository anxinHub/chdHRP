var app = (function(originApp) {
    var _config;
    var _storageKey = "Spread.Sheets.designer";
    var _fileSelectorEventAdded = false;
    var $emptyFileSelector;
    var _saveAsDialog;

    function config(name, value) {
        if (value === undefined) {
            return getConfig(name);
        } else {
            setConfig(name, value);
            return app;
        }
    }

    function getConfig(name) {
        var parts = getKeyItems(name), count = parts.length, cur = _config;
        if (count > 0) {
            for (var i = 0; i < count; i++) {
                if (!cur)
                    break;
                var key = parts[i];
                cur = cur[key];
            }
            return cur;
        }
        return undefined;
    }

    function setConfig(name, value) {
        var parts = getKeyItems(name), count = parts.length, cur = _config, i = 0;
        if (count > 0) {
            for (; i < count - 1; i++) {
                var key = parts[i];
                cur = cur[key] = cur[key] || {};
            }
            cur[parts[count - 1]] = value;
            saveConfig();
        }
    }

    function getKeyItems(name) {
        return !name ? [] : name.split(".");
    }

    function saveConfig() {
        localStorage.setItem(_storageKey, JSON.stringify(_config));
    }
    $(window).unload(function() {
        saveConfig();
    });
    $(window).load(function() {
        var value = localStorage.getItem(_storageKey);
        if(value=="undefined"){//perry 增加判断
        	_config={};
        }else{
        	_config = !value ? {} : JSON.parse(value);	
        }
        
    });

    function processSelectedFile(file, action, callback, options) {
        if (file) {
            var operationResult = { status: 'success', file: file };
            switch (action) {
                case "insertPicture":
                    readFile(operationResult, "dataurl", callback);
                    break;
                default:
                    readFile(operationResult, "", callback, options);
                    break;
            }
        }
    }

    function getFileInfo(fullPath) {
        var result = { dir: '', fileName: '' };

        if (fullPath) {
            var found = false, pos;
            ['\\', '/'].forEach(function(c) {
                if (!found && (pos = fullPath.lastIndexOf(c)) !== -1) {
                    found = true;
                }
            });
            result.fileName = fullPath.substr(pos + 1);
            if (found) {
                result.dir = fullPath.substr(0, pos);
            }
        }

        return result;
    }

    function getExtension(fileName, detail) {
        var pos = fileName.lastIndexOf('.');
        if (detail) {
            detail.pos = pos;
            detail.name = fileName.substr(0, pos);
        }
        return pos === -1 ? "" : fileName.substring(pos + 1).toLowerCase();
    }

    function readFile(target, type, callback, options) {
        if (target && target.file) {
            var file = target.file;
            if (!type) {
                type = getExtension(file.name);
            }

            var reader = new FileReader();
            reader.onload = function() {
                if (type === "xlsx") {
                    importExcel(this.result, options, callback, file);
                } else {
                    target.data = this.result;
                    callback(target);
                }
            };
            switch (type) {
                case "dataurl":
                    reader.readAsDataURL(file);
                    break;
                case "xlsx":
                    reader.readAsArrayBuffer(file);
                    break;
                default:
                    reader.readAsText(file);
                    break;
            }
        }
    }

    function getFilters(nameFilters) {
        function convert(filterString) {
            var items = filterString.split('('),
                name = items[0].trim(),
                patterns = items[1].trim().replace(')', ''),
                extensions = patterns.split(' ').map(function(v){v.replace('*.', '.')});

            return {
                name: name,
                extensions: extensions
            };
        }

        return nameFilters && nameFilters.map(convert) || [];
    }

    function importExcel(buffer, options, callback, file) {
        var excelIo = new GC.Spread.Excel.IO();
        excelIo.open(buffer, function(json) {
            callback && callback({
                status: 'success',
                data: json,
                file: file
            });
        }, function(err) {
            //process error
            callback && callback({
                status: 'failed',
                errorData: err,
                file: file
            });
        }, options);
    }

    function exportExcel(spreadJson, exportOptions, callback) {
        var options = { password: exportOptions.password };
        var excelIo = new GC.Spread.Excel.IO();
        
        excelIo.save(spreadJson, function (data) {
            callback && callback({
                status: 'success',
                data: data
            });
        }, function (err) {
            callback && callback({
                status: 'failed',
                errorData: err
            });
        }, options);
    }

    return {
        config: config,
        showSaveDialog: function(options, callback) {
            if (!_saveAsDialog) {
                _saveAsDialog = new GC.Spread.Sheets.designer.SaveAsDialog();
            }
            var fileExtension = options.fileExtension || getFilters(options.nameFilters)[0].extensions[0];
            _saveAsDialog.open({
                fileExtension: fileExtension,
                done: function(result) {
                    var fileName = result && result.fileName, cancelled = result && result.cancelled;
                    if (cancelled || !fileName) {
                        callback({ status: 'cancelled' });
                    } else {
                        callback({ status: 'success', fileName: fileName + fileExtension });
                    }
                }
            });
        },
        showOpenDialog: function(options, callback, importOptions) {
            if (!$emptyFileSelector) {
                $emptyFileSelector = $("#fileSelector").clone();
            } else {
                // clear (with cloned object (.val("") not works for IE)) to make sure change event occurs even when same file selected again
                $("#fileSelector").replaceWith($emptyFileSelector.clone());
            }
            if (!_fileSelectorEventAdded) {
                $(document.body).on("change", "#fileSelector", function() {
                    var files = this.files, file = files && files[0], action = $(this).data("action"), callback = $(this).data("callback");
                    if (!file) {
                        return false;
                    }
                    processSelectedFile(file, action, callback, importOptions);
                });
                _fileSelectorEventAdded = true;
            }
            var $fileSelector = $("#fileSelector");
            $fileSelector.data("action", options.action);
            $fileSelector.data("callback", callback);
            var filters = options.filters || getFilters(options.nameFilters)[0].extensions.join(" ");
            $fileSelector.attr("accept", filters || "");
            $fileSelector.click();
        },
        save: function(fileName, saveData, callback, options) {
            function getVariableName(fileName) {
                var detail = {};
                getExtension(fileName, detail);
                var name = detail.name;

                if (/^[_a-zA-Z\$]{1}[_a-zA-Z0-9\$]*$/.test(name)) {
                    return name;
                } else {
                    return '_' + name.replace(/[ \.]/g, '_');
                }
            }

            function saveFile(fileName, data, mimeType, callback) {
                try {
                    saveAs(new Blob([data], { type: mimeType }), fileName);
                    callback({ status: 'success' });
                } catch (ex) {
                    callback({ status: 'failed', message: ex });
                }
            }

            var data = saveData.spread || saveData;

            var isJSFile = options && options.saveAsJS;

            if (isJSFile) {
                data = 'var ' + getVariableName(fileName) + ' = ' + data;
            }

            var ext = saveData.exportFileType,
                mimeType = "text/plain;charset=utf-8";

            switch(ext) {
                case 'xlsx':
                    mimeType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
                    exportExcel(data, options, function(result) {
                        if (result.status === 'success') {
                            saveFile(fileName, result.data, mimeType, callback);
                        } else {
                            callback({
                                status: 'failed',
                                message: result.errorData
                            });
                        }
                    });
                    return;
                case 'csv':
                    mimeType = 'application/octet-stream';
                    break;
            }

            saveFile(fileName, data, mimeType, callback);
        },
        notify: function() { return ""; },
        openUrl: function() { return false; },
        notifyReady: function() {},
        getFileInfo: getFileInfo,
        getExtension: getExtension
    };
})(window['app']);
