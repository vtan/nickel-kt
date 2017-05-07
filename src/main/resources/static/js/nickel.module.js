angular
  .module('nickel', ['ngRoute', 'chart.js'])
  .config(function($locationProvider, $routeProvider) {
    $locationProvider.hashPrefix('!');

    $routeProvider
      .when('/new-expense', {
        template: '<expense-entry></expense-entry>'
      })
      .when('/expenses', {
        template: '<expenses></expenses>'
      })
      .when('/incomes', {
        template: '<incomes></incomes>'
      })
      .when('/chart', {
        template: '<chart></chart>'
      })
      .otherwise('/new-expense');
  });
