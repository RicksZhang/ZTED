# POST http://localhost:8080/ZTED/administrator/register
{
"name":"",
"email":"",
"password":"",
"confirmPassword":"",
"position":""
}
400:"两次密码输入不匹配"
404:"注册邮箱已存在"
403:"Register failed *_*"
# POST http://localhost:8080/ZTED/administrator/login
{
"email":"",
"password":"",
}
429:"请一分钟后再尝试"
400:"邮箱或密码输入错误，请重新输入"
# POST http://localhost:8080/ZTED/user/login
{
"email":"",
"password":"",
}
404:"邮箱不存在，请重新输入或注册"
429:"请一分钟后再尝试"
400:"邮箱或密码输入错误，请重新输入"
返回：
{
"Username": "",
"LoginSuccess": ""
}
# POST http://localhost:8080/ZTED/user/register
{
"name":"",
"email":"",
"phoneNum":"",
"password":"",
"confirmPassword":""
}
400:"两次密码输入不匹配"
404:"注册邮箱已存在"
403:"Register failed *_*"
# POST http://localhost:8080/ZTED/registrationForm
{
"registerEmail":"",
"name":"",
"phoneNum":"",
"companyName":"",
"position":"",
"annualRevenue":"",
"classType":""
}
404:"用户未找到"
429:"一个账号最多提交5次报名信息"
400:"Submit failed *_*"
401:"请重新登陆"
# GET http://localhost:8080/ZTED/registerform?adminEmail=
只有position = "1"才能通过权限
# GET http://localhost:8080/ZTED/allUsers?adminEmail=
只有position = "1"才能通过权限
# GET http://localhost:8080/ZTED/allAdmins?adminEmail=
只有position = "1"才能通过权限
# DELETE http://localhost:8080/ZTED/registration/{id}adminEmail=
204:删除成功，无返回
404:未找到，无返回
400:权限不足
# DELETE http://localhost:8080/ZTED/administrator/{email}adminEmail=
204:删除成功，无返回
404:未找到，无返回
400:权限不足
# DELETE http://localhost:8080/ZTED/user/{deletemail}adminEmail=
204:删除成功，无返回
404:未找到，无返回
400:权限不足


