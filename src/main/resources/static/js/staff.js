var appEmployee = angular.module("myApp", ["ngRoute"]);
appEmployee.config(function ($routeProvider) {
$routeProvider
  // .when("/", {
  //   templateUrl: "/employee/content/hoadonstaff.html",
  // })
  .when("/hoa-don", {
    templateUrl: "/employee/content/hoadonstaff.html",
  })
  .when("/tra-cuu-lich-hen", {
    templateUrl: "/employee/content/tracuulichhen.html",
    controller: "lichHenController",
  })
  .when("/nhan-vien-tao-lich-hen", {
    templateUrl: "/employee/content/staffTaoLichHen.html",
  })
  // .otherwise({
  //   redirectTo: "/",
  // });
});
appEmployee.controller("laman", function ($http, $scope) {
$scope.lichhen = []; // Dữ liệu lich hen

$scope.isSidebarHidden = false;
$scope.currentYear = new Date().getFullYear();
$scope.currentDay = new Date();
$scope.toggleSidebar = function () {
  $scope.isSidebarHidden = !$scope.isSidebarHidden;
  console.log("Sidebar hidden:", $scope.isSidebarHidden);
};
});

//app.controller("ServiceController", function ($scope) {
//$scope.services = [];
//$scope.parts = [];
//$scope.showPartsSelection = false;
//
//$scope.addService = function () {
//  if ($scope.selectedService) {
//    $scope.services.push($scope.selectedService);
//    if ($scope.selectedService === "Thay thế phụ tùng") {
//      $scope.showPartsSelection = true;
//    }
//    $scope.selectedService = "";
//  }
//};
//
//$scope.removeService = function (index) {
//  if ($scope.services[index] === "Thay thế phụ tùng") {
//    $scope.showPartsSelection = false;
//    $scope.parts = [];
//  }
//  $scope.services.splice(index, 1);
//};
//
//$scope.addPart = function () {
//  if ($scope.selectedPart && $scope.selectedQuantity) {
//    $scope.parts.push({ name: $scope.selectedPart, quantity: $scope.selectedQuantity });
//    $scope.selectedPart = "";
//    $scope.selectedQuantity = "";
//  }
//};
//
//$scope.removePart = function (index) {
//  $scope.parts.splice(index, 1);
//};
//
//$scope.saveServiceTicket = function () {
//  alert("Phiếu dịch vụ đã được lưu!");
//};
//});


