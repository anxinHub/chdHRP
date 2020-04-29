angular.module('sampleDirective', [])
.directive('toolTip', ['codeService', function factory(codeService) {
    return function ($scope, element, attrs) {
        if (element) {
            element.hover(function(){
                // Hover over code
                var title = $(this).attr('title');
                $(this).data('tipText', title).removeAttr('title');
                $('<p class="tooltip"></p>')
                    .text(title)
                    .appendTo('body')
                    .fadeIn('slow');
            }, function() {
                // Hover out code
                $(this).attr('title', $(this).data('tipText'));
                $('.tooltip').remove();
            }).mousemove(function(e) {
                    var mousex = e.pageX + 20; //Get X coordinates
                    var mousey = e.pageY + 10; //Get Y coordinates
                    $('.tooltip').css({ top: mousey, left: mousex });
                });
        }
    };
}])
.directive('animateMove', ['codeService', function factory(codeService) {
    return function ($scope, element, attrs) {
        if (element) {
            $scope.$watch('newFeatureIndex', function(newValue, oldValue) {
                if (newValue !== undefined && newValue !== null && oldValue !== undefined && oldValue !== null && newValue !== oldValue){
                    var isNext = newValue > oldValue,
                        newFeatures = $scope.newFeatures;

                    if (newFeatures && newFeatures.length > 0) {
                        var featuresCount = newFeatures.length;
                        if (!$scope.isAnimationEnd || !element) {
                            return;
                        }
                        $scope.isAnimationEnd = false;
                        var width = element.parent().width();
                        var left = parseFloat(element.css('left'));
                        if (isNaN(left)) {
                            left = 0;
                        }
                        if (isNext) {
                            if (left <= 0 && left > 0 - width * (featuresCount - 1)) {
                                left -= width;
                            } 
                        } else {
                            if (left < 0 && left >= 0 - width * (featuresCount - 1)) {
                                left += width;
                            }
                        }
                        element.animate({"left": left}, {
                            speed: 'fast',
                            esaing: 'linear',
                            queue: false,
                            complete: function() {
                                $scope.isAnimationEnd = true;
                            }
                        });
                    }
                }
            });
        }
    };
}])
.directive('splitPanel', ['codeService', function factory(codeService) {
    return function ($scope, element, attrs) {
        if (element) {
            element.splitterPanel();
        }
    };
}])
.directive('collapse', ['codeService', '$timeout', function factory(codeService, $timeout) {
    return function ($scope, element, attrs) {
        if (element) {
            $timeout(function() {
                element.collapse();
                element.each(function() {
                    var collapseItem = $(this);
                    if (collapseItem && collapseItem.data('collapse')) {
                        var collapse = collapseItem.data('collapse');
                        var $bindEle = $(collapse.getBindElement());
                        if ($bindEle.hasClass('split-panel')) {
                            var splitPanel = $bindEle.data('splitPanel');
                            if (splitPanel) {
                                var cacheScale = splitPanel.splitScale();
                                collapse.collapseWay = function() {
                                    if (splitPanel.splitScale() === cacheScale) {
                                        splitPanel.splitScale(0);
                                        collapse.collapsed = true;
                                    } else {
                                        splitPanel.splitScale(cacheScale);
                                        collapse.collapsed = false;
                                    }
                                };
                            }
                        }
                    }
                });
            }, 100, false);
        }
    };
}])
.directive('codeMirror', ['codeService', function factory(codeService) {
    return function ($scope, element, attrs) {
        if (element) {
            codeService.codeMirror = CodeMirror.fromTextArea(element.get(0), {
                mode: 'htmlmixed',
                lineNumbers: true
            });
        }
    };
}])
.directive('toggleSwitch', ['codeService', function factory(codeService) {
    return function ($scope, element, attrs) {
        if (element) {
            var switchCases = {
                codeJS: 'Javascript',
                codeBody: 'HTML',
                codeCss: 'CSS',
                codeResource: 'Resources'
            };
            var items = element.children();
            if (items && items.length > 0) {
                $scope.$watch('cacheChoice', function(newValue) {
                    if (newValue && newValue !== '' && switchCases[newValue] && switchCases[newValue] !== '') {
                        var itemText = switchCases[newValue];
                        for (var i = 0, len = items.length; i < len; i++) {
                            var $item = $(items[i]);
                            if ($item && $item.text() === itemText) {
                                items.removeClass('sample-detail-toggle-switch-active');
                                $item.addClass('sample-detail-toggle-switch-active');
                                if (itemText === switchCases.codeJS) {
                                    codeService.codeMirror.setOption('mode', 'javascript');
                                } else {
                                    codeService.codeMirror.setOption('mode', 'text/html');
                                }
                                break;
                            }
                        }
                    }
                });
            }
        }
    };
}])
.directive('selectList', ['$timeout', function factory($timeout) {
    return function ($scope, element, attrs) {
        if (element) {
            var select = null;
            $scope.$watch('featureDetails', function(newValue) {
                if (newValue && newValue.length > 0){
                    var resource = $scope.resource;
                    if (resource) {
                        for (var i = 0, len = newValue.length; i < len; i++) {
                            var item = newValue[i];
                            if (item && item.resource === resource) {
                                $scope.featureItem = item;
                            }
                        }
                    }
                    $timeout(function() {
                        select = new CustomSelect(element.get(0));
                    }, 0, false);
                }
            });
            $scope.$watch('nameIndex', function(newValue) {
                if (newValue >= 0 && select){
                    var text = $scope.featureDetails[newValue].name;
                    select.setSelected(text);
                }
            });
        }
    };
}])
.directive('contentLoad', ['codeService','$timeout', function factory(codeService, $timeout) {
    return function ($scope, element, attrs) {
        if (element) {
            $scope.$watch('content', function(newValue) {
                if (newValue && newValue !== ''){
                    $timeout(function() {
                        var contentHTML = codeService.applyContent(newValue);
                        if (contentHTML !== '') {
                            element.get(0).innerHTML = contentHTML;
                            $('pre code').each(function (i, block) {
                                hljs.highlightBlock(block);
                            });
                        }
                    }, 0, false);
                }
            });
        }
    };
}])
.directive('codeApply', ['$timeout','codeService', function factory($timeout, codeService) {
    return function ($scope, element, attrs) {
        if (element) {
            var time = null;
            $scope.$watch('code', function(newValue, oldValue) {
                if (newValue && newValue !== '') {
                    if (time === null) {
                        time = $timeout(function() {
                            var $resultCode = element;
                            if (!codeService.codeMirror) {
                                return;
                            }
                            if (codeService.codeMirror.getValue() !== $scope[$scope.cacheChoice]) { //Do not need to change the code mirror's value after edit.
                                codeService.codeMirror.setValue($scope[$scope.cacheChoice]);
                            }
                            $resultCode.empty();
                            $resultCode.append("<iframe frameborder='1' id='codeIframe' style='width: 100%; height: 100%; border: none' src='about:blank'></iframe>");
                            var resultCodeIframe = $resultCode.children().get(0);
                            var doc = resultCodeIframe.contentWindow.document;
                            try {
                                doc.open();
                                // remove cdn resource, use local
                                var content = newValue.replace($scope.codeResource, "");
                                content = codeService.removeMarkItems(content);
                                content = codeService.replaceFileResource(content, $scope);
                                doc.write(content);
                                doc.close();
                            } catch(e) {
                                alert(!!e.message ? e.message : e);
                            }
                            time = null;
                        }, 100, false);
                    }
                }
            });
        }
    };
}])
.directive('codeDownload', ['$timeout', 'codeService', function factory($timeout, codeService) {
    return function ($scope, element, attrs) {
        if (element) {
            $timeout(function() {
                element.on('click', function(event) {
                    var filename = $scope.featureItem.resource + '.html',
                        code = $scope.code;
                    if (code !== '' && filename !== '') {
                        // remove local resource, use cdn
                        var fixedCode = code.replace($scope.localResource, "");
                        fixedCode = codeService.removeMarkItems(fixedCode);
                        if (window.ActiveXObject !== undefined) {
                            var myFrame = document.createElement('iframe');
                            myFrame.style.display = "none";
                            document.body.appendChild(myFrame);
                            var contentDocument = myFrame.contentDocument;
                            contentDocument.open("text/html", "replace");
                            contentDocument.write(fixedCode);
                            contentDocument.close();
                            contentDocument.execCommand('SaveAs', true, filename);
                            document.body.removeChild(myFrame);

                            if ($scope.featureItem.fileUrls) {
                                $scope.featureItem.fileUrls.forEach(function (fileItem) {
                                    var downloadFrame = document.createElement('iframe');
                                    downloadFrame.style.display = "none";
                                    document.body.appendChild(downloadFrame);
                                    
                                    downloadFrame.src = fileItem.path;

                                    window.setTimeout(function () {
                                        if (downloadFrame.contentDocument) {
                                            downloadFrame.contentDocument.execCommand('SaveAs', true, fileItem.fileName);
                                        } else {
                                            console.log("timeout when load file for download :(", fileItem.fileName);
                                        }
                                        document.body.removeChild(downloadFrame);
                                    }, 800);
                                });
                            }
                        } else {
                            var alink = document.createElement('a');
                            alink.style.display = 'none';
                            document.body.appendChild(alink);
                            var blob = new Blob([fixedCode], {type: 'text/html'});
                            var urlContent = URL.createObjectURL(blob);
                            alink.setAttribute('download', filename);
                            alink.setAttribute('href', urlContent);
                            alink.click();
                            if ($scope.featureItem.fileUrls) {
                                $scope.featureItem.fileUrls.forEach(function (fileItem) {
                                    alink.setAttribute('download', fileItem.fileName);
                                    alink.setAttribute('href', fileItem.path);
                                    alink.click();
                                });
                            }
                            document.body.removeChild(alink);
                            return true;
                        }
                    }
                });
            }, 100, false);
        }
    };
}]);
