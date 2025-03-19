var admin_app = angular.module("megaviaApp", ["ngRoute"]);

admin_app.controller("MainController", function ($scope) {
  $scope.isSidebarHidden = false;

  $scope.toggleSidebar = function () {
    $scope.isSidebarHidden = !$scope.isSidebarHidden;
  };
});

// Cấu hình các route
admin_app.config(function ($routeProvider) {
  $routeProvider
    .when("/", {
      templateUrl: " /statistics",
      controller: "StatisticsController",
    })
    .when("/employee", {
      templateUrl: "/employee",
      controller: "EmployeeController",
    })
    .when("/customer", {
      templateUrl: "/customer",
      controller: "CustomerController",
    })
    .when("/account", {
      templateUrl: "/account",
      controller: "AccountController",
    })
    .when("/service", {
      templateUrl: "/service",
      controller: "ServiceController",
    })
    .when("/accessory", {
      templateUrl: "/accessory",
      controller: "AccessoryController",
    })
    .when("/booking", {
      templateUrl: "/booking",
      controller: "BookingController",
    })
    .when("/invoice", {
      templateUrl: "/invoice",
      controller: "InvoiceController",
    })
    .otherwise({
      redirectTo: "/", // Điều hướng mặc định nếu không tìm thấy route
    });
});

admin_app.controller("EmployeeController", function ($scope, $http) {
  $scope.pageTitle = "Quản lý nhân viên";

  $scope.employees = [];
  $scope.employee = {};

  // Lấy danh sách nhân viên
  $scope.getAllEmployees = function () {
    $http.get("/api/nhanvien").then(function (response) {
      $scope.employees = response.data;
    });
  };

  // Thêm mới nhân viên
  $scope.addEmployee = function () {
    $http.post("/api/nhanvien", $scope.employee).then(function (response) {
      $scope.getAllEmployees();
      $scope.employee = {}; // Reset form
    });
  };

  // Sửa nhân viên
  $scope.updateEmployee = function () {
    $http.put("/api/nhanvien/" + $scope.employee.idNhanVien, $scope.employee).then(function (response) {
      $scope.getAllEmployees();
      $scope.employee = {}; // Reset form
    });
  };

  // Xóa nhân viên
  $scope.deleteEmployee = function (id) {
    if (confirm("Bạn có chắc chắn muốn xóa nhân viên này?")) {
      $http.delete("/api/nhanvien/" + id).then(function (response) {
        $scope.getAllEmployees();
      });
    }
  };

  // Gọi hàm để lấy danh sách nhân viên ngay khi khởi tạo
  $scope.getAllEmployees();
});

admin_app.controller("CustomerController", function ($scope) {
  $scope.pageTitle = "Quản lý khách hàng";
});
admin_app.controller("AccountController", function ($scope) {
  $scope.pageTitle = "Quản lý tài khoản";
});
admin_app.controller("ServiceController", function ($scope) {
  $scope.pageTitle = "Quản lý dịch vụ";
});
admin_app.controller("AccessoryController", function ($scope) {
  $scope.pageTitle = "Quản lý phụ tùng";
});
admin_app.controller("BookingController", function ($scope) {
  $scope.pageTitle = "Quản lý lịch hẹn";
});
admin_app.controller("InvoiceController", function ($scope) {
  $scope.pageTitle = "Quản lý hoá đơn";
});
admin_app.controller("StatisticsController", function ($scope) {
  $scope.pageTitle = "Thống kê";
});
