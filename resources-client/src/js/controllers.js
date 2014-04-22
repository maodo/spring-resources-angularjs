var springControllers = angular.module('springControllers', []);

springControllers.controller('HelloCtrl', ['$scope',
    function ($scope) {
        $scope.name = 'world';
    }]
);

springControllers.controller('HelloNameCtrl', ['$scope', '$routeParams',
    function($scope, $routeParams) {
        $scope.name = $routeParams.name;
    }]
);