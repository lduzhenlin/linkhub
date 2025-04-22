<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>首页</title>
    <style>
        .header{
            display: flex;
            justify-content: flex-end;
        }
        .main{
           width: 700px;
           margin:200px auto;
        }
        .main table{
           width: 100%;
           text-align: center;

        }
        .main .search{

        }
        .main .search input.username{
           height: 30px;
           line-height: 30px;
           width: 250px;
        }
        .main .search input.btn{
           height: 35px;
           line-height: 35px;
           width: 90px;

        }

        .main .add{
            margin-top:20px;
            margin-bottom: 5px;
        }
        .main .add input{
        height: 35px;
           line-height: 35px;
           width: 90px;
        }

    </style>
</head>
<body>
    <div class="header">
        欢迎
        <form action="/api/logout" method="post" >
            <input type="submit" value="退出">
        </form>
    </div>
    <div class="main">
<#--        ${msg}-->
        index
    </div>

    

    
</body>
</html>