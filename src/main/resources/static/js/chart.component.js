var chartController = function(expenseResource) {
  var ctrl = this;
  var expensesUri = 'api/expenses';

  var chartOptions = {
    scales: {
      yAxes: [{
        ticks: {
          beginAtZero: true
        },
        stacked: true
      }]
    }
  };

  ctrl.createChartFromExpenseSums = function(expenseSums) {
    var months = expenseSums
      .map(function(entry) { return entry.month; })
      .filter(function(elem, index, self) { return index == self.indexOf(elem); })
      .sort();
    var categories = expenseSums
      .map(function(entry) { return entry.category; })
      .filter(function(elem, index, self) { return index == self.indexOf(elem); });
    var dataByCategories = categories
      .reduce(function(result, category) {
        var monthlySums = months.map(function(month) {
          var foundEntry = expenseSums
            .find(function(entry) { return entry.category == category && entry.month == month; })
          return foundEntry ? foundEntry.sum : 0;
        });
        var sumOfRecentMonths = monthlySums
          .slice(-6)
          .reduce(function(x, y) { return x + y; }, 0);

        result[category] = {
          data: monthlySums,
          ordinal: -sumOfRecentMonths
        };
        return result;
      }, {});
    var sortedCategories = categories
      .sort(function(x, y) { return dataByCategories[x].ordinal - dataByCategories[y].ordinal; });
    var sortedData = sortedCategories
      .map(function(category) { return dataByCategories[category].data; });

    ctrl.months = months;
    ctrl.categories = sortedCategories;
    ctrl.categoryVisibilities = sortedCategories
      .reduce(function(result, category) { result[category] = true; return result; }, {});
    ctrl.data = sortedData;
    ctrl.chart = {
      labels: months,
      series: sortedCategories,
      data: sortedData,
      options: chartOptions
    };
  }

  ctrl.applyCategoryVisibilities = function() {
    var dataVisibilities = {};
    ctrl.chart.series = ctrl.categories
      .filter(function(category, index) {
        var result = ctrl.categoryVisibilities[category];
        dataVisibilities[index] = result
        return result;
      });
    ctrl.chart.data = ctrl.data
      .filter(function(data, index) { return dataVisibilities[index]; });
  }

  expenseResource.getSumsByCategoryAndYearMonth(function(sums) {
    ctrl.createChartFromExpenseSums(sums);
  });
};

angular
  .module('nickel')
  .component('chart', {
    templateUrl: 'js/chart.template.html',
    controller: chartController
  });
