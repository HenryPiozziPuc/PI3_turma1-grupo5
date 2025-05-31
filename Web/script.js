document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('login-form');
    const errorMessage = document.getElementById('error-message');
  
    loginForm.addEventListener('submit', (e) => {
      e.preventDefault();
  
      const email = document.getElementById('email').value.trim();
      const password = document.getElementById('password').value.trim();
  
      if (!email || !password) {
        errorMessage.textContent = 'Preencha todos os campos.';
        return;
      }
  
      if (password.length < 6) {
        errorMessage.textContent = 'A senha deve ter pelo menos 6 caracteres.';
        return;
      }
      if (email === 'usuario@superid.com' && password === '123456') {
        errorMessage.textContent = '';
        window.location.href = 'sucesso.html';
      } else {
        errorMessage.textContent = 'E-mail ou senha inválidos.';
      }
    });
  });
  