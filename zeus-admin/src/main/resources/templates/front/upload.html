<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>File Upload Dialog</title>
    <!-- Tailwind CSS CDN -->
    <script src="https://cdn.tailwindcss.com"></script>
    <!-- jQuery CDN -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <style>
        .file-upload-preview {
            display: none;
        }
        .file-upload-preview.active {
            display: block;
        }
        .progress-bar {
            transition: width 0.3s ease-in-out;
        }
    </style>
</head>
<body class="bg-gray-100 min-h-screen flex items-center justify-center">
    <div class="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
        <h2 class="text-2xl font-bold mb-6 text-gray-800">File Upload</h2>
        
        <!-- Upload Area -->
        <div class="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center mb-4 hover:border-blue-500 transition-colors cursor-pointer" id="dropZone">
            <div class="space-y-2">
                <svg class="mx-auto h-12 w-12 text-gray-400" stroke="currentColor" fill="none" viewBox="0 0 48 48">
                    <path d="M28 8H12a4 4 0 00-4 4v20m32-12v8m0 0v8a4 4 0 01-4 4H12a4 4 0 01-4-4v-4m32-4l-3.172-3.172a4 4 0 00-5.656 0L28 28M8 32l9.172-9.172a4 4 0 015.656 0L28 28m0 0l4 4m4-24h8m-4-4v8m-12 4h.02" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                </svg>
                <div class="text-gray-600">
                    <span class="text-blue-500 hover:text-blue-600" id="browseButton">Browse files</span> or drag and drop
                </div>
                <p class="text-sm text-gray-500">PNG, JPG, GIF up to 10MB</p>
            </div>
            <input type="file" id="fileInput" class="hidden" multiple accept=".png,.jpg,.jpeg,.gif">
        </div>

        <!-- Preview Area -->
        <div id="previewArea" class="space-y-4">
            <!-- Preview items will be added here -->
        </div>

        <!-- Upload Progress -->
        <div id="uploadProgress" class="hidden mt-4">
            <div class="flex justify-between text-sm text-gray-600 mb-1">
                <span id="progressText">Uploading...</span>
                <span id="progressPercentage">0%</span>
            </div>
            <div class="w-full bg-gray-200 rounded-full h-2">
                <div id="progressBar" class="progress-bar bg-blue-500 h-2 rounded-full" style="width: 0%"></div>
            </div>
        </div>

        <!-- Upload Button -->
        <button id="uploadButton" class="w-full bg-blue-500 text-white py-2 px-4 rounded-lg hover:bg-blue-600 transition-colors disabled:opacity-50 disabled:cursor-not-allowed mt-4" disabled>
            Upload Files
        </button>

        <!-- Status Messages -->
        <div id="statusMessage" class="mt-4 text-sm"></div>
    </div>

    <script>
        $(document).ready(function() {
            const dropZone = $('#dropZone');
            const fileInput = $('#fileInput');
            const previewArea = $('#previewArea');
            const uploadButton = $('#uploadButton');
            const uploadProgress = $('#uploadProgress');
            const progressBar = $('#progressBar');
            const progressText = $('#progressText');
            const progressPercentage = $('#progressPercentage');
            const statusMessage = $('#statusMessage');
            let selectedFiles = [];

            // 配置
            const config = {
                maxFileSize: 10 * 1024 * 1024, // 10MB
                allowedTypes: ['image/png', 'image/jpeg', 'image/gif'],
                uploadUrl: '/api/upload' // 在这里修改上传URL
            };

            // Handle browse button click
            $('#browseButton').click(function() {
                fileInput.click();
            });

            // Handle file selection
            fileInput.change(function(e) {
                handleFiles(e.target.files);
            });

            // Handle drag and drop
            dropZone.on('dragover', function(e) {
                e.preventDefault();
                $(this).addClass('border-blue-500');
            });

            dropZone.on('dragleave', function(e) {
                e.preventDefault();
                $(this).removeClass('border-blue-500');
            });

            dropZone.on('drop', function(e) {
                e.preventDefault();
                $(this).removeClass('border-blue-500');
                handleFiles(e.originalEvent.dataTransfer.files);
            });

            function validateFile(file) {
                if (!config.allowedTypes.includes(file.type)) {
                    <#--throw new Error(`File type ${file.type} is not allowed`);-->
                }
                if (file.size > config.maxFileSize) {
                    <#--throw new Error(`File size exceeds ${formatFileSize(config.maxFileSize)}`);-->
                }
                return true;
            }

            function handleFiles(files) {
                selectedFiles = [];
                previewArea.empty();
                statusMessage.removeClass('text-red-500 text-green-500').text('');
                
                Array.from(files).forEach((file, index) => {
                    try {
                        validateFile(file);
                        selectedFiles.push(file);
                        addPreviewItem(file, index);
                    } catch (error) {
                        showStatus(error.message, 'error');
                    }
                });

                uploadButton.prop('disabled', selectedFiles.length === 0);
            }

            function addPreviewItem(file, index) {
                const previewItem = $(`
                    <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                        <div class="flex items-center space-x-3">
                            <div class="w-10 h-10 bg-gray-200 rounded flex items-center justify-center">
                                <svg class="w-6 h-6 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 21h10a2 2 0 002-2V9.414a1 1 0 00-.293-.707l-5.414-5.414A1 1 0 0012.586 3H7a2 2 0 00-2 2v14a2 2 0 002 2z" />
                                </svg>
                            </div>
                            <div>
                                <p class="text-sm font-medium text-gray-900">${r'${file.name}'}</p>
                                <p class="text-xs text-gray-500">${r'${formatFileSize(file.size)}'}</p>
                            </div>
                        </div>
                        <button class="text-red-500 hover:text-red-600" onclick="removeFile(${r'${index}'})">
                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                            </svg>
                        </button>
                    </div>
                `);
                previewArea.append(previewItem);
            }

            // Format file size
            function formatFileSize(bytes) {
                if (bytes === 0) return '0 Bytes';
                const k = 1024;
                const sizes = ['Bytes', 'KB', 'MB', 'GB'];
                const i = Math.floor(Math.log(bytes) / Math.log(k));
                return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
            }

            // Remove file from selection
            window.removeFile = function(index) {
                selectedFiles.splice(index, 1);
                handleFiles(selectedFiles);
            };

            function showStatus(message, type = 'info') {
                statusMessage
                    .removeClass('text-red-500 text-green-500')
                    .addClass(type === 'error' ? 'text-red-500' : 'text-green-500')
                    .text(message);
            }

            function updateProgress(percent) {
                progressBar.css('width', percent + '%');
                progressPercentage.text(percent + '%');
            }

            // Handle upload button click
            uploadButton.click(function() {
                if (selectedFiles.length === 0) return;

                const formData = new FormData();
                selectedFiles.forEach(file => {
                    formData.append('files[]', file);
                });

                // 禁用上传按钮
                uploadButton.prop('disabled', true);
                // 显示进度条
                uploadProgress.removeClass('hidden');
                // 重置进度
                updateProgress(0);
                showStatus('Uploading files...', 'info');

                $.ajax({
                    url: config.uploadUrl,
                    type: 'POST',
                    data: formData,
                    processData: false,
                    contentType: false,
                    xhr: function() {
                        const xhr = new window.XMLHttpRequest();
                        xhr.upload.addEventListener('progress', function(e) {
                            if (e.lengthComputable) {
                                const percent = Math.round((e.loaded / e.total) * 100);
                                updateProgress(percent);
                            }
                        }, false);
                        return xhr;
                    },
                    success: function(response) {
                        showStatus('Files uploaded successfully!', 'success');
                        // 清空选择的文件
                        selectedFiles = [];
                        previewArea.empty();
                        uploadButton.prop('disabled', true);
                        // 隐藏进度条
                        setTimeout(() => {
                            uploadProgress.addClass('hidden');
                        }, 1000);
                    },
                    error: function(xhr, status, error) {
                        showStatus('Upload failed: ' + (xhr.responseText || error), 'error');
                        uploadButton.prop('disabled', false);
                    }
                });
            });
        });
    </script>
</body>
</html> 