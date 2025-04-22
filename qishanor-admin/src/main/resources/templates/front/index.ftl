<!DOCTYPE html>
<html lang="zh-CN" class="dark">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>书签导航系统</title>
  <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
  <style>
    :root {
      --bg-dark: #1a1b1e;
      --bg-card-dark: #25262b;
      --bg-light: #f8f9fa;
      --bg-card-light: #ffffff;
    }
    
    /* 暗色模式 */
    .dark {
      color-scheme: dark;
    }
    .dark .bg-theme {
      background-color: var(--bg-dark);
    }
    .dark .bg-card-theme {
      background-color: var(--bg-card-dark);
    }
    .dark .border-theme {
      border-color: #2c2d31;
    }
    .dark .text-theme {
      color: #e5e5e5;
    }
    .dark .text-theme-secondary {
      color: #a1a1aa;
    }
    
    /* 浅色模式 */
    .light .bg-theme {
      background-color: var(--bg-light);
    }
    .light .bg-card-theme {
      background-color: var(--bg-card-light);
    }
    .light .border-theme {
      border-color: #e5e7eb;
    }
    .light .text-theme {
      color: #1f2937;
    }
    .light .text-theme-secondary {
      color: #6b7280;
    }

    /* 卡片阴影 */
    .light .card-shadow {
      box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
    }
  </style>
