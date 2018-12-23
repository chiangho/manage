$.extend($.fn.combobox.methods, {
    yearandmonth: function (jq) {
        return jq.each(function () {
            var obj = $(this).combobox();
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var table = $('<table>');
            var tr1 = $('<tr>');
            var tr1td1 = $('<td>', {
                text: '-',
                click: function () {
                    var y = $(this).next().html();
                    y = parseInt(y) - 1;
                    $(this).next().html(y);
                }
            });
            tr1td1.appendTo(tr1);
            var tr1td2 = $('<td>', {
                text: year
            }).appendTo(tr1);

            var tr1td3 = $('<td>', {
                text: '+',
                click: function () {
                    var y = $(this).prev().html();
                    y = parseInt(y) + 1;
                    $(this).prev().html(y);
                }
            }).appendTo(tr1);
            tr1.appendTo(table);

            var n = 1;
            for (var i = 1; i <= 4; i++) {
                var tr = $('<tr>');
                for (var m = 1; m <= 3; m++) {
                    var td = $('<td>', {
                        text: n,
                        click: function () {
                            var yyyy = $(table).find("tr:first>td:eq(1)").html();
                            var cell = $(this).html();
                            var v = yyyy + '-' + (cell.length < 2 ? '0' + cell : cell);
                            obj.combobox('setValue', v).combobox('hidePanel');

                        }
                    });
                    if (n == month) {
                        td.addClass('tdbackground');
                    }
                    td.appendTo(tr);
                    n++;
                }
                tr.appendTo(table);
            }
            table.addClass('mytable cursor');
            table.find('td').hover(function () {
                $(this).addClass('tdbackground');
            }, function () {
                $(this).removeClass('tdbackground');
            });
            table.appendTo(obj.combobox("panel"));

        });
    }
});


function stampToTime(timestamp) {
	if(timestamp==null||timestamp==""||timestamp==undefined){
		return "";
	}
	if(timestamp.length==10){
		timestamp = timestamp*1000;
	}
	var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() + ' ';
    h = date.getHours() + ':';
    m = date.getMinutes() + ':';
    s = date.getSeconds();
    return Y+M+D+h+m+s;
}

function stampToDate(timestamp) {
	if(timestamp==null||timestamp==""||timestamp==undefined){
		return "";
	}
	if(timestamp.length==10){
		timestamp = timestamp*1000;
	}
	var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
	Y = date.getFullYear() + '-';
	M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
	D = date.getDate() + ' ';
	h = date.getHours() + ':';
	m = date.getMinutes() + ':';
	s = date.getSeconds();
	return Y+M+D;
}



function isCardNo(code){
	var city = {
		11: "北京",
		12: "天津",
		13: "河北",
		14: "山西",
		15: "内蒙古",
		21: "辽宁",
		22: "吉林",
		23: "黑龙江 ",
		31: "上海",
		32: "江苏",
		33: "浙江",
		34: "安徽",
		35: "福建",
		36: "江西",
		37: "山东",
		41: "河南",
		42: "湖北 ",
		43: "湖南",
		44: "广东",
		45: "广西",
		46: "海南",
		50: "重庆",
		51: "四川",
		52: "贵州",
		53: "云南",
		54: "西藏 ",
		61: "陕西",
		62: "甘肃",
		63: "青海",
		64: "宁夏",
		65: "新疆",
		71: "台湾",
		81: "香港",
		82: "澳门",
		91: "国外 "
	};
	var tip = "";
	var pass = true;

	if (!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)) {
		tip = "身份证号格式错误";
		pass = false;
	} else if (!city[code.substr(0, 2)]) {
		tip = "地址编码错误";
		pass = false;
	} else {
		//18位身份证需要验证最后一位校验位
		if (code.length == 18) {
			code = code.split('');
			//∑(ai×Wi)(mod 11)
			//加权因子
			var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
			//校验位
			var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
			var sum = 0;
			var ai = 0;
			var wi = 0;
			for (var i = 0; i < 17; i++) {
				ai = code[i];
				wi = factor[i];
				sum += ai * wi;
			}
			var last = parity[sum % 11];
			if (parity[sum % 11] != code[17]) {
				tip = "校验位错误";
				pass = false;
			}
		}
	}
	return pass;
}


/***
 * 获取 uuid
 * @returns 返回唯一编码
 */
function getUuid() {
	var s = [];
	var hexDigits = "0123456789abcdef";
	for (var i = 0; i < 36; i++) {
		s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	}
	s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
	s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
	s[8] = s[13] = s[18] = s[23] = "-";

	var uuid = s.join("");
	return uuid;
}

