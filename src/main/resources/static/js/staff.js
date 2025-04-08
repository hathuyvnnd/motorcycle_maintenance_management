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
            case "Đang sửa chữa":
                return "Hoàn tất sửa chữa";
            case "Đã sửa chữa":
                return "Thanh toán";
            case "Đã thanh toán":
                return "Hoàn tất";
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
               case "Đang sửa chữa":
                   $scope.panelTemplate = "/employee/content/panel/xacNhanHoanTatPanel.html";
                   break;
               case "Đã sửa chữa":
                   $scope.panelTemplate = "/employee/content/panel/thanhToanPanel.html"; // <- File html cho modal thanh toán
                     $http.get("http://localhost:8081/test/findid/phieu-phu-tung", {
                           params: { id: $scope.selectedAppointment.idLichHen }
                       }).then(function (response) {
                           $scope.phuTungDaChon = response.data.result;
                           $scope.tinhTongTien(); // Cập nhật tổng tiền sau khi load
                       });

                       $http.get("http://localhost:8081/test/findid/phieu-dich-vu-ct", {
                           params: { id: $scope.selectedAppointment.idLichHen }
                       }).then(function (response) {
                           $scope.dichVuDaChon = response.data.result;
                           $scope.tinhTongTien(); // Cập nhật tổng tiền sau khi load
                       });
                   break;
            default:
                $scope.panelTemplate = "";
                break;
        }

        var myModal = new bootstrap.Modal(document.getElementById("lichHenModal"));
        myModal.show();
    };
$scope.dichVuDaChon = [];
$scope.phuTungDaChon = [];
$scope.tongTien = 0;

$scope.tinhTongTien = function () {
    let tong = 0;
    if ($scope.dichVuDaChon) {
        $scope.dichVuDaChon.forEach(d => tong += d.dichVu.giaDichVu);
    }
    if ($scope.phuTungDaChon) {
        $scope.phuTungDaChon.forEach(p => tong += p.donGia * p.soLuong);
    }
    $scope.tongTien = tong;
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
            idLichHen: $scope.selectedAppointment.idLichHen,
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
    $scope.openModalXem = function (appointment) {
    $scope.selectedAppointment = appointment;

    switch (appointment.trangThai) {
        case "Đã xác nhận":
            $scope.panelTemplate = "/employee/content/panel/lichHenDetailPanel.html";
            break;
        case "Đang kiểm tra":
            $scope.panelTemplate = "/employee/content/panel/xemPhieuTinhTrangPanel.html";
            break;
        default:
            alert("Không xác định trạng thái để xem.");
            return;
    }

    $('#lichHenModal').modal('show');
};
    $scope.openModalThayDoi = function (appointment) {
    $scope.selectedAppointment = appointment;

    switch (appointment.trangThai) {
        case "Đã xác nhận":
            $scope.panelTemplate = "/employee/content/panel/lichHenEditPanel.html";
            break;
        case "Đang kiểm tra":
            $scope.panelTemplate = "/employee/content/panel/ghiNhanTinhTrangPanel.html";
            break;
        default:
            alert("Không xác định trạng thái để chỉnh sửa.");
            return;
    }

    $('#lichHenModal').modal('show');
};
    $scope.saveServiceTicket = function () {
        if ($scope.services.length === 0) {
            alert("Vui lòng chọn ít nhất một dịch vụ!");
            return;
        }
            console.log("tesst", $scope.tesst);

    $http.get("http://localhost:8081/test/findid/phieu-tinh-trang-by-lich-hen", { params: { id: $scope.selectedAppointment.idLichHen } })
                .then(function (response) {
                    if (response.data && response.data.result) {
                        $scope.idPhieuGNX = response.data.result.idPhieuGNX;
                        console.log("ID phiếu GNX:", $scope.idPhieuGNX);
                         var requestDataa = {
                                    tenNhanVienSuaChua: "An",
                                    idPhieuGNX: $scope.idPhieuGNX, // Lúc này đã có giá trị

                                    listIdDichVu: $scope.services.map(function (service) {
                                        return service.idDichVu;
                                    }),
                                    idLichHen: $scope.selectedAppointment.idLichHen
                                };
                                 console.log("listDv", requestDataa);

                                        $http.post("http://localhost:8081/api/staff/phieu-dich-vu/tao-phieu", requestDataa)
                                            .then(function (response) {
                                                alert("Phieu dich vu đã được tạo thành công!");
                                                location.reload();
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
    $scope.hoanTatSuaChua = function () {
        if (!$scope.selectedAppointment || !$scope.selectedAppointment.idLichHen) {
            alert("Không có lịch hẹn để cập nhật.");
            return;
        }

        $http.put("http://localhost:8081/api/lich-hen/update-trang-thai", {
            idLichHen: $scope.selectedAppointment.idLichHen,
            trangThai: "Đã sửa chữa"
        })
        .then(function (response) {
                alert(response.data.message);
                $scope.loadAppointments();
        })
        .catch(function (error) {
            console.error("Lỗi khi cập nhật trạng thái:", error);
            alert("Lỗi khi cập nhật!");
        });

        const modalEl = document.getElementById("lichHenModal");
        const modal = bootstrap.Modal.getInstance(modalEl);
        if (modal) {
            modal.hide();
        }
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



app.controller("thanhToanController", function($scope, $http) {
    var idLichHen = $scope.selectedAppointment.idLichHen;

    $http.get("http://localhost:8081/test/findid/phieu-phu-tung", { params: { id: idLichHen } })
        .then(function(response) {
            $scope.phuTungDaChon = response.data.result;
        });

    $http.get("http://localhost:8081/test/findid/phieu-dich-vu-ct", { params: { id: idLichHen } })
        .then(function(response) {
            $scope.dichVuDaChon = response.data.result;
        });

    // Tính tổng tiền
    $scope.tinhTongTien = function () {
        let tong = 0;
        if ($scope.dichVuDaChon) {
            $scope.dichVuDaChon.forEach(d => tong += d.dichVu.giaDichVu);
        }
        if ($scope.phuTungDaChon) {
            $scope.phuTungDaChon.forEach(p => tong += p.donGia * p.soLuong);
        }
        $scope.tongTien = tong;
    };

    // Tự động tính lại mỗi lần dữ liệu thay đổi
    $scope.$watchGroup(['dichVuDaChon', 'phuTungDaChon'], function () {
        $scope.tinhTongTien();
    });
});





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
