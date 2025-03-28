var app = angular.module("myApp", ["ngRoute"]);
app.config(function ($routeProvider) {
$routeProvider
  .when("/", {
    templateUrl: "/employee/content/hoadonstaff.html",
  })
  .when("/hoa-don", {
    templateUrl: "/employee/content/hoadonstaff.html",
  })

  .when("/tra-cuu-lich-hen", {
    templateUrl: "/employee/content/tracuulichhen.html",
    controller: "lichHenController",
  })
  .when("/cap-nhat-phieu/:id", {
    templateUrl: "/employee/content/capNhatPhieu.html",
  })
  .when("/ghi-nhan-tinh-trang/:id", {
    templateUrl: "/employee/content/ghiNhanTinhTrang.html",
  })
  .when("/nhan-vien-tao-lich-hen", {
    templateUrl: "/employee/content/staffTaoLichHen.html",
  })
  .otherwise({
    redirectTo: "/",
  });
});
app.controller("laman", function ($http, $scope) {
$scope.lichhen = []; // Dữ liệu lich hen

$scope.isSidebarHidden = false;
$scope.currentYear = new Date().getFullYear();
$scope.currentDay = new Date();
$scope.toggleSidebar = function () {
  $scope.isSidebarHidden = !$scope.isSidebarHidden;
  console.log("Sidebar hidden:", $scope.isSidebarHidden);
};

$scope.getActionLink = function (appointment) {
  switch (appointment.status) {
    case false:
      return "#!/ghi-nhan-tinh-trang/" + appointment.id;
    case true:
      return "#!/cap-nhat-phieu/" + appointment.id;
    case "Đang sửa chữa":
      return "#!/cap-nhat-phieu/" + appointment.id;
    case "Đã hoàn thành":
      return "#!/thanh-toan/" + appointment.id;
    case "Đã thanh toán":
      return "#!/hoa-don/" + appointment.id;
    default:
      return "#!/aa";
  }
};
});
app.controller("ServiceController", function ($scope) {
$scope.services = [];
$scope.parts = [];
$scope.showPartsSelection = false;

$scope.addService = function () {
  if ($scope.selectedService) {
    $scope.services.push($scope.selectedService);
    if ($scope.selectedService === "Thay thế phụ tùng") {
      $scope.showPartsSelection = true;
    }
    $scope.selectedService = "";
  }
};

$scope.removeService = function (index) {
  if ($scope.services[index] === "Thay thế phụ tùng") {
    $scope.showPartsSelection = false;
    $scope.parts = [];
  }
  $scope.services.splice(index, 1);
};

$scope.addPart = function () {
  if ($scope.selectedPart && $scope.selectedQuantity) {
    $scope.parts.push({ name: $scope.selectedPart, quantity: $scope.selectedQuantity });
    $scope.selectedPart = "";
    $scope.selectedQuantity = "";
  }
};

$scope.removePart = function (index) {
  $scope.parts.splice(index, 1);
};

$scope.saveServiceTicket = function () {
  alert("Phiếu dịch vụ đã được lưu!");
};
});
app.controller("lichHenController", function ($scope, $http) {
  $scope.appointments = [];

  // Gọi API lấy danh sách lịch hẹn
  $http.get("http://localhost:8081/api/lich-hen/today")
     .then(function (response) {
       console.log("Dữ liệu từ API:", response.data);

       // Kiểm tra nếu response có dữ liệu
       if (response.data && response.data.result) {
         $scope.appointments = response.data.result; // Chỉ lấy mảng result
       } else {
         console.error("API không trả về dữ liệu hợp lệ.");
       }
     })
     .catch(function (error) {
       console.error("Lỗi khi lấy dữ liệu:", error);
     });

  // Hàm xử lý đường dẫn dựa trên trạng thái
  $scope.getActionLink = function (appointment) {
    switch (appointment.trangThai) {
      case "Chưa đến":
        return "#!/ghi-nhan-tinh-trang/" + appointment.idLichHen;
      case "Đang kiểm tra":
      case "Đang sửa chữa":
        return "#!/cap-nhat-phieu/" + appointment.idLichHen;
      case "Đã hoàn thành":
        return "#!/thanh-toan/" + appointment.idLichHen;
      case "Đã thanh toán":
        return "#!/hoa-don/" + appointment.idLichHen;
      default:
        return "#";
    }
  };

  $scope.getActionText = function (status) {
    switch (status) {
      case false:
        return "Lập phiếu tình trạng xe";
      case true:
        return "Lập phiếu dịch vụ";
      case "true":
        return "Cập nhật phiếu dịch vụ";
      case "false":
        return "Thanh toán";
      case "Đã thanh toán":
        return "Xem hóa đơn";
      default:
        return "Không xác định";
    }
  };
});

