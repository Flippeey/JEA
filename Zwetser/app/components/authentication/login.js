'use strict';

var mod_login = angular.module('login', ['zwetser_navbar']);

mod_login.directive('loginForm', function () {
    return {
        restrict: 'E',
        templateUrl: 'components/login/login_form.html',
        controller: 'LoginController'
    };
});

mod_login.factory('authFactory', ['$rootScope', '$http', '$cookieStore', function ($rootScope, $http, $cookieStore) {

        var authFactory = {
            authData: undefined,
            user: undefined
        };

        authFactory.login = function (user) {
            return $http.post('http://localhost:8080/Zwetser-Backend/api/auth/login', user);
        };


        authFactory.getSessionUser = function (authData) {
            return $http.post('http://localhost:8080/Zwetser-Backend/api/auth/getSessionUser', authData);
        };

        authFactory.setAuthData = function (authData) {
            this.authData = {
                authId: authData.authId,
                authToken: authData.authToken,
                authPermission: authData.authPermission
            };
            $cookieStore.put('authId', authData.authId);
            $cookieStore.put('authToken', authData.authToken);
            $cookieStore.put('authPermission', authData.authPermission);
            $rootScope.$broadcast('authChanged', authData);
        };

        authFactory.getAuthData = function () {
            return this.authData;
        };
        
        authFactory.setUser = function(user) {
            this.user = user;
        };
        
        authFactory.getUser = function() {
            return this.user;
        };

        authFactory.isAuthenticated = function () {
            return !angular.isUndefined(this.getAuthData());
        };


        return authFactory;
    }]);


mod_login.factory('authHttpRequestInterceptor', ['$injector', function ($injector) {
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

mod_login.controller('LoginController', ['$scope', '$state', 'Session', 'authFactory', 'ZwetserAPI', function ($scope, $state, Session, authFactory, ZwetserAPI)
    {
        $scope.username = '';
        $scope.password = '';

        $scope.$on('authChanged', function (event, data) {
            authFactory.getSessionUser(data).success(function (result) {
                           Session.setSessionUser(result);
                $state.go('profile');
                    });
        });

        $scope.isLoggedIn = function ()
        {
            return Object.getOwnPropertyNames(Session.getSessionUser()).length > 0;
        };

//        $scope.login = function (username, password)
//        {
//            var user = loginFactory.authenticate(username, password);
//            if (user) {
//                $scope.username = '';
//                $scope.password = '';
//                Session.setSessionUser(user);
//                $state.go('profile');
//            } else {
//                console.log("Invalid Login");
//            }
//        };

        $scope.login = function (username, password) {
            var user = new Object();
            user.username = username;
            user.password = password;
            authFactory.login(user).success(function (data) {
                            authFactory.setAuthData(data);
                console.log("set auth data " + data);
                    }).error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
                console.log(status + data);
            });
        };

        $scope.logout = function ()
        {
            Session.clearSessionUser();
            $state.go('login');
        };
    }]);