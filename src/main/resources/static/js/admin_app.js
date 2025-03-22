var app = angular.module("megaviaApp", ["ngRoute"]);

app.controller("MainController", function ($scope) {
  $scope.isSidebarHidden = false;
  $scope.toggleSidebar = function () {
    $scope.isSidebarHidden = !$scope.isSidebarHidden;
  };
});

app.config(function ($routeProvider) {
  $routeProvider
    .when("/statistics", {
      templateUrl: "admin/views/statistics.html",
      controller: "StatisticsController",
    })
    .when("/employee", {
      templateUrl: "admin/views/employee.html",
      controller: "EmployeeController",
    })
    .when("/customer", {
      templateUrl: "admin/views/customer.html",
      controller: "CustomerController",
    })
    .when("/account", {
      templateUrl: "admin/views/account.html",
      controller: "AccountController",
    })
    .when("/service", {
      templateUrl: "admin/views/service.html",
      controller: "ServiceController",
    })
    .when("/accessory", {
      templateUrl: "admin/views/accessory.html",
      controller: "AccessoryController",
    })
    .when("/booking", {
      templateUrl: "admin/views/booking.html",
      controller: "BookingController",
    })
    .when("/invoice", {
      templateUrl: "admin/views/invoice.html",
      controller: "InvoiceController",
    })
    .when("/", {
      redirectTo: "/statistics",
    })
    .otherwise({
      redirectTo: "/statistics",
    });
});

// ========================= NhanVienService =========================
// ============== Service cho NhanVien ==============
app.factory("NhanVienService", function ($http) {
  var baseUrl = "/api/nhanvien";
  return {
    // 1. Lấy tất cả nhân viên
    getAllNhanVien: function () {
      return $http.get(baseUrl);
    },
    // 2. Lấy nhân viên theo ID
    getNhanVienById: function (id) {
      return $http.get(baseUrl + "/" + id);
    },
    // 3. Thêm nhân viên (kèm file)
    addNhanVien: function (nhanVien, file) {
      var formData = new FormData();
      formData.append("nhanVien", new Blob([JSON.stringify(nhanVien)], { type: "application/json" }));
      formData.append("file", file);
      return $http.post(baseUrl + "/upload", formData, {
        headers: { "Content-Type": undefined },
      });
    },
    // 4a. Cập nhật nhân viên không có file
    updateNhanVien: function (id, nhanVien) {
      return $http.put(baseUrl + "/" + id, nhanVien);
    },
    // 4b. Cập nhật nhân viên có file
    updateNhanVienWithFile: function (id, nhanVien, file) {
      var formData = new FormData();
      formData.append("nhanVien", new Blob([JSON.stringify(nhanVien)], { type: "application/json" }));
      formData.append("file", file);
      return $http.put(baseUrl + "/updateWithFile/" + id, formData, {
        headers: { "Content-Type": undefined },
      });
    },
    // 5. Xóa nhân viên
    deleteNhanVien: function (id) {
      return $http.delete(baseUrl + "/" + id);
    },
  };
});

