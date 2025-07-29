  const observerOptions = {
            threshold: 0.1,
            rootMargin: '0px 0px -50px 0px'
        };

        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.style.opacity = '1';
                    entry.target.style.transform = 'translateY(0)';
                }
            });
        }, observerOptions);

        // Efecto de entrada para el logo
         const logo = document.querySelector('.logo');
        logo.addEventListener('mouseenter', function() {
            this.style.transform = 'scale(1.1)';
            this.style.color = '#61C8B2';
        });

        logo.addEventListener('mouseleave', function() {
            this.style.transform = 'scale(1)';
            this.style.color = '#333';
        });

        // Observar las tarjetas de productos
        document.querySelectorAll('.product-card').forEach(card => {
            card.style.opacity = '0';
            card.style.transform = 'translateY(50px)';
            card.style.transition = 'all 0.8s ease';
            observer.observe(card);
        });

        // Efecto de hover en los botones del catálogo
        document.querySelectorAll('.catalog-btn').forEach(btn => {
            btn.addEventListener('mouseenter', function() {
                this.style.transform = 'translateY(-2px) scale(1.05)';
            });
            
            btn.addEventListener('mouseleave', function() {
                this.style.transform = 'translateY(0) scale(1)';
            });
        });

        // Efecto parallax suave en las tarjetas
        window.addEventListener('scroll', () => {
            const scrolled = window.pageYOffset;
            const cards = document.querySelectorAll('.product-card');
            
            cards.forEach((card, index) => {
                const speed = 0.1 + (index * 0.05);
                card.style.transform = `translateY(${scrolled * speed}px)`;
            });
        });

       