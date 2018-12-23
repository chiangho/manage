-- 初始化超级管理员
delete from sys_access where id=1;
insert into sys_access(id,access,mobile_phone,email,user_name,password)values(1,'admin','13110000000','chiangho@123.com','超级管理员','96E79218965EB72C92A549DD5A330112');
-- 初始化菜单
delete from sys_menu where id in(1,2,3,4);
insert into sys_menu(id,name,url,level,parent_id,sort)values(1,'系统设置','',1,0,1);
insert into sys_menu(id,name,url,level,parent_id,sort)values(2,'账号管理','access/index.action',2,1,1);
insert into sys_menu(id,name,url,level,parent_id,sort)values(3,'权限管理','permission/index.action',2,1,2);
insert into sys_menu(id,name,url,level,parent_id,sort)values(4,'角色管理','role/index.action',2,1,3);
insert into sys_menu(id,name,url,level,parent_id,sort)values(5,'菜单管理','menu/index.action',2,1,4);

-- 初始化角色
delete from sys_role where id=1;
insert into sys_role(id,role_code,role_name)values(1,'SUPER_ADMIN','超级管理员');
-- 设置admin为超级管理员
delete from sys_access_role where id=1;
insert into sys_access_role(id,access_id,role_id)values(1,1,1);


