angular.module('sampleServices', [])
.factory('codeService', ['$http', '$q', function($http, $q) {
    var jsStartString = "/*code_begin*/", jsEndString = "/*code_end*/",
        cssStartString = "/*css_begin*/", cssEndString = "/*css_end*/",
        bodyStartString = "<body", bodyEndString = "</body>",
        localReferencesMarkString = "<!--local_references_resources-->",
        resourceReferencesMarkString = "<!--link_references_resources-->",
        localResourceStartString = "<!--local_resource_begin-->",
        localResourceEndString = "<!--local_resource_end-->"
        resourceStartString = "<!--link_begin-->", resourceEndString = "<!--link_end-->";
    function sliceExceptEdge(str, startString, endString, checkPresent) {
        var startIndex = str.indexOf(startString) + startString.length;
        var endIndex = str.indexOf(endString);
        return checkPresent && endIndex === -1 ? "" : str.slice(startIndex, endIndex);
    }
    function sliceIncludeEdge(str, startString, endString) {
        var startIndex = str.indexOf(startString);
        var endIndex = str.indexOf(endString) + endString.length;
        return str.slice(startIndex, endIndex);
    }
    function takeStartAndEndExceptEdge(str, startString, endString) {
        var startIndex = str.indexOf(startString) + startString.length;
        var endIndex = str.indexOf(endString);
        var startEnd = {
            start: str.substring(0, startIndex),
            end: str.substring(endIndex)
        };
        return startEnd;
    }
    function takeStartAndEndIncludeEdge(str, startString, endString) {
        var startIndex = str.indexOf(startString);
        var endIndex = str.indexOf(endString) + endString.length;
        var startEnd = {
            start: str.substring(0, startIndex),
            end: str.substring(endIndex)
        };
        return startEnd;
    }
    // remove all mark items used in code for output and download
    function removeMarkItems(str) {
        if (str) {
            [jsStartString, jsEndString,
                cssStartString, cssEndString,
                localResourceStartString, localResourceEndString,
                resourceStartString, resourceEndString
            ].forEach(function (mark) {
                str = str.replace(mark, "");
            });
        }

        return str;
    }
    var cacheData = [];
    var codeService = {
        codeMirror: null,
        codeMerge: function (codeTemplate, codeContent, referenceNames, referencesInfo) {
            if (codeTemplate && codeTemplate !== '' && codeContent && codeContent !== '') {
                // process with additional reference support
                var localReferenceItems = "",
                    resourceReferenceItems = "";

                if (referenceNames && referenceNames.length) {
                    referenceNames.forEach(function (name) {
                        var item = referencesInfo[name];
                        if (item && item.href && item.local) {
                            resourceReferenceItems += item.href + "\r\n\r\n    ";
                            localReferenceItems += item.local + "\r\n\r\n    ";
                        }
                    });
                    resourceReferenceItems = "\r\n    " + resourceReferenceItems;
                    localReferenceItems = "\r\n    " + localReferenceItems;
                }
                codeTemplate = codeTemplate.replace(resourceReferencesMarkString, resourceReferenceItems).replace(localReferencesMarkString, localReferenceItems);

                var result,
                    codeCss = '',
                    codeJS = sliceExceptEdge(codeContent, jsStartString, jsEndString),
                    codeBody = sliceIncludeEdge(codeContent, bodyStartString, bodyEndString),
                    codeResource = sliceExceptEdge(codeTemplate, resourceStartString, resourceEndString),
                    localResource = sliceExceptEdge(codeTemplate, localResourceStartString, localResourceEndString),
                    cssStartIndex = codeContent.indexOf(cssStartString),
                    cssEndIndex = codeContent.indexOf(cssEndString);

                if (cssStartIndex !== -1 && cssEndIndex !== -1) {
                    codeCss = codeContent.slice(cssStartIndex + cssStartString.length, cssEndIndex);
                }

                var startEnd = takeStartAndEndExceptEdge(codeTemplate, jsStartString, jsEndString);
                result = startEnd.start + codeJS + startEnd.end;

                startEnd = takeStartAndEndExceptEdge(result, cssStartString, cssEndString);
                result = startEnd.start + codeCss + startEnd.end;

                startEnd = takeStartAndEndIncludeEdge(result, bodyStartString, bodyEndString);
                result = startEnd.start + codeBody + startEnd.end;

                var code = {
                    codeAll: result,
                    codeJS: codeJS,
                    codeBody: codeBody,
                    codeCss: codeCss,
                    codeResource: codeResource,
                    localResource: localResource
                };
                return code;
            }
        },
        jsCodeChange: function($scope, codeAll) {
            if (codeAll && codeAll !== '') {
                $scope[$scope.cacheChoice] = codeService.codeMirror.getValue();

                var startEnd = takeStartAndEndExceptEdge(codeAll, resourceStartString, resourceEndString);
                codeAll = startEnd.start + $scope.codeResource + startEnd.end;

                startEnd = takeStartAndEndExceptEdge(codeAll, jsStartString, jsEndString);
                codeAll = startEnd.start + $scope.codeJS + startEnd.end;

                startEnd = takeStartAndEndExceptEdge(codeAll, cssStartString, cssEndString);
                codeAll = startEnd.start + $scope.codeCss + startEnd.end;

                startEnd = takeStartAndEndIncludeEdge(codeAll, bodyStartString, bodyEndString);
                codeAll = startEnd.start + $scope.codeBody + startEnd.end;
            }
            return codeAll;
        },
        switchCode: function($scope, choice) {
            if (choice !== $scope.cacheChoice) {
                $scope[$scope.cacheChoice] = codeService.codeMirror.getValue();
                codeService.codeMirror.setValue($scope[choice]);
                $scope.cacheChoice = choice;
            }
        },
        getResource: function(url, $scope, scopeName) {
            var len = cacheData.length;
            for (var i = 0; i < len; i++) {
                var cacheItem = cacheData[i];
                if (cacheItem.url === url) {
                    $scope[scopeName] = cacheItem.data;
                    return;
                }
            }
            $http.get(url).success(function(data) {
                cacheData[cacheData.length] = {
                    url: url,
                    data: data
                };
                $scope[scopeName] = data;
            });
        },
        getResources: function(urls) {
            var promises = [];
            angular.forEach(urls , function(url) {
                var deferred = $q.defer();
                var i, len = cacheData.length;
                for (i = 0; i < len; i++) {
                    var cacheItem = cacheData[i];
                    if (cacheItem.url === url) {
                        deferred.resolve(cacheItem.data);
                        break;
                    }
                }
                if (i === len) {
                    $http.get(url)
                        .success(function(data){
                            cacheData[cacheData.length] = {
                                url: url,
                                data: data
                            };
                            deferred.resolve(data);
                        })
                        .error(function (error) {
                            deferred.reject();
                        });
                }
                promises.push(deferred.promise);
            });
            return $q.all(promises);
        },
        applyContent: function(value) {
            var contentHTML = '';
            if (value) {
                var elements = [], subItems;
                elements.push("<div class='sample-content'><h1 class='sample-title'>" + value.title + "</h1>");
                var segments = value.segments;
                if (segments) {
                    segments.forEach(function (segment) {
                        if (segment) {
                            segment.forEach(function (item) {
                                if (item) {
                                    if (item.description) {                              //description
                                        elements.push("<p>" + item.description + "</p>");
                                    } else if (item.list && item.list.length > 0) {      // list
                                        subItems = item.list.map(function (listItem) {
                                            return "<li><b>" + listItem + "</b></li>";
                                        }).join("");
                                        elements.push("<ul>" + subItems + "</ul>");
                                    } else if (item.code && item.code.length > 0) {      //code
                                        subItems = item.code.map(function (lineCode) {
                                            return lineCode ?
                                                lineCode.replace(/\</g, "&lt;").replace(/\>/g, "&gt;")
                                                : "";
                                        }).join("\n");
                                        var codeType = subItems.indexOf("&lt;html") >= 0 || subItems.indexOf("&lt;link") >= 0 ? "html" : "js";
                                        elements.push("<pre class='sample-code'><code class='" + codeType + "'>" + subItems + "</code></pre>");
                                    }
                                }
                            });
                        }
                    });
                }
                elements.push("<div class='sample-content-end'></div></div>");
                contentHTML = elements.join("");
            }
            return contentHTML;
        },
        removeMarkItems: removeMarkItems,
        // prepare items for support search & download
        prepareFeatures: function (categorys) {
            if (categorys && !categorys.prepared) {
                categorys.forEach(function (c) {
                    var categoryFolder = c.category.folder;
                    c.featureList.forEach(function (f) {
                        var featureFolder = f.featureResourceFolder;
                        var path = ["page_resource", categoryFolder, featureFolder];
                        f.featureDetails.forEach(function (fd) {
                            fd.keywords = (fd.tags || []).concat([fd.name, c.category.title, f.feature]).map(function (str) {
                                return str.toLowerCase();
                            });
                            fd.fileUrls = (fd.files || []).map(function (name) {
                                return { path: name.indexOf("/") === -1 ? path.concat([name]).join("/") : name, fileName: name };
                            });
                        });
                    });
                });
                categorys.prepared = true;
            }
        },
        // replace file resource with corresponding path for load from json
        replaceFileResource: function (str, $scope) {
            var item = $scope.featureItem;

            if (item && item.fileUrls) {
                item.fileUrls.forEach(function (f) {
                    str = str.replace('url: "' + f.fileName + '"', 'url: "' +  f.path + '"');
                });
            }
            return str;
        }
    }
    return codeService;
}]);