var incomesController = function(incomeResource) {
  var ctrl = this;

  ctrl.incomes = [];
  ctrl.initInput = function() {
    ctrl.input = {
      beginMonth: '',
      endMonth: null,
      amount: '',
      description: ''
    };
  }

  ctrl.createIncome = function() {
    var newIncome = {
      beginMonth: ctrl.input.beginMonth,
      endMonth: !ctrl.input.endMonth || !ctrl.input.endMonth.trim()
        ? null
        : ctrl.input.endMonth,
      amount: ctrl.input.amount,
      category: ctrl.input.category,
      description: ctrl.input.description
    };
    incomeResource.create(newIncome, function() {
      ctrl.initInput();
      ctrl.getIncomes();
    })
  };

  ctrl.deleteIncome = function(id) {
    incomeResource.delete(id, function() {
      ctrl.incomes = ctrl.incomes
        .filter(function(income) { return income.id != id; });
    });
  };

  ctrl.getIncomes = function() {
    incomeResource.getAll(function(incomes) {
      ctrl.incomes = incomes;
    });
  };

  ctrl.getIncomes();
}

angular
  .module('nickel')
  .component('incomes', {
    templateUrl: 'js/incomes.template.html',
    controller: incomesController
  });