//app.controller("TaoLichHenController", function ($scope, $http) {
//    $scope.lichHen = {};
//    $scope.daCoTaiKhoan = false;  // Để disable input tên khách hàng
//    $scope.choPhepNhap = false;   // Bật input sau khi kiểm tra
//
//    // Hàm kiểm tra số điện thoại
//    $scope.checkPhone = function () {
//        if (!$scope.lichHen.phone) {
//            alert("Vui lòng nhập số điện thoại!");
//            return;
//        }
//
//        $http.get("http://localhost:8081/api/lich-hen/check-phone?phone=" + $scope.lichHen.phone)
//            .then(function (response) {
//                if (response.data.result) {
//                    // Nếu tài khoản tồn tại, điền thông tin khách hàng
//                    $scope.lichHen.hoTen = response.data.result.tenKhachHang;
//                    $scope.daCoTaiKhoan = true;
//                } else {
//                    // Nếu chưa có tài khoản, cho phép nhập tên
//                    $scope.lichHen.hoTen = "";
//                    $scope.daCoTaiKhoan = false;
//                }
//                $scope.choPhepNhap = true;
//            })
//            .catch(function (error) {
//                console.error("Lỗi khi kiểm tra số điện thoại:", error);
//            });
//    };
//
//    // Hàm lưu lịch hẹn
//    $scope.luuLichHen = function () {
//        if (!$scope.lichHen.hoTen || !$scope.lichHen.bienSo || !$scope.lichHen.moTa) {
//            alert("Vui lòng nhập đầy đủ thông tin!");
//            return;
//        }
//
//        $http.post("/api/taoLichHen", $scope.lichHen)
//            .then(function (response) {
//                alert("Lịch hẹn đã được tạo thành công!");
//                console.log(response.data);
//            })
//            .catch(function (error) {
//                console.error("Lỗi khi tạo lịch hẹn:", error);
//            });
//    };
//});

app.controller("TaoLichHenController", function ($scope, $http) {
    $scope.phone = "";
    $scope.customerExists = false;
    $scope.phoneChecked = false;
    $scope.tenKhachHang = "";
    $scope.bienSoXe = "";
    $scope.moTa = "";
    $scope.thoiGian = "";
    $scope.idLoaiXe = "";
    $scope.danhSachLoaiXe = [
        { id: 1, ten: "Xe máy" },
        { id: 2, ten: "Ô tô" }
    ];

    // Hàm kiểm tra số điện thoại
    $scope.checkPhone = function () {
        if (!$scope.phone) {
            alert("Vui lòng nhập số điện thoại!");
            return;
        }

        $http.get("http://localhost:8081/api/lich-hen/checkPhone", { params: { phone: $scope.phone } })
            .then(function (response) {
                if (response.data.result) {
                    // Nếu số điện thoại có tài khoản, tự động điền tên & khóa ô nhập
                    $scope.customerExists = true;
                    $scope.tenKhachHang = response.data.result.tenKhachHang; // ✅ Gán đúng biến
                } else {
                    // Nếu chưa có tài khoản, cho phép nhập
                    $scope.customerExists = false;
                    $scope.tenKhachHang = "";
                }
                $scope.phoneChecked = true;
            })
            .catch(function (error) {
                console.error("Lỗi khi kiểm tra số điện thoại:", error);
            });
    };

    // Hàm tạo lịch hẹn
    $scope.taoLichHen = function () {
        if (!$scope.bienSoXe) {
            alert("Vui lòng nhập biển số xe!");
            return;
        }

        if (!$scope.tenKhachHang || $scope.tenKhachHang.trim() === "") {
            alert("Vui lòng nhập tên khách hàng!");
            return;
        }

        var requestData = {
            idKhachHang: $scope.phone,
            thoiGian: $scope.thoiGian ? new Date($scope.thoiGian).toISOString() : new Date().toISOString(),
            bienSoXe: $scope.bienSoXe,
            ghiChu: $scope.moTa,
            dichVu: "Bảo dưỡng",
            tenKhachHang: $scope.tenKhachHang
        };

        $http.post("http://localhost:8081/api/lich-hen/tao-lich-hen", requestData)
            .then(function (response) {
                alert("Lịch hẹn đã được tạo thành công!");
                location.reload();
            })
            .catch(function (error) {
                console.error("Lỗi khi tạo lịch hẹn:", error);
            });
    };
});

