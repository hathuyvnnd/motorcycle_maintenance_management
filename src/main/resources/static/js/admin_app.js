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

// MHÂN VIÊN ------------------------------------------------------------------------------------
// Service cho NhanVien: gọi API của Spring Boot tại /api/nhanvien
app.factory("NhanVienService", function ($http) {
  var baseUrl = "/api/nhanvien";
  return {
    // Lấy tất cả nhân viên
    getAllNhanVien: function () {
      return $http.get(baseUrl);
    },
    // Lấy nhân viên theo ID
    getNhanVienById: function (id) {
      return $http.get(baseUrl + "/" + id);
    },
    // Thêm nhân viên
    addNhanVien: function (nhanVien, hinhAnh) {
      var formData = new FormData();
      formData.append("nhanVien", new Blob([JSON.stringify(nhanVien)], { type: "application/json" }));
      formData.append("file", hinhAnh);

      return $http.post(baseUrl + "/upload", formData, {
        headers: {
          "Content-Type": undefined,
        },
      });
    },
    // Cập nhật nhân viên
    updateNhanVien: function (id, nhanVien) {
      return $http.put(baseUrl + "/" + id, nhanVien);
    },
    // Xóa nhân viên
    deleteNhanVien: function (id) {
      return $http.delete(baseUrl + "/" + id);
    },
  };
});

// Controller cho trang quản lý nhân viên
app.controller("EmployeeController", function ($scope, NhanVienService) {
  $scope.pageTitle = "Quản lý nhân viên";
  $scope.employees = [];
  $scope.newEmployee = {}; // Đối tượng dùng để thêm mới
  $scope.selectedEmployee = {}; // Đối tượng dùng để sửa
  $scope.file = null;
  // Biến phân trang
  $scope.currentPage = 1;
  $scope.pageSize = 5; // Số mục trên mỗi trang
  $scope.totalItems = 0;

  // Hàm load danh sách nhân viên
  $scope.getAllEmployees = function () {
    NhanVienService.getAllNhanVien().then(
      function (response) {
        $scope.employees = response.data;
        $scope.totalItems = $scope.employees.length;

        //Gán timestamp để tránh cache ảnh
        // $scope.currentTimestamp = new Date().getTime();
      },
      function (error) {
        console.error("Lỗi khi lấy danh sách nhân viên:", error);
      }
    );
  };

  // Hàm trả về số trang
  $scope.getPageCount = function () {
    return Math.ceil($scope.totalItems / $scope.pageSize);
  };

  // Hàm chuyển trang
  $scope.setPage = function (page) {
    if (page >= 1 && page <= $scope.getPageCount()) {
      $scope.currentPage = page;
    }
  };

  // Hàm cắt dữ liệu theo trang hiện tại
  $scope.getPaginatedData = function () {
    var start = ($scope.currentPage - 1) * $scope.pageSize;
    return $scope.employees.slice(start, start + $scope.pageSize);
  };
  // Hàm gán file từ input vào biến $scope.file
  $scope.setFile = function (files) {
    if (files && files.length > 0) {
      $scope.file = files[0];
      $scope.$apply(); // Cập nhật lại scope nếu cần
    }
  };

  // Hàm thêm nhân viên mới với file upload
  $scope.addEmployee = function () {
    if ($scope.file) {
      NhanVienService.addNhanVien($scope.newEmployee, $scope.file).then(
        function (response) {
          $scope.getAllEmployees();
          $scope.newEmployee = {};
          $scope.file = null;
          // Nếu muốn reset input file, bạn có thể thực hiện thêm sau:
          document.getElementById("profileImage").value = "";
        },
        function (error) {
          console.error("Lỗi khi thêm nhân viên:", error);
        }
      );
    } else {
      alert("Vui lòng chọn file ảnh");
    }
  };

  // Chọn nhân viên để chỉnh sửa
  $scope.selectEmployee = function (employee) {
    $scope.selectedEmployee = angular.copy(employee);
  };

  // Cập nhật nhân viên
  $scope.updateEmployee = function () {
    NhanVienService.updateNhanVien($scope.selectedEmployee.idNhanVien, $scope.selectedEmployee).then(
      function (response) {
        $scope.getAllEmployees(); // Load lại danh sách
        $scope.selectedEmployee = {};
      },
      function (error) {
        console.error("Lỗi khi cập nhật nhân viên:", error);
      }
    );
  };

  // Xóa nhân viên
  $scope.deleteEmployee = function (idNhanVien) {
    if (confirm("Bạn có chắc chắn muốn xóa?")) {
      NhanVienService.deleteNhanVien(idNhanVien).then(
        function () {
          $scope.getAllEmployees();
        },
        function (error) {
          console.error("Lỗi khi xóa nhân viên:", error);
        }
      );
    }
  };

  // Gọi hàm load danh sách khi controller khởi tạo
  $scope.getAllEmployees();
});
// MHÂN VIÊN ------------------------------------------------------------------------------------

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
