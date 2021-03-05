var main = {
    init: function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });

        $('#btn-signup').on('click', function () {
            _this.signup();
        });

        $('#btn-signin').on('click', function () {
            _this.signin();
        });

        $('#username').blur(function () {
            _this.idCheck();
        });

        $('#password').blur(function () {
            _this.passwordCheck1();
        });

        $('#password2').blur(function () {
            _this.passwordCheck2();
        });

        $('#btn-findId').on('click', function () {
            _this.findId();
        });

        $('#btn-findPassword').on('click', function () {
            _this.findPassword();
        });

        $('#btn-userUpdate').on('click', function () {
            _this.userUpdate();
        });

    },

    save: function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    update: function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    delete: function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    signup: function () {

        var data = {
            username: $('#username').val(),
            password: $('#password').val(),
            name: $('#name').val(),
            email: $('#email').val()
        };

        if ($('#username').val() == "") {
            $('#checkAll').html('<p style="color:red">아이디를 입력하지 않았습니다.</p>');
            $('#username').val().focus();
            return false;
        }
        if ($('#password').val() == "") {
            $('#checkAll').html('<p style="color:red">비밀번호를 입력하지 않았습니다.</p>');
            $('#password').val();
            return false;
        }
        if ($('#name').val() == "") {
            $('#checkAll').html('<p style="color:red">이름을 입력하지 않았습니다.</p>');
            $('#name').val().focus();
            return false;
        }
        if ($('#email').val() == "") {
            $('#checkAll').html('<p style="color:red">이메일을 입력하지 않았습니다.</p>');
            $('#email').val().focus();
            return false;
        }

        $.ajax({
            type: 'POST',
            url: '/api/v1/userLogin/signup',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('회원가입이 되었습니다.');
            window.location.href = '/login';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },

    signin: function () {
        var data = {
            username: $('#username').val(),
            password: $('#password').val()
        };

        if ($('#username').val() == "") {
            alert("아이디를 입력해주세요.")
            return false;
        }

        if ($('#password').val() == "") {
            alert("비밀번호를 입력해주세요.")
            return false;
        }

        $.ajax({
            type: 'POST',
            url: '/api/v1/userLogin/login',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            window.location.href = '/';
        }).fail(function (error) {
            alert("일치하지 않거나 없는 회원입니다.");
        });
    },

    idCheck: function () {

        if ($('#username').val().length < 5) {

            $('#checkId').html('<p style="color:red">아이디의 길이가 짧습니다.</p>');

        } else {
            $.ajax({
                type: "POST",
                url: "/api/v1/userLogin/idCheck",
                data: {
                    "username": $('#username').val()
                }
            }).done(function (data) {

                if ($.trim(data) == "YES") {
                    if ($('#username').val() != '') {
                        $('#checkId').html('<p style="color:green">사용 가능한 ID 입니다.</p>');
                    }
                } else if ($.trim(data) == "NO") {
                    if ($('#username').val() != '') {
                        $('#checkId').html('<p style="color:red">이미 사용중인 ID 입니다.</p>');
                        $('#username').val().focus();
                        return false;
                    }
                }
            });
        }
    },


    passwordCheck1: function () {

        var password = $('#password').val();
        var username = $('#username').val();

        if (password.length < 8 || password.length > 20) {
            $('#checkPw').html('<p style="color:red">비밀번호 길이가 8자에서 20자리가 되지 않습니다.</p>');
        } else {

            if (!/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/.test(password)) {
                $('#checkPw').html('<p style="color:red">숫자+영문자+특수문자 조합으로 8자리 이상 사용해야 합니다.</p>');
                return false;
            }
            var checkNumber = password.search(/[0-9]/g);
            var checkEnglish = password.search(/[a-z]/ig);

            if (checkNumber < 0 || checkEnglish < 0) {
                $('#checkPw').html('<p style="color:red">숫자와 영문자를 혼용하여야 합니다.</p>');
                password.focus();
                return false;
            }
            if (/(\w)\1\1\1/.test(password)) {
                $('#checkPw').html('<p style="color:red">같은 문자를 4번 이상 사용하실 수 없습니다.</p>');
                password.focus();
                return false;
            }

            if (password.search(username) > -1) {
                $('#checkPw').html('<p style="color:red">비밀번호에 아이디가 포함되었습니다.</p>');
                $('#password').val('').focus();
                return false;
            }

            return true;
            $('#checkPw').html('<p></p>');
        }


    },

    passwordCheck2: function () {

        if ($('#password').val() != $('#password2').val()) {
            if ($('#password2').val() != '') {
                $('#checkPw').html('<p style="color:red">비밀번호가 일치하지 않습니다.</p>');
                $('#password2').val('');
                $('#password2').focus();
            }
        } else if ($('#password').val() == $('#password2').val()) {
            $('#checkPw').html('<p style="color:green">비밀번호가 일치합니다.</p>');
        }

    },

    findId: function () {

        var data = {
            name: $('#name').val(),
            email: $('#email').val()
        };

        if ($('#name').val() == "") {
            alert("이름을 입력해주세요.")
            return false;
        }

        if ($('#email').val() == "") {
            alert("이메일을 입력해주세요.")
            return false;
        }

        $.ajax({
            type: 'POST',
            url: '/api/v1/userLogin/findid',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (data) {

            var id = data;
            var url = '/findid/' + id;
            var name = "아이디 찾기";
            var option = "width = 300, height = 300, top = 100, left = 200, location = no";
            window.open(url, name, option);
            $('#name').focus();
            $('#email').focus();

        }).fail(function (error) {
            $('#findid').html('<p style="color:red">일치하지 않거나 없는 회원입니다.</p>');
        });
    },

    findPassword: function () {

        var data = {
            username: $('#username').val(),
            name: $('#name').val(),
            email: $('#email').val()
        };

        if ($('#username').val() == "") {
            alert("아이디를 입력해주세요.")
            return false;
        }

        if ($('#name').val() == "") {
            alert("이름을 입력해주세요.")
            return false;
        }

        if ($('#email').val() == "") {
            alert("이메일을 입력해주세요.")
            return false;
        }

        $.ajax({
            type: 'POST',
            url: '/api/v1/userLogin/findpassword',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (data) {

            var id = data;
            var url = '/findpassword/' + id;
            var name = "비밀번호 찾기";
            var option = "width = 300, height = 300, top = 100, left = 200, location = no";
            window.open(url, name, option);
            $('#username').focus();
            $('#name').focus();
            $('#email').focus();

        }).fail(function (error) {
            $('#findpassword').html('<p style="color:red">일치하지 않거나 없는 회원입니다.</p>');
        });
    },

    userUpdate: function () {

        var data = {
            password: $('#password').val(),
            name: $('#name').val(),
            email: $('#email').val()
        };
        var username = $('#username').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/user/'+username,
            dataType: 'text',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('회원정보가 수정되었습니다.');
            window.location.href = '/mypage/detail/'+username;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }


};

main.init();