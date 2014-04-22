'use strict';

var springApp = angular.module('springApp', ['ngRoute', 'springControllers']);

springApp
    .config(['$routeProvider', '$locationProvider',
        function($routeProvider, $locationProvider) {
            $locationProvider.html5Mode(true);
            $routeProvider.
                when('/hello', {
                    templateUrl: '/partials/hello.html',
                    controller: 'HelloCtrl'
                }).
                when('/hello/:name', {
                    templateUrl: '/partials/hello.html',
                    controller: 'HelloNameCtrl'
                }).
                otherwise({
                    redirectTo: '/hello'
                });
    }]);