//app.controller("lichHenController", function ($scope, $http, $rootScope) {
//    $scope.appointments = [];
//    $scope.selectedAppointment = null;
//    $scope.panelTemplate = ""; // Load giao diện modal phù hợp
//
//    // Gọi API lấy danh sách lịch hẹn
//    $http.get("http://localhost:8081/api/lich-hen/today")
//        .then(function (response) {
//            if (response.data && response.data.result) {
//                $scope.appointments = response.data.result;
//            } else {
//                console.error("API không trả về dữ liệu hợp lệ.");
//            }
//        })
//        .catch(function (error) {
//            console.error("Lỗi khi lấy dữ liệu:", error);
//        });
//    $scope.getActionText = function (status) {
//    switch (status) {
//        case "Đã xác nhận":
//            return "Lập phiếu tình trạng xe";
//        case "Đang kiểm tra":
//            return "Lập phiếu dịch vụ";
//        case "true":
//            return "Cập nhật phiếu dịch vụ";
//        case "false":
//            return "Thanh toán";
//        case "Đã thanh toán":
//            return "Xem hóa đơn";
//        default:
//            return "Không xác định";
//    }
//};
//    // Mở modal phù hợp với trạng thái
//         $scope.services = [];
//        $scope.parts = [];
//        $scope.showPartsSelection = false;
//    $scope.openModal = function (appointment) {
//        console.log("selectedAppointment: ",  $scope.selectedAppointment);
//        $scope.selectedAppointment = appointment;
//
//        // Load template phù hợp với trạng thái
//        switch (appointment.trangThai) {
//             case "Đã xác nhận":
//                $scope.panelTemplate = "/employee/content/panel/ghiNhanTinhTrangPanel.html";
//                break;
//            case "Đang kiểm tra":
//                $scope.panelTemplate = "/employee/content/panel/lapPhieuDichVuModal.html";
//                break;
//            case "Đang sửa chữa":
//            case "Đã hoàn thành":
//            case "Đã thanh toán":
//                $scope.panelTemplate = "/employee/content/panel/ghiNhanTinhTrangPanel.html";
//                break;
//            default:
//                $scope.panelTemplate = "";
//                break;
//        }
//
//        // Mở modal Bootstrap
//        var myModal = new bootstrap.Modal(document.getElementById("lichHenModal"));
//        myModal.show();
//  };
//    $scope.addService = function () {
//     console.log("selectedAppointment: ",  $scope.selectedService);
//      if ($scope.selectedService) {
//        $scope.services.push($scope.selectedService);
//        if ($scope.selectedService === "Thay thế phụ tùng") {
//          $scope.showPartsSelection = true;
//        }
//        $scope.selectedService = "";
//      }
//    };
//
//    $scope.removeService = function (index) {
//      if ($scope.services[index] === "Thay thế phụ tùng") {
//        $scope.showPartsSelection = false;
//        $scope.parts = [];
//      }
//      $scope.services.splice(index, 1);
//    };
//
//    $scope.addPart = function () {
//      if ($scope.selectedPart && $scope.selectedQuantity) {
//        $scope.parts.push({ name: $scope.selectedPart, quantity: $scope.selectedQuantity });
//        $scope.selectedPart = "";
//        $scope.selectedQuantity = "";
//      }
//    };
//
//    $scope.removePart = function (index) {
//      $scope.parts.splice(index, 1);
//    };
//    $scope.taoPhieuTinhTrang = function (appointment) {
//  console.log("Selected Appointment:", $scope.selectedAppointment); // Kiểm tra dữ liệu
//
//    if (!$scope.selectedAppointment || !$scope.selectedAppointment.bienSoXe) {
//        alert("Lỗi: Không có thông tin xe để tạo phiếu!");
//        return;
//    }
//
//    const data = {
//        bienSoXe: $scope.selectedAppointment.bienSoXe,
//        moTaTinhTrangXe: $scope.selectedAppointment.moTaTinhTrangXe || ""
//    };
//
//    $http.post("http://localhost:8081/api/staff/phieu-tinh-trang", data)
//        .then(function (response) {
//            if (response.data && response.data.result) {
//                alert("Tạo phiếu ghi nhận thành công!");
//                console.log(response.data.result);
//                 $scope.loadAppointments();
//            } else {
//                alert("Không thể tạo phiếu ghi nhận.");
//            }
//        })
//        .catch(function (error) {
//            console.error("Lỗi khi tạo phiếu:", error);
//            alert("Lỗi khi tạo phiếu.");
//        });
//  var myModalEl = document.getElementById("lichHenModal");
//                var modal = bootstrap.Modal.getInstance(myModalEl);
//                if (modal) {
//                    modal.hide();
//                }
//};
//    $scope.loadAppointments = function () {
//        $http.get("http://localhost:8081/api/lich-hen/today")
//            .then(function (response) {
//                if (response.data && response.data.result) {
//                    $scope.appointments = response.data.result;
//                }
//            })
//            .catch(function (error) {
//                console.error("Lỗi khi lấy dữ liệu:", error);
//            });
//    };
//
//
//
//
//
//
//
//
//
//    $scope.saveServiceTicket = function () {
//      alert("Phiếu dịch vụ đã được lưu!");
//    };
//});

