var expensesController = function($http, $window) {
  var ctrl = this;
  var expensesUri = 'api/expenses';
  var yearMonthsUri = expensesUri + '/yearmonths';

  ctrl.yearMonthSelected = function() {
    var params = {
      yearmonth: ctrl.selectedYearMonth
    };
    $http.get(expensesUri, {params: params}).then(
      function success(response) {
        ctrl.expensesInSelectedYearMonth = response.data;
      },
      function failure(response) {
        $window.alert('Status ' + response.status);
      }
    );
  };

  $http.get(yearMonthsUri).then(
    function success(response) {
      ctrl.yearMonths = response.data;
      ctrl.selectedYearMonth = ctrl.yearMonths[ctrl.yearMonths.length - 1];
      ctrl.yearMonthSelected();
    },
    function failure(response) {
      $window.alert('Status ' + response.status);
    }
  );
}

angular
  .module('nickel')
  .component('expenses', {
    templateUrl: 'js/expenses.template.html',
    controller: expensesController
  });
