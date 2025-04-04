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
});

app.controller("lichHenController", function ($scope, $http, $rootScope) {
    $scope.appointments = [];
    $scope.selectedAppointment = null;
    $scope.panelTemplate = ""; // Load giao diện modal phù hợp
    $scope.dichVuList = [];


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
                  $http.get("http://localhost:8081/api/staff/lich-hen-chi-tiet/byidlichhen" ,{ params: { idLichHen: $scope.selectedAppointment.idLichHen } })
                    .then(function (response) {
                        if (response.data.result) {
                            $scope.services = response.data.result;
                            $scope.tesst = "Anhi";

                            console.log("Dịch vụ trong lịch hẹn:", $scope.services);
                        }
                    })
                    .catch(function (error) {
                        console.error("Lỗi khi tải danh sách dịch vụ:", error);
                    });


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
//            if ($rootScope.selectedService === "Thay thế phụ tùng") {
//                $scope.showPartsSelection = true;
//            }
             console.log("dich vu hien tai", $scope.services);
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
        if ($scope.services.length === 0) {
            alert("Vui lòng chọn ít nhất một dịch vụ!");
            return;
        }
            console.log("tesst", $scope.tesst);

    $http.get("http://localhost:8081/api/staff/phieu-tinh-trang/phieu-gnx/find", {
                    params: {
                        bienSoXe: $scope.selectedAppointment.bienSoXe,
                        thoiGian: $scope.selectedAppointment.thoiGian
                    }
                })
                .then(function (response) {
                    if (response.data && response.data.result) {
                        $scope.idPhieuGNX = response.data.result.idPhieuGNX;
                        console.log("ID phiếu GNX:", $scope.idPhieuGNX);
                         var requestDataa = {
                                    tenNhanVienSuaChua: "",
                                    idPhieuGNX: $scope.idPhieuGNX, // Lúc này đã có giá trị
                                    listIdDichVu: $scope.services.map(function (service) {
                                        return service.idDichVu;
                                    }),
                                };
                                 console.log("listDv", requestDataa);

                                        $http.post("http://localhost:8081/api/staff/phieu-dich-vu/tao-phieu", requestDataa)
                                            .then(function (response) {
                                                alert("Phieu dich vu đã được tạo thành công!");
                                                location.reload(); // Tải lại trang
                                            })
                                            .catch(function (error) {
                                                console.error("Lỗi khi tạo lịch hẹn:", error);
                                            });
                    } else {
                        console.warn("Không tìm thấy phiếu ghi nhận.");
                    }
                })
                .catch(function (error) {
                    console.error("Lỗi khi lấy phiếu ghi nhận:", error);
                });
    };
    console.log("tesst", $scope.tesst);
    $scope.loadAppointments();
    $scope.loadDichVu = function () {
            $http.get("http://localhost:8081/api/admin/dichvu")
                .then(function (response) {
                    if (response.data) {
                        console.log("da goi api lay dich vu");
                        $scope.dichVuList = response.data;
                        console.log("listdichvu", $scope.dichVuList );
                    } else {
                        console.error("API không trả về dữ liệu hợp lệ.");
                    }
                })
                .catch(function (error) {
                    console.error("Lỗi khi tải danh sách dịch vụ:", error);
                });
        };
    $scope.loadDichVu();
});


