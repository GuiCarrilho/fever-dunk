import React from 'react'
import './LoginCriar.css'

import email_icon from '../Assets/email.png'
import password_icon from '../Assets/password.png'

export const LoginCriar = () => {
  return (
    <div className='container'>
      <div className="header">
        <div className="texto">Registrar</div>
        <div className="sublinhado"></div>
      </div>
      <div className="inputs">
        <div className="input">
          <img src={email_icon} alt="" />
          <input type="email" placeholder="E-mail" id="" />
        </div>
        <div className="input">
          <img src={password_icon} alt="" />
          <input type="senha" placeholder="Senha" id="" />
        </div>
        <div className="esqueci-senha"><span>Esqueceu Sua Senha?</span></div>
        <div className="enviar-container">
            <div className="enviar">Registrar</div>
            <div className="enviar">Login</div>
        </div>
      </div>
    </div>
  )
}
