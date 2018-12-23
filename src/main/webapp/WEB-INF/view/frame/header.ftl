	<script>
	$(document).ready(function(){
		$("#setMenuDiv").menu({
			zIndex:1000
		});
		$("#setButton").on("click",function(e){
			setButtonTop  = $(this).offset().top;
			setButtonLeft = $(this).offset().left;
			
			$("#setMenuDiv").menu("show",{
				left:(setButtonLeft-8),
				top:(setButtonTop+37)
			});
		})
		
		//监听退出按钮
		$("#logoutButton").on("click",function(e){
			$.ajax({
				url:"${domain}logout.action",
				dataType:"json",
				success:function(rtn){
					if(rtn.rtnCode=="000000"){
						location.href="${domain}";
					}
				}
			})
		})
		//监听修改密码按钮
		$("#changePasswordButton").on("click",function(e){
			
		})	
	})
	</script>
	
	<div data-options="region:'north',border:false" class="super-north" >
			<!--顶部-->
			<div class="super-navigation">
				<!--系统名称-->
				<div class="super-navigation-title">
					<span style="margin-left:5px">人事工资发放系统</span>
				</div>
				<!--自定义导航-->
				<div class="super-navigation-main">
					<div class="super-setting-left">
						<ul>
							<!--
							<li><i class="fa fa-commenting-o"></i>消息</li>
							<li><i class="fa fa-envelope-o"></i>邮件</li>
							<li><i class="fa fa-bell-o"></i>通知</li>
							-->
						</ul>
					</div>
					<div class="super-setting-right">
						<ul>
							<li>
								<div class="super-setting-icon" id="setButton">
									设置<i class="fa fa-chevron-down"></i>
								</div>
								<div id="setMenuDiv" style="display:none">
									<div id="changePasswordButton">修改密码</div>
									<div id="settingThemeButton">主题</div>
									<div class="menu-sep"></div>
									<div id="logoutButton">退出</div>
								</div>
							</li>
							<li class="user">
								欢迎<span class="user-icon"></span>${sessionUser.userName}
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		