app.controller("loginController", function ($scope, $http, $location) {
  $scope.username = "";
  $scope.password = "";
  $scope.errorMessage = "";


  $scope.login = function () {
    $http.post("http://localhost:8080/login", null, {
      params: {
        username: $scope.username,
        password: $scope.password
      }
    }).then(function (response) {
      if (response.status === 200) {
        // Lưu idTaiKhoan vào localStorage
        localStorage.setItem("idTaiKhoan", response.data.idTaiKhoan);
        $location.path("/");
      } else {
        Swal.fire({
          title: "Thất bại",
          text: response.data.message || "Đăng nhập thất bại!",
          icon: "error"
        });
      }
    }).catch(function (error) {
      console.log(error);
      var errorMessage = "Tên đăng nhập hoặc mật khẩu không đúng!";
      if (error.data && error.data.message) {
        errorMessage = error.data.message;
      }
      Swal.fire({
        title: "Thất bại",
        text: errorMessage,
        icon: "error"
      });
      $scope.errorMessage = errorMessage;
    });
  };
});
