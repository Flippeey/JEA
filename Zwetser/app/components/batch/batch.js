'use strict';

var mod_batch = angular.module('batch', []);

mod_batch.directive('batch-dir', function () {
    return {
        restrict: 'E',
        templateUrl: '<form>  <input type="submit" class="btn btn-default" ng-click="importTweets()" value="Import tweets"/></form>',
        controller: 'BatchController'
    };
});

mod_batch.controller('BatchController', ['$http', '$scope', function ($http, $scope) {
        //Simple http call to tell the backend to start importing the batch. Check progress in Glassfish console.
        $scope.importTweets = function () {
            $http.post('http://localhost:8080/Zwetser-Backend/api/auth/importTweets').then(function (data) {
                console.log(data);
            });
        };
    }]);