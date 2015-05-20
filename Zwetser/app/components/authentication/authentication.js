'use strict';

var mod_auth = angular.module('authentication', ['zwetser_navbar', 'batch']);

mod_auth.directive('loginForm', function () {
    return {
        restrict: 'E',
        templateUrl: 'components/authentication/login_form.html',
        controller: 'LoginController'
    };
});

mod_auth.factory('authFactory', ['$rootScope', '$http', '$localStorage', function ($rootScope, $http, $localStorage) {

        var authFactory = {
            authData: undefined
        };

        authFactory.login = function (user) {
            return $http.post('http://localhost:8080/Zwetser-Backend/api/auth/login', user);
        };

        authFactory.getSessionUser = function (authData) {
            return $http.post('http://localhost:8080/Zwetser-Backend/api/auth/getSessionUser', authData);
        };
        authFactory.importTweets = function () {
            return $http.post('http://localhost:8080/Zwetser-Backend/api/auth/importTweets');
        };

        authFactory.setAuthData = function (authData) {
            this.authData = {
                authId: authData.authId,
                authToken: authData.authToken,
                authPermission: authData.authPermission
            };
            this.setAccessElement(authData);
            $rootScope.$broadcast('authChanged', authData);
        };

        authFactory.getAuthData = function () {
            this.authData = this.getAccessElement();
            return this.authData;
        };

        authFactory.clearAuthData = function () {
            this.clearAccessElement();
            this.authData = undefined;
        };

        authFactory.setAccessElement = function (authData) {
            $localStorage.authId = authData.authId;
            $localStorage.authToken = authData.authToken;
            $localStorage.authPermission = authData.authPermission;
        };

        authFactory.getAccessElement = function () {
            return  {
                authId: $localStorage.authId,
                authToken: $localStorage.authToken,
                authPermission: $localStorage.authPermission
            };
        };

        authFactory.clearAccessElement = function () {
            delete $localStorage.authId;
            delete $localStorage.authToken;
            delete $localStorage.authPermission;
        };

        authFactory.isAuthenticated = function () {
            return !angular.isUndefined(this.getAuthData().authToken);
        };


        return authFactory;
    }]);


mod_auth.factory('authHttpRequestInterceptor', ['$injector', function ($injector) {
        var authHttpRequestInterceptor = {
            request: function ($request) {
                var authFactory = $injector.get('authFactory');
                if (authFactory.isAuthenticated()) {
                    var data = authFactory.getAuthData();
                    $request.headers['auth-id'] = data.authId;
                    $request.headers['auth-token'] = data.authToken;
                }
                return $request;
            }
        };
        return authHttpRequestInterceptor;
    }
]);

mod_auth.controller('LoginController', ['$scope', '$state', 'authFactory', function ($scope, $state, authFactory)
    {
        $scope.username = '';
        $scope.password = '';
        $scope.status = '';

        $scope.$on('authChanged', function (event, data) {
            if (authFactory.isAuthenticated()) {
                $scope.status = '';
                $state.go('profile');
            } else {
                $scope.status = 'The authentication process was unsuccessful. Please check your credentials and try again.';
            }
        });

        $scope.importTweets = function () {
            authFactory.importTweets().then(function (data) {
                $scope.status = 'Started batch import, please go to your glassfish admin console to check the status.';
            });
        };
        
        $scope.login = function (username, password) {
            var user = new Object();
            user.username = username;
            user.password = password;
            $scope.status = 'Attempting authentication, please wait...';
            authFactory.login(user).success(function (data) {
                            authFactory.setAuthData(data);
            });
        };
    }]);