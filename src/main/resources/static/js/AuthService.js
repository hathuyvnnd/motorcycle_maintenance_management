app.factory("AuthService", function ($rootScope) {
    return {
        login: function (token, vaiTro) {
            sessionStorage.setItem("token", token);
            sessionStorage.setItem("vaiTro", vaiTro);
            $rootScope.isLoggedIn = true;
        },
        logout: function () {
            sessionStorage.removeItem("token");
            sessionStorage.removeItem("vaiTro");
            sessionStorage.clear();
            $rootScope.isLoggedIn = false;
        },
        isLoggedIn: function () {
            return sessionStorage.getItem("token") !== null;
        },
        getToken: function () {
            return sessionStorage.getItem("token");
        },
        getRole: function () {
            return sessionStorage.getItem("vaiTro");
        },
        getToken: function () {
            return sessionStorage.getItem('token');
        }
    };
});
