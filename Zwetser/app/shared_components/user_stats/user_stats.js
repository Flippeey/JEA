'use strict';

var mod_userStats = angular.module('user_stats', []);

mod_userStats.directive('userStats', function () {
    return {
        restrict: 'E',
        templateUrl: 'shared_components/user_stats/user_stats.html',
        controller: 'UserStatsController'
    };
});

mod_userStats.controller('UserStatsController', [function () {
       
    }]);