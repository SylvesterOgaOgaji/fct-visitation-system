/**
 * FCT Visitation System - Main JavaScript File
 * Contains common functionality used across the system
 */

// Wait for document to be fully loaded
document.addEventListener('DOMContentLoaded', function() {
    initializeFctSystem();
});

/**
 * Initialize the FCT Visitation System
 */
function initializeFctSystem() {
    setupUIComponents();
    initializeFormValidation();
    setupNotifications();
    initializeAccessibility();
}

/**
 * Setup UI Components
 */
function setupUIComponents() {
    // Setup tooltips
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function(tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Setup popovers
    const popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    popoverTriggerList.map(function(popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });

    // Mobile sidebar toggle
    const sidebarToggle = document.querySelector('.sidebar-toggle');
    if (sidebarToggle) {
        sidebarToggle.addEventListener('click', function() {
            const sidebar = document.querySelector('.admin-sidebar') || 
                           document.querySelector('.security-sidebar') || 
                           document.querySelector('.officer-sidebar');
            if (sidebar) {
                sidebar.classList.toggle('collapsed');
            }
        });
    }

    // Auto-hide alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert:not(.alert-permanent)');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, 5000);
    });
}

/**
 * Initialize Form Validation
 */
function initializeFormValidation() {
    // Add custom validation for Nigerian phone numbers
    const phoneInputs = document.querySelectorAll('input[type="tel"]');
    phoneInputs.forEach(function(input) {
        input.addEventListener('input', function(e) {
            const value = e.target.value.replace(/\D/g, '');
            if (value.startsWith('0')) {
                e.target.value = value.substring(1);
            } else {
                e.target.value = value;
            }
        });
        
        input.addEventListener('blur', function(e) {
            const value = e.target.value.trim();
            if (value && (value.length !== 10 || !/^\d+$/.test(value))) {
                input.setCustomValidity('Please enter a valid 10-digit Nigerian phone number (without the leading zero)');
            } else {
                input.setCustomValidity('');
            }
        });
    });

    // Add custom validation for NIN
    const ninInputs = document.querySelectorAll('input[data-type="nin"]');
    ninInputs.forEach(function(input) {
        input.addEventListener('input', function(e) {
            const value = e.target.value.replace(/\D/g, '');
            e.target.value = value;
        });
        
        input.addEventListener('blur', function(e) {
            const value = e.target.value.trim();
            if (value && (value.length !== 11 || !/^\d+$/.test(value))) {
                input.setCustomValidity('Please enter a valid 11-digit Nigerian National Identification Number');
            } else {
                input.setCustomValidity('');
            }
        });
    });

    // Bootstrap validation
    const forms = document.querySelectorAll('.needs-validation');
    Array.prototype.slice.call(forms).forEach(function(form) {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });
}

/**
 * Setup Notifications System
 */
function setupNotifications() {
    // Check for notification permission
    if ("Notification" in window && Notification.permission !== "denied") {
        Notification.requestPermission();
    }

    // Function to show notification
    window.showNotification = function(title, message, icon = '/images/fct-logo.png') {
        if ("Notification" in window && Notification.permission === "granted") {
            const notification = new Notification(title, {
                body: message,
                icon: icon
            });
            
            notification.onclick = function() {
                window.focus();
                this.close();
            };
            
            setTimeout(notification.close.bind(notification), 7000);
        } else {
            // Fallback to toast notification
            showToast(title, message);
        }
    };

    // Function to show toast notification
    window.showToast = function(title, message, type = 'info') {
        const toastContainer = document.getElementById('toast-container');
        if (!toastContainer) {
            const container = document.createElement('div');
            container.id = 'toast-container';
            container.className = 'position-fixed bottom-0 end-0 p-3';
            container.style.zIndex = '1050';
            document.body.appendChild(container);
        }
        
        const toastId = 'toast-' + Date.now();
        const bgClass = type === 'success' ? 'bg-success' : 
                       type === 'warning' ? 'bg-warning text-dark' : 
                       type === 'error' ? 'bg-danger' : 'bg-info';
        
        const toastHtml = `
            <div id="${toastId}" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header ${bgClass} text-white">
                    <i class="fas fa-bell me-2"></i>
                    <strong class="me-auto">${title}</strong>
                    <small>${new Date().toLocaleTimeString()}</small>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body">
                    ${message}
                </div>
            </div>
        `;
        
        document.getElementById('toast-container').insertAdjacentHTML('beforeend', toastHtml);
        const toastElement = document.getElementById(toastId);
        const toast = new bootstrap.Toast(toastElement, { delay: 5000 });
        toast.show();
        
        // Remove toast from DOM after it's hidden
        toastElement.addEventListener('hidden.bs.toast', function() {
            toastElement.remove();
        });
    };

    // Setup WebSocket for real-time notifications (if needed)
    setupWebSocketNotifications();
}

/**
 * Setup WebSocket for real-time notifications
 */
