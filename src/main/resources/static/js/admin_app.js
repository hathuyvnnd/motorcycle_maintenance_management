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

app.controller("CustomerController", function ($scope) {
  $scope.pageTitle = "Quản lý khách hàng";
});
// ============== Service cho Account ==============
app.factory("AccountService", function ($http) {
  var baseUrl = "/api/taikhoan";
  return {
    // 1. Lấy tất cả tài khoản
    getAllAccounts: function () {
      return $http.get(baseUrl);
    },
    // 2. Lấy tài khoản theo ID
    getAccountById: function (id) {
      return $http.get(baseUrl + "/" + id);
    },
    // 3. Thêm tài khoản
    addAccount: function (account) {
      return $http.post(baseUrl + "/add_account", account);
    },
    // 4. Cập nhật tài khoản
    updateAccount: function (id, account) {
      return $http.put(baseUrl + "/update/" + id, account);
    },
    // 5. Xóa tài khoản
    deleteAccount: function (id) {
      return $http.delete(baseUrl + "/" + id);
    },
  };
});

// ============== Controller cho Account ==============
app.controller("AccountController", function ($scope, AccountService) {
  $scope.pageTitle = "Quản lý tài khoản";
  $scope.accounts = [];
  // Danh sách tài khoản theo vai trò
  $scope.accountsAdmin = [];
  $scope.accountsEmployee = [];
  $scope.accountsCustomer = [];

  // Các biến dữ liệu từng tab
  $scope.newAccountAdmin = {};
  $scope.newAccountEmployee = {};
  $scope.newAccountCustomer = {};
  // Các biến trạng thái sửa
  $scope.isEditModeAdmin = false;
  $scope.isEditModeEmployee = false;
  $scope.isEditModeCustomer = false;
  // Phân trang
  $scope.currentPage = 1;
  $scope.pageSize = 5;
  $scope.totalItems = 0;
  // Khởi tạo các biến hiển thị mật khẩu
  $scope.showPasswordAdmin = false;
  $scope.showRePasswordAdmin = false;
  $scope.showPasswordEmployee = false;
  $scope.showRePasswordEmployee = false;
  $scope.showPasswordCustomer = false;
  $scope.showRePasswordCustomer = false;
  // Load danh sách tài khoản và phân loại theo vai trò
  $scope.getAllAccounts = function () {
    AccountService.getAllAccounts().then(
      function (response) {
        $scope.accounts = response.data;
        // Phân loại theo vai trò dựa trên giá trị vaiTro
        $scope.accountsAdmin = $scope.accounts.filter(function (account) {
          return account.vaiTro === "Admin";
        });
        $scope.accountsEmployee = $scope.accounts.filter(function (account) {
          return account.vaiTro === "Nhân viên";
        });
        $scope.accountsCustomer = $scope.accounts.filter(function (account) {
          return account.vaiTro === "Khách hàng";
        });
      },
      function (error) {
        console.error("Lỗi khi lấy danh sách tài khoản:", error);
      }
    );
  };

  // Các hàm thêm/cập nhật/xóa tài khoản dựa trên vai trò
  $scope.saveAccount = function (role) {
    if (role === "admin") {
      // Gán vai trò mặc định cho tài khoản quản trị viên
      $scope.newAccountAdmin.vaiTro = "Admin";
      if (!$scope.isEditModeAdmin) {
        AccountService.addAccount($scope.newAccountAdmin).then(
          function (res) {
            $scope.getAllAccounts();
            $scope.newAccountAdmin = {};
          },
          function (err) {
            console.error("Lỗi khi thêm tài khoản quản trị viên:", err);
          }
        );
      } else {
        AccountService.updateAccount($scope.newAccountAdmin.idTaiKhoan, $scope.newAccountAdmin).then(
          function (res) {
            $scope.getAllAccounts();
            $scope.newAccountAdmin = {};
            $scope.isEditModeAdmin = false;
          },
          function (err) {
            console.error("Lỗi khi cập nhật tài khoản quản trị viên:", err);
          }
        );
      }
    } else if (role === "employee") {
      // Gán vai trò mặc định cho tài khoản nhân viên
      $scope.newAccountEmployee.vaiTro = "Nhân viên";
      if (!$scope.isEditModeEmployee) {
        AccountService.addAccount($scope.newAccountEmployee).then(
          function (res) {
            $scope.getAllAccounts();
            $scope.newAccountEmployee = {};
          },
          function (err) {
            console.error("Lỗi khi thêm tài khoản nhân viên:", err);
          }
        );
      } else {
        AccountService.updateAccount($scope.newAccountEmployee.idTaiKhoan, $scope.newAccountEmployee).then(
          function (res) {
            $scope.getAllAccounts();
            $scope.newAccountEmployee = {};
            $scope.isEditModeEmployee = false;
          },
          function (err) {
            console.error("Lỗi khi cập nhật tài khoản nhân viên:", err);
          }
        );
      }
    } else if (role === "customer") {
      // Gán vai trò mặc định cho tài khoản khách hàng
      $scope.newAccountCustomer.vaiTro = "Khách hàng";
      if (!$scope.isEditModeCustomer) {
        AccountService.addAccount($scope.newAccountCustomer).then(
          function (res) {
            $scope.getAllAccounts();
            $scope.newAccountCustomer = {};
          },
          function (err) {
            console.error("Lỗi khi thêm tài khoản khách hàng:", err);
          }
        );
      } else {
        AccountService.updateAccount($scope.newAccountCustomer.idTaiKhoan, $scope.newAccountCustomer).then(
          function (res) {
            $scope.getAllAccounts();
            $scope.newAccountCustomer = {};
            $scope.isEditModeCustomer = false;
          },
          function (err) {
            console.error("Lỗi khi cập nhật tài khoản khách hàng:", err);
          }
        );
      }
    }
  };

  // Hàm chọn tài khoản để sửa
  $scope.selectAccount = function (account, role) {
    if (role === "admin") {
      $scope.newAccountAdmin = angular.copy(account);
      $scope.isEditModeAdmin = true;
    } else if (role === "employee") {
      $scope.newAccountEmployee = angular.copy(account);
      $scope.isEditModeEmployee = true;
    } else if (role === "customer") {
      $scope.newAccountCustomer = angular.copy(account);
      $scope.isEditModeCustomer = true;
    }
  };

  // Hàm xóa tài khoản
  $scope.deleteAccount = function (idTaiKhoan, role) {
    if (confirm("Bạn có chắc chắn muốn xóa?")) {
      AccountService.deleteAccount(idTaiKhoan).then(
        function () {
          alert("Tài khoản đã được xóa thành công!");
          $scope.getAllAccounts();
        },
        function (err) {
          console.error("Lỗi khi xóa tài khoản:", err);
          alert("Lỗi khi xóa tài khoản, vui lòng thử lại.");
        }
      );
    }
  };

  // Các hàm reset form tương ứng cho từng loại
  $scope.resetAdminForm = function () {
    $scope.newAccountAdmin = {};
    $scope.isEditModeAdmin = false;
  };
  $scope.resetEmployeeForm = function () {
    $scope.newAccountEmployee = {};
    $scope.isEditModeEmployee = false;
  };
  $scope.resetCustomerForm = function () {
    $scope.newAccountCustomer = {};
    $scope.isEditModeCustomer = false;
  };

  // Khởi tạo: load danh sách tài khoản
  $scope.getAllAccounts();
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
