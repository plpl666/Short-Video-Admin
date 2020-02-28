<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<!-- BEGIN PAGE HEADER-->
	<!-- BEGIN PAGE BAR -->
	<div class="page-bar">
	    <ul class="page-breadcrumb">
	        <li>
	            <span>首页</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>bgm管理</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>添加背景音乐</span>
	        </li>
	    </ul>
	</div>
	<!-- END PAGE BAR -->
	<!-- END PAGE HEADER-->
                        
	<div class="row">
    	<div class="col-md-12">
			<br/>
			<!-- 意见反馈 start -->
			<div class="tabbable-line boxless tabbable-reversed">
            	<div class="portlet box green-jungle">
                	<div class="portlet-title">
                    	<div class="caption">
                    		<i class="icon-plus"></i>添加背景音乐
                    	</div>
					</div>
					
					<div class="portlet-body form">
					
	                    <!-- BEGIN FORM-->
	                    <form id="addBgmForm" class="form-horizontal">
		                    <div class="form-body">
		                    
		                    	<div class="form-group">
		                        	<label class="col-md-3 control-label"><span class="field-required"> * </span>歌手:</label>
		                            <div class="col-md-4">
		                            	<div class="input-error">
		                            		<input id="author" name="author" type="text" class="form-control" placeholder="请输入歌手名">
		                            	</div>
									</div>
								</div>
								
								<div class="form-group">
		                        	<label class="col-md-3 control-label"><span class="field-required"> * </span>歌曲:</label>
		                            <div class="col-md-4">
		                            	<div class="input-error">
		                            		<input id="name" name="name" type="text" class="form-control" placeholder="请输入歌曲名">
		                            	</div>
									</div>
								</div>
								
								<div class="form-group">
		                        	<label class="col-md-3 control-label"><span class="field-required"> * </span>音乐片段</label>
		                            <div class="col-md-4">
		                            	<div class="input-error">
	                            			<%--<input type="hidden" id="path" name="path" class="form-control"/>--%>

											<input id="file" type="file" style="margin-top: 8px;" name="file" accept=".mp3"/>
											
											<div id="bgmContent"></div>
	                            		</div>
	                            		
									</div>
								</div>
		                                                  
							</div>
	                                                        
							<div class="form-actions">
			                    <div class="row">
			                        <div class="col-md-offset-3 col-md-9">
			                            <button type="button" id="button" class="btn green-jungle">提 交</button>
			                            <button type="reset" class="btn grey-salsa btn-outline">取  消</button>
			                        </div>
			                    </div>
							</div>
						</form>
						<!-- END FORM-->
						
					</div>
				</div>
			</div>
                            
		</div>
	</div>
	
<script type="text/javascript">



    $('#button').click(function () {
		if ($('#author').val() !== '' && $('#name').val() !== '' && $('#file').val() !== '') {
		    $.ajax({
                url: $('#hdnContextPath').val() + '/videos/addBgm',
                data: new FormData($('#addBgmForm')[0]),
                type: 'post',
                dataType: 'json',
				//上传文件时后三项必写
                contentType: false,
                processData: false,
                cache: false,
				//
                success: function (data) {
                    console.log(data);
					if (data.status === 200) {
					    alert("上传成功!");
					    $('#addBgmForm')[0].reset();
					} else {
					    alert(data.msg);
					}
                }
            });
		}
    });

    <%--$("#file").fileupload({--%>
        <%--// pasteZone: "#bgmContent",--%>
        <%--dataType: "json",--%>
        <%--url: "<%=request.getContextPath() %>/videos/bgmUpload",--%>
        <%--done: function (e, data) {--%>
            <%--if (data.result.status !== 200) {--%>
                <%--alert(data.result.msg);--%>
            <%--} else {--%>
                <%--var bgmServer = $("#bgmServer").val();--%>
                <%--var url = bgmServer + data.result.data;--%>
                <%--$("#bgmContent").html("<a href='" + url + "' target='_blank'>点我播放</a>");--%>
                <%--$("#path").attr("value", data.result.data);--%>
            <%--}--%>
        <%--}--%>
    <%--});--%>


    // $('#addBgmForm').validate({
    //     errorElement: 'span', //default input error message container
    //     errorClass: 'help-block', // default input error message class
    //     focusInvalid: false, // do not focus the last invalid input
    //     rules: {
    //         author: {
    //             required: true
    //         },
    //         name: {
    //             required: true
    //         },
		// 	path: {
    //             required: true
		// 	}
    //     },
    //
    //     messages: {
    //         author: {
    //             required: "歌手不能为空."
    //         },
    //         name: {
    //             required: "歌名不能为空."
    //         },
		// 	path: {
    //             required: "路径不能为空."
		// 	}
    //     },
    //
    //     highlight: function(element) { // hightlight error inputs
    //         $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
    //     },
    //
    //     success: function(label) {
    //         label.closest('.form-group').removeClass('has-error');
    //         label.remove();
    //     },
    //
    //     errorPlacement: function(error, element) {
    //         error.insertAfter(element.closest('.input-error'));
    //     },
    //
		// invalidHandler: function (e, validator) {
		//
    //     },
    //
    //     submitHandler: function(form) {
    //
    //     }
    // });
    
    // var submitBgm = function () {
    //     $('#addBgmForm').ajaxSubmit({
		// 	url: $('#hdnContextPath').val() + '/videos/addBgm',
		// 	type: 'post',
		// 	success: function (data) {
		// 		if (data.status === 200) {
		// 		    alert('Bgm添加成功...');
		// 		} else {
		// 		    alert('Bgm添加失败...');
		// 		}
    //         }
		// });
    // }
    
</script>