function setupWebSocketNotifications() {
    // Check if WebSocket is available in browser
    if ("WebSocket" in window) {
        try {
            const protocol = window.location.protocol === 'https:' ? 'wss://' : 'ws://';
            const host = window.location.host;
            const wsUrl = `${protocol}${host}/api/notifications/ws`;
            
            const notificationSocket = new WebSocket(wsUrl);
            
            notificationSocket.onopen = function() {
                console.log("WebSocket connection established");
            };
            
            notificationSocket.onmessage = function(event) {
                try {
                    const notification = JSON.parse(event.data);
                    showNotification(notification.title, notification.message, notification.icon);
                } catch (e) {
                    console.error("Error handling WebSocket notification:", e);
                }
            };
            
            notificationSocket.onclose = function() {
                console.log("WebSocket connection closed");
                // Attempt to reconnect after 5 seconds
                setTimeout(setupWebSocketNotifications, 5000);
            };
            
            notificationSocket.onerror = function(error) {
                console.error("WebSocket error:", error);
                notificationSocket.close();
            };
            
            // Store socket in window object for later use
            window.notificationSocket = notificationSocket;
            
        } catch (e) {
            console.error("Error setting up WebSocket:", e);
        }
    }
}

/**
 * Initialize Accessibility Features
 */
function initializeAccessibility() {
    // Add skip to content link
    const skipLink = document.createElement('a');
    skipLink.href = '#main-content';
    skipLink.className = 'skip-to-content';
    skipLink.innerText = 'Skip to main content';
    document.body.insertBefore(skipLink, document.body.firstChild);
    
    // Add main-content id to main element if not present
    const mainElement = document.querySelector('main');
    if (mainElement && !mainElement.id) {
        mainElement.id = 'main-content';
    }
    
    // High contrast mode toggle
    const highContrastToggle = document.getElementById('high-contrast-toggle');
    if (highContrastToggle) {
        highContrastToggle.addEventListener('click', function() {
            document.body.classList.toggle('high-contrast');
            localStorage.setItem('high-contrast', document.body.classList.contains('high-contrast'));
        });
        
        // Check for saved preference
        if (localStorage.getItem('high-contrast') === 'true') {
            document.body.classList.add('high-contrast');
        }
    }
    
    // Font size controls
    const increaseFontBtn = document.getElementById('increase-font');
    const decreaseFontBtn = document.getElementById('decrease-font');
    const resetFontBtn = document.getElementById('reset-font');
    
    if (increaseFontBtn) {
        increaseFontBtn.addEventListener('click', function() {
            changeFontSize(1);
        });
    }
    
    if (decreaseFontBtn) {
        decreaseFontBtn.addEventListener('click', function() {
            changeFontSize(-1);
        });
    }
    
    if (resetFontBtn) {
        resetFontBtn.addEventListener('click', function() {
            resetFontSize();
        });
    }
}

/**
 * Change Font Size
 * @param {number} delta - Amount to change font size (positive or negative)
 */
function changeFontSize(delta) {
    let currentSize = parseFloat(getComputedStyle(document.documentElement).fontSize);
    let newSize = currentSize + delta;
    
    // Limit the font size to a reasonable range
    if (newSize >= 12 && newSize <= 20) {
        document.documentElement.style.fontSize = newSize + 'px';
        localStorage.setItem('font-size', newSize);
    }
}

/**
 * Reset Font Size to Default
 */
function resetFontSize() {
    document.documentElement.style.fontSize = '16px';
    localStorage.removeItem('font-size');
}

/**
 * Format Date for Display
 * @param {string|Date} date - Date to format
 * @param {boolean} includeTime - Whether to include time
 * @returns {string} Formatted date string
 */
function formatDate(date, includeTime = false) {
    if (!date) return '';
    
    const dateObj = date instanceof Date ? date : new Date(date);
    
    const options = {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    };
    
    if (includeTime) {
        options.hour = '2-digit';
        options.minute = '2-digit';
    }
    
    return dateObj.toLocaleDateString('en-NG', options);
}

/**
 * Format Currency for Display
 * @param {number} amount - Amount to format
 * @returns {string} Formatted currency string
 */
function formatCurrency(amount) {
    return new Intl.NumberFormat('en-NG', {
        style: 'currency',
        currency: 'NGN'
    }).format(amount);
}

/**
 * Debounce Function
 * @param {Function} func - Function to debounce
 * @param {number} wait - Wait time in milliseconds
 * @returns {Function} Debounced function
 */
function debounce(func, wait = 300) {
    let timeout;
    return function(...args) {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), wait);
    };
}

/**
 * Check if an element is in viewport
 * @param {HTMLElement} element - Element to check
 * @returns {boolean} True if element is in viewport
 */
function isInViewport(element) {
    const rect = element.getBoundingClientRect();
    return (
        rect.top >= 0 &&
        rect.left >= 0 &&
        rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
        rect.right <= (window.innerWidth || document.documentElement.clientWidth)
    );
}

/**
 * Load content via AJAX
 * @param {string} url - URL to load
 * @param {HTMLElement} targetElement - Element to populate with response
 * @param {Function} callback - Optional callback function
 */
function loadContent(url, targetElement, callback) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    
    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 400) {
            targetElement.innerHTML = xhr.responseText;
            if (typeof callback === 'function') {
                callback(null, xhr.responseText);
            }
        } else {
            console.error('Request failed with status:', xhr.status);
            if (typeof callback === 'function') {
                callback(new Error('Request failed'), null);
            }
        }
    };
    
    xhr.onerror = function() {
        console.error('Request failed');
        if (typeof callback === 'function') {
            callback(new Error('Request failed'), null);
        }
    };
    
    xhr.send();
}

// Add global FCT namespace
window.FCT = {
    showNotification,
    showToast,
    formatDate,
    formatCurrency,
    debounce,
    isInViewport,
    loadContent
};