<!DOCTYPE html>
<html lang="zh-CN" class="dark">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>LinkHub导航系统</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <#--  消息弹框-->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
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

    .hidden { display: none !important; }

    /* 移动端导航菜单 */
    @media (max-width: 768px) {
      .mobile-menu {
        transform: translateX(-100%);
        transition: transform 0.3s ease-in-out;
      }
      .mobile-menu.active {
        transform: translateX(0);
      }
    }
  </style>
</head>
<body class="bg-theme text-theme min-h-screen transition-colors duration-200">
  <div class="min-h-screen flex flex-col">
    <!-- 顶部导航栏 -->
    <header class="bg-theme border-b border-theme">
      <div class="container mx-auto px-4 sm:px-6 py-3">
        <div class="flex items-center justify-between">
          <!-- 移动端菜单按钮 -->
          <button id="mobileMenuBtn" class="lg:hidden text-theme-secondary hover:text-purple-500 transition-colors">
            <i class="fas fa-bars text-xl"></i>
          </button>
          
          <div class="flex items-center space-x-3">
            <h1 class="text-xl font-bold text-purple-500">LinkHub</h1>
            <span class="text-theme-secondary hidden sm:inline">|</span>
            <span class="text-theme-secondary text-sm hidden sm:inline">高效导航集成平台</span>
          </div>
          
          <div class="flex items-center space-x-4">
            <!-- 搜索框 - 在移动端隐藏 -->
            <div class="relative hidden md:block">
              <input type="text" id="searchInput" placeholder="搜索链接..."
                class="w-80 px-4 py-1.5 pl-10 bg-card-theme rounded text-theme border border-theme focus:outline-none focus:border-purple-500 transition-colors duration-200">
              <i id="searchbtn" class="fas fa-search absolute right-3 top-1/2 transform -translate-y-1/2 text-theme-secondary"></i>
            </div>
            
            <div class="flex items-center space-x-4">
              <button id="themeToggle" class="text-theme-secondary hover:text-purple-500 transition-colors" title="切换主题">
                <i class="fas fa-moon"></i>
                <i class="fas fa-sun"></i>
              </button>
              <div class="h-4 w-px bg-theme-secondary opacity-20 hidden sm:block"></div>
              <button class="px-4 py-1.5 bg-purple-500 hover:bg-purple-600 text-white rounded text-sm transition-colors" id="loginBtn">
                登录
              </button>
              <span id="userInfo" class="hidden text-theme-secondary text-sm cursor-pointer select-none">
                <i class="fas fa-user mr-1"></i><span id="usernameDisplay"></span>
                <button id="logoutBtn" onclick="handleLogout()" class="ml-2 text-xs text-purple-500 hover:text-purple-400">退出</button>
              </span>
            </div>
          </div>
        </div>
        
        <!-- 移动端搜索框 -->
        <div class="mt-3 md:hidden">
          <div class="relative">
            <input type="text" id="mobileSearchInput" placeholder="搜索链接..."
              class="w-full px-4 py-2 pl-10 bg-card-theme rounded text-theme border border-theme focus:outline-none focus:border-purple-500 transition-colors duration-200">
            <i class="fas fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-theme-secondary"></i>
          </div>
        </div>
      </div>
    </header>

    <!-- 移动端导航菜单 -->
    <div id="mobileMenu" class="mobile-menu fixed inset-y-0 left-0 w-64 bg-card-theme z-50 md:hidden">
      <div class="p-4 border-b border-theme">
        <div class="flex justify-between items-center">
          <h2 class="text-lg font-medium text-theme">导航菜单</h2>
          <button id="closeMobileMenu" class="text-theme-secondary hover:text-theme transition-colors">
            <i class="fas fa-times"></i>
          </button>
        </div>
      </div>
      <div id="mobileNavList" class="p-4 space-y-2">
        <!-- 移动端分类列表将通过 JavaScript 动态生成 -->
      </div>
    </div>

    <!-- 分类导航 - 在移动端隐藏 -->
    <nav class="bg-theme sticky top-0 z-10 border-b border-theme block">
      <div class="container mx-auto px-6 py-2">
        <div id="navList" class="flex items-center space-x-2 overflow-x-auto">
          <!-- 分类列表将通过 JavaScript 动态生成 -->
        </div>
      </div>
    </nav>

    <!-- 主要内容区 -->
    <main class="flex-1 container mx-auto px-4 sm:px-6 py-6">
      <div id="linkList" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
        <!-- 工具卡片  有js动态生成-->

      </div>
    </main>
  </div>

  <!-- 悬浮按钮 - 在移动端调整位置 -->
  <div id="floatBtn" class="fixed right-4 bottom-4 sm:right-6 sm:bottom-6 flex flex-col space-y-3 hidden">
    <button onclick="openLinkEditModal(null)" class="w-12 h-12 bg-blue-500 hover:bg-blue-600 text-white rounded-full shadow-lg flex items-center justify-center transition-colors" title="添加书签">
      <i class="fas fa-plus"></i>
    </button>
    <button onclick="openSaveCategoryModal()" class="w-12 h-12 bg-purple-500 hover:bg-purple-600 text-white rounded-full shadow-lg flex items-center justify-center transition-colors" title="分类管理">
      <i class="fas fa-tags"></i>
    </button>
  </div>

  <!-- 登录弹框 -->
  <div id="loginModal" class="fixed inset-0 bg-black bg-opacity-50 hidden items-center justify-center z-50">
    <div class="bg-card-theme rounded-lg w-[480px] max-w-[95%] relative">
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
              <input type="tel" value="13345092258" class="w-full pl-10 pr-4 py-2.5 bg-theme border border-theme rounded-md text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入手机号">
            </div>
          </div>
          <div>
            <label class="block text-sm text-theme-secondary mb-2">密码</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <i class="fas fa-lock text-theme-secondary"></i>
              </div>
              <input type="password" value="1" class="w-full pl-10 pr-4 py-2.5 bg-theme border border-theme rounded-md text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入密码">
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
            <button type="button" onclick="openForgotPasswordModal()" class="text-sm text-purple-500 hover:text-purple-400 transition-colors">忘记密码？</button>
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
            <label class="block text-sm text-theme-secondary mb-2">手机号</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <i class="fas fa-envelope text-theme-secondary"></i>
              </div>
              <input type="tel" id="tel" class="w-full pl-10 pr-4 py-2.5 bg-theme border border-theme rounded-md text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入手机号">
            </div>
          </div>
          <div>
            <label class="block text-sm text-theme-secondary mb-2">密码</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <i class="fas fa-lock text-theme-secondary"></i>
              </div>
              <input type="password" id="password" class="w-full pl-10 pr-4 py-2.5 bg-theme border border-theme rounded-md text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入密码">
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
              <input type="password" id="password1" class="w-full pl-10 pr-4 py-2.5 bg-theme border border-theme rounded-md text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请再次输入密码">
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

  <!-- 找回密码弹框 -->
  <div id="forgotPasswordModal" class="fixed inset-0 bg-black bg-opacity-50 hidden items-center justify-center z-50">
    <div class="bg-card-theme rounded-lg w-[400px] relative">
      <div class="p-8">
        <div class="flex justify-between items-center mb-6">
          <h2 class="text-xl font-medium text-theme">找回密码</h2>
          <button onclick="closeForgotPasswordModal()" class="text-theme-secondary hover:text-theme transition-colors">
            <i class="fas fa-times"></i>
          </button>
        </div>
        <form id="forgotPasswordForm" class="space-y-6">
          <div>
            <label class="block text-sm text-theme-secondary mb-2">手机号</label>
            <input type="text" id="forgotPhone" class="w-full px-3 py-2 bg-theme border border-theme rounded text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入手机号">
          </div>
          <div>
            <label class="block text-sm text-theme-secondary mb-2">验证码</label>
            <div class="flex space-x-2">
              <input type="text" id="forgotCode" class="flex-1 px-3 py-2 bg-theme border border-theme rounded text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入验证码">
              <button type="button" onclick="sendForgotCode()" class="px-3 py-2 bg-purple-500 hover:bg-purple-600 text-white text-sm rounded transition-colors">获取验证码</button>
            </div>
          </div>
          <div>
            <label class="block text-sm text-theme-secondary mb-2">新密码</label>
            <input type="password" id="forgotNewPassword" class="w-full px-3 py-2 bg-theme border border-theme rounded text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入新密码">
          </div>
          <button type="button" onclick="handleForgotPassword()" class="w-full py-2.5 bg-purple-500 hover:bg-purple-600 text-white text-sm font-medium rounded-md transition-colors">
            重置密码
          </button>
        </form>
      </div>
    </div>
  </div>


  <!-- 编辑弹框 -->
  <div id="editLinkModal" class="fixed inset-0 bg-black bg-opacity-50 hidden items-center justify-center z-50">
    <div class="bg-card-theme rounded-lg w-[480px] relative">
      <div class="p-4 border-b border-theme flex justify-between items-center">
        <h2 class="text-base font-medium text-theme">编辑书签</h2>
        <button onclick="closeLinkEditModal()" class="text-theme-secondary hover:text-theme transition-colors">
          <i class="fas fa-times"></i>
        </button>
      </div>
      <form id="editForm" class="p-6">
        <div class="space-y-4">
          <input type="hidden" id="editLinkId"></input>
          <div>
            <label class="block text-sm text-theme mb-1">名称</label>
            <input type="text" id="editLinkTitle" class="w-full px-3 py-2 bg-theme border border-theme rounded text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入书签名称">
          </div>
          <div>
            <label class="block text-sm text-theme mb-1">链接</label>
            <input type="url" id="editLinkUrl" class="w-full px-3 py-2 bg-theme border border-theme rounded text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入链接地址">
          </div>
          <div>
            <label class="block text-sm text-theme mb-1">描述</label>
            <textarea id="editLinkDescription" rows="3" class="w-full px-3 py-2 bg-theme border border-theme rounded text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors resize-none" placeholder="请输入书签描述"></textarea>
          </div>
          <div>
            <label class="block text-sm text-theme mb-1">分类</label>
            <select id="editLinkCategoryId" class="w-full px-3 py-2 bg-theme border border-theme rounded text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors appearance-none"></select>
          </div>
          <div>
            <label class="block text-sm text-theme mb-1">图标</label>
            <div class="flex items-center space-x-2">
              <div class="w-8 h-8 bg-theme rounded flex items-center justify-center">
                <img id="previewLinkIcon" src="" alt="" class="w-4 h-4">
              </div>
              <div class="flex-1">
                <input type="text" id="editLinkIcon" class="w-full px-3 py-2 bg-theme border border-theme rounded text-sm text-theme focus:outline-none focus:border-purple-500 transition-colors" placeholder="请输入图标地址">
              </div>
              <button type="button" class="px-4 py-2 bg-theme text-sm text-theme-secondary hover:text-theme rounded border border-theme transition-colors">上传</button>
            </div>
          </div>
        </div>
      </form>
      <div class="p-4 border-t border-theme flex justify-end space-x-2">
        <button type="button" onclick="closeLinkEditModal()" class="px-6 py-2 text-sm bg-theme hover:bg-opacity-80 text-theme-secondary rounded transition-colors">取消</button>
        <button type="button" onclick="saveLink()" class="px-6 py-2 bg-blue-500 hover:bg-blue-600 text-white text-sm rounded transition-colors">保存</button>
      </div>
    </div>
  </div>

  <!-- 删除确认弹框 -->
  <div id="deleteLinkModal" class="fixed inset-0 bg-black bg-opacity-50 hidden items-center justify-center z-50">
    <div class="bg-card-theme rounded-lg p-6 w-[400px] relative">
      <button onclick="closeDeleteLinkModal()" class="absolute right-4 top-4 text-theme-secondary hover:text-purple-500 transition-colors">
        <i class="fas fa-times"></i>
      </button>
      <h2 class="text-lg font-medium text-theme mb-4">确认删除</h2>
      <p class="text-theme-secondary mb-6">确定要删除这个链接吗？此操作无法撤销。</p>
      <span id="deleteLinkId" class="hidden"></span>
      <div class="flex justify-end space-x-3">
        <button onclick="closeDeleteLinkModal()" class="px-4 py-2 text-sm text-theme-secondary hover:text-theme transition-colors">取消</button>
        <button onclick="confirmDeleteLink()" class="px-4 py-2 bg-red-500 hover:bg-red-600 text-white text-sm rounded transition-colors">删除</button>
      </div>
    </div>
  </div>


  <!-- 分类管理弹框 -->
  <div id="categoryModal" class="fixed inset-0 bg-black bg-opacity-50 hidden items-center justify-center z-50">
    <div class="bg-card-theme rounded-lg w-[480px] relative">
      <div class="p-4 border-b border-theme flex justify-between items-center">
        <h2 class="text-base font-medium text-theme">分类管理</h2>
        <button onclick="closeSaveCategoryModal()" class="text-theme-secondary hover:text-theme transition-colors">
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
            <input type="hidden" id="categoryId">
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
          <p class="text-sm text-theme-secondary">分类名称：<span id="deleteCategoryName" class="text-theme"></span>
            <span id="deleteCategoryId" class="hidden"></span>
          </p>
        </div>
      </div>
      <div class="p-4 border-t border-theme flex justify-end space-x-2">
        <button type="button" onclick="closeDeleteCategoryModal()" class="px-6 py-2 text-sm bg-theme hover:bg-opacity-80 text-theme-secondary rounded transition-colors">取消</button>
        <button type="button" onclick="confirmDeleteCategory()" class="px-6 py-2 bg-red-500 hover:bg-red-600 text-white text-sm rounded transition-colors">删除</button>
      </div>
    </div>
  </div>

  <script>
    let BASE_API="http://localhost:8085/api"
    function toggleThemeIcon(theme){
      if (theme === 'dark') {
        $(themeToggle).find('.fa-moon').removeClass('hidden');
        $(themeToggle).find('.fa-sun').addClass('hidden');
      } else {
        $(themeToggle).find('.fa-moon').addClass('hidden');
        $(themeToggle).find('.fa-sun').removeClass('hidden');
      }
    }
    $(document).ready(function() {
      // 主题切换功能
      const htmlElement = document.documentElement;
      const themeToggle = $('#themeToggle');
      
      // 检查本地存储中的主题设置
      const savedTheme = localStorage.getItem('theme');
      if (savedTheme) {
        htmlElement.classList.remove('dark', 'light');
        htmlElement.classList.add(savedTheme);

        //切换主题图标
        toggleThemeIcon(savedTheme)
      }

      // 切换主题
      themeToggle.on('click', function() {
        const isDark = htmlElement.classList.contains('dark');
        htmlElement.classList.remove('dark', 'light');
        const newTheme = isDark ? 'light' : 'dark';
        htmlElement.classList.add(newTheme);
        localStorage.setItem('theme', newTheme);

        //切换主题图标
        toggleThemeIcon(newTheme)
      });

      //搜索
      $('#searchBtn').on('click', handleSearch);
      $('#searchInput').on('keydown', function(e) {
        if (e.key === 'Enter' || e.keyCode === 13) {
          handleSearch();
        }
      });
      function handleSearch() {
        const keyword = $('#searchInput').val().trim();
        if (keyword) {
          loadLinks(null,keyword)
        } else {
          toastr.warning('请输入搜索内容');
        }
      }

      // 收藏功能
      // $('.fa-star').on('click', function() {
      //   $(this).toggleClass('far fas text-purple-500 text-theme-secondary');
      // });

      // 分类点击筛选
      $('nav button').on('click', function() {
        $('nav button').removeClass('bg-purple-500 text-white').addClass('text-theme-secondary');
        $(this).removeClass('text-theme-secondary').addClass('bg-purple-500 text-white');
      });

      // 修改登录按钮点击事件绑定
      $('#loginBtn').on('click', function(e) {
        e.preventDefault();
        openLoginModal();
        console.log("进来了。。。")
      });

      // 确保所有关闭按钮都能正常工作
      $('.text-theme-secondary i.fa-times').parent().on('click', function() {
        closeLoginModal();
        closeRegisterModal();
      });

      // 点击模态框背景关闭
      $('#loginModal').on('click', function(e) {
        if (e.target === this) {
          closeLoginModal();
        }
      });

      // 添加移动端菜单控制
      $('#mobileMenuBtn').on('click', function() {
        $('#mobileMenu').addClass('active');
      });

      $('#closeMobileMenu').on('click', function() {
        $('#mobileMenu').removeClass('active');
      });

      // 移动端搜索
      $('#mobileSearchInput').on('keydown', function(e) {
        if (e.key === 'Enter' || e.keyCode === 13) {
          const keyword = $(this).val().trim();
          if (keyword) {
            loadLinks(null, keyword);
            $('#mobileMenu').removeClass('active');
          } else {
            toastr.warning('请输入搜索内容');
          }
        }
      });

      // 点击移动端菜单外部关闭菜单
      $(document).on('click', function(e) {
        if (!$(e.target).closest('#mobileMenu, #mobileMenuBtn').length) {
          $('#mobileMenu').removeClass('active');
        }
      });
    });

    // 添加 ESC 键关闭功能
    $(document).on('keydown', function(e) {
      if (e.key === 'Escape') {
        closeLoginModal();
        closeRegisterModal();
      }
    });


    initOp()
    //页面加载初始化操作
    function initOp(){
      let userId=localStorage.getItem("userId")
      if(""!=userId&&undefined!=userId){
        if(isLogin()){
          // 登录成功后隐藏登录按钮，显示用户名
          $('#loginBtn').hide();
          $('#userInfo').removeClass('hidden').show();
          $('#usernameDisplay').text(localStorage.getItem("username"));
          //显示悬浮菜单
          $('#floatBtn').removeClass('hidden').show()
          loadCategories()
        }
      }
    }
    //页面初次加载操作
    function isLogin(){
      console.log(localStorage.getItem("tenantId"))
      let result=false;
      $.ajax({
        url:BASE_API+"/user/isLogin",
        method:"get",
        dataType:"json",
        async: false,
        headers:{'TENANT-ID':localStorage.getItem("tenantId")},
        success:function(res){
          if(res.code==0){
            result= res.data;
          }
        }
      })
      return result;
    }

    // 登录/注册相关函数
    function openLoginModal() {
      closeRegisterModal();
      $('#loginModal').removeClass('hidden').addClass('flex');
      // 防止背景滚动
      document.body.style.overflow = 'hidden';
    }

    function closeLoginModal() {
      $('#loginModal').removeClass('flex').addClass('hidden');
      $('#loginForm')[0].reset();
      // 恢复背景滚动
      document.body.style.overflow = '';
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
      const tel = $('#loginForm input[type="tel"]').val();
      const password = $('#loginForm input[type="password"]').val();
      const remember = $('#loginForm input[type="checkbox"]').is(':checked');

      if (!tel || !password) {
        alert('请填写完整的登录信息');
        return;
      }

      // 这里添加登录的后端交互逻辑
      // console.log('登录信息：', { tel, password, remember });
      $.ajax({
        url:BASE_API+"/user/login",
        method:"post",
        dataType:"json",
        headers:{'TENANT-ID':localStorage.getItem("tenantId")},
        data:{tel:tel,password:password},
        success:function(res){
          if(res.code==0){
            //登录成功
            toastr.success('登录成功')

            //用户名 和租户存在本地存储
            localStorage.setItem("userId",res.data.userId)
            localStorage.setItem("username",res.data.username)
            localStorage.setItem("tel",res.data.tel)
            localStorage.setItem("tenantId",res.data.tenantId)
            localStorage.setItem("token",res.data.token)

            // 登录成功后隐藏登录按钮，显示用户名
            $('#loginBtn').hide();
            $('#userInfo').removeClass('hidden').show();
            $('#usernameDisplay').text(localStorage.getItem("username"));
            //显示悬浮菜单
            $('#floatBtn').removeClass('hidden').show()

            //加载目录和书签
            loadCategories()

            closeLoginModal();
          }else{
            alert(res.msg)
          }
        }

      })
    }

    function handleLogout(){
      $.ajax({
        url:BASE_API+"/user/logout",
        method:"post",
        dataType:"json",
        headers:{'TENANT-ID':localStorage.getItem("tenantId")},
        data:{userId:localStorage.getItem("userId")},
        success:function(res){
          if(res.code==0){
            toastr.success('退出成功')
            //清空本地存储
            localStorage.removeItem("userId")
            localStorage.removeItem("username")
            localStorage.removeItem("tel")
            localStorage.removeItem("tenantId")
            localStorage.removeItem("token")

            // 退出登录逻辑
            $('#userInfo').hide();
            $('#loginBtn').show();
            $('#usernameDisplay').text('');

            window.location="/"


          }else{
            toastr.error(res.msg)
          }
        }
      })
    }

    function openRegisterModal() {
      closeLoginModal();
      $('#registerModal').removeClass('hidden').addClass('flex');
    }

    function closeRegisterModal() {
      $('#registerModal').removeClass('flex').addClass('hidden');
      $('#registerForm')[0].reset();
    }

    function handleRegister() {
      const tel = $('#registerForm input[type="tel"]').val();
      const password = $('#registerForm input[type="password"]').eq(0).val();
      const confirmPassword = $('#registerForm input[type="password"]').eq(1).val();
      const agreement = $('#registerForm input[type="checkbox"]').is(':checked');

      if (!tel || !password || !confirmPassword) {
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
      // console.log('注册信息：', { tel, password });
      $.ajax({
        url:BASE_API+"/user/register",
        method:"post",
        dataType:"json",//服务器返回结果
        headers:{'TENANT-ID':localStorage.getItem("tenantId")},
        data:{tel:tel,password:password},
        success:function (res) {
          if(res.code==0){
            //注册成功
            toastr.success("注册成功")
            closeRegisterModal();
            openLoginModal(); // 注册成功后跳转到登录
          }else{
            //注册失败
            toastr.error(res.msg)
          }
        }
      })
    }

    function switchToLogin() {
      closeRegisterModal();
      openLoginModal();
    }

    function openForgotPasswordModal() {
      closeLoginModal();
      $('#forgotPasswordModal').removeClass('hidden').addClass('flex');
    }

    function closeForgotPasswordModal() {
      $('#forgotPasswordModal').removeClass('flex').addClass('hidden');
      $('#forgotPasswordForm')[0].reset();
    }

    function sendForgotCode() {
      const phone = $('#forgotPhone').val().trim();
      if (!phone) {
        alert('请输入手机号');
        return;
      }
      // 这里添加发送验证码的后端交互逻辑
      alert('验证码已发送到 ' + phone);
    }

    function handleForgotPassword() {
      const phone = $('#forgotPhone').val().trim();
      const code = $('#forgotCode').val().trim();
      const newPassword = $('#forgotNewPassword').val().trim();
      if (!phone || !code || !newPassword) {
        alert('请填写完整信息');
        return;
      }
      // 这里添加重置密码的后端交互逻辑
      alert('密码已重置，请使用新密码登录');
      closeForgotPasswordModal();
      openLoginModal();
    }



    // 打开编辑弹框
    function openLinkEditModal(linkId) {

      // 重置表单
      $('#editLinkId').val('');
      $('#editLinkTitle').val('');
      $('#editLinkDescription').val('');
      $('#editLinkIcon').val('');
      $('#editLinkUrl').val('');
      $('#editLinkCategoryId').val('');
      $('#previewLinkIcon').hide();
      
      if (linkId) {
        //编辑
        $.ajax({
          url:BASE_API+"/link/getById",
          method:"get",
          dataType:"json",
          headers:{'TENANT-ID':localStorage.getItem("tenantId")},
          data:{linkId:linkId},
          success:function(res){
            if(res.code==0){
              $('#editLinkId').val(res.data.linkId);
              $('#editLinkTitle').val(res.data.title);
              $('#editLinkUrl').val(res.data.url);
              $('#editLinkDescription').val(res.data.description);
              $('#editLinkCategoryId').val(res.data.categoryId);
              $('#editLinkIcon').val(res.data.icon);
              if (res.data.icon) {
                $('#previewLinkIcon').attr('src', res.data.icon).show();
              }
            }else{
              toastr.error(res.msg)
            }
          }
        })
      }
      //新增
      $('#editLinkModal').removeClass('hidden').addClass('flex');
    }

    // 关闭编辑弹框
    function closeLinkEditModal() {
      $('#editLinkModal').removeClass('flex').addClass('hidden');
    }

    //监听url变化
    $('#editLinkUrl').on('blur', function() {
      const editLinkUrl = $(this).val();
      if (editLinkUrl) {
        const iconUrl=getFaviconWithSize(editUrl)
        $('#editIcon').val(iconUrl);

        //预览图标
        previewIcon()
      }
    });
    // 监听图标地址变化
    // $('#editIcon').on('input', function() {
    //   console.log("icon change...")
    //   const iconUrl = $(this).val();
    //   if (iconUrl) {
    //     $('#previewIcon').attr('src', iconUrl).show();
    //   } else {
    //     $('#previewIcon').hide();
    //   }
    // });
    function previewIcon(){
      const iconUrl = $('#editLinkIcon').val();
      if (iconUrl) {
        $('#previewIcon').attr('src', iconUrl).show();
      } else {
        $('#previewIcon').hide();
      }
    }
    //根据域名获取icon图标地址
    function getFaviconWithSize(url) {
      let domain=url;
      if (url.startsWith('http://') || url.startsWith('https://')) {
        domain = new URL(url).hostname;
      }
      return `https://ico.ihuan.me/${r'${domain}'}`;
    }



    // 链接相关js
    // 保存编辑
    function saveLink() {
      const linkId = $('#editLinkId').val().trim();
      const title = $('#editLinkTitle').val().trim();
      const url = $('#editLinkUrl').val().trim();
      const description = $('#editLinkDescription').val().trim();
      const categoryId = $('#editLinkCategoryId').val();
      const icon = $('#editLinkIcon').val().trim();

      if (!title) {
        alert('请输入书签名称');
        return;
      }

      if (!url) {
        alert('请输入链接地址');
        return;
      }
      if (!categoryId) {
        alert('请选择所属分类');
        return;
      }
      $.ajax({
        url:BASE_API+"/link",
        method:"post",
        dataType:"json",
        headers:{'TENANT-ID':localStorage.getItem("tenantId")},
        data:{linkId:linkId,title:title,url:url,description:description,icon:icon,categoryId:categoryId},
        success:function(res){
          if(res.code==0){
            toastr.success("保存成功")
            loadCategories()
          }else{
            toastr.error(res.msg)
          }
        }
      })

      closeLinkEditModal();
    }

    // 打开删除确认弹框
    function openDeleteLinkModal(linkId) {
      $("#deleteLinkId").text(linkId)
      $('#deleteLinkModal').removeClass('hidden').addClass('flex');
    }

    // 关闭删除确认弹框
    function closeDeleteLinkModal() {
      $("#deleteLinkId").empty()
      $('#deleteLinkModal').removeClass('flex').addClass('hidden');
    }

    // 确认删除
    function confirmDeleteLink() {

      let deleteLinkId= $("#deleteLinkId").text()
      $.ajax({
        url:BASE_API+"/link",
        method:"delete",
        dataType:"json",
        headers:{'TENANT-ID':localStorage.getItem("tenantId")},
        data:{linkId:deleteLinkId},
        success:function(res){
          if(res.code==0){
            toastr.success("删除成功")
            loadCategories()

            closeDeleteLinkModal();
          }else{
            toastr.error(res.msg)
          }
        }
      })
    }
    //加载书签数据
    function loadLinks(categoryId,linkTitle=''){
      $.ajax({
        url:BASE_API+"/link/list",
        method:"get",
        dataType:"json",
        headers:{'TENANT-ID':localStorage.getItem("tenantId")},
        data:{categoryId:categoryId,linkTitle:linkTitle},
        success:function(res){
          const linkList=$("#linkList")
          linkList.empty()

          if(res.code==0){
            res.data.forEach(link=>{
              linkList.append(`
                  <div   class="bg-card-theme rounded-lg p-3 hover:ring-1 hover:ring-purple-500 transition-all duration-200 card-shadow">
                   <div class="flex items-start justify-between mb-2">
                       <div class="flex items-center space-x-2">
                         <div class="w-8 h-8 bg-theme rounded flex items-center justify-center">
                          <i class="fas fa-code text-base text-purple-500"></i>
                         </div>
                         <a href="javascript:void(0)"  onclick="openLink('${r'${link.url}'}')"><h3  class="text-sm font-medium text-theme hover:cursor-pointer">${r'${link.title}'}</h3></a>
                       </div>
                       <div class="flex items-center space-x-1">
                        <button class="text-theme-secondary hover:text-purple-500 transition-colors p-1.5 rounded-full hover:bg-theme" onclick="openLinkEditModal('${r'${link.linkId}'}')" title="编辑">
                          <i class="fas fa-edit text-sm"></i>
                        </button>
                        <button class="text-theme-secondary hover:text-red-500 transition-colors p-1.5 rounded-full hover:bg-theme" onclick="openDeleteLinkModal('${r'${link.linkId}'}')" title="删除">
                          <i class="fas fa-trash-alt text-sm"></i>
                        </button>
                       </div>
                   </div>
                   <p class="text-xs text-theme-secondary line-clamp-2">${r'${link.description}'}</p>
                 </div>

              `)
            })
          }else{
            toastr.error(res.msg)
          }
        }
      })
    }

    //书签点击打开链接
    function openLink(linkUrl){
      if(!linkUrl.startsWith("http")||linkUrl.startsWith("https")){
        linkUrl="http://"+linkUrl;
      }
      window.open(linkUrl,"_blank")
    }


    // 分类管理相关函数


    function openSaveCategoryModal() {
      loadCategories();
      $('#categoryModal').removeClass('hidden').addClass('flex');
    }

    function closeSaveCategoryModal() {
      $('#categoryModal').removeClass('flex').addClass('hidden');
    }
    function addCategory() {
      const newCategory = $('#newCategory').val().trim();
      if (!newCategory) {
        alert('请输入分类名称');
        return;
      }

      // 这里后端的交互逻辑
      $.ajax({
        url:BASE_API+"/category",
        method:"post",
        dataType:"json",
        headers:{'TENANT-ID':localStorage.getItem("tenantId")},
        data:{name:newCategory},
        success:function(res){
          if(res.code==0){
            toastr.success("添加成功")

            $('#newCategory').val('');
            loadCategories();
          }else{
            toastr.error(res.msg)
          }
        }
      })

    }



    function openEditCategoryModal(categoryId,categoryName) {
      $('#categoryId').val(categoryId);
      $('#editCategoryName').val(categoryName);
      $('#editCategoryModal').removeClass('hidden').addClass('flex');
    }

    function closeEditCategoryModal() {
      $('#editCategoryModal').removeClass('flex').addClass('hidden');
    }

    function updateCategory() {
      const newName = $('#editCategoryName').val().trim();
      const categoryId = $('#categoryId').val();
      
      if (!newName) {
        alert('请输入分类名称');
        return;
      }

      // 这里应该添加与后端的交互逻辑
      $.ajax({
        url:BASE_API+"/category",
        method:"put",
        dataType:'json',
        headers:{'TENANT-ID':localStorage.getItem("tenantId")},
        data:{categoryId:categoryId,name:newName},
        success:function(res){
          if(res.code==0){
            toastr.success("修改成功")

            closeEditCategoryModal();
            loadCategories(); // 重新加载分类列表
          }else{
            toastr.error(res.msg)
          }
        }
      })
      

    }

    function openDeleteCategoryModal(categoryId,categoryName) {
      $('#deleteCategoryId').text(categoryId);
      $('#deleteCategoryName').text(categoryName);
      $('#deleteCategoryModal').removeClass('hidden').addClass('flex');
    }

    function closeDeleteCategoryModal() {
      $('#deleteCategoryModal').removeClass('flex').addClass('hidden');
    }

    function confirmDeleteCategory() {

      const categoryId=$("#deleteCategoryId").text()

      // 这里应该添加与后端的交互逻辑
      $.ajax({
        url:BASE_API+"/category",
        method:"delete",
        dataType:"json",
        headers:{'TENANT-ID':localStorage.getItem("tenantId")},
        data:{categoryId:categoryId},
        success:function(res){
          if(res.code==0){
            toastr.success("删除成功")

            closeDeleteCategoryModal();
            loadCategories(); // 重新加载分类列表
          }else{
            toastr.error("删除失败")
          }
        }
      })


    }




    // 修改 loadCategories 函数中的分类项模板
    function loadCategories() {
      let categories = [];

      //加载远程
      $.ajax({
        url: BASE_API + "/category/list",
        method: "get",
        dataType: "json",
        data: "json",
        async: false,
        headers:{'TENANT-ID':localStorage.getItem("tenantId")},
        success: function(res) {
          if (res.code == 0) {
            res.data.forEach(u => categories.push(u));
            //加载书签数据
            loadLinks(categories[0].categoryId);
          } else {
            toastr.error("加载失败");
          }
        }
      });

      //生成横向导航（桌面端）
      const navList = $('#navList');
      navList.empty();
      categories.forEach(category => {
        navList.append(`
          <button onclick="loadLinks('${r'${category.categoryId}'}')" class="px-4 py-1.5 text-sm text-theme-secondary hover:bg-card-theme rounded transition-colors whitespace-nowrap">${r'${category.name}'}</button>
        `);
      });

      //生成移动端导航菜单
      const mobileNavList = $('#mobileNavList');
      mobileNavList.empty();
      categories.forEach(category => {
        mobileNavList.append(`
          <button onclick="loadLinks('${r'${category.categoryId}'}'); $('#mobileMenu').removeClass('active');"
            class="w-full px-4 py-2 text-sm text-theme-secondary hover:bg-theme rounded transition-colors text-left">
            ${r'${category.name}'}
          </button>
        `);
      });

      //生成书签选择目录列表
      const selectCategoryList = $("#editLinkCategoryId");
      selectCategoryList.empty();
      categories.forEach(category => {
        selectCategoryList.append(`
          <option value="${r'${category.categoryId}'}">${r'${category.name}'}</option>
        `);
      });

      //生成目录列表
      const categoryList = $('#categoryList');
      categoryList.empty();
      categories.forEach(category => {
        categoryList.append(`
          <div class="flex items-center justify-between p-3 bg-theme rounded group">
            <span class="text-sm text-theme">${r'${category.name}'}</span>
            <div class="flex items-center space-x-2 opacity-0 group-hover:opacity-100 transition-opacity">
              <button class="text-theme-secondary hover:text-blue-500 transition-colors p-1.5 hover:bg-black hover:bg-opacity-10 rounded" 
                onclick="openEditCategoryModal('${r'${category.categoryId}'}','${r'${category.name}'}')" title="编辑分类">
                <i class="fas fa-edit"></i>
              </button>
              <button class="text-theme-secondary hover:text-red-500 transition-colors p-1.5 hover:bg-black hover:bg-opacity-10 rounded" 
                onclick="openDeleteCategoryModal('${r'${category.categoryId}'}','${r'${category.name}'}')" title="删除分类">
                <i class="fas fa-trash-alt"></i>
              </button>
            </div>
          </div>
        `);
      });
    }



  </script>
</body>
</html> 