		/*获取upload btn:点击选择文件的按钮;img:放置上传文件路径的hidden的ID;pic:预览上传图片的img的ID*/
		/*如有特殊需求-请在此JS添加新的方法 勿改此方法*/
        function getUpload(btn, img, pic) {
			var setting = {
				flash_url : contextPath+"/assets/swfupload/1.0.0/2.5/swfupload.swf",
				upload_url : contextPath+"/system/upload/doUpload",
				post_params : {},
				use_query_string : false,
				file_size_limit : "5 MB",
				file_types : "*.jpg;*.png;*.gif;*.jpeg;*.bmp",
				file_types_description : "All Files",
				file_upload_limit : 0,
				file_queue_limit : 0,
				debug : false,
				// Button settings
				button_width : '60',
				button_height : "20",
				button_text : "<span class=\"fontStyle\">选择图片</span>",
				button_text_style : ".fontStyle{font-size: 12px;color:#A9A9A9;}", 
				button_placeholder_id : btn,
				button_text_left_padding : 0,
				button_text_top_padding : 6,
				button_cursor : SWFUpload.CURSOR.HAND,
				file_queued_handler : function(file) {
				},
				file_dialog_complete_handler : function(numFilesSelected,
						numFilesQueued) {
					////console.log(1);
					this.startUpload();
				},
				upload_start_handler : function(file) {
					//console.log(file)
					return true;
				},
				upload_error_handler : function(file, errorCode, message) {
					try {
					} catch (ex) {
						this.debug(ex);
					}
				},
				upload_success_handler : function uploadSuccess(file,
						serverData) {
					var data = eval("(" + serverData + ")");
					$("#" + img).val(data.bean.realPath);
					var url = contextPath +"/"+ data.bean.realPath;
					$("#" + pic).attr("src", url);
				}
			};
			return new SWFUpload(setting);
		}
        
        
        
        function fnGetUpload(btn, img, pic, uploadUrl) {
			var setting = {
				flash_url : contextPath+"/assets/swfupload/1.0.0/2.5/swfupload.swf",
				upload_url : contextPath+uploadUrl,
				post_params : {},
				use_query_string : false,
				file_size_limit : "5 MB",
				file_types : "*.jpg;*.png;*.gif;*.jpeg;*.bmp",
				file_types_description : "All Files",
				file_upload_limit : 0,
				file_queue_limit : 0,
				debug : false,
				// Button settings
				button_width : '60',
				button_height : "20",
				button_text : "<span class=\"fontStyle\">选择图片</span>",
				button_text_style : ".fontStyle{font-size: 12px;color:#A9A9A9;}", 
				button_placeholder_id : btn,
				button_text_left_padding : 0,
				button_text_top_padding : 6,
				button_cursor : SWFUpload.CURSOR.HAND,
				file_queued_handler : function(file) {
				},
				file_dialog_complete_handler : function(numFilesSelected,
						numFilesQueued) {
					////console.log(1);
					this.startUpload();
				},
				upload_start_handler : function(file) {
					//console.log(file)
					return true;
				},
				upload_error_handler : function(file, errorCode, message) {
					try {
					} catch (ex) {
						this.debug(ex);
					}
				},
				upload_success_handler : function uploadSuccess(file,
						serverData) {
					var data = eval("(" + serverData + ")");
					$("#" + img).val(data.bean.realPath);
					var url = contextPath +"/"+ data.bean.realPath;
					$("#" + pic).attr("src", url);
				}
			};
			return new SWFUpload(setting);
		}
        
        
        
        /*该方法为保存到非项目路径下如e:/img/advertisement/，---广告管理使用 回调方法写法不同 数据库存的是图片名称 不带路径*/    
        function advGetUpload(btn, img, pic, uploadUrl) {
			var setting = {
				flash_url : contextPath+"/assets/swfupload/1.0.0/2.5/swfupload.swf",
				upload_url : contextPath+uploadUrl,
				post_params : {},
				use_query_string : false,
				file_size_limit : "5 MB",
				file_types : "*.jpg;*.png;*.gif;*.jpeg;*.bmp",
				file_types_description : "All Files",
				file_upload_limit : 0,
				file_queue_limit : 0,
				debug : false,
				// Button settings
				button_width : '60',
				button_height : "20",
				button_text : "<span class=\"fontStyle\">选择图片</span>",
				button_text_style : ".fontStyle{font-size: 12px;color:#A9A9A9;}", 
				button_placeholder_id : btn,
				button_text_left_padding : 0,
				button_text_top_padding : 6,
				button_cursor : SWFUpload.CURSOR.HAND,
				file_queued_handler : function(file) {
				},
				file_dialog_complete_handler : function(numFilesSelected,
						numFilesQueued) {
					////console.log(1);
					this.startUpload();
				},
				upload_start_handler : function(file) {
					//console.log(file)
					return true;
				},
				upload_error_handler : function(file, errorCode, message) {
					try {
					} catch (ex) {
						this.debug(ex);
					}
				},
				upload_success_handler : function uploadSuccess(file,
						serverData) {
					var data = eval("(" + serverData + ")");
					$("#" + img).val(data.bean.titleField);
					var url = data.bean.realPath;
					$("#" + pic).attr("src", url);
				}
			};
			return new SWFUpload(setting);
		}
        /*上传Excel文件 优惠信息*/
        function getFileMerchant(btn,filePath) {
        	var setting = {
        			flash_url : contextPath+"/assets/swfupload/1.0.0/2.5/swfupload.swf",
        			upload_url : contextPath+"/biz/merchant/importMerchant",
        			post_params : {},
        			use_query_string : false,
        			file_size_limit : 0,
        			file_types : "*.xls;*.xlsx;",
        			file_types_description : "All Files",
        			file_upload_limit : 0,
        			file_queue_limit : 0,
        			debug : false,
        			// Button settings
        			button_width : '100',
        			button_height : "20",
        			button_text : "<span class=\"fontStyle\">选择文件并导入</span>",
        			button_text_style : ".fontStyle{font-size: 12px;color:#A9A9A9;}", 
        			button_placeholder_id : btn,
        			button_text_left_padding : 0,
        			button_text_top_padding : 6,
        			button_cursor : SWFUpload.CURSOR.HAND,
        			file_queued_handler : function(file) {
        			},
        			file_dialog_complete_handler : function(numFilesSelected,
        					numFilesQueued) {
        				////console.log(1);
        				this.startUpload();
        			},
        			upload_start_handler : function(file) {
        				//console.log(file)
        				return true;
        			},
        			upload_error_handler : function(file, errorCode, message) {
        				try {
        				} catch (ex) {
        					this.debug(ex);
        				}
        			},
        			upload_success_handler : function uploadSuccess(file,
        					serverData) {
        				var data = eval("(" + serverData + ")");
        				$("#"+filePath).val("文件已导入成功，保存路径为："+data.bean.realPath);
        				initMerchantListInfo();
        			}
        	};
        	return new SWFUpload(setting);
        }
        /*上传Excel文件*/
        function getFile(btn,filePath,getList) {
			var setting = {
				flash_url : contextPath+"/assets/swfupload/1.0.0/2.5/swfupload.swf",
				upload_url : contextPath+"/biz/atm/importAtm",
				post_params : {},
				use_query_string : false,
				file_size_limit : "5 MB",
				file_types : "*.xls;*.xlsx;",
				file_types_description : "All Files",
				file_upload_limit : 0,
				file_queue_limit : 0,
				debug : false,
				// Button settings
				button_width : '100',
				button_height : "20",
				button_text : "<span class=\"fontStyle\">选择文件并导入</span>",
				button_text_style : ".fontStyle{font-size: 12px;color:#A9A9A9;}", 
				button_placeholder_id : btn,
				button_text_left_padding : 0,
				button_text_top_padding : 6,
				button_cursor : SWFUpload.CURSOR.HAND,
				file_queued_handler : function(file) {
				},
				file_dialog_complete_handler : function(numFilesSelected,
						numFilesQueued) {
					////console.log(1);
					this.startUpload();
				},
				upload_start_handler : function(file) {
					//console.log(file)
					return true;
				},
				upload_error_handler : function(file, errorCode, message) {
					try {
					} catch (ex) {
						this.debug(ex);
					}
				},
				upload_success_handler : function uploadSuccess(file,
						serverData) {
					var data = eval("(" + serverData + ")");
					$("#"+filePath).val("文件已导入成功，保存路径为："+data.bean.realPath);
					getList();
				}
			};
			return new SWFUpload(setting);
		}
        
        /**上传个金部客户等级信息 excel*/
        function getFileStomerInfo(btn,filePath) {
        	var setting = {
        			flash_url : contextPath+"/assets/swfupload/1.0.0/2.5/swfupload.swf",
        			upload_url : contextPath+"/system/customerLevelInfo/importMerchant",
        			post_params : {},
        			use_query_string : false,
        			file_size_limit : "20 MB",
        			file_types : "*.xls;*.xlsx;",
        			file_types_description : "All Files",
        			file_upload_limit : 0,
        			file_queue_limit : 0,
        			debug : false,
        			// Button settings
        			button_width : '100',
        			button_height : "20",
        			button_text : "<span class=\"fontStyle\">选择文件并导入</span>",
        			button_text_style : ".fontStyle{font-size: 12px;color:#A9A9A9;}", 
        			button_placeholder_id : btn,
        			button_text_left_padding : 0,
        			button_text_top_padding : 6,
        			button_cursor : SWFUpload.CURSOR.HAND,
        			file_queued_handler : function(file) {
        			},
        			file_dialog_complete_handler : function(numFilesSelected,
        					numFilesQueued) {
        				////console.log(1);
        				this.startUpload();
        			},
        			upload_start_handler : function(file) {
        				//console.log(file)
        				return true;
        			},
        			upload_error_handler : function(file, errorCode, message) {
        				try {
        				} catch (ex) {
        					this.debug(ex);
        				}
        			},
        			upload_success_handler : function uploadSuccess(file,
        					serverData) {
        				var data = eval("(" + serverData + ")");
        				$("#"+filePath).val("文件已导入成功，保存路径为："+data.bean.realPath);
        				initMerchantListInfo();
        			}
        	};
        	return new SWFUpload(setting);
        }
        
        /*上传Excel文件*/
        /**
         * @param btn 上传按钮的id
         * @param filePath 页面的提示位置
         * @param savePath 上传文件的保存路径（相对路径）
         * @param limitSize 文件限制大小
         * @param fileType  上传文件支持的类型
         * @param button_width 上传按钮的宽
         * @param button_height 上传按钮的高
         * @param getList  上传完成后，获取信息列表
         * @returns {SWFUpload}
         */
        function getFile(btn,filePath,savePath,limitSize,fileType,button_width,button_height,getList) {
			var setting = {
				flash_url : contextPath+"/assets/swfupload/1.0.0/2.5/swfupload.swf",
				upload_url : contextPath+savePath,
				post_params : {},
				use_query_string : false,
				file_size_limit : limitSize,
				file_types : fileType,
				file_types_description : "All Files",
				file_upload_limit : 0,
				file_queue_limit : 0,
				debug : false,
				// Button settings
				button_width : button_width,
				button_height : button_height,
				button_text : "<span class=\"fontStyle\">选择文件并导入</span>",
				button_text_style : ".fontStyle{font-size: 12px;color:#A9A9A9;}", 
				button_placeholder_id : btn,
				button_text_left_padding : 0,
				button_text_top_padding : 6,
				button_cursor : SWFUpload.CURSOR.HAND,
				file_queued_handler : function(file) {
				},
				file_dialog_complete_handler : function(numFilesSelected,
						numFilesQueued) {
					////console.log(1);
					this.startUpload();
				},
				upload_start_handler : function(file) {
					//console.log(file)
					return true;
				},
				upload_error_handler : function(file, errorCode, message) {
					try {
					} catch (ex) {
						this.debug(ex);
					}
				},
				upload_success_handler : function uploadSuccess(file,
						serverData) {
					var data = eval("(" + serverData + ")");
					//alert(data);
					$("#"+filePath).val("文件已导入成功，保存路径为："+data.bean.realPath);
					getList();
				}
			};
			return new SWFUpload(setting);
		}
        /*邮学堂文章上传*/
        function getArticleStudyUpload(btn, img, pic) {
			var setting = {
				flash_url : contextPath+"/assets/swfupload/1.0.0/2.5/swfupload.swf",
				upload_url : contextPath+"/biz/studyArticle/doUpload",
				post_params : {},
				use_query_string : false,
				file_size_limit : "5 MB",
				file_types : "*.jpg;*.png;*.gif;*.jpeg;*.bmp",
				file_types_description : "All Files",
				file_upload_limit : 0,
				file_queue_limit : 0,
				debug : false,
				// Button settings
				button_width : '60',
				button_height : "20",
				button_text : "<span class=\"fontStyle\">选择图片</span>",
				button_text_style : ".fontStyle{font-size: 12px;color:#A9A9A9;}", 
				button_placeholder_id : btn,
				button_text_left_padding : 0,
				button_text_top_padding : 6,
				button_cursor : SWFUpload.CURSOR.HAND,
				file_queued_handler : function(file) {
				},
				file_dialog_complete_handler : function(numFilesSelected,
						numFilesQueued) {
					////console.log(1);
					this.startUpload();
				},
				upload_start_handler : function(file) {
					//console.log(file)
					return true;
				},
				upload_error_handler : function(file, errorCode, message) {
					try {
					} catch (ex) {
						this.debug(ex);
					}
				},
				upload_success_handler : function uploadSuccess(file,
						serverData) {
					var data = eval("(" + serverData + ")");
					$("#" + img).val(data.bean.titleField);
					var url = data.bean.realPath;
					$("#" + pic).attr("src", url);
				}
			};
			return new SWFUpload(setting);
		}
        
        
        /*上传微信素材之图片*/    
        function uploadMaterial(btn, img, pic, uploadUrl,initimageInfo) {
			var setting = {
				flash_url : contextPath+"/assets/swfupload/1.0.0/2.5/swfupload.swf",
				upload_url : contextPath+uploadUrl,
				post_params : {},
				use_query_string : true,
				file_size_limit : "2 MB",
				file_types : "*.jpg;*.png;*.gif;*.jpeg;*.bmp",
				file_types_description : "All Files",
				file_upload_limit : 0,
				file_queue_limit : 0,
				debug : false,
				// Button settings
				button_width : '60',
				button_height : "20",
				button_text : "<span class=\"fontStyle\">选择图片</span>",
				button_text_style : ".fontStyle{font-size: 12px;color:#A9A9A9;}", 
				button_placeholder_id : btn,
				button_text_left_padding : 0,
				button_text_top_padding : 6,
				button_cursor : SWFUpload.CURSOR.HAND,
				file_queued_handler : function(file) {
				},
				file_dialog_complete_handler : function(numFilesSelected,
						numFilesQueued) {
					console.log(document.getElementById("addimagename"));
					if(document.getElementById("addimagename") != null){
						var param={'imageName':document.getElementById("addimagename").value};
						this.setPostParams(param);
					}
					this.startUpload();
				},
				upload_start_handler : function(file) {
					//console.log(file)
					return true;
				},
				upload_error_handler : function(file, errorCode, message) {
					try {
					} catch (ex) {
						this.debug(ex);
					}
				},
				upload_success_handler : function uploadSuccess(file,
						serverData) {
					var data = JSON.parse(serverData);
					if(data.bean.errorMessage !="" && data.bean.errorMessage !=null){
						alert(data.bean.errorMessage);
					}else if(data.bean.mediaId==null || data.bean.mediaId==''){
						alert("微信服务器调用失败。请稍后重试");
					}else if(data.returnCode=="0"){
						$("#" + img).val(data.bean.titleField);
						var url = data.bean.realPath;
						$("#" + pic).attr("src", url);
						$("#returnMessage").text(data.returnMessage);
						//在回调函数里面加入了返回的参数，如果不需要，可以不使用，没有影响
						initimageInfo(null,null,null,file,data);
					}else{
						alert("core层调用异常");
					}

				}
			};
			return new SWFUpload(setting);
		}
        
        
        /*上传微信素材之视频*/    
        function uploadVideo(btn, localUrl, showVideo, uploadUrl) {
			var setting = {
				flash_url : contextPath+"/assets/swfupload/1.0.0/2.5/swfupload.swf",
				upload_url : contextPath+uploadUrl,
				post_params : {},
				use_query_string : false,
				file_size_limit : "10 MB",
				file_types : "*.mp4;",
				file_types_description : "All Files",
				file_upload_limit : 0,
				file_queue_limit : 0,
				debug : false,
				// Button settings
				button_width : '60',
				button_height : "20",
				button_text : "<span class=\"fontStyle\">选择视频</span>",
				button_text_style : ".fontStyle{font-size: 12px;color:#A9A9A9;}", 
				button_placeholder_id : btn,
				button_text_left_padding : 0,
				button_text_top_padding : 6,
				button_cursor : SWFUpload.CURSOR.HAND,
				file_queued_handler : function(file) {
				},
				file_dialog_complete_handler : function(numFilesSelected,
						numFilesQueued) {
					////console.log(1);
					this.startUpload();
				},
				upload_start_handler : function(file) {
					//console.log(file)
					return true;
				},
				upload_error_handler : function(file, errorCode, message) {
					try {
					} catch (ex) {
						this.debug(ex);
					}
				},
				upload_success_handler : function uploadSuccess(file,
						serverData) {
					var data = eval("(" + serverData + ")");
					console.log(data);
					if(data.bean.errorMessage !="" && data.bean.errorMessage !=null){
						alert(data.bean.errorMessage);
					}else if(data.bean.mediaId==null || data.bean.mediaId==''){
						alert("微信服务器调用失败。请稍后重试");
					}else{
						$("#" + localUrl).val(data.bean.titleField);
						var url = data.bean.realPath;
						$("#" + showVideo).attr("src", url);
						$("#" + showVideo).show();
						$("#mediaId").val(data.bean.mediaId);
					}

				}
			};
			return new SWFUpload(setting);
		}
        
        
        /*上传微信素材之音频*/    
        function uploadVoice(btn, localUrl, showVoice, uploadUrl) {
			var setting = {
				flash_url : contextPath+"/assets/swfupload/1.0.0/2.5/swfupload.swf",
				upload_url : contextPath+uploadUrl,
				post_params : {},
				use_query_string : false,
				file_size_limit : "2 MB",
				file_types : "*.mp3;*.wma;*.wav;*.amr",
				file_types_description : "All Files",
				file_upload_limit : 0,
				file_queue_limit : 0,
				debug : false,
				// Button settings
				button_width : '60',
				button_height : "20",
				button_text : "<span class=\"fontStyle\">选择音频</span>",
				button_text_style : ".fontStyle{font-size: 12px;color:#A9A9A9;}", 
				button_placeholder_id : btn,
				button_text_left_padding : 0,
				button_text_top_padding : 6,
				button_cursor : SWFUpload.CURSOR.HAND,
				file_queued_handler : function(file) {
				},
				file_dialog_complete_handler : function(numFilesSelected,
						numFilesQueued) {
					////console.log(1);
					this.startUpload();
				},
				upload_start_handler : function(file) {
					//console.log(file)
					return true;
				},
				upload_error_handler : function(file, errorCode, message) {
					try {
					} catch (ex) {
						this.debug(ex);
					}
				},
				upload_success_handler : function uploadSuccess(file,
						serverData) {
					var data = eval("(" + serverData + ")");
					console.log(data);
					if(data.bean.errorMessage !="" && data.bean.errorMessage !=null){
						alert(data.bean.errorMessage);
					}else if(data.bean.mediaId==null || data.bean.mediaId==''){
						alert("微信服务器调用失败。请稍后重试");
					}else{
						$("#" + localUrl).val(data.bean.titleField);
						var url = data.bean.realPath;
						$("#" + showVoice).attr("src", url);
						$("#" + showVoice).show();
						$("#mediaId").val(data.bean.mediaId);
					}

				}
			};
			return new SWFUpload(setting);
		}
        
        
        /*导入Excel文件 抽奖人员信息*/
        function importAwardPlayer(btn,filePath) {
        	var setting = {
        			flash_url : contextPath+"/assets/swfupload/1.0.0/2.5/swfupload.swf",
        			upload_url : contextPath+"/biz/awardPlayer/getAwardPlayer",
        			post_params : {},
        			use_query_string : false,
        			file_size_limit : 0,
        			file_types : "*.xls;*.xlsx;",
        			file_types_description : "All Files",
        			file_upload_limit : 0,
        			file_queue_limit : 0,
        			debug : false,
        			// Button settings
        			button_width : '100',
        			button_height : "20",
        			button_text : "<span class=\"fontStyle\">选择文件并导入</span>",
        			button_text_style : ".fontStyle{font-size: 12px;color:#A9A9A9;}", 
        			button_placeholder_id : btn,
        			button_text_left_padding : 0,
        			button_text_top_padding : 6,
        			button_cursor : SWFUpload.CURSOR.HAND,
        			file_queued_handler : function(file) {
        			},
        			file_dialog_complete_handler : function(numFilesSelected,
        					numFilesQueued) {
        				////console.log(1);
        				this.startUpload();
        			},
        			upload_start_handler : function(file) {
        				//console.log(file)
        				return true;
        			},
        			upload_error_handler : function(file, errorCode, message) {
        				try {
        				} catch (ex) {
        					this.debug(ex);
        				}
        			},
        			upload_success_handler : function uploadSuccess(file,
        					serverData) {
        				var data = eval("(" + serverData + ")");
        				$("#"+filePath).val("数据上传成功,点击'确定'开始导入");
        				
        			}
        	};
        	return new SWFUpload(setting);
        }