//app.controller("TaoLichHenController", function ($scope, $http) {
//    $scope.phone = "";
//    $scope.customerExists = false;
//    $scope.phoneChecked = false;
//    $scope.tenKhachHang = "";
//    $scope.bienSoXe = "";
//    $scope.moTa = "";
//    $scope.thoiGian = "";
//    $scope.idLoaiXe = "";
//    $scope.danhSachLoaiXe = [
//        { id: 1, ten: "Xe máy" },
//        { id: 2, ten: "Ô tô" }
//    ];
//
//    // Hàm kiểm tra số điện thoại
//    $scope.checkPhone = function () {
//        if (!$scope.phone) {
//            alert("Vui lòng nhập số điện thoại!");
//            return;
//        }
//
//        $http.get("http://localhost:8081/api/lich-hen/checkPhone", { params: { phone: $scope.phone } })
//            .then(function (response) {
//                if (response.data.result) {
//                    // Nếu số điện thoại có tài khoản, tự động điền tên & khóa ô nhập
//                    $scope.customerExists = true;
//                    $scope.tenKhachHang = response.data.result.tenKhachHang; // ✅ Gán đúng biến
//                } else {
//                    // Nếu chưa có tài khoản, cho phép nhập
//                    $scope.customerExists = false;
//                    $scope.tenKhachHang = "";
//                }
//                $scope.phoneChecked = true;
//            })
//            .catch(function (error) {
//                console.error("Lỗi khi kiểm tra số điện thoại:", error);
//            });
//    };
//
//    // Hàm tạo lịch hẹn
//    $scope.taoLichHen = function () {
//        if (!$scope.bienSoXe) {
//            alert("Vui lòng nhập biển số xe!");
//            return;
//        }
//
//        if (!$scope.tenKhachHang || $scope.tenKhachHang.trim() === "") {
//            alert("Vui lòng nhập tên khách hàng!");
//            return;
//        }
//
//        var requestData = {
//            idKhachHang: $scope.phone,
//            thoiGian: $scope.thoiGian ? new Date($scope.thoiGian).toISOString() : new Date().toISOString(),
//            bienSoXe: $scope.bienSoXe,
//            ghiChu: $scope.moTa,
//            dichVu: "Bảo dưỡng",
//            tenKhachHang: $scope.tenKhachHang
//        };
//
//        $http.post("http://localhost:8081/api/lich-hen/tao-lich-hen", requestData)
//            .then(function (response) {
//                alert("Lịch hẹn đã được tạo thành công!");
//                location.reload();
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
    $scope.services = [];  // Mảng lưu trữ các dịch vụ đã chọn
    $scope.selectedService = null; // Dịch vụ đang được chọn
    $scope.listIdDichVu = []; // Danh sách các dịch vụ

    // Hàm kiểm tra số điện thoại
    $scope.checkPhone = function () {
        if (!$scope.phone) {
            alert("Vui lòng nhập số điện thoại!");
            return;
        }

        $http.get("http://localhost:8081/api/lich-hen/checkPhone", { params: { phone: $scope.phone } })
            .then(function (response) {
                if (response.data.result) {
                    $scope.customerExists = true;
                    $scope.tenKhachHang = response.data.result.tenKhachHang;
                } else {
                    $scope.customerExists = false;
                    $scope.tenKhachHang = "";
                }
                $scope.phoneChecked = true;
            })
            .catch(function (error) {
                console.error("Lỗi khi kiểm tra số điện thoại:", error);
            });
    };

    // Hàm thêm dịch vụ vào mảng dịch vụ đã chọn
    $scope.addService = function () {
        if ($scope.selectedService && !$scope.services.includes($scope.selectedService)) {
            $scope.services.push($scope.selectedService);
            $scope.selectedService = null; // Reset selected service sau khi thêm
            console.log("Danh sách dịch vụ đã chọn: ", $scope.services);
        } else {
            alert("Vui lòng chọn dịch vụ để thêm.");
        }
    };

    // Hàm xóa dịch vụ khỏi mảng dịch vụ đã chọn
    $scope.removeService = function (index) {
        $scope.services.splice(index, 1);
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

        if ($scope.services.length === 0) {
            alert("Vui lòng chọn ít nhất một dịch vụ!");
            return;
        }

        var requestData = {
            idKhachHang: $scope.phone,
            thoiGian: $scope.thoiGian ? new Date($scope.thoiGian).toISOString() : new Date().toISOString(),
            bienSoXe: $scope.bienSoXe,
            ghiChu: $scope.moTa,
            listIdDichVu: $scope.services.map(function (service) {
                       return service.idDichVu;
                   }),
            tenKhachHang: $scope.tenKhachHang
        };
        console.log("listDv", $scope.listIdDichVu);

        // Gửi yêu cầu tạo lịch hẹn
        $http.post("http://localhost:8081/api/lich-hen/tao-lich-hen", requestData)
            .then(function (response) {
                alert("Lịch hẹn đã được tạo thành công!");
                location.reload(); // Tải lại trang
            })
            .catch(function (error) {
                console.error("Lỗi khi tạo lịch hẹn:", error);
            });
    };

    // Hàm tải danh sách dịch vụ
    $scope.loadDichVu = function () {
        $http.get("http://localhost:8081/api/admin/dichvu")
            .then(function (response) {
                if (response.data) {
                    $scope.dichVuList = response.data; // Gán danh sách dịch vụ vào scope
                } else {
                    console.error("API không trả về dữ liệu hợp lệ.");
                }
            })
            .catch(function (error) {
                console.error("Lỗi khi tải danh sách dịch vụ:", error);
            });
    };

    $scope.loadDichVu(); // Tải danh sách dịch vụ khi khởi tạo controller
});