// ============== Controller cho trang quản lý nhân viên ==============
app.controller("EmployeeController", function ($scope, NhanVienService) {
  $scope.pageTitle = "Quản lý nhân viên";
  $scope.employees = [];
  $scope.newEmployee = { taiKhoanNV: {} };
  $scope.isEditMode = false;
  $scope.file = null;

  // Phân trang
  $scope.currentPage = 1;
  $scope.pageSize = 5;
  $scope.totalItems = 0;

  // Load danh sách
  $scope.getAllEmployees = function () {
    NhanVienService.getAllNhanVien().then(
      function (response) {
        $scope.employees = response.data;
        $scope.totalItems = $scope.employees.length;
      },
      function (error) {
        console.error("Lỗi khi lấy danh sách nhân viên:", error);
      }
    );
  };

  $scope.getPageCount = function () {
    return Math.ceil($scope.totalItems / $scope.pageSize);
  };

  $scope.setPage = function (page) {
    if (page >= 1 && page <= $scope.getPageCount()) {
      $scope.currentPage = page;
    }
  };

  $scope.getPaginatedData = function () {
    var start = ($scope.currentPage - 1) * $scope.pageSize;
    return $scope.employees.slice(start, start + $scope.pageSize);
  };

  // Lưu file chọn vào $scope.file
  $scope.setFile = function (files) {
    if (files && files.length > 0) {
      $scope.file = files[0];
      $scope.$apply();
    }
  };

  // Lưu nhân viên (thêm mới hoặc sửa)
  $scope.saveEmployee = function () {
    if (!$scope.isEditMode) {
      // Thêm mới
      if (!$scope.file) {
        alert("Vui lòng chọn file ảnh!");
        return;
      }
      NhanVienService.addNhanVien($scope.newEmployee, $scope.file).then(
        function (res) {
          $scope.getAllEmployees();
          $scope.newEmployee = { taiKhoanNV: {} };
          $scope.file = null;
          document.getElementById("profileImage").value = "";
        },
        function (err) {
          console.error("Lỗi khi thêm nhân viên:", err);
        }
      );
    } else {
      // Cập nhật
      if ($scope.file) {
        // Cập nhật có file
        NhanVienService.updateNhanVienWithFile($scope.newEmployee.idNhanVien, $scope.newEmployee, $scope.file).then(
          function (res) {
            $scope.getAllEmployees();
            $scope.newEmployee = { taiKhoanNV: {} };
            $scope.isEditMode = false;
            $scope.file = null;
            document.getElementById("profileImage").value = "";
          },
          function (err) {
            console.error("Lỗi khi cập nhật nhân viên:", err);
          }
        );
      } else {
        // Cập nhật không file
        NhanVienService.updateNhanVien($scope.newEmployee.idNhanVien, $scope.newEmployee).then(
          function (res) {
            $scope.getAllEmployees();
            $scope.newEmployee = { taiKhoanNV: {} };
            $scope.isEditMode = false;
          },
          function (err) {
            console.error("Lỗi khi cập nhật nhân viên:", err);
          }
        );
      }
    }
  };

  // Chọn nhân viên để sửa
  $scope.selectEmployee = function (employee) {
    $scope.newEmployee = angular.copy(employee);
    if (!$scope.newEmployee.taiKhoanNV) {
      $scope.newEmployee.taiKhoanNV = {};
    }
    $scope.isEditMode = true;
  };

  // Hàm xóa nhân viên
  $scope.deleteEmployee = function (idNhanVien) {
    if (confirm("Bạn có chắc chắn muốn xóa?")) {
      NhanVienService.deleteNhanVien(idNhanVien).then(
        function () {
          alert("Nhân viên đã được xóa thành công!");
          $scope.getAllEmployees();
        },
        function (err) {
          console.error("Lỗi khi xóa nhân viên:", err);
          alert("Lỗi khi xóa nhân viên, vui lòng thử lại.");
        }
      );
    }
  };

  // Khởi tạo
  $scope.getAllEmployees();
});

// Các Controller khác
app.controller("CustomerController", function ($scope) {
  $scope.pageTitle = "Quản lý khách hàng";
});
app.controller("AccountController", function ($scope) {
  $scope.pageTitle = "Quản lý tài khoản";
});
app.controller("ServiceController", function ($scope) {
  $scope.pageTitle = "Quản lý dịch vụ";
});
app.controller("AccessoryController", function ($scope) {
  $scope.pageTitle = "Quản lý phụ tùng";
});
app.controller("BookingController", function ($scope) {
  $scope.pageTitle = "Quản lý lịch hẹn";
});
app.controller("InvoiceController", function ($scope) {
  $scope.pageTitle = "Quản lý hoá đơn";
});
app.controller("StatisticsController", function ($scope) {
  $scope.pageTitle = "Thống kê";
});
