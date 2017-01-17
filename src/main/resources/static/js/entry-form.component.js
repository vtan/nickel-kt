var entryFormController = function($http, $window) {
  var ctrl = this;
  var expensesUri = 'api/expenses';

  ctrl.input = {
    date: new Date(),
    amounts: '',
    category: ''
  };

  ctrl.createExpense = function() {
    var newExpense = {
      date: ctrl.input.date,
      amount: ctrl.calcAmount(),
      category: ctrl.input.category
    };
    $http.post(expensesUri, newExpense).then(
      function success(response) {
        ctrl.input = {};
      },
      function failure(response) {
        $window.alert('Status ' + response.status);
      }
    );
  };

  ctrl.calcAmount = function() {
    return ctrl.input.amounts
      .split(' ')
      .map(function(x) { return parseInt(x, 10); })
      .filter(function(x) { return !isNaN(x); })
      .reduce(function(x, y) { return x + y; }, 0);
  };
};

angular
  .module('nickel')
  .component('entryForm', {
    templateUrl: 'js/entry-form.template.html',
    controller: entryFormController
  });
