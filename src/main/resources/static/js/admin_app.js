var appAdmin = angular.module("megaviaApp", ["ngRoute"]);
appAdmin.factory("AuthInterceptor", [
  "$q",
  "$window",
  function ($q, $window) {
    return {
      request: function (config) {
        const token = sessionStorage.getItem("token");
        if (token) {
          config.headers = config.headers || {};
          config.headers.Authorization = "Bearer " + token;
          console.log("Admin: token added to header");
        }
        return config;
      },
      responseError: function (rejection) {
        if (rejection.status === 401) {
          $window.location.href = "/views/dangnhap.html";
        }
        return $q.reject(rejection);
      },
    };
  },
]);

appAdmin.config([
  "$httpProvider",
  function ($httpProvider) {
    $httpProvider.interceptors.push("AuthInterceptor");
  },
]);

appAdmin.controller("MainController", function ($scope) {
  $scope.isSidebarHidden = false;
  $scope.toggleSidebar = function () {
    $scope.isSidebarHidden = !$scope.isSidebarHidden;
  };
  $scope.logout = function () {
    // Xóa token và các thông tin liên quan
    sessionStorage.clear();      // hoặc: sessionStorage.removeItem("token"); v.v...
    localStorage.clear();

    // Điều hướng về trang chủ
    window.location.href = "/";
};
});

appAdmin.config(function ($routeProvider) {
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
    .when("/type_accessory", {
      templateUrl: "admin/views/type_accessory.html",
      controller: "TypeAccessoryController",
    })
    .when("/index", {
      redirectTo: "/statistics",
    })
    .otherwise({
      redirectTo: "/index/statistics",
    });
});

