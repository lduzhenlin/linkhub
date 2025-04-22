<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户登录</title>
    <style>
        .main{
            width: 300px;
            margin:200px auto;
        }
        .main .form{
            display: flex;
        }
        .main input{
          height: 30px;
          line-height: 30px;
          width: 250px;
          margin-bottom: 10px;
        }
        .main input[type='submit']{
            height: 35px;
          line-height: 35px;
          width: 90px;
          margin-top:10px;
        }
    </style>
</head>
<body>
    <div class="main">
        <form action="/api/login" method="post">
            <label style="margin-right:10px;">用户名:</label><input type="text" name="username" value="admin" placeholder="请输入用户名"/><br/>
            <label style="margin-right:10px;">密码:</label><input type="password" name="password" value="123456" placeholder="请输入密码"/><br/>
            <input type="submit" value="登录"/>&nbsp;&nbsp;
        </form>
    </div>
</body>
</html>