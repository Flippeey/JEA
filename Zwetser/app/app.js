'use strict';

// Declare app level module which depends on views, and components
var app = angular.module('Zwetser', [
    'ui.router',
    'ngResource',
    'ngCookies',
    'user',
    'profile',
    'authentication',
    'version',
    'angucomplete',
    'ngStorage'
]);

app.config(function ($stateProvider, $httpProvider) {
    $httpProvider.interceptors.push('authHttpRequestInterceptor');

    $stateProvider.state('login', {
        url: '/login',
        templateUrl: 'components/authentication/authentication.html',
        controller: 'LoginController'
    }).state('users', {
        url: '/users/:id/',
        templateUrl: 'components/user/user.html',
        controller: 'UserController'
    }).state('profile', {
        url: '/profile',
        templateUrl: 'components/profile/profile.html',
        controller: 'ProfileController'
    });
}).run(function ($state, authFactory) {
    if (authFactory.isAuthenticated()) {
        $state.go('profile');                
    } else {
        $state.go('login');
    }
});

app.factory('ZwetserAPI', function ($resource) {

    return $resource('http://localhost:8080/Zwetser-Backend/api/users/:id/:command', {id: '@id', command: '@command'}, {
        'getUsers': {method: 'GET', isArray: true},
        'getUser': {method: 'GET'},
        'getSessionUser': {method: 'GET', params: {command: 'getSessionUser'}},
        'countFollowers': {method: 'GET', params: {command: 'countFollowers'}},
        'countUsers': {method: 'GET', params: {command: 'countUsers'}},
        'countTweets': {method: 'GET', params: {command: 'countTweets'}},
        'getFollowers': {method: 'GET', isArray: true, params: {command: 'followers'}},
        'getFollowing': {method: 'GET', isArray: true, params: {command: 'following'}},
        'getTweets': {method: 'GET', isArray: true, params: {command: 'tweets'}},
        'getAllTweets': {method: 'GET', isArray: true, params: {command: 'alltweets'}},
        'getMentions': {method: 'GET', isArray: true, params: {command: 'mentions'}},
        'getTimeline': {method: 'GET', isArray: true, params: {command: 'timeline'}},
        'addUser': {method: 'POST', params: {command: 'addUser'}},
        'addTweet': {method: 'POST', params: {command: 'addTweet'}},
        'addFollow': {method: 'POST', params: {command: 'addFollow'}},
        'deleteUser': {method: 'DELETE', params: {command: 'deleteUser'}},
        'deleteTweet': {method: 'POST', params: {command: 'deleteTweet'}},
        'deleteFollow': {method: 'POST', params: {command: 'deleteFollow'}},
        'updateUser': {method: 'PUT', params: {command: 'updateUser'}},
        'updateTweet': {method: 'PUT', params: {command: 'updateTweet'}},
        'updateFollow': {method: 'PUT', params: {command: 'updateFollow'}}
    });
});


app.service('popupService', function ($window) {
    this.showPopup = function (message) {
        return $window.confirm(message);
    };
});

