package ec.edu.puce.professorCheck.ctrl;
///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package ec.gob.mrl.peti.ctrl;
//
//import java.util.ArrayList;
//import java.util.List;
//import javax.faces.bean.ManagedBean;
//import javax.faces.model.SelectItem;
//
///**
// *
//// * @author juan
// */
//@ManagedBean(name = "buscarItemsCtrl")
//public class BuscarItemsCtrl extends BaseCtrl {
//
//    private List<SelectItem> orderByItems;
//    private List<SelectItem> parametrosItems;
//    private List<SelectItem> institucionItems;
//    private List<SelectItem> sucursalesItems;
//    private List<SelectItem> supervisorItems;
//    private List<SelectItem> grupoItems;
//    private List<SelectItem> grupoInstitucionesItems;
//    private List<SelectItem> encuestaItems;
//    private List<SelectItem> eventoItems;
//    private List<SelectItem> cursoItems;
//    private List<SelectItem> usuariosItems;
//    private List<SelectItem> personasItems;
//    private List<SelectItem> fichasItems;
//    private List<SelectItem> institucionesItems;
//    private List<SelectItem> etniasItems;
//    private List<SelectItem> serviciosItems;
//    private List<SelectItem> actividadesEconomicasItems;    
//    private List<SelectItem> condicionesPerjudicialesItems;
//    private List<SelectItem> admPortalItems;
//
//    public List<SelectItem> getServiciosItems() {
//        
//        serviciosItems = new ArrayList<SelectItem>();
//        
//        serviciosItems.add(new SelectItem("nombre", getBundleEtiquetas("nombre", null))); 
//        
//        return serviciosItems;
//    }
//
//    public void setServiciosItems(List<SelectItem> serviciosItems) {
//        this.serviciosItems = serviciosItems;
//    }
//
//    public List<SelectItem> getEtniasItems() {
//        
//        etniasItems = new ArrayList<SelectItem>();
//        
//        etniasItems.add(new SelectItem("nombre", getBundleEtiquetas("nombre", null)));
//        
//        return etniasItems;
//    }
//
//    public void setEtniasItems(List<SelectItem> etniasItems) {
//        this.etniasItems = etniasItems;
//    }
//
//    public List<SelectItem> getInstitucionesItems() {
//        
//        institucionesItems = new ArrayList<SelectItem>();
//        
//        institucionesItems.add(new SelectItem("nombre", getBundleEtiquetas("nombre", null)));
//        
//        return institucionesItems;
//    }
//
//    public void setInstitucionesItems(List<SelectItem> institucionesItems) {
//        this.institucionesItems = institucionesItems;
//    }
//
//    public List<SelectItem> getFichasItems() {
//        
//        fichasItems = new ArrayList<SelectItem>();
//        
//        fichasItems.add(new SelectItem("fechaRegistro", getBundleEtiquetas(
//                "fecha", null)));
//        
//        return fichasItems;
//    }
//
//    public void setFichasItems(List<SelectItem> fichasItems) {
//        this.fichasItems = fichasItems;
//    }
//
//    public List<SelectItem> getPersonasItems() {
//        
//        personasItems = new ArrayList<SelectItem>();
//        
//        personasItems.add(new SelectItem("identificacion", getBundleEtiquetas(
//                "identificacion", null)));
//        
//        personasItems.add(new SelectItem("primerNombre", getBundleEtiquetas(
//                "nombre", null)));
//        
//        personasItems.add(new SelectItem("primerApellido", getBundleEtiquetas(
//                "apellido", null)));
//        
//        return personasItems;
//    }
//
//    public void setPersonasItems(List<SelectItem> personasItems) {
//        this.personasItems = personasItems;
//    }
//    
//    public List<SelectItem> getUsuariosItems() {
//        
//        usuariosItems = new ArrayList<SelectItem>();
//
//        usuariosItems.add(new SelectItem("identificacion", getBundleEtiquetas(
//                "identificacion", null)));  
//        
//        usuariosItems.add(new SelectItem("nombre", getBundleEtiquetas(
//                "nombres", null)));
//        
//        usuariosItems.add(new SelectItem("apellido", getBundleEtiquetas(
//                "apellidos", null)));
//        
//        return usuariosItems;
//    }
//
//    public void setUsuariosItems(List<SelectItem> usuariosItems) {
//        this.usuariosItems = usuariosItems;
//    }
//
//    public List<SelectItem> getOrderByItems() {
//        orderByItems = new ArrayList<SelectItem>();
//
//        orderByItems.add(new SelectItem(true, "ASC"));
//        orderByItems.add(new SelectItem(false, "DESC"));
//
//        return orderByItems;
//    }
//
//    public void setOrderByItems(List<SelectItem> orderByItems) {
//        this.orderByItems = orderByItems;
//    }
//
//    public List<SelectItem> getGrupoItems() {
//        grupoItems = new ArrayList<SelectItem>();
//
//        grupoItems.add(new SelectItem("grpNombre", getBundleEtiquetas(
//                "nombre", null)));
//        grupoItems.add(new SelectItem("grpNumero", getBundleEtiquetas(
//                "numero.grupo", null)));
//
//        return grupoItems;
//    }
//
//    public void setGrupoItems(List<SelectItem> grupoItems) {
//        this.grupoItems = grupoItems;
//    }
//
//    public List<SelectItem> getParametrosItems() {
//        parametrosItems = new ArrayList<SelectItem>();
//
//        parametrosItems.add(new SelectItem("nombre", getBundleEtiquetas(
//                "nombre", null)));  
//        
//        parametrosItems.add(new SelectItem("valor", getBundleEtiquetas(
//                "valor", null)));
//
//        return parametrosItems;
//    }
//
//    public void setParametrosItems(List<SelectItem> parametrosItems) {
//        this.parametrosItems = parametrosItems;
//    }
//
//    public List<SelectItem> getInstitucionItems() {
//        institucionItems = new ArrayList<SelectItem>();
//
//        institucionItems.add(new SelectItem("ins_nombre", getBundleEtiquetas(
//                "nombre", null)));
//        institucionItems.add(new SelectItem("ins_fecha_creacion", getBundleEtiquetas(
//                "fecha.creacion", null)));
//        return institucionItems;
//    }
//
//    public List<SelectItem> getSucursalesItems() {
//        sucursalesItems = new ArrayList<SelectItem>();
//
//        sucursalesItems.add(new SelectItem("sucNombre", getBundleEtiquetas(
//                "nombre", null)));
//
//        return sucursalesItems;
//    }
//
//    public List<SelectItem> getEventoItems() {
//        eventoItems = new ArrayList<SelectItem>();
//
//        eventoItems.add(new SelectItem("eveNombreEvento", getBundleEtiquetas(
//                "nombre", null)));
//        eventoItems.add(new SelectItem("eveNumero", getBundleEtiquetas(
//                "numero", null)));
//        return eventoItems;
//    }
//
//    public void setEventoItems(List<SelectItem> eventoItems) {
//        this.eventoItems = eventoItems;
//    }
//
//    public List<SelectItem> getEncuestaItems() {
//        encuestaItems = new ArrayList<SelectItem>();
//
//        encuestaItems.add(new SelectItem("encNombre", getBundleEtiquetas(
//                "nombre", null)));
//
//        return encuestaItems;
//    }
//
//    public void setSucursalesItems(List<SelectItem> sucursalesItems) {
//        this.sucursalesItems = sucursalesItems;
//    }
//
//    public List<SelectItem> getSupervisorItems() {
//        supervisorItems = new ArrayList<SelectItem>();
//
//        supervisorItems.add(new SelectItem("supApellidos", getBundleEtiquetas(
//                "apellidos", null)));
//        supervisorItems.add(new SelectItem("supDocIdentificacion", getBundleEtiquetas(
//                "rucCedula", null)));
//
//
//        return supervisorItems;
//    }
//
//    public void setSupervisorItems(List<SelectItem> supervisorItems) {
//        this.supervisorItems = supervisorItems;
//    }
//
//    public void setInstitucionItems(List<SelectItem> institucionItems) {
//        this.institucionItems = institucionItems;
//    }
//
//    public List<SelectItem> getGrupoInstitucionesItems() {
//        if (grupoInstitucionesItems == null) {
//            grupoInstitucionesItems = new ArrayList<SelectItem>();
//
//            grupoInstitucionesItems.add(new SelectItem("nombre", getBundleEtiquetas(
//                    "nombre", null)));
//            grupoInstitucionesItems.add(new SelectItem("numero", getBundleEtiquetas(
//                    "numero.grupo", null)));
//
//        }
//
//        return grupoInstitucionesItems;
//    }
//
//    public void setGrupoInstitucionesItems(List<SelectItem> grupoInstitucionesItems) {
//        this.grupoInstitucionesItems = grupoInstitucionesItems;
//    }
//
//    public List<SelectItem> getCursoItems() {
//        cursoItems = new ArrayList<SelectItem>();
//
//        cursoItems.add(new SelectItem("nombreCurso", getBundleEtiquetas(
//                "nombre", null)));
//        cursoItems.add(new SelectItem("institucionDicta", getBundleEtiquetas(
//                "institucion", null)));
//        cursoItems.add(new SelectItem("numeroCurso", getBundleEtiquetas(
//                "numero", null)));
//
//        return cursoItems;
//    }
//
//    public void setCursoItems(List<SelectItem> cursoItems) {
//        this.cursoItems = cursoItems;
//    }
//
//    public List<SelectItem> getActividadesEconomicasItems() {
//        actividadesEconomicasItems = new ArrayList<SelectItem>();
//        
//        actividadesEconomicasItems.add(new SelectItem("nombre", getBundleEtiquetas("nombre", null)));
//        
//        return actividadesEconomicasItems;
//    }
//
//    public void setActividadesEconomicasItems(List<SelectItem> actividadesEconomicas) {
//        this.actividadesEconomicasItems = actividadesEconomicas;
//    }
//
//    public List<SelectItem> getCondicionesPerjudicialesItems() {
//        condicionesPerjudicialesItems = new ArrayList<SelectItem>();
//        
//        condicionesPerjudicialesItems.add(new SelectItem("nombre", getBundleEtiquetas("nombre", null)));
//        
//        return condicionesPerjudicialesItems;
//    }
//
//    public void setCondicionesPerjudicialesItems(List<SelectItem> condicionesPerjudicialesItems) {
//        this.condicionesPerjudicialesItems = condicionesPerjudicialesItems;
//    }
//
//    public List<SelectItem> getAdmPortalItems() {
//        admPortalItems = new ArrayList<SelectItem>();
//        
//        admPortalItems.add(new SelectItem("tituloHtml", getBundleEtiquetas("titulo", null)));
//        
//        return admPortalItems;
//    }
//
//    public void setAdmPortalItems(List<SelectItem> admPortalItems) {
//        this.admPortalItems = admPortalItems;
//    }
//}
