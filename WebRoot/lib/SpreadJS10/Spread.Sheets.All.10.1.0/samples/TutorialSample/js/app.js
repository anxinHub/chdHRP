/* App Module*/
angular.module('sampleApp', [
    'ngRoute',
    'sampleAppAnimations',
    'sampleControllers',
    'sampleServices',
    'sampleDirective'
])
.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider
            .when('/samples', {
                templateUrl: 'samples/sample-list.html',
                controller: 'sampleListCtrl'
            })
            .when('/samples/:resource', {
                templateUrl:'samples/sample-detail.html',
                controller: 'sampleDetailCtrl',
                reloadOnSearch: false
            })
            .otherwise({
                redirectTo: 'samples'
            });
    }
]);