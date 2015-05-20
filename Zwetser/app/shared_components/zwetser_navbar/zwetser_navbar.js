'use strict';
var mod_navbar = angular.module('zwetser_navbar', ['angucomplete']);
mod_navbar.directive('zwNavbar', function () {
    return {
        restrict: 'E',
        templateUrl: 'shared_components/zwetser_navbar/zwetser_navbar.html',
        controller: 'NavBarController'
    };
});
mod_navbar.controller('NavBarController', ['$scope', '$state', 'authFactory', 'ZwetserAPI', function ($scope, $state, authFactory, ZwetserAPI) {
        $scope.isLoggedIn = function ()
        {
            return authFactory.isAuthenticated();
        };
        
        $scope.logout = function ()
        {
            authFactory.clearAuthData();
            $state.go('login');
        };

        if ($scope.isLoggedIn()) {
            authFactory.getSessionUser(authFactory.getAuthData()).success(function (result) {
                $scope.sessionUser = result;
            });
            ZwetserAPI.getUsers().$promise.then(function (result) {
                $scope.users = result;
            });
        }

        $scope.$on('selectedUser', function (event, data) {
            $state.go('users', {id: data.id});
        });
    }]);