var hao = (function(remote, $, undefined) {
	$.ajaxSetup({
		global: false,
		timeout: '900000'
	});
	/***
	 * 异步请求
	 */
	remote.ajax = function(options) {
		options = options || {};
		var async = options.async === undefined ? true : options.async;
		var url = options.url;
		var traditional = options.traditional === undefined?false:options.traditional;
		var dataType = (options.dataType === undefined||options.dataType==null)?"json":options.dataType;
		$.ajax({
			type: "POST",
			async: async,
			url: url,
			data: options.data,
			dataType:dataType,
			traditional:traditional,
			success: function(res) {
				if(res.rtnCode=="NO_AOUTH"){
					window.location.href = res.domain+res.loginUrl;
				}else{
					options.success(res);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			
			}
		});
	};
	
	/***
	 * 异步提交表单
	 */
	remote.submitForm = function(options) {
		options = options || {};
		var async = options.async === undefined ? true : options.async;
		var url = options.url;
		if(options.form){
			var formOpt = new Object();
			formOpt.dataType="json";
			formOpt.async=async;
			if(options.data){
				formOpt.data=options.data;
			}
			if(options.url){
				formOpt.url=options.url;
			}
			if(options.success){
				formOpt.success=options.success;
			}
			$(options.form).ajaxSubmit(formOpt)
		}else{
			console.log("缺少表单对象");
		}
	};
	//检查手机是否联网
	remote.checkConnection = function() {
		var flag = true;
		var networkState = navigator.connection.type;
		var states = {};
		states[Connection.UNKNOWN] = 'Unknown connection';
		states[Connection.ETHERNET] = 'Ethernet connection';
		states[Connection.WIFI] = 'WiFi connection';
		states[Connection.CELL_2G] = 'Cell 2G connection';
		states[Connection.CELL_3G] = 'Cell 3G connection';
		states[Connection.CELL_4G] = 'Cell 4G connection';
		states[Connection.CELL] = 'Cell generic connection';
		states[Connection.NONE] = 'No network connection';

		if ('No network connection' === states[networkState]) {
			flag = false;
		}
		return flag;
	}
	
	/***
	 * 异步加载页面
	 */
	remote.ajaxLoad = function(doc,url,data,callBack){
		if(doc==null){
			return;
		}
		if($(doc)){
			$(doc).load(url,data,function(response,status,xhr){
				
			})
		}
	}
	
	/***
	 * 上传
	 * url
	 * success
	 */
	remote.ajaxUploading = function(s){
		if(!s.success){
			console.log("缺少回调函数");
			return;
		}
		if(!s.url||s.url==""){
			console.log("缺少参数上传地址");
			return;
		}
		
		var fileId = getUuid();
		console.info(fileId);
		var uploadFile = document.createElement("input"); 
		uploadFile.type = "file";
		uploadFile.id = fileId;
		uploadFile.name = "file1";
		document.body.appendChild(uploadFile);
		uploadFile.click();
		uploadFile.onchange=function(){ 
			remote.ajaxUpload({
				fileId:fileId,
				url:s.url,
				success:s.success
			});
			$('#'+fileId).remove();
		};   
		return;
	}
	
	/***
	 * 上传
	 * fileId
	 * data
	 * url
	 * success
	 */
	remote.ajaxUpload = function(s){
		if(!s.fileId){
			console.log("缺少参数fileId");
			return;
		}
		if(!s.success){
			console.log("缺少回调函数");
			return;
		}
		if(!s.url||s.url==""){
			console.log("缺少参数上传地址");
			return;
		}
		
		var id = getUuid();
		//创建上传需要的表单
		var formId = 'jUploadForm' + id;
		var fileId = 'jUploadFile' + id;
		var form = $('<form  action="" method="POST" name="' + formId + '" id="' + formId+ '" enctype="multipart/form-data"></form>');
		
		var oldElement = $('#' + s.fileId);
//		console.info((oldElement).attr("id"));
		if($(oldElement).attr("name")==undefined||$(oldElement).attr("name")==""){
			console.log("fileId值为空");
			return;
		}
		
		var newElement = $(oldElement).clone();
		$(oldElement).before(newElement);
		$(oldElement).attr('id', fileId);
		$(oldElement).appendTo(form);
		
		
		// set attributes
		$(form).css('position', 'absolute');
		$(form).css('top', '-1200px');
		$(form).css('left', '-1200px');
		$(form).appendTo('body');
		
		if(s.data){
			var originalElement = $('<input type="hidden" name="" value="">');
			for ( var key in s.data) {
				name = key;
				value = s.data[key];
				var cloneElement = originalElement.clone();
				cloneElement.attr({
					'name' : name,
					'value' : value
				});
				$(cloneElement).appendTo(form);
			}
		}
		
		$(form).attr('action', s.url);
		$(form).attr('method', 'POST');
		if (form.encoding) {
			form.encoding = 'multipart/form-data';
		} else {
			form.enctype = 'multipart/form-data';
		}
		remote.submitForm({
			form:form,
			success:function(rtn){
				s.success(rtn);
				$(form).remove();
			}
		})
	}
	return remote;
}(hao || {}, jQuery));
