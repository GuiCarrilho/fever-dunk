import React, { useState } from 'react'
import { Navigate, useNavigate } from 'react-router-dom';
import './LoginCriar.css'
import email_icon from '../Assets/email.png'
import password_icon from '../Assets/password.png'

export const LoginCriar = () => {
  const navigate = useNavigate();
  const [action, setAction] = useState("Registrar");
  const [message, setMessage] = useState("");

  const handleLogin = async () => {
    if(action === "Login"){
      await login();
    }else {
      setAction("Login");
    }
  };

  const handleSignup = async() => {
    if(action === "Registrar"){
      await signup();
    }else {
      setAction("Registrar")
    }
  };

  async function login() {
    let email = document.getElementById("email").value;
    let senha = document.getElementById("senha").value;

    try {
      const response = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json; charset=utf8",
          Accept: "application/json",
        },
        body: JSON.stringify({
          email: email,
          senha: senha,
        }),
      });

      const responseBody = await response.json();
      const token = responseBody["authorization"];
      const key = "Authorization";
      window.localStorage.setItem(key, token);

      if (response.status === 200) {
        // Redireciona para /index após o login ser aceito
        navigate('/index');
      } else {
        displayMessage('Login falhou.');
      }
    } catch (error) {
      displayMessage('Login falhou.');
    }
  }

  async function signup() {
    let nome = document.getElementById("nome").value;
    let email = document.getElementById("email").value;
    let senha = document.getElementById("senha").value;

    try {
      const response = await fetch("http://localhost:8080/manager", {
        method: "POST",
        headers: new Headers({
          "Content-Type": "application/json; charset=utf8",
          Accept: "application/json",
        }),
        body: JSON.stringify({
          nome: nome,
          email: email,
          senha: senha,
        }),
      });

      if(response.status ===  201){
        setMessage("Registro concluído com sucesso");
        setAction("Login");
      }
      else {
        displayMessage("Registro não foi concluído com sucesso.");
        console.log(response.status);
      }

      }catch (error){
      displayMessage("Registro não foi concluído com sucesso.");
    }
  }

  function displayMessage(mes){
    setMessage(mes);

    setTimeout(() => {
      setMessage("");
    }, 10000);
  }

  return (
    <div className='container'>
      <div className="header">
        <div className="texto">Login</div>
        <div className="sublinhado"></div>
      </div>
      {message && (
          <div className="message-container">
            <div className="message-box">{message}</div>
          </div>
      )}
      <div className="inputs">
        <div className={action==="Registrar"?"input":"sumir"}>
          <img src={email_icon} alt="" />
          <input type="text" placeholder="Nome" id="nome" />
        </div>
        <div className="input">
          <img src={email_icon} alt="" />
          <input type="email" placeholder="E-mail" id="email" />
        </div>
        <div className="input">
          <img src={password_icon} alt="" />
          <input type="password" placeholder="Senha" id="senha" />
        </div>
        <div className="enviar-container">
            <div className={action==="Login"?"enviar cinza":"enviar"}
            onClick={handleSignup}>Registrar</div>
            <div className={action==="Registrar"?"enviar cinza":"enviar"}
            onClick={handleLogin}>Login</div>
        </div>
      </div>
    </div>
  );
};