</head>
<body class="bg-theme text-theme min-h-screen transition-colors duration-200">
  <div class="min-h-screen flex flex-col">
    <!-- 顶部导航栏 -->
    <header class="bg-theme border-b border-theme">
      <div class="container mx-auto px-6 py-3">
        <div class="flex items-center justify-between">
          <div class="flex items-center space-x-3">
            <h1 class="text-xl font-bold text-purple-500">极速工具箱</h1>
            <span class="text-theme-secondary">|</span>
            <span class="text-theme-secondary text-sm">高效开发工具集成平台</span>
          </div>
          <div class="flex items-center space-x-6">
            <div class="relative">
              <input type="text" id="searchInput" placeholder="搜索工具..." 
                class="w-80 px-4 py-1.5 pl-10 bg-card-theme rounded text-theme border border-theme focus:outline-none focus:border-purple-500 transition-colors duration-200">
              <i class="fas fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-theme-secondary"></i>
            </div>
            <div class="flex items-center space-x-4">
              <button class="text-theme-secondary hover:text-purple-500 transition-colors" title="我的收藏">
                <i class="fas fa-star"></i>
              </button>
              <button class="text-theme-secondary hover:text-purple-500 transition-colors" title="消息通知">
                <i class="fas fa-bell"></i>
              </button>
              <button class="text-theme-secondary hover:text-purple-500 transition-colors" title="历史记录">
                <i class="fas fa-history"></i>
              </button>
              <button class="text-theme-secondary hover:text-purple-500 transition-colors" title="设置">
                <i class="fas fa-cog"></i>
              </button>
              <button id="themeToggle" class="text-theme-secondary hover:text-purple-500 transition-colors" title="切换主题">
                <i class="fas fa-moon dark:hidden"></i>
                <i class="fas fa-sun hidden dark:inline"></i>
              </button>
              <div class="h-4 w-px bg-theme-secondary opacity-20"></div>
              <button class="px-4 py-1.5 bg-purple-500 hover:bg-purple-600 text-white rounded text-sm transition-colors">
                登录
              </button>
            </div>
          </div>
        </div>
      </div>
    </header>

    <!-- 分类导航 -->
    <nav class="bg-theme sticky top-0 z-10 border-b border-theme">
      <div class="container mx-auto px-6 py-2">
        <div class="flex items-center space-x-2">
          <button class="px-4 py-1.5 text-sm bg-purple-500 text-white rounded">全部工具</button>
          <button class="px-4 py-1.5 text-sm text-theme-secondary hover:bg-card-theme rounded transition-colors">常用工具</button>
          <button class="px-4 py-1.5 text-sm text-theme-secondary hover:bg-card-theme rounded transition-colors">我的收藏</button>
          <button class="px-4 py-1.5 text-sm text-theme-secondary hover:bg-card-theme rounded transition-colors">JSON工具</button>
          <button class="px-4 py-1.5 text-sm text-theme-secondary hover:bg-card-theme rounded transition-colors">编码加密</button>
          <button class="px-4 py-1.5 text-sm text-theme-secondary hover:bg-card-theme rounded transition-colors">网络工具</button>
          <button class="px-4 py-1.5 text-sm text-theme-secondary hover:bg-card-theme rounded transition-colors">时间日期</button>
          <button class="px-4 py-1.5 text-sm text-theme-secondary hover:bg-card-theme rounded transition-colors">代码工具</button>
          <button class="px-4 py-1.5 text-sm text-theme-secondary hover:bg-card-theme rounded transition-colors">文本处理</button>
        </div>
      </div>
    </nav>

    <!-- 主要内容区 -->
    <main class="flex-1 container mx-auto px-6 py-6">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
        <!-- 工具卡片 -->
        <div class="bg-card-theme rounded-lg p-3 hover:ring-1 hover:ring-purple-500 transition-all duration-200 card-shadow">
          <div class="flex items-start justify-between mb-2">
            <div class="flex items-center space-x-2">
              <div class="w-8 h-8 bg-theme rounded flex items-center justify-center">
                <i class="fas fa-code text-base text-purple-500"></i>
              </div>
              <h3 class="text-sm font-medium text-theme">JSON格式化</h3>
            </div>
            <div class="flex items-center space-x-1">
              <button class="text-theme-secondary hover:text-purple-500 transition-colors p-1.5 rounded-full hover:bg-theme" onclick="openEditModal(this)" title="编辑">
                <i class="fas fa-edit text-sm"></i>
              </button>
              <button class="text-theme-secondary hover:text-red-500 transition-colors p-1.5 rounded-full hover:bg-theme" onclick="openDeleteModal(this)" title="删除">
                <i class="fas fa-trash-alt text-sm"></i>
              </button>
            </div>
          </div>
          <p class="text-xs text-theme-secondary line-clamp-2">JSON数据美化与验证工具，支持格式化、压缩、转义等功能</p>
        </div>

        <div class="bg-card-theme rounded-lg p-3 hover:ring-1 hover:ring-purple-500 transition-all duration-200 card-shadow">
          <div class="flex items-start justify-between mb-2">
            <div class="flex items-center space-x-2">
              <div class="w-8 h-8 bg-theme rounded flex items-center justify-center">
                <i class="fas fa-globe text-base text-blue-500"></i>
              </div>
              <h3 class="text-sm font-medium text-theme">HTTP请求测试</h3>
            </div>
            <div class="flex items-center space-x-1">
              <button class="text-theme-secondary hover:text-purple-500 transition-colors p-1.5 rounded-full hover:bg-theme" onclick="openEditModal(this)" title="编辑">
                <i class="fas fa-edit text-sm"></i>
              </button>
              <button class="text-theme-secondary hover:text-red-500 transition-colors p-1.5 rounded-full hover:bg-theme" onclick="openDeleteModal(this)" title="删除">
                <i class="fas fa-trash-alt text-sm"></i>
              </button>
            </div>
          </div>
          <p class="text-xs text-theme-secondary line-clamp-2">在线API接口调试工具，支持多种请求方式和参数设置</p>
        </div>

        <div class="bg-card-theme rounded-lg p-3 hover:ring-1 hover:ring-purple-500 transition-all duration-200 card-shadow">
          <div class="flex items-start justify-between mb-2">
            <div class="flex items-center space-x-2">
              <div class="w-8 h-8 bg-theme rounded flex items-center justify-center">
                <i class="fas fa-clock text-base text-green-500"></i>
              </div>
              <h3 class="text-sm font-medium text-theme">时间转换</h3>
            </div>
            <div class="flex items-center space-x-1">
              <button class="text-theme-secondary hover:text-purple-500 transition-colors p-1.5 rounded-full hover:bg-theme" onclick="openEditModal(this)" title="编辑">
                <i class="fas fa-edit text-sm"></i>
              </button>
              <button class="text-theme-secondary hover:text-red-500 transition-colors p-1.5 rounded-full hover:bg-theme" onclick="openDeleteModal(this)" title="删除">
                <i class="fas fa-trash-alt text-sm"></i>
              </button>
            </div>
          </div>
          <p class="text-xs text-theme-secondary line-clamp-2">时间戳转换工具，支持多种格式和时区转换</p>
        </div>

        <div class="bg-card-theme rounded-lg p-3 hover:ring-1 hover:ring-purple-500 transition-all duration-200 card-shadow">
          <div class="flex items-start justify-between mb-2">
            <div class="flex items-center space-x-2">
              <div class="w-8 h-8 bg-theme rounded flex items-center justify-center">
                <i class="fas fa-file-alt text-base text-red-500"></i>
              </div>
              <h3 class="text-sm font-medium text-theme">Base64编码</h3>
            </div>
            <div class="flex items-center space-x-1">
              <button class="text-theme-secondary hover:text-purple-500 transition-colors p-1.5 rounded-full hover:bg-theme" onclick="openEditModal(this)" title="编辑">
                <i class="fas fa-edit text-sm"></i>
              </button>
              <button class="text-theme-secondary hover:text-red-500 transition-colors p-1.5 rounded-full hover:bg-theme" onclick="openDeleteModal(this)" title="删除">
                <i class="fas fa-trash-alt text-sm"></i>
              </button>
            </div>
          </div>
          <p class="text-xs text-theme-secondary line-clamp-2">Base64编码解码工具，支持文本和文件处理</p>
        </div>
      </div>
    </main>
  </div>

  <!-- 编辑弹框 -->
  <div id="editModal" class="fixed inset-0 bg-black bg-opacity-50 hidden items-center justify-center z-50">
    <div class="bg-card-theme rounded-lg w-[480px] relative">
      <div class="p-4 border-b border-theme flex justify-between items-center">
        <h2 class="text-base font-medium text-theme">编辑书签</h2>
        <button onclick="closeEditModal()" class="text-theme-secondary hover:text-theme transition-colors">
          <i class="fas fa-times"></i>
        </button>
      </div>
      <form id="editForm" class="p-6">
        <div class="space-y-4">
          <div>
            <label class="block text-sm text-theme mb-1">名称</label>
            <input type="text" id="editTitle" class="w-full px-3 py-2 bg-theme border border-theme rounded text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入书签名称">
          </div>
          <div>
            <label class="block text-sm text-theme mb-1">链接</label>
            <input type="url" id="editUrl" class="w-full px-3 py-2 bg-theme border border-theme rounded text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入链接地址">
          </div>
          <div>
            <label class="block text-sm text-theme mb-1">描述</label>
            <textarea id="editDescription" rows="3" class="w-full px-3 py-2 bg-theme border border-theme rounded text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors resize-none" placeholder="请输入书签描述"></textarea>
          </div>
          <div>
            <label class="block text-sm text-theme mb-1">分类</label>
            <select id="editCategory" class="w-full px-3 py-2 bg-theme border border-theme rounded text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors appearance-none">
              <option value="">请选择分类</option>
              <option value="常用工具">常用工具</option>
              <option value="JSON工具">JSON工具</option>
              <option value="编码加密">编码加密</option>
              <option value="网络工具">网络工具</option>
              <option value="时间日期">时间日期</option>
              <option value="文本处理">文本处理</option>
            </select>
          </div>
          <div>
            <label class="block text-sm text-theme mb-1">图标</label>
            <div class="flex items-center space-x-2">
              <div class="w-8 h-8 bg-theme rounded flex items-center justify-center">
                <img id="previewIcon" src="" alt="" class="w-4 h-4">
              </div>
              <div class="flex-1">
                <input type="text" id="editIcon" class="w-full px-3 py-2 bg-theme border border-theme rounded text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入图标地址">
              </div>
              <button type="button" class="px-4 py-2 bg-theme text-sm text-theme-secondary hover:text-theme rounded border border-theme transition-colors">上传</button>
            </div>
          </div>
        </div>
      </form>
      <div class="p-4 border-t border-theme flex justify-end space-x-2">
        <button type="button" onclick="closeEditModal()" class="px-6 py-2 text-sm bg-theme hover:bg-opacity-80 text-theme-secondary rounded transition-colors">取消</button>
        <button type="button" onclick="saveEdit()" class="px-6 py-2 bg-blue-500 hover:bg-blue-600 text-white text-sm rounded transition-colors">保存</button>
      </div>
    </div>
  </div>

  <!-- 删除确认弹框 -->
  <div id="deleteModal" class="fixed inset-0 bg-black bg-opacity-50 hidden items-center justify-center z-50">
    <div class="bg-card-theme rounded-lg p-6 w-[400px] relative">
      <button onclick="closeDeleteModal()" class="absolute right-4 top-4 text-theme-secondary hover:text-purple-500 transition-colors">
        <i class="fas fa-times"></i>
      </button>
      <h2 class="text-lg font-medium text-theme mb-4">确认删除</h2>
      <p class="text-theme-secondary mb-6">确定要删除这个工具吗？此操作无法撤销。</p>
      <div class="flex justify-end space-x-3">
        <button onclick="closeDeleteModal()" class="px-4 py-2 text-sm text-theme-secondary hover:text-theme transition-colors">取消</button>
        <button onclick="confirmDelete()" class="px-4 py-2 bg-red-500 hover:bg-red-600 text-white text-sm rounded transition-colors">删除</button>
      </div>
    </div>
  </div>

  <!-- 登录弹框 -->
  <div id="loginModal" class="fixed inset-0 bg-black bg-opacity-50 hidden items-center justify-center z-50">
    <div class="bg-card-theme rounded-lg w-[480px] relative">
      <div class="p-8">
        <!-- 标题和关闭按钮 -->
        <div class="flex justify-between items-center mb-8">
          <h2 class="text-xl font-medium text-theme">欢迎登录</h2>
          <button onclick="closeLoginModal()" class="text-theme-secondary hover:text-theme transition-colors">
            <i class="fas fa-times"></i>
          </button>
        </div>

        <!-- 登录表单 -->
        <form id="loginForm" class="space-y-6">
          <div>
            <label class="block text-sm text-theme-secondary mb-2">账号</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <i class="fas fa-user text-theme-secondary"></i>
              </div>
              <input type="text" class="w-full pl-10 pr-4 py-2.5 bg-theme border border-theme rounded-md text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入用户名或邮箱">
            </div>
          </div>
          <div>
            <label class="block text-sm text-theme-secondary mb-2">密码</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <i class="fas fa-lock text-theme-secondary"></i>
              </div>
              <input type="password" class="w-full pl-10 pr-4 py-2.5 bg-theme border border-theme rounded-md text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入密码">
              <button type="button" class="absolute inset-y-0 right-0 pr-3 flex items-center text-theme-secondary hover:text-theme transition-colors" onclick="togglePassword(this)">
                <i class="fas fa-eye"></i>
              </button>
            </div>
          </div>
          <div class="flex items-center justify-between">
            <label class="flex items-center space-x-2 cursor-pointer select-none">
              <input type="checkbox" class="w-4 h-4 bg-theme border-theme rounded text-purple-500 focus:ring-0 focus:ring-offset-0">
              <span class="text-sm text-theme-secondary">记住我</span>
            </label>
            <button type="button" class="text-sm text-purple-500 hover:text-purple-400 transition-colors">忘记密码？</button>
          </div>
          <button type="button" onclick="handleLogin()" class="w-full py-2.5 bg-purple-500 hover:bg-purple-600 text-white text-sm font-medium rounded-md transition-colors">
            登录
          </button>
          <div class="text-center">
            <span class="text-sm text-theme-secondary">还没有账号？</span>
            <button type="button" onclick="openRegisterModal()" class="text-sm text-purple-500 hover:text-purple-400 transition-colors ml-1">立即注册</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- 注册弹框 -->
  <div id="registerModal" class="fixed inset-0 bg-black bg-opacity-50 hidden items-center justify-center z-50">
    <div class="bg-card-theme rounded-lg w-[480px] relative">
      <div class="p-8">
        <!-- 标题和关闭按钮 -->
        <div class="flex justify-between items-center mb-8">
          <h2 class="text-xl font-medium text-theme">注册账号</h2>
          <button onclick="closeRegisterModal()" class="text-theme-secondary hover:text-theme transition-colors">
            <i class="fas fa-times"></i>
          </button>
        </div>

        <!-- 注册表单 -->
        <form id="registerForm" class="space-y-6">
          <div>
            <label class="block text-sm text-theme-secondary mb-2">用户名</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <i class="fas fa-user text-theme-secondary"></i>
              </div>
              <input type="text" class="w-full pl-10 pr-4 py-2.5 bg-theme border border-theme rounded-md text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入用户名">
            </div>
          </div>
          <div>
            <label class="block text-sm text-theme-secondary mb-2">邮箱</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <i class="fas fa-envelope text-theme-secondary"></i>
              </div>
              <input type="email" class="w-full pl-10 pr-4 py-2.5 bg-theme border border-theme rounded-md text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入邮箱">
            </div>
          </div>
          <div>
            <label class="block text-sm text-theme-secondary mb-2">密码</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <i class="fas fa-lock text-theme-secondary"></i>
              </div>
              <input type="password" class="w-full pl-10 pr-4 py-2.5 bg-theme border border-theme rounded-md text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入密码">
              <button type="button" class="absolute inset-y-0 right-0 pr-3 flex items-center text-theme-secondary hover:text-theme transition-colors" onclick="togglePassword(this)">
                <i class="fas fa-eye"></i>
              </button>
            </div>
          </div>
          <div>
            <label class="block text-sm text-theme-secondary mb-2">确认密码</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <i class="fas fa-lock text-theme-secondary"></i>
              </div>
              <input type="password" class="w-full pl-10 pr-4 py-2.5 bg-theme border border-theme rounded-md text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请再次输入密码">
              <button type="button" class="absolute inset-y-0 right-0 pr-3 flex items-center text-theme-secondary hover:text-theme transition-colors" onclick="togglePassword(this)">
                <i class="fas fa-eye"></i>
              </button>
            </div>
          </div>
          <div class="flex items-start space-x-2">
            <input type="checkbox" class="mt-1 w-4 h-4 bg-theme border-theme rounded text-purple-500 focus:ring-0 focus:ring-offset-0">
            <span class="text-sm text-theme-secondary">我已阅读并同意 <a href="#" class="text-purple-500 hover:text-purple-400">服务条款</a> 和 <a href="#" class="text-purple-500 hover:text-purple-400">隐私政策</a></span>
          </div>
          <button type="button" onclick="handleRegister()" class="w-full py-2.5 bg-purple-500 hover:bg-purple-600 text-white text-sm font-medium rounded-md transition-colors">
            注册
          </button>
          <div class="text-center">
            <span class="text-sm text-theme-secondary">已有账号？</span>
            <button type="button" onclick="switchToLogin()" class="text-sm text-purple-500 hover:text-purple-400 transition-colors ml-1">立即登录</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- 分类管理弹框 -->
  <div id="categoryModal" class="fixed inset-0 bg-black bg-opacity-50 hidden items-center justify-center z-50">
    <div class="bg-card-theme rounded-lg w-[480px] relative">
      <div class="p-4 border-b border-theme flex justify-between items-center">
        <h2 class="text-base font-medium text-theme">分类管理</h2>
        <button onclick="closeCategoryModal()" class="text-theme-secondary hover:text-theme transition-colors">
          <i class="fas fa-times"></i>
        </button>
      </div>
      <div class="p-6">
        <div class="flex items-center space-x-2 mb-4">
          <input type="text" id="newCategory" class="flex-1 px-3 py-2 bg-theme border border-theme rounded text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入分类名称">
          <button onclick="addCategory()" class="px-4 py-2 bg-blue-500 hover:bg-blue-600 text-white text-sm rounded transition-colors">添加</button>
        </div>
        <div class="space-y-2" id="categoryList">
          <!-- 分类列表将通过 JavaScript 动态生成 -->
        </div>
      </div>
    </div>
  </div>

  <!-- 分类编辑弹框 -->
  <div id="editCategoryModal" class="fixed inset-0 bg-black bg-opacity-50 hidden items-center justify-center z-50">
    <div class="bg-card-theme rounded-lg w-[400px] relative">
      <div class="p-4 border-b border-theme flex justify-between items-center">
        <h2 class="text-base font-medium text-theme">编辑分类</h2>
        <button onclick="closeEditCategoryModal()" class="text-theme-secondary hover:text-theme transition-colors">
          <i class="fas fa-times"></i>
        </button>
      </div>
      <form id="editCategoryForm" class="p-6">
        <div class="space-y-4">
          <div>
            <label class="block text-sm text-theme mb-1">分类名称</label>
            <input type="text" id="editCategoryName" class="w-full px-3 py-2 bg-theme border border-theme rounded text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入分类名称">
            <input type="hidden" id="oldCategoryName">
          </div>
        </div>
      </form>
      <div class="p-4 border-t border-theme flex justify-end space-x-2">
        <button type="button" onclick="closeEditCategoryModal()" class="px-6 py-2 text-sm bg-theme hover:bg-opacity-80 text-theme-secondary rounded transition-colors">取消</button>
        <button type="button" onclick="updateCategory()" class="px-6 py-2 bg-blue-500 hover:bg-blue-600 text-white text-sm rounded transition-colors">保存</button>
      </div>
    </div>
  </div>

  <!-- 分类删除确认弹框 -->
  <div id="deleteCategoryModal" class="fixed inset-0 bg-black bg-opacity-50 hidden items-center justify-center z-50">
    <div class="bg-card-theme rounded-lg w-[400px] relative">
      <div class="p-4 border-b border-theme flex justify-between items-center">
        <h2 class="text-base font-medium text-theme">删除分类</h2>
        <button onclick="closeDeleteCategoryModal()" class="text-theme-secondary hover:text-theme transition-colors">
          <i class="fas fa-times"></i>
        </button>
      </div>
      <div class="p-6">
        <div class="flex items-center space-x-3 text-yellow-500 mb-4">
          <i class="fas fa-exclamation-triangle text-xl"></i>
          <p class="text-sm text-theme">确定要删除该分类吗？删除后无法恢复。</p>
        </div>
        <div class="bg-theme rounded p-4">
          <p class="text-sm text-theme-secondary">分类名称：<span id="deleteCategoryName" class="text-theme"></span></p>
        </div>
      </div>
      <div class="p-4 border-t border-theme flex justify-end space-x-2">
        <button type="button" onclick="closeDeleteCategoryModal()" class="px-6 py-2 text-sm bg-theme hover:bg-opacity-80 text-theme-secondary rounded transition-colors">取消</button>
        <button type="button" onclick="confirmDeleteCategory()" class="px-6 py-2 bg-red-500 hover:bg-red-600 text-white text-sm rounded transition-colors">删除</button>
      </div>
    </div>
  </div>

  <!-- 悬浮按钮 -->
  <div class="fixed right-6 bottom-6 flex flex-col space-y-3">
    <button onclick="openEditModal(null)" class="w-12 h-12 bg-blue-500 hover:bg-blue-600 text-white rounded-full shadow-lg flex items-center justify-center transition-colors" title="添加书签">
      <i class="fas fa-plus"></i>
    </button>
    <button onclick="openCategoryModal()" class="w-12 h-12 bg-purple-500 hover:bg-purple-600 text-white rounded-full shadow-lg flex items-center justify-center transition-colors" title="分类管理">
      <i class="fas fa-tags"></i>
    </button>
  </div>

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script>
    $(document).ready(function() {
      // 主题切换功能
      const htmlElement = document.documentElement;
      const themeToggle = $('#themeToggle');
      
      // 检查本地存储中的主题设置
      const savedTheme = localStorage.getItem('theme');
      if (savedTheme) {
        htmlElement.classList.remove('dark', 'light');
        htmlElement.classList.add(savedTheme);
      }

      // 切换主题
      themeToggle.on('click', function() {
        const isDark = htmlElement.classList.contains('dark');
        htmlElement.classList.remove('dark', 'light');
        const newTheme = isDark ? 'light' : 'dark';
        htmlElement.classList.add(newTheme);
        localStorage.setItem('theme', newTheme);
      });

      // 搜索功能
      $('#searchInput').on('input', function() {
        const searchText = $(this).val().toLowerCase();
        $('.tool-card').each(function() {
          const title = $(this).find('h3').text().toLowerCase();
          const description = $(this).find('p').text().toLowerCase();
          if (title.includes(searchText) || description.includes(searchText)) {
            $(this).show();
          } else {
            $(this).hide();
          }
        });
      });

      // 收藏功能
      $('.fa-star').on('click', function() {
        $(this).toggleClass('far fas text-purple-500 text-theme-secondary');
      });

      // 分类筛选
      $('nav button').on('click', function() {
        $('nav button').removeClass('bg-purple-500 text-white').addClass('text-theme-secondary');
        $(this).removeClass('text-theme-secondary').addClass('bg-purple-500 text-white');
      });

      // 监听图标选择变化
      $('#editIcon').on('change', function() {
        const iconClass = $(this).val();
        <#noparse>$('#selectedIcon').attr('class', `fas {{= iconClass}} text-purple-500`);</#noparse>
      });

      // 监听颜色选择变化
      $('#editColor').on('change', function() {
        const colorClass = $(this).val();
        const color = colorClass.replace('text-', 'bg-');
        $('#selectedColor').attr('class', `w-3 h-3 {{= color}} absolute left-2.5 top-1/2 -translate-y-1/2 rounded-full`);
        $('#selectedIcon').removeClass('text-purple-500 text-blue-500 text-green-500 text-red-500 text-yellow-500').addClass(colorClass);
      });

      // 修改登录按钮点击事件绑定
      $('button:contains("登录")').on('click', function(e) {
        e.preventDefault();
        openLoginModal();
      });

      // 确保所有关闭按钮都能正常工作
      $('.text-theme-secondary i.fa-times').parent().on('click', function() {
        closeLoginModal();
        closeRegisterModal();
      });

      // 点击模态框背景关闭
      $('#loginModal, #registerModal').on('click', function(e) {
        if (e.target === this) {
          closeLoginModal();
          closeRegisterModal();
        }
      });

      // 添加 ESC 键关闭功能
      $(document).on('keydown', function(e) {
        if (e.key === 'Escape') {
          closeLoginModal();
          closeRegisterModal();
        }
      });

      // 登录/注册相关函数
      function openLoginModal() {
        closeRegisterModal();
        $('#loginModal').removeClass('hidden').addClass('flex');
      }

      function closeLoginModal() {
        $('#loginModal').removeClass('flex').addClass('hidden');
        $('#loginForm')[0].reset();
      }

      function togglePassword(button) {
        const input = $(button).siblings('input');
        const icon = $(button).find('i');
        
        if (input.attr('type') === 'password') {
          input.attr('type', 'text');
          icon.removeClass('fa-eye').addClass('fa-eye-slash');
        } else {
          input.attr('type', 'password');
          icon.removeClass('fa-eye-slash').addClass('fa-eye');
        }
      }

      function handleLogin() {
        const username = $('#loginForm input[type="text"]').val();
        const password = $('#loginForm input[type="password"]').val();
        const remember = $('#loginForm input[type="checkbox"]').is(':checked');
        
        if (!username || !password) {
          alert('请填写完整的登录信息');
          return;
        }
        
        // 这里添加登录的后端交互逻辑
        console.log('登录信息：', { username, password, remember });
        
        closeLoginModal();
      }

      function openRegisterModal() {
        closeLoginModal();
        $('#registerModal').removeClass('hidden').addClass('flex');
      }

      function closeRegisterModal() {
        $('#registerModal').removeClass('flex').addClass('hidden');
        $('#registerForm')[0].reset();
      }

      function switchToLogin() {
        closeRegisterModal();
        openLoginModal();
      }

      function handleRegister() {
        const username = $('#registerForm input[type="text"]').val();
        const email = $('#registerForm input[type="email"]').val();
        const password = $('#registerForm input[type="password"]').eq(0).val();
        const confirmPassword = $('#registerForm input[type="password"]').eq(1).val();
        const agreement = $('#registerForm input[type="checkbox"]').is(':checked');
        
        if (!username || !email || !password || !confirmPassword) {
          alert('请填写完整的注册信息');
          return;
        }
        
        if (password !== confirmPassword) {
          alert('两次输入的密码不一致');
          return;
        }
        
        if (!agreement) {
          alert('请阅读并同意服务条款和隐私政策');
          return;
        }
        
        // 这里添加注册的后端交互逻辑
        console.log('注册信息：', { username, email, password });
        
        closeRegisterModal();
        openLoginModal(); // 注册成功后跳转到登录
      }
    });

    // 当前编辑的卡片元素
    let currentCard = null;

    // 打开编辑弹框
    function openEditModal(button) {
      currentCard = button ? $(button).closest('.bg-card-theme') : null;
      
      // 重置表单
      $('#editTitle').val('');
      $('#editDescription').val('');
      $('#editIcon').val('');
      $('#editUrl').val('');
      $('#editCategory').val('');
      $('#previewIcon').hide();
      
      if (currentCard) {
        const title = currentCard.find('h3').text();
        const description = currentCard.find('p').text();
        const icon = currentCard.find('img').attr('src') || '';
        const url = currentCard.data('url') || '';
        const category = currentCard.data('category') || '';
        
        $('#editTitle').val(title);
        $('#editDescription').val(description);
        $('#editIcon').val(icon);
        $('#editUrl').val(url);
        $('#editCategory').val(category);
        
        if (icon) {
          $('#previewIcon').attr('src', icon).show();
        }
      }
      
      $('#editModal').removeClass('hidden').addClass('flex');
    }

    // 关闭编辑弹框
    function closeEditModal() {
      $('#editModal').removeClass('flex').addClass('hidden');
      currentCard = null;
    }

    // 监听图标地址变化
    $('#editIcon').on('input', function() {
      const iconUrl = $(this).val();
      if (iconUrl) {
        $('#previewIcon').attr('src', iconUrl).show();
      } else {
        $('#previewIcon').hide();
      }
    });

    // 保存编辑
    function saveEdit() {
      if (!currentCard) return;

      const title = $('#editTitle').val().trim();
      const description = $('#editDescription').val().trim();
      const icon = $('#editIcon').val().trim();
      const url = $('#editUrl').val().trim();
      const category = $('#editCategory').val();

      if (!title) {
        alert('请输入书签名称');
        return;
      }

      if (!url) {
        alert('请输入链接地址');
        return;
      }

      // 更新卡片内容
      currentCard.find('h3').text(title);
      currentCard.find('p').text(description);
      if (icon) {
        currentCard.find('.w-8.h-8').html(`<img src="{{= icon}}" alt="" class="w-4 h-4">`);
      }
      currentCard.data('url', url);
      currentCard.data('category', category);

      closeEditModal();
    }

    // 打开删除确认弹框
    function openDeleteModal(button) {
      currentCard = $(button).closest('.bg-card-theme');
      $('#deleteModal').removeClass('hidden').addClass('flex');
    }

    // 关闭删除确认弹框
    function closeDeleteModal() {
      $('#deleteModal').removeClass('flex').addClass('hidden');
      currentCard = null;
    }

    // 确认删除
    function confirmDelete() {
      if (!currentCard) return;
      
      currentCard.fadeOut(300, function() {
        $(this).remove();
      });
      
      closeDeleteModal();
    }

    // 分类管理相关函数
    let currentCategory = null;

    function editCategory(category) {
      currentCategory = category;
      $('#oldCategoryName').val(category);
      $('#editCategoryName').val(category);
      $('#editCategoryModal').removeClass('hidden').addClass('flex');
    }

    function closeEditCategoryModal() {
      $('#editCategoryModal').removeClass('flex').addClass('hidden');
      currentCategory = null;
    }

    function updateCategory() {
      const newName = $('#editCategoryName').val().trim();
      const oldName = $('#oldCategoryName').val();
      
      if (!newName) {
        alert('请输入分类名称');
        return;
      }

      if (newName === oldName) {
        closeEditCategoryModal();
        return;
      }

      // 这里应该添加与后端的交互逻辑
      alert('修改分类：' + oldName + ' -> ' + newName);
      
      closeEditCategoryModal();
      loadCategories(); // 重新加载分类列表
    }

    function deleteCategory(category) {
      currentCategory = category;
      $('#deleteCategoryName').text(category);
      $('#deleteCategoryModal').removeClass('hidden').addClass('flex');
    }

    function closeDeleteCategoryModal() {
      $('#deleteCategoryModal').removeClass('flex').addClass('hidden');
      currentCategory = null;
    }

    function confirmDeleteCategory() {
      if (!currentCategory) return;

      // 这里应该添加与后端的交互逻辑
      alert('删除分类：' + currentCategory);
      
      closeDeleteCategoryModal();
      loadCategories(); // 重新加载分类列表
    }

    // 修改 loadCategories 函数中的分类项模板
    function loadCategories() {
      const categories = [
        '常用工具', 'JSON工具', '编码加密', '网络工具', 
        '时间日期', '代码工具', '文本处理', '图像工具'
      ];
      
      const categoryList = $('#categoryList');
      categoryList.empty();
      
      categories.forEach(category => {
        categoryList.append(`
          <div class="flex items-center justify-between p-3 bg-theme rounded group">
            <span class="text-sm text-theme">{{= category}}</span>
            <div class="flex items-center space-x-2 opacity-0 group-hover:opacity-100 transition-opacity">
              <button class="text-theme-secondary hover:text-blue-500 transition-colors p-1.5 hover:bg-black hover:bg-opacity-10 rounded" onclick="editCategory('{{= category}}')" title="编辑分类">
                <i class="fas fa-edit"></i>
              </button>
              <button class="text-theme-secondary hover:text-red-500 transition-colors p-1.5 hover:bg-black hover:bg-opacity-10 rounded" onclick="deleteCategory('{{= category}}')" title="删除分类">
                <i class="fas fa-trash-alt"></i>
              </button>
            </div>
          </div>
        `);
      });
    }

    function addCategory() {
      const newCategory = $('#newCategory').val().trim();
      if (!newCategory) {
        alert('请输入分类名称');
        return;
      }
      
      // 这里应该添加与后端的交互逻辑
      alert('添加分类：' + newCategory);
      $('#newCategory').val('');
      loadCategories();
    }

    function openCategoryModal() {
      loadCategories();
      $('#categoryModal').removeClass('hidden').addClass('flex');
    }

    function closeCategoryModal() {
      $('#categoryModal').removeClass('flex').addClass('hidden');
    }
  </script>
</body>
</html> 