// ============== Service cho NhanVien ==============
appAdmin.factory("NhanVienService", function ($http) {
  var baseUrl = "/api/admin/nhanvien";
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
appAdmin.controller("EmployeeController", function ($scope, NhanVienService, AccountService) {
  $scope.pageTitle = "Quản lý nhân viên";
  $scope.employees = [];
  $scope.newEmployee = { taiKhoanNV: {} };
  $scope.isEditMode = false;
  $scope.file = null;
  $scope.availableAccounts = []; // Mảng lưu danh sách tài khoản khả dụng
  // Phân trang
  $scope.currentPage = 1;
  $scope.pageSize = 5;
  $scope.totalItems = 0;

  // Load danh sách
  $scope.getAllEmployees = function () {
    NhanVienService.getAllNhanVien().then(
      function (response) {
        $scope.employees = response.data;
        console.log("danh sach nhan vien",$scope.employees );
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
          // Gọi lại loadAvailableAccounts() để refresh danh sách tài khoản khả dụng (chưa liên kết với nhân viên) trên client
          $scope.loadAvailableAccounts();
          $scope.newEmployee = { taiKhoanNV: {} };
          $scope.file = null;
          document.getElementById("profileImage").value = "";
          $scope.employeeForm.$setPristine();
          $scope.employeeForm.$setUntouched();
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
            $scope.employeeForm.$setPristine();
            $scope.employeeForm.$setUntouched();
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
            $scope.employeeForm.$setPristine();
            $scope.employeeForm.$setUntouched();
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
    // Nếu tài khoản này không nằm trong availableAccounts,
    // ta thêm thủ công để select box hiển thị được
    var currentId = $scope.newEmployee.taiKhoanNV.idTaiKhoan;
    if (
      currentId &&
      !$scope.availableAccounts.some(function (acc) {
        return acc.idTaiKhoan === currentId;
      })
    ) {
      $scope.availableAccounts.push({
        idTaiKhoan: currentId,
      });
    }

    $scope.isEditMode = true;
    // console.log($scope.newEmployee);
  };

  // Hàm xóa nhân viên
  $scope.deleteEmployee = function (idNhanVien) {
    if (confirm("Bạn có chắc chắn muốn xóa?")) {
      NhanVienService.deleteNhanVien(idNhanVien).then(
        function () {
          // Kiểm tra nếu phần tử bị xóa là phần tử cuối cùng trong trang
          if ($scope.getPaginatedData().length === 1 && $scope.currentPage > 1) {
            // Nếu trang còn lại có ít nhất 1 phần tử và trang hiện tại không phải trang đầu tiên
            $scope.setPage($scope.currentPage - 1); // Chuyển về trang liền trước
          }
          $scope.getAllEmployees();
          alert("Nhân viên đã được xóa thành công!");

          // Reload trang sau khi xóa thành công
          // window.location.reload();
        },
        function (err) {
          console.error("Lỗi khi xóa nhân viên:", err);
          alert("Lỗi khi xóa nhân viên, vui lòng thử lại.");
        }
      );
    }
  };
  // Hàm load tài khoản khả dụng
  $scope.loadAvailableAccounts = function () {
    AccountService.getAvailableAccounts().then(
      function (response) {
        $scope.availableAccounts = response.data;
      },
      function (error) {
        console.error("Lỗi khi lấy tài khoản khả dụng:", error);
      }
    );
  };

  // Gọi hàm này khi khởi tạo
  $scope.init = function () {
    $scope.getAllEmployees();
    $scope.loadAvailableAccounts();
  };

  // Khởi tạo
  $scope.init();
});

// ============== Service cho Customer ==============
appAdmin.factory("CustomerService", function ($http) {
  var baseUrl = "/api/admin/khachhang";
  return {
    // 1. Lấy tất cả khách hàng
    getAllCustomers: function () {
      return $http.get(baseUrl);
    },
    // 2. Lấy khách hàng theo ID
    getCustomerById: function (id) {
      return $http.get(baseUrl + "/" + id);
    },
  };
});
// ============== Controller cho Customer ==============
appAdmin.controller("CustomerController", function ($scope, CustomerService) {
  $scope.customers = [];
  $scope.newCustomer = { taiKhoanKH: {} };
  $scope.isEditMode = false;
  $scope.file = null;

  // Phân trang
  $scope.currentPage = 1;
  $scope.pageSize = 5;
  $scope.totalItems = 0;

  // Load danh sách
  $scope.getAllCustomers = function () {
    CustomerService.getAllCustomers().then(
      function (response) {
        $scope.customers = response.data;
        $scope.totalItems = $scope.customers.length;
      },
      function (error) {
        console.error("Lỗi khi lấy danh sách khách hàng:", error);
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
    return $scope.customers.slice(start, start + $scope.pageSize);
  };
  $scope.getAllCustomers();
});
// ============== Service cho Account ==============
appAdmin.factory("AccountService", function ($http) {
  var baseUrl = "/api/admin/taikhoan";
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
    //6. Lấy danh sách tài khoản có sẵn chưa liên kết với nhân viên
    getAvailableAccounts: function () {
      return $http.get(baseUrl + "/available");
    },
  };
});

// ============== Controller cho Account ==============
appAdmin.controller("AccountController", function ($scope, AccountService) {
  $scope.pageTitle = "Quản lý tài khoản";
  $scope.accounts = [];
  // Danh sách tài khoản theo vai trò
  $scope.accountsAdmin = [];
  $scope.accountsEmployee = [];
  $scope.accountsCustomer = [];

  // Các biến phân trang cho từng tab
  $scope.currentPageEmployee = 1;
  $scope.pageSizeEmployee = 5;
  $scope.totalItemsEmployee = 0;

  $scope.currentPageCustomer = 1;
  $scope.pageSizeCustomer = 5;
  $scope.totalItemsCustomer = 0;
  // Các biến dữ liệu từng tab
  $scope.newAccountAdmin = {};
  $scope.newAccountEmployee = { trangThai: true };
  $scope.newAccountCustomer = { trangThai: true };
  // Các biến trạng thái sửa
  $scope.isEditModeAdmin = false;
  $scope.isEditModeEmployee = false;
  $scope.isEditModeCustomer = false;

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
        // Cập nhật tổng số phần tử
        $scope.totalItemsEmployee = $scope.accountsEmployee.length;
        $scope.totalItemsCustomer = $scope.accountsCustomer.length;
      },
      function (error) {
        console.error("Lỗi khi lấy danh sách tài khoản:", error);
      }
    );
  };
  // Hàm set page cho tab nhân viên
  $scope.setPageEmployee = function (page) {
    $scope.currentPageEmployee = page;
  };

  // Hàm set page cho tab khách hàng
  $scope.setPageCustomer = function (page) {
    $scope.currentPageCustomer = page;
  };

  // Hàm tính toán số trang
  $scope.getPageCountEmployee = function () {
    return Math.ceil($scope.totalItemsEmployee / $scope.pageSizeEmployee);
  };

  $scope.getPageCountCustomer = function () {
    return Math.ceil($scope.totalItemsCustomer / $scope.pageSizeCustomer);
  };

  // Hàm lấy các tài khoản phân trang
  $scope.getPaginatedEmployeeAccounts = function () {
    let start = ($scope.currentPageEmployee - 1) * $scope.pageSizeEmployee;
    let end = start + $scope.pageSizeEmployee;
    return $scope.accountsEmployee.slice(start, end);
  };

  $scope.getPaginatedCustomerAccounts = function () {
    let start = ($scope.currentPageCustomer - 1) * $scope.pageSizeCustomer;
    let end = start + $scope.pageSizeCustomer;
    return $scope.accountsCustomer.slice(start, end);
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
            $scope.employeeForm.$setPristine();
            $scope.employeeForm.$setUntouched();
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
            $scope.employeeForm.$setPristine();
            $scope.employeeForm.$setUntouched();
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

// ============== Service cho Dịch vụ ==============
appAdmin.factory("DichVuService", function ($http) {
  var baseUrl = "/api/admin/dichvu";
  return {
    // 1. Lấy tất cả dịch vụ
    getAllDichVu: function () {
      return $http.get(baseUrl);
    },
    // 2. Lấy dịch vụ theo ID
    getDichVuById: function (id) {
      return $http.get(baseUrl + "/" + id);
    },
    // 3. Thêm dịch vụ (kèm file)
    addDichVu: function (dichVu, file) {
      var formData = new FormData();
      formData.append("dichVu", new Blob([JSON.stringify(dichVu)], { type: "application/json" }));
      formData.append("file", file);
      return $http.post(baseUrl + "/upload", formData, {
        headers: { "Content-Type": undefined },
      });
    },
    // 4a. Cập nhật dịch vụ không có file
    updateDichVu: function (id, dichVu) {
      return $http.put(baseUrl + "/" + id, dichVu);
    },
    // 4b. Cập nhật dịch vụ có file
    updateDichVuWithFile: function (id, dichVu, file) {
      var formData = new FormData();
      formData.append("dichVu", new Blob([JSON.stringify(dichVu)], { type: "application/json" }));
      formData.append("file", file);
      return $http.put(baseUrl + "/updateWithFile/" + id, formData, {
        headers: { "Content-Type": undefined },
      });
    },
    // 5. Xóa dịch vụ
    deleteDichVu: function (id) {
      return $http.delete(baseUrl + "/" + id);
    },
  };
});

// ============== Controller cho Dịch vụ ==============
appAdmin.controller("ServiceController", function ($scope, DichVuService) {
  $scope.pageTitle = "Quản lý dịch vụ";
  $scope.services = [];
  $scope.newService = { trangThai: true };
  $scope.isEditMode = false;
  $scope.file = null;

  // Phân trang
  $scope.currentPage = 1;
  $scope.pageSize = 5;
  $scope.totalItems = 0;

  // Hàm load danh sách dịch vụ
  $scope.getAllServices = function () {
    DichVuService.getAllDichVu().then(
      function (response) {
        $scope.services = response.data;
        $scope.totalItems = $scope.services.length;
      },
      function (error) {
        console.error("Lỗi khi lấy danh sách dịch vụ:", error);
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
    return $scope.services.slice(start, start + $scope.pageSize);
  };

  // Lưu file được chọn vào $scope.file
  $scope.setFile = function (files) {
    if (files && files.length > 0) {
      $scope.file = files[0];
      $scope.$apply();
    }
  };

  // Hàm lưu dịch vụ (thêm mới hoặc cập nhật)
  $scope.saveService = function () {
    if (!$scope.isEditMode) {
      // Thêm mới
      if (!$scope.file) {
        alert("Vui lòng chọn file ảnh!");
        return;
      }
      DichVuService.addDichVu($scope.newService, $scope.file).then(
        function (res) {
          $scope.getAllServices();
          $scope.newService = {};
          $scope.file = null;
          document.getElementById("profileImage").value = "";
          $scope.serviceForm.$setPristine();
          $scope.serviceForm.$setUntouched();
        },
        function (err) {
          console.error("Lỗi khi thêm dịch vụ:", err);
        }
      );
    } else {
      // Cập nhật
      if ($scope.file) {
        // Cập nhật có file
        DichVuService.updateDichVuWithFile($scope.newService.idDichVu, $scope.newService, $scope.file).then(
          function (res) {
            $scope.getAllServices();
            $scope.newService = {};
            $scope.isEditMode = false;
            $scope.file = null;
            document.getElementById("profileImage").value = "";
            $scope.serviceForm.$setPristine();
            $scope.serviceForm.$setUntouched();
          },
          function (err) {
            console.error("Lỗi khi cập nhật dịch vụ:", err);
          }
        );
      } else {
        // Cập nhật không file
        DichVuService.updateDichVu($scope.newService.idDichVu, $scope.newService).then(
          function (res) {
            $scope.getAllServices();
            $scope.newService = {};
            $scope.isEditMode = false;
            $scope.serviceForm.$setPristine();
            $scope.serviceForm.$setUntouched();
          },
          function (err) {
            console.error("Lỗi khi cập nhật dịch vụ:", err);
          }
        );
      }
    }
  };

  // Hàm chọn dịch vụ để sửa
  $scope.selectService = function (service) {
    $scope.newService = angular.copy(service);
    $scope.isEditMode = true;
    console.log("Selected service:", $scope.newService);
  };

  // Hàm xóa dịch vụ
  $scope.deleteDichVu = function (idDichVu) {
    if (confirm("Bạn có chắc chắn muốn xóa?")) {
      DichVuService.deleteDichVu(idDichVu).then(
        function () {
          // Kiểm tra nếu phần tử bị xóa là phần tử cuối cùng trong trang
          if ($scope.getPaginatedData().length === 1 && $scope.currentPage > 1) {
            // Nếu trang còn lại có ít nhất 1 phần tử và trang hiện tại không phải trang đầu tiên
            $scope.setPage($scope.currentPage - 1); // Chuyển về trang liền trước
          }
          $scope.getAllServices();
          alert("Dịch vụ đã được xóa thành công!");
        },
        function (err) {
          console.error("Lỗi khi xóa dịch vụ:", err);
          alert("Lỗi khi xóa dịch vụ, vui lòng thử lại.");
        }
      );
    }
  };

  // Khởi tạo: load danh sách dịch vụ
  $scope.getAllServices();
});
// ============== Service cho Loại phụ tùng ==============
appAdmin.factory("LoaiPhuTungService", function ($http) {
  var baseUrl = "/api/admin/loaiphutung";
  return {
    getAllLoaiPT: function () {
      return $http.get(baseUrl);
    },
    // 2. Lấy loại phụ tùng theo ID
    getLoaiPhuTungById: function (id) {
      return $http.get(baseUrl + "/" + id);
    },
    // 3. Thêm loại phụ tùng
    addLoaiPhuTung: function (loaiPhuTung) {
      return $http.post(baseUrl + "/add", loaiPhuTung);
    },
    // 4. Cập nhật loại phụ tùng
    updateLoaiPhuTung: function (id, loaiPhuTung) {
      return $http.put(baseUrl + "/" + id, loaiPhuTung);
    },

    // 5. Xóa loại phụ tùng
    deleteLoaiPhuTung: function (id) {
      return $http.delete(baseUrl + "/" + id);
    },
  };
});
// ============== Controller cho Loại Phụ tùng ==============
appAdmin.controller("TypeAccessoryController", function ($scope, LoaiPhuTungService) {
  $scope.pageTitle = "Quản lý loại phụ tùng";
  $scope.type_accessorys = [];
  $scope.newTypeAccessory = {};
  $scope.isEditMode = false;
  // Phân trang
  $scope.currentPage = 1;
  $scope.pageSize = 5;
  $scope.totalItems = 0;

  // Hàm load danh sách loại phụ tùng
  $scope.getAllTypeAccessorys = function () {
    LoaiPhuTungService.getAllLoaiPT().then(
      function (response) {
        $scope.type_accessorys = response.data;
        $scope.totalItems = $scope.type_accessorys.length;
      },
      function (error) {
        console.error("Lỗi khi lấy danh sách loại phụ tùng:", error);
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
    return $scope.type_accessorys.slice(start, start + $scope.pageSize);
  };

  // Hàm thêm mới hoặc cập nhật
  $scope.saveTypeAccessory = function () {
    if (!$scope.isEditMode) {
      // console.log($scope.newTypeAccessory);
      LoaiPhuTungService.addLoaiPhuTung($scope.newTypeAccessory).then(
        function (res) {
          $scope.getAllTypeAccessorys();
          $scope.newTypeAccessory = {};
          $scope.loaiPhuTungForm.$setPristine();
          $scope.loaiPhuTungForm.$setUntouched();
        },
        function (err) {
          console.error("Lỗi khi thêm loại phụ tùng:", err);
        }
      );
    } else {
      // Cập nhật
      LoaiPhuTungService.updateLoaiPhuTung($scope.newTypeAccessory.idLoaiPT, $scope.newTypeAccessory).then(
        function (res) {
          $scope.getAllTypeAccessorys();
          $scope.newTypeAccessory = {};
          $scope.isEditMode = false;
          $scope.loaiPhuTungForm.$setPristine();
          $scope.loaiPhuTungForm.$setUntouched();
        },
        function (err) {
          console.error("Lỗi khi cập nhật loại phụ tùng:", err);
        }
      );
    }
  };

  // Hàm chọn loại phụ tùng để sửa
  $scope.selectTypeAccessory = function (typeAccessory) {
    $scope.newTypeAccessory = angular.copy(typeAccessory);
    $scope.isEditMode = true;
    console.log("Selected service:", $scope.newTypeAccessory);
  };

  // Hàm xóa phụ tùng
  $scope.deleteLoaiPhuTung = function (idLoaiPT) {
    if (confirm("Bạn có chắc chắn muốn xóa?")) {
      LoaiPhuTungService.deleteLoaiPhuTung(idLoaiPT).then(
        function () {
          // Kiểm tra nếu phần tử bị xóa là phần tử cuối cùng trong trang
          if ($scope.getPaginatedData().length === 1 && $scope.currentPage > 1) {
            // Nếu trang còn lại có ít nhất 1 phần tử và trang hiện tại không phải trang đầu tiên
            $scope.setPage($scope.currentPage - 1); // Chuyển về trang liền trước
          }

          // Sau khi xóa, tải lại danh sách loại phụ tùng
          $scope.getAllTypeAccessorys();
          alert("Loại phụ tùng đã được xóa thành công!");
        },
        function (err) {
          console.error("Lỗi khi xóa loại phụ tùng:", err);
          alert("Lỗi khi xóa loại phụ tùng, vui lòng thử lại.");
        }
      );
    }
  };

  $scope.getAllTypeAccessorys();
});
// ============== Service cho Phụ tùng ==============
appAdmin.factory("PhuTungService", function ($http) {
  var baseUrl = "/api/admin/phutung";
  return {
    // 1. Lấy tất cả phụ tùng
    getAllPhuTung: function () {
      return $http.get(baseUrl);
    },
    // 2. Lấy phụ tùng theo ID
    getPhuTungById: function (id) {
      return $http.get(baseUrl + "/" + id);
    },
    // 3. Thêm phụ tùng (kèm file)
    addPhuTung: function (phuTung, file) {
      var formData = new FormData();
      formData.append("phuTung", new Blob([JSON.stringify(phuTung)], { type: "application/json" }));
      formData.append("file", file);
      return $http.post(baseUrl + "/upload", formData, {
        headers: { "Content-Type": undefined },
      });
    },
    // 4a. Cập nhật phụ tùng không có file
    updatePhuTung: function (id, phuTung) {
      return $http.put(baseUrl + "/" + id, phuTung);
    },
    // 4b. Cập nhật phụ tùng có file
    updatePhuTungWithFile: function (id, phuTung, file) {
      var formData = new FormData();
      formData.append("phuTung", new Blob([JSON.stringify(phuTung)], { type: "application/json" }));
      formData.append("file", file);
      return $http.put(baseUrl + "/updateWithFile/" + id, formData, {
        headers: { "Content-Type": undefined },
      });
    },
    // 5. Xóa phụ tùng
    deletePhuTung: function (id) {
      return $http.delete(baseUrl + "/" + id);
    },
  };
});

// ============== Controller cho Phụ tùng ==============
appAdmin.controller("AccessoryController", function ($scope, PhuTungService, DichVuService, LoaiPhuTungService) {
  $scope.pageTitle = "Quản lý phụ tùng";
  $scope.accessorys = [];
  $scope.newAccessory = { tinhTrang: true };
  $scope.isEditMode = false;
  $scope.file = null;
  $scope.listLoaiPhuTung = [];
  $scope.listDichVu = [];
  // Phân trang
  $scope.currentPage = 1;
  $scope.pageSize = 5;
  $scope.totalItems = 0;

  // Hàm load danh sách phụ tùng
  $scope.getAllAccessorys = function () {
    PhuTungService.getAllPhuTung().then(
      function (response) {
        $scope.accessorys = response.data;
        $scope.totalItems = $scope.accessorys.length;
      },
      function (error) {
        console.error("Lỗi khi lấy danh sách phụ tùng:", error);
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
    return $scope.accessorys.slice(start, start + $scope.pageSize);
  };

  // Lưu file được chọn vào $scope.file
  $scope.setFile = function (files) {
    if (files && files.length > 0) {
      $scope.file = files[0];
      $scope.$apply();
    }
  };

  // Hàm thêm mới hoặc cập nhật
  $scope.saveAccessory = function () {
    if (!$scope.isEditMode) {
      // Thêm mới
      if (!$scope.file) {
        alert("Vui lòng chọn file ảnh!");
        return;
      }
      PhuTungService.addPhuTung($scope.newAccessory, $scope.file).then(
        function (res) {
          $scope.getAllAccessorys();
          $scope.newAccessory = {};
          $scope.file = null; //Xóa dữ liệu trong form thêm phụ tùng, chuẩn bị cho lần nhập tiếp theo.
          document.getElementById("profileImage").value = ""; //Reset lại biến chứa file hình ảnh hoặc tài liệu đính kèm
          $scope.accessoryForm.$setPristine();
          $scope.accessoryForm.$setUntouched();
        },
        function (err) {
          console.error("Lỗi khi thêm phụ tùng:", err);
        }
      );
    } else {
      // Cập nhật
      if ($scope.file) {
        // Cập nhật có file
        PhuTungService.updatePhuTungWithFile($scope.newAccessory.idPhuTung, $scope.newAccessory, $scope.file).then(
          function (res) {
            $scope.getAllAccessorys();
            $scope.newAccessory = {};
            $scope.isEditMode = false;
            $scope.file = null;
            document.getElementById("profileImage").value = "";
            $scope.accessoryForm.$setPristine();
            $scope.accessoryForm.$setUntouched();
          },
          function (err) {
            console.error("Lỗi khi cập nhật phụ tùng:", err);
          }
        );
      } else {
        // Cập nhật không file
        PhuTungService.updatePhuTung($scope.newAccessory.idPhuTung, $scope.newAccessory).then(
          function (res) {
            $scope.getAllAccessorys();
            $scope.newAccessory = {};
            $scope.isEditMode = false;
            $scope.accessoryForm.$setPristine();
            $scope.accessoryForm.$setUntouched();
          },
          function (err) {
            console.error("Lỗi khi cập nhật phụ tùng:", err);
          }
        );
      }
    }
  };

  // Hàm chọn phụ tùng để sửa
  $scope.selectAccessory = function (accessory) {
    console.log("listLoaiPhuTung", $scope.listLoaiPhuTung);
    console.log("accessory.loaiPT", accessory.loaiPT);

    if (typeof accessory.loaiPT === "string") {
      // Tìm trong listLoaiPhuTung phần tử có idLoaiPT nào
      var foundLoaiPT = $scope.listLoaiPhuTung.find((item) => item.idLoaiPT === accessory.loaiPT);
      if (foundLoaiPT) {
        accessory.loaiPT = foundLoaiPT; // Gán lại object
      }
    }
    if (typeof accessory.dichVuPT === "string") {
      // Tìm trong listLoaiPhuTung phần tử có idDichVu nào
      var foundLoaiDV = $scope.listDichVu.find((item) => item.idDichVu === accessory.dichVuPT);
      if (foundLoaiDV) {
        accessory.dichVuPT = foundLoaiDV; // Gán lại object
      }
    }
    // Xử lý ngày
    if (accessory.ngayNhapKho) {
      accessory.ngayNhapKho = new Date(accessory.ngayNhapKho);
    }
    if (accessory.hanSuDung) {
      accessory.hanSuDung = new Date(accessory.hanSuDung);
    }
    $scope.newAccessory = angular.copy(accessory);
    $scope.isEditMode = true;
  };
  // xem pt.loaiPT là object hay chuỗi, rồi trả về ID
  $scope.getLoaiPTDisplay = function (loaiPT) {
    // Nếu là object => lấy loaiPT.idLoaiPT
    if (loaiPT && typeof loaiPT === "object" && loaiPT.idLoaiPT) {
      return loaiPT.idLoaiPT;
    }
    // Nếu là chuỗi => trả về chính chuỗi đó
    return loaiPT || "";
  };
  //xem pt.dichVuPT là object hay chuỗi, rồi trả về ID
  $scope.getLoaiDVDisplay = function (dichVuPT) {
    // Nếu là object => lấy loaiPT.idLoaiPT
    if (dichVuPT && typeof dichVuPT === "object" && dichVuPT.idDichVu) {
      return dichVuPT.idDichVu;
    }
    // Nếu là chuỗi => trả về chính chuỗi đó
    return dichVuPT || "";
  };
  // Hàm xóa phụ tùng
  $scope.deletePhuTung = function (idPhuTung) {
    if (confirm("Bạn có chắc chắn muốn xóa?")) {
      PhuTungService.deletePhuTung(idPhuTung).then(
        function () {
          // Kiểm tra nếu phần tử bị xóa là phần tử cuối cùng trong trang
          if ($scope.getPaginatedData().length === 1 && $scope.currentPage > 1) {
            // Nếu trang còn lại có ít nhất 1 phần tử và trang hiện tại không phải trang đầu tiên
            $scope.setPage($scope.currentPage - 1); // Chuyển về trang liền trước
          }
          $scope.getAllAccessorys();
          alert("Phụ tùng đã được xóa thành công!");
        },
        function (err) {
          console.error("Lỗi khi xóa phụ tùng:", err);
          alert("Lỗi khi xóa phụ tùng, vui lòng thử lại.");
        }
      );
    }
  };
  // ========== Load danh sách loại phụ tùng cho dropdown ==========
  $scope.loadListLoaiPT = function () {
    LoaiPhuTungService.getAllLoaiPT().then(
      function (response) {
        $scope.listLoaiPhuTung = response.data;
      },
      function (error) {
        console.error("Lỗi khi lấy loại phụ tùng:", error);
        $window.alert("Lỗi khi tải danh sách loại phụ tùng.");
      }
    );
  };

  // ========== Load danh sách dịch vụ cho dropdown ==========
  $scope.loadListDV = function () {
    DichVuService.getAllDichVu().then(
      function (response) {
        $scope.listDichVu = response.data;
      },
      function (error) {
        console.error("Lỗi khi lấy dịch vụ:", error);
      }
    );
  };

  // Hàm khởi tạo
  $scope.init = function () {
    $scope.getAllAccessorys();
    $scope.loadListLoaiPT();
    $scope.loadListDV();
  };

  // Gọi hàm khởi tạo khi controller được load
  $scope.init();
});

// ============== Service cho Booking ==============
appAdmin.factory("LichHenService", function ($http) {
  var baseUrl = "/api/admin/lich_hen";
  return {
    // Lấy tất cả lịch hẹn
    getAllLichHen: function () {
      return $http.get(baseUrl);
    },
  };
});
// ============== Controller cho Booking ==============
appAdmin.controller("BookingController", function ($scope, LichHenService) {
  $scope.bookings = [];
  $scope.newBooking = {};
  // $scope.isEditMode = false;
  // $scope.file = null;

  // Phân trang
  $scope.currentPage = 1;
  $scope.pageSize = 5;
  $scope.totalItems = 0;

  // Load danh sách
  $scope.getAllLichHen = function () {
    LichHenService.getAllLichHen().then(
      function (response) {
        $scope.bookings = response.data;
        $scope.totalItems = $scope.bookings.length;
      },
      function (error) {
        console.error("Lỗi khi lấy danh sách lich hẹn:", error);
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
    return $scope.bookings.slice(start, start + $scope.pageSize);
  };
  $scope.getAllLichHen();
});
// ============== Service cho hoá đơn ==============
appAdmin.factory("HoaDonService", function ($http) {
  var baseUrl = "/api/admin/hoa_don";
  return {
    // Lấy tất cả hoá đơn
    getAllHoaDon: function () {
      return $http.get(baseUrl);
    },
  };
});
// ============== Controller cho hoá đơn ==============
appAdmin.controller("InvoiceController", function ($scope, HoaDonService) {
  $scope.invoices = [];
  $scope.newInvoice = {};
  // $scope.isEditMode = false;
  // $scope.file = null;

  // Phân trang
  $scope.currentPage = 1;
  $scope.pageSize = 5;
  $scope.totalItems = 0;

  // Load danh sách
  $scope.getAllHoaDon = function () {
    HoaDonService.getAllHoaDon().then(
      function (response) {
        $scope.invoices = response.data;
        $scope.totalItems = $scope.invoices.length;
      },
      function (error) {
        console.error("Lỗi khi lấy danh sách hoá đơn:", error);
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
    return $scope.invoices.slice(start, start + $scope.pageSize);
  };
  $scope.getAllHoaDon();
});

// app.controller("StatisticsController", function ($scope) {
//   $scope.pageTitle = "Thống kê";
// });
appAdmin.controller("StatisticsController", [
  "$scope",
  "$http",
  function ($scope, $http) {
    $scope.revenueStats = {
      startDate: new Date(), // Lấy ngày hiện tại động
      endDate: new Date(), /// Lấy ngày hiện tại động
      results: [],
      currentPage: 1,
      totalPages: 0,
      pages: [],
    };
    $scope.isSubmitted = false; // Biến để kiểm soát trạng thái gửi form
    $scope.isDateValid = true; // Biến kiểm tra ngày hợp lệ

    $scope.getRevenueStats = function (page) {
      if (!page) page = 1;
      var startDate = $scope.revenueStats.startDate ? $scope.revenueStats.startDate.toISOString().split("T")[0] : "";
      var endDate = $scope.revenueStats.endDate ? $scope.revenueStats.endDate.toISOString().split("T")[0] : "";

      // Kiểm tra nếu startDate lớn hơn endDate
      if (startDate && endDate && startDate > endDate) {
        $scope.isDateValid = false;
        return; // Không thực hiện gọi API nếu ngày không hợp lệ
      }
      $scope.isDateValid = true;
      $scope.isSubmitted = true;

      $http
        .get("/api/revenue-stats", {
          params: {
            startDate: startDate,
            endDate: endDate,
            page: page,
            size: 10,
          },
        })
        .then(
          function (response) {
            $scope.revenueStats.results = response.data.content;
            $scope.revenueStats.currentPage = response.data.number + 1;
            $scope.revenueStats.totalPages = response.data.totalPages;
            $scope.revenueStats.pages = [];
            for (var i = 1; i <= response.data.totalPages; i++) {
              $scope.revenueStats.pages.push(i);
            }
          },
          function (error) {
            console.error("Lỗi khi lấy số liệu doanh thu:", error);
          }
        );
      // Kiểm tra ngày khi thay đổi input
      $scope.$watchGroup(["revenueStats.startDate", "revenueStats.endDate"], function (newValues) {
        var startDate = newValues[0] ? new Date(newValues[0]) : null;
        var endDate = newValues[1] ? new Date(newValues[1]) : null;
        $scope.isDateValid = !(startDate && endDate && startDate > endDate);
      });
    };
  },
]);

appAdmin.filter("sumByKey", function () {
  return function (data, key) {
    if (!angular.isArray(data) || !key) {
      return 0;
    }
    return data.reduce(function (sum, item) {
      return sum + (parseFloat(item[key]) || 0);
    }, 0);
  }});


