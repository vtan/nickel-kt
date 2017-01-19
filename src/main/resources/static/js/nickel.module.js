angular
  .module('nickel', ['ngRoute', 'chart.js'])
  .config(function($locationProvider, $routeProvider) {
    $locationProvider.hashPrefix('!');

    $routeProvider
      .when('/new', {
        template: '<entry-form></entry-form>'
      })
      .when('/expenses', {
        template: '<expenses></expenses>'
      })
      .when('/chart', {
        template: '<chart></chart>'
      })
      .otherwise('/new');
  });
