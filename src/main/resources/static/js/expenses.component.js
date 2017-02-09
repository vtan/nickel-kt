var expensesController = function(expenseResource) {
  var ctrl = this;

  ctrl.selectYearMonth = function() {
    if (ctrl.selectedYearMonth != null) {
      expenseResource.getAllInYearMonth(ctrl.selectedYearMonth, function(expenses) {
        ctrl.expensesInSelectedYearMonth = expenses;
      });
    }
  };

  ctrl.deleteExpense = function(id) {
    expenseResource.delete(id, function() {
      ctrl.expensesInSelectedYearMonth = ctrl.expensesInSelectedYearMonth
        .filter(function(expense) { return expense.id != id; });
    });
  };

  expenseResource.getYearMonths(function(yearMonths) {
    ctrl.yearMonths = yearMonths;
    ctrl.selectedYearMonth = ctrl.yearMonths[ctrl.yearMonths.length - 1];
    ctrl.selectYearMonth();
  });
}

angular
  .module('nickel')
  .component('expenses', {
    templateUrl: 'js/expenses.template.html',
    controller: expensesController
  });