appEmployee.controller("lichHenController", function ($scope, $http, $rootScope) {
    $scope.appointments = [];
    $scope.selectedAppointment = null;
    $scope.panelTemplate = ""; // Load giao diện modal phù hợp

    // Gọi API lấy danh sách lịch hẹn
    $scope.loadAppointments = function () {
        $http.get("http://localhost:8081/api/lich-hen/today")
            .then(function (response) {
                if (response.data && response.data.result) {
                    $scope.appointments = response.data.result;
                } else {
                    console.error("API không trả về dữ liệu hợp lệ.");
                }
            })
            .catch(function (error) {
                console.error("Lỗi khi lấy dữ liệu:", error);
            });
    };

    $scope.getActionText = function (status) {
        switch (status) {
            case "Đã xác nhận":
                return "Lập phiếu tình trạng xe";
            case "Đang kiểm tra":
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

    // Mở modal phù hợp với trạng thái
    $scope.openModal = function (appointment) {
        $scope.selectedAppointment = appointment;
        $scope.services = [];
        $scope.parts = [];
        $scope.showPartsSelection = false;
        $rootScope.selectedService = ""; // Reset select service

        switch (appointment.trangThai) {
            case "Đã xác nhận":
                $scope.panelTemplate = "/employee/content/panel/ghiNhanTinhTrangPanel.html";
                break;
            case "Đang kiểm tra":
                $scope.panelTemplate = "/employee/content/panel/lapPhieuDichVuModal.html";
                break;
            default:
                $scope.panelTemplate = "";
                break;
        }

        var myModal = new bootstrap.Modal(document.getElementById("lichHenModal"));
        myModal.show();
    };

    // Thêm dịch vụ
    $scope.addService = function () {
        if ($rootScope.selectedService) {
            $scope.services.push($rootScope.selectedService);
            if ($rootScope.selectedService === "Thay thế phụ tùng") {
                $scope.showPartsSelection = true;
            }
            $rootScope.selectedService = "";
        }
    };

    $scope.removeService = function (index) {
        if ($scope.services[index] === "Thay thế phụ tùng") {
            $scope.showPartsSelection = false;
            $scope.parts = [];
        }
        $scope.services.splice(index, 1);
    };

    // Thêm phụ tùng
    $scope.addPart = function () {
        if ($rootScope.selectedPart && $rootScope.selectedQuantity) {
            $scope.parts.push({ name: $rootScope.selectedPart, quantity: $rootScope.selectedQuantity });
            $rootScope.selectedPart = "";
            $rootScope.selectedQuantity = "";
        }
    };

    $scope.removePart = function (index) {
        $scope.parts.splice(index, 1);
    };

    $scope.taoPhieuTinhTrang = function () {
        if (!$scope.selectedAppointment || !$scope.selectedAppointment.bienSoXe) {
            alert("Lỗi: Không có thông tin xe để tạo phiếu!");
            return;
        }

        const data = {
            bienSoXe: $scope.selectedAppointment.bienSoXe,
            moTaTinhTrangXe: $scope.selectedAppointment.moTaTinhTrangXe || ""
        };

        $http.post("http://localhost:8081/api/staff/phieu-tinh-trang", data)
            .then(function (response) {
                if (response.data && response.data.result) {
                    alert("Tạo phiếu ghi nhận thành công!");
                    $scope.loadAppointments();
                } else {
                    alert("Không thể tạo phiếu ghi nhận.");
                }
            })
            .catch(function (error) {
                console.error("Lỗi khi tạo phiếu:", error);
                alert("Lỗi khi tạo phiếu.");
            });

        var myModalEl = document.getElementById("lichHenModal");
        var modal = bootstrap.Modal.getInstance(myModalEl);
        if (modal) {
            modal.hide();
        }
    };

    $scope.saveServiceTicket = function () {
        alert("Phiếu dịch vụ đã được lưu!");
    };

    $scope.loadAppointments(); // Load lịch hẹn khi khởi tạo
});


appEmployee.controller("TaoLichHenController", function ($scope, $http) {
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

