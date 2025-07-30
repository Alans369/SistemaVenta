  // Toggle de contraseña
        function togglePassword() {
            const passwordInput = document.getElementById('password');
            const passwordIcon = document.getElementById('passwordIcon');
            
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                passwordIcon.className = 'fas fa-eye-slash';
            } else {
                passwordInput.type = 'password';
                passwordIcon.className = 'fas fa-eye';
            }
        }

        // Validación de fortaleza de contraseña
        function checkPasswordStrength(password) {
            const strengthBar = document.getElementById('strengthBar');
            let strength = 0;
            
            if (password.length >= 8) strength++;
            if (/[a-z]/.test(password)) strength++;
            if (/[A-Z]/.test(password)) strength++;
            if (/[0-9]/.test(password)) strength++;
            if (/[^A-Za-z0-9]/.test(password)) strength++;
            
            strengthBar.className = 'password-strength-bar';
            
            switch (strength) {
                case 0:
                case 1:
                    strengthBar.classList.add('strength-weak');
                    return 'Débil';
                case 2:
                    strengthBar.classList.add('strength-fair');
                    return 'Regular';
                case 3:
                case 4:
                    strengthBar.classList.add('strength-good');
                    return 'Buena';
                case 5:
                    strengthBar.classList.add('strength-strong');
                    return 'Fuerte';
                default:
                    return 'Débil';
            }
        }

        // Validación en tiempo real
        document.getElementById('password').addEventListener('input', function() {
            const password = this.value;
            const strength = checkPasswordStrength(password);
            
            if (password.length > 0) {
                if (strength === 'Débil' || strength === 'Regular') {
                    this.classList.add('is-invalid');
                    this.classList.remove('is-valid');
                    document.getElementById('passwordError').textContent = `Contraseña ${strength.toLowerCase()}. Usa mayúsculas, números y símbolos.`;
                } else {
                    this.classList.add('is-valid');
                    this.classList.remove('is-invalid');
                }
            } else {
                this.classList.remove('is-valid', 'is-invalid');
            }
        });

        // Validación de campos
        function validateField(field) {
            const value = field.value.trim();
            
            switch (field.id) {
                case 'nombre':
                case 'apellido':
                    if (value.length < 2) {
                        field.classList.add('is-invalid');
                        field.classList.remove('is-valid');
                        field.nextElementSibling.textContent = 'Debe tener al menos 2 caracteres';
                        return false;
                    } else {
                        field.classList.add('is-valid');
                        field.classList.remove('is-invalid');
                        return true;
                    }
                    
                case 'correo':
                    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                    if (!emailRegex.test(value)) {
                        field.classList.add('is-invalid');
                        field.classList.remove('is-valid');
                        field.nextElementSibling.textContent = 'Ingresa un email válido';
                        return false;
                    } else {
                        field.classList.add('is-valid');
                        field.classList.remove('is-invalid');
                        return true;
                    }
                    
                case 'password':
                    const strength = checkPasswordStrength(value);
                    if (value.length < 6 || strength === 'Débil') {
                        field.classList.add('is-invalid');
                        field.classList.remove('is-valid');
                        return false;
                    } else {
                        field.classList.add('is-valid');
                        field.classList.remove('is-invalid');
                        return true;
                    }
            }
            return true;
        }

        // Validación en tiempo real para todos los campos
        ['nombre', 'apellido', 'correo'].forEach(id => {
            document.getElementById(id).addEventListener('input', function() {
                validateField(this);
            });
        });

        // Función de cancelar
        function cancelForm() {
            if (confirm('¿Estás seguro de que quieres cancelar el registro?')) {
                document.getElementById('registerForm').reset();
                // Remover clases de validación
                document.querySelectorAll('.form-control').forEach(field => {
                    field.classList.remove('is-valid', 'is-invalid');
                });
                // Resetear barra de contraseña
                document.getElementById('strengthBar').className = 'password-strength-bar';
            }
        }

        // Manejo del formulario
        document.getElementById('registerForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Validar todos los campos
            const nombre = document.getElementById('nombre');
            const apellido = document.getElementById('apellido');
            const correo = document.getElementById('correo');
            const password = document.getElementById('password');
            const rol = document.querySelector('input[name="rol"]:checked');
            
            let isValid = true;
            
            // Validar campos individuales
            isValid &= validateField(nombre);
            isValid &= validateField(apellido);
            isValid &= validateField(correo);
            isValid &= validateField(password);
            
            // Validar rol
            if (!rol) {
                document.getElementById('rolError').textContent = 'Debes seleccionar un rol';
                document.getElementById('rolError').style.display = 'block';
                isValid = false;
            } else {
                document.getElementById('rolError').style.display = 'none';
            }
            
            if (isValid) {
                // Simular envío
                const submitBtn = document.querySelector('.btn-accept');
                const originalContent = submitBtn.innerHTML;
                
                submitBtn.innerHTML = '<div class="spinner"></div> Registrando...';
                submitBtn.disabled = true;
                document.body.classList.add('loading');
                
                setTimeout(() => {
                    alert(`¡Registro exitoso!\n\nDatos:\n- Nombre: ${nombre.value}\n- Apellido: ${apellido.value}\n- Email: ${correo.value}\n- Rol: ${rol.value}\n\n¡Bienvenido a ModernApp!`);
                    
                    // Resetear formulario
                    this.reset();
                    document.querySelectorAll('.form-control').forEach(field => {
                        field.classList.remove('is-valid', 'is-invalid');
                    });
                    document.getElementById('strengthBar').className = 'password-strength-bar';
                    
                    // Restaurar botón
                    submitBtn.innerHTML = originalContent;
                    submitBtn.disabled = false;
                    document.body.classList.remove('loading');
                }, 2000);
            }
        });

        // Animación de entrada
        window.addEventListener('load', function() {
            const container = document.querySelector('.register-container');
            container.style.opacity = '0';
            container.style.transform = 'translateY(30px)';
            
            setTimeout(() => {
                container.style.transition = 'all 0.6s ease';
                container.style.opacity = '1';
                container.style.transform = 'translateY(0)';
            }, 100);
        });