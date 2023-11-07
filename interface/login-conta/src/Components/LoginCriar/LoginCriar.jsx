import React from 'react'
import './LoginCriar.css'

import user_icon from '../Assets/person.png'
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
          <img src={user_icon} alt="" />
          <input type="texto" name="" id="" />
        </div>
        <div className="input">
          <img src={email_icon} alt="" />
          <input type="email" name="" id="" />
        </div>
        <div className="input">
          <img src={password_icon} alt="" />
          <input type="senha" name="" id="" />
        </div>
        <div className="esqueci-senha"><span>Esqueci minha Senha</span></div>
        <div className="enviar-container">
          <div className="enviar">
            <div className="enviar">Registrar</div>
            <div className="enviar">Login</div>
          </div>
        </div>
      </div>
    </div>
  )
}
