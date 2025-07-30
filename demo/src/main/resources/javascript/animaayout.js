  function performSearch() {
            const searchInput = document.getElementById('searchInput');
            const query = searchInput.value.trim();
            
            if (query) {
                // Animación del botón
                const searchBtn = document.querySelector('.search-btn');
                searchBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i>';
                
                // Simular búsqueda
                setTimeout(() => {
                    alert(`Búsqueda realizada: "${query}"`);
                    searchBtn.innerHTML = '<i class="fas fa-search"></i>';
                    searchInput.value = '';
                }, 1000);
            } else {
                // Efecto de shake si está vacío
                searchInput.style.animation = 'shake 0.5s';
                setTimeout(() => {
                    searchInput.style.animation = '';
                }, 500);
            }
        }

        // Búsqueda al presionar Enter
        document.getElementById('searchInput').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                performSearch();
            }
        });

        // Newsletter subscription
        function subscribeNewsletter() {
            const emailInput = document.querySelector('.newsletter-input');
            const email = emailInput.value.trim();
            const btn = document.querySelector('.newsletter-btn');
            
            if (email && isValidEmail(email)) {
                // Animación del botón
                btn.innerHTML = '<i class="fas fa-spinner fa-spin"></i>';
                
                // Simular suscripción
                setTimeout(() => {
                    alert(`¡Gracias por suscribirte! Hemos enviado un email a: ${email}`);
                    btn.innerHTML = '<i class="fas fa-paper-plane"></i>';
                    emailInput.value = '';
                }, 1500);
            } else {
                // Efecto de shake si el email no es válido
                emailInput.style.animation = 'shake 0.5s';
                setTimeout(() => {
                    emailInput.style.animation = '';
                }, 500);
            }
        }

        // Validar email
        function isValidEmail(email) {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return emailRegex.test(email);
        }

        // Suscripción al presionar Enter
        document.querySelector('.newsletter-input').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                subscribeNewsletter();
            }
        });

        // Smooth scroll para links del footer
        document.querySelectorAll('.footer-links a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', function (e) {
                e.preventDefault();
                const target = document.querySelector(this.getAttribute('href'));
                if (target) {
                    target.scrollIntoView({
                        behavior: 'smooth',
                        block: 'start'
                    });
                }
            });
        });

        // Efecto de navbar al hacer scroll
        let lastScrollTop = 0;
        window.addEventListener('scroll', function() {
            let scrollTop = window.pageYOffset || document.documentElement.scrollTop;
            const navbar = document.querySelector('.navbar');
            
            if (scrollTop > lastScrollTop && scrollTop > 100) {
                // Scrolling down
                navbar.style.transform = 'translateY(-100%)';
            } else {
                // Scrolling up
                navbar.style.transform = 'translateY(0)';
            }
            
            // Cambiar opacidad del navbar según scroll
            if (scrollTop > 50) {
                navbar.style.background = 'rgba(255, 255, 255, 0.15)';
            } else {
                navbar.style.background = 'rgba(255, 255, 255, 0.1)';
            }
            
            lastScrollTop = scrollTop;
        });

        // Cerrar navbar móvil al hacer click en un enlace
        document.querySelectorAll('.navbar-nav .nav-link').forEach(link => {
            link.addEventListener('click', () => {
                const navbarCollapse = document.querySelector('.navbar-collapse');
                if (navbarCollapse.classList.contains('show')) {
                    bootstrap.Collapse.getInstance(navbarCollapse).hide();
                }
            });
        });

        // Animación CSS para shake
        const style = document.createElement('style');
        style.textContent = `
            @keyframes shake {
                0%, 100% { transform: translateX(0); }
                25% { transform: translateX(-5px); }
                75% { transform: translateX(5px); }
            }
        `;
        document.head.appendChild(style);