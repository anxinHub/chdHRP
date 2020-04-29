angular.module('sampleControllers', [])
.controller('sampleListCtrl', ['$scope','$http', 'codeService',
    function($scope, $http, codeService) {
        $scope.isAnimationEnd = true;
        codeService.getResources(['json_resource/controlList.json']).then(function(dataArray) {
            if (dataArray && dataArray.length > 0) {
                $scope.categorys = dataArray[0];
                codeService.prepareFeatures($scope.categorys);
            }
        });
        codeService.getResources(['json_resource/homePage.json']).then(function(dataArray) {
            if (dataArray && dataArray.length > 0) {
                $scope.pageResource = dataArray[0];
                var newFeatures = dataArray[0].newFeatures;
                if (newFeatures && newFeatures.length > 0) {
                    $scope.newFeatures = newFeatures;
                    $scope.newFeatureIndex = 0;
                }
            }
        });
        $scope.preFeature = function() {
            if ($scope.isAnimationEnd) {
                var newFeatures = $scope.newFeatures,
                    newFeatureIndex = $scope.newFeatureIndex;
                if (newFeatureIndex !== null && newFeatureIndex !== undefined && newFeatureIndex > 0 && newFeatures.length > 0) {
                    $scope.newFeatureIndex--;
                }
            }
        }
        $scope.nextFeature = function() {
            if ($scope.isAnimationEnd) {
                var newFeatures = $scope.newFeatures,
                    newFeatureIndex = $scope.newFeatureIndex;
                if (newFeatureIndex !== null && newFeatureIndex !== undefined && newFeatures.length > 0 && newFeatureIndex < newFeatures.length - 1) {
                    $scope.newFeatureIndex++;
                }
            }
        }

        getFeatureDetails = function (feature, key) {
            var result = feature.featureDetails;

            if (key) {
                key = key.toLowerCase();
                result = result.filter(function (item) {
                    return item.keywords.some(function (element) {
                        return element.indexOf(key) >= 0;
                    })
                });
            }

            return result;
        }

        $scope.getCategorys = function (key) {
            var result = $scope.categorys;

            // data not ready yet
            if (!result) return undefined;

            if (key) {
                result = result.filter(function (category) {
                    var featureList = category.filterFeatureList = [];

                    category.featureList.forEach(function (feature) {
                        var items = feature.filterFeatureDetails = getFeatureDetails(feature, key);

                        if (items.length > 0) {
                            featureList.push(feature);
                        }
                    });

                    return featureList.length > 0;
                });
            } else {
                result.forEach(function (category) {
                    var features = category.featureList;
                    category.filterFeatureList = features;

                    features.forEach(function (feature) {
                        feature.filterFeatureDetails = feature.featureDetails;
                    });
                });
            }

            return result;
        }
    }
])
.controller('sampleDetailCtrl', ['$scope', '$routeParams', '$route', '$location','$http', '$timeout', 'codeService',
    function ($scope, $routeParams, $route, $location, $http, $timeout, codeService) {
        $scope.location = $location;
        $scope.route = $route;

        $scope.$watch('featureItem', function(newValue) {
            if (newValue) {
                var pathParams = $scope.route.current.pathParams,
                    resource = newValue.resource;

                // sync location path, allow refresh with correct result
                if (resource !== pathParams["resource"]) {
                    pathParams["resource"] = resource;
                    $scope.location.path('/samples/' + resource);
                } else {
                    window.scrollTo(0, 0);
                }

                var featureDetails = $scope.featureDetails;
                if (featureDetails && featureDetails.length > 0 && newValue) {
                    var itemIndex = featureDetails.indexOf(newValue);
                    $scope.nameIndex = itemIndex;
                    var resource = newValue.resource;
                    var references = newValue.references;
                    var resourceURL = $scope.category.folder + '/' + $scope.featureResourceFolder + '/' + resource;
                    codeService.getResource('json_resource/content/' + resourceURL + '.json', $scope, 'content');
                    codeService.getResources(['page_resource/' + resourceURL + '.html']).then(function(dataArray) {
                        if (dataArray && dataArray.length > 0) {
                            var codeContent = dataArray[0];
                            $scope.codeContent = codeContent;
                            var codeTemplate = $scope.codeTemplate;
                            if (codeContent && codeContent !== '' && codeTemplate && codeTemplate !== '') {
                                var code = codeService.codeMerge(codeTemplate, codeContent, references, $scope.references);
                                $scope.code = code.codeAll;
                                $scope.codeJS = code.codeJS;
                                $scope.codeBody = code.codeBody;
                                $scope.codeCss = code.codeCss;
                                $scope.codeResource = code.codeResource;
                                $scope.localResource = code.localResource;
                                $scope.cacheChoice = 'codeJS';
                            }
                        }
                    });
                }
            }
        });
        $scope.apply = function() {
            $scope.code = codeService.jsCodeChange($scope, $scope.code);
        }
        $scope.switchJS = function() {
            codeService.switchCode($scope, 'codeJS');
        }
        $scope.switchHTML = function() {
            codeService.switchCode($scope, 'codeBody');
        }
        $scope.switchCSS = function() {
            codeService.switchCode($scope, 'codeCss');
        }
        $scope.switchResources = function() {
            codeService.switchCode($scope, 'codeResource');
        }
        $scope.preStep = function () {
            var featureDetails = $scope.featureDetails, featureItem = $scope.featureItem;
            if (featureDetails && featureDetails.length > 0 && featureItem) {
                var itemIndex = featureDetails.indexOf(featureItem);
                if (itemIndex > 0) {
                    itemIndex--;
                    $scope.featureItem = featureDetails[itemIndex];
                }
            }
        }
        $scope.nextStep = function () {
            var featureDetails = $scope.featureDetails, featureItem = $scope.featureItem;
            if (featureDetails && featureDetails.length > 0 && featureItem) {
                var itemIndex = featureDetails.indexOf(featureItem);
                if (itemIndex < featureDetails.length - 1) {
                    itemIndex++;
                    $scope.featureItem = featureDetails[itemIndex];
                }
            }
        }
        $scope.resource = $routeParams.resource;
        $scope.featureDetails = [];
        codeService.getResources(['json_resource/controlList.json']).then(function(dataArray) {
            if (dataArray && dataArray.length > 0) {
                var categorys = dataArray[0];
                var details = [], resource = $scope.resource;

                codeService.prepareFeatures(categorys);

                for (var i = 0, len = categorys.length; i < len; i++){
                    var category = categorys[i];
                    if (category && category.featureList) {
                        var featureList = category.featureList;
                        for (var j = 0, len1 = featureList.length; j < len1; j++) {
                            var feature = featureList[j];
                            if (feature && feature.featureDetails) {
                                var featureDetails = feature.featureDetails;
                                for (var k = 0, len2 = featureDetails.length; k < len2; k++) {
                                    var featureItem = featureDetails[k];
                                    if (featureItem && featureItem.resource === resource){
                                        $scope.featureDetails = details.concat(featureDetails);
                                        $scope.featureItem = featureItem;
                                        break;
                                    }
                                }
                                if (k !== len2) {
                                    $scope.feature = feature.feature;
                                    $scope.featureResourceFolder = feature.featureResourceFolder;
                                    break;
                                }
                            }
                        }
                        if (j !== len1) {
                            $scope.category = category.category;
                            break;
                        }
                    }
                }
            }
        });
        codeService.getResources(['page_resource/sample_template.html']).then(function(dataArray) {
            if (dataArray && dataArray.length > 0) {
                var codeTemplate = dataArray[0];
                $scope.codeTemplate = codeTemplate;
                /* not sure when need this logic, the usage of $scope.code is not same as other place
                var codeContent = $scope.codeContent;
                if (codeTemplate && codeTemplate !== '' && codeContent && codeContent !== '') {
                    $scope.code = codeService.codeMerge(codeTemplate, codeContent);
                }
                */
            }
        });
        codeService.getResources(['json_resource/childPage.json']).then(function(dataArray) {
            if (dataArray && dataArray.length > 0) {
                $scope.pageResource = dataArray[0];
            }
        });
        codeService.getResources(['json_resource/references.json']).then(function (dataArray) {
            if (dataArray && dataArray.length > 0) {
                $scope.references = dataArray[0];
            }
        });
    }
]);









