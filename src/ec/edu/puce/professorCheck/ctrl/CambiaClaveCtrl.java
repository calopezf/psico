package ec.edu.puce.professorCheck.ctrl;
///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.prottaps.medicalApp.ctrl;
//
//import ec.gob.mrl.peti.dm.LogIn;
//import ec.gob.mrl.peti.model.Usuario;
//import ec.gob.mrl.peti.service.UsuarioServicio;
//import ec.gob.mrl.peti.util.Md5;
//import java.security.NoSuchAlgorithmException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.ejb.EJB;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ManagedProperty;
//import javax.faces.bean.ViewScoped;
//import javax.faces.component.UIComponent;
//import javax.faces.component.UIInput;
//import javax.faces.context.FacesContext;
//
///**
// *
// * @author juan
// */
//@ManagedBean(name = "cambiaClaveCtrl")
//@ViewScoped
//public class CambiaClaveCtrl extends BaseCtrl {
//
//    @EJB
//    private UsuarioServicio usuarioServicio;
//    @ManagedProperty("#{LogIn}")
//    private LogIn logIn;
//    private String claveActual;
//    private String claveNueva;
//    private String confirmaClaveNueva;
//    private boolean habreExito = false;
//
//    //Metodos Aspirante
//    public void validaClaveActual(FacesContext context, UIComponent component, Object value) {
//        if (value != null) {
//            try {
//                String claveActual = (String) value;
//                String claveActualHash = Md5.hash(claveActual);
//                Usuario usuario = obtenerUsuarioConectado();
//                if (!usuario.getPassword().equals(claveActualHash)) {
//                    habreExito = false;
//
//                    ((UIInput) component).setValid(false);
//                    addErrorMessage(null, getBundleMensajes("error.clave.actual.no.coincide", null), "");
//                }
//            } catch (NoSuchAlgorithmException ex) {
//                Logger.getLogger(CambiaClaveCtrl.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//
//    private Usuario obtenerUsuarioConectado(){
//        return usuarioServicio.findByPk(getRemoteUser());
//    }
//    
//    public void cambiarClave() {
//        if (getClaveNueva().equals(getConfirmaClaveNueva())) {
//            try {
//                String claveActualHash = Md5.hash(getClaveNueva());
//
//                Usuario usuario = obtenerUsuarioConectado();
//                usuario.setPassword(claveActualHash);
//
//                usuarioServicio.update(usuario);
//
//                getLogIn().setUsuarioConectado(usuario);
//
//                habreExito = true;
//            } catch (NoSuchAlgorithmException ex) {
//                habreExito = false;
//                Logger.getLogger(CambiaClaveCtrl.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else {
//            habreExito = false;
//            addErrorMessage(null, getBundleMensajes("error.clave.nueva.confirmacion.no.iguales", null), "");
//        }
//    }
//
//    public String cerrarExitoMp() {
//        claveActual = "";
//        claveNueva = "";
//        confirmaClaveNueva = "";
//        habreExito = false;
//
//        return null;
//    }
//
//    public String getClaveActual() {
//        return claveActual;
//    }
//
//    public void setClaveActual(String claveActual) {
//        this.claveActual = claveActual;
//    }
//
//    public String getClaveNueva() {
//        return claveNueva;
//    }
//
//    public void setClaveNueva(String claveNueva) {
//        this.claveNueva = claveNueva;
//    }
//
//    public String getConfirmaClaveNueva() {
//        return confirmaClaveNueva;
//    }
//
//    public void setConfirmaClaveNueva(String confirmaClaveNueva) {
//        this.confirmaClaveNueva = confirmaClaveNueva;
//    }
//
//    public boolean isHabreExito() {
//        return habreExito;
//    }
//
//    public void setHabreExito(boolean habreExito) {
//        this.habreExito = habreExito;
//    }
//
//    public UsuarioServicio getUsuarioServicio() {
//        return usuarioServicio;
//    }
//
//    public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
//        this.usuarioServicio = usuarioServicio;
//    }
//
//    public LogIn getLogIn() {
//        return logIn;
//    }
//
//    public void setLogIn(LogIn logIn) {
//        this.logIn = logIn;
//    }
//}