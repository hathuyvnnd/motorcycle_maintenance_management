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
    case "Chưa đến":
      return "#!/ghi-nhan-tinh-trang/" + appointment.id;
    case "Đang kiểm tra":
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
      case "Chưa đến":
        return "Lập phiếu tình trạng xe";
      case "Đang kiểm tra":
        return "Lập phiếu dịch vụ";
      case "Đang sửa chữa":
        return "Cập nhật phiếu dịch vụ";
      case "Đã hoàn thành":
        return "Thanh toán";
      case "Đã thanh toán":
        return "Xem hóa đơn";
      default:
        return "Không xác định";
    }
